
package Presentacion.Mantenimiento;

import Dominio.Funcionario;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import Presentacion.RenderCodigos;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.edisoncor.gui.textField.TextFieldRound;


public class InternalAumentoSalarial extends javax.swing.JInternalFrame {

   private static InternalAumentoSalarial instancia=null;
   private LogFuncionario log;
   private ArrayList<Funcionario> funcionarios;
   private ArrayList<Funcionario> listado;
   private Integer tipo=null;
   
    public InternalAumentoSalarial() throws ClassNotFoundException, SQLException {
        initComponents();
        String twoLines = "Previsualizar\nIncremento";
        this.btnPre.setText("<html><center>" + twoLines.replaceAll("\\n", "<br>") + "</center></html>");
        twoLines = "Confirmar\nActualización";
        this.btnConf.setText("<html><center>" + twoLines.replaceAll("\\n", "<br>") + "</center></html>");
        twoLines = "Previsualizar\nDecremento";
        this.btnPreDecre.setText("<html><center>" + twoLines.replaceAll("\\n", "<br>") + "</center></html>");
        twoLines = "Modificar\nAntigüedad";
        this.btnAntiguedad.setText("<html><center>" + twoLines.replaceAll("\\n", "<br>") + "</center></html>");
        this.txtIncremento.requestFocus();
        this.txtAntiActual.setEditable(false);
        this.txtAntiNuevo.setEditable(false);
        
       try {
           this.log = new LogFuncionario();
           String anti=this.log.valorDeAntiguedad();
           this.txtAntiActual.setText(Double.valueOf(anti).toString());
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

         public static InternalAumentoSalarial instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalAumentoSalarial();
         }
         return instancia;
      
   }

    public TextFieldRound getTxtIncremento() {
        return txtIncremento;
    }

    public void setTxtIncremento(TextFieldRound txtIncremento) {
        this.txtIncremento = txtIncremento;
    }
  
         
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtIncremento = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel5 = new javax.swing.JLabel();
        btnPre = new javax.swing.JButton();
        btnConf = new javax.swing.JButton();
        btnExcel = new org.edisoncor.gui.button.ButtonIcon();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        lblMsj = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAntiActual = new org.edisoncor.gui.textField.TextFieldRound();
        txtAntiNuevo = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnPreDecre = new javax.swing.JButton();
        btnAntiguedad = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Aumento de Salarios por Porcentaje");
        setPreferredSize(new java.awt.Dimension(750, 630));
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

        txtIncremento.setBackground(new java.awt.Color(102, 153, 255));
        txtIncremento.setForeground(new java.awt.Color(255, 255, 255));
        txtIncremento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIncremento.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIncremento.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtIncremento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIncrementoFocusGained(evt);
            }
        });
        txtIncremento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIncrementoKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Porcentaje de Incremento (##.###0) ");

        btnPre.setBackground(new java.awt.Color(51, 51, 51));
        btnPre.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnPre.setForeground(new java.awt.Color(255, 255, 255));
        btnPre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnConf.setBackground(new java.awt.Color(51, 51, 51));
        btnConf.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnConf.setForeground(new java.awt.Color(255, 255, 255));
        btnConf.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfActionPerformed(evt);
            }
        });

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Excel_2013_23480.png"))); // NOI18N
        btnExcel.setText("buttonIcon1");
        btnExcel.setToolTipText("Exportar a Excel");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Func", "Sueldo Actual", "Sueldo Nuevo", "Nombre Cargo", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(75);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(75);
            tabla.getColumnModel().getColumn(0).setMaxWidth(75);
            tabla.getColumnModel().getColumn(1).setMinWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setMaxWidth(100);
            tabla.getColumnModel().getColumn(2).setMinWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setMaxWidth(100);
            tabla.getColumnModel().getColumn(3).setMinWidth(200);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(3).setMaxWidth(200);
            tabla.getColumnModel().getColumn(4).setMinWidth(250);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(250);
            tabla.getColumnModel().getColumn(4).setMaxWidth(250);
        }

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Situación Presupuestal");

        lblMsj.setForeground(new java.awt.Color(255, 0, 0));
        lblMsj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Antigüedad:");

        txtAntiActual.setBackground(new java.awt.Color(102, 102, 102));
        txtAntiActual.setForeground(new java.awt.Color(255, 255, 255));
        txtAntiActual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAntiActual.setCaretColor(new java.awt.Color(255, 255, 255));
        txtAntiActual.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtAntiActual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAntiActualFocusLost(evt);
            }
        });
        txtAntiActual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAntiActualKeyTyped(evt);
            }
        });

        txtAntiNuevo.setBackground(new java.awt.Color(102, 102, 102));
        txtAntiNuevo.setForeground(new java.awt.Color(255, 255, 255));
        txtAntiNuevo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAntiNuevo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtAntiNuevo.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtAntiNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAntiNuevoKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Nuevo");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Actual");

        btnPreDecre.setBackground(new java.awt.Color(51, 51, 51));
        btnPreDecre.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnPreDecre.setForeground(new java.awt.Color(255, 255, 255));
        btnPreDecre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPreDecre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreDecreActionPerformed(evt);
            }
        });

        btnAntiguedad.setBackground(new java.awt.Color(51, 51, 51));
        btnAntiguedad.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAntiguedad.setForeground(new java.awt.Color(255, 255, 255));
        btnAntiguedad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAntiguedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntiguedadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtIncremento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(btnPreDecre, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnConf, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(541, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtAntiActual, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAntiNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(32, 32, 32)
                        .addComponent(btnAntiguedad, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnConf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnExcel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPreDecre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIncremento, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtAntiActual, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAntiNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addComponent(btnAntiguedad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        funcionarios=null;
        this.LimpiarTabla();
        this.lblMsj.setText("");
        String str = this.txtIncremento.getText();
        Double d = null;
        if(this.esDouble(str)){
            d=Double.valueOf(str);
            if(d!=null){
                try {
                    tipo=1;
                    listado=this.log.listadoSueldo();
                    String anti=this.log.valorDeAntiguedad();
                    this.armaTabla(listado,d,anti);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else{
            this.lblMsj.setText("Ingrese solo números");
        }

    }//GEN-LAST:event_btnPreActionPerformed

    private void txtIncrementoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIncrementoKeyTyped
        funcionarios=null;
        tipo=null;
        this.lblMsj.setText("");
        this.LimpiarTabla();
        this.txtAntiNuevo.setText("");
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==45|| k==44||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(k==10){
            this.btnPre.doClick();
        }
    }//GEN-LAST:event_txtIncrementoKeyTyped

    private void btnConfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfActionPerformed
      this.lblMsj.setText("");
      Integer respuesta=null;
        if(this.funcionarios!=null){
            if(funcionarios.size()>0){
                if(tipo!=null){
                    if(tipo==1){
                        respuesta = JOptionPane.showConfirmDialog(this, "LOS SALARIOS SERAN INCREMENTADOS TAL CUAL SE MUESTRA EN LA GRILLA INFERIOR, ESTA SEGURO/A?",null, JOptionPane.YES_NO_OPTION);
                    }
                    else if(tipo==0){
                        respuesta = JOptionPane.showConfirmDialog(this, "LOS SALARIOS SERAN DECREMENTADOS TAL CUAL SE MUESTRA EN LA GRILLA INFERIOR, ESTA SEGURO/A?",null, JOptionPane.YES_NO_OPTION);
                    }
                }
                if(respuesta==0){
                    String str = this.txtAntiNuevo.getText();
                    String porcentaje = this.txtIncremento.getText();
                    Integer i = this.log.actualizaSalarios(funcionarios,str,porcentaje, tipo);
                    JOptionPane.showMessageDialog(this, "Se han actualizado los salarios de los "+i+" funcionarios.");
                    this.LimpiarTabla();
                    this.txtAntiNuevo.setText("");
                    this.txtAntiActual.setText("");
                    this.txtIncremento.setText("");
                    this.txtIncremento.requestFocus();
                }
            }
            else{
            this.lblMsj.setText("Ejecute el botón de previsualizar antes");
        }
        }
        else{
            this.lblMsj.setText("Ejecute el botón de previsualizar antes");
        }
       
    }//GEN-LAST:event_btnConfActionPerformed

    private void txtIncrementoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIncrementoFocusGained
        this.txtIncremento.selectAll();
    }//GEN-LAST:event_txtIncrementoFocusGained

    private void txtAntiActualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAntiActualKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAntiActualKeyTyped

    private void txtAntiNuevoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAntiNuevoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAntiNuevoKeyTyped

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
       try {
           this.procesarExcel();
       } catch (IOException ex) {
          JOptionPane.showMessageDialog(this, "El documento está abierto");
       } catch (ClassNotFoundException ex) {
          JOptionPane.showMessageDialog(this, "El documento está abierto"); 
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this, "El documento está abierto");
       }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPreDecreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreDecreActionPerformed
        funcionarios=null;
        this.LimpiarTabla();
        this.lblMsj.setText("");
        String str = this.txtIncremento.getText();
        Double d = null;
        if(this.esDouble(str)){
            d=Double.valueOf(str);
            if(d!=null){
                try {
                    tipo=0;
                    listado=this.log.listadoSueldo();
                    String anti=this.log.valorDeAntiguedad();
                    this.armaTabla(listado,d,anti);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else{
            this.lblMsj.setText("Ingrese solo números");
        }
    }//GEN-LAST:event_btnPreDecreActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtAntiActualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAntiActualFocusLost
        this.txtAntiActual.setEditable(false);
    }//GEN-LAST:event_txtAntiActualFocusLost

    private void btnAntiguedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntiguedadActionPerformed
       
       String respuesta=JOptionPane.showInputDialog(rootPane, "Ingrese nuevo valor de antigüedad", this.txtAntiActual.getText());
       if(respuesta!=null){
           String anti=this.txtAntiActual.getText();
        if(!respuesta.equals(anti)){
            try {
                if(this.log.actualizaParametros(respuesta,this.txtAntiActual.getText() , "ANTIGUEDAD")){
                   funcionarios=null;
                   tipo=null;
                   this.lblMsj.setText("");
                   this.LimpiarTabla();
                   this.txtAntiNuevo.setText("");
                   this.txtIncremento.setText("");
                   String ant=this.log.valorDeAntiguedad();
                   this.txtAntiActual.setText(Double.valueOf(ant).toString());
                }
            } catch (BDExcepcion ex) {
                Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
            }  catch (ClassNotFoundException ex) {
                   Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                   Logger.getLogger(InternalAumentoSalarial.class.getName()).log(Level.SEVERE, null, ex);
               }
        }
       }
    }//GEN-LAST:event_btnAntiguedadActionPerformed

    
    private Boolean esDouble(String num){
        Boolean es=true;
        Integer i=0;
        Integer ret = 0;
        if(num.length()==1){
            if(!Character.isDigit(num.charAt(0))){
                es=false;
            }
        }
        else{
            if(num.equals("")){
                es=false;
            }
            while(i<num.length() && es){
                 if(!Character.isDigit(num.charAt(i)) && num.charAt(i)!='.'){
                      es=false;
                 }
                 else if(num.charAt(i)=='.'){
                     ret++;
                     if(ret>1){
                         es=false;
                     }
                 }
                i++;
            }
        }
        return es;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAntiguedad;
    private javax.swing.JButton btnConf;
    private org.edisoncor.gui.button.ButtonIcon btnExcel;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnPreDecre;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtAntiActual;
    private org.edisoncor.gui.textField.TextFieldRound txtAntiNuevo;
    private org.edisoncor.gui.textField.TextFieldRound txtIncremento;
    // End of variables declaration//GEN-END:variables

    private void armaTabla(ArrayList<Funcionario> listado, Double d, String anti) {
       funcionarios=null;
       funcionarios=new ArrayList<>();
       Double antes=0.0;
       Double despues=0.0;
       d=(d/100)+1;
       DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
       Object[] filas=new Object[modelo.getColumnCount()];
       for(Funcionario f:listado){   
                            Funcionario fu=new Funcionario();
                            filas[0]=f.getCodFunc().toString();
                            fu.setCodFunc(f.getCodFunc());
                            filas[1]=this.decimales(f.getSueldoCargo());
                            
                            fu.setCuenta(f.getSueldoCargo());
                            
                            antes+=f.getSueldoCargo();
                            if(tipo==1){
                            Double aumento=Math.ceil(f.getSueldoCargo()*d);
                            filas[2]=this.decimales(aumento); 
                            fu.setSueldoCargo(aumento);
                            despues+=aumento;
                            }
                            else if(tipo==0){
                            Double decremento=Math.floor(f.getSueldoCargo()/d);
                            filas[2]=this.decimales(decremento); 
                            fu.setSueldoCargo(decremento);
                            despues+=decremento;    
                            }
                                                        
                            filas[3]=f.getCargo().getNombre().trim();
                            filas[4]=f.getNomCompletoApe();
                            
                            fu.setNombre1(f.getNombre1());
                            fu.setNombre2(f.getNombre2());
                            fu.setApellido1(f.getApellido1());
                            fu.setApellido2(f.getApellido2());
                            fu.setCargo(f.getCargo());
                            RenderCodigos rr=new RenderCodigos(4);
                            tabla.setDefaultRenderer(Object.class, rr);
                            modelo.addRow(filas);
                           
                            funcionarios.add(fu);
                    
       }
       filas[0]="";
       filas[1]="";
       filas[2]="";
       filas[3]="";
       filas[4]="";
       modelo.addRow(filas);
       filas[0]=listado.size();
       filas[1]=this.decimales(antes);
       filas[2]=this.decimales(despues);
       filas[3]="";
       filas[4]="";
       modelo.addRow(filas);
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.changeSelection(modelo.getRowCount()-1, 0, false, false);
        Double antiActual = Double.valueOf(anti);
        this.txtAntiActual.setText(antiActual.toString());
        if(tipo==1){
        Double antiNuevo = Math.ceil(Double.valueOf(anti.trim())*d);
        this.txtAntiNuevo.setText(antiNuevo.toString());
        }
        else if(tipo==0){
        Double antiNuevo = Math.floor(Double.valueOf(anti.trim())/d);
        this.txtAntiNuevo.setText(antiNuevo.toString());
        }
        this.Alinear_Grillas();
    }
    
     private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
}
     
      private void Alinear_Grillas(){
            
        DefaultTableCellRenderer modeloDer = new DefaultTableCellRenderer();
        modeloDer.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer modeloIzq = new DefaultTableCellRenderer();
        modeloIzq.setHorizontalAlignment(SwingConstants.LEFT);
        DefaultTableCellRenderer modeloCen = new DefaultTableCellRenderer();
        modeloCen.setHorizontalAlignment(SwingConstants.CENTER);
        this.tabla.getColumnModel().getColumn(0).setCellRenderer(modeloCen); 
        this.tabla.getColumnModel().getColumn(1).setCellRenderer(modeloDer); 
        this.tabla.getColumnModel().getColumn(2).setCellRenderer(modeloDer);
        this.tabla.getColumnModel().getColumn(3).setCellRenderer(modeloIzq); 
        this.tabla.getColumnModel().getColumn(4).setCellRenderer(modeloIzq); 
     
    }
      
     private void LimpiarTabla() {
        
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
  
    }
     
public void procesarExcel() throws IOException, ClassNotFoundException, SQLException{
    if(funcionarios!=null){
    javax.swing.filechooser.FileNameExtensionFilter filterXls = new javax.swing.filechooser.FileNameExtensionFilter("Documentosxcel 95/2003", "xls");

        File fileXLS = null;
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filterXls);
        fc.setSelectedFile(fileXLS);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
     
        int seleccion = fc.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            FileOutputStream fileOut = null;
            
            String[] headers = new String[]{
                "Cod. Func",//0
                "Sueldo Actual",//1
                "Sueldo Nuevo",//2
                "Nombre Cargo",//3
                "Nombre",//4
               
            };
            
       
              fileXLS = fc.getSelectedFile();
              String name = fileXLS.getName();
                if (name.indexOf('.') == -1) {
                    name += ".xls";
                    fileXLS = new File(fileXLS.getParentFile(), name);
                }
              fileOut = new FileOutputStream(fileXLS);
              
              Workbook libro = new HSSFWorkbook();
              Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                          
               Row fi=hoja.createRow(0);
               Cell cel = fi.createCell(0);
               
               Date hoy = new Date();
               cel.setCellValue("Aumento de Salarios por Porcentaje "+ this.convertirFecha(hoy));
              
                  
             CellStyle style = libro.createCellStyle();
             style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
             style.setFillPattern(CellStyle.SOLID_FOREGROUND);
             style.setAlignment(HorizontalAlignment.RIGHT);
             
              for(int i=2;i<=funcionarios.size()+1;i++){
                 
                  Row fila = hoja.createRow(i);
                  
                  
                  for(int c=0;c<headers.length;c++){
                    Cell celda = fila.createCell(c);
                 
                    hoja.setColumnWidth(c, 5000);
                    
                        if(i==2){
                            celda.setCellValue(headers[c]);

                        }
                                              
                            else{
                            Funcionario m=funcionarios.get(i-2);
                                    switch(c)
                                    {                                       
                                        case 0:
                                            celda.setCellValue(m.getCodFunc());
                                            break;
                                        case 1:
                                            celda.setCellValue(decimales(m.getSueldoCargo()));
                                            celda.setCellStyle(style);
                                            break;
                                        case 2:
                                            celda.setCellValue(this.decimales(m.getCuenta()));
                                            celda.setCellStyle(style);
                                            break;
                                        case 3:
                                            celda.setCellValue(m.getCargo().getNombre());
                                            break;   
                                        case 4:
                                            celda.setCellValue(m.getNomCompleto());
                                            break;
                                       
                                        
                                    }
                                }
                       // celda.setCellStyle(style);

                    }
              
              }
              
              libro.write(fileOut);
              fileOut.close();
              Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileXLS.getAbsolutePath());
                  

                 
        }
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


}
