package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Administrador;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/adm-create") // 1. URL MUDADA
public class InserirAdmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 2. CAMINHO CORRIGIDO: mostra o formulário de cadastro
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/cadastrarAdm.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        if (!senha.equals(confirmarSenha)) {
            request.setAttribute("mensagem", "As senhas não são iguais!");
            // 3. CORREÇÃO: Em caso de erro, encaminha de volta para a PÁGINA DE CADASTRO
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/cadastrarAdm.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

        try {
            Administrador administrador = new Administrador();
            administrador.setEmail(email);
            administrador.setSenha(senhaHash);
            AdmDAO admDAO = new AdmDAO();

            if (admDAO.buscarPorEmail(email) != null) {
                request.setAttribute("mensagem", "Já existe um cadastro com este e-mail!");
                // 4. CORREÇÃO: Em caso de erro, encaminha de volta para a PÁGINA DE CADASTRO
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/cadastrarAdm.jsp");
                dispatcher.forward(request, response);
                return;
            }

            int idAdm = admDAO.inserir(administrador);
            if (idAdm > 0) {
                // 5. SUCESSO! Redireciona para o servlet de LISTAGEM
                response.sendRedirect(request.getContextPath() + "/adm");
            } else {
                // ... (tratar erro) ...
            }
        } catch (Exception e) {
            // ... (tratar erro) ...
        }
    }
}