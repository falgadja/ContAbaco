package servlet;

import Validacao.Validacao;
import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;

import java.io.IOException;

@WebServlet("/cadastrarEmpresa")
public class CadastrarEmpresaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nomeEmpresa");
        String cnpj = request.getParameter("cnpj");
        String email = request.getParameter("emailEmpresa");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        // Validação de senha
        if (!senha.equals(confirmarSenha)) {
            request.setAttribute("erro", "As senhas não conferem!");
            request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
            return;
        }

        // Validar se o CNPJ já existe
        if (Validacao.valorExiste("Empresa", "cnpj", cnpj)) {
            request.setAttribute("erro", "Já existe um CNPJ cadastrado");
            request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
            return;
        }

        // Validar se o email já existe
        if (Validacao.valorExiste("Empresa", "email", email)) {
            request.setAttribute("erro", "Já existe um email cadastrado");
            request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
            return;
        }

        // Criação do objeto Empresa
        Empresa empresa = new Empresa();
        empresa.setNome(nome);
        empresa.setCnpj(cnpj);
        empresa.setEmail(email);
        empresa.setSenha(senha);
        empresa.setIdPlano(4);
        empresa.setQntdFuncionarios(0);

        // Inserção no banco
        EmpresaDAO empresaDAO = new EmpresaDAO();
        try {
            int idEmpresa = empresaDAO.inserir(empresa);
            if (idEmpresa > 0) {
                response.sendRedirect(request.getContextPath() + "/view/crud.jsp");
            } else {
                request.setAttribute("erro", "Não foi possível cadastrar a empresa.");
                request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao cadastrar empresa: " + e.getMessage());
            request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
        }
    }
}
