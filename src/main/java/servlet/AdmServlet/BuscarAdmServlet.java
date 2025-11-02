package servlet.AdmServlet;

// IMPORTS NECESSÁRIOS PARA FUNCIONAR O SERVLET, CONEXÃO COM DAO, FILTROS E JSP
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.AdmDAO;
import filtros.AdministradorFiltro;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Administrador;

/**
 * SERVLET RESPONSÁVEL POR BUSCAR E LISTAR ADMINISTRADORES
 * PERMITE PESQUISA POR EMAIL, ORDENAR LISTA E TRATAR MENSAGENS TEMPORÁRIAS
 */
@WebServlet("/adm")
public class BuscarAdmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PEGANDO MENSAGENS DA SESSÃO (PADRÃO PRG) PARA MOSTRAR NO JSP
        HttpSession session = request.getSession();
        String mensagemSessao = (String) session.getAttribute("mensagem");
        if (mensagemSessao != null) {
            request.setAttribute("mensagem", mensagemSessao); // JOGA A MENSAGEM NO REQUEST
            session.removeAttribute("mensagem"); // REMOVE DA SESSÃO PRA NÃO REPETIR
        }

        // RECEBE PARAMETROS DO FORMULÁRIO DE PESQUISA E ORDENACAO
        String email = request.getParameter("email");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // CRIA OBJETOS NECESSÁRIOS: DAO, FILTRO E LISTA DE RESULTADOS
        AdmDAO admDAO = new AdmDAO();
        AdministradorFiltro administradorFiltro = new AdministradorFiltro();
        List<Administrador> adms = new ArrayList<>();

        try {
            // SE EMAIL FOR INFORMADO, FAZ A BUSCA POR EMAIL
            if (email != null && !email.trim().isEmpty()) {
                Administrador adm = admDAO.buscarPorEmail(email);
                if (adm == null) {
                    // SE NAO ENCONTRAR, MENSAGEM DE ERRO
                    request.setAttribute("mensagem", "Não foi encontrado nenhum administrador com esse email, digite novamente.");
                } else {
                    // SE ENCONTRAR, COLOCA NA LISTA E PASSA PARA O JSP
                    List<Administrador> lista = new ArrayList<>();
                    lista.add(adm);
                    adms = lista;
                    request.setAttribute("mensagem", "Administrador encontrado.");
                    request.setAttribute("adms", adms);
                }
            } else {
                // SE EMAIL NAO FOR INFORMADO, LISTA TODOS OS ADMINISTRADORES
                adms = admDAO.listar();
                if (adms == null || adms.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum administrador");
                } else {
                    request.setAttribute("adms", adms);
                }

                // SE HOUVER PARAMETRO DE ORDENACAO, ORDENA A LISTA
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && adms != null && !adms.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) {
                        adms = administradorFiltro.OrdenarIdCrece(adms);
                    } else if (tipoOrdenacao.equals("idDecrescente")) {
                        adms = administradorFiltro.OrdenarIdDecre(adms);
                    } else if (tipoOrdenacao.equals("Az")) {
                        adms = administradorFiltro.OrdenarEmailAz(adms);
                    } else if (tipoOrdenacao.equals("Za")) {
                        adms = administradorFiltro.OrdenarEmailZa(adms);
                    }
                }
                // PASSA A LISTA ORDENADA (OU ORIGINAL) PARA O JSP
                request.setAttribute("adms", adms);
            }
        } catch (Exception e) {
            // TRATA ERROS INESPERADOS, MOSTRA NO CONSOLE E PASSA MENSAGEM PARA O JSP
            e.printStackTrace();
            request.setAttribute("mensagemBusca", "Erro inesperado ao acessar o banco de dados.");
        }

        // ENCAMINHA PARA O JSP RESPONSÁVEL PELO CRUD
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
        dispatcher.forward(request, response);
    }

    // DOPOST CHAMA DOGET PRA FORMULARIO DE FILTRO FUNCIONAR SEM PRECISAR REPETIR CÓDIGO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
