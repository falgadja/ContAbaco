package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/planos-delete")
public class DeletarPlanoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET não deleta, apenas redireciona para a lista de planos
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/planos");
    }

    // POST realiza a exclusão do plano
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        PlanoDAO planoDAO = new PlanoDAO(); // DAO instanciado aqui
        String mensagem;

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do plano não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro);

                if (planoDAO.deletar(id) > 0) {
                    mensagem = "Plano deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o plano.";
                }
            }
        } catch (NumberFormatException nfe) {
            mensagem = "ID inválido.";
        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro inesperado ao tentar deletar o plano.";
        }

        // Salva a mensagem na sessão e redireciona para a lista
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/planos");
    }
}
