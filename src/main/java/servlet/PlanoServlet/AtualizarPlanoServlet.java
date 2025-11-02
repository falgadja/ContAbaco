package servlet.PlanoServlet;

// IMPORTS DO DAO E MODEL
import dao.PlanoDAO;
import model.Plano;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/planos-update")
public class AtualizarPlanoServlet extends HttpServlet {

    // DOGET = CARREGA DADOS DO PLANO PARA O FORMULÁRIO //
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        // VALIDAÇÃO DE ID
        if (idParam == null || idParam.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do plano não informado.");
            response.sendRedirect(request.getContextPath() + "/planos");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            PlanoDAO planoDAO = new PlanoDAO();

            // BUSCA PLANO PELO ID
            Plano plano = planoDAO.buscarPorId(id);
            if (plano == null) {
                request.getSession().setAttribute("mensagem", "Plano não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/planos");
                return;
            }

            // ENVIA OBJETO PARA O JSP
            request.setAttribute("planoParaEditar", plano);

            // ENCERRA O GET ENCAMINHANDO PARA JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/atualizarPlano.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/planos");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar plano: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/planos");
        }
    }

    // DOPOST = PROCESSA ATUALIZAÇÃO DO PLANO //
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // RECEBE PARÂMETROS DO FORMULÁRIO
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String precoParam = request.getParameter("preco");

        PlanoDAO planoDAO = new PlanoDAO();

        try {
            // VALIDAÇÃO BÁSICA DOS CAMPOS OBRIGATÓRIOS
            if (idParam == null || idParam.isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    precoParam == null || precoParam.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos.");

            } else {
                // CONVERSÃO DE TIPOS
                int id = Integer.parseInt(idParam);
                double preco = Double.parseDouble(precoParam);

                // BUSCA PLANO EXISTENTE
                Plano plano = planoDAO.buscarPorId(id);
                if (plano == null) {
                    request.getSession().setAttribute("mensagem", "Plano não encontrado para atualizar.");
                } else {
                    // ATUALIZA DADOS DO PLANO
                    plano.setNome(nome.trim());
                    plano.setPreco(preco);

                    // EXECUTA ATUALIZAÇÃO NO BANCO
                    if (planoDAO.atualizar(plano) > 0) {
                        request.getSession().setAttribute("mensagem", "Plano atualizado com sucesso.");
                    } else {
                        request.getSession().setAttribute("mensagem", "Não foi possível atualizar o plano.");
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valor numérico inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar plano: " + e.getMessage());
        }

        // REDIRECIONA PARA A LISTA DE PLANOS
        response.sendRedirect(request.getContextPath() + "/planos");
    }
}
