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
public class Historico {
    private int id;
    private User usuario;
    private LocalDate data;
    private String observacoes;
    
    public Historico(int id, User usuario, LocalDate data, String observacoes){
        this.id = id;
        this.usuario = usuario;
        this.data = data;
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    @Override
    public String toString(){
        return String.format("\nHISTORICO\n %s | Data: %s | Observacoes: %s", getUsuario().getName(), getData(), getObservacoes());
    }
    
}
