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
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/atualizar.css">

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