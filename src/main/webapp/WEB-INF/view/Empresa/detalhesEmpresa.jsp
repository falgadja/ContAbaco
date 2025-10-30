<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Empresa, model.Funcionario, model.Endereco" %>
<%@ page import="dao.EmpresaDAO, dao.FuncionarioDAO, dao.EnderecoDAO" %>
<%@ page import="java.util.List" %>

<%
  // Recupera o parâmetro "id" da URL, exemplo: detalhesEmpresa.jsp?id=3
  String idParam = request.getParameter("id");

  // Inicializa objetos que vamos carregar do banco
  Empresa empresa = null;
  Endereco endereco = null;
  List<Funcionario> funcionarios = null;

  if (idParam != null) { // Verifica se foi passado um id
    int id = Integer.parseInt(idParam); // Converte para int

    // Buscar empresa pelo id
    EmpresaDAO empresaDAO = new EmpresaDAO();
    empresa = empresaDAO.buscarPorId(id);

    if (empresa != null) { // Verifica se a empresa existe
      // Buscar endereço usando o id da empresa (não o id do endereço)
      EnderecoDAO enderecoDAO = new EnderecoDAO();
      endereco = enderecoDAO.buscarPorEmpresa(id); // <-- método correto

      // Buscar todos os funcionários associados à empresa
      FuncionarioDAO funcDAO = new FuncionarioDAO();
      funcionarios = funcDAO.buscarPorIdEmpresa(id);
    }
  }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Detalhes da Empresa</title>
</head>
<body>
<% if (empresa != null) { %>
<!-- Exibe nome da empresa -->
<h1><%= empresa.getNome() %></h1>

<h2>Endereço da Empresa</h2>
<% if (endereco != null) { %>
<p>
  <%= endereco.getRua() %>, <%= endereco.getNumero() %>,
  <%= endereco.getBairro() %>, <%= endereco.getCidade() %> -
  <%= endereco.getEstado() %>, <%= endereco.getCep() %>
</p>

<!-- Botão Atualizar Endereço -->
<button onclick="window.location.href='<%= request.getContextPath() %>/view/Endereco/atualizarEndereco.jsp?id=<%= endereco.getId() %>'">
  <i class="fa fa-pen"></i> Atualizar
</button>

<!-- Botão Deletar Endereço com confirmação -->
<button onclick="if(confirm('Deseja excluir este endereço?')) window.location.href='<%= request.getContextPath() %>/deletarEnderecoServlet?id=<%= endereco.getId() %>'">
  <i class="fa fa-trash"></i> Deletar
</button>

<% } else { %>
<!-- Caso não exista endereço, link para adicionar -->
<a href="<%= request.getContextPath() %>/InserirEndereco?idEmpresa=<%= empresa.getId() %>">
  Adicionar Endereço
</a>
<% } %>


<h2>Funcionários</h2>
<% if (funcionarios != null && !funcionarios.isEmpty()) { %>
<ul>
  <% for (Funcionario f : funcionarios) { %>
  <li><%= f.getNome() %> - <%= f.getEmail() %></li>
  <% } %>
</ul>
<% } else { %>
<p>Nenhum funcionário cadastrado.</p>
<% } %>

<% } else { %>
<!-- Caso não exista empresa ou id não informado -->
<p>Empresa não encontrada!</p>
<% } %>
</body>
</html>
