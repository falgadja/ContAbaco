package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/DeletarEnderecoServlet")
public class DeletarEnderecoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.setAttribute("mensagemDeletar", "ID do endereço não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (enderecoDAO.deletar(id) > 0) {
                    request.setAttribute("mensagemDeletar", "Endereço deletado com sucesso!");
                } else {
                    request.setAttribute("mensagemDeletar", "Não foi possível deletar.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagemDeletar", "ID inválido.");
        } catch (Exception e) {
            request.setAttribute("mensagemDeletar", "Erro inesperado ao tentar deletar.");
        }

        // Caminho absoluto para o JSP de CRUD do endereço
        request.getRequestDispatcher("/WEB-INF/view/Endereco/crudEndereco.jsp").forward(request, response);
    }
}
