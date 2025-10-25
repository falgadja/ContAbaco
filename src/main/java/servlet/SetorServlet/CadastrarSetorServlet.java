package servlet.SetorServlet;

import dao.SetorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Setor;

import java.io.IOException;

@WebServlet("/InserirSetor")
public class CadastrarSetorServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    // Apenas exibe o formulário
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/view/Setor/cadastrarSetor.jsp").forward(request, response);
    }

    // Recebe os dados do formulário e insere no banco
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");

        // Validação simples
        if (nome == null || nome.trim().isEmpty()) {
            request.setAttribute("mensagem", "O nome do setor não pode ser vazio.");
            request.getRequestDispatcher("/view/Setor/cadastrarSetor.jsp").forward(request, response);
            return;
        }

        // Cria objeto Setor
        Setor setor = new Setor();
        setor.setNome(nome);

        // Cria DAO
        SetorDAO setorDAO = new SetorDAO();

        try {
            int idGerado = setorDAO.inserir(setor);
            if (idGerado > 0) {
                request.setAttribute("mensagem", "Setor cadastrado com sucesso!");
            } else {
                request.setAttribute("mensagem", "Não foi possível cadastrar o setor. Tente novamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado. Contate o administrador.");
        }

        request.getRequestDispatcher("/view/Setor/cadastrarSetor.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
