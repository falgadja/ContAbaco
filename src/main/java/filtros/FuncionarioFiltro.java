package filtros;

import model.Funcionario;
import model.Pagamento;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioFiltro {

    public List<Funcionario> OrdenarIdDecre(List<Funcionario> funcionarios){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < funcionarios.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < funcionarios.size() - 1 - i; j++) {
                if (funcionarios.get(j).getId() < funcionarios.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Funcionario funcionario = funcionarios.get(j);
                    funcionarios.set(j, funcionarios.get(j + 1));
                    funcionarios.set(j + 1, funcionario);
                }
            }
        }
        // Retorna a lista ordenada
        return funcionarios;
    }

    public List<Funcionario> OrdenarIdCrece(List<Funcionario> funcionarios){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < funcionarios.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < funcionarios.size() - 1 - i; j++) {
                if (funcionarios.get(j).getId() > funcionarios.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Funcionario funcionario = funcionarios.get(j);
                    funcionarios.set(j, funcionarios.get(j + 1));
                    funcionarios.set(j + 1, funcionario);
                }
            }
        }
        // Retorna a lista ordenada
        return funcionarios;
    }


    public List<Funcionario> OrdenarNomeAz (List<Funcionario> funcionarios){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < funcionarios.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < funcionarios.size() - 1 - i; j++) {
                if ((funcionarios.get(j).getNome().compareTo(funcionarios.get(j + 1).getNome()) > 0)) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Funcionario funcionario = funcionarios.get(j);
                    funcionarios.set(j, funcionarios.get(j + 1));
                    funcionarios.set(j + 1, funcionario);
                }
            }
        }
        // Retorna a lista ordenada
        return funcionarios;
    }

    public List<Funcionario> OrdenarNomeZa (List<Funcionario> funcionarios){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < funcionarios.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < funcionarios.size() - 1 - i; j++) {
                if ((funcionarios.get(j).getNome().compareTo(funcionarios.get(j+1).getNome()) < 0)) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Funcionario funcionario = funcionarios.get(j);
                    funcionarios.set(j, funcionarios.get(j + 1));
                    funcionarios.set(j + 1, funcionario);
                }
            }
        }
        // Retorna a lista ordenada
        return funcionarios;
    }

    public List<Funcionario> filtrarPorIdEmpresa(List<Funcionario> funcionarios, int IdEmpresa) {
        // Instancia a lista que será retornada
        List<Funcionario> filtrados = new ArrayList<>();

        // Verifica quais funcionarios correspondem com o id da empressa
        for (Funcionario f : funcionarios) {
            if (f.getIdEmpresa() == IdEmpresa) {
                // Se corresponder adiciona na lista que será retornada
                filtrados.add(f);
            }
        }
        return filtrados;
    }

}
