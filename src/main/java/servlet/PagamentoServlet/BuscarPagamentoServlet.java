package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Pagamento;
import filtros.PagamentoFiltro;



import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BuscarPagamentoServlet")
public class BuscarPagamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recebendo as variaveis de filtragem do JSP
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

            //verifica se aconteceu uma pesquisa por id
            if (id != null && !id.trim().isEmpty()) {

                // Busca o pagamento pelo id
                // Valida o ID passado, transformando de String para Int, se for inválido cai em exceção
                int idNum = Integer.parseInt(id);
                Pagamento pagamento = pagamentoDAO.buscarPorId(idNum);
                // Verifica se existe um pagamento com esse id
                if (pagamento == null) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum pagamento com esse id, digite novamente.");
                } else {
                    request.setAttribute("mensagem", "pagamento encontrado.");
                    request.setAttribute("pagamento", pagamento);
                }

            } else {

                // Lista as pagamentos
                pagamentos = pagamentoDAO.listar();

                // Verifica se existem pagamentos registrados
                if (pagamentos == null || pagamentos.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum pagamento");
                } else {
                    request.setAttribute("pagamentos", pagamentos);
                }

                //verifica se aconteceu uma pesquisa por tipo de pagamento
                if (tipos != null && !tipos.isEmpty()) {
                    pagamentos = pagamentoFiltro.filtrarPorTipo(pagamentos, tipos);

                    // Verifica se existe um pagamento com esse tipo
                    if (pagamentos.isEmpty()) {
                        request.setAttribute("mensagem", "Não foram encontrados pagamentos com esse tipo, digite novamente.");
                    } else {
                        request.setAttribute("mensagem", "pagamentos encontrados.");
                    }
                }

                //verifica se aconteceu uma pesquisa de data do pagamento
                if (inicio != null && !inicio.trim().isEmpty() && fim != null && !fim.trim().isEmpty()) {
                    // Valida a entrada convertendo para a data
                    LocalDate inicioDt = LocalDate.parse(inicio);
                    LocalDate fimDt = LocalDate.parse(fim);
                    pagamentos = pagamentoFiltro.filtrarPorData(pagamentos, inicioDt, fimDt);

                    // Verifica se existe um pagamento com esse tipo
                    if (pagamentos.isEmpty()) {
                        request.setAttribute("mensagem", "Não foram encontrados pagamentos entre essas datas, digite novamente.");
                    } else {
                        request.setAttribute("mensagem", "pagamentos encontrados.");
                    }
                }
                // Ordenação da lista de pagamentos
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
                request.setAttribute("pagamentos", pagamentos);
            }
        } catch(NumberFormatException nfe){
                // Caso o ID seja inválido, retorna uma mensagem ao JSP
                request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");
        } catch (DateTimeParseException dte) {
            // Caso a data passada esteja inválida, retorna uma mensagem ao JSP
            request.setAttribute("mensagem", "Data inválida, verifique a data digitada.");
        } catch(Exception e){
                // Qualquer outro erro inesperado
                e.printStackTrace();
                request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
            }

            // Encaminha para o JSP
            request.getRequestDispatcher("/view/Pagamento/crudPagamento.jsp").forward(request, response);
        }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doGet(request, response);
    }
}