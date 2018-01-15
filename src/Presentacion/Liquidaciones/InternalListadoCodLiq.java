
package Presentacion.Liquidaciones;

import Dominio.Codigo;
import Logica.LogCodigo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class InternalListadoCodLiq extends javax.swing.JInternalFrame {

    private static InternalListadoCodLiq instancia=null;
    private LogCodigo log;
    private InternalIngresoPorFunc internal;
    private InternalIngresoPorCod internalCod;
    private int fr;
    DefaultTableModel tmMov=null;
    public InternalListadoCodLiq(LogCodigo cod, Object inte,int fram) throws ClassNotFoundException, SQLException {
        initComponents();
        this.log=cod;
        fr=fram;
        if(fram==1){
            this.internalCod=(InternalIngresoPorCod) inte;
        }
        else if(fram==0){
            this.internal=(InternalIngresoPorFunc) inte;
        }
        
        ArrayList<Codigo> listaCod=log.cargaComboCodigoFullLiq();
        
                tmMov = (DefaultTableModel) tablaCod.getModel();
        tablaCod.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    try {
                        cargarCod(e);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalListadoCodLiq.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalListadoCodLiq.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
            }    
        });
        this.cargaTabla(listaCod);
    }

     public static InternalListadoCodLiq instancia(LogCodigo cod, Object inte,int fram) throws ClassNotFoundException, SQLException 
   {    
         if (instancia== null)
         {
            instancia = new InternalListadoCodLiq(cod,inte,fram);
         }
         return instancia;
      
   }
     
     private void cargarCod(MouseEvent e) throws ClassNotFoundException, SQLException{
        Integer m=this.tablaCod.rowAtPoint(e.getPoint());
        String cod=String.valueOf(tmMov.getValueAt(m, 0));
        if(fr==0){
        internal.getTxtCod().setText(cod);
        internal.getTxtCod().requestFocus();
        internal.getBtnBuscaCod().doClick();
        }
        else{
        internalCod.getTxtCod().setText(cod);
        internalCod.getTxtCod().requestFocus();
        internalCod.getBtnBuscaCod().doClick(); 
        }
        
        this.dispose();
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCod = new javax.swing.JTable();
        txtBuscar = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Listado de Códigos");
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
            tablaCod.getColumnModel().getColumn(0).setPreferredWidth(5);
            tablaCod.getColumnModel().getColumn(1).setPreferredWidth(180);
        }

        txtBuscar.setBackground(new java.awt.Color(102, 153, 255));
        txtBuscar.setForeground(new java.awt.Color(255, 255, 255));
        txtBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscar.setCaretColor(new java.awt.Color(255, 255, 255));
        txtBuscar.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Buscar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 222, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void tablaCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaCodKeyPressed
        int k = (int) evt.getKeyChar();
        if(k==27||k==43){
            if(fr==0){
            internal.getTxtCod().requestFocus();
            internal.getTxtCod().selectAll();
            }
            else{
            internalCod.getTxtCod().requestFocus();
            internalCod.getTxtCod().selectAll();
            }
            this.dispose();
        }
    }//GEN-LAST:event_tablaCodKeyPressed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        try {
            String buscar="";
            int p =(int) evt.getKeyChar();
            char k = evt.getKeyChar();
            if(p!=8){
            buscar=this.txtBuscar.getText()+k;
            }
            else{
            buscar=this.txtBuscar.getText();
            }
            
            this.cargaTabla(this.log.cargaComboCodigoLike(buscar));
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalListadoCodLiq.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalListadoCodLiq.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_txtBuscarKeyTyped

   private void LimpiarTabla() {
    DefaultTableModel modelo=(DefaultTableModel) tablaCod.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaCod.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCod;
    private org.edisoncor.gui.textField.TextFieldRound txtBuscar;
    // End of variables declaration//GEN-END:variables

    private void cargaTabla(ArrayList<Codigo> listaCod) {
        this.LimpiarTabla();
         DefaultTableModel modelo = (DefaultTableModel)tablaCod.getModel();
        
        Object[] filas=new Object[modelo.getColumnCount()];
        
      
        if(listaCod.size()>0){
                                                          
                    for(Codigo c:listaCod){                       
                            filas[0]=c.getCod().toString();
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
 
    
    
}
