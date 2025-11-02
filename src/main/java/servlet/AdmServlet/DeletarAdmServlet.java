package servlet.AdmServlet;

import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * SERVLET RESPONSÁVEL POR DELETAR ADMINISTRADORES
 * NÃO PERMITE GET PARA DELEÇÃO, APENAS REDIRECIONA
 * POST REALIZA A EXCLUSÃO
 */
@WebServlet("/adm-delete")
public class DeletarAdmServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET NÃO DELETA, APENAS REDIRECIONA PARA LISTAGEM
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/adm"); // REDIRECIONA PRA LISTA DE ADM
    }

    // POST REALIZA A EXCLUSÃO DO ADMINISTRADOR
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id"); // PEGA ID DO FORMULÁRIO
        AdmDAO admDAO = new AdmDAO(); // INSTANCIA DAO PARA ACESSAR BANCO

        String mensagem;

        try {
            // VERIFICA SE ID FOI INFORMADO
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do administrador não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro); // CONVERTE ID PARA INTEIRO

                // TENTA DELETAR E DEFINE MENSAGEM
                if (admDAO.deletar(id) > 0) {
                    mensagem = "Administrador deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o administrador.";
                }
            }
        } catch (NumberFormatException nfe) {
            // TRATA CASO ID NÃO SEJA NÚMERO
            mensagem = "ID inválido.";
        } catch (Exception e) {
            // ERRO INESPERADO
            mensagem = "Erro inesperado ao tentar deletar o administrador.";
            e.printStackTrace(); // PARA DEBUG
        }

        // SALVA MENSAGEM NA SESSÃO E REDIRECIONA PARA A LISTA DE ADMINISTRADORES
        request.getSession().setAttribute("mensagemDeletar", mensagem);
        response.sendRedirect(request.getContextPath() + "/adm");
    }
}
