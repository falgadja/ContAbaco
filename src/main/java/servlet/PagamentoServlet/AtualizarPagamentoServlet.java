package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import model.Pagamento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/AtualizarPagamentoServlet")
public class AtualizarPagamentoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona chamadas GET para o método POST
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo dados do formulário
        String idParametro = request.getParameter("id");
        String tipoPagto = request.getParameter("tipoPagto");
        String totalParametro = request.getParameter("total");
        String dataParametro = request.getParameter("data");
        String idEmpresaParametro = request.getParameter("idEmpresa");

        // Obs: comprovante pode ser recebido como arquivo (input type="file")
        // aqui deixaremos como null, pois o upload é tratado de outra forma
        byte[] comprovante = null;

        Pagamento pagamento = new Pagamento();
        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        try {
            // Verifica se os parâmetros estão vazios ou nulos
            if (idParametro == null || idParametro.isEmpty() ||
                    tipoPagto == null || tipoPagto.trim().isEmpty() ||
                    totalParametro == null || totalParametro.isEmpty() ||
                    dataParametro == null || dataParametro.trim().isEmpty() ||
                    idEmpresaParametro == null || idEmpresaParametro.isEmpty()) {

                // Define uma mensagem de erro que será mostrada
                request.setAttribute("mensagemAtualizar", "Não foi possível encontrar os parâmetros.");

            } else {

                // Conversões e validações
                int id = Integer.parseInt(idParametro);
                int idEmpresa = Integer.parseInt(idEmpresaParametro);
                double total = Double.parseDouble(totalParametro);

                LocalDate data;
                try {
                    data = LocalDate.parse(dataParametro);
                } catch (DateTimeParseException e) {
                    request.setAttribute("mensagemAtualizar", "Data inválida.");
                    return;
                }

                // Criando o objeto Pagamento
                pagamento.setId(id);
                pagamento.setTipoPagto(tipoPagto.trim());
                pagamento.setTotal(total);
                pagamento.setData(data);
                pagamento.setComprovante(comprovante); // opcional por enquanto
                pagamento.setIdEmpresa(idEmpresa);

                // Chama o método atualizar do DAO
                if (pagamentoDAO.atualizar(pagamento) > 0) {
                    request.setAttribute("mensagemAtualizar", "Pagamento atualizado com sucesso.");
                } else {
                    request.setAttribute("mensagemAtualizar", "Não foi possível atualizar o pagamento.");
                }
            }

        } catch (NumberFormatException nfe) {
            // Caso algum valor numérico seja inválido
            request.setAttribute("mensagemAtualizar", "Valores numéricos inválidos.");
        } catch (Exception e) {
            // Caso ocorra qualquer outro erro inesperado
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar o pagamento.");
        }
    }
}
