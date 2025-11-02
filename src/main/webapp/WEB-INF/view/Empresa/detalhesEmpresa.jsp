<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Empresa, model.Endereco, model.Funcionario, java.util.List" %>

<%
  // Bloco de código Java para buscar os dados
  Empresa empresa = (Empresa) request.getAttribute("empresa");
  Endereco endereco = (Endereco) request.getAttribute("endereco");
  List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/detalhes.css">
  
  <title>Detalhes da Empresa</title>
</head>
<body>

  
  <h2>Detalhes da Empresa</h2>
  
  <% if (empresa != null) { %>
    <p><strong>Nome:</strong> <%= empresa.getNome() %></p>
    <p><strong>CNPJ:</strong> <%= empresa.getCnpj() %></p>
  <% } else { %>
    <p>Erro ao carregar dados da empresa.</p>
  <% } %>

  <h3>Endereço</h3>
  <% if (endereco != null) { %>
  <p>
    <%= endereco.getRua() %>, <%= endereco.getNumero() %> -
    <%= endereco.getBairro() %>, <%= endereco.getCidade() %>/<%= endereco.getEstado() %> -
    CEP: <%= endereco.getCep() %>
  </p>
  <% } else { %>
  <p>Empresa sem endereço cadastrado.</p>
  <% } %>

  <hr>

  <h3>Funcionários</h3>

  <% if (funcionarios != null && !funcionarios.isEmpty()) { %>
  <ul>
    <% for (Funcionario f : funcionarios) { %>
    <li><strong><%= f.getNome() %></strong> <span>(<%= f.getEmail() %>)</span></li>
    <% } %>
  </ul>
  <% } else { %>
  <p>Nenhum funcionário cadastrado nesta empresa.</p>
  <% } %>
  
</body>
</html>
