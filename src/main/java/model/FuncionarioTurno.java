package model;

public class FuncionarioTurno {
    private int id;
    private int idFuncionario;
    private int idTurno;

    // CONSTRUTOR
    public FuncionarioTurno(int id, int idFuncionario, int idTurno) {
        this.id = id;
        this.idFuncionario = idFuncionario;
        this.idTurno = idTurno;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public int getIdTurno() {
        return idTurno;
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    // TO STRING
    @Override
    public String toString() {
        return " -- FuncionarioTurno -- " +
                "\nID: " + id +
                "\nID do Funcionário: " + idFuncionario +
                "\nID do Turno: " + idTurno;
    }
}
