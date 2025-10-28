package servlet.PagamentoServlet;

import dao.PagamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Pagamento;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException; // Importado
import java.util.regex.Pattern; // Importado

@WebServlet("/InserirPagamento")
@MultipartConfig // Necessário para upload de arquivo (comprovante)
public class InserirPagamentoServlet extends HttpServlet {

    // Regex para Data (YYYY-MM-DD): Garante o formato exato
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Exibe o formulário JSP de cadastro
        request.getRequestDispatcher("/view/Pagamento/cadastrarPagamento.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Recebe os parâmetros do formulário
            String tipoPagto = request.getParameter("tipoPagto");
            double total = Double.parseDouble(request.getParameter("total"));
            String dataStr = request.getParameter("data");
            int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));

            System.out.println("Tipo de pagto: " + tipoPagto);
            System.out.println("Total: " + total);
            System.out.println("Data: " + dataStr);
            System.out.println("IdEmpresa: " + idEmpresa);


            // --- INÍCIO DA VALIDAÇÃO DE DATA ---

            // 1. Validação de formato de Data com Regex
            if (dataStr == null || !Pattern.matches(DATE_REGEX, dataStr)) {
                request.setAttribute("mensagem", "O formato da data deve ser AAAA-MM-DD (ex: 2025-10-27)!");
                request.getRequestDispatcher("/view/Pagamento/cadastrarPagamento.jsp").forward(request, response);
                return;
            }

            // Conversão de data (agora mais segura)
            LocalDate data = LocalDate.parse(dataStr);

            // Cria o objeto Pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setTipoPagto(tipoPagto);
            pagamento.setTotal(total);
            pagamento.setData(data);
            pagamento.setIdEmpresa(idEmpresa);

            // Insere no banco via DAO
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            int idPagamento = pagamentoDAO.inserir(pagamento);

            if (idPagamento > 0) {
                response.sendRedirect(request.getContextPath()+"/view/Pagamento/crudPagamento.jsp"); // sucesso
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar o pagamento. Tente novamente!");
                request.getRequestDispatcher("/view/Pagamento/cadastrarPagamento.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro: valores numéricos inválidos!");
            request.getRequestDispatcher("/view/Pagamento/cadastrarPagamento.jsp").forward(request, response);
        } catch (DateTimeParseException e) { // <-- NOVO CATCH
            // Ocorre se o formato (Regex) estiver certo, mas a data for impossível
            // Exemplo: "2025-02-30"
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro: A data de pagamento fornecida é inválida!");
            request.getRequestDispatcher("/view/Pagamento/cadastrarPagamento.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao cadastrar pagamento!");
            request.getRequestDispatcher("/view/Pagamento/cadastrarPagamento.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}