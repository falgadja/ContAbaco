<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/includes/header.jsp" %>

<div class="adicionador">
  <button class="botao-add" onclick="abrirModal('modalSetorAdd')">Adicionar Setor</button>
</div>

<div class="tabela">
  <div class="tabela-container">
    <table id="tabela-setor">
      <thead><tr><th>id_setor</th><th>nome</th><th class="acoes-col">Ações</th></tr></thead>
      <tbody>
        <c:forEach var="s" items="${setores}">
          <tr>
            <td>${s.id}</td><td>${s.nome}</td>
            <td class="acoes">
              <div class="action-row">
                <button class="btn ver" onclick="abrirLerSetor(${s.id},'${s.nome}')">👁</button>
                <button class="btn editar" onclick="abrirAtualizarSetor(${s.id},'${s.nome}')">✏️</button>
                <button class="btn excluir" onclick="abrirDeletarSetor(${s.id})">🗑️</button>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Modais: Add / Atualizar / Deletar -->
<div id="modalSetorAdd" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Adicionar</h3><div class="sub">Setor</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/InserirSetorServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>Nome</label><input type="text" name="nome" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalSetorAdd')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div id="modalSetorAtualizar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Atualizar</h3><div class="sub">Setor</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/AtualizarSetorServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>ID</label><input type="text" name="id" id="set-edit-id" readonly></div>
          <div class="form-row"><label>Nome</label><input type="text" name="nome" id="set-edit-nome" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalSetorAtualizar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div id="modalSetorDeletar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Deletar</h3><div class="sub">Setor</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/DeletarSetorServlet" method="post">
        <input type="hidden" name="id" id="set-del-id">
        <p>Confirmar exclusão do setor?</p>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn" style="background:#d9534f;">Deletar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalSetorDeletar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function abrirAtualizarSetor(id,nome){
    document.getElementById('set-edit-id').value = id;
    document.getElementById('set-edit-nome').value = nome;
    abrirModal('modalSetorAtualizar');
  }
  function abrirDeletarSetor(id){
    document.getElementById('set-del-id').value = id;
    abrirModal('modalSetorDeletar');
  }
  function abrirLerSetor(id,nome){
    abrirAtualizarSetor(id,nome);
    document.getElementById('set-edit-nome').setAttribute('readonly','readonly');
  }
</script>

</main></div>