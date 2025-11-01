package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import org.mindrot.jbcrypt.BCrypt;
import utils.ValidacaoRegex;

import java.io.IOException;

@WebServlet("/empresas-update")
public class AtualizarEmpresaServlet extends HttpServlet {

    /**
     * doGet: Carrega os dados da empresa para o formulário de edição.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo o ID da empresa da URL
        String idParametro = request.getParameter("id");
        if (idParametro == null || idParametro.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID da empresa não informado.");
            response.sendRedirect(request.getContextPath() + "/empresas");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);
            EmpresaDAO empresaDAO = new EmpresaDAO();

            // Busca a empresa pelo ID
            Empresa empresa = empresaDAO.buscarPorId(id);
            if (empresa == null) {
                request.getSession().setAttribute("mensagem", "Empresa não encontrada (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/empresas");
                return;
            }

            // Coloca a empresa no request para preencher o formulário
            request.setAttribute("empresaParaEditar", empresa);

            // Encaminha para a página de atualização
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/atualizarEmpresa.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/empresas");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar empresa: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/empresas");
        }
    }

    /**
     * doPost: Processa a atualização da empresa.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo os parâmetros do formulário
        String idParametro = request.getParameter("id");
        String cnpj = request.getParameter("cnpj");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); // Senha nova, pode estar vazia
        String idPlanoParametro = request.getParameter("idPlano");
        String qntdFuncionariosParametro = request.getParameter("qntdFuncionarios");

        EmpresaDAO empresaDAO = new EmpresaDAO();

        try {
            // Validação básica: checa se todos os campos obrigatórios foram preenchidos
            if (idParametro == null || idParametro.isEmpty() ||
                    cnpj == null || cnpj.trim().isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    idPlanoParametro == null || idPlanoParametro.isEmpty() ||
                    qntdFuncionariosParametro == null || qntdFuncionariosParametro.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos obrigatórios.");

            } else {
                // Convertendo parâmetros para os tipos corretos
                int id = Integer.parseInt(idParametro);
                int idPlano = Integer.parseInt(idPlanoParametro);
                int qntdFuncionarios = Integer.parseInt(qntdFuncionariosParametro);

                // Busca a empresa existente para preservar dados como a senha
                Empresa empresaExistente = empresaDAO.buscarPorId(id);
                if (empresaExistente == null) {
                    request.getSession().setAttribute("mensagem", "Empresa não encontrada para atualização.");
                } else if (!ValidacaoRegex.verificarEmail(email)) {
                    request.getSession().setAttribute("mensagem", "Email inválido!");
                } else if (senha != null && !senha.isEmpty()) {
                    if (!ValidacaoRegex.verificarSenha(senha)) {
                        request.getSession().setAttribute("mensagem", "Nova senha inválida! Use ao menos 8 caracteres, você pode usar letras, números e símbolos como @, #, $, não use espaços.");
                    }
                } else if (cnpj == null) {
                    request.getSession().setAttribute("mensagem", "CNPJ inválido!");
                } else {
                    // Atualiza os dados com os valores do formulário
                    empresaExistente.setCnpj(cnpj.trim());
                    empresaExistente.setNome(nome.trim());
                    empresaExistente.setEmail(email.trim());
                    empresaExistente.setIdPlano(idPlano);
                    empresaExistente.setQntdFuncionarios(qntdFuncionarios);

                    // Atualiza a senha apenas se o usuário digitou uma nova
                    if (senha != null && !senha.trim().isEmpty()) {
                        empresaExistente.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
                    }

                    // Atualiza no banco de dados
                    if (empresaDAO.atualizar(empresaExistente) > 0) {
                        request.getSession().setAttribute("mensagem", "Empresa atualizada com sucesso.");
                    } else {
                        request.getSession().setAttribute("mensagem", "Não foi possível atualizar a empresa.");
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valores numéricos inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar empresa: " + e.getMessage());
        }

        // Redireciona para a lista de empresas
        response.sendRedirect(request.getContextPath() + "/empresas");
    }
}
