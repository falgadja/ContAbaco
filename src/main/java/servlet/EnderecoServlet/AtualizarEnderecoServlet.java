package servlet.EnderecoServlet;

// IMPORTS DO DAO E MODEL
import dao.EnderecoDAO;
import dao.EmpresaDAO;
import model.Endereco;
import model.Empresa;

// IMPORTS PADRÕES DE SERVLET
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/endereco-update")
public class AtualizarEnderecoServlet extends HttpServlet {

    // DOGET = CARREGA DADOS DO ENDEREÇO PARA O FORMULÁRIO //
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        // VALIDAÇÃO DE ID
        if (idParam == null || idParam.isEmpty()) {
            request.getSession().setAttribute("mensagem", "ID do endereço não informado.");
            response.sendRedirect(request.getContextPath() + "/enderecos");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            EnderecoDAO enderecoDAO = new EnderecoDAO();

            // BUSCA ENDEREÇO PELO ID
            Endereco endereco = enderecoDAO.buscarPorId(id);
            if (endereco == null) {
                request.getSession().setAttribute("mensagem", "Endereço não encontrado (ID: " + id + ").");
                response.sendRedirect(request.getContextPath() + "/enderecos");
                return;
            }

            // BUSCA EMPRESA ASSOCIADA AO ENDEREÇO
            Empresa empresa = new EmpresaDAO().buscarPorId(endereco.getIdEmpresa());

            // ENVIA OBJETOS PARA O JSP
            request.setAttribute("enderecoParaEditar", endereco);
            request.setAttribute("empresa", empresa);

            // ENCERRA O GET ENCAMINHANDO PARA JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/Endereco/atualizarEndereco.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/enderecos");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro ao carregar endereço: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/enderecos");
        }
    }

    // DOPOST = PROCESSA ATUALIZAÇÃO DO ENDEREÇO //
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // RECEBE OS PARÂMETROS DO FORMULÁRIO
        String idParam = request.getParameter("id");
        String idEmpresaParam = request.getParameter("idEmpresa");
        String pais = request.getParameter("pais");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");
        String bairro = request.getParameter("bairro");
        String rua = request.getParameter("rua");
        String numeroParam = request.getParameter("numero");
        String cep = request.getParameter("cep");

        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try {
            // VALIDAÇÃO DE CAMPOS OBRIGATÓRIOS
            if (idParam == null || idParam.isEmpty() ||
                    idEmpresaParam == null || idEmpresaParam.isEmpty() ||
                    pais == null || pais.trim().isEmpty() ||
                    estado == null || estado.trim().isEmpty() ||
                    cidade == null || cidade.trim().isEmpty() ||
                    bairro == null || bairro.trim().isEmpty() ||
                    rua == null || rua.trim().isEmpty() ||
                    numeroParam == null || numeroParam.isEmpty() ||
                    cep == null || cep.trim().isEmpty()) {

                request.getSession().setAttribute("mensagem", "Preencha todos os campos obrigatórios.");
                response.sendRedirect(request.getContextPath() + "/enderecos");
                return;
            }

            // CONVERSÃO DE TIPOS
            int id = Integer.parseInt(idParam);
            int idEmpresa = Integer.parseInt(idEmpresaParam);
            int numero = Integer.parseInt(numeroParam);

            // CRIA OBJETO ENDEREÇO COM DADOS ATUALIZADOS
            Endereco endereco = new Endereco();
            endereco.setId(id);
            endereco.setIdEmpresa(idEmpresa);
            endereco.setPais(pais.trim());
            endereco.setEstado(estado.trim());
            endereco.setCidade(cidade.trim());
            endereco.setBairro(bairro.trim());
            endereco.setRua(rua.trim());
            endereco.setNumero(numero);
            endereco.setCep(cep.trim());

            // EXECUTA ATUALIZAÇÃO NO BANCO
            if (enderecoDAO.atualizar(endereco) > 0) {
                request.getSession().setAttribute("mensagem", "Endereço atualizado com sucesso!");
            } else {
                request.getSession().setAttribute("mensagem", "Não foi possível atualizar o endereço.");
            }

        } catch (NumberFormatException nfe) {
            request.getSession().setAttribute("mensagem", "Valores numéricos inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensagem", "Erro inesperado ao atualizar endereço: " + e.getMessage());
        }

        // REDIRECIONA PARA LISTAGEM DE ENDEREÇOS
        response.sendRedirect(request.getContextPath() + "/enderecos");
    }
}
