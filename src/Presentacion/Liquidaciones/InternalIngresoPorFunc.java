
package Presentacion.Liquidaciones;

import Dominio.Codigo;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Logica.LogHorario;
import Persistencia.BDExcepcion;
import Presentacion.Marcas.InternalListadoCod;
import Presentacion.Marcas.InternalMarcaCodigo;
import Presentacion.RenderCodigos;
import Presentacion.frmPrin;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.edisoncor.gui.button.ButtonIcon;
import org.edisoncor.gui.textField.TextFieldRound;

public class InternalIngresoPorFunc extends javax.swing.JInternalFrame {

    private LogFuncionario log;
    private Funcionario f;
    private static InternalIngresoPorFunc instancia=null;
    private InternalModificarIngreso internalIng;
    private InternalListadoCodLiq listadoCod;
    private InternalListadoFuncFiltro listadoFunc;
    private LogCodigo logs;
    private Ingreso ingres;
    private Codigo codIng;
    private ArrayList<Ingreso> codigos=new ArrayList<>();
    Codigo cod;
    DefaultTableModel tmMov=null;
    
    public InternalIngresoPorFunc() throws ClassNotFoundException, SQLException {
        initComponents();
        this.txtNombre.setEditable(false);
        this.txtDescripcion.setEditable(false);
        log=new LogFuncionario();
        logs=new LogCodigo();
        this.txtFechaLiq.setText(this.log.fechaLiquidacion());
        this.txtFechaLiq.setEditable(false);
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

    public ButtonIcon getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(ButtonIcon btnBuscar) {
        this.btnBuscar = btnBuscar;
    }
    
    
    
    
//    generar eliminacion y modificacion, considerar ingresos en tabla y en memoria
    
    private void cargarMovimiento(MouseEvent e) throws ParseException {
     Integer m=this.tabla.rowAtPoint(e.getPoint());
     this.ingres=new Ingreso();
     this.codIng=new Codigo();
     codIng.setCod(Integer.valueOf(String.valueOf(tmMov.getValueAt(m, 0))));
     codIng.setDescripcion(String.valueOf(tmMov.getValueAt(m,1)));
     String cantidad=String.valueOf(tmMov.getValueAt(m,2));
     String importe=String.valueOf(tmMov.getValueAt(m,3)).replace(",", "");
     if(!cantidad.equals("1.0")){
         ingres.setCantidad(Double.valueOf(cantidad));
         ingres.setImporte(0.0);
         codIng.setTipoValor(0);
     }
     else if(!importe.equals("1.0")){
         ingres.setImporte(Double.valueOf(importe));
         ingres.setCantidad(1.0);
         codIng.setTipoValor(1);
     }
     ingres.setEnPers(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,4))));
     ingres.setCodFunc(f.getCodFunc());
     ingres.setCodMov(codIng);
    }

    public static InternalIngresoPorFunc instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalIngresoPorFunc();
         }
         return instancia;
      
   }

    public TextFieldRound getTxtNumFunc() {
        return txtNumFunc;
    }

    public void setTxtNumFunc(TextFieldRound txtNumFunc) {
        this.txtNumFunc = txtNumFunc;
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
    
    
    
    private void inicializo(boolean b){
        this.txtCod.setEditable(b);
        this.txtValor.setEditable(b);
        this.btnLimpiar.setEnabled(b);
        this.btnConfirmar.setEnabled(b);
        this.btnBuscaCod.setEnabled(b);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel2 = new javax.swing.JLabel();
        txtNumFunc = new org.edisoncor.gui.textField.TextFieldRound();
        btnBuscar = new org.edisoncor.gui.button.ButtonIcon();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel3 = new javax.swing.JLabel();
        txtFechaLiq = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        btnBuscaCod = new org.edisoncor.gui.button.ButtonIcon();
        txtDescripcion = new org.edisoncor.gui.textField.TextFieldRound();
        txtValor = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnConfirmar = new org.edisoncor.gui.button.ButtonRound();
        btnLimpiar = new org.edisoncor.gui.button.ButtonRound();

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
        setTitle("Ingreso de Movimientos por Funcionario");
        setPreferredSize(new java.awt.Dimension(780, 650));
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

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Código");

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

        btnBuscaCod.setBackground(new java.awt.Color(102, 153, 255));
        btnBuscaCod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lupa.png"))); // NOI18N
        btnBuscaCod.setText("buttonIcon1");
        btnBuscaCod.setToolTipText("Buscar");
        btnBuscaCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaCodActionPerformed(evt);
            }
        });

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

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Descripción");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Valor");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Cantidad", "Importe", "EnPers"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setResizable(false);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(10);
            tabla.getColumnModel().getColumn(1).setResizable(false);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(2).setResizable(false);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(40);
            tabla.getColumnModel().getColumn(3).setResizable(false);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(40);
            tabla.getColumnModel().getColumn(4).setMinWidth(0);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabla.getColumnModel().getColumn(4).setMaxWidth(0);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscaCod, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnBuscaCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFechaLiq, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFuncKeyTyped
        this.inicializo(false);
        this.txtDescripcion.setText("");
        this.txtValor.setText("");
        this.txtCod.setText("");
        this.txtNombre.setText("");
        this.cod=null;
        this.codigos=null;
        this.LimpiarTabla();
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
            this.txtCod.setText(""); 
            try {
                listadoFunc=InternalListadoFuncFiltro.instancia(log,0);
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
                Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_txtNumFuncKeyTyped

     public void LimpiarTabla() {
    DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String numFunc=this.txtNumFunc.getText();
        this.cod=null;
        this.codigos=null;
        if(this.esNum(numFunc)){
          
            try {
                f = this.log.funcParcial(numFunc);
                if(f!=null){
                    this.txtNombre.setText(f.getNomCompletoApe());
                    this.txtCod.requestFocus();
                    this.inicializo(true);
                    this.LimpiarTabla();  
                    this.recargaTabla(f.getCodFunc());
             
                }
                else{
                    this.txtNumFunc.requestFocus();
                    this.txtNombre.setText("El funcionario no existe");
                    this.txtNumFunc.selectAll();
                    this.LimpiarTabla();
                    this.inicializo(false);
                    
                }
            } catch (BDExcepcion ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
            }
                
              
            
                          
                }
                
            
        
    }//GEN-LAST:event_btnBuscarActionPerformed

    public void recargaTabla(Integer cod) throws BDExcepcion{
        
        ArrayList<Ingreso> ingresos=this.logs.traePersIngresos(cod);
                   if(ingresos.size()>0){
                       for(Ingreso i:ingresos){
                           if(i.getCodMov().getTipoValor()==1){
                               this.cargaTabla(i.getCodMov(),String.valueOf(i.getImporte()),1);
                            }
                            else if(i.getCodMov().getTipoValor()==0){
                               this.cargaTabla(i.getCodMov(),String.valueOf(i.getCantidad()),1);
                            }
                       }
                      
                   }
                   if(codigos!=null){
                       if(codigos.size()>0){
                           for(Ingreso i:codigos){
                           if(i.getCodMov().getTipoValor()==1){
                               this.cargaTabla(i.getCodMov(),String.valueOf(i.getImporte()),0);
                            }
                            else if(i.getCodMov().getTipoValor()==0){
                               this.cargaTabla(i.getCodMov(),String.valueOf(i.getCantidad()),0);
                            }
                       }
                       }
                   }
   }
    
    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtFechaLiqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaLiqKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaLiqKeyTyped

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
        this.txtDescripcion.setText("");
        char c=evt.getKeyChar();
        this.txtValor.setText("");
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
                listadoCod=InternalListadoCodLiq.instancia(logs,this,0);
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
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(evt.getKeyChar()==27){
            this.dispose();
        }
    }//GEN-LAST:event_txtCodKeyTyped

    private void btnBuscaCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaCodActionPerformed
        String str=this.txtCod.getText().trim();
        
        if(this.esNum(str)){
            try {
                Integer codigo=Integer.valueOf(this.txtCod.getText().trim());
                this.cod=this.logs.obtenerCodigoTipoLiq1(codigo);
                if(cod!=null){
                    this.txtDescripcion.setText(cod.toString());
                    this.txtValor.requestFocus();
                }
                else{
                    this.txtDescripcion.setText("Código Incorrecto");
                    this.txtCod.requestFocus();
                    this.txtCod.selectAll();
                    this.cod=null;
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

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyTyped
        Date fecha=new Date();
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
                    if(!this.codigoEsta(cod)&&!this.logs.estaEnPersIngresos(f,cod)&&!str.equals("0")&&!str.equals("")){
                        Ingreso ing=new Ingreso();
                        ing.setCodMov(cod);
                        ing.setFecha(fecha);
                        ing.setCodFunc(f.getCodFunc());
                        if(cod.getTipoValor()==1){
                            ing.setImporte(Double.parseDouble(str));
                            ing.setCantidad(1.0);
                        }
                        else if(cod.getTipoValor()==0){
                            ing.setCantidad(Double.parseDouble(str));
                            ing.setImporte(0.0);
                        }
                        ing.setEnPers(0);
                        this.cargaTabla(cod,str,0);
                        this.codigos.add(ing);
                        this.txtDescripcion.setText("");
                        this.txtCod.setText("");
                        this.txtValor.setText("");
                        this.txtCod.requestFocus();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Este código ya esta en la tabla o ya fue ingresado");
                        this.txtDescripcion.setText("");
                        this.txtCod.setText("");
                        this.txtValor.setText("");
                        this.txtCod.requestFocus();
                    }
                } catch (BDExcepcion ex) {
                   JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                }
            }
        }
    }//GEN-LAST:event_txtValorKeyTyped

        private void cargaTabla(Codigo c,String str,Integer EnPers) {
                            
         DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
        
         Object[] filas=new Object[modelo.getColumnCount()];
            
                            filas[0]=c.getCod().toString();
                            filas[1]=c.getDescripcion();
                            if(c.getTipoValor()==1){
                                filas[3]=this.decimales(Double.parseDouble(str)); 
                                filas[2]=1.0;
                                filas[4]=EnPers;
                            }
                            else if(c.getTipoValor()==0){
                                filas[2]=Double.parseDouble(str);
                                filas[3]=0.0;
                                filas[4]=EnPers;
                            }
                            RenderCodigos rr=new RenderCodigos(4);
                            tabla.setDefaultRenderer(Object.class, rr);
                            modelo.addRow(filas);
                           
                            
                    
               
        
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.changeSelection(modelo.getRowCount()-1, 0, false, false);
    }
    
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed
    private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
    }
    //eliminar
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            if(this.logs.bloqueoContaduria()==0){
                if(ingres!=null){
                    int respuesta=JOptionPane.showConfirmDialog(this, "Seguro desea eliminar esta línea?");
                    //si es 0,no es 1 cancela es 2
                    if(respuesta==0){
                        if(ingres.getEnPers()==1){
                            
                            try {
                                if(this.logs.borrarCodigoEnPersIngresos(ingres)){
                                    this.LimpiarTabla();
                                    this.recargaTabla(ingres.getCodFunc());
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
                                    if(codigos.get(i).getCodFunc().equals(ingres.getCodFunc()) && codigos.get(i).getCodMov().getCod().equals(ingres.getCodMov().getCod())){
                                        codigos.remove(i);
                                        esta=true;
                                    }
                                    i++;
                                }
                                
                                this.LimpiarTabla();
                                this.recargaTabla(ingres.getCodFunc());
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

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
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
                            Logger.getLogger(InternalIngresoPorFunc.class.getName()).log(Level.SEVERE, null, ex);
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
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
      this.inicializo(false);
      this.txtCod.setText("");
      this.txtDescripcion.setText("");
      this.txtNombre.setText("");
      this.txtNumFunc.setText("");
      this.txtValor.setText("");
      this.LimpiarTabla();
      this.txtNumFunc.requestFocus();
      this.f=null;
      this.cod=null;
      this.codIng=null;
      this.codigos=null;
              
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            if(this.logs.bloqueoContaduria()==0){
                if(this.ingres!=null){
                    try {
                        this.internalIng=InternalModificarIngreso.instancia(logs,this,ingres,0);
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
           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txtNumFuncFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFuncFocusGained
        this.txtNumFunc.selectAll();
    }//GEN-LAST:event_txtNumFuncFocusGained

    private void txtCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFocusGained
       this.txtCod.selectAll();
    }//GEN-LAST:event_txtCodFocusGained

    private void txtValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusGained
       this.txtValor.selectAll();
    }//GEN-LAST:event_txtValorFocusGained

    
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnBuscaCod;
    private org.edisoncor.gui.button.ButtonIcon btnBuscar;
    private org.edisoncor.gui.button.ButtonRound btnConfirmar;
    private org.edisoncor.gui.button.ButtonRound btnLimpiar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    private org.edisoncor.gui.textField.TextFieldRound txtDescripcion;
    private org.edisoncor.gui.textField.TextFieldRound txtFechaLiq;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtNumFunc;
    private org.edisoncor.gui.textField.TextFieldRound txtValor;
    // End of variables declaration//GEN-END:variables

    private boolean codigoEsta(Codigo cod) {
        boolean esta=false;
        int i=0;
        while(i<codigos.size()&&!esta){
            if(codigos.get(i).getCodMov().getCod().equals(cod.getCod())){
                esta=true;
            }
            i++;
        }
        return esta;
    }
}
