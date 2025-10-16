package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet(name = "CadastrarEmpresa", value="/cadastarEmpresa")
public class CadastrarEmpresaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Dados da empresa
        String nomeEmpresa=request.getParameter("nomeEmpresa");
        String cnpjEmpresa=request.getParameter("cnpj");
        String emailEmpresa=request.getParameter("email");
        String senha=request.getParameter("senha");
        String confirmaSenha=request.getParameter("confirmaSenha");

        //Endereço da empresa






    }
}
