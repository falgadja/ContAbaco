package servlet.PlanoServlet;

import dao.PlanoDAO;
import jakarta.servlet.RequestDispatcher; // Importe
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Importe
import model.Plano;
import filtros.PlanoFiltro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            session.removeAttribute("mensagem"); // Limpa a mensagem
        }

        // --- Sua lógica de filtro (já estava correta) ---
        String nome = request.getParameter("nome");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        PlanoDAO planoDAO = new PlanoDAO();
        PlanoFiltro planoFiltro = new PlanoFiltro();
        List<Plano> planos = new ArrayList<>();

        try {
            if (nome != null && !nome.trim().isEmpty()) {
                Plano plano = planoDAO.buscarPorNome(nome);
                if ( plano == null) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum plano com esse nome, digite novamente.");
                } else {
                    List<Plano> lista = new ArrayList<>();
                    lista.add(plano);
                    planos = lista;
                    request.setAttribute("mensagem", "Plano encontrado.");
                    request.setAttribute("planos", planos);
                }
            }  else {
                planos = planoDAO.listar();
                if (planos == null || planos.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum plano");
                } else {
                    request.setAttribute("planos", planos);
                }
                // ... (Sua lógica de ordenação) ...
                request.setAttribute("planos", planos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }
        // --- Fim da lógica de filtro ---

        // 3. Encaminha para o JSP (já estava correto)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Plano/crudPlano.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // O formulário de filtro usa POST (ou GET), então chamamos o doGet
        doGet(request, response);
    }
}