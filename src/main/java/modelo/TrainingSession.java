/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author Luiz Guilherme
 */
public class TrainingSession {
    private int id;
    private Training treino;
    private LocalDate data;
    private int duracao;
    
    public TrainingSession(int id, Training treino, LocalDate data, int duracao){
        this.id = id;
        this.treino = treino;
        this.data = data;
        this.duracao = duracao;        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Training getTreino() {
        return treino;
    }

    public void setTreino(Training treino) {
        this.treino = treino;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }    
}
