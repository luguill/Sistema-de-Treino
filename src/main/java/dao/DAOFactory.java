/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Luiz Guilherme
 */
public class DAOFactory {
    
    public static UserDAO criarUserDAO() {
        return new UserDAOJDBC();
    } 
    
    public static TrainingDAO criarTrainingDAO() {
        return new TrainingDAOJDBC();
    } 
            
    public static HistoricoDAO criarHistoricoDAO() {
        return new HistoricoDAOJDBC();
    } 
    
    public static ExerciseDAO criarExerciseDAO() {
        return new ExerciseDAOJDBC();
    } 
    
    public static TrainingExerciseDAO criarTrainingExerciseDAO() {
        return new TrainingExerciseDAOJDBC();
    } 
    
}
