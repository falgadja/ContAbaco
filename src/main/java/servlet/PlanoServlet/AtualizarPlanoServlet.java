package servlet.PlanoServlet;

import dao.PlanoDAO;
import model.Plano;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/planos-update")
public class AtualizarPlanoServlet extends HttpServlet {

    /**
     * doGet: Carrega os dados do plano para o formulário de edição.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo o ID do plano da URL
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do plano não informado.");
            response.sendRedirect(request.getContextPath() + "/planos");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // Buscando o plano pelo ID
            Plano plano = new PlanoDAO().buscarPorId(id);
            if (plano == null) {
                request.getSession().setAttribute("mensagem", "Plano não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/planos");
                return;
            }

            // Colocando o objeto Plano no request para a JSP
            request.setAttribute("planoParaEditar", plano);

            // Encaminhando para a página de edição
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

    /**
     * doPost: Processa a atualização do plano.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo os parâmetros do formulário
        String idParam = request.getParameter("id");
        String nome = request.getParameter("nome");
        String precoParam = request.getParameter("preco");

        PlanoDAO planoDAO = new PlanoDAO();

        try {
            // Validação básica: checa se todos os campos foram preenchidos
            if (idParam == null || idParam.isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    precoParam == null || precoParam.isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos.");
            } else {
                // Convertendo parâmetros para os tipos corretos
                int id = Integer.parseInt(idParam);
                double preco = Double.parseDouble(precoParam);

                // Criando objeto Plano e preenchendo com os dados do formulário
                Plano plano = new Plano();
                plano.setId(id);
                plano.setNome(nome.trim());
                plano.setPreco(preco);

                // Atualizando no banco de dados
                if (planoDAO.atualizar(plano) > 0) {
                    request.getSession().setAttribute("mensagem", "Plano atualizado com sucesso.");
                } else {
                    request.getSession().setAttribute("mensagem", "Não foi possível atualizar o plano.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valor numérico inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar plano: " + e.getMessage());
        }

        // Redireciona para a lista de planos
        response.sendRedirect(request.getContextPath() + "/planos");
    }
}
