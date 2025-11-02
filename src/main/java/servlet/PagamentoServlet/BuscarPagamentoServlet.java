package servlet.PagamentoServlet;

// IMPORTS NECESSÁRIOS: DAO, FILTRO, SERVLET, SESSION, MODEL, DATAS
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
 * SERVLET RESPONSÁVEL POR BUSCAR E LISTAR PAGAMENTOS
 * PERMITE PESQUISA POR ID, FILTRO POR DATA, TIPO E ORDENAR RESULTADOS
 * TRATA MENSAGENS TEMPORÁRIAS (PADRÃO PRG)
 */
@WebServlet("/pagamento")
public class BuscarPagamentoServlet extends HttpServlet {

    // FORMATADOR DE DATAS PARA CONVERSÃO ENTRE STRING E LOCALDATE
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO MENSAGENS TEMPORÁRIAS DA SESSÃO (PADRÃO PRG)
        HttpSession session = request.getSession();
        String mensagemSessao = (String) session.getAttribute("mensagem");
        if (mensagemSessao != null) {
            request.setAttribute("mensagem", mensagemSessao); // COLOCA MENSAGEM NO REQUEST
            session.removeAttribute("mensagem"); // REMOVE DA SESSÃO PRA NÃO REPETIR
        }

        // RECEBE PARAMETROS DO FORMULÁRIO
        String id = request.getParameter("id");
        String inicio = request.getParameter("inicio");
        String fim = request.getParameter("fim");
        String[] tiposV = request.getParameterValues("tipos");
        List<String> tipos = new ArrayList<>();
        if (tiposV != null && tiposV.length > 0) {
            tipos = List.of(tiposV); // TRANSFORMA ARRAY EM LIST PARA FACILITAR FILTRO
        }
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // CRIA OBJETOS NECESSÁRIOS: DAO, FILTRO E LISTA
        PagamentoDAO pagamentoDAO = new PagamentoDAO();
        PagamentoFiltro pagamentoFiltro = new PagamentoFiltro();
        List<Pagamento> pagamentos = new ArrayList<>();

        try {
            // PESQUISA POR ID SE INFORMADO
            if (id != null && !id.trim().isEmpty()) {
                int idNum = Integer.parseInt(id);
                Pagamento pagamento = pagamentoDAO.buscarPorId(idNum);

                if (pagamento == null) {
                    request.setAttribute("mensagem", "Nenhum pagamento encontrado com esse ID. Tente novamente.");
                } else {
                    pagamentos.add(pagamento);
                    request.setAttribute("mensagem", "Pagamento encontrado com sucesso.");
                }

            } else {
                // LISTA TODOS OS PAGAMENTOS SE NÃO HOUVER PESQUISA POR ID
                pagamentos = pagamentoDAO.listar();

                if (pagamentos == null || pagamentos.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum pagamento registrado no sistema.");
                } else {

                    // FILTRA POR TIPO DE PAGAMENTO SE NÃO FOR "todos"
                    if (!tipos.isEmpty() && !tipos.contains("todos")) {
                        pagamentos = pagamentoFiltro.filtrarPorTipo(pagamentos, tipos);
                    }

                    // FILTRA POR PERÍODO DE DATAS SE INFORMADO
                    if (inicio != null && !inicio.trim().isEmpty() && fim != null && !fim.trim().isEmpty()) {
                        LocalDate inicioDt = LocalDate.parse(inicio, FORMATTER);
                        LocalDate fimDt = LocalDate.parse(fim, FORMATTER);
                        pagamentos = pagamentoFiltro.filtrarPorData(pagamentos, inicioDt, fimDt);
                    }

                    if (pagamentos.isEmpty()) {
                        request.setAttribute("mensagem", "Não foi encontrado nenhum pagamento no sistema com os filtros aplicados.");
                    } else {
                        request.setAttribute("mensagem", "Foram encontrados " + pagamentos.size() + " pagamentos.");
                    }

                        // ORDENACAO CASO TENHA SIDO ESCOLHIDA
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

            // DEFINE LISTA FINAL COMO ATRIBUTO DO REQUEST
            request.setAttribute("pagamentos", pagamentos);

        } catch (NumberFormatException nfe) {
            // TRATA ERRO QUANDO ID NÃO É NÚMERO
            request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");
        } catch (DateTimeParseException dte) {
            // TRATA ERRO QUANDO DATA É INVÁLIDA
            request.setAttribute("mensagem", "Data inválida. Use o formato dd/MM/yyyy.");
        } catch (Exception e) {
            // TRATA ERROS INESPERADOS
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        // ENCAMINHA PARA O JSP RESPONSÁVEL PELO CRUD DE PAGAMENTOS
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // REDIRECIONA POST PARA O MESMO FLUXO DO GET
        doGet(request, response);
    }
}
