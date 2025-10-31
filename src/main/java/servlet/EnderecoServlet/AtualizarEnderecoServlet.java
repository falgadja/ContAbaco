package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;

import java.io.IOException;

@WebServlet("/endereco-update")
public class AtualizarEnderecoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostra o formulário de edição
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Endereco/atualizarEndereco.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String pais = request.getParameter("pais");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String rua = request.getParameter("rua");
        String numeroParam = request.getParameter("numero");
        String cep = request.getParameter("cep");
        String idEmpresaParam = request.getParameter("idEmpresa");

        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {
            if (idParam == null || idParam.isEmpty() ||
                    pais == null || pais.trim().isEmpty() ||
                    estado == null || estado.trim().isEmpty() ||
                    cidade == null || cidade.trim().isEmpty() ||
                    bairro == null || bairro.trim().isEmpty() ||
                    rua == null || rua.trim().isEmpty() ||
                    numeroParam == null || numeroParam.isEmpty() ||
                    cep == null || cep.trim().isEmpty() ||
                    idEmpresaParam == null || idEmpresaParam.isEmpty()) {

                request.setAttribute("mensagemAtualizar", "Preencha todos os campos corretamente.");

            } else {
                int id = Integer.parseInt(idParam);
                int numero = Integer.parseInt(numeroParam);
                int idEmpresa = Integer.parseInt(idEmpresaParam);

                Endereco endereco = new Endereco(id, pais, estado, cidade, bairro, rua, numero, cep, idEmpresa);

                if (enderecoDAO.atualizar(endereco) > 0) {
                    request.getSession().setAttribute("mensagem", "Endereço atualizado com sucesso.");
                    response.sendRedirect(request.getContextPath() + "/empresa");
                    return;
                } else {
                    request.setAttribute("mensagemAtualizar", "Não foi possível atualizar o endereço.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagemAtualizar", "Valores numéricos inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao atualizar o endereço: " + e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/Endereco/atualizarEndereco.jsp");
    }
}
