/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Luiz Guilherme
 */

import enums.Level;
import java.lang.Math;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private int age;
    private double weight;
    private double height;
    private Level level;
    
    public User (int id, String name, String email, String password, int age, double weight, double height, Level level){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.level = level;
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
        if (name == null || name.trim().isEmpty()){
             throw new IllegalArgumentException("O nome do usuario não pode estar vazio."); 
        }else{
            this.name = name;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    
    public double calculateBMI(){ 
        return this.weight/Math.pow(this.height, 2.0);
    
    }
    
    @Override
    public String toString(){
        return String.format("ID: %d | Nome: %s | Idade: %d\nE-mail: %s | Peso: %.2f | Altura: %.2f\nNível: %s | IMC: %.2f", id, name, age, email, weight, height, level, calculateBMI());
    }
    
    
}
