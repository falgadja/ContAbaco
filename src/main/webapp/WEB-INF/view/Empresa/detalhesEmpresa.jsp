<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Empresa, model.Funcionario, model.Endereco" %>

<%
  // Recebe os objetos enviados pelo Servlet
  Empresa empresa = (Empresa) request.getAttribute("empresa");
  Endereco endereco = (Endereco) request.getAttribute("endereco");
  List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Detalhes da Empresa</title>
</head>
<body>
<% if (empresa != null) { %> <!-- Verifica se a empresa existe -->

<!-- Nome da empresa -->
<h1><%= empresa.getNome() %></h1>

<h2>Endereço</h2>
<% if (endereco != null) { %> <!-- Verifica se a empresa possui endereço -->

<!-- Exibe os dados do endereço -->
<p>
  <%= endereco.getRua() %>, <%= endereco.getNumero() %> -
  <%= endereco.getCidade() %>, <%= endereco.getEstado() %>
</p>

<!-- Botão para atualizar o endereço -->
<button onclick="window.location.href='<%= request.getContextPath() %>/atualizarEnderecoServletid=<%= endereco.getId() %>'">
  Atualizar
</button>

<!-- Botão para deletar o endereço com confirmação -->
<button onclick="if(confirm('Deseja excluir este endereço?'))
        window.location.href='<%= request.getContextPath() %>/deletarEnderecoServlet?id=<%= endereco.getId() %>'">
  Deletar
</button>

<% } else { %>
<!-- Caso não exista endereço, exibe link para adicionar -->
<a href="<%= request.getContextPath() %>/InserirEndereco?idEmpresa=<%= empresa.getId() %>">
  Adicionar Endereço
</a>
<% } %>

<h2>Funcionários</h2>
<% if (funcionarios != null && !funcionarios.isEmpty()) { %>
<!-- Lista todos os funcionários -->
<ul>
  <% for (Funcionario f : funcionarios) { %>
  <li><%= f.getNome() %> - <%= f.getEmail() %></li>
  <% } %>
</ul>
<% } else { %>
<!-- Caso não haja funcionários -->
<p>Nenhum funcionário cadastrado.</p>
<% } %>

<% } else { %>
<!-- Caso a empresa não exista ou id não informado -->
<p>Empresa não encontrada!</p>
<% } %>
</body>
</html>