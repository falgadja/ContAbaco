package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 1. URL "limpa" para a ação de deletar
@WebServlet("/adm-delete")
public class DeletarAdmServlet extends HttpServlet {

    /**
     * Ações de exclusão NUNCA devem ser feitas com GET.
     * Este método redireciona para a lista principal para evitar
     * que alguém delete dados acidentalmente por um link.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona para o servlet de listagem
        response.sendRedirect(request.getContextPath() + "/adm");
    }

    /**
     * Processa a exclusão do administrador.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        AdmDAO admDAO = new AdmDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.getSession().setAttribute("mensagemDeletar", "ID do administrador não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (admDAO.deletar(id) > 0) {
                    request.getSession().setAttribute("mensagemDeletar", "Administrador deletado com sucesso!");
                } else {
                    request.getSession().setAttribute("mensagemDeletar", "Não foi possível deletar.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagemDeletar", "ID Inválido.");
        } catch (Exception e) {
            request.getSession().setAttribute("mensagemDeletar", "Erro inesperado ao tentar deletar.");
        }

        // 2. CORREÇÃO PRINCIPAL (Post-Redirect-Get):
        // Redireciona de volta para o servlet de LISTAGEM (/adm).
        // O servlet /adm vai carregar a lista (sem o item deletado) e mostrar a mensagem.
        response.sendRedirect(request.getContextPath() + "/adm");
    }
}