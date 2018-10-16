
package Presentacion.Liquidaciones;

import Dominio.Retencion;
import Logica.LogCodigo;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModalModRetencion extends javax.swing.JDialog {
    private Retencion ret;
    private LogCodigo log;
    private InternalRetencionesFijas inte;
    public ModalModRetencion(java.awt.Frame parent, boolean modal) throws ClassNotFoundException, SQLException {
        super(parent, modal);
        initComponents();
        this.ret=InternalRetencionesFijas.instancia().getRet();
        this.log=InternalRetencionesFijas.instancia().getLogs();
        this.inte=InternalRetencionesFijas.instancia();
        this.setLocationRelativeTo(null);
        this.cargarRetencion();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        txtValor = new org.edisoncor.gui.textField.TextFieldRound();
        btnListar1 = new org.edisoncor.gui.button.ButtonIcon();
        jLabel8 = new javax.swing.JLabel();
        radioImporte = new javax.swing.JRadioButton();
        radioPorcentaje = new javax.swing.JRadioButton();
        checkSueldo = new javax.swing.JCheckBox();
        checkAgui = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCod = new javax.swing.JLabel();
        lblFunc = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modificar Retención Fija");
        setPreferredSize(new java.awt.Dimension(500, 318));
        setResizable(false);

        txtValor.setBackground(new java.awt.Color(102, 153, 255));
        txtValor.setForeground(new java.awt.Color(255, 255, 255));
        txtValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValor.setCaretColor(new java.awt.Color(255, 255, 255));
        txtValor.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorKeyTyped(evt);
            }
        });

        btnListar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/new.png"))); // NOI18N
        btnListar1.setText("buttonIcon1");
        btnListar1.setToolTipText("Modificar");
        btnListar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListar1ActionPerformed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Valor");

        buttonGroup2.add(radioImporte);
        radioImporte.setText("Importe");
        radioImporte.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioImporteItemStateChanged(evt);
            }
        });

        buttonGroup2.add(radioPorcentaje);
        radioPorcentaje.setText("Porcentaje");
        radioPorcentaje.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioPorcentajeItemStateChanged(evt);
            }
        });
        radioPorcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPorcentajeActionPerformed(evt);
            }
        });

        checkSueldo.setText("Sueldo");

        checkAgui.setText("Aguinaldo y Vacacional");

        jLabel2.setText("Funcionario: ");

        jLabel1.setText("Código:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 424, Short.MAX_VALUE)
                        .addComponent(btnListar1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblFunc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(radioImporte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioPorcentaje))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkSueldo)
                        .addGap(21, 21, 21)
                        .addComponent(checkAgui)))
                .addGap(121, 121, 121))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFunc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkAgui)
                    .addComponent(checkSueldo))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioImporte)
                    .addComponent(radioPorcentaje))
                .addGap(29, 29, 29)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListar1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyTyped
        int k = (int) evt.getKeyChar();
        String valor = this.txtValor.getText();
        if(k==10){
            this.btnListar1.doClick();
        }
        if(!valor.contains(".")){
            if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==45||k==8||k==47||k==42||k==43)  {
                evt.consume();
            }
        }
        else{
            if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==46 || k==32||k==45||k==8||k==47||k==42||k==43)  {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtValorKeyTyped

    private void btnListar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListar1ActionPerformed
    
        try {
            Retencion r=new Retencion();
            String valor=this.txtValor.getText();
            valor=valor.replace(",", "");
            r.setCod(ret.getCod());
            r.setFunc(ret.getFunc());
            if(this.checkSueldo.isSelected()){
                r.setSueldo(1);
            }
            else{
                r.setSueldo(0);
            }
            if(this.checkAgui.isSelected()){
                r.setOtros(1);
            }
            else{
                r.setOtros(0);
            }
           
            if(this.radioImporte.isSelected()){
                r.setTipo(0);
                r.setPorcentaje(0.0);
                r.setImporte(Double.valueOf(valor));
            }
            else{
                r.setTipo(1);
                Double por=Double.valueOf(valor);
                if(por<1){
                    r.setPorcentaje(por);
                }
                else{
                    r.setPorcentaje(por/100);
                }
                r.setImporte(0.0);
            }

            if(this.log.actualizaRetencion(r)){
                this.dispose();
                this.inte.setRetMod(r);
                this.inte.getBtnListar().doClick();

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModalModRetencion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ModalModRetencion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnListar1ActionPerformed

    private void radioImporteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioImporteItemStateChanged
        this.txtValor.setText("");
        this.txtValor.requestFocus();
    }//GEN-LAST:event_radioImporteItemStateChanged

    private void radioPorcentajeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioPorcentajeItemStateChanged
        this.txtValor.setText("");
        this.txtValor.requestFocus();
    }//GEN-LAST:event_radioPorcentajeItemStateChanged

    private void radioPorcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPorcentajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioPorcentajeActionPerformed

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
            java.util.logging.Logger.getLogger(ModalModRetencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModalModRetencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModalModRetencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModalModRetencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ModalModRetencion dialog = new ModalModRetencion(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ModalModRetencion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ModalModRetencion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
     private void cargarRetencion() {
        this.lblCod.setText(this.ret.getCod().toString());
        this.lblFunc.setText(this.ret.getFunc().getCodFunc()+ " - "+this.ret.getFunc().getNombre1());
        if(this.ret.getSueldo()==1){
            this.checkSueldo.setSelected(true);
        }
        if(this.ret.getOtros()==1){
            this.checkAgui.setSelected(true);
        }
       
        if(ret.getTipo()==1){
            this.radioPorcentaje.setSelected(true);
            this.txtValor.setText(ret.getPorcentaje().toString());
        }
        else{
            this.radioImporte.setSelected(true);
            this.txtValor.setText(this.decimales(ret.getImporte()));
        }
        this.txtValor.selectAll();
    }
     
   private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnListar1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox checkAgui;
    private javax.swing.JCheckBox checkSueldo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblFunc;
    private javax.swing.JRadioButton radioImporte;
    private javax.swing.JRadioButton radioPorcentaje;
    private org.edisoncor.gui.textField.TextFieldRound txtValor;
    // End of variables declaration//GEN-END:variables
}
