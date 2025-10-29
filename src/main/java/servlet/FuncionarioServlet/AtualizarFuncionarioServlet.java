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
import java.time.format.DateTimeFormatter;

@WebServlet("/AtualizarFuncionarioServlet")
public class AtualizarFuncionarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona GET para POST
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo os parâmetros do formulário
        String idParametro = request.getParameter("id");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String dataNascimentoParametro = request.getParameter("dataNascimento");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String idSetorParametro = request.getParameter("idSetor");
        String idEmpresaParametro = request.getParameter("idEmpresa");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Funcionario funcionario = new Funcionario();

        try {
            // Verifica campos obrigatórios
            if (idParametro == null || idParametro.isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    sobrenome == null || sobrenome.trim().isEmpty() ||
                    dataNascimentoParametro == null || dataNascimentoParametro.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    idSetorParametro == null || idSetorParametro.isEmpty() ||
                    idEmpresaParametro == null || idEmpresaParametro.isEmpty()) {

                request.setAttribute("mensagemAtualizar", "Campos obrigatórios não preenchidos.");
                request.getRequestDispatcher("/view/Funcionario/crudFuncionario.jsp").forward(request, response);
                return;
            }

            // Conversão de valores
            int id = Integer.parseInt(idParametro);
            int idSetor = Integer.parseInt(idSetorParametro);
            int idEmpresa = Integer.parseInt(idEmpresaParametro);
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoParametro, DateTimeFormatter.ISO_LOCAL_DATE);

            // Busca o funcionário existente no BD para preservar senha
            Funcionario funcionarioExistente = funcionarioDAO.buscarPorId(id);

            // Monta o objeto atualizado
            funcionario.setId(id);
            funcionario.setNome(nome.trim());
            funcionario.setSobrenome(sobrenome.trim());
            funcionario.setDataNascimento(dataNascimento);
            funcionario.setEmail(email.trim());
            funcionario.setIdSetor(idSetor);
            funcionario.setIdEmpresa(idEmpresa);

            // Atualiza senha apenas se o campo foi preenchido
            if (senha != null && !senha.trim().isEmpty()) {
                funcionario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
            } else {
                funcionario.setSenha(funcionarioExistente.getSenha());
            }

            // Atualiza no banco
            if (funcionarioDAO.atualizar(funcionario) > 0) {
                // Redireciona para evitar duplo envio
                response.sendRedirect(request.getContextPath() + "/view/Funcionario/crudFuncionario.jsp?msg=sucesso");
            } else {
                request.setAttribute("mensagemAtualizar", "Erro ao atualizar o funcionário.");
                request.getRequestDispatcher("/view/Funcionario/crudFuncionario.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensagemAtualizar", "Campos numéricos inválidos.");
            e.printStackTrace();
            request.getRequestDispatcher("/view/Funcionario/crudFuncionario.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar o funcionário.");
            e.printStackTrace();
            request.getRequestDispatcher("/view/Funcionario/crudFuncionario.jsp").forward(request, response);
        }
    }
}
