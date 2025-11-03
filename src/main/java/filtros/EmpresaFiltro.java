package filtros;

import model.Empresa;
import java.util.ArrayList;
import java.util.List;

public class EmpresaFiltro {

    public List<Empresa> OrdenarIdDecre(List<Empresa> empresas){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < empresas.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < empresas.size() - 1 - i; j++) {
                if (empresas.get(j).getId() < empresas.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Empresa empresa = empresas.get(j);
                    empresas.set(j, empresas.get(j + 1));
                    empresas.set(j + 1, empresa);
                }
            }
        }
        // Retorna a lista ordenada
        return empresas;
    }

    public List<Empresa> OrdenarIdCrece(List<Empresa> empresas){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < empresas.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < empresas.size() - 1 - i; j++) {
                if (empresas.get(j).getId() > empresas.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Empresa empresa = empresas.get(j);
                    empresas.set(j, empresas.get(j + 1));
                    empresas.set(j + 1, empresa);
                }
            }
        }
        // Retorna a lista ordenada
        return empresas;
    }


    public List<Empresa> OrdenarNomeAz (List<Empresa> empresas){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < empresas.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < empresas.size() - 1 - i; j++) {
                if ((empresas.get(j).getNome().compareTo(empresas.get(j + 1).getNome()) > 0)) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Empresa empresa = empresas.get(j);
                    empresas.set(j, empresas.get(j + 1));
                    empresas.set(j + 1, empresa);
                }
            }
        }
        // Retorna a lista ordenada
        return empresas;
    }

    public List<Empresa> OrdenarNomeZa (List<Empresa> empresas){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < empresas.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < empresas.size() - 1 - i; j++) {
                if ((empresas.get(j).getNome().compareTo(empresas.get(j+1).getNome()) < 0)) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Empresa empresa = empresas.get(j);
                    empresas.set(j, empresas.get(j + 1));
                    empresas.set(j + 1, empresa);
                }
            }
        }
        // Retorna a lista ordenada
        return empresas;
    }

    public List<Empresa> OrdenarQntdFuncionarioDecre(List<Empresa> empresas){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < empresas.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < empresas.size() - 1 - i; j++) {
                if (empresas.get(j).getQntdFuncionarios() < empresas.get(j + 1).getQntdFuncionarios()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Empresa empresa = empresas.get(j);
                    empresas.set(j, empresas.get(j + 1));
                    empresas.set(j + 1, empresa);
                }
            }
        }
        // Retorna a lista ordenada
        return empresas;
    }

    public List<Empresa> OrdenarQntdFuncionarioCrece(List<Empresa> empresas){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < empresas.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < empresas.size() - 1 - i; j++) {
                if (empresas.get(j).getQntdFuncionarios() > empresas.get(j + 1).getQntdFuncionarios()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Empresa empresa = empresas.get(j);
                    empresas.set(j, empresas.get(j + 1));
                    empresas.set(j + 1, empresa);
                }
            }
        }
        // Retorna a lista ordenada
        return empresas;
    }


    public List<Empresa> filtrarPorQtdFuncionario(List<Empresa> empresas, int min, int max) {
        // Instancia a lista que será retornada
        List<Empresa> filtrados = new ArrayList<>();

        // Verifica se a quantidade de funcionários da empresa está entre as quantidades passadas
        for (Empresa e : empresas) {
            if (e.getQntdFuncionarios() >= min && e.getQntdFuncionarios() <= max) {
                // Se estiver entre, adiciona na lista que será retornada
                filtrados.add(e);
            }
        }
        return filtrados;
    }

}

