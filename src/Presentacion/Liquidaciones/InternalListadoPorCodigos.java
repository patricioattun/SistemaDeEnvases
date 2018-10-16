
package Presentacion.Liquidaciones;

import Dominio.Codigo;
import Dominio.CodigoBcu;
import Dominio.Liquidacion;
import Logica.LogCodigo;
import Logica.LogLiquidacion;
import Persistencia.BDExcepcion;
import Presentacion.RenderCodigos;
import Presentacion.frmPrin;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.edisoncor.gui.textField.TextFieldRound;

public class InternalListadoPorCodigos extends javax.swing.JInternalFrame {

    private static InternalListadoPorCodigos instancia=null;
    private LogLiquidacion log;
    private LogCodigo logCod;
    private Codigo cod;
    boolean elimino=false;
    int pasada=0;
    int tablaTamaño;
    private Codigo codBorrado=null;
    private ArrayList<Codigo> codigos = new ArrayList<>();
     private ArrayList<Liquidacion> liquidaciones = new ArrayList<>();
    public InternalListadoPorCodigos() {
        try {
            initComponents();
            log = new LogLiquidacion();
            logCod = new LogCodigo();
            this.cargoComboAño();
        } catch (BDExcepcion ex) {
            this.lblMsj.setText(ex.getMessage());
        }
    }

       public static InternalListadoPorCodigos instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalListadoPorCodigos();
         }
         return instancia;
      
   }

    public TextFieldRound getTxtCod() {
        return txtCod;
    }

    public void setTxtCod(TextFieldRound txtCod) {
        this.txtCod = txtCod;
    }
       
       
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        comboAño = new javax.swing.JComboBox();
        comboMes = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        txtNameCod = new org.edisoncor.gui.textField.TextFieldRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCod = new javax.swing.JTable();
        lblMsj = new javax.swing.JLabel();
        btnPasar = new org.edisoncor.gui.button.ButtonIcon();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaGeneral = new javax.swing.JTable();
        buttonColoredAction1 = new org.edisoncor.gui.button.ButtonColoredAction();
        buttonIcon2 = new org.edisoncor.gui.button.ButtonIcon();

        jMenuItem1.setText("Eliminar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setIconifiable(true);
        setPreferredSize(new java.awt.Dimension(800, 610));

        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 0), 10));

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione Liquidación");

        comboAño.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboAñoItemStateChanged(evt);
            }
        });

        comboMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        comboMes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboMesItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel2.setText("MES");

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel3.setText("AÑO");

        txtCod.setBackground(new java.awt.Color(153, 204, 0));
        txtCod.setForeground(new java.awt.Color(255, 255, 255));
        txtCod.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCod.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        txtCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodFocusGained(evt);
            }
        });
        txtCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel4.setText("CÓDIGO");

        txtNameCod.setEditable(false);
        txtNameCod.setBackground(new java.awt.Color(153, 204, 0));
        txtNameCod.setForeground(new java.awt.Color(255, 255, 255));
        txtNameCod.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNameCod.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        tablaCod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCod.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tablaCod);
        if (tablaCod.getColumnModel().getColumnCount() > 0) {
            tablaCod.getColumnModel().getColumn(0).setResizable(false);
            tablaCod.getColumnModel().getColumn(0).setPreferredWidth(70);
            tablaCod.getColumnModel().getColumn(1).setResizable(false);
            tablaCod.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        lblMsj.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        lblMsj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnPasar.setBackground(new java.awt.Color(204, 255, 0));
        btnPasar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/chevron.png"))); // NOI18N
        btnPasar.setText("buttonIcon1");
        btnPasar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasarActionPerformed(evt);
            }
        });

        tablaGeneral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha Liq", "Codigo Mov.", "Cobro", "Nombre", "Importe", "No Retenido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaGeneral);
        if (tablaGeneral.getColumnModel().getColumnCount() > 0) {
            tablaGeneral.getColumnModel().getColumn(3).setMinWidth(250);
            tablaGeneral.getColumnModel().getColumn(3).setPreferredWidth(250);
            tablaGeneral.getColumnModel().getColumn(3).setMaxWidth(250);
        }

        buttonColoredAction1.setBackground(new java.awt.Color(102, 204, 0));
        buttonColoredAction1.setText("LISTAR");
        buttonColoredAction1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonColoredAction1ActionPerformed(evt);
            }
        });

        buttonIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Excel_2013_23480.png"))); // NOI18N
        buttonIcon2.setText("buttonIcon1");
        buttonIcon2.setToolTipText("Exportar a Excel");
        buttonIcon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonIcon2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboAño, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNameCod, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnPasar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonColoredAction1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(comboAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtNameCod, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnPasar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonColoredAction1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
      this.lblMsj.setText("");
      this.txtNameCod.setText("");
      this.cod=null;
      char c=evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
        }
        if(this.txtCod.getText().length()>3){
             evt.consume();
        }
        int k = (int) evt.getKeyChar();
        if(k==10){
          try {
              this.buscarCodigo();
          } catch (BDExcepcion ex) {
              this.lblMsj.setText(ex.getMessage());
          }
        }
        if(evt.getKeyChar()==43){
            this.txtCod.setText("");
            try {
                InternalListadoCodLiq listadoCod=InternalListadoCodLiq.instancia(logCod,this,1);
                frmPrin prin=frmPrin.instancia();
                if (!listadoCod.isVisible()) {
                    prin.getDesktop().add(listadoCod);
                    listadoCod.setLocation((prin.getDesktop().getWidth()/70)-(listadoCod.getWidth()/70),(prin.getDesktop().getHeight()/70) - listadoCod.getHeight()/70);
                    listadoCod.setVisible(true);
                    listadoCod.repaint();
                    listadoCod.revalidate();
                }
                else{
                    listadoCod.requestFocus();
                    try {
                        listadoCod.setSelected(true);
                        listadoCod.setVisible(true);
                        listadoCod.repaint();
                        listadoCod.revalidate();

                    } catch (PropertyVetoException ex) {
                        //lblMensaje.setText(ex.getMessage());
                    }
                }

            }  catch (SQLException | ClassNotFoundException ex) {
                 this.lblMsj.setText(ex.getMessage());
            }
        }
        if(evt.getKeyChar()==27){
            this.dispose();
        }
    }//GEN-LAST:event_txtCodKeyTyped

    private void txtCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFocusGained
       this.txtCod.selectAll();
       this.codBorrado=null;
    }//GEN-LAST:event_txtCodFocusGained

    private void btnPasarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasarActionPerformed
       if(cod!=null){
           if(!codigos.contains(cod)){ 
               codigos.add(cod);
               this.recargarTabla();
               this.LimpiarTablaGeneral();
               this.liquidaciones.clear();
               this.txtCod.setText("");
               this.txtNameCod.setText("");
               this.txtCod.requestFocus();
          }else{
               this.lblMsj.setText("Este código ya esta cargado");
               this.txtCod.requestFocus();
               this.txtCod.selectAll();
           }
       }
    }//GEN-LAST:event_btnPasarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       Integer linea = tablaCod.getSelectedRow(); 
       Codigo c = this.codigos.get(linea);
       if(c!=null){
         this.codigos.remove(c); 
         this.LimpiarTablaGeneral();
         this.liquidaciones.clear();
       }
       this.recargarTabla();
       
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void buttonColoredAction1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonColoredAction1ActionPerformed
        int mes = (int) this.comboMes.getSelectedIndex()+1;
        int año = (int) this.comboAño.getSelectedItem();
        if(this.codigos.size()>0){
            try {
                liquidaciones=this.log.cargoLiquidacionesPorCodigos(mes,año,codigos);
                if(liquidaciones.size()>0){
                    this.cargoTablaGeneral();
                }
            } catch (BDExcepcion ex) {
                 this.lblMsj.setText(ex.getMessage());
            }
        }
    }//GEN-LAST:event_buttonColoredAction1ActionPerformed

    private void comboMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboMesItemStateChanged
        this.LimpiarTablaGeneral();
        this.liquidaciones.clear();
        this.lblMsj.setText(""); 
    }//GEN-LAST:event_comboMesItemStateChanged

    private void comboAñoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboAñoItemStateChanged
        this.LimpiarTablaGeneral();
        this.liquidaciones.clear();
        this.lblMsj.setText(""); 
    }//GEN-LAST:event_comboAñoItemStateChanged

    private void buttonIcon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIcon2ActionPerformed

        if(liquidaciones!=null){
            if(liquidaciones.size()>0){
                try {
                    this.procesarExcel();
                    this.lblMsj.setText(""); 
                } catch (IOException ex) {
                     this.lblMsj.setText(ex.getMessage());
                }
            }else{
                this.lblMsj.setText("No hay ninguna tabla cargada para exportar");
            }
        }
        else{
            this.lblMsj.setText("No hay ninguna tabla cargada para exportar");
        }

    }//GEN-LAST:event_buttonIcon2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnPasar;
    private org.edisoncor.gui.button.ButtonColoredAction buttonColoredAction1;
    private org.edisoncor.gui.button.ButtonIcon buttonIcon2;
    private javax.swing.JComboBox comboAño;
    private javax.swing.JComboBox<String> comboMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JTable tablaCod;
    private javax.swing.JTable tablaGeneral;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    private org.edisoncor.gui.textField.TextFieldRound txtNameCod;
    // End of variables declaration//GEN-END:variables

    private void cargoComboAño() throws BDExcepcion {
        ArrayList<Integer> años = this.log.cargoAñosLiquidaciones();
        for(int i=0; i<años.size();i++){
         this.comboAño.addItem(años.get(i));
          this.comboAño.setSelectedIndex(0);
      }  
    }
    
     private Boolean esNum(String num){
        Boolean es=true;
        Integer i=0;
        if(num.equals("")){
            es=false;
        }
        while(i<num.length() && es){
            if(!Character.isDigit(num.charAt(i))){
                es=false;
            }
            i++;
        }
        return es;
    }

    public void buscarCodigo() throws BDExcepcion {
         String str=this.txtCod.getText().trim();
         
          if(this.esNum(str)){
              Integer codigo=Integer.valueOf(this.txtCod.getText().trim());
              this.cod=this.logCod.obtenerCodigo(codigo);
              if(cod!=null){
                  this.txtNameCod.setText(cod.getDescripcion());
                  this.btnPasar.requestFocus();
              }else{
                  this.txtNameCod.setText("CODIGO NO ENCONTRADO");
              }
          }
    }

    private void eliminarLinea(int row) {
        Codigo borrado=codigos.get(row);
        pasada++;
        if(codBorrado==null){
        this.codBorrado=codigos.get(row);
        this.codigos.remove(row);
        this.recargarTabla();
        tablaTamaño=codigos.size();
        }
        if(pasada==row){
            codBorrado=null;
            pasada=0;
        }
       
        
    }
//    
//    private class JTableButtonRenderer implements TableCellRenderer {        
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            JButton button = (JButton)value;
//            return button;  
//        }
//    }
//    
    
  

    private void recargarTabla() {
     
        this.LimpiarTabla();
//        tablaCod.addMouseListener(new JTableButtonMouseListener(tablaCod));
        DefaultTableModel modelo = (DefaultTableModel)tablaCod.getModel();
        Object[] filas=new Object[modelo.getColumnCount()];
         for(Codigo c: codigos){
                            filas[0]=c.getCod();
                            filas[1]=c.getDescripcion();
//                            TableCellRenderer buttonRenderer = new JTableButtonRenderer();
//                            JButton button = new JButton("Eliminar");
//                            filas[2]=button;
//                            tablaCod.getColumn("Eliminar").setCellRenderer(buttonRenderer);
                            modelo.addRow(filas);
         }
        JTableHeader th; 
        th = tablaCod.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
         
    }
    
//    private  class JTableButtonMouseListener extends MouseAdapter {
//        private JTable table;
//        
//        public JTableButtonMouseListener(JTable table) {
//            this.table = table;
//        }
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            
//            int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
//            int row    = e.getY()/table.getRowHeight(); //get the row of the button
//
//           /*Checking the row or column is valid or not*/
//            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
//                Object value = table.getValueAt(row, column);
//                if (value instanceof JButton) {
//                    /*perform a click event*/
//                    ((JButton)value).doClick();
//                        InternalListadoPorCodigos.instancia.eliminarLinea(row);
//                         
//                }
//           
//          }
//        }
//    }
      
    
    
     public void LimpiarTabla() {
         
    DefaultTableModel modelo=(DefaultTableModel) tablaCod.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaCod.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }

    private void cargoTablaGeneral() {
        this.LimpiarTablaGeneral();
        this.Alinear_Grillas();
        DefaultTableModel modelo = (DefaultTableModel)tablaGeneral.getModel();
        Object[] filas=new Object[modelo.getColumnCount()];
         for(Liquidacion l: liquidaciones){
                            filas[0]=this.armaFecha(this.formateo(l.getFechaLiq()));
                            filas[1]=l.getCodigo().getCod();
                            filas[2]=l.getF().getCodFunc();
                            filas[3]=l.getF().getNomCompletoApe();
                            filas[4]=this.decimales(l.getImporte());
                            filas[5]=this.decimales(l.getImporteNoRet());
                      
                            modelo.addRow(filas);
         }
        JTableHeader th; 
        th = tablaCod.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
    }
    
     private void Alinear_Grillas(){
        DefaultTableCellRenderer modelo = new DefaultTableCellRenderer();
        modelo.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tablaGeneral.getColumnModel().getColumn(1).setCellRenderer(modelo); 
        this.tablaGeneral.getColumnModel().getColumn(2).setCellRenderer(modelo); 
        this.tablaGeneral.getColumnModel().getColumn(4).setCellRenderer(modelo);
        this.tablaGeneral.getColumnModel().getColumn(5).setCellRenderer(modelo);    

    }
    
    private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
   
    }
    
     
     private String formateo(Date hoy){
      String retorno="";
      if(hoy!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
         retorno=formateador.format(hoy);
      }
      return retorno;
     }  
     
      private String armaFecha(String fecha) {
        String mes = fecha.substring(3, 5);
        String año = fecha.substring(6, 10);
        String retorno="";
        switch (mes) {
            case "01":
                retorno="ENERO/"+año;
                break;
            case "02":
                retorno="FEBRERO/"+año; 
                break;
            case "03":
                retorno="MARZO/"+año;
                break;
            case "04":
                retorno="ABRIL/"+año;
                break;
            case "05":
                retorno="MAYO/"+año; 
                break;
            case "6":
                retorno="JUNIO/"+año;
                break;
            case "07":
                retorno="JULIO/"+año;
                break;
            case "08":
                retorno="AGOSTO/"+año; 
                break;
            case "09":
                retorno="SETIEMBRE/"+año;
                break;
            case "10":
                retorno="OCTUBRE/"+año;
                break;
            case "11":
                retorno="NOVIEMBRE/"+año; 
                break;
            case "12":
                retorno="DICIEMBRE/"+año;
                break;
            default:
                break;
        }
       return retorno; 
    }

    private void LimpiarTablaGeneral() {
       DefaultTableModel modelo=(DefaultTableModel) tablaGeneral.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaGeneral.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }

    private void procesarExcel() throws IOException {
         javax.swing.filechooser.FileNameExtensionFilter filterXls = new javax.swing.filechooser.FileNameExtensionFilter("Documentosxcel 95/2003", "xls");

        File fileXLS = null;
        FileOutputStream fileOut = null;
            
            String[] headers = new String[]{
                "Fecha Liquidacion",//0
                "Código Mov",//1
                "Cobro",//2
                "Nombre",//3
                "Importe",//4
                "No Retenido",//5
                
                
             };
            String mes =(String) this.comboMes.getSelectedItem();
            int año = (int) this.comboAño.getSelectedItem();
            
            
            String ruta="C:\\SUELDOSLIST\\LISTADO DE DESCUENTOS POR CODIGOS "+mes+"-"+año+".xls"; 
          
            fileOut = new FileOutputStream(ruta);
              
            Workbook libro = new HSSFWorkbook();
            Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
          
            HSSFCellStyle style3 = (HSSFCellStyle) libro.createCellStyle();
            org.apache.poi.ss.usermodel.Font font = libro.createFont();
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            font.setBold(true);
            style3.setFont(font);
            
            CellStyle estilo = libro.createCellStyle();
            estilo.setAlignment(CellStyle.ALIGN_RIGHT);

            hoja.addMergedRegion(new CellRangeAddress(0,0,0,3));
            Row fu=hoja.createRow(0);
            Cell cel = fu.createCell(0);
            
            cel.setCellValue("LISTADO DE DESCUENTOS POR CODIGOS "+mes+"-"+año); 
            
             int h=2;
           
             for(int i=0;i<=liquidaciones.size();i++){
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
                                             celda.setCellValue(this.armaFecha(this.formateo(liquidaciones.get(i-1).getFechaLiq())));
                                        break;
                                        case 1:
                                             celda.setCellValue(liquidaciones.get(i-1).getCodigo().getCod());
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 2:
                                             celda.setCellValue(liquidaciones.get(i-1).getF().getCodFunc());
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 3:
                                             celda.setCellValue(liquidaciones.get(i-1).getF().getNomCompletoApe());
                                        break;
                                        case 4:
                                             String t=this.decimales(liquidaciones.get(i-1).getImporte());
                                             celda.setCellValue(t);
                                             celda.setCellStyle(estilo);
                                        break;
                                        case 5:
                                            t=this.decimales(liquidaciones.get(i-1).getImporteNoRet());
                                            celda.setCellValue(t);
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
         
            libro.write(fileOut);
             //Cerramos nuestro archivo
            fileOut.close();
        
    }

    
     
    }
    

