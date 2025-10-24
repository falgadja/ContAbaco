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
    <title>CRUD de Pagamentos</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
        a.button { text-decoration: none; padding: 6px 12px; border: 1px solid #333; border-radius: 4px; background-color: #eee; color: black; }
        a.button:hover { background-color: #ddd; }
    </style>
</head>
<body>
<h1>CRUD de Pagamentos</h1>

<a href="<%= request.getContextPath() %>/view/Pagamento/cadastrarPagamento.jsp" class="button">Adicionar Pagamento</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tipo</th>
        <th>Total</th>
        <th>Data</th>
        <th>ID Empresa</th>
        <th>Ações</th>
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
        <td>
            <a href="<%= request.getContextPath() %>/view/Pagamento/atualizarPagamento.jsp?id=<%= p.getId() %>" class="button">Atualizar</a>
            <a href="<%= request.getContextPath() %>/view/Pagamento/deletarPagamento.jsp?id=<%= p.getId() %>" class="button" onclick="return confirm('Deseja realmente deletar?');">Deletar</a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
