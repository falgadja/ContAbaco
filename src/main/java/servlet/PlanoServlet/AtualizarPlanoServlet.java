package servlet.PlanoServlet;

import dao.PlanoDAO;
import model.Plano;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AtualizarPlanoServlet")
public class AtualizarPlanoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona o GET para o POST
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Pega os parâmetros do formulário
        String idParametro = request.getParameter("id");
        String nome = request.getParameter("nome");
        String precoParametro = request.getParameter("preco");

        PlanoDAO planoDAO = new PlanoDAO();
        Plano plano = new Plano();

        try {
            // Verifica se os campos estão vazios
            if (idParametro == null || idParametro.isEmpty() ||
                    nome == null || nome.trim().isEmpty() ||
                    precoParametro == null || precoParametro.isEmpty()) {

                request.setAttribute("mensagemAtualizar", "Não foi possível encontrar os parâmetros.");
            } else {
                // Conversão de tipos
                int id = Integer.parseInt(idParametro);
                double preco = Double.parseDouble(precoParametro);

                // Preenche o objeto
                plano.setId(id);
                plano.setNome(nome.trim());
                plano.setPreco(preco);

                // Atualiza no banco
                if (planoDAO.atualizar(plano) > 0) {
                    request.setAttribute("mensagemAtualizar", "Plano atualizado com sucesso.");
                    response.sendRedirect(request.getContextPath() + "/WEB-INF/view/Plano/crudPlano.jsp");
                } else {
                    request.setAttribute("mensagemAtualizar", "Não foi possível atualizar o plano.");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensagemAtualizar", "Valores numéricos inválidos.");
        } catch (Exception e) {
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar o plano.");
        }
    }
}
