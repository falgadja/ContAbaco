package servlet;

import dao.AdmDAO;
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="loginEmpresaServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        EmpresaDAO empresaDAO = new EmpresaDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        AdmDAO admDAO = new AdmDAO();
        if (empresaDAO.buscarPorSenhaEEemail(email, senha) != null) {
            request.getRequestDispatcher("WEB-INF/crudEmpresa.jsp").forward(request, response);
        } else if (funcionarioDAO.buscarPorEmailESenha(email, senha) != null) {
            request.getRequestDispatcher("WEB-INF/crudFuncionario.jsp").forward(request, response);
        } else if (admDAO.buscarPorEmailSenha(email, senha) != null) {
            request.getRequestDispatcher("WEB-INF/crudADM.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("WEB-INF/EmailNaoEncontrado.jsp").forward(request, response);
        }
    }
}
