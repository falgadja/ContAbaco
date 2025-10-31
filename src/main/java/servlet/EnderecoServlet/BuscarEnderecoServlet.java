package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;
import model.Funcionario;
import model.Plano;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/endereco")
public class BuscarEnderecoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            //verifica se aconteceu uma pesquisa por id
            if (id != null && !id.trim().isEmpty()) {
                // Busca o endereco pelo id

                // Valida o ID passado, transformando de String para Int, se for inválido cai em exceção
                int idNum = Integer.parseInt(id);

                Endereco endereco = enderecoDAO.buscarPorId(idNum);

                // Verifica se existe um endereco com esse id
                if (endereco == null) {
                    request.setAttribute("mensagemBusca", "Não foi encontrado nenhum endereco com esse id, digite novamente.");
                } else {
                    request.setAttribute("mensagemBusca", "endereco encontrado.");
                    request.setAttribute("endereco", endereco);
                }

            }  else {

                // Lista os enderecos
                List<Endereco> enderecos = enderecoDAO.listar();

                // Verifica se existem enderecos registrados
                if (enderecos == null || enderecos.isEmpty()) {
                    request.setAttribute("mensagemLista", "Não foi encontrado nenhum endereco");
                } else {
                    request.setAttribute("enderecos", enderecos);
                }
            }
        } catch (NumberFormatException nfe){
            // Caso o ID seja inválido, retorna uma mensagem ao JSP
            request.setAttribute("mensagemBusca", "ID inválido, digite apenas numeros inteiros");
        }

        catch (Exception e) {
            // Qualquer outro erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagemBusca", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP
        request.getRequestDispatcher("../CadastrarEndereco.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}