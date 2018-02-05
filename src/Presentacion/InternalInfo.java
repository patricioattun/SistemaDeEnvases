
package Presentacion;

import Dominio.Funcionario;
import Logica.LogFuncionario;
import Presentacion.Licencias.InternalCalcular;
import java.awt.Dimension;
import java.awt.Graphics;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;


public class InternalInfo extends javax.swing.JInternalFrame {
    private String usuario;
    LogFuncionario logF;
    private static InternalInfo instancia;
    
    private InternalInfo() throws ClassNotFoundException, SQLException {
        initComponents();
        this.cargaVencimientos();
        this.jPanel1.setVisible(true);
    }
    
        public static InternalInfo instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalInfo();
         }
         return instancia;
      
   }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
        this.lblNombre.setText("Hola "+usuario);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblVencimiento = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Ebrima", 1, 18))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 300));
        jPanel1.setLayout(null);

        lblVencimiento.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblVencimiento.setForeground(new java.awt.Color(255, 0, 0));
        lblVencimiento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(lblVencimiento);
        lblVencimiento.setBounds(80, 90, 630, 42);

        lblNombre.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblNombre);
        lblNombre.setBounds(10, 30, 590, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/alerta.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 80, 50, 50);

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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void cargaVencimientos() throws ClassNotFoundException, SQLException{
    logF= new LogFuncionario();
    ArrayList<Funcionario> listado=logF.vencimientoCarne();
    Integer x=0;
    Date fecha=new Date();
    for(Integer s=0;s<listado.size();s++){
        if(listado.get(s).getVencimientoCarne()!=null){
            if(((listado.get(s).getVencimientoCarne().getTime()-fecha.getTime())/86400000)<=7){
            x++;
            }
        }
    }
  
    this.lblVencimiento.setText("Hoy hay "+x+" vencimientos de Carne de Salud que requieren su atención.");
    
    
}
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblVencimiento;
    // End of variables declaration//GEN-END:variables
}
