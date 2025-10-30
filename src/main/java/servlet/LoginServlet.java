package servlet;

import dao.AdmDAO;
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import dao.PagamentoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Pagamento;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Login/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        EmpresaDAO empresaDAO = new EmpresaDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        AdmDAO admDAO = new AdmDAO();

        try {
            String hashAdm = admDAO.buscarHashPorEmail(email);
            String hashFunc = funcionarioDAO.buscarHashPorEmail(email);
            String hashEmpresa = empresaDAO.buscarHashPorEmail(email);

            if (hashAdm != null && BCrypt.checkpw(senha, hashAdm)) {
                response.sendRedirect(request.getContextPath() + "/adm");

            } else if (hashFunc != null && BCrypt.checkpw(senha, hashFunc)) {
                response.sendRedirect(request.getContextPath() + "/funcionarios");

            } else if (hashEmpresa != null && BCrypt.checkpw(senha, hashEmpresa)) {
                response.sendRedirect(request.getContextPath() + "/empresas");

            } else {
                request.setAttribute("mensagem", "Email ou senha inválidos, digite novamente");
                request.getRequestDispatcher("/WEB-INF/view/Login/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("mensagem", "Erro inesperado: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/Login/login.jsp").forward(request, response);
        }
    }
}