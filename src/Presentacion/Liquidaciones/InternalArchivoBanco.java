
package Presentacion.Liquidaciones;

import Dominio.LineaArchivoBanco;
import Logica.ArmoTxtxBanco;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class InternalArchivoBanco extends javax.swing.JInternalFrame {
    private LogFuncionario log;
    private LogCodigo logCod;
    private static InternalArchivoBanco instancia=null;
    public InternalArchivoBanco() throws SQLException, ClassNotFoundException {
        initComponents();
        log=new LogFuncionario();
        logCod=new LogCodigo();
        this.txtFechaLiq.setText(this.log.fechaLiquidacion());
        this.txtFechaLiq.setEditable(false);
        this.txtFechaInfo.requestFocus();
        this.btnTxt.setEnabled(false);
      
    }

    public static InternalArchivoBanco instancia() throws ClassNotFoundException, SQLException
    {    
         if (instancia== null)
         {
            instancia = new InternalArchivoBanco();
         }
         return instancia;
     
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtFechaLiq = new org.edisoncor.gui.textField.TextFieldRound();
        lblFechaLiq = new javax.swing.JLabel();
        txtFechaInfo = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        btnTxt = new org.edisoncor.gui.button.ButtonRound();
        lblMsj = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Archivos para Banco");
        setPreferredSize(new java.awt.Dimension(400, 500));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        txtFechaLiq.setBackground(new java.awt.Color(102, 153, 255));
        txtFechaLiq.setForeground(new java.awt.Color(255, 255, 255));
        txtFechaLiq.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaLiq.setCaretColor(new java.awt.Color(255, 255, 255));
        txtFechaLiq.setSelectionColor(new java.awt.Color(102, 102, 102));

        lblFechaLiq.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblFechaLiq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaLiq.setText("Fecha de Liquidación");

        txtFechaInfo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFechaInfo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaInfo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaInfoFocusLost(evt);
            }
        });
        txtFechaInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaInfoKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Fecha de Informado");

        btnTxt.setBackground(new java.awt.Color(204, 204, 204));
        btnTxt.setForeground(new java.awt.Color(0, 0, 0));
        btnTxt.setText("Generar Archivo Banco");
        btnTxt.setColorDeSombra(null);
        btnTxt.setPreferredSize(new java.awt.Dimension(99, 33));
        btnTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTxtActionPerformed(evt);
            }
        });
        btnTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnTxtKeyTyped(evt);
            }
        });

        lblMsj.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblFechaLiq, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(txtFechaLiq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(txtFechaInfo))
                .addGap(17, 17, 17))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(btnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblFechaLiq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaInfo)
                    .addComponent(txtFechaLiq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addComponent(btnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(393, 393, 393))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(242, 242, 242)
                    .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(243, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFechaInfoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaInfoFocusLost
        this.btnTxt.setEnabled(true);
    }//GEN-LAST:event_txtFechaInfoFocusLost

    private void txtFechaInfoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaInfoKeyTyped
        int k = (int) evt.getKeyChar();
        this.lblMsj.setText("");
        if(k==10){
             this.btnTxt.setEnabled(true);
             this.btnTxt.requestFocus();
        }
    }//GEN-LAST:event_txtFechaInfoKeyTyped

    private void btnTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTxtActionPerformed
        ArmoTxtxBanco txt = new ArmoTxtxBanco();
        String fecha = txtFechaInfo.getText();
        
            if(!fecha.equals("")){
                ArrayList<Object> liquidacion=this.logCod.cargoLiquidacionFecha(fecha);
                ArrayList<Object> liquidacionSinCuenta=this.logCod.cargoLiquidacionFechaSinCuenta(fecha);
                if(liquidacion.size()>0){
                    String str = txt.armoTxtValesBanco(this.stringADate(this.txtFechaLiq.getText()),this.stringADate(fecha),liquidacion,1,liquidacionSinCuenta);
                    this.lblMsj.setText(str);
                    if(liquidacionSinCuenta.size()==0){
                        JOptionPane.showMessageDialog(this, "No hay funcionarios SIN CUENTA para esta liquidacion y no se ha generado la planilla.");
                    }
                }
                else{
                    this.lblMsj.setText("No hay registros en la base de datos para esta fecha");
                }
            }
            else{
                this.lblMsj.setText("Seleccione una fecha de informado");
            }
        
       
    }//GEN-LAST:event_btnTxtActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTxtKeyTyped
       int k = (int) evt.getKeyChar();
       if(k==10){
             this.btnTxt.doClick();
        }
    }//GEN-LAST:event_btnTxtKeyTyped

       public static Date stringADate(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonRound btnTxt;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblFechaLiq;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JFormattedTextField txtFechaInfo;
    private org.edisoncor.gui.textField.TextFieldRound txtFechaLiq;
    // End of variables declaration//GEN-END:variables
}
