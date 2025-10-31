package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/adm-delete")
public class DeletarAdmServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET não deleta, apenas redireciona para a listagem
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/adm");
    }

    // POST realiza a exclusão do administrador
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        AdmDAO admDAO = new AdmDAO(); // Instancia aqui, como pedido

        String mensagem;

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do administrador não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro);

                if (admDAO.deletar(id) > 0) {
                    mensagem = "Administrador deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o administrador.";
                }
            }
        } catch (NumberFormatException nfe) {
            mensagem = "ID inválido.";
        } catch (Exception e) {
            mensagem = "Erro inesperado ao tentar deletar o administrador.";
            e.printStackTrace(); // simples, para debug
        }

        // Salva a mensagem e redireciona para a lista
        request.getSession().setAttribute("mensagemDeletar", mensagem);
        response.sendRedirect(request.getContextPath() + "/adm");
    }
}
