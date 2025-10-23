<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/includes/header.jsp" %>

<div class="tabela">
  <div class="tabela-container">
    <table id="tabela-pagamento">
      <thead><tr><th>id_pagamento</th><th>tipo_pagto</th><th>total</th><th>data_pagto</th><th>id_empresa</th><th>comprovante</th><th class="acoes-col">Ações</th></tr></thead>
      <tbody>
        <c:forEach var="p" items="${pagamentos}">
          <tr>
            <td>${p.id}</td><td>${p.tipoPagto}</td><td>${p.total}</td><td>${p.dataPagto}</td><td>${p.idEmpresa}</td><td>${p.comprovante}</td>
            <td class="acoes">
              <div class="action-row">
                <button class="btn ver" onclick="abrirLerPag(${p.id},'${p.tipoPagto}','${p.total}','${p.dataPagto}',${p.idEmpresa},'${p.comprovante}')">👁</button>
                <button class="btn editar" onclick="abrirAtualizarPag(${p.id},'${p.tipoPagto}','${p.total}','${p.dataPagto}',${p.idEmpresa},'${p.comprovante}')">✏️</button>
                <button class="btn excluir" onclick="abrirDeletarPag(${p.id})">🗑️</button>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<!-- Atualizar Pagamento -->
<div id="modalPagAtualizar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Atualizar</h3><div class="sub">Pagamento</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/AtualizarPagamentoServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>ID</label><input type="text" name="id" id="pag-edit-id" readonly></div>
          <div class="form-row"><label>Tipo Pagto</label><input type="text" name="tipo_pagto" id="pag-edit-tipo" required></div>
          <div class="form-row"><label>Total</label><input type="text" name="total" id="pag-edit-total" required></div>
          <div class="form-row"><label>Data Pagto</label><input type="date" name="data_pagto" id="pag-edit-data" required></div>
          <div class="form-row"><label>ID Empresa</label><input type="number" name="id_empresa" id="pag-edit-ide" required></div>
          <div class="form-row"><label>Comprovante</label><input type="text" name="comprovante" id="pag-edit-comp" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalPagAtualizar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Deletar -->
<div id="modalPagDeletar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Deletar</h3><div class="sub">Pagamento</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/DeletarPagamentoServlet" method="post">
        <input type="hidden" name="id" id="pag-del-id">
        <p>Confirmar exclusão do pagamento?</p>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn" style="background:#d9534f;">Deletar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalPagDeletar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function abrirAtualizarPag(id,tipo,total,data,ide,comp){
    document.getElementById('pag-edit-id').value = id;
    document.getElementById('pag-edit-tipo').value = tipo;
    document.getElementById('pag-edit-total').value = total;
    // ensure date format yyyy-mm-dd if needed
    document.getElementById('pag-edit-data').value = data;
    document.getElementById('pag-edit-ide').value = ide;
    document.getElementById('pag-edit-comp').value = comp;
    abrirModal('modalPagAtualizar');
  }
  function abrirDeletarPag(id){
    document.getElementById('pag-del-id').value = id;
    abrirModal('modalPagDeletar');
  }
  function abrirLerPag(id,tipo,total,data,ide,comp){
    abrirAtualizarPag(id,tipo,total,data,ide,comp);
    ['pag-edit-tipo','pag-edit-total','pag-edit-data','pag-edit-ide','pag-edit-comp'].forEach(i=>document.getElementById(i).setAttribute('readonly','readonly'));
  }
</script>

</main></div>
