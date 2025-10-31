package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/pagamento-delete")
public class DeletarPagamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET não deleta, apenas redireciona para a lista de pagamentos
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/pagamento");
    }

    // POST realiza a exclusão do pagamento
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        PagamentoDAO pagamentoDAO = new PagamentoDAO(); // DAO instanciado aqui
        String mensagem;

        try {
            if (idParametro == null || idParametro.isEmpty()) {
                mensagem = "ID do pagamento não foi encontrado.";
            } else {
                int id = Integer.parseInt(idParametro);

                if (pagamentoDAO.deletar(id) > 0) {
                    mensagem = "Pagamento deletado com sucesso!";
                } else {
                    mensagem = "Não foi possível deletar o pagamento.";
                }
            }
        } catch (NumberFormatException nfe) {
            mensagem = "ID inválido.";
        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro inesperado ao tentar deletar o pagamento.";
        }

        // Salva a mensagem na sessão e redireciona para a lista
        request.getSession().setAttribute("mensagem", mensagem);
        response.sendRedirect(request.getContextPath() + "/pagamento");
    }
}
