<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Plano</title>
</head>
<body>
<h1>Cadastrar Novo Plano</h1>

<form action="<%= request.getContextPath() %>/CadastrarPlanoServlet" method="post">
    <label>Nome do Plano:</label>
    <input type="text" name="nome" required><br><br>

    <label>Preço:</label>
    <input type="number" step="0.01" name="preco" required><br><br>


    <button type="submit">Cadastrar</button>
</form>

<a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp">Voltar</a>
</body>
</html>

