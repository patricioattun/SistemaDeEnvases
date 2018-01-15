
package Presentacion.Mantenimiento;

import Dominio.Feriado;
import Logica.LogFuncionario;


import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InternalModFeriado extends javax.swing.JInternalFrame {
    
    private LogFuncionario log;
    private ArrayList<Feriado> feriados=null;
    private Feriado f=null;
    DefaultTableModel tm=null;
    private static InternalModFeriado instancia=null;
    
    private InternalModFeriado() throws ClassNotFoundException, SQLException, ParseException {
        initComponents();
        this.log=new LogFuncionario();
        this.cargaCombo();
        this.jPanel1.setVisible(true);
        if(this.comboAño.getItemCount()>0){
        feriados=this.log.listarFeriados(Integer.valueOf(this.comboAño.getSelectedItem().toString()));
        }
       
        
         tm = (DefaultTableModel) tablaFeriado.getModel();
         tablaFeriado.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                   
                    try {
                        modificarFeriado(e);
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                 }
         }});
         this.cargarTabla();
    }

    
     public static InternalModFeriado instancia() throws ClassNotFoundException, SQLException, ParseException
   {    
         if (instancia== null)
         {
            instancia = new InternalModFeriado();
         }
         return instancia;
      
   }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtDesc = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFeriado = new javax.swing.JTable();
        btnAceptar = new org.edisoncor.gui.button.ButtonIcon();
        buttonAqua2 = new org.edisoncor.gui.button.ButtonIcon();
        radioLab = new javax.swing.JRadioButton();
        radioNoLab = new javax.swing.JRadioButton();
        lblMsg = new javax.swing.JLabel();
        comboAño = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mantenimiento Feriados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 18))); // NOI18N

        txtDesc.setBackground(new java.awt.Color(102, 153, 255));
        txtDesc.setForeground(new java.awt.Color(255, 255, 255));
        txtDesc.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDesc.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Euphemia", 1, 14)); // NOI18N
        jLabel2.setText("Descripción");

        txtFecha.setDateFormatString("dd/MM/yyyy");

        jLabel4.setFont(new java.awt.Font("Euphemia", 1, 14)); // NOI18N
        jLabel4.setText("Fecha Inicio");

        tablaFeriado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Fecha", "Descripcion", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaFeriado);

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ok.png"))); // NOI18N
        btnAceptar.setText("buttonIcon1");
        btnAceptar.setToolTipText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        buttonAqua2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        buttonAqua2.setText("buttonIcon1");
        buttonAqua2.setToolTipText("Eliminar Feriado");
        buttonAqua2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAqua2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioLab);
        radioLab.setSelected(true);
        radioLab.setText("Laborable");

        buttonGroup1.add(radioNoLab);
        radioNoLab.setText("No Laborable");

        lblMsg.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        lblMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboAño.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboAñoItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Listar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(radioLab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(radioNoLab)
                        .addGap(52, 52, 52))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(comboAño, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(63, 63, 63)
                                .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonAqua2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioLab)
                    .addComponent(radioNoLab))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAqua2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescKeyTyped
        this.lblMsg.setText("");
        int k = (int) evt.getKeyChar();
        if(this.txtDesc.getText().length()==30){
            evt.consume();
        }
    }//GEN-LAST:event_txtDescKeyTyped

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String descripcion=this.txtDesc.getText();
        Date fecha=this.txtFecha.getDate();
        Integer tipo=-1;
        if(this.radioLab.isSelected()){
            tipo=1;
        }
        else{
            if(this.radioNoLab.isSelected())
            tipo=0;
        }
        try {
            if(!"".equals(descripcion)&&fecha!=null){
                if(f==null) {
                    if(this.log.insertarFeriado(descripcion, fecha, tipo)){

                        this.comboAño.removeAllItems();
                        this.cargaCombo();
                        this.comboAño.setSelectedItem(this.obtenerAño1(fecha));
                        feriados=this.log.listarFeriados(Integer.valueOf(this.comboAño.getSelectedItem().toString()));
                        this.cargarTabla();
                        this.limpiar();
                        this.lblMsg.setText("Feriado ingresado");
                    }
                }
                else{
                    f.setDescripcion(descripcion);

                    f.setTipo(tipo);
                    Date hoy=new Date();
                    if((hoy.before(f.getFecha())||hoy.equals(f.getFecha()))&&(hoy.before(fecha)||hoy.equals(fecha))){
                        f.setFecha(fecha);
                        if(this.log.modificarFeriado(f)){
                            this.comboAño.removeAllItems();
                            this.cargaCombo();
                            this.feriados=this.log.listarFeriados(Integer.valueOf(this.comboAño.getSelectedItem().toString()));
                            this.cargarTabla();
                            this.limpiar();
                            this.lblMsg.setText("Modificación Exitosa");
                        }

                    }
                    else{
                        this.lblMsg.setText("No puede modificar un feriado anterior a hoy");
                    }
                }
            }
            else{
                this.lblMsg.setText("Verifique los campos");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {

            this.lblMsg.setText("Este Feriado ya fue ingresado");
        } catch (ParseException ex) {
            Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void buttonAqua2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAqua2ActionPerformed
        if(f!=null) {
            Date hoy=new Date();
            if(hoy.before(f.getFecha())||hoy.equals(f.getFecha())){
                try {
                    if(this.log.eliminarFeriado(f)){
                        this.feriados=this.log.listarFeriados(Integer.valueOf(this.comboAño.getSelectedItem().toString()));
                        this.comboAño.removeAllItems();
                        this.cargaCombo();
                        this.cargarTabla();
                        this.limpiar();
                        this.lblMsg.setText("Feriado Eliminado");
                    }   } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    this.limpiar();
                    this.lblMsg.setText("No se puede eliminar un feriado anterior a hoy");

                }
            }
            else{
                this.lblMsg.setText("Seleccione un feriado para eliminar");
            }
    }//GEN-LAST:event_buttonAqua2ActionPerformed

    private void comboAñoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboAñoItemStateChanged
        this.limpiar();
        try {
            if(this.comboAño.getItemCount()>0){
                this.feriados=this.log.listarFeriados(Integer.valueOf(this.comboAño.getSelectedItem().toString()));
                this.cargarTabla();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(InternalModFeriado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_comboAñoItemStateChanged

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed
    
      private void cargaCombo() throws ClassNotFoundException, SQLException {
        ArrayList<String> lista=this.log.cargaComboFeriadoAñosDistintos();
        for(String s:lista){
            this.comboAño.addItem(s);
        }
    }
      
         private void modificarFeriado(java.awt.event.MouseEvent e) throws SQLException, ClassNotFoundException{
     
     Integer m=this.tablaFeriado.rowAtPoint(e.getPoint());
     String codigo=String.valueOf(tm.getValueAt(m,0));
     String fecha=String.valueOf(tm.getValueAt(m,1));
     String descripcion=String.valueOf(tm.getValueAt(m,2));
     String tipo=String.valueOf(tm.getValueAt(m,3));
     this.f=new Feriado();
     this.f.setCodigo(Integer.valueOf(codigo));
     this.f.setFecha(this.fechaStringaDate(fecha));
        this.txtDesc.setText(descripcion);
        this.txtFecha.setDate(this.fechaStringaDate(fecha));
        if(tipo.equals("Laborable")){
            this.radioLab.setSelected(true);
        }
        else{
            this.radioNoLab.setSelected(true);
        }
     this.lblMsg.setText("Pronto para Modificar o Eliminar");
    }

     private void cargarTabla() throws SQLException, ClassNotFoundException, ParseException{
     
       this.LimpiarTabla();
       DefaultTableModel modelo = (DefaultTableModel)tablaFeriado.getModel();
        
        Object[] filas=new Object[modelo.getColumnCount()];
        
        if(feriados!=null){     
        if(feriados.size()>0){
                                                          
                    for(Feriado f:feriados){                       
                            
                            filas[0]=f.getCodigo();
                            filas[1]=this.convertirFecha(f.getFecha());
                            filas[2]=f.getDescripcion();  
                            if(f.getTipo()==1){
                            filas[3]="Laborable";
                            }
                            else{
                            filas[3]="No Laborable";    
                            }
                            modelo.addRow(filas);
                      }
             JTableHeader th; 
             th = tablaFeriado.getTableHeader(); 
             Font fuente = new Font("Ebrima", Font.BOLD, 14); 
             th.setBackground(Color.LIGHT_GRAY);
             th.setFont(fuente); 
             //this.jPanel1.setVisible(true);
                }
        }
    }
     
         private String obtenerAño1(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.YEAR));
        return t;
    }
         
         private void LimpiarTabla() {
        DefaultTableModel modelo=(DefaultTableModel) tablaFeriado.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaFeriado.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }
    private void limpiar(){
        this.txtDesc.setText("");
        this.radioLab.setSelected(true);
        this.txtFecha.setDate(null);
        this.lblMsg.setText("");
        this.f=null;
    }
    
    private Date fechaStringaDate(String str){
    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
    Date fecha = null;
        try {

        fecha = formatoDelTexto.parse(str);

        } catch (ParseException ex) {

        ex.printStackTrace();

        }

        return fecha;
    }
         
    
    private String convertirFecha(Date fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
        }
        return str;
    }   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnAceptar;
    private org.edisoncor.gui.button.ButtonIcon buttonAqua2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox comboAño;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JRadioButton radioLab;
    private javax.swing.JRadioButton radioNoLab;
    private javax.swing.JTable tablaFeriado;
    private org.edisoncor.gui.textField.TextFieldRound txtDesc;
    private com.toedter.calendar.JDateChooser txtFecha;
    // End of variables declaration//GEN-END:variables
}
