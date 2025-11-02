package servlet.FuncionarioServlet;

// IMPORTS DO DAO E MODEL
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import dao.SetorDAO;
import model.Empresa;
import model.Funcionario;
import model.Setor;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// UTILITÁRIOS
import org.mindrot.jbcrypt.BCrypt;
import utils.ValidacaoRegex;

import java.io.IOException;
import java.util.List;

@WebServlet("/funcionarios-update")
public class AtualizarFuncionarioServlet extends HttpServlet {

    // DOGET = CARREGA DADOS DO FUNCIONÁRIO PARA O FORMULÁRIO //
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        // VALIDAÇÃO DE ID
        if (idParam == null || idParam.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do funcionário não informado.");
            response.sendRedirect(request.getContextPath() + "/funcionarios");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

            // BUSCA FUNCIONÁRIO PELO ID
            Funcionario funcionario = funcionarioDAO.buscarPorId(id);
            if (funcionario == null) {
                request.getSession().setAttribute("mensagem", "Funcionário não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // BUSCA LISTAS DE SETORES E EMPRESAS PARA DROPDOWN
            List<Setor> setores = new SetorDAO().listarTodos();
            List<Empresa> empresas = new EmpresaDAO().listar();

            // ENVIA OBJETOS PARA O JSP
            request.setAttribute("funcionarioParaEditar", funcionario);
            request.setAttribute("setores", setores);
            request.setAttribute("empresas", empresas);

            // ENCERRA O GET ENCAMINHANDO PARA JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Funcionario/atualizarFuncionario.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/funcionarios");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar funcionário: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/funcionarios");
        }
    }

    // DOPOST = PROCESSA ATUALIZAÇÃO DO FUNCIONÁRIO //
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // RECEBE PARÂMETROS DO FORMULÁRIO
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); // NOVA SENHA (OPCIONAL)
        String idSetorParam = request.getParameter("idSetor");
        String idEmpresaParam = request.getParameter("idEmpresa");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        try {
            // VALIDAÇÃO BÁSICA DOS CAMPOS OBRIGATÓRIOS
            if (idParam == null || idParam.isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    idSetorParam == null || idSetorParam.isEmpty() ||
                    idEmpresaParam == null || idEmpresaParam.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos.");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // VALIDAÇÃO DE EMAIL
            if (!ValidacaoRegex.verificarEmail(email)) {
                request.getSession().setAttribute("mensagem", "Email inválido!");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // VALIDAÇÃO DE SENHA (SE INFORMADA)
            if (senha != null && !senha.trim().isEmpty() && !ValidacaoRegex.verificarSenha(senha)) {
                request.getSession().setAttribute("mensagem",
                        "Nova senha inválida! Use ao menos 8 caracteres, letras, números e símbolos como @, #, $, sem espaços.");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // CONVERSÃO DE TIPOS
            int id = Integer.parseInt(idParam);
            int idSetor = Integer.parseInt(idSetorParam);
            int idEmpresa = Integer.parseInt(idEmpresaParam);

            // BUSCA FUNCIONÁRIO EXISTENTE
            Funcionario funcionario = funcionarioDAO.buscarPorId(id);
            if (funcionario == null) {
                request.getSession().setAttribute("mensagem", "Funcionário não encontrado para atualizar.");
                response.sendRedirect(request.getContextPath() + "/funcionarios");
                return;
            }

            // ATUALIZA DADOS DO FUNCIONÁRIO
            funcionario.setNome(nome.trim());
            funcionario.setEmail(email.trim());
            funcionario.setIdSetor(idSetor);
            funcionario.setIdEmpresa(idEmpresa);

            // ATUALIZA SENHA SE INFORMADA
            if (senha != null && !senha.trim().isEmpty()) {
                funcionario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
            }

            // EXECUTA ATUALIZAÇÃO NO BANCO
            if (funcionarioDAO.atualizar(funcionario) > 0) {
                request.getSession().setAttribute("mensagem", "Funcionário atualizado com sucesso.");
            } else {
                request.getSession().setAttribute("mensagem", "Não foi possível atualizar o funcionário.");
            }

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valores numéricos inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar funcionário: " + e.getMessage());
        }

        // REDIRECIONA PARA LISTAGEM DE FUNCIONÁRIOS
        response.sendRedirect(request.getContextPath() + "/funcionarios");
    }
}
