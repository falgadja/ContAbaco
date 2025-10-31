package servlet.PagamentoServlet;

import dao.EmpresaDAO;
import dao.PagamentoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import model.Pagamento;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/pagamento-create")
@MultipartConfig
public class InserirPagamentoServlet extends HttpServlet {

    private static final Pattern DATE_REGEX = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            EmpresaDAO empresaDAO = new EmpresaDAO();
            List<Empresa> empresas = empresaDAO.listar();
            request.setAttribute("empresas", empresas);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao carregar lista de empresas.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/cadastrarPagamento.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/cadastrarPagamento.jsp");

        try {
            String tipoPagto = request.getParameter("tipoPagto");
            String totalStr = request.getParameter("total");
            String dataStr = request.getParameter("data");
            String idEmpresaStr = request.getParameter("idEmpresa");

            // Validação básica de campos
            if (tipoPagto == null || tipoPagto.isBlank() ||
                    totalStr == null || totalStr.isBlank() ||
                    dataStr == null || dataStr.isBlank() ||
                    idEmpresaStr == null || idEmpresaStr.isBlank()) {

                request.setAttribute("mensagem", "Todos os campos são obrigatórios!");
                doGet(request, response);
                return;
            }

            // Validação de formato de data
            if (!DATE_REGEX.matcher(dataStr).matches()) {
                request.setAttribute("mensagem", "O formato da data deve ser AAAA-MM-DD (ex: 2025-10-27).");
                doGet(request, response);
                return;
            }

            double total = Double.parseDouble(totalStr);
            LocalDate data = LocalDate.parse(dataStr);
            int idEmpresa = Integer.parseInt(idEmpresaStr);

            Pagamento pagamento = new Pagamento();
            pagamento.setTipoPagto(tipoPagto);
            pagamento.setTotal(total);
            pagamento.setData(data);
            pagamento.setIdEmpresa(idEmpresa);

            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            int idPagamento = pagamentoDAO.inserir(pagamento);

            if (idPagamento > 0) {
                request.getSession().setAttribute("mensagem", "Pagamento cadastrado com sucesso!");
                response.sendRedirect(request.getContextPath() + "/pagamento");
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar o pagamento. Tente novamente!");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Valor inválido informado (número ou empresa).");
            doGet(request, response);

        } catch (DateTimeParseException e) {
            request.setAttribute("mensagem", "Data inválida. Use o formato AAAA-MM-DD.");
            doGet(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro interno: " + e.getMessage());
            doGet(request, response);
        }
    }
}
