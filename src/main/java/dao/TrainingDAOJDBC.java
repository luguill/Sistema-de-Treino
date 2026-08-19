/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.DayWeek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Exercise;
import modelo.Training;
import modelo.User;

/**
 *
 * @author Luiz Guilherme
 */
public class TrainingDAOJDBC implements TrainingDAO {
    
     Connection conexao = null;
    PreparedStatement sql = null;
    ResultSet rset = null;
    
    @Override
    public int inserir(Training treino) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO training(name, day_week, user_id) ")
                .append("VALUES (?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int idGerado = 0;
        //int linha = 0;
        try {          
            idGerado = DAOGenerico.executarComandoRetornandoId(insert, treino.getName(), treino.getDayWeek().name(), treino.getUsuario().getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return idGerado;
    }

    @Override
    public int editar(Training treino) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE training SET ")
                .append("name = ?, ")
                .append("day_week = ?, ")
                .append("user_id = ? ")
                .append("WHERE id = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            
            linha = DAOGenerico.executarComando(update, treino.getName(), treino.getDayWeek().name(), treino.getUsuario().getId(), treino.getId());
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
                .append("DELETE FROM training ")
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
    public List<Training> listar() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT t.id AS training_id, t.name AS training_name, t.day_week, u.id AS user_id, u.name AS user_name ")
                .append("FROM training t ")
                .append("INNER JOIN users u ON (t.user_id = u.id) ")
                .append("ORDER BY t.id");        
        String select = sqlBuilder.toString();

        List<Training> treinos = new ArrayList<Training>();

        try {       
            rset = DAOGenerico.executarConsulta(select);

            while (rset.next()) {

                Training treino = new Training();
                treino.setId(rset.getInt("training_id"));
                treino.setName(rset.getString("training_name"));
                
                String dia = rset.getString("day_week");
                treino.setDayWeek(DayWeek.valueOf(dia));
                          
                User usuario = new User();
                usuario.setId(rset.getInt("user_id"));
                usuario.setName(rset.getString("user_name"));
                
                treino.setUsuario(usuario);
                treinos.add(treino);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return treinos;
    }

    @Override
    public Training listar(int id) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT t.id AS training_id, t.name AS training_name, t.day_week, u.id AS user_id, u.name AS user_name ")
                .append("FROM training t ")
                .append("INNER JOIN users u ON (t.user_id = u.id) ")                
                .append("WHERE u.id = ?");
        String select = sqlBuilder.toString();
        
        Training treino = null;

        try {       
            rset = DAOGenerico.executarConsulta(select, id);

            if (rset.next()) {

                treino = new Training();
                treino.setId(rset.getInt("training_id"));
                treino.setName(rset.getString("training_name"));
                
                String dia = rset.getString("day_week");
                treino.setDayWeek(DayWeek.valueOf(dia));
                          
                User usuario = new User();
                usuario.setId(rset.getInt("user_id"));
                usuario.setName(rset.getString("user_name"));
                
                treino.setUsuario(usuario);
             
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return treino;        
    }
    
    @Override
    public Training buscarPorId(int id){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
               .append("SELECT t.id AS training_id, t.name AS training_name, t.day_week, t.user_id, u.name AS user_name ")
               .append("FROM training t ")
               .append("LEFT JOIN users u ON (t.user_id = u.id) ") 
               .append("WHERE t.id = ?");
        String select = sqlBuilder.toString();
        
        Training treino = null;
        
        try {       
            rset = DAOGenerico.executarConsulta(select, id);

            if (rset.next()) {
                
                treino = new Training();
                treino.setId(rset.getInt("training_id"));
                treino.setName(rset.getString("training_name"));
                
                String dia = rset.getString("day_week");
               
                treino.setDayWeek(DayWeek.valueOf(dia));
                          
                User usuario = new User();
                usuario.setId(rset.getInt("user_id"));
                usuario.setName(rset.getString("user_name"));
                
                treino.setUsuario(usuario);
                
                TrainingExerciseDAO treinoExercicioDao = new TrainingExerciseDAOJDBC();
                List<Exercise> exercicios = treinoExercicioDao.listarExercicios(id);
                
                for (Exercise ex : exercicios){
                    treino.addExercise(ex);
                }
                
            } 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return treino;        
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
