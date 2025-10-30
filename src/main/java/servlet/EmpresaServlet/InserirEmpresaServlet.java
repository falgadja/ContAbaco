package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.RequestDispatcher; // Importe
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

// 1. URL "limpa" para o formulário de cadastro
@WebServlet("/empresas-create")
public class InserirEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 2. Apenas mostra o formulário (já estava correto)
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

            // 3. Sua lógica de validação (já estava boa)
            if (nome == null || nome.isBlank() || /* ...outras validações... */ !senha.equals(confirmarSenha)) {
                request.setAttribute("mensagem", "As senhas não conferem ou campos obrigatórios estão vazios!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/cadastrarEmpresa.jsp");
                dispatcher.forward(request, response);
                return;
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
                // 4. CORREÇÃO: Em caso de sucesso, REDIRECIONA para o servlet de listagem
                request.getSession().setAttribute("mensagem", "Empresa cadastrada com sucesso!");
                response.sendRedirect(request.getContextPath() + "/empresas");
            } else {
                // 5. Em caso de falha, encaminha de volta para o form com erro
                request.setAttribute("mensagem", "Não foi possível cadastrar a empresa. Tente novamente!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/cadastrarEmpresa.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Valores numéricos inválidos!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/cadastrarEmpresa.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao cadastrar empresa!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/cadastrarEmpresa.jsp");
            dispatcher.forward(request, response);
        }
    }
}