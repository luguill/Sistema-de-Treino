/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.ExerciseType;
import enums.MuscleGroups;

/**
 *
 * @author Luiz Guilherme
 */
public class ExerciseRepetition extends Exercise{
    private int sets;
    private int repetition;
    
    public ExerciseRepetition(int id, String name, String description, MuscleGroups muscle, int sets, int repetition ){
        super(id, name, description, muscle, ExerciseType.REPETICAO);
        this.sets = sets;
        this.repetition = repetition;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }
    
    @Override
    int calculateVolume(){
      return sets * repetition ;
    }

    @Override
    public String toString() {
        return name + ": " + sets + " x " + repetition + "reps\nVolume do treino: "+calculateVolume();
    }
    
    
}
