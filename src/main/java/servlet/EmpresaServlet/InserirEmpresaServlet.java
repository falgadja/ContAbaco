package servlet.EmpresaServlet;

// IMPORTS DO DAO E MODEL
import dao.EmpresaDAO;
import model.Empresa;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// BIBLIOTECA PRA CRIPTOGRAFAR SENHAS
import org.mindrot.jbcrypt.BCrypt;
// UTILIDADES DE VALIDAÇÃO
import utils.ValidacaoRegex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// DEFINE A ROTA DO SERVLET
@WebServlet("/empresa-create")
public class InserirEmpresaServlet extends HttpServlet {

    // MÉTODO DOGET → QUANDO O USUÁRIO ABRE A PÁGINA DE CADASTRO
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // REDIRECIONA PARA O JSP DE CADASTRO DE EMPRESA
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/cadastrarEmpresa.jsp");
        dispatcher.forward(request, response);
    }

    // MÉTODO DOPOST → QUANDO O USUÁRIO ENVIA O FORMULÁRIO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // VARIÁVEL PRA MENSAGENS DE ERRO OU SUCESSO
        String mensagem = null;

        // INSTANCIA DAO E LISTA PRA CASO PRECISE RECUPERAR DADOS
        EmpresaDAO dao = new EmpresaDAO();
        List<Empresa> empresas = new ArrayList<>();

        try {
            // PEGANDO OS DADOS DO FORMULÁRIO
            String nome = request.getParameter("nomeEmpresa");
            String cnpj = request.getParameter("cnpj");
            String email = request.getParameter("emailEmpresa");
            String senha = request.getParameter("senha");
            String confirmarSenha = request.getParameter("confirmarSenha");
            String idPlanoStr = request.getParameter("idPlano");
            String qntdFuncStr = request.getParameter("qntdFuncionarios");

            // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
            if (nome == null || nome.isBlank() || senha == null || confirmarSenha == null) {
                mensagem = "Campos obrigatórios estão vazios!";
            } else if (!senha.equals(confirmarSenha)) {
                mensagem = "As senhas não conferem!";
            } else if (ValidacaoRegex.verificarCnpj(cnpj)==null) {
                mensagem = "CNPJ inválido!";
            } else if (!ValidacaoRegex.verificarSenha(senha)) {
                mensagem = "Senha inválida! Use ao menos 8 caracteres e sem espaços.";
            } else if (!ValidacaoRegex.verificarEmail(email)) {
                mensagem = "Email inválido!";
            }

            // SE ALGUMA VALIDAÇÃO FALHAR, MOSTRA MENSAGEM E VOLTA PRO CRUD
            if (mensagem != null) {
                empresas = dao.listar(); // CARREGA LISTA ATUAL DE EMPRESAS
                request.setAttribute("empresas", empresas);
                request.setAttribute("mensagem", mensagem);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/crudEmpresa.jsp");
                dispatcher.forward(request, response);
                return; // PARA AQUI PRA NÃO CONTINUAR EXECUTANDO
            }

            // CONVERSÃO DE VALORES NUMÉRICOS
            int idPlano = Integer.parseInt(idPlanoStr);
            int qntdFuncionarios = Integer.parseInt(qntdFuncStr);

            // CRIPTOGRAFANDO A SENHA
            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

            // LIMPA CNPJ PRA TER SÓ NÚMEROS
            String cnpjNumeros = cnpj.replaceAll("[^0-9]", "");

            // CRIA OBJETO EMPRESA E SETA OS DADOS
            Empresa empresa = new Empresa();
            empresa.setNome(nome);
            empresa.setCnpj(cnpjNumeros);
            empresa.setEmail(email);
            empresa.setSenha(senhaHash);
            empresa.setIdPlano(idPlano);
            empresa.setQntdFuncionarios(qntdFuncionarios);

            // CHAMA DAO PRA INSERIR
            int idEmpresa = dao.inserir(empresa);

            // VERIFICA SE DEU CERTO
            if (idEmpresa > 0) {
                mensagem = "Empresa cadastrada com sucesso!";
            } else {
                mensagem = "Não foi possível cadastrar a empresa. Tente novamente!";
            }

            // ENVIA MENSAGEM PRA SESSÃO E REDIRECIONA
            request.getSession().setAttribute("mensagem", mensagem);
            response.sendRedirect(request.getContextPath() + "/empresas");

        } catch (NumberFormatException e) {
            // CASO HOUVER ERRO DE CONVERSÃO NUMÉRICA
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Valores numéricos inválidos!");
            response.sendRedirect(request.getContextPath() + "/empresas");
        } catch (Exception e) {
            // QUALQUER OUTRO ERRO
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao cadastrar empresa!");
            response.sendRedirect(request.getContextPath() + "/empresas");
        }
    }
}
