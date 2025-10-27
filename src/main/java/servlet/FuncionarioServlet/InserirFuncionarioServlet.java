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

@WebServlet("/InserirFuncionario")
public class InserirFuncionarioServlet extends HttpServlet {

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

            // Transformar o id em int
            int idSetor = Integer.parseInt(idSetorStr);
            int idEmpresa = Integer.parseInt(idEmpresaStr);

            // Validação de senha
            if (!senha.equals(confirmarSenha)) {
                request.setAttribute("mensagem", "As senhas não conferem!");
                request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
                return;
            }

            // Conversão da data de nascimento
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);

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
            request.setAttribute("mensagem", "Erro: valores numéricos inválidos!");
            request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao cadastrar funcionário!");
            request.getRequestDispatcher("/view/Funcionario/cadastrarFuncionario.jsp").forward(request, response);
        }
    }
}

