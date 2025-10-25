<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página de Cadastro e Login</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        /* --- Reset e Corpo --- */
        * { margin:0; padding:0; box-sizing:border-box; }
        body {
            font-family: 'Poppins', sans-serif;
            display:flex;
            justify-content:center;
            align-items:center;
            min-height:100vh;
            background: linear-gradient(to right, #304FFE, #0c1a4b);
            padding: 40px;
        }

        /* --- Container Principal --- */
        .main-container {
            display: flex;
            width: 100%;
            max-width: 1290px;
            min-height: 600px;
            border-radius: 50px;
            background-color: #fff;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            overflow: hidden;
            flex-direction: column;
            position: relative;
        }

        @media (min-width:768px) { .main-container { flex-direction: row; } }

        /* --- Painéis --- */
        .panel {
            display:flex;
            justify-content:center;
            align-items:center;
            padding:40px;
            width:100%;
        }
        .panel .content {
            display:flex;
            flex-direction:column;
            align-items:center;
            text-align:center;
            width:100%;
            max-width:320px;
        }

        .left-panel { background-color:#fff; order:2; }
        @media (min-width:768px) { .left-panel { flex:2; order:1; } }

        .left-panel h1 { font-size:clamp(1.8rem,5vw,2.2rem); font-weight:700; margin-bottom:15px; color:#304FFE; }
        .left-panel p { font-size:clamp(0.9rem,2.5vw,1rem); margin-bottom:30px; color:#304FFE; }
        .left-panel .logo { width:90px; margin-bottom:30px; }

        .right-panel { background-color:#fff; color:#333; padding:40px 20px; order:1; }
        @media (min-width:768px) { .right-panel { flex:3; padding:40px 60px; order:2; } }
        .right-panel h1 { font-size:clamp(1.6rem,4vw,1.8rem); font-weight:600; color:#304FFE; margin-bottom:25px; }

        /* --- Formulário --- */
        form { width:100%; }
        .form-group { margin-bottom:20px; text-align:left; width:100%; }
        .form-group label { display:block; font-size:0.9rem; color:rgb(20,44,201); margin-bottom:8px; }
        .form-group input { width:100%; padding:12px 15px; border:2px solid #304FFE; border-radius:12px; font-size:1rem; outline:none; background-color:#fff; color:#304FFE; }
        .form-group input::placeholder { color:#b0b0e0; opacity:0.8; }

        /* --- Botões --- */
        .btn { width:100%; padding:12px; border:none; border-radius:12px; font-size:1rem; font-weight:600; cursor:pointer; transition:all 0.3s ease; text-align:center; }
        .btn-outline { background:#fff; color:#304FFE; border:2px solid #304FFE; }
        .btn-outline:hover { background:#f0f0f0; border-color:#0019CB; }
        .btn-solid { background:#304FFE; color:#fff; margin-top:10px; }
        .btn-solid:hover { background:#0019CB; }

        /* --- Links --- */
        .link-sm { display:block; margin-top:15px; font-size:0.85rem; text-decoration:underline; color:#304FFE; }
        .link-sm:hover { color:#0019CB; }
    </style>
</head>
<body>
<div class="main-container">
    <!-- Painel de Cadastro -->
    <div class="panel right-panel">
        <div class="content">
            <h1>Faça o cadastro da sua empresa!</h1>
            <form action="${pageContext.request.contextPath}/InserirEmpresa" method="post">
            <div class="form-group">
                    <label for="empresa">Nome da Empresa</label>
                    <input type="text" id="empresa" name="nomeEmpresa" placeholder="Digite o nome da sua empresa" required>
                </div>
                <div class="form-group">
                    <label for="cnpj">CNPJ</label>
                    <input type="text" id="cnpj" name="cnpj" placeholder="00.000.000/0000-00" required minlength="9">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="emailEmpresa" placeholder="seuemail@exemplo.com" required>
                </div>
                <div class="form-group">
                    <label for="senha">Senha</label>
                    <input type="password" id="senha" name="senha" placeholder="Crie uma senha forte" required minlength="8">
                </div>
                <div class="form-group">
                    <label for="confirme-senha">Confirme a sua senha</label>
                    <input type="password" id="confirme-senha" name="confirmarSenha" placeholder="Repita a senha" required minlength="8">
                </div>

                <!-- Planos (radio: só um pode escolher) -->
                <div class="form-group">
                    <label>Escolha o plano</label><br>
                    <input type="radio" id="plano1" name="idPlano" value="1" required>
                    <label for="plano1">Plano Básico</label><br>
                    <input type="radio" id="plano2" name="idPlano" value="2">
                    <label for="plano2">Plano Intermediário</label><br>
                    <input type="radio" id="plano3" name="idPlano" value="3">
                    <label for="plano3">Plano Premium</label>
                </div>

                <!-- Quantidade de funcionários -->
                <div class="form-group">
                    <label for="qntdFuncionarios">Quantidade de funcionários</label>
                    <input type="number" id="qntdFuncionarios" name="qntdFuncionarios" min="1" placeholder="Digite a quantidade">
                </div>

                <button type="submit" class="btn btn-solid">Cadastrar</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
