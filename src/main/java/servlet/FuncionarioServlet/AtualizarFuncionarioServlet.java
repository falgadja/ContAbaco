package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Funcionario;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/AtualizarFuncionarioServlet")
public class AtualizarFuncionarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // redireciona GET para POST
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String dataNascimentoParametro = request.getParameter("dataNascimento");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String idSetorParametro = request.getParameter("idSetor");
        String idEmpresaParametro = request.getParameter("idEmpresa");

        Funcionario funcionario = new Funcionario();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        try {
            if (idParametro == null || nome == null || sobrenome == null ||
                    dataNascimentoParametro == null || email == null ||
                    idSetorParametro == null || idEmpresaParametro == null ||
                    nome.trim().isEmpty() || sobrenome.trim().isEmpty() ||
                    email.trim().isEmpty() || dataNascimentoParametro.trim().isEmpty()) {

                request.setAttribute("mensagemAtualizar", "Campos obrigatórios não preenchidos.");
            } else {
                int id = Integer.parseInt(idParametro);
                int idSetor = Integer.parseInt(idSetorParametro);
                int idEmpresa = Integer.parseInt(idEmpresaParametro);
                LocalDate dataNascimento = LocalDate.parse(dataNascimentoParametro);

                funcionario.setId(id);
                funcionario.setNome(nome.trim());
                funcionario.setSobrenome(sobrenome.trim());
                funcionario.setDataNascimento(dataNascimento);
                funcionario.setEmail(email.trim());
                funcionario.setIdSetor(idSetor);
                funcionario.setIdEmpresa(idEmpresa);

                if (senha != null && !senha.isEmpty()) {
                    funcionario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
                }

                if (funcionarioDAO.atualizar(funcionario) > 0) {
                    request.setAttribute("mensagemAtualizar", "Funcionário atualizado com sucesso.");
                } else {
                    request.setAttribute("mensagemAtualizar", "Erro ao atualizar o funcionário.");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("mensagemAtualizar", "Valores numéricos inválidos.");
        } catch (Exception e) {
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar o funcionário.");
        }

        // Encaminha mantendo atributos
        request.getRequestDispatcher("/view/Funcionario/crudFuncionario.jsp").forward(request, response);
    }
}
