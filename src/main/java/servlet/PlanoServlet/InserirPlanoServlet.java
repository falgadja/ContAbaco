package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Plano;
import java.io.IOException;

@WebServlet("/planos-create")
public class InserirPlanoServlet extends HttpServlet {

    // Exibe o formulário de cadastro de plano
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/view/Plano/cadastrarPlano.jsp");
        dispatcher.forward(request, response);
    }

    // Processa o cadastro do plano
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");

        try {
            double preco = Double.parseDouble(precoStr);

            Plano plano = new Plano();
            plano.setNome(nome);
            plano.setPreco(preco);

            PlanoDAO planoDAO = new PlanoDAO();
            int idGerado = planoDAO.inserir(plano);

            if (idGerado > 0) {
                request.getSession().setAttribute("mensagem", "Plano cadastrado com sucesso!");
                response.sendRedirect(request.getContextPath() + "/planos");
            } else {
                request.setAttribute("mensagem", "Não foi possível inserir, tente novamente mais tarde.");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Preço inválido. Digite um número válido.");
            doGet(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado. Contate o administrador.");
            doGet(request, response);
        }
    }

}
