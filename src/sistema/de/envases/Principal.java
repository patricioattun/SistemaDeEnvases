
package sistema.de.envases;

import com.itextpdf.text.DocumentException;

import com.sun.javafx.PlatformUtil;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Principal extends javax.swing.JFrame{
    
    Integer multi=0;
    Persistencia pers=null;
    ArrayList<Envase> envases;
    Ticket ticket=null;
    JPasswordField pf = null;
    String ret=null;
    public Principal() throws SQLException {
        initComponents();
        pers=new Persistencia();
        ticket=new Ticket();
        envases=new ArrayList<>();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
        this.txtBarra.requestFocus();
        this.txtUlt.setText(this.pers.buscarNro().toString());
    }
 


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCodigo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtBarra = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMov = new javax.swing.JTable();
        txtTotal = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel4 = new javax.swing.JLabel();
        txtUlt = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Recepción de Envases");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(800, 600));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        lblCodigo.setFont(new java.awt.Font("Ebrima", 1, 36)); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo.png"))); // NOI18N

        txtBarra.setBackground(new java.awt.Color(0, 153, 204));
        txtBarra.setForeground(new java.awt.Color(255, 255, 255));
        txtBarra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBarra.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        txtBarra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBarraFocusGained(evt);
            }
        });
        txtBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBarraKeyTyped(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/botella2.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 48)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Recepción de Envases");

        tablaMov.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        tablaMov.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CANTIDAD", "DESCRIPCION", "IMPORTE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaMov.setFocusable(false);
        tablaMov.setGridColor(new java.awt.Color(0, 153, 204));
        jScrollPane1.setViewportView(tablaMov);
        if (tablaMov.getColumnModel().getColumnCount() > 0) {
            tablaMov.getColumnModel().getColumn(0).setResizable(false);
            tablaMov.getColumnModel().getColumn(1).setPreferredWidth(300);
        }

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(0, 153, 204));
        txtTotal.setForeground(new java.awt.Color(255, 255, 255));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setFocusable(false);
        txtTotal.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("+ Imprimir     / Reimprimir     * Cantidad     - Limpiar     . Anular ");

        txtUlt.setEditable(false);
        txtUlt.setBackground(new java.awt.Color(0, 153, 204));
        txtUlt.setForeground(new java.awt.Color(255, 255, 255));
        txtUlt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUlt.setFocusable(false);
        txtUlt.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        txtUlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel5.setText("Ultimo Número:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtUlt, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBarra, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
                        .addGap(192, 192, 192))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel3)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 21, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUlt, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBarraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBarraKeyTyped
        char c=evt.getKeyChar();
       String str="";
       if(!Character.isDigit(c)){
           evt.consume();
       }
  
       //* Cantidad
       if(evt.getKeyChar()==42){
           if(this.esnumero(this.txtBarra.getText().trim())){
           if(!"".equals(this.txtBarra.getText())){
                if(this.txtBarra.getText().length()<=3){
                    if(Integer.valueOf(this.txtBarra.getText())>100){
                        int respuesta = JOptionPane.showConfirmDialog(this, "La cantidad de envases seleccionada es mayor a 100, ¿Desea continuar?",null, JOptionPane.YES_NO_OPTION);
                        switch (respuesta) {
                            case 0:
                                multi=Integer.valueOf(this.txtBarra.getText());
                                str=this.txtBarra.getText()+"X";
                                this.txtBarra.setText(null);
                                this.lblCodigo.setText(str);
                                evt.consume();
                                break;
                            case 1:
                                multi=0;
                                this.lblCodigo.setText(null);
                                this.txtBarra.setText(null);
                                evt.consume();
                                break;
                            default:
                              break;
                        }
                    }
                    else{
                        multi=Integer.valueOf(this.txtBarra.getText());
                        str=this.txtBarra.getText()+"X";
                        this.txtBarra.setText(null);
                        this.lblCodigo.setText(str);
                        evt.consume();
                    }
                }
                else{
                    this.txtBarra.setText("Numero excesivo");
                    this.lblCodigo.setText("");
                    this.txtBarra.selectAll();
                    this.txtBarra.requestFocus();
                }
           }
           }
           else{
               this.txtBarra.setText("Ingrese una cantidad válida");
               this.txtBarra.selectAll();
           }
       }
       //enter ingresa escaner 
       if(evt.getKeyChar()==10){
           if(this.esnumero(this.txtBarra.getText().trim())){ 
           if(!"".equals(this.txtBarra.getText())){
                 str=this.txtBarra.getText().trim();
                try {
                    Envase e=this.pers.buscaVsupInvenarti(str);
                        if(e!=null){
                            if(multi>0){
                                e.setCantidad(multi);
                                e.setPrecio(e.getPrecio()*multi);
                            }
                            else{
                                e.setCantidad(1);
                            }
                            if(this.revisaLista(e)==false){
                                this.envases.add(e);
                            }
                            this.multi=0;
                            this.txtBarra.selectAll();
                            this.txtBarra.requestFocus();
                            this.lblCodigo.setText(null);
                            this.armaTabla();
                         }
                        else{
                            this.txtBarra.setText("Envase no Encontrado");
                            this.lblCodigo.setText("");
                            this.txtBarra.selectAll();
                            this.txtBarra.requestFocus();
                        }
                        
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           }
           else{
               this.txtBarra.setText("Ingrese un código válido");
               this.txtBarra.selectAll();
           }
       }
       // - limpiar
       if(evt.getKeyChar()==45){
           this.LimpiarTabla();
           this.envases.clear();
           this.txtTotal.setText(null);
           this.txtBarra.setText(null);
           this.lblCodigo.setText(null);
           evt.consume();
       }
       // + imprimir
       if(evt.getKeyChar()==43){
            try {
                if(envases!=null){
                    if(envases.size()>0){
                String reto=this.abreInput();
                if(reto.trim().equals("2897")){
                this.guardaImprime();
                }
                else if(reto.trim().equals("")){
                        this.txtBarra.selectAll();
                        this.txtBarra.requestFocus();
                        this.ret=null;
                    }
                    else{
                        this.txtBarra.setText("La clave no es válida");
                        this.txtBarra.selectAll();
                        this.txtBarra.requestFocus();
                        this.ret=null;
                    
                }
                    }
              }
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PrintException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       }
       // / reimprimir
       if(evt.getKeyChar()==47){
            String password=null;
           if(this.esnumero(this.txtBarra.getText().trim())){
           if(!"".equals(this.txtBarra.getText().trim())){
               if(this.txtBarra.getText().trim().length()<=9){
              
                   Integer nro= Integer.valueOf(this.txtBarra.getText().trim());
              
              
               try {
                   this.armaTicket(nro,1);
                   this.LimpiarTabla();
                   this.envases.clear();
                   this.txtTotal.setText(null);
                   this.lblCodigo.setText(null);
                   //this.txtBarra.setText(null);
                                     
               } catch (IOException ex) {
                   Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
               } catch (DocumentException ex) {
                   Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
               } catch (ClassNotFoundException ex) {
                   Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                   Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
               } catch (PrintException ex) {
                       Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                   }
                            
               }
           }
           }
           else{
               this.txtBarra.setText("Ingrese un código válido");
               this.txtBarra.selectAll();
           }
       }
       // . Anular
       if(evt.getKeyChar()==46){
           if(this.esnumero(this.txtBarra.getText().trim())){
           if(!"".equals(this.txtBarra.getText())){
              Integer nro= Integer.valueOf(this.txtBarra.getText());
                try {
                    if(this.pers.anularTicket(nro)>0){
                        this.txtBarra.setText("Ticket Nro. "+nro + " anulado");
                        this.txtBarra.selectAll();
                        this.txtBarra.requestFocus();
                        this.LimpiarTabla();
                        this.envases.clear();
                        this.txtTotal.setText(null);
                        this.lblCodigo.setText(null);
                    }
                    else{
                       this.txtBarra.setText("Este ticket no existe");
                       this.txtBarra.selectAll();
                       this.txtBarra.requestFocus();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           }
           else{
               this.txtBarra.setText("Ingrese un código válido");
               this.txtBarra.selectAll();
           }
       }
    }//GEN-LAST:event_txtBarraKeyTyped

    private void txtBarraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBarraFocusGained
        this.txtBarra.selectAll();
    }//GEN-LAST:event_txtBarraFocusGained

    private void txtUltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        this.txtBarra.requestFocus();
    }//GEN-LAST:event_formFocusGained

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JTable tablaMov;
    private org.edisoncor.gui.textField.TextFieldRound txtBarra;
    private org.edisoncor.gui.textField.TextFieldRound txtTotal;
    private org.edisoncor.gui.textField.TextFieldRound txtUlt;
    // End of variables declaration//GEN-END:variables

    private boolean revisaLista(Envase e) {
        int i=0;
        boolean esta=false;
        if(envases.size()>0){
            while(i<envases.size() &&!esta){
                if(envases.get(i).getBarra().equals(e.getBarra())){
                    envases.get(i).setCantidad(envases.get(i).getCantidad()+e.getCantidad());
                    envases.get(i).setPrecio(envases.get(i).getPrecio()+e.getPrecio());
                    esta=true;
                }
                i++;
            }
        }
        return esta;
    }

    private void armaTabla() {
       this.LimpiarTabla();
       DefaultTableModel modelo = (DefaultTableModel)tablaMov.getModel();
       Double total=0.0; 
        Object[] filas=new Object[modelo.getColumnCount()];
        
        if(envases!=null){     
        if(envases.size()>0){
                                                          
                    for(Envase e:envases){                       
                            
                            filas[0]=e.getCantidad();
                            filas[1]=e.getDescripcion();
                            filas[2]=e.getPrecio();
                            modelo.addRow(filas);
                            total+=e.getPrecio();
                            
                      }
//             JTableHeader th; 
//             th = tablaMov.getTableHeader(); 
//             Font fuente = new Font("Ebrima", Font.BOLD, 14); 
//             th.setBackground(Color.LIGHT_GRAY);
//             th.setFont(fuente); 
             this.txtTotal.setText("$"+String.valueOf(total));
                }
        }
    }
    
         private void LimpiarTabla() {
        DefaultTableModel modelo=(DefaultTableModel) tablaMov.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaMov.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }

    private void guardaImprime() throws IOException, FileNotFoundException, DocumentException, ClassNotFoundException, PrintException {
        try {
            if(envases.size()>0){
                Integer nro=this.pers.insertarTicket(envases);
                if(nro!=null){
                     this.armaTicket(nro,0);
                }
            }
            else{
                this.txtBarra.setText("No hay envases ingresados");
                this.txtBarra.selectAll();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void armaTicket(Integer nro,Integer reimp) throws FileNotFoundException, IOException, DocumentException, ClassNotFoundException, SQLException, PrintException{
        FileOutputStream fileOut = null;
        File filePDF = null;
       
        ArrayList<MovEnvases> movimientos =this.pers.traerEnvases(nro);
        if(movimientos.size()>0){
            if(movimientos.get(0).getActivo()==1){
                 
                 if(PlatformUtil.isWindows()){
                     filePDF = new File("C:\\EJECUTABLES\\ticket.pdf");
                 }
                 else{
                     filePDF = new File("/opt/SistemaDeEnvases/ticket.pdf");
                 }
                
                 fileOut = new FileOutputStream(filePDF);
                 if(movimientos.get(0).getNota()==0){
                        if(this.ticket.createPDF(filePDF,nro,movimientos,reimp)){
                                if(fileOut!=null){
                               
                                    fileOut.close();
                                    
                                    if(PlatformUtil.isWindows()){
                                    //Desktop.getDesktop().print(filePDF);
                                    this.txtBarra.requestFocus();
                                    }
                                    else{
                                       this.imprimirLinux();
                                    }
                                    
                                                             
                               this.txtUlt.setText(this.pers.buscarNro().toString());
                               this.LimpiarTabla();
                               this.envases.clear();
                               this.txtTotal.setText(null);
                               this.txtBarra.setText(null);
                               this.lblCodigo.setText(null);
                                 }       
                            }
            }
                 else{
                     String orig="";
                     
                     if(movimientos.get(0).getOrigen()==1){
                         orig="nota de crédito";
                     }
                     else if(movimientos.get(0).getOrigen()==2){
                         orig="nota contado";
                     }
                     this.txtBarra.setText("El comprobante ya fue utilizado en la "+orig+" Nro. "+movimientos.get(0).getNota());
                     this.txtBarra.selectAll();
                 }
            }
            else{
            this.txtBarra.setText("Este Ticket fue anulado");
            this.txtBarra.selectAll();
        }
        }
        else{
            this.txtBarra.setText("El número de ticket no existe");
            this.txtBarra.selectAll();
        }
        this.txtBarra.requestFocus();
    }

    private boolean esnumero(String text) {
        boolean es=true;
        int i=0;
        while(i<text.length()&&es){
            if(!Character.isDigit(text.charAt(i))){
                es=false;
            }
            i++;
        }
        return es;
    }

    private String abreInput(){
     pf=new JPasswordField();
     JOptionPane pane = new JOptionPane(pf, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
    JDialog dialog = pane.createDialog("Ingrese la clave");
     //Add a listener to the dialog to request focus of Password Field
    dialog.addComponentListener(new ComponentListener(){
        String ret=null;
        @Override
        public void componentShown(ComponentEvent e) {
            pane.requestFocus();
            pf.requestFocus();
                       
        }
      
            @Override
            public void componentResized(ComponentEvent e) {
                
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
            
        });
    dialog.setVisible(true);
    pf.requestFocus();
    ret=String.valueOf(pf.getPassword());
    this.txtBarra.requestFocus();
    return ret;
    }
    
    
private void imprimirLinux() throws IOException{
    FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/opt/SistemaDeEnvases/ticket.pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }

        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();


        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }

        inputStream.close();
    
    
}
}
