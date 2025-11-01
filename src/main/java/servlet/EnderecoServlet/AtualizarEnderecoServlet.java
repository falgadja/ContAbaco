package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;
import utils.ValidacaoRegex;

import java.io.IOException;

@WebServlet("/endereco-update")
public class AtualizarEnderecoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                EnderecoDAO enderecoDAO = new EnderecoDAO();
                Endereco endereco = enderecoDAO.buscarPorId(id);

                if (endereco != null) {
                    request.setAttribute("endereco", endereco);
                } else {
                    request.setAttribute("mensagemAtualizar", "Endereço não encontrado.");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("mensagemAtualizar", "ID inválido.");
            }
        } else {
            request.setAttribute("mensagemAtualizar", "ID não informado.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Endereco/atualizarEndereco.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String pais = request.getParameter("pais");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String rua = request.getParameter("rua");
        String numeroParam = request.getParameter("numero");
        String cep = request.getParameter("cep");
        String cepValidado = ValidacaoRegex.verificarCep(request.getParameter("cep"));

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        String mensagemAtualizar = null;

        try {
            if (idParam == null || idParam.isEmpty() ||
                    pais == null || pais.trim().isEmpty() ||
                    estado == null || estado.trim().isEmpty() ||
                    cidade == null || cidade.trim().isEmpty() ||
                    bairro == null || bairro.trim().isEmpty() ||
                    rua == null || rua.trim().isEmpty() ||
                    numeroParam == null || numeroParam.isEmpty() ||
                    cep == null || cep.trim().isEmpty()) {

                mensagemAtualizar = "Preencha todos os campos corretamente.";
                request.getSession().setAttribute("mensagem", mensagemAtualizar);
            } else if (cepValidado==null || cepValidado.trim().isEmpty()) {
                request.setAttribute("mensagemAtualizar", "CEP inválido.");}
            else {
                int id = Integer.parseInt(idParam);
                int numero = Integer.parseInt(numeroParam);

                Endereco endereco = new Endereco();
                endereco.setId(id);
                endereco.setPais(pais);
                endereco.setEstado(estado);
                endereco.setCidade(cidade);
                endereco.setBairro(bairro);
                endereco.setRua(rua);
                endereco.setNumero(numero);
                endereco.setCep(cep);

                if (enderecoDAO.atualizar(endereco) > 0) {
                    request.getSession().setAttribute("mensagem", "Endereço atualizado com sucesso!");
                } else {
                    request.getSession().setAttribute("mensagem", "Não foi possível atualizar o endereço.");
                }
            }
        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valores numéricos inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar o endereço: " + e.getMessage());
        }

        // Redireciona sempre para a tela do CRUD de empresas
        response.sendRedirect(request.getContextPath() + "/empresas");
    }
}
