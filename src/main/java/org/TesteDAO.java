package org;

import dao.AdmDAO;
import model.Administrador;

public class TesteDAO {
    public static void main(String[] args) {
        AdmDAO admDAO = new AdmDAO();
        admDAO.listar();
    }
}
