package servlet.PagamentoServlet;

// IMPORTS DO DAO E MODEL
import dao.EmpresaDAO;
import dao.PagamentoDAO;
import model.Empresa;
import model.Pagamento;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

// DEFINE A ROTA DO SERVLET E PERMITE UPLOAD DE ARQUIVOS
@WebServlet("/pagamento-create")
@MultipartConfig
public class InserirPagamentoServlet extends HttpServlet {

    // REGEX PRA VALIDAR FORMATO DE DATA AAAA-MM-DD
    private static final Pattern DATE_REGEX = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    // MÉTODO DOGET → EXIBE FORMULÁRIO DE CADASTRO DE PAGAMENTO
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // BUSCA LISTA DE EMPRESAS PRA SELECT
            EmpresaDAO empresaDAO = new EmpresaDAO();
            List<Empresa> empresas = empresaDAO.listar();
            request.setAttribute("empresas", empresas);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao carregar lista de empresas.");
        }

        // REDIRECIONA PRO JSP DE CADASTRO
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/cadastrarPagamento.jsp");
        dispatcher.forward(request, response);
    }

    // MÉTODO DOPOST → PROCESSA O CADASTRO DE PAGAMENTO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // PEGANDO DADOS DO FORMULÁRIO
            String tipoPagto = request.getParameter("tipoPagto");
            String totalStr = request.getParameter("total");
            String dataStr = request.getParameter("data");
            String idEmpresaStr = request.getParameter("idEmpresa");

            // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
            if (tipoPagto == null || tipoPagto.isBlank() ||
                    totalStr == null || totalStr.isBlank() ||
                    dataStr == null || dataStr.isBlank() ||
                    idEmpresaStr == null || idEmpresaStr.isBlank()) {

                request.getSession().setAttribute("mensagem", "Todos os campos são obrigatórios!");
                response.sendRedirect(request.getContextPath() + "/pagamento");
                return;
            }

            // VALIDAÇÃO DE FORMATO DE DATA
            if (!DATE_REGEX.matcher(dataStr).matches()) {
                request.getSession().setAttribute("mensagem", "O formato da data deve ser AAAA-MM-DD (ex: 2025-10-27).");
                response.sendRedirect(request.getContextPath() + "/pagamento");
                return;
            }

            // CONVERSÃO DE VALORES NUMÉRICOS E DATA
            double total = Double.parseDouble(totalStr);
            LocalDate data = LocalDate.parse(dataStr);
            int idEmpresa = Integer.parseInt(idEmpresaStr);

            // CRIA OBJETO PAGAMENTO
            Pagamento pagamento = new Pagamento();
            pagamento.setTipoPagto(tipoPagto);
            pagamento.setTotal(total);
            pagamento.setData(data);
            pagamento.setIdEmpresa(idEmpresa);

            // INSERE NO BANCO
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            int idPagamento = pagamentoDAO.inserir(pagamento);

            // RETORNA MENSAGEM DE SUCESSO OU ERRO
            if (idPagamento > 0) {
                request.getSession().setAttribute("mensagem", "Pagamento cadastrado com sucesso!");
            } else {
                request.getSession().setAttribute("mensagem", "Não foi possível cadastrar o pagamento. Tente novamente!");
            }

            response.sendRedirect(request.getContextPath() + "/pagamento");

        } catch (NumberFormatException e) {
            // ERRO QUANDO NÚMEROS SÃO INVÁLIDOS
            request.getSession().setAttribute("mensagem", "Valor inválido informado (número ou empresa).");
            response.sendRedirect(request.getContextPath() + "/pagamento");

        } catch (DateTimeParseException e) {
            // ERRO QUANDO FOR DATA INVÁLIDA
            request.getSession().setAttribute("mensagem", "Data inválida. Use o formato AAAA-MM-DD.");
            response.sendRedirect(request.getContextPath() + "/pagamento");

        } catch (Exception e) {
            // QUALQUER OUTRO ERRO INTERNO
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro interno: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pagamento");
        }
    }
}
