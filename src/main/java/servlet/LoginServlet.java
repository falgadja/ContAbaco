package servlet;

import java.io.IOException;

import dao.AdmDAO;
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // recebendo os parametros e declarando as variaveis
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // declarando as DAOs
        EmpresaDAO empresaDAO = new EmpresaDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        AdmDAO admDAO = new AdmDAO();

        try {
            if (empresaDAO.buscarPorEmailESenha(email, senha) != null) {
                request.getRequestDispatcher("/view/erro.jsp").forward(request, response);
            } else if (funcionarioDAO.buscarPorEmailESenha(email, senha) != null) {
                request.getRequestDispatcher("/view/erro.jsp").forward(request, response);
            } else if (admDAO.buscarPorEmailSenha(email, senha) != null) {
                request.getRequestDispatcher("/view/crud.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/view/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("mensagem", "Email ou senha invalidos, digite novamente");
            request.getRequestDispatcher("/view/login.jsp").forward(request, response);
        }
    }
}
