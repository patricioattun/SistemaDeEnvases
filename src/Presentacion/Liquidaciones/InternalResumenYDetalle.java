
package Presentacion.Liquidaciones;

import Dominio.Irpf;
import Dominio.Liquidacion;
import Dominio.ResumenLiquidacion;
import Dominio.irpfDetallado;
import Logica.LogFuncionario;
import Logica.logPdf;
import Persistencia.BDExcepcion;
import Persistencia.PersistenciaLiquidacion;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;


public class InternalResumenYDetalle extends javax.swing.JInternalFrame {
    
    private LogFuncionario log;
    private PersistenciaLiquidacion pers;
    private logPdf logpdf;
    private static InternalResumenYDetalle instancia=null;
    public InternalResumenYDetalle() throws ClassNotFoundException, SQLException {
        initComponents();
        log=new LogFuncionario();
        this.txtFechaLiq.setText(this.log.fechaLiquidacion().replace('/', '-'));
        this.pers=new PersistenciaLiquidacion();
        this.logpdf=new logPdf();
    }

   public static InternalResumenYDetalle instancia() throws ClassNotFoundException, SQLException
         {    
         if (instancia== null)
         {
            instancia = new InternalResumenYDetalle();
         }
         return instancia;
      
        }
   
   public void crearCarpeta(){
        File dir = new File("c:\\SUELDOSLIST");
        dir.mkdir();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnResumen = new org.edisoncor.gui.button.ButtonColoredAction();
        btnIrpf = new org.edisoncor.gui.button.ButtonColoredAction();
        btnDetalle = new org.edisoncor.gui.button.ButtonColoredAction();
        btnDescuentos = new org.edisoncor.gui.button.ButtonColoredAction();
        btnRecibos = new org.edisoncor.gui.button.ButtonColoredAction();
        txtFechaInfo = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        lblMsj = new javax.swing.JLabel();
        txtFechaLiq = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnTotalesGanados = new org.edisoncor.gui.button.ButtonColoredAction();
        btnTotalesCentroCosto = new org.edisoncor.gui.button.ButtonColoredAction();
        btnGanadosYDescuentos = new org.edisoncor.gui.button.ButtonColoredAction();

        setClosable(true);
        setMaximizable(true);
        setTitle("Resumen y Detalle de Liquidación");
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

        btnResumen.setBackground(new java.awt.Color(153, 255, 51));
        btnResumen.setText("Resumen de la Liquidacion en PDF");
        btnResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumenActionPerformed(evt);
            }
        });

        btnIrpf.setBackground(new java.awt.Color(255, 102, 51));
        btnIrpf.setText("Detalle Irpf Aplicado en PDF");
        btnIrpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIrpfActionPerformed(evt);
            }
        });

        btnDetalle.setBackground(new java.awt.Color(51, 153, 255));
        btnDetalle.setText("Detalle de la Liquidacion en PDF");
        btnDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetalleActionPerformed(evt);
            }
        });

        btnDescuentos.setBackground(new java.awt.Color(153, 153, 0));
        btnDescuentos.setText("Descuentos Efectuados en la Liquidacion en PDF");
        btnDescuentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescuentosActionPerformed(evt);
            }
        });

        btnRecibos.setBackground(new java.awt.Color(102, 255, 153));
        btnRecibos.setText("Recibos de Sueldos en PDF");
        btnRecibos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecibosActionPerformed(evt);
            }
        });

        txtFechaInfo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd-MM-yyyy"))));
        txtFechaInfo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaInfo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Aplicado en la Liquidacion del: ");

        lblMsj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtFechaLiq.setEditable(false);
        txtFechaLiq.setBackground(new java.awt.Color(102, 153, 255));
        txtFechaLiq.setForeground(new java.awt.Color(255, 255, 255));
        txtFechaLiq.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaLiq.setCaretColor(new java.awt.Color(255, 255, 255));
        txtFechaLiq.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtFechaLiq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFechaLiqFocusGained(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Fecha de Liquidacion corriente:");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setText("EXCEL");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("PDF");

        btnTotalesGanados.setBackground(new java.awt.Color(102, 102, 255));
        btnTotalesGanados.setText("Totales Ganados a Excel");
        btnTotalesGanados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalesGanadosActionPerformed(evt);
            }
        });

        btnTotalesCentroCosto.setBackground(new java.awt.Color(255, 102, 102));
        btnTotalesCentroCosto.setText("Totales Ganados por Centro de Costo a Excel");
        btnTotalesCentroCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalesCentroCostoActionPerformed(evt);
            }
        });

        btnGanadosYDescuentos.setBackground(new java.awt.Color(255, 255, 51));
        btnGanadosYDescuentos.setText("Ganados y Descuentos Mensuales a Excel");
        btnGanadosYDescuentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGanadosYDescuentosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtFechaInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnIrpf, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnRecibos, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnTotalesGanados, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnTotalesCentroCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGanadosYDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2))
                    .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1))
                    .addComponent(txtFechaInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addComponent(btnIrpf, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnRecibos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addComponent(btnTotalesGanados, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnTotalesCentroCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGanadosYDescuentos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFechaInfoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaInfoFocusLost
     
    }//GEN-LAST:event_txtFechaInfoFocusLost

    private void txtFechaInfoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaInfoKeyTyped
        int k = (int) evt.getKeyChar();
        this.lblMsj.setText("");
        if(k==10){
        
        }

    }//GEN-LAST:event_txtFechaInfoKeyTyped

    private void btnResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenActionPerformed
        String fecha = this.txtFechaInfo.getText().trim();
        String fechaLiq=this.txtFechaLiq.getText().trim();
        this.lblMsj.setText("");
        this.crearCarpeta();
        if(!"".equals(fecha)){
            if(fecha.equals(fechaLiq)){
                try {
                //carga liquidacion actual
                ArrayList<Liquidacion> liquidaciones = this.pers.cargoResumen();
                //carga totales generales
                ResumenLiquidacion resumen= this.pers.cargoTotalesGenerales();
                if(liquidaciones.size()>0){
                    this.armoResumenDeLiquidacion(fecha, liquidaciones,resumen); 
                }else{
                   liquidaciones = this.pers.cargoResumenHistorico(fecha.replace("-", "/"));
                //carga totales generales
                resumen= this.pers.cargoTotalesGenerales();
                if(liquidaciones.size()>0){
                    this.armoResumenDeLiquidacion(fecha, liquidaciones,resumen); 
                }
                }
                } catch (BDExcepcion | ParseException ex) {
                    this.lblMsj.setText(ex.getMessage());
                }
            }else{try {
                //va a buscar al historico
                ArrayList<Liquidacion> liquidaciones = this.pers.cargoResumenHistorico(fecha.replace("-", "/"));
                //carga totales generales
                ResumenLiquidacion resumen= this.pers.cargoTotalesGenerales();
                if(liquidaciones.size()>0){
                    this.armoResumenDeLiquidacion(fecha, liquidaciones,resumen); 
                }
                } catch (BDExcepcion | ParseException ex) {
                     this.lblMsj.setText(ex.getMessage());
                }
            }
            
        }else{
            lblMsj.setText("Ingrese una fecha válida");
        }
    }//GEN-LAST:event_btnResumenActionPerformed

    private void txtFechaLiqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaLiqFocusGained
        this.txtFechaInfo.requestFocus();
    }//GEN-LAST:event_txtFechaLiqFocusGained

    private void btnIrpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIrpfActionPerformed
        String fecha = this.txtFechaInfo.getText().trim();
        String fechaLiq=this.txtFechaLiq.getText().trim();
        this.lblMsj.setText("");
        this.crearCarpeta();
        if(!"".equals(fecha)){
            if(fecha.equals(fechaLiq)){
                try {
                //carga liquidacion actual
                ArrayList<Liquidacion> detallado = this.pers.cargoLiqIrpf();
              
                if(detallado.size()>0){
                    this.armoDetalleIrpf(fecha, detallado); 
                }else{
                    detallado = this.pers.cargoLiqIrpfHisorico(fecha.replace("-", "/"));
              
                    if(detallado.size()>0){
                        this.armoDetalleIrpf(fecha, detallado); 
                    }
                }
                } catch (BDExcepcion ex) {
                    this.lblMsj.setText(ex.getMessage());
                }
            }else{try {
                //va a buscar al historico
                ArrayList<Liquidacion> detallado = this.pers.cargoLiqIrpfHisorico(fecha.replace("-", "/"));
              
                if(detallado.size()>0){
                    this.armoDetalleIrpf(fecha, detallado); 
                }
                } catch (BDExcepcion ex) {
                    this.lblMsj.setText(ex.getMessage());
                }
            }
            
        }else{
            lblMsj.setText("Ingrese una fecha válida");
        }
    }//GEN-LAST:event_btnIrpfActionPerformed

    private void btnDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetalleActionPerformed
       String fecha = this.txtFechaInfo.getText().trim();
        String fechaLiq=this.txtFechaLiq.getText().trim();
        this.lblMsj.setText("");
        this.crearCarpeta();
        if(!"".equals(fecha)){
            if(fecha.equals(fechaLiq)){
                try {
                    //carga liquidacion actual
                    ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoDetalleLiquidacion();
                    if(resumenes.size()>0){
                        this.armoDetalleLiquidacion(fecha, resumenes);
                    }else{
                        resumenes = this.pers.cargoDetalleLiquidacionHistorico(fecha.replace("-", "/"));
                    if(resumenes.size()>0){
                        this.armoDetalleLiquidacion(fecha, resumenes);
                    }
                    }
                } catch (BDExcepcion | ClassNotFoundException ex) {
                   this.lblMsj.setText(ex.getMessage());
                }
            }else{try {
                //va a buscar al historico
                ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoDetalleLiquidacionHistorico(fecha.replace("-", "/"));
                if(resumenes.size()>0){
                    this.armoDetalleLiquidacion(fecha, resumenes);
                }
                } catch (BDExcepcion ex) {
                    this.lblMsj.setText(ex.getMessage());
                }
            }
            
        }else{
            lblMsj.setText("Ingrese una fecha válida");
        }
    }//GEN-LAST:event_btnDetalleActionPerformed

    private void btnDescuentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescuentosActionPerformed
        String fecha = this.txtFechaInfo.getText().trim();
        String fechaLiq=this.txtFechaLiq.getText().trim();
        this.lblMsj.setText("");
        this.crearCarpeta();
        if(!"".equals(fecha)){
            if(fecha.equals(fechaLiq)){
                try {
                    //carga liquidacion actual
                    ArrayList<Liquidacion> descuentos = this.pers.cargoDescuentos();
                    if(descuentos.size()>0){
                        this.armoDescuentos(fecha, descuentos);
                    }else{
                        descuentos = this.pers.cargoDescuentosHistoricos(fecha.replace("-", "/"));
                    if(descuentos.size()>0){
                        this.armoDescuentos(fecha, descuentos);
                    }
                    }
                } catch (BDExcepcion | DocumentException ex) {
                   this.lblMsj.setText(ex.getMessage());
                }
            }else{try {
                //va a buscar al historico
                ArrayList<Liquidacion> descuentos = this.pers.cargoDescuentosHistoricos(fecha.replace("-", "/"));
                if(descuentos.size()>0){
                    this.armoDescuentos(fecha, descuentos);
                }
                } catch (BDExcepcion | DocumentException ex) {
                     this.lblMsj.setText(ex.getMessage());
                }
            }
            
        }else{
            lblMsj.setText("Ingrese una fecha válida");
        }
    }//GEN-LAST:event_btnDescuentosActionPerformed

    private void btnRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecibosActionPerformed
        String fecha = this.txtFechaInfo.getText().trim();
        String fechaLiq=this.txtFechaLiq.getText().trim();
        this.lblMsj.setText("");
        this.crearCarpeta();
        if(!"".equals(fecha)){
            if(fecha.equals(fechaLiq)){
                try {
                    //carga liquidacion actual
                    ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoDetalleLiquidacion();
                    if(resumenes.size()>0){
                        this.armoRecibosDeSueldos(fecha, resumenes);
                    }else{
                        resumenes = this.pers.cargoDetalleLiquidacionHistorico(fecha.replace("-", "/"));
                    if(resumenes.size()>0){
                        this.armoRecibosDeSueldos(fecha, resumenes);
                    }
                    }
                } catch (BDExcepcion | ClassNotFoundException ex) {
                   this.lblMsj.setText(ex.getMessage());
                }
            }else{try {
                //va a buscar al historico
                ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoDetalleLiquidacionHistorico(fecha.replace("-", "/"));
                if(resumenes.size()>0){
                    this.armoRecibosDeSueldos(fecha, resumenes);
                }
                } catch (BDExcepcion | ClassNotFoundException ex) {
                    this.lblMsj.setText(ex.getMessage());
                }
            }
            
        }else{
            lblMsj.setText("Ingrese una fecha válida");
        }
    }//GEN-LAST:event_btnRecibosActionPerformed

    private void btnTotalesGanadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalesGanadosActionPerformed
        String fecha = this.txtFechaInfo.getText().trim();
        String fechaLiq=this.txtFechaLiq.getText().trim();
        this.lblMsj.setText("");
        this.crearCarpeta();
        if(!"".equals(fecha)){
            if(fecha.equals(fechaLiq)){
                try {
                    //carga liquidacion actual
                    ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoTotalesGanados("");
                    if(resumenes.size()>0){
                        this.armoTotalesGanados(fecha, resumenes);
                    }else{
                        resumenes = this.pers.cargoTotalesGanados(fecha.replace("-", "/"));
                    if(resumenes.size()>0){
                        this.armoTotalesGanados(fecha, resumenes);
                    }
                    }
                } catch (BDExcepcion | FileNotFoundException ex) {
                   this.lblMsj.setText(ex.getMessage());
                } catch (IOException ex) {
                    this.lblMsj.setText(ex.getMessage());
                }
            }else{try {
                //va a buscar al historico
                ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoTotalesGanados(fecha.replace("-", "/"));
                if(resumenes.size()>0){
                   this.armoTotalesGanados(fecha, resumenes);
                }
                } catch (BDExcepcion | FileNotFoundException ex) {
                    this.lblMsj.setText(ex.getMessage());
                } catch (IOException ex) {
                   this.lblMsj.setText(ex.getMessage());
                }
            }
            
        }else{
            lblMsj.setText("Ingrese una fecha válida");
        }
    }//GEN-LAST:event_btnTotalesGanadosActionPerformed

    private void btnTotalesCentroCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalesCentroCostoActionPerformed
      String fecha = this.txtFechaInfo.getText().trim();
        String fechaLiq=this.txtFechaLiq.getText().trim();
        this.lblMsj.setText("");
        this.crearCarpeta();
        if(!"".equals(fecha)){
            if(fecha.equals(fechaLiq)){
                try {
                    //carga liquidacion actual
                    ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoTotalesGanadosCentro("");
                    if(resumenes.size()>0){
                        this.armoTotalesGanadosCentro(fecha, resumenes);
                    }else{
                        resumenes = this.pers.cargoTotalesGanadosCentro(fecha.replace("-", "/"));
                    if(resumenes.size()>0){
                        this.armoTotalesGanadosCentro(fecha, resumenes);
                    }
                    }
                } catch (BDExcepcion | FileNotFoundException ex) {
                   this.lblMsj.setText(ex.getMessage());
                } catch (IOException ex) {
                    this.lblMsj.setText(ex.getMessage());
                }
            }else{try {
                //va a buscar al historico
                ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoTotalesGanadosCentro(fecha.replace("-", "/"));
                if(resumenes.size()>0){
                   this.armoTotalesGanadosCentro(fecha, resumenes);
                }
                } catch (BDExcepcion | FileNotFoundException ex) {
                    this.lblMsj.setText(ex.getMessage());
                } catch (IOException ex) {
                   this.lblMsj.setText(ex.getMessage());
                }
            }
            
        }else{
            lblMsj.setText("Ingrese una fecha válida");
        }
    }//GEN-LAST:event_btnTotalesCentroCostoActionPerformed

    private void btnGanadosYDescuentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGanadosYDescuentosActionPerformed
       String fecha = this.txtFechaInfo.getText().trim();
        String fechaLiq=this.txtFechaLiq.getText().trim();
        this.lblMsj.setText("");
        this.crearCarpeta();
        if(!"".equals(fecha)){
            if(fecha.equals(fechaLiq)){
                try {
                    //carga liquidacion actual
                    ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoTotalesGanadosYDescuentos(fecha.replace("-", "/"));
                    if(resumenes.size()>0){
                        this.armoTotalesGanadosYDescuento(fecha, resumenes);
                    }else{
                        resumenes = this.pers.cargoTotalesGanadosYDescuentos(fecha.replace("-", "/"));
                    if(resumenes.size()>0){
                        this.armoTotalesGanadosYDescuento(fecha, resumenes);
                    }
                    }
                } catch (BDExcepcion | IOException ex) {
                   this.lblMsj.setText(ex.getMessage());
                }
            }else{try {
                //va a buscar al historico
                ArrayList<ResumenLiquidacion> resumenes = this.pers.cargoTotalesGanadosYDescuentos(fecha.replace("-", "/"));
                if(resumenes.size()>0){
                   this.armoTotalesGanadosYDescuento(fecha, resumenes);
                }
                } catch (BDExcepcion | IOException ex) {
                   this.lblMsj.setText(ex.getMessage());
                }
            }
            
        }else{
            lblMsj.setText("Ingrese una fecha válida");
        }
    }//GEN-LAST:event_btnGanadosYDescuentosActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    public Date ParseFecha(String fecha) throws ParseException
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaDate = null; 
        fechaDate = formato.parse(fecha);
              
        return fechaDate;
    }
    private String formateoFecha(String fecha){
      String str="";
      SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
      str=formateador.format(fecha);
      return str;
     }
    
    private void armoResumenDeLiquidacion(String fecha,ArrayList<Liquidacion> liquidaciones, ResumenLiquidacion resumen) throws ParseException{                        
            
      try{
       String ruta="C:\\SUELDOSLIST\\RESUMEN DE LIQUIDACION DEL "+fecha+".pdf";
       FileOutputStream archivo = new FileOutputStream(ruta);
     //  FileOutputStream archivo = new FileOutputStream("C:\\hola.pdf");
                  
       this.logpdf.crearResumenLiquidacion(fecha,liquidaciones,archivo,resumen);
               
       archivo.close();
        
      
      }catch (FileNotFoundException ex) {
            this.lblMsj.setText("El documento está abierto, ciérrelo y vuelva a intentar");
           
      } catch (IOException | DocumentException ex) {
            this.lblMsj.setText(ex.getMessage());
      }
        
    }                                  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonColoredAction btnDescuentos;
    private org.edisoncor.gui.button.ButtonColoredAction btnDetalle;
    private org.edisoncor.gui.button.ButtonColoredAction btnGanadosYDescuentos;
    private org.edisoncor.gui.button.ButtonColoredAction btnIrpf;
    private org.edisoncor.gui.button.ButtonColoredAction btnRecibos;
    private org.edisoncor.gui.button.ButtonColoredAction btnResumen;
    private org.edisoncor.gui.button.ButtonColoredAction btnTotalesCentroCosto;
    private org.edisoncor.gui.button.ButtonColoredAction btnTotalesGanados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JFormattedTextField txtFechaInfo;
    private org.edisoncor.gui.textField.TextFieldRound txtFechaLiq;
    // End of variables declaration//GEN-END:variables

    private void armoDetalleIrpf(String fecha, ArrayList<Liquidacion> detallado) {
        try{
       String ruta="C:\\SUELDOSLIST\\DETALLE DE IRPF APLICADO DEL "+fecha+".pdf";
       FileOutputStream archivo = new FileOutputStream(ruta);
     //  FileOutputStream archivo = new FileOutputStream("C:\\hola.pdf");
                  
       this.logpdf.crearDetalleIrpf(fecha,archivo,detallado);
               
       archivo.close();
        
      
      }catch (FileNotFoundException ex) {
            this.lblMsj.setText("El documento está abierto, ciérrelo y vuelva a intentar");
           
      } catch (IOException | DocumentException ex) {
            this.lblMsj.setText(ex.getMessage());
      }
    }

    private void armoDetalleLiquidacion(String fecha, ArrayList<ResumenLiquidacion> resumenes) {
         try{
       String ruta="C:\\SUELDOSLIST\\DETALLE DE LIQUIDACION DEL "+fecha+".pdf";
       FileOutputStream archivo = new FileOutputStream(ruta);
  
                  
       this.logpdf.crearDetalleLiquidacion(fecha,archivo,resumenes);
               
       archivo.close();
        
      
      }catch (FileNotFoundException ex) {
            this.lblMsj.setText("El documento está abierto, ciérrelo y vuelva a intentar");
           
      } catch (IOException | DocumentException ex) {
            this.lblMsj.setText(ex.getMessage());
      }
    }

    private void armoDescuentos(String fecha, ArrayList<Liquidacion> descuentos) throws DocumentException {
            try{
       String ruta="C:\\SUELDOSLIST\\DESCUENTOS EFECTUADOS EL "+fecha+".pdf";
       FileOutputStream archivo = new FileOutputStream(ruta);
  
                  
       this.logpdf.crearDescuentosPdf(fecha,archivo,descuentos);
               
       archivo.close();
        
      
      }catch (FileNotFoundException ex) {
            this.lblMsj.setText("El documento está abierto, ciérrelo y vuelva a intentar");
           
      } catch (IOException ex) {
            this.lblMsj.setText(ex.getMessage());
      }
    }

    private void armoRecibosDeSueldos(String fecha, ArrayList<ResumenLiquidacion> resumenes) throws BDExcepcion, ClassNotFoundException {
      try{
       String ruta="C:\\SUELDOSLIST\\RECIBOS DE SUELDO DEL "+fecha+".pdf";
       FileOutputStream archivo = new FileOutputStream(ruta);
  
                  
       this.logpdf.crearRecibosDeSueldos(fecha,archivo,resumenes);
               
       archivo.close();
        
      
      }catch (FileNotFoundException ex) {
            this.lblMsj.setText("El documento está abierto, ciérrelo y vuelva a intentar");
           
      } catch (IOException | DocumentException ex) {
            this.lblMsj.setText(ex.getMessage());
      }
    }

    private void armoTotalesGanados(String fecha, ArrayList<ResumenLiquidacion> resumenes) throws FileNotFoundException, IOException {
  
        javax.swing.filechooser.FileNameExtensionFilter filterXls = new javax.swing.filechooser.FileNameExtensionFilter("Documentosxcel 95/2003", "xls");

        File fileXLS = null;
        FileOutputStream fileOut = null;
            
            String[] headers = new String[]{
                "Num. Func",//0
                "Nombre",//1
                "Total Ganado",//2
                "Ganado BPS",//3
                "Ganado Irpf",//4
                "Centro Costo",//5
                "Nombre Centro Costo",//6
                "Cedula",//7
                "Fonasa",//8
                
             };
            
            
            
            String ruta="C:\\SUELDOSLIST\\TOTALES GANADOS DE SUELDOS "+this.armaFecha(fecha)+".xls"; 
          
            fileOut = new FileOutputStream(ruta);
              
            Workbook libro = new HSSFWorkbook();
            Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
          
            HSSFCellStyle style3 = (HSSFCellStyle) libro.createCellStyle();
            Font font = libro.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            font.setBold(true);
            style3.setFont(font);
            
            CellStyle estilo = libro.createCellStyle();
            estilo.setAlignment(CellStyle.ALIGN_RIGHT);

            hoja.addMergedRegion(new CellRangeAddress(0,0,0,1));
            Row fu=hoja.createRow(0);
            Cell cel = fu.createCell(0);
            cel.setCellValue("TOTALES GANADOS AL: "+fecha); 
            
             int h=2;
           
             for(int i=0;i<=resumenes.size();i++){
                  Row fila = hoja.createRow(h);
                  h++;
                  for(int c=0;c<headers.length;c++){
                    Cell celda = fila.createCell(c);
                   
                          if(i==0){
                            celda.setCellValue(headers[c]);
                            celda.setCellStyle(style3);
                          }else{
                               switch(c)
                                    {
                                        case 0:
                                            Integer num=resumenes.get(i-1).getFuncionario().getCodFunc();
                                            celda.setCellValue(num);
                                        break;
                                        case 1:
                                             String nom=resumenes.get(i-1).getFuncionario().getNomCompletoApe();
                                             celda.setCellValue(nom);
                                        break;
                                        case 2:
                                             String d=this.decimales(resumenes.get(i-1).getTotalHaberes());
                                             celda.setCellValue(d);
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 3:
                                             String s=this.decimales(resumenes.get(i-1).getTotalHaberesBps());
                                             celda.setCellValue(s);
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 4:
                                             String t=this.decimales(resumenes.get(i-1).getTotalHaberesIrpf());
                                             celda.setCellValue(t);
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 5:
                                            Integer y=resumenes.get(i-1).getCentro().getCodigo();
                                             celda.setCellValue(y);
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 6:
                                            String x=resumenes.get(i-1).getCentro().getNombre();
                                            celda.setCellValue(x);
                                        break;
                                        case 7:
                                            Integer o=resumenes.get(i-1).getFuncionario().getCedula();
                                            celda.setCellValue(o);
                                            celda.setCellStyle(estilo);
                                        break;
                                        case 8:
                                            Integer l=resumenes.get(i-1).getFuncionario().getSns().getCodigo();
                                            celda.setCellValue(l);
                                            celda.setCellStyle(estilo);
                                        break;
                               }
                          }
                    
                  }
            } 
            hoja.autoSizeColumn(0); 
            hoja.autoSizeColumn(1); 
            hoja.autoSizeColumn(2);
            hoja.autoSizeColumn(3);
            hoja.autoSizeColumn(4);
            hoja.autoSizeColumn(5);
            hoja.autoSizeColumn(6);
            hoja.autoSizeColumn(7);
            hoja.autoSizeColumn(8);
            hoja.autoSizeColumn(9);
            libro.write(fileOut);
             //Cerramos nuestro archivo
            fileOut.close();
        
    }
    
   private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
}
    private String armaFecha(String fecha) {
        String mes = fecha.substring(3, 5);
        String año = fecha.substring(6, 10);
        String retorno="";
        switch (mes) {
            case "01":
                retorno="ENERO "+año;
                break;
            case "02":
                retorno="FEBRERO "+año; 
                break;
            case "03":
                retorno="MARZO "+año;
                break;
            case "04":
                retorno="ABRIL "+año;
                break;
            case "05":
                retorno="MAYO "+año; 
                break;
            case "6":
                retorno="JUNIO "+año;
                break;
            case "07":
                retorno="JULIO "+año;
                break;
            case "08":
                retorno="AGOSTO "+año; 
                break;
            case "09":
                retorno="SETIEMBRE "+año;
                break;
            case "10":
                retorno="OCTUBRE "+año;
                break;
            case "11":
                retorno="NOVIEMBRE "+año; 
                break;
            case "12":
                retorno="DICIEMBRE "+año;
                break;
            default:
                break;
        }
       return retorno; 
    }

    private void armoTotalesGanadosCentro(String fecha, ArrayList<ResumenLiquidacion> resumenes) throws IOException {
        java.io.ByteArrayOutputStream memoryStream = null;
        javax.swing.filechooser.FileNameExtensionFilter filterXls = new javax.swing.filechooser.FileNameExtensionFilter("Documentosxcel 95/2003", "xls");

        File fileXLS = null;
        FileOutputStream fileOut = null;
            
            String[] headers = new String[]{
                "Num. Func",//0
                "Nombre",//1
                "Total Ganado",//2
                "Ganado BPS",//3
                "Ganado Irpf",//4
                "Centro Costo",//5
                "Nombre Centro Costo",//6
                "Cedula",//7
                "Fonasa",//8
                
             };
                        
            
            String ruta="C:\\SUELDOSLIST\\TOTALES GANADOS POR CENTRO DE COSTO SUELDO "+this.armaFecha(fecha)+".xlsx";
          
            fileOut = new FileOutputStream(ruta);
              
            Workbook libro = new HSSFWorkbook();
            Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
          
            HSSFCellStyle style3 = (HSSFCellStyle) libro.createCellStyle();
            Font font = libro.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            font.setBold(true);
            style3.setFont(font);
            
            HSSFCellStyle style2 = (HSSFCellStyle) libro.createCellStyle();
            font = libro.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            font.setBold(true);
            style2.setFont(font);
            style2.setAlignment(CellStyle.ALIGN_RIGHT);
            
            CellStyle estilo = libro.createCellStyle();
            estilo.setAlignment(CellStyle.ALIGN_RIGHT);

            hoja.addMergedRegion(new CellRangeAddress(0,0,0,1));
            Row fu=hoja.createRow(0);
            
            Cell cel = fu.createCell(0);
            
            cel.setCellValue("TOTALES GANADOS POR CENTRO DE COSTO AL: "+fecha); 
            int h=2;
            double totalGanado=0;
            double totalBps=0;
            double totalIrpf=0;
            Integer centro=-1;
            Row fila;
             for(int i=0;i<=resumenes.size();i++){
               fila = hoja.createRow(h);
                h++;
                  for(int c=0;c<headers.length;c++){
                          if(i==0){
                            Cell celda = fila.createCell(c);
                            celda.setCellValue(headers[c]);
                            celda.setCellStyle(style3);
                          }else{
                            
                              int cod=resumenes.get(i-1).getFuncionario().getCodFunc();
                              if(cod==6095){
                                  int m= 0;
                              }

                              if(!centro.equals(resumenes.get(i-1).getCentro().getCodigo())){
                                  centro=resumenes.get(i-1).getCentro().getCodigo();
                                  c--;
                                  
                                  if(i>1){
                                    h--;
                                    fila=hoja.createRow(h);
                                    
                                    Cell celds = fila.createCell(1);
                                    celds.setCellValue("SUB-TOTALES");
                                    celds.setCellStyle(style3);
                                    Cell celd1 = fila.createCell(2);
                                    celd1.setCellValue(this.decimales(totalGanado));
                                    celd1.setCellStyle(style2);
                                    
                                    Cell celd2 = fila.createCell(3);
                                    celd2.setCellValue(this.decimales(totalBps));
                                    celd2.setCellStyle(style2);
                                    
                                    Cell celd3 = fila.createCell(4);
                                    celd3.setCellValue(this.decimales(totalIrpf));
                                    celd3.setCellStyle(style2);
                                    
                                    totalGanado=0;
                                    totalBps=0;
                                    totalIrpf=0;
                                    h++;
                                    h++;
                                    fila = hoja.createRow(h);
                                    h++;
                                  }
                              }else{
                                   Cell celd = fila.createCell(c);
                               switch(c)
                                    {
                                        case 0:
                                            Integer num=resumenes.get(i-1).getFuncionario().getCodFunc();
                                            celd.setCellValue(num);
                                        break;
                                        case 1:
                                             String nom=resumenes.get(i-1).getFuncionario().getNomCompletoApe();
                                             celd.setCellValue(nom);
                                        break;
                                        case 2:
                                             String d=this.decimales(resumenes.get(i-1).getTotalHaberes());
                                             totalGanado+=resumenes.get(i-1).getTotalHaberes();
                                             celd.setCellValue(d);
                                             celd.setCellStyle(estilo);
                                        break;
                                        case 3:
                                             String s=this.decimales(resumenes.get(i-1).getTotalHaberesBps());
                                             totalBps+=resumenes.get(i-1).getTotalHaberesBps();
                                             celd.setCellValue(s);
                                             celd.setCellStyle(estilo);
                                        break;
                                        case 4:
                                             String t=this.decimales(resumenes.get(i-1).getTotalHaberesIrpf());
                                             totalIrpf+=resumenes.get(i-1).getTotalHaberesIrpf();
                                             celd.setCellValue(t);
                                             celd.setCellStyle(estilo);
                                        break;
                                        case 5:
                                            Integer y=resumenes.get(i-1).getCentro().getCodigo();
                                             celd.setCellValue(y);
                                             celd.setCellStyle(estilo);
                                        break;
                                        case 6:
                                            String x=resumenes.get(i-1).getCentro().getNombre();
                                            celd.setCellValue(x);
                                        break;
                                        case 7:
                                            Integer o=resumenes.get(i-1).getFuncionario().getCedula();
                                            celd.setCellValue(o);
                                            celd.setCellStyle(estilo);
                                        break;
                                        case 8:
                                            Integer l=resumenes.get(i-1).getFuncionario().getSns().getCodigo();
                                            celd.setCellValue(l);
                                            celd.setCellStyle(estilo);
                                        break;
                               }
                          }
                       }
                  }
            } 
             
            
            fila=hoja.createRow(h);

            Cell celds = fila.createCell(1);
            celds.setCellValue("SUB-TOTALES");
            celds.setCellStyle(style3);
            Cell celd1 = fila.createCell(2);
            celd1.setCellValue(this.decimales(totalGanado));
            celd1.setCellStyle(style2);

            Cell celd2 = fila.createCell(3);
            celd2.setCellValue(this.decimales(totalBps));
            celd2.setCellStyle(style2);

            Cell celd3 = fila.createCell(4);
            celd3.setCellValue(this.decimales(totalIrpf));
            celd3.setCellStyle(style2);
             
             
            hoja.autoSizeColumn(0); 
            hoja.autoSizeColumn(1); 
            hoja.autoSizeColumn(2);
            hoja.autoSizeColumn(3);
            hoja.autoSizeColumn(4);
            hoja.autoSizeColumn(5);
            hoja.autoSizeColumn(6);
            hoja.autoSizeColumn(7);
            hoja.autoSizeColumn(8);
            hoja.autoSizeColumn(9);
            libro.write(fileOut);
            
            fileOut.close();
        
    }

    private void armoTotalesGanadosYDescuento(String fecha, ArrayList<ResumenLiquidacion> resumenes) throws FileNotFoundException, IOException {
       
        javax.swing.filechooser.FileNameExtensionFilter filterXls = new javax.swing.filechooser.FileNameExtensionFilter("Documentosxcel 95/2003", "xls");

        File fileXLS = null;
        FileOutputStream fileOut = null;
            
            String[] headers = new String[]{
                "COD. FUNC",//0
                "HABERES",//1
                "HABERES BPS",//2
                "HABERES IRPF",//3
                "DIPAICO",//4
                "CASENPACE VOL.",//5
                "FONASA",//6
                "FRL",//7
                "IRPF",//8
                "LIQ. FINAL",//9
                "CEDULA",//10
                "NOMBRES",//11
                "C. COSTO",//12
                "NOMBRE CENTRO COSTO",//13
                "FONASA",//14
             };
            
            
            
            String ruta="C:\\SUELDOSLIST\\RESUMEN TOTALES GANADOS DE SUELDOS "+this.armaFecha(fecha)+".xls"; 
          
            fileOut = new FileOutputStream(ruta);
              
            Workbook libro = new HSSFWorkbook();
            Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
          
            HSSFCellStyle style3 = (HSSFCellStyle) libro.createCellStyle();
            Font font = libro.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            font.setBold(true);
            style3.setFont(font);
            
            CellStyle estilo = libro.createCellStyle();
            estilo.setAlignment(CellStyle.ALIGN_RIGHT);

            hoja.addMergedRegion(new CellRangeAddress(0,0,0,4));
            Row fu=hoja.createRow(0);
            Cell cel = fu.createCell(0);
            cel.setCellValue("MONTOS TOTALES MENSUALES CORRESPONDIENTES A: "+fecha); 
            int h=2;
           
            Integer totFunc = 0;
            double totalHaberes = 0;
            double totalHabBps = 0;
            double totalHabIrpf = 0;
            double totalDipaico = 0;
            double totalCasenpace = 0;
            double totalFonasa = 0;
            double totalFrl = 0;
            double totalIrpf = 0;
            double totalLiquido = 0;
            boolean borro;
            for(int i=0;i<=resumenes.size();i++){
                  Row fila = hoja.createRow(h);
                  h++;
                  borro = false;
                  for(int c=0;c<headers.length;c++){
                    Cell celda = fila.createCell(c);
                   
                          if(i==0){
                            celda.setCellValue(headers[c]);
                            celda.setCellStyle(style3);
                          }else{
                              if(resumenes.get(i-1).getTotalHaberes()>0 ||resumenes.get(i-1).getTotalHaberesBps()>0 || resumenes.get(i-1).getTotalHaberesIrpf()>0){
                               switch(c)
                                    {
                                        case 0:
                                            Integer num=resumenes.get(i-1).getFuncionario().getCodFunc();
                                            celda.setCellValue(num);
                                            celda.setCellStyle(estilo);
                                            totFunc++;
                                        break;
                                        case 1:
                                             String d=this.decimales(resumenes.get(i-1).getTotalHaberes());
                                             celda.setCellValue(d);
                                             celda.setCellStyle(estilo);
                                             totalHaberes+=resumenes.get(i-1).getTotalHaberes();
                                        break;
                                        case 2:
                                             d=this.decimales(resumenes.get(i-1).getTotalHaberesBps());
                                             celda.setCellValue(d);
                                             celda.setCellStyle(estilo);
                                             totalHabBps+=resumenes.get(i-1).getTotalHaberesBps();
                                        break;
                                        case 3:
                                             String s=this.decimales(resumenes.get(i-1).getTotalHaberesIrpf());
                                             celda.setCellValue(s);
                                             celda.setCellStyle(estilo);
                                             totalHabIrpf+=resumenes.get(i-1).getTotalHaberesIrpf();
                                        break;
                                        case 4:
                                             String t=this.decimales(resumenes.get(i-1).getDipaico());
                                             celda.setCellValue(t);
                                             celda.setCellStyle(estilo);
                                             totalDipaico+=resumenes.get(i-1).getDipaico();
                                        break;
                                        case 5:
                                             t=this.decimales(resumenes.get(i-1).getCasenpace());
                                             celda.setCellValue(t);
                                             celda.setCellStyle(estilo);
                                             totalCasenpace+=resumenes.get(i-1).getCasenpace();
                                        break;
                                        case 6:
                                             t=this.decimales(resumenes.get(i-1).getFonasa());
                                             celda.setCellValue(t);
                                             celda.setCellStyle(estilo);
                                             totalFonasa+=resumenes.get(i-1).getFonasa();
                                        break;
                                        case 7:
                                             t=this.decimales(resumenes.get(i-1).getFrl());
                                             celda.setCellValue(t);
                                             celda.setCellStyle(estilo);
                                             totalFrl+=resumenes.get(i-1).getFrl();
                                        break;
                                        case 8:
                                             t=this.decimales(resumenes.get(i-1).getIrpf());
                                             celda.setCellValue(t);
                                             celda.setCellStyle(estilo);
                                             totalIrpf+=resumenes.get(i-1).getIrpf();
                                        break;
                                        case 9:
                                             t=this.decimales(resumenes.get(i-1).getLiquidoFinal());
                                             celda.setCellValue(t);
                                             celda.setCellStyle(estilo);
                                             totalLiquido+=resumenes.get(i-1).getLiquidoFinal();
                                        break;
                                        case 10:
                                             celda.setCellValue(resumenes.get(i-1).getFuncionario().getCedula());
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 11:
                                             celda.setCellValue(resumenes.get(i-1).getFuncionario().getNomCompletoApe());
                                        break;
                                        case 12:
                                             celda.setCellValue(resumenes.get(i-1).getCentro().getCodigo());
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 13:
                                             celda.setCellValue(resumenes.get(i-1).getCentro().getNombre());
                                        break;
                                        case 14:
                                             celda.setCellValue(resumenes.get(i-1).getFuncionario().getSns().getCodigo());
                                             celda.setCellStyle(estilo);
                                            
                                        break;
                               }
                          }else{
                                  if(!borro){
                                  hoja.removeRow(fila);
                                  borro=true;
                                  h--;
                                  }
                              }
                          }
                    
                  }
            } 
            h++;
            Row fila = hoja.createRow(h);
            Cell celda = fila.createCell(0);
            celda.setCellValue(totFunc);
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(1);
            celda.setCellValue(this.decimales(totalHaberes));
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(2);
            celda.setCellValue(this.decimales(totalHabBps));
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(3);
            celda.setCellValue(this.decimales(totalHabIrpf));
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(4);
            celda.setCellValue(this.decimales(totalDipaico));
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(5);
            celda.setCellValue(this.decimales(totalCasenpace));
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(6);
            celda.setCellValue(this.decimales(totalFonasa));
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(7);
            celda.setCellValue(this.decimales(totalFrl));
            celda.setCellStyle(estilo);

            celda = fila.createCell(8);
            celda.setCellValue(this.decimales(totalIrpf));
            celda.setCellStyle(estilo);
            
            celda = fila.createCell(9);
            celda.setCellValue(this.decimales(totalLiquido));
            celda.setCellStyle(estilo);
                        
            hoja.autoSizeColumn(0); 
            hoja.autoSizeColumn(1); 
            hoja.autoSizeColumn(2);
            hoja.autoSizeColumn(3);
            hoja.autoSizeColumn(4);
            hoja.autoSizeColumn(5);
            hoja.autoSizeColumn(6);
            hoja.autoSizeColumn(7);
            hoja.autoSizeColumn(8);
            hoja.autoSizeColumn(9);
            hoja.autoSizeColumn(10);
            hoja.autoSizeColumn(11);
            hoja.autoSizeColumn(12);
            hoja.autoSizeColumn(13);
            hoja.autoSizeColumn(14);
            
            libro.write(fileOut);
             //Cerramos nuestro archivo
            fileOut.close();
        
    }
}
