
package Presentacion.Marcas;

import Dominio.Codigo;
import Dominio.Marca;
import Logica.LogCodigo;
import Presentacion.RenderMarca;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


public class InternalListadoCod extends javax.swing.JInternalFrame {

    private LogCodigo log;
    private static InternalListadoCod instancia=null;
    DefaultTableModel tmMov=null;
    private InternalMarcaCodigo internal;
    public InternalListadoCod(LogCodigo cod,InternalMarcaCodigo inte) throws SQLException, ClassNotFoundException {
        initComponents();
        log= cod;
        internal=inte;
        ArrayList<Codigo> listaCod=log.cargaComboCodigoFull();
        
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
        internal.getTxtCod().setText(cod);
        internal.getTxtCod().requestFocus();
        internal.getTxtCant().requestFocus();
        
        this.dispose();
    }
    
    
     public static InternalListadoCod instancia(LogCodigo cod,InternalMarcaCodigo inte) throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalListadoCod(cod,inte);
         }
         return instancia;
      
   }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCod = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
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
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
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
            tablaCod.getColumnModel().getColumn(0).setMinWidth(100);
            tablaCod.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaCod.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaCod.getColumnModel().getColumn(1).setMinWidth(200);
            tablaCod.getColumnModel().getColumn(1).setPreferredWidth(200);
            tablaCod.getColumnModel().getColumn(1).setMaxWidth(200);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void tablaCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaCodKeyPressed
        int k = (int) evt.getKeyChar();
        if(k==27||k==43){
          internal.getTxtCod().requestFocus();
          internal.getTxtCod().selectAll();
          this.dispose();
        }
    }//GEN-LAST:event_tablaCodKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int k = (int) evt.getKeyChar();
        if(k==27||k==43){
          internal.getTxtCod().requestFocus();
          internal.getTxtCod().selectAll();
          this.dispose();
        }
    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCod;
    // End of variables declaration//GEN-END:variables

    private void cargaTabla(ArrayList<Codigo> listaCod) {
         DefaultTableModel modelo = (DefaultTableModel)tablaCod.getModel();

        Object[] filas=new Object[modelo.getColumnCount()];


        if(listaCod.size()>0){

                    for(Codigo c:listaCod){                       
                            filas[0]=c.getCod().toString();
                            filas[1]=c.getDescripcion();

                            modelo.addRow(filas);


                    }

        }
        this.resizeColumnWidth(tablaCod);
        JTableHeader th; 
        th = tablaCod.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente);  
        tablaCod.setColumnSelectionAllowed(false);
        tablaCod.setRowSelectionAllowed(true);

    }
    
    public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 160; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +1 , width);
        }
        if(width > 100)
            width=150;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}    
}
