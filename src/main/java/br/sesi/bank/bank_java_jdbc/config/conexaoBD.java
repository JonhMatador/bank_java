package br.sesi.bank.bank_java_jdbc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexaoBD{
    public static void main(String[] args){
        try{
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/banco_sesi?user=postgres&password=root");

            System.out.println("Conseguiu conectar!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}


