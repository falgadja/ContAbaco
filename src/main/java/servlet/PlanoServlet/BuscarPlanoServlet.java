package servlet.PlanoServlet;

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

// 1. URL "limpa" para o menu
@WebServlet("/planos")
public class BuscarPlanoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Lógica para ler mensagens da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagem = (String) session.getAttribute("mensagem");
        if (mensagem != null) {
            request.setAttribute("mensagem", mensagem);
            session.removeAttribute("mensagem");
        }

        // --- Variáveis de Filtro e DAO ---
        String nomeFiltro = request.getParameter("nome");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        PlanoDAO planoDAO = new PlanoDAO();
        PlanoFiltro planoFiltro = new PlanoFiltro();
        List<Plano> planos = new ArrayList<>();
        String mensagemExibicao = null;

        try {
            // 1. Obter a lista completa (ou pré-filtrada, se seu DAO suportar)
            List<Plano> listaCompleta = planoDAO.listar();
            planos = listaCompleta;

            // 2. Aplicar FILTRO por nome (se o parâmetro 'nome' foi enviado)
            if (nomeFiltro != null && !nomeFiltro.trim().isEmpty()) {
                final String nomeLower = nomeFiltro.trim().toLowerCase();

                // Filtra a lista para incluir planos cujo nome contém o filtro
                planos = listaCompleta.stream()
                        .filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(nomeLower))
                        .collect(Collectors.toList());

                if (planos.isEmpty()) {
                    mensagemExibicao = "Não foi encontrado nenhum plano que contenha o nome: " + nomeFiltro;
                } else {
                    mensagemExibicao = "Foram encontrados " + planos.size() + " planos.";
                }
            }


            // 3. Aplicar ORDENAÇÃO (se o parâmetro 'tipoOrdenacao' foi enviado)
            if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && !planos.isEmpty()) {
                switch (tipoOrdenacao) {
                    case "idCrescente":
                        planos = planoFiltro.OrdenarIdCrece(planos);
                        break;
                    case "idDecrescente":
                        planos = planoFiltro.OrdenarIdDecre(planos);
                        break;
                    case "Az":
                        planos = planoFiltro.OrdenarNomeAz(planos);
                        break;
                    case "Za":
                        planos = planoFiltro.OrdenarNomeZa(planos);
                        break;
                    case "precoCrescente":
                        planos = planoFiltro.OrdenarPrecoCrece(planos);
                        break;
                    case "precoDecrescente":
                        planos = planoFiltro.OrdenarPrecoDecre(planos);
                        break;
                    default:
                        // Nenhuma ordenação específica
                        break;
                }
            }

            // 4. Lidar com lista vazia (se não houver filtro por nome)
            if (planos.isEmpty() && (nomeFiltro == null || nomeFiltro.trim().isEmpty())) {
                mensagemExibicao = "Não foi encontrado nenhum plano cadastrado.";
            }

            // 5. Configurar atributos para a JSP
            request.setAttribute("planos", planos);
            // Prioriza a mensagem do fluxo PRG, senão usa a mensagem de filtro/lista
            if (request.getAttribute("mensagem") == null && mensagemExibicao != null) {
                request.setAttribute("mensagem", mensagemExibicao);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }
        // --- Fim da lógica de filtro e ordenação ---

        // 6. Encaminha para o JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/crudPlano.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}