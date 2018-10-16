
package Presentacion.Reportes;

import Dominio.Banco;
import Dominio.CentroCosto;
import Dominio.Funcionario;
import Dominio.Horario;
import Dominio.Sns;
import Dominio.Sucursal;
import Logica.LogFuncionario;
import Logica.logPdf;
import Persistencia.BDExcepcion;

import Presentacion.Mantenimiento.InternalModFunc;

import Presentacion.frmPrin;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class InternalListadoFunc extends javax.swing.JInternalFrame {

   DefaultTableModel tm=null;
    ArrayList<Funcionario> listado;
    private LogFuncionario LogF;
    private static InternalListadoFunc instancia=null;
    private Integer codfunc;
    ArrayList<Funcionario> funcionarios;
    private logPdf logPdf;
    private InternalListadoFunc() throws ClassNotFoundException, SQLException {
        initComponents();
         this.LogF=new LogFuncionario();
         this.pnlTabla.setVisible(false);
         this.cargaCombo();
         this.jPanel1.setVisible(true);
         
         tm = (DefaultTableModel) tablaFunc.getModel();
         tablaFunc.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
         tablaFunc.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    try {
                        if(frmPrin.instancia().getUsuario().getPermiso().getPermiso().equals(2)||frmPrin.instancia().getUsuario().getPermiso().getPermiso().equals(1)){
                        pasarAFicha(e);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No tiene permiso para acceder a la ficha de Funcionario");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
                else if(e.getClickCount()==1){
                        cargarFuncionario(e);
                }
         }});
    }
    
    
    private void cargarFuncionario(MouseEvent e){
        Integer m=this.tablaFunc.rowAtPoint(e.getPoint());
        this.codfunc=Integer.valueOf(String.valueOf(tm.getValueAt(m,0)));
    }
    
  public static InternalListadoFunc instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalListadoFunc();
         }
         return instancia;
      
   }
    
    private void pasarAFicha(java.awt.event.MouseEvent e) throws SQLException, ClassNotFoundException{
     Integer m=this.tablaFunc.rowAtPoint(e.getPoint());
       String dato=String.valueOf(tm.getValueAt(m,0));
       frmPrin prin=frmPrin.instancia();
               InternalModFunc pnl=InternalModFunc.instancia();
               //this.frm.getPnlContenido().add(pnl);
                pnl.getTxtNumFunc().setText(dato);
                pnl.getBtnBuscar().doClick();
                             
      
         
        if (!pnl.isVisible()) {
            prin.getDesktop().add(pnl);
            pnl.setLocation((prin.getDesktop().getWidth()/2)-(pnl.getWidth()/2),(prin.getDesktop().getHeight()/2) - pnl.getHeight()/2);
            pnl.setVisible(true);
        }
        else{
            pnl.requestFocus();
            try {
                pnl.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
           this.repaint();
           this.revalidate();
                
                
    }
    
    private void cargaCombo() throws SQLException, ClassNotFoundException {
    ArrayList<Sucursal> sucursales=LogF.cargaComboSucursal();
      for(int i=0; i<sucursales.size();i++){
         this.comboSucursal.addItem(sucursales.get(i));
          this.comboSucursal.setSelectedIndex(0);
      }   
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoTabla = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnListar = new org.edisoncor.gui.button.ButtonIcon();
        buttonIcon2 = new org.edisoncor.gui.button.ButtonIcon();
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFunc = new javax.swing.JTable();
        checkActivos = new javax.swing.JRadioButton();
        checkInactivos = new javax.swing.JRadioButton();
        checkTodos = new javax.swing.JRadioButton();
        lblMsg = new javax.swing.JLabel();
        comboSucursal = new javax.swing.JComboBox();
        txtFiltro = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();

        jMenuItem1.setText("Generar PDF");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Editar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setMaximumSize(new java.awt.Dimension(1280, 768));
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
        getContentPane().setLayout(new java.awt.FlowLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reportes de Funcionarios", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 18))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 650));

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel1.setText("Filtro");

        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tabla.png"))); // NOI18N
        btnListar.setText("buttonIcon1");
        btnListar.setToolTipText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
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

        pnlTabla.setPreferredSize(new java.awt.Dimension(1000, 700));
        pnlTabla.setLayout(null);

        tablaFunc.setAutoCreateRowSorter(true);
        tablaFunc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num. Funcionario", "Apellido1", "Apellido2", "Nombre1", "Nombre2", "Dirección", "Localidad", "Departamento", "Telefono", "Celular", "Cédula", "Credencial", "Fecha de Nacimiento", "Estado Civil", "Sexo", "Iniciales", "Fecha de Ingreso", "Fecha de Egreso", "Sueldo", "Centro Costo", "Vigencia Cargo", "Base Horaria", "Base Horas", "Lugar de Trabajo", "Afap", "Cuenta", "Banco", "Tipo de Cuenta", "Num. Socio", "Horario", "SNS", "Vencimiento Carne"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaFunc.setToolTipText("");
        tablaFunc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablaFunc.setComponentPopupMenu(jPopupMenu1);
        tablaFunc.setMinimumSize(new java.awt.Dimension(700, 64));
        tablaFunc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaFuncMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaFunc);

        pnlTabla.add(jScrollPane1);
        jScrollPane1.setBounds(0, 0, 960, 460);

        grupoTabla.add(checkActivos);
        checkActivos.setText("Funcionarios Activos");
        checkActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkActivosMouseClicked(evt);
            }
        });

        grupoTabla.add(checkInactivos);
        checkInactivos.setText("Funcionarios Inactivos");
        checkInactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkInactivosMouseClicked(evt);
            }
        });

        grupoTabla.add(checkTodos);
        checkTodos.setSelected(true);
        checkTodos.setText("Todos");
        checkTodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkTodosMouseClicked(evt);
            }
        });

        lblMsg.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        comboSucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODOS" }));
        comboSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboSucursalMouseClicked(evt);
            }
        });
        comboSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSucursalActionPerformed(evt);
            }
        });

        txtFiltro.setBackground(new java.awt.Color(102, 153, 255));
        txtFiltro.setForeground(new java.awt.Color(255, 255, 255));
        txtFiltro.setCaretColor(new java.awt.Color(255, 255, 255));
        txtFiltro.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel2.setText("Sucursal");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(comboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(checkActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(checkInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(checkTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(buttonIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lblMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(510, 510, 510))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboSucursal)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkActivos)
                    .addComponent(checkInactivos)
                    .addComponent(checkTodos))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(pnlTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        //       Sucursal suc=(Sucursal) this.comboSucursal.getSelectedItem();
        //       Integer codSuc=suc.getNumero();
        String filtro=this.txtFiltro.getText().toUpperCase();

        if(this.checkActivos.isSelected()&&this.comboSucursal.getSelectedIndex()<=0 ){
            try {
                listado=new ArrayList<>();
                listado=LogF.listadoFuncionariosActivos(filtro);
                this.cargarTabla(listado);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(this.checkTodos.isSelected()&&this.comboSucursal.getSelectedIndex()<=0){
            try {
                listado=new ArrayList<>();
                listado=LogF.listadoFuncionarios(filtro);
                this.cargarTabla(listado);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(this.checkInactivos.isSelected()&&this.comboSucursal.getSelectedIndex()<=0){

            try {
                listado=new ArrayList<>();
                listado=LogF.listadoFuncionariosInactivos(filtro);
                this.cargarTabla(listado);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else if(this.checkActivos.isSelected()&&this.comboSucursal.getSelectedIndex()>0){
            Sucursal suc=(Sucursal) this.comboSucursal.getSelectedItem();
            Integer cod=suc.getNumero();
            listado=new ArrayList<>();
            try {
                listado=LogF.listadoFuncionariosActivosPorSuc(cod,filtro);
                this.cargarTabla(listado);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else if(this.checkInactivos.isSelected()&&this.comboSucursal.getSelectedIndex()>0){
            Sucursal suc=(Sucursal) this.comboSucursal.getSelectedItem();
            Integer cod=suc.getNumero();
            listado=new ArrayList<>();
            try {
                listado=LogF.listadoFuncionariosInactivosPorSuc(cod,filtro);
                this.cargarTabla(listado);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else if(this.checkTodos.isSelected()&&this.comboSucursal.getSelectedIndex()>0){
            Sucursal suc=(Sucursal) this.comboSucursal.getSelectedItem();
            Integer cod=suc.getNumero();
            listado=new ArrayList<>();
            try {
                listado=LogF.listadoFuncionariosPorSuc(cod,filtro);
                this.cargarTabla(listado);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        Alinear_Grillas();
    }//GEN-LAST:event_btnListarActionPerformed

    private void buttonIcon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIcon2ActionPerformed
        
            if(listado!=null){
                try {
                    this.procesarExcel();
                } catch (IOException ex) {
                    Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                this.lblMsg.setText("No hay ninguna tabla cargada para exportar");
            }
         
    }//GEN-LAST:event_buttonIcon2ActionPerformed

    private void tablaFuncMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaFuncMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaFuncMouseClicked

    private void checkActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkActivosMouseClicked
        this.LimpiarTablaFuncionarios();
    }//GEN-LAST:event_checkActivosMouseClicked

    private void checkInactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkInactivosMouseClicked
        this.LimpiarTablaFuncionarios();
    }//GEN-LAST:event_checkInactivosMouseClicked

    private void checkTodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkTodosMouseClicked
        this.LimpiarTablaFuncionarios();
    }//GEN-LAST:event_checkTodosMouseClicked

    private void comboSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboSucursalMouseClicked
        this.LimpiarTablaFuncionarios();
    }//GEN-LAST:event_comboSucursalMouseClicked

    private void comboSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSucursalActionPerformed
        this.LimpiarTablaFuncionarios();
    }//GEN-LAST:event_comboSucursalActionPerformed

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
        this.LimpiarTablaFuncionarios();
        this.tablaFunc.repaint();
        this.lblMsg.setText("");
        int k = (int) evt.getKeyChar();
        if(k==10){
            this.btnListar.doClick();
        }
    }//GEN-LAST:event_txtFiltroKeyTyped

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int[] seleccionados = tablaFunc.getSelectedRows();
        int total=0;
        if(seleccionados.length>0){
            this.funcionarios=new ArrayList<>();
            for(int i=0;i<seleccionados.length;i++){
                
                try {
                    this.funcionarios.add(this.cargarFuncionarioMultiple(seleccionados[i]));
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            try {
                this.mandaAPdf();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(InternalListadoFunc.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
      try {
             Integer i = this.tablaFunc.getSelectedRow();
             Funcionario e = this.listado.get(i);
             if(e.getFechaEgreso()==null){
             InternalDatosSecretaria frm =InternalDatosSecretaria.instancia(e.getCodFunc().toString());
             frmPrin prin=frmPrin.instancia();
             if (!frm.isVisible()) {
                 prin.getDesktop().add(frm);
                 frm.setLocation((prin.getDesktop().getWidth()/2)-(frm.getWidth()/2),(prin.getDesktop().getHeight()/2) - frm.getHeight()/2);
                 frm.setVisible(true);
             }
             else{
                 frm.requestFocus();
                 frm.setSelected(true);
                 
                 
             }
             this.repaint();
             this.revalidate();
             }
             else{
                 JOptionPane.showMessageDialog(null, "Este funcionario actualmente está inactivo");
             }
         } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
         } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
         } catch (BDExcepcion ex) {
           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
       }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    
    private void mandaAPdf() throws ClassNotFoundException, SQLException, DocumentException, IOException{
        FileOutputStream fileOut = null;
        File filePDF = null;
        this.logPdf=new logPdf();
        try {

            javax.swing.filechooser.FileNameExtensionFilter filterPDF = new javax.swing.filechooser.FileNameExtensionFilter("DocumentosPdf", "pdf");
            String sFichero = "c:\\FICHAS DE FUNCIONARIOS";
            File fichero = new File(sFichero);

            if (fichero.isFile()){
                
            }
            else{
                String str="";
            }
            
            
            
            final JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filterPDF);
            fc.setSelectedFile(filePDF);
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int seleccion = fc.showSaveDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                filePDF = fc.getSelectedFile();
                String name = filePDF.getName();
              for(Funcionario f:funcionarios){
                name="FichaFuncionario"+f.getCodFunc();
                    name += ".pdf";
                    filePDF = new File(filePDF.getParentFile(), name);
                
                fileOut = new FileOutputStream(filePDF);
                this.logPdf.createPDFFichaFunc(filePDF,f);
                if(fileOut!=null){
                    fileOut.close();
                    
                }
              }
              
            }
            
        } 
        catch (FileNotFoundException ex) {
                 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalListadoMovLic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalListadoMovLic.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

           
         //                if(filePDF!=null){
//                   Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filePDF.getAbsolutePath());
//                   this.lblMsg.setText("");
//                }

            
            

        }
       
    }
    
    //Carga tabla    
    private void cargarTabla(ArrayList<Funcionario> listado ) throws ClassNotFoundException, SQLException{
            this.LimpiarTablaFuncionarios();
        Integer cont=listado.size();
        this.lblMsg.setText(cont + " Funcionarios mostrados.");
        this.lblMsg.setVisible(true);
        DefaultTableModel modelo = (DefaultTableModel)tablaFunc.getModel();
        
        Object[] filas=new Object[modelo.getColumnCount()];
        
        if(listado.size()>0){
                                                          
                    for(Funcionario f:listado){                       
                            
                            filas[0]=f.getCodFunc();
                            filas[1]=f.getApellido1();
                            filas[2]=f.getApellido2();
                            filas[3]=f.getNombre1();
                            filas[4]=f.getNombre2();
                            filas[5]=f.getDireccion();
                            filas[6]=f.getLocalidad();
                            filas[7]=f.getDepartamento().getNombre();
                            filas[8]=f.getTelefono();
                            filas[9]=f.getCelular();
                            filas[10]=f.getCedula();
                            filas[11]=f.getCredencial();
                            filas[12]=this.formateo(f.getFechaNac());
                            filas[13]=f.getEstadoCivil().getNombre();
                            filas[14]=f.getSexo();
                            filas[15]=f.getIniciales();
                            filas[16]=this.formateo(f.getFechaIngreso());
                            filas[17]=this.formateo(f.getFechaEgreso());
                            filas[18]=this.decimales(f.getSueldoCargo());
                            filas[19]=this.centro(f.getCentroCosto());
                            filas[20]=this.formateo(f.getVigenciaCargo());
                            filas[21]=f.getBaseHoraria();
                            filas[22]=f.getBaseHoras();
                            filas[23]=this.lugar(f.getLugarTrabajo());
                            filas[24]=this.afap(f.getAfap());
                            String cuenta=f.getCuenta().toString();
                            cuenta=cuenta.substring(0, cuenta.length()-2);
                            filas[25]=cuenta;
                            filas[26]=this.banco(f.getBanco());
                            filas[27]=this.cuentas(f.getTipoCuenta());
                            filas[28]=f.getNumSocio();
                            filas[29]=this.horarios(f.getHorarios());
                            filas[30]=this.sns(f.getSns());
                            filas[31]=this.formateo(f.getVencimientoCarne());
                            
                                              
                            //Renderizado rr = new Renderizado(6);
                            //tablaFunc.setDefaultRenderer(Object.class, rr);
                            modelo.addRow(filas);
                      }
                }
        this.resizeColumnWidth(tablaFunc);
        JTableHeader th; 
        th = tablaFunc.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 14); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente); 
        this.pnlTabla.setVisible(true);
        
    }

    
     private void Alinear_Grillas(){
            
        DefaultTableCellRenderer modelo = new DefaultTableCellRenderer();
        modelo.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tablaFunc.getColumnModel().getColumn(0).setCellRenderer(modelo); 
        this.tablaFunc.getColumnModel().getColumn(8).setCellRenderer(modelo); 
        this.tablaFunc.getColumnModel().getColumn(9).setCellRenderer(modelo);
        this.tablaFunc.getColumnModel().getColumn(10).setCellRenderer(modelo); 
        this.tablaFunc.getColumnModel().getColumn(18).setCellRenderer(modelo); 
        this.tablaFunc.getColumnModel().getColumn(21).setCellRenderer(modelo);  
        this.tablaFunc.getColumnModel().getColumn(22).setCellRenderer(modelo); 
        this.tablaFunc.getColumnModel().getColumn(25).setCellRenderer(modelo); 
        this.tablaFunc.getColumnModel().getColumn(28).setCellRenderer(modelo);  
            
    }
    
      //Procesar Excel
    public void procesarExcel() throws IOException{
    java.io.ByteArrayOutputStream memoryStream = null;
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
                "Num. Funcionario",//0
                "Apellido1",//1
                "Apellido2",//2
                "Nombre1",//3
                "Nombre2",//4
                "Dirección",//5
                "Localidad",//6
                "Departamento",//7
                "Teléfono",//8
                "Celular",//9
                "Cédula",//10
                "Credencial",//11
                "Fecha de Nacimiento",//12
                "Estado Civil",//13
                "Sexo",//14
                "Iniciales",//15
                "Fecha de Ingreso",//16
                "Fecha de Egreso",//17
                "Sueldo",//18
                "Centro Costo",//19
                "Vigencia Cargo",//20
                "Base Horaria",//21
                "Base Horas",//22
                "Lugar de Trabajo",//23
                "Afap",//24
                "Cuenta",//25
                "Banco",//26
                "Tipo de cuenta",//27
                "Num. Socio",//28
                "Horario",//29
                "SNS",//30
                "Fecha de Vencimiento",//31
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
                          
              for(int i=0;i<=listado.size();i++){
                  Row fila = hoja.createRow(i);
                  
                  
                  for(int c=0;c<headers.length;c++){
                    Cell celda = fila.createCell(c);
                 
                    hoja.setColumnWidth(c, 5000);
                    
                        if(i==0){
                            celda.setCellValue(headers[c]);

                        }
                            else{
                                    switch(c)
                                    {
                                        case 0:
                                            String num=String.valueOf(listado.get(i-1).getCodFunc());
                                            celda.setCellValue(num);
                                            break;
                                        case 1:
                                            celda.setCellValue(listado.get(i-1).getApellido1());
                                            break;
                                        case 2:
                                            celda.setCellValue(listado.get(i-1).getApellido2());
                                            break;
                                        case 3:
                                            celda.setCellValue(listado.get(i-1).getNombre1());
                                            break;   
                                        case 4:
                                            celda.setCellValue(listado.get(i-1).getNombre2());
                                            break;
                                        case 5:
                                            celda.setCellValue(listado.get(i-1).getDireccion());
                                            break;
                                        case 6:
                                            celda.setCellValue(listado.get(i-1).getLocalidad());
                                            break; 
                                        case 7:
                                            celda.setCellValue(listado.get(i-1).getDepartamento().getNombre());
                                            break; 
                                        case 8:
                                            celda.setCellValue(listado.get(i-1).getTelefono());
                                            break; 
                                        case 9:
                                            celda.setCellValue(listado.get(i-1).getCelular());
                                            break; 
                                        case 10:
                                            celda.setCellValue(listado.get(i-1).getCedula());
                                            break; 
                                        case 11:
                                            celda.setCellValue(listado.get(i-1).getCredencial());
                                            break; 
                                        case 12:
                                            if(listado.get(i-1).getFechaNac()!=null){
                                            celda.setCellValue(this.formateo(listado.get(i-1).getFechaNac()));
                                            }
                                            else{
                                            celda.setCellValue("");
                                            }
                                            break; 
                                        case 13:
                                            celda.setCellValue(listado.get(i-1).getEstadoCivil().getNombre());
                                            break; 
                                        case 14:
                                            celda.setCellValue(listado.get(i-1).getSexo());
                                            break; 
                                        case 15:
                                            celda.setCellValue(listado.get(i-1).getIniciales());
                                            break; 
                                        case 16:
                                            if(listado.get(i-1).getFechaIngreso()!=null){
                                            celda.setCellValue(this.formateo(listado.get(i-1).getFechaIngreso()));
                                            }
                                            else{
                                            celda.setCellValue("");
                                            }
                                            break; 
                                        case 17:
                                            if(listado.get(i-1).getFechaEgreso()!=null){
                                            celda.setCellValue(this.formateo(listado.get(i-1).getFechaEgreso()));
                                            }
                                            else{
                                                  celda.setCellValue("");
                                            }
                                            break; 
                                        case 18:
                                            celda.setCellValue(this.decimales(listado.get(i-1).getSueldoCargo()));
                                            break; 
                                        case 19:
                                            celda.setCellValue(this.centro(listado.get(i-1).getCentroCosto()));
                                            break; 
                                        case 20:
                                            if(listado.get(i-1).getVigenciaCargo()!=null){
                                            celda.setCellValue(this.formateo(listado.get(i-1).getVigenciaCargo()));
                                            }
                                            else{
                                            celda.setCellValue("");
                                            }
                                            break; 
                                        case 21:
                                            celda.setCellValue(listado.get(i-1).getBaseHoraria());
                                            break; 
                                        case 22:
                                            celda.setCellValue(listado.get(i-1).getBaseHoras());
                                            break; 
                                        case 23:
                                            celda.setCellValue(this.lugar(listado.get(i-1).getLugarTrabajo()));
                                            break; 
                                        case 24:
                                            celda.setCellValue(this.afap(listado.get(i-1).getAfap()));
                                            break; 
                                        case 25:
                                            celda.setCellValue(listado.get(i-1).getCuenta());
                                            break; 
                                        case 26:
                                            celda.setCellValue(this.banco(listado.get(i-1).getBanco()));
                                            break; 
                                        case 27:
                                            celda.setCellValue(this.cuentas(listado.get(i-1).getTipoCuenta()));
                                            break; 
                                        case 28:
                                            celda.setCellValue(listado.get(i-1).getNumSocio());
                                            break; 
                                        case 29:
                                            celda.setCellValue(this.horarios(listado.get(i-1).getHorarios()));
                                            break; 
                                        case 30:
                                            celda.setCellValue(this.sns(listado.get(i-1).getSns()));
                                            break; 
                                        case 31:
                                            if(listado.get(i-1).getVencimientoCarne()!=null){
                                            celda.setCellValue(this.formateo(listado.get(i-1).getVencimientoCarne()));
                                            }
                                            else{
                                            celda.setCellValue("");
                                            }
                                            break; 
                                        
                                    }
                                }

                    }
              
              }
              libro.write(fileOut);
            
            
        
          
      
                
  //Cerramos nuestro archivo
                    fileOut.close();
                  
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileXLS.getAbsolutePath());
                  

                 
        }
    }
    
     private void LimpiarTablaFuncionarios() {
        
        DefaultTableModel modelo=(DefaultTableModel) tablaFunc.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaFunc.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }
     
      private String formateo(Date hoy){
      String ret="";
      if(hoy!=null){  
      SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
      ret=formateador.format(hoy);
        }
      return ret;
     }  
    
 private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
}
 
  private String sns(Sns s){
        String ret="";
        if(s!=null){
            ret=s.toString();
        }
        return ret;
    }
    private String centro(CentroCosto c){
        String ret="";
        if(c!=null){
            ret=c.toString();
        }
        return ret;
    }
    private String lugar(Sucursal suc){
        String ret="";
        if(suc!=null){
            ret=suc.getNombre();
        }
        return ret;
    }
    
    private String afap(Integer i){
        String af="NO";
        if(i==1){
            af="SI";
        }
        return af;
    }
    private String banco(Banco b){
        String ret="";
        if(b!=null){
            ret=b.getSucursal().toString()+"-"+b.getNombre();
        }
        return ret;
    }
    private String cuentas(String cuenta){
        String ret="";
        if(cuenta!=null){
            if(cuenta.equals("CC")){
                ret="Cuenta Corriente";
            }
            else if(cuenta.equals("CA")){
                ret="Caja de Ahorro";
            }
        }
        return ret;
    }
    
     private String horarios(ArrayList<Horario> hor){
        String ret="";
        if(hor.size()>0){
            for(Horario h:hor){
            ret+=h.toString()+" -- ";
            }
        }
       
        return ret;
    }
     
     //MANEJO TAMAÑO COLUMNAS
    public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 150; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +1 , width);
        }
        if(width > 300)
            width=300;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnListar;
    private org.edisoncor.gui.button.ButtonIcon buttonIcon2;
    private javax.swing.JRadioButton checkActivos;
    private javax.swing.JRadioButton checkInactivos;
    private javax.swing.JRadioButton checkTodos;
    private javax.swing.JComboBox comboSucursal;
    private javax.swing.ButtonGroup grupoTabla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JTable tablaFunc;
    private org.edisoncor.gui.textField.TextFieldRound txtFiltro;
    // End of variables declaration//GEN-END:variables

    private Funcionario cargarFuncionarioMultiple(int m) throws ClassNotFoundException, SQLException {
        return this.LogF.buscarFuncionario(String.valueOf(tm.getValueAt(m,0)));
    }
}
