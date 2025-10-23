<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/includes/header.jsp" %>

<div class="adicionador">
  <button class="botao-add" onclick="abrirModal('modalFuncAdd')">Adicionar Funcionário</button>
</div>

<div class="tabela">
  <div class="tabela-container">
    <table id="tabela-funcionario">
      <thead><tr><th>id_funcionario</th><th>nome</th><th>dt_nascimento</th><th class="acoes-col">Ações</th></tr></thead>
      <tbody>
        <c:forEach var="f" items="${funcionarios}">
          <tr>
            <td>${f.id}</td><td>${f.nome}</td><td>${f.dtNascimento}</td>
            <td class="acoes">
              <div class="action-row">
                <button class="btn ver" onclick="abrirLerFunc(${f.id}, '${f.nome}', '${f.dtNascimento}')">👁</button>
                <button class="btn editar" onclick="abrirAtualizarFunc(${f.id}, '${f.nome}', '${f.dtNascimento}')">✏️</button>
                <button class="btn excluir" onclick="abrirDeletarFunc(${f.id})">🗑️</button>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Add -->
<div id="modalFuncAdd" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Adicionar</h3><div class="sub">Funcionário</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/InserirFuncionarioServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>Nome</label><input type="text" name="nome" required></div>
          <div class="form-row"><label>Data Nasc.</label><input type="date" name="dt_nascimento" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalFuncAdd')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Atualizar -->
<div id="modalFuncAtualizar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Atualizar</h3><div class="sub">Funcionário</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/AtualizarFuncionarioServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>ID</label><input type="text" name="id" id="func-edit-id" readonly></div>
          <div class="form-row"><label>Nome</label><input type="text" name="nome" id="func-edit-nome" required></div>
          <div class="form-row"><label>Data Nasc.</label><input type="date" name="dt_nascimento" id="func-edit-dt" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalFuncAtualizar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Deletar -->
<div id="modalFuncDeletar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Deletar</h3><div class="sub">Funcionário</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/DeletarFuncionarioServlet" method="post">
        <input type="hidden" name="id" id="func-del-id">
        <p>Confirmar exclusão do funcionário?</p>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn" style="background:#d9534f;">Deletar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalFuncDeletar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function abrirAtualizarFunc(id,nome,dt){
    document.getElementById('func-edit-id').value = id;
    document.getElementById('func-edit-nome').value = nome;
    document.getElementById('func-edit-dt').value = dt;
    abrirModal('modalFuncAtualizar');
  }
  function abrirDeletarFunc(id){
    document.getElementById('func-del-id').value = id;
    abrirModal('modalFuncDeletar');
  }
  function abrirLerFunc(id,nome,dt){
    abrirAtualizarFunc(id,nome,dt);
    document.getElementById('func-edit-nome').setAttribute('readonly','readonly');
    document.getElementById('func-edit-dt').setAttribute('readonly','readonly');
  }
</script>

</main></div>