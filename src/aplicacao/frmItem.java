/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplicacao;

/**
 *
 * @author Y
 */
import modelo.Categoria;
import modelo.Item;
import dao.ItemDAO;
import dao.DAOFactory;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.util.Date;

public class frmItem extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(frmItem.class.getName());
    private Item item;
    private ItemDAO itemDAO = DAOFactory.criarItemDAO();
    private JDateChooser dateValidade;
   /**
     * Creates new form frmItem
     */
    public frmItem() {
        initComponents();
        
        
        String[] categorias = {Categoria.ARMARIO.getValor(), Categoria.GELADEIRA.getValor()};
        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(categorias));
        cbCategoria.setSelectedItem(null);

        // Só são validas datas presentes ou futuras
        jdcValidade.setMinSelectableDate(new Date());
    }
    public frmItem(Item item) {
        initComponents();

        this.item = item;

        String[] categorias = {Categoria.ARMARIO.getValor(), Categoria.GELADEIRA.getValor()};
        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(categorias));
        jdcValidade.setMinSelectableDate(new Date());

        if (this.item != null) {
            btnAdicionar.setText("Editar item");
            
            tfNome.setText(item.getNome() != null ? item.getNome() : "");
            cbCategoria.setSelectedItem(item.getCategoria().getValor());
            jsQuantidade.setValue(item.getQuantidade());
            if (item.getValidade() != null) {
                jdcValidade.setDate(new java.util.Date(item.getValidade().getTime()));
            }

        } else {

            btnAdicionar.setText("Adicionar item");

        }
    }
    
    
    
    private void inserir() {
    if (tfNome.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Digite o nome do item.");
        tfNome.requestFocus();
        return;
    }
    Item itemInserido = new Item();
    
    itemInserido.setNome(tfNome.getText());

    String catString = (String) cbCategoria.getSelectedItem();
    Categoria categoriaSelecionada = Categoria.fromString(catString);
    itemInserido.setCategoria(categoriaSelecionada);

    itemInserido.setQuantidade((int) jsQuantidade.getValue());

    java.util.Date data = jdcValidade.getDate();
    java.util.Date hoje = new java.util.Date(); // current date and time

    if (data == null || data.before(hoje)) {
        JOptionPane.showMessageDialog(this,"Data de validade é inválida.\nSelecione uma data a partir de " 
        + new java.text.SimpleDateFormat("dd/MM/yyyy").format(hoje));
        jdcValidade.requestFocus();
        return;
    }
    itemInserido.setValidade(new java.sql.Date(data.getTime()));

    int linha = itemDAO.inserir(itemInserido);

    if (linha > 0) {
        JOptionPane.showMessageDialog(this, "Item inserido com sucesso!");
    } else {
        JOptionPane.showMessageDialog(this, "Erro ao inserir item.");
            }
    }
    
    
    private void editar() {
        if (tfNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome do item.");
            tfNome.requestFocus();
            return;
        }
        
        java.util.Date data = jdcValidade.getDate();
        if (data == null) {
            JOptionPane.showMessageDialog(this,
                "Data de validade é inválida.");
            jdcValidade.requestFocus();
            return;
        }
        // Criar novo objeto para envio ao DAO
        Item itemEditado = new Item();
        itemEditado.setId(item.getId()); //ID do item original

        // Atualizar campos
        itemEditado.setNome(tfNome.getText());

        String catString = (String) cbCategoria.getSelectedItem();
        Categoria categoriaSelecionada = Categoria.fromString(catString);
        itemEditado.setCategoria(categoriaSelecionada);

        itemEditado.setQuantidade((int) jsQuantidade.getValue());

        itemEditado.setValidade(new java.sql.Date(data.getTime()));

        // Enviar ao DAO para atualizar
        int linha = itemDAO.editar(itemEditado);

        if (linha > 0) {
            JOptionPane.showMessageDialog(this, "Item editado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao editar item.");
        }
    }
    
    private void cancelar() {
    int resposta = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente sair?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION);

    if (resposta == JOptionPane.YES_OPTION) {
        dispose();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jsQuantidade = new javax.swing.JSpinner();
        tfNome = new javax.swing.JTextField();
        jdcValidade = new com.toedter.calendar.JDateChooser();
        btnAdicionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        jLabel1.setText("Nome:");

        cbCategoria.addActionListener(this::cbCategoriaActionPerformed);

        jLabel3.setText("Quantidade:");

        jLabel2.setText("Categoria:");

        jLabel4.setText("Validade:");

        jsQuantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));

        tfNome.addActionListener(this::tfNomeActionPerformed);

        jdcValidade.setDateFormatString("dd/MM/yyyy");

        btnAdicionar.setText("Adicionar Item");
        btnAdicionar.addActionListener(this::btnAdicionarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jsQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdcValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addGap(37, 37, 37))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAdicionar, btnCancelar});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jsQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jdcValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnCancelar))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCategoriaActionPerformed

    private void tfNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomeActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        if (item != null) {
            editar();
        } else {
            inserir();
        }
        dispose(); 
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new frmItem().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jdcValidade;
    private javax.swing.JSpinner jsQuantidade;
    private javax.swing.JTextField tfNome;
    // End of variables declaration//GEN-END:variables
}
