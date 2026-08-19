/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Exercise;

/**
 *
 * @author Luiz Guilherme
 */
public interface ExerciseDAO {
    public int inserir(Exercise exercicio);
    public int editar(Exercise exercicio);
    public int apagar(int id);
    public List<Exercise> listar();
    public Exercise listar(int id);
    public Exercise buscarPorId(int id);
}
