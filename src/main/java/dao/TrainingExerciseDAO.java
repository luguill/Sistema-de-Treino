/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Exercise;
import modelo.Training;
/**
 *
 * @author Luiz Guilherme
 */
public interface TrainingExerciseDAO {
    public int inserir(int trainingId, int exerciseId);
    public int apagar(int trainingId, int exerciseId);
    public List<Exercise> listarExercicios(int trainingId);
    public List<Training> listarTreinos(int exerciseId);
    public int apagarPorTreino(int treinoId);
}
