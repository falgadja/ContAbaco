package servlet.PagamentoServlet;

// IMPORTS DO DAO E MODEL
import dao.EmpresaDAO;
import dao.PagamentoDAO;
import model.Empresa;
import model.Pagamento;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/pagamento-update")
public class AtualizarPagamentoServlet extends HttpServlet {

    // DOGET = CARREGA DADOS DO PAGAMENTO PARA O FORMULÁRIO //
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        // VALIDAÇÃO DE ID
        if (idParam == null || idParam.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do pagamento não informado.");
            response.sendRedirect(request.getContextPath() + "/pagamento");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            PagamentoDAO pagamentoDAO = new PagamentoDAO();

            // BUSCA PAGAMENTO PELO ID
            Pagamento pagamento = pagamentoDAO.buscarPorId(id);
            if (pagamento == null) {
                request.getSession().setAttribute("mensagem", "Pagamento não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/pagamento");
                return;
            }

            // BUSCA LISTA DE EMPRESAS PARA DROPDOWN
            List<Empresa> empresas = new EmpresaDAO().listar();

            // ENVIA OBJETOS PARA O JSP
            request.setAttribute("pagamentoParaEditar", pagamento);
            request.setAttribute("empresas", empresas);

            // ENCERRA O GET ENCAMINHANDO PARA JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Pagamento/atualizarPagamento.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/pagamento");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar pagamento: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pagamento");
        }
    }

    // DOPOST = PROCESSA ATUALIZAÇÃO DO PAGAMENTO //
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // RECEBE PARÂMETROS DO FORMULÁRIO
        String idParam = request.getParameter("id");
        String tipoPagto = request.getParameter("tipoPagto");
        String totalParam = request.getParameter("total");
        String dataParam = request.getParameter("data");
        String idEmpresaParam = request.getParameter("idEmpresa");

        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        try {
            // VALIDAÇÃO BÁSICA DOS CAMPOS OBRIGATÓRIOS
            if (idParam == null || idParam.isEmpty() ||
                    tipoPagto == null || tipoPagto.trim().isEmpty() ||
                    totalParam == null || totalParam.isEmpty() ||
                    dataParam == null || dataParam.trim().isEmpty() ||
                    idEmpresaParam == null || idEmpresaParam.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos.");
                response.sendRedirect(request.getContextPath() + "/pagamento");
                return;
            }

            // CONVERSÃO DE TIPOS
            int id = Integer.parseInt(idParam);
            double total = Double.parseDouble(totalParam);
            LocalDate data = LocalDate.parse(dataParam);
            int idEmpresa = Integer.parseInt(idEmpresaParam);

            // BUSCA PAGAMENTO EXISTENTE
            Pagamento pagamento = pagamentoDAO.buscarPorId(id);
            if (pagamento == null) {
                request.getSession().setAttribute("mensagem", "Pagamento não encontrado para atualizar.");
                response.sendRedirect(request.getContextPath() + "/pagamento");
                return;
            }

            // ATUALIZA DADOS DO PAGAMENTO
            pagamento.setTipoPagto(tipoPagto.trim());
            pagamento.setTotal(total);
            pagamento.setData(data);
            pagamento.setIdEmpresa(idEmpresa);

            // EXECUTA ATUALIZAÇÃO NO BANCO
            if (pagamentoDAO.atualizar(pagamento) > 0) {
                request.getSession().setAttribute("mensagem", "Pagamento atualizado com sucesso.");
                response.sendRedirect(request.getContextPath() + "/pagamento");
            } else {
                request.getSession().setAttribute("mensagem", "Não foi possível atualizar o pagamento.");
                response.sendRedirect(request.getContextPath() + "/pagamento");
            }

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valores numéricos inválidos.");
            response.sendRedirect(request.getContextPath() + "/pagamento");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar pagamento: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/pagamento");
        }
    }
}
