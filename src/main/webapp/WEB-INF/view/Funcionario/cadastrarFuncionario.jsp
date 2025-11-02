<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Removemos TODOS os imports de DAO --%>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Funcionário</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cadastrar.css">

</head>
<body style="background:transparent;">

<div class="container">
    <div class="form-inner">
        <div class="title">Adicionar</div>
        <div class="subtitle">Funcionário</div>

        <c:if test="${not empty mensagem}">
            <p class="alert">${mensagem}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/funcionarios-create" method="post" target="_top">

            <div class="field-group">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" placeholder="Nome do funcionário" maxlength="50" required>
            </div>
            <div class="field-group">
                <label for="sobrenome">Sobrenome</label>
                <input type="text" id="sobrenome" name="sobrenome" placeholder="Sobrenome do funcionário" maxlength="50" required>
            </div>
            <div class="field-group">
                <label for="dataNascimento">Data de Nascimento</label>
                <input type="date" id="dataNascimento" name="dataNascimento" required>
            </div>
            <div class="field-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="email@exemplo.com" maxlength="80" required>
            </div>
            <div class="field-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" placeholder="Crie uma senha forte" minlength="8" maxlength="30" required>
            </div>
            <div class="field-group">
                <label for="confirmarSenha">Confirme sua senha</label>
                <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="Repita a senha" minlength="8" maxlength="30" required>
            </div>

            <div class="field-group">
                <label for="idSetor">Setor</label>
                <select id="idSetor" name="idSetor" required>
                    <option value="">Selecione o setor</option>
                    <c:forEach var="setor" items="${setores}">
                        <option value="${setor.id}">${setor.nome}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="field-group">
                <label for="idEmpresa">Empresa</label>
                <select id="idEmpresa" name="idEmpresa" required>
                    <option value="">Selecione a empresa</option>
                    <c:forEach var="empresa" items="${empresas}">
                        <option value="${empresa.id}">${empresa.nome}</option>
                    </c:forEach>
                </select>
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