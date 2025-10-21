package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;

import java.io.IOException;

@WebServlet(name = "CadastrarEndereco", value = "/servlet/EnderecoServlet")
public class CadastrarEnderecoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Endereco endereco = new Endereco();
        endereco.setPais(request.getParameter("pais"));
        endereco.setEstado(request.getParameter("estado"));
        endereco.setCidade(request.getParameter("cidade"));
        endereco.setBairro(request.getParameter("bairro"));
        endereco.setRua(request.getParameter("rua"));
        endereco.setNumero(Integer.parseInt(request.getParameter("numero")));
        endereco.setCep(request.getParameter("cep"));
        endereco.setIdEmpresa(Integer.parseInt(request.getParameter("idEmpresa")));

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        int resultado = enderecoDAO.inserir(endereco);

        if (resultado != -1) {
            response.sendRedirect("view/crud.jsp"); // Cadastro finalizado
        } else {
            response.sendRedirect("erro.jsp");
        }
    }
}
