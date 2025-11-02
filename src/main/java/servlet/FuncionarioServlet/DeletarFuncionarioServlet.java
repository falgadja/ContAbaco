package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * SERVLET RESPONSÁVEL POR DELETAR FUNCIONÁRIOS
 * NÃO PERMITE GET PARA DELEÇÃO, APENAS REDIRECIONA
 * POST REALIZA A EXCLUSÃO
 */
@WebServlet("/funcionarios-delete")
public class DeletarFuncionarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET NÃO DELETA, APENAS REDIRECIONA PARA LISTA DE FUNCIONÁRIOS
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/funcionarios"); // REDIRECIONA PRA LISTA
    }

    // POST REALIZA A EXCLUSÃO DO FUNCIONÁRIO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id"); // PEGA ID DO FORMULÁRIO
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(); // INSTANCIA DAO
        String mensagem;

        try {
            // VERIFICA SE ID FOI INFORMADO
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do funcionário não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro); // CONVERTE PARA INTEIRO

                // TENTA DELETAR E DEFINE MENSAGEM
                if (funcionarioDAO.deletar(id) > 0) {
                    mensagem = "Funcionário deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o funcionário.";
                }
            }
        } catch (NumberFormatException nfe) {
            // TRATA CASO ID NÃO SEJA NÚMERO
            mensagem = "ID inválido.";
        } catch (Exception e) {
            // ERRO INESPERADO
            e.printStackTrace(); // PARA DEBUG
            mensagem = "Erro inesperado ao tentar deletar o funcionário.";
        }

        // SALVA MENSAGEM NA SESSÃO E REDIRECIONA PARA LISTA DE FUNCIONÁRIOS
        request.getSession().setAttribute("mensagemDeletar", mensagem);
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
}
