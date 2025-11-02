package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;

import java.io.IOException;

/**
 * SERVLET RESPONSÁVEL POR DELETAR EMPRESAS
 * NÃO PERMITE GET PARA DELEÇÃO, APENAS REDIRECIONA
 * POST REALIZA A EXCLUSÃO, DELETANDO PRIMEIRO ENDEREÇO SE EXISTIR
 */
@WebServlet("/empresa-delete")
public class DeletarEmpresaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET NÃO DELETA, APENAS REDIRECIONA PARA LISTAGEM
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/empresas"); // REDIRECIONA PRA LISTA DE EMPRESAS
    }

    // POST REALIZA A EXCLUSÃO DA EMPRESA
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id"); // PEGA ID DO FORMULÁRIO

        // INSTANCIA DAOS E OBJETO ENDEREÇO
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        Endereco endereco = new Endereco();

        String mensagem;

        try {
            // VERIFICA SE ID FOI INFORMADO
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID da empresa não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro); // CONVERTE ID PARA INTEIRO

                // BUSCA ENDEREÇO ASSOCIADO À EMPRESA
                endereco = enderecoDAO.buscarPorEmpresa(id);

                if (endereco != null) {
                    // DELETA PRIMEIRO O ENDEREÇO
                    enderecoDAO.deletar(endereco.getId());
                }

                // DELETA AGORA A EMPRESA
                if (empresaDAO.deletar(id) > 0) {
                    mensagem = "Empresa deletada com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar a empresa.";
                }
            }
        } catch (NumberFormatException nfe) {
            // TRATA ID INVÁLIDO
            mensagem = "ID inválido.";
        } catch (Exception e) {
            // ERRO INESPERADO
            e.printStackTrace(); // PARA DEBUG
            mensagem = "Erro inesperado ao tentar deletar.";
        }

        // SALVA MENSAGEM NA SESSÃO E REDIRECIONA PARA LISTA DE EMPRESAS
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/empresas");
    }
}
