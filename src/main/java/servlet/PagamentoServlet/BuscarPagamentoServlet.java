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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet responsável por buscar e listar pagamentos.
 * Permite pesquisa por ID, tipo, período e ordenação,
 * além de exibir mensagens temporárias armazenadas na sessão (Padrão PRG).
 */
@WebServlet("/pagamento")
public class BuscarPagamentoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leitura de mensagens temporárias da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem"); // Limpa a mensagem após exibir
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

        // Instancia DAO e filtro
        PagamentoDAO pagamentoDAO = new PagamentoDAO();
        PagamentoFiltro pagamentoFiltro = new PagamentoFiltro();
        List<Pagamento> pagamentos = new ArrayList<>();

        try {
            // Pesquisa por ID se informado
            if (id != null && !id.trim().isEmpty()) {
                int idNum = Integer.parseInt(id);
                Pagamento pagamento = pagamentoDAO.buscarPorId(idNum);

                if (pagamento == null) {
                    request.setAttribute("mensagem", "Nenhum pagamento encontrado com esse ID. Tente novamente.");
                } else {
                    pagamentos.add(pagamento);
                    request.setAttribute("mensagem", "Pagamento encontrado com sucesso.");
                    request.setAttribute("pagamentos", pagamentos);
                }

            } else {
                // Lista todos os pagamentos se não houver pesquisa por ID
                pagamentos = pagamentoDAO.listar();

                if (pagamentos == null || pagamentos.isEmpty()) {
                    request.setAttribute("mensagem", "Nenhum pagamento encontrado.");
                } else {

                    // Filtra por tipo, se informado
                    if (tipos != null && !tipos.isEmpty()) {
                        pagamentos = pagamentoFiltro.filtrarPorTipo(pagamentos, tipos);

                        if (pagamentos.isEmpty()) {
                            request.setAttribute("mensagem", "Nenhum pagamento encontrado para os tipos selecionados.");
                        } else {
                            request.setAttribute("mensagem", "Pagamentos encontrados para os tipos selecionados.");
                        }
                    }

                    // Filtra por período de datas, se informado
                    if (inicio != null && !inicio.trim().isEmpty() && fim != null && !fim.trim().isEmpty()) {
                        LocalDate inicioDt = LocalDate.parse(inicio);
                        LocalDate fimDt = LocalDate.parse(fim);
                        pagamentos = pagamentoFiltro.filtrarPorData(pagamentos, inicioDt, fimDt);

                        if (pagamentos.isEmpty()) {
                            request.setAttribute("mensagem", "Nenhum pagamento encontrado nesse período.");
                        } else {
                            request.setAttribute("mensagem", "Pagamentos encontrados no período informado.");
                        }
                    }

                    // Ordena a lista de pagamentos caso tipoOrdenacao seja informado
                    if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && pagamentos != null && !pagamentos.isEmpty()) {
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
            // Data inválida
            request.setAttribute("mensagem", "Data inválida, verifique a data digitada.");
        } catch (Exception e) {
            // Trata erros inesperados
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        } finally {
            // Encaminha para o JSP correspondente
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona POST para o mesmo fluxo do GET
        doGet(request, response);
    }
}
