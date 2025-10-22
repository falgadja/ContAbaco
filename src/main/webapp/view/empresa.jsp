<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/includes/header.jsp" %>

<div class="adicionador">
  <button class="botao-add" onclick="abrirModal('modalEmpresaAdd')">Adicionar Novo</button>
</div>

<div class="tabela">
  <div class="tabela-container">
    <table id="tabela-empresas">
      <thead>
        <tr>
          <th>id</th><th>cnpj</th><th>nome</th><th>email</th><th>senha</th><th>id_plano</th><th>qntd_funcionarios</th><th class="acoes-col">Ações</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="e" items="${empresas}">
          <tr>
            <td>${e.id}</td><td>${e.cnpj}</td><td>${e.nome}</td><td>${e.email}</td><td>${e.senha}</td><td>${e.idPlano}</td><td>${e.qntdFuncionarios}</td>
            <td class="acoes">
              <div class="action-row">
                <button class="btn ver" onclick="abrirLerEmpresa(${e.id},'${e.cnpj}','${e.nome}','${e.email}','${e.senha}',${e.idPlano},${e.qntdFuncionarios})"><img src="" alt=""></button>
                <button class="btn editar" onclick="abrirAtualizarEmpresa(${e.id},'${e.cnpj}','${e.nome}','${e.email}','${e.senha}',${e.idPlano},${e.qntdFuncionarios})"><img src="" alt=""></button>
                <button class="btn excluir" onclick="abrirDeletarEmpresa(${e.id})"><img src="" alt=""></button>
              </div>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<div id="modalEmpresaAdd" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Adicionar</h3><div class="sub">Empresa</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/InserirEmpresaServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>CNPJ</label><input type="text" name="cnpj" required></div>
          <div class="form-row"><label>Nome</label><input type="text" name="nome" required></div>
          <div class="form-row"><label>Email</label><input type="email" name="email" required></div>
          <div class="form-row"><label>Senha</label><input type="password" name="senha" required></div>
          <div class="form-row"><label>ID Plano</label><input type="number" name="id_plano" required></div>
          <div class="form-row"><label>Qtd Funcionários</label><input type="number" name="qntd_funcionarios" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalEmpresaAdd')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div id="modalEmpresaAtualizar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Atualizar</h3><div class="sub">Empresa</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/AtualizarEmpresaServlet" method="post">
        <div class="form-grid">
          <div class="form-row"><label>ID</label><input type="text" name="id" id="emp-edit-id" readonly></div>
          <div class="form-row"><label>CNPJ</label><input type="text" name="cnpj" id="emp-edit-cnpj" required></div>
          <div class="form-row"><label>Nome</label><input type="text" name="nome" id="emp-edit-nome" required></div>
          <div class="form-row"><label>Email</label><input type="email" name="email" id="emp-edit-email" required></div>
          <div class="form-row"><label>Senha</label><input type="password" name="senha" id="emp-edit-senha" required></div>
          <div class="form-row"><label>ID Plano</label><input type="number" name="id_plano" id="emp-edit-idplano" required></div>
          <div class="form-row"><label>Qtd Funcionários</label><input type="number" name="qntd_funcionarios" id="emp-edit-qtd" required></div>
        </div>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn">Salvar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalEmpresaAtualizar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<div id="modalEmpresaDeletar" class="modal-overlay">
  <div class="modal">
    <div class="modal-header"><h3>Deletar</h3><div class="sub">Empresa</div></div>
    <div class="inner">
      <form action="${pageContext.request.contextPath}/DeletarEmpresaServlet" method="post">
        <input type="hidden" name="id" id="emp-del-id">
        <p>Confirma deletar esta empresa?</p>
        <div style="display:flex;gap:8px;justify-content:center;margin-top:12px;">
          <button type="submit" class="entrar-btn" style="background:#d9534f;">Deletar</button>
          <button type="button" class="entrar-btn" style="background:#ccc;color:#333;" onclick="fecharModal('modalEmpresaDeletar')">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  function abrirModal(id){ document.getElementById(id).style.display='flex'; }
  function fecharModal(id){ document.getElementById(id).style.display='none'; }

  function abrirAtualizarEmpresa(id, cnpj, nome, email, senha, idplano, qtd){
    document.getElementById('emp-edit-id').value = id;
    document.getElementById('emp-edit-cnpj').value = cnpj;
    document.getElementById('emp-edit-nome').value = nome;
    document.getElementById('emp-edit-email').value = email;
    document.getElementById('emp-edit-senha').value = senha;
    document.getElementById('emp-edit-idplano').value = idplano;
    document.getElementById('emp-edit-qtd').value = qtd;
    abrirModal('modalEmpresaAtualizar');
  }
  function abrirDeletarEmpresa(id){
    document.getElementById('emp-del-id').value = id;
    abrirModal('modalEmpresaDeletar');
  }
  function abrirLerEmpresa(id, cnpj, nome, email, senha, idplano, qtd){
    abrirAtualizarEmpresa(id, cnpj, nome, email, senha, idplano, qtd);
    document.getElementById('emp-edit-cnpj').setAttribute('readonly','readonly');
    document.getElementById('emp-edit-nome').setAttribute('readonly','readonly');
    document.getElementById('emp-edit-email').setAttribute('readonly','readonly');
    document.getElementById('emp-edit-senha').setAttribute('readonly','readonly');
    document.getElementById('emp-edit-idplano').setAttribute('readonly','readonly');
    document.getElementById('emp-edit-qtd').setAttribute('readonly','readonly');
  }
</script>

</main></div>