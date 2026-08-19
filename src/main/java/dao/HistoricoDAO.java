/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Historico;

/**
 *
 * @author Luiz Guilherme
 */
public interface HistoricoDAO {
     public int inserir(Historico historico);
    public int editar(Historico historico);
    public int apagar(int id);
    public List<Historico> listar();
    public Historico listar(int id);
}
