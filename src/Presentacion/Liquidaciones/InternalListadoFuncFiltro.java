
package Presentacion.Liquidaciones;

import Dominio.Funcionario;
import Logica.LogFuncionario;
import Presentacion.Mantenimiento.InternalModFunc;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InternalListadoFuncFiltro extends javax.swing.JInternalFrame {
    private LogFuncionario log;
    private int fr;
    DefaultTableModel tmMov=null;
    private static InternalListadoFuncFiltro instancia=null;
    public InternalListadoFuncFiltro(LogFuncionario lo,int fram) throws ClassNotFoundException, SQLException {
        initComponents();
        log=lo;
        fr=fram;
        tmMov = (DefaultTableModel) tablaCod.getModel();
        tablaCod.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount()==2){
                    try {
                        cargaFunc(e);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(InternalListadoCodLiq.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(InternalListadoCodLiq.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 }
            }    
        });
        this.cargaTabla(this.log.listarFuncionariosActivosLike(""));
        this.txtBuscar.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscar = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCod = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(316, 577));
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

        tablaCod.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num. Funcionario", "Nombre"
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
            tablaCod.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablaCod.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

     public static InternalListadoFuncFiltro instancia(LogFuncionario lo,int fram) throws ClassNotFoundException, SQLException 
   {    
         if (instancia== null)
         {
            instancia = new InternalListadoFuncFiltro(lo, fram);
         }
         return instancia;
      
   }
    
    private void cargaFunc(MouseEvent e) throws ClassNotFoundException, SQLException{
        Integer m=this.tablaCod.rowAtPoint(e.getPoint());
        String cod=String.valueOf(tmMov.getValueAt(m, 0));
        switch (fr) {
            case 0:
                InternalIngresoPorFunc.instancia().getTxtNumFunc().setText(cod);
                InternalIngresoPorFunc.instancia().getTxtNumFunc().requestFocus();
                InternalIngresoPorFunc.instancia().getBtnBuscar().doClick();
                break;
            case 1:
                InternalIngresoPorCod.instancia().getTxtNumFunc().setText(cod);
                InternalIngresoPorCod.instancia().getTxtNumFunc().requestFocus();
                InternalIngresoPorCod.instancia().getBtnBuscar().doClick();
                break;
            case 2:
                InternalModFunc.instancia().getTxtNumFunc().setText(cod);
                InternalModFunc.instancia().getTxtNumFunc().requestFocus();
                InternalModFunc.instancia().getBtnBuscar().doClick();
                break;
            case 3:
                InternalFijoPorCod.instancia().getTxtNumFunc().setText(cod);
                InternalFijoPorCod.instancia().getTxtNumFunc().requestFocus();
                InternalFijoPorCod.instancia().getBtnBuscar().doClick();
                break;
            case 4:
                InternalFijoPorFunc.instancia().getTxtNumFunc().setText(cod);
                InternalFijoPorFunc.instancia().getTxtNumFunc().requestFocus();     
                InternalFijoPorFunc.instancia().getBtnBuscar().doClick();
                break;
            case 5:
                InternalRetencionesFijas.instancia().getTxtNumFunc().setText(cod);
                InternalRetencionesFijas.instancia().getTxtNumFunc().requestFocus(); 
                break;
            case 6:
                InternalRetencionesFijas.instancia().getTxtNumFunc2().setText(cod);
                InternalRetencionesFijas.instancia().getTxtNumFunc2().requestFocus(); 
                break;
            default:
                break;
        }
        
        this.dispose();
    }
    
    
    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        try {
            String buscar="";
            int p =(int) evt.getKeyChar();
            char k = evt.getKeyChar();
            if(k==27||k==43){
                switch (fr) {
                    case 0:
                        try {
                            InternalIngresoPorFunc.instancia().getTxtNumFunc().requestFocus();
                            InternalIngresoPorFunc.instancia().getTxtNumFunc().selectAll();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                        }       break;
                    case 1:
                        try {
                            InternalIngresoPorCod.instancia().getTxtNumFunc().requestFocus();
                            InternalIngresoPorCod.instancia().getTxtNumFunc().selectAll();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                        }       break;
                    case 2:
                        try {
                            InternalModFunc.instancia().getTxtNumFunc().requestFocus();
                            InternalModFunc.instancia().getTxtNumFunc().selectAll();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                        }       break;
                    case 3:
                        InternalFijoPorCod.instancia().getTxtNumFunc().requestFocus();
                        InternalFijoPorCod.instancia().getTxtNumFunc().selectAll();
                        break;
                    case 4:
                        InternalFijoPorFunc.instancia().getTxtNumFunc().requestFocus();
                        InternalFijoPorFunc.instancia().getTxtNumFunc().selectAll();
                        break;
                    case 5:
                        InternalRetencionesFijas.instancia().getTxtNumFunc().requestFocus();
                        InternalRetencionesFijas.instancia().getTxtNumFunc().selectAll();
                        break;
                    case 6:
                        InternalRetencionesFijas.instancia().getTxtNumFunc2().requestFocus();
                        InternalRetencionesFijas.instancia().getTxtNumFunc2().selectAll();
                        break;
                    default:
                        break;
                }
            
            this.dispose();
        }
            
            
            if(p!=8){
                buscar=this.txtBuscar.getText()+k;
            }
            else{
                buscar=this.txtBuscar.getText();
            }

            this.cargaTabla(this.log.listarFuncionariosActivosLike(buscar));

           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InternalListadoCodLiq.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InternalListadoCodLiq.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void tablaCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaCodKeyPressed
        int k = (int) evt.getKeyChar();
        if(k==27||k==43){
            if(fr==0){
                try {
                    InternalIngresoPorFunc.instancia().getTxtNumFunc().requestFocus();
                    InternalIngresoPorFunc.instancia().getTxtNumFunc().selectAll();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fr==1){
                try {
                    InternalIngresoPorCod.instancia().getTxtNumFunc().requestFocus();
                    InternalIngresoPorCod.instancia().getTxtNumFunc().selectAll();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
              else if(fr==2){
                try {
                    InternalModFunc.instancia().getTxtNumFunc().requestFocus();
                    InternalModFunc.instancia().getTxtNumFunc().selectAll();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(fr==3){
                try {
                    InternalFijoPorCod.instancia().getTxtNumFunc().requestFocus();
                    InternalFijoPorCod.instancia().getTxtNumFunc().selectAll();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(InternalListadoFuncFiltro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.dispose();
        }
    }//GEN-LAST:event_tablaCodKeyPressed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCod;
    private org.edisoncor.gui.textField.TextFieldRound txtBuscar;
    // End of variables declaration//GEN-END:variables

    private void cargaTabla(ArrayList<Funcionario> lista) {
        this.LimpiarTabla();
        DefaultTableModel modelo = (DefaultTableModel)tablaCod.getModel();
        
        Object[] filas=new Object[modelo.getColumnCount()];
          
        if(lista.size()>0){
                                                          
                    for(Funcionario c:lista){                       
                            filas[0]=c.getCodFunc();
                            filas[1]=c.getNomCompleto();
                            
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
    private void LimpiarTabla() {
    DefaultTableModel modelo=(DefaultTableModel) tablaCod.getModel();
        //primero limpio todas las filas
        for (int i = 0; i < tablaCod.getRowCount(); i++) {
            modelo.removeRow(i);
            i-=1;
        }
      }
}
