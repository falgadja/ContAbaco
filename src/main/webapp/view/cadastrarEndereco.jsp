<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 16/10/2025
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Endereço</title>
</head>
<body>
<h2>Cadastro de Endereço</h2>
<form action="EnderecoServlet" method="post">
    <!-- Campo hidden para o ID da empresa -->
    <input type="hidden" name="idEmpresa" value="${param.idEmpresa}">

    <label>País:</label>
    <input type="text" name="pais" required><br>

    <label>Estado:</label>
    <input type="text" name="estado" required><br>

    <label>Cidade:</label>
    <input type="text" name="cidade" required><br>

    <label>Bairro:</label>
    <input type="text" name="bairro" required><br>

    <label>Rua:</label>
    <input type="text" name="rua" required><br>

    <label>Número:</label>
    <input type="number" name="numero" required><br>

    <label>CEP:</label>
    <input type="text" name="cep" required><br>

    <button type="submit">Finalizar</button>
</form>
</body>
</html>
