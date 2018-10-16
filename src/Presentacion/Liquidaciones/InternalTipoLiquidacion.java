
package Presentacion.Liquidaciones;

import Logica.LogCodigo;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import Persistencia.PersistenciaLiquidacion;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



public class InternalTipoLiquidacion extends javax.swing.JInternalFrame {

    private LogFuncionario log;
    private static InternalTipoLiquidacion instancia=null;
    private String fechaAct;
    private boolean cambio;
    private final LogCodigo logCod;
    private PersistenciaLiquidacion pers;
    private int bloqueo;
    
    public InternalTipoLiquidacion() throws ClassNotFoundException, SQLException, ParseException, BDExcepcion, IOException {
        initComponents();
        this.cargoComboAnio();
        log=new LogFuncionario();
        this.cargoFechaActual();
        this.lblAnio.setVisible(false);
        this.lblMes.setVisible(false);
        this.anioChooser.setVisible(false);
        this.mesChooser.setVisible(false);
        pers=new PersistenciaLiquidacion();
        this.verificoLiquidacionActual();
        logCod=new LogCodigo();
        this.verificaBloqueo();
    }
    
      public static InternalTipoLiquidacion instancia() throws ClassNotFoundException, SQLException, ParseException, BDExcepcion, IOException
   {    
         if (instancia== null)
         {
            instancia = new InternalTipoLiquidacion();
         }
         return instancia;
      
   }

    public static InternalTipoLiquidacion getInstancia() {
        return instancia;
    }

    public static void setInstancia(InternalTipoLiquidacion instancia) {
        InternalTipoLiquidacion.instancia = instancia;
    }
    
    private void verificoLiquidacionActual() throws BDExcepcion{
       Date fechaLiqCorriente=this.log.fechaLiquidacionEnLiq();
       if(fechaLiqCorriente!=null){
            String corriente=this.convertirFecha(fechaLiqCorriente);
            if(corriente.equals(fechaAct)){
                this.estado.setText("LIQUIDACION EN CURSO");
                this.estado.setBackground(Color.RED);
                cambio=false;
            }else{
                this.estado.setText("CONSULTE CON DESARROLLO");
                this.estado.setBackground(Color.RED);
                cambio=false;
            }
       }else{
           this.estado.setText("SELECCIONE FECHA DE LIQUIDACIÓN");
           this.estado.setBackground(Color.YELLOW);
           cambio=true;
       }
    }
      
      

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        combo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtFechaLiq = new org.edisoncor.gui.textField.TextField();
        buttonTextDown1 = new org.edisoncor.gui.button.ButtonTextDown();
        lblMsj = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtFechaActual = new javax.swing.JLabel();
        txtTipoActual = new javax.swing.JLabel();
        lblAnio = new javax.swing.JLabel();
        lblMes = new javax.swing.JLabel();
        mesChooser = new javax.swing.JComboBox<>();
        anioChooser = new javax.swing.JComboBox<>();
        estado = new org.edisoncor.gui.textField.TextFieldRound();
        jSeparator2 = new javax.swing.JSeparator();
        btnBloqueo = new javax.swing.JButton();
        lblBloqueo = new javax.swing.JLabel();
        buttonTextDown2 = new org.edisoncor.gui.button.ButtonTextDown();
        lblProceso = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Tipo de Liquidación");
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

        combo.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione tipo de Liquidación", "Liquidación de Sueldos", "Re-Liquidación de Sueldos", "Liquidación de Aguinaldos", "Re-Liquidación de Aguinaldos", "Liquidación de Vacacionales", "Re-Liquidación de Vacacionales" }));
        combo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel1.setText("Tipo de liquidación");

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel2.setText("Fecha Generada");

        txtFechaLiq.setEditable(false);
        txtFechaLiq.setForeground(new java.awt.Color(255, 0, 0));
        txtFechaLiq.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaLiq.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N

        buttonTextDown1.setBackground(new java.awt.Color(102, 204, 0));
        buttonTextDown1.setText("CONFIRMAR");
        buttonTextDown1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTextDown1ActionPerformed(evt);
            }
        });

        lblMsj.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        lblMsj.setForeground(new java.awt.Color(51, 153, 0));

        txtFechaActual.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        txtFechaActual.setText("Fecha Actual:");

        txtTipoActual.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        txtTipoActual.setText("Tipo Actual:");

        lblAnio.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblAnio.setText("Año:");

        lblMes.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblMes.setText("Mes:");

        mesChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", " " }));
        mesChooser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                mesChooserItemStateChanged(evt);
            }
        });

        anioChooser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                anioChooserItemStateChanged(evt);
            }
        });

        estado.setEditable(false);
        estado.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnBloqueo.setToolTipText("Abrir");
        btnBloqueo.setBorder(null);
        btnBloqueo.setBorderPainted(false);
        btnBloqueo.setContentAreaFilled(false);
        btnBloqueo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBloqueo.setFocusPainted(false);
        btnBloqueo.setRolloverEnabled(true);
        btnBloqueo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBloqueoActionPerformed(evt);
            }
        });

        lblBloqueo.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N

        buttonTextDown2.setBackground(new java.awt.Color(102, 204, 0));
        buttonTextDown2.setText("HACER INCORPORACION");
        buttonTextDown2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTextDown2ActionPerformed(evt);
            }
        });

        lblProceso.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Incorporación");

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Bloqueo Contaduría");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonTextDown1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblBloqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(btnBloqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(anioChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(410, 410, 410)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1))
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtTipoActual, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(410, 410, 410)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(lblMes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addComponent(mesChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(440, 440, 440)
                .addComponent(buttonTextDown2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(lblBloqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBloqueo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(239, 239, 239)
                .addComponent(lblProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(282, 282, 282)
                .addComponent(anioChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4))
            .addGroup(layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(jLabel1))
            .addGroup(layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(jLabel3))
            .addGroup(layout.createSequentialGroup()
                .addGap(285, 285, 285)
                .addComponent(lblAnio))
            .addGroup(layout.createSequentialGroup()
                .addGap(301, 301, 301)
                .addComponent(buttonTextDown2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(txtFechaActual))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(txtTipoActual))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(lblMes))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(419, 419, 419)
                        .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(mesChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonTextDown1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboItemStateChanged
       int i = this.combo.getSelectedIndex();
        this.lblAnio.setVisible(false);
        this.lblMes.setVisible(false);
        this.anioChooser.setVisible(false);
        this.mesChooser.setVisible(false);
       if(i>=0){
          this.fijaFecha(i);
       }else{
           try {
               this.cargoFechaActual();
           } catch (ClassNotFoundException | SQLException | ParseException ex) {
               this.lblMsj.setText(ex.getMessage());
           }
       }
    }//GEN-LAST:event_comboItemStateChanged

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
      instancia=null;
        try {
            this.log.actualizaParametro("", "USUARIO_TIPO_LIQ");
        } catch (BDExcepcion ex) {
            Logger.getLogger(InternalTipoLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameClosed

    private void buttonTextDown1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTextDown1ActionPerformed
       if(cambio){
       String fec = this.txtFechaLiq.getText().trim();
     
       if(!fec.equals("") && !fec.equals("Seleccione tipo de Liquidación")){
           try {
               if(this.log.actualizaParametros(fec,fechaAct ,"FECHALIQ")){
                   this.cargoFechaActual();
                   this.combo.setSelectedIndex(0);
                   this.txtFechaLiq.setText(""); 
                   this.lblMsj.setText("Actualización Exitosa");
               }
           } catch (BDExcepcion | ClassNotFoundException | SQLException | ParseException ex) {
               this.lblMsj.setText(ex.getMessage());
           }
       }else{
           this.txtFechaLiq.setText("Seleccione tipo de Liquidación");
       }
       }else{
           this.lblMsj.setText(""); 
           JOptionPane.showMessageDialog(this, "Hay una liquidación en curso. Debe realizar la incorporación para continuar");
       }
    }//GEN-LAST:event_buttonTextDown1ActionPerformed

    private void anioChooserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_anioChooserItemStateChanged
        int i = this.combo.getSelectedIndex();
        this.fijaFecha(i);
    }//GEN-LAST:event_anioChooserItemStateChanged

    private void mesChooserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_mesChooserItemStateChanged
        int i = this.combo.getSelectedIndex();
        this.fijaFecha(i);
    }//GEN-LAST:event_mesChooserItemStateChanged

    private void btnBloqueoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBloqueoActionPerformed
       if(bloqueo==0){
           try {
               this.log.actualizaParametros("1",String.valueOf(bloqueo), "BLOQUEO_CONTADURIA");
           } catch (BDExcepcion ex) {
               Logger.getLogger(InternalTipoLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
           }
       }else{
           try {
               this.log.actualizaParametros("0",String.valueOf(bloqueo), "BLOQUEO_CONTADURIA");
           } catch (BDExcepcion ex) {
               Logger.getLogger(InternalTipoLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
        try {
            this.verificaBloqueo();
        } catch (BDExcepcion | IOException ex) {
            Logger.getLogger(InternalTipoLiquidacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBloqueoActionPerformed

    private void buttonTextDown2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTextDown2ActionPerformed
        try {
            int e=JOptionPane.showInternalConfirmDialog(this, "Está seguro que desea hacer la incorporación a las tablas históricas?");
            
            if(e==0){
                if(this.pers.fechaValida(fechaAct)){
                    if(this.pers.hayRegistrosEnLiquidacion()){
                        this.pers.incorporacion(fechaAct);
                        JOptionPane.showMessageDialog(this, "La incorporación fue hecha de manera exitosa.");
                        this.verificoLiquidacionActual();
                    }else{
                        JOptionPane.showMessageDialog(this, "No hay registros para incorporar");
                    } 
                }else{
                    JOptionPane.showMessageDialog(this, "Fecha no válida");
                } 
            }
        } catch (BDExcepcion | SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_buttonTextDown2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> anioChooser;
    private javax.swing.JButton btnBloqueo;
    private org.edisoncor.gui.button.ButtonTextDown buttonTextDown1;
    private org.edisoncor.gui.button.ButtonTextDown buttonTextDown2;
    private javax.swing.JComboBox<String> combo;
    private org.edisoncor.gui.textField.TextFieldRound estado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAnio;
    private javax.swing.JLabel lblBloqueo;
    private javax.swing.JLabel lblMes;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JLabel lblProceso;
    private javax.swing.JComboBox<String> mesChooser;
    private javax.swing.JLabel txtFechaActual;
    private org.edisoncor.gui.textField.TextField txtFechaLiq;
    private javax.swing.JLabel txtTipoActual;
    // End of variables declaration//GEN-END:variables

    private void fijaFecha(int i) {
     
      this.lblMsj.setText("");
      Date hoy = new Date();
      Calendar cal = Calendar.getInstance();
      cal.setTime(hoy);
      int me=Integer.valueOf((String)this.mesChooser.getSelectedItem());
      int an=Integer.valueOf((String) this.anioChooser.getSelectedItem());
      
      int mes=cal.get(Calendar.MONTH)+1;
      int anio=cal.get(Calendar.YEAR);
      Date fechaLiq;
      if(i>=1){
     
          switch (i) {
              case 5:
                  cal.set(Calendar.DAY_OF_MONTH, 1);
                  break;
              case 6:
                  this.lblAnio.setVisible(true);
                  this.lblMes.setVisible(true);
                  this.anioChooser.setVisible(true);
                  this.mesChooser.setVisible(true);
                  cal.set(Calendar.YEAR, an);
                  cal.set(Calendar.MONTH, me-1); 
                  cal.set(Calendar.DAY_OF_MONTH, 2);
                  break;
              case 3:
                  cal.set(Calendar.DAY_OF_MONTH, 15);
                  break;
              case 4:
                  this.lblAnio.setVisible(true);
                  this.lblMes.setVisible(true);
                  this.anioChooser.setVisible(true);
                  this.mesChooser.setVisible(true);
                  cal.set(Calendar.DAY_OF_MONTH, 14);
                  cal.set(Calendar.YEAR, an);
                  cal.set(Calendar.MONTH, me-1); 
                  break;
              case 2:
                  if(me==2){
                      if ((an % 4 == 0) && ((an % 100 != 0) || (an % 400 == 0))){
                            this.lblAnio.setVisible(true);
                            this.lblMes.setVisible(true);
                            this.anioChooser.setVisible(true);
                            this.mesChooser.setVisible(true);
                            an=Integer.valueOf((String) this.anioChooser.getSelectedItem());
                            me=Integer.valueOf((String)this.mesChooser.getSelectedItem())-1;
                            cal.set(Calendar.DAY_OF_MONTH, 27);
                            cal.set(Calendar.YEAR, an);
                            cal.set(Calendar.MONTH, me); 
                      }else{
                            this.lblAnio.setVisible(true);
                            this.lblMes.setVisible(true);
                            this.anioChooser.setVisible(true);
                            this.mesChooser.setVisible(true);
                            an=Integer.valueOf((String) this.anioChooser.getSelectedItem());
                            me=Integer.valueOf((String)this.mesChooser.getSelectedItem())-1;
                            cal.set(Calendar.DAY_OF_MONTH, 26);
                            cal.set(Calendar.YEAR, an);
                            cal.set(Calendar.MONTH, me); 
                       }
                  }else{
                      if(me==1 || me==3 || me==5 ||me==7 || me==8 || me==10 || me==12){
                           this.lblAnio.setVisible(true);
                            this.lblMes.setVisible(true);
                            this.anioChooser.setVisible(true);
                            this.mesChooser.setVisible(true);
                            an=Integer.valueOf((String) this.anioChooser.getSelectedItem());
                            me=Integer.valueOf((String)this.mesChooser.getSelectedItem())-1;
                            cal.set(Calendar.DAY_OF_MONTH, 29);
                            cal.set(Calendar.YEAR, an);
                            cal.set(Calendar.MONTH, me); 
                      }else{
                           this.lblAnio.setVisible(true);
                            this.lblMes.setVisible(true);
                            this.anioChooser.setVisible(true);
                            this.mesChooser.setVisible(true);
                            an=Integer.valueOf((String) this.anioChooser.getSelectedItem());
                            me=Integer.valueOf((String)this.mesChooser.getSelectedItem())-1;
                            cal.set(Calendar.DAY_OF_MONTH, 28);
                            cal.set(Calendar.YEAR, an);
                            cal.set(Calendar.MONTH, me); 
                      }
                  } break;
              case 1:
                  if(mes==2){
                      if ((anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0))){
                          cal.set(Calendar.DAY_OF_MONTH, 29);
                      }else{
                          cal.set(Calendar.DAY_OF_MONTH, 28);
                      }
                  }else{
                      if(mes==1 || mes==3 || mes==5 ||mes==7 || mes==8 || mes==10 || mes==12){
                          cal.set(Calendar.DAY_OF_MONTH, 31);
                      }else{
                          cal.set(Calendar.DAY_OF_MONTH, 30);
                      }
                  } break;
              default:
                  break;
          }
        fechaLiq=cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String cadena = sdf.format(fechaLiq);
        this.txtFechaLiq.setText(cadena);
      }else{
          this.txtFechaLiq.setText("Seleccione tipo de Liquidación");
      }
    }
    
  private void cargoFechaActual() throws ClassNotFoundException, SQLException, ParseException {
        fechaAct=this.log.fechaLiquidacion();
        fechaAct=fechaAct.trim();
        this.lblMsj.setText("");
        String str="";
        if(fechaAct!=null){
        if(!fechaAct.equals("")){
        Date fechaLiq=this.stringADate(fechaAct);
        this.txtFechaActual.setText("Fecha Actual: "+fechaAct);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaLiq);
        int dia =cal.get(Calendar.DAY_OF_MONTH);
        int mes =cal.get(Calendar.MONTH)+1;
        
            if(mes!=2){
                switch (dia) {
                    case 01:
                        str="Liquidación de Vacacionales";
                        break;
                    case 02:
                        str="Re-Liquidación de Vacacionales";
                        break;
                    case 15:
                        str="Liquidación de Aguinaldos";
                        break;
                    case 14:
                        str="Re-Liquidación de Aguinaldos";
                        break;
                    case 30: case 31:
                        str="Liquidación de Sueldos";
                        break;
                    case 29: case 28:
                        str="Re-Liquidación de Sueldos";
                        break;
                    default:
                        break;
                }
            }else{
                 switch (dia) {
                    case 01:
                        str="Liquidación de Vacacionales";
                        break;
                    case 02:
                        str="Re-Liquidación de Vacacionales";
                        break;
                    case 15:
                        str="Liquidación de Aguinaldos";
                        break;
                    case 14:
                        str="Re-Liquidación de Aguinaldos";
                        break;
                    case 28: case 29:
                        str="Liquidación de Sueldos";
                        break;
                    case 27: case 26:
                        str="Re-Liquidación de Sueldos";
                        break;
                    default:
                        break;
                }
            }
        }else{
            this.txtFechaActual.setText("Fecha Actual: ----------------------------");
            str="-----------------------------"; 
        }
        }else{
            this.txtFechaActual.setText("Fecha Actual: ----------------------------");
            str="-----------------------------"; 
        }
        this.txtTipoActual.setText("Tipo Actual: "+str);
        
    }
    
    private Date stringADate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(s);
        return date;
    }

    private void cargoComboAnio() {
        Calendar cal = Calendar.getInstance();
        int anio=cal.get(Calendar.YEAR)+1;
        int i=15;
        while(i>0){
            anio--;
            this.anioChooser.addItem(String.valueOf(anio));
            i--;
        }
    }
    
    private String convertirFecha(Date fecha){
        String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
        }
       return str;
    }

    private void verificaBloqueo() throws BDExcepcion, IOException {
       if(this.logCod.bloqueoContaduria()==0){
           Image img = ImageIO.read(getClass().getResource("/Imagenes/unlock.png"));
           btnBloqueo.setIcon(new ImageIcon(img));
           bloqueo=0;
           this.lblBloqueo.setText("Bloquear"); 
       }else{
           Image img = ImageIO.read(getClass().getResource("/Imagenes/lock.png"));
           btnBloqueo.setIcon(new ImageIcon(img));
           bloqueo=1;
           this.lblBloqueo.setText("Desbloquear"); 
       }
    }
}
