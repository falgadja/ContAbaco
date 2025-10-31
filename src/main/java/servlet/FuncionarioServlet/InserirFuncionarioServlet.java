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

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);

    // doGet exibe o formulário de cadastro
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SetorDAO setorDAO = new SetorDAO();
            EmpresaDAO empresaDAO = new EmpresaDAO();

            List<Setor> setores = setorDAO.listarTodos();
            List<Empresa> empresas = empresaDAO.listar();

            request.setAttribute("setores", setores);
            request.setAttribute("empresas", empresas);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao carregar listas de setores ou empresas.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/cadastrarFuncionario.jsp");
        dispatcher.forward(request, response);
    }

    // doPost processa o cadastro e redireciona para o CRUD com modal
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarSenha");
            String dataNascimentoStr = request.getParameter("dataNascimento");
            int idSetor = Integer.parseInt(request.getParameter("idSetor"));
            int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));

            if (nome == null || nome.isBlank() || sobrenome == null || sobrenome.isBlank()) {
                request.getSession().setAttribute("mensagem", "Preencha todos os campos obrigatórios!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                request.getSession().setAttribute("mensagem", "As senhas não conferem!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            if (!EMAIL_REGEX.matcher(email).matches()) {
                request.getSession().setAttribute("mensagem", "Email inválido!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            LocalDate dataNascimento;
            try {
                dataNascimento = LocalDate.parse(dataNascimentoStr);
            } catch (DateTimeParseException e) {
                request.getSession().setAttribute("mensagem", "Data de nascimento inválida!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

            if (funcionarioDAO.buscarHashPorEmail(email) != null) {
                request.getSession().setAttribute("mensagem", "Já existe um funcionário com este email!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nome);
            funcionario.setSobrenome(sobrenome);
            funcionario.setEmail(email);
            funcionario.setSenha(senhaHash);
            funcionario.setDataNascimento(dataNascimento);
            funcionario.setIdSetor(idSetor);
            funcionario.setIdEmpresa(idEmpresa);

            int id = funcionarioDAO.inserir(funcionario);

            if (id > 0) {
                request.getSession().setAttribute("mensagem", "Funcionário cadastrado com sucesso!");
            } else {
                request.getSession().setAttribute("mensagem", "Erro ao cadastrar funcionário!");
            }

            response.sendRedirect(request.getContextPath() + "/funcionarios");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro interno: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/funcionarios");
        }
    }
}
