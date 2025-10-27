package servlet.EmpresaServlet;

import dao.EmpresaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Empresa;
import filtros.EmpresaFiltro;

import java.io.IOException;
import java.util.List;

@WebServlet("/BuscarEmpresaServlet")
public class BuscarEmpresaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String tipoOrdenacao = request.getParameter("tipoOrdenacao");
        EmpresaDAO empresaDAO = new EmpresaDAO();
        EmpresaFiltro empresaFiltro = new EmpresaFiltro();

        try {
            //verifica se aconteceu uma pesquisa por nome
            if (nome != null && !nome.trim().isEmpty()) {
                // Busca a empresa pelo nome
                Empresa empresa = empresaDAO.buscarPorNome(nome);

                // Verifica se existe uma empresa com esse nome
                if (empresa == null) {
                    request.setAttribute("mensagemBusca", "Não foi encontrado nenhuma empresa com esse nome, digite novamente.");
                } else {
                    request.setAttribute("mensagemBusca", "Empresa encontrada.");
                    request.setAttribute("empresa", empresa);
                }

            } else {

                // Lista as empresas
                List<Empresa> empresas = empresaDAO.listar();

                // Verifica se existem empresas registradas
                if (empresas == null || empresas.isEmpty()) {
                    request.setAttribute("mensagemLista", "Não foi encontrado nenhuma empresa");
                } else {
                    request.setAttribute("empresas", empresas);
                }

                //verifica se aconteceu uma pesquisa pela quantidade de funcionários da empresa
                if (min!= null && !min.isEmpty() && max!= null && !max.isEmpty()) {

                    // Valida o ID da empresa passado, transformando de String para Int, se for inválido cai em exceção
                    int minNum = Integer.parseInt(min);
                    int maxNum = Integer.parseInt(max);
                    empresas = empresaFiltro.filtrarPorQtdFuncionario(empresas, minNum, maxNum);

                    // Verifica se existe uma empresa com a quantidade de funcionarios entre min e max
                    if (empresas.isEmpty()) {
                        request.setAttribute("mensagem", "Não foram encontradas empresas entre essa faixa de funcionários");
                    } else {
                        request.setAttribute("mensagem", "funcionários encontrados.");
                    }
                }



                // Ordenação da lista de empresa
                if (tipoOrdenacao != null && !tipoOrdenacao.isEmpty() && empresas != null && !empresas.isEmpty()) {
                    if (tipoOrdenacao.equals("idCrescente")) {
                        empresas = empresaFiltro.OrdenarIdCrece(empresas);
                    } else if (tipoOrdenacao.equals("idDecrescente")) {
                        empresas = empresaFiltro.OrdenarIdDecre(empresas);
                    } else if (tipoOrdenacao.equals("Az")) {
                        empresas = empresaFiltro.OrdenarNomeAz(empresas);
                    } else if (tipoOrdenacao.equals("Za")) {
                        empresas = empresaFiltro.OrdenarNomeZa(empresas);
                    }  else if (tipoOrdenacao.equals("qtndFuncionarioCrescente")) {
                        empresas = empresaFiltro.OrdenarQntdFuncionarioCrece(empresas);
                    } else if (tipoOrdenacao.equals("qtndFuncionarioDecrescente")) {
                        empresas = empresaFiltro.OrdenarQntdFuncionarioDecre(empresas);
                    }
                }
                request.setAttribute("empresas", empresas);
            }

        } catch(NumberFormatException nfe){
            // Caso o min e max seja inválido, retorna uma mensagem ao JSP
            request.setAttribute("mensagem", "Número minímo ou maxímo inválido, digite apenas números inteiros.");
        } catch (Exception e) {
            // Qualquer outro erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagemBusca", "Erro inesperado ao acessar o banco de dados.");
        }

        // Encaminha para o JSP
        request.getRequestDispatcher("../CrudEmpresa.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}