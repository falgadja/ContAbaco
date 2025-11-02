<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Removemos TODOS os imports de DAO e scriptlets de busca --%>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Atualizar Funcionário</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/atualizar.css">

</head>
<body style="background:transparent;">

<%-- O servlet envia 'funcionarioParaEditar', 'setores', 'empresas' --%>
<c:set var="func" value="${funcionarioParaEditar}" />

<div class="container">
    <c:choose>
        <c:when test="${empty func}">
            <div class="form-inner" role="alert" style="align-items: center;">
                <div class="title">Funcionário não encontrado</div>
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
                <div class="subtitle">Funcionário</div>

                <c:if test="${not empty mensagem}">
                    <div class="alert">${mensagem}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/funcionarios-update" method="post" target="_top">
                    <input type="hidden" name="id" value="${func.id}"/>

                    <div class="field-group">
                        <label>Nome:</label>
                        <input type="text" name="nome" value="${func.nome}" required/>
                    </div>
                    <div class="field-group">
                        <label>Sobrenome:</label>
                        <input type="text" name="sobrenome" value="${func.sobrenome}" required/>
                    </div>
                    <div class="field-group">
                        <label>Data de Nascimento:</label>
                        <input type="date" name="dataNascimento" value="${func.dataNascimento}" required/>
                    </div>
                    <div class="field-group">
                        <label>Email:</label>
                        <input type="email" name="email" value="${func.email}" required/>
                    </div>
                    <div class="field-group">
                        <label>Senha (deixe em branco para não alterar):</label>
                        <input type="password" name="senha" placeholder="Não alterar"/>
                    </div>

                    <div class="field-group">
                        <label for="idSetor">Setor</label>
                        <select id="idSetor" name="idSetor" required>
                            <option value="">Selecione o setor</option>
                            <c:forEach var="setor" items="${setores}">
                                <option value="${setor.id}" ${setor.id == func.idSetor ? 'selected' : ''}>
                                        ${setor.nome}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="field-group">
                        <label for="idEmpresa">Empresa</label>
                        <select id="idEmpresa" name="idEmpresa" required>
                            <option value="">Selecione a empresa</option>
                            <c:forEach var="empresa" items="${empresas}">
                                <option value="${empresa.id}" ${empresa.id == func.idEmpresa ? 'selected' : ''}>
                                        ${empresa.nome}
                                </option>
                            </c:forEach>
                        </select>
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