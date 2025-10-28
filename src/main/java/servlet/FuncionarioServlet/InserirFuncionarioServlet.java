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
import java.time.format.DateTimeParseException; // Import necessário
import java.util.regex.Pattern; // Import necessário

@WebServlet("/InserirFuncionario")
public class InserirFuncionarioServlet extends HttpServlet {

    // --- Padrões de Regex ---
    // Regex para email: Padrão comum que aceita a maioria dos emails válidos
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    // Regex para Data (YYYY-MM-DD): Garante o formato exato
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Recebe parâmetros
            String nome = request.getParameter("nome");
            String sobrenome = request.getParameter("sobrenome");
            String dataNascimentoStr = request.getParameter("dataNascimento");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarSenha");
            String idSetorStr = request.getParameter("idSetor");
            String idEmpresaStr = request.getParameter("idEmpresa");

            System.out.println("Nome: " + nome);
            System.out.println("Sobrenome: " + sobrenome);
            System.out.println("Data de nascimento: " + dataNascimentoStr);
            System.out.println("Email: " + email);
            System.out.println("Senha: " + senha);
            System.out.println("id setor"+ idSetorStr);
            System.out.println("id empresa"+ idEmpresaStr);


            // Valida campos obrigatórios
            if (nome == null || nome.isBlank() ||
                    sobrenome == null || sobrenome.isBlank() ||
                    dataNascimentoStr == null || dataNascimentoStr.isBlank() ||
                    email == null || email.isBlank() ||
                    senha == null || senha.isBlank() ||
                    confirmarSenha == null || confirmarSenha.isBlank() ||
                    idSetorStr == null || idSetorStr.isBlank() ||
                    idEmpresaStr == null || idEmpresaStr.isBlank()) {

                request.setAttribute("mensagem", "Todos os campos são obrigatórios!");
                request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
                return;
            }

            // --- INÍCIO DAS NOVAS VALIDAÇÕES ---

            // 1. Validação de formato de Email com Regex
            if (!Pattern.matches(EMAIL_REGEX, email)) {
                request.setAttribute("mensagem", "O formato do email é inválido!");
                request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
                return;
            }

            // 2. Validação de formato de Data com Regex
            if (!Pattern.matches(DATE_REGEX, dataNascimentoStr)) {
                request.setAttribute("mensagem", "O formato da data deve ser AAAA-MM-DD (ex: 1990-05-15)!");
                request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
                return;
            }

            // --- FIM DAS NOVAS VALIDAÇÕES ---


            // Transformar o id em int
            int idSetor = Integer.parseInt(idSetorStr);
            int idEmpresa = Integer.parseInt(idEmpresaStr);

            // Validação de senha
            if (!senha.equals(confirmarSenha)) {
                request.setAttribute("mensagem", "As senhas não conferem!");
                request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
                return;
            }

            // Conversão da data de nascimento (agora sabemos que o formato está correto)
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);

            // 3. Validação lógica da data (Não pode ser no futuro)
            if (dataNascimento.isAfter(LocalDate.now())) {
                request.setAttribute("mensagem", "A data de nascimento não pode ser uma data futura!");
                request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
                return;
            }

            // Cria objeto Funcionario
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nome);
            funcionario.setSobrenome(sobrenome);
            funcionario.setDataNascimento(dataNascimento);
            funcionario.setEmail(email);

            // Hash da senha
            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
            funcionario.setSenha(senhaHash);

            funcionario.setIdSetor(idSetor);
            funcionario.setIdEmpresa(idEmpresa);

            // Inserção no banco
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            int idFuncionario = funcionarioDAO.inserir(funcionario);

            if (idFuncionario > 0) {
                response.sendRedirect(request.getContextPath()+"/view/Funcionario/crudFuncionario.jsp"); // sucesso
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar o funcionário. Tente novamente!");
                request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro: ID do setor ou empresa deve ser um número válido!");
            request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
        } catch (DateTimeParseException e) { // NOVO CATCH
            // Ocorre se o formato (Regex) estiver certo, mas a data for impossível
            // Exemplo: "2025-02-30"
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro: A data de nascimento fornecida é inválida!");
            request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao cadastrar funcionário!");
            request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
        }
    }
}