<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Empresa</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cadastrar.css">

</head>
<body>

<div class="container">
    <div class="form-inner" role="dialog" aria-modal="true">
        <div class="title">Adicionar</div>
        <div class="subtitle">Empresa</div>

        <c:if test="${not empty mensagem}">
            <div class="alert">${mensagem}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/empresa-create" method="post" target="_top">
            <h2>Cadastre sua empresa</h2>
            <br>
            <br>
            <div class="field-group">
                <label for="nomeEmpresa">Nome da Empresa</label>
                <input type="text" id="nomeEmpresa" name="nomeEmpresa" placeholder="Digite o nome da sua empresa" required>
            </div>
            <div class="field-group">
                <label for="cnpj">CNPJ</label>
                <input type="text" id="cnpj" name="cnpj" placeholder="00.000.000/0000-00" required>
            </div>
            <div class="field-group">
                <label for="emailEmpresa">Email</label>
                <input type="email" id="emailEmpresa" name="emailEmpresa" placeholder="seuemail@exemplo.com" required>
            </div>
            <div class="field-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" placeholder="Crie uma senha forte" required>
            </div>
            <div class="field-group">
                <label for="confirmarSenha">Confirmar senha:</label>
                <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="Repita a senha" required>
            </div>
            <div class="field-group">
                <label for="qntdFuncionarios">Quantidade de funcionários</label>
                <input type="number" id="qntdFuncionarios" name="qntdFuncionarios" min="1" placeholder="Digite a quantidade" required>
            </div>
            <div class="field-group">
                <label for="idPlano">Plano</label>
                <select id="idPlano" name="idPlano" required>
                    <option value="">Selecione um plano</option>
                    <option value="1">Mensal</option>
                    <option value="2">Anual</option>
                    <option value="3">Trimestral</option>
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