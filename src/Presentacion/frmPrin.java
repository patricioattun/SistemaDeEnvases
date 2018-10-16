
package Presentacion;


import Dominio.Usuario;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import Presentacion.Declaraciones.InternalMantenimientoDecla;
import Presentacion.Licencias.InternalAdelantado;
import Presentacion.Licencias.InternalAjustesLicencia;
import Presentacion.Licencias.InternalCalcular;
import Presentacion.Licencias.InternalDiasLic;
import Presentacion.Licencias.InternalMovLicencia;
import Presentacion.Licencias.pnlDiasLic;
import Presentacion.Liquidaciones.InternalArchivoBanco;
import Presentacion.Liquidaciones.InternalFechaPrelacionFunc;
import Presentacion.Liquidaciones.InternalFechasPrelacion;
import Presentacion.Liquidaciones.InternalFijoPorCod;
import Presentacion.Liquidaciones.InternalFijoPorFunc;
import Presentacion.Liquidaciones.InternalIngresoPorCod;
import Presentacion.Liquidaciones.InternalIngresoPorFunc;
import Presentacion.Liquidaciones.InternalLiquidaAguinaldo;
import Presentacion.Liquidaciones.InternalLiquidaSueldo;
import Presentacion.Liquidaciones.InternalListadoPorCodigos;
import Presentacion.Liquidaciones.InternalPromedioHorasExtras;
import Presentacion.Liquidaciones.InternalReLiqVacacionales;
import Presentacion.Liquidaciones.InternalReLiquidaSueldos;

import Presentacion.Liquidaciones.InternalResumenYDetalle;
import Presentacion.Liquidaciones.InternalRetencionesFijas;
import Presentacion.Liquidaciones.InternalTipoLiquidacion;
import Presentacion.Liquidaciones.InternalVacacionales;
import Presentacion.Mantenimiento.InternalAumentoSalarial;
import Presentacion.Mantenimiento.InternalMantParametros;
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
import Presentacion.Vales.InternalVales;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.io.IOException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
   private InternalMantenimientoDecla internalDecla;
   private InternalAumentoSalarial internalAumento;
   private InternalVales internalVale;
   private InternalArchivoBanco internalArchivoBanco;
   private InternalDiasLic internalDiasLic;
   private static frmPrin instancia;
   private String fechaLiq;
   private LogFuncionario log;
   private Usuario usuario;
   private String nom;
   
   JDesktopPane desktop = new JDesktopPane();
   
    public frmPrin(Usuario us) throws ClassNotFoundException, SQLException {
        initComponents();
        this.setMinimumSize(new Dimension(1024, 768)); 
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
        //this.licPorFecha.setVisible(false);
        this.setContentPane(desktop);
        this.usuario=us;
        this.chequeaPermisos();
        this.setTitle("Sistema de Sueldos // "+"Bienvenido "+this.usuario.getNombre());
        log=new LogFuncionario();
     
    }
    
    public void cargaInfo() throws ClassNotFoundException, SQLException{
                
        this.internalInfo=InternalInfo.instancia();
        this.internalInfo.setUsuario(usuario.getNombre());
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
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
            instancia = new frmPrin(null);
         }
         return instancia;
      
   }

    public static frmPrin instancia2(Usuario us) throws  SQLException, ClassNotFoundException
   {    
         if (instancia== null)
         {
            instancia = new frmPrin(us);
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
        barraMenu = new javax.swing.JMenuBar();
        menuMant = new javax.swing.JMenu();
        mantFunc = new javax.swing.JMenuItem();
        mantHora = new javax.swing.JMenuItem();
        mantBanco = new javax.swing.JMenuItem();
        mantFeriado = new javax.swing.JMenuItem();
        mantCodEgre = new javax.swing.JMenuItem();
        mantAumento = new javax.swing.JMenuItem();
        parametros = new javax.swing.JMenuItem();
        menuRep = new javax.swing.JMenu();
        repSalud = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        repLicGen = new javax.swing.JMenuItem();
        repMovInt = new javax.swing.JMenuItem();
        menuMarca = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuLic = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        licPorFecha = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        menuLiq = new javax.swing.JMenu();
        liqMov = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        liqCodFijo = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        liqRetFija = new javax.swing.JMenuItem();
        liqBanco = new javax.swing.JMenuItem();
        prelacion = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        horasExtras = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        liquidaciones = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        menuVale = new javax.swing.JMenu();
        jMenuItem20 = new javax.swing.JMenuItem();
        menuDecla = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 102, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.FlowLayout());
        getContentPane().add(pnlContenido);

        menuMant.setText("Mantenimiento");
        menuMant.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        mantFunc.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        mantFunc.setText("Mantenimiento Funcionario");
        mantFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantFuncActionPerformed(evt);
            }
        });
        menuMant.add(mantFunc);

        mantHora.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        mantHora.setText("Mantenimiento Horario");
        mantHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantHoraActionPerformed(evt);
            }
        });
        menuMant.add(mantHora);

        mantBanco.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        mantBanco.setText("Mantenimiento Banco");
        mantBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantBancoActionPerformed(evt);
            }
        });
        menuMant.add(mantBanco);

        mantFeriado.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        mantFeriado.setText("Mantenimiento Feriado");
        mantFeriado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantFeriadoActionPerformed(evt);
            }
        });
        menuMant.add(mantFeriado);

        mantCodEgre.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        mantCodEgre.setText("Mantenimiento Código Egreso");
        mantCodEgre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantCodEgreActionPerformed(evt);
            }
        });
        menuMant.add(mantCodEgre);

        mantAumento.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        mantAumento.setText("Aumentos Salariales");
        mantAumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantAumentoActionPerformed(evt);
            }
        });
        menuMant.add(mantAumento);

        parametros.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        parametros.setText("Mantenimiento de Parámetros");
        parametros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parametrosActionPerformed(evt);
            }
        });
        menuMant.add(parametros);

        barraMenu.add(menuMant);

        menuRep.setText("Reportes");
        menuRep.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        repSalud.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        repSalud.setText("Vencimientos Carne de Salud");
        repSalud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repSaludActionPerformed(evt);
            }
        });
        menuRep.add(repSalud);

        jMenuItem3.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem3.setText("Listado de Funcionarios");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuRep.add(jMenuItem3);

        repLicGen.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        repLicGen.setText("Licencia Generada");
        repLicGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repLicGenActionPerformed(evt);
            }
        });
        menuRep.add(repLicGen);

        repMovInt.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        repMovInt.setText("Movimientos Internos Licencia");
        repMovInt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repMovIntActionPerformed(evt);
            }
        });
        menuRep.add(repMovInt);

        barraMenu.add(menuRep);

        menuMarca.setText("Marcas");
        menuMarca.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem1.setText("Actualizar Marcas");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuMarca.add(jMenuItem1);

        barraMenu.add(menuMarca);

        menuLic.setText("Licencias");
        menuLic.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenuItem8.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem8.setText("Calcular Licencia");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        menuLic.add(jMenuItem8);

        licPorFecha.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        licPorFecha.setText("Calcular Licencia por fecha");
        licPorFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licPorFechaActionPerformed(evt);
            }
        });
        menuLic.add(licPorFecha);

        jMenuItem10.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem10.setText("Ingresar Licencia Adelantada");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        menuLic.add(jMenuItem10);

        jMenuItem9.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem9.setText("Ingresar Movimiento Interno");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        menuLic.add(jMenuItem9);

        jMenuItem15.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem15.setText("Ajustes de Licencias");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        menuLic.add(jMenuItem15);

        barraMenu.add(menuLic);

        menuLiq.setText("Liquidaciones");
        menuLiq.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        liqMov.setText("Ingreso de Movimientos");
        liqMov.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        jMenuItem13.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem13.setText("Ingresos por Código");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        liqMov.add(jMenuItem13);

        jMenuItem16.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem16.setText("Ingresos por Funcionario");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        liqMov.add(jMenuItem16);

        menuLiq.add(liqMov);

        liqCodFijo.setText("Carga de Códigos Fijos");
        liqCodFijo.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        jMenuItem17.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem17.setText("Por Funcionario");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        liqCodFijo.add(jMenuItem17);

        jMenuItem18.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem18.setText("Por Código");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        liqCodFijo.add(jMenuItem18);

        menuLiq.add(liqCodFijo);

        liqRetFija.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        liqRetFija.setText("Retenciones Fijas");
        liqRetFija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liqRetFijaActionPerformed(evt);
            }
        });
        menuLiq.add(liqRetFija);

        liqBanco.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        liqBanco.setText("Archivos para Banco");
        liqBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liqBancoActionPerformed(evt);
            }
        });
        menuLiq.add(liqBanco);

        prelacion.setText("Carga de Prelacion");
        prelacion.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        jMenuItem6.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem6.setText("Por Código");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        prelacion.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem7.setText("Por Funcionario");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        prelacion.add(jMenuItem7);

        menuLiq.add(prelacion);

        horasExtras.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        horasExtras.setText("Generación Prom. Horas Extras Anual");
        horasExtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horasExtrasActionPerformed(evt);
            }
        });
        menuLiq.add(horasExtras);

        jMenuItem19.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem19.setText("Cambiar Tipo de Liquidación");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        menuLiq.add(jMenuItem19);

        liquidaciones.setText("Liquidación");
        liquidaciones.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N

        jMenuItem5.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem5.setText("Sueldos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        liquidaciones.add(jMenuItem5);

        jMenuItem12.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem12.setText("Re Liquidación Sueldos");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        liquidaciones.add(jMenuItem12);

        jMenuItem2.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem2.setText("Resumen y Detalle de Liquidacion");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        liquidaciones.add(jMenuItem2);

        jMenuItem11.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem11.setText("Listados por codigos");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        liquidaciones.add(jMenuItem11);

        jMenuItem4.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem4.setText("Vacacionales");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        liquidaciones.add(jMenuItem4);

        jMenuItem14.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem14.setText("Re Liquidación Vacacionales");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        liquidaciones.add(jMenuItem14);

        jMenuItem22.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem22.setText("Aguinaldo");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        liquidaciones.add(jMenuItem22);

        menuLiq.add(liquidaciones);

        barraMenu.add(menuLiq);

        menuVale.setText("Vales");
        menuVale.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenuItem20.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem20.setText("Ingreso de Vales");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        menuVale.add(jMenuItem20);

        barraMenu.add(menuVale);

        menuDecla.setText("Declaraciones Juradas");
        menuDecla.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N

        jMenuItem21.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jMenuItem21.setText("Mantenimiento");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        menuDecla.add(jMenuItem21);

        barraMenu.add(menuDecla);

        setJMenuBar(barraMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Vencimiento Carne de salud
    private void repSaludActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repSaludActionPerformed
        
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
        
    }//GEN-LAST:event_repSaludActionPerformed
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
    private void mantBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantBancoActionPerformed
      
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

    }//GEN-LAST:event_mantBancoActionPerformed
    //Mantenimiento funcionario
    private void mantFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantFuncActionPerformed
      
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

    }//GEN-LAST:event_mantFuncActionPerformed
    //Mantenimiento horario 
    private void mantHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantHoraActionPerformed
            
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
    }//GEN-LAST:event_mantHoraActionPerformed

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

    private void repLicGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repLicGenActionPerformed
      
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
    }//GEN-LAST:event_repLicGenActionPerformed
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

    private void repMovIntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repMovIntActionPerformed
      
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
           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
       } catch (ClassNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
       } catch (BDExcepcion ex) {
           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
       }
       
        this.repaint();
        this.revalidate();
    }//GEN-LAST:event_repMovIntActionPerformed

    private void licPorFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licPorFechaActionPerformed
       try {
           this.internalDiasLic = InternalDiasLic.instancia();
       if (!internalDiasLic.isVisible()) {
            desktop.add(internalDiasLic);
            internalDiasLic.setLocation((desktop.getWidth()/2)-(internalDiasLic.getWidth()/2),(desktop.getHeight()/2) - internalDiasLic.getHeight()/2);
            internalDiasLic.setVisible(true);
        }
        else{
            internalDiasLic.requestFocus();
            try {
                internalDiasLic.setSelected(true);
                
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
    }//GEN-LAST:event_licPorFechaActionPerformed

    private void mantFeriadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantFeriadoActionPerformed
   
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
    }//GEN-LAST:event_mantFeriadoActionPerformed

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

    private void mantCodEgreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantCodEgreActionPerformed
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
    }//GEN-LAST:event_mantCodEgreActionPerformed

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

    private void liqRetFijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liqRetFijaActionPerformed
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
    }//GEN-LAST:event_liqRetFijaActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
         try {
              
           this.internalDecla=InternalMantenimientoDecla.instancia();
         
        if (!internalDecla.isVisible()) {
            desktop.add(internalDecla);
            internalDecla.setLocation((desktop.getWidth()/2)-(internalDecla.getWidth()/2),(desktop.getHeight()/2) - internalDecla.getHeight()/2);
            internalDecla.setVisible(true);
            internalDecla.getTxtNumFunc().requestFocus();
           }
        else{
            internalDecla.requestFocus();
            try {
                internalDecla.setSelected(true);
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
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void mantAumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantAumentoActionPerformed
       try {
           this.internalAumento=InternalAumentoSalarial.instancia();
           if (!internalAumento.isVisible()) {
               desktop.add(internalAumento);
               internalAumento.setLocation((desktop.getWidth()/2)-(internalAumento.getWidth()/2),(desktop.getHeight()/2) - internalAumento.getHeight()/2);
               internalAumento.setVisible(true);
               internalAumento.getTxtIncremento().requestFocus();
               
           }
           else{
               internalAumento.requestFocus();
               try {
                   internalAumento.setSelected(true);
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
    }//GEN-LAST:event_mantAumentoActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
       try {
           this.internalVale=InternalVales.instancia();
           if (!internalVale.isVisible()) {
               desktop.add(internalVale);
               internalVale.setLocation((desktop.getWidth()/2)-(internalVale.getWidth()/2),(desktop.getHeight()/2) - internalVale.getHeight()/2);
               internalVale.setVisible(true);
               
               
           }
           else{
               internalVale.requestFocus();
               try {
                   internalVale.setSelected(true);
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
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void liqBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liqBancoActionPerformed
         try {
           this.internalArchivoBanco =InternalArchivoBanco.instancia();
           if (!internalArchivoBanco.isVisible()) {
               desktop.add(internalArchivoBanco);
               internalArchivoBanco.setLocation((desktop.getWidth()/2)-(internalArchivoBanco.getWidth()/2),(desktop.getHeight()/2) - internalArchivoBanco.getHeight()/2);
               internalArchivoBanco.setVisible(true);
               
               
           }
           else{
               internalArchivoBanco.requestFocus();
               try {
                   internalArchivoBanco.setSelected(true);
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
    }//GEN-LAST:event_liqBancoActionPerformed

    private void parametrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parametrosActionPerformed
        try {
           InternalMantParametros internal=InternalMantParametros.instancia();
           if (!internal.isVisible()) {
               desktop.add(internal);
               internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
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
       } catch (BDExcepcion ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_parametrosActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        try {
           InternalFechasPrelacion internal=InternalFechasPrelacion.instancia();
           if (!internal.isVisible()) {
               desktop.add(internal);
               internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
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
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
       try {
           InternalFechaPrelacionFunc internal=InternalFechaPrelacionFunc.instancia();
           if (!internal.isVisible()) {
               desktop.add(internal);
               internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
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
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
      
       try {
           if(correspondeFecha(30,31,29,28)){
               if(chequeoUsuarioLiq()){
                   if(chequeoUsuarioTipoLiq()){
                       if(chequeoVentanas2()){
                           if(noEstaLaFecha()){
                           try {
                               InternalLiquidaSueldo internal=InternalLiquidaSueldo.instancia();
                               if (!internal.isVisible()) {
                                   desktop.add(internal);
                                   internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
                                   internal.setVisible(true);
                                   this.log.actualizaParametro(usuario.getNombre(), "USUARIO_LIQ");
                                   
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
                           } catch (BDExcepcion ex) {
                               Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
                           }
                        }else{
                            JOptionPane.showMessageDialog(this, "Ya fue incorporada una liquidacion con fecha "+fechaLiq);   
                        }
                       }else{
                           JOptionPane.showMessageDialog(this, "Cierre la ventana de 'Tipo de Liquidación' antes de continuar");
                       }
                   }else{
                       JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta la ventana de 'Tipo de Liquidacion'");
                   }
               }else{
                   JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta esta ventana en su equipo.");    
               }
           }else{
               if(!fechaLiq.equals("")){
                   JOptionPane.showMessageDialog(this, "La fecha de liquidación es "+fechaLiq+" y no coincide con este tipo de liquidación. Verifique.");
               }else{
                   JOptionPane.showMessageDialog(this, "No hay fecha de liquidación asignada. Verifique");
               }
           }
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ParseException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (BDExcepcion ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       
          
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       try {
           InternalResumenYDetalle internal=InternalResumenYDetalle.instancia();
           if (!internal.isVisible()) {
               desktop.add(internal);
               internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
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
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
         try {
           InternalListadoPorCodigos internal=InternalListadoPorCodigos.instancia();
           if (!internal.isVisible()) {
               desktop.add(internal);
               internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
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
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
       
        try {
           if(correspondeFecha(1,1,1,1)){
               if(chequeoUsuarioLiq()){
                   if(chequeoUsuarioTipoLiq()){
                       if(chequeoVentanas2()){
                           if(noEstaLaFecha()){
               try {
                   InternalVacacionales internal=InternalVacacionales.instancia();
                   if (!internal.isVisible()) {
                       desktop.add(internal);
                       internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
                       internal.setVisible(true);
                       this.log.actualizaParametro(usuario.getNombre(), "USUARIO_LIQ");
                       
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
                           }else{
                             JOptionPane.showMessageDialog(this, "Ya fue incorporada una liquidacion con fecha "+fechaLiq);     
                           }
                       }else{
                         JOptionPane.showMessageDialog(this, "Cierre la ventana de 'Tipo de Liquidación' antes de continuar");  
                       }
                   }else{
                       JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta la ventana de 'Tipo de Liquidacion'");
                   }
               }else{
                   JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta esta ventana en su equipo.");    
               }
           }else{
               if(!fechaLiq.equals("")){
                   JOptionPane.showMessageDialog(this, "La fecha de liquidación es "+fechaLiq+" y no coincide con este tipo de liquidación. Verifique.");
               }else{
                   JOptionPane.showMessageDialog(this, "No hay fecha de liquidación asignada. Verifique");
               }
           } } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ParseException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (BDExcepcion ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
      
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
       
        try {
           if(correspondeFecha(29,28,27,26)){
               if(chequeoUsuarioLiq()){
                   if(chequeoUsuarioTipoLiq()){
                       if(chequeoVentanas2()){
                           if(noEstaLaFecha()){
               try {
                   InternalReLiquidaSueldos internal=InternalReLiquidaSueldos.instancia();
                   if (!internal.isVisible()) {
                       desktop.add(internal);
                       internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
                       internal.setVisible(true);
                       this.log.actualizaParametro(usuario.getNombre(), "USUARIO_LIQ");
                       
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
                           }else{
                               JOptionPane.showMessageDialog(this, "Ya fue incorporada una liquidacion con fecha "+fechaLiq);   
                           }
                       }else{
                            JOptionPane.showMessageDialog(this, "Cierre la ventana de 'Tipo de Liquidación' antes de continuar");
                       }
                   }else{
                        JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta la ventana de 'Tipo de Liquidacion'");
                   }
               }else{
                   JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta esta ventana en su equipo.");    
               }
           }else{
               if(!fechaLiq.equals("")){
                   JOptionPane.showMessageDialog(this, "La fecha de liquidación es "+fechaLiq+" y no coincide con este tipo de liquidación. Verifique.");
               }else{
                   JOptionPane.showMessageDialog(this, "No hay fecha de liquidación asignada. Verifique");
               }
           }
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ParseException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (BDExcepcion ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void horasExtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horasExtrasActionPerformed
        try {
           InternalPromedioHorasExtras internal=InternalPromedioHorasExtras.instancia();
           if (!internal.isVisible()) {
               desktop.add(internal);
               internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
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
    }//GEN-LAST:event_horasExtrasActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
      
  
       try {
           if(correspondeFecha(2,2,2,2)){
               if(chequeoUsuarioLiq()){
                   if(chequeoUsuarioTipoLiq()){
                       if(chequeoVentanas2()){
                           if(noEstaLaFecha()){
                           try {
                               InternalReLiqVacacionales internal=InternalReLiqVacacionales.instancia();
                               if (!internal.isVisible()) {
                                   desktop.add(internal);
                                   internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
                                   internal.setVisible(true);
                                   this.log.actualizaParametro(usuario.getNombre(), "USUARIO_LIQ");
                                   
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
                               this.repaint();
                               this.revalidate();
                           } catch (ClassNotFoundException | SQLException ex) {
                               Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           }else{
                               JOptionPane.showMessageDialog(this, "Ya fue incorporada una liquidacion con fecha "+fechaLiq);   
                           }
                       }else{
                           JOptionPane.showMessageDialog(this, "Cierre la ventana de 'Tipo de Liquidación' antes de continuar");
                       }
                   }else{
                       JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta la ventana de 'Tipo de Liquidacion'");
                   }
               }else{
                   JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta esta ventana en su equipo.");
               }
           }else{
               if(!fechaLiq.equals("")){
                   JOptionPane.showMessageDialog(this, "La fecha de liquidación es "+fechaLiq+" y no coincide con este tipo de liquidación. Verifique.");
               }else{
                   JOptionPane.showMessageDialog(this, "No hay fecha de liquidación asignada. Verifique");
               }
           } 
       } catch (ClassNotFoundException | SQLException | ParseException | BDExcepcion ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        if(chequeoVentanas()){
            try {
                if(this.chequeoUsuarioLiq()){
                    if(chequeoUsuarioTipoLiq()){
                        InternalTipoLiquidacion internal=InternalTipoLiquidacion.instancia();  
                        if (!internal.isVisible()) {
                            desktop.add(internal);
                            internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
                            internal.setVisible(true);
                            this.log.actualizaParametro(usuario.getNombre(), "USUARIO_TIPO_LIQ");
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
                        this.repaint();
                        this.revalidate();
                    }else{
                        JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta esta ventana en su equipo");
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta una ventana de liquidación en su equipo.");
                }
            } catch (BDExcepcion | ClassNotFoundException | SQLException | ParseException ex){
                Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Cierre todas las ventanas vinculadas a las liquidaciones antes de continuar.");
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
       
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       try {
           if(this.chequeoUsuarioLiq()){
               this.log.actualizaParametro("", "USUARIO_LIQ");
           }
       } catch (BDExcepcion | ClassNotFoundException | SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
       try {
           if(chequeoUsuarioTipoLiq()){
                if(nom.equals(usuario.getNombre())){
                     this.log.actualizaParametro("", "USUARIO_TIPO_LIQ");
                }
           }
       } catch (BDExcepcion | ClassNotFoundException | SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
         try {
           if(correspondeFecha(15,14,15,14)){
               if(chequeoUsuarioLiq()){
                   if(chequeoUsuarioTipoLiq()){
                       if(chequeoVentanas2()){
                           if(noEstaLaFecha()){
                           try {
                               InternalLiquidaAguinaldo internal=InternalLiquidaAguinaldo.instancia();
                               if (!internal.isVisible()) {
                                   desktop.add(internal);
                                   internal.setLocation((desktop.getWidth()/2)-(internal.getWidth()/2),(desktop.getHeight()/2) - internal.getHeight()/2);
                                   internal.setVisible(true);
                                   this.log.actualizaParametro(usuario.getNombre(), "USUARIO_LIQ");
                                   
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
                           } catch (BDExcepcion ex) {
                               Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
                           }
                        }else{
                            JOptionPane.showMessageDialog(this, "Ya fue incorporada una liquidacion con fecha "+fechaLiq);   
                        }
                       }else{
                           JOptionPane.showMessageDialog(this, "Cierre la ventana de 'Tipo de Liquidación' antes de continuar");
                       }
                   }else{
                       JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta la ventana de 'Tipo de Liquidacion'");
                   }
               }else{
                   JOptionPane.showMessageDialog(this, "El usuario "+nom+" tiene abierta esta ventana en su equipo.");    
               }
           }else{
               if(!fechaLiq.equals("")){
                   JOptionPane.showMessageDialog(this, "La fecha de liquidación es "+fechaLiq+" y no coincide con este tipo de liquidación. Verifique.");
               }else{
                   JOptionPane.showMessageDialog(this, "No hay fecha de liquidación asignada. Verifique");
               }
           }
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ParseException ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       } catch (BDExcepcion ex) {
           Logger.getLogger(frmPrin.class.getName()).log(Level.SEVERE, null, ex);
       }
    }//GEN-LAST:event_jMenuItem22ActionPerformed


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
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JMenuItem horasExtras;
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
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem licPorFecha;
    private javax.swing.JMenuItem liqBanco;
    private javax.swing.JMenu liqCodFijo;
    private javax.swing.JMenu liqMov;
    private javax.swing.JMenuItem liqRetFija;
    private javax.swing.JMenu liquidaciones;
    private javax.swing.JMenuItem mantAumento;
    private javax.swing.JMenuItem mantBanco;
    private javax.swing.JMenuItem mantCodEgre;
    private javax.swing.JMenuItem mantFeriado;
    private javax.swing.JMenuItem mantFunc;
    private javax.swing.JMenuItem mantHora;
    private javax.swing.JMenu menuDecla;
    private javax.swing.JMenu menuLic;
    private javax.swing.JMenu menuLiq;
    private javax.swing.JMenu menuMant;
    private javax.swing.JMenu menuMarca;
    private javax.swing.JMenu menuRep;
    private javax.swing.JMenu menuVale;
    private javax.swing.JMenuItem parametros;
    private javax.swing.JPanel pnlContenido;
    private javax.swing.JMenu prelacion;
    private javax.swing.JMenuItem repLicGen;
    private javax.swing.JMenuItem repMovInt;
    private javax.swing.JMenuItem repSalud;
    // End of variables declaration//GEN-END:variables

    private void chequeaPermisos() throws SQLException, ClassNotFoundException {
 
       switch (usuario.getPermiso().getPermiso()) {
           //CONTADURIA
           case 2:
               this.mantFeriado.setVisible(false);
               this.repLicGen.setVisible(false);
               this.repMovInt.setVisible(false);
               this.repSalud.setVisible(false);
               this.menuMarca.setVisible(false);
               this.menuLic.setVisible(false);
               this.liqBanco.setVisible(false);
               this.menuVale.setVisible(false);
               //this.liquidaciones.setVisible(false);
               break;
            //SECRETARIA   
           case 3:
               this.cargaInfo();
               this.mantAumento.setVisible(false);
               this.mantBanco.setVisible(false);
               this.mantCodEgre.setVisible(false);
               this.mantFunc.setVisible(false);
               this.menuLiq.setVisible(false);
               this.menuVale.setVisible(false);
               this.menuDecla.setVisible(false);
               this.prelacion.setVisible(false);
               this.parametros.setVisible(false);
               this.liquidaciones.setVisible(false);
               break;
            //TESORERIA
           case 6:
               this.menuDecla.setVisible(false);
               this.menuLic.setVisible(false);
               this.menuMant.setVisible(false);
               this.menuMarca.setVisible(false);
               this.repLicGen.setVisible(false);
               this.repMovInt.setVisible(false);
               this.repSalud.setVisible(false);
               this.liqCodFijo.setVisible(false);
               this.liqMov.setVisible(false);
               this.liqRetFija.setVisible(false);
               this.prelacion.setVisible(false);
               this.parametros.setVisible(false);
               this.liquidaciones.setVisible(false);
               break;
           case 1:
               break;
           default:
               this.barraMenu.setVisible(false);
               JOptionPane.showMessageDialog(this, "NO TIENE PERMISOS PARA ESTAR AQUI");
               this.dispose();
               break;
       }

    }

    private Date stringADate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(s);
        return date;
    }
    
    private boolean correspondeFecha(int i, int e, int u,int s) throws ClassNotFoundException, SQLException, ParseException {
        fechaLiq=log.fechaLiquidacion();
        boolean ret=false;
        Calendar cale = Calendar.getInstance();
        cale.setTime(this.stringADate(fechaLiq));
        int mes=cale.get(Calendar.MONTH)+1;
        
        if(!fechaLiq.equals("")){
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.stringADate(fechaLiq)); 
            int dia=cal.get(Calendar.DAY_OF_MONTH);
            if(mes!=2){
                if(dia==i || dia==e){
                    ret=true;
                }
            }else{
                if(dia==u || dia==s){
                    ret=true;
                }
            }
        }
        return ret;
    }
    
    private boolean chequeoUsuarioLiq() throws BDExcepcion, ClassNotFoundException, SQLException{
      nom=log.cargoParametro("USUARIO_LIQ"); 
      if("".equals(nom)){
          return true;
      }else{
          return nom.equals(this.usuario.getNombre());
      }
    }
    
    private boolean chequeoUsuarioTipoLiq() throws BDExcepcion, ClassNotFoundException, SQLException{
     nom=log.cargoParametro("USUARIO_TIPO_LIQ");
      if("".equals(nom)){
          return true;
      }else{
          return nom.equals(this.usuario.getNombre());
      }
    }

    private boolean chequeoVentanas() {
        boolean pasa=true;
        
        if(InternalLiquidaSueldo.getInstancia()!=null || InternalReLiquidaSueldos.getInstancia()!=null
           || InternalVacacionales.getInstancia()!=null || InternalReLiqVacacionales.getInstancia()!=null
                || InternalLiquidaAguinaldo.getInstancia()!=null){
            pasa=false;
        }
        
        return pasa;
    }
    
    private boolean chequeoVentanas2() {
        boolean pasa=true;
        
        if(InternalTipoLiquidacion.getInstancia()!=null){
            pasa=false;
        }
        
        return pasa;
    }

    private boolean noEstaLaFecha() throws BDExcepcion {
        return this.log.fechaLiquidacionHistorica(fechaLiq);
    }
       
}
