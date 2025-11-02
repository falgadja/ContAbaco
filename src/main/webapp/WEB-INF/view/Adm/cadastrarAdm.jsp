<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String modalParam = request.getParameter("modal");
    boolean isModal = "1".equals(modalParam);
%>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Administrador</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cadastrar.css">

</head>
<body<%= isModal ? "" : " style='background:#f5f6ff;'" %>>

<%-- ********* VERSÃO MODAL (dentro do iframe) ********* --%>
<% if (isModal) { %>
<div class="container">
    <div class="form-inner" role="dialog" aria-modal="true">
        <div class="title">Adicionar</div>
        <div class="subtitle">Administrador</div>

        <c:if test="${not empty mensagem}">
            <div class="alert">${mensagem}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/adm-create" method="post" target="_top">
            <div class="field-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required placeholder="ex: adm@contabaco.com" value="${param.email}">
            </div>
            <div class="field-group">
                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" required placeholder="">
            </div>
            <div class="field-group">
                <label for="confirmarSenha">Confirmar senha:</label>
                <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="" required>
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

<%-- ********* VERSÃO PÁGINA CHEIA (não-modal) ********* --%>
<% } else { %>
<div class="container" style="min-height:80vh; align-items:flex-start; padding-top:48px;">
    <div class="form-card" style="position:relative;">
        <div class="title">Adicionar</div>
        <div class="subtitle">Administrador</div>

        <div class="form-inner">
            <c:if test="${not empty mensagem}">
                <div class="alert">${mensagem}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/adm-create" method="post">
                <div class="field-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required placeholder="ex: adm@contabaco.com" value="${param.email}">
                </div>
                <div class="field-group">
                    <label for="senha">Senha:</label>
                    <input type="password" id="senha" name="senha" required placeholder="">
                </div>
                <div class="field-group">
                    <label for="confirmarSenha">Confirmar senha:</label>
                    <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="" required>
                </div>
                <div class="actions">
                    <button type="submit" class="btn">Cadastrar</button>
                    <a class="btn-sec" href="${pageContext.request.contextPath}/adm">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
<% } %>
</body>
</html>