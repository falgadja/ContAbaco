package servlet.EnderecoServlet;

import dao.EnderecoDAO;
import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Endereco;
import model.Empresa;

import java.io.IOException;

@WebServlet("/endereco-update")
public class AtualizarEnderecoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.getWriter().write("ID do endereço não informado.");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Endereco endereco = new EnderecoDAO().buscarPorId(id);

            if (endereco == null) {
                response.getWriter().write("Endereço não encontrado (ID: " + id + ").");
                return;
            }

            // 🔹 Buscar empresa associada ao endereço
            int idEmpresa = endereco.getIdEmpresa();
            Empresa empresa = new EmpresaDAO().buscarPorId(idEmpresa);

            // 🔹 Enviar dados para o JSP
            request.setAttribute("enderecoParaEditar", endereco);
            request.setAttribute("empresa", empresa);

            // 🔹 Encaminhar para o JSP de atualização
            request.getRequestDispatcher("/WEB-INF/view/Endereco/atualizarEndereco.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.getWriter().write("ID inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Erro ao carregar dados: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idEmpresaString = request.getParameter("idEmpresa");
        String idParam = request.getParameter("id");
        String pais = request.getParameter("pais");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String rua = request.getParameter("rua");
        String numeroParam = request.getParameter("numero");
        String cep = request.getParameter("cep");

        System.out.println("id do endereco: " + idParam);
        System.out.println("id da empresa: " + idEmpresaString);

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        String mensagem;

        try {
            if (idParam == null || idParam.isEmpty() ||
                    idEmpresaString == null || idEmpresaString.isEmpty() ||
                    pais == null || pais.trim().isEmpty() ||
                    estado == null || estado.trim().isEmpty() ||
                    cidade == null || cidade.trim().isEmpty() ||
                    bairro == null || bairro.trim().isEmpty() ||
                    rua == null || rua.trim().isEmpty() ||
                    numeroParam == null || numeroParam.isEmpty() ||
                    cep == null || cep.trim().isEmpty()) {

                mensagem = "Preencha todos os campos corretamente.";

            } else {
                int id = Integer.parseInt(idParam);
                int idEmpresa = Integer.parseInt(idEmpresaString);
                int numero = Integer.parseInt(numeroParam);

                Endereco endereco = new Endereco();
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
                    mensagem = "Endereço atualizado com sucesso!";
                } else {
                    mensagem = "Não foi possível atualizar o endereço.";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mensagem = "Erro inesperado: " + e.getMessage();
        }

        response.setContentType("text/plain");
        response.getWriter().write(mensagem);
    }
}
