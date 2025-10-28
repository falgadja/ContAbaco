package servlet.FuncionarioServlet;

import dao.FuncionarioDAO;
import filtros.FuncionarioFiltro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Funcionario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BuscarFuncionarioServlet")
public class BuscarFuncionarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo as variaveis de filtragem do JSP
        String nome = request.getParameter("nome");
        String idEmpresa  = request.getParameter("idEmpresa");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
        List<Funcionario> funcionarios = new ArrayList<>();

        try {

            //verifica se aconteceu uma pesquisa por nome
            if (nome != null && !nome.trim().isEmpty()) {

                String[] partes = nome.split(" ");

                if (partes.length == 1) {
                    // Somente nome
                    funcionarios = funcionarioDAO.buscarPorNome(partes[0]);
                } else {
                    // Nome + sobrenome
                    funcionarios = funcionarioDAO.buscarPorNomeESobrenome(partes[0], partes[1]);
                }
                // Verifica se existe funcionários com esse nome ou nome e sobrenome
                if (funcionarios == null || funcionarios.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum Funcionário com esse nome, digite novamente.");
                }
            } else {

                // Lista as funcionarios
                funcionarios = funcionarioDAO.listar();

                // Verifica se existem funcionarios registrados
                if (funcionarios == null || funcionarios.isEmpty()) {
                    request.setAttribute("mensagem", "Não foi encontrado nenhum funcionário");
                } else {
                    request.setAttribute("funcionarios", funcionarios);
                }

                //verifica se aconteceu uma pesquisa pelo id da empresa do funcionário
                if (idEmpresa!= null && !idEmpresa.isEmpty()) {

                    // Valida o ID da empresa passado, transformando de String para Int, se for inválido cai em exceção
                    int idEmpresaNum = Integer.parseInt(idEmpresa);
                    funcionarios = funcionarioFiltro.filtrarPorIdEmpresa(funcionarios, idEmpresaNum);

                    // Verifica se existe um funcionario com esse ID de empresa
                    if (funcionarios.isEmpty()) {
                        request.setAttribute("mensagem", "Não foram encontrados funcionarios com esse ID de empresa, digite novamente.");
                    } else {
                        request.setAttribute("mensagem", "funcionarios encontrados.");
                    }
                }

                // Ordenação da lista de funcionarios
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && funcionarios != null && !funcionarios.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) {
                        funcionarios = funcionarioFiltro.OrdenarIdCrece(funcionarios);
                    } else if (tipoOrdenacao.equals("idDecrescente")) {
                        funcionarios = funcionarioFiltro.OrdenarIdDecre(funcionarios);
                    } else if (tipoOrdenacao.equals("Az")) {
                        funcionarios = funcionarioFiltro.OrdenarNomeAz(funcionarios);
                    } else if (tipoOrdenacao.equals("Za")) {
                        funcionarios = funcionarioFiltro.OrdenarNomeZa(funcionarios);
                    }
                }
            }
            request.setAttribute("funcionarios", funcionarios);
        } catch(NumberFormatException nfe){
            // Caso o ID seja inválido, retorna uma mensagem ao JSP
            request.setAttribute("mensagem", "ID inválido, digite apenas números inteiros.");
        } catch(Exception e){
            // Qualquer outro erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagem", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP
        request.getRequestDispatcher("/view/Funcionario/crudFuncionario.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}