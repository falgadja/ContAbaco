package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * SERVLET RESPONSÁVEL POR DELETAR PAGAMENTOS
 * NÃO PERMITE GET PARA DELEÇÃO, APENAS REDIRECIONA
 * POST REALIZA A EXCLUSÃO
 */
@WebServlet("/pagamento-delete")
public class DeletarPagamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET NÃO DELETA, APENAS REDIRECIONA PARA LISTA DE PAGAMENTOS
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/pagamento"); // REDIRECIONA PRA LISTA
    }

    // POST REALIZA A EXCLUSÃO DO PAGAMENTO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id"); // PEGA ID DO FORMULÁRIO
        PagamentoDAO pagamentoDAO = new PagamentoDAO(); // INSTANCIA DAO
        String mensagem;

        try {
            // VERIFICA SE ID FOI INFORMADO
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do pagamento não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro); // CONVERTE PARA INTEIRO

                // TENTA DELETAR E DEFINE MENSAGEM
                if (pagamentoDAO.deletar(id) > 0) {
                    mensagem = "Pagamento deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o pagamento.";
                }
            }
        } catch (NumberFormatException nfe) {
            // TRATA CASO ID NÃO SEJA NÚMERO
            mensagem = "ID inválido.";
        } catch (Exception e) {
            // ERRO INESPERADO
            e.printStackTrace(); // PARA DEBUG
            mensagem = "Erro inesperado ao tentar deletar o pagamento.";
        }

        // SALVA MENSAGEM NA SESSÃO E REDIRECIONA PARA LISTA DE PAGAMENTOS
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/pagamento");
    }
}
