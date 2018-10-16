
package Presentacion.Licencias;

import Dominio.Licencia;
import Logica.LogFuncionario;
import Persistencia.BDExcepcion;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


public class InternalDiasLic extends javax.swing.JInternalFrame {

 private LogFuncionario log;
 private static InternalDiasLic instancia=null;
    public InternalDiasLic() throws ClassNotFoundException, SQLException {
        initComponents();
        this.log=new LogFuncionario();
        Date hoy = new Date();
        this.fecha.setDate(hoy);
    }

     public static InternalDiasLic instancia() throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalDiasLic();
         }
         return instancia;
      
   }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaLic = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        btnListar = new org.edisoncor.gui.button.ButtonIcon();
        fecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        lblMsg = new javax.swing.JLabel();

        setTitle("Días de Licencia por Fecha");
        setPreferredSize(new java.awt.Dimension(780, 606));
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

        tablaLic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodFunc", "Fecha de Ingreso", "Dias Generados", "Año", "Fecha Generado", "Nombre1", "Nombre2", "Apellido1", "Apellido2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaLic);

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel1.setText("Número de Funcionario");

        txtCod.setBackground(new java.awt.Color(102, 153, 255));
        txtCod.setForeground(new java.awt.Color(255, 255, 255));
        txtCod.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCod.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCod.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodKeyTyped(evt);
            }
        });

        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tabla.png"))); // NOI18N
        btnListar.setText("buttonIcon1");
        btnListar.setToolTipText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        fecha.setDateFormatString("dd/MM/yyyy");

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel2.setText("Fecha de cálculo");

        lblMsg.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                        .addGap(140, 140, 140)
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                    .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
        this.LimpiarTabla();
        this.lblMsg.setText("");
        //this.jScrollPane1.setVisible(false);
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)) {
            evt.consume();
        }
        if(evt.getKeyChar()==10){
            this.btnListar.doClick();
        }
    }//GEN-LAST:event_txtCodKeyTyped

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        this.LimpiarTabla();
        String cod=this.txtCod.getText();
        Date fecha=this.fecha.getDate();
        if(cod!=""){
           
                Integer codigo=Integer.valueOf(cod);
                if(fecha!=null){
                    try {
                        Licencia lic=this.log.calculaLicenciaIndividual(codigo, fecha);
                        if(lic!=null){
                            this.cargarTabla(lic,fecha);
                            this.lblMsg.setText(lic.getFuncionario().getNomCompletoApe());
                        }
                        else{
                            this.lblMsg.setText("Este funcionario no existe");
                        }   } catch (BDExcepcion ex) {
                              JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } catch (ParseException ex) {
                              JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    }
                }
            
            
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnListar;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JTable tablaLic;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    // End of variables declaration//GEN-END:variables
 private void LimpiarTabla() {
        
        DefaultTableModel modelo=(DefaultTableModel) tablaLic.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaLic.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }
 
  private String convierteFecha(Date fecha){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = df.format(fecha);

// Print what date is today!
        return reportDate;
    }
 
  private void cargarTabla(Licencia f, Date fecha){
        DefaultTableModel modelo = (DefaultTableModel)tablaLic.getModel();
        String fech=this.convierteFecha(fecha);
        this.jScrollPane1.setVisible(true);
        Object[] filas=new Object[modelo.getColumnCount()];
                            filas[0]=f.getFuncionario().getCodFunc();
                            filas[1]=f.getFuncionario().getFechaIngreso();
                            filas[2]=f.getDiasGenerados();
                            filas[3]=f.getAño();
                            filas[4]=fech;
                            filas[5]=f.getFuncionario().getNombre1();
                            filas[6]=f.getFuncionario().getNombre2();
                            filas[7]=f.getFuncionario().getApellido1();
                            filas[8]=f.getFuncionario().getApellido2();
                            modelo.addRow(filas);
                      
                
        this.resizeColumnWidth(tablaLic);
        JTableHeader th; 
        th = tablaLic.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 14); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente); 
              
    }
  
   //MANEJO TAMAÑO COLUMNAS
    public void resizeColumnWidth(JTable table) {
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 150; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +1 , width);
        }
        if(width > 300)
            width=300;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}    

}
