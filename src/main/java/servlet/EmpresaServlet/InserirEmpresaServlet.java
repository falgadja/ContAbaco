package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import org.mindrot.jbcrypt.BCrypt;
import utils.ValidacaoRegex;

import java.io.IOException;

@WebServlet("/empresas-create")
public class InserirEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/cadastrarEmpresa.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nome = request.getParameter("nomeEmpresa");
            String cnpj = request.getParameter("cnpj");
            String email = request.getParameter("emailEmpresa");
            String senha = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarSenha");
            String idPlanoStr = request.getParameter("idPlano");
            String qntdFuncStr = request.getParameter("qntdFuncionarios");
            String cnpjValidado = ValidacaoRegex.verificarCnpj(request.getParameter("cnpj"));

            if (nome == null || nome.isBlank() || senha == null || confirmarSenha == null || !senha.equals(confirmarSenha)) {
                request.getSession().setAttribute("mensagem", "As senhas não conferem ou campos obrigatórios estão vazios!");
                response.sendRedirect(request.getContextPath() + "/empresas");
                return;
            } else if (cnpjValidado==null) {
            request.setAttribute("mensagem", "O CNPJ é inválido!");
        }  else if (!ValidacaoRegex.verificarSenha(senha)) {
            request.setAttribute("mensagem", "A senha é inválida!");
        }  else if (!ValidacaoRegex.verificarEmail(email)) {
            request.setAttribute("mensagem", "O email é inválido!");
        }


            int idPlano = Integer.parseInt(idPlanoStr);
            int qntdFuncionarios = Integer.parseInt(qntdFuncStr);
            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
            String cnpjNumeros = cnpj.replaceAll("[^0-9]", "");

            Empresa empresa = new Empresa();
            empresa.setNome(nome);
            empresa.setCnpj(cnpjNumeros);
            empresa.setEmail(email);
            empresa.setSenha(senhaHash);
            empresa.setIdPlano(idPlano);
            empresa.setQntdFuncionarios(qntdFuncionarios);

            EmpresaDAO dao = new EmpresaDAO();
            int idEmpresa = dao.inserir(empresa);

            if (idEmpresa > 0) {
                request.getSession().setAttribute("mensagem", "Empresa cadastrada com sucesso!");
            } else {
                request.getSession().setAttribute("mensagem", "Não foi possível cadastrar a empresa. Tente novamente!");
            }

            response.sendRedirect(request.getContextPath() + "/empresas");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("mensagem", "Valores numéricos in válidos!");
            response.sendRedirect(request.getContextPath() + "/empresas");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao cadastrar empresa!");
            response.sendRedirect(request.getContextPath() + "/empresas");
        }
    }
}
