package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Define que este servlet será acessado pela URL /DeletarPlanoServlet
@WebServlet("/DeletarPlanoServlet")
public class DeletarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona chamadas GET para o metodo POST
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        PlanoDAO planoDAO = new PlanoDAO();

        try {
            // Verifica se o parâmetro ID está vazio ou nulo
            if (idParametro == null || idParametro.isEmpty()) {

                // Define uma mensagem de erro que será mostrada
                request.setAttribute("mensagemDeletar", "ID do Plano não foi encontrado.");

            } else {
                // Converte o ID recebido (String) para número inteiro
                int id = Integer.parseInt(idParametro);

                // Chama o metodo deletar do DAO
                if (planoDAO.deletar(id) > 0) {
                    request.setAttribute("mensagemDeletar", "Plano deletado com sucesso!");
                } else {
                    request.setAttribute("mensagemDeletar", "Não foi possível deletar.");
                }
            }
        } catch (NumberFormatException nfe) {
            // Caso o ID enviado não seja um número
            request.setAttribute("mensagemDeletar", "ID Inválido.");
        } catch (Exception e) {
            // Caso ocorra qualquer outro erro inesperado
            request.setAttribute("mensagemDeletar", "Erro inesperado ao tentar deletar.");
        }

        // Encaminha a requisição e resposta para a página JSP de CRUD
        request.getRequestDispatcher("../crud.jsp").forward(request, response);
    }
}
