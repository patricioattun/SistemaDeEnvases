
package Presentacion.Liquidaciones;

import Dominio.Funcionario;
import Dominio.Ingreso;

import Logica.LogFuncionario;
import Dominio.Liquidacion;
import Logica.LogCodigo;
import Persistencia.BDExcepcion;
import Persistencia.PersistenciaLiquidacion;
import Presentacion.frmPrin;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.edisoncor.gui.button.ButtonIcon;
import org.edisoncor.gui.textField.TextFieldRound;


public class InternalLiquidaSueldo extends javax.swing.JInternalFrame {

   private Funcionario f=null;
   private LogFuncionario log;
   private LogCodigo logCod;
   private PersistenciaLiquidacion persLiq;
   private ArrayList<Ingreso> ingresos;
   private static InternalLiquidaSueldo instancia=null;
   
   public InternalLiquidaSueldo() throws ClassNotFoundException, SQLException {
        initComponents();
        log=new LogFuncionario();
        logCod=new LogCodigo();
        this.txtFechaLiq.setText(this.log.fechaLiquidacion());
        
    }

    public static InternalLiquidaSueldo getInstancia() {
        return instancia;
    }

    public static void setInstancia(InternalLiquidaSueldo instancia) {
        InternalLiquidaSueldo.instancia = instancia;
    }
   
   
    

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNumFunc = new org.edisoncor.gui.textField.TextFieldRound();
        btnBuscar = new org.edisoncor.gui.button.ButtonIcon();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        txtFechaLiq = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLiquidar = new org.edisoncor.gui.button.ButtonIcon();
        jLabel5 = new javax.swing.JLabel();
        lblMsj = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Liquidación de Sueldos");
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

        txtFechaLiq.setEditable(false);
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

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Nro. Funcionario ");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Nombre de Funcionario");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Fecha de Liquidación");

        btnLiquidar.setBackground(new java.awt.Color(102, 153, 255));
        btnLiquidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/calcular.png"))); // NOI18N
        btnLiquidar.setText("buttonIcon1");
        btnLiquidar.setToolTipText("Buscar");
        btnLiquidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiquidarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("LIQUIDAR");

        lblMsj.setFont(new java.awt.Font("Ebrima", 1, 8)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Para liquidar un funcionario específico debe cargarlo previamente,");

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("de lo contrario se liquidarán todos los funcionarios.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(38, 38, 38)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnLiquidar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumFuncFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFuncFocusGained
        this.txtNumFunc.selectAll();
    }//GEN-LAST:event_txtNumFuncFocusGained

     public static InternalLiquidaSueldo instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalLiquidaSueldo();
         }
         return instancia;
      
   }

    public TextFieldRound getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(TextFieldRound txtNombre) {
        this.txtNombre = txtNombre;
    }
     
     
    public TextFieldRound getTxtNumFunc() {
        return txtNumFunc;
    }

    public void setTxtNumFunc(TextFieldRound txtNumFunc) {
        this.txtNumFunc = txtNumFunc;
    }

    public ButtonIcon getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(ButtonIcon btnBuscar) {
        this.btnBuscar = btnBuscar;
    }
    
    
     
    private void txtNumFuncKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFuncKeyTyped
        this.f=null;
        this.ingresos=null;
        this.txtNombre.setText("");
        
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
            this.txtNumFunc.setText("");
            try {
               InternalListadoFuncFiltro listadoFunc=InternalListadoFuncFiltro.instancia(log,11);
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
               this.lblMsj.setText(ex.getMessage()); 
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
        String numFunc=this.txtNumFunc.getText();
        
        if(this.esNum(numFunc)){
            
            try {
                f = this.log.buscarFuncionario(numFunc);
                if(f!=null){
                   this.txtNombre.setText(f.getNomCompletoApe());
                  
                                     
                }
                else{
                    this.txtNumFunc.requestFocus();
                    this.txtNombre.setText("El funcionario no existe");
                    this.txtNumFunc.selectAll();
                    f=null;
                    
                }
            } catch (ClassNotFoundException | SQLException ex) {
                this.lblMsj.setText(ex.getMessage());
            }

        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtFechaLiqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaLiqKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaLiqKeyTyped

    private void btnLiquidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiquidarActionPerformed
       try {
           if(this.logCod.bloqueoContaduria()==0){
           Date fecha = this.stringADate(this.txtFechaLiq.getText());
           this.persLiq = new PersistenciaLiquidacion(fecha,1);
           if(f!=null){
               ingresos=this.persLiq.verificoPrelacion(f);
               if(ingresos.isEmpty()){
                    this.persLiq.liquidar(f,fecha,1,false);
                    
                        InternalLiquidaSueldo.instancia().getTxtNombre().setText("Liquidación Finalizada");
                        InternalLiquidaSueldo.instancia().getTxtNumFunc().setText("");
                        InternalLiquidaSueldo.instancia().repaint();
                        InternalLiquidaSueldo.instancia().getTxtNombre().paint( InternalLiquidaSueldo.instancia().getTxtNombre().getGraphics());
                        InternalLiquidaSueldo.instancia().getTxtNumFunc().paint( InternalLiquidaSueldo.instancia().getTxtNumFunc().getGraphics());
                        InternalLiquidaSueldo.instancia().revalidate();
                        f=null;
                        this.log.actualizaParametros("1", "0", "BLOQUEO_CONTADURIA");
               }else{
                    this.cargoNoPrelacion(ingresos, false);
               }
           }
           else{
               ingresos=this.persLiq.verificoPrelacion(null);
               if(ingresos.isEmpty()){
               ArrayList<Funcionario> funcionarios = this.log.listadoFuncionariosActivos(""); 
                    if(funcionarios!=null){
                        if(funcionarios.size()>0){
                               for(Funcionario k: funcionarios){
                                    InternalLiquidaSueldo.instancia().getTxtNombre().setText(k.getNomCompletoApe());
                                    InternalLiquidaSueldo.instancia().getTxtNumFunc().setText(k.getCodFunc().toString());
                                    InternalLiquidaSueldo.instancia().repaint();
                                    InternalLiquidaSueldo.instancia().getTxtNumFunc().paint( InternalLiquidaSueldo.instancia().getTxtNumFunc().getGraphics());
                                    InternalLiquidaSueldo.instancia().getTxtNombre().paint( InternalLiquidaSueldo.instancia().getTxtNombre().getGraphics());
                                    InternalLiquidaSueldo.instancia().revalidate();
                                    this.persLiq.liquidar(k,fecha,1,false);
                                }
                                    InternalLiquidaSueldo.instancia().getTxtNombre().setText("Liquidación Finalizada");
                                    InternalLiquidaSueldo.instancia().getTxtNumFunc().setText("");
                                    InternalLiquidaSueldo.instancia().repaint();
                                    InternalLiquidaSueldo.instancia().getTxtNombre().paint( InternalLiquidaSueldo.instancia().getTxtNombre().getGraphics());
                                    InternalLiquidaSueldo.instancia().getTxtNumFunc().paint( InternalLiquidaSueldo.instancia().getTxtNumFunc().getGraphics());
                                    InternalLiquidaSueldo.instancia().revalidate();
                                    f=null;
                                    this.log.actualizaParametros("1", "0", "BLOQUEO_CONTADURIA");

                        }
                    }
                  
               }else{
                  this.cargoNoPrelacion(ingresos,true);
               }
           }
           
           
           
           }else{
               JOptionPane.showMessageDialog(this, "En estos momentos está bloqueado. Vuelva a activar la liquidación");
           }
       } catch (ParseException | ClassNotFoundException | SQLException | BDExcepcion ex) {
           this.lblMsj.setText(ex.getMessage());
            Logger.getLogger(InternalLiquidaSueldo.class.getName()).log(Level.SEVERE, null, ex);
           System.out.println(ex.getMessage());
       }
    }//GEN-LAST:event_btnLiquidarActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       instancia=null;
       try {
           this.log.actualizaParametro("", "USUARIO_LIQ");
       } catch (BDExcepcion ex) {
           Logger.getLogger(InternalLiquidaSueldo.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_formInternalFrameClosed

    private Date stringADate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(s);
        return date;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnBuscar;
    private org.edisoncor.gui.button.ButtonIcon btnLiquidar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblMsj;
    private org.edisoncor.gui.textField.TextFieldRound txtFechaLiq;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    private org.edisoncor.gui.textField.TextFieldRound txtNumFunc;
    // End of variables declaration//GEN-END:variables

    private void cargoNoPrelacion(ArrayList<Ingreso> ingresos, boolean todos) {
       try {
           InternalNoPrelaciones internal=InternalNoPrelaciones.instancia(ingresos,todos);
           if (!internal.isVisible()) {
               frmPrin.instancia().getDesktop().add(internal);
               internal.setLocation((frmPrin.instancia().getDesktop().getWidth()/2)-(internal.getWidth()/2),(frmPrin.instancia().getDesktop().getHeight()/2) - internal.getHeight()/2);
               internal.setVisible(true);
                       
           }
           else{
               internal.requestFocus();
               try {
                   internal.setSelected(true);
                   //internalFijoPorCod.getTxtCod().requestFocus();
               } catch (PropertyVetoException ex) {
                   //lblMensaje.setText(ex.getMessage());
               }
           }
           
           // this.pnlContenido.add(pnlAjustes);
           this.repaint();
           this.revalidate();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
