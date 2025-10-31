package servlet.EmpresaServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import dao.EmpresaDAO;
import dao.EnderecoDAO;
import dao.FuncionarioDAO;
import model.Empresa;
import model.Endereco;
import model.Funcionario;

/**
 * Servlet responsável por carregar os detalhes de uma empresa, incluindo
 * seu endereço e funcionários, e encaminhar os dados para o JSP.
 */
@WebServlet("/detalhesEmpresa") // URL que acessa este Servlet
public class DetalhesEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebe o parâmetro "id" da URL (exemplo: detalhesEmpresa?id=3)
        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam); // Converte para inteiro

                // Busca a empresa pelo id
                EmpresaDAO empresaDAO = new EmpresaDAO();
                Empresa empresa = empresaDAO.buscarPorId(id);

                if (empresa != null) {
                    // Se a empresa existe, busca o endereço dela
                    EnderecoDAO enderecoDAO = new EnderecoDAO();
                    Endereco endereco = enderecoDAO.buscarPorEmpresa(id); // busca pelo id da empresa

                    // Busca todos os funcionários da empresa
                    FuncionarioDAO funcDAO = new FuncionarioDAO();
                    List<Funcionario> funcionarios = funcDAO.buscarPorIdEmpresa(id);

                    // Armazena os objetos como atributos da requisição
                    // para que o JSP consiga acessar
                    request.setAttribute("empresa", empresa);
                    request.setAttribute("endereco", endereco);
                    request.setAttribute("funcionarios", funcionarios);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace(); // Se id não for número, mostra erro no console
            }
        }

        // Encaminha para o JSP responsável pela exibição dos detalhes
        request.getRequestDispatcher("/WEB-INF/view/Empresa/detalhesEmpresa.jsp").forward(request, response);
    }
}
