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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/pagamento")
public class BuscarPagamentoServlet extends HttpServlet {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String mensagemSessao = (String) session.getAttribute("mensagem");
        if (mensagemSessao != null) {
            request.setAttribute("mensagem", mensagemSessao);
            session.removeAttribute("mensagem");
        }

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
            if (id != null && !id.trim().isEmpty()) {
                int idNum = Integer.parseInt(id);
                Pagamento pagamento = pagamentoDAO.buscarPorId(idNum);

                if (pagamento == null) {
                    request.setAttribute("mensagem", "Nenhum pagamento encontrado com esse ID.");
                } else {
                    pagamentos.add(pagamento);
                    request.setAttribute("mensagem", "Pagamento encontrado com sucesso.");
                }

            } else {
                // Lista todos os pagamentos
                pagamentos = pagamentoDAO.listar();

                if (pagamentos == null || pagamentos.isEmpty()) {
                    request.setAttribute("mensagem", "Nenhum pagamento cadastrado.");
                } else {

                    // Filtra por tipo se não for "todos"
                    if (!tipos.isEmpty() && !tipos.contains("todos")) {
                        pagamentos = pagamentoFiltro.filtrarPorTipo(pagamentos, tipos);
                    }

                    // Filtra por período de datas se fornecido
                    if (inicio != null && !inicio.trim().isEmpty() && fim != null && !fim.trim().isEmpty()) {
                        LocalDate inicioDt = LocalDate.parse(inicio, FORMATTER);
                        LocalDate fimDt = LocalDate.parse(fim, FORMATTER);
                        pagamentos = pagamentoFiltro.filtrarPorData(pagamentos, inicioDt, fimDt);
                    }

                    // Ordena se solicitado
                    if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !pagamentos.isEmpty()) {
                        switch (tipoOrdenacao) {
                            case "idCrescente" -> pagamentos = pagamentoFiltro.OrdenarIdCrece(pagamentos);
                            case "idDecrescente" -> pagamentos = pagamentoFiltro.OrdenarIdDecre(pagamentos);
                            case "totalCrescente" -> pagamentos = pagamentoFiltro.OrdenarTotalCrece(pagamentos);
                            case "totalDecrescente" -> pagamentos = pagamentoFiltro.OrdenarTotalDecre(pagamentos);
                            case "dataCrescente" -> pagamentos = pagamentoFiltro.OrdenarDataCrece(pagamentos);
                            case "dataDecrescente" -> pagamentos = pagamentoFiltro.OrdenarDataDecre(pagamentos);
                        }
                    }

                    // Define mensagem final
                    if (pagamentos.isEmpty()) {
                        request.setAttribute("mensagem", "Nenhum pagamento encontrado com os filtros aplicados.");
                    } else {
                        request.setAttribute("mensagem", "Pagamentos encontrados: " + pagamentos.size());
                    }
                }
            }

            request.setAttribute("pagamentos", pagamentos);

        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");
        } catch (DateTimeParseException dte) {
            request.setAttribute("mensagem", "Data inválida. Use o formato dd/MM/yyyy.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
