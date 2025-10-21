package filtros;

import model.Administrador;

import java.util.List;

public class AdministradorFiltro {

    public List<Administrador> OrdenarIdDecre(List<Administrador> administradores){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < administradores.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < administradores.size() - 1 - i; j++) {
                if (administradores.get(j).getId() < administradores.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Administrador administrador = administradores.get(j);
                    administradores.set(j, administradores.get(j + 1));
                    administradores.set(j + 1, administrador);
                }
            }
        }
        // Retorna a lista ordenada
        return administradores;
    }

    public List<Administrador> OrdenarIdCrece(List<Administrador> administradores){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < administradores.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < administradores.size() - 1 - i; j++) {
                if (administradores.get(j).getId() > administradores.get(j + 1).getId()) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Administrador administrador = administradores.get(j);
                    administradores.set(j, administradores.get(j + 1));
                    administradores.set(j + 1, administrador);
                }
            }
        }
        // Retorna a lista ordenada
        return administradores;
    }


    public List<Administrador> OrdenarEmailAz (List<Administrador> administradores){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < administradores.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < administradores.size() - 1 - i; j++) {
                if ((administradores.get(j).getEmail().compareTo(administradores.get(j + 1).getEmail()) > 0)) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Administrador administrador = administradores.get(j);
                    administradores.set(j, administradores.get(j + 1));
                    administradores.set(j + 1, administrador);
                }
            }
        }
        // Retorna a lista ordenada
        return administradores;
    }

    public List<Administrador> OrdenarEmailZa (List<Administrador> administradores){
        // Percorre a lista, fazendo que todos os elementos vizinhos troquem de posições até ela ser ordenada
        for (int i = 0; i < administradores.size() - 1; i++) {
            // Percorre novamente a lista trocando elementos vizinhos
            for (int j = 0; j < administradores.size() - 1 - i; j++) {
                if ((administradores.get(j).getEmail().compareTo(administradores.get(j+1).getEmail()) < 0)) {
                    // Instancia uma variavel temporaria para receber o objeto que será trocado
                    Administrador administrador = administradores.get(j);
                    administradores.set(j, administradores.get(j + 1));
                    administradores.set(j + 1, administrador);
                }
            }
        }
        // Retorna a lista ordenada
        return administradores;
    }

}
