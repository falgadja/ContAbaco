<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Endereço</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cadastrar.css">

</head>

<body>
<div class="container">
    <div class="form-inner">
        <div class="title">Adicionar</div>
        <div class="subtitle">Endereço</div>

        <c:if test="${not empty mensagem}">
            <p class="alert">${mensagem}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/endereco-create" method="post" target="_top">

            <div class="field-group">
                <label for="pais">País:</label>
                <input type="text" id="pais" name="pais" placeholder="Ex: Brasil" value="${param.pais}" required>
            </div>

            <div class="field-group">
                <label for="estado">Estado:</label>
                <select id="estado" name="estado" required>
                    <option value="">Selecione</option>
                    <option value="AC">AC</option>
                    <option value="AL">AL</option>
                    <option value="AP">AP</option>
                    <option value="AM">AM</option>
                    <option value="BA">BA</option>
                    <option value="CE">CE</option>
                    <option value="DF">DF</option>
                    <option value="ES">ES</option>
                    <option value="GO">GO</option>
                    <option value="MA">MA</option>
                    <option value="MT">MT</option>
                    <option value="MS">MS</option>
                    <option value="MG">MG</option>
                    <option value="PA">PA</option>
                    <option value="PB">PB</option>
                    <option value="PR">PR</option>
                    <option value="PE">PE</option>
                    <option value="PI">PI</option>
                    <option value="RJ">RJ</option>
                    <option value="RN">RN</option>
                    <option value="RS">RS</option>
                    <option value="RO">RO</option>
                    <option value="RR">RR</option>
                    <option value="SC">SC</option>
                    <option value="SP">SP</option>
                    <option value="SE">SE</option>
                    <option value="TO">TO</option>
                </select>
            </div>

            <div class="field-group">
                <label for="cep">CEP:</label>
                <input type="text" id="cep" name="cep" placeholder="Ex: 12345678" value="${param.cep}" required>
            </div>

            <div class="field-group">
                <label for="cidade">Cidade:</label>
                <input type="text" id="cidade" name="cidade" placeholder="Ex: São Paulo" value="${param.cidade}" required>
            </div>

            <div class="field-group">
                <label for="bairro">Bairro:</label>
                <input type="text" id="bairro" name="bairro" placeholder="Ex: Freguesia do Ó" value="${param.bairro}" required>
            </div>

            <div class="field-group">
                <label for="rua">Rua:</label>
                <input type="text" id="rua" name="rua" placeholder="Ex: Rua Caraputinga" value="${param.rua}" required>
            </div>

            <div class="field-group">
                <label for="numero">Número:</label>
                <input type="number" id="numero" name="numero" placeholder="Ex: 765" value="${param.numero}" required>
            </div>

            <div class="field-group">
                <label for="empresa">Empresa:</label>
                <select id="empresa" name="idEmpresa" required>
                    <option value="">Selecione a empresa</option>
                    <c:forEach var="empresa" items="${empresas}">
                        <option value="${empresa.id}">
                                ${empresa.nome} (ID: ${empresa.id})
                        </option>
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
