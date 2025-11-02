<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Pagamento, model.Empresa" %>

<%
    // Recupera os atributos enviados pelo Servlet
    Pagamento pag = (Pagamento) request.getAttribute("pagamentoParaEditar");
    List<Empresa> empresas = (List<Empresa>) request.getAttribute("empresas");
    String mensagem = (String) request.getAttribute("mensagem");
%>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Atualizar Pagamento</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/atualizar.css">

</head>
<body style="background:transparent;">

<div class="container">
    <div class="form-inner">

        <% if (pag == null) { %>
        <div class="title">Pagamento não encontrado</div>
        <div class="actions">
            <button type="button" class="btn-sec"
                    onclick="try{ parent.document.getElementById('modalClose').click(); }catch(e){ window.close(); }">
                Fechar
            </button>
        </div>
        <% } else { %>

        <div class="title">Atualizar</div>
        <div class="subtitle">Pagamento</div>

        <% if (mensagem != null) { %>
        <div class="alert"><%= mensagem %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/pagamento-update" method="post" target="_top">
            <input type="hidden" name="id" value="<%= pag.getId() %>">

            <div class="field-group">
                <label for="tipoPagto">Tipo de Pagamento</label>
                <select id="tipoPagto" name="tipoPagto" required>
                    <option value="">Selecione o tipo</option>
                    <option value="Cartão Débito" <%= "Cartão Débito".equals(pag.getTipoPagto()) ? "selected" : "" %>>Cartão Débito</option>
                    <option value="Cartão Crédito" <%= "Cartão Crédito".equals(pag.getTipoPagto()) ? "selected" : "" %>>Cartão Crédito</option>
                    <option value="Boleto" <%= "Boleto".equals(pag.getTipoPagto()) ? "selected" : "" %>>Boleto</option>
                    <option value="Dinheiro" <%= "Dinheiro".equals(pag.getTipoPagto()) ? "selected" : "" %>>Dinheiro</option>
                    <option value="Transferência" <%= "Transferência".equals(pag.getTipoPagto()) ? "selected" : "" %>>Transferência</option>
                    <option value="PIX" <%= "PIX".equals(pag.getTipoPagto()) ? "selected" : "" %>>PIX</option>

                </select>
            </div>

            <div class="field-group">
                <label for="total">Total (R$)</label>
                <input type="number" step="0.01" id="total" name="total" value="<%= pag.getTotal() %>" required>
            </div>

            <div class="field-group">
                <label for="data">Data</label>
                <input type="date" id="data" name="data" value="<%= pag.getData() %>" required>
            </div>

            <div class="field-group">
                <label for="idEmpresa">Empresa</label>
                <select id="idEmpresa" name="idEmpresa" required>
                    <option value="">Selecione a empresa</option>
                    <% if (empresas != null) {
                        for (Empresa empresa : empresas) { %>
                    <option value="<%= empresa.getId() %>" <%= (empresa.getId() == pag.getIdEmpresa()) ? "selected" : "" %>>
                        <%= empresa.getNome() %>
                    </option>
                    <%  }
                    } %>
                </select>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Salvar alterações</button>
                <button type="button" class="btn-sec"
                        onclick="try{ parent.document.getElementById('modalClose').click(); }catch(e){ window.close(); }">
                    Cancelar
                </button>
            </div>
        </form>
        <% } %>
    </div>
</div>
</body>
</html>
