package servlet;

import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/RedefinirSenhaServlet")
public class RedefinirSenhaEmpresaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cnpj = request.getParameter("cnpj");
        String email = request.getParameter("email");
        String novaSenha = request.getParameter("senha");

        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = empresaDAO.buscarPorCnpj(cnpj);

        if (empresa != null && empresa.getEmail().equals(email)) {
            // Atualiza a senha
            empresa.setSenha(novaSenha);
            int resultado = empresaDAO.atualizar(empresa);

            if (resultado == 1) {
                response.getWriter().println("Senha redefinida com sucesso!");
            } else {
                response.getWriter().println("Erro ao redefinir a senha.");
            }
        } else {
            response.getWriter().println("Empresa não encontrada ou e-mail inválido.");
        }
    }
}
