package filtros;

import model.Plano;
import model.Plano;

import java.util.List;

public class PlanoFiltro {

    public List<Plano> OrdenarIdDecre(List<Plano> planos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < planos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < planos.size() - 1 - i; j++) {
                if (planos.get(j).getId() < planos.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Plano plano = planos.get(j);
                    planos.set(j, planos.get(j + 1));
                    planos.set(j + 1, plano);
                }
            }
        }
        // Retorna a lista ordenada
        return planos;
    }

    public List<Plano> OrdenarIdCrece(List<Plano> planos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < planos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < planos.size() - 1 - i; j++) {
                if (planos.get(j).getId() > planos.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Plano plano = planos.get(j);
                    planos.set(j, planos.get(j + 1));
                    planos.set(j + 1, plano);
                }
            }
        }
        // Retorna a lista ordenada
        return planos;
    }


    public List<Plano> OrdenarNomeAz (List<Plano> planos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < planos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < planos.size() - 1 - i; j++) {
                if ((planos.get(j).getNome().compareTo(planos.get(j + 1).getNome()) > 0)) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Plano plano = planos.get(j);
                    planos.set(j, planos.get(j + 1));
                    planos.set(j + 1, plano);
                }
            }
        }
        // Retorna a lista ordenada
        return planos;
    }

    public List<Plano> OrdenarNomeZa (List<Plano> planos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < planos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < planos.size() - 1 - i; j++) {
                if ((planos.get(j).getNome().compareTo(planos.get(j+1).getNome()) < 0)) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Plano plano = planos.get(j);
                    planos.set(j, planos.get(j + 1));
                    planos.set(j + 1, plano);
                }
            }
        }
        // Retorna a lista ordenada
        return planos;
    }

    public List<Plano> OrdenarPrecoDecre(List<Plano> planos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < planos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < planos.size() - 1 - i; j++) {
                if (planos.get(j).getPreco() < planos.get(j + 1).getPreco()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Plano plano = planos.get(j);
                    planos.set(j, planos.get(j + 1));
                    planos.set(j + 1, plano);
                }
            }
        }
        // Retorna a lista ordenada
        return planos;
    }

    public List<Plano> OrdenarPrecoCrece(List<Plano> planos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < planos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < planos.size() - 1 - i; j++) {
                if (planos.get(j).getPreco() > planos.get(j + 1).getPreco()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Plano plano = planos.get(j);
                    planos.set(j, planos.get(j + 1));
                    planos.set(j + 1, plano);
                }
            }
        }
        // Retorna a lista ordenada
        return planos;
    }
}

