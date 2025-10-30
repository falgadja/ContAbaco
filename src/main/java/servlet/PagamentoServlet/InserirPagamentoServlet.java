package servlet.PagamentoServlet;

import dao.EmpresaDAO; // Importe
import dao.PagamentoDAO;
import jakarta.servlet.RequestDispatcher; // Importe
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.Part; // (Não usado no código)
import model.Empresa; // Importe
import model.Pagamento;

import java.io.IOException;
// import java.io.InputStream; // (Não usado no código)
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List; // Importe
import java.util.regex.Pattern;

// 1. URL "limpa"
@WebServlet("/pagamento-create")
@MultipartConfig
public class InserirPagamentoServlet extends HttpServlet {

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    /**
     * 2. doGet: Exibe o formulário e BUSCA A LISTA DE EMPRESAS.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Busca a lista de empresas para o dropdown
            EmpresaDAO empresaDAO = new EmpresaDAO();
            List<Empresa> empresas = empresaDAO.listar();
            request.setAttribute("empresas", empresas);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao carregar lista de empresas.");
        }
        
        // 3. CORREÇÃO: Encaminha para o JSP dentro do WEB-INF
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/cadastrarPagamento.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Prepara o dispatcher para o caso de erro
        RequestDispatcher formDispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/cadastrarPagamento.jsp");

        try {
            String tipoPagto = request.getParameter("tipoPagto");
            double total = Double.parseDouble(request.getParameter("total"));
            String dataStr = request.getParameter("data");
            int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
            
            // ... (Sua lógica de validação de data) ...
            if (dataStr == null || !Pattern.matches(DATE_REGEX, dataStr)) {
                request.setAttribute("mensagem", "O formato da data deve ser AAAA-MM-DD (ex: 2025-10-27)!");
                formDispatcher.forward(request, response);
                return;
            }
            LocalDate data = LocalDate.parse(dataStr);

            Pagamento pagamento = new Pagamento();
            pagamento.setTipoPagto(tipoPagto);
            pagamento.setTotal(total);
            pagamento.setData(data);
            pagamento.setIdEmpresa(idEmpresa);

            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            int idPagamento = pagamentoDAO.inserir(pagamento);

            if (idPagamento > 0) {
                // 4. CORREÇÃO: Redireciona para o servlet de LISTAGEM
                request.getSession().setAttribute("mensagem", "Pagamento cadastrado com sucesso!");
                response.sendRedirect(request.getContextPath() + "/pagamento");
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar o pagamento. Tente novamente!");
                formDispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao cadastrar pagamento: " + e.getMessage());
            // 5. CORREÇÃO: Recarrega as listas de empresas em caso de erro
            try {
                EmpresaDAO empresaDAO = new EmpresaDAO();
                request.setAttribute("empresas", empresaDAO.listar());
            } catch (Exception ex) {
                // Ignora erro aninhado
            }
            formDispatcher.forward(request, response);
        }
    }
}