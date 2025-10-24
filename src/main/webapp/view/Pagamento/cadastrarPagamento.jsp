<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Pagamento</title>
</head>
<body>
<h1>Cadastrar Pagamento</h1>

<form action="<%= request.getContextPath() %>/CadastrarPagamentoServlet" method="post" enctype="multipart/form-data">
    Tipo de Pagamento: <input type="text" name="tipoPagto" required><br><br>
    Total: <input type="number" step="0.01" name="total" required><br><br>
    Data: <input type="date" name="data" required><br><br>
    Comprovante: <input type="file" name="comprovante"><br><br>
    ID Empresa: <input type="number" name="idEmpresa" required><br><br>
    <input type="submit" value="Cadastrar">
</form>

<a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp">Voltar</a>
</body>
</html>

