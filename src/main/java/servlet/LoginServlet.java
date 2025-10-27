package servlet;

import dao.AdmDAO;
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        EmpresaDAO empresaDAO = new EmpresaDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        AdmDAO admDAO = new AdmDAO();

        try {
            // Buscar o hash da senha de cada tipo de usuário
            String hashAdm = admDAO.buscarHashPorEmail(email);
            String hashFunc = funcionarioDAO.buscarHashPorEmail(email);
            String hashEmpresa = empresaDAO.buscarHashPorEmail(email);

            if (hashAdm != null && BCrypt.checkpw(senha, hashAdm)) {
                request.getRequestDispatcher("/view/Empresa/crudEmpresa.jsp").forward(request, response);
            } else if (hashFunc != null && BCrypt.checkpw(senha, hashFunc)) {
                request.getRequestDispatcher("/view/Funcionario/crudFuncionario.jsp").forward(request, response);
            } else if (hashEmpresa != null && BCrypt.checkpw(senha, hashEmpresa)) {
                request.getRequestDispatcher("/view/Erros/erro.jsp").forward(request, response);
            } else {
                request.setAttribute("mensagem", "Email ou senha inválidos, digite novamente");
                request.getRequestDispatcher("/view/Login/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("mensagem", "Erro inesperado: " + e.getMessage());
            request.getRequestDispatcher("/view/Login/login/jsp").forward(request, response);
        }
    }
}
