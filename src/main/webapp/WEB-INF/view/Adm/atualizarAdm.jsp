<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Atualizar Administrador</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/atualizar.css">
</head>
<body>

<%-- O objeto "admParaEditar" foi enviado pelo "AtualizarAdmServlet" (doGet) --%>
<c:set var="adm" value="${admParaEditar}" />

<div class="container">
    <c:choose>
        <%-- Caso o servlet não encontre o ADM (ex: ID não existe) --%>
        <c:when test="${empty adm}">
            <div class="form-inner" role="alert">
                <div class="title">Administrador não encontrado</div>
                <div class="actions">
                    <button type="button" class="btn-sec"
                            onclick="try{ parent.document.getElementById('modalClose').click(); }catch(e){ window.close(); }">
                        Fechar
                    </button>
                </div>
            </div>
        </c:when>

        <%-- Caso o servlet encontre o ADM e exiba o formulário --%>
        <c:otherwise>
            <div class="form-inner" role="dialog" aria-modal="true">
                <div class="title">Atualizar</div>
                <div class="subtitle">Administrador</div>

                <c:if test="${not empty mensagem}">
                    <div class="alert">${mensagem}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/adm-update" method="post" target="_top">
                    <div class="field-group">
                        <label for="id_visual">ID:</label>
                        <input type="text" id="id_visual" value="${adm.id}" disabled>
                        <input type="hidden" id="id" name="id" value="${adm.id}">
                    </div>

                    <div class="field-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="${adm.email}" required>
                    </div>

                    <div class="field-group">
                        <label for="senha">Senha (deixe vazio para não alterar):</label>
                        <input type="password" id="senha" name="senha" placeholder="">
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