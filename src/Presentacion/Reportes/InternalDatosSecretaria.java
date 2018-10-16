
package Presentacion.Reportes;

import Dominio.Funcionario;
import Dominio.Horario;
import Logica.LogFuncionario;
import Logica.LogHorario;
import Persistencia.BDExcepcion;
import Presentacion.Mantenimiento.InternalModFunc;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


public class InternalDatosSecretaria extends javax.swing.JInternalFrame {
    ArrayList<Horario> listaHorarios;
    private LogFuncionario log;
    private LogHorario logH;
    private Funcionario f;
    private static InternalDatosSecretaria instancia=null;
    public InternalDatosSecretaria(String codFunc) throws ClassNotFoundException, SQLException, BDExcepcion {
        initComponents();
        log=new LogFuncionario();
        logH=new LogHorario();
        f=this.log.funcParcial(codFunc);
        listaHorarios=f.getHorarios();
        this.recargaTabla();
        this.vencimientoCarneSalud.setDate(f.getVencimientoCarne());
        this.lblNombre.setText(f.getNomCompletoYNum());
        this.cargaHorarios();
    }

    
       public static InternalDatosSecretaria instancia(String codFunc) throws ClassNotFoundException, SQLException, BDExcepcion
   {    
         if (instancia== null)
         {
            instancia = new InternalDatosSecretaria(codFunc);
         }
         return instancia;
      
   }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboHorario = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnAgregarHorario = new org.edisoncor.gui.button.ButtonIcon();
        btnQuitarHorario = new org.edisoncor.gui.button.ButtonIcon();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        vencimientoCarneSalud = new com.toedter.calendar.JDateChooser();
        buttonAqua4 = new org.edisoncor.gui.button.ButtonIcon();
        lblHorarioMensaje = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Actualización Datos Secretaría");
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

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Horarios"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        btnAgregarHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar.png"))); // NOI18N
        btnAgregarHorario.setText("buttonIcon2");
        btnAgregarHorario.setToolTipText("Agregar Horario");
        btnAgregarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarHorarioActionPerformed(evt);
            }
        });

        btnQuitarHorario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/quitar.png"))); // NOI18N
        btnQuitarHorario.setText("buttonIcon2");
        btnQuitarHorario.setToolTipText("Quitar Horario");
        btnQuitarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarHorarioActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel31.setText("Carné de Salud");

        jLabel32.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel32.setText("Horario");

        vencimientoCarneSalud.setToolTipText("");
        vencimientoCarneSalud.setDateFormatString("dd/MM/yyyy");

        buttonAqua4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ok.png"))); // NOI18N
        buttonAqua4.setText("buttonIcon1");
        buttonAqua4.setToolTipText("Aceptar");
        buttonAqua4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAqua4ActionPerformed(evt);
            }
        });

        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vencimientoCarneSalud, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblHorarioMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboHorario, 0, 414, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAgregarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnQuitarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addComponent(buttonAqua4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnQuitarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vencimientoCarneSalud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblHorarioMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonAqua4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarHorarioActionPerformed
        this.lblHorarioMensaje.setVisible(false);

        if(this.comboHorario.getSelectedIndex()>-1){
            String s=this.comboHorario.getSelectedItem().toString();
            if(listaHorarios.size()<3){
                Horario hor=(Horario) this.comboHorario.getSelectedItem();
                if(!this.horarioEsta(hor)){
                    this.listaHorarios.add(hor);
                    this.recargaTabla();

                }
                else{
                    this.lblHorarioMensaje.setText("Este horario ya está");
                    this.lblHorarioMensaje.setVisible(true);
                }

            }
            else{
                this.lblHorarioMensaje.setText("No puede agregar mas de 3 horarios");
                this.lblHorarioMensaje.setVisible(true);
            }

        }
        else{
            this.lblHorarioMensaje.setText("Seleccione un horario");
            this.lblHorarioMensaje.setVisible(true);
        }

    }//GEN-LAST:event_btnAgregarHorarioActionPerformed

    private void btnQuitarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarHorarioActionPerformed
        this.lblHorarioMensaje.setVisible(false);
        Integer pos=this.tabla.getSelectedRow();
        if(pos!=null){
            if(pos>=0){
                this.listaHorarios.remove((int)pos);
                this.recargaTabla();
            }
            else{
                this.lblHorarioMensaje.setText("Seleccione un horario");
                this.lblHorarioMensaje.setVisible(true);
            }
        }
        else{
            this.lblHorarioMensaje.setText("No hay mas elementos para quitar");
            this.lblHorarioMensaje.setVisible(true);
        }
    }//GEN-LAST:event_btnQuitarHorarioActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void buttonAqua4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAqua4ActionPerformed
       Date carnet = this.vencimientoCarneSalud.getDate();
       this.lblHorarioMensaje.setText("");
       if(this.listaHorarios.size()>0){
           try {
               this.f.setVencimientoCarne(carnet);
               this.f.setHorarios(listaHorarios);
               if(this.log.modFuncionarioSecretaria(f)){
                   this.lblHorarioMensaje.setText("Modificación exitosa");
                   InternalVencimientoCarne inter = InternalVencimientoCarne.instancia();
                   inter.getBtnListar().doClick();
                   this.lblHorarioMensaje.setText("Modificación exitosa");
                   
               }
           } catch (SQLException ex) {
               Logger.getLogger(InternalDatosSecretaria.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(InternalDatosSecretaria.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }//GEN-LAST:event_buttonAqua4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnAgregarHorario;
    private org.edisoncor.gui.button.ButtonIcon btnQuitarHorario;
    private org.edisoncor.gui.button.ButtonIcon buttonAqua4;
    private javax.swing.JComboBox comboHorario;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHorarioMensaje;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tabla;
    private com.toedter.calendar.JDateChooser vencimientoCarneSalud;
    // End of variables declaration//GEN-END:variables
private boolean horarioEsta(Horario hor) {
        boolean esta = false;
        int i = 0;
        if(listaHorarios.size()>0){
            while(i<this.listaHorarios.size()&&!esta){
                if(hor.getCodigo().equals(listaHorarios.get(i).getCodigo())){
                    esta=true;
                }
                i++;
            }
        }
        return esta;
    }

    

    private void recargaTabla() {
        this.LimpiarTabla();
        DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
        Object[] filas=new Object[modelo.getColumnCount()];
    
       for(Horario h: listaHorarios){
              filas[0]=h.toString();
              modelo.addRow(filas);
       }
    }
    private void LimpiarTabla() {
        
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }

    private void cargaHorarios() {
        try {
            ArrayList<Horario> horarios=logH.cargaComboHorario();
            for(int i=0; i<horarios.size();i++){
                
                this.comboHorario.addItem(horarios.get(i));
                this.comboHorario.setSelectedIndex(-1);
            } } catch (SQLException ex) {
            Logger.getLogger(InternalDatosSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalDatosSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
