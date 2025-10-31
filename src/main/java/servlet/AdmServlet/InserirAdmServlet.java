package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Administrador;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/adm-create")
public class InserirAdmServlet extends HttpServlet {

    // Regex simples para validar email
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Encaminha para o formulário de cadastro de Administrador
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/cadastrarAdm.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        // Validações básicas
        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            request.setAttribute("mensagem", "Preencha todos os campos!");
            doGet(request, response);
            return;
        }

        if (!EMAIL_REGEX.matcher(email).matches()) {
            request.setAttribute("mensagem", "Email inválido!");
            doGet(request, response);
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            request.setAttribute("mensagem", "As senhas não são iguais!");
            doGet(request, response);
            return;
        }

        // Criptografa a senha
        String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

        try {
            AdmDAO admDAO = new AdmDAO();

            // Verifica se o email já está cadastrado
            if (admDAO.buscarPorEmail(email) != null) {
                request.setAttribute("mensagem", "Já existe um administrador com este e-mail!");
                doGet(request, response);
                return;
            }

            // Cria objeto administrador
            Administrador administrador = new Administrador();
            administrador.setEmail(email);
            administrador.setSenha(senhaHash);

            // Insere no banco
            int idAdm = admDAO.inserir(administrador);

            if (idAdm > 0) {
                // Sucesso → redireciona para a listagem
                response.sendRedirect(request.getContextPath() + "/adm");
            } else {
                request.setAttribute("mensagem", "Erro ao cadastrar administrador!");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro interno: " + e.getMessage());
            doGet(request, response);
        }
    }
}
