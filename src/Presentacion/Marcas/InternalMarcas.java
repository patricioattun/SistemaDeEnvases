package Presentacion.Marcas;

import Dominio.Codigo;
import Dominio.Funcionario;
import Dominio.Marca;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Logica.LogTripaliare;
import Persistencia.BDExcepcion;
import Presentacion.Licencias.InternalAjustesLicencia;
import Presentacion.RenderMarca;
import Presentacion.Reportes.InternalListadoMovLic;

import Presentacion.frmPrin;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.edisoncor.gui.button.ButtonIcon;


public class InternalMarcas extends javax.swing.JInternalFrame {

    private LogTripaliare trip;
    private LogFuncionario pers;
    private LogCodigo cod;
    private static InternalMarcas instancia;
    private Marca marca=null;
    private ArrayList<Codigo> codes;
    DefaultTableModel tmMov=null;
    private InternalAjustesLicencia internalAjustes;
    private InternalListadoMovLic internalMovLic;
    private InternalSupervisar internalSupervisar;
    private InternalMarcaCodigo marcaCod;
    private InternalListadosMarcas listadosMarcas;
    private InternalAgregarDia internalAgregar;
    private ArrayList<Marca> marcas;
    private ArrayList<Marca> aux;
    JPasswordField pf = null;
    private Funcionario feMa;
    public InternalMarcas() throws ClassNotFoundException, SQLException {
        initComponents();
        trip=new LogTripaliare();
        pers=new LogFuncionario();
        cod=new LogCodigo();
        codes=new ArrayList<>();
        aux=new ArrayList<>();
        tmMov = (DefaultTableModel) tablaLic.getModel();
        tablaLic.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
        
        tablaLic.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    try { 
                        cargaCodigo(e);
                        
                    } catch (ParseException ex) {
                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
                else if(e.getClickCount()==1){
                    try {
                        cargarMovimiento(e);
                    } catch (ParseException ex) {
                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
         }

            
           
        });
    }

    
    private void cargaCodigo(MouseEvent e) throws ParseException, ClassNotFoundException, SQLException{
     Integer m=this.tablaLic.rowAtPoint(e.getPoint());
     this.marca=this.cargarMovimientoMultiple(m);
     marcaCod=InternalMarcaCodigo.instancia(trip,cod,this);
     
     if(marca.getProcesado()==null){
     frmPrin prin=frmPrin.instancia();
     Funcionario f=this.pers.funcParcial(String.valueOf(tmMov.getValueAt(m,9)));
             if (!marcaCod.isVisible()) {
                 prin.getDesktop().add(marcaCod);
                 marcaCod.setLocation((prin.getDesktop().getWidth()/2)-(marcaCod.getWidth()/2),(prin.getDesktop().getHeight()/2) - marcaCod.getHeight()/2);
                 marcaCod.setVisible(true);
                 marcaCod.setMarcas(null);
                 
                 marcaCod.setMarca(marca);
                 marcaCod.getLblNombres().setText(f.getNomCompleto());
                 marcaCod.getLblMarca().setText(marca.toString());
                 marcaCod.repaint();
                 marcaCod.revalidate();

             }
             else{
                 marcaCod.requestFocus();
                 try {
                        marcaCod.setSelected(true);
                        marcaCod.setVisible(true);
                        marcaCod.setMarcas(null);
                        marcaCod.setMarca(marca);
                        marcaCod.getLblNombres().setText(f.getNomCompleto());
                        marcaCod.getLblMarca().setText(marca.toString());
                        marcaCod.repaint();
                        marcaCod.revalidate();

                 } catch (PropertyVetoException ex) {
                     //lblMensaje.setText(ex.getMessage());
                 }
             }
     }
     else{
         JOptionPane.showMessageDialog(this, "Esta marca no se puede editar");
     }
    }
    
    
    public static InternalMarcas instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalMarcas();
         }
         return instancia;
      
   }

    public ButtonIcon getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(ButtonIcon btnListar) {
        this.btnListar = btnListar;
    }
    
    
     private void cargarMovimiento(MouseEvent e) throws ParseException {
     Integer m=this.tablaLic.rowAtPoint(e.getPoint());
     this.marca=new Marca();
     marca.setFechaUso(this.stringADate(String.valueOf(tmMov.getValueAt(m,1))));
     marca.setHoraUso(String.valueOf(tmMov.getValueAt(m,2)));
     marca.setIncongruencia(Integer.valueOf(String.valueOf(tmMov.getValueAt(m, 3))));
     if(tmMov.getValueAt(m,4).equals("NO")){
     marca.setSupervisado(0);
     }
     else{
     marca.setSupervisado(1);  
     }
     marca.setResponsable(String.valueOf(tmMov.getValueAt(m,5)));
     marca.setFecha(this.stringADate(String.valueOf(tmMov.getValueAt(m,6))));
     marca.setTipo(String.valueOf(tmMov.getValueAt(m,7)));
     marca.setId(Long.parseLong(String.valueOf(tmMov.getValueAt(m,8))));
     marca.setFunCod(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,9))));
     marca.setProcesado(this.stringADate(String.valueOf(tmMov.getValueAt(m,11))));
     marca.setMarcaFecha((Timestamp) tmMov.getValueAt(m, 12));
    }
     
   
     private Marca cargarMovimientoMultiple(Integer m) throws ParseException {
     Marca mar=new Marca();
     mar.setFechaUso(this.stringADate(String.valueOf(tmMov.getValueAt(m,1))));
     mar.setHoraUso(String.valueOf(tmMov.getValueAt(m,2)));
     mar.setIncongruencia(Integer.valueOf(String.valueOf(tmMov.getValueAt(m, 3))));
     if(tmMov.getValueAt(m,4).equals("NO")){
     mar.setSupervisado(0);
     }
     else{
     mar.setSupervisado(1);  
     }
     mar.setResponsable(String.valueOf(tmMov.getValueAt(m,5)));
     mar.setFecha(this.stringADate(String.valueOf(tmMov.getValueAt(m,6))));
     mar.setTipo(String.valueOf(tmMov.getValueAt(m,7)));
     mar.setId(Long.parseLong(String.valueOf(tmMov.getValueAt(m,8))));
     mar.setFunCod(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,9))));
     mar.setProcesado(this.stringADate(String.valueOf(tmMov.getValueAt(m,11))));
     mar.setMarcaFecha((Timestamp) tmMov.getValueAt(m,12));
     return mar;
     //this.aux.add(mar);
    } 
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        txtFechaDesde = new com.toedter.calendar.JDateChooser();
        txtFechahasta = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        buttonIcon1 = new org.edisoncor.gui.button.ButtonIcon();
        lblMsg = new javax.swing.JLabel();
        btnListar = new org.edisoncor.gui.button.ButtonIcon();
        jLabel5 = new javax.swing.JLabel();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaLic = new javax.swing.JTable();
        lblNombre = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lblHorarios = new javax.swing.JLabel();
        btnListar1 = new org.edisoncor.gui.button.ButtonIcon();
        jLabel11 = new javax.swing.JLabel();
        jSup = new javax.swing.JCheckBox();
        buttonIcon2 = new org.edisoncor.gui.button.ButtonIcon();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCodes = new javax.swing.JTable();

        jMenuItem1.setText("Ajustar Licencia");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Ver Movimientos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("Supervisar");
        jMenuItem3.setToolTipText("");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        jMenuItem4.setText("Editar Marca");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem4);

        jMenuItem5.setText("Ingresar Código");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem5);

        jMenuItem6.setText("Agregar Marca");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem6);

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(1020, 660));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Marcas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 18))); // NOI18N
        jPanel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel1FocusGained(evt);
            }
        });

        txtFechaDesde.setDateFormatString("dd/MM/yyyy");
        txtFechaDesde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaDesdeKeyTyped(evt);
            }
        });

        txtFechahasta.setDateFormatString("dd/MM/yyyy");
        txtFechahasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechahastaKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Listar");

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel2.setText("Hasta:");

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel3.setText("Desde:");

        buttonIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizar.png"))); // NOI18N
        buttonIcon1.setText("buttonIcon1");
        buttonIcon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonIcon1ActionPerformed(evt);
            }
        });

        lblMsg.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        lblMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tabla.png"))); // NOI18N
        btnListar.setText("buttonIcon1");
        btnListar.setToolTipText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel5.setText("Actualizar Marcas");

        txtCod.setBackground(new java.awt.Color(102, 153, 255));
        txtCod.setForeground(new java.awt.Color(255, 255, 255));
        txtCod.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCod.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCod.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtCod.setSelectionColor(new java.awt.Color(255, 255, 255));
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

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Número de Funcionario");

        tablaLic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Día", "Fecha", "Marca", "Diferencia", "Supervisado", "Responsable", "Fecha Actualizado", "Tipo", "Id", "Num. Funcionario", "Códigos", "Procesado", "MarcaReal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaLic.setComponentPopupMenu(jPopupMenu1);
        jScrollPane2.setViewportView(tablaLic);
        if (tablaLic.getColumnModel().getColumnCount() > 0) {
            tablaLic.getColumnModel().getColumn(8).setMinWidth(0);
            tablaLic.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaLic.getColumnModel().getColumn(8).setMaxWidth(0);
            tablaLic.getColumnModel().getColumn(12).setMinWidth(0);
            tablaLic.getColumnModel().getColumn(12).setPreferredWidth(0);
            tablaLic.getColumnModel().getColumn(12).setMaxWidth(0);
        }

        lblNombre.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton1.setBackground(new java.awt.Color(255, 255, 0));

        jButton2.setBackground(new java.awt.Color(0, 255, 255));

        jButton3.setBackground(new java.awt.Color(255, 0, 0));

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel4.setText("Marca vacía automática");

        jLabel7.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel7.setText("Marca superpuesta");

        jLabel8.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel8.setText("Licencia");

        jLabel9.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel9.setText("Falta");

        jButton4.setBackground(new java.awt.Color(0, 0, 255));

        jButton5.setBackground(new java.awt.Color(153, 153, 153));

        jLabel10.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel10.setText("Feriado");

        lblHorarios.setFont(new java.awt.Font("Ebrima", 0, 11)); // NOI18N
        lblHorarios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnListar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/procesar.png"))); // NOI18N
        btnListar1.setText("buttonIcon1");
        btnListar1.setToolTipText("Procesar");
        btnListar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListar1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Procesar Período");

        jSup.setText("Sin supervisar");

        buttonIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Excel_2013_23480.png"))); // NOI18N
        buttonIcon2.setText("buttonIcon1");
        buttonIcon2.setToolTipText("Exportar a Excel");
        buttonIcon2.setMaximumSize(new java.awt.Dimension(160, 68));
        buttonIcon2.setMinimumSize(new java.awt.Dimension(160, 68));
        buttonIcon2.setPreferredSize(new java.awt.Dimension(160, 68));
        buttonIcon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonIcon2ActionPerformed(evt);
            }
        });

        tablaCodes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Total", "Fechas"
            }
        ));
        jScrollPane1.setViewportView(tablaCodes);
        if (tablaCodes.getColumnModel().getColumnCount() > 0) {
            tablaCodes.getColumnModel().getColumn(0).setMinWidth(100);
            tablaCodes.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaCodes.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaCodes.getColumnModel().getColumn(1).setMinWidth(100);
            tablaCodes.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaCodes.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(240, 240, 240)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(txtFechahasta, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHorarios, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(buttonIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(767, 767, 767)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(txtFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(277, 277, 277)
                        .addComponent(buttonIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(607, 607, 607)
                        .addComponent(jSup, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(607, 607, 607)
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(767, 767, 767)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(767, 767, 767)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(767, 767, 767)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnListar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5)
                        .addGap(31, 31, 31)
                        .addComponent(txtFechahasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lblHorarios, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6))
                        .addGap(11, 11, 11)
                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(lblMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txtFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(buttonIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addComponent(buttonIcon2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSup, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnListar1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(80, 80, 80))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFechaDesdeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaDesdeKeyTyped
       this.lblMsg.setText("");
       this.lblNombre.setText(""); 
       this.lblHorarios.setText("");
       this.marcas=null;
    }//GEN-LAST:event_txtFechaDesdeKeyTyped

    private void txtFechahastaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechahastaKeyTyped
       this.lblMsg.setText("");
       this.lblNombre.setText(""); 
       this.lblHorarios.setText("");
       this.marcas=null;
    }//GEN-LAST:event_txtFechahastaKeyTyped

    private void buttonIcon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIcon1ActionPerformed
        this.LimpiarTabla();
        Date desde=this.txtFechaDesde.getDate();
        Date hasta=this.txtFechahasta.getDate();
        this.lblMsg.setText("");
        this.lblNombre.setText(""); 
        this.lblHorarios.setText("");
        this.marcas=null;
        Date hoy=new Date();
        boolean fin=false;
        Date ult=null;
        Integer sin=-1;
        try {
            ult = this.trip.ultimaMarca();
        } catch (SQLException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(desde!=null && hasta!=null){
                if(hasta.after(desde)){
                    if(hasta.before(ult)){
                if(hasta.before(hoy)){
                ArrayList<Integer>listado=trip.funcionariosSoloTrip(desde, hasta);
                String str="";
                if(listado.size()>0){
                    for(Integer i:listado){
                        str+=i+", ";
                    }
                    str=str.substring(0, str.length()-2);
                    JOptionPane.showMessageDialog(this, "Estos funcionarios existen solo en el reloj y no se traerán sus marcas, "+str);
                    fin=trip.prueba(desde,hasta,listado);
                    
                }
                else{
                    fin=trip.prueba(desde,hasta,null);
                }
            this.lblMsg.repaint();
            if(fin==true){
                sin=this.trip.sinSupervisar(desde, hasta);
                JOptionPane.showMessageDialog(this, "Actualización de marcas lista. Hay "+sin+" sin supervisar.");
                }
            }
                else{
                    JOptionPane.showMessageDialog(this, "La fecha 'Hasta' no puede ser posterior a hoy");
                }
                }
               else{
                   JOptionPane.showMessageDialog(this, "Actualice el reloj, la última marca es del "+ this.obtenerFecha((Timestamp) ult));
               }
                }
                else{
                     JOptionPane.showMessageDialog(this, "La fecha 'Desde' no puede ser posterior a 'Hasta'");
                }
        }
            
        } catch (SQLException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDExcepcion ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonIcon1ActionPerformed

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
       this.LimpiarTabla();  
       this.lblMsg.setText("");
       this.lblNombre.setText(""); 
       this.lblHorarios.setText("");
       this.marcas=null;
       //this.jScrollPane1.setVisible(false);
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)) {
            evt.consume();
        }
        if(evt.getKeyChar()==10){
            this.btnListar.doClick();
        }
    }//GEN-LAST:event_txtCodKeyTyped

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
       this.LimpiarTabla();
       this.lblMsg.setText("");
       this.lblNombre.setText("");
       Date hoy=new Date();
       Date desde=this.txtFechaDesde.getDate();
       Date hasta=this.txtFechahasta.getDate();
       String cod=this.txtCod.getText();
       this.marcas=null;
       Integer sup=null;
     
       if(this.jSup.isSelected()){
           sup=0;
       }
       else{
           sup=1;
       }
       
       if(desde!=null && hasta!=null){
           if(hasta.after(desde)){
            
                   if(hasta.before(hoy)){
                       try {
                           if(!cod.equals("")){
                               Funcionario f=this.pers.funcParcial(cod);
                               feMa=f;
                               if(f!=null){
                                marcas=this.trip.traerTablaFiltro(desde,hasta,f,sup);
                                codes=this.trip.traerCodigosPeriodo(desde,hasta,cod);
                                if(marcas!=null){
                                    if(marcas.size()>0){
                                        this.cargarTabla(marcas);
                                    }
                                    else{
                                        this.lblMsg.setText("No hay marcas en el período seleccionado");
                                        this.txtCod.selectAll();
                                    }
                                }
                           
                           
                               
                                   this.lblNombre.setText(f.getNomCompleto());
                                   if(f.getHorarios().size()==2){
                                       this.lblHorarios.setText(f.getHorarios().get(0).toString()+" - " +f.getHorarios().get(1).toString());
                                   }
                                   else{
                                       this.lblHorarios.setText(f.getHorarios().get(0).toString());
                                   }
                               }
                               else{
                                   this.lblMsg.setText("El funcionario que busca no existe");
                                   this.txtCod.selectAll();
                               }
                           }
                           else{
                               marcas=this.trip.traerTablaFiltro(desde,hasta,null,sup);
                               if(marcas!=null){
                                    if(marcas.size()>0){
                                        this.cargarTabla(marcas);
                                    }
                                    else{
                                        this.lblMsg.setText("No hay marcas en el período seleccionado");
                                        this.txtCod.selectAll();
                                    }
                                }
                           }
                           
                       } catch (ClassNotFoundException ex) {
                           Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                       } catch (SQLException ex) {
                           Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   } 
                   else{
                       JOptionPane.showMessageDialog(this, "La fecha 'Hasta' no puede ser posterior a hoy");
                   }
             
            }
        else{
            JOptionPane.showMessageDialog(this, "La fecha 'Desde' no puede ser posterior a 'Hasta'");
            }
       }
       else{
           this.lblMsg.setText("Debe ingresar un período");
       }
    }//GEN-LAST:event_btnListarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int[] seleccionados = tablaLic.getSelectedRows();
        
        if(marca.getProcesado()==null){
        try {
           this.internalAjustes=InternalAjustesLicencia.instancia(pers,trip);
           frmPrin prin=frmPrin.instancia();
        if (!internalAjustes.isVisible()) {
            prin.getDesktop().add(internalAjustes);
            internalAjustes.setLocation((prin.getDesktop().getWidth()/2)-(internalAjustes.getWidth()/2),(prin.getDesktop().getHeight()/2) - internalAjustes.getHeight()/2);
            internalAjustes.getTxtCodFunc().setText(this.txtCod.getText());
            internalAjustes.setVisible(true);
            internalAjustes.setFechaAjuste(this.marca.getFechaUso());
            internalAjustes.setMarca(marca);
            internalAjustes.getBtnBuscar().doClick();
            internalAjustes.repaint();
            internalAjustes.revalidate();
            
        }
        else{
            internalAjustes.requestFocus();
            try {
                internalAjustes.setSelected(true);
                internalAjustes.getTxtCodFunc().setText(this.txtCod.getText());
                internalAjustes.setFechaAjuste(this.marca.getFechaUso());
                internalAjustes.getBtnBuscar().doClick();
                internalAjustes.repaint();
                internalAjustes.revalidate();

            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
           
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       // this.pnlContenido.add(pnlAjustes);
        this.repaint();
        this.revalidate();
        }
        else{
         JOptionPane.showMessageDialog(this, "Esta marca no se puede editar");
     }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
           this.internalMovLic=InternalListadoMovLic.instancia();
           frmPrin prin=frmPrin.instancia();
        if (!internalMovLic.isVisible()) {
            prin.getDesktop().add(internalMovLic);
            internalMovLic.setLocation((prin.getDesktop().getWidth()/2)-(internalMovLic.getWidth()/2),(prin.getDesktop().getHeight()/2) - internalMovLic.getHeight()/2);
            internalMovLic.getTxtCod().setText(this.txtCod.getText());
            internalMovLic.setVisible(true);
            internalMovLic.getBtnListar().doClick();
            internalMovLic.repaint();
            internalMovLic.revalidate();
            
        }
        else{
            internalMovLic.requestFocus();
            try {
                internalMovLic.setSelected(true);
                internalMovLic.getTxtCod().setText(this.txtCod.getText());
                internalMovLic.getBtnListar().doClick();
                internalMovLic.repaint();
                internalMovLic.revalidate();

            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
           
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       // this.pnlContenido.add(pnlAjustes);
        this.repaint();
        this.revalidate();
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        int[] seleccionados = tablaLic.getSelectedRows();
        int total=0;
        if(seleccionados.length>0){
            for(int i=0;i<seleccionados.length;i++){
                try {
                    this.marca=this.cargarMovimientoMultiple(seleccionados[i]);
                          if(marca.getSupervisado()==0){
                                Date date=new Date();
                                this.marca.setSupervisado(1);
                                this.marca.setFecha(date);
                                try {
                                    total+=this.trip.actualizaMarca(marca,null);
                                        
                                    
                                } catch (SQLException ex) {
                                    Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                }
                                else{
                                 JOptionPane.showMessageDialog(this, "La marca del día "+this.formateo(marca.getFechaUso())+", "+marca.getHoraUso()+" no se puede editar");
                             }
                } catch (ParseException ex) {
                    Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(total>0){
                this.btnListar.doClick();
            }
            
        }
  
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
       if(marca.getProcesado()==null){
        try {
           if(this.marca!=null){
                this.internalSupervisar=InternalSupervisar.instancia(trip,this);
                frmPrin prin=frmPrin.instancia();
             if (!internalSupervisar.isVisible()) {
                 prin.getDesktop().add(internalSupervisar);
                 internalSupervisar.setLocation((prin.getDesktop().getWidth()/2)-(internalSupervisar.getWidth()/2),(prin.getDesktop().getHeight()/2) - internalSupervisar.getHeight()/2);
                 internalSupervisar.setVisible(true);
                 internalSupervisar.setMarca(marca);
                 internalSupervisar.setNombre(this.lblNombre.getText());
                 internalSupervisar.getBtnInvisible().doClick();
                 internalSupervisar.repaint();
                 internalSupervisar.revalidate();

             }
             else{
                 internalSupervisar.requestFocus();
                 try {
                        internalSupervisar.setSelected(true);
                        internalSupervisar.setVisible(true);
                        internalSupervisar.setMarca(marca);
                        internalSupervisar.setNombre(this.lblNombre.getText());
                        internalSupervisar.getBtnInvisible().doClick();
                        internalSupervisar.repaint();
                        internalSupervisar.revalidate();

                 } catch (PropertyVetoException ex) {
                     //lblMensaje.setText(ex.getMessage());
                 }
             }
       }   
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       // this.pnlContenido.add(pnlAjustes);
        this.repaint();
        this.revalidate();
       }
       else{
         JOptionPane.showMessageDialog(this, "Esta marca no se puede editar");
     }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jPanel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel1FocusGained
        this.btnListar.doClick();
    }//GEN-LAST:event_jPanel1FocusGained

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnListar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListar1ActionPerformed
       Date desde=this.txtFechaDesde.getDate();
       Date hasta=this.txtFechahasta.getDate();
       ArrayList<Marca> marcas=null;
       ArrayList<Integer> total;
       Integer sin=null; 
        if(desde!=null && hasta!=null){
       try {
             sin=this.trip.sinSupervisar(desde, hasta);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(sin==0){
            if(this.cod.bloqueoLiq()){
               
                    try {
                        if(this.abreInput().equals("1234")){
                            total=this.trip.procesar(desde,hasta);
                            this.LimpiarTabla();
                            this.lblMsg.setText("");
                            this.lblNombre.setText("");   
                            this.lblHorarios.setText("");
                            JOptionPane.showMessageDialog(this, "Proceso completo! Se ingresaron "+total.get(0)+" movimientos. Se procesaron " +total.get(1)+" lineas.");
                            }
                        else{
                            JOptionPane.showMessageDialog(this, "Clave incorrecta");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
            }
            else{
               JOptionPane.showMessageDialog(this, "Ya no tiene permitido procesar las marcas"); 
            }
        }
            else{
                JOptionPane.showMessageDialog(this, "Aún hay "+sin+" sin supervisar."); 
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
           JOptionPane.showMessageDialog(this, "Ingrese un período para procesar");
        }
        
    }//GEN-LAST:event_btnListar1ActionPerformed

    private void txtCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFocusGained
       this.txtCod.selectAll();
    }//GEN-LAST:event_txtCodFocusGained

    private void buttonIcon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIcon2ActionPerformed
        try {
            listadosMarcas=InternalListadosMarcas.instancia(trip,pers);
            frmPrin prin=frmPrin.instancia();
             if (!listadosMarcas.isVisible()) {
                 prin.getDesktop().add(listadosMarcas);
                 listadosMarcas.setLocation((prin.getDesktop().getWidth()/2)-(listadosMarcas.getWidth()/2),(prin.getDesktop().getHeight()/2) - listadosMarcas.getHeight()/2);
                 listadosMarcas.setVisible(true);
                 listadosMarcas.repaint();
                 listadosMarcas.revalidate();

             }
             else{
                 listadosMarcas.requestFocus();
                 try {
                        listadosMarcas.setSelected(true);
                        listadosMarcas.setVisible(true);
                        listadosMarcas.repaint();
                        listadosMarcas.revalidate();

                 } catch (PropertyVetoException ex) {
                     //lblMensaje.setText(ex.getMessage());
                 }
             }
            
      
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //            if(marcas!=null){
//                if(this.txtFechaDesde!=null&&this.txtFechahasta!=null){
//                    try {
//                        this.procesarExcel();
//                    } catch (IOException ex) {
//                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (ClassNotFoundException ex) {
//                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                else{
//                   JOptionPane.showMessageDialog(this, "Seleccione un período de fechas");  
//                }
//            }
//            else{
//                JOptionPane.showMessageDialog(this, "No hay ninguna tabla cargada para exportar");
//                
//            }
//   
    }//GEN-LAST:event_buttonIcon2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
       aux=null;
       aux=new ArrayList<>();
        int[] seleccionados = tablaLic.getSelectedRows();
        if(seleccionados.length>0){
            for(int i=0;i<seleccionados.length;i++){
                try {
                    this.marca=this.cargarMovimientoMultiple(seleccionados[i]);
                    if(marca.getProcesado()==null){
                        aux.add(marca);
                    }
                    
                } catch (ParseException ex) {
                    Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
     frmPrin prin=null;
            try {
                prin = frmPrin.instancia();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                marcaCod=InternalMarcaCodigo.instancia(trip,cod,this);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
            }
     if (!marcaCod.isVisible()) {
                 prin.getDesktop().add(marcaCod);
                 marcaCod.setLocation((prin.getDesktop().getWidth()/2)-(marcaCod.getWidth()/2),(prin.getDesktop().getHeight()/8) - marcaCod.getHeight()/8);
                 marcaCod.setVisible(true);
                 marcaCod.setMarca(null);
                 marcaCod.setMarcas(aux);
                 if(aux.size()>1){
                 marcaCod.getLblNombres().setText("Ingreso de código a múltiples marcas");
                 marcaCod.getLblMarca().setText("");
                 }
                 else if(aux.size()==1){
                     
                     try {
                         marcaCod.getLblNombres().setText(this.pers.funcParcial(String.valueOf(aux.get(0).getFunCod())).getNomCompleto());
                     } catch (ClassNotFoundException ex) {
                         Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (SQLException ex) {
                         Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 marcaCod.getLblMarca().setText(aux.get(0).toString());
                 }
                 marcaCod.repaint();
                 marcaCod.revalidate();

             }
             else{
                 marcaCod.requestFocus();
                 try {
                        marcaCod.setSelected(true);
                        marcaCod.setVisible(true);
                        marcaCod.setMarca(null);
                        marcaCod.setMarcas(aux);
                       if(aux.size()>1){
                        marcaCod.getLblNombres().setText("Ingreso de código a múltiples marcas");
                        marcaCod.getLblMarca().setText("");
                        }
                        else if(aux.size()==1){

                            try {
                                marcaCod.getLblNombres().setText(this.pers.funcParcial(String.valueOf(aux.get(0).getFunCod())).getNomCompleto());
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        marcaCod.getLblMarca().setText(aux.get(0).toString());
                        }
                               marcaCod.repaint();
                               marcaCod.revalidate();

                        } catch (PropertyVetoException ex) {
                            //lblMensaje.setText(ex.getMessage());
                        }
                    }
     }
    
            
        
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    //Agregar dia domingo
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        if(feMa!=null){
              try {
           
                this.internalAgregar=InternalAgregarDia.instancia(trip,feMa);
                frmPrin prin=frmPrin.instancia();
             if (!internalAgregar.isVisible()) {
                 prin.getDesktop().add(internalAgregar);
                 internalAgregar.setLocation((prin.getDesktop().getWidth()/2)-(internalAgregar.getWidth()/2),(prin.getDesktop().getHeight()/2) - internalAgregar.getHeight()/2);
                 internalAgregar.setVisible(true);
                 internalAgregar.repaint();
                 internalAgregar.revalidate();

             }
             else{
                 internalAgregar.requestFocus();
                 try {
                        internalAgregar.setSelected(true);
                        internalAgregar.setVisible(true);
                        internalAgregar.repaint();
                        internalAgregar.revalidate();

                 } catch (PropertyVetoException ex) {
                     //lblMensaje.setText(ex.getMessage());
                 }
             }
          
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private String abreInput(){
        String ret="";
     pf=new JPasswordField();
     JOptionPane pane = new JOptionPane(pf,JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
     JDialog dialog = pane.createDialog("Ingrese la clave");
     
       
     dialog.addComponentListener(new ComponentListener(){
        String ret=null;
        @Override
        public void componentShown(ComponentEvent e) {
            pf.requestFocusInWindow();
                       
        }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
            
        });
    dialog.setVisible(true);
    ret=String.valueOf(pf.getPassword());
    return ret;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnListar;
    private org.edisoncor.gui.button.ButtonIcon btnListar1;
    private org.edisoncor.gui.button.ButtonIcon buttonIcon1;
    private org.edisoncor.gui.button.ButtonIcon buttonIcon2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox jSup;
    private javax.swing.JLabel lblHorarios;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tablaCodes;
    private javax.swing.JTable tablaLic;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    private com.toedter.calendar.JDateChooser txtFechaDesde;
    private com.toedter.calendar.JDateChooser txtFechahasta;
    // End of variables declaration//GEN-END:variables

    private void cargarTabla(ArrayList<Marca> marcas) throws ClassNotFoundException, SQLException {
        DefaultTableModel modelo = (DefaultTableModel)tablaLic.getModel();
        
        Object[] filas=new Object[modelo.getColumnCount()];
        
      
                                                                
                    for(Marca m:marcas){    
                            String fecha=this.obtenerFecha(m.getMarcaFecha());
                            filas[0]=this.obtenerDia(m.getMarcaFecha());
                            filas[1]=fecha;
                            filas[2]=this.obtenerHora(m.getMarcaFecha());
                            filas[3]=m.getIncongruencia();
                            if(m.getSupervisado()==0){
                            filas[4]="NO";
                            }
                            else{
                            filas[4]="SI"; 
                            }
                            filas[5]=m.getResponsable();
                            filas[6]=this.formateo(m.getFecha());
                            filas[7]=m.getTipo();
                            filas[8]=m.getId();
                            filas[9]=m.getFunCod();
                            ArrayList<Integer> codigos=this.cod.obtenerCodigos(m.getFunCod(),m.getId());
                            if(codigos.size()>0){
                            filas[10]=this.formateaCodigo(codigos,fecha);
                            }
                            else{
                            filas[10]=null;    
                            }
                            
                            filas[11]=this.formateo(m.getProcesado());
                            filas[12]=m.getMarcaFecha();
                           
                                    
                            RenderMarca rr=new RenderMarca(7,4,3);
                            tablaLic.setDefaultRenderer(Object.class, rr);
                            modelo.addRow(filas);
                           
                            
                    }
               
        this.armaTablaCodigos();
        this.resizeColumnWidth(tablaLic);
        JTableHeader th; 
        th = tablaLic.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tablaLic.setColumnSelectionAllowed(false);
        tablaLic.setRowSelectionAllowed(true);
        
    }
    
    private String formateo(Date hoy){
      String retorno="";
      if(hoy!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
         retorno=formateador.format(hoy);
      }
      return retorno;
     }  
    
    public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 160; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +1 , width);
        }
        if(width > 100)
            width=150;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}    

    private String obtenerFecha(Timestamp marcaFecha) {
      Date date = new Date(marcaFecha.getTime());
        return this.formateo(date);
    }

    private String obtenerHora(Timestamp marcaFecha) {
        String retorno="";
      if(marcaFecha!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
         retorno=formateador.format(marcaFecha);
      }
      
      return retorno;
    }
    
    private Date stringADate(String s) throws ParseException{
    Date date=null;
        if(s!=""){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = formatter.parse(s);
        }
    return date;
    }
        
     private void LimpiarTabla() {
        
        DefaultTableModel modelo=(DefaultTableModel) tablaLic.getModel();
        DefaultTableModel model=(DefaultTableModel) tablaCodes.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaLic.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
        for (int i = 0; i < tablaCodes.getRowCount(); i++) {
            model.removeRow(i);
            i-=1;
        }
    }

    private String obtenerDia(Timestamp marcaFecha) {
        String retorno="";
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(marcaFecha);
     
        switch(cal.get(Calendar.DAY_OF_WEEK)){
            case 1:
                retorno="Domingo";
            break;
            case 2: 
                retorno="Lunes";   
            break;
            case 3:
                retorno="Martes";
            break;
            case 4: 
                retorno="Miercoles";   
            break;
            case 5:
                retorno="Jueves";
            break;
            case 6: 
                retorno="Viernes";   
            break;
            case 7: 
                retorno="Sábado";   
            break;
        }
        
        return retorno;
        
    }

    private String formateaCodigo(ArrayList<Integer> codigos,String fecha) {
        String retorno=null;
        if(codigos.size()>0){
            retorno="";
            for(int i=0;i<codigos.size();i++){
                retorno+=codigos.get(i).toString()+", ";
                for(int a=0;a<codes.size();a++){
                    if(codigos.get(i).equals(codes.get(a).getCod())){
                        codes.get(a).getFecha().add(fecha);
                    }
                }
            }
        }
        if(retorno!=null){
        retorno=retorno.substring(0, retorno.length()-2);
        }
        return retorno;
    }
    
  
    private String formateaCodigos(ArrayList<Integer> codigos) {
        String retorno=null;
        if(codigos.size()>0){
            retorno="";
            for(int i=0;i<codigos.size();i++){
                retorno+=codigos.get(i).toString()+", ";
            }
        }
        if(retorno!=null){
        retorno=retorno.substring(0, retorno.length()-2);
        }
        return retorno;
    }

    
    public void procesarExcel() throws IOException, ClassNotFoundException, SQLException{
    
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
                "Día",//0
                "Fecha",//1
                "Marca",//2
                "Diferencia",//3
                "Supervisado",//4
                "Responsable",//5
                "Fecha de Actualización",//6
                "Tipo",//7
                "Num Funcionario",//8
                "Codigos",//9
                "Procesado",//10
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
                          
               Row f=hoja.createRow(0);
               Cell cel = f.createCell(0);
               String nombre=this.lblNombre.getText();
               if(!nombre.equals("")){
               cel.setCellValue("Reporte de marcas del perídodo del "+this.formateo(this.txtFechaDesde.getDate())+" al "+this.formateo(this.txtFechahasta.getDate())+" de "+nombre);
               }
               else{
               cel.setCellValue("Reporte de marcas del perídodo del "+this.formateo(this.txtFechaDesde.getDate())+" al "+this.formateo(this.txtFechahasta.getDate())+" de todos");    
               }
              for(int i=2;i<=marcas.size()+1;i++){
                 
                  Row fila = hoja.createRow(i);
                  
                  
                  for(int c=0;c<headers.length;c++){
                    Cell celda = fila.createCell(c);
                 
                    hoja.setColumnWidth(c, 5000);
                    
                        if(i==0){
                            celda.setCellValue(headers[c]);

                        }
                                              
                            else{
                            Marca m=marcas.get(i-2);
                                    switch(c)
                                    {                                       
                                        case 0:
                                            String dia=this.obtenerDia(m.getMarcaFecha());
                                            celda.setCellValue(dia);
                                            break;
                                        case 1:
                                            String fecha=this.obtenerFecha(m.getMarcaFecha());
                                            celda.setCellValue(fecha);
                                            break;
                                        case 2:
                                            String Hora=this.obtenerHora(m.getMarcaFecha());
                                            celda.setCellValue(Hora);
                                            break;
                                        case 3:
                                            celda.setCellValue(m.getIncongruencia());
                                            break;   
                                        case 4:
                                            if(m.getSupervisado()==0){
                                            celda.setCellValue("NO");
                                            }
                                            else{
                                             celda.setCellValue("SI");
                                            }
                                            break;
                                        case 5:
                                            celda.setCellValue(m.getResponsable());
                                            break;
                                        case 6:
                                            celda.setCellValue(this.formateo(m.getFecha()));
                                            break; 
                                        case 7:
                                            celda.setCellValue(m.getTipo());
                                            break; 
                                        case 8:
                                            celda.setCellValue(m.getId());
                                            break; 
                                        case 9:
                                            celda.setCellValue(m.getFunCod());
                                            break; 
                                        case 10:
                                            ArrayList<Integer> codigos=this.cod.obtenerCodigos(m.getFunCod(),m.getId());
                                            if(codigos.size()>0){
                                             celda.setCellValue(this.formateaCodigos(codigos));
                                             }
                                             else{
                                             celda.setCellValue("");    
                                             }
                                             break; 
                                        case 11:
                                            celda.setCellValue(this.formateo(m.getProcesado()));
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

    private void armaTablaCodigos() {
      if(codes.size()>0){
       DefaultTableModel modelo = (DefaultTableModel)tablaCodes.getModel();
       Object[] filas=new Object[modelo.getColumnCount()];
       String aux="";
                          for(Codigo m:codes){    
                            
                            filas[0]=m.getCod();
                            filas[1]=m.getValor();
                            for(String s:m.getFecha()){
                                aux+=s+", ";
                            }
                            aux=aux.substring(0, aux.length()-2);
                            filas[2]=aux;
                            modelo.addRow(filas);
                            aux="";
                   }
                          
      this.resizeColumnWidth(tablaCodes);
        JTableHeader th; 
        th = tablaCodes.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tablaCodes.setColumnSelectionAllowed(false);
        tablaCodes.setRowSelectionAllowed(true);
    } 
    }
}


//SELECT distinct(Codigo),sum(cantidad) as cant FROM ADMIN."PERS_INGRESOS_MARCAS"  where codfunc in(select codFunc from pers_Marcas where Marca>='01/10/2017' and marca<='3/10/2017') and id in (select id from pers_Marcas where Marca>='01/10/2017' and marca<='3/10/2017') group by codigo