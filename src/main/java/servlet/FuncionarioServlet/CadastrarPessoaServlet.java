package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Funcionario;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/cadastrarPessoa")
public class CadastrarPessoaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Captura dos campos do formulário
        String nome = req.getParameter("nome");
        String sobrenome = req.getParameter("sobrenome");
        String dataNascimentoStr = req.getParameter("data-nascimento"); //Vem do html assim dd-mm-yyyy
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String confirmarSenha = req.getParameter("confirme-senha");

        // Variável para controlar se houve erro
        boolean temErro = false;

        // Validação de senha
        if (!senha.equals(confirmarSenha)) {
            req.setAttribute("erro", "As senhas não são iguais!");
            temErro = true;
        }

        // Validação da data de nascimento
        LocalDate dataNascimento = null;
        try {
            dataNascimento = LocalDate.parse(dataNascimentoStr); // formato YYYY-MM-DD
        } catch (DateTimeParseException e) {
            req.setAttribute("erro", "Data de nascimento inválida!");
            temErro = true;
        }

        // Se não houver erro, prossegue com cadastro
        if (!temErro) {
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nome);
            funcionario.setSobrenome(sobrenome);
            funcionario.setDataNascimento(dataNascimento);
            funcionario.setEmail(email);
            funcionario.setSenha(senha);
            funcionario.setIdEmpresa(0);
            funcionario.setIdSetor(1);

            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            try {
                System.out.println("Tentando inserir funcionário...");
                int idGerado = funcionarioDAO.inserir(funcionario);
                System.out.println("ID gerado: " + idGerado);

                if (idGerado > 0) {
                    System.out.println("Cadastro realizado com sucesso!");
                    resp.sendRedirect("view/crud.jsp");
                } else {
                    System.out.println("Falha no cadastro: idGerado = 0");
                    req.setAttribute("erro", "Não foi possível cadastrar o funcionário.");
                    req.getRequestDispatcher("view/cadastrarPessoa.jsp").forward(req, resp);
                }
            } catch (Exception e) {
                System.out.println("Exceção capturada no servlet:");
                e.printStackTrace(); // Isso mostra no console do Tomcat
                req.setAttribute("erro", "Erro ao cadastrar funcionário: " + e.getMessage());
                req.getRequestDispatcher("view/cadastrarPessoa.jsp").forward(req, resp);
            }
        }
        }}