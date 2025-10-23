<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/includes/header.jsp" %>

<div class="adicionador">
  <button class="botao-add" onclick="abrirModal('modalEnderecoAdd')">Adicionar Endereço</button>
</div>

<div class="tabela">
  <div class="tabela-container">
    <table id="tabela-endereco">
      <thead><tr><th>id_endereco</th><th>pais</th><th>cidade</th><th>bairro</th><th>rua</th><th>numero</th><th>cep</th><th class="acoes-col">Ações</th></tr></thead>
      <tbody>
        <c:forEach var="en" items="${enderecos}">
          <tr>
            <td>${en.id}</td><td>${en.pais}</td><td>${en.cidade}</td><td>${en.bairro}</td><td>${en.rua}</td><td>${en.numero}</td><td>${en.cep}</td>
            <td class="acoes">
              <div class="action-row">
                <button class="btn ver" onclick="abrirLerEndereco(${en.id},'${en.pais}','${en.cidade}','${en.bairro}','${en.rua}','${en.numero}','${en.cep}')">👁</button>
                <button class="btn editar" onclick="abrirAtualizarEndereco(${en.id},'${en.pais}','${en.cidade}','${en.bairro}','${en.rua}','${en.numero}','${en.cep}')">✏️</button>
                <button class="btn excluir" onclick="abrirDeletarEndereco(${en.id})">🗑️</button>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Add -->
<div id="modalEnderecoAdd" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Adicionar</h3><div class="sub">Endereço</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/InserirEnderecoServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>Pais</label><input type="text" name="pais" required></div>
          <div class="form-row"><label>Cidade</label><input type="text" name="cidade" required></div>
          <div class="form-row"><label>Bairro</label><input type="text" name="bairro" required></div>
          <div class="form-row"><label>Rua</label><input type="text" name="rua" required></div>
          <div class="form-row"><label>Numero</label><input type="text" name="numero" required></div>
          <div class="form-row"><label>CEP</label><input type="text" name="cep" required></div>
          <!-- se necessário, incluir hidden company id -->
          <input type="hidden" name="id_empresa" value="${param.empresaId}">
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalEnderecoAdd')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Atualizar -->
<div id="modalEnderecoAtualizar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Atualizar</h3><div class="sub">Endereço</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/AtualizarEnderecoServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>ID</label><input type="text" name="id" id="end-edit-id" readonly></div>
          <div class="form-row"><label>Pais</label><input type="text" name="pais" id="end-edit-pais" required></div>
          <div class="form-row"><label>Cidade</label><input type="text" name="cidade" id="end-edit-cidade" required></div>
          <div class="form-row"><label>Bairro</label><input type="text" name="bairro" id="end-edit-bairro" required></div>
          <div class="form-row"><label>Rua</label><input type="text" name="rua" id="end-edit-rua" required></div>
          <div class="form-row"><label>Numero</label><input type="text" name="numero" id="end-edit-numero" required></div>
          <div class="form-row"><label>CEP</label><input type="text" name="cep" id="end-edit-cep" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalEnderecoAtualizar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Deletar -->
<div id="modalEnderecoDeletar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Deletar</h3><div class="sub">Endereço</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/DeletarEnderecoServlet" method="post">
        <input type="hidden" name="id" id="end-del-id">
        <p>Confirma deletar esse endereço?</p>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn" style="background:#d9534f;">Deletar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalEnderecoDeletar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function abrirAtualizarEndereco(id,pais,cidade,bairro,rua,numero,cep){
    document.getElementById('end-edit-id').value = id;
    document.getElementById('end-edit-pais').value = pais;
    document.getElementById('end-edit-cidade').value = cidade;
    document.getElementById('end-edit-bairro').value = bairro;
    document.getElementById('end-edit-rua').value = rua;
    document.getElementById('end-edit-numero').value = numero;
    document.getElementById('end-edit-cep').value = cep;
    abrirModal('modalEnderecoAtualizar');
  }
  function abrirDeletarEndereco(id){
    document.getElementById('end-del-id').value = id;
    abrirModal('modalEnderecoDeletar');
  }
  function abrirLerEndereco(id,pais,cidade,bairro,rua,numero,cep){
    abrirAtualizarEndereco(id,pais,cidade,bairro,rua,numero,cep);
    var inputs = ['end-edit-pais','end-edit-cidade','end-edit-bairro','end-edit-rua','end-edit-numero','end-edit-cep'];
    inputs.forEach(i=>document.getElementById(i).setAttribute('readonly','readonly'));
  }
</script>

</main></div>