package servlet;

import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Empresa;

import java.io.IOException;

@WebServlet(name="loginEmpresaServlet")
public class LoginEmpresaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Evitar problemas com acentuação
        request.setCharacterEncoding("UTF-8");

        // Pegar do formulário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Criar objeto DAO e buscar empresa
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.buscarPorSenhaEEemail(email, senha);

        if (empresa != null) {
            //  Criar sessão
            HttpSession session = request.getSession();
            session.setAttribute("empresaLogada", empresa);

            // Redireciona para página de sucesso
            response.sendRedirect("respostaLogin.jsp");
        } else {
            // Redireciona de volta com erro
            response.sendRedirect("login.jsp?erro=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona GET para a página de login
        response.sendRedirect("login.jsp");
    }
}
