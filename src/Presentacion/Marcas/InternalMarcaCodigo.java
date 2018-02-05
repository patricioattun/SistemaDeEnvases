
package Presentacion.Marcas;

import Dominio.Codigo;
import Dominio.Marca;
import Logica.LogCodigo;
import Logica.LogTripaliare;
import Presentacion.frmPrin;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.edisoncor.gui.textField.TextFieldRound;


public class InternalMarcaCodigo extends javax.swing.JInternalFrame {

    LogCodigo log;
    Marca marca;
    private ArrayList<Marca> marcas;
    Codigo cod;
    private InternalMarcas internal;
    private InternalListadoCod listadoCod;
    LogTripaliare trip;
    private static InternalMarcaCodigo instancia=null;
    JPasswordField pf = null;
    public InternalMarcaCodigo(LogTripaliare tripa,LogCodigo loga,InternalMarcas inte) throws SQLException, ClassNotFoundException {
        initComponents();
        log=loga;
        trip=tripa;
        this.txtCod.requestFocus();
        this.internal=inte;
        
    }

    
    public ArrayList<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(ArrayList<Marca> marcas) {
        this.marcas = marcas;
    }
    
    public Marca getMarca() {
        return marca;
    }

    public TextFieldRound getTxtCant() {
        return txtCant;
    }

    public void setTxtCant(TextFieldRound txtCant) {
        this.txtCant = txtCant;
    }

    public TextFieldRound getTxtCod() {
        return txtCod;
    }

    public void setTxtCod(TextFieldRound txtCod) {
        this.txtCod = txtCod;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public JLabel getLblNombres() {
        return lblNombres;
    }

    public void setLblNombres(JLabel lblNombres) {
        this.lblNombres = lblNombres;
    }
    
        
    public static InternalMarcaCodigo instancia(LogTripaliare tripa,LogCodigo loga,InternalMarcas inte) throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalMarcaCodigo(tripa,loga,inte);
         }
         return instancia;
      
   }

    public JLabel getLblMarca() {
        return lblMarca;
    }

    public void setLblMarca(JLabel lblMarca) {
        this.lblMarca = lblMarca;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonSeven1 = new org.edisoncor.gui.button.ButtonSeven();
        lblCant = new javax.swing.JLabel();
        txtCant = new org.edisoncor.gui.textField.TextFieldRound();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel6 = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        btnAceptar = new org.edisoncor.gui.button.ButtonIcon();
        lblMarca = new javax.swing.JLabel();
        txtCodDesc = new org.edisoncor.gui.textField.TextFieldRound();
        lblMsj = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        buttonSeven1.setText("buttonSeven1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ingresar Código");
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

        lblCant.setFont(new java.awt.Font("Euphemia", 1, 14)); // NOI18N
        lblCant.setText("Cantidad");

        txtCant.setBackground(new java.awt.Color(102, 153, 255));
        txtCant.setForeground(new java.awt.Color(255, 255, 255));
        txtCant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCant.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCant.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtCant.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantKeyTyped(evt);
            }
        });

        txtCod.setBackground(new java.awt.Color(102, 153, 255));
        txtCod.setForeground(new java.awt.Color(255, 255, 255));
        txtCod.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCod.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCod.setNextFocusableComponent(txtCant);
        txtCod.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtCod.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFocusLost(evt);
            }
        });
        txtCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Euphemia", 1, 14)); // NOI18N
        jLabel6.setText("Código");

        lblNombres.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblNombres.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombres.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ok.png"))); // NOI18N
        btnAceptar.setText("buttonIcon1");
        btnAceptar.setToolTipText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        lblMarca.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblMarca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMarca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtCodDesc.setBackground(new java.awt.Color(102, 153, 255));
        txtCodDesc.setForeground(new java.awt.Color(255, 255, 255));
        txtCodDesc.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCodDesc.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCodDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodDescKeyTyped(evt);
            }
        });

        lblMsj.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblMsj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setText("+ Desplegar listado de Códigos    Esc Salir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCant, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCodDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 74, Short.MAX_VALUE))
                    .addComponent(lblMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(lblMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCant))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
        this.txtCodDesc.setText("");
        char c=evt.getKeyChar();
        this.lblMsj.setText("");
        this.txtCant.setText("");
        this.lblCant.setText("Cantidad");
        if(!Character.isDigit(c)){
            evt.consume();
        }
        int k = (int) evt.getKeyChar();
        if(k==10){
            this.txtCant.requestFocus();
        }
        if(evt.getKeyChar()==43){
            try {
                listadoCod=InternalListadoCod.instancia(log,this);
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

    private void txtCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyTyped
        char c=evt.getKeyChar();
        int k = (int) evt.getKeyChar();
        this.lblMsj.setText("");
        if(!Character.isDigit(c)&& c!='.'){
            evt.consume();
        }
        if(k==10){
            this.btnAceptar.doClick();
        }
        if(k==27){
            this.dispose();
        }
    }//GEN-LAST:event_txtCantKeyTyped

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String str=this.txtCod.getText().trim();
        String strCant=this.txtCant.getText().trim();
        if(this.esNum(str) && this.esDouble(strCant)){
        Integer codigo=Integer.valueOf(this.txtCod.getText().trim());
        Double cant=Double.valueOf(this.txtCant.getText().trim());
        
       
        if(cant>0){
            if(marca!=null){
                    try {
                        if(codigo == 35 || codigo ==36){
                        Integer tope = this.log.calculoTopesCodigos(marca, codigo);
                        if(tope>0){
                            if(tope>=cant){
                                    marca.setSupervisado(1);
                                    if(this.log.insertarCodigoMarca(marca.getId(), marca.getFunCod(), codigo, cant) && this.trip.actualizaMarca(marca,null)==1){
                                        this.lblMsj.setText("Ingreso Exitoso");
                                        this.txtCant.setText("");
                                        this.txtCod.setText("");
                                        this.txtCodDesc.setText("");
                                        this.lblCant.setText("Cantidad");
                                        this.txtCod.requestFocus();
                                        this.txtCod.selectAll();
                                        this.internal.getBtnListar().doClick();
                                    }
                                    else{
                                        this.lblMsj.setText("Inténtelo de nuevo");
                                        this.txtCod.requestFocus();
                                        this.txtCod.selectAll();
                                    }
                            }
                            else{
                              this.lblMsj.setText("Saldo " +tope +". Ingrese una cantidad menor o igual al saldo");
                              this.txtCant.requestFocus();
                              this.txtCant.selectAll();
                            }
                        }
                        else{
                            this.lblMsj.setText("El saldo para este código está en cero");
                            this.txtCod.requestFocus();
                            this.txtCod.selectAll();
                        }
                        }
                        else{
                             marca.setSupervisado(1);
                                if(this.log.insertarCodigoMarca(marca.getId(), marca.getFunCod(), codigo, cant) && this.trip.actualizaMarca(marca,null)==1){
                                    this.lblMsj.setText("Ingreso Exitoso");
                                    this.txtCant.setText("");
                                    this.txtCod.setText("");
                                    this.txtCodDesc.setText("");
                                    this.lblCant.setText("Cantidad");
                                    this.txtCod.requestFocus();
                                    this.txtCod.selectAll();
                                    this.internal.getBtnListar().doClick();
                                }
                                else{
                                    this.lblMsj.setText("Inténtelo de nuevo");
                                    this.txtCod.requestFocus();
                                    this.txtCod.selectAll();
                                }
                        }
                        
                    } catch (SQLException ex) {
                        int respuesta = JOptionPane.showConfirmDialog(this, "Ya tiene ingreso del código "+ codigo +" para este funcionario y esta marca.¿Desea eliminarla?",null, JOptionPane.YES_NO_OPTION);
                        if(respuesta==0){
                            try {
                                if(this.abreInput().equals("1234")){
                                    if(this.log.borrarCodigoMarca(marca.getId(),marca.getFunCod(),codigo)){
                                        this.lblMsj.setText("Borrado Exitoso");
                                        this.txtCant.setText("");
                                        this.txtCod.setText("");
                                        this.txtCodDesc.setText("");
                                        this.txtCod.requestFocus();
                                        this.txtCod.selectAll();
                                        this.internal.getBtnListar().doClick();
                                    }
                                }
                            } catch (SQLException ex1) {
                                Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                        else{
                        this.txtCod.requestFocus();
                        this.txtCod.selectAll();
                        }
                       
                    } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            else if(marcas.size()>0){
                Integer i=0;
                for(Marca m:marcas){
                    m.setSupervisado(1);
                    try {
                        if(this.log.insertarCodigoMarca(m.getId(), m.getFunCod(), codigo, cant) && this.trip.actualizaMarca(m,null)==1){
                            i++;
                            this.txtCant.setText("");
                            this.txtCod.setText("");
                            this.txtCodDesc.setText("");
                            this.txtCod.requestFocus();
                            this.txtCod.selectAll();
                           
                        }
                    } catch (SQLException ex) {
                        int respuesta = JOptionPane.showConfirmDialog(this, "La marca "+this.formateo(m.getFechaUso())+", "+m.getHoraUso()+" del funcionario "+m.getFunCod()+" ya tiene ingresad el código "+ codigo +" .¿Desea eliminarla?",null, JOptionPane.YES_NO_OPTION);
                        if(respuesta==0){
                            try {
                                if(this.abreInput().equals("1234")){
                                    if(this.log.borrarCodigoMarca(m.getId(),m.getFunCod(),codigo)){
                                        this.lblMsj.setText("Borrado Exitoso");
                                        this.txtCant.setText("");
                                        this.txtCod.setText("");
                                        this.txtCodDesc.setText("");
                                        this.txtCod.requestFocus();
                                        this.txtCod.selectAll();
                                        
                                    }
                                }
                            } catch (SQLException ex1) {
                                Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(i>0){
                    this.lblMsj.setText("Se ingresó el código para "+i+" marcas");
                    this.txtCant.setText("");
                    this.txtCod.setText("");
                    this.txtCodDesc.setText("");
                    this.txtCod.requestFocus();
                    this.txtCod.selectAll();
                    this.internal.getBtnListar().doClick();
                }
                else{
                    this.lblMsj.setText("Inténtelo de nuevo");
                    this.txtCod.requestFocus();
                    this.txtCod.selectAll();
                    }
                this.internal.getBtnListar().doClick();
            }
        }
        else{
            this.lblMsj.setText("La cantidad debe ser mayor a 0");
            this.txtCant.requestFocus();
            this.txtCant.selectAll();
        }
        }
        else{
            this.lblMsj.setText("Verifique los campos");
            this.txtCod.requestFocus();
            this.txtCod.selectAll();
        }
        
    
     
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtCodDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodDescKeyTyped
        char c=evt.getKeyChar();
        evt.consume();
    }//GEN-LAST:event_txtCodDescKeyTyped

    private void txtCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFocusLost
        String str=this.txtCod.getText().trim();
        
        if(this.esNum(str)){
          Integer codigo=Integer.valueOf(this.txtCod.getText().trim());
        try {
            this.cod=this.log.obtenerCodigoTipo(codigo);
            if(cod!=null){
            this.txtCodDesc.setText(cod.toString());
            if(cod.getTipoUnidad()==1){
                this.lblCant.setText("Cantidad (Dias)");
            }
            else{ 
                this.lblCant.setText("Cantidad (Minutos)");
            }
            }
            else{
            this.txtCodDesc.setText("Código Incorrecto");
            this.txtCod.requestFocus();
            this.txtCod.selectAll();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InternalMarcaCodigo.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
            if(!str.equals("")){
                this.txtCodDesc.setText("Ingrese solo números");
                this.txtCod.requestFocus();
                this.txtCod.selectAll();
            }
        }
        
    }//GEN-LAST:event_txtCodFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnAceptar;
    private org.edisoncor.gui.button.ButtonSeven buttonSeven1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblCant;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JLabel lblNombres;
    private org.edisoncor.gui.textField.TextFieldRound txtCant;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    private org.edisoncor.gui.textField.TextFieldRound txtCodDesc;
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
    private Boolean esDouble(String num){
        Boolean es=true;
        Integer i=0;
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
                i++;
            }
        }
        return es;
    }
    
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
    private String formateo(Date hoy){
      String retorno="";
      if(hoy!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
         retorno=formateador.format(hoy);
      }
      return retorno;
     }  
}
