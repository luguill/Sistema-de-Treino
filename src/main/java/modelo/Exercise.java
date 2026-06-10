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
abstract class Exercise {
    protected int id;
    protected String name;
    protected String description;
    protected MuscleGroups muscle;
    protected ExerciseType type;
    
    public Exercise(int id, String name, String description, MuscleGroups muscle, ExerciseType type){
        setId(id);
        this.name = name;
        this.description = description;
        this.muscle = muscle;
        this.type = type;
    }
     
    public int getId() {
        return id;
    }

    public void setId(int id) {
      
            this.id = id;

    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    abstract int calculateVolume();
   
}
