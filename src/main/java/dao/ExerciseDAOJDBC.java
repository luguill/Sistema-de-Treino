/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.ExerciseType;
import enums.MuscleGroups;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.ExercicioIsometrico;
import modelo.Exercise;
import modelo.ExerciseCardio;
import modelo.ExerciseRepetition;

/**
 *
 * @author Luiz Guilherme
 */
public class ExerciseDAOJDBC implements ExerciseDAO{
    Connection conexao = null;
    PreparedStatement sql = null;
    ResultSet rset = null;
    
    @Override
    public int inserir(Exercise exercicio) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO exercises(name, type, muscle_group, description, sets, repetition, duracao, distancia, tempo_segundos) ")
                .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {                      
                if (exercicio instanceof ExerciseRepetition repetition){
                    linha = DAOGenerico.executarComando(insert, repetition.getName(), repetition.getType().name(), repetition.getMuscle().name(), repetition.getDescription(), repetition.getSets(), repetition.getRepetition(), null, null, null);
                } else if (exercicio instanceof ExercicioIsometrico isometrico){
                    linha = DAOGenerico.executarComando(insert, isometrico.getName(), isometrico.getType().name(), isometrico.getMuscle().name(), isometrico.getDescription(), null, null,null , null, isometrico.getTempoSegundos());
                } else if (exercicio instanceof ExerciseCardio cardio){
                    linha = DAOGenerico.executarComando(insert, cardio.getName(), cardio.getType().name(), cardio.getMuscle().name(), cardio.getDescription(), null, null, cardio.getDuracao() , cardio.getDistancia(), null);
                } 
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }

    @Override
    public int editar(Exercise exercicio) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder 
                .append("UPDATE exercises SET ")
                .append("name = ?, ")
                .append("type = ?,")
                .append("muscle_group  = ?,")
                .append("description = ?,")
                .append("sets  = ?,")
                .append("repetition = ?,")
                .append("duracao  = ?,")
                .append("distancia = ?,")
                .append("tempo_segundos = ? ")
                .append("WHERE id = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            if (exercicio instanceof ExerciseRepetition repetition){
                    linha = DAOGenerico.executarComando(update, repetition.getName(), repetition.getType().name(), repetition.getMuscle().name(), repetition.getDescription(), repetition.getSets(), repetition.getRepetition(), null, null, null, repetition.getId());
                } else if (exercicio instanceof ExercicioIsometrico isometrico){
                    linha = DAOGenerico.executarComando(update, isometrico.getName(), isometrico.getType().name(), isometrico.getMuscle().name(), isometrico.getDescription(), null, null, null , null, isometrico.getTempoSegundos(), isometrico.getId());
                } else if (exercicio instanceof ExerciseCardio cardio){
                    linha = DAOGenerico.executarComando(update, cardio.getName(), cardio.getType().name(), cardio.getMuscle().name(), cardio.getDescription(), null, null, cardio.getDuracao(), cardio.getDistancia(), null, cardio.getId());
                } 
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
                .append("DELETE FROM exercises ")
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
    public List<Exercise> listar() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT e.id, e.name, e.type, e.muscle_group, e.description, e.sets, e.repetition, e.duracao, e.distancia, e.tempo_segundos ")
                .append("FROM exercises e ");
        
        String select = sqlBuilder.toString();

        List<Exercise> exercicios = new ArrayList<Exercise>();

        try {       
            rset = DAOGenerico.executarConsulta(select);


            while (rset.next()) {
                Exercise exercicio = null;
                
                String tipo = rset.getString("type");
                
                switch (tipo){
                    case "REPETICAO":
                        exercicio = new ExerciseRepetition();
                        break;
                    case "ISOMETRICO":
                        exercicio = new ExercicioIsometrico();
                        break;
                    case "CARDIO":
                        exercicio = new ExerciseCardio();
                        break;  
                    default:
                        throw new IllegalArgumentException("Tipo de exercicio invalido.");
                }   
                exercicio.setId(rset.getInt("id"));
                exercicio.setName(rset.getString("name"));
                exercicio.setDescription(rset.getString("description"));
                exercicio.setType(ExerciseType.valueOf(tipo)); 
                exercicio.setMuscle(MuscleGroups.valueOf(rset.getString("muscle_group")));
                
                if (exercicio instanceof ExerciseRepetition repetition){
                    repetition.setSets(rset.getInt("sets"));
                    repetition.setRepetition(rset.getInt("repetition"));
                } else if (exercicio instanceof ExercicioIsometrico isometrico){
                    isometrico.setTempoSegundos(rset.getInt("tempo_segundos"));
                } else if (exercicio instanceof ExerciseCardio cardio){
                    cardio.setDuracao(rset.getInt("duracao"));
                    cardio.setDistancia(rset.getInt("distancia"));
                } 
                
                exercicios.add(exercicio);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return exercicios;
    }

    @Override
    public Exercise listar(int id) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT e.id, e.name, e.type, e.muscle_groups, e.description, e.sets, e.repetition, e.duracao, e.distancia, e.tempo_segundos ")
                .append("FROM exercises e ")
                .append("WHERE e.id = ?");
        String select = sqlBuilder.toString();
        
         Exercise exercicio = null;

        try {       
            rset = DAOGenerico.executarConsulta(select, id);


            while (rset.next()) {

                
                String tipo = rset.getString("type");
                
                switch (tipo){
                    case "REPETICAO":
                        exercicio = new ExerciseRepetition();
                        break;
                    case "ISOMETRICO":
                        exercicio = new ExercicioIsometrico();
                        break;
                    case "CARDIO":
                        exercicio = new ExerciseCardio();
                        break;  
                    default:
                        throw new IllegalArgumentException("Tipo de exercicio invalido.");
                }   
                exercicio.setId(rset.getInt("id"));
                exercicio.setName(rset.getString("name"));
                exercicio.setDescription(rset.getString("description"));
                exercicio.setType(ExerciseType.valueOf(tipo)); 
                exercicio.setMuscle(MuscleGroups.valueOf(rset.getString("muscle_group")));
                
                if (exercicio instanceof ExerciseRepetition repetition){
                    repetition.setSets(rset.getInt("sets"));
                    repetition.setRepetition(rset.getInt("repetition"));
                } else if (exercicio instanceof ExercicioIsometrico isometrico){
                    isometrico.setTempoSegundos(rset.getInt("tempo_segundos"));
                } else if (exercicio instanceof ExerciseCardio cardio){
                    cardio.setDuracao(rset.getInt("duracao"));
                    cardio.setDistancia(rset.getInt("distancia"));
                } 
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return exercicio;        
    }
    
    @Override
    public Exercise buscarPorId(int id){
        String sql = "SELECT * FROM exercises WHERE id = ? ";
        
        try {       
            rset = DAOGenerico.executarConsulta(sql, id);

            if (rset.next()) {

                
                String tipo = rset.getString("type");
                Exercise exercicio;
                
                switch (tipo){
                    case "REPETICAO":
                        exercicio = new ExerciseRepetition();
                        break;
                    case "ISOMETRICO":
                        exercicio = new ExercicioIsometrico();                      
                        break;
                    case "CARDIO":
                        exercicio = new ExerciseCardio();
                        break;  
                    default:
                        throw new IllegalArgumentException("Tipo de exercicio invalido.");
                }                   
                
                exercicio.setId(rset.getInt("id"));
                exercicio.setName(rset.getString("name"));
                exercicio.setDescription(rset.getString("description"));
                exercicio.setType(ExerciseType.valueOf(tipo));
                exercicio.setMuscle(MuscleGroups.valueOf(rset.getString("muscle_group").toUpperCase()));
                         
                if (exercicio instanceof ExerciseRepetition repetition){
                    repetition.setSets(rset.getInt("sets"));
                    repetition.setRepetition(rset.getInt("repetition"));
                } else if (exercicio instanceof ExercicioIsometrico isometrico){
                    isometrico.setTempoSegundos(rset.getInt("tempo_segundos"));
                } else if (exercicio instanceof ExerciseCardio cardio){
                    cardio.setDuracao(rset.getInt("duracao"));
                    cardio.setDistancia(rset.getInt("distancia"));
                }

                return exercicio;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return null;
        
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
