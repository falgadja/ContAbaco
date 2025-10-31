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

        try {
            String tipoPagto = request.getParameter("tipoPagto");
            String totalStr = request.getParameter("total");
            String dataStr = request.getParameter("data");
            String idEmpresaStr = request.getParameter("idEmpresa");

            if (tipoPagto == null || tipoPagto.isBlank() ||
                    totalStr == null || totalStr.isBlank() ||
                    dataStr == null || dataStr.isBlank() ||
                    idEmpresaStr == null || idEmpresaStr.isBlank()) {
                request.getSession().setAttribute("mensagem", "Todos os campos são obrigatórios!");
                response.sendRedirect(request.getContextPath() + "/pagamento");
                return;
            }

            if (!DATE_REGEX.matcher(dataStr).matches()) {
                request.getSession().setAttribute("mensagem", "O formato da data deve ser AAAA-MM-DD (ex: 2025-10-27).");
                response.sendRedirect(request.getContextPath() + "/pagamento");
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
            } else {
                request.getSession().setAttribute("mensagem", "Não foi possível cadastrar o pagamento. Tente novamente!");
            }

            response.sendRedirect(request.getContextPath() + "/pagamento");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("mensagem", "Valor inválido informado (número ou empresa).");
            response.sendRedirect(request.getContextPath() + "/pagamento");

        } catch (DateTimeParseException e) {
            request.getSession().setAttribute("mensagem", "Data inválida. Use o formato AAAA-MM-DD.");
            response.sendRedirect(request.getContextPath() + "/pagamento");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro interno: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pagamento");
        }
    }

}
