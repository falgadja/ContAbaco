package servlet.PagamentoServlet;

import dao.EmpresaDAO;
import dao.PagamentoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
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

@WebServlet("/pagamento-update")
public class AtualizarPagamentoServlet extends HttpServlet {

    /**
     * doGet: Carrega o pagamento E a lista de empresas.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do pagamento não encontrado para edição.");
            response.sendRedirect(request.getContextPath() + "/pagamento");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            PagamentoDAO dao = new PagamentoDAO();
            Pagamento pagamento = dao.buscarPorId(id);

            EmpresaDAO empresaDAO = new EmpresaDAO();
            List<Empresa> empresas = empresaDAO.listar();

            if (pagamento == null) {
                request.getSession().setAttribute("mensagem", "Pagamento não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/pagamento");
                return;
            }

            request.setAttribute("pagamentoParaEditar", pagamento);
            request.setAttribute("empresas", empresas);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/atualizarPagamento.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar dados para edição: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pagamento");
        }
    }

    /**
     * doPost: Processa as alterações.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String tipoPagto = request.getParameter("tipoPagto");
        String totalParam = request.getParameter("total");
        String dataParam = request.getParameter("data");
        String idEmpresaParam = request.getParameter("idEmpresa");

        RequestDispatcher formDispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/atualizarPagamento.jsp");

        try {
            // ==================================================================
            // AQUI ESTÁ A CORREÇÃO (O 'if' com todas as validações)
            // ==================================================================
            if (idParam == null || idParam.isEmpty() ||
                    tipoPagto == null || tipoPagto.trim().isEmpty() ||
                    totalParam == null || totalParam.isEmpty() ||
                    dataParam == null || dataParam.trim().isEmpty() ||
                    idEmpresaParam == null || idEmpresaParam.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos.");

            } else {
                int id = Integer.parseInt(idParam);
                double total = Double.parseDouble(totalParam);
                LocalDate data = LocalDate.parse(dataParam);
                int idEmpresa = Integer.parseInt(idEmpresaParam);

                Pagamento pagamento = new Pagamento();
                pagamento.setId(id);
                pagamento.setTipoPagto(tipoPagto.trim());
                pagamento.setTotal(total);
                pagamento.setData(data);
                pagamento.setIdEmpresa(idEmpresa);

                PagamentoDAO dao = new PagamentoDAO();
                int linhasAfetadas = dao.atualizar(pagamento);

                if (linhasAfetadas > 0) {
                    request.getSession().setAttribute("mensagem", "Pagamento atualizado com sucesso.");
                    response.sendRedirect(request.getContextPath() + "/pagamento");
                    return;
                } else {
                    request.setAttribute("mensagem", "Não foi possível atualizar o pagamento.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao atualizar: " + e.getMessage());
        }

        // Se deu erro, re-busca as listas e faz forward de volta para o form
        try {
            EmpresaDAO empresaDAO = new EmpresaDAO();
            request.setAttribute("empresas", empresaDAO.listar());
        } catch (Exception ex) {
            // Ignora erro aninhado
        }
        formDispatcher.forward(request, response);
    }
}