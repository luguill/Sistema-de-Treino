/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.DayWeek;
import enums.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Exercise;
import modelo.Training;


/**
 *
 * @author Luiz Guilherme
 */
public class TrainingExerciseDAOJDBC implements TrainingExerciseDAO {
     
    Connection conexao = null;
    PreparedStatement sql = null;
    ResultSet rset = null;
    
    @Override
    public int inserir(int treinoId, int exercicioId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO training_exercise(training_id, exercise_id) ")
                .append("VALUES (?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {          
            linha = DAOGenerico.executarComando(insert, treinoId, exercicioId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }

    

    @Override
    public int apagar(int treinoId, int exercicioId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM training_exercise ")
                .append("WHERE training_id = ? ")
                .append("AND exercise_id = ?");
        
        String delete = sqlBuilder.toString();
        int linha = 0;
        try {         
            linha = DAOGenerico.executarComando(delete, treinoId, exercicioId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
public List<Training> listarTreinos(int exerciseId) {

    StringBuilder sqlBuilder = new StringBuilder();

    sqlBuilder
            .append("SELECT t.* ")
            .append("FROM training t ")
            .append("INNER JOIN training_exercise te ")
            .append("ON t.id = te.training_id ")
            .append("WHERE te.exercise_id = ?");

    String select = sqlBuilder.toString();

    List<Training> treinos = new ArrayList<>();

    try {

        rset = DAOGenerico.executarConsulta(select, exerciseId);

        while (rset.next()) {

            Training treino = new Training();

            treino.setId(rset.getInt("id"));
            treino.setName(rset.getString("name"));
            treino.setDayWeek(DayWeek.valueOf(rset.getString("day_week").toUpperCase()));

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
    public List<Exercise> listarExercicios(int trainingId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder               
               .append("SELECT e.* ")
               .append("FROM exercises e ")
               .append("INNER JOIN training_exercise te ")
               .append("ON e.id = te.exercise_id ")
               .append("WHERE te.training_id = ?");
        
        String select = sqlBuilder.toString();

        List<Exercise> exercicioIds = new ArrayList<>();

        try {       
            rset = DAOGenerico.executarConsulta(select, trainingId);
            
            ExerciseDAO exerciseDAO = new ExerciseDAOJDBC();
            
            while (rset.next()) {
                int exerciseId = rset.getInt("id");
                
                Exercise exercicio = exerciseDAO.buscarPorId(exerciseId);
                
                if (exercicio != null) {
                exercicioIds.add(exercicio);
            }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return exercicioIds;
    }

    
    public int apagarPorTreino(int treinoId) {
        
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
               .append("DELETE FROM training_exercise WHERE training_id = ? ");
        String select = sqlBuilder.toString();
        
        int linha = 0;

        try {
            linha = DAOGenerico.executarComando(select, treinoId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return linha;
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
