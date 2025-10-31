<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Imports de DAO removidos --%>
<%
    String modalParam = request.getParameter("modal");
    boolean isModal = "1".equals(modalParam);
%>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Pagamento</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <style>
        :root{ --cor_falgadja_1:#1800CC; --cor_falgadja_2:#0C0066; --cinza:#7f7aa7; }
        *{ box-sizing:border-box; font-family:'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, Arial; margin:0; padding:0; }
        html,body{ height:100%; width:100%; background:transparent; color:var(--cor_falgadja_1); }
        .container{
            width:100%;
            height:100%;
            display:flex;
            align-items: flex-start; /* Alinhado ao topo */
            justify-content:center;
            background:transparent;
            padding: 24px 0;
            overflow-y: auto; /* Permite rolagem */
        }
        .title{ font-size:32px; font-weight:800; color:var(--cor_falgadja_1); text-align:center; }
        .subtitle{ margin-top:-6px; color:var(--cinza); font-weight:600; font-size:14px; text-align:center; margin-bottom: 22px; }
        .form-inner{ width:100%; max-width:760px; display:flex; flex-direction:column; gap:18px; padding: 0 18px; }
        .form-inner form { display: flex; flex-direction: column; gap: 18px; }
        label{ display:block; font-weight:600; color:var(--cor_falgadja_1); margin-bottom:8px; font-size:14px; }
        input[type="text"], input[type="number"], input[type="date"], select {
            width:100%; padding:14px 18px; border-radius:12px; border:2px solid rgba(24,0,204,0.18); outline:none; font-size:15px; color:#222; background:#fff; transition:border .12s, box-shadow .12s;
        }
        input::placeholder{ color:rgba(24,0,204,0.22); }
        input:focus{ border-color:var(--cor_falgadja_1); box-shadow:0 8px 18px rgba(24,0,204,0.06); }
        .field-group{ display:block; width:100%; }
        .actions{ display:flex; justify-content:center; gap: 12px; margin-top:6px; }
        .btn{ padding:12px 42px; border-radius:12px; background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; border:0; font-weight:800; cursor:pointer; font-size:16px; box-shadow:0 10px 18px rgba(12,0,102,0.12); }
        .btn-sec { padding:12px 18px; border-radius:12px; background:#fff; color:var(--cor_falgadja_1); border:2px solid rgba(24,0,204,0.18); font-weight:800; cursor:pointer; }
        .alert { background:#fceded; border:1px solid #f7b2b2; color:#b71c1c; padding:10px 14px; border-radius:10px; font-weight:600; width: 100%; }
    </style>
</head>
<body style="background:transparent;">

<div class="container">
    <div class="form-inner">
        <div class="title">Adicionar</div>
        <div class="subtitle">Pagamento</div>

        <c:if test="${not empty mensagem}">
            <p class="alert">${mensagem}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/pagamento-create" method="post" target="_top">

            <div class="field-group">
                <label for="tipoPagto">Tipo de Pagamento</label>
                <select id="tipoPagto" name="tipoPagto" required>
                    <option value="">Selecione o tipo</option>
                    <option value="Cartão Débito ">Cartão Débito</option>
                    <option value="Boleto">Boleto</option>
                    <option value="Dinheiro">Dinheiro</option>
                    <option value="Cartão Crédito">Cartão Crédito</option>
                    <option value="Transferência">Transferência</option>
                </select>
            </div>
            <div class="field-group">
                <label for="total">Total (R$)</label>
                <input type="number" step="0.01" id="total" name="total" placeholder="Ex: 29.99" required>
            </div>
            <div class="field-group">
                <label for="data">Data</label>
                <input type="date" id="data" name="data" required>
            </div>

            <div class="field-group">
                <label for="idEmpresa">Empresa</label>
                <select id="idEmpresa" name="idEmpresa" required>
                    <option value="">Selecione a empresa</option>
                    <c:forEach var="empresa" items="${empresas}">
                        <option value="${empresa.id}">${empresa.nome}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Cadastrar</button>
                <button type="button" class="btn-sec"
                        onclick="try{ parent.document.getElementById('modalClose').click(); }catch(e){ window.close(); }">
                    Cancelar
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>