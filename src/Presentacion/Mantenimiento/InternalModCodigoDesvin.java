
package Presentacion.Mantenimiento;

import Dominio.CodigoDesvinc;
import Logica.LogFuncionario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InternalModCodigoDesvin extends javax.swing.JInternalFrame {

 private LogFuncionario fun;
 private CodigoDesvinc c;
 private static InternalModCodigoDesvin instancia=null;
    public InternalModCodigoDesvin() throws ClassNotFoundException, SQLException {
        initComponents();
        this.fun=new LogFuncionario();
        this.cargaCodigo();
    }
    
    public static InternalModCodigoDesvin instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalModCodigoDesvin();
         }
         return instancia;
      
   }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txtDesc = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel3 = new javax.swing.JLabel();
        btnAceptar = new org.edisoncor.gui.button.ButtonIcon();
        btnCancelar1 = new org.edisoncor.gui.button.ButtonIcon();
        comboCodigo = new javax.swing.JComboBox();
        lblNuevo = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mantenimiento Códigos Egreso", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Ebrima", 1, 18))); // NOI18N

        txtDesc.setBackground(new java.awt.Color(102, 153, 255));
        txtDesc.setForeground(new java.awt.Color(255, 255, 255));
        txtDesc.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDesc.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Euphemia", 1, 14)); // NOI18N
        jLabel1.setText("Códigos Existentes");

        jLabel2.setFont(new java.awt.Font("Euphemia", 1, 14)); // NOI18N
        jLabel2.setText("Código");

        txtCod.setBackground(new java.awt.Color(102, 153, 255));
        txtCod.setForeground(new java.awt.Color(255, 255, 255));
        txtCod.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCod.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Euphemia", 1, 14)); // NOI18N
        jLabel3.setText("Descripción");

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ok.png"))); // NOI18N
        btnAceptar.setText("buttonIcon1");
        btnAceptar.setToolTipText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnCancelar1.setText("buttonIcon1");
        btnCancelar1.setToolTipText("Eliminar Banco");
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });

        comboCodigo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCodigoItemStateChanged(evt);
            }
        });
        comboCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                comboCodigoMouseReleased(evt);
            }
        });

        lblNuevo.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        lblNuevo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboCodigo, 0, 536, Short.MAX_VALUE)
                            .addComponent(txtDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 763, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(9, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(9, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargaCodigo() throws SQLException, ClassNotFoundException{
      ArrayList<CodigoDesvinc> cod=fun.cargaComboCodigoDesvinc();
      this.comboCodigo.addItem("INGRESAR NUEVO");  
      if(cod.size()>0){
        for(int i=0; i<cod.size();i++){
            this.comboCodigo.addItem(cod.get(i));
            this.comboCodigo.setSelectedIndex(0);
        }
      }
     }
    
    
    private void txtDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescKeyTyped
        this.lblNuevo.setText("");
        int k = (int) evt.getKeyChar();
        if(k==10){
            this.btnAceptar.doClick();
        }
        if(this.txtDesc.getText().length()==200){
            evt.consume();
        }
    }//GEN-LAST:event_txtDescKeyTyped

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
        this.lblNuevo.setText("");
        int k = (int) evt.getKeyChar();

        if (k >= 97 && k <= 122 || k >= 65 && k <= 90 || k==32||k==46||k==45||k==8||k==47||k==42||k==43)  {

            evt.consume();
        }
        if(k==10){

            this.txtDesc.requestFocus();
        }
    }//GEN-LAST:event_txtCodKeyTyped

    private void comboCodigoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCodigoItemStateChanged
    if(this.comboCodigo.getSelectedIndex()!=0){
            CodigoDesvinc c=(CodigoDesvinc) this.comboCodigo.getSelectedItem();
            if(c!=null){
                String desc=c.getDescripcion();
                Integer id=c.getId();
                this.txtCod.setText(id.toString());
                this.txtDesc.setText(desc);
                this.lblNuevo.setText("");
                
            }
            
        }
    else{
        this.txtCod.setText("");
        this.txtDesc.setText("");
        //this.lblNuevo.setText("");
    }
        
    }//GEN-LAST:event_comboCodigoItemStateChanged

    private void comboCodigoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboCodigoMouseReleased
        this.txtCod.requestFocus();
    }//GEN-LAST:event_comboCodigoMouseReleased

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
       if(this.comboCodigo.getSelectedIndex()!=0){
            c=(CodigoDesvinc) this.comboCodigo.getSelectedItem();
        }
        else{
            c=null;
        }
        String desc=this.txtDesc.getText().toUpperCase();
        String cod=this.txtCod.getText();
        
        if(c!=null){
            if(this.esNum(cod)&&!desc.equals("")){

                Integer idNueva=Integer.valueOf(cod);
                Integer idVieja=c.getId();
                CodigoDesvinc codi=new CodigoDesvinc();
                codi.setDescripcion(desc);
                codi.setId(idNueva);
                try {
                    if(this.fun.modificaCodDesvinc(idVieja,codi)==1){
                        this.lblNuevo.setText("Código actualizado");
                        
                    }
                    else if(this.fun.modificaCodDesvinc(idVieja,codi)==5){
                        this.lblNuevo.setText("Este código está asignado, solo se modificó la descripción");
                    }
                   else{
                        this.lblNuevo.setText("No se ha podido actualizar el Banco");
                    }
                } catch (SQLException ex) {
                    this.lblNuevo.setText("No puede asignar un número de código ya en uso");
                }
            }

            else{
                this.lblNuevo.setText("Verifique los campos obligatorios");
            }
        }
           else{
            if(this.esNum(cod)&&!desc.equals("")){
               Integer idNueva=Integer.valueOf(cod);
                try {
                    if(!this.fun.existeCodigoDesvinc(idNueva)){
                        CodigoDesvinc codi=new CodigoDesvinc();
                        codi.setDescripcion(desc);
                        codi.setId(idNueva);
                        if(this.fun.altaCodigoDesvinc(codi)){
                            this.lblNuevo.setText("Nuevo Código Ingresado");
                        }
                    }
                    else{
                        this.lblNuevo.setText("El código que desea ingresar ya existe");
                    }} catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalModCodigoDesvin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(InternalModCodigoDesvin.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                else{
                    this.lblNuevo.setText("Verifique los campos obligatorios");
                }
            }
        this.comboCodigo.removeAllItems();
     try {
         this.cargaCodigo();
     } catch (SQLException ex) {
         Logger.getLogger(InternalModCodigoDesvin.class.getName()).log(Level.SEVERE, null, ex);
     } catch (ClassNotFoundException ex) {
         Logger.getLogger(InternalModCodigoDesvin.class.getName()).log(Level.SEVERE, null, ex);
     }
     
            this.comboCodigo.repaint();
            this.comboCodigo.revalidate();
            this.comboCodigo.setSelectedIndex(0);
            this.txtCod.setText("");
            this.txtDesc.setText("");
            this.txtCod.requestFocus();
     
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        if(this.comboCodigo.getSelectedIndex()!=0){
           c=(CodigoDesvinc) this.comboCodigo.getSelectedItem();
        }
        else{
            c=null;
        }
        String cod=this.txtCod.getText();
        
        if(c!=null){
            try {
                if(!this.fun.CodigoDesvincUsado(c.getId())){
                     if(this.fun.bajaCodigoDesvinc(c.getId())){
                        this.lblNuevo.setText("Código eliminado");
                    }
                }
                else{
                    this.lblNuevo.setText("Este código no se puede eliminar, actualmente está asignado");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InternalModCodigoDesvin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InternalModCodigoDesvin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            this.lblNuevo.setText("Seleccione un código para eliminar");
        }
        this.comboCodigo.removeAllItems();
     try {
         this.cargaCodigo();
     } catch (SQLException ex) {
         Logger.getLogger(InternalModCodigoDesvin.class.getName()).log(Level.SEVERE, null, ex);
     } catch (ClassNotFoundException ex) {
         Logger.getLogger(InternalModCodigoDesvin.class.getName()).log(Level.SEVERE, null, ex);
     }
     
            this.comboCodigo.repaint();
            this.comboCodigo.revalidate();
            this.comboCodigo.setSelectedIndex(0);
            this.txtCod.setText("");
            this.txtDesc.setText("");
            this.txtCod.requestFocus();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    
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
    private org.edisoncor.gui.button.ButtonIcon btnAceptar;
    private org.edisoncor.gui.button.ButtonIcon btnCancelar1;
    private javax.swing.JComboBox comboCodigo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblNuevo;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    private org.edisoncor.gui.textField.TextFieldRound txtDesc;
    // End of variables declaration//GEN-END:variables
}
