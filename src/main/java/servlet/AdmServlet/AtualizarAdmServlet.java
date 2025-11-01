package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Administrador;
import utils.ValidacaoRegex;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/adm-update")
public class AtualizarAdmServlet extends HttpServlet {

    /**
     * doGet: Carrega os dados do administrador para o formulário de edição.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo o ID do administrador
        String idParametro = request.getParameter("id");
        if (idParametro == null || idParametro.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do administrador não informado.");
            response.sendRedirect(request.getContextPath() + "/adm");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);
            AdmDAO admDAO = new AdmDAO();

            // Busca o administrador pelo ID
            Administrador adm = admDAO.buscarPorId(id);
            if (adm == null) {
                request.getSession().setAttribute("mensagem", "Administrador não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/adm");
                return;
            }

            // Coloca o objeto no request para preencher o formulário
            request.setAttribute("admParaEditar", adm);

            // Encaminha para a JSP de atualização
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/atualizarAdm.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/adm");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar administrador: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/adm");
        }
    }

    /**
     * doPost: Processa a atualização do administrador.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo os parâmetros do formulário
        String idParametro = request.getParameter("id");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); // Senha nova (pode estar vazia)

        AdmDAO admDAO = new AdmDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.getSession().setAttribute("mensagem", "ID inválido. Não foi possível atualizar.");
            } else {
                int id = Integer.parseInt(idParametro);

                // Busca o administrador existente no banco
                Administrador adm = admDAO.buscarPorId(id);
                if (adm == null) {
                    request.getSession().setAttribute("mensagem", "Administrador não encontrado para atualização.");
                } else if (!ValidacaoRegex.verificarEmail(email)) {
                    request.getSession().setAttribute("mensagem", "Email inválido!");

                } else if(senha!=null && !senha.isEmpty()) {
                    if (!ValidacaoRegex.verificarSenha(senha)) {
                        request.getSession().setAttribute("mensagem", "Nova senha inválida! Use ao menos 8 caracteres, você pode usar letras, números e símbolos como @, #, $, não use espaços.");
                    }
                } else {
                    // Atualiza os campos com os dados do formulário
                    adm.setEmail(email);

                    // Atualiza a senha apenas se foi digitada uma nova
                    if (senha != null && !senha.trim().isEmpty()) {
                        adm.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
                    }

                    // Executa a atualização no banco
                    if (admDAO.atualizar(adm) > 0) {
                        request.getSession().setAttribute("mensagem", "Administrador atualizado com sucesso!");
                    } else {
                        request.getSession().setAttribute("mensagem", "Não foi possível atualizar o administrador.");
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar administrador: " + e.getMessage());
            e.printStackTrace();
        }

        // Redireciona para a listagem de administradores
        response.sendRedirect(request.getContextPath() + "/adm");
    }
}
