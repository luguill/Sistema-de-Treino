/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package aplicacao;

import dao.DAOFactory;
import dao.ExerciseDAO;
import enums.ExerciseType;
import enums.MuscleGroups;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.ExercicioIsometrico;
import modelo.Exercise;
import modelo.ExerciseCardio;
import modelo.ExerciseRepetition;

/**
 *
 * @author Luiz Guilherme
 */
public class frmExercicio extends javax.swing.JDialog {

    Exercise exercicio;
    ExerciseDAO exercicioDao = DAOFactory.criarExerciseDAO();
    /**
     * Creates new form frmExercicio
     */
    public frmExercicio(java.awt.Frame parent, boolean modal, Exercise exercicio) {
        super(parent, modal);
        initComponents();
        
        this.exercicio = exercicio;
        
        if (this.exercicio != null){
            btnOk.setText("Editar");
             txtNome.setText(exercicio.getName());
        } else {
            btnOk.setText("Inserir");
        }
        
        pnlCardio.setVisible(false);
        pnlRepeticao.setVisible(false);
        pnlIsometrico.setVisible(false);

        cmbTipo.setModel(new DefaultComboBoxModel<String>(
            Arrays.stream(ExerciseType.values()).map(Enum::name).toArray(String[]::new)
        ));
        
        cmbGrupoMuscular.setModel(new DefaultComboBoxModel<String>(
            Arrays.stream(MuscleGroups.values()).map(Enum::name).toArray(String[]::new)
        ));
        
        if (this.exercicio != null) {
            btnOk.setText("Editar");
            txtNome.setText(exercicio.getName());
            cmbTipo.setSelectedItem(exercicio.getType().name());
            cmbGrupoMuscular.setSelectedItem(exercicio.getMuscle().name()); 

            if (exercicio instanceof ExerciseRepetition rep) {
                pnlRepeticao.setVisible(true);
                txtSeries.setText(String.valueOf(rep.getSets()));
                txtRepeticoes.setText(String.valueOf(rep.getRepetition()));
            } else if (exercicio instanceof ExerciseCardio cardio) {
                pnlCardio.setVisible(true);
                txtDistancia.setText(String.valueOf(cardio.getDistancia()));
                txtDuracao.setText(String.valueOf(cardio.getDuracao()));
            } else if (exercicio instanceof ExercicioIsometrico iso) {
                pnlIsometrico.setVisible(true);
                txtTempo.setText(String.valueOf(iso.getTempoSegundos()));
            }
        } else {
            btnOk.setText("Inserir");
        }
        
    }
    
     private void cancelar() {
        int opcao = JOptionPane.showConfirmDialog(this, "Deseja realmente cancelar?", "Aviso", JOptionPane.YES_NO_OPTION);
        if (opcao == 0) {
            this.dispose();
        }
    }
     
    private void inserir() {
        
            Exercise exercicioInserido;
            
            String tipoSelecionado = (String) cmbTipo.getSelectedItem();
            ExerciseType tipo = ExerciseType.valueOf(tipoSelecionado);
       
           switch (tipo){
                case REPETICAO:
                     ExerciseRepetition rep = new ExerciseRepetition();

                     rep.setSets(Integer.parseInt(txtSeries.getText()));
                     rep.setRepetition(Integer.parseInt(txtRepeticoes.getText()));

                     exercicioInserido = rep;
                     break;
                case CARDIO:
                     ExerciseCardio cardio = new ExerciseCardio();

                     cardio.setDistancia(Double.parseDouble(txtDistancia.getText()));
                     cardio.setDuracao(Double.parseDouble(txtDuracao.getText()));

                     exercicioInserido = cardio;
                     break;
                case ISOMETRICO:
                     ExercicioIsometrico iso = new ExercicioIsometrico();

                     iso.setTempoSegundos(Double.parseDouble(txtTempo.getText()));   

                     exercicioInserido = iso;
                     break;   
                default:
                    throw new IllegalStateException("Tipo de exercício não reconhecido");
            }
           
           exercicioInserido.setName(txtNome.getText());
           exercicioInserido.setDescription(txtDescricao.getText());
           exercicioInserido.setMuscle(MuscleGroups.valueOf((String) cmbGrupoMuscular.getSelectedItem()));           
           exercicioInserido.setType(ExerciseType.valueOf((String) cmbTipo.getSelectedItem()));
            int linha = exercicioDao.inserir(exercicioInserido);
        
              
 
        if (linha > 0) {
            JOptionPane.showMessageDialog(this, "Exercício inserido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao inserir Local.");
        } 
     }
     
    private void editar() {
        Exercise exercicioEditado;
        
        String tipoSelecionado = (String) cmbTipo.getSelectedItem();
        ExerciseType tipo = ExerciseType.valueOf(tipoSelecionado);
        
       switch (tipo){
            case REPETICAO:
                ExerciseRepetition rep = new ExerciseRepetition();

                rep.setSets(Integer.parseInt(txtSeries.getText()));
                rep.setRepetition(Integer.parseInt(txtRepeticoes.getText()));

                exercicioEditado = rep;
                break;
            case CARDIO:
                ExerciseCardio cardio = new ExerciseCardio();

                cardio.setDistancia(Double.parseDouble(txtDistancia.getText()));
                cardio.setDuracao(Double.parseDouble(txtDuracao.getText()));

                exercicioEditado = cardio;
                break;
            case ISOMETRICO:
                ExercicioIsometrico iso = new ExercicioIsometrico();

                iso.setTempoSegundos(Double.parseDouble(txtTempo.getText()));   

                exercicioEditado = iso;
                break;   
            default:
                throw new IllegalStateException("Tipo de exercício não reconhecido");
        }
        exercicioEditado.setId(exercicio.getId());
        exercicioEditado.setName(txtNome.getText());
        exercicioEditado.setDescription(txtDescricao.getText());
        exercicioEditado.setType(tipo);
        exercicioEditado.setMuscle(MuscleGroups.valueOf((String) cmbGrupoMuscular.getSelectedItem()));
        int linha = exercicioDao.editar(exercicioEditado);
        if (linha > 0) {
            JOptionPane.showMessageDialog(this, "Exercício editado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao editar Local.");
        } 
     
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTipo = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        txtNome = new javax.swing.JTextField();
        cmbTipo = new javax.swing.JComboBox<>();
        lblNome = new javax.swing.JLabel();
        lblGrupoMuscular = new javax.swing.JLabel();
        cmbGrupoMuscular = new javax.swing.JComboBox<>();
        txtDescricao = new javax.swing.JTextField();
        lblDescricao = new javax.swing.JLabel();
        pnlRepeticao = new javax.swing.JPanel();
        lblSeries = new javax.swing.JLabel();
        lblRepeticoes = new javax.swing.JLabel();
        txtSeries = new javax.swing.JTextField();
        txtRepeticoes = new javax.swing.JTextField();
        pnlIsometrico = new javax.swing.JPanel();
        lblTempo = new javax.swing.JLabel();
        txtTempo = new javax.swing.JTextField();
        lblSegundos = new javax.swing.JLabel();
        pnlCardio = new javax.swing.JPanel();
        lblDistancia = new javax.swing.JLabel();
        lblDuracao = new javax.swing.JLabel();
        txtDistancia = new javax.swing.JTextField();
        txtDuracao = new javax.swing.JTextField();
        lblKm = new javax.swing.JLabel();
        lblMinutos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTipo.setText("Tipo:");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoActionPerformed(evt);
            }
        });

        lblNome.setText("Nome:");

        lblGrupoMuscular.setText("Grupo Muscular:");

        cmbGrupoMuscular.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbGrupoMuscular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbGrupoMuscularActionPerformed(evt);
            }
        });

        txtDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoActionPerformed(evt);
            }
        });

        lblDescricao.setText("Descrição:");

        lblSeries.setText("Séries:");

        lblRepeticoes.setText("Repetições:");

        javax.swing.GroupLayout pnlRepeticaoLayout = new javax.swing.GroupLayout(pnlRepeticao);
        pnlRepeticao.setLayout(pnlRepeticaoLayout);
        pnlRepeticaoLayout.setHorizontalGroup(
            pnlRepeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRepeticaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRepeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRepeticoes)
                    .addComponent(lblSeries, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlRepeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtRepeticoes)
                    .addComponent(txtSeries, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(244, Short.MAX_VALUE))
        );
        pnlRepeticaoLayout.setVerticalGroup(
            pnlRepeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRepeticaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRepeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSeries)
                    .addComponent(txtSeries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlRepeticaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRepeticoes)
                    .addComponent(txtRepeticoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlIsometrico.setPreferredSize(new java.awt.Dimension(400, 63));

        lblTempo.setText("Tempo:");

        txtTempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTempoActionPerformed(evt);
            }
        });

        lblSegundos.setText("segundos");

        javax.swing.GroupLayout pnlIsometricoLayout = new javax.swing.GroupLayout(pnlIsometrico);
        pnlIsometrico.setLayout(pnlIsometricoLayout);
        pnlIsometricoLayout.setHorizontalGroup(
            pnlIsometricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIsometricoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTempo)
                .addGap(18, 18, 18)
                .addComponent(txtTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(186, Short.MAX_VALUE))
        );
        pnlIsometricoLayout.setVerticalGroup(
            pnlIsometricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIsometricoLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(pnlIsometricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSegundos)
                    .addComponent(txtTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTempo))
                .addGap(19, 19, 19))
        );

        lblDistancia.setText("Distância:");

        lblDuracao.setText("Duração:");

        lblKm.setText("km");

        lblMinutos.setText("min");

        javax.swing.GroupLayout pnlCardioLayout = new javax.swing.GroupLayout(pnlCardio);
        pnlCardio.setLayout(pnlCardioLayout);
        pnlCardioLayout.setHorizontalGroup(
            pnlCardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDuracao)
                    .addComponent(lblDistancia))
                .addGap(18, 18, 18)
                .addGroup(pnlCardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDuracao)
                    .addComponent(txtDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMinutos)
                    .addComponent(lblKm, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        pnlCardioLayout.setVerticalGroup(
            pnlCardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKm)
                    .addGroup(pnlCardioLayout.createSequentialGroup()
                        .addGroup(pnlCardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDistancia)
                            .addComponent(txtDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlCardioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDuracao)
                            .addComponent(txtDuracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMinutos))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRepeticao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlCardio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlIsometrico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGrupoMuscular)
                            .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDescricao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbGrupoMuscular, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNome)
                            .addComponent(txtDescricao))))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGrupoMuscular)
                    .addComponent(cmbGrupoMuscular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnlCardio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlRepeticao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlIsometrico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnOk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (exercicio != null) {
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

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed
        ExerciseType tipo = ExerciseType.valueOf((String)cmbTipo.getSelectedItem());
        
        pnlCardio.setVisible(false);
        pnlRepeticao.setVisible(false);
        pnlIsometrico.setVisible(false);
        
        switch (tipo){
            case REPETICAO:
                 pnlRepeticao.setVisible(true);
                 break;
            case CARDIO:
                 pnlCardio.setVisible(true);
                 break;
            case ISOMETRICO:
                 pnlIsometrico.setVisible(true);
                 break;   
        }
        
    }//GEN-LAST:event_cmbTipoActionPerformed

    private void cmbGrupoMuscularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbGrupoMuscularActionPerformed
       
    }//GEN-LAST:event_cmbGrupoMuscularActionPerformed

    private void txtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescricaoActionPerformed

    private void txtTempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTempoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTempoActionPerformed

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
            java.util.logging.Logger.getLogger(frmExercicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmExercicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmExercicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmExercicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmExercicio dialog = new frmExercicio(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cmbGrupoMuscular;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblDistancia;
    private javax.swing.JLabel lblDuracao;
    private javax.swing.JLabel lblGrupoMuscular;
    private javax.swing.JLabel lblKm;
    private javax.swing.JLabel lblMinutos;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblRepeticoes;
    private javax.swing.JLabel lblSegundos;
    private javax.swing.JLabel lblSeries;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel pnlCardio;
    private javax.swing.JPanel pnlIsometrico;
    private javax.swing.JPanel pnlRepeticao;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtDistancia;
    private javax.swing.JTextField txtDuracao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtRepeticoes;
    private javax.swing.JTextField txtSeries;
    private javax.swing.JTextField txtTempo;
    // End of variables declaration//GEN-END:variables
}
