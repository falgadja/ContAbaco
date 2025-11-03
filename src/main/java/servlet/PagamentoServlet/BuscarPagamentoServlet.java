package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import filtros.PagamentoFiltro;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Pagamento;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * SERVLET RESPONSÁVEL POR BUSCAR E LISTAR PAGAMENTOS
 * PERMITE PESQUISA POR ID, FILTRO POR DATA, TIPO E ORDENAR RESULTADOS
 * TRATA MENSAGENS TEMPORÁRIAS (PADRÃO PRG)
 */
@WebServlet("/pagamento")
public class BuscarPagamentoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String mensagemSessao = (String) session.getAttribute("mensagem");
        if (mensagemSessao != null) {
            request.setAttribute("mensagem", mensagemSessao);
            session.removeAttribute("mensagem");
        }

        // Recebe parâmetros
        String id = request.getParameter("id");
        String inicio = request.getParameter("inicio");
        String fim = request.getParameter("fim");
        String[] tiposV = request.getParameterValues("tipos");
        List<String> tipos = new ArrayList<>();
        if (tiposV != null && tiposV.length > 0) {
            tipos = List.of(tiposV);
        }
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        PagamentoDAO pagamentoDAO = new PagamentoDAO();
        PagamentoFiltro pagamentoFiltro = new PagamentoFiltro();
        List<Pagamento> pagamentos = new ArrayList<>();

        try {
            // Pesquisa por ID
            if (id != null && !id.trim().isEmpty()) {
                int idNum = Integer.parseInt(id);
                Pagamento pagamento = pagamentoDAO.buscarPorId(idNum);
                if (pagamento != null) {
                    pagamentos.add(pagamento);
                    request.setAttribute("mensagem", "Pagamento encontrado com sucesso.");
                } else {
                    request.setAttribute("mensagem", "Nenhum pagamento encontrado com esse ID.");
                }
            } else {
                // Lista todos
                pagamentos = pagamentoDAO.listar();

                if (pagamentos == null) pagamentos = new ArrayList<>();

                // Filtra por tipo
                if (!tipos.isEmpty() && !tipos.contains("todos")) {
                    pagamentos = pagamentoFiltro.filtrarPorTipo(pagamentos, tipos);
                }

                // Filtra por período de datas
                if (inicio != null && !inicio.isBlank() && fim != null && !fim.isBlank()) {
                    try {
                        LocalDate inicioDt = LocalDate.parse(inicio); // input type="date" envia yyyy-MM-dd
                        LocalDate fimDt = LocalDate.parse(fim);

                        if (fimDt.isBefore(inicioDt)) {
                            request.setAttribute("mensagem", "Data fim não pode ser menor que a data início.");
                            pagamentos.clear();
                        } else {
                            pagamentos = pagamentoFiltro.filtrarPorData(pagamentos, inicioDt, fimDt);
                        }
                    } catch (DateTimeParseException e) {
                        request.setAttribute("mensagem", "Formato de data inválido. Use o calendário.");
                        pagamentos.clear();
                    }
                }

                if (pagamentos.isEmpty()) {
                    request.setAttribute("mensagem", "Nenhum pagamento encontrado com os filtros aplicados.");
                } else {
                    request.setAttribute("mensagem", "Foram encontrados " + pagamentos.size() + " pagamentos.");
                }

                // Ordenação
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !pagamentos.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) pagamentos = pagamentoFiltro.OrdenarIdCrece(pagamentos);
                    else if (tipoOrdenacao.equals("idDecrescente")) pagamentos = pagamentoFiltro.OrdenarIdDecre(pagamentos);
                    else if (tipoOrdenacao.equals("totalCrescente")) pagamentos = pagamentoFiltro.OrdenarTotalCrece(pagamentos);
                    else if (tipoOrdenacao.equals("totalDecrescente")) pagamentos = pagamentoFiltro.OrdenarTotalDecre(pagamentos);
                    else if (tipoOrdenacao.equals("dataCrescente")) pagamentos = pagamentoFiltro.OrdenarDataCrece(pagamentos);
                    else if (tipoOrdenacao.equals("dataDecrescente")) pagamentos = pagamentoFiltro.OrdenarDataDecre(pagamentos);
                }
            }

            request.setAttribute("pagamentos", pagamentos);

        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagem", "ID inválido, digite apenas números.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
