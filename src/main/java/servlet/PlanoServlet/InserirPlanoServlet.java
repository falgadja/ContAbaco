package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Plano;

import java.io.IOException;

@WebServlet("/cadastrarPlano")
public class InserirPlanoServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }
    // Apenas exibe o formulário
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/view/crud.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");
        double preco;

        // Validação de preço
        try {
            preco = Double.parseDouble(precoStr);
        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Preço inválido. Digite um número válido.");
            request.getRequestDispatcher("/view/crud.jsp").forward(request, response);
            return;
        }
        //Objeto de plano
        Plano plano = new Plano();
        plano.setNome(nome);
        plano.setPreco(preco);

        //Criar plano dao
        PlanoDAO planoDAO = new PlanoDAO();

        try {
            int idGerado = planoDAO.inserir(plano);
            if (idGerado > 0) {
                request.setAttribute("mensagem", "Plano cadastrado com sucesso!");
            } else {
                request.setAttribute("mensagem", "Não foi possível inserir, tente novamente mais tarde.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao processar a requisição.");
        } catch (ServletException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro interno do servidor.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado. Contate o administrador.");
        }

        request.getRequestDispatcher("/view/crud.jsp").forward(request, response);
    }

    @Override //Finaliza o servlet e libera recursos alocados
    public void destroy() {
        super.destroy();}
}
