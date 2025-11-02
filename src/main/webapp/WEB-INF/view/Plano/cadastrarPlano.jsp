<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Plano</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cadastrar.css">

</head>
<body style="background:transparent;">

<div class="container">
    <div class="form-inner">
        <div class="title">Adicionar</div>
        <div class="subtitle">Plano</div>

        <c:if test="${not empty mensagem}">
            <p class="alert">${mensagem}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/planos-create" method="post" target="_top">

            <div class="field-group">
                <label for="nome">Nome do Plano</label>
                <input type="text" id="nome" name="nome" placeholder="Ex: Plano Mensal" value="${param.nome}" required>
            </div>
            <div class="field-group">
                <label for="preco">Preço (R$)</label>
                <input type="number" step="0.01" id="preco" name="preco" placeholder="Ex: 29.99" value="${param.preco}" required>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Cadastrar</button>
                <button type="button" class="btn-sec"
                        onclick="try{ parent.document.getElementById('modalClose').click(); }catch(e){ window.close(); }">
                    Cancelar
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>