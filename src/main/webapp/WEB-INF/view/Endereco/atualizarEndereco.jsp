<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Endereco, model.Empresa" %>

<%
    Endereco endereco = (Endereco) request.getAttribute("enderecoParaEditar");
    Empresa empresa = (Empresa) request.getAttribute("empresa");
%>

<h2>Atualizar Endereço</h2>

<form id="formEndereco" action="<%= request.getContextPath() %>/endereco-update" method="post">
    <input type="hidden" name="id" value="<%= endereco.getId() %>">
    <input type="hidden" name="idEmpresa" value="<%= empresa.getId() %>">

    <label>Pais:</label>
    <input type="text" name="pais" value="<%= endereco.getPais() %>" required><br>

    <label>Estado:</label>
    <input type="text" name="estado" value="<%= endereco.getEstado() %>" required><br>

    <label>Cidade:</label>
    <input type="text" name="cidade" value="<%= endereco.getCidade() %>" required><br>

    <label>Bairro:</label>
    <input type="text" name="bairro" value="<%= endereco.getBairro() %>" required><br>

    <label>Rua:</label>
    <input type="text" name="rua" value="<%= endereco.getRua() %>" required><br>

    <label>Número:</label>
    <input type="text" name="numero" value="<%= endereco.getNumero() %>" required><br>

    <label>CEP:</label>
    <input type="text" name="cep" value="<%= endereco.getCep() %>" required><br>

    <button type="submit">Atualizar</button>
</form>

<script>
    document.getElementById('formEndereco').addEventListener('submit', function(e){
        e.preventDefault(); // evita submit normal
        const formData = new FormData(this);
        fetch(this.action, { method: 'POST', body: formData })
            .then(response => response.text())
            .then(msg => alert(msg))
            .catch(err => console.error(err));
    });
</script>
