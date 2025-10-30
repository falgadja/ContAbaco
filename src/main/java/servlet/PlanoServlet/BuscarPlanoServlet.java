package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Plano;
import filtros.PlanoFiltro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BuscarPlanoServlet")
public class BuscarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");
        
        PlanoDAO planoDAO = new PlanoDAO();
        PlanoFiltro planoFiltro = new PlanoFiltro();
        List<Plano> planos = new ArrayList<>();

        try {
            //verifica se aconteceu uma pesquisa por nome
            if (nome != null && !nome.trim().isEmpty()) {
                // Busca o plano pelo nome
                Plano plano = planoDAO.buscarPorNome(nome);

                // Verifica se existe um plano com esse nome
                if ( plano == null) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum plano com esse nome, digite novamente.");
                } else {
                    // transforma em lista com 1 elemento
                    List<Plano> lista = new ArrayList<>();
                    lista.add(plano);
                    planos = lista;
                    request.setAttribute("mensagem", "Plano encontrado.");
                    request.setAttribute("planos", planos);
                }

            }  else {

                // Lista os planos
                planos = planoDAO.listar();

                // Verifica se existem planos registrados
                if (planos == null || planos.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum plano");
                } else {
                    request.setAttribute("planos", planos);
                }

                // Ordenação da lista de planos
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && planos != null && !planos.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) {
                        planos = planoFiltro.OrdenarIdCrece(planos);
                    } else if (tipoOrdenacao.equals("idDecrescente")) {
                        planos = planoFiltro.OrdenarIdDecre(planos);
                    } else if (tipoOrdenacao.equals("Az")) {
                        planos = planoFiltro.OrdenarNomeAz(planos);
                    } else if (tipoOrdenacao.equals("Za")) {
                        planos = planoFiltro.OrdenarNomeZa(planos);
                    }  else if (tipoOrdenacao.equals("precoCrescente")) {
                        planos = planoFiltro.OrdenarPrecoCrece(planos);
                    } else if (tipoOrdenacao.equals("precoDecrescente")) {
                        planos = planoFiltro.OrdenarPrecoDecre(planos);
                    }
                }
                request.setAttribute("planos", planos);
            }
        } catch (Exception e) {
            // Qualquer outro erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP
        request.getRequestDispatcher("/WEB-INF/view/Plano/crudPlano.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}