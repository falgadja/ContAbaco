<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Atualizar Empresa</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/atualizar.css">

</head>
<body>

<c:set var="empresa" value="${empresaParaEditar}" />

<div class="container">
    <c:choose>
        <c:when test="${empty empresa}">
            <div class="form-inner" role="alert">
                <div class="title">Empresa não encontrada</div>
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
                <div class="subtitle">Empresa</div>

                <c:if test="${not empty mensagem}">
                    <div class="alert">${mensagem}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/empresa-update" method="post" target="_top">
                    <input type="hidden" id="id" name="id" value="${empresa.id}">

                    <div class="field-group">
                        <label for="nome">Nome:</label>
                        <input type="text" id="nome" name="nome" value="${empresa.nome}" required>
                    </div>
                    <div class="field-group">
                        <label for="cnpj">CNPJ:</label>
                        <input type="text" id="cnpj" name="cnpj" value="${empresa.cnpj}" required>
                    </div>
                    <div class="field-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="${empresa.email}" required>
                    </div>
                    <div class="field-group">
                        <label for="senha">Senha (deixe vazio para não alterar):</label>
                        <input type="password" id="senha" name="senha" placeholder="">
                    </div>
                    <div class="field-group">
                        <label for="idPlano">Plano</label>
                        <select id="idPlano" name="idPlano" required>
                            <option value="1" ${empresa.idPlano == 1 ? 'selected' : ''}>Mensal</option>
                            <option value="2" ${empresa.idPlano == 2 ? 'selected' : ''}>Anual</option>
                            <option value="3" ${empresa.idPlano == 3 ? 'selected' : ''}>Premium</option>
                        </select>
                    </div>
                    <div class="field-group">
                        <label for="qntdFuncionarios">Qtd Funcionários:</label>
                        <input type="number" id="qntdFuncionarios" name="qntdFuncionarios" value="${empresa.qntdFuncionarios}" required>
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
