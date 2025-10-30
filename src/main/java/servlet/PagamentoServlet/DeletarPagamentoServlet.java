package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/DeletarPagamentoServlet")
public class DeletarPagamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                request.setAttribute("mensagemDeletar", "ID do pagamento não foi encontrado.");
            } else {
                int id = Integer.parseInt(idParametro);

                if (pagamentoDAO.deletar(id) > 0) {
                    request.setAttribute("mensagemDeletar", "Pagamento deletado com sucesso!");
                } else {
                    request.setAttribute("mensagemDeletar", "Não foi possível deletar.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagemDeletar", "ID inválido.");
        } catch (Exception e) {
            request.setAttribute("mensagemDeletar", "Erro inesperado ao tentar deletar.");
        }

        // Caminho absoluto para o JSP de CRUD do pagamento
        request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp").forward(request, response);
    }
}
