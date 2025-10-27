package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import model.Funcionario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/AtualizarFuncionarioServlet")
public class AtualizarFuncionarioServlet extends HttpServlet {
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
            // Verifica se os parâmetros estão vazios ou nulos
            if (idParametro == null || idParametro.isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    sobrenome == null || sobrenome.trim().isEmpty() ||
                    dataNascimentoParametro == null || dataNascimentoParametro.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    senha == null || senha.trim().isEmpty() ||
                    idSetorParametro == null || idSetorParametro.isEmpty() ||
                    idEmpresaParametro == null || idEmpresaParametro.isEmpty()) {

                // Define uma mensagem de erro que será mostrada
                request.setAttribute("mensagemAtualizar", "Não foi possível encontrar os parâmetros.");

            } else {

                // Convertendo valores para os tipos apropriados
                int id = Integer.parseInt(idParametro);
                int idSetor = Integer.parseInt(idSetorParametro);
                int idEmpresa = Integer.parseInt(idEmpresaParametro);
                LocalDate dataNascimento;

                // Validando a data de nascimento
                try {
                    dataNascimento = LocalDate.parse(dataNascimentoParametro);
                } catch (DateTimeParseException e) {
                    request.setAttribute("mensagemAtualizar", "Data de nascimento inválida.");
                    return;
                }

                // Criando o objeto Funcionario
                funcionario.setId(id);
                funcionario.setNome(nome.trim());
                funcionario.setSobrenome(sobrenome.trim());
                funcionario.setDataNascimento(dataNascimento);
                funcionario.setEmail(email.trim());
                funcionario.setSenha(senha.trim());
                funcionario.setIdSetor(idSetor);
                funcionario.setIdEmpresa(idEmpresa);

                // Chama o metodo atualizar do dao
                if (funcionarioDAO.atualizar(funcionario) > 0) {
                    request.setAttribute("mensagemAtualizar", "Funcionário atualizado com sucesso.");
                    response.sendRedirect(request.getContextPath() + "/view/Funcionario/crudFuncionario.jsp");
                } else {
                    request.setAttribute("mensagemAtualizar", "Não foi possível atualizar o funcionário.");
                }
            }
        } catch (NumberFormatException nfe) {
            // Caso algum valor numérico seja inválido
            request.setAttribute("mensagemAtualizar", "Valores numéricos inválidos.");
        } catch (Exception e) {
            // Caso ocorra qualquer outro erro inesperado
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar o funcionário.");
        }
    }
}
