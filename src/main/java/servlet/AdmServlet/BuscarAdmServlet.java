package servlet.AdmServlet;

import dao.AdmDAO;
import model.Administrador;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

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
        AdmDAO admDAO = new AdmDAO();

        try {
            //verifica se aconteceu uma pesquisa por e-mail
            if (email != null && !email.trim().isEmpty()) {
                // Busca o adm pelo email
                Administrador adm = admDAO.buscarPorEmail(email);

                // Verifica se existe um administrador com esse e-mail
                if (adm == null) {
                    request.setAttribute("mensagemBusca", "Não foi encontrado nenhum administrador com esse email, digite novamente.");
                } else {
                    request.setAttribute("mensagemBusca", "Empresa encontrada.");
                    request.setAttribute("adm", adm);
                }

            }  else {

                // Lista os administradores
                List<Administrador> adms = admDAO.listar();

                // Verifica se existem empresas registradas
                if (adms == null || adms.isEmpty()) {
                    request.setAttribute("mensagemLista", "Não foi encontrado nenhum administrador");
                } else {
                    request.setAttribute("adms", adms);
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