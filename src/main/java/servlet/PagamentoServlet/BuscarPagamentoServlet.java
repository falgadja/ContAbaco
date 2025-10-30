package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.RequestDispatcher; // Importe
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Importe
import model.Pagamento;
import filtros.PagamentoFiltro;
// import model.Plano; // (Não usado neste servlet)

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

// 1. URL "limpa" para o menu
@WebServlet("/pagamento")
public class BuscarPagamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Lógica para ler mensagens da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem"); // Limpa a mensagem
        }

        // --- Sua lógica de filtro (já estava correta) ---
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
                    request.setAttribute("mensagem", "Não foi encontrado nenhum pagamento com esse id, digite novamente.");
                } else {
                    List<Pagamento> lista = new ArrayList<>();
                    lista.add(pagamento);
                    pagamentos = lista;
                    request.setAttribute("mensagem", "Pagamento encontrado.");
                    request.setAttribute("pagamentos", pagamentos);
                }
            } else {
                pagamentos = pagamentoDAO.listar();
                if (pagamentos == null || pagamentos.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum pagamento");
                } else {
                    request.setAttribute("pagamentos", pagamentos);
                }
                if (tipos != null && !tipos.isEmpty()) {
                    // ... (lógica de filtro de tipo) ...
                }
                if (inicio != null && !inicio.trim().isEmpty() && fim != null && !fim.trim().isEmpty()) {
                    // ... (lógica de filtro de data) ...
                }
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && pagamentos != null && !pagamentos.isEmpty()) {
                    // ... (lógica de ordenação) ...
                }
                request.setAttribute("pagamentos", pagamentos);
            }
        } catch(NumberFormatException nfe){
            request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");
        } catch (DateTimeParseException dte) {
            request.setAttribute("mensagem", "Data inválida, verifique a data digitada.");
        } catch(Exception e){
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }
        // --- Fim da lógica de filtro ---

        // 3. Encaminha para o JSP (já estava correto)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}