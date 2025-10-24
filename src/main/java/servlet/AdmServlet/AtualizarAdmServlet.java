package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Administrador;

import java.io.IOException;

// Define que este servlet será acessado pela URL /AtualizarAdmServlet
@WebServlet("/AtualizarAdmServlet")
public class AtualizarAdmServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona chamadas GET para o metodo POST
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo dados do formulário
        String idParametro = request.getParameter("id");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Administrador administrador = new Administrador();
        AdmDAO admDAO = new AdmDAO();

        try {
            // Verifica se os parâmetros estão vazios ou nulos
            if (idParametro == null || idParametro.isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    senha == null || senha.trim().isEmpty()) {

                // Define uma mensagem de erro que será mostrada
                request.setAttribute("mensagemAtualizar", "Não foi possível encontrar os parâmetros.");

            } else {

                int id = Integer.parseInt(idParametro);

                // Criando o objeto do modelo
                administrador.setId(id);
                administrador.setEmail(email);
                administrador.setSenha(senha);

                // Chama o metodo atualizar do dao
                if (admDAO.atualizar(administrador) > 0) {
                    request.setAttribute("mensagemAtualizar", "Administrador atualizado com sucesso.");
                } else {
                    request.setAttribute("mensagemAtualizar", "Não foi possível atualizar o Administrador.");
                }
            }
        } catch (NumberFormatException nfe) {
            // Caso o ID enviado não seja um número
            request.setAttribute("mensagemAtualizar", "ID Inválido.");
        } catch (Exception e) {
            // Caso ocorra qualquer outro erro inesperado
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar.");
        }
    }
}
