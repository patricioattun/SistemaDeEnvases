
package Presentacion.Liquidaciones;

import Dominio.Funcionario;
import Dominio.Ingreso;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import Persistencia.PersLiquida;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class InternalPromedioHorasExtras extends javax.swing.JInternalFrame {

    private LogFuncionario log;
    private PersLiquida liq;
    private static InternalPromedioHorasExtras instancia=null;
    public InternalPromedioHorasExtras() throws ClassNotFoundException, SQLException {
        initComponents();
        log=new LogFuncionario();
        liq=new PersLiquida();
        this.txtFechaLiq.setText(this.log.fechaLiquidacion());
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtFechaLiq = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        buttonSeven1 = new org.edisoncor.gui.button.ButtonSeven();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        inicio = new javax.swing.JLabel();
        fin = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Promedio de Horas Extras Anual");

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

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Fecha de Liquidación");

        buttonSeven1.setBackground(new java.awt.Color(0, 153, 255));
        buttonSeven1.setText("Consultar");
        buttonSeven1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSeven1ActionPerformed(evt);
            }
        });

        tabla.setAutoCreateRowSorter(true);
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD. FUNC", "NOMBRES", "EXT COM C31", "PROM C24", "EXT FNL C32", "PROM C46", "EXT FL C33", "PROM C47", "EXT COM NOC C61", "PROM C81", "EXT FNL NOC C62", "PROM C82", "EXT FL NOC C63", "PROM C83"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setToolTipText("");
        tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabla.setMinimumSize(new java.awt.Dimension(700, 64));
        jScrollPane1.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(75);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(75);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(75);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(9).setPreferredWidth(75);
            tabla.getColumnModel().getColumn(10).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(11).setPreferredWidth(75);
            tabla.getColumnModel().getColumn(12).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(13).setPreferredWidth(75);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(buttonSeven1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fin, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(buttonSeven1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtFechaLiq, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fin, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static InternalPromedioHorasExtras instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalPromedioHorasExtras();
         }
         return instancia;
      
   }
    private void txtFechaLiqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaLiqKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaLiqKeyTyped

    private void buttonSeven1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSeven1ActionPerformed
        try {
            this.inicio.setText(new Date().toString());
            Date fecha = this.stringADate(this.txtFechaLiq.getText());
            ArrayList<Funcionario> funcionarios = this.liq.cargoPromedioHorasExtras(fecha);
            if(funcionarios.size()>0){
                this.cargoTabla(funcionarios);
            }
        } catch (ParseException ex) {
            Logger.getLogger(InternalPromedioHorasExtras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BDExcepcion ex) {
            Logger.getLogger(InternalPromedioHorasExtras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_buttonSeven1ActionPerformed

     private Date stringADate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(s);
        return date;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonSeven buttonSeven1;
    private javax.swing.JLabel fin;
    private javax.swing.JLabel inicio;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    private org.edisoncor.gui.textField.TextFieldRound txtFechaLiq;
    // End of variables declaration//GEN-END:variables

     public void LimpiarTabla() {
        
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }
     
    private void cargoTabla(ArrayList<Funcionario> funcionarios) {
         this.LimpiarTabla();
         this.Alinear_Grillas();
         
         DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
         Object[] filas=new Object[modelo.getColumnCount()];
         int posicion=0;
         for(Funcionario f:funcionarios){
                         
                            filas[0]=f.getCodFunc();
                            filas[1]=f.getNomCompletoApe();
                            int e=2;
                            for(Ingreso i: f.getIngresos()){
                                  filas[e]=this.fijarNumero(i.getCantidad(), 2);
                                  filas[e+1]=this.fijarNumero(i.getImporte(), 2);
                               e+=2;
                            }    
                            modelo.addRow(filas);
                           
                }
        JTableHeader th; 
        th = tabla.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(true);
        tabla.changeSelection(posicion, 0, false, false);
        
        this.fin.setText(new Date().toString());
    }
    
    private void Alinear_Grillas(){
        DefaultTableCellRenderer modelo = new DefaultTableCellRenderer();
        modelo.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabla.getColumnModel().getColumn(0).setCellRenderer(modelo); 
        int i =2;
        while(i<14){
             this.tabla.getColumnModel().getColumn(i).setCellRenderer(modelo); 
             i++;
        }
    }
    
     public double fijarNumero(double numero, int digitos) {
        double resultado;
        BigDecimal num = new java.math.BigDecimal(String.valueOf(numero)); 
        BigDecimal pow = new java.math.BigDecimal(Math.pow(10, digitos));
        BigDecimal res = num.multiply(pow);
        resultado = res.doubleValue();
        resultado = Math.round(resultado);
        res=new java.math.BigDecimal(String.valueOf(resultado)); 
        res=res.divide(pow);
        resultado = res.doubleValue();
        return resultado;
   }

}
