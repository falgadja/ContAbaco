<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Imports de DAO removidos --%>
<%
    String modalParam = request.getParameter("modal");
    boolean isModal = "1".equals(modalParam);
%>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Pagamento</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cadastrar.css">

</head>
<body style="background:transparent;">

<div class="container">
    <div class="form-inner">
        <div class="title">Adicionar</div>
        <div class="subtitle">Pagamento</div>

        <c:if test="${not empty mensagem}">
            <p class="alert">${mensagem}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/pagamento-create" method="post" target="_top">

            <div class="field-group">
                <label for="tipoPagto">Tipo de Pagamento</label>
                <select id="tipoPagto" name="tipoPagto" required>
                    <option value="">Selecione o tipo</option>
                    <option value="Cartão Débito ">Cartão Débito</option>
                    <option value="Boleto">Boleto</option>
                    <option value="Dinheiro">Dinheiro</option>
                    <option value="Cartão Crédito">Cartão Crédito</option>
                    <option value="Transferencia">Transferência</option>
                </select>
            </div>
            <div class="field-group">
                <label for="total">Total (R$)</label>
                <input type="number" step="0.01" id="total" name="total" placeholder="Ex: 29.99" required>
            </div>
            <div class="field-group">
                <label for="data">Data</label>
                <input type="date" id="data" name="data" required>
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