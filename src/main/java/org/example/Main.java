package org.example;

import conexao.Conexao;
import dao.LeituraAbacoDAO;
import dao.AvariaDAO;
import model.LeituraAbaco;
import model.Avaria;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Testar conexão
        Conexao conexao = new Conexao();
        System.out.println(conexao.conectar() != null ? "Conectado com sucesso" : "Erro ao conectar");

        // ********* AVARIA *********
        AvariaDAO daoAvaria = new AvariaDAO();

        // Inserir uma nova avaria
        Avaria avaria = new Avaria("Estria", "Mancha", 6);
        System.out.println("Inserir avaria: " + (daoAvaria.inserir(avaria) == 1 ? "Sucesso" : "Falha"));

        // Listar todas as avarias
        System.out.println("\nLista de todas as avarias:");
        for (Avaria a : daoAvaria.listarTodas()) {
            System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome() + " | Descrição: " + a.getDescricao());
        }

        // Atualizar descrição
        System.out.println("Atualizar descrição avaria ID 7: " +
                (daoAvaria.atualizarDescricao(7, "manchas feias") == 1 ? "Sucesso" : "Falha"));

        // Atualizar nome
        System.out.println("Atualizar nome avaria ID 7: " +
                (daoAvaria.atualizarNome(7, "mancha") == 1 ? "Sucesso" : "Falha"));

        // Deletar avaria
        System.out.println("Deletar avaria ID 6: " +
                (daoAvaria.deletar(6) == 1 ? "Sucesso" : "Falha"));

        // Buscar por nome
        Avaria avariaBuscada = (Avaria) daoAvaria.buscarPorNome("Fratura Óssea");
        System.out.println("\nBuscar avaria por nome 'Fratura Óssea': " +
                (avariaBuscada != null ? avariaBuscada.getNome() + " encontrada" : "Nenhuma avaria encontrada"));

        // ********* LEITURA ABACO *********
        LeituraAbacoDAO daoLeitura = new LeituraAbacoDAO();

        // Inserir nova leitura
        byte[] imagemExemplo = {1, 2, 3, 4, 5};
        LocalDateTime dataAgora = LocalDateTime.now();
        LeituraAbaco novaLeitura = new LeituraAbaco(imagemExemplo, dataAgora);
        System.out.println("\nInserir leitura: " + (daoLeitura.inserirLeituraAbaco(novaLeitura) == 1 ? "Sucesso" : "Falha"));

        // Listar todas as leituras
        System.out.println("\nLista de todas as leituras:");
        for (LeituraAbaco l : daoLeitura.listarLeituraAbaco()) {
            System.out.println(l);
            System.out.println("-----------------------------");
        }

        // Buscar por ID
        int idBusca = 1;
        LeituraAbaco leituraPorId = daoLeitura.buscarPorId(idBusca);
        System.out.println("\nBusca por ID " + idBusca + ": " +
                (leituraPorId != null ? "Registro encontrado" : "Nenhum registro encontrado"));

        // Buscar por Data e Hora
        LeituraAbaco leituraPorDataHora = daoLeitura.buscarPorDataHora(dataAgora);
        System.out.println("Busca por Data/Hora " + dataAgora + ": " +
                (leituraPorDataHora != null ? "Registro encontrado" : "Nenhum registro encontrado"));
    }
}
