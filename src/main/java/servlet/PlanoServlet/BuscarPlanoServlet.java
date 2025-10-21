package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Plano;

import java.io.IOException;
import java.util.List;

@WebServlet("/BuscarPlanoServlet")
public class BuscarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        PlanoDAO planoDAO = new PlanoDAO();

        try {
            //verifica se aconteceu uma pesquisa por nome
            if (nome != null && !nome.trim().isEmpty()) {
                // Busca o plano pelo nome
                Plano plano = planoDAO.buscarPorNome(nome);

                // Verifica se existe um plano com esse nome
                if ( plano == null) {
                    request.setAttribute("mensagemBusca", "Não foi encontrado nenhum plano com esse nome, digite novamente.");
                } else {
                    request.setAttribute("mensagemBusca", "Plano encontrado.");
                    request.setAttribute("plano", plano);
                }

            }  else {

                // Lista os planos
                List<Plano> planos = planoDAO.listar();

                // Verifica se existem planos registrados
                if (planos == null || planos.isEmpty()) {
                    request.setAttribute("mensagemLista", "Não foi encontrado nenhum plano");
                } else {
                    request.setAttribute("planos", planos);
                }
            }
        } catch (Exception e) {
            // Qualquer outro erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagemBusca", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP
        request.getRequestDispatcher("../CadastrarEmpresa.jsp").forward(request, response);
    }
}