
package Presentacion.Mantenimiento;


import Dominio.CodigoDesvinc;
import Logica.LogFuncionario;
import Presentacion.Marcas.InternalListadoCod;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InternalListadoEgreso extends javax.swing.JInternalFrame {

    private static InternalListadoEgreso instancia=null;
    DefaultTableModel tmMov=null;
    private InternalModFunc internal;
    private LogFuncionario logs;
    public InternalListadoEgreso(LogFuncionario log, InternalModFunc internal) throws SQLException, ClassNotFoundException {
        initComponents();
        this.internal=internal;
        this.logs=log;
        ArrayList<CodigoDesvinc> listaCod = logs.cargaComboCodigoDesvinc();     
        tmMov = (DefaultTableModel) tablaCod.getModel();
        tablaCod.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    try {
                        cargarCod(e);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalListadoCod.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalListadoCod.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
            }    
        });
        this.cargaTabla(listaCod);
    }
    
    
     private void cargarCod(MouseEvent e) throws ClassNotFoundException, SQLException{
        Integer m=this.tablaCod.rowAtPoint(e.getPoint());
        String cod=String.valueOf(tmMov.getValueAt(m, 0));
        internal.getTxtEgreso().setText(cod);
        internal.getTxtEgreso().requestFocus();
        internal.getTxtDescMotivo().requestFocus();
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCod = new javax.swing.JTable();

        setTitle("Listado de Códigos de Egreso");
        setPreferredSize(new java.awt.Dimension(750, 300));

        tablaCod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CONCEPTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaCodKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCod);
        if (tablaCod.getColumnModel().getColumnCount() > 0) {
            tablaCod.getColumnModel().getColumn(0).setMinWidth(70);
            tablaCod.getColumnModel().getColumn(0).setPreferredWidth(70);
            tablaCod.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaCodKeyPressed
        int k = (int) evt.getKeyChar();
        if(k==27||k==43){
            internal.getTxtEgreso().requestFocus();
            internal.getTxtEgreso().selectAll();
            this.dispose();
        }
    }//GEN-LAST:event_tablaCodKeyPressed

      public static InternalListadoEgreso instancia(LogFuncionario log, InternalModFunc internal) throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalListadoEgreso(log,internal);
         }
         return instancia;
      
   }
      
       private void cargaTabla(ArrayList<CodigoDesvinc> listaCod) {
        DefaultTableModel modelo = (DefaultTableModel)tablaCod.getModel();
        Object[] filas=new Object[modelo.getColumnCount()];
              
        if(listaCod.size()>0){
                                                          
                    for(CodigoDesvinc c:listaCod){                       
                            filas[0]=c.getId();
                            filas[1]=c.getDescripcion();
                            modelo.addRow(filas);
                           
                            
                    }
               
        }
        JTableHeader th; 
        th = tablaCod.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tablaCod.setColumnSelectionAllowed(false);
        tablaCod.setRowSelectionAllowed(true);
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCod;
    // End of variables declaration//GEN-END:variables
}