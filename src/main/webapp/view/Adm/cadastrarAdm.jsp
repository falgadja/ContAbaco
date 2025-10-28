<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.reflect.Field" %>
<%@ page import="model.Administrador" %>
<%
    String modalParam = request.getParameter("modal");
    boolean isModal = "1".equals(modalParam);
%>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <title>Cadastrar Administrador</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>

    <style>
        :root{ --cor_falgadja_1:#1800CC; --cor_falgadja_2:#0C0066; --cinza:#7f7aa7; }
        *{ box-sizing:border-box; font-family:'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, Arial; margin:0; padding:0; }

        /* O body e o html SÃO transparentes */
        html,body{ height:100%; width:100%; background:transparent; color:var(--cor_falgadja_1); }

        /* O container ocupa 100% da altura e é transparente */
        .container{
            width:100%;
            height:100%;  /* <-- MUDADO DE min-height: 100vh */
            display:flex;
            align-items:center;
            justify-content:center;
            background:transparent;
        }

        /* REMOVIDO .form-card (não precisamos mais dele) */

        .title{ font-size:32px; font-weight:800; color:var(--cor_falgadja_1); text-align:center; }
        .subtitle{ margin-top:-6px; color:var(--cinza); font-weight:600; font-size:14px; text-align:center; margin-bottom: 22px; }

        .form-inner{ width:100%; max-width:760px; display:flex; flex-direction:column; gap:18px; }

        label{ display:block; font-weight:600; color:var(--cor_falgadja_1); margin-bottom:8px; font-size:14px; }
        input[type="text"],input[type="email"],input[type="password"]{
            width:100%; padding:14px 18px; border-radius:12px; border:2px solid rgba(24,0,204,0.18); outline:none; font-size:15px; color:#222; background:#fff; transition:border .12s, box-shadow .12s;
        }
        input::placeholder{ color:rgba(24,0,204,0.22); }
        input:focus{ border-color:var(--cor_falgadja_1); box-shadow:0 8px 18px rgba(24,0,204,0.06); }

        .field-group{ display:block; width:100%; }
        .actions{ display:flex; justify-content:center; margin-top:6px; }
        .btn{ padding:12px 42px; border-radius:12px; background:linear-gradient(180deg,var(--cor_falgadja_1),var(--cor_falgadja_2)); color:#fff; border:0; font-weight:800; cursor:pointer; font-size:16px; box-shadow:0 10px 18px rgba(12,0,102,0.12); }

        .link-voltar{ margin-top:8px; text-align:center; }
        .link-voltar a{ color:var(--cor_falgadja_1); text-decoration:none; font-weight:700; cursor:pointer; }

        /* REMOVIDO .close-inline (não precisamos mais dele) */

        @media (max-width:900px){
            .title{ font-size:24px; }
        }
    </style>
</head>
<body<%= isModal ? " style='background:transparent;'" : "" %>>
<% if (isModal) { %>
<div class="container">

    <div class="form-inner">
        <div class="title">Adicionar</div>
        <div class="subtitle">Administrador</div>

        <form action="${pageContext.request.contextPath}/InserirAdm" method="post" target="_top">
            <%
                Field[] fields = Administrador.class.getDeclaredFields();
                boolean hadPassword = false;
                for (Field f : fields) {
                    String name = f.getName();
                    if ("id".equalsIgnoreCase(name) || "serialVersionUID".equalsIgnoreCase(name)) continue;

                    String label = name.replaceAll("([A-Z])", " $1");
                    label = label.substring(0,1).toUpperCase() + label.substring(1);

                    String type = "text";
                    String lname = name.toLowerCase();
                    boolean isEmail = lname.contains("email");
                    boolean isPass  = (lname.contains("senha") || lname.contains("password") || lname.contains("pass"));
                    if (isEmail) type = "email";
                    if (isPass)  { type = "password"; hadPassword = true; }
            %>
            <div class="field-group">
                <label for="<%= name %>"><%= label %>:</label>
                <input type="<%= type %>" id="<%= name %>" name="<%= name %>" <%= (isEmail || isPass) ? "required" : "" %> placeholder="">
            </div>
            <%
                } // end for
                if (hadPassword) {
            %>
            <div class="field-group">
                <label for="confirmarSenha">Confirmar senha:</label>
                <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="" required>
            </div>
            <%
                }
            %>
            <div class="actions">
                <button type="submit" class="btn">Cadastrar</button>
            </div>
        </form>
    </div>

</div> <% } else { %>
<div class="container" style="min-height:80vh; align-items:flex-start; padding-top:48px;">
    <div class="form-card" style="position:relative;">
        <div class="title">Adicionar</div>
        <div class="subtitle">Administrador</div>

        <div class="form-inner">
            <form action="${pageContext.request.contextPath}/InserirAdm" method="post">
                <%
                    Field[] fields = Administrador.class.getDeclaredFields();
                    boolean hadPassword = false;
                    for (Field f : fields) {
                        String name = f.getName();
                        if ("id".equalsIgnoreCase(name) || "serialVersionUID".equalsIgnoreCase(name)) continue;

                        String label = name.replaceAll("([A-Z])", " $1");
                        label = label.substring(0,1).toUpperCase() + label.substring(1);

                        String type = "text";
                        String lname = name.toLowerCase();
                        boolean isEmail = lname.contains("email");
                        boolean isPass  = (lname.contains("senha") || lname.contains("password") || lname.contains("pass"));
                        if (isEmail) type = "email";
                        if (isPass)  { type = "password"; hadPassword = true; }
                %>
                <div class="field-group">
                    <label for="<%= name %>"><%= label %>:</label>
                    <input type="<%= type %>" id="<%= name %>" name="<%= name %>" <%= (isEmail || isPass) ? "required" : "" %> placeholder="">
                </div>
                <%
                    } // end for
                    if (hadPassword) {
                %>
                <div class="field-group">
                    <label for="confirmarSenha">Confirmar senha:</label>
                    <input type="password" id="confirmarSenha" name="confirmarSenha" placeholder="" required>
                </div>
                <%
                    }
                %>
                <div class="actions">
                    <button type="submit" class="btn">Cadastrar</button>
                </div>
            </form>

            <div class="link-voltar">
                <a href="<%= request.getContextPath() %>/view/Adm/crudAdm.jsp">← Voltar</a>
            </div>
        </div>
    </div>
</div>
<% } %>
</body>
</html>