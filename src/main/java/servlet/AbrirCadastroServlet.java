package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/abrirCadastro")
public class AbrirCadastroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo"); // recebe "adm", "empresas", etc.
        String pagina = null;

        switch (tipo) {
            case "adm":
                pagina = "view/cadastrarAdm.jsp";
                break;
            case "empresas":
                pagina = "view/cadastrarEmpresa.jsp";
                break;
            case "planos":
                pagina = "view/cadastrarPlano.jsp";
                break;
            case "pagamento":
                pagina = "view/cadastrarPagamento.jsp";
                break;
            default:
                response.getWriter().println("Tipo de cadastro não definido!");
                return;
        }

        // Redireciona para a página JSP correspondente
        request.getRequestDispatcher(pagina).forward(request, response);
    }
}

