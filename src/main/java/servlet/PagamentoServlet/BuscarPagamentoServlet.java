package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Pagamento;

import java.io.IOException;
import java.util.List;

@WebServlet("/BuscarPagamentoServlet")
public class BuscarPagamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        try {
            //verifica se aconteceu uma pesquisa por id
            if (id != null && !id.trim().isEmpty()) {
                // Busca o pagamento pelo id

                // Valida o ID passado, transformando de String para Int, se for inválido cai em exceção
                int idNum = Integer.parseInt(id);

                Pagamento pagamento = pagamentoDAO.buscarPorId(idNum);

                // Verifica se existe um pagamento com esse id
                if (pagamento == null) {
                    request.setAttribute("mensagemBusca", "Não foi encontrado nenhum pagamento com esse id, digite novamente.");
                } else {
                    request.setAttribute("mensagemBusca", "pagamento encontrado.");
                    request.setAttribute("pagamento", pagamento);
                }

            }

            // Lista as pagamentos
            List<Pagamento> pagamentos = pagamentoDAO.listar();

            // Verifica se existem pagamentos registrados
            if (pagamentos == null || pagamentos.isEmpty()) {
                request.setAttribute("mensagemLista", "Não foi encontrado nenhum pagamento");
            } else {
                request.setAttribute("pagamentos", pagamentos);
            }
        } catch (NumberFormatException nfe){
            // Caso o ID seja inválido, retorna uma mensagem ao JSP
            request.setAttribute("mensagemBusca", "ID inválido, digite apenas numeros inteiros");
        }

        catch (Exception e) {
            // Qualquer outro erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagemBusca", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP
        request.getRequestDispatcher("../Cadastrarpagamento.jsp").forward(request, response);
    }
}