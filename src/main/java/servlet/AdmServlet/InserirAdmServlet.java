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
import java.util.ArrayList;
import java.util.List;
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

        String mensagem = null;

        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            mensagem = "Preencha todos os campos!";
        } else if (!EMAIL_REGEX.matcher(email).matches()) {
            mensagem = "Email inválido!";
        } else if (!senha.equals(confirmarSenha)) {
            mensagem = "As senhas não são iguais!";
        }

        AdmDAO admDAO = new AdmDAO();
        List<Administrador> adms = new ArrayList<>();

        try {
            // Verifica duplicidade
            if (mensagem == null && admDAO.buscarPorEmail(email) != null) {
                mensagem = "Já existe um administrador com este e-mail!";
            }

            if (mensagem != null) {
                adms = admDAO.listar();
                request.setAttribute("adms", adms);
                request.setAttribute("mensagem", mensagem);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Cria e insere o administrador
            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
            Administrador administrador = new Administrador();
            administrador.setEmail(email);
            administrador.setSenha(senhaHash);

            int idAdm = admDAO.inserir(administrador);

            if (idAdm > 0) {
                response.sendRedirect(request.getContextPath() + "/adm");
            } else {
                mensagem = "Erro ao cadastrar administrador!";
                adms = admDAO.listar();
                request.setAttribute("adms", adms);
                request.setAttribute("mensagem", mensagem);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro interno: " + e.getMessage();
            try {
                adms = admDAO.listar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            request.setAttribute("adms", adms);
            request.setAttribute("mensagem", mensagem);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
            dispatcher.forward(request, response);
        }
    }

}
