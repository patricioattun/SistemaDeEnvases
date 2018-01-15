
package Presentacion;


import Presentacion.Licencias.InternalAdelantado;
import Presentacion.Licencias.InternalAjustesLicencia;
import Presentacion.Licencias.InternalCalcular;
import Presentacion.Licencias.InternalMovLicencia;
import Presentacion.Licencias.pnlDiasLic;
import Presentacion.Liquidaciones.InternalFijoPorCod;
import Presentacion.Liquidaciones.InternalFijoPorFunc;
import Presentacion.Liquidaciones.InternalIngresoPorCod;
import Presentacion.Liquidaciones.InternalIngresoPorFunc;
import Presentacion.Liquidaciones.InternalRetencionesFijas;
import Presentacion.Mantenimiento.InternalModBanco;
import Presentacion.Mantenimiento.InternalModCodigoDesvin;
import Presentacion.Mantenimiento.InternalModFeriado;
import Presentacion.Mantenimiento.InternalModFunc;
import Presentacion.Mantenimiento.InternalModHorario;
import Presentacion.Marcas.InternalMarcas;
import Presentacion.Reportes.InternalListadoFunc;
import Presentacion.Reportes.InternalListadoLicencia;
import Presentacion.Reportes.InternalListadoMovLic;
import Presentacion.Reportes.InternalVencimientoCarne;
import java.awt.Dimension;
import java.beans.PropertyVetoException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;



public class frmPrin extends javax.swing.JFrame {

  

   private pnlDiasLic pnlDiasLic;
   private InternalAjustesLicencia internalAjustes;
   private InternalCalcular internalCalcular;
   private InternalAdelantado internalAdelantado;
   private InternalMovLicencia internalMovLicencia;
   private InternalVencimientoCarne internalVencimientoCarne;
   private InternalListadoMovLic internalListadoMovLic;
   private InternalListadoLicencia internalListadoLicencia;
   private InternalListadoFunc internalListadoFunc;
   private InternalModFunc internalModFunc;
   private InternalModBanco internalModBanco;
   private InternalModHorario internalModHorario;
   private InternalModFeriado internalModFeriado;
   private InternalInfo internalInfo;
   private InternalMarcas internalMarcas;
   private InternalModCodigoDesvin internalModCodigoDesvin;
   private InternalIngresoPorFunc internalIngresoPorFunc;
   private InternalIngresoPorCod internalIngresoPorCod;
   private InternalFijoPorCod internalFijoPorCod;
   private InternalFijoPorFunc internalFijoPorFunc;
   private InternalRetencionesFijas internalRetencionesFijas;
   
   private static frmPrin instancia;


   private String usuario;
   
   JDesktopPane desktop = new JDesktopPane();
   
    public frmPrin() throws ClassNotFoundException, SQLException {
        initComponents();
        this.setMinimumSize(new Dimension(1024, 768)); 
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setTitle("Sistema de Sueldos");
        this.setVisible(true);
        this.licPorFecha.setVisible(false);
        this.setContentPane(desktop);
        //this.jMenu5.setVisible(false);
        
    }
    
    public void cargaInfo() throws ClassNotFoundException, SQLException{
                
        this.internalInfo=InternalInfo.instancia();
        this.internalInfo.setUsuario(usuario);
        if (!internalInfo.isVisible()) {
            desktop.add(internalInfo);
            this.internalInfo.setLocation(330,100);
            //internalInfo.setLocation((desktop.getWidth()/2)-(internalInfo.getWidth()/2),(desktop.getHeight()/2) - internalInfo.getHeight()/2);
            internalInfo.setVisible(true);
        }
        else{
            internalInfo.requestFocus();
            try {
               internalInfo.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
            this.repaint();
            this.revalidate();
    
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
 
    

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }
    
    public static frmPrin instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new frmPrin();
         }
         return instancia;
      
   }

    public JPanel getPnlContenido() {
        return pnlContenido;
    }

    public void setPnlContenido(JPanel pnlContenido) {
        this.pnlContenido = pnlContenido;
    }



    



   



 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContenido = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        licPorFecha = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 102, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        getContentPane().setLayout(new java.awt.FlowLayout());
        getContentPane().add(pnlContenido);

        jMenu1.setText("Mantenimiento");
        jMenu1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenuItem4.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem4.setText("Mantenimiento Funcionario");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem5.setText("Mantenimiento Horario");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem6.setText("Mantenimiento Banco");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem14.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem14.setText("Mantenimiento Feriado");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem14);

        jMenuItem11.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem11.setText("Mantenimiento Código Egreso");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem11);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Reportes");
        jMenu2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenuItem2.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem2.setText("Vencimientos Carne de Salud");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem3.setText("Listado de Funcionarios");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem7.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem7.setText("Licencia Generada");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem12.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem12.setText("Movimientos Internos Licencia");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem12);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Marcas");
        jMenu3.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenuItem1.setText("Actualizar Marcas");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Licencias");
        jMenu4.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenuItem8.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem8.setText("Calcular Licencia");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        licPorFecha.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        licPorFecha.setText("Calcular Licencia por fecha");
        licPorFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licPorFechaActionPerformed(evt);
            }
        });
        jMenu4.add(licPorFecha);

        jMenuItem10.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem10.setText("Ingresar Licencia Adelantada");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem9.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem9.setText("Ingresar Interno Movimiento");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem15.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem15.setText("Ajustes de Licencias");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem15);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Liquidaciones");
        jMenu5.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenu6.setText("Ingreso de Movimientos");

        jMenuItem13.setText("Ingresos por Código");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem13);

        jMenuItem16.setText("Ingresos por Funcionario");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem16);

        jMenu5.add(jMenu6);

        jMenu7.setText("Carga de Códigos FIjos");

        jMenuItem17.setText("Por Funcionario");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem17);

        jMenuItem18.setText("Por Código");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem18);

        jMenu5.add(jMenu7);

        jMenuItem19.setText("Retenciones Fijas");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Vencimiento Carne de salud
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
       try {
            this.internalVencimientoCarne=InternalVencimientoCarne.instancia();
         
        if (!internalVencimientoCarne.isVisible()) {
            desktop.add(internalVencimientoCarne);
            internalVencimientoCarne.setLocation((desktop.getWidth()/2)-(internalVencimientoCarne.getWidth()/2),(desktop.getHeight()/2) - internalVencimientoCarne.getHeight()/2);
           internalVencimientoCarne.setVisible(true);
        }
        else{
            internalVencimientoCarne.requestFocus();
            try {
               internalVencimientoCarne.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
            this.repaint();
            this.revalidate();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    //Listado de funcionarios
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
      
     
       try {
           this.internalListadoFunc=InternalListadoFunc.instancia();
         
        if (!internalListadoFunc.isVisible()) {
            desktop.add(internalListadoFunc);
            internalListadoFunc.setLocation((desktop.getWidth()/2)-(internalListadoFunc.getWidth()/2),(desktop.getHeight()/2) - internalListadoFunc.getHeight()/2);
           internalListadoFunc.setVisible(true);
        }
        else{
            internalListadoFunc.requestFocus();
            try {
               internalListadoFunc.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
            this.repaint();
            this.revalidate();
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
         
    }//GEN-LAST:event_jMenuItem3ActionPerformed

   //mantenimiento bancos
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
      
        try {
            this.internalModBanco=InternalModBanco.instancia();
         
        if (!internalModBanco.isVisible()) {
            desktop.add(internalModBanco);
            internalModBanco.setLocation((desktop.getWidth()/2)-(internalModBanco.getWidth()/2),(desktop.getHeight()/2) - internalModBanco.getHeight()/2);
            internalModBanco.setVisible(true);
        }
        else{
            internalModBanco.requestFocus();
            try {
                internalModBanco.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
            this.repaint();
            this.revalidate();
        } catch (SQLException ex) {
            Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItem6ActionPerformed
    //Mantenimiento funcionario
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
      
        try {
            this.internalModFunc=InternalModFunc.instancia();
         
        if (!internalModFunc.isVisible()) {
            desktop.add(internalModFunc);
            internalModFunc.setLocation((desktop.getWidth()/2)-(internalModFunc.getWidth()/2),(desktop.getHeight()/2) - internalModFunc.getHeight()/2);
            internalModFunc.setVisible(true);
        }
        else{
            internalModFunc.requestFocus();
            try {
                internalModFunc.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
            this.repaint();
            this.revalidate();
        } catch (SQLException ex) {
            Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItem4ActionPerformed
    //Mantenimiento horario 
    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
            
        try {
            this.internalModHorario=InternalModHorario.instancia();
         
        if (!internalModHorario.isVisible()) {
            desktop.add(internalModHorario);
            internalModHorario.setLocation((desktop.getWidth()/2)-(internalModHorario.getWidth()/2),(desktop.getHeight()/2) - internalModHorario.getHeight()/2);
            internalModHorario.setVisible(true);
        }
        else{
            internalModHorario.requestFocus();
            try {
                internalModHorario.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
            this.repaint();
            this.revalidate();
        } catch (SQLException ex) {
            Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
     
       try {
           this.internalMarcas=InternalMarcas.instancia();
         
        if (!internalMarcas.isVisible()) {
            desktop.add(internalMarcas);
            internalMarcas.setLocation((desktop.getWidth()/2)-(internalMarcas.getWidth()/2),(desktop.getHeight()/2) - internalMarcas.getHeight()/2);
            internalMarcas.setVisible(true);
        }
        else{
            internalMarcas.requestFocus();
            try {
                internalMarcas.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
            this.repaint();
            this.revalidate();
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
              
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
      
       try {
         this.internalListadoLicencia=InternalListadoLicencia.instancia();
         
        if (!internalListadoLicencia.isVisible()) {
            desktop.add(internalListadoLicencia);
            internalListadoLicencia.setLocation((desktop.getWidth()/2)-(internalListadoLicencia.getWidth()/2),(desktop.getHeight()/2) - internalListadoLicencia.getHeight()/2);
            internalListadoLicencia.setVisible(true);
        }
        else{
            internalListadoLicencia.requestFocus();
            try {
                internalListadoLicencia.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
           this.repaint();
           this.revalidate();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_jMenuItem7ActionPerformed
    //Calcular licencia
    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
       
       try {
           this.internalCalcular=InternalCalcular.instancia();
         
        if (!internalCalcular.isVisible()) {
            desktop.add(internalCalcular);
            internalCalcular.setLocation((desktop.getWidth()/2)-(internalCalcular.getWidth()/2),(desktop.getHeight()/2) - internalCalcular.getHeight()/2);
            internalCalcular.setVisible(true);
        }
        else{
            internalCalcular.requestFocus();
            try {
                internalCalcular.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       // this.pnlContenido.add(calcular);
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed

       try {
           this.internalMovLicencia=InternalMovLicencia.instancia();
         
        if (!internalMovLicencia.isVisible()) {
            desktop.add(internalMovLicencia);
            internalMovLicencia.setLocation((desktop.getWidth()/2)-(internalMovLicencia.getWidth()/2),(desktop.getHeight()/2) - internalMovLicencia.getHeight()/2);
            internalMovLicencia.setVisible(true);
        }
        else{
            internalMovLicencia.requestFocus();
            try {
                internalMovLicencia.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
     
      
       try {
           this.internalAdelantado = InternalAdelantado.instancia();
       if (!internalAdelantado.isVisible()) {
            desktop.add(internalAdelantado);
            internalAdelantado.setLocation((desktop.getWidth()/2)-(internalAdelantado.getWidth()/2),(desktop.getHeight()/2) - internalAdelantado.getHeight()/2);
            internalAdelantado.setVisible(true);
        }
        else{
            internalAdelantado.requestFocus();
            try {
                internalAdelantado.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
           
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
 
        //this.pnlContenido.add(licAdelantada);
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
      
       try {
           this.internalListadoMovLic = InternalListadoMovLic.instancia();
       if (!internalListadoMovLic.isVisible()) {
            desktop.add(internalListadoMovLic);
            internalListadoMovLic.setLocation((desktop.getWidth()/2)-(internalListadoMovLic.getWidth()/2),(desktop.getHeight()/2) - internalListadoMovLic.getHeight()/2);
            internalListadoMovLic.setVisible(true);
        }
        else{
            internalListadoMovLic.requestFocus();
            try {
                internalListadoMovLic.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void licPorFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licPorFechaActionPerformed
     
       try {
           this.pnlDiasLic=new pnlDiasLic(this);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
        this.pnlContenido.add(pnlDiasLic);
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_licPorFechaActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
   
       try {
            this.internalModFeriado= InternalModFeriado.instancia();
       if (!internalModFeriado.isVisible()) {
            desktop.add(internalModFeriado);
            internalModFeriado.setLocation((desktop.getWidth()/2)-(internalModFeriado.getWidth()/2),(desktop.getHeight()/2) - internalModFeriado.getHeight()/2);
            internalModFeriado.setVisible(true);
        }
        else{
            internalModFeriado.requestFocus();
            try {
                internalModFeriado.setSelected(true);
                
            } catch (PropertyVetoException ex) {
                //lblMensaje.setText(ex.getMessage());
            }
        }
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ParseException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed

       try {
           this.internalAjustes=InternalAjustesLicencia.instancia(null,null);
         
        if (!internalAjustes.isVisible()) {
            desktop.add(internalAjustes);
            internalAjustes.setLocation((desktop.getWidth()/2)-(internalAjustes.getWidth()/2),(desktop.getHeight()/2) - internalAjustes.getHeight()/2);
            internalAjustes.setVisible(true);
        }
        else{
            internalAjustes.requestFocus();
            try {
                internalAjustes.setSelected(true);
                
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
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        try {
           this.internalModCodigoDesvin=InternalModCodigoDesvin.instancia();
         
        if (!internalModCodigoDesvin.isVisible()) {
            desktop.add(internalModCodigoDesvin);
            internalModCodigoDesvin.setLocation((desktop.getWidth()/2)-(internalModCodigoDesvin.getWidth()/2),(desktop.getHeight()/2) - internalModCodigoDesvin.getHeight()/2);
            internalModCodigoDesvin.setVisible(true);
        }
        else{
            internalModCodigoDesvin.requestFocus();
            try {
                internalModCodigoDesvin.setSelected(true);
                
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
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
       try {
           this.internalIngresoPorFunc=InternalIngresoPorFunc.instancia();
         
        if (!internalIngresoPorFunc.isVisible()) {
            desktop.add(internalIngresoPorFunc);
            internalIngresoPorFunc.setLocation((desktop.getWidth()/2)-(internalIngresoPorFunc.getWidth()/2),(desktop.getHeight()/2) - internalIngresoPorFunc.getHeight()/2);
            internalIngresoPorFunc.setVisible(true);
            internalIngresoPorFunc.getTxtNumFunc().requestFocus();
           }
        else{
            internalIngresoPorFunc.requestFocus();
            try {
                internalIngresoPorFunc.setSelected(true);
                internalIngresoPorFunc.getTxtNumFunc().requestFocus();
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
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
       try {
           this.internalIngresoPorCod=InternalIngresoPorCod.instancia();
         
        if (!internalIngresoPorCod.isVisible()) {
            desktop.add(internalIngresoPorCod);
            internalIngresoPorCod.setLocation((desktop.getWidth()/2)-(internalIngresoPorCod.getWidth()/2),(desktop.getHeight()/2) - internalIngresoPorCod.getHeight()/2);
            internalIngresoPorCod.setVisible(true);
            internalIngresoPorCod.getTxtCod().requestFocus();
           }
        else{
            internalIngresoPorCod.requestFocus();
            try {
                internalIngresoPorCod.setSelected(true);
                internalIngresoPorCod.getTxtCod().requestFocus();
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
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
       try {
           this.internalFijoPorCod=InternalFijoPorCod.instancia();
         
        if (!internalFijoPorCod.isVisible()) {
            desktop.add(internalFijoPorCod);
            internalFijoPorCod.setLocation((desktop.getWidth()/2)-(internalFijoPorCod.getWidth()/2),(desktop.getHeight()/2) - internalFijoPorCod.getHeight()/2);
            internalFijoPorCod.setVisible(true);
            //internalFijoPorCod.getTxtCod().requestFocus();
           }
        else{
            internalFijoPorCod.requestFocus();
            try {
                internalFijoPorCod.setSelected(true);
                //internalFijoPorCod.getTxtCod().requestFocus();
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
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        try {
           this.internalFijoPorFunc=InternalFijoPorFunc.instancia();
         
        if (!internalFijoPorFunc.isVisible()) {
            desktop.add(internalFijoPorFunc);
            internalFijoPorFunc.setLocation((desktop.getWidth()/2)-(internalFijoPorFunc.getWidth()/2),(desktop.getHeight()/2) - internalFijoPorFunc.getHeight()/2);
            internalFijoPorFunc.setVisible(true);
            internalFijoPorFunc.getTxtNumFunc().requestFocus();
           }
        else{
            internalFijoPorFunc.requestFocus();
            try {
                internalFijoPorFunc.setSelected(true);
                //internalFijoPorCod.getTxtCod().requestFocus();
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
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        try {
           this.internalRetencionesFijas=InternalRetencionesFijas.instancia();
         
        if (!internalRetencionesFijas.isVisible()) {
            desktop.add(internalRetencionesFijas);
            internalRetencionesFijas.setLocation((desktop.getWidth()/2)-(internalRetencionesFijas.getWidth()/2),(desktop.getHeight()/2) - internalRetencionesFijas.getHeight()/2);
            internalRetencionesFijas.setVisible(true);
            internalRetencionesFijas.getTxtNumFunc().requestFocus();
           }
        else{
            internalRetencionesFijas.requestFocus();
            try {
                internalRetencionesFijas.setSelected(true);
                //internalFijoPorCod.getTxtCod().requestFocus();
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
    }//GEN-LAST:event_jMenuItem19ActionPerformed


//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(frmPrin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(frmPrin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(frmPrin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(frmPrin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    new frmPrin().setVisible(true);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SQLException ex) {
//                    Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem licPorFecha;
    private javax.swing.JPanel pnlContenido;
    // End of variables declaration//GEN-END:variables
}
