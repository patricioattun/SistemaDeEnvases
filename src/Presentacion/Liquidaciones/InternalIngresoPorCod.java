
package Presentacion.Liquidaciones;

import Dominio.Codigo;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Dominio.TipoVale;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import Persistencia.Conexion;
import Presentacion.Liquidaciones.InternalIngresoPorFunc;
import Presentacion.Liquidaciones.InternalListadoCodLiq;
import Presentacion.Liquidaciones.InternalListadoFuncFiltro;
import Presentacion.Liquidaciones.InternalModificarIngreso;
import Presentacion.Marcas.InternalMarcaCodigo;
import Presentacion.RenderCodigos;
import Presentacion.Vales.InternalVales;
import Presentacion.frmPrin;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.edisoncor.gui.button.ButtonIcon;
import org.edisoncor.gui.textField.TextFieldRound;

public class InternalIngresoPorCod extends javax.swing.JInternalFrame {
    private InternalListadoCodLiq listadoCod;
    private LogCodigo logs;
    Codigo cod;
    private InternalModificarIngreso internalIng;
    private InternalListadoFuncFiltro listadoFunc;
    private Funcionario f;
    private LogFuncionario log;
    private static InternalIngresoPorCod instancia=null;
    private ArrayList<Ingreso> codigos=new ArrayList<>();
    DefaultTableModel tmMov=null;  
    private Ingreso ingres;
    private Codigo codIng;
    private Double cantidad;
    private Double Importe;
    private Double sumaCant=0.0;
    private Double sumaImp=0.0;
    Connection cnn; 
    
    public InternalIngresoPorCod() throws ClassNotFoundException, SQLException {
        initComponents();
        this.txtNombre.setEditable(false);
        this.txtDescripcion.setEditable(false);
        log=new LogFuncionario();
        logs=new LogCodigo();
        this.txtFechaLiq.setText(this.log.fechaLiquidacion());
        this.txtFechaLiq.setEditable(false);
        this.btnCargar.setVisible(false);
        this.lblSacec.setVisible(false); 
       
        this.inicializo(false);
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

    public ArrayList<Ingreso> getCodigos() {
        return codigos;
    }

    public void setCodigos(ArrayList<Ingreso> codigos) {
        this.codigos = codigos;
    }
    
    
    
    private void cargarMovimiento(MouseEvent e) throws ParseException {
     Integer m=this.tabla.rowAtPoint(e.getPoint());
     this.ingres=new Ingreso();
     ingres.setCodFunc(Integer.valueOf(String.valueOf(tmMov.getValueAt(m, 0))));
     String cantidad=String.valueOf(tmMov.getValueAt(m,2));
     String importe=String.valueOf(tmMov.getValueAt(m,3)).replace(",", "");
     ingres.setCantidad(Double.valueOf(cantidad));
     
     ingres.setImporte(Double.valueOf(importe));
     ingres.setEnPers(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,4))));
     ingres.setCodMov(cod);
  //   REVISAR EL CONTRAFORMATEO DE LOS IMPORTES
    }
    
    
     public static InternalIngresoPorCod instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalIngresoPorCod();
         }
         return instancia;
      
   }

    public ButtonIcon getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(ButtonIcon btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

     
    public ButtonIcon getBtnBuscaCod() {
        return btnBuscaCod;
    }

    public void setBtnBuscaCod(ButtonIcon btnBuscaCod) {
        this.btnBuscaCod = btnBuscaCod;
    }

    public TextFieldRound getTxtCod() {
        return txtCod;
    }

    public void setTxtCod(TextFieldRound txtCod) {
        this.txtCod = txtCod;
    }

    public TextFieldRound getTxtNumFunc() {
        return txtNumFunc;
    }

    public void setTxtNumFunc(TextFieldRound txtNumFunc) {
        this.txtNumFunc = txtNumFunc;
    }

    public TextFieldRound getTxtFechaLiq() {
        return txtFechaLiq;
    }

    public void setTxtFechaLiq(TextFieldRound txtFechaLiq) {
        this.txtFechaLiq = txtFechaLiq;
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel5 = new javax.swing.JLabel();
        btnBuscaCod = new org.edisoncor.gui.button.ButtonIcon();
        jLabel6 = new javax.swing.JLabel();
        txtDescripcion = new org.edisoncor.gui.textField.TextFieldRound();
        txtFechaLiq = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNumFunc = new org.edisoncor.gui.textField.TextFieldRound();
        btnBuscar = new org.edisoncor.gui.button.ButtonIcon();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        txtValor = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnConfirmar = new org.edisoncor.gui.button.ButtonRound();
        btnLimpiar = new org.edisoncor.gui.button.ButtonRound();
        txtImpo = new org.edisoncor.gui.textField.TextFieldRound();
        txtCant = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnCargar = new org.edisoncor.gui.button.ButtonIcon();
        lblSacec = new javax.swing.JLabel();
        lblMsj = new javax.swing.JLabel();

        jMenuItem1.setText("Modificar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Eliminar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        setClosable(true);
        setIconifiable(true);
        setTitle("Ingreso de Movimientos por Código");
        setToolTipText("");
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

        txtCod.setBackground(new java.awt.Color(102, 153, 255));
        txtCod.setForeground(new java.awt.Color(255, 255, 255));
        txtCod.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCod.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCod.setSelectionColor(new java.awt.Color(102, 102, 102));
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

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Código");

        btnBuscaCod.setBackground(new java.awt.Color(102, 153, 255));
        btnBuscaCod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lupa.png"))); // NOI18N
        btnBuscaCod.setText("buttonIcon1");
        btnBuscaCod.setToolTipText("");
        btnBuscaCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaCodActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Descripción");

        txtDescripcion.setBackground(new java.awt.Color(102, 102, 102));
        txtDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescripcion.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDescripcion.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Movimientos"));

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
        btnBuscar.setToolTipText("");
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

        txtValor.setBackground(new java.awt.Color(102, 153, 255));
        txtValor.setForeground(new java.awt.Color(255, 255, 255));
        txtValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValor.setCaretColor(new java.awt.Color(255, 255, 255));
        txtValor.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorFocusGained(evt);
            }
        });
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Nro. Funcionario ");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Nombre de Funcionario");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Func", "Nombre", "Cantidad", "Importe", "EnPers", "Fecha Vale"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(4).setMinWidth(0);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabla.getColumnModel().getColumn(4).setMaxWidth(0);
            tabla.getColumnModel().getColumn(5).setMinWidth(0);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(0);
            tabla.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.setPreferredSize(new java.awt.Dimension(99, 33));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

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

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Totales:");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Valor");

        btnCargar.setBackground(new java.awt.Color(204, 255, 204));
        btnCargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/import.png"))); // NOI18N
        btnCargar.setToolTipText("Cargar Excel SACEC");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });

        lblSacec.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSacec.setText("Cargar Excel SACEC");

        lblMsj.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(127, 127, 127)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtValor, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(btnCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(txtImpo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSacec, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImpo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSacec)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscaCod, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnBuscaCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
        TableColumnModel tcm = tabla.getColumnModel();
        tcm.getColumn(5).setPreferredWidth(0);   
        tcm.getColumn(5).setMaxWidth(0);
        tcm.getColumn(5).setMinWidth(0);
        tcm.getColumn(5).setWidth(0);
        this.txtNombre.setText("");
        this.lblMsj.setText("");
        this.txtDescripcion.setText("");
        this.txtCant.setText("");
        this.txtImpo.setText("");
        this.sumaCant=0.0;
        this.sumaImp=0.0;
        char c=evt.getKeyChar();
        this.txtValor.setText("");
        this.txtNumFunc.setText("");
        this.LimpiarTabla();
        this.codigos=null;
        this.cod=null;
        this.btnCargar.setVisible(false);
        this.lblSacec.setVisible(false); 
        this.btnConfirmar.setEnabled(false);
        if(!Character.isDigit(c)){
            evt.consume();
        }
        if(this.txtCod.getText().length()>3){
             evt.consume();
        }
        int k = (int) evt.getKeyChar();
        if(k==10){
            this.btnBuscaCod.doClick();
        }
        if(evt.getKeyChar()==43){
            this.txtCod.setText("");
            try {
                listadoCod=InternalListadoCodLiq.instancia(logs,this,1);
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

            }  catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
            } catch (ClassNotFoundException ex) {
                 JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
            }
        }
        if(evt.getKeyChar()==27){
            this.dispose();
        }
    }//GEN-LAST:event_txtCodKeyTyped

    private void btnBuscaCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaCodActionPerformed
        String str=this.txtCod.getText().trim();
        TableColumnModel tcm = tabla.getColumnModel();
        tcm.getColumn(5).setPreferredWidth(0);   
        tcm.getColumn(5).setMaxWidth(0);
        tcm.getColumn(5).setMinWidth(0);
        tcm.getColumn(5).setWidth(0);
        this.txtCant.setText("");
        this.txtImpo.setText("");
        this.lblMsj.setText("");
        this.sumaCant=0.0;
        this.sumaImp=0.0;
        this.codigos=null;
        this.btnCargar.setVisible(false);
        this.lblSacec.setVisible(false); 
        this.LimpiarTabla();
        TipoVale tipo = new TipoVale();
        tipo.setCodigo(1);
        if(this.esNum(str)){
       
            try {
                Integer codigo=Integer.valueOf(this.txtCod.getText().trim());
                this.cod=this.logs.obtenerCodigoTipoLiq1(codigo);
                if(cod!=null){
                    if(cod.getCod()==401){
                        Boolean esta =this.logs.hayCodigoEnIngreso(cod);
                        if(!esta){
                            ArrayList<Ingreso> vales=this.logs.cargoVales(this.txtFechaLiq.getText(),cod,tipo);
                            this.codigos=vales;
                            DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
                            this.recargaTabla(codigo); 
                            this.btnConfirmar.setEnabled(true);
                            this.btnLimpiar.setEnabled(true);
                            this.txtDescripcion.setText(cod.toString());
                            this.txtNumFunc.requestFocus();
                            this.inicializo(true);
                        }
                        else{
                            int resp = JOptionPane.showConfirmDialog(this, "Existen vales cargados, desea eliminarlos y volverlos a cargar?",null, JOptionPane.YES_NO_OPTION);
                            if(resp==0){
                                if(this.logs.eliminaCodigoEnIngreso(cod)){
                                    this.btnBuscaCod.doClick();
                                }
                            }
                            else{
                                this.recargaTabla(codigo);
                                this.txtDescripcion.setText(cod.toString());
                                this.txtNumFunc.requestFocus();
                                this.inicializo(true);  
                            }
                        }
                        
                    }
                    else if(cod.getCod()==461){
                        this.recargaTabla(codigo);
                        this.btnConfirmar.setEnabled(true);
                        this.btnLimpiar.setEnabled(true);
                        this.txtDescripcion.setText(cod.toString());
                        this.txtNumFunc.requestFocus();
                        this.inicializo(true);
                        this.btnCargar.setVisible(true);
                        this.lblSacec.setVisible(true); 
                    }
                    else if(cod.getCod()==412){
                        this.recargaTabla(codigo);
                        this.btnConfirmar.setEnabled(true);
                        this.btnLimpiar.setEnabled(true);
                        this.txtDescripcion.setText(cod.toString());
                        this.txtNumFunc.requestFocus();
                        this.inicializo(true);
                        this.btnCargar.setVisible(true);
                        this.lblSacec.setVisible(true);
                        this.lblSacec.setText("Cargar Excel SACEC");
                    }
                    else if(cod.getCod()==520){
                        this.recargaTabla(codigo);
                        this.btnConfirmar.setEnabled(true);
                        this.btnLimpiar.setEnabled(true);
                        this.txtDescripcion.setText(cod.toString());
                        this.txtNumFunc.requestFocus();
                        this.inicializo(true);
                        this.btnCargar.setVisible(true);
                        this.lblSacec.setVisible(true);
                        this.lblSacec.setText("Cargar Excel CAP"); 
                    }
                    else if(cod.getCod()==410){
                        Boolean esta =this.logs.hayCodigoEnIngreso(cod);
                        if(!esta){
                            String nombreTabla=this.logs.buscoTablaRetAce(this.txtFechaLiq.getText());
                            ArrayList<Ingreso> retenciones=null;
                            if(!"".equals(nombreTabla)){
                                retenciones = this.logs.cargoRetencionesAce(nombreTabla,cod);
                                this.codigos=retenciones;
                                this.recargaTabla(codigo); 
                                this.btnConfirmar.setEnabled(true);
                                this.btnLimpiar.setEnabled(true);
                                this.inicializo(true);
                            }
                            this.txtDescripcion.setText(cod.toString());
                            this.txtNumFunc.requestFocus();
                        }
                        else{
                            int resp = JOptionPane.showConfirmDialog(this, "Existen retenciones cargadas, desea eliminarlas y volverlas a cargar?",null, JOptionPane.YES_NO_OPTION);
                            if(resp==0){
                                if(this.logs.eliminaCodigoEnIngreso(cod)){
                                    this.btnBuscaCod.doClick();
                                }
                            }
                            else{
                                this.recargaTabla(codigo);
                                this.txtDescripcion.setText(cod.toString());
                                this.txtNumFunc.requestFocus();
                                this.inicializo(true);  
                            }
                        }
                    }
                    else{
                        this.recargaTabla(codigo);
                        this.txtDescripcion.setText(cod.toString());
                        this.txtNumFunc.requestFocus();
                        this.inicializo(true);
                    }
                }
                else{
                    this.txtDescripcion.setText("Código Incorrecto");
                    this.txtCod.requestFocus();
                    this.txtCod.selectAll();
                    this.cod=null;
                    this.inicializo(false);
                }
            } catch (BDExcepcion ex) {
                   JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
            }
            }
            else{
                if(!str.equals("")){
                    this.txtDescripcion.setText("Ingrese solo números");
                    this.txtCod.requestFocus();
                    this.txtCod.selectAll();
                }
            }
    }//GEN-LAST:event_btnBuscaCodActionPerformed

    public void recargaTabla(Integer cod) throws BDExcepcion{

        ArrayList<Ingreso> ingresos=this.logs.traeIngresosPorCod(cod);
                   if(ingresos.size()>0){
                       for(Ingreso i:ingresos){
                           if(i.getCodMov().getTipoValor()==1){
                               this.cargaTabla(i.getCodMov(),String.valueOf(i.getImporte()),1,i.getFunc(),i.getFecha());
                            }
                            else if(i.getCodMov().getTipoValor()==0){
                               this.cargaTabla(i.getCodMov(),String.valueOf(i.getCantidad()),1,i.getFunc(),i.getFecha());
                            }
                
                       }
                      
                   }
                   if(codigos!=null){
                       if(codigos.size()>0){
                           for(Ingreso i:codigos){
                           if(i.getCodMov().getTipoValor()==1){
                               this.cargaTabla(i.getCodMov(),String.valueOf(i.getImporte()),0,i.getFunc(),i.getFecha());
                            }
                            else if(i.getCodMov().getTipoValor()==0){
                               this.cargaTabla(i.getCodMov(),String.valueOf(i.getCantidad()),0,i.getFunc(),i.getFecha());
                            }
                         
                           }
                       }
                   }
                       this.txtCant.setText(this.decimales(sumaCant));
                       this.txtImpo.setText(this.decimales(sumaImp));
    }
    
    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtFechaLiqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaLiqKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaLiqKeyTyped

    private void txtNumFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFuncKeyTyped
        this.txtValor.setText("");
        this.txtNombre.setText("");
        this.lblMsj.setText("");
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
         if(this.txtNumFunc.getText().length()>3){
             evt.consume();
        }
        if(evt.getKeyChar()==43){
           // this.txtCod.setText(""); 
            try {
                listadoFunc=InternalListadoFuncFiltro.instancia(log,1);
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
                JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
            } catch (SQLException ex) {
                 JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
            }
        }
    }//GEN-LAST:event_txtNumFuncKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String numFunc=this.txtNumFunc.getText();
        this.lblMsj.setText("");
        //this.codigos=null;
        if(this.esNum(numFunc)){
            try {
                f = this.log.funcParcial(numFunc);
                if(f!=null){
                    this.txtNombre.setText(f.getNomCompletoApe());
                    this.txtValor.requestFocus();
//                    this.inicializo(true);
//                    this.LimpiarTabla();
//                    this.recargaTabla(f.getCodFunc());

                }
                else{
                    this.txtNumFunc.requestFocus();
                    this.txtNombre.setText("El funcionario no existe");
                    this.txtNumFunc.selectAll();
//                    this.LimpiarTabla();
//                    this.inicializo(false);

                }
            } catch (BDExcepcion ex) {
               JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
            }

        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyTyped
        this.lblMsj.setText("");
        int k = (int) evt.getKeyChar();
        char c=evt.getKeyChar();
        if(k!=46){
            if(!Character.isDigit(c)){
                evt.consume();
            }
        }

        if(k==10){
            if(cod!=null){
                try {
                    if(codigos==null){
                        codigos=new ArrayList<>();
                    }
                    String str=this.txtValor.getText().trim();
                    if(!this.codigoEsta()&&!this.logs.estaEnPersIngresos(f,cod)&&!str.equals("0")&&!str.equals("")){
                        Ingreso ing=new Ingreso();
                        ing.setCodMov(cod);
                        // ing.setFecha(fecha);
                        ing.setCodFunc(f.getCodFunc());
                        ing.setFunc(f);
                        if(cod.getTipoValor()==1){
                            ing.setImporte(Double.parseDouble(str));
                            ing.setCantidad(1.0);
                        }
                        else if(cod.getTipoValor()==0){
                            ing.setCantidad(Double.parseDouble(str));
                            ing.setImporte(0.0);
                        }
                        ing.setEnPers(0);
                        this.cargaTabla(cod,str,0,f,null);
                        this.codigos.add(ing);
                        this.txtNombre.setText("");
                        this.txtNumFunc.setText("");
                        this.txtValor.setText("");
                        this.txtNumFunc.requestFocus();
                    }
                    else{
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(this, "Este código ya esta en la tabla o ya fue ingresado");
                        this.txtNombre.setText("");
                        this.txtNumFunc.setText("");
                        this.txtValor.setText("");
                        this.txtNumFunc.requestFocus();
                    }
                } catch (BDExcepcion ex) {
                  JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                }
            }
        }
    }//GEN-LAST:event_txtValorKeyTyped

    private boolean codigoEsta() {
        boolean esta=false;
        int i=0;
        while(i<codigos.size()&&!esta){
            if(codigos.get(i).getCodFunc().equals(f.getCodFunc())){
                esta=true;
            }
            i++;
        }
        return esta;
    }
    private void cargaTabla(Codigo c,String str,Integer EnPers,Funcionario fu, Date fecha) {
     
        DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
        TableColumnModel tcm = tabla.getColumnModel();
        Object[] filas=new Object[modelo.getColumnCount()];
         if(fu!=null){
                            filas[0]=fu.getCodFunc();
                            filas[1]=fu.getNomCompletoApe();
                            if(c.getTipoValor()==1){
                                filas[3]=this.decimales(Double.parseDouble(str)); 
                                filas[2]=1.0;
                                filas[4]=EnPers;
                                sumaImp+=Double.parseDouble(str);
                                sumaCant+=1.0;
                            }
                            else if(c.getTipoValor()==0){
                                filas[2]=Double.parseDouble(str);
                                filas[3]=0.0; 
                                filas[4]=EnPers;
                                sumaCant+=Double.parseDouble(str);
                            }
//                            if(c.getCod()==401){
//                            tcm.getColumn(5).setPreferredWidth(50); 
//                            tcm.getColumn(5).setMaxWidth(100);
//                            tcm.getColumn(5).setMinWidth(100);
//                            tcm.getColumn(5).setWidth(100);
//                            if(fecha!=null){
//                                filas[5]=this.convertirFecha(fecha);
//                            }
//                            }
//                            else{
//                            tcm.getColumn(5).setPreferredWidth(0);   
//                            tcm.getColumn(5).setMaxWidth(0);
//                            tcm.getColumn(5).setMinWidth(0);
//                            tcm.getColumn(5).setWidth(0);
//                            }
                           
                            
                            RenderCodigos rr=new RenderCodigos(4);
                            tabla.setDefaultRenderer(Object.class, rr);
                            modelo.addRow(filas);
         }
         else{
             this.lblMsj.setText("Almenos un funcionario no fue encontrado");
         }
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.changeSelection(modelo.getRowCount()-1, 0, false, false);
        this.txtCant.setText(this.decimales(sumaCant));
        this.txtImpo.setText(this.decimales(sumaImp));
    }
    
    private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
}
    
    
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        this.lblMsj.setText("");
        try {
            String fechaLiq = this.txtFechaLiq.getText();
            if(this.logs.bloqueoContaduria()==0){
                if(codigos!=null){
                    if(codigos.size()>0){
                        try {
                            int i;
                            
                            i = this.logs.insertarEnPersIngresos(codigos,fechaLiq);
                            JOptionPane.showMessageDialog(this, "Se han insertado "+i+" líneas.");
                            this.btnLimpiar.doClick();
                            
                        } catch (SQLException ex) {
                           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema, consulte a Desarrollo");
                        }
                        
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "En estos momentos no puede ingresar ni modificar ningún dato.");
            }
        } catch (BDExcepcion ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un problema, consulte a Desarrollo");
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void inicializo(boolean b){
        this.txtNumFunc.setEditable(b);
        this.txtValor.setEditable(b);
        this.btnLimpiar.setEnabled(b);
        this.btnConfirmar.setEnabled(b);
        this.btnBuscar.setEnabled(b);
    }
    
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        this.inicializo(false);
        this.txtCod.setText("");
        this.lblMsj.setText("");
        this.txtDescripcion.setText("");
        this.txtNombre.setText("");
        this.txtNumFunc.setText("");
        this.txtValor.setText("");
        this.txtCant.setText("");
        this.txtImpo.setText("");
        this.sumaCant=0.0;
        this.sumaImp=0.0;
        this.LimpiarTabla();
        this.txtCod.requestFocus();
        this.f=null;
        this.cod=null;
       // this.codIng=null;
        this.codigos=null;

    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            if(this.logs.bloqueoContaduria()==0){
                if(ingres!=null){
                    int respuesta=JOptionPane.showConfirmDialog(this, "Seguro desea eliminar esta línea?");
                    //si es 0,no es 1 cancela es 2
                    if(respuesta==0){
                        if(ingres.getEnPers()==1){
                            try {
                                ingres.setCodMov(cod);
                                if(this.logs.borrarCodigoEnPersIngresos(ingres)){
                                    this.LimpiarTabla();
                                    this.recargaTabla(cod.getCod());
                                }
                            } catch (BDExcepcion ex) {
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                            }
                        }
                        else if(ingres.getEnPers()==0){
                            try {
                                int i=0;
                                boolean esta=false;
                                while(i<codigos.size()&&!esta){
                                    if(codigos.get(i).getCodFunc().equals(ingres.getCodFunc())){
                                        codigos.remove(i);
                                        esta=true;
                                        sumaCant=0.0;
                                        sumaImp=0.0;
                                    }
                                    i++;
                                }
                                
                                this.LimpiarTabla();
                                this.recargaTabla(ingres.getCodMov().getCod());
                            } catch (BDExcepcion ex) {
                                JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                            }

                        }
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "En estos momentos no puede ingresar ni modificar ningún dato.");
            }
        } catch (BDExcepcion ex) {  
           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            if(this.logs.bloqueoContaduria()==0){
                if(this.ingres!=null){
                    try {
                        this.internalIng=InternalModificarIngreso.instancia(logs,this,ingres,1);
                        frmPrin prin=frmPrin.instancia();
                        if (!internalIng.isVisible()) {
                            prin.getDesktop().add(internalIng);
                            internalIng.setLocation((prin.getDesktop().getWidth()/2)-(internalIng.getWidth()/2),(prin.getDesktop().getHeight()/2) - internalIng.getHeight()/2);
                            internalIng.setVisible(true);
                            internalIng.repaint();
                            internalIng.revalidate();
                            
                        }
                        else{
                            internalIng.setSelected(true);
                            internalIng.requestFocus();
                            internalIng.setVisible(true);
                            internalIng.repaint();
                            internalIng.revalidate();
                            
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "En estos momentos no puede ingresar ni modificar ningún dato.");
            }
        } catch (BDExcepcion ex) {
           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema, consulte a Desarrollo");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txtImpoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImpoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImpoKeyTyped

    private void txtCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantKeyTyped

    private void txtCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFocusGained
        this.txtCod.selectAll();
    }//GEN-LAST:event_txtCodFocusGained

    private void txtNumFuncFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFuncFocusGained
        this.txtNumFunc.selectAll();
    }//GEN-LAST:event_txtNumFuncFocusGained

    private void txtValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusGained
        this.txtValor.selectAll();
    }//GEN-LAST:event_txtValorFocusGained

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        
        try {
            this.txtCant.setText("");
            this.txtImpo.setText("");
            this.sumaCant=0.0;
            this.sumaImp=0.0;
            this.codigos=null;
            this.LimpiarTabla();
            Integer codigo=Integer.valueOf(this.txtCod.getText().trim());
            if(codigo==461){
            this.cargaExcel();
            }
            if(codigo==412){
            this.cargaExcel();
            }
            else if(codigo==520){
             this.cargaExcelCap();
            }
        } catch (BDExcepcion ex) {
             JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
        }
    }//GEN-LAST:event_btnCargarActionPerformed

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
         sumaCant=0.0;
         sumaImp=0.0;
    DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
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

    
     private void cargaExcel() throws BDExcepcion{
        try {
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
                    Double importe = null;
                    if(codigos==null){
                    codigos=new ArrayList<>();
                    }
                    while (rowIterator.hasNext()){
                        row = rowIterator.next();
                        if(row.getRowNum()>=10){
                            Iterator<Cell> cellIterator = row.cellIterator();
                            Cell celda;
                            
                            while (cellIterator.hasNext()){
                                celda = cellIterator.next();
                                int i = celda.getColumnIndex();
                                if(celda.getColumnIndex()==1){
                                    dato=celda.getNumericCellValue();
                                    codFunc=dato.intValue();
                                }
                                if(celda.getColumnIndex()==6){
                                    importe=celda.getNumericCellValue();
                                }
                                
                            }
                            if(importe!=null){
                                if(importe>0){
                                    if(codFunc!=null){
                                        if(codFunc>0){
                                            this.armaCodigo(codFunc,importe,cnn);
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
            if(codigos!=null){
                if(codigos.size()>0){
                    int respuesta = JOptionPane.showConfirmDialog(this, "Se borraran los ingresos existentes para este código y fecha y se van a insertar "+codigos.size()+" nuevas líneas." + "Desea continuar?",null, JOptionPane.YES_NO_OPTION);
                    if(respuesta==0){
                         String fechaLiq = this.txtFechaLiq.getText();
                        this.logs.borrarEnPersIngresos(Integer.valueOf(this.txtCod.getText()),fechaLiq);
                        this.recargaTabla(Integer.valueOf(this.txtCod.getText()));
                    }
                }
            }
            if(cnn!=null){
                cnn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
           

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnBuscaCod;
    private org.edisoncor.gui.button.ButtonIcon btnBuscar;
    private org.edisoncor.gui.button.ButtonIcon btnCargar;
    private org.edisoncor.gui.button.ButtonRound btnConfirmar;
    private org.edisoncor.gui.button.ButtonRound btnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JLabel lblSacec;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtCant;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    private org.edisoncor.gui.textField.TextFieldRound txtDescripcion;
    private org.edisoncor.gui.textField.TextFieldRound txtFechaLiq;
    private org.edisoncor.gui.textField.TextFieldRound txtImpo;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtNumFunc;
    private org.edisoncor.gui.textField.TextFieldRound txtValor;
    // End of variables declaration//GEN-END:variables

    private void armaCodigo(Integer codFunc, Double vale, Connection cnn) {
        try {
            f=this.log.funcVale(codFunc, cnn);
            if(f!=null){
                Ingreso i = new Ingreso();
                i.setCodFunc(codFunc);
                i.setFunc(f);
                i.setImporte(vale);
                i.setCantidad(1.0); 
                Codigo c=new Codigo();
                c.setTipoValor(1);
                i.setCodMov(cod);
                this.codigos.add(i);
            }  } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargaExcelCap() {
       try {
            boolean continuar = true;
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
                    Double importe = null;
                    if(codigos==null){
                    codigos=new ArrayList<>();
                    }
                    while (continuar && rowIterator.hasNext()){
                       row = rowIterator.next();
                       
                                              
                        if(row.getRowNum()>=3){
                            Iterator<Cell> cellIterator = row.cellIterator();
                            Cell celda;
                                 
                            while (continuar && cellIterator.hasNext()){
                                celda = cellIterator.next();
                                int i = celda.getColumnIndex();
                                                     
                                
                                if(celda.getColumnIndex()==1){
                                   
                                    dato=celda.getNumericCellValue();
                                    codFunc=dato.intValue();
                                }
                                if(celda.getColumnIndex()==3){
                                    importe=celda.getNumericCellValue();
                                }
                                
                            }
                            if(importe!=null){
                                if(importe>0){
                                    if(codFunc!=null){
                                        if(codFunc>0){
                                            this.armaCodigo(codFunc,importe,cnn);
                                        }
                                    }
                                }
                                
                            }
                        if (row.getCell(1) == null || row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK) {
                                        continuar = false;
                        } else if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING && 
                                                row.getCell(1).getStringCellValue().isEmpty()) {
                                      continuar = false;
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
            if(codigos!=null){
                if(codigos.size()>0){
                    codigos.remove(codigos.size()-1);
                    int respuesta = JOptionPane.showConfirmDialog(this, "Se borraran los ingresos existentes para este código y fecha y se van a insertar "+codigos.size()+" nuevas líneas." + "Desea continuar?",null, JOptionPane.YES_NO_OPTION);
                    if(respuesta==0){
                         String fechaLiq = this.txtFechaLiq.getText();
                        this.logs.borrarEnPersIngresos(Integer.valueOf(this.txtCod.getText()),fechaLiq);
                        this.recargaTabla(Integer.valueOf(this.txtCod.getText()));
                    }
                }
            }
            if(cnn!=null){
                cnn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalVales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDExcepcion ex) {
            Logger.getLogger(InternalIngresoPorCod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
