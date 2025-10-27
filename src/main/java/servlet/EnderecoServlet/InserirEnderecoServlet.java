package servlet.EnderecoServlet;
import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;

import java.io.IOException;

@WebServlet("/InserirEndereco")
public class InserirEnderecoServlet extends HttpServlet {

    @Override // Inicializa o servlet de cadastro de endereço
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Apenas exibe o formulário de cadastro
        request.getRequestDispatcher("/view/Endereco/cadastrarEndereco.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pais = request.getParameter("pais");
            String estado = request.getParameter("estado");
            String cidade = request.getParameter("cidade");
            String bairro = request.getParameter("bairro");
            String rua = request.getParameter("rua");
            String cep = request.getParameter("cep");
            String numeroStr = request.getParameter("numero");
            String idEmpresaStr = request.getParameter("idEmpresa");

            // Validação de campos obrigatórios
            if (pais.isBlank() || estado.isBlank() || cidade.isBlank() || bairro.isBlank() ||
                    rua.isBlank() || cep.isBlank() || numeroStr.isBlank() || idEmpresaStr.isBlank()) {
                request.setAttribute("mensagem", "Todos os campos são obrigatórios!");
                request.getRequestDispatcher("/view/Endereco/cadastrarEndereco.jsp").forward(request, response);
                return;
            }

            int numero = Integer.parseInt(numeroStr);
            int idEmpresa = Integer.parseInt(idEmpresaStr);

            Endereco endereco = new Endereco(pais, estado, cidade, bairro, rua, numero, cep, idEmpresa);
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            int idGerado = enderecoDAO.inserir(endereco);

            if (idGerado > 0) {
                response.sendRedirect(request.getContextPath() + "/view/Empresa/crudEmpresa.jsp  ");
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar o endereço. Tente novamente!");
                request.getRequestDispatcher("/view/Endereco/cadastrarEndereco.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Erro: número inválido informado!");
            request.getRequestDispatcher("/view/Endereco/cadastrarEndereco.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("mensagem", "Erro ao processar o cadastro de endereço!");
            request.getRequestDispatcher("/view/Endereco/cadastrarEndereco.jsp").forward(request, response);
        }
    }


    @Override // Finaliza o servlet e libera recursos alocados
    public void destroy() {
        super.destroy();
    }
}
