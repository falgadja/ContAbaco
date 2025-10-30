package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.RequestDispatcher; // IMPORTANTE
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Administrador;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/adm-update")
public class AtualizarAdmServlet extends HttpServlet {

    /**
     * CORRIGIDO: Este método carrega os dados do admin PARA DENTRO do formulário de edição.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        if (idParametro == null || idParametro.isEmpty()) {
            // Se não tiver ID, aí sim redireciona com erro
            request.getSession().setAttribute("mensagem", "ID do administrador não foi encontrado para edição.");
            response.sendRedirect(request.getContextPath() + "/adm");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);
            AdmDAO admDAO = new AdmDAO();

            // 1. Busca o administrador no banco
            Administrador adm = admDAO.buscarPorId(id);

            if (adm == null) {
                // Se o ID não existir, redireciona com erro
                request.getSession().setAttribute("mensagem", "Administrador não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/adm");
                return;
            }

            // 2. Coloca o objeto "adm" no request com o nome "admParaEditar"
            // (O seu atualizarAdm.jsp já está esperando por esse nome)
            request.setAttribute("admParaEditar", adm);

            // 3. Encaminha (FORWARD) para a JSP do formulário
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/atualizarAdm.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar dados para edição: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/adm");
        }
    }

    /**
     * CORRIGIDO: Este método processa o formulário de atualização.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); // Senha nova (ou vazia)

        AdmDAO admDAO = new AdmDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.getSession().setAttribute("mensagem", "ID inválido. Não foi possível atualizar.");
            } else {
                int id = Integer.parseInt(idParametro);

                // 1. Busca o administrador que JÁ EXISTE no banco
                Administrador adm = admDAO.buscarPorId(id);

                if (adm == null) {
                    request.getSession().setAttribute("mensagem", "Erro: Administrador não encontrado para atualizar.");
                } else {
                    // 2. Atualiza os campos
                    adm.setEmail(email);

                    // 3. SÓ atualiza a senha se o usuário digitou uma nova
                    if (senha != null && !senha.trim().isEmpty()) {
                        String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
                        adm.setSenha(senhaHash);
                    }
                    // Se a senha veio vazia, ele NÃO mexe na senha antiga.

                    // 4. Manda o DAO atualizar (usando o método inteligente que criamos)
                    if (admDAO.atualizar(adm) > 0) {
                        request.getSession().setAttribute("mensagem", "Administrador atualizado com sucesso!");
                    } else {
                        request.getSession().setAttribute("mensagem", "Não foi possível atualizar o administrador no banco.");
                    }
                }
            }
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro inesperado ao tentar atualizar: " + e.getMessage());
            e.printStackTrace();
        }

        // 5. Redireciona de volta para o servlet de LISTAGEM
        response.sendRedirect(request.getContextPath() + "/adm");
    }
}