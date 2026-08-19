/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Historico;
import modelo.Training;
import modelo.User;

/**
 *
 * @author Luiz Guilherme
 */
public class HistoricoDAOJDBC implements HistoricoDAO{
    Connection conexao = null;
    PreparedStatement sql = null;
    ResultSet rset = null;
    
    @Override
    public int inserir(Historico historico) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO history(id, user_id, training_id , training_date, observations) ")
                .append("VALUES (?, ?, ?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {          
            linha = DAOGenerico.executarComando(insert, historico.getId(), historico.getUsuario().getId(), historico.getTreino().getId(), historico.getData(), historico.getObservacoes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }

    @Override
    public int editar(Historico historico) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE history SET ")
                .append("user_id = ?, ")
                .append("training_id = ?, ")
                .append("training_date = ?, ")
                .append("observations = ? ")
                .append("WHERE id = ?");
                        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            
            linha = DAOGenerico.executarComando(update, historico.getId(), historico.getUsuario().getId(), java.sql.Date.valueOf(historico.getData()), historico.getObservacoes(), historico.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public int apagar(int codigo) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM history ")
                .append("WHERE id = ?");
        
        String delete = sqlBuilder.toString();
        int linha = 0;
        try {         
            linha = DAOGenerico.executarComando(delete, codigo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public List<Historico> listar() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT h.id, h.user_id, u.name AS user_name, h.training_id, t.name AS training_name, h.training_date, h.observations ")
                .append("FROM history h ")
                .append("INNER JOIN users u ON h.user_id = u.id ")
                .append("INNER JOIN training t ON h.training_id = t.id ")
                .append("ORDER BY h.id");        

        String select = sqlBuilder.toString();

        List<Historico> historicos = new ArrayList<Historico>();

        try {       
            rset = DAOGenerico.executarConsulta(select);

            while (rset.next()) {

                Historico historico = new Historico();
                historico.setId(rset.getInt("id"));
                
               
                User usuario = new User();
                usuario.setId(rset.getInt("user_id"));
                usuario.setName(rset.getString("user_name"));
                
                historico.setUsuario(usuario);
                
                Training treino = new Training();
                treino.setId(rset.getInt("training_id"));
                treino.setName(rset.getString("training_name"));
                historico.setTreino(treino);
                
                
                historico.setData(rset.getDate("training_date").toLocalDate());
                historico.setObservacoes(rset.getString("observations"));
                
                historicos.add(historico);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return historicos;
    }

    @Override
    public Historico listar(int id) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT h.id, h.user_id, h.training_id, h.training_date, h.observations ")
                .append("FROM history h ")
                .append("INNER JOIN users u ON (h.user_id = u.id) ")
                .append("INNER JOIN training t ON (h.training_id = t.id) ")
                .append("WHERE h.id = ?");
        String select = sqlBuilder.toString();
        
        Historico historico = null;

        try {       
            rset = DAOGenerico.executarConsulta(select, id);


            while (rset.next()) {

                historico = new Historico();
                historico.setId(rset.getInt("id"));
                
                historico.setData(rset.getDate("training_date").toLocalDate());
                historico.setObservacoes(rset.getString("observations"));
                
                User usuario = new User();
                usuario.setId(rset.getInt("user_id"));
                
                historico.setUsuario(usuario);
                
                Training treino = new Training();
                treino.setId(rset.getInt("training_id"));
                
                historico.setTreino(treino);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return historico;        
    }
    
    private void fecharConexao() {
        try {
            if (rset != null) {
                rset.close();
            }
            if (sql != null) {
                sql.close();
            }

            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
