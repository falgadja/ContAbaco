<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Plano</title>
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
            align-items: center; /* Centraliza verticalmente */
            justify-content:center;
            background:transparent;
            padding: 24px;
        }
        .title{ font-size:32px; font-weight:800; color:var(--cor_falgadja_1); text-align:center; }
        .subtitle{ margin-top:-6px; color:var(--cinza); font-weight:600; font-size:14px; text-align:center; margin-bottom: 22px; }
        .form-inner{ width:100%; max-width:760px; display:flex; flex-direction:column; gap:18px; }
        .form-inner form { display: flex; flex-direction: column; gap: 18px; }
        label{ display:block; font-weight:600; color:var(--cor_falgadja_1); margin-bottom:8px; font-size:14px; }
        input[type="text"], input[type="number"] {
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
        <div class="subtitle">Endereço</div>

        <c:if test="${not empty mensagem}">
            <p class="alert">${mensagem}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/endereco-create" method="post" target="_top">

            <div class="field-group">
                <label for="cep">CEP do endereço:</label>
                <input type="number" step="0.01" id="nome" name="nome" placeholder="Ex: 12344321" value="${param.cep}" required>
            </div>
            <div class="field-group">
                <label for="pais">País:</label>
                <input type="text" id="pais" name="pais" placeholder="Ex: Brasil" value="${param.pais}" required>
            </div>

            <label>Estado:</label>
            <select name="estado" required>
                <option value="">Selecione</option>
                <option value="AC">AC</option>
                <option value="AL">AL</option>
                <option value="AP">AP</option>
                <option value="AM">AM</option>
                <option value="BA">BA</option>
                <option value="CE">CE</option>
                <option value="DF">DF</option>
                <option value="ES">ES</option>
                <option value="GO">GO</option>
                <option value="MA">MA</option>
                <option value="MT">MT</option>
                <option value="MS">MS</option>
                <option value="MG">MG</option>
                <option value="PA">PA</option>
                <option value="PB">PB</option>
                <option value="PR">PR</option>
                <option value="PE">PE</option>
                <option value="PI">PI</option>
                <option value="RJ">RJ</option>
                <option value="RN">RN</option>
                <option value="RS">RS</option>
                <option value="RO">RO</option>
                <option value="RR">RR</option>
                <option value="SC">SC</option>
                <option value="SP">SP</option>
                <option value="SE">SE</option>
                <option value="TO">TO</option>
            </select>

            <div class="field-group">
                <label for="cidade">Cidade:</label>
                <input type="text" id="cidade" name="cidade" placeholder="Ex: São Paulo" value="${param.cidade}" required>
            </div>
            <div class="field-group">
                <label for="bairro">Bairro:</label>
                <input type="text" id="bairro" name="bairro" placeholder="Ex: Freguesia do Ó" value="${param.bairro}" required>
            </div>

            <div class="field-group">
                <label for="rua">Rua:</label>
                <input type="text" id="rua" name="rua" placeholder="Ex: Rua Caraputinga" value="${param.rua}" required>
            </div>

            <div class="field-group">
                <label for="numero">Numero:</label>
                <input type="number" step="0.01" id="numero" name="numero" placeholder="Ex: 765" value="${param.numero}" required>
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