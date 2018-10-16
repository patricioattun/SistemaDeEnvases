
package Presentacion.Liquidaciones;

import Dominio.Funcionario;
import Dominio.Ingreso;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import Persistencia.PersistenciaLiquidacion;
import Presentacion.frmPrin;
import java.awt.Color;
import java.awt.Font;
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
import org.edisoncor.gui.textField.TextFieldRound;

public class InternalVacacionales extends javax.swing.JInternalFrame {

    private Funcionario f;
    private LogFuncionario log;
    private ArrayList<Ingreso> ingresos;
    private PersistenciaLiquidacion persLiq;
    private ArrayList<Funcionario> funcionarios;
    private LogCodigo logCod;
    private static InternalVacacionales instancia=null;
    
    public InternalVacacionales() throws ClassNotFoundException, SQLException {
        initComponents();
        log=new LogFuncionario();
        this.txtFechaLiq.setText(this.log.fechaLiquidacion());
        this.funcionarios=new ArrayList<>();
        logCod=new LogCodigo();
    }

    public static InternalVacacionales getInstancia() {
        return instancia;
    }

    public static void setInstancia(InternalVacacionales instancia) {
        InternalVacacionales.instancia = instancia;
    }
    
    
    
   public static InternalVacacionales instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalVacacionales();
         }
         return instancia;
      
   }

    public TextFieldRound getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(TextFieldRound txtNombre) {
        this.txtNombre = txtNombre;
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
        jLabel2 = new javax.swing.JLabel();
        txtNumFunc = new org.edisoncor.gui.textField.TextFieldRound();
        btnBuscar = new org.edisoncor.gui.button.ButtonIcon();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel3 = new javax.swing.JLabel();
        txtFechaLiq = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnLiquidar = new org.edisoncor.gui.button.ButtonIcon();
        lblMsj = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnAgregar = new org.edisoncor.gui.button.ButtonRound();
        btnEliminarTodos = new org.edisoncor.gui.button.ButtonRound();

        jMenuItem1.setText("Eliminar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setIconifiable(true);
        setTitle("Liquidación de Salarios Vacacionales");
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

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Nro. Funcionario ");

        txtNumFunc.setBackground(new java.awt.Color(102, 153, 255));
        txtNumFunc.setForeground(new java.awt.Color(255, 255, 255));
        txtNumFunc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumFunc.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNumFunc.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtNumFunc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumFuncFocusGained(evt);
            }
        });
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

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Nombre de Funcionario");

        txtFechaLiq.setEditable(false);
        txtFechaLiq.setBackground(new java.awt.Color(102, 102, 102));
        txtFechaLiq.setForeground(new java.awt.Color(255, 255, 255));
        txtFechaLiq.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaLiq.setCaretColor(new java.awt.Color(255, 255, 255));
        txtFechaLiq.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtFechaLiq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaLiqKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Fecha de Liquidación");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("LIQUIDAR");

        btnLiquidar.setBackground(new java.awt.Color(102, 153, 255));
        btnLiquidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/calcular.png"))); // NOI18N
        btnLiquidar.setText("buttonIcon1");
        btnLiquidar.setToolTipText("Buscar");
        btnLiquidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidarActionPerformed(evt);
            }
        });

        lblMsj.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Func", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setComponentPopupMenu(jPopupMenu1);
        tabla.setInheritsPopupMenu(true);
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(70);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(0).setMaxWidth(70);
            tabla.getColumnModel().getColumn(1).setMinWidth(350);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(350);
            tabla.getColumnModel().getColumn(1).setMaxWidth(350);
        }

        btnAgregar.setBackground(new java.awt.Color(153, 255, 0));
        btnAgregar.setText("Agregar a Liquidación");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminarTodos.setBackground(new java.awt.Color(255, 0, 0));
        btnEliminarTodos.setText("Eliminar Todos");
        btnEliminarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                            .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(38, 38, 38))
                                    .addComponent(btnLiquidar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEliminarTodos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumFuncFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFuncFocusGained
        this.txtNumFunc.selectAll();
    }//GEN-LAST:event_txtNumFuncFocusGained

    private void txtNumFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFuncKeyTyped
        this.f=null;
        this.ingresos=null;
        this.txtNombre.setText("");
        this.lblMsj.setText("");
        
        int k = (int) evt.getKeyChar();

        if(this.txtNumFunc.getText().length()>3){
            evt.consume();
        }
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(k==10){
            this.btnBuscar.doClick();

        }
        if(evt.getKeyChar()==43){
            this.txtNumFunc.setText("");
            try {
                InternalListadoFuncFiltro listadoFunc=InternalListadoFuncFiltro.instancia(log,11);
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
                    listadoFunc.setSelected(true);
                    listadoFunc.setVisible(true);
                    listadoFunc.repaint();
                    listadoFunc.revalidate();

                }

            } catch (ClassNotFoundException | SQLException | PropertyVetoException ex) {
                this.lblMsj.setText(ex.getMessage());
            }
        }
    }//GEN-LAST:event_txtNumFuncKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String numFunc=this.txtNumFunc.getText();

        if(this.esNum(numFunc)){

            try {
                f = this.log.buscarFuncionario(numFunc);
                if(f.getApellido1()!=null){
                    this.txtNombre.setText(f.getNomCompletoApe());

                }
                else{
                    this.txtNumFunc.requestFocus();
                    this.txtNombre.setText("El funcionario no existe");
                    this.txtNumFunc.selectAll();
                    f=null;

                }
            } catch (ClassNotFoundException | SQLException ex) {
                this.lblMsj.setText(ex.getMessage());
            }

        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtFechaLiqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaLiqKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaLiqKeyTyped

    private void btnLiquidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidarActionPerformed
        try {
            if(this.logCod.bloqueoContaduria()==0){
            Date fecha = this.stringADate(this.txtFechaLiq.getText());
            this.persLiq = new PersistenciaLiquidacion(fecha,3);
            ingresos=this.persLiq.verificoPrelacion(null);
                if(ingresos.isEmpty()){
                   
                    if(funcionarios!=null){
                        if(funcionarios.size()>0){
                            for(Funcionario k: funcionarios){
                                InternalVacacionales.instancia().getTxtNombre().setText(k.getNomCompletoApe());
                                InternalVacacionales.instancia().getTxtNumFunc().setText(k.getCodFunc().toString());
                                InternalVacacionales.instancia().repaint();
                                InternalVacacionales.instancia().getTxtNumFunc().paint( InternalVacacionales.instancia().getTxtNumFunc().getGraphics());
                                InternalVacacionales.instancia().getTxtNombre().paint( InternalVacacionales.instancia().getTxtNombre().getGraphics());
                                InternalVacacionales.instancia().revalidate();
                                this.persLiq.liquidar(k,fecha,3,false);
                            }
                            InternalVacacionales.instancia().getTxtNombre().setText("Liquidación Finalizada");
                            InternalVacacionales.instancia().getTxtNumFunc().setText("");
                            InternalVacacionales.instancia().repaint();
                            InternalVacacionales.instancia().getTxtNombre().paint( InternalVacacionales.instancia().getTxtNombre().getGraphics());
                            InternalVacacionales.instancia().getTxtNumFunc().paint( InternalVacacionales.instancia().getTxtNumFunc().getGraphics());
                            InternalVacacionales.instancia().revalidate();
                            f=null;
                            this.log.actualizaParametros("1", "0", "BLOQUEO_CONTADURIA");
                        }
                    }

                }else{
                    this.cargoNoPrelacion(ingresos,true);
                }
            }else{
               JOptionPane.showMessageDialog(this, "En estos momentos está bloqueado. Vuelva a activar la liquidación");
           }

        } catch (ParseException | ClassNotFoundException | SQLException | BDExcepcion ex) {
            this.lblMsj.setText(ex.getMessage());
            Logger.getLogger(InternalLiquidaSueldo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnLiquidarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if(f!=null){
            if(!this.esta(f)){
                this.funcionarios.add(f);
                this.LimpiarTabla();
                this.cargarTabla();
            }else{
                this.lblMsj.setText("Este funcionario ya esta cargado");
            }
        }
       this.txtNombre.setText("");
       this.txtNumFunc.setText("");
       this.txtNumFunc.requestFocus();
       f=null;
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodosActionPerformed
        this.funcionarios.clear();
        this.LimpiarTabla();
        this.txtNombre.setText("");
        this.txtNumFunc.setText("");
        this.lblMsj.setText("");
        this.txtNumFunc.requestFocus();
        f=null;
    }//GEN-LAST:event_btnEliminarTodosActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
        try {
            this.log.actualizaParametro("", "USUARIO_LIQ");
        } catch (BDExcepcion ex) {
            Logger.getLogger(InternalVacacionales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameClosed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       int i = tabla.getSelectedRow();
       this.funcionarios.remove(i);
       this.LimpiarTabla();
       this.cargarTabla();
       this.txtNombre.setText("");
       this.txtNumFunc.setText("");
       this.txtNumFunc.requestFocus();
       f=null;
    }//GEN-LAST:event_jMenuItem1ActionPerformed

     public void LimpiarTabla() {
        
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
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
    
     private Date stringADate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(s);
        return date;
    }
     
     private void cargoNoPrelacion(ArrayList<Ingreso> ingresos, boolean todos) {
             try {
           InternalNoPrelaciones internal=InternalNoPrelaciones.instancia(ingresos,todos);
           if (!internal.isVisible()) {
               frmPrin.instancia().getDesktop().add(internal);
               internal.setLocation((frmPrin.instancia().getDesktop().getWidth()/2)-(internal.getWidth()/2),(frmPrin.instancia().getDesktop().getHeight()/2) - internal.getHeight()/2);
               internal.setVisible(true);
                       
           }
           else{
               internal.requestFocus();
               try {
                   internal.setSelected(true);
                   //internalFijoPorCod.getTxtCod().requestFocus();
               } catch (PropertyVetoException ex) {
                   //lblMensaje.setText(ex.getMessage());
               }
           }
           
           // this.pnlContenido.add(pnlAjustes);
           this.repaint();
           this.revalidate();
       } catch (ClassNotFoundException | SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonRound btnAgregar;
    private org.edisoncor.gui.button.ButtonIcon btnBuscar;
    private org.edisoncor.gui.button.ButtonRound btnEliminarTodos;
    private org.edisoncor.gui.button.ButtonIcon btnLiquidar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtFechaLiq;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtNumFunc;
    // End of variables declaration//GEN-END:variables

    private void cargarTabla() {
         this.LimpiarTabla();
         this.Alinear_Grillas();
         
         DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
         Object[] filas=new Object[modelo.getColumnCount()];
         int posicion=0;
         for(Funcionario f:funcionarios){
                         
                            filas[0]=f.getCodFunc();
                            filas[1]=f.getNomCompletoApe();
                                                
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
    }
    
    private void Alinear_Grillas(){
        DefaultTableCellRenderer modelo = new DefaultTableCellRenderer();
        modelo.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabla.getColumnModel().getColumn(0).setCellRenderer(modelo); 
    }

    private boolean esta(Funcionario f) {
        boolean es=false;
        int i =0;
        if(funcionarios.size()>0){
            while(i<funcionarios.size() && !es){
                if(f.getCodFunc().equals(funcionarios.get(i).getCodFunc())){
                    es=true;
                }
                i++;
            }
        }
        return es;
    }
}
