/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.User;

/**
 *
 * @author Luiz Guilherme
 */
public class UserDAOJDBC implements UserDAO{
    
    Connection conexao = null;
    PreparedStatement sql = null;
    ResultSet rset = null;
    
    @Override
    public int inserir(User usuario) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO users(id, name, age, weight, height, level) ")
                .append("VALUES (?, ?, ?, ?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {          
            linha = DAOGenerico.executarComando(insert, usuario.getId(), usuario.getName(), usuario.getAge(), usuario.getWeight(), usuario.getHeight(), usuario.getLevel().name());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }

    @Override
    public int editar(User usuario) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE users SET ")
                .append("name = ?, ")
                .append("age = ?, ")
                .append("weight = ?, ")
                .append("height = ?, ")
                .append("level = ? ")
                .append("WHERE id = ? ");
        
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            
            linha = DAOGenerico.executarComando(update, usuario.getName(), usuario.getAge(), usuario.getWeight(), usuario.getHeight(), usuario.getLevel().name(), usuario.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public int apagar(int codigo) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM users ")
                .append("WHERE id = ?");
        
        String delete = sqlBuilder.toString();
        int linha = 0;
        try {         
            linha = DAOGenerico.executarComando(delete, codigo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public List<User> listar() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT u.id, u.name, u.age, u.weight, u.height, u.level ")
                .append("FROM users u ");
        
        String select = sqlBuilder.toString();

        List<User> usuarios = new ArrayList<User>();

        try {       
            rset = DAOGenerico.executarConsulta(select);


            while (rset.next()) {
               
                User usuario = new User();
                usuario.setId(rset.getInt("u.id"));
                
                usuario.setName(rset.getString("u.name"));
                usuario.setAge(rset.getInt("u.age"));
                usuario.setWeight(rset.getDouble("u.weight"));
                usuario.setHeight(rset.getDouble("u.height"));
                usuario.setLevel(Level.valueOf(rset.getString("u.level")));

                usuarios.add(usuario);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return usuarios;
    }

    @Override
    public User listar(int id) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT u.id, u.name, u.age, u.weight, u.height, u.level ")
                .append("FROM users u ")
                .append("WHERE u.id = ?");
        String select = sqlBuilder.toString();
        
        User usuario = null;

        try {       
            rset = DAOGenerico.executarConsulta(select, id);


            while (rset.next()) {

                usuario = new User();
                usuario.setId(rset.getInt("u.id"));
                
                usuario.setName(rset.getString("u.name"));
                usuario.setAge(rset.getInt("u.age"));
                usuario.setWeight(rset.getDouble("u.weight"));
                usuario.setHeight(rset.getDouble("u.height"));
                usuario.setLevel(Level.valueOf(rset.getString("u.level")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return usuario;        
    }
    
    @Override
    public User buscarPorId(int id){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT u.id, u.name, u.age, u.weight, u.height, u.level ")
                .append("FROM users u ")
                .append("WHERE u.id = ?");
        String select = sqlBuilder.toString();
        
        User usuario = null;
        
        try {       
            rset = DAOGenerico.executarConsulta(select, id);


            if (rset.next()) {

                usuario = new User();
                usuario.setId(rset.getInt("u.id"));
                
                usuario.setName(rset.getString("u.name"));
                usuario.setAge(rset.getInt("u.age"));
                usuario.setWeight(rset.getDouble("u.weight"));
                usuario.setHeight(rset.getDouble("u.height"));
                usuario.setLevel(Level.valueOf(rset.getString("u.level")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return usuario;        
    }
    
    private void fecharConexao() {
        try {
            if (rset != null) {
                rset.close();
            }
            if (sql != null) {
                sql.close();
            }

            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
