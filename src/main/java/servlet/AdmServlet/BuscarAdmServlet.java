package servlet.AdmServlet;


// Imports da classe
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
 * Servlet responsável por buscar e listar administradores.
 * Permite pesquisa por e-mail e ordenação da lista de resultados,
 * Lida também com mensagens temporárias armazenadas na sessão (Padrão PRG).
 */

@WebServlet("/adm")
public class BuscarAdmServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leitura de mensagens temporárias da sessão (Padrão PRG)
        HttpSession session = request.getSession();
        String mensagemSessao = (String) session.getAttribute("mensagem");
        if (mensagemSessao != null) {
            request.setAttribute("mensagem", mensagemSessao);
            session.removeAttribute("mensagem");
        }

        // Recebe parâmetros de pesquisa e ordenação do JSP
        String email = request.getParameter("email");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        // Instancia DAO, filtro e a lista de resultados
        AdmDAO admDAO = new AdmDAO();
        AdministradorFiltro administradorFiltro = new AdministradorFiltro();
        List<Administrador> adms = new ArrayList<>();

        try {
            // Pesquisa por e-mail se informado
            if (email != null && !email.trim().isEmpty()) {
                Administrador adm = admDAO.buscarPorEmail(email);
                if (adm == null) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum administrador com esse email, digite novamente.");
                } else {
                    List<Administrador> lista = new ArrayList<>();
                    lista.add(adm);
                    adms = lista;
                    request.setAttribute("mensagem", "Administrador encontrado.");
                    request.setAttribute("adms", adms);
                }
            } else {
                // Lista todos os pagamentos se não houver pesquisa por ID
                adms = admDAO.listar();
                if (adms == null || adms.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum administrador");
                } else {
                    request.setAttribute("adms", adms);
                }

                // Ordena a lista de administradores caso tipoOrdenacao seja informado
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
                // Define a lista de administradores como atributo do request
                request.setAttribute("adms", adms);
            }
        } catch (Exception e) {
            // Trata erros inesperados
            e.printStackTrace();
            request.setAttribute("mensagemBusca", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP correspondente
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Adm/crudAdm.jsp");
        dispatcher.forward(request, response);
    }

    // 4. doPost agora chama o doGet (para o formulário de filtro/busca funcionar)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}