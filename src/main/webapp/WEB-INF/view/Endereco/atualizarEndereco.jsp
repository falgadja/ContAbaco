<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Endereco, model.Empresa" %>

<%
    Endereco endereco = (Endereco) request.getAttribute("enderecoParaEditar");
    Empresa empresa = (Empresa) request.getAttribute("empresa");
%>

<h2>Atualizar Endereço</h2>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/atualizar.css">


<form id="formEndereco" action="<%= request.getContextPath() %>/endereco-update" method="post">
    <input type="hidden" name="id" value="<%= endereco.getId() %>">
    <input type="hidden" name="idEmpresa" value="<%= empresa.getId() %>">

    <label>País:</label>
    <input type="text" name="pais" value="<%= endereco.getPais() %>" required>

    <label>Estado:</label>
    <input type="text" name="estado" value="<%= endereco.getEstado() %>" required>

    <label>Cidade:</label>
    <input type="text" name="cidade" value="<%= endereco.getCidade() %>" required>

    <label>Bairro:</label>
    <input type="text" name="bairro" value="<%= endereco.getBairro() %>" required>

    <label>Rua:</label>
    <input type="text" name="rua" value="<%= endereco.getRua() %>" required>

    <label>Número:</label>
    <input type="text" name="numero" value="<%= endereco.getNumero() %>" required>

    <label>CEP:</label>
    <input type="text" name="cep" value="<%= endereco.getCep() %>" required>

    <button type="submit">Atualizar</button>
</form>

<!-- JS corrigido -->
<script>
    document.getElementById('formEndereco').addEventListener('submit', function(e){
        e.preventDefault();

        // Converter FormData em URLSearchParams para enviar no formato x-www-form-urlencoded
        const formData = new FormData(this);
        const params = new URLSearchParams();
        formData.forEach((value, key) => {
            params.append(key, value);
        });

        fetch(this.action, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params
        })
            .then(response => response.text())
            .then(msg => alert(msg))
            .catch(err => console.error(err));
    });
</script>
