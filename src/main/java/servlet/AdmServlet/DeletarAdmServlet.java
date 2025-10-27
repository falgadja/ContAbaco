package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/DeletarAdmServlet")
public class DeletarAdmServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        AdmDAO admDAO = new AdmDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.setAttribute("mensagemDeletar", "ID do administrador não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (admDAO.deletar(id) > 0) {
                    request.setAttribute("mensagemDeletar", "Administrador deletado com sucesso!");
                } else {
                    request.setAttribute("mensagemDeletar", "Não foi possível deletar.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagemDeletar", "ID Inválido.");
        } catch (Exception e) {
            request.setAttribute("mensagemDeletar", "Erro inesperado ao tentar deletar.");
        }

        // Caminho absoluto para o JSP de CRUD
        request.getRequestDispatcher("/view//Adm/crudAdm.jsp").forward(request, response);

    }
}
