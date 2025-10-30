package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 1. URL "limpa"
@WebServlet("/pagamento-delete")
public class DeletarPagamentoServlet extends HttpServlet {
    
    /**
     * 2. CORREÇÃO: doGet agora redireciona (Evita delete por GET)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/pagamento");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.getSession().setAttribute("mensagem", "ID do pagamento não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (pagamentoDAO.deletar(id) > 0) {
                    request.getSession().setAttribute("mensagem", "Pagamento deletado com sucesso!");
                } else {
                    request.getSession().setAttribute("mensagem", "Não foi possível deletar o pagamento.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao tentar deletar.");
        }

        // 3. CORREÇÃO: REDIRECIONA para o servlet de listagem
        response.sendRedirect(request.getContextPath() + "/pagamento");
    }
}