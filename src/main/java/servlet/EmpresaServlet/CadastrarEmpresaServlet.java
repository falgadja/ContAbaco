package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/cadastrarEmpresa")
public class CadastrarEmpresaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Recebe parâmetros
            String nome = request.getParameter("nomeEmpresa");
            String cnpj = request.getParameter("cnpj");
            String email = request.getParameter("emailEmpresa");
            String senha = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarSenha");
            String idPlanoStr = request.getParameter("idPlano");
            String qntdFuncStr = request.getParameter("qntdFuncionarios");

            // Valida campos obrigatórios
            if (nome == null || nome.isBlank() ||
                    cnpj == null || cnpj.isBlank() ||
                    email == null || email.isBlank() ||
                    senha == null || senha.isBlank() ||
                    confirmarSenha == null || confirmarSenha.isBlank() ||
                    idPlanoStr == null || idPlanoStr.isBlank() ||
                    qntdFuncStr == null || qntdFuncStr.isBlank()) {

                request.setAttribute("mensagem", "Todos os campos são obrigatórios!");
                request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
                return;
            }

            // Valida senha
            if (!senha.equals(confirmarSenha)) {
                request.setAttribute("mensagem", "As senhas não conferem!");
                request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
                return;
            }

            // Converte valores numéricos
            int idPlano = Integer.parseInt(idPlanoStr);
            int qntdFuncionarios = Integer.parseInt(qntdFuncStr);

            // Faz hash da senha
            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

            // Cria objeto Empresa
            Empresa empresa = new Empresa();
            empresa.setNome(nome);
            empresa.setCnpj(cnpj);
            empresa.setEmail(email);
            empresa.setSenha(senhaHash);
            empresa.setIdPlano(1);
            empresa.setQntdFuncionarios(0);

            // Insere no banco
            EmpresaDAO dao = new EmpresaDAO();
            int idEmpresa = dao.inserir(empresa);

            if (idEmpresa > 0) {
                response.sendRedirect(request.getContextPath() + "/view/Empresa/crudEmpresa.jsp");
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar a empresa. Tente novamente!");
                request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Valores numéricos inválidos!");
            request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao cadastrar empresa!");
            request.getRequestDispatcher("/view/Empresa/cadastrarEmpresa.jsp").forward(request, response);
        }
    }
}
