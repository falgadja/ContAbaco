<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Endereço</title>
</head>
<body>
<h2>Cadastro de Endereço</h2>
<form action="<%= request.getContextPath() %>/InserirEndereco" method="post">

    <!-- Campo hidden para o ID da empresa -->
    <input type="hidden" name="idEmpresa" value="${param.idEmpresa}">

    <label>País:</label>
    <input type="text" name="pais" required><br>

    <label>Estado:</label>
    <select name="estado" required>
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
    </select><br>

    <label>Cidade:</label>
    <input type="text" name="cidade" required><br>

    <label>Bairro:</label>
    <input type="text" name="bairro" required><br>

    <label>Rua:</label>
    <input type="text" name="rua" required><br>

    <label>Número:</label>
    <input type="number" name="numero" required><br>

    <label>CEP:</label>
    <input type="text" name="cep" required><br>

    <button type="submit">Finalizar</button>
</form>
</body>
</html>
