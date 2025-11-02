package servlet.AdmServlet;

// IMPORTANDO O DAO QUE FAZ A PARTE DE BANCO DE DADOS
import dao.AdmDAO;
// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// IMPORTANDO A CLASSE ADMINISTRADOR DO MODEL
import model.Administrador;
// BIBLIOTECA PRA CRIPTOGRAFAR SENHAS
import org.mindrot.jbcrypt.BCrypt;
// CLASSE DE VALIDAÇÃO PERSONALIZADA
import utils.ValidacaoRegex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// ESSA ANOTAÇÃO DEFINE A ROTA DO SERVLET
@WebServlet("/adm-create")
public class InserirAdmServlet extends HttpServlet {

    // MÉTODO DOGET = QUANDO O USUÁRIO ENTRA NA ROTA SEM ENVIAR FORMULÁRIO //
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //  MANDA O USUÁRIO PRA PÁGINA JSP DE CADASTRO DE ADMIN
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/cadastrarAdm.jsp");
        dispatcher.forward(request, response);
    }

    // MÉTODO DOPOST = QUANDO O USUÁRIO ENVIA O FORMULÁRIO DE CADASTRO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO OS DADOS DIGITADOS NO FORMULÁRIO
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        // VARIÁVEL PRA GUARDAR MENSAGEM DE ERRO OU SUCESSO
        String mensagem = null;

        // PRIMEIRA VALIDAÇÃO: SENHA
        // VERIFICA SE A SENHA SEGUE O PADRÃO (8+ CARACTERES, SEM ESPAÇO, ETC)
        if (!ValidacaoRegex.verificarSenha(senha)) {
            request.setAttribute("mensagem", "Senha inválida! Use ao menos 8 caracteres, você pode usar letras, números e símbolos como @, #, $, não use espaços.");
            doGet(request, response);
            return; // PARA AQUI PRA NÃO CONTINUAR EXECUTANDO
        }

        // SEGUNDA VALIDAÇÃO: EMAIL
        if (!ValidacaoRegex.verificarEmail(email)) {
            request.setAttribute("mensagem", "Email inválido!");
            doGet(request, response);
            return;
        }

        // TERCEIRA VALIDAÇÃO: AS SENHAS TÊM QUE SER IGUAIS
        if (!senha.equals(confirmarSenha)) {
            request.setAttribute("mensagem", "As senhas não são iguais!");
            doGet(request, response);
            return;
        }

        // INSTANCIA O DAO PRA TRABALHAR COM O BANCO
        AdmDAO admDAO = new AdmDAO();
        List<Administrador> adms = new ArrayList<>();

        try {
            // VERIFICA SE JÁ EXISTE ADMIN COM ESSE EMAIL
            if (mensagem == null && admDAO.buscarPorEmail(email) != null) {
                mensagem = "Já existe um administrador com este e-mail!";
            }

            // SE DER ERRO OU EMAIL DUPLICADO, RECARREGA A LISTA E MOSTRA A MENSAGEM
            if (mensagem != null) {
                adms = admDAO.listar();
                request.setAttribute("adms", adms);
                request.setAttribute("mensagem", mensagem);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // AQUI É ONDE REALMENTE CADASTRA O ADMINISTRADOR
            // PRIMEIRO, CRIPTOGRAFA A SENHA PRA NÃO GUARDAR TEXTO PURO NO BANCO
            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

            // CRIA UM OBJETO ADMINISTRADOR E COLOCA OS DADOS
            Administrador administrador = new Administrador();
            administrador.setEmail(email);
            administrador.setSenha(senhaHash);

            // CHAMA O MÉTODO INSERIR DO DAO
            int idAdm = admDAO.inserir(administrador);

            // SE VOLTAR UM ID MAIOR QUE ZERO, É PORQUE DEU CERTO
            if (idAdm > 0) {
                mensagem = "Administrador cadastrado com sucesso!";
                adms = admDAO.listar(); // RECARREGA A LISTA DE ADMINS PRA MOSTRAR NA TELA
                request.setAttribute("adms", adms);
                request.setAttribute("mensagem", mensagem);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
                dispatcher.forward(request, response);
            } else {
                // SE NÃO VOLTAR, MOSTRA QUE DEU ERRO
                mensagem = "Erro ao cadastrar administrador!";
                adms = admDAO.listar();
                request.setAttribute("adms", adms);
                request.setAttribute("mensagem", mensagem);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            // SE DER QUALQUER ERRO DURANTE O PROCESSO
            e.printStackTrace();
            mensagem = "Erro interno: " + e.getMessage();

            try {
                adms = admDAO.listar(); // TENTA PEGAR A LISTA MESMO ASSIM
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // ENVIA MENSAGEM DE ERRO PRA TELA
            request.setAttribute("adms", adms);
            request.setAttribute("mensagem", mensagem);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
            dispatcher.forward(request, response);
        }
    }

}
