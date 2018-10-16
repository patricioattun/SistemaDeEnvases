
package Presentacion.Vales;

import Dominio.Funcionario;
import Dominio.TipoVale;
import Dominio.Vale;
import Logica.ArmoTxtxBanco;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Persistencia.Conexion;
import Presentacion.RenderMarca;
import Presentacion.RenderVales;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;


import org.apache.poi.ss.usermodel.Row;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;


public class InternalVales extends javax.swing.JInternalFrame {
    private Funcionario f;
    private LogFuncionario log;
    private LogCodigo logCod;
    private static InternalVales instancia=null;
    Connection cnn; 
    private ArrayList<Vale> vales;
    
    ArrayList<Object> sueldo;
    ArrayList<Object> vacacional;
    ArrayList<Object> quebranto;
    ArrayList<Object> aguinaldo;
    
    ArrayList<Object> sueldoSin;
    ArrayList<Object> vacacionalSin;
    ArrayList<Object> quebrantoSin;
    ArrayList<Object> aguinaldoSin;
    
    private Double importes;
    private Integer cantidad;
    private boolean base;
    public InternalVales() throws ClassNotFoundException, SQLException {
        initComponents();
        this.txtFechaInfo.requestFocus();
        log=new LogFuncionario();
        logCod=new LogCodigo();
        this.txtFechaLiq.setText(this.log.fechaLiquidacion());
        this.txtFechaLiq.setEditable(false);
       
        cnn=Conexion.Cadena();
        this.inicializa(false);
        this.cargaCombo();
        this.txtNumCuenta.setEditable(false);
        this.btnTxt.setEnabled(false);    
        
     //  this.txtFechaLiq.setEnabled(true);
        
        
    }
    
    public static InternalVales instancia() throws ClassNotFoundException, SQLException
    {    
         if (instancia== null)
         {
            instancia = new InternalVales();
         }
         return instancia;
     
    }
    
    private void inicializa(boolean estado){
        this.btnBuscar.setEnabled(estado);
        this.btnCargar.setEnabled(estado);
        this.txtNumFunc.setEditable(estado);
        this.txtImporte.setEditable(estado);
        this.btnConfirmar.setEnabled(estado);
    }

    public ArrayList<Vale> getVales() {
        return vales;
    }

    public void setVales(ArrayList<Vale> vales) {
        this.vales = vales;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        txtFechaInfo = new javax.swing.JFormattedTextField();
        txtFechaLiq = new org.edisoncor.gui.textField.TextFieldRound();
        lblFechaLiq = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        txtNumFunc = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new org.edisoncor.gui.button.ButtonIcon();
        btnCargar = new org.edisoncor.gui.button.ButtonIcon();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        txtNumCuenta = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel7 = new javax.swing.JLabel();
        txtImporte = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel8 = new javax.swing.JLabel();
        comboTipo = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        btnLimpiar = new org.edisoncor.gui.button.ButtonRound();
        btnConfirmar = new org.edisoncor.gui.button.ButtonRound();
        lblMsj = new javax.swing.JLabel();
        txtCant = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel10 = new javax.swing.JLabel();
        txtImpo = new org.edisoncor.gui.textField.TextFieldRound();
        btnTxt = new org.edisoncor.gui.button.ButtonRound();

        jMenuItem1.setText("Editar Fecha");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Editar Importe");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem2);

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(750, 650));
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

        txtFechaInfo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        txtFechaInfo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFechaInfo.setComponentPopupMenu(jPopupMenu1);
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

        lblFechaLiq.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblFechaLiq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaLiq.setText("Fecha de Liquidación");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Fecha de Informado");

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(102, 102, 102));
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNombre.setSelectionColor(new java.awt.Color(255, 255, 255));

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

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nro. Funcionario ");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Nombre de Funcionario");

        btnBuscar.setBackground(new java.awt.Color(102, 153, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lupa.png"))); // NOI18N
        btnBuscar.setText("buttonIcon1");
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnCargar.setBackground(new java.awt.Color(204, 255, 204));
        btnCargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/import.png"))); // NOI18N
        btnCargar.setToolTipText("Cargar Excel");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });
        btnCargar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnCargarKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Cargar Excel");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Func", "Cuenta", "Importe", "Nombre", "Fecha Liq.", "Tipo", "Pers"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setComponentPopupMenu(jPopupMenu2);
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(70);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(0).setMaxWidth(70);
            tabla.getColumnModel().getColumn(1).setMinWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setMaxWidth(100);
            tabla.getColumnModel().getColumn(2).setMinWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setMaxWidth(100);
            tabla.getColumnModel().getColumn(3).setMinWidth(250);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(250);
            tabla.getColumnModel().getColumn(3).setMaxWidth(250);
            tabla.getColumnModel().getColumn(4).setMinWidth(100);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(4).setMaxWidth(100);
            tabla.getColumnModel().getColumn(5).setMinWidth(150);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(5).setMaxWidth(150);
            tabla.getColumnModel().getColumn(6).setMinWidth(0);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(0);
            tabla.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        txtNumCuenta.setBackground(new java.awt.Color(102, 153, 255));
        txtNumCuenta.setForeground(new java.awt.Color(255, 255, 255));
        txtNumCuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumCuenta.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNumCuenta.setSelectionColor(new java.awt.Color(102, 102, 102));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Nro. Cuenta");

        txtImporte.setBackground(new java.awt.Color(102, 153, 255));
        txtImporte.setForeground(new java.awt.Color(255, 255, 255));
        txtImporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImporte.setCaretColor(new java.awt.Color(255, 255, 255));
        txtImporte.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtImporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImporteKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Importe");

        comboTipo.setToolTipText("null");
        comboTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTipoItemStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("Tipo");

        btnLimpiar.setBackground(new java.awt.Color(204, 204, 204));
        btnLimpiar.setForeground(new java.awt.Color(0, 0, 0));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setColorDeSombra(null);
        btnLimpiar.setPreferredSize(new java.awt.Dimension(99, 33));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnConfirmar.setBackground(new java.awt.Color(204, 204, 204));
        btnConfirmar.setForeground(new java.awt.Color(0, 0, 0));
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setColorDeSombra(null);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        lblMsj.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

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

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("Totales:");

        txtImpo.setEditable(false);
        txtImpo.setBackground(new java.awt.Color(102, 102, 102));
        txtImpo.setForeground(new java.awt.Color(255, 255, 255));
        txtImpo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImpo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtImpo.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtImpo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImpoKeyTyped(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtFechaInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNumFunc, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNumCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(txtImporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtImpo, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFechaLiq)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFechaLiq, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                                    .addComponent(txtFechaInfo))
                                .addGap(31, 31, 31))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                                    .addComponent(txtNumCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtImporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtImpo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFuncKeyTyped
         int k = (int) evt.getKeyChar();
         this.lblMsj.setText("");
         this.txtNombre.setText("");
         this.txtNumCuenta.setText("");
         if(this.txtNumFunc.getText().length()>3){
             evt.consume();
        }
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(k==10){
            this.btnBuscar.doClick();
            
        }
    }//GEN-LAST:event_txtNumFuncKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String numFunc=this.txtNumFunc.getText();
        this.lblMsj.setText("");
        if(cnn==null){
            try {
                cnn=Conexion.Cadena();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            if(cnn.isClosed()){ 
                cnn=Conexion.Cadena();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.esNum(numFunc)){
            try {
                f = this.log.funcVale(Integer.valueOf(numFunc),cnn);
                if(f!=null){
                    this.txtNombre.setText(f.getNomCompletoApe());
                    this.txtImporte.requestFocus();
                    String cuenta=f.getCuenta().toString();
                    cuenta=cuenta.substring(0, cuenta.length()-2);
                    this.txtNumCuenta.setText(cuenta);
                    
                    
                    
                }
                else{
                    this.txtNumFunc.requestFocus();
                    this.txtNombre.setText("El funcionario no existe");
                    this.txtNumFunc.selectAll();
                    //this.inicializo(false);
                    
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
            }
                          
                }
                
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtFechaInfoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaInfoFocusLost
        TipoVale tipo=null;
        if(this.comboTipo.getSelectedIndex()>0){
         tipo = (TipoVale) this.comboTipo.getSelectedItem();
         }
       if(tipo!=null){
        this.cargaDetabla(tipo);
       }
       else{
           this.lblMsj.setText("Seleccione un tipo de vale");
       }
    }//GEN-LAST:event_txtFechaInfoFocusLost

    private void cargaDetabla(TipoVale tipo){
         if(!"".equals(txtFechaInfo.getText())){
             Date fec=this.validaFecha(txtFechaInfo.getText());
           if(fec!=null){
                Date hoy=new Date();
                if(fec.after(hoy)||this.fijaHora(fec).equals(this.fijaHora(hoy))){
                this.txtFechaInfo.setEditable(false);
                this.inicializa(true);
                this.buscaVales(tipo);
                this.btnCargar.requestFocus();
                if(this.btnCargar.isEnabled()==false){
                    this.txtNumFunc.requestFocus();
                }
                }
                else{
                    this.lblMsj.setText("La fecha debe ser igual o posterior a la de hoy");
                }
           }
       }
       else{
            this.txtFechaInfo.setEditable(true); 
            this.inicializa(false);
       }
    }
    
    private Timestamp fijaHora(Date date){
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(date.getTime());
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    Timestamp ts = new java.sql.Timestamp(date.getTime());
    ts.setTime(c.getTimeInMillis());
    return ts;
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.LimpiarTabla();
        this.vales=null;
        this.txtFechaInfo.setEditable(true); 
        this.inicializa(false);
        this.txtFechaInfo.selectAll();
        this.txtFechaInfo.requestFocus();
        this.btnTxt.setEnabled(false);      
        this.lblMsj.setText("");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
       this.LimpiarTabla();
       this.inicializa(false);
       this.cargaExcel();
       this.inicializa(true);
       this.lblMsj.setText("");
    }//GEN-LAST:event_btnCargarActionPerformed

    private void txtImporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImporteKeyTyped
        this.lblMsj.setText("");
        Vale v = null;
        String importe = this.txtImporte.getText();
        if(vales==null){
             vales=new ArrayList<>();
        }
        TipoVale tipo=null;
       
        if(this.comboTipo.getSelectedIndex()>0){
         tipo = (TipoVale) this.comboTipo.getSelectedItem();
        }
        int k = (int) evt.getKeyChar();
        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(k==10){
                if(f!=null){
                    if(this.esDouble(importe)){
                        if(tipo!=null){
                        v=this.estaEnVales(f,tipo);
                        if(v==null){
                                Vale val = new Vale();
                                val.setCodFunc(f.getCodFunc());
                                val.setCuenta(f.getCuenta());
                                val.setImporteVale(Double.valueOf(importe));
                                val.setNombre(f.getNomCompletoApe());
                                val.setTipo(tipo);
                                this.cargaTabla(val);
                                this.vales.add(val);
                                this.txtImporte.setText("");
                                this.txtNombre.setText("");
                                this.txtNumCuenta.setText("");
                                this.txtNumFunc.setText("");
                                this.txtNumFunc.requestFocus();
                        }
                        else{
                           int respuesta=JOptionPane.showConfirmDialog(this, "Ya existe un vale para este funcionario, desea modificarlo?");
                            //si es 0,no es 1 cancela es 2
                            if(respuesta==0){
                                modalEditarVale modal=new modalEditarVale(null, closable,v);
                                modal.setVisible(true);
                                this.txtImporte.setText("");
                                this.txtNombre.setText("");
                                this.txtNumCuenta.setText("");
                                this.txtNumFunc.setText("");
                                this.txtNumFunc.requestFocus();
                            }
                        }
                       }
                    }
                }
          }
    }//GEN-LAST:event_txtImporteKeyTyped

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtFechaInfoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaInfoKeyTyped
      int k = (int) evt.getKeyChar();
      this.lblMsj.setText(""); 
      TipoVale tipo=null;
       
       if(this.comboTipo.getSelectedIndex()>0){
         tipo = (TipoVale) this.comboTipo.getSelectedItem();
         }
      if(k==10){
          if(tipo!=null){
                this.cargaDetabla(tipo);
       }
          else{
           this.lblMsj.setText("Seleccione un tipo de vale");
       }
      }
      
      
       
       
    }//GEN-LAST:event_txtFechaInfoKeyTyped

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int i = tabla.getSelectedRow();
        Vale v = vales.get(i);
        if(v.getFechaLiq()==null){
            modalEditarVale modal=new modalEditarVale(null, closable,v);
            modal.setVisible(true);
            tabla.changeSelection(i, 0, false, false);
        }
        else{
            JOptionPane.showMessageDialog(this, "Este vale ya fue liquidado y no se puede editar");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
      this.LimpiarTabla();
      this.vales=null;
      this.lblMsj.setText("");
      this.txtFechaInfo.setText("");
      this.txtFechaInfo.setEditable(true);
      this.inicializa(false);
      this.btnTxt.setEnabled(false);
      this.comboTipo.setSelectedIndex(0);
      this.txtImpo.setText("");
      this.txtCant.setText("");
      this.txtImporte.setText("");
      this.txtCant.setText("");
      this.importes=0.0;
      this.cantidad=0;
      this.txtNombre.setText("");
      this.txtNumFunc.setText("");
      
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
       TipoVale tipo=null;
       String fecha = this.txtFechaInfo.getText();
       if(this.comboTipo.getSelectedIndex()>0){
         tipo = (TipoVale) this.comboTipo.getSelectedItem();
         }
        if(vales.size()>0){
//            if(tipo!=null){
                if(!fecha.equals("")){
                   // if(this.logCod.chequeaTiposDistintos(tipo,fecha)){
                        ArrayList<Integer> in = this.logCod.ingresarVales(vales,tipo,fecha);
                        JOptionPane.showInternalMessageDialog(this, "Se han insertado "+in.get(0)+" vales nuevos, se han modificado "+in.get(1)+" vales y no se han modificado "+in.get(2)+" vales porque ya fueron liquidados");
                        this.lblMsj.setText("");
                        this.LimpiarTabla();
                        this.vales=null;
                        this.inicializa(false);
                        //this.comboTipo.setSelectedIndex(0);
                        this.btnTxt.setEnabled(false);
                        this.cargaDetabla(tipo);
//                    }
//                    else{
//                        this.lblMsj.setText("No se puede ingresar distintos tipos de vales para misma fecha");
//                    }
                }
//            }
//            else{
//                this.lblMsj.setText("Seleccione un tipo de vale");
//            }
            
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void comboTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTipoItemStateChanged
      this.lblMsj.setText("");
      this.LimpiarTabla();
      this.txtImporte.setText("");
      this.txtCant.setText("");
      this.vales=null;
      this.btnTxt.setEnabled(false);
      this.txtImpo.setText("");
      this.txtCant.setText("");
      this.importes=0.0;
      this.cantidad=0;
      this.txtNombre.setText("");
      this.txtNumFunc.setText("");
      this.txtFechaInfo.requestFocus();
      this.txtNumFunc.requestFocus();
    }//GEN-LAST:event_comboTipoItemStateChanged

    private void txtCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantKeyTyped

    private void txtImpoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImpoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImpoKeyTyped

    private void btnTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTxtActionPerformed
       ArmoTxtxBanco txt = new ArmoTxtxBanco();
       String fecha = txtFechaInfo.getText();
       TipoVale tipo=null;
      
       if(this.comboTipo.getSelectedIndex()>0){
         tipo = (TipoVale) this.comboTipo.getSelectedItem();
       }
       if(base==true){
           if(!fecha.equals("")){
               if(tipo!=null){
                ArrayList<Object> valesBase=this.logCod.cargoValesFechaObject(fecha,tipo);
                ArrayList<Object> valesBaseSinCuenta=this.logCod.cargoValesFechaObjectSinCuenta(fecha);
                this.reorganizaValesMismaFecha(valesBase);
                this.reorganizaValesMismaFechaSinCuenta(valesBaseSinCuenta);
                if(sueldo.size()>0){
                    String str = txt.armoTxtValesBanco(this.stringADate(this.txtFechaLiq.getText()),this.stringADate(fecha),sueldo,2,sueldoSin);
                    this.lblMsj.setText(str);
                    if(sueldoSin.size()>0){
                        JOptionPane.showMessageDialog(this, "No hay funcionarios SIN CUENTA para esta liquidacion y no se ha generado la planilla.");
                    }
                }
                if(vacacional.size()>0){
                    String str = txt.armoTxtValesBanco(this.stringADate(this.txtFechaLiq.getText()),this.stringADate(fecha),vacacional,2,vacacionalSin);
                    this.lblMsj.setText(str);
                    if(vacacionalSin.size()>0){
                        JOptionPane.showMessageDialog(this, "No hay funcionarios SIN CUENTA para esta liquidacion y no se ha generado la planilla.");
                    }
                }
                if(quebranto.size()>0){
                    String str = txt.armoTxtValesBanco(this.stringADate(this.txtFechaLiq.getText()),this.stringADate(fecha),quebranto,2,quebrantoSin);
                    this.lblMsj.setText(str);
                    if(quebrantoSin.size()>0){
                        JOptionPane.showMessageDialog(this, "No hay funcionarios SIN CUENTA para esta liquidacion y no se ha generado la planilla.");
                    }
                }
                if(aguinaldo.size()>0){
                    String str = txt.armoTxtValesBanco(this.stringADate(this.txtFechaLiq.getText()),this.stringADate(fecha),aguinaldo,2,aguinaldoSin);
                    this.lblMsj.setText(str);
                    if(aguinaldoSin.size()>0){
                        JOptionPane.showMessageDialog(this, "No hay funcionarios SIN CUENTA para esta liquidacion y no se ha generado la planilla.");
                    }
                }
//                else{
//                    this.lblMsj.setText("No hay registros en la base de datos para esta fecha");
//                }
               }
               else{
                    this.lblMsj.setText("Seleccione un tipo de vale");
               }
           }
           else{
                this.lblMsj.setText("Seleccione una fecha de informado");
           }
       }
       else{
           this.lblMsj.setText("No hay registros en la base de datos para esta fecha");
       }
    }//GEN-LAST:event_btnTxtActionPerformed

    private void txtNumFuncFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFuncFocusGained
        this.txtNumFunc.selectAll();
    }//GEN-LAST:event_txtNumFuncFocusGained

    private void btnCargarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCargarKeyTyped
        this.btnCargar.doClick();
    }//GEN-LAST:event_btnCargarKeyTyped

    private void txtFechaLiqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaLiqFocusGained
        this.txtFechaInfo.requestFocus();
    }//GEN-LAST:event_txtFechaLiqFocusGained

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
    public void LimpiarTabla() {
    DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        } 
       this.cantidad=0;
       this.importes=0.0;
      }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnBuscar;
    private org.edisoncor.gui.button.ButtonIcon btnCargar;
    private org.edisoncor.gui.button.ButtonRound btnConfirmar;
    private org.edisoncor.gui.button.ButtonRound btnLimpiar;
    private org.edisoncor.gui.button.ButtonRound btnTxt;
    private javax.swing.JComboBox comboTipo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFechaLiq;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtCant;
    private javax.swing.JFormattedTextField txtFechaInfo;
    private org.edisoncor.gui.textField.TextFieldRound txtFechaLiq;
    private org.edisoncor.gui.textField.TextFieldRound txtImpo;
    private org.edisoncor.gui.textField.TextFieldRound txtImporte;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtNumCuenta;
    private org.edisoncor.gui.textField.TextFieldRound txtNumFunc;
    // End of variables declaration//GEN-END:variables


public static Date validaFecha(String fecha) {
    Date fec=null;
    try{
    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    formatoFecha.setLenient(false);
    fec=formatoFecha.parse(fecha);
    
    } catch (ParseException e) {
         return fec;
    }
    return fec;
    }


      private void cargaExcel(){
        try {
             TipoVale tipo=null;
            String fecha = this.txtFechaInfo.getText();
            if(this.comboTipo.getSelectedIndex()>0){
              tipo = (TipoVale) this.comboTipo.getSelectedItem();
              }
            if(tipo!=null){
            File files = null;
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "xls", "text");
            final JFileChooser fc = new JFileChooser("C:/SUELDOSLIST");
            fc.setFileFilter(filter);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int seleccion = fc.showSaveDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                cnn=Conexion.Cadena();
                FileInputStream file = null;
                try {
                    files = fc.getSelectedFile();
                    file = new FileInputStream(files);
                    HSSFWorkbook workbook = new HSSFWorkbook(file);
                    HSSFSheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> rowIterator = sheet.iterator();
                    Row row;
                    Double dato;
                    Integer codFunc = null;
                    Double vale = null;
                    if(vales==null){
                    vales=new ArrayList<>();
                    }
                    while (rowIterator.hasNext()){
                        row = rowIterator.next();
                        if(row.getRowNum()>=3){
                            Iterator<Cell> cellIterator = row.cellIterator();
                            Cell celda;
                            
                            while (cellIterator.hasNext()){
                                celda = cellIterator.next();
                                int i = celda.getColumnIndex();
                                if(celda.getColumnIndex()==1){
                                    dato=celda.getNumericCellValue();
                                    codFunc=dato.intValue();
                                }
                                if(celda.getColumnIndex()==7){
                                    vale=celda.getNumericCellValue();
                                }
                                
                            }
                            if(vale!=null){
                                if(vale>0){
                                    if(codFunc!=null){
                                        if(codFunc>0){
                                            this.armaVale(codFunc,vale,cnn,tipo,0);
                                        }
                                    }
                                }
                                
                            }
                        }
                        
                    }
                    workbook.close();
                
                
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalStateException ex) {
                   JOptionPane.showInternalMessageDialog(this, "El archivo seleccionado no está en el formato esperado. Comuníquese con Desarrollo");
                } 
                finally {
                    try {
                        file.close();
                    } catch (IOException ex) {
                        Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if(vales!=null){
                vales.forEach((v) -> {
                    this.cargaTabla(v);
                });
            }
            cnn.close();
            }
            else{
                 this.lblMsj.setText("Seleccione un tipo de vale");
                }
        } catch (SQLException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        }
      }

    private void armaVale(Integer codFunc, Double vale,Connection cnn,TipoVale tipo,int enPers) {
        try {
            f=this.log.funcVale(codFunc, cnn);
            if(f!=null){
                Vale val = new Vale();
                val.setCodFunc(codFunc);
                val.setNombre(f.getNomCompletoApe());
                val.setCuenta(f.getCuenta());
                val.setImporteVale(vale);
                val.setTipo(tipo);
                val.setEnPers(0);
                this.vales.add(val);
            }  } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }

    public void cargaTabla(Vale vale) {
        cantidad++;
      // this.Alinear_Grillas();
          DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
          Object[] filas=new Object[modelo.getColumnCount()];
         
                            filas[0]=vale.getCodFunc();
                            DecimalFormat df = new DecimalFormat("##0");
                            filas[1]=df.format(vale.getCuenta());               
                            filas[2]=this.decimales(vale.getImporteVale());
                            filas[3]=vale.getNombre();
                            filas[4]=this.convertirFecha(vale.getFechaLiq());
                            filas[5]=vale.getTipo();
                            filas[6]=vale.getEnPers();
                            modelo.addRow(filas);
                            importes+=vale.getImporteVale();
                            RenderVales rr=new RenderVales(6);
                            tabla.setDefaultRenderer(Object.class, rr);
        
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.changeSelection(modelo.getRowCount()-1, 0, false, false);
        this.txtCant.setText(cantidad.toString());
        this.txtImpo.setText(this.decimales(importes));
     }
        
   private String convertirFecha(Date fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
    }
        
    return str;
    }
    
    
   private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
    }
     
    private void Alinear_Grillas(){
        DefaultTableCellRenderer modelo = new DefaultTableCellRenderer();
        modelo.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabla.getColumnModel().getColumn(0).setCellRenderer(modelo); 
        this.tabla.getColumnModel().getColumn(1).setCellRenderer(modelo); 
        this.tabla.getColumnModel().getColumn(2).setCellRenderer(modelo);    

    }

    private Vale estaEnVales(Funcionario f,TipoVale tipo) {
       int i = 0;
       boolean esta = false;
       Vale val=null;
       while(i<vales.size() && !esta){
           if(vales.get(i).getCodFunc().equals(f.getCodFunc())){
               if(vales.get(i).getTipo().getCodigo().equals(tipo.getCodigo())){
               esta = true;
               val=vales.get(i);
               }
           }
           i++;
       }
       return val;
    }

    void actualizaVales(String importe, Vale val) {
       int i = 0;
       boolean esta = false;
       while(i<vales.size() && !esta){
           if(vales.get(i).getCodFunc().equals(val.getCodFunc())){
               esta = true;
               vales.get(i).setImporteVale(Double.valueOf(importe));
           }
           i++;
       }
       this.LimpiarTabla();
       if(vales!=null){
           vales.forEach((v) -> {
               this.cargaTabla(v);
           });
            
       tabla.changeSelection(i-1, 0, false, false);
       }
                
      }

    private void cargaCombo() {
       ArrayList<TipoVale>t=this.logCod.cargoTipoVales();
       
       this.comboTipo.addItem("SELECCIONE TIPO");
      for(int i=0; i<t.size();i++){
          this.comboTipo.addItem(t.get(i));
          this.comboTipo.setSelectedIndex(0);
          
      }
    }

    private void buscaVales(TipoVale tipo) {
        this.LimpiarTabla();
        String fecha = txtFechaInfo.getText();
        this.vales=this.logCod.cargoValesFecha(fecha,tipo);
        if(vales!=null){
            if(vales.size()>0){
                vales.forEach((v) -> {
                    this.cargaTabla(v);
                    base=true;
                    this.btnTxt.setEnabled(true);
                });
                this.btnCargar.setEnabled(false);
            }else{
                this.btnCargar.setEnabled(true);
                base=false;
                this.btnTxt.setEnabled(false);           
            }
          
        }else{
            this.btnCargar.setEnabled(true);
            base=false;
            this.btnTxt.setEnabled(false);           
        }
    }

    private void reorganizaValesMismaFecha(ArrayList<Object> valesBase) {
        this.sueldo=new ArrayList<>();
        this.vacacional=new ArrayList<>();
        this.quebranto=new ArrayList<>();
        this.aguinaldo=new ArrayList<>();
        for(Object o:valesBase){
           Vale v=((Vale)o);
           if(null!=v.getTipo().getCodigo()) //sueldo
             switch (v.getTipo().getCodigo()) {
                 case 1:
                     this.sueldo.add(o);
                     break;
                 case 2:
                     this.vacacional.add(o);
                     break;
                 case 3:
                     this.quebranto.add(o);
                     break;
                 case 4:
                     this.aguinaldo.add(o);
                     break;
                 default:
                     break;
             }
        }
    }
    
      private void reorganizaValesMismaFechaSinCuenta(ArrayList<Object> valesBase) {
        this.sueldoSin=new ArrayList<>();
        this.vacacionalSin=new ArrayList<>();
        this.quebrantoSin=new ArrayList<>();
        this.aguinaldoSin=new ArrayList<>();
        for(Object o:valesBase){
           Vale v=((Vale)o);
           if(null!=v.getTipo().getCodigo()) //sueldo
             switch (v.getTipo().getCodigo()) {
                 case 1:
                     this.sueldoSin.add(o);
                     break;
                 case 2:
                     this.vacacionalSin.add(o);
                     break;
                 case 3:
                     this.quebrantoSin.add(o);
                     break;
                 case 4:
                     this.aguinaldoSin.add(o);
                     break;
                 default:
                     break;
             }
        }
    }


    
}
