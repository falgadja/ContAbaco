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

import java.io.IOException;

@WebServlet("/empresas-update")
public class AtualizarEmpresaServlet extends HttpServlet {

    /**
     * Este é o doGet que corrige a "tela réplica".
     * Ele carrega os dados da empresa para o formulário de edição.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        if (idParametro == null || idParametro.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID da empresa não encontrado para edição.");
            response.sendRedirect(request.getContextPath() + "/empresas");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);
            EmpresaDAO empresaDAO = new EmpresaDAO();

            // Assumindo que seu EmpresaDAO tem este método
            Empresa empresa = empresaDAO.buscarPorId(id);

            if (empresa == null) {
                request.getSession().setAttribute("mensagem", "Empresa não encontrada (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/empresas");
                return;
            }

            // Sucesso: Coloca a empresa no request
            request.setAttribute("empresaParaEditar", empresa);

            // Encaminha (FORWARD) para a JSP do formulário
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Empresa/atualizarEmpresa.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar dados para edição: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/empresas");
        }
    }

    /**
     * Este é o doPost com a linha 77 CORRIGIDA.
     * Ele processa as alterações.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo dados do formulário
        String idParametro = request.getParameter("id");
        String cnpj = request.getParameter("cnpj");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); // Senha nova (ou vazia)
        String idPlanoParametro = request.getParameter("idPlano");
        String qntdFuncionariosParametro = request.getParameter("qntdFuncionarios");

        EmpresaDAO empresaDAO = new EmpresaDAO();

        try {
            // ==================================================================
            // AQUI ESTÁ A CORREÇÃO (LINHA 77)
            // O comentário "/* ... */" foi removido e substituído pelas
            // validações que você já tinha no seu código original.
            // ==================================================================
            if (idParametro == null || idParametro.isEmpty() ||
                    cnpj == null || cnpj.trim().isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    idPlanoParametro == null || idPlanoParametro.isEmpty() ||
                    qntdFuncionariosParametro == null || qntdFuncionariosParametro.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Parâmetros inválidos. Não foi possível atualizar.");

            } else {
                int id = Integer.parseInt(idParametro);
                int idPlano = Integer.parseInt(idPlanoParametro);
                int qntdFuncionarios = Integer.parseInt(qntdFuncionariosParametro);

                // Busca a empresa existente para pegar a senha antiga
                Empresa empresaExistente = empresaDAO.buscarPorId(id);
                if (empresaExistente == null) {
                    request.getSession().setAttribute("mensagem", "Erro: Empresa não encontrada para atualizar.");
                } else {
                    // Preenche o objeto com os novos dados
                    empresaExistente.setId(id);
                    empresaExistente.setCnpj(cnpj.trim());
                    empresaExistente.setNome(nome.trim());
                    empresaExistente.setEmail(email.trim());
                    empresaExistente.setIdPlano(idPlano);
                    empresaExistente.setQntdFuncionarios(qntdFuncionarios);

                    // Só gera novo hash se o usuário alterou a senha
                    if (senha != null && !senha.trim().isEmpty()) {
                        empresaExistente.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
                    }
                    // Se a senha veio vazia, a senha antiga (já no objeto) é mantida

                    if (empresaDAO.atualizar(empresaExistente) > 0) {
                        request.getSession().setAttribute("mensagem", "Empresa atualizada com sucesso.");
                    } else {
                        request.getSession().setAttribute("mensagem", "Não foi possível atualizar a Empresa.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao tentar atualizar: " + e.getMessage());
        }

        // No final, REDIRECIONA para o servlet de listagem
        response.sendRedirect(request.getContextPath() + "/empresas");
    }
}