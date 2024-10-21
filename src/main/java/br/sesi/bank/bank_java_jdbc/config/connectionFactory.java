package br.sesi.bank.bank_java_jdbc.config;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class connectionFactory{
    public Connection recuperarConexao(){
        try{
            return createDataSource().getConnection();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private HikariDataSource createDataSource(){
        HikariDataSource config = new HikariDataSource();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/banco_sesi");
        config.setUsername("postgres");
        config.setPassword("root");
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }
}
