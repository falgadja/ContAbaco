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
import java.util.List;

@WebServlet("/pagamento-update")
public class AtualizarPagamentoServlet extends HttpServlet {

    /**
     * doGet: Carrega o pagamento e a lista de empresas para o formulário de edição.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo o ID do pagamento da URL
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do pagamento não informado.");
            response.sendRedirect(request.getContextPath() + "/pagamento");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // Buscando o pagamento pelo ID
            Pagamento pagamento = new PagamentoDAO().buscarPorId(id);
            if (pagamento == null) {
                request.getSession().setAttribute("mensagem", "Pagamento não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/pagamento");
                return;
            }

            // Buscando todas as empresas para preencher o dropdown
            List<Empresa> empresas = new EmpresaDAO().listar();

            // Colocando os objetos no request para a JSP
            request.setAttribute("pagamentoParaEditar", pagamento);
            request.setAttribute("empresas", empresas);

            // Encaminhando para a página de edição
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/atualizarPagamento.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar dados: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pagamento");
        }
    }

    /**
     * doPost: Processa a atualização do pagamento.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo os parâmetros do formulário
        String idParam = request.getParameter("id");
        String tipoPagto = request.getParameter("tipoPagto");
        String totalParam = request.getParameter("total");
        String dataParam = request.getParameter("data");
        String idEmpresaParam = request.getParameter("idEmpresa");

        PagamentoDAO dao = new PagamentoDAO();

        try {
            // Validação básica: checa se todos os campos foram preenchidos
            if (idParam == null || idParam.isEmpty() ||
                    tipoPagto == null || tipoPagto.trim().isEmpty() ||
                    totalParam == null || totalParam.isEmpty() ||
                    dataParam == null || dataParam.trim().isEmpty() ||
                    idEmpresaParam == null || idEmpresaParam.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos.");

            } else {
                // Convertendo parâmetros para os tipos corretos
                int id = Integer.parseInt(idParam);
                double total = Double.parseDouble(totalParam);
                LocalDate data = LocalDate.parse(dataParam);
                int idEmpresa = Integer.parseInt(idEmpresaParam);

                // Criando objeto Pagamento e preenchendo com os dados do formulário
                Pagamento pagamento = new Pagamento();
                pagamento.setId(id);
                pagamento.setTipoPagto(tipoPagto.trim());
                pagamento.setTotal(total);
                pagamento.setData(data);
                pagamento.setIdEmpresa(idEmpresa);

                // Atualizando no banco de dados
                if (dao.atualizar(pagamento) > 0) {
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

        // Recarrega lista de empresas caso haja erro e volta para o formulário
        try {
            request.setAttribute("empresas", new EmpresaDAO().listar());
        } catch (Exception ignored) {}

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp");
        dispatcher.forward(request, response);
    }
}
