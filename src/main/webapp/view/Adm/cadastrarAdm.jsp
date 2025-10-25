<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 24/10/2025
  Time: 00:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<div class="container">
    <h2>Cadastrar Administrador</h2>

    <form action="${pageContext.request.contextPath}/InserirAdm" method="post">
        <label for="email">E-mail:</label>
        <input type="email" id="email" name="email" required placeholder="exemplo@email.com">

        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" required>

        <label for="confirmarSenha">Confirmar senha: </label>
        <input type="password" id="confirmarSenha" name="confirmarSenha">

        <button type="submit" class="btn">Cadastrar</button>
    </form>

    <div class="link-voltar">
        <a href="${pageContext.request.contextPath}/crudAdm">← Voltar</a>
    </div>
</div>

</body>
</html>