package servlet.PlanoServlet;

// Imports da classe
import dao.PlanoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Plano;
import filtros.PlanoFiltro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet responsável por buscar e listar planos.
 * Permite pesquisa por nome e ordenação da lista de resultados,
 * Lida também com mensagens temporárias armazenadas na sessão (Padrão PRG).
 */

@WebServlet("/planos")
public class BuscarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leitura de mensagens temporárias da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem");
        }

        // Recebe parâmetros de pesquisa e ordenação do JSP
        String nome = request.getParameter("nome");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // Instancia DAO, filtro, mensagem ao JSP e a lista de resultados
        PlanoDAO planoDAO = new PlanoDAO();
        PlanoFiltro planoFiltro = new PlanoFiltro();
        List<Plano> planos = new ArrayList<>();
        String mensagemExibicao = null;

        try {

            // Pesquisa por nome se informado
            if (nome != null && !nome.trim().isEmpty()) {
                Plano plano = planoDAO.buscarPorNome(nome);

                if (plano == null) {
                    request.setAttribute("mensagem", "Nenhum plano encontrado com esse nome. Tente novamente.");
                } else {
                    // Cria lista com único resultado
                    planos.add(plano);
                    request.setAttribute("mensagem", "Plano encontrado com sucesso.");
                    request.setAttribute("planos", planos);
                }
            } else {


                // Lista todos os planos se não houver pesquisa por nome
                List<Plano> listaCompleta = planoDAO.listar();
                planos = listaCompleta;


                if (planos.isEmpty()) {
                    mensagemExibicao = "Não foi encontrado nenhum registrado no sistema.";
                } else {
                    mensagemExibicao = "Foram encontrados " + planos.size() + " planos.";
                }


                // Ordena a lista de planos caso tipoOrdenacao seja informado
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !planos.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) {
                        planos = planoFiltro.OrdenarIdCrece(planos);
                    } else if (tipoOrdenacao.equals("idDecrescente")) {
                        planos = planoFiltro.OrdenarIdDecre(planos);
                    } else if (tipoOrdenacao.equals("Az")) {
                        planos = planoFiltro.OrdenarNomeAz(planos);
                    } else if (tipoOrdenacao.equals("Za")) {
                        planos = planoFiltro.OrdenarNomeZa(planos);
                    } else if (tipoOrdenacao.equals("precoCrescente")) {
                        planos = planoFiltro.OrdenarPrecoCrece(planos);
                    } else if (tipoOrdenacao.equals("precoDecrescente")) {
                        planos = planoFiltro.OrdenarPrecoDecre(planos);
                    }
                }
            }

            // Define a lista de planos como atributo do request
            request.setAttribute("planos", planos);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP correspondente
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/crudPlano.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}