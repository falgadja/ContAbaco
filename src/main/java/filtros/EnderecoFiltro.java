package filtros;

import model.Endereco;

import java.util.ArrayList;
import java.util.List;

public class EnderecoFiltro {


    public List<Endereco> OrdenarIdDecre(List<Endereco> enderecos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < enderecos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < enderecos.size() - 1 - i; j++) {
                if (enderecos.get(j).getId() < enderecos.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Endereco endereco = enderecos.get(j);
                    enderecos.set(j, enderecos.get(j + 1));
                    enderecos.set(j + 1, endereco);
                }
            }
        }
        // Retorna a lista ordenada
        return enderecos;
    }

    public List<Endereco> OrdenarIdCrece(List<Endereco> enderecos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < enderecos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < enderecos.size() - 1 - i; j++) {
                if (enderecos.get(j).getId() > enderecos.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Endereco endereco = enderecos.get(j);
                    enderecos.set(j, enderecos.get(j + 1));
                    enderecos.set(j + 1, endereco);
                }
            }
        }
        // Retorna a lista ordenada
        return enderecos;
    }

    public List<Endereco> filtrarPorEstado(List<Endereco> enderecos, List<String> estados) {
        // Instancia a lista que será retornada
        List<Endereco> filtrados = new ArrayList<>();

        // Verifica se o tipo corresponde a algum tipo passado na lista
        for (Endereco e : enderecos) {
            if (estados.contains(e.getEstado())) {
                // Se corresponder adiciona na lista que será retornada
                filtrados.add(e);
            }
        }
        return filtrados;
    }
}
