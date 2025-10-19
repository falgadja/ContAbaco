<%--
  Created by IntelliJ IDEA.
  User: annaabreu-ieg
  Date: 16/10/2025
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Área Restrita - CRUD</title>
    <link rel="icon" href="imagens/logo azul bonito sem fundo.png">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700;800&display=swap');

        :root {
            --cor_falgadja_1: #1800CC;
            --cor_falgadja_2: #0C0066;
            --cor_sair: #b71c1c;
            --branco: #ffffff;
            --cor_degrade: linear-gradient(180deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
        }

        html , body {
            margin:0;
            padding:0;
            height:100%;
            width:100%;
            font-family: 'Poppins', system-ui, -apple-system, 'Segoe UI', Roboto, 'Helvetica Neue', Arial;
            background: var(--branco);
        }

        .esquerda {
            width:100%;
            height:100%;
            display:flex;
            overflow:hidden;
        }

        .sidebar {
            width:255px;
            padding:28px 20px;
            box-sizing:border-box;
            display:flex;
            flex-direction:column;
            gap:18px;
            position:relative;
        }

        .aplicativo {
            display:flex; gap:15px; align-items:center;
        }

        .logo {
            width:56px;
            height:56px;
            border-radius:10px;
            border:2px solid var(--cor_falgadja_1);
            display:flex;
            align-items:center;
            justify-content:center;
            overflow:hidden;
        }

        .logo img {
            display:block;
            max-width:100%;
            max-height:100%;
            width:auto;
            height:auto;
        }

        .titulo_app {
            font-weight:700; color:var(--cor_falgadja_1); font-size:20px;
        }

        .subtitulo_app {
            font-size:12px; color: grey; margin-top:2px;
        }

        .linha{
            height:6px;
            border-radius:30px;
            background: linear-gradient(90deg, var(--cor_falgadja_1), var(--cor_falgadja_2));
            width:80%;
            margin-top:6px;
            margin-bottom:6px;
        }

        .nav {
            display:flex; flex-direction:column; gap:15px; margin-top:10px;
        }

        .botao {
            display:flex;
            align-items:center;
            gap:12px;
            padding:12px 18px;
            border-radius:10px;
            border:2px solid var(--cor_falgadja_1);
            background:transparent;
            color:var(--cor_falgadja_1);
            font-weight:600;
            width:190px;
            box-sizing:border-box;
            cursor:pointer;
            transition:all .18s ease;
            justify-content:flex-start;
            overflow:hidden;
        }

        .selecionado {
            background: var(--cor_degrade);
            color: #fff;
            box-shadow: 0 6px 18px rgba(12,0,102,0.25);
            border-color: transparent;
        }

        .botao:active{
            transform:translateY(1px);
        }

        .icone {
            width:18px;
            height:18px;
            object-fit:contain;
            display:inline-block;
            transition: filter .18s ease;
        }

        .botao.selecionado .icone {
            filter: brightness(0) invert(1);
        }

        .separator {
            width:4px;
            background: var(--cor_falgadja_1);
            height:100%;
            position:relative;
            top:0;
        }

        .main {
            flex:1;
            padding:36px 48px;
            box-sizing:border-box;
            position:relative;
        }

        .titulos{
            display:flex; align-items:center; justify-content:space-between;
        }

        #AR {
            color:var(--cor_falgadja_1);
            font-weight:800; font-size:30px;
            margin:0;
        }

        #CRUD {
            color: grey;
            font-weight:600;
            font-size:15px;
            margin-top: -10px;
        }

        .pesquisar {
            width:380px;
            height:44px;
            border-radius:10px;
            border:2px solid var(--cor_falgadja_1);
            display:flex;
            align-items:center;
            padding:8px 12px;
            box-sizing:border-box;
            gap:10px;
        }
        .pesquisar input{
            flex:1;
            border:0;
            outline:0;
            font-size:14px;
            padding-left:4px;
            color: var(--cor_falgadja_1);
        }

        .pesquisar input::placeholder{
            color: rgba(24,0,204,0.5);
        }

        #lupa{
            width:18px;
            height:18px;
            object-fit:contain;
            display:inline-block;
        }

        .adicionador {
            margin-top:34px;
        }

        .botao-add {
            display:inline-flex;
            align-items:center;
            gap:10px;
            padding:12px 20px;
            border-radius:12px;
            background: var(--cor_degrade);
            color:#fff;
            font-weight:700;
            box-shadow:0 14px 22px rgba(12,0,102,0.12);
            border:0;
            cursor:pointer;
            font-size:15px;
        }

        .icone-add {
            width:14px;
            height:14px;
            object-fit:contain;
            display:inline-block;
        }

        .tabela{
            margin-top:28px;
            height:calc(100% - 150px);
            border-radius:8px;
        }

        .sair {
            margin-top:auto; padding-top:6px; padding-bottom:12px;
        }

        .botao-sair {
            display:inline-flex;
            align-items:center;
            gap:10px;
            padding:10px 18px;
            border-radius:8px;
            background:var(--cor_sair);
            color:#fff;
            font-weight:700;
            border:0;
            cursor:pointer;
            width:190px;
            justify-content:flex-start;
        }

        img{
            max-width:100%; height:auto;
        }
    </style>
</head>
<body>
<div class="esquerda">
    <aside class="sidebar">
        <div class="aplicativo">
            <div class="logo"><img src="imagens/contAbaco cantinho.png" alt="logo ContÁbaco"></div>
            <div>
                <div class="titulo_app">ContÁbaco</div>
                <div class="subtitulo_app">adm</div>
            </div>
        </div>
        <div class="linha"></div>

        <nav class="nav barra-lateral">
            <button class="botao selecionado" onclick="mostrarPagina('adm', this)"><img src="../img/coroaAdm.png" alt="" class="icone"> Adm</button>
            <button class="botao" onclick="mostrarPagina('empresas', this)"><img src="../img/simboloEmpresa.png" alt="" class="icone"> Empresas</button>
            <a href=""><button class="botao" onclick="mostrarPagina('planos', this)"><img src="../img/cardenetaPlanos.png" alt="" class="icone"> Planos</button></a>
            <button class="botao" onclick="mostrarPagina('pagamento', this)"><img src="../img/maoPagamento.png" alt="" class="icone"> Pagamento</button>
        </nav>

        <div class="sair">
            <button class="botao-sair"><img src="imagens/sair.png" alt="" class="icone"> Sair</button>
        </div>
    </aside>

    <div class="separator"></div>

    <section class="main">
        <div class="titulos">
            <div>
                <h1 id="AR">Área Restrita</h1>
                <div id="CRUD"><p>CRUD</p></div>
            </div>

            <div class="pesquisar">
                <img src="imagens/lupa.png" alt="" id="lupa">
                <input type="text" placeholder="Buscar por id, nome, email...">
            </div>
        </div>

        <div class="adicionador">
            <button class="botao-add"><img src="imagens/circulo_mais.png" alt="" class="icone-add"> Adicionar Novo</button>
        </div>

        <div class="tabela">

            <!--ADM-->
            <section id="adm" class="pagina">
                <h2>ADM</h2>
                <p>Conteúdo ADM</p>
            </section>

            <!-- EMPRESAS -->
            <section id="empresas" class="pagina" style="display: none;">
                <h2>Empresas</h2>
                <p>Conteúdo Empresas</p>
            </section>

            <!-- PLANOS -->
            <section id="planos" class="pagina" style="display: none;">
                <h2>Planos</h2>
                <p>Conteúdo Planos</p>
            </section>

            <!-- PAGAMENTO -->
            <section id="pagamento" class="pagina" style="display: none;">
                <h2>Pagamento</h2>
                <p>Conteúdo Pagamento</p>
            </section>
        </div>

    </section>
</div>

<script>
    function mostrarPagina(id, botaoClicado) {
        document.querySelectorAll('.pagina').forEach(sec => sec.style.display = 'none');

        document.getElementById(id).style.display = 'block';

        document.querySelectorAll('.barra-lateral .botao').forEach(btn => btn.classList.remove('selecionado'));
        botaoClicado.classList.add('selecionado');
    }
</script>
</body>
</html>
