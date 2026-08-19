/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package aplicacao;

import dao.DAOFactory;
import dao.ExerciseDAO;
import dao.TrainingDAO;
import dao.TrainingExerciseDAO;
import dao.UserDAO;
import enums.DayWeek;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import modelo.ExercicioIsometrico;
import modelo.Exercise;
import modelo.ExerciseCardio;
import modelo.ExerciseRepetition;
import modelo.Training;
import modelo.User;

/**
 *
 * @author Luiz Guilherme
 */
public class frmTreino extends javax.swing.JDialog {

    
    Training treino;
    TrainingDAO treinoDao = DAOFactory.criarTrainingDAO();
    UserDAO usuarioDao = DAOFactory.criarUserDAO();
    ExerciseDAO exercicioDao = DAOFactory.criarExerciseDAO();
    TrainingExerciseDAO treinoExercicioDao = DAOFactory.criarTrainingExerciseDAO();
    List<Exercise> exerciciosSelecionados = new ArrayList();
    /**
     * Creates new form frmTreino
     */
    public frmTreino(java.awt.Frame parent, boolean modal, Training treino) {
        super(parent, modal);
        initComponents();
        
        this.treino = treino;
        
        if (this.treino != null){
            btnOk.setText("Editar");
             txtNome.setText(treino.getName());
        } else {
            btnOk.setText("Inserir");
        }
        
        cmbDia.setModel(new DefaultComboBoxModel<String>(
            Arrays.stream(DayWeek.values()).map(Enum::name).toArray(String[]::new)
        ));
        
        List<User> usuarios = usuarioDao.listar();
        cmbUsuario.setModel(new DefaultComboBoxModel<User>(usuarios.toArray(new User[0])));
        cmbUsuario.setEditable(true);
    
        JTextField campoTexto = (JTextField) cmbUsuario.getEditor().getEditorComponent();
        campoTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER ||
                    evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP ||
                    evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN ||
                    evt.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE ||
                    evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    return;
                }

                String texto = campoTexto.getText().toLowerCase();

                List<User> filtrados = usuarios.stream()
                    .filter(u -> u.getName().toLowerCase().contains(texto))
                    .toList();

                DefaultComboBoxModel<User> modeloFiltrado = new DefaultComboBoxModel<>(filtrados.toArray(new User[0]));
                cmbUsuario.setModel(modeloFiltrado);
                cmbUsuario.setSelectedItem(null);
                campoTexto.setText(texto);
                cmbUsuario.showPopup();
                
            }
        });

        if (this.treino != null && this.treino.getUsuario() != null) {
            cmbUsuario.setSelectedItem(this.treino.getUsuario());
        }
        
        List<Exercise> exercicios = exercicioDao.listar();
        
        cmbExercicio.setModel(new DefaultComboBoxModel<>(exercicios.toArray(new Exercise[0])));

        if (this.treino != null) {
            List<Exercise> exerciciosDoTreino = treinoExercicioDao.listarExercicios(this.treino.getId());

            exerciciosSelecionados.addAll(exerciciosDoTreino);
        }
  
        
    }
    
    private void cancelar() {
        int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente cancelar?", "Aviso", JOptionPane.YES_NO_OPTION);
        if (opcao == 0) {
            this.dispose();
        }
    }
    
       
     private void inserir() {
         Training treinoInserido = new Training();
         
         treinoInserido.setName(txtNome.getText());
         treinoInserido.setDayWeek(DayWeek.valueOf((String) cmbDia.getSelectedItem()));
         treinoInserido.setUsuario((User) cmbUsuario.getSelectedItem());
         
         int idTreino = treinoDao.inserir(treinoInserido);
    
        if (idTreino <= 0) {
            JOptionPane.showMessageDialog(this, "Erro ao inserir Local.");
            return;
        } 
        
        for (Exercise exercicio : exerciciosSelecionados){
             int resultado = treinoExercicioDao.inserir(idTreino, exercicio.getId());
         
        }
        JOptionPane.showMessageDialog(this, "Treino inserido com sucesso!");
     }
     
     private void editar() {
                
        Training treinoEditado = new Training();
        
         treinoEditado.setId(this.treino.getId());
         treinoEditado.setName(txtNome.getText());
         treinoEditado.setDayWeek(DayWeek.valueOf((String) cmbDia.getSelectedItem()));
         treinoEditado.setUsuario((User) cmbUsuario.getSelectedItem());
         
         int linha = treinoDao.editar(treinoEditado);
        if (linha > 0) {
            treinoExercicioDao.apagarPorTreino(treinoEditado.getId());
            
            for (Exercise exercicio : exerciciosSelecionados){
                treinoExercicioDao.inserir(treinoEditado.getId(), exercicio.getId());
            }

            JOptionPane.showMessageDialog(this, "Treino editado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao editar Local.");
        } 
     
     }
  
     private void adicionarExercicio(){
         Exercise exercicio = (Exercise) cmbExercicio.getSelectedItem();
         
         if (exercicio == null){
             JOptionPane.showMessageDialog(this,"Seleciona um exercício");
             return;
         }
         
         if (exerciciosSelecionados.contains(exercicio)){
             JOptionPane.showMessageDialog(this,"Esse exercício já foi adicionado ao treino.");
             return;
         }else {
              exerciciosSelecionados.add(exercicio);
         
         
              mostrarMensagem("Exercício adicionado!");
         }
         
         
                  
     }
     
          
     private void mostrarMensagem(String mensagem){
         lblMensagem.setText(mensagem);
         pnlMensagem.setVisible(true);
         
         Timer timer = new Timer(1500, e -> {pnlMensagem.setVisible(false);});
         
         timer.setRepeats(false);
         timer.start();     
     }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbDia = new javax.swing.JComboBox<>();
        btnOk = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmbUsuario = new javax.swing.JComboBox<>();
        lblTipo1 = new javax.swing.JLabel();
        lblAdicionarExercicio = new javax.swing.JLabel();
        cmbExercicio = new javax.swing.JComboBox<>();
        btnAdicionar = new javax.swing.JButton();
        pnlMensagem = new javax.swing.JPanel();
        lblMensagem = new javax.swing.JLabel();

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNome.setText("Nome:");

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        lblTipo.setText("Dia da semana:");

        cmbDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDiaActionPerformed(evt);
            }
        });

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cmbUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUsuarioActionPerformed(evt);
            }
        });

        lblTipo1.setText("Usuário:");

        lblAdicionarExercicio.setText("Adicionar exercício:");

        cmbExercicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbExercicioActionPerformed(evt);
            }
        });

        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMensagemLayout = new javax.swing.GroupLayout(pnlMensagem);
        pnlMensagem.setLayout(pnlMensagemLayout);
        pnlMensagemLayout.setHorizontalGroup(
            pnlMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMensagemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlMensagemLayout.setVerticalGroup(
            pnlMensagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMensagemLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTipo1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNome)
                                    .addComponent(cmbUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAdicionarExercicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmbExercicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(btnAdicionar)))
                                .addGap(108, 108, 108))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnOk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo1)
                    .addComponent(cmbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblAdicionarExercicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbExercicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdicionar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(pnlMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnOk))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void cmbDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDiaActionPerformed
        
    }//GEN-LAST:event_cmbDiaActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (treino != null) {
            try{
                editar();
                dispose();
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Verifique os campos numéricos: não são aceitos valores com ponto flutuante (EX.: 4,0 OU 4.0)", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try{
                inserir();
                dispose();
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Verifique os campos numéricos:  não são aceitos valores com ponto flutuante (EX.: 4,0 OU 4.0)", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUsuarioActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        adicionarExercicio();
        mostrarMensagem("Exercício Adicionado!!");
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void cmbExercicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbExercicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbExercicioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmTreino dialog = new frmTreino(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<modelo.Exercise> cmbExercicio;
    private javax.swing.JComboBox<modelo.User> cmbUsuario;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblAdicionarExercicio;
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTipo1;
    private javax.swing.JPanel pnlMensagem;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
