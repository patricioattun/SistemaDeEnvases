
package Presentacion.Liquidaciones;

import Dominio.Codigo;
import Dominio.CodigoPrelacion;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Dominio.Liquidacion;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import Persistencia.PersistenciaLiquidacion;
import Presentacion.RenderCodigos;
import Presentacion.frmPrin;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import org.edisoncor.gui.textField.TextFieldRound;


public class InternalFechaPrelacionFunc extends javax.swing.JInternalFrame {
    private Funcionario f;
    private InternalListadoFuncFiltro listadoFunc;
    private LogFuncionario log;
    private Codigo codigo=null;
    private CodigoPrelacion codPrel=null;
    private LogCodigo logs;
    private ArrayList<CodigoPrelacion> lista=null;
    private static InternalFechaPrelacionFunc instancia=null;
    DefaultTableModel tmMov=null;
    private CodigoPrelacion prel=null;
    private ArrayList<Codigo> c;
    private Ingreso ingreso=null;
    private boolean todos;
    private Liquidacion Liq;
    private PersistenciaLiquidacion persLiq;
    
    public InternalFechaPrelacionFunc() throws ClassNotFoundException, SQLException {
        try {
            initComponents();
            log=new LogFuncionario();
            logs=new LogCodigo();
            this.cargaCodigos();
            this.bloqueo(false);
            
        tmMov = (DefaultTableModel) tabla.getModel();
        tabla.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
        
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                         cargaCodigoPrelacion(e);
                 }
                else if(e.getClickCount()==1){
                    cargaCodigoPrelacionUnClick(e);
                }
             }

           

            
           
        });
        } catch (BDExcepcion ex) {
            Logger.getLogger(InternalFechaPrelacionFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     public InternalFechaPrelacionFunc(Ingreso ing, boolean todos) throws ClassNotFoundException, SQLException {
        try {
            initComponents();
            log=new LogFuncionario();
            logs=new LogCodigo();
            this.cargaCodigos();
            this.bloqueo(false);
            ingreso=ing;
            this.txtNumFunc.setText(ingreso.getCodFunc().toString());
            this.buscarFuncionario();
            this.buscarCodigo(ingreso.getCod());
            this.todos=todos;
            persLiq=new PersistenciaLiquidacion();
            
        tmMov = (DefaultTableModel) tabla.getModel();
        tabla.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
        
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                         cargaCodigoPrelacion(e);
                 }
                else if(e.getClickCount()==1){
                    cargaCodigoPrelacionUnClick(e);
                }
             }

           

            
           
        });
        } catch (BDExcepcion ex) {
            Logger.getLogger(InternalFechaPrelacionFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        txtNumFunc = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        fechaHora = new com.lavantech.gui.comp.DateTimePicker();
        btnAceptar = new org.edisoncor.gui.button.ButtonRound();
        comboPrelacion = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        lblMsj = new javax.swing.JLabel();

        jMenuItem1.setText("Eliminar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setClosable(true);
        setIconifiable(true);
        setTitle("Carga de Prelación por Código");
        setPreferredSize(new java.awt.Dimension(720, 573));
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

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Nro. Funcionario ");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Nombre de Funcionario");

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

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Fecha y Hora");

        fechaHora.setMinimumSize(new java.awt.Dimension(100, 32));
        fechaHora.setPreferredSize(new java.awt.Dimension(100, 23));
        fechaHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaHoraActionPerformed(evt);
            }
        });

        btnAceptar.setForeground(new java.awt.Color(102, 153, 255));
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        comboPrelacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPrelacionItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Seleccione Código");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Fecha", "Hora", "Pers"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(3).setMinWidth(0);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabla.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        lblMsj.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboPrelacion, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(78, 78, 78)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel5)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboPrelacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(513, 513, 513)
                    .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static InternalFechaPrelacionFunc instancia() throws ClassNotFoundException, SQLException
        {    
         if (instancia== null)
         {
            instancia = new InternalFechaPrelacionFunc();
         }
         return instancia;
      
    }
    
    public static InternalFechaPrelacionFunc instancia2(Ingreso ingreso, boolean todos) throws ClassNotFoundException, SQLException
        {    
         if (instancia== null)
         {
            instancia = new InternalFechaPrelacionFunc(ingreso, todos);
         }
         return instancia;
      
    }

    public ArrayList<CodigoPrelacion> getLista() {
        return lista;
    }

    public void setLista(ArrayList<CodigoPrelacion> lista) {
        this.lista = lista;
    }
    
    private void cargaCodigoPrelacionUnClick(MouseEvent e){
        Integer m=this.tabla.rowAtPoint(e.getPoint());
        prel = this.lista.get(m);
    }
    
    
    private void cargaCodigoPrelacion(MouseEvent e) {
        Integer m=this.tabla.rowAtPoint(e.getPoint());
        prel = this.lista.get(m);
        InternalEditarPrelacion modal = new InternalEditarPrelacion(null,true,prel,logs,2);
        modal.setVisible(true);
     }
    
     private void cargaCodigos() throws SQLException, ClassNotFoundException, BDExcepcion {
      c=this.logs.cargaGrupoDosYTres();
  
      this.comboPrelacion.addItem("SELECCIONE CÓDIGO");
      this.comboPrelacion.addItem("Grupo 2 ---------------------------------------------------------------------------");
      for(int i=0; i<c.size();i++){
          
          if(i==2){
                this.comboPrelacion.addItem("Grupo 3 ---------------------------------------------------------------------------");
          }
          this.comboPrelacion.addItem(c.get(i));
        
          this.comboPrelacion.setSelectedIndex(0);
                  
      }
    }
    
     private void bloqueo(boolean bol){
        
        this.fechaHora.setEnabled(bol);
        this.btnAceptar.setEnabled(bol);
        this.lblMsj.setText(""); 
        this.comboPrelacion.setEnabled(bol);
        this.LimpiarTabla();
       
        this.codigo=null;
        this.codPrel=null;
        this.txtNombre.setText("");
        this.txtNumFunc.setText(""); 
    }
    
     public void LimpiarTabla() {
     DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }
    
    public TextFieldRound getTxtNumFunc() {
        return txtNumFunc;
    }

    public void setTxtNumFunc(TextFieldRound txtNumFunc) {
        this.txtNumFunc = txtNumFunc;
    }
    
    private void txtNumFuncFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFuncFocusGained
        this.txtNumFunc.selectAll();
    }//GEN-LAST:event_txtNumFuncFocusGained

    private void txtNumFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFuncKeyTyped
        this.LimpiarTabla();
        this.f=null;
        this.txtNombre.setText("");
        this.lblMsj.setText("");
       
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(k==10){
            this.buscarFuncionario();
        }
        if(this.txtNumFunc.getText().length()>3){
            evt.consume();
        }
        if(evt.getKeyChar()==43){
            try {
                // this.txtCod.setText("");
                
                listadoFunc=InternalListadoFuncFiltro.instancia(log,10);
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
                this.lblMsj.setText("Ha ocurrido un problema.");
            }

            
        }
    }//GEN-LAST:event_txtNumFuncKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void fechaHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaHoraActionPerformed
        
    }//GEN-LAST:event_fechaHoraActionPerformed
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
     public void buscarFuncionario() {
       String numFunc=this.txtNumFunc.getText();
        this.lblMsj.setText("");
        //this.codigos=null;
        if(this.esNum(numFunc)){
            try {
                f = this.log.funcParcial(numFunc);
                if(f!=null){
                    this.txtNombre.setText(f.getNomCompletoApe());
                    this.fechaHora.requestFocus();
                    this.cargoPrelaciones();
                    this.fechaHora.setEnabled(true);
                    this.btnAceptar.setEnabled(true);
                    this.comboPrelacion.setEnabled(true);

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
    }
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        this.lblMsj.setText("");
        if(this.f!=null){
             if(this.codigo!=null){
                    if(!estaEnLista()){
                        Date fecha=this.fechaHora.getDate();
                        if(fecha!=null){
                            Date hoy = new Date();
                            if(fecha.before(hoy)|| fecha.equals(hoy)){
                                try {
                                    codPrel=new CodigoPrelacion();
                                    codPrel.setFecha(this.pasarATimestamp(fecha));
                                    codPrel.setF(f);
                                    codPrel.setCod(codigo);
                                    if(this.logs.insertoCodigoPrelacion(codPrel)){
                                        try {
                                            this.lista=this.logs.cargoPrelacionFuncionario(f);
                                            this.cargaTabla();
                                            if(ingreso!=null){
                                                ArrayList<Ingreso> ingresos;
                                                if(todos){
                                                     ingresos=this.persLiq.verificoPrelacion(null);
                                                }else{
                                                    Funcionario f=new Funcionario();
                                                    f.setCodFunc(ingreso.getCodFunc());
                                                    ingresos=this.persLiq.verificoPrelacion(f);
                                                }
                                                InternalNoPrelaciones.instancia2().setIngresos(ingresos);
                                                InternalNoPrelaciones.instancia2().setTodos(todos);
                                                InternalNoPrelaciones.instancia2().cargoTabla();
                                                InternalNoPrelaciones.instancia2().setSelected(true);
                                                instancia=null;
                                                this.dispose();
                                            }
                                        } catch (BDExcepcion ex) {
                                            this.lblMsj.setText(ex.getMessage());
                                        } catch (ClassNotFoundException ex) {
                                            this.lblMsj.setText(ex.getMessage());
                                        } catch (SQLException ex) {
                                            this.lblMsj.setText(ex.getMessage());
                                        } catch (PropertyVetoException ex) {
                                            this.lblMsj.setText(ex.getMessage());
                                        }
                                    }
                                } catch (BDExcepcion ex) {
                                    this.lblMsj.setText(ex.getMessage());
                                }
                            }
                            else{
                                this.lblMsj.setText("La fecha no puede ser posterior a hoy");
                            }
                        }
                        else{
                            this.lblMsj.setText("Seleccione una fecha válida");
                        }
                    }
                    else{
                        this.lblMsj.setText("Este funcionario ya tiene un ingreso para este código");
                    }
                }
                else{
                    this.lblMsj.setText("Seleccione un código válido");
                }
        }
        else{
            this.lblMsj.setText("Seleccione un funcionario");
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

      private boolean estaEnLista(){
        boolean esta=false;
                    int i=0;
                    while(i<lista.size()&&!esta){
                           if(lista.get(i).getCod().getCod().equals(codigo.getCod())){ 
                              esta=true;
                           }
                         i++;
                    }
        return esta;
    }
      
    private Timestamp pasarATimestamp(Date fecha){
    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    cal.set(Calendar.MILLISECOND, 0);

    return new java.sql.Timestamp(cal.getTimeInMillis());    
    
    }
    
    private void comboPrelacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPrelacionItemStateChanged
        this.lblMsj.setText("");  
        this.codigo=null;
        if(this.comboPrelacion.getSelectedIndex()!=0){
            if(this.comboPrelacion.getSelectedIndex()==1 || this.comboPrelacion.getSelectedIndex()==4){
                this.comboPrelacion.setSelectedIndex(0);
            }
            else{
               
                codigo=(Codigo) this.comboPrelacion.getSelectedItem();
          }
        }
    }//GEN-LAST:event_comboPrelacionItemStateChanged

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
      try{
        if(prel!=null){
            if(this.logs.esPosibleEliminar(prel)){
                if(this.logs.eliminarPrelacion(prel)){
                    this.lblMsj.setText("Borrado Exitoso."); 
                       this.buscarFuncionario();
                 }
                
            }
        }
       }
       catch (BDExcepcion ex) {
           this.lblMsj.setText(ex.getMessage()); 
       }
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonRound btnAceptar;
    private javax.swing.JComboBox comboPrelacion;
    private com.lavantech.gui.comp.DateTimePicker fechaHora;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtNumFunc;
    // End of variables declaration//GEN-END:variables

    private void cargoPrelaciones() {
        try {
           
            this.lista=this.logs.cargoPrelacionFuncionario(f);
            this.cargaTabla();
        } catch (BDExcepcion ex) {
          this.lblMsj.setText("Ha ocurrido un problema");
        }
    }
    
     public void cargaTabla() {
        this.LimpiarTabla();
        DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
        TableColumnModel tcm = tabla.getColumnModel();
        Object[] filas=new Object[modelo.getColumnCount()];
        for(CodigoPrelacion c:lista){
                            filas[0]=c.getCod().toString();
                            filas[1]=this.formateo(c.getFecha());
                            filas[2]=this.obtenerHora(c.getFecha());
                            if(c.isPersistencia()){
                            filas[3]=1;
                            }
                            else{
                            filas[3]=0;   
                            }
                            RenderCodigos rr=new RenderCodigos(3);
                            tabla.setDefaultRenderer(Object.class, rr);
                            modelo.addRow(filas);
        }
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.changeSelection(modelo.getRowCount()-1, 0, false, false);
     
    }
     
     private String obtenerHora(Date marcaFecha) {
     String retorno="";
      if(marcaFecha!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
         retorno=formateador.format(marcaFecha);
      }
      
      return retorno;
    }
    
      private String formateo(Date hoy){
      String retorno="";
      if(hoy!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
         retorno=formateador.format(hoy);
      }
      return retorno;
     }  

    private void buscarCodigo(Integer cod) {
        int i=0;
        boolean esta=false;
        Codigo e=null;
        while(i<c.size() && !esta){
            if(c.get(i).getCod().equals(cod)){
                esta=true;
                e=c.get(i);
            }
            i++;
        }
        
        this.comboPrelacion.setSelectedItem(e);
    }
}
