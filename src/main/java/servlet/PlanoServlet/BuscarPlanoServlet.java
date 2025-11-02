package servlet.PlanoServlet;

// IMPORTS NECESSÁRIOS: DAO, FILTRO, SERVLET, SESSION, MODEL E STREAMS
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
 * SERVLET RESPONSÁVEL POR BUSCAR E LISTAR PLANOS
 * PERMITE PESQUISA POR NOME E ORDENAR RESULTADOS
 * EXIBE MENSAGENS TEMPORÁRIAS (PADRÃO PRG)
 */
@WebServlet("/planos")
public class BuscarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO MENSAGENS TEMPORÁRIAS DA SESSÃO (PADRÃO PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem); // COLOCA MENSAGEM NO REQUEST
            session.removeAttribute("mensagem"); // REMOVE DA SESSÃO PRA NÃO REPETIR
        }

        // RECEBE PARAMETROS DO FORMULÁRIO
        String nome = request.getParameter("nome");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // CRIA OBJETOS NECESSÁRIOS: DAO, FILTRO, LISTA E MENSAGEM EXIBIÇÃO
        PlanoDAO planoDAO = new PlanoDAO();
        PlanoFiltro planoFiltro = new PlanoFiltro();
        List<Plano> planos = new ArrayList<>();
        String mensagemExibicao = null;

        try {
            // PESQUISA POR NOME SE INFORMADO
            if (nome != null && !nome.trim().isEmpty()) {
                Plano plano = planoDAO.buscarPorNome(nome);

                if (plano == null) {
                    request.setAttribute("mensagem", "Nenhum plano encontrado com esse nome. Tente novamente.");
                } else {
                    // CRIA LISTA COM ÚNICO RESULTADO
                    planos.add(plano);
                    request.setAttribute("mensagem", "Plano encontrado com sucesso.");
                    request.setAttribute("planos", planos);
                }
            } else {
                // LISTA TODOS OS PLANOS SE NÃO HOUVER PESQUISA POR NOME
                List<Plano> listaCompleta = planoDAO.listar();
                planos = listaCompleta;

                if (planos.isEmpty()) {
                    mensagemExibicao = "Não foi encontrado nenhum registrado no sistema.";
                } else {
                    mensagemExibicao = "Foram encontrados " + planos.size() + " planos.";
                }

                // ORDENACAO CASO TENHA SIDO ESCOLHIDA
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

            // DEFINE LISTA FINAL COMO ATRIBUTO DO REQUEST
            request.setAttribute("planos", planos);

        } catch (Exception e) {
            // TRATA ERROS INESPERADOS
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        // ENCAMINHA PARA O JSP RESPONSÁVEL PELO CRUD DE PLANOS
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/crudPlano.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // REDIRECIONA POST PARA O MESMO FLUXO DO GET
        doGet(request, response);
    }
}
