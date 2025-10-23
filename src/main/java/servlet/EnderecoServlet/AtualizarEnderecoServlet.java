package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;

import java.io.IOException;

// Define que este servlet será acessado pela URL /AtualizarEnderecoServlet
@WebServlet("AtualizarEnderecoServlet")
public class AtualizarEnderecoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona chamadas GET para o metodo POST
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo dados do formulário
        String idParametro = request.getParameter("id");
        String pais = request.getParameter("pais");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String rua = request.getParameter("rua");
        String numeroParametro = request.getParameter("numero");
        String cep = request.getParameter("cep");
        String idEmpresaParametro = request.getParameter("idEmpresa");

        Endereco endereco = new Endereco();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {
            // Verifica se os parâmetros estão vazios ou nulos
            if (idParametro == null || idParametro.isEmpty() ||
                    pais == null || pais.trim().isEmpty() ||
                    estado == null || estado.trim().isEmpty() ||
                    cidade == null || cidade.trim().isEmpty() ||
                    bairro == null || bairro.trim().isEmpty() ||
                    rua == null || rua.trim().isEmpty() ||
                    numeroParametro == null || numeroParametro.isEmpty() ||
                    cep == null || cep.trim().isEmpty() ||
                    idEmpresaParametro == null || idEmpresaParametro.isEmpty()) {

                // Define uma mensagem de erro que será mostrada
                request.setAttribute("mensagemAtualizar", "Não foi possível encontrar os parâmetros.");

            } else {

                int id = Integer.parseInt(idParametro);
                int numero = Integer.parseInt(numeroParametro);
                int idEmpresa = Integer.parseInt(idEmpresaParametro);

                // Criando o objeto do modelo
                endereco.setId(id);
                endereco.setPais(pais.trim());
                endereco.setEstado(estado.trim());
                endereco.setCidade(cidade.trim());
                endereco.setBairro(bairro.trim());
                endereco.setRua(rua.trim());
                endereco.setNumero(numero);
                endereco.setCep(cep.trim());
                endereco.setIdEmpresa(idEmpresa);

                // Chama o metodo atualizar do dao
                if (enderecoDAO.atualizar(endereco) > 0) {
                    request.setAttribute("mensagemAtualizar", "Endereço atualizado com sucesso.");
                } else {
                    request.setAttribute("mensagemAtualizar", "Não foi possível atualizar o Endereço.");
                }
            }
        } catch (NumberFormatException nfe) {
            // Caso algum valor numérico seja inválido
            request.setAttribute("mensagemAtualizar", "Valores numéricos inválidos.");
        } catch (Exception e) {
            // Caso ocorra qualquer outro erro inesperado
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar o endereço.");
        }
    }
}
