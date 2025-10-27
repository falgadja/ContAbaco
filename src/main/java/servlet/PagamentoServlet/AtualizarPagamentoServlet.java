package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Pagamento;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/AtualizarPagamentoServlet")
public class AtualizarPagamentoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona GET para POST (mesmo padrão do AtualizarEmpresaServlet)
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo dados do formulário
        String idParam = request.getParameter("id");
        String tipoPagto = request.getParameter("tipoPagto");
        String totalParam = request.getParameter("total");
        String dataParam = request.getParameter("data");
        String idEmpresaParam = request.getParameter("idEmpresa");

        Pagamento pagamento = new Pagamento();
        PagamentoDAO dao = new PagamentoDAO();

        try {
            // Verifica se os parâmetros estão vazios ou nulos
            if (idParam == null || idParam.isEmpty() ||
                    tipoPagto == null || tipoPagto.trim().isEmpty() ||
                    totalParam == null || totalParam.isEmpty() ||
                    dataParam == null || dataParam.trim().isEmpty() ||
                    idEmpresaParam == null || idEmpresaParam.isEmpty()) {

                request.setAttribute("mensagemAtualizar", "Preencha todos os campos.");
                request.getRequestDispatcher("/view/Pagamento/atualizarPagamento.jsp").forward(request, response);
                return;
            }

            // Conversão dos parâmetros
            int id = Integer.parseInt(idParam);
            double total = Double.parseDouble(totalParam);
            LocalDate data = LocalDate.parse(dataParam);
            int idEmpresa = Integer.parseInt(idEmpresaParam);

            // Preenche o objeto Pagamento
            pagamento.setId(id);
            pagamento.setTipoPagto(tipoPagto.trim());
            pagamento.setTotal(total);
            pagamento.setData(data);
            pagamento.setIdEmpresa(idEmpresa);

            // Atualiza no banco
            int linhasAfetadas = dao.atualizar(pagamento);

            if (linhasAfetadas > 0) {
                request.setAttribute("mensagemAtualizar", "Pagamento atualizado com sucesso.");
                response.sendRedirect(request.getContextPath() + "/view/Pagamento/crudPagamento.jsp");
            } else {
                request.setAttribute("pagamento", pagamento);
                request.setAttribute("mensagemAtualizar", "Não foi possível atualizar o pagamento.");
                request.getRequestDispatcher("/view/Pagamento/atualizarPagamento.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("pagamento", pagamento);
            request.setAttribute("mensagemAtualizar", "Valores numéricos inválidos.");
            request.getRequestDispatcher("/view/Pagamento/atualizarPagamento.jsp").forward(request, response);
        } catch (DateTimeParseException e) {
            request.setAttribute("pagamento", pagamento);
            request.setAttribute("mensagemAtualizar", "Data inválida.");
            request.getRequestDispatcher("/view/Pagamento/atualizarPagamento.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("pagamento", pagamento);
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao atualizar o pagamento.");
            request.getRequestDispatcher("/view/Pagamento/atualizarPagamento.jsp").forward(request, response);
        }
    }
}
