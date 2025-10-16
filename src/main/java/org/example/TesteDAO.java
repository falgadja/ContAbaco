package org.example;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import conexao.Conexao;
import dao.EmpresaDAO;
import dao.EnderecoDAO;
import dao.FechamentoAvariaDAO;
import dao.FechamentoTurnoDAO;
import model.Empresa;
import model.Endereco;
import model.FechamentoAvaria;
import model.FechamentoTurno;

public class  TesteDAO{
    public static void main(String[] args) {

        // CONECTAR COM O BANCO
        Conexao conexao = new Conexao();
        Connection con = conexao.conectar();

        // -------------------------- TESTAR EMPRESA --------------------------
        EmpresaDAO empresaDAO = new EmpresaDAO();

        // 1 - INSERIR EMPRESA
        Empresa empresaTeste = new Empresa(9, "12345678000190", "algadja Ltda", "contato@algadja.com", "senha123", 1, 50);
        int empresaInserida = empresaDAO.inserir(empresaTeste);
        if (empresaInserida == 1) System.out.println("EMPRESA INSERIDA COM SUCESSO! ID: " + empresaTeste.getId());
        else System.out.println("ERRO AO INSERIR EMPRESA.");

        // 2 - LISTAR TODAS AS EMPRESAS
        List<Empresa> listaEmpresas = empresaDAO.listar();
        System.out.println("\nLISTA DE TODAS AS EMPRESAS:");
        if (listaEmpresas != null) {
            for (Empresa e : listaEmpresas) {
                System.out.println(e.getId() + " - " + e.getNome() + " | CNPJ: " + e.getCnpj());
            }
        }

        // 3 - BUSCAR EMPRESA POR ID
        Empresa empresaBuscada = empresaDAO.buscarPorId(empresaTeste.getId());
        if (empresaBuscada != null) System.out.println("\nEMPRESA BUSCADA POR ID: " + empresaBuscada.getNome());

        // 4 - BUSCAR EMPRESA POR NOME
        Empresa empresaPorNome = empresaDAO.buscarPorNome("algadja Ltda");
        if (empresaPorNome != null) System.out.println("EMPRESA ENCONTRADA PELO NOME: " + empresaPorNome.getNome());

        // 5 - ATUALIZAR EMPRESA
        empresaTeste.setNome("Falgadja Tecnologia Ltda");
        empresaTeste.setQntdFuncionarios(60);
        int empresaAtualizada = empresaDAO.atualizar(empresaTeste);
        if (empresaAtualizada == 1) System.out.println("\nEMPRESA ATUALIZADA COM SUCESSO!");

        // 6 - DELETAR EMPRESA
        int empresaDeletada = empresaDAO.deletar(empresaTeste.getId());
        if (empresaDeletada == 1) System.out.println("\nEMPRESA DELETADA COM SUCESSO!");

        // -------------------------- TESTAR ENDEREÇO --------------------------
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        // 1 - INSERIR ENDEREÇO
        Endereco enderecoTeste = new Endereco(0, "Brasil", "São Paulo", "São Paulo", "Centro", "Rua A", 123, "01000-000", 1);
        int enderecoInserido = enderecoDAO.inserirEndereco(enderecoTeste);
        if (enderecoInserido == 1) System.out.println("\nENDEREÇO INSERIDO COM SUCESSO!");
        else System.out.println("\nERRO AO INSERIR ENDEREÇO.");

        // 2 - LISTAR TODOS OS ENDEREÇOS
        List<Endereco> listaEnderecos = enderecoDAO.listarTodos();
        System.out.println("\nLISTA DE TODOS OS ENDEREÇOS:");
        if (listaEnderecos != null) {
            for (Endereco e : listaEnderecos) {
                System.out.println(e.getId() + " - " + e.getRua() + ", " + e.getBairro() + " | " + e.getCidade());
            }
        }

        // 3 - BUSCAR ENDEREÇO POR ID
        Endereco enderecoBuscado = enderecoDAO.buscarPorId(enderecoTeste.getId());
        if (enderecoBuscado != null) System.out.println("\nENDEREÇO BUSCADO POR ID: " + enderecoBuscado.getRua() + ", " + enderecoBuscado.getCidade());

        // 4 - BUSCAR ENDEREÇO POR PAÍS
        List<Endereco> enderecosBrasil = enderecoDAO.buscarPorPais("Brasil");
        System.out.println("\nENDEREÇOS NO BRASIL:");
        if (enderecosBrasil != null) {
            for (Endereco e : enderecosBrasil) {
                System.out.println(e.getId() + " - " + e.getRua() + ", " + e.getCidade());
            }
        }

        // 5 - ATUALIZAR ENDEREÇO
        enderecoTeste.setRua("Rua B");
        enderecoTeste.setNumero(456);
        int enderecoAtualizado = enderecoDAO.atualizarEndereco(enderecoTeste);
        if (enderecoAtualizado == 1) System.out.println("\nENDEREÇO ATUALIZADO COM SUCESSO!");

        // 6 - DELETAR ENDEREÇO
        int enderecoDeletado = enderecoDAO.deletarEndereco(enderecoTeste.getId());
        if (enderecoDeletado == 1) System.out.println("\nENDEREÇO DELETADO COM SUCESSO!");

        // -------------------------- TESTAR FECHAMENTO DE TURNO --------------------------
        FechamentoTurnoDAO fechamentoTurnoDAO = new FechamentoTurnoDAO();

        // 1 - INSERIR FECHAMENTO DE TURNO
        FechamentoTurno turnoTeste = new FechamentoTurno(1, 1, LocalDate.now(), 100, 2);
        int turnoInserido = fechamentoTurnoDAO.inserir(turnoTeste);
        if (turnoInserido == 1) System.out.println("\nFECHAMENTO DE TURNO INSERIDO COM SUCESSO! ID: " + turnoTeste.getId());

        // 2 - LISTAR TODOS OS FECHAMENTOS DE TURNO
        List<FechamentoTurno> listaTurnos = fechamentoTurnoDAO.listar();
        System.out.println("\nLISTA DE TODOS OS FECHAMENTOS DE TURNO:");
        if (listaTurnos != null) {
            for (FechamentoTurno ft : listaTurnos) {
                System.out.println(ft.getId() + " - Lote: " + ft.getLote() + " | Data: " + ft.getData());
            }
        }

        // 3 - BUSCAR POR ID
        FechamentoTurno turnoBuscado = fechamentoTurnoDAO.buscarPorId(turnoTeste.getId());
        if (turnoBuscado != null) System.out.println("\nFECHAMENTO DE TURNO BUSCADO POR ID: Lote " + turnoBuscado.getLote());

        // 4 - ATUALIZAR FECHAMENTO DE TURNO
        turnoTeste.setLote(101);
        int turnoAtualizado = fechamentoTurnoDAO.atualizar(turnoTeste);
        if (turnoAtualizado == 1) System.out.println("\nFECHAMENTO DE TURNO ATUALIZADO COM SUCESSO!");

        // 5 - DELETAR FECHAMENTO DE TURNO
        int turnoDeletado = fechamentoTurnoDAO.deletar(turnoTeste.getId());
        if (turnoDeletado == 1) System.out.println("\nFECHAMENTO DE TURNO DELETADO COM SUCESSO!");

        // -------------------------- TESTAR FECHAMENTO AVARIA --------------------------
        FechamentoAvariaDAO fechamentoAvariaDAO = new FechamentoAvariaDAO();

        // 1 - INSERIR FECHAMENTO AVARIA
        FechamentoAvaria avariaTeste = new FechamentoAvaria(1, 1, 1, 5);
        int avariaInserida = fechamentoAvariaDAO.inserir(avariaTeste);
        if (avariaInserida == 1) System.out.println("\nFECHAMENTO AVARIA INSERIDO COM SUCESSO! ID: " + avariaTeste.getId());

        // 2 - LISTAR TODOS OS FECHAMENTOS AVARIA
        List<FechamentoAvaria> listaAvarias = fechamentoAvariaDAO.listar();
        System.out.println("\nLISTA DE TODOS OS FECHAMENTOS AVARIA:");
        if (listaAvarias != null) {
            for (FechamentoAvaria fa : listaAvarias) {
                System.out.println(fa.getId() + " - ID Fechamento: " + fa.getIdFechamento() + " | ID Avaria: " + fa.getIdAvaria() + " | Quantidade: " + fa.getQuantidade());
            }
        }

        // 3 - BUSCAR POR ID
        FechamentoAvaria avariaBuscada = fechamentoAvariaDAO.buscarPorId(avariaTeste.getId());
        if (avariaBuscada != null) System.out.println("\nFECHAMENTO AVARIA BUSCADO POR ID: Quantidade " + avariaBuscada.getQuantidade());

        // 4 - ATUALIZAR FECHAMENTO AVARIA
        avariaTeste.setQuantidade(10);
        int avariaAtualizada = fechamentoAvariaDAO.atualizar(avariaTeste);
        if (avariaAtualizada == 1) System.out.println("\nFECHAMENTO AVARIA ATUALIZADO COM SUCESSO!");

        // 5 - DELETAR FECHAMENTO AVARIA
        int avariaDeletada = fechamentoAvariaDAO.deletar(avariaTeste.getId());
        if (avariaDeletada == 1) System.out.println("\nFECHAMENTO AVARIA DELETADO COM SUCESSO!");

        // -------------------------- FECHAR CONEXÃO --------------------------
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("ERRO AO FECHAR A CONEXÃO: " + e.getMessage());
        }

    }
}
