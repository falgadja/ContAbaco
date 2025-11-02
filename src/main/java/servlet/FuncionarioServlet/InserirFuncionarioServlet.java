package servlet.FuncionarioServlet;

// IMPORTS DO DAO E MODEL
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import dao.SetorDAO;
import model.Empresa;
import model.Funcionario;
import model.Setor;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// BIBLIOTECA PRA CRIPTOGRAFAR SENHAS
import org.mindrot.jbcrypt.BCrypt;
// UTILIDADES DE VALIDAÇÃO
import utils.ValidacaoRegex;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

// DEFINE A ROTA DO SERVLET
@WebServlet("/funcionarios-create")
public class InserirFuncionarioServlet extends HttpServlet {

    // MÉTODO DOGET → EXIBE O FORMULÁRIO DE CADASTRO DE FUNCIONÁRIO
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // BUSCA LISTA DE SETORES E EMPRESAS PRA SELECT
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

        // REDIRECIONA PRO JSP DE CADASTRO
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/cadastrarFuncionario.jsp");
        dispatcher.forward(request, response);
    }

    // MÉTODO DOPOST → PROCESSA O CADASTRO DE FUNCIONÁRIO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PEGANDO DADOS DO FORMULÁRIO
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarSenha");
            String dataNascimentoStr = request.getParameter("dataNascimento");
            int idSetor = Integer.parseInt(request.getParameter("idSetor"));
            int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));

            // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
            if (nome == null || nome.isBlank() || sobrenome == null || sobrenome.isBlank()) {
                request.getSession().setAttribute("mensagem", "Preencha todos os campos obrigatórios!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // VALIDAÇÃO DE SENHAS
            if (!senha.equals(confirmarSenha)) {
                request.setAttribute("mensagem", "As senhas não são iguais!");
                doGet(request, response); // Recarrega listas de setores e empresas
                return;
            }

            if (!ValidacaoRegex.verificarSenha(senha)) {
                request.setAttribute("mensagem", "Senha inválida! Use ao menos 8 caracteres, letras, números e símbolos, sem espaços.");
                doGet(request, response);
                return;
            }

            // VALIDAÇÃO DE EMAIL
            if (!ValidacaoRegex.verificarEmail(email)) {
                request.setAttribute("mensagem", "Email inválido!");
                doGet(request, response);
                return;
            }

            // VALIDAÇÃO DE DATA
            LocalDate dataNascimento;
            try {
                dataNascimento = LocalDate.parse(dataNascimentoStr);
            } catch (DateTimeParseException e) {
                request.getSession().setAttribute("mensagem", "Data de nascimento inválida!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // CRIPTOGRAFANDO SENHA
            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

            // VERIFICA SE EMAIL JÁ EXISTE
            if (funcionarioDAO.buscarHashPorEmail(email) != null) {
                request.getSession().setAttribute("mensagem", "Já existe um funcionário com este email!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // CRIA OBJETO FUNCIONÁRIO
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nome);
            funcionario.setSobrenome(sobrenome);
            funcionario.setEmail(email);
            funcionario.setSenha(senhaHash);
            funcionario.setDataNascimento(dataNascimento);
            funcionario.setIdSetor(idSetor);
            funcionario.setIdEmpresa(idEmpresa);

            // INSERE NO BANCO
            int id = funcionarioDAO.inserir(funcionario);

            // RETORNA MENSAGEM DE SUCESSO OU ERRO
            if (id > 0) {
                request.getSession().setAttribute("mensagem", "Funcionário cadastrado com sucesso!");
            } else {
                request.getSession().setAttribute("mensagem", "Erro ao cadastrar funcionário!");
            }

            // REDIRECIONA PRA LISTA DE FUNCIONÁRIOS
            response.sendRedirect(request.getContextPath() + "/funcionarios");

        } catch (Exception e) {
            // TRATAMENTO DE ERRO INTERNO
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro interno: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/funcionarios");
        }
    }
}
