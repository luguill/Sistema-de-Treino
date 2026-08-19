/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.User;

/**
 *
 * @author Luiz Guilherme
 */
public interface UserDAO {
    public int inserir(User usuario);
    public int editar(User usuario);
    public int apagar(int id);
    public List<User> listar();
    public User listar(int id);
    public User buscarPorId(int id);
}
