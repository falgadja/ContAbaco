package servlet.AdmServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.AdmDAO;
import filtros.AdministradorFiltro;
import jakarta.servlet.RequestDispatcher; // IMPORTANTE
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Administrador;

// 1. MUDANÇA PRINCIPAL: URL "limpa"
@WebServlet("/adm")
public class BuscarAdmServlet extends HttpServlet {

    // 2. LÓGICA MOVIDA PARA doGet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        AdmDAO admDAO = new AdmDAO();
        AdministradorFiltro administradorFiltro = new AdministradorFiltro();
        List<Administrador> adms = new ArrayList<>();

        try {
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
                adms = admDAO.listar();
                if (adms == null || adms.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum administrador");
                } else {
                    request.setAttribute("adms", adms);
                }

                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && adms != null && !adms.isEmpty()) {
                    // ... (lógica de ordenação é a mesma) ...
                }
                request.setAttribute("adms", adms);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagemBusca", "Erro inesperado ao acessar o banco de dados.");
        }

        // 3. CAMINHO JÁ ESTAVA CORRETO (forward para a JSP)
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