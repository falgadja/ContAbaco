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

    <style>
        :root {
            --cor_falgadja_1: #1800CC;
            --cor_falgadja_2: #0C0066;
            --cinza: #ffffff;
        }
        * {
            box-sizing: border-box;
            font-family: 'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, Arial;
            margin: 0; padding: 0;
        }
        html, body, .container {
            height: 100%; width: 100%;
            background: transparent;
            color: var(--cor_falgadja_1);
        }
        .container {
            display: flex; align-items: center; justify-content: center;
            padding: 0;
        }
        .title {
            font-size: 32px; font-weight: 800; color: var(--cor_falgadja_1); text-align: center;
        }
        .subtitle {
            margin-top: -6px; color: var(--cinza); font-weight: 600; font-size: 14px; text-align: center;
        }
        .form-inner {
            width: 100%;
            max-width: 760px;
            display: flex;
            flex-direction: column;
            gap: 18px;
            padding: 18px;
        }
        .form-inner form {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }
        label { display: block; font-weight: 600; color: var(--cor_falgadja_1); margin-bottom: 8px; font-size: 14px; }
        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%; padding: 14px 18px; border-radius: 12px;
            border: 2px solid rgba(24, 0, 204, 0.18);
            outline: none; font-size: 15px; color: #222; background: #fff;
            transition: border .12s, box-shadow .12s;
        }
        input::placeholder { color: rgba(24, 0, 204, 0.22); }
        input:focus { border-color: var(--cor_falgadja_1); box-shadow: 0 8px 18px rgba(24, 0, 204, 0.06); }
        .field-group { display: block; width: 100%; }
        .actions {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin-top: 6px;
            flex-wrap: wrap;
        }
        .btn {
            padding: 12px 42px; border-radius: 12px;
            background: linear-gradient(180deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
            color: #fff; border: 0; font-weight: 800; cursor: pointer; font-size: 16px;
            box-shadow: 0 10px 18px rgba(12, 0, 102, 0.12);
        }
        .btn-sec {
            padding: 12px 18px; border-radius: 12px; background: #fff;
            color: var(--cor_falgadja_1); border: 2px solid rgba(24,0,204,0.18); font-weight: 800; cursor: pointer;
        }
        .alert {
            background:#fceded; border:1px solid #f7b2b2; color:#b71c1c; padding:10px 14px; border-radius:10px; font-weight:600;
            width: 100%;
        }
        @media (max-width:900px) {
            .form-inner { max-width: 100%; }
            .title { font-size: 24px; }
        }
    </style>
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