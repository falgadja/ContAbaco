<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Empresa, model.Endereco" %>

<%
  Empresa empresa = (Empresa) request.getAttribute("empresa");
  Endereco endereco = (Endereco) request.getAttribute("endereco");
%>

<h2>Detalhes da Empresa</h2>
<p>Nome: <%= empresa.getNome() %></p>
<p>CNPJ: <%= empresa.getCnpj() %></p>

<h3>Endereço</h3>
<% if (endereco != null) { %>
<p>
  <%= endereco.getRua() %>, <%= endereco.getNumero() %> -
  <%= endereco.getBairro() %>, <%= endereco.getCidade() %>/<%= endereco.getEstado() %> -
  CEP: <%= endereco.getCep() %>
</p>

<!-- Botão para atualizar endereço -->
<form action="<%= request.getContextPath() %>/endereco-update" method="get" style="display:inline;">
  <input type="hidden" name="id" value="<%= endereco.getId() %>">
  <button type="submit">Atualizar Endereço</button>
</form>

<% } else { %>
<p>Empresa sem endereço cadastrado.</p>
<a href="<%= request.getContextPath() %>/endereco-create?empresaId=<%= empresa.getId() %>">Inserir Endereço</a>
<% } %>
