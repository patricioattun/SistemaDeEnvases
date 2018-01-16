
package Presentacion.Liquidaciones;

import Dominio.Codigo;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Presentacion.frmPrin;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.edisoncor.gui.button.ButtonIcon;
import org.edisoncor.gui.textField.TextFieldRound;


public class InternalFijoPorCod extends javax.swing.JInternalFrame {
    private LogCodigo logs;
    private LogFuncionario log;
    private InternalListadoFuncFiltro listadoFunc;
    private Funcionario f;
    Codigo cod=null;
    private Ingreso ingres=null;
    DefaultTableModel tmMov=null;
    private static InternalFijoPorCod instancia=null;
    public InternalFijoPorCod() throws ClassNotFoundException, SQLException {
        initComponents();
        log=new LogFuncionario();
        logs=new LogCodigo();
        this.cargaCodigos();
        this.txtCant.setEditable(false);
        habilita(false);
        
            tmMov = (DefaultTableModel) tabla.getModel();
           tabla.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==1){
                    try {
                        cargarMovimiento(e);
                    } catch (ParseException ex) {
                        Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                
         }

       });
    }
    
    
    private void cargarMovimiento(MouseEvent e) throws ParseException {
     Integer m=this.tabla.rowAtPoint(e.getPoint());
     this.ingres=new Ingreso();
     ingres.setCodFunc(Integer.valueOf(String.valueOf(tmMov.getValueAt(m, 0))));
     ingres.setCantidad(Double.valueOf(String.valueOf(tmMov.getValueAt(m, 2))));
     ingres.setCodMov(cod);
    }
    

   public static InternalFijoPorCod instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalFijoPorCod();
         }
         return instancia;
      
   }

    public ButtonIcon getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(ButtonIcon btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public TextFieldRound getTxtNumFunc() {
        return txtNumFunc;
    }

    public void setTxtNumFunc(TextFieldRound txtNumFunc) {
        this.txtNumFunc = txtNumFunc;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel5 = new javax.swing.JLabel();
        comboCodigos = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        txtNumFunc = new org.edisoncor.gui.textField.TextFieldRound();
        btnBuscar = new org.edisoncor.gui.button.ButtonIcon();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        txtVal = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtCant = new org.edisoncor.gui.textField.TextFieldRound();

        jMenuItem1.setText("Eliminar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setIconifiable(true);
        setTitle("Carga de Códigos Fijos Por Código");
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

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Código");

        comboCodigos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCodigosItemStateChanged(evt);
            }
        });
        comboCodigos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboCodigosMouseReleased(evt);
            }
        });

        txtNumFunc.setBackground(new java.awt.Color(102, 153, 255));
        txtNumFunc.setForeground(new java.awt.Color(255, 255, 255));
        txtNumFunc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumFunc.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNumFunc.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtNumFunc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumFuncKeyTyped(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(102, 153, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lupa.png"))); // NOI18N
        btnBuscar.setText("buttonIcon1");
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(102, 102, 102));
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNombre.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtVal.setBackground(new java.awt.Color(102, 153, 255));
        txtVal.setForeground(new java.awt.Color(255, 255, 255));
        txtVal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVal.setCaretColor(new java.awt.Color(255, 255, 255));
        txtVal.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtVal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Nro. Funcionario ");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Nombre de Funcionario");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Valor");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Func", "Nombre", "Valor", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setResizable(false);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setResizable(false);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(3).setResizable(false);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Totales:");

        txtCant.setEditable(false);
        txtCant.setBackground(new java.awt.Color(102, 102, 102));
        txtCant.setForeground(new java.awt.Color(255, 255, 255));
        txtCant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCant.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCant.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtVal, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jLabel3))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtVal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboCodigos, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboCodigos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboCodigosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCodigosItemStateChanged
        if(this.comboCodigos.getSelectedIndex()!=0){
            cod=(Codigo) this.comboCodigos.getSelectedItem();
            if(cod!=null){
                this.LimpiarTabla();
                try {
                    this.txtCant.setText("");
                    this.cargaTabla();
                    habilita(true);
                    this.txtNumFunc.requestFocus();
                 
                } catch (SQLException ex) {
                    Logger.getLogger(InternalFijoPorCod.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalFijoPorCod.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else{
            this.LimpiarTabla();
            habilita(false);
            this.txtCant.setText("");
        }
        this.txtNombre.setText("");
        this.txtNumFunc.setText("");
        this.txtVal.setText("");
        
    }//GEN-LAST:event_comboCodigosItemStateChanged

    private void habilita(boolean h){
        this.txtNumFunc.setEditable(h);
        this.txtVal.setEditable(h);
        this.btnBuscar.setEnabled(h);
    }
    private void comboCodigosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCodigosMouseReleased
        this.txtNombre.requestFocus();
    }//GEN-LAST:event_comboCodigosMouseReleased

    private void txtNumFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFuncKeyTyped
        this.txtVal.setText("");
        this.txtNombre.setText("");
        //this.cod=null;
        //this.codigos=null;
        //this.LimpiarTabla();
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(k==10){
            this.btnBuscar.doClick();
        }
        if(evt.getKeyChar()==43){
            
            try {
                listadoFunc=InternalListadoFuncFiltro.instancia(log,3);
                frmPrin prin=frmPrin.instancia();
                if (!listadoFunc.isVisible()) {
                    prin.getDesktop().add(listadoFunc);
                    listadoFunc.setLocation((prin.getDesktop().getWidth()/70)-(listadoFunc.getWidth()/70),(prin.getDesktop().getHeight()/70) - listadoFunc.getHeight()/70);
                    listadoFunc.setVisible(true);
                    listadoFunc.repaint();
                    listadoFunc.revalidate();
                }
                else{
                    listadoFunc.requestFocus();
                    try {
                        listadoFunc.setSelected(true);
                        listadoFunc.setVisible(true);
                        listadoFunc.repaint();
                        listadoFunc.revalidate();

                    } catch (PropertyVetoException ex) {
                        //lblMensaje.setText(ex.getMessage());
                    }
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalFijoPorCod.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalFijoPorCod.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtNumFuncKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String numFunc=this.txtNumFunc.getText();
        //this.codigos=null;
        if(this.esNum(numFunc)){
            try {
                f = this.log.funcParcial(numFunc);
                if(f!=null){
                    this.txtNombre.setText(f.getNomCompleto());
                    if(!cod.getCod().equals(44)){
                        this.txtVal.setText("1");
                    }
                    else{
                        this.txtVal.setText("");
                    }
                    this.txtVal.requestFocus();
                    //                    this.inicializo(true);
                    //                    this.LimpiarTabla();
                    //                    this.recargaTabla(f.getCodFunc());

                }
                else{
                    this.txtNumFunc.requestFocus();
                    this.txtNombre.setText("El funcionario no existe");
                    this.txtNumFunc.selectAll();
                    f=null;
                    //                    this.LimpiarTabla();
                    //                    this.inicializo(false);

                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtValKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValKeyTyped
        Date fecha=new Date();
        int k = (int) evt.getKeyChar();
        char c=evt.getKeyChar();
        if(k!=46){
            if(!Character.isDigit(c)){
                evt.consume();
            }
        }
        if(cod!=null){
            if(!cod.getCod().equals(44)){
                evt.consume();
                this.txtVal.setText("1");
            }
        }
        
        if(k==10){
            if(cod!=null&&f!=null){
                try {
                    String str=this.txtVal.getText().trim();
                    if(!this.logs.estaEnCodigosFijos(f,cod)&&!str.equals("0")&&!str.equals("")){
                        Ingreso ing=new Ingreso();
                        ing.setCodMov(cod);
                        ing.setFecha(fecha);
                        ing.setFunc(f);
                        if(!cod.getCod().equals(44)){
                        ing.setCantidad(Double.parseDouble("1"));
                        }
                        else{
                          ing.setCantidad(Double.parseDouble(str));
                        }
                        this.txtVal.requestFocus();
                        
                        if(this.logs.insertaEnCodigosFijos(ing)){
                            this.LimpiarTabla();
                            this.cargaTabla();
                            this.txtNombre.setText("");
                            this.txtNumFunc.setText("");
                            this.txtVal.setText("");
                            this.txtNumFunc.requestFocus();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Este código ya fue ingresado");
                        this.txtNombre.setText("");
                        this.txtNumFunc.setText("");
                        this.txtVal.setText("");
                        this.txtNumFunc.requestFocus();
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtValKeyTyped

    private void txtCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantKeyTyped

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
         if(ingres!=null){
           int respuesta=JOptionPane.showConfirmDialog(this, "Seguro desea eliminar esta línea?");
           //si es 0,no es 1 cancela es 2
           if(respuesta==0){
               
                    try {
                        
                        if(this.logs.borrarCodigoEnPersCodFijo(ingres)){
                            this.LimpiarTabla();  
                            this.cargaTabla();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
             }
       }
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnBuscar;
    private javax.swing.JComboBox comboCodigos;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtCant;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtNumFunc;
    private org.edisoncor.gui.textField.TextFieldRound txtVal;
    // End of variables declaration//GEN-END:variables

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
    
    private void cargaCodigos() throws SQLException, ClassNotFoundException {
         ArrayList<Codigo> c=this.logs.cargaComboCodigoFijo();
      this.comboCodigos.addItem("SELECCIONE CÓDIGO");  
      for(int i=0; i<c.size();i++){
          this.comboCodigos.addItem(c.get(i));
          this.comboCodigos.setSelectedIndex(0);
      }
    }

    private void cargaTabla() throws SQLException, ClassNotFoundException {
        this.Alinear_Grillas();
        ArrayList<Ingreso> lista=this.logs.cargaMovimientosFijo(cod.getCod());
         DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
         Object[] filas=new Object[modelo.getColumnCount()];
         int cuenta=0; 
         int posicion=0;
         for(Ingreso i:lista){
                            if(f!=null){
                                if(f.getCodFunc().equals(i.getFunc().getCodFunc())){
                                    posicion=cuenta;
                                }
                            }
                            filas[0]=i.getFunc().getCodFunc();
                            filas[1]=i.getFunc().getNomCompleto();
                            filas[2]=i.getCantidad(); 
                            filas[3]=this.formateo(i.getFecha());
                            //RenderCodigos rr=new RenderCodigos(4);
                           // tabla.setDefaultRenderer(Object.class, rr);
                           cuenta++;
                           modelo.addRow(filas);
                           
                }
                    
               
        
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.changeSelection(posicion, 0, false, false);
        this.sumaCeldas();
   
        f=null;
    }
    
     private void sumaCeldas(){
        double sumaCant=0.0;
        int totalRow= tabla.getRowCount();
        sumaCant=Double.parseDouble(String.valueOf(totalRow));
        this.txtCant.setText(String.valueOf(sumaCant));
     }
    public void LimpiarTabla() {
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }
     private String formateo(Date hoy){
      String retorno="";
      if(hoy!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
         retorno=formateador.format(hoy);
      }
      return retorno;
     }  
     
        private void Alinear_Grillas(){
        DefaultTableCellRenderer modelo = new DefaultTableCellRenderer();
        modelo.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabla.getColumnModel().getColumn(0).setCellRenderer(modelo); 
        this.tabla.getColumnModel().getColumn(2).setCellRenderer(modelo); 
        this.tabla.getColumnModel().getColumn(3).setCellRenderer(modelo);    

    }
}
