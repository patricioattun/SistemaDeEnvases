
package Presentacion.Liquidaciones;

import Dominio.Ingreso;
import Presentacion.frmPrin;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class InternalNoPrelaciones extends javax.swing.JInternalFrame {

    ArrayList<Ingreso> ingresos;
    private static InternalNoPrelaciones instancia=null;
    private boolean todos;
    public InternalNoPrelaciones(ArrayList<Ingreso> ingresos, boolean todos) {
        initComponents();
        this.ingresos=ingresos;
        this.cargoTabla();
        this.todos=todos;
        
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    cargaLinea(e);
                 }
                
             }

            
                      
        });
    }
    
   public static InternalNoPrelaciones instancia(ArrayList<Ingreso> ingresos, boolean todos) throws ClassNotFoundException, SQLException
        {    
         if (instancia== null)
         {
            instancia = new InternalNoPrelaciones(ingresos,todos);
         }
         return instancia;
      
    }

    public InternalNoPrelaciones() {
        
    }

    public boolean isTodos() {
        return todos;
    }

    public void setTodos(boolean todos) {
        this.todos = todos;
    }
    
   
   
   public static InternalNoPrelaciones instancia2() throws ClassNotFoundException, SQLException
        {    
         if (instancia== null)
         {
            instancia = new InternalNoPrelaciones();
         }
         return instancia;
      
    }

    public ArrayList<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(ArrayList<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        buttonIcon3 = new org.edisoncor.gui.button.ButtonIcon();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Se debe ingresar la prelacion de todos los funcionarios para los códigos del grupo 2 y 3 antes de liquidar");

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("A continuación se muestran los funcionarios y los codigos que no cuentan con prelación");

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Con doble click sobre la fila se habilita el ingreso");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Funcionario", "Codigo de Movimiento", "Grupo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla);

        buttonIcon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Excel_2013_23480.png"))); // NOI18N
        buttonIcon3.setText("buttonIcon1");
        buttonIcon3.setToolTipText("Exportar a Excel");
        buttonIcon3.setMaximumSize(new java.awt.Dimension(160, 68));
        buttonIcon3.setMinimumSize(new java.awt.Dimension(160, 68));
        buttonIcon3.setPreferredSize(new java.awt.Dimension(160, 68));
        buttonIcon3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonIcon3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Exportar a Excel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 724, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonIcon3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonIcon3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonIcon3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIcon3ActionPerformed
        if(ingresos!=null){
            if(ingresos.size()>0){
                    java.io.ByteArrayOutputStream memoryStream = null;
                    javax.swing.filechooser.FileNameExtensionFilter filterXls = new javax.swing.filechooser.FileNameExtensionFilter("Documentosxcel 95/2003", "xls");

                    File fileXLS = null;
                    final JFileChooser fc = new JFileChooser();
                    fc.setFileFilter(filterXls);
                    fc.setSelectedFile(fileXLS);
                    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

                    int seleccion = fc.showSaveDialog(null);
                    if (seleccion == JFileChooser.APPROVE_OPTION) {
                        try {
                            FileOutputStream fileOut = null;
                            
                            String[] headers = new String[]{
                                "Num. Funcionario",//0
                                "Nombre Completo",//1
                                "Codigo",//2
                                "Grupo",//3
                                
                            };
                            
                            
                            fileXLS = fc.getSelectedFile();
                            String name = fileXLS.getName();
                            if (name.indexOf('.') == -1) {

                                name += ".xls";
                                fileXLS = new File(fileXLS.getParentFile(), name);
                            }
                            fileOut = new FileOutputStream(fileXLS);
                            
                            Workbook libro = new HSSFWorkbook();
                            Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                            
                            for(int i=0;i<=ingresos.size();i++){
                                Row fila = hoja.createRow(i);
                                
                                
                                for(int c=0;c<headers.length;c++){
                                    Cell celda = fila.createCell(c);
                                    
                                    hoja.setColumnWidth(c, 5000);
                                    
                                    if(i==0){
                                        celda.setCellValue(headers[c]);

                                    }
                                    else{
                                        switch(c)
                                        {
                                            case 0:
                                                String num=String.valueOf(ingresos.get(i-1).getCodFunc());
                                                celda.setCellValue(num);
                                                break;
                                            case 1:
                                                String nombre=String.valueOf(ingresos.get(i-1).getFunc().getNomCompletoApe());
                                                celda.setCellValue(nombre);
                                                break;
                                            case 2:
                                                String cod=String.valueOf(ingresos.get(i-1).getCod().toString());
                                                celda.setCellValue(cod);
                                                break;
                                            case 3:
                                                Integer grupo=ingresos.get(i-1).getEnPers();
                                                if(grupo.equals(2)){
                                                    celda.setCellValue("2-CREDITOS DE NÓMINA");
                                                }else{
                                                    celda.setCellValue("3-RETENCIONES VARIAS");
                                                }
                                                break;
                                           
                                                
                                        }
                                    }
                                    
                                }
                                
                            }
                            libro.write(fileOut);
                            
                            
                            fileOut.close();
                            
                            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileXLS.getAbsolutePath());
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(InternalNoPrelaciones.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(InternalNoPrelaciones.class.getName()).log(Level.SEVERE, null, ex);
                        }



                             }
             }
        }
    }//GEN-LAST:event_buttonIcon3ActionPerformed

     private void cargaLinea(MouseEvent e) {
         Integer m=this.tabla.rowAtPoint(e.getPoint());
         Ingreso i =ingresos.get(m);
                 
         try {
           InternalFechaPrelacionFunc internal=InternalFechaPrelacionFunc.instancia2(i,todos);
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

     public void cargoTabla() {
         this.LimpiarTabla();
         this.Alinear_Grillas();
         
         DefaultTableModel modelo = (DefaultTableModel)tabla.getModel();
         Object[] filas=new Object[modelo.getColumnCount()];
         int posicion=0;
         for(Ingreso i:ingresos){
                         
                            filas[0]=i.getCodFunc();
                            filas[1]=i.getCod();
                            if(i.getEnPers().equals(2)){
                                filas[2]="2-CREDITOS DE NÓMINA";
                            }else{
                                filas[2]="3-RETENCIONES VARIAS";
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
    }
     
    public void LimpiarTabla() {
        DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }
    
    
private void Alinear_Grillas(){
        DefaultTableCellRenderer modelo = new DefaultTableCellRenderer();
        modelo.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabla.getColumnModel().getColumn(0).setCellRenderer(modelo); 
        this.tabla.getColumnModel().getColumn(1).setCellRenderer(modelo); 
        

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon buttonIcon2;
    private org.edisoncor.gui.button.ButtonIcon buttonIcon3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}
