<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/includes/header.jsp" %>

<div class="adicionador">
  <button class="botao-add" onclick="abrirModal('modalPlanoAdd')">Adicionar Plano</button>
</div>

<div class="tabela">
  <div class="tabela-container">
    <table id="tabela-planos">
      <thead><tr><th>id_plano</th><th>nome</th><th>preco</th><th class="acoes-col">Ações</th></tr></thead>
      <tbody>
        <c:forEach var="pl" items="${planos}">
          <tr>
            <td>${pl.id}</td><td>${pl.nome}</td><td>${pl.preco}</td>
            <td class="acoes">
              <div class="action-row">
                <button class="btn ver" onclick="abrirLerPlano(${pl.id},'${pl.nome}','${pl.preco}')">👁</button>
                <button class="btn editar" onclick="abrirAtualizarPlano(${pl.id},'${pl.nome}','${pl.preco}')">✏️</button>
                <button class="btn excluir" onclick="abrirDeletarPlano(${pl.id})">🗑️</button>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Add -->
<div id="modalPlanoAdd" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Adicionar</h3><div class="sub">Plano</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/InserirPlanoServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>Nome</label><input type="text" name="nome" required></div>
          <div class="form-row"><label>Preco</label><input type="text" name="preco" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalPlanoAdd')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Atualizar -->
<div id="modalPlanoAtualizar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Atualizar</h3><div class="sub">Plano</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/AtualizarPlanoServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>ID</label><input type="text" name="id" id="plan-edit-id" readonly></div>
          <div class="form-row"><label>Nome</label><input type="text" name="nome" id="plan-edit-nome" required></div>
          <div class="form-row"><label>Preco</label><input type="text" name="preco" id="plan-edit-preco" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalPlanoAtualizar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Deletar -->
<div id="modalPlanoDeletar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Deletar</h3><div class="sub">Plano</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/DeletarPlanoServlet" method="post">
        <input type="hidden" name="id" id="plan-del-id">
        <p>Confirmar exclusão do plano?</p>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn" style="background:#d9534f;">Deletar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalPlanoDeletar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function abrirAtualizarPlano(id,nome,preco){
    document.getElementById('plan-edit-id').value = id;
    document.getElementById('plan-edit-nome').value = nome;
    document.getElementById('plan-edit-preco').value = preco;
    abrirModal('modalPlanoAtualizar');
  }
  function abrirDeletarPlano(id){
    document.getElementById('plan-del-id').value = id;
    abrirModal('modalPlanoDeletar');
  }
  function abrirLerPlano(id,nome,preco){
    abrirAtualizarPlano(id,nome,preco);
    document.getElementById('plan-edit-nome').setAttribute('readonly','readonly');
    document.getElementById('plan-edit-preco').setAttribute('readonly','readonly');
  }
</script>

</main></div>
