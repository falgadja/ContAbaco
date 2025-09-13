package org.example;

import java.time.LocalDate;

import dao.EmpresaDAO;
import model.Empresa;
//import model.Funcionario;
import model.Pagamento;

public class Main  {
    public static void main(String[] args) {
        //EMPRESA
        Empresa empresa = new Empresa(123456789,"Seara",25);
        System.out.println("****************** OBJETO EMPRESA ***********************");
        System.out.println(empresa);
        // EMPRESA DAO
        EmpresaDAO dao = new EmpresaDAO();

        //FUNCIONARIOS
//        Funcionario funcionario=new Funcionario(1,"Sergio",);
    }
    //TESTAR PAGAMENTODAO
    Pagamento pagamento=new Pagamento(1,LocalDate.now(), 250.00,"CARTAO");
}