
package Presentacion.Reportes;

import Dominio.Codigo;
import Dominio.Licencia;
import Dominio.LicenciaAdelantada;
import Dominio.MovimientoLicencia;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Logica.logPdf;
import Persistencia.BDExcepcion;
import Presentacion.frmPrin;
import com.itextpdf.text.DocumentException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import org.edisoncor.gui.button.ButtonIcon;
import org.edisoncor.gui.textField.TextFieldRound;


public class InternalListadoMovLic extends javax.swing.JInternalFrame {
    private LogFuncionario log;
    private LogCodigo logCod;
   private static InternalListadoMovLic instancia=null;
    private ArrayList<MovimientoLicencia> lic;
    DefaultTableModel tmMov=null;
    DefaultTableModel tmAdel=null;
    MovimientoLicencia mov=null;
    private logPdf logPdf;
    private ArrayList<Codigo> listaCodigos;
    
    public InternalListadoMovLic() throws ClassNotFoundException, SQLException, BDExcepcion {
        initComponents();
        this.log=new LogFuncionario();
        this.logCod=new LogCodigo();
        this.listaCodigos=logCod.cargaComboCodigoLic();
        this.btnPdf.setEnabled(false);
        this.lblTick.setVisible(false);
        this.cargaListenTabla();
        this.jPanel1.setVisible(true);
        this.cargaCombo();
    }

    public ButtonIcon getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(ButtonIcon btnListar) {
        this.btnListar = btnListar;
    }

    public TextFieldRound getTxtCod() {
        return txtCod;
    }

    public void setTxtCod(TextFieldRound txtCod) {
        this.txtCod = txtCod;
    }
    
    public static InternalListadoMovLic instancia() throws ClassNotFoundException, SQLException, BDExcepcion
   {    
         if (instancia== null)
         {
            instancia = new InternalListadoMovLic();
         }
         return instancia;
      
   }

        private void cargaListenTabla(){
        tmAdel = (DefaultTableModel) tablaLic1.getModel();
        tmMov = (DefaultTableModel) tablaLic.getModel();
        tablaLic.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    try {
                        cargarMovimiento(e);
                    } catch (SQLException ex) {
                       JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } catch (ClassNotFoundException ex) {
                       JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } catch (BDExcepcion ex) {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } 
                 }
         }});
        tablaLic1.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    try {
                        cargarAdel(e);
                    }  catch (ParseException ex) {
                       JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } catch (SQLException ex) {
                       JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } catch (ClassNotFoundException ex) {
                       JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } catch (BDExcepcion ex) {
                       JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
                    } 
                 }
         }});
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaLic = new javax.swing.JTable();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel1 = new javax.swing.JLabel();
        btnListar = new org.edisoncor.gui.button.ButtonIcon();
        lblMsgAdel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaLic1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMsg = new javax.swing.JLabel();
        btnPdf = new org.edisoncor.gui.button.ButtonIcon();
        lblMov = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        lblTick = new javax.swing.JLabel();
        comboAño = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(1022, 650));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Movimiento Licencia", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ebrima", 1, 18))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 650));

        tablaLic.setAutoCreateRowSorter(true);
        tablaLic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num Func", "Generado Año", "Fecha Mov", "Fecha Inicio", "Fecha Fin", "Saldo", "Dias Tomados", "Saldo Post", "Codigo", "Tipo Licencia", "CodMovLic", "Tipo Licencia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaLic.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(tablaLic);
        if (tablaLic.getColumnModel().getColumnCount() > 0) {
            tablaLic.getColumnModel().getColumn(8).setMinWidth(0);
            tablaLic.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaLic.getColumnModel().getColumn(8).setMaxWidth(0);
            tablaLic.getColumnModel().getColumn(9).setResizable(false);
            tablaLic.getColumnModel().getColumn(9).setPreferredWidth(0);
            tablaLic.getColumnModel().getColumn(11).setResizable(false);
        }

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

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel1.setText("Número de Funcionario");

        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tabla.png"))); // NOI18N
        btnListar.setText("buttonIcon1");
        btnListar.setToolTipText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        lblMsgAdel.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblMsgAdel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tablaLic1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num Funcionario", "Generado Año", "Fecha Movimiento", "Fecha Inicio", "Fecha Fin", "Dias Pedidos", "CodMovLic"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaLic1);

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel2.setText("Movimientos de Licencia");

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel3.setText("Licencia Adelantada");

        lblMsg.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf.png"))); // NOI18N
        btnPdf.setText("buttonIcon1");
        btnPdf.setToolTipText("Exportar a Excel");
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });

        lblMov.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblNom.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        lblNom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tick.png"))); // NOI18N

        comboAño.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                comboAñoFocusGained(evt);
            }
        });
        comboAño.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboAñoKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel4.setText("Año Licencia Generada");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addGap(52, 52, 52)
                        .addComponent(lblMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(203, 203, 203)
                                .addComponent(lblMsgAdel, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboAño, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblNom, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(lblTick))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(318, 318, 318)
                                .addComponent(lblMov, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(comboAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(lblNom, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTick)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMov, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel2))
                    .addComponent(lblMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMsgAdel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1008, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 616, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
        this.LimpiarTabla();
        this.LimpiarTablaAdel();
        this.lblTick.setVisible(false);
        this.lblMsgAdel.setText("");
        this.lblMsg.setText("");
        this.lblNom.setText("");
        this.lblMov.setText("");
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
        this.LimpiarTablaAdel();
        this.lblMov.setText("");
        this.lblTick.setVisible(false);
        this.lblMsgAdel.setText("");
        this.lblMsg.setText("");
        this.lblNom.setText("");
        MovimientoLicencia l=null;
        if(this.comboAño.getSelectedIndex()!=0){
            l=(MovimientoLicencia) this.comboAño.getSelectedItem();
        }
        String cod=this.txtCod.getText();
        try {

            ArrayList<MovimientoLicencia> listado=log.movimientoLic(cod,l);
            this.cargarTabla(listado);
            this.lic=log.listarLicAdelantada(cod,l);
            this.cargaTablaAdelantada(lic);
            Alinear_Grillas();
        } catch (ClassNotFoundException | SQLException | BDExcepcion ex) {
           JOptionPane.showMessageDialog(null, "Ha ocurrido un problema. Reinicie el programa y si persiste consulte a Desarrollo.");
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        FileOutputStream fileOut = null;
        File filePDF = null;

        try {

            javax.swing.filechooser.FileNameExtensionFilter filterPDF = new javax.swing.filechooser.FileNameExtensionFilter("DocumentosPdf", "pdf");

            final JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filterPDF);
            fc.setSelectedFile(filePDF);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int seleccion = fc.showSaveDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION) {

                filePDF = fc.getSelectedFile();
                String name = filePDF.getName();
                if (name.indexOf('.') == -1) {
                    name += ".pdf";
                    filePDF = new File(filePDF.getParentFile(), name);
                }
                fileOut = new FileOutputStream(filePDF);
                String respuesta="";
                String ref="";
                
              
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        


        Object[] inputFields = {"Observaciones:", textField1,
                                "Num. de Referencia", textField2};
        int option = JOptionPane.showConfirmDialog(this, inputFields, "Generar PDF", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            respuesta = textField1.getText();
            ref=textField2.getText();
            this.logPdf=new logPdf();
            this.logPdf.createPDFLicencia(filePDF,mov,respuesta,ref);
        }
        else{
            filePDF=null;
        }
                
                
                
            }
            
        } 
        catch (FileNotFoundException ex) {
            this.lblMsg.setText("El documento está abierto, ciérrelo y vuelva a intentar");
            filePDF=null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalListadoMovLic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalListadoMovLic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(InternalListadoMovLic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(InternalListadoMovLic.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {
                if(fileOut!=null){
                    fileOut.close();
                }
            } catch (IOException ex) { 
                Logger.getLogger(InternalListadoMovLic.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            try {
                if(filePDF!=null){
                   Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filePDF.getAbsolutePath());
                   this.lblMsg.setText("");
                }
              } catch (IOException ex) {
                Logger.getLogger(InternalListadoMovLic.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }
    }//GEN-LAST:event_btnPdfActionPerformed

    private void comboAñoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboAñoFocusGained
        this.btnPdf.setEnabled(true);
        this.LimpiarTabla();
        this.LimpiarTablaAdel();

    }//GEN-LAST:event_comboAñoFocusGained

    private void comboAñoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboAñoKeyTyped
        char c=evt.getKeyChar();
        if(evt.getKeyChar()==10){
            this.btnListar.doClick();
        }
    }//GEN-LAST:event_comboAñoKeyTyped

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void cargaCombo() throws SQLException, ClassNotFoundException{
    ArrayList<MovimientoLicencia> lista=log.comboMovimientoAñoLic();
            this.comboAño.removeAllItems();
            this.comboAño.addItem("TODOS");
            if(lista.size()>0){
                for(int i=0;i<lista.size();i++){
                    this.comboAño.addItem(lista.get(i));
                }
            }
    }
    
    private void cargarMovimiento(java.awt.event.MouseEvent e) throws SQLException, ClassNotFoundException, ParseException, BDExcepcion{
     Integer m=this.tablaLic.rowAtPoint(e.getPoint());
     this.mov=new MovimientoLicencia();
     mov.setAñoSaldo(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,1))));
     mov.setFechaIni(this.stringADate(String.valueOf(tmMov.getValueAt(m,3))));
     mov.setFechaFin(this.stringADate(String.valueOf(tmMov.getValueAt(m,4))));
     mov.setDiasTomados(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,6))));
     mov.setSaldoPos(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,7))));
     mov.setTipoLic(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,9))));
     mov.setFuncionario(this.log.funcParcial(String.valueOf(tmMov.getValueAt(m,0))));
     mov.setFechaMovimiento(this.stringADate(String.valueOf(tmMov.getValueAt(m,2))));
     mov.setCodMovLic(Integer.valueOf(String.valueOf(tmMov.getValueAt(m,10))));
     if(mov.getFechaIni()!=null &&mov.getTipoLic()!=0){
            if(mov.getTipoLic().equals(10)){
                Licencia lic=this.log.LicenciaAño(String.valueOf(tmMov.getValueAt(m,0)),String.valueOf(tmMov.getValueAt(m,1)));
                mov.setSaldoAño(lic.getDiasGenerados());
            }
            else{
                Codigo cod=this.logCod.obtenerCodigoTipo(this.mov.getTipoLic());
                mov.setSaldoAño(cod.getValor());
            }
            this.lblMov.setText("Movimiento cargado");
            this.btnPdf.setEnabled(true);
            this.lblTick.setVisible(true);
     }
     else{
         this.lblMov.setText("Este Movimiento no se puede cargar");
         this.btnPdf.setEnabled(false);
         this.lblTick.setVisible(false);
     }
    }    
    
    private void cargarAdel(java.awt.event.MouseEvent e) throws SQLException, ClassNotFoundException, ParseException, BDExcepcion{
     Integer m=this.tablaLic1.rowAtPoint(e.getPoint());
     this.mov=new MovimientoLicencia();
     mov.setFuncionario(this.log.funcParcial(String.valueOf(tmAdel.getValueAt(m,0))));
     mov.setAñoSaldo(Integer.valueOf(String.valueOf(tmAdel.getValueAt(m,1))));
     mov.setFechaMovimiento(this.stringADate(String.valueOf(tmAdel.getValueAt(m,2))));
     mov.setFechaIni(this.stringADate(String.valueOf(tmAdel.getValueAt(m,3))));
     mov.setFechaFin(this.stringADate(String.valueOf(tmAdel.getValueAt(m,4))));
     mov.setDiasTomados(Integer.valueOf(String.valueOf(tmAdel.getValueAt(m,5))));
     mov.setTipoLic(10);
     mov.setCodMovLic(Integer.valueOf(String.valueOf(tmAdel.getValueAt(m,6))));
     if(mov.getFechaIni()!=null &&mov.getTipoLic()!=0){
            this.lblMov.setText("Movimiento cargado");
            this.btnPdf.setEnabled(true);
            this.lblTick.setVisible(true);
     }
     else{
         this.lblMov.setText("Este Movimiento no se puede cargar");
         this.btnPdf.setEnabled(false);
         this.lblTick.setVisible(false);
     }
     }    
    
     private void LimpiarTabla() {
        
        DefaultTableModel modelo=(DefaultTableModel) tablaLic.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaLic.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }
     
      private void LimpiarTablaAdel() {
      DefaultTableModel modelo=(DefaultTableModel) tablaLic1.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaLic1.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
    }
      
      private void cargarTabla(ArrayList<MovimientoLicencia> listado ) throws ClassNotFoundException, SQLException{
        
        Integer cont=listado.size();
        
        DefaultTableModel modelo = (DefaultTableModel)tablaLic.getModel();
        
        Object[] filas=new Object[modelo.getColumnCount()];
        
        if(listado.size()>0){
            this.jScrollPane1.setVisible(true);
                    if(!"".equals(this.txtCod.getText())){
                    this.lblNom.setText(listado.get(0).getFuncionario().getNomCompleto());
                    }
                    for(MovimientoLicencia f:listado){                       
                            
                        if(f.getFuncionario().getCodFunc()==510){
                            int e=7;
                        }
                            filas[0]=f.getFuncionario().getCodFunc();
                            if(f.getTipoLic().equals(10)){
                            filas[1]=f.getAñoSaldo()-1;
                            }
                            else{
                            filas[1]=f.getAñoSaldo(); 
                            }
                            filas[2]=this.formateo(f.getFechaMovimiento());
                            filas[3]=this.formateo(f.getFechaIni());
                            filas[4]=this.formateo(f.getFechaFin());
                            filas[5]=f.getSaldoAño();
                            filas[6]=f.getDiasTomados();
                            filas[7]=f.getSaldoPos();
                            filas[11]=this.buscarLic(f.getTipoLic());
                            filas[10]=f.getCodMovLic();
                            filas[9]=f.getTipoLic();
                           
                            modelo.addRow(filas);
                      }
                }
        else{
            this.lblMsg.setText("Este funcionario no tiene movimientos" );
        }
        
        this.resizeColumnWidth(tablaLic);
        JTableHeader th; 
        th = tablaLic.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente); 
       
        
    }
      
 private void cargaTablaAdelantada(ArrayList<MovimientoLicencia> lista){
    //this.jScrollPane1.setVisible(false);
    if(lista.isEmpty()){
        this.lblMsgAdel.setText("No existen registros de licencias adelantadas para el prox año");
    }
    else{
        DefaultTableModel modelo = (DefaultTableModel)tablaLic1.getModel();
        
        Object[] filas=new Object[modelo.getColumnCount()];
        
        if(lista.size()>0){
         this.jScrollPane1.setVisible(true);                                                 
                    for(MovimientoLicencia f:lista){                       
                            
                            filas[0]=f.getFuncionario().getCodFunc();
                            filas[1]=f.getAñoSaldo()-1;
                            filas[2]=this.formateo(f.getFechaMovimiento());
                            filas[3]=this.formateo(f.getFechaIni());
                            filas[4]=this.formateo(f.getFechaFin());
                            filas[5]=f.getDiasTomados();
                            filas[6]=f.getCodMovLic();
                            
                           
                            
                            modelo.addRow(filas);
                      }
                }
        this.resizeColumnWidth(tablaLic);
        JTableHeader th; 
        th = tablaLic1.getTableHeader(); 
        Font fuente = new Font("Ebrima", Font.BOLD, 12); 
        th.setBackground(Color.LIGHT_GRAY);
        th.setFont(fuente); 
       
        
    }
    
}
      
       private void Alinear_Grillas(){
            
        DefaultTableCellRenderer modelo = new DefaultTableCellRenderer();
        modelo.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tablaLic.getColumnModel().getColumn(0).setCellRenderer(modelo); 
        this.tablaLic.getColumnModel().getColumn(1).setCellRenderer(modelo); 
        this.tablaLic.getColumnModel().getColumn(5).setCellRenderer(modelo);
        this.tablaLic.getColumnModel().getColumn(6).setCellRenderer(modelo); 
        this.tablaLic.getColumnModel().getColumn(7).setCellRenderer(modelo); 
        this.tablaLic.getColumnModel().getColumn(8).setCellRenderer(modelo);  
        
        this.tablaLic1.getColumnModel().getColumn(0).setCellRenderer(modelo); 
        this.tablaLic1.getColumnModel().getColumn(1).setCellRenderer(modelo); 
        this.tablaLic1.getColumnModel().getColumn(5).setCellRenderer(modelo);  
            
    }
       
   private Date stringADate(String s) throws ParseException{
    Date date=null;
        if(s!=""){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                date = formatter.parse(s);
        }
    return date;
    }
   
      private String formateo(Date hoy){
      String ret="";
      if(hoy!=null){  
      SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
      ret=formateador.format(hoy);
        }
      return ret;
     }  

      private String buscarLic(Integer tipoLic) {
        String retorno="";
        Integer i=0;
        boolean esta=false;
        while(i<this.listaCodigos.size()&& !esta){
            if(listaCodigos.get(i).getCod().equals(tipoLic)){
                retorno=listaCodigos.get(i).toString();
                esta=true;
            }
            i++;
        }
        return retorno;
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
      
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnListar;
    private org.edisoncor.gui.button.ButtonIcon btnPdf;
    private javax.swing.JComboBox comboAño;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMov;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JLabel lblMsgAdel;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblTick;
    private javax.swing.JTable tablaLic;
    private javax.swing.JTable tablaLic1;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    // End of variables declaration//GEN-END:variables
}
