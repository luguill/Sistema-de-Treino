/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import modelo.Training;

/**
 *
 * @author Luiz Guilherme
 */
public interface TrainingDAO {
    public int inserir(Training treino);
    public int editar(Training treino);
    public int apagar(int id);
    public List<Training> listar();
    public Training listar(int id);
    public Training buscarPorId(int id);
    
}
