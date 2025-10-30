package servlet;

import dao.AdmDAO;
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import dao.PagamentoDAO;
import jakarta.servlet.RequestDispatcher; // Importe o RequestDispatcher
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Pagamento;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

// 2. Anotação corrigida para uma URL "limpa"
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // 1. Método doGet ADICIONADO para MOSTRAR a página de login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Apenas exibe a página de login para o usuário
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Login/login.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        EmpresaDAO empresaDAO = new EmpresaDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        AdmDAO admDAO = new AdmDAO();

        try {
            String hashAdm = admDAO.buscarHashPorEmail(email);
            String hashFunc = funcionarioDAO.buscarHashPorEmail(email);
            String hashEmpresa = empresaDAO.buscarHashPorEmail(email);

            if (hashAdm != null && BCrypt.checkpw(senha, hashAdm)) {
                PagamentoDAO pagamentoDAO = new PagamentoDAO();
                List<Pagamento> pagamentos = pagamentoDAO.listar();
                request.setAttribute("pagamentos", pagamentos);
                request.getRequestDispatcher("/WEB-INF/view/Pagamento/crudPagamento.jsp").forward(request, response);

            } else if (hashFunc != null && BCrypt.checkpw(senha, hashFunc)) {
                // SUGESTÃO: Enviar para o painel do funcionário
                request.getRequestDispatcher("/WEB-INF/view/Funcionario/crudFuncionario.jsp").forward(request, response);

            } else if (hashEmpresa != null && BCrypt.checkpw(senha, hashEmpresa)) {
                // SUGESTÃO: Enviar para o painel da empresa
                request.getRequestDispatcher("/WEB-INF/view/Empresa/crudEmpresa.jsp").forward(request, response);

            } else {
                request.setAttribute("mensagem", "Email ou senha inválidos, digite novamente");
                request.getRequestDispatcher("/WEB-INF/view/Login/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("mensagem", "Erro inesperado: " + e.getMessage());
            // 3. Caminho no 'catch' CORRIGIDO
            request.getRequestDispatcher("/WEB-INF/view/Login/login.jsp").forward(request, response);
        }
    }
}