package servlet;

import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import model.Endereco;
import dao.EmpresaDAO;

import java.io.IOException;

@WebServlet(name = "CadastrarEmpresa", value = "/EmpresaServlet")
public class CadastrarEmpresaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Cria objeto empresa com os dados do formulário
        Empresa empresa = new Empresa();
        empresa.setCnpj(request.getParameter("cnpj"));
        empresa.setNome(request.getParameter("nome"));
        empresa.setEmail(request.getParameter("email"));
        empresa.setSenha(request.getParameter("senha"));
        empresa.setIdPlano(Integer.parseInt(request.getParameter("idPlano")));
        empresa.setQntdFuncionarios(Integer.parseInt(request.getParameter("qtdFuncionarios")));

        // Insere empresa e pega o ID gerado
        EmpresaDAO empresaDAO = new EmpresaDAO();
        int idEmpresa = empresaDAO.inserir(empresa);

        if (idEmpresa > 0) {
            // Redireciona para a página de endereço, passando o idEmpresa
            response.sendRedirect("endereco.jsp?idEmpresa=" + idEmpresa);
        } else {
            response.sendRedirect("erro.jsp");
        }
    }
}
