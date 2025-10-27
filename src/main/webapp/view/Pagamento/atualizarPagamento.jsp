<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 26/10/2025
  Time: 14:00
  Página de atualização de Pagamento
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Pagamento" %>

<%
    Pagamento p = (Pagamento) request.getAttribute("pagamento");
    String mensagem = request.getParameter("msg");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Atualizar Pagamento</title>
</head>
<body>

<h1>Atualizar Pagamento</h1>

<% if (mensagem != null) { %>
<p><%= mensagem %></p>
<% } %>

<% if (p != null) { %>
<form action="<%= request.getContextPath() %>/AtualizarPagamentoServlet" method="post">
    <input type="hidden" name="id" value="<%= p.getId() %>">

    Tipo de Pagamento:<br>
    <input type="text" name="tipoPagto" value="<%= p.getTipoPagto() %>" required><br><br>

    Total:<br>
    <input type="number" name="total" step="0.01" value="<%= p.getTotal() %>" required><br><br>

    Data:<br>
    <input type="date" name="data" value="<%= p.getData() %>" required><br><br>

    ID Empresa:<br>
    <input type="number" name="idEmpresa" value="<%= p.getIdEmpresa() %>" required><br><br>

    <input type="submit" value="Atualizar">
    <a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Cancelar</a>
</form>
<% } else { %>
<p>Pagamento não encontrado!</p>
<a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Voltar</a>
<% } %>

</body>
</html>
