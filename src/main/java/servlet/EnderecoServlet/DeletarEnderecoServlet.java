package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * SERVLET RESPONSÁVEL POR DELETAR ENDEREÇOS
 * NÃO PERMITE GET PARA DELEÇÃO, APENAS REDIRECIONA
 * POST REALIZA A EXCLUSÃO
 */
@WebServlet("/endereco-delete")
public class DeletarEnderecoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET NÃO DELETA, APENAS REDIRECIONA PARA LISTA DE ENDEREÇOS
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/endereco"); // REDIRECIONA PRA LISTA
    }

    // POST REALIZA A EXCLUSÃO DO ENDEREÇO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id"); // PEGA ID DO FORMULÁRIO
        EnderecoDAO enderecoDAO = new EnderecoDAO(); // INSTANCIA DAO
        String mensagem;

        try {
            // VERIFICA SE ID FOI INFORMADO
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do endereço não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro); // CONVERTE PARA INTEIRO

                // TENTA DELETAR E DEFINE MENSAGEM
                if (enderecoDAO.deletar(id) > 0) {
                    mensagem = "Endereço deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o endereço.";
                }
            }
        } catch (NumberFormatException nfe) {
            // TRATA CASO ID NÃO SEJA NÚMERO
            mensagem = "ID inválido.";
        } catch (Exception e) {
            // ERRO INESPERADO
            e.printStackTrace(); // PARA DEBUG
            mensagem = "Erro inesperado ao tentar deletar o endereço.";
        }

        // SALVA MENSAGEM NA SESSÃO E REDIRECIONA PARA LISTA DE ENDEREÇOS
        request.getSession().setAttribute("mensagemDeletar", mensagem);
        response.sendRedirect(request.getContextPath() + "/endereco");
    }
}
