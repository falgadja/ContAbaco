<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.PagamentoDAO" %>
<%@ page import="model.Pagamento" %>

<%
    PagamentoDAO dao = new PagamentoDAO();
    List<Pagamento> pagamentos = dao.listar();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Pagamentos</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
<h1>Lista de Pagamentos</h1>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tipo</th>
        <th>Total</th>
        <th>Data</th>
        <th>ID Empresa</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Pagamento p : pagamentos) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getTipoPagto() %></td>
        <td><%= p.getTotal() %></td>
        <td><%= p.getData() %></td>
        <td><%= p.getIdEmpresa() %></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Voltar</a>
</body>
</html>
