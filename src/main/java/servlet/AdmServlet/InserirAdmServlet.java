package servlet.AdmServlet;


import dao.AdmDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Administrador;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/InserirAdm")
public class InserirAdmServlet extends HttpServlet {

    //Será criado apenas uma vez

    @Override //Inicializa o servlet de cadastro do adm
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Apenas exibe o formulário de cadastro
        request.getRequestDispatcher("/view/Adm/cadastrarAdm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        // Verifica se as senhas são iguais
        if (!senha.equals(confirmarSenha)) {
            request.setAttribute("mensagem", "As senhas não são iguais!");
            request.getRequestDispatcher("/view/Adm/crudAdm.jsp").forward(request, response);
        }else{
            //Hash da senha
            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());

        // Criar objeto Administrador
            try {
            Administrador administrador = new Administrador();
        administrador.setEmail(email);
        administrador.setSenha(senhaHash);

        //Criar objeto de ADM DAO
        AdmDAO admDAO = new AdmDAO();

        // Inserir no banco

            int idAdm = admDAO.inserir(administrador);
            if (idAdm > 0) {
                response.sendRedirect(request.getContextPath() + "/view/Adm/crudAdm.jsp");
            } else {
                request.setAttribute("mensagem", "Não foi possível inserir, tente mais tarde!");
                request.getRequestDispatcher("/view/erro.jsp").forward(request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro ao processar a requisição.");
            request.getRequestDispatcher("/view/erro.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro interno do servidor.");
            request.getRequestDispatcher("/view/erro.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagem", "Ocorreu um erro ao inserir o administrador!");
            request.getRequestDispatcher("/view/erro.jsp").forward(request, response);
        }
        }
    }

    @Override //Finaliza o servlet e libera recursos alocados
    public void destroy() {
        super.destroy();
    }
}
