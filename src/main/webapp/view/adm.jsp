<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp"%>

<div class="adicionador">
  <button class="botao-add" onclick="abrirModal('modalAdmAdd')">Adicionar Novo</button>
</div>

<div class="tabela">
  <div class="tabela-container">
    <table id="tabela-adm">
      <thead>
        <tr>
          <th>id_adm</th>
          <th>email</th>
          <th>senha</th>
          <th class="acoes-col">Ações</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="adm" items="${adms}">
          <tr>
            <td>${adm.id}</td>
            <td>${adm.email}</td>
            <td>${adm.senha}</td>
            <td class="acoes">
              <div class="action-row">
                <button class="btn ver" title="Ler" onclick="abrirLerAdm(${adm.id}, '${adm.email}', '${adm.senha}')"><img src="" alt=""></button>
                <button class="btn editar" title="Atualizar" onclick="abrirAtualizarAdm(${adm.id}, '${adm.email}', '${adm.senha}')"><img src="" alt=""></button>
                <button class="btn excluir" title="Deletar" onclick="abrirDeletarAdm(${adm.id})"><img src="" alt=""></button>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<div id="modalAdmAdd" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Adicionar</h3><div class="sub">ADM</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/InserirAdmServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>Email</label><input type="email" name="email" required></div>
          <div class="form-row"><label>Senha</label><input type="password" name="senha" required></div>
        </div>
        <div style="display:flex; gap:8px; justify-content:center; margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalAdmAdd')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div id="modalAdmAtualizar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Atualizar</h3><div class="sub">ADM</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/AtualizarAdmServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>ID</label><input type="text" name="id" id="adm-edit-id" readonly></div>
          <div class="form-row"><label>Email</label><input type="email" name="email" id="adm-edit-email" required></div>
          <div class="form-row"><label>Senha</label><input type="password" name="senha" id="adm-edit-senha" required></div>
        </div>
        <div style="display:flex; gap:8px; justify-content:center; margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalAdmAtualizar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div id="modalAdmDeletar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Deletar</h3><div class="sub">Confirmar exclusão</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/DeletarAdmServlet" method="post">
        <input type="hidden" name="id" id="adm-del-id">
        <p>Tem certeza que deseja deletar este ADM?</p>
        <div style="display:flex; gap:8px; justify-content:center; margin-top:12px;">
          <button type="submit" class="entrar-btn" style="background:red;">Deletar</button>
          <button type="button" class="entrar-btn" style="background:white;color:var(--cor_falgadja_1);" onclick="fecharModal('modalAdmDeletar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function abrirModal(id) { document.getElementById(id).style.display='flex'; }
  function fecharModal(id) { document.getElementById(id).style.display='none'; }

  function abrirAtualizarAdm(id,email,senha){
    document.getElementById('adm-edit-id').value = id;
    document.getElementById('adm-edit-email').value = email;
    document.getElementById('adm-edit-senha').value = senha;
    abrirModal('modalAdmAtualizar');
  }
  function abrirDeletarAdm(id){
    document.getElementById('adm-del-id').value = id;
    abrirModal('modalAdmDeletar');
  }
  function abrirLerAdm(id,email,senha){
    abrirAtualizarAdm(id,email,senha);
    document.getElementById('adm-edit-email').setAttribute('readonly','readonly');
    document.getElementById('adm-edit-senha').setAttribute('readonly','readonly');
  }
</script>

</main></div>