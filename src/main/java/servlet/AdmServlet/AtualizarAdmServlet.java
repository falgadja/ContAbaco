package servlet.AdmServlet;

// IMPORTS DO DAO E MODEL
import dao.AdmDAO;
import model.Administrador;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// UTILITÁRIOS
import utils.ValidacaoRegex;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/adm-update")
public class AtualizarAdmServlet extends HttpServlet {


    // DOGET = CARREGA DADOS PARA ATUALIZAR //
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");

        // VALIDAÇÃO DE ID
        if (idParametro == null || idParametro.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do administrador não informado.");
            response.sendRedirect(request.getContextPath() + "/adm");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);
            AdmDAO admDAO = new AdmDAO();

            // BUSCA ADMINISTRADOR PELO ID
            Administrador adm = admDAO.buscarPorId(id);
            if (adm == null) {
                request.getSession().setAttribute("mensagem", "Administrador não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/adm");
                return;
            }

            // ENVIA OBJETO PARA O FORMULÁRIO DE EDIÇÃO
            request.setAttribute("admParaEditar", adm);

            // ENCERRA O GET ENCAMINHANDO PARA JSP
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


    // DOPOST = PROCESSA ATUALIZAÇÃO //
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha"); // NOVA SENHA (OPCIONAL)

        AdmDAO admDAO = new AdmDAO();

        try {
            // VALIDAÇÃO DE ID
            if (idParametro == null || idParametro.isEmpty()) {
                request.getSession().setAttribute("mensagem", "ID inválido. Não foi possível atualizar.");
                response.sendRedirect(request.getContextPath() + "/adm");
                return;
            }

            int id = Integer.parseInt(idParametro);
            Administrador adm = admDAO.buscarPorId(id);

            // VERIFICA SE O ADMIN EXISTE
            if (adm == null) {
                request.getSession().setAttribute("mensagem", "Administrador não encontrado para atualização.");
                response.sendRedirect(request.getContextPath() + "/adm");
                return;
            }

            // VALIDAÇÃO DE EMAIL
            if (!ValidacaoRegex.verificarEmail(email)) {
                request.getSession().setAttribute("mensagem", "Email inválido!");
                response.sendRedirect(request.getContextPath() + "/adm");
                return;
            }

            // VALIDAÇÃO DE SENHA (SE INFORMADA)
            if (senha != null && !senha.isBlank() && !ValidacaoRegex.verificarSenha(senha)) {
                request.getSession().setAttribute("mensagem",
                        "Nova senha inválida! Use ao menos 8 caracteres, letras, números e símbolos como @, #, $, sem espaços.");
                response.sendRedirect(request.getContextPath() + "/adm");
                return;
            }

            // ATUALIZA CAMPOS
            adm.setEmail(email);

            if (senha != null && !senha.trim().isEmpty()) {
                adm.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt()));
            }

            // EXECUTA ATUALIZAÇÃO NO BANCO
            if (admDAO.atualizar(adm) > 0) {
                request.getSession().setAttribute("mensagem", "Administrador atualizado com sucesso!");
            } else {
                request.getSession().setAttribute("mensagem", "Não foi possível atualizar o administrador.");
            }

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar administrador: " + e.getMessage());
        }

        // REDIRECIONA PARA LISTAGEM DE ADMINISTRADORES
        response.sendRedirect(request.getContextPath() + "/adm");
    }
}
