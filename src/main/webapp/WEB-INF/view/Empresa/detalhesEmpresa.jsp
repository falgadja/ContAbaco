<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Empresa, model.Endereco, model.Funcionario, java.util.List" %>

<%
  Empresa empresa = (Empresa) request.getAttribute("empresa");
  Endereco endereco = (Endereco) request.getAttribute("endereco");
  List<Funcionario> funcionarios = (List<Funcionario>) request.getAttribute("funcionarios");
%>

<h2>Detalhes da Empresa</h2>
<p><strong>Nome:</strong> <%= empresa.getNome() %></p>
<p><strong>CNPJ:</strong> <%= empresa.getCnpj() %></p>

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
  <li><strong><%= f.getNome() %></strong> — (<%= f.getEmail() %>)</li>
  <% } %>
</ul>
<% } else { %>
<p>Nenhum funcionário cadastrado nesta empresa.</p>
<% } %>
