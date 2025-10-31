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

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/funcionarios-create")
public class InserirFuncionarioServlet extends HttpServlet {

    // Regex de exemplo (validação de email)
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Buscar listas para dropdowns
            SetorDAO setorDAO = new SetorDAO();
            EmpresaDAO empresaDAO = new EmpresaDAO();

            List<Setor> setores = setorDAO.listarTodos(); // Todos os setores
            List<Empresa> empresas = empresaDAO.listar(); // Todas as empresas

            // Enviar listas para a JSP
            request.setAttribute("setores", setores);
            request.setAttribute("empresas", empresas);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao carregar listas de setores ou empresas.");
        }

        // Encaminhar para o formulário de cadastro
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/cadastrarFuncionario.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");
        String dataNascimentoStr = request.getParameter("dataNascimento");
        int idSetor = Integer.parseInt(request.getParameter("idSetor"));
        int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));

        // Validações básicas
        if (!senha.equals(confirmarSenha)) {
            request.setAttribute("mensagem", "As senhas não são iguais!");
            doGet(request, response); // Recarrega as listas
            return;
        }

        if (!EMAIL_REGEX.matcher(email).matches()) {
            request.setAttribute("mensagem", "Email inválido!");
            doGet(request, response);
            return;
        }

        LocalDate dataNascimento;
        try {
            dataNascimento = LocalDate.parse(dataNascimentoStr);
        } catch (DateTimeParseException e) {
            request.setAttribute("mensagem", "Data de nascimento inválida!");
            doGet(request, response);
            return;
        }

        // Criptografar senha
        String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

        try {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

            // Verifica se já existe email cadastrado
            if (funcionarioDAO.buscarHashPorEmail(email) != null) {
                request.setAttribute("mensagem", "Já existe um funcionário com este email!");
                doGet(request, response);
                return;
            }

            // Cria objeto funcionário
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nome);
            funcionario.setSobrenome(sobrenome);
            funcionario.setEmail(email);
            funcionario.setSenha(senhaHash);
            funcionario.setDataNascimento(dataNascimento);
            funcionario.setIdSetor(idSetor);
            funcionario.setIdEmpresa(idEmpresa);

            // Insere no banco
            int id = funcionarioDAO.inserir(funcionario);
            if (id > 0) {
                // Sucesso: redireciona para a listagem
                response.sendRedirect(request.getContextPath() + "/funcionarios");
            } else {
                request.setAttribute("mensagem", "Erro ao cadastrar funcionário!");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro interno: " + e.getMessage());
            doGet(request, response);
        }
    }
}
