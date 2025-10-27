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

// Define que este servlet será acessado pela URL /AtualizarEmpresaServlet
@WebServlet("/AtualizarEmpresaServlet")
public class AtualizarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona chamadas GET para o metodo POST
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo dados do formulário
        String idParametro = request.getParameter("id");
        String cnpj = request.getParameter("cnpj");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String idPlanoParametro = request.getParameter("idPlano");
        String qntdFuncionariosParametro = request.getParameter("qntdFuncionarios");

        Empresa empresa = new Empresa();
        EmpresaDAO empresaDAO = new EmpresaDAO();

        try {
            // Verifica se os parâmetros estão vazios ou nulos
            if (idParametro == null || idParametro.isEmpty() ||
                    cnpj == null || cnpj.trim().isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    senha == null || senha.trim().isEmpty() ||
                    idPlanoParametro == null || idPlanoParametro.isEmpty() ||
                    qntdFuncionariosParametro == null || qntdFuncionariosParametro.isEmpty()) {

                // Define uma mensagem de erro que será mostrada
                request.setAttribute("mensagemAtualizar", "Não foi possível encontrar os parâmetros.");

            } else {

                int id = Integer.parseInt(idParametro);
                int idPlano = Integer.parseInt(idPlanoParametro);
                int qntdFuncionarios = Integer.parseInt(qntdFuncionariosParametro);

                // Criando o objeto do modelo
                empresa.setId(id);
                empresa.setCnpj(cnpj.trim());
                empresa.setNome(nome.trim());
                empresa.setEmail(email.trim());
                empresa.setIdPlano(idPlano);
                empresa.setQntdFuncionarios(qntdFuncionarios);

                //Hash da senha
                String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
                empresa.setSenha(senhaHash.trim());


                // Chama o metodo atualizar do dao
                if (empresaDAO.atualizar(empresa) > 0) {
                    request.setAttribute("mensagemAtualizar", "Empresa atualizada com sucesso.");
                    response.sendRedirect(request.getContextPath() + "/view/Empresa/crudEmpresa.jsp");
                } else {
                    request.setAttribute("mensagemAtualizar", "Não foi possível atualizar a Empresa.");
                }
            }
        } catch (NumberFormatException nfe) {
            // Caso o ID enviado não seja um número
            request.setAttribute("mensagemAtualizar", "ID Inválido.");
        } catch (Exception e) {
            // Caso ocorra qualquer outro erro inesperado
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar.");
        }
    }
}
