/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplicacao;

import conexao.ConexaoMySQL;
import dao.ItemDAO;
import dao.DAOFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Item;
import java.sql.Connection;
import java.awt.event.ItemEvent;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Categoria;

/**
 *
 * @author Y
 */
        
public class frmCategorizado extends javax.swing.JFrame {
    
    Connection conexao = null;
    ItemDAO itemDAO = DAOFactory.criarItemDAO();
    private DefaultTableModel modelo = null;
    private Categoria categoriaFiltro;
    
    public frmCategorizado(){
    }
    public frmCategorizado(Categoria categoriaSelec) {
        try {
            conexao = ConexaoMySQL.getConexao();
        } catch (Exception ex) {
            lblMensagem.setText("Sem conexão com o BD!");
            Logger.getLogger(frmTodosItens.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.categoriaFiltro = categoriaSelec;
        initComponents();
        tblItem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelo = (DefaultTableModel) tblItem.getModel();

        // Populate category combo dynamically
        cbCategorias.setModel(new DefaultComboBoxModel<>(new String[]{
            Categoria.ARMARIO.getValor(),
            Categoria.GELADEIRA.getValor()
        }));
        cbCategorias.setSelectedItem(categoriaFiltro.getValor());

        preencherTabela();
        attTituloIcone(categoriaFiltro);

        cbCategorias.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selecionado = (String) cbCategorias.getSelectedItem();
                categoriaFiltro = Categoria.fromString(selecionado);
                preencherTabela();
                attTituloIcone(categoriaFiltro);
            }
        });
    } 
    
    private void preencherTabela() {
        modelo.setRowCount(0);
        try {
            for (Item item : itemDAO.listar()) {
                if (item.getCategoria() == categoriaFiltro) {
                    modelo.addRow(new Object[]{
                        item.getId(),
                        item.getNome(),
                        item.getCategoria(),
                        item.getQuantidade(),
                        item.getValidade()
                    });
                }
            }
        } catch (Exception e) {
            //throw e;
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void attTituloIcone(Categoria categoria) {
        setTitle("Itens em " + categoriaFiltro.getValor() + ":");

        // Change the window icon dynamically
        String iconePath;
        switch (categoriaFiltro) {
            case ARMARIO -> iconePath = "/recurso/cabinet.png";
            case GELADEIRA -> iconePath = "/recurso/fridge.png";
            default -> iconePath = "/recurso/icone.png";
        }
        setIconImage(new javax.swing.ImageIcon(getClass().getResource(iconePath)).getImage());
    }
    
    
    private void apagar() {
        try {
            Integer id = (Integer) modelo.getValueAt(tblItem.getSelectedRow(), 0);

            int linha = itemDAO.apagar(id);

            if (linha > 0) {
                modelo.removeRow(tblItem.getSelectedRow());
                JOptionPane.showMessageDialog(this, "Item retirado de " + categoriaFiltro.getValor() + " com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao retirar item de " + categoriaFiltro.getValor());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para retirar.");
            }
    }
    
    private void editar() {
        int selectedRow = tblItem.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para editar.");
            return;
        }

        try {
            Integer id = (Integer) modelo.getValueAt(selectedRow, 0);
            String nome = (String) modelo.getValueAt(selectedRow, 1);
            Categoria categoria = (Categoria) modelo.getValueAt(selectedRow, 2);
            Integer quantidade = (Integer) modelo.getValueAt(selectedRow, 3);
            Date validadeUtil = (Date) modelo.getValueAt(selectedRow, 4);
            java.sql.Date validade = new java.sql.Date(validadeUtil.getTime());

            Item item = new Item();
            item.setId(id);
            item.setNome(nome);
            item.setCategoria(categoria);
            item.setQuantidade(quantidade);
            item.setValidade(validade);

            new frmItem(item).setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar item: " + e.getMessage());
            e.printStackTrace();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblItem = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        lblMensagem = new javax.swing.JLabel();
        cbCategorias = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        tblItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Categoria", "Quantidade", "Validade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblItem);

        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(this::btnAdicionarActionPerformed);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnRemover.setText("Remover");
        btnRemover.addActionListener(this::btnRemoverActionPerformed);

        lblMensagem.setForeground(new java.awt.Color(204, 0, 51));
        lblMensagem.setText(" ");

        cbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ARMARIO", "GELADEIRA", " " }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEditar)
                                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAdicionar, btnEditar, btnRemover, cbCategorias});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdicionar)
                        .addGap(55, 55, 55)
                        .addComponent(btnEditar)
                        .addGap(55, 55, 55)
                        .addComponent(btnRemover))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        new frmItem(null).setVisible(true);
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        apagar();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        preencherTabela();
    }//GEN-LAST:event_formWindowGainedFocus

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmCategorizado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCategorizado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCategorizado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCategorizado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCategorizado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cbCategorias;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMensagem;
    private javax.swing.JTable tblItem;
    // End of variables declaration//GEN-END:variables
}
