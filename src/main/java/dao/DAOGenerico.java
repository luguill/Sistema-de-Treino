/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luiz Guilherme
 */
public abstract class DAOGenerico {
      public static Connection getConexao() throws SQLException, ClassNotFoundException {
        String USUARIO = "root";
        String SENHA = "123456";
        String URL_BANCO = "jdbc:mysql://localhost:3306/treino";
        //Faz com que a classe seja carregada pela JVM
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL_BANCO, USUARIO, SENHA);
    }
    
     public static int executarComando(String query, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement sql = (PreparedStatement)  getConexao().prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            sql.setObject(i+1,params[i]);
        }
        int result = sql.executeUpdate();
        sql.close();
        return result;
     }
     
     public static ResultSet executarConsulta(String query, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement sql = (PreparedStatement)  getConexao().prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            sql.setObject(i+1,params[i]);
        }
        return sql.executeQuery();
    }
     
     public static int executarComandoRetornandoId(
        String query,
        Object... params)
        throws SQLException, ClassNotFoundException {

    PreparedStatement sql = null;
    ResultSet generatedKeys = null;

    try {

        sql = getConexao().prepareStatement(
                query,
                java.sql.Statement.RETURN_GENERATED_KEYS
        );

        for (int i = 0; i < params.length; i++) {
            sql.setObject(i + 1, params[i]);
        }

        sql.executeUpdate();

        generatedKeys = sql.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }

        return 0;

    } finally {

        if (generatedKeys != null) {
            generatedKeys.close();
        }

        if (sql != null) {
            sql.close();
        }
    }
}
         
}
