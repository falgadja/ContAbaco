package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Administrador;

import java.io.IOException;

@WebServlet("/AtualizarAdmServlet")
public class AtualizarAdmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona GET para POST
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        AdmDAO admDAO = new AdmDAO();
        String mensagem;

        try {
            if (idParametro == null || idParametro.isEmpty() ||
                    email == null || email.trim().isEmpty()) {

                mensagem = "Parâmetros inválidos. Não foi possível atualizar.";

            } else {
                int id = Integer.parseInt(idParametro);

                Administrador adm = new Administrador();
                adm.setId(id);
                adm.setEmail(email);

                // Atualiza senha apenas se o usuário digitou algo
                if (senha != null && !senha.trim().isEmpty()) {
                    adm.setSenha(senha);
                }

                if (admDAO.atualizar(adm) > 0) {
                    mensagem = "Administrador atualizado com sucesso!";
                } else {
                    mensagem = "Não foi possível atualizar o administrador.";
                }
            }

        } catch (NumberFormatException nfe) {
            mensagem = "ID inválido.";
        } catch (Exception e) {
            mensagem = "Erro inesperado ao tentar atualizar.";
            e.printStackTrace();
        }

        // Redireciona para o CRUD com a mensagem como query string
        response.sendRedirect(request.getContextPath() + "/view/Adm/crudAdm.jsp?msg=");
    }
}
