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
public class ExercicioIsometrico extends Exercise{
    private int tempoSegundos;
    
    public ExercicioIsometrico(int id, String name, String description, MuscleGroups muscle){
        super(id, name, description, muscle, ExerciseType.ISOMETRICO);
        this.tempoSegundos = 0;
    } 

    public double getTempoSegundos() {
        return tempoSegundos;
    }

    public void setTempoSegundos(int tempoSegundos) {
        this.tempoSegundos = tempoSegundos;
    }
    
    @Override
    int calculateVolume(){
        return tempoSegundos; 
    }
}
