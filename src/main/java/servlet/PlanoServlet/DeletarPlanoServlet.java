package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/DeletarPlanoServlet")
public class DeletarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        PlanoDAO planoDAO = new PlanoDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.setAttribute("mensagemDeletar", "ID do plano não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (planoDAO.deletar(id) > 0) {
                    request.setAttribute("mensagemDeletar", "Plano deletado com sucesso!");
                } else {
                    request.setAttribute("mensagemDeletar", "Não foi possível deletar.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagemDeletar", "ID inválido.");
        } catch (Exception e) {
            request.setAttribute("mensagemDeletar", "Erro inesperado ao tentar deletar.");
        }

        // Caminho absoluto para o JSP de CRUD do plano
        request.getRequestDispatcher("/view/Plano/crudPlano.jsp").forward(request, response);
    }
}
