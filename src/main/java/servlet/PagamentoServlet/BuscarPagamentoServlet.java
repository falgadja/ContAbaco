package servlet.PagamentoServlet;


// Imports da classe
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

/**
 * Servlet responsável por buscar e listar pagamentos.
 * Permite pesquisa por ID, filtros de data e tipo de pagamento e ordenação da lista de resultados,
 * Lida também com mensagens temporárias armazenadas na sessão (Padrão PRG).
 */

@WebServlet("/pagamento")
public class BuscarPagamentoServlet extends HttpServlet {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leitura de mensagens temporárias da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagemSessao = (String) session.getAttribute("mensagem");
        if (mensagemSessao != null) {
            request.setAttribute("mensagem", mensagemSessao);
            session.removeAttribute("mensagem");
        }

        // Recebe parâmetros de pesquisa e ordenação do JSP
        String id = request.getParameter("id");
        String inicio = request.getParameter("inicio");
        String fim = request.getParameter("fim");
        String[] tiposV = request.getParameterValues("tipos");
        List<String> tipos = new ArrayList<>();
        if (tiposV != null && tiposV.length > 0) {
            tipos = List.of(tiposV);
        }
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // Instancia DAO, filtro e a lista de resultados
        PagamentoDAO pagamentoDAO = new PagamentoDAO();
        PagamentoFiltro pagamentoFiltro = new PagamentoFiltro();
        List<Pagamento> pagamentos = new ArrayList<>();

        try {

            // Pesquisa por ID se informado
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
                // Lista todos os pagamentos se não houver pesquisa por ID
                pagamentos = pagamentoDAO.listar();

                if (pagamentos == null || pagamentos.isEmpty()) {
                    request.setAttribute("mensagem", "Nenhum pagamento cadastrado.");
                } else {

                    // Filtra por tipo de pagamento se não for "todos"
                    if (!tipos.isEmpty() && !tipos.contains("todos")) {
                        pagamentos = pagamentoFiltro.filtrarPorTipo(pagamentos, tipos);
                    }

                    // Filtra por período de datas se fornecido
                    if (inicio != null && !inicio.trim().isEmpty() && fim != null && !fim.trim().isEmpty()) {
                        LocalDate inicioDt = LocalDate.parse(inicio, FORMATTER);
                        LocalDate fimDt = LocalDate.parse(fim, FORMATTER);
                        pagamentos = pagamentoFiltro.filtrarPorData(pagamentos, inicioDt, fimDt);
                    }

                    // Ordena a lista de pagamentos caso tipoOrdenacao seja informado
                    if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !pagamentos.isEmpty()) {
                        if (tipoOrdenacao.equals("idCrescente")) {
                            pagamentos = pagamentoFiltro.OrdenarIdCrece(pagamentos);
                        } else if (tipoOrdenacao.equals("idDecrescente")) {
                            pagamentos = pagamentoFiltro.OrdenarIdDecre(pagamentos);
                        } else if (tipoOrdenacao.equals("totalCrescente")) {
                            pagamentos = pagamentoFiltro.OrdenarTotalCrece(pagamentos);
                        } else if (tipoOrdenacao.equals("totalDecrescente")) {
                            pagamentos = pagamentoFiltro.OrdenarTotalDecre(pagamentos);
                        } else if (tipoOrdenacao.equals("dataCrescente")) {
                            pagamentos = pagamentoFiltro.OrdenarDataCrece(pagamentos);
                        } else if (tipoOrdenacao.equals("dataDecrescente")) {
                            pagamentos = pagamentoFiltro.OrdenarDataDecre(pagamentos);
                        }
                    }
                }
            }

            // Define a lista de pagamentos como atributo do request
            request.setAttribute("pagamentos", pagamentos);

        } catch (NumberFormatException nfe) {
            // ID inválido
            request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");
        } catch (DateTimeParseException dte) {
            // data inválida
            request.setAttribute("mensagem", "Data inválida. Use o formato dd/MM/yyyy.");
        } catch (Exception e) {
            // Trata erros inesperados
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }
        // Encaminha para o JSP correspondente
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
