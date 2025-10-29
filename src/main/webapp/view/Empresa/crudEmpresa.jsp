<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Empresa" %>
<%@ page import="dao.EmpresaDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Empresas - Área Restrita</title>
  <link rel="icon" href="${pageContext.request.contextPath}/img/logo%20azul%20bonito%20sem%20fundo%202%20(1).png">

  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>

  <style>
    /* ======== ESTILIZAÇÃO DO ENTORNO ======== */
    :root { --cor_falgadja_1:#1800CC; --cor_falgadja_2:#0C0066; --cor_sair:#b71c1c; --branco:#ffffff; }
    *{ box-sizing:border-box; margin:0; padding:0; font-family:'Poppins',system-ui,-apple-system,'Segoe UI',Roboto,Arial; }
    html,body{ height:100%; width:100%; background:var(--branco); color:var(--cor_falgadja_1); }

    .esquerda{ display:flex; height:100vh; width:100%; overflow:hidden; }
    .sidebar{ width:255px; padding:28px 20px; display:flex; flex-direction:column; gap:18px; }
    .aplicativo{ display:flex; gap:15px; align-items:center; }
    .logo{ width:56px; height:56px; border-radius:10px; border:2px solid var(--cor_falgadja_1); display:flex; align-items:center; justify-content:center; overflow:hidden; }
    .logo img{ max-width:100%; max-height:100%; display:block; }
    .titulo_app{ font-weight:700; color:var(--cor_falgadja_1); font-size:20px; }
    .subtitulo_app{ font-size:12px; color:grey; margin-top:2px; }

    .linha{ height:6px; border-radius:30px; background:linear-gradient(90deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); width:80%; margin:6px 0; }

    .nav{ display:flex; flex-direction:column; gap:12px; margin-top:10px; }
    .botao{ display:flex; align-items:center; gap:12px; padding:12px 18px; border-radius:10px; border:2px solid var(--cor_falgadja_1); background:transparent; color:var(--cor_falgadja_1); font-weight:600; width:190px; cursor:pointer; text-decoration:none; }
    .botao.selecionado{ background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; border-color:transparent; box-shadow:0 6px 18px rgba(12,0,102,0.25); }

    .separator{ width:4px; background:var(--cor_falgadja_1); }

    .main{ flex:1; padding:36px 48px; overflow:auto; }

    .titulos{ display:flex; align-items:center; justify-content:space-between; gap:12px; }
    #AR{ font-size:30px; font-weight:800; color:var(--cor_falgadja_1); }
    #CRUD{ color:grey; font-weight:600; margin-top:-10px; }

    .adicionador{ margin-top:28px; }
    .botao-add{ padding:12px 18px; border-radius:12px; background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; font-weight:700; border:0; cursor:pointer; display:inline-flex; align-items:center; gap:10px; text-decoration:none; }

    .sair{ margin-top:auto;}
    .botao-sair{padding:12px 18px; border-radius:12px; background:var(--cor_sair); color:white; border:0; cursor:pointer; font-weight:700; width:100%; display:inline-flex; align-items:center; gap:10px;}

    /* === ESTILOS DO FILTRO (ADICIONADOS) === */
    .filtros {
      margin-top: 24px;
      margin-bottom: -10px;
      padding: 16px;
      background: #fafaff;
      border-radius: 12px;
      border: 2px solid rgba(24, 0, 204, 0.1);
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      gap: 16px;
      width: calc(100% - 32px);
      max-width: 1180px;
      margin-left: auto;
      margin-right: auto;
    }
    .filtros label {
      font-weight: 600;
      color: var(--cor_falgadja_1);
      font-size: 14px;
      margin-right: -8px;
    }
    .filtros input[type="text"],
    .filtros select {
      padding: 10px 14px;
      border-radius: 10px;
      border: 2px solid rgba(24, 0, 204, 0.18);
      outline: none;
      font-size: 14px;
      color: #222;
    }
    .filtros input[type="text"] {
      width: 280px; /* Tamanho customizado */
    }
    .filtros button {
      padding: 10px 24px;
      border-radius: 10px;
      background: linear-gradient(180deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
      color: #fff;
      font-weight: 700;
      border: 0;
      cursor: pointer;
      font-size: 14px;
      margin-left: auto;
    }
    .mensagem {
      margin: 16px auto 0 auto;
      width: calc(100% - 32px);
      max-width: 1180px;
      color: var(--cor_falgadja_1);
      font-weight: 600;
      padding: 10px 16px;
      background: #f0f4ff;
      border: 1px solid #c8d3ff;
      border-radius: 10px;
      text-align: center;
    }
    /* === FIM DOS ESTILOS DO FILTRO === */

    /* ======== ESTILO DA TABELA (mantido) ======== */
    .tabela {
      margin-top:20px;
      border:3px solid rgba(24,0,204,0.95);
      border-radius:14px;
      padding:10px;
      width: calc(100% - 32px);
      max-width:1180px;
      margin-left:auto; margin-right:auto;
      box-shadow:0 10px 22px rgba(12,0,102,0.08);
      position:relative;
      background:#fff;
      overflow: visible;
    }

    .tabela-container {
      width:100%;
      max-height:calc(100vh - 260px);
      overflow-y:auto;
      overflow-x:auto;
      padding:10px;
    }
    .tabela-container::-webkit-scrollbar{ height:10px; width:10px; }
    .tabela-container::-webkit-scrollbar-thumb{ background: linear-gradient(180deg, rgba(24,0,204,0.2), rgba(12,0,102,0.2)); border-radius:10px; }

    .empresa-style table { width:auto; table-layout:auto; border-collapse:collapse; font-size:14px; white-space:nowrap; }
    .empresa-style thead th, .empresa-style tbody td {
      padding:14px 16px; overflow:visible; text-overflow:clip; white-space:nowrap;
      font-weight:600; text-align:center;
    }
    .empresa-style tbody tr:nth-child(even){ background:#fafaff; }
    .empresa-style tbody tr:hover{ background:rgba(24,0,204,0.04); }

    .empresa-style th.acoes-col, .empresa-style td.acoes {
      position:sticky; right:0; background:#fff; box-shadow:-8px 0 12px rgba(12,0,102,0.06);
      min-width:170px; padding:0 8px; vertical-align:middle; text-align:center; overflow:visible; white-space:nowrap;
    }

    .empresa-style td.acoes .btn {
      display:inline-flex;
      align-items:center;
      justify-content:center;
      width:32px;
      height:32px;
      border-radius:6px;
      border:1.5px solid var(--cor_falgadja_1);
      background:transparent;
      color: var(--cor_falgadja_1);
      cursor:pointer;
      transition:.2s;
      margin: 0 4px;
    }
    .empresa-style td.acoes .btn:hover {
      background: var(--cor_falgadja_1);
      color: #fff;
    }

    /* === ESTILOS DO MODAL (mantido) === */
    .in-table-modal {
      position: fixed;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      width: min(80%, 900px);
      min-height: 460px;
      max-height: calc(100% - 60px);
      border-radius: 18px;
      border: 3px solid rgba(24, 0, 204, 0.95);
      background: #fff;
      box-shadow: 0 18px 40px rgba(12, 0, 102, 0.12);
      z-index: 1000;
      display: none;
      overflow: hidden;
      padding: 24px 36px;
    }
    .in-table-modal.open { display: block; }
    .in-table-modal .modal-close {
      position: absolute;
      top: 10px;
      right: 10px;
      width: 44px;
      height: 44px;
      border-radius: 50%;
      background: #fff;
      border: 2px solid rgba(24, 0, 204, 0.15);
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      z-index: 60;
      box-shadow: 0 6px 14px rgba(24, 0, 204, 0.08);
    }
    .in-table-modal .modal-close i { color: var(--cor_falgadja_1); }
    .in-table-modal iframe {
      width: 100%;
      height: 100%;
      border: 0;
      display: block;
      background: transparent;
      min-height: 460px;
    }
    @media (max-width:920px) {
      .in-table-modal {
        width: calc(100% - 32px);
        left: 50%;
        transform: translate(-50%, -45%);
      }
    }
  </style>
</head>
<body>
<div class="esquerda">
  <div class="sidebar">
    <div class="aplicativo">
      <div class="logo">
        <img src="${pageContext.request.contextPath}/img/CelularFotoContabaco.png" alt="logo">
      </div>
      <div>
        <div class="titulo_app">Contábaco</div>
        <div class="subtitulo_app">adm</div>
      </div>
    </div>

    <div class="linha"></div>

    <div class="nav">
      <a href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp" class="botao"><i class="fa-solid fa-crown"></i> Adm</a>
      <a href="<%= request.getContextPath() %>/view/Empresa/crudEmpresa.jsp" class="botao selecionado"><i class="fa-solid fa-building"></i> Empresas</a>
      <a href="<%= request.getContextPath() %>/view/Funcionario/crudFuncionario.jsp" class="botao"><i class="fa-solid fa-user-tie"></i> Funcionários</a>
      <a href="<%= request.getContextPath() %>/view/Plano/crudPlano.jsp" class="botao"><i class="fa-solid fa-clipboard-list"></i> Planos</a>
      <a href="<%= request.getContextPath() %>/view/Pagamento/crudPagamento.jsp" class="botao"><i class="fa-solid fa-credit-card"></i> Pagamento</a>
    </div>

    <div class="sair">
      <button class="botao-sair" onclick="location.href='${pageContext.request.contextPath}/view/Login/login.jsp'">
        <i class="fa-solid fa-right-from-bracket"></i> Sair
      </button>
    </div>
  </div>

  <div class="main">
    <div class="titulos">
      <div>
        <div id="AR">Área Restrita</div>
        <div id="CRUD">CRUD</div>
      </div>
    </div>

    <div class="adicionador">
      <button id="openModal" class="botao-add" type="button">
        <i class="fa-solid fa-plus"></i> Adicionar Novo
      </button>
    </div>

    <form action="${pageContext.request.contextPath}/BuscarEmpresaServlet" method="get" class="filtros">
      <label for="nome">Buscar por Nome:</label>
      <input type="text" name="nome" id="nome" placeholder="Digite o nome da empresa">

      <label for="idPlano">Plano:</label>
      <select name="idPlano" id="idPlano">
        <option value="">-- Todos --</option>
        <option value="1">Mensal</option>
        <option value="2">Anual</option>
        <option value="3">Premium</option>
      </select>

      <label for="tipoOrdenacao">Ordenar por:</label>
      <select name="tipoOrdenacao" id="tipoOrdenacao">
        <option value="">-- Nenhum --</option>
        <option value="idCrescente">ID Crescente</option>
        <option value="idDecrescente">ID Decrescente</option>
        <option value="nomeAz">Nome (A-Z)</option>
        <option value="nomeZa">Nome (Z-A)</option>
      </select>

      <button type="submit">Filtrar</button>
    </form>

    <c:if test="${not empty mensagem}">
      <p class="mensagem">${mensagem}</p>
    </c:if>
    <div class="tabela empresa-style">
      <div class="tabela-container">

        <% // === LÓGICA DA TABELA CORRIGIDA ===
          // 1. Tenta pegar a lista "empresas" que o servlet de filtro (BuscarEmpresaServlet) pode ter enviado.
          List<Empresa> lista = (List<Empresa>) request.getAttribute("empresas");

          // 2. Se o servlet NÃO enviou a lista (ex: primeiro acesso à página),
          //    então nós mesmos buscamos a lista completa.
          if (lista == null) {
            EmpresaDAO dao = new EmpresaDAO();
            lista = dao.listar();
          }

          // 3. Agora, itera sobre a lista (seja ela a filtrada ou a completa)
          if (lista != null && !lista.isEmpty()) {
        %>
        <table>
          <thead>
          <tr>
            <th>id_empresa</th>
            <th>nome</th>
            <th>cnpj</th>
            <th>email</th>
            <th>qtd_funcionários</th>
            <th>plano</th>
            <th class="acoes-col">Ações</th>
          </tr>
          </thead>
          <tbody>
          <%
            for (Empresa e : lista) {
              String plano;
              switch(e.getIdPlano()) {
                case 1: plano = "Mensal"; break;
                case 2: plano = "Anual"; break;
                case 3: plano = "Premium"; break;
                default: plano = "Teste";
              }
          %>
          <tr>
            <td><%= e.getId() %></td>
            <td><%= e.getNome() %></td>
            <td><%= e.getCnpj() %></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getQntdFuncionarios() %></td>
            <td><%= plano %></td>
            <td class="acoes">

              <button class="btn" title="Editar"
                      onclick="abrirModalEditar(<%= e.getId() %>)">
                <i class="fa-solid fa-pen"></i>
              </button>

              <button class="btn" title="Excluir"
                      onclick="if(confirm('Deseja excluir esta empresa?')) window.location.href='<%= request.getContextPath() %>/DeletarEmpresaServlet?id=<%= e.getId() %>'">
                <i class="fa-solid fa-trash"></i>
              </button>
            </td>
          </tr>
          <% } // Fim do for %>
          </tbody>
        </table>
        <%
        } else { // Fim do if (lista não está vazia)
        %>
        <p style="padding:10px 6px; color:#666;">Nenhuma empresa cadastrada (ou encontrada no filtro).</p>
        <% } %>
      </div>

      <div class="in-table-modal" id="inTableModal" role="dialog" aria-hidden="true"
           aria-label="Formulário da Empresa">
        <button class="modal-close" id="modalClose" title="Fechar"><i
                class="fa-solid fa-xmark"></i></button>
        <iframe id="modalFrame" src="" name="modalFrame"
                title="Formulário Cadastrar/Atualizar Empresa"></iframe>
      </div>

    </div>
  </div>
</div>

<script>
  (function () {
    // Define as variáveis do modal
    const modal = document.getElementById('inTableModal');
    const frame = document.getElementById('modalFrame');
    const openBtn = document.getElementById('openModal'); // Botão "Adicionar Novo"
    const closeBtn = document.getElementById('modalClose'); // Botão 'X' de fechar

    if (!modal || !frame || !openBtn || !closeBtn) {
      console.error('Elementos do modal não encontrados. Verifique os IDs: inTableModal, modalFrame, openModal, modalClose');
      return;
    }

    // --- Função para Abrir o Modal de Adicionar ---
    openBtn.addEventListener('click', function () {
      frame.src = '<%= request.getContextPath() %>/view/Empresa/cadastrarEmpresa.jsp?modal=1';
      modal.classList.add('open');
      modal.setAttribute('aria-hidden', 'false');
    });

    // --- Função para Fechar o Modal ---
    function closeModal() {
      modal.classList.remove('open');
      modal.setAttribute('aria-hidden', 'true');
      frame.src = 'about:blank';

      // Recomendo descomentar a linha abaixo.
      // Ela faz a tabela atualizar sozinha depois que você cadastra, edita ou filtra.
      // window.location.reload();
    }

    closeBtn.addEventListener('click', closeModal);
    document.addEventListener('keydown', function (e) {
      if (e.key === 'Escape' && modal.classList.contains('open')) {
        closeModal();
      }
    });

    // --- Função para Abrir o Modal de Editar ---
    window.abrirModalEditar = function (id) {
      if (!id) return;
      frame.src = '<%= request.getContextPath() %>/view/Empresa/atualizarEmpresa.jsp?id=' + id + '&modal=1';
      modal.classList.add('open');
      modal.setAttribute('aria-hidden', 'false');
    };

  })();
</script>

</body>
</html>