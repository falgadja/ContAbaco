package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * SERVLET RESPONSÁVEL POR DELETAR PLANOS
 * NÃO PERMITE GET PARA DELEÇÃO, APENAS REDIRECIONA
 * POST REALIZA A EXCLUSÃO
 */
@WebServlet("/planos-delete")
public class DeletarPlanoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET NÃO DELETA, APENAS REDIRECIONA PARA LISTA DE PLANOS
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/planos"); // REDIRECIONA PRA LISTA
    }

    // POST REALIZA A EXCLUSÃO DO PLANO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id"); // PEGA ID DO FORMULÁRIO
        PlanoDAO planoDAO = new PlanoDAO(); // INSTANCIA DAO
        String mensagem;

        try {
            // VERIFICA SE ID FOI INFORMADO
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do plano não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro); // CONVERTE PARA INTEIRO

                // TENTA DELETAR E DEFINE MENSAGEM
                if (planoDAO.deletar(id) > 0) {
                    mensagem = "Plano deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o plano.";
                }
            }
        } catch (NumberFormatException nfe) {
            // TRATA CASO ID NÃO SEJA NÚMERO
            mensagem = "ID inválido.";
        } catch (Exception e) {
            // ERRO INESPERADO
            e.printStackTrace(); // PARA DEBUG
            mensagem = "Erro inesperado ao tentar deletar o plano.";
        }

        // SALVA MENSAGEM NA SESSÃO E REDIRECIONA PARA LISTA DE PLANOS
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/planos");
    }
}
