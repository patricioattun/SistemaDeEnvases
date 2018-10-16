
package Presentacion.Declaraciones;

import Dominio.Declaracion;
import Dominio.Funcionario;
import Dominio.Marca;
import Dominio.PersonasACargo;
import Dominio.Relacion;
import Logica.LogFuncionario;
import Logica.logPdf;
import Persistencia.BDExcepcion;

import Presentacion.Liquidaciones.InternalListadoFuncFiltro;
import Presentacion.Mantenimiento.InternalModFunc;
import Presentacion.frmPrin;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.edisoncor.gui.button.ButtonIcon;
import org.edisoncor.gui.textField.TextFieldRound;


public class InternalMantenimientoDecla extends javax.swing.JInternalFrame {

  private InternalListadoFuncFiltro listadoFunc;
  private LogFuncionario log;
  private static InternalMantenimientoDecla instancia=null;
  private Funcionario f;
  private String mensaje;
  private Declaracion d;
  private PersonasACargo pe;
  private boolean cargado=false;
  private DefaultTableModel tmMov=null;
  private ArrayList<PersonasACargo> lista = new ArrayList<>();
    public InternalMantenimientoDecla() throws ClassNotFoundException, SQLException {
        initComponents();
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        log=new LogFuncionario();
        this.limpiarTodo();
        this.cargaCombos(); 
          tmMov = (DefaultTableModel) tabla.getModel();
            tabla.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    
                        cargaCodigo(e);
                        
                     
                 }
                else if(e.getClickCount()==1){
                   
                        cargarMovimiento(e);
                    
                }
         }
    });
 }
    
    
   private void cargaCodigo(MouseEvent e) {
       this.limpiarRubroDos();  
       if(pe!=null){
           cargado=true;
           this.comboTipoDocCargo.setSelectedItem(pe.getTipoDoc());
           this.txtDocCargo.setText(pe.getNroDoc());
           this.comboPaisCargo.setSelectedItem(pe.getPais());
           this.txtFechaNacCargo.setDate(pe.getFecha_nac());
           this.comboNacionalCargo.setSelectedItem(pe.getNacionalidad());
           if(pe.getSexo()=='M'){
           this.comboSexoCargo.setSelectedItem("MASCULINO");
           }
           else{
           this.comboSexoCargo.setSelectedItem("FEMENINO");    
           }
           switch (pe.getRelacion()) {
               case 'H':
                   this.comboRelacionCargo.setSelectedItem("HIJO");
                   break;
               case 'T':
                   this.comboRelacionCargo.setSelectedItem("TUTELA");
                   break;
               case 'C':        
                   this.comboRelacionCargo.setSelectedItem("CURATELA");
                   break;
               default:
                   break;
           }
           if(pe.getSistSalud().equals("PRI")){
               this.comboSaludCargo.setSelectedItem("PRIVADA");
           }
           else{
               this.comboSaludCargo.setSelectedItem("PUBLICA");
           }
           
           this.txtAtribCargo.setValue(pe.getPjedist());
           if(pe.getDiscapacidad() == 0){
           this.comboDiscapCargo.setSelectedItem("NO");
           }
           else{
           this.comboDiscapCargo.setSelectedItem("SI");
           }
           this.txtApellidoUnoCargo.setText(pe.getApellidoUno());
           this.txtApellidoDosCargo.setText(pe.getApellidoDos());
           this.txtNombreUnoCargo.setText(pe.getNombreUno());
           this.txtNombreDosCargo.setText(pe.getNombreDos());
       }
   }
    private void cargarMovimiento(MouseEvent e) {
      this.limpiarRubroDos();  
      cargado=false;
      pe=null;
      try {
          Integer m=this.tabla.rowAtPoint(e.getPoint());
          this.pe=new PersonasACargo();
          Relacion r = new Relacion();
          pe.setCodFunc(f.getCodFunc());
          pe.setOrdinal(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,0))));
          pe.setTipoDoc(String.valueOf(tmMov.getValueAt(m,1)).trim());
          pe.setNroDoc(String.valueOf(tmMov.getValueAt(m, 2)));
          pe.setPais(String.valueOf(tmMov.getValueAt(m,3)));
          pe.setFecha_nac(this.stringADate(String.valueOf(tmMov.getValueAt(m,4))));
          pe.setApellidoUno(String.valueOf(tmMov.getValueAt(m,5)));
          pe.setApellidoDos(String.valueOf(tmMov.getValueAt(m,6)));
          pe.setNombreUno(String.valueOf(tmMov.getValueAt(m,7)));
          pe.setNombreDos(String.valueOf(tmMov.getValueAt(m,8)));
          pe.setNacionalidad(String.valueOf(tmMov.getValueAt(m,9)));
          pe.setSexo(String.valueOf(tmMov.getValueAt(m,10)).charAt(0));
          pe.setRelacion(String.valueOf(tmMov.getValueAt(m,11)).charAt(0));
          pe.setSistSalud(String.valueOf(tmMov.getValueAt(m,12)));
          pe.setPjedist(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,13))));
          if(String.valueOf(tmMov.getValueAt(m,14)).equals("NO")){
              pe.setDiscapacidad(0);
          }
          else{
              pe.setDiscapacidad(1);
          }
          
      } catch (ParseException ex) {
          Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
      }
     }

    public ButtonIcon getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(ButtonIcon btnBuscar) {
        this.btnBuscar = btnBuscar;
    }
    
    
    
     private Date stringADate(String s) throws ParseException{
    Date date=null;
        if(s!=""){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = formatter.parse(s);
        }
    return date;
    }
         public static InternalMantenimientoDecla instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalMantenimientoDecla();
         }
         return instancia;
      
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
        jPanel1 = new javax.swing.JPanel();
        txtNumFunc = new org.edisoncor.gui.textField.TextFieldRound();
        btnBuscar = new org.edisoncor.gui.button.ButtonIcon();
        jLabel2 = new javax.swing.JLabel();
        comboTipoDocFunc = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        txtDocFunc = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtVigenciaMes = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel7 = new javax.swing.JLabel();
        txtVigenciaAño = new org.edisoncor.gui.textField.TextFieldRound();
        comboPaisFunc = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        comboTipoDocCargo = new javax.swing.JComboBox();
        txtDocCargo = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel8 = new javax.swing.JLabel();
        comboPaisCargo = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        comboNacionalCargo = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        comboSexoCargo = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        comboRelacionCargo = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        comboSaludCargo = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        txtAtribCargo = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        comboDiscapCargo = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        txtNombreUnoCargo = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreDosCargo = new org.edisoncor.gui.textField.TextFieldRound();
        txtApellidoUnoCargo = new org.edisoncor.gui.textField.TextFieldRound();
        txtApellidoDosCargo = new org.edisoncor.gui.textField.TextFieldRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnAgregar = new org.edisoncor.gui.button.ButtonIcon();
        lblMsj = new javax.swing.JLabel();
        txtFechaNacCargo = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        txtCategoria = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel21 = new javax.swing.JLabel();
        txtCajaPro = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel22 = new javax.swing.JLabel();
        comboFondo = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        comboAdicionalFondo = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        comboMinimo = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        txtFechaRecep = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        comboNucleo = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        comboTipoDocCony = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtDocCony = new org.edisoncor.gui.textField.TextFieldRound();
        comboPaisCony = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        txtFechaNacCony = new com.toedter.calendar.JDateChooser();
        jLabel28 = new javax.swing.JLabel();
        comboNacionalCony = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        comboSexoCony = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        txtNombreUnoCony = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel31 = new javax.swing.JLabel();
        txtNombreDosCony = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel32 = new javax.swing.JLabel();
        txtApellidoUnoCony = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel33 = new javax.swing.JLabel();
        txtApellidoDosCony = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel34 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnImprimir = new org.edisoncor.gui.button.ButtonIcon();
        lblError = new javax.swing.JLabel();

        jMenuItem1.setText("Eliminar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setIconifiable(true);
        setPreferredSize(new java.awt.Dimension(940, 700));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)), "Rubro 1 - Identificación", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

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

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel2.setText("Nro. Funcionario ");

        comboTipoDocFunc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboTipoDocFuncMouseReleased(evt);
            }
        });
        comboTipoDocFunc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboTipoDocFuncKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel3.setText("Tipo Documento");

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

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel4.setText("Nombre de Funcionario");

        txtDocFunc.setBackground(new java.awt.Color(102, 153, 255));
        txtDocFunc.setForeground(new java.awt.Color(255, 255, 255));
        txtDocFunc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDocFunc.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDocFunc.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtDocFunc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDocFuncFocusLost(evt);
            }
        });
        txtDocFunc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDocFuncKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel5.setText("Nro. Documento ");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel6.setText("País");

        txtVigenciaMes.setBackground(new java.awt.Color(102, 153, 255));
        txtVigenciaMes.setForeground(new java.awt.Color(255, 255, 255));
        txtVigenciaMes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVigenciaMes.setCaretColor(new java.awt.Color(255, 255, 255));
        txtVigenciaMes.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtVigenciaMes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVigenciaMesFocusLost(evt);
            }
        });
        txtVigenciaMes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVigenciaMesKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel7.setText("Vigencia (MM-AAAA)");

        txtVigenciaAño.setBackground(new java.awt.Color(102, 153, 255));
        txtVigenciaAño.setForeground(new java.awt.Color(255, 255, 255));
        txtVigenciaAño.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVigenciaAño.setCaretColor(new java.awt.Color(255, 255, 255));
        txtVigenciaAño.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtVigenciaAño.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVigenciaAñoFocusLost(evt);
            }
        });
        txtVigenciaAño.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVigenciaAñoKeyTyped(evt);
            }
        });

        comboPaisFunc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboPaisFuncMouseReleased(evt);
            }
        });
        comboPaisFunc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboPaisFuncKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNumFunc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboTipoDocFunc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtDocFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboPaisFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtVigenciaMes, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtVigenciaAño, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVigenciaMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVigenciaAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDocFunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboPaisFunc)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboTipoDocFunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)), "Rubro 2 - Atención Médica de Personas a Cargo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel11.setText("Tipo Documento");

        comboTipoDocCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboTipoDocCargoMouseReleased(evt);
            }
        });
        comboTipoDocCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboTipoDocCargoKeyTyped(evt);
            }
        });

        txtDocCargo.setBackground(new java.awt.Color(102, 153, 255));
        txtDocCargo.setForeground(new java.awt.Color(255, 255, 255));
        txtDocCargo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDocCargo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDocCargo.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtDocCargo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDocCargoFocusLost(evt);
            }
        });
        txtDocCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDocCargoKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel8.setText("Nro. Documento ");

        comboPaisCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboPaisCargoMouseReleased(evt);
            }
        });
        comboPaisCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboPaisCargoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel12.setText("País");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel13.setText("Fecha de Nacimiento");

        comboNacionalCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboNacionalCargoMouseReleased(evt);
            }
        });
        comboNacionalCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboNacionalCargoKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel14.setText("Nacionalidad");

        comboSexoCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMENINO" }));
        comboSexoCargo.setSelectedIndex(-1);
        comboSexoCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboSexoCargoMouseReleased(evt);
            }
        });
        comboSexoCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboSexoCargoKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel15.setText("Sexo");

        comboRelacionCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "HIJO", "TUTELA", "CURATELA" }));
        comboRelacionCargo.setSelectedIndex(-1);
        comboRelacionCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboRelacionCargoMouseReleased(evt);
            }
        });
        comboRelacionCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboRelacionCargoKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel16.setText("Relación");

        comboSaludCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PUBLICA", "PRIVADA" }));
        comboSaludCargo.setSelectedIndex(-1);
        comboSaludCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboSaludCargoMouseReleased(evt);
            }
        });
        comboSaludCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboSaludCargoKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel17.setText("Sistema de Salud");

        txtAtribCargo.setModel(new javax.swing.SpinnerNumberModel(50, 50, 100, 50));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel18.setText("% Atrib.");

        comboDiscapCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));
        comboDiscapCargo.setSelectedIndex(-1);
        comboDiscapCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboDiscapCargoMouseReleased(evt);
            }
        });
        comboDiscapCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboDiscapCargoKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel19.setText("Discapacidad");

        txtNombreUnoCargo.setBackground(new java.awt.Color(102, 153, 255));
        txtNombreUnoCargo.setForeground(new java.awt.Color(255, 255, 255));
        txtNombreUnoCargo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNombreUnoCargo.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtNombreUnoCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreUnoCargoKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel9.setText("2do Apellido");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel10.setText("1er Apellido");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel20.setText("2do Nombre");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel1.setText("1er  Nombre ");

        txtNombreDosCargo.setBackground(new java.awt.Color(102, 153, 255));
        txtNombreDosCargo.setForeground(new java.awt.Color(255, 255, 255));
        txtNombreDosCargo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNombreDosCargo.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtNombreDosCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreDosCargoKeyTyped(evt);
            }
        });

        txtApellidoUnoCargo.setBackground(new java.awt.Color(102, 153, 255));
        txtApellidoUnoCargo.setForeground(new java.awt.Color(255, 255, 255));
        txtApellidoUnoCargo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtApellidoUnoCargo.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtApellidoUnoCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoUnoCargoKeyTyped(evt);
            }
        });

        txtApellidoDosCargo.setBackground(new java.awt.Color(102, 153, 255));
        txtApellidoDosCargo.setForeground(new java.awt.Color(255, 255, 255));
        txtApellidoDosCargo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtApellidoDosCargo.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtApellidoDosCargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoDosCargoKeyTyped(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nº", "Tipo Doc.", "Nº Doc.", "País", "Fecha Nac.", "1er Apellido", "2do Apellido", "1er Nombre", "2do Nombre", "Nacionalidad", "Sexo", "Relación", "Sist. Salud", "% Atrib.", "Discapacidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(4).setMinWidth(100);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(4).setMaxWidth(100);
            tabla.getColumnModel().getColumn(5).setMinWidth(100);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(5).setMaxWidth(100);
            tabla.getColumnModel().getColumn(6).setMinWidth(100);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(6).setMaxWidth(100);
            tabla.getColumnModel().getColumn(7).setMinWidth(100);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(7).setMaxWidth(100);
            tabla.getColumnModel().getColumn(8).setMinWidth(100);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(8).setMaxWidth(100);
            tabla.getColumnModel().getColumn(9).setMinWidth(100);
            tabla.getColumnModel().getColumn(9).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(9).setMaxWidth(100);
            tabla.getColumnModel().getColumn(14).setMinWidth(100);
            tabla.getColumnModel().getColumn(14).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(14).setMaxWidth(100);
        }

        btnAgregar.setBackground(new java.awt.Color(102, 153, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregarGris.png"))); // NOI18N
        btnAgregar.setText("buttonIcon1");
        btnAgregar.setToolTipText("Cargar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        txtFechaNacCargo.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboTipoDocCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtDocCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboPaisCargo, 0, 155, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaNacCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboNacionalCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSexoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboRelacionCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSaludCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAtribCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboDiscapCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreUnoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreDosCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellidoUnoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtApellidoDosCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDocCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboTipoDocCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFechaNacCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(comboPaisCargo)
                        .addComponent(comboNacionalCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboSexoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboRelacionCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboSaludCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAtribCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboDiscapCargo))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel20)
                            .addComponent(jLabel1)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreUnoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreDosCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellidoUnoCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellidoDosCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)), "Rubro 3 - Deducciones Profesionales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(920, 71));
        jPanel4.setLayout(null);

        txtCategoria.setBackground(new java.awt.Color(102, 153, 255));
        txtCategoria.setForeground(new java.awt.Color(255, 255, 255));
        txtCategoria.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCategoria.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCategoria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCategoriaFocusLost(evt);
            }
        });
        txtCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCategoriaKeyTyped(evt);
            }
        });
        jPanel4.add(txtCategoria);
        txtCategoria.setBounds(10, 30, 168, 23);

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel21.setText("Categoría");
        jPanel4.add(jLabel21);
        jLabel21.setBounds(10, 14, 47, 20);

        txtCajaPro.setBackground(new java.awt.Color(102, 153, 255));
        txtCajaPro.setForeground(new java.awt.Color(255, 255, 255));
        txtCajaPro.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCajaPro.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCajaPro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCajaProFocusLost(evt);
            }
        });
        txtCajaPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCajaProKeyTyped(evt);
            }
        });
        jPanel4.add(txtCajaPro);
        txtCajaPro.setBounds(260, 30, 168, 23);

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel22.setText("Reint. Aportes CJPPU");
        jPanel4.add(jLabel22);
        jLabel22.setBounds(260, 14, 106, 20);

        comboFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboFondoMouseReleased(evt);
            }
        });
        comboFondo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboFondoKeyTyped(evt);
            }
        });
        jPanel4.add(comboFondo);
        comboFondo.setBounds(500, 30, 146, 26);

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel23.setText("Fondo de Solidaridad");
        jPanel4.add(jLabel23);
        jLabel23.setBounds(500, 14, 110, 20);

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel24.setText("Adicional Fondo de Solidaridad");
        jPanel4.add(jLabel24);
        jLabel24.setBounds(720, 20, 170, 10);

        comboAdicionalFondo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));
        comboAdicionalFondo.setSelectedIndex(-1);
        comboAdicionalFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboAdicionalFondoMouseReleased(evt);
            }
        });
        comboAdicionalFondo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboAdicionalFondoKeyTyped(evt);
            }
        });
        jPanel4.add(comboAdicionalFondo);
        comboAdicionalFondo.setBounds(720, 30, 146, 26);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)), "R4 - Contrib. mas de un ingreso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel35.setText("Mínimo no Imponible");

        comboMinimo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));
        comboMinimo.setSelectedIndex(-1);
        comboMinimo.setToolTipText("");
        comboMinimo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboMinimoMouseReleased(evt);
            }
        });
        comboMinimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboMinimoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboMinimo)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)), "R7 - Const. de Recep", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel7.setToolTipText("");

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel36.setText("Fecha de Recepción");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtFechaRecep, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFechaRecep, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)), "Núcleo Familiar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel8.setToolTipText("");

        comboNucleo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "NO" }));
        comboNucleo.setSelectedIndex(-1);
        comboNucleo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboNucleoMouseReleased(evt);
            }
        });
        comboNucleo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboNucleoKeyTyped(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel37.setText("Núcleo Familiar");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboNucleo, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboNucleo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)), "Rubro 5 - Datos del Cónyuge", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(920, 112));

        comboTipoDocCony.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboTipoDocConyMouseReleased(evt);
            }
        });
        comboTipoDocCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboTipoDocConyKeyTyped(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel25.setText("Tipo Documento");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel26.setText("Nro. Documento ");

        txtDocCony.setBackground(new java.awt.Color(102, 153, 255));
        txtDocCony.setForeground(new java.awt.Color(255, 255, 255));
        txtDocCony.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDocCony.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDocCony.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtDocCony.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDocConyFocusLost(evt);
            }
        });
        txtDocCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDocConyKeyTyped(evt);
            }
        });

        comboPaisCony.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboPaisConyMouseReleased(evt);
            }
        });
        comboPaisCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboPaisConyKeyTyped(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel27.setText("País");

        txtFechaNacCony.setDateFormatString("dd/MM/yyyy");

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel28.setText("Fecha de Nacimiento");

        comboNacionalCony.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboNacionalConyMouseReleased(evt);
            }
        });
        comboNacionalCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboNacionalConyKeyTyped(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel29.setText("Nacionalidad");

        comboSexoCony.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MASCULINO", "FEMENINO" }));
        comboSexoCony.setSelectedIndex(-1);
        comboSexoCony.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboSexoConyMouseReleased(evt);
            }
        });
        comboSexoCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboSexoConyKeyTyped(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel30.setText("Sexo");

        txtNombreUnoCony.setBackground(new java.awt.Color(102, 153, 255));
        txtNombreUnoCony.setForeground(new java.awt.Color(255, 255, 255));
        txtNombreUnoCony.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNombreUnoCony.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtNombreUnoCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreUnoConyKeyTyped(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel31.setText("1er  Nombre ");

        txtNombreDosCony.setBackground(new java.awt.Color(102, 153, 255));
        txtNombreDosCony.setForeground(new java.awt.Color(255, 255, 255));
        txtNombreDosCony.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNombreDosCony.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtNombreDosCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreDosConyKeyTyped(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel32.setText("2do Nombre");

        txtApellidoUnoCony.setBackground(new java.awt.Color(102, 153, 255));
        txtApellidoUnoCony.setForeground(new java.awt.Color(255, 255, 255));
        txtApellidoUnoCony.setCaretColor(new java.awt.Color(255, 255, 255));
        txtApellidoUnoCony.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtApellidoUnoCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoUnoConyKeyTyped(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel33.setText("1er Apellido");

        txtApellidoDosCony.setBackground(new java.awt.Color(102, 153, 255));
        txtApellidoDosCony.setForeground(new java.awt.Color(255, 255, 255));
        txtApellidoDosCony.setCaretColor(new java.awt.Color(255, 255, 255));
        txtApellidoDosCony.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtApellidoDosCony.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoDosConyKeyTyped(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel34.setText("2do Apellido");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreUnoCony, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(75, 75, 75)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreDosCony, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(txtApellidoUnoCony, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addComponent(txtApellidoDosCony, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTipoDocCony, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(txtDocCony, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboPaisCony, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaNacCony, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboNacionalCony, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSexoCony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboTipoDocCony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDocCony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboPaisCony))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtFechaNacCony, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboNacionalCony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboSexoCony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreUnoCony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombreDosCony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtApellidoUnoCony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtApellidoDosCony, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );

        btnConfirmar.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnImprimir.setBackground(new java.awt.Color(102, 153, 255));
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/print.png"))); // NOI18N
        btnImprimir.setText("buttonIcon1");
        btnImprimir.setToolTipText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 899, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 899, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(btnConfirmar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConfirmar)
                            .addComponent(btnEliminar)
                            .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFuncKeyTyped
       this.txtNombre.setText("");
       this.limpiarTodo();
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(k==10){
            this.btnBuscar.doClick();
        }
        if(evt.getKeyChar()==43){
           
           
           try {
               listadoFunc=InternalListadoFuncFiltro.instancia(log,7);
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
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
           } catch (PropertyVetoException ex) {
               Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
           }

            
        }
    }//GEN-LAST:event_txtNumFuncKeyTyped

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
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
     this.limpiarTodo();
     String numFunc=this.txtNumFunc.getText();
       if(this.esNum(numFunc)){

            try {
                f = this.log.funcBasico(numFunc);
                if(f!=null){
                    this.txtNombre.setText(f.getNomCompletoApe());
                    this.habilitar();
                    d = this.log.cargoDeclaracion(f.getCodFunc());
                    if(d!=null){
                        this.cargoDeclaracion();
                    }else{
                       this.comboNucleo.setSelectedItem("NO");
       
                    }

                }
                else{
                    this.txtNumFunc.requestFocus();
                    this.mensaje = "El funcionario que busca no existe";
                    this.showMensaje();
                   
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalModFunc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalModFunc.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        else{
            this.txtNumFunc.requestFocus();
            this.mensaje = "Ingrese solo números";
            this.showMensaje();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void comboTipoDocFuncMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboTipoDocFuncMouseReleased

      
    }//GEN-LAST:event_comboTipoDocFuncMouseReleased

    private void comboTipoDocFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboTipoDocFuncKeyTyped
       
    }//GEN-LAST:event_comboTipoDocFuncKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtVigenciaMesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVigenciaMesKeyTyped
        this.mensaje="";
        this.showMensaje();
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(this.txtVigenciaMes.getText().length()==2){
            evt.consume();
        }
    }//GEN-LAST:event_txtVigenciaMesKeyTyped

    private void txtVigenciaAñoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVigenciaAñoKeyTyped
        this.mensaje="";
        this.showMensaje();
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(this.txtVigenciaAño.getText().length()>=4){
            evt.consume();
        }
    }//GEN-LAST:event_txtVigenciaAñoKeyTyped

    private void comboPaisFuncMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboPaisFuncMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPaisFuncMouseReleased

    private void comboPaisFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboPaisFuncKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPaisFuncKeyTyped

    private void comboNacionalCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboNacionalCargoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboNacionalCargoKeyTyped

    private void comboNacionalCargoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboNacionalCargoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboNacionalCargoMouseReleased

    private void comboPaisCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboPaisCargoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPaisCargoKeyTyped

    private void comboPaisCargoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboPaisCargoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPaisCargoMouseReleased

    private void txtDocCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocCargoKeyTyped
            this.mensaje="";
            this.showMensaje();   
       
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(this.txtDocCargo.getText().length()>=8){
            evt.consume();
        }

        if(k==10){
           
        }
    }//GEN-LAST:event_txtDocCargoKeyTyped

    private void comboTipoDocCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboTipoDocCargoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoDocCargoKeyTyped

    private void comboTipoDocCargoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboTipoDocCargoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoDocCargoMouseReleased

    private void comboSexoCargoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboSexoCargoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexoCargoMouseReleased

    private void comboSexoCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboSexoCargoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexoCargoKeyTyped

    private void comboRelacionCargoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboRelacionCargoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboRelacionCargoMouseReleased

    private void comboRelacionCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboRelacionCargoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboRelacionCargoKeyTyped

    private void comboSaludCargoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboSaludCargoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSaludCargoMouseReleased

    private void comboSaludCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboSaludCargoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSaludCargoKeyTyped

    private void comboDiscapCargoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboDiscapCargoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDiscapCargoMouseReleased

    private void comboDiscapCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboDiscapCargoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDiscapCargoKeyTyped

    private void txtNombreUnoCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreUnoCargoKeyTyped
        int k = (int) evt.getKeyChar();
        if(this.txtNombreUnoCargo.getText().length()==35|| k==48||k==49||k==50||k==51||k==52||k==53||k==54||k==55||k==56||k==57){
            evt.consume();
        }

        if(k==10){
            this.txtNombreDosCargo.requestFocus();
        }
     
    }//GEN-LAST:event_txtNombreUnoCargoKeyTyped

    private void txtNombreDosCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreDosCargoKeyTyped
       int k = (int) evt.getKeyChar();
        if(this.txtNombreDosCargo.getText().length()==35|| k==48||k==49||k==50||k==51||k==52||k==53||k==54||k==55||k==56||k==57){
            evt.consume();
        }

        if(k==10){
            this.txtApellidoUnoCargo.requestFocus();
        }
    }//GEN-LAST:event_txtNombreDosCargoKeyTyped

    private void txtApellidoUnoCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoUnoCargoKeyTyped
               int k = (int) evt.getKeyChar();
        if(this.txtApellidoUnoCargo.getText().length()==35|| k==48||k==49||k==50||k==51||k==52||k==53||k==54||k==55||k==56||k==57){
            evt.consume();
        }

        if(k==10){
            this.txtApellidoDosCargo.requestFocus();
        }
    }//GEN-LAST:event_txtApellidoUnoCargoKeyTyped

    private void txtApellidoDosCargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoDosCargoKeyTyped
      int k = (int) evt.getKeyChar();
        if(this.txtApellidoDosCargo.getText().length()==35|| k==48||k==49||k==50||k==51||k==52||k==53||k==54||k==55||k==56||k==57){
            evt.consume();
        }
     
    }//GEN-LAST:event_txtApellidoDosCargoKeyTyped

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        this.mensaje="";
        this.showMensajeCargo();
        String tipoDoc = (String) this.comboTipoDocCargo.getSelectedItem();
        String numDoc = this.txtDocCargo.getText();
        String pais = (String) this.comboPaisCargo.getSelectedItem();
        Date fecha = txtFechaNacCargo.getDate();
        String nacional = (String) this.comboNacionalCargo.getSelectedItem();
        Character sexo = ' ';
        if(this.comboSexoCargo.getSelectedItem()=="MASCULINO"){
            sexo='M';
        }
        else if(this.comboSexoCargo.getSelectedItem()=="FEMENINO"){
            sexo = 'F';
        }
        Character relacion = ' ';
        if(this.comboRelacionCargo.getSelectedItem()=="HIJO"){
            relacion ='H';
        }
        else if(this.comboRelacionCargo.getSelectedItem()=="TUTELA"){
            relacion = 'T';
        }
        else if(this.comboRelacionCargo.getSelectedItem()=="CURATELA"){
            relacion = 'C';
        }
        String salud = "";
        if(this.comboSaludCargo.getSelectedItem()=="PRIVADA"){
            salud="PRI";
        }
        else if(this.comboSaludCargo.getSelectedItem()=="PUBLICA"){
            salud="PUB";
        }
        Integer atrib = (Integer) this.txtAtribCargo.getValue();
        
        Integer discapacidad = null;
        if(this.comboDiscapCargo.getSelectedItem()=="NO"){
            discapacidad = 0;
        }
        else if(this.comboDiscapCargo.getSelectedItem()=="SI"){
            discapacidad = 1;
        }
        String nombreUno = this.txtNombreUnoCargo.getText();
        String nombreDos = this.txtNombreDosCargo.getText();
        String apellidoUno = this.txtApellidoUnoCargo.getText();
        String apellidoDos = this.txtApellidoDosCargo.getText();
        
        if(f!=null){
        if(tipoDoc!=null){
            if(this.esNum(numDoc)){
                if(pais!=null){
                    if(fecha!=null){
                        if(nacional!=null){
                            if(!"".equals(salud)){
                                if(sexo!=' '){
                                    if(relacion!=' '){
                                        if(atrib==50||atrib==100){
                                            if(discapacidad!=null){
                                                if(!nombreUno.equals("")){
                                                    if(!apellidoUno.equals("")){
                                                        if(cargado){
                                                            if(pe!=null){
                                                                if(tipoDoc.trim().equals("C.I.")){
                                                                    if(this.EsCIValida(numDoc.trim())){
                                                                        PersonasACargo p = new PersonasACargo();
                                                                        p.setOrdinal(pe.getOrdinal());
                                                                        p.setApellidoDos(apellidoDos);
                                                                        p.setApellidoUno(apellidoUno);
                                                                        p.setDiscapacidad(discapacidad);
                                                                        p.setFecha_nac(fecha);
                                                                        p.setNacionalidad(nacional);
                                                                        p.setNombreDos(nombreDos);
                                                                        p.setNombreUno(nombreUno);
                                                                        p.setNroDoc(numDoc);
                                                                        p.setPais(pais);
                                                                        p.setPjedist(atrib);
                                                                        p.setRelacion(relacion);
                                                                        p.setSexo(sexo);
                                                                        p.setSistSalud(salud);
                                                                        p.setTipoDoc(tipoDoc);
                                                                        this.verificaLista(p);
                                                                    }
                                                                    else{
                                                                        this.mensaje="Ingrese una cédula válida";
                                                                        this.showMensajeCargo();
                                                                    }
                                                                }else{
                                                                    PersonasACargo p = new PersonasACargo();
                                                                    p.setOrdinal(pe.getOrdinal());
                                                                    p.setApellidoDos(apellidoDos);
                                                                    p.setApellidoUno(apellidoUno);
                                                                    p.setDiscapacidad(discapacidad);
                                                                    p.setFecha_nac(fecha);
                                                                    p.setNacionalidad(nacional);
                                                                    p.setNombreDos(nombreDos);
                                                                    p.setNombreUno(nombreUno);
                                                                    p.setNroDoc(numDoc);
                                                                    p.setPais(pais);
                                                                    p.setPjedist(atrib);
                                                                    p.setRelacion(relacion);
                                                                    p.setSexo(sexo);
                                                                    p.setSistSalud(salud);
                                                                    p.setTipoDoc(tipoDoc);
                                                                    this.verificaLista(p);
                                                                }
                                                            }
                                                            else{
                                                               this.mensaje="Cargue una línea para editar";
                                                               this.showMensajeCargo();
                                                            }
                                                        }else{
                                                            if(tipoDoc.trim().equals("C.I.")){
                                                                    if(this.EsCIValida(numDoc.trim())){
                                                                        if(!this.verificaDocumentoExiste(numDoc)){
                                                                        PersonasACargo p = new PersonasACargo();
                                                                        p.setOrdinal(this.buscaUltimoOrdinal());
                                                                        p.setApellidoDos(apellidoDos);
                                                                        p.setApellidoUno(apellidoUno);
                                                                        p.setDiscapacidad(discapacidad);
                                                                        p.setFecha_nac(fecha);
                                                                        p.setNacionalidad(nacional);
                                                                        p.setNombreDos(nombreDos);
                                                                        p.setNombreUno(nombreUno);
                                                                        p.setNroDoc(numDoc);
                                                                        p.setPais(pais);
                                                                        p.setPjedist(atrib);
                                                                        p.setRelacion(relacion);
                                                                        p.setSexo(sexo);
                                                                        p.setSistSalud(salud);
                                                                        p.setTipoDoc(tipoDoc);
                                                                        this.verificaLista(p);
                                                                    }
                                                                }
                                                                    else{
                                                                         this.mensaje="Ingrese una cédula válida";
                                                                         this.showMensajeCargo();
                                                                    }
                                                            }
                                                            else{
                                                                    if(!this.verificaDocumentoExiste(numDoc)){
                                                                        PersonasACargo p = new PersonasACargo();
                                                                        p.setOrdinal(this.buscaUltimoOrdinal());
                                                                        p.setApellidoDos(apellidoDos);
                                                                        p.setApellidoUno(apellidoUno);
                                                                        p.setDiscapacidad(discapacidad);
                                                                        p.setFecha_nac(fecha);
                                                                        p.setNacionalidad(nacional);
                                                                        p.setNombreDos(nombreDos);
                                                                        p.setNombreUno(nombreUno);
                                                                        p.setNroDoc(numDoc);
                                                                        p.setPais(pais);
                                                                        p.setPjedist(atrib);
                                                                        p.setRelacion(relacion);
                                                                        p.setSexo(sexo);
                                                                        p.setSistSalud(salud);
                                                                        p.setTipoDoc(tipoDoc);
                                                                        this.verificaLista(p);
                                                                    }
                                                                    else{
                                                                       this.mensaje="Número de documento ya existe";
                                                                       this.showMensajeCargo(); 
                                                                    }
                                                            }
                                                        }
                                                    }
                                                    else{
                                                        this.mensaje="El 1er apellido es obligatorio";
                                                        this.showMensajeCargo();
                                                    }
                                                    
                                                }
                                                else{
                                                    this.mensaje="El 1er nombre es obligatorio";
                                                    this.showMensajeCargo();
                                                }
                                                
                                            }
                                            else{
                                                this.mensaje="Seleccione el valor para discapacidad";
                                                this.showMensajeCargo();
                                            }
                                            
                                        }
                                        else{
                                          this.mensaje="El porcentaje de atribución debe ser 50 o 100";
                                          this.showMensajeCargo();  
                                        }
                                    }
                                    else{
                                        this.mensaje="Debe seleccionar la relación";
                                        this.showMensajeCargo();
                                    }
                                }
                                else{
                                    this.mensaje="Debe seleccionar el sexo";
                                    this.showMensajeCargo();
                                }
                            }
                            else{
                                this.mensaje="Ingrese un sistema de salud";
                                this.showMensajeCargo();
                            }
                        }
                        else{
                            this.mensaje="Seleccione una nacionalidad";
                            this.showMensajeCargo();
                        }
                    }
                    else{
                        this.mensaje="Ingrese una fecha válida DD/MM/AAAA";
                        this.showMensajeCargo();
                    }
                }
                else{
                    this.mensaje="Seleccione un país";
                    this.showMensajeCargo();
                }
                
            }
            else{
            this.mensaje="Ingrese solo números en el documento";
            this.showMensajeCargo();
            }
            
        }
        else{
            this.mensaje="Debe seleccionar un tipo de Documento";
            this.showMensajeCargo();
        }
    }
    else{
    this.mensaje="Debe buscar el funcionario primero";
    this.showMensajeCargo();
    }
            
        
    
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCategoriaKeyTyped
        this.mensaje="";
        this.showMensaje();
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(this.txtCategoria.getText().length()>=2){
            evt.consume();
        }
             
    }//GEN-LAST:event_txtCategoriaKeyTyped

    private void txtCajaProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCajaProKeyTyped
        
        this.mensaje="";
        this.showMensaje();
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
     
    }//GEN-LAST:event_txtCajaProKeyTyped

    private void comboFondoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboFondoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFondoMouseReleased

    private void comboFondoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboFondoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFondoKeyTyped

    private void comboAdicionalFondoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboAdicionalFondoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboAdicionalFondoMouseReleased

    private void comboAdicionalFondoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboAdicionalFondoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboAdicionalFondoKeyTyped

    private void comboTipoDocConyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboTipoDocConyMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoDocConyMouseReleased

    private void comboTipoDocConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboTipoDocConyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoDocConyKeyTyped

    private void txtDocConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocConyKeyTyped
            this.mensaje="";
            this.showMensaje();   
      
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(this.txtDocCony.getText().length()>=8){
            evt.consume();
        }
    }//GEN-LAST:event_txtDocConyKeyTyped

    private void comboPaisConyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboPaisConyMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPaisConyMouseReleased

    private void comboPaisConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboPaisConyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPaisConyKeyTyped

    private void comboNacionalConyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboNacionalConyMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboNacionalConyMouseReleased

    private void comboNacionalConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboNacionalConyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboNacionalConyKeyTyped

    private void comboSexoConyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboSexoConyMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexoConyMouseReleased

    private void comboSexoConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboSexoConyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSexoConyKeyTyped

    private void txtNombreUnoConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreUnoConyKeyTyped
      int k = (int) evt.getKeyChar();
        if(this.txtNombreUnoCony.getText().length()==35|| k==48||k==49||k==50||k==51||k==52||k==53||k==54||k==55||k==56||k==57){
            evt.consume();
        }

        if(k==10){
            this.txtNombreDosCony.requestFocus();
        }
    }//GEN-LAST:event_txtNombreUnoConyKeyTyped

    private void txtNombreDosConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreDosConyKeyTyped
       int k = (int) evt.getKeyChar();
        if(this.txtNombreDosCony.getText().length()==35|| k==48||k==49||k==50||k==51||k==52||k==53||k==54||k==55||k==56||k==57){
            evt.consume();
        }

        if(k==10){
            this.txtApellidoUnoCony.requestFocus();
        }
    }//GEN-LAST:event_txtNombreDosConyKeyTyped

    private void txtApellidoUnoConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoUnoConyKeyTyped
       int k = (int) evt.getKeyChar();
        if(this.txtApellidoUnoCony.getText().length()==35|| k==48||k==49||k==50||k==51||k==52||k==53||k==54||k==55||k==56||k==57){
            evt.consume();
        }

        if(k==10){
            this.txtApellidoDosCony.requestFocus();
        }
    }//GEN-LAST:event_txtApellidoUnoConyKeyTyped

    private void txtApellidoDosConyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoDosConyKeyTyped
       int k = (int) evt.getKeyChar();
        if(this.txtApellidoDosCony.getText().length()==35|| k==48||k==49||k==50||k==51||k==52||k==53||k==54||k==55||k==56||k==57){
            evt.consume();
        }

//        if(k==10){
//            this.txtNombreDosCony.requestFocus();
//        }
    }//GEN-LAST:event_txtApellidoDosConyKeyTyped

    private void comboNucleoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboNucleoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboNucleoMouseReleased

    private void comboNucleoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboNucleoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboNucleoKeyTyped

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
      try {
          this.imprimirFicha();
      } catch (IOException ex) {
          Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
      }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void imprimirFicha() throws IOException{
      try {
          FileOutputStream fileOut = null;
          File filePDF = null;
          filePDF = new File("C:\\declaracion.pdf");
          fileOut = new FileOutputStream(filePDF);
          logPdf pdf = new logPdf();
          pdf.armoDeclaracion(filePDF, f, d, lista);
          if(fileOut!=null){
             fileOut.close();
             if(filePDF!=null){
                   Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filePDF.getAbsolutePath());
              }
          }
          //Desktop.getDesktop().print(filePDF);
      } catch (FileNotFoundException ex) {
          Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
      } catch (DocumentException ex) {
          Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {
          Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
          Logger.getLogger(InternalMantenimientoDecla.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       instancia = null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtDocFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocFuncKeyTyped
        this.mensaje="";
        this.showMensaje(); 
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(this.txtDocFunc.getText().length()>=8){
            evt.consume();
        }

        if(k==10){
           
        }
    }//GEN-LAST:event_txtDocFuncKeyTyped

    private void txtDocFuncFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDocFuncFocusLost
    if(this.comboTipoDocFunc.getSelectedItem()!=null){
        if(this.comboTipoDocFunc.getSelectedItem().equals("C.I.")){
            if(!this.EsCIValida(this.txtDocFunc.getText())){
                this.mensaje="Ingrese una cédula válida";
                this.showMensaje();   
            }
        }
     }
    }//GEN-LAST:event_txtDocFuncFocusLost

    private void txtNumFuncFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFuncFocusGained
       this.txtNumFunc.selectAll();
      // this.limpiarTodo();
    }//GEN-LAST:event_txtNumFuncFocusGained

    private void txtCategoriaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCategoriaFocusLost
      if(!this.esNum(this.txtCategoria.getText())){
          
      }
      else{
          int num = Integer.valueOf(this.txtCategoria.getText());
          if(num<0 || num>11){
              this.mensaje="La Categoría debe ser entre 1 y 11";
              this.showMensaje();
              this.txtCategoria.requestFocus();
          }
      }
    }//GEN-LAST:event_txtCategoriaFocusLost

    private void txtDocCargoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDocCargoFocusLost
      if(this.comboTipoDocCargo.getSelectedItem()!=null){
        if(this.comboTipoDocCargo.getSelectedItem().equals("C.I.")){
          if(!this.EsCIValida(this.txtDocCargo.getText())){
              this.mensaje="Ingrese una cédula válida";
              this.showMensajeCargo();
          }
          else{
              this.mensaje="";
              this.showMensajeCargo();
          }
        }
      }
    }//GEN-LAST:event_txtDocCargoFocusLost

    private void txtDocConyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDocConyFocusLost
      if(this.comboTipoDocCony.getSelectedItem()!=null){
        if(this.comboTipoDocCony.getSelectedItem().equals("C.I.")){
        if(!this.EsCIValida(this.txtDocCony.getText())){
            this.mensaje="Ingrese una cédula válida";
            this.showMensaje();   
        }
        }
      }
    }//GEN-LAST:event_txtDocConyFocusLost

    private void txtVigenciaMesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVigenciaMesFocusLost
        if(this.esNum(this.txtVigenciaMes.getText())){
            int i = Integer.valueOf(this.txtVigenciaMes.getText());
            if(i<1 || i>12){
                this.mensaje="El mes de Vigencia debe estar entre 1 y 12";
                this.showMensaje();   
                this.txtVigenciaMes.requestFocus();
            }
        }
        else{
            this.mensaje="El mes de Vigencia debe estar entre 1 y 12";
            this.showMensaje();    
            this.txtVigenciaMes.requestFocus();
            
        }
    }//GEN-LAST:event_txtVigenciaMesFocusLost

    private void txtVigenciaAñoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVigenciaAñoFocusLost
       if(this.esNum(this.txtVigenciaAño.getText())){
            int i = Integer.valueOf(this.txtVigenciaAño.getText());
            if(i<2007){
                this.mensaje="El año de Vigencia debe ser mayor a 2007";
                this.showMensaje();
                 this.txtVigenciaAño.requestFocus();
            }
        }
        else{
            this.mensaje="El año de Vigencia debe ser mayor a 2007";
            this.showMensaje(); 
            this.txtVigenciaAño.requestFocus();
        }
    }//GEN-LAST:event_txtVigenciaAñoFocusLost

    private void txtCajaProFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCajaProFocusLost
        if(!this.esDouble(this.txtCajaPro.getText())){
            this.mensaje="Ingrese un número válido";
            this.showMensaje();
            this.txtCajaPro.requestFocus();
        }
    }//GEN-LAST:event_txtCajaProFocusLost

    private void comboMinimoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboMinimoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMinimoMouseReleased

    private void comboMinimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboMinimoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMinimoKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       if(pe!=null){
           this.eliminarDeLista();
           this.mensaje="";
           this.showMensajeCargo();
       }
       else{
           this.mensaje="Seleccione una línea";
           this.showMensajeCargo();
       }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        this.mensaje="";
        this.showMensaje();
        Integer codFunc = f.getCodFunc();
        String tipoDoc = (String) this.comboTipoDocFunc.getSelectedItem();
        String nroDoc = this.txtDocFunc.getText();
        String pais = (String) this.comboPaisFunc.getSelectedItem();
        String strVigMes = this.txtVigenciaMes.getText();
        String strVigAño = this.txtVigenciaAño.getText();
        String strCatcjpu = this.txtCategoria.getText();
        String strReint = this.txtCajaPro.getText();
        String fondo = (String) this.comboFondo.getSelectedItem();
        String strAdicional = (String) this.comboAdicionalFondo.getSelectedItem();
        String strMinimo = (String) this.comboMinimo.getSelectedItem();
        Date fechaRecep = this.txtFechaRecep.getDate();
        String familiar = (String) this.comboNucleo.getSelectedItem();
        String tipoDocCony = (String) this.comboTipoDocCony.getSelectedItem();
        String nroDocCony = this.txtDocCony.getText();
        String paisCony = (String) this.comboPaisCony.getSelectedItem();
        String apeUnoCony = this.txtApellidoUnoCony.getText();
        String apeDosCony = this.txtApellidoDosCony.getText();
        String nomUnoCony = this.txtNombreUnoCony.getText();
        String nomDosCony = this.txtNombreDosCony.getText();
        Date fechaNacCony = this.txtFechaNacCony.getDate();
        String nacCony = (String) this.comboNacionalCony.getSelectedItem();
        String sexoCony = (String) this.comboSexoCony.getSelectedItem();
       
     
       if(f!=null){ 
        if(tipoDoc!=null){
            if(this.esNum(nroDoc.trim())){
                if(pais!=null){
                    if(this.esNum(strVigMes.trim())){
                        if(this.esNum(strVigAño.trim())){
                            if(this.esNum(strCatcjpu.trim())){
                                if(this.esDouble(strReint)){
                                    if(fondo!=null){
                                        if(strAdicional!=null){
                                            if(strMinimo!=null){
                                                if(fechaRecep!=null){
                                                      if(!nroDocCony.equals("") || fechaNacCony != null || !apeUnoCony.equals("") || !nomUnoCony.equals("")){
                                                            if(this.esNum(nroDocCony)){
                                                                if(fechaNacCony!=null){
                                                                    if(!apeUnoCony.equals("")){
                                                                        if(!nomUnoCony.equals("")){
                                                                            if(paisCony!=null){
                                                                                if(sexoCony!=null){
                                                                                    if(nacCony!=null){
                                                                                        if(tipoDocCony!=null){
                                                                                            if(tipoDoc.equals("C.I.")){
                                                                                                if(this.EsCIValida(nroDoc)){
                                                                                                    if(tipoDocCony.equals("C.I.")){
                                                                                                        if(this.EsCIValida(nroDocCony)){
                                                                                                            try {
                                                                                                                //ALTA DE FUNCIONARIO CON CI CON CONYUGE CEDULA
                                                                                                                if(this.log.ingresoDeclaracion(codFunc,tipoDoc,nroDoc,pais,strVigMes,strVigAño,strCatcjpu,strReint,fondo,strAdicional,strMinimo,fechaRecep,familiar,tipoDocCony,nroDocCony,paisCony,apeUnoCony,apeDosCony,nomUnoCony,nomDosCony,fechaNacCony,nacCony,sexoCony,lista)){
                                                                                                                    this.limpiarTodo();
                                                                                                                    this.mensaje="Ingreso Exitoso";
                                                                                                                    this.showMensaje();
                                                                                                                    this.txtNumFunc.selectAll();
                                                                                                                    this.txtNumFunc.requestFocus();
                                                                                                                }
                                                                                                                else{
                                                                                                                    this.mensaje="Intente nuevamente";  
                                                                                                                    this.showMensaje();
                                                                                                                }
                                                                                                            } catch (BDExcepcion ex) {
                                                                                                                 this.mensaje=ex.getMessage();  
                                                                                                                 this.showMensaje();
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                    else{
                                                                                                        try {
                                                                                                            //ALTA DE FUNCIONARIO CON CI CON CONYUGE NO CEDULA
                                                                                                            if(this.log.ingresoDeclaracion(codFunc,tipoDoc,nroDoc,pais,strVigMes,strVigAño,strCatcjpu,strReint,fondo,strAdicional,strMinimo,fechaRecep,familiar,tipoDocCony,nroDocCony,paisCony,apeUnoCony,apeDosCony,nomUnoCony,nomDosCony,fechaNacCony,nacCony,sexoCony,lista)){
                                                                                                                this.limpiarTodo();
                                                                                                                this.mensaje="Ingreso Exitoso";
                                                                                                                this.showMensaje();
                                                                                                                this.txtNumFunc.selectAll();
                                                                                                                this.txtNumFunc.requestFocus();
                                                                                                            }
                                                                                                            else{
                                                                                                                this.mensaje="Intente nuevamente";
                                                                                                                this.showMensaje();  
                                                                                                            }
                                                                                                        } catch (BDExcepcion ex) {
                                                                                                             this.mensaje=ex.getMessage();  
                                                                                                             this.showMensaje();
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                                else{
                                                                                                    this.mensaje="Ingrese una cédula válida para el funcionario";
                                                                                                    this.showMensaje();
                                                                                                }
                                                                                            }
                                                                                            else{
                                                                                                if(tipoDocCony.equals("C.I.")){
                                                                                                    if(this.EsCIValida(nroDocCony)){
                                                                                                        try {
                                                                                                            //ALTA DE FUNCIONARIO NO CI CON CONYUGE CEDULA
                                                                                                            if(this.log.ingresoDeclaracion(codFunc,tipoDoc,nroDoc,pais,strVigMes,strVigAño,strCatcjpu,strReint,fondo,strAdicional,strMinimo,fechaRecep,familiar,tipoDocCony,nroDocCony,paisCony,apeUnoCony,apeDosCony,nomUnoCony,nomDosCony,fechaNacCony,nacCony,sexoCony,lista)){
                                                                                                                this.limpiarTodo();
                                                                                                                this.mensaje="Ingreso Exitoso";
                                                                                                                this.showMensaje();
                                                                                                                this.txtNumFunc.selectAll();
                                                                                                                this.txtNumFunc.requestFocus();
                                                                                                            }
                                                                                                            else{
                                                                                                                this.mensaje="Intente nuevamente";
                                                                                                                this.showMensaje();
                                                                                                            } 
                                                                                                        } catch (BDExcepcion ex) {
                                                                                                             this.mensaje=ex.getMessage();  
                                                                                                             this.showMensaje();
                                                                                                        }
                                                                                                     }
                                                                                                    }
                                                                                                    else{
                                                                                                    try {
                                                                                                        //ALTA DE FUNCIONARIO NO CI CON CONYUGE NO CEDULA
                                                                                                        if(this.log.ingresoDeclaracion(codFunc,tipoDoc,nroDoc,pais,strVigMes,strVigAño,strCatcjpu,strReint,fondo,strAdicional,strMinimo,fechaRecep,familiar,tipoDocCony,nroDocCony,paisCony,apeUnoCony,apeDosCony,nomUnoCony,nomDosCony,fechaNacCony,nacCony,sexoCony,lista)){
                                                                                                            this.limpiarTodo();
                                                                                                            this.mensaje="Ingreso Exitoso";
                                                                                                            this.showMensaje();
                                                                                                            this.txtNumFunc.selectAll();
                                                                                                            this.txtNumFunc.requestFocus();
                                                                                                        }
                                                                                                        else{
                                                                                                            this.mensaje="Intente nuevamente";
                                                                                                            this.showMensaje();
                                                                                                        }
                                                                                                    } catch (BDExcepcion ex) {
                                                                                                        this.mensaje=ex.getMessage();  
                                                                                                        this.showMensaje();
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                        else{
                                                                                            this.mensaje="Seleccione el tipo de documento para el cónyuge";
                                                                                            this.showMensaje();
                                                                                        }
                                                                                       }   
                                                                                    else{
                                                                                        this.mensaje="Seleccione la nacionalidad del cónyuge";
                                                                                        this.showMensaje();
                                                                                    }
                                                                                }
                                                                                else{
                                                                                     this.mensaje="Seleccione el sexo del cónyuge";
                                                                                     this.showMensaje();
                                                                                }
                                                                            }
                                                                            else{
                                                                                this.mensaje="Seleccione un país para el cónyuge";
                                                                                this.showMensaje();
                                                                            }
                                                                        }
                                                                        else{
                                                                            this.mensaje="El 1er nombre del cónyuge es obligatorio";
                                                                            this.showMensaje();
                                                                        }
                                                                    }
                                                                    else{
                                                                        this.mensaje="El 1er apellido del cónyuge es obligatorio";
                                                                        this.showMensaje();
                                                                    }
                                                                }
                                                                else{
                                                                    this.mensaje="Ingrese fecha de nacimiento del cónyuge";
                                                                    this.showMensaje();
                                                                }
                                                            }
                                                            else{
                                                                this.mensaje="El documento del cónyuge debe ser solo Números";
                                                                this.showMensaje();
                                                            }
                                                        }
                                                        else{
                                                            if(tipoDoc.equals("C.I.")){
                                                                if(this.EsCIValida(nroDoc)){
                                                                    try {
                                                                        //INGRESO FUNCIONARIO CON CI SIN CONYUGE
                                                                        if(this.log.ingresoDeclaracion(codFunc,tipoDoc,nroDoc,pais,strVigMes,strVigAño,strCatcjpu,strReint,fondo,strAdicional,strMinimo,fechaRecep,familiar,null,null,null,null,null,null,null,null,null,null,lista)){
                                                                            this.limpiarTodo();
                                                                            this.mensaje="Ingreso Exitoso";
                                                                            this.showMensaje();
                                                                            this.txtNumFunc.selectAll();
                                                                            this.txtNumFunc.requestFocus();
                                                                        }
                                                                        else{
                                                                            this.mensaje="Intente nuevamente";
                                                                            this.showMensaje();  
                                                                        }
                                                                    } catch (BDExcepcion ex) {
                                                                         this.mensaje=ex.getMessage();  
                                                                         this.showMensaje();
                                                                    }
                                                                }
                                                                else{
                                                                    this.mensaje="Ingrese una cédula válida";
                                                                    this.showMensaje();
                                                                }
                                                            }
                                                            else{
                                                                try {
                                                                    //INGRESO FUNCIONARIO NO CI SIN CONYUGE
                                                                    if(this.log.ingresoDeclaracion(codFunc,tipoDoc,nroDoc,pais,strVigMes,strVigAño,strCatcjpu,strReint,fondo,strAdicional,strMinimo,fechaRecep,familiar,null,null,null,null,null,null,null,null,null,null,lista)){
                                                                        this.limpiarTodo();
                                                                        this.mensaje="Ingreso Exitoso";
                                                                        this.showMensaje();
                                                                        this.txtNumFunc.selectAll();
                                                                        this.txtNumFunc.requestFocus();
                                                                    }
                                                                    else{
                                                                        this.mensaje="Intente nuevamente";  
                                                                        this.showMensaje();
                                                                    }
                                                                } catch (BDExcepcion ex) {
                                                                     this.mensaje=ex.getMessage();  
                                                                     this.showMensaje();
                                                                }
                                                            }
                                                        }
                                                    
                                                }
                                                else{
                                                    this.mensaje="Ingrese fecha de recepción";
                                                    this.showMensaje();
                                                }
                                            }
                                            else{
                                                this.mensaje="Seleccione mínimo no imponible";
                                                this.showMensaje();
                                            }
                                        }
                                        else{
                                            this.mensaje="Seleccione el valor de Adicional";
                                            this.showMensaje();
                                        }
                                    }
                                    else{
                                        this.mensaje="Seleccione el fondo de solidaridad";
                                        this.showMensaje();
                                    }
                                }
                                else{
                                    this.mensaje="El reintegro debe ser un número válido";
                                    this.showMensaje();
                                }
                                
                            }
                            else{
                                this.mensaje="La categoría debe ser un número";
                                this.showMensaje();
                            }
                        }
                        else{
                            this.mensaje="Ingrese solo números en el mes de vigencia";
                            this.showMensaje();
                        }
                        
                    }
                    else{
                     this.mensaje="Ingrese solo números en el mes de vigencia";
                     this.showMensaje();
                    }
                    
                }
                else{
                     this.mensaje="Seleccione un país para el funcionario";
                     this.showMensaje();
                }
                
            }
            else{
                 this.mensaje="El documento debe ser solo Números";
                 this.showMensaje();
            }
            
        }
        else{
             this.mensaje="Seleccione tipo documento de Funcionario";
             this.showMensaje();
        }
    }
        
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
       if(f!=null){
           if(this.log.eliminarDeclaracion(f)){
               this.mensaje="Borrado exitoso.";
               this.showMensaje();
               this.limpiarTodo();
               this.txtNumFunc.selectAll();
               this.txtNumFunc.requestFocus();
            }
       }
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnAgregar;
    private org.edisoncor.gui.button.ButtonIcon btnBuscar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEliminar;
    private org.edisoncor.gui.button.ButtonIcon btnImprimir;
    private javax.swing.JComboBox comboAdicionalFondo;
    private javax.swing.JComboBox comboDiscapCargo;
    private javax.swing.JComboBox comboFondo;
    private javax.swing.JComboBox comboMinimo;
    private javax.swing.JComboBox comboNacionalCargo;
    private javax.swing.JComboBox comboNacionalCony;
    private javax.swing.JComboBox comboNucleo;
    private javax.swing.JComboBox comboPaisCargo;
    private javax.swing.JComboBox comboPaisCony;
    private javax.swing.JComboBox comboPaisFunc;
    private javax.swing.JComboBox comboRelacionCargo;
    private javax.swing.JComboBox comboSaludCargo;
    private javax.swing.JComboBox comboSexoCargo;
    private javax.swing.JComboBox comboSexoCony;
    private javax.swing.JComboBox comboTipoDocCargo;
    private javax.swing.JComboBox comboTipoDocCony;
    private javax.swing.JComboBox comboTipoDocFunc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtApellidoDosCargo;
    private org.edisoncor.gui.textField.TextFieldRound txtApellidoDosCony;
    private org.edisoncor.gui.textField.TextFieldRound txtApellidoUnoCargo;
    private org.edisoncor.gui.textField.TextFieldRound txtApellidoUnoCony;
    private javax.swing.JSpinner txtAtribCargo;
    private org.edisoncor.gui.textField.TextFieldRound txtCajaPro;
    private org.edisoncor.gui.textField.TextFieldRound txtCategoria;
    private org.edisoncor.gui.textField.TextFieldRound txtDocCargo;
    private org.edisoncor.gui.textField.TextFieldRound txtDocCony;
    private org.edisoncor.gui.textField.TextFieldRound txtDocFunc;
    private com.toedter.calendar.JDateChooser txtFechaNacCargo;
    private com.toedter.calendar.JDateChooser txtFechaNacCony;
    private com.toedter.calendar.JDateChooser txtFechaRecep;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtNombreDosCargo;
    private org.edisoncor.gui.textField.TextFieldRound txtNombreDosCony;
    private org.edisoncor.gui.textField.TextFieldRound txtNombreUnoCargo;
    private org.edisoncor.gui.textField.TextFieldRound txtNombreUnoCony;
    private org.edisoncor.gui.textField.TextFieldRound txtNumFunc;
    private org.edisoncor.gui.textField.TextFieldRound txtVigenciaAño;
    private org.edisoncor.gui.textField.TextFieldRound txtVigenciaMes;
    // End of variables declaration//GEN-END:variables

    private void showMensaje() {
       this.lblError.setText("");
       this.lblError.setText(mensaje);
    }
    private void showMensajeCargo() {
       this.lblMsj.setText("");
       this.lblMsj.setText(mensaje);
    }

    private void cargaCombos() throws SQLException, ClassNotFoundException {
       ArrayList<String> documentos = this.log.cargoComboDocumento();
       ArrayList<String> paises = this.log.cargoComboPais();
       ArrayList<String> nacionalidad = this.log.cargoComboNacionalidad();
//       ArrayList<Relacion> relacion = this.log.cargoComboRelacion();
       ArrayList<String> fondo = this.log.cargoComboFondo();
       
        for(int i=0; i<documentos.size();i++){
          this.comboTipoDocCargo.addItem(documentos.get(i));
          this.comboTipoDocCargo.setSelectedIndex(-1);
          this.comboTipoDocCony.addItem(documentos.get(i));
          this.comboTipoDocCony.setSelectedIndex(-1);
          this.comboTipoDocFunc.addItem(documentos.get(i));
          this.comboTipoDocFunc.setSelectedIndex(-1);
        }
        for(int i=0; i<paises.size();i++){
          this.comboPaisCargo.addItem(paises.get(i));
          this.comboPaisCargo.setSelectedIndex(-1);
          this.comboPaisCony.addItem(paises.get(i));
          this.comboPaisCony.setSelectedIndex(-1);
          this.comboPaisFunc.addItem(paises.get(i));
          this.comboPaisFunc.setSelectedIndex(-1);
        }
        for(int i=0; i<nacionalidad.size();i++){
          this.comboNacionalCargo.addItem(nacionalidad.get(i));
          this.comboNacionalCargo.setSelectedIndex(-1);
          this.comboNacionalCony.addItem(nacionalidad.get(i));
          this.comboNacionalCony.setSelectedIndex(-1);
        }
//        for(int i=0; i<relacion.size();i++){
//          this.comboRelacionCargo.addItem(relacion.get(i).getDescripcion());
//          this.comboRelacionCargo.setSelectedIndex(-1);
//        }
         for(int i=0; i<fondo.size();i++){
          this.comboFondo.addItem(fondo.get(i));
          this.comboFondo.setSelectedIndex(-1);
        }
        
     
      
        
    }

    private void limpiarTodo() {
        cargado=false;
        this.f=null;
        this.d=null;
        this.pe=null;
        this.lista = null;
        this.btnAgregar.setEnabled(false);
        this.btnConfirmar.setEnabled(false);
        this.btnEliminar.setEnabled(false);
        this.btnImprimir.setEnabled(false);
        this.comboAdicionalFondo.setSelectedIndex(-1);
        this.comboDiscapCargo.setSelectedIndex(-1);
        this.comboFondo.setSelectedIndex(-1);
        this.comboNacionalCargo.setSelectedIndex(-1);
        this.comboNacionalCony.setSelectedIndex(-1);
        this.comboNucleo.setSelectedIndex(-1);
        this.comboPaisCargo.setSelectedIndex(-1);
        this.comboPaisCony.setSelectedIndex(-1);
        this.comboPaisFunc.setSelectedIndex(-1);
        this.comboRelacionCargo.setSelectedIndex(-1);
        this.comboSaludCargo.setSelectedIndex(-1);
        this.comboSexoCargo.setSelectedIndex(-1);
        this.comboSexoCony.setSelectedIndex(-1);
        this.comboTipoDocCargo.setSelectedIndex(-1);
        this.comboTipoDocCony.setSelectedIndex(-1);
        this.comboTipoDocFunc.setSelectedIndex(-1);
        this.comboMinimo.setSelectedIndex(-1);
        this.lblError.setText("");
        this.lblMsj.setText("");
        this.txtApellidoDosCargo.setEditable(false);
        this.txtApellidoDosCargo.setText("");
        this.txtApellidoDosCony.setEditable(false);
        this.txtApellidoDosCony.setText("");
        this.txtApellidoUnoCargo.setEditable(false);
        this.txtApellidoUnoCargo.setText("");
        this.txtApellidoUnoCony.setEditable(false);
        this.txtApellidoUnoCony.setText("");
        this.txtAtribCargo.setEnabled(false);
        this.txtAtribCargo.setValue(0); 
        this.txtCajaPro.setEditable(false);
        this.txtCajaPro.setText("");
        this.txtCategoria.setEditable(false);
        this.txtCategoria.setText("");
        this.txtDocCargo.setEditable(false);
        this.txtDocCargo.setText("");
        this.txtDocCony.setEditable(false);
        this.txtDocCony.setText("");
        this.txtDocFunc.setEditable(false);
        this.txtDocFunc.setText("");
        this.txtFechaNacCargo.setEnabled(false);
        this.txtFechaNacCargo.setDate(null);
        this.txtFechaNacCony.setEnabled(false);
        this.txtFechaNacCony.setDate(null);
        this.txtFechaRecep.setEnabled(false);
        this.txtFechaRecep.setDate(null);
        this.txtNombre.setEditable(false);
        this.txtNombre.setText("");
        this.txtNombreDosCargo.setEditable(false);
        this.txtNombreDosCargo.setText("");
        this.txtNombreDosCony.setEditable(false);
        this.txtNombreDosCony.setText("");
        this.txtNombreUnoCargo.setEditable(false);
        this.txtNombreUnoCargo.setText("");
        this.txtNombreUnoCony.setEditable(false);
        this.txtNombreUnoCony.setText("");
        this.txtVigenciaAño.setEditable(false);
        this.txtVigenciaAño.setText("");
        this.txtVigenciaMes.setEditable(false);
        this.txtVigenciaMes.setText("");
        this.LimpiarTabla();
    }
    
    private void habilitar() {
        this.btnAgregar.setEnabled(true);
        this.btnConfirmar.setEnabled(true);
        this.btnEliminar.setEnabled(true);
        this.btnImprimir.setEnabled(true);
        this.comboAdicionalFondo.setSelectedIndex(-1);
        this.comboDiscapCargo.setSelectedIndex(-1);
        this.comboFondo.setSelectedIndex(-1);
        this.comboNacionalCargo.setSelectedIndex(-1);
        this.comboNacionalCony.setSelectedIndex(-1);
        this.comboNucleo.setSelectedIndex(-1);
        this.comboPaisCargo.setSelectedIndex(-1);
        this.comboPaisCony.setSelectedIndex(-1);
        this.comboPaisFunc.setSelectedIndex(-1);
        this.comboRelacionCargo.setSelectedIndex(-1);
        this.comboSaludCargo.setSelectedIndex(-1);
        this.comboSexoCargo.setSelectedIndex(-1);
        this.comboSexoCony.setSelectedIndex(-1);
        this.comboTipoDocCargo.setSelectedIndex(-1);
        this.comboTipoDocCony.setSelectedIndex(-1);
        this.comboTipoDocFunc.setSelectedIndex(-1);
        this.comboMinimo.setSelectedIndex(-1);
        this.lblError.setText("");
        this.lblMsj.setText("");
        this.txtApellidoDosCargo.setEditable(true);
        this.txtApellidoDosCargo.setText("");
        this.txtApellidoDosCony.setEditable(true);
        this.txtApellidoDosCony.setText("");
        this.txtApellidoUnoCargo.setEditable(true);
        this.txtApellidoUnoCargo.setText("");
        this.txtApellidoUnoCony.setEditable(true);
        this.txtApellidoUnoCony.setText("");
        this.txtAtribCargo.setEnabled(true);
        this.txtAtribCargo.setValue(100);
        this.txtCajaPro.setEditable(true);
        this.txtCajaPro.setText("");
        this.txtCategoria.setEditable(true);
        this.txtCategoria.setText("");
        this.txtDocCargo.setEditable(true);
        this.txtDocCargo.setText("");
        this.txtDocCony.setEditable(true);
        this.txtDocCony.setText("");
        this.txtDocFunc.setEditable(true);
        this.txtDocFunc.setText("");
        this.txtFechaNacCargo.setEnabled(true);
        this.txtFechaNacCargo.setDate(null);
        this.txtFechaNacCony.setEnabled(true);
        this.txtFechaNacCony.setDate(null);
        this.txtFechaRecep.setEnabled(true);
        this.txtFechaRecep.setDate(null);
        this.txtNombre.setEditable(false);
        this.txtNombreDosCargo.setEditable(true);
        this.txtNombreDosCargo.setText("");
        this.txtNombreDosCony.setEditable(true);
        this.txtNombreDosCony.setText("");
        this.txtNombreUnoCargo.setEditable(true);
        this.txtNombreUnoCargo.setText("");
        this.txtNombreUnoCony.setEditable(true);
        this.txtNombreUnoCony.setText("");
        this.txtVigenciaAño.setEditable(true);
        this.txtVigenciaAño.setText("");
        this.txtVigenciaMes.setEditable(true);
        this.txtVigenciaMes.setText("");
        
             
    }
    
    private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
    }
    

    private void cargoDeclaracion() {
        if(d!=null){
            this.comboTipoDocFunc.setSelectedItem(d.getTipoDoc().trim());
            this.txtDocFunc.setText(d.getNroDoc().trim());
            this.comboPaisFunc.setSelectedItem(d.getPais().trim());
            String vigencia = String.valueOf(d.getVigencia());
            this.txtVigenciaAño.setText(vigencia.substring(0, 4));
            this.txtVigenciaMes.setText(vigencia.substring(4, 6));
            this.txtCategoria.setText(d.getCatcjpu().toString());
            this.txtCajaPro.setText(this.decimales(d.getReintcjpu()));
            this.comboFondo.setSelectedItem(d.getFondosolcjpu().trim());
            if(d.getAdicionalcjpu()==0){
            this.comboAdicionalFondo.setSelectedItem("NO");
            }
            else if(d.getAdicionalcjpu() == 1){
            this.comboAdicionalFondo.setSelectedItem("SI");    
            }
            if(d.getTipoDocConyu()!=null){
                this.comboTipoDocCony.setSelectedItem(d.getTipoDocConyu().trim());
            }
            if(d.getNroDocConyu() !=null){
                this.txtDocCony.setText(d.getNroDocConyu().trim());
            }
            if(d.getPaisConyu() !=null){
                this.comboPaisCony.setSelectedItem(d.getPaisConyu().trim());
            }
            if(d.getFechaNacConyu() !=null){
                this.txtFechaNacCony.setDate(d.getFechaNacConyu());
            }
            if(d.getNacionalConyu() !=null){
                this.comboNacionalCony.setSelectedItem(d.getNacionalConyu().trim());
            }
            if(d.getSexoConyu() !=null){
                if(d.getSexoConyu()=='M'){
                   this.comboSexoCony.setSelectedItem("MASCULINO");
                }
                else if(d.getSexoConyu()=='F'){
                    this.comboSexoCony.setSelectedItem("FEMENINO");
                }
            }
            if(d.getApellidoUnoConyu()!=null){
                this.txtApellidoUnoCony.setText(d.getApellidoUnoConyu().trim());
            }
            if(d.getApellidoDosConyu() !=null){
                this.txtApellidoDosCony.setText(d.getApellidoDosConyu().trim());
            }
            if(d.getNombreUnoConyu() !=null){
                this.txtNombreUnoCony.setText(d.getNombreUnoConyu().trim());
            }
            if(d.getNombreDosConyu() != null){
                this.txtNombreDosCony.setText(d.getNombreDosConyu().trim()); 
            }
            if(d.getMinimoimp()==0){
                this.comboMinimo.setSelectedItem("NO");
            }
            else{
                this.comboMinimo.setSelectedItem("SI");
            }
            
            this.txtFechaRecep.setDate(d.getFechaRecepcion());
            
            if(this.d.getFamiliar() == 1){
                this.comboNucleo.setSelectedItem("SI");
            }
            else{
                this.comboNucleo.setSelectedItem("NO");
            }
        
            if(this.d.getPersonasACargo()>0){
                lista = this.log.cargoPeronas(f.getCodFunc());
                this.cargarACargo();
            }
            else{
               lista = new ArrayList<>(); 
            }
        }
    }

    private void cargarACargo() {
       
       
       this.LimpiarTabla();
       if(lista.size()>0){
           this.Alinear_Grillas();
           DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
           Object[] filas=new Object[modelo.getColumnCount()];
           int posicion=0;
           for(PersonasACargo p:lista){
               filas[0]=p.getOrdinal();
               filas[1]=p.getTipoDoc();
               filas[2]=p.getNroDoc().trim();
               filas[3]=p.getPais().trim();
               filas[4]=this.formateo(p.getFecha_nac());
               if(p.getApellidoUno() != null){
                   filas[5]=p.getApellidoUno().trim();
               }
               if(p.getApellidoDos() != null){
                   filas[6]=p.getApellidoDos().trim();
               }
               if(p.getNombreUno() != null){
                   filas[7]=p.getNombreUno().trim();
               }
               if(p.getNombreDos() != null){
                   filas[8]=p.getNombreDos().trim();
               }
               
               filas[9]=p.getNacionalidad().trim();
               filas[10]=p.getSexo();
               filas[11]=p.getRelacion();
               filas[12]=p.getSistSalud();
               filas[13]=p.getPjedist();
               if(p.getDiscapacidad()==0){
               filas[14]="NO";
               }
               else if(p.getDiscapacidad()==1){
               filas[14]="SI";
               }
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
    }
    
    

    
     private void LimpiarTabla() {
        
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
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
        this.tabla.getColumnModel().getColumn(13).setCellRenderer(modelo);   
     
    }

    private void limpiarRubroDos() {
        this.comboTipoDocCargo.setSelectedIndex(-1);
        this.txtDocCargo.setText("");
        this.comboPaisCargo.setSelectedIndex(-1);
        this.txtFechaNacCargo.setDate(null);
        this.comboNacionalCargo.setSelectedIndex(-1);
        this.comboSexoCargo.setSelectedIndex(-1);
        this.comboRelacionCargo.setSelectedIndex(-1);
        this.comboSaludCargo.setSelectedIndex(-1);
        this.txtAtribCargo.setValue(100);
        this.comboDiscapCargo.setSelectedIndex(-1);
        this.txtApellidoUnoCargo.setText("");
        this.txtApellidoDosCargo.setText("");
        this.txtNombreUnoCargo.setText("");
        this.txtNombreDosCargo.setText("");
    }
    
    public boolean EsCIValida(String ci) { 

        if(ci.length() != 7 && ci.length() != 8){ 
            return false; 
        }else{ 
            try{ 
                Integer.parseInt(ci); 
            }catch (NumberFormatException e){ 
                return false; 
            } 
        } 

        int digVerificador = Integer.parseInt((ci.charAt(ci.length() - 1)) + "" ) ; 
        int[] factores; 
        if(ci.length() == 7){ // CI viejas 
            factores = new int[]{9, 8, 7, 6, 3, 4}; 
        }
        else{ 
            factores = new int[]{2, 9, 8, 7, 6, 3, 4}; 
        } 


        int suma = 0; 
        for(int i=0; i<ci.length()-1; i++ ){ 
            int digito = Integer.parseInt(ci.charAt(i) + "" ) ; 
            suma += digito * factores[ i ]; 
        } 

        int resto = suma % 10; 
        int checkdigit = 10 - resto; 

        if(checkdigit == 10){ 
            return (digVerificador == 0); 
        }
        else { 
            return (checkdigit == digVerificador) ; 
        } 

    }
    
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

    private void verificaLista(PersonasACargo p) {
        boolean nuevo = true; 
        ArrayList<PersonasACargo> aux = new ArrayList<>();
        if(lista!=null){
        for(PersonasACargo pers: lista){
            if(pers.getOrdinal().equals(p.getOrdinal())){
                aux.add(p);
                nuevo=false;
            }
            else{
                aux.add(pers);
            }
        }
            if(lista.isEmpty()){
                aux.add(p);
            }else if(nuevo){
               aux.add(p);
            }
        }
        else{
            aux.add(p);
        }
        this.lista = aux;
        this.cargarACargo();
        this.limpiarRubroDos();
        pe=null;
    }

    private void eliminarDeLista() {
        ArrayList<PersonasACargo> aux = new ArrayList<>();
        for(PersonasACargo pers: lista){
            if(!pers.getOrdinal().equals(pe.getOrdinal())){
                aux.add(pers);
                
            }
            
        }
        this.lista = aux;
        this.cargarACargo();
        this.limpiarRubroDos();
        pe=null;
    }

    private Integer buscaUltimoOrdinal() {
       Integer ret = 0;
       if(lista!=null){
        for(PersonasACargo pers: lista){
            if(pers.getOrdinal()>ret){
                ret=pers.getOrdinal();
            }
        }
       }
       return ret+1;
    }

    private boolean verificaDocumentoExiste(String numDoc) {
       int i = 0;
       boolean existe = false;
       if(lista!=null){
        while(i<lista.size() && !existe){
            if(lista.get(i).getNroDoc().trim().equals(numDoc)){
                existe = true;
            }
            i++;
        }
       }
       return existe;
    }
    
}
