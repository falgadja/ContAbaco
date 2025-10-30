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
@WebServlet("/AtualizarEnderecoServlet")
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
            if (idParametro == null || idParametro.isEmpty() ||
                    pais == null || pais.trim().isEmpty() ||
                    estado == null || estado.trim().isEmpty() ||
                    cidade == null || cidade.trim().isEmpty() ||
                    bairro == null || bairro.trim().isEmpty() ||
                    rua == null || rua.trim().isEmpty() ||
                    numeroParametro == null || numeroParametro.isEmpty() ||
                    cep == null || cep.trim().isEmpty() ||
                    idEmpresaParametro == null || idEmpresaParametro.isEmpty()) {

                request.setAttribute("mensagemAtualizar", "Parâmetros ausentes no formulário.");

            } else {
                int id = Integer.parseInt(idParametro);
                int numero = Integer.parseInt(numeroParametro);
                int idEmpresa = Integer.parseInt(idEmpresaParametro);

                endereco.setId(id);
                endereco.setPais(pais.trim());
                endereco.setEstado(estado.trim());
                endereco.setCidade(cidade.trim());
                endereco.setBairro(bairro.trim());
                endereco.setRua(rua.trim());
                endereco.setNumero(numero);
                endereco.setCep(cep.trim());
                endereco.setIdEmpresa(idEmpresa);

                if (enderecoDAO.atualizar(endereco) > 0) {
                    response.sendRedirect(request.getContextPath() + "/WEB-INF/view/Empresa/crudEmpresa.jsp");
                    return; // encerra o método para não cair no forward abaixo
                } else {
                    request.setAttribute("mensagemAtualizar", "Não foi possível atualizar o Endereço.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.setAttribute("mensagemAtualizar", "Valores numéricos inválidos.");
        } catch (Exception e) {
            request.setAttribute("mensagemAtualizar", "Erro inesperado ao tentar atualizar o endereço.");
            e.printStackTrace();
        }
    }

}
