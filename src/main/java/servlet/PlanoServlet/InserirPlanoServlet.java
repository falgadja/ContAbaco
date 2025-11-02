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

// DEFINE A ROTA DO SERVLET
@WebServlet("/planos-create")
public class InserirPlanoServlet extends HttpServlet {

    // MÉTODO DOGET → EXIBE O FORMULÁRIO DE CADASTRO DE PLANO
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/cadastrarPlano.jsp");
        dispatcher.forward(request, response);
    }

    // MÉTODO DOPOST → PROCESSA O CADASTRO DE PLANO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO DADOS DO FORMULÁRIO
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");

        try {
            // CONVERSÃO DE PREÇO
            double preco = Double.parseDouble(precoStr);

            // CRIA OBJETO PLANO
            Plano plano = new Plano();
            plano.setNome(nome);
            plano.setPreco(preco);

            // INSERE NO BANCO
            PlanoDAO planoDAO = new PlanoDAO();
            int idGerado = planoDAO.inserir(plano);

            // RETORNA MENSAGEM DE SUCESSO OU ERRO
            if (idGerado > 0) {
                request.getSession().setAttribute("mensagem", "Plano cadastrado com sucesso!");
                response.sendRedirect(request.getContextPath() + "/planos");
            } else {
                request.setAttribute("mensagem", "Não foi possível inserir, tente novamente mais tarde.");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            // TRATA ERRO QUANDO O PREÇO NÃO É NÚMERO VÁLIDO
            request.setAttribute("mensagem", "Preço inválido. Digite um número válido.");
            doGet(request, response);

        } catch (Exception e) {
            // TRATA QUALQUER OUTRO ERRO
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado. Contate o administrador.");
            doGet(request, response);
        }
    }
}
