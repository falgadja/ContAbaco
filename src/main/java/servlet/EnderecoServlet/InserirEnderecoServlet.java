package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;
import utils.ValidacaoRegex;

import java.io.IOException;

@WebServlet("/endereco-create")
public class InserirEnderecoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Exibe o formulário de cadastro de endereço
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Endereco/cadastrarEndereco.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pais = request.getParameter("pais");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String rua = request.getParameter("rua");
        String cep = request.getParameter("cep");
        String numeroStr = request.getParameter("numero");
        String idEmpresaStr = request.getParameter("idEmpresa");
        String cepValidado = ValidacaoRegex.verificarCep(request.getParameter("cep"));

        try {
            // Validação de campos obrigatórios
            if (pais == null || estado == null || cidade == null || bairro == null ||
                    rua == null || cep == null || numeroStr == null || idEmpresaStr == null ||
                    pais.isBlank() || estado.isBlank() || cidade.isBlank() || bairro.isBlank() ||
                    rua.isBlank() || cep.isBlank() || numeroStr.isBlank() || idEmpresaStr.isBlank()) {

                request.setAttribute("mensagem", "Todos os campos são obrigatórios!");
                doGet(request, response);
                return;
            }  else if (cepValidado == null || cepValidado.trim().isEmpty()) {
                request.setAttribute("mensagem", "CEP inválido.");
                doGet(request, response);
                return;
            }

            int numero = Integer.parseInt(numeroStr);
            int idEmpresa = Integer.parseInt(idEmpresaStr);

            // Cria objeto Endereco
            Endereco endereco = new Endereco(pais, estado, cidade, bairro, rua, numero, cep, idEmpresa);

            // Insere no banco
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            int idGerado = enderecoDAO.inserir(endereco);

            if (idGerado > 0) {
                // Redireciona após sucesso
                response.sendRedirect(request.getContextPath() + "/endereco");
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar o endereço. Tente novamente!");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Erro: número inválido informado!");
            doGet(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro interno: " + e.getMessage());
            doGet(request, response);
        }
    }
}
