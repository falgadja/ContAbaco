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

@WebServlet("/InserirPagamento")
@MultipartConfig // Necessário para upload de arquivo (comprovante)
public class InserirPagamentoServlet extends HttpServlet {

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



            // Conversão de data
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
