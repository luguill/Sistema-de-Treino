/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.DayWeek;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Guilherme
 */
public class Training {
    private int id;
    private String name;
    private DayWeek dayWeek;
    private List<Exercise> exerciseList = new ArrayList<>();
    private User usuario;
    
    public Training(int id, String name, DayWeek dayweek){
        this.id = id;
        this.name = name;
        this.dayWeek = dayweek; 
        this.usuario = usuario;
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

    public DayWeek getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(DayWeek dayWeek) {
        this.dayWeek = dayWeek;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
    public void addExercise(Exercise exercise){
        exerciseList.add(exercise);    
    }
    
    @Override
    public String toString(){
        return String.format("\nTREINO\nNome: %s | Dia: %s | Usuario: %s",getName(), getDayWeek(), getUsuario().getName());
    }
    
}
