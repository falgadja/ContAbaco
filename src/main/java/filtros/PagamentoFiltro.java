package filtros;

import model.Pagamento;

import java.util.List;

public class PagamentoFiltro {

    public List<Pagamento> OrdenarIdDecre(List<Pagamento> pagamentos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < pagamentos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < pagamentos.size() - 1 - i; j++) {
                if (pagamentos.get(j).getId() < pagamentos.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Pagamento pagamento = pagamentos.get(j);
                    pagamentos.set(j, pagamentos.get(j + 1));
                    pagamentos.set(j + 1, pagamento);
                }
            }
        }
        // Retorna a lista ordenada
        return pagamentos;
    }

    public List<Pagamento> OrdenarIdCrece(List<Pagamento> pagamentos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < pagamentos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < pagamentos.size() - 1 - i; j++) {
                if (pagamentos.get(j).getId() > pagamentos.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Pagamento pagamento = pagamentos.get(j);
                    pagamentos.set(j, pagamentos.get(j + 1));
                    pagamentos.set(j + 1, pagamento);
                }
            }
        }
        // Retorna a lista ordenada
        return pagamentos;
    }

    public List<Pagamento> OrdenarTotalDecre(List<Pagamento> pagamentos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < pagamentos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < pagamentos.size() - 1 - i; j++) {
                if (pagamentos.get(j).getTotal() < pagamentos.get(j + 1).getTotal()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Pagamento pagamento = pagamentos.get(j);
                    pagamentos.set(j, pagamentos.get(j + 1));
                    pagamentos.set(j + 1, pagamento);
                }
            }
        }
        // Retorna a lista ordenada
        return pagamentos;
    }

    public List<Pagamento> OrdenarTotalCrece(List<Pagamento> pagamentos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < pagamentos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < pagamentos.size() - 1 - i; j++) {
                if (pagamentos.get(j).getTotal() > pagamentos.get(j + 1).getTotal()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Pagamento pagamento = pagamentos.get(j);
                    pagamentos.set(j, pagamentos.get(j + 1));
                    pagamentos.set(j + 1, pagamento);
                }
            }
        }
        // Retorna a lista ordenada
        return pagamentos;
    }

    public List<Pagamento> OrdenarDataDecre(List<Pagamento> pagamentos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < pagamentos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < pagamentos.size() - 1 - i; j++) {
                if (pagamentos.get(j).getData().isBefore(pagamentos.get(j+1).getData())) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Pagamento pagamento = pagamentos.get(j);
                    pagamentos.set(j, pagamentos.get(j + 1));
                    pagamentos.set(j + 1, pagamento);
                }
            }
        }
        // Retorna a lista ordenada
        return pagamentos;
    }

    public List<Pagamento> OrdenarDataCrece(List<Pagamento> pagamentos){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < pagamentos.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < pagamentos.size() - 1 - i; j++) {
                if (pagamentos.get(j).getData().isAfter(pagamentos.get(j+1).getData())) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Pagamento pagamento = pagamentos.get(j);
                    pagamentos.set(j, pagamentos.get(j + 1));
                    pagamentos.set(j + 1, pagamento);
                }
            }
        }
        // Retorna a lista ordenada
        return pagamentos;
    }
}
