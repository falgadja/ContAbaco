package servlet.FuncionarioServlet;

import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import dao.SetorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import model.Funcionario;
import model.Setor;
import org.mindrot.jbcrypt.BCrypt;
import utils.ValidacaoRegex;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/funcionarios-update")
public class AtualizarFuncionarioServlet extends HttpServlet {

    /**
     * doGet: Carrega o funcionário e as listas de setores e empresas para edição.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo o ID do funcionário da URL
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do funcionário não informado.");
            response.sendRedirect(request.getContextPath() + "/funcionarios");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // Buscando o funcionário pelo ID
            Funcionario funcionario = new FuncionarioDAO().buscarPorId(id);
            if (funcionario == null) {
                request.getSession().setAttribute("mensagem", "Funcionário não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // Buscando listas para dropdowns
            List<Setor> setores = new SetorDAO().listarTodos();
            List<Empresa> empresas = new EmpresaDAO().listar();

            // Colocando os objetos no request
            request.setAttribute("funcionarioParaEditar", funcionario);
            request.setAttribute("setores", setores);
            request.setAttribute("empresas", empresas);

            // Encaminhando para a página de edição
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/atualizarFuncionario.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/funcionarios");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar funcionário: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/funcionarios");
        }
    }

    /**
     * doPost: Processa a atualização do funcionário.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo parâmetros do formulário
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String idSetorParam = request.getParameter("idSetor");
        String idEmpresaParam = request.getParameter("idEmpresa");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        try {
            // Validação básica
            if (idParam == null || idParam.isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    idSetorParam == null || idSetorParam.isEmpty() ||
                    idEmpresaParam == null || idEmpresaParam.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos.");

            } else if (!ValidacaoRegex.verificarEmail(email)) {
                request.getSession().setAttribute("mensagem", "Email inválido!");

            } else if(senha!=null && !senha.isEmpty()) {
                if (!ValidacaoRegex.verificarSenha(senha)) {
                    request.getSession().setAttribute("mensagem", "Nova senha inválida! Use ao menos 8 caracteres, você pode usar letras, números e símbolos como @, #, $, não use espaços.");
                }
            } else {
                // Convertendo parâmetros
                int id = Integer.parseInt(idParam);
                int idSetor = Integer.parseInt(idSetorParam);
                int idEmpresa = Integer.parseInt(idEmpresaParam);

                // Busca o funcionário existente
                Funcionario funcionario = funcionarioDAO.buscarPorId(id);
                if (funcionario == null) {
                    request.getSession().setAttribute("mensagem", "Funcionário não encontrado para atualizar.");
                } else {
                    // Atualiza dados do objeto
                    funcionario.setNome(nome.trim());
                    funcionario.setEmail(email.trim());
                    funcionario.setIdSetor(idSetor);
                    funcionario.setIdEmpresa(idEmpresa);

                    // Atualiza senha apenas se o campo foi preenchido
                    if (senha != null && !senha.trim().isEmpty()) {
                        funcionario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
                    }

                    // Atualiza no banco
                    if (funcionarioDAO.atualizar(funcionario) > 0) {
                        request.getSession().setAttribute("mensagem", "Funcionário atualizado com sucesso.");
                    } else {
                        request.getSession().setAttribute("mensagem", "Não foi possível atualizar o funcionário.");
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valores numéricos inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar funcionário: " + e.getMessage());
        }

        // Redireciona para a lista de funcionários
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
}
