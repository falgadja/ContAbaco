<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Removemos TODOS os imports de DAO e scriptlets de busca --%>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Atualizar Plano</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">

    <style>
        :root {
            --cor_falgadja_1: #1800CC;
            --cor_falgadja_2: #0C0066;
            --cinza: #7f7aa7;
        }
        * {
            box-sizing: border-box;
            font-family: 'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, Arial;
            margin: 0; padding: 0;
        }
        html, body {
            height: 100%; width: 100%;
            background: transparent;
            color: var(--cor_falgadja_1);
        }
        .container {
            width: 100%;
            height: 100%;
            display: flex; align-items: center; justify-content: center;
            padding: 18px;
            background: transparent;
        }
        .title {
            font-size: 32px; font-weight: 800; color: var(--cor_falgadja_1); text-align: center;
        }
        .subtitle {
            margin-top: -6px; color: var(--cinza); font-weight: 600; font-size: 14px; text-align: center;
        }
        .form-inner { width: 100%; max-width: 760px; display: flex; flex-direction: column; gap: 18px; }
        .form-inner form {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }
        label { display: block; font-weight: 600; color: var(--cor_falgadja_1); margin-bottom: 8px; font-size: 14px; }
        input[type="text"], input[type="number"] {
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
            background:#f0f4ff; border:1px solid #c8d3ff; color:#243; padding:10px 14px; border-radius:10px; font-weight:600;
            width: 100%;
        }
    </style>
</head>
<body style="background:transparent;">

<%-- O servlet envia 'planoParaEditar' --%>
<c:set var="plano" value="${planoParaEditar}" />

<div class="container" style="height:100%; padding: 24px 0; align-items: center; overflow-y: auto;">
    <c:choose>
        <c:when test="${empty plano}">
            <div class="form-inner" role="alert" style="align-items: center;">
                <div class="title">Plano não encontrado</div>
                <div class="actions">
                    <button type="button" class="btn-sec"
                            onclick="try{ parent.document.getElementById('modalClose').click(); }catch(e){ window.close(); }">
                        Fechar
                    </button>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <div class="form-inner" role="dialog" aria-modal="true">
                <div class="title">Atualizar</div>
                <div class="subtitle">Plano</div>

                <c:if test="${not empty mensagem}">
                    <div class="alert">${mensagem}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/planos-update" method="post" target="_top">
                    <input type="hidden" name="id" value="${plano.id}"/>

                    <div class="field-group">
                        <label>Nome do Plano:</label>
                        <input type="text" name="nome" value="${plano.nome}" required/>
                    </div>
                    <div class="field-group">
                        <label>Preço (R$):</label>
                        <input type="number" step="0.01" name="preco" value="${plano.preco}" required/>
                    </div>

                    <div class="actions">
                        <button type="submit" class="btn">Salvar alterações</button>
                        <button type="button" class="btn-sec"
                                onclick="try{ parent.document.getElementById('modalClose').click(); }catch(e){ window.close(); }">
                            Cancelar
                        </button>
                    </div>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>