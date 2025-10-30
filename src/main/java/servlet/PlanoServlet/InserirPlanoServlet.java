package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.RequestDispatcher; // Importe
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Plano;

import java.io.IOException;

// 1. URL "limpa"
@WebServlet("/planos-create")
public class InserirPlanoServlet extends HttpServlet {

    /**
     * 2. doGet: Apenas exibe o formulário de cadastro.
     * (Corrigido para apontar para o JSP de cadastro, e não um 'crud.jsp' genérico)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/cadastrarPlano.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");
        double preco;

        RequestDispatcher formDispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/cadastrarPlano.jsp");

        try {
            preco = Double.parseDouble(precoStr);
        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Preço inválido. Digite um número válido.");
            formDispatcher.forward(request, response);
            return;
        }

        Plano plano = new Plano();
        plano.setNome(nome);
        plano.setPreco(preco);
        PlanoDAO planoDAO = new PlanoDAO();

        try {
            int idGerado = planoDAO.inserir(plano);
            if (idGerado > 0) {
                // 3. CORREÇÃO: Redireciona para o servlet de LISTAGEM
                request.getSession().setAttribute("mensagem", "Plano cadastrado com sucesso!");
                response.sendRedirect(request.getContextPath() + "/planos");
            } else {
                request.setAttribute("mensagem", "Não foi possível inserir, tente novamente mais tarde.");
                formDispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado. Contate o administrador.");
            formDispatcher.forward(request, response);
        }
    }
}