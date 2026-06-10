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
public class ExerciseCardio extends Exercise{
    private int duracao;
    private int distancia;
    
    public ExerciseCardio(int id, String name, String description, MuscleGroups muscle){
        super(id, name, description, muscle, ExerciseType.CARDIO);
        this.duracao = 0;
        this.distancia = 0;
    } 

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    @Override
    int calculateVolume(){
        return distancia; 
    }
    
}
