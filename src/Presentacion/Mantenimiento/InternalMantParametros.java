
package Presentacion.Mantenimiento;

import Dominio.Parametro;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


public class InternalMantParametros extends javax.swing.JInternalFrame {

    ArrayList<Parametro> parametros;
    LogFuncionario log;
    private static InternalMantParametros instancia=null;
    public InternalMantParametros() throws ClassNotFoundException, SQLException, BDExcepcion {
        initComponents();
        this.log=new LogFuncionario();
        parametros=log.cargoParametros();
        this.cargoTabla();
    }
    
   public static InternalMantParametros instancia() throws BDExcepcion, SQLException, ClassNotFoundException
   {    
         if (instancia== null)
         {
            instancia = new InternalMantParametros();
         }
         return instancia;
      
   }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnAceptar6 = new org.edisoncor.gui.button.ButtonIcon();
        lblMsj = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Modificar Parámetros");
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
                "Nombre", "Valor", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla);

        btnAceptar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Ok.png"))); // NOI18N
        btnAceptar6.setText("buttonIcon1");
        btnAceptar6.setToolTipText("Aceptar");
        btnAceptar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptar6ActionPerformed(evt);
            }
        });

        lblMsj.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMsj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAceptar6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMsj, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptar6ActionPerformed
        try {
            this.lblMsj.setText("");
            this.cargarParametros();
            if(parametros!=null){
            Integer total=this.log.actualizoParametros(parametros);
            this.lblMsj.setText("Se han actualizado "+total+" parámetro/s.");
            parametros=log.cargoParametros();
            this.cargoTabla();
            }
            else{
            parametros=log.cargoParametros();    
            }
        } catch (BDExcepcion ex) {
            this.lblMsj.setText("Ha ocurrido un problema.");
        }
        
    }//GEN-LAST:event_btnAceptar6ActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void cargarParametros(){
       DefaultTableModel tmMov=null;
       tmMov = (DefaultTableModel) tabla.getModel();
            for(int i=0;i<parametros.size();i++){
                String str=String.valueOf(tmMov.getValueAt(i, 1)).trim();
                if(esDouble(str)){
                    if(Double.valueOf(parametros.get(i).getValor())<1){
                    java.math.BigDecimal valor = new java.math.BigDecimal(str);
                    str=String.valueOf(valor.divide(new BigDecimal("100")));
                    }
                    if(!parametros.get(i).getValor().trim().equals(str)){
                        parametros.get(i).setValorNuevo(str);
                    }
                }
                else{
                    this.lblMsj.setText("Existen valores no numéricos en "+parametros.get(i).getNombre().trim());
                    i=parametros.size();
                    parametros=null;
                    break;
                }
            }
    }
    
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnAceptar6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables

    private void cargoTabla() {
        this.LimpiarTabla();
      if(parametros!=null){
          if(parametros.size()>0){
               DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
               Object[] filas=new Object[modelo.getColumnCount()];
               for(Parametro p:parametros){
                   if(p.getModificable()==1){
                        filas[0]=p.getNombre();
                        if(Double.valueOf(p.getValor())<1){
                        java.math.BigDecimal valor = new java.math.BigDecimal(p.getValor());
                        filas[1]=String.valueOf(valor.multiply(new BigDecimal("100")));
                        filas[2]="%";
                        }
                        else{
                        filas[1]=p.getValor();    
                        filas[2]="Numérico";
                        }
                        modelo.addRow(filas);
                   }
               }
          }
      }
    }
        public void LimpiarTabla() {
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }
    
    

}
