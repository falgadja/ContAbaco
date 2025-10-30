package servlet.PlanoServlet;

import dao.PlanoDAO;
import model.Plano;
import jakarta.servlet.RequestDispatcher; // Importe
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 1. URL "limpa"
@WebServlet("/planos-update")
public class AtualizarPlanoServlet extends HttpServlet {

    /**
     * 2. NOVO doGet: Carrega os dados do plano para o formulário de edição.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        if (idParametro == null || idParametro.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do plano não encontrado para edição.");
            response.sendRedirect(request.getContextPath() + "/planos");
            return;
        }

        try {
            int id = Integer.parseInt(idParametro);
            PlanoDAO planoDAO = new PlanoDAO();
            Plano plano = planoDAO.buscarPorId(id); // (Seu PlanoDAO precisa ter 'buscarPorId')

            if (plano == null) {
                request.getSession().setAttribute("mensagem", "Plano não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/planos");
                return;
            }

            // Sucesso: Coloca o plano no request
            request.setAttribute("planoParaEditar", plano);

            // Encaminha (FORWARD) para a JSP do formulário
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/atualizarPlano.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar dados para edição: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/planos");
        }
    }

    /**
     * 3. doPost: Processa as alterações.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParametro = request.getParameter("id");
        String nome = request.getParameter("nome");
        String precoParametro = request.getParameter("preco");

        PlanoDAO planoDAO = new PlanoDAO();

        try {
            if (idParametro == null || idParametro.isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    precoParametro == null || precoParametro.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Parâmetros inválidos. Não foi possível atualizar.");
            } else {
                int id = Integer.parseInt(idParametro);
                double preco = Double.parseDouble(precoParametro);

                Plano plano = new Plano();
                plano.setId(id);
                plano.setNome(nome.trim());
                plano.setPreco(preco);

                if (planoDAO.atualizar(plano) > 0) { // (Seu PlanoDAO precisa ter 'atualizar')
                    request.getSession().setAttribute("mensagem", "Plano atualizado com sucesso.");
                } else {
                    request.getSession().setAttribute("mensagem", "Não foi possível atualizar o plano.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao tentar atualizar o plano.");
        }

        // 4. CORREÇÃO: REDIRECIONA para o servlet de listagem
        response.sendRedirect(request.getContextPath() + "/planos");
    }
}