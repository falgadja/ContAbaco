package servlet.AdmServlet;

import java.io.IOException;
import java.util.List;

import dao.AdmDAO;
import filtros.AdministradorFiltro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Administrador;

@WebServlet("/BuscarAdmServlet")
public class BuscarAdmServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        AdmDAO admDAO = new AdmDAO();
        AdministradorFiltro administradorFiltro = new AdministradorFiltro();

        try {
            //verifica se aconteceu uma pesquisa por e-mail
            if (email != null && !email.trim().isEmpty()) {
                // Busca o administrador pelo email
                Administrador adm = admDAO.buscarPorEmail(email);

                // Verifica se existe um administrador com esse e-mail
                if (adm == null) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum administrador com esse email, digite novamente.");
                } else {
                    request.setAttribute("mensagem", "Administrador encontrada.");
                    request.setAttribute("adm", adm);
                }

            }  else {

                // Lista os administradores
                List<Administrador> adms = admDAO.listar();

                // Verifica se existem administradores
                if (adms == null || adms.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum administrador");
                } else {
                    request.setAttribute("adms", adms);
                }

                // Ordenação da lista de empresa
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
                request.setAttribute("adms", adms);
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