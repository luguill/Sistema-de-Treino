/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Luiz Guilherme
 */
public class ExerciseRecord {
    private int id;
    private TrainingSession sessaoTreino;
    private Exercise exercise;
    private String resultado;
    
    public ExerciseRecord(int id, TrainingSession sessaoTreino, Exercise exercise, String resultado){
        this.id = id;
        this.sessaoTreino = sessaoTreino;
        this.exercise = exercise;
        this.resultado = resultado;        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TrainingSession getSessaoTreino() {
        return sessaoTreino;
    }

    public void setSessaoTreino(TrainingSession sessaoTreino) {
        this.sessaoTreino = sessaoTreino;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    @Override
    public String toString(){
        return String.format("\nREGISTRO\nResultado: %s ", getResultado());
    }
}
