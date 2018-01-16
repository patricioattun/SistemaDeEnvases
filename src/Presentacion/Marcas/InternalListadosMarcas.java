
package Presentacion.Marcas;

import Dominio.Codigo;
import Dominio.Funcionario;
import Dominio.Marca;
import Logica.LogCodigo;
import Logica.LogFuncionario;
import Logica.LogTripaliare;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class InternalListadosMarcas extends javax.swing.JInternalFrame {

    private LogTripaliare trip;
    private LogFuncionario pers;
    private LogCodigo cod;
    private static InternalListadosMarcas instancia=null;
    private ArrayList<Marca>marcas=null;
    private Funcionario f;
    private ArrayList<Codigo> codes;
    private ArrayList<Codigo> codigos=null;
    public InternalListadosMarcas(LogTripaliare trip,LogFuncionario pers) {
        initComponents();
        codes=new ArrayList<>();
        this.pers=pers;
        this.trip=trip;
         cod=new LogCodigo();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtFechahasta = new com.toedter.calendar.JDateChooser();
        txtFechaDesde = new com.toedter.calendar.JDateChooser();
        btnExc = new org.edisoncor.gui.button.ButtonIcon();
        txtCod = new org.edisoncor.gui.textField.TextFieldRound();
        jLabel6 = new javax.swing.JLabel();
        checkPorCodigo = new javax.swing.JCheckBox();
        jSup = new javax.swing.JCheckBox();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setTitle("Listados");
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

        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel3.setText("Desde:");

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel2.setText("Hasta:");

        txtFechahasta.setDateFormatString("dd/MM/yyyy");

        txtFechaDesde.setDateFormatString("dd/MM/yyyy");

        btnExc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Excel_2013_23480.png"))); // NOI18N
        btnExc.setText("buttonIcon1");
        btnExc.setToolTipText("Exportar a Excel");
        btnExc.setMaximumSize(new java.awt.Dimension(160, 68));
        btnExc.setMinimumSize(new java.awt.Dimension(160, 68));
        btnExc.setPreferredSize(new java.awt.Dimension(160, 68));
        btnExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcActionPerformed(evt);
            }
        });

        txtCod.setBackground(new java.awt.Color(102, 153, 255));
        txtCod.setForeground(new java.awt.Color(255, 255, 255));
        txtCod.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCod.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtCod.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtCod.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodFocusGained(evt);
            }
        });
        txtCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Número de Funcionario");

        checkPorCodigo.setText("Por Códigos");

        jSup.setText("Sin supervisar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addComponent(txtFechahasta, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addComponent(txtFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(checkPorCodigo))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSup, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btnExc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(checkPorCodigo)))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechahasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSup, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnExc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static InternalListadosMarcas instancia(LogTripaliare tripa,LogFuncionario pers) throws ClassNotFoundException, SQLException
   {    
         if (instancia== null)
         {
            instancia = new InternalListadosMarcas(tripa,pers);
         }
         return instancia;
      
   }
    
    private void btnExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcActionPerformed
       
       Date hoy=new Date();
       Date desde=this.txtFechaDesde.getDate();
       Date hasta=this.txtFechahasta.getDate();
       String cod=this.txtCod.getText();
       Integer sup=null;
       Integer codi=null;
       if(this.jSup.isSelected()){
           sup=0;
       }
       else{
           sup=1;
       }
       if(this.checkPorCodigo.isSelected()){
           codi=1;
       }
       else{
           codi=0;
       }
       
        if(desde!=null && hasta!=null){
           if(hasta.after(desde)){
                 if(hasta.before(hoy)){
                       try {
                           if(codi==0){
                           if(!cod.equals("")){
                               f=this.pers.funcParcial(cod);
                               if(f!=null){
                                marcas=this.trip.traerTablaFiltro(desde,hasta,f,sup);
                          
                                }
                               else{
                                  // this.lblMsg.setText("El funcionario que busca no existe");
                                   this.txtCod.selectAll();
                               }
                           }
                           else{
                               marcas=this.trip.traerTablaFiltro(desde,hasta,null,sup);
                              
                           }
                           if(marcas!=null){
                                if(marcas.size()>0){
                                    this.procesarExcelDos();
                                }
                               }
                       }
                           else{
                               if(!cod.equals("")){
                               Funcionario f=this.pers.funcParcial(cod);
                               if(f!=null){
                                marcas=this.trip.codigosFuncionarios(f,desde,hasta,sup);
                                this.codigos=this.trip.codigosDistintos(f, desde, hasta, sup);
                                }
                               else{
                                  // this.lblMsg.setText("El funcionario que busca no existe");
                                   this.txtCod.selectAll();
                               }
                               }
                               else{
                               marcas=this.trip.codigosFuncionarios(null,desde,hasta,sup);   
                               this.codigos=this.trip.codigosDistintos(null, desde, hasta, sup);
                               }
                               if(marcas!=null){
                                   if(marcas.size()>0){
                                    this.procesarExcel();
                                   }
                               }
                           }
                           
                       } catch (ClassNotFoundException ex) {
                           Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                       } catch (SQLException ex) {
                           Logger.getLogger(InternalMarcas.class.getName()).log(Level.SEVERE, null, ex);
                       } catch (IOException ex) {
                         JOptionPane.showMessageDialog(this, "Tiene el documento abierto, ciérrelo y vuelva a intentar");
                     }
                   } 
                   else{
                       JOptionPane.showMessageDialog(this, "La fecha 'Hasta' no puede ser posterior a hoy");
                   }
             
            }
        else{
            JOptionPane.showMessageDialog(this, "La fecha 'Desde' no puede ser posterior a 'Hasta'");
            }
       }
       else{
          // this.lblMsg.setText("Debe ingresar un período");
       }
       
       
       
       
       
       
        
//       if(marcas!=null){
//            if(this.txtFechaDesde!=null&&this.txtFechahasta!=null){
//                try {
//                    this.procesarExcel();
//                } catch (IOException ex) {
//                    Logger.getLogger(InternalListadosMarcas.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(InternalListadosMarcas.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SQLException ex) {
//                    Logger.getLogger(InternalListadosMarcas.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            else{
//                JOptionPane.showMessageDialog(this, "Seleccione un período de fechas");
//            }
//        }
//        else{
//            JOptionPane.showMessageDialog(this, "No hay ninguna tabla cargada para exportar");
//
//        }

    }//GEN-LAST:event_btnExcActionPerformed

    private void txtCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFocusGained
        this.txtCod.selectAll();
    }//GEN-LAST:event_txtCodFocusGained

    private void txtCodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodKeyTyped
       
       
       
        char c=evt.getKeyChar();
        if(!Character.isDigit(c)) {
            evt.consume();
        }
        if(evt.getKeyChar()==10){
            this.btnExc.doClick();
        }
    }//GEN-LAST:event_txtCodKeyTyped

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instancia=null;
    }//GEN-LAST:event_formInternalFrameClosed

    
    public void procesarExcel() throws IOException, ClassNotFoundException, SQLException{
    
    javax.swing.filechooser.FileNameExtensionFilter filterXls = new javax.swing.filechooser.FileNameExtensionFilter("Documentosxcel 95/2003", "xls");

        File fileXLS = null;
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filterXls);
        fc.setSelectedFile(fileXLS);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = fc.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            FileOutputStream fileOut = null;
            
            String[] headers = new String[]{
                "Codigo de Funcionario",//0
                "Nombre",//1
                "Cantidad",//2
                
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
              HSSFCellStyle style = (HSSFCellStyle) libro.createCellStyle();
              style.setFillForegroundColor(HSSFColor.CORAL.index);
              style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
              HSSFCellStyle style1 = (HSSFCellStyle) libro.createCellStyle();
              style1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
              style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
              Row fu=hoja.createRow(0);
              Cell cel = fu.createCell(0);
                      
               if(f!=null){
               cel.setCellValue("Reporte de cantidad por código del perídodo del "+this.formateo(this.txtFechaDesde.getDate())+" al "+this.formateo(this.txtFechahasta.getDate())+" de "+ f.getNomCompleto());
               }
               else{
               cel.setCellValue("Reporte de cantidad por código del perídodo del "+this.formateo(this.txtFechaDesde.getDate())+" al "+this.formateo(this.txtFechahasta.getDate())+" de todos los funcionarios");    
               }
               
               Integer r=2;
               Double total=0.0;
                  Row fil=hoja.createRow(r);
                  Cell celd = fil.createCell(1);
                  celd.setCellValue(headers[0]);
                  celd = fil.createCell(2);
                  celd.setCellValue(headers[1]);
                  celd = fil.createCell(3);
                  celd.setCellValue(headers[2]);
                  
               for(int s=0;s<=codigos.size()-1;s++){
                   
                  Row fi=hoja.createRow(r+1);
                  Cell ce = fi.createCell(0);
                  Cell ce1 = fi.createCell(1);
                  Cell ce2 = fi.createCell(2);
                  Cell ce3 = fi.createCell(3);
                  ce.setCellValue("Código Nº " +codigos.get(s).getCod()+" - "+codigos.get(s).getDescripcion());
                  ce.setCellStyle(style);
                  ce1.setCellStyle(style);
                  ce2.setCellStyle(style);
                  ce3.setCellStyle(style);
               for(int i=3;i<=marcas.size()+2;i++){
                   if(marcas.get(i-3).getIncongruencia().equals(codigos.get(s).getCod())){
                        Row fila = hoja.createRow(r+2);
                              r++;      
                  for(int c=0;c<headers.length+1;c++){
                    Cell celda = fila.createCell(c);
                    hoja.setColumnWidth(c, 5000);
                  
                            Marca m=marcas.get(i-3);
                                    switch(c)
                                    {                                       
                                        case 1:
                                           
                                            celda.setCellValue(m.getFunCod());
                                            break;
                                        case 2:
                                            if(f!=null){
                                                celda.setCellValue(f.getNomCompleto());
                                            }
                                            else{
                                                Funcionario func=this.pers.funcParcial(String.valueOf(m.getFunCod()));
                                                celda.setCellValue(func.getNomCompleto());
                                             
                                            }
                                            break;
                                        case 3:
                                            if(m.getIncongruencia()!=10){
                                                Double cantidad=this.trip.CantidadCodigo(this.txtFechaDesde.getDate(), this.txtFechahasta.getDate(), m.getFunCod(), m.getIncongruencia());

                                                    if(codigos.get(s).getTipoUnidad().equals(0)&& !codigos.get(s).getCod().equals(40)){
                                                        cantidad=this.recalculaCantidad(cantidad);
                                                        celda.setCellValue(cantidad);
                                                        total+=cantidad;
                                                    }
                                                    else{
                                                        celda.setCellValue(cantidad);
                                                        total+=cantidad;
                                                    }
                                            }
                                            else if(m.getIncongruencia()==10){
                                                Integer cantidad=m.getEditada();
                                                celda.setCellValue(cantidad);
                                                total+=cantidad;
                                            }
                                            break;
                                        
                                        
                                    }
                                

                    }
               }
              }
                  Row filla=hoja.createRow(r+2);
                  Cell e = filla.createCell(3);
                  e.setCellValue("Total: " +total);
                  e.setCellStyle(style1);
                  Row row=hoja.createRow(r+3);
                  Cell t = row.createCell(0);
                  t.setCellValue("");
                  total=0.0;
               r=r+3;
               
        }
              libro.write(fileOut);
            
            
        
          
                
  //Cerramos nuestro archivo
                    fileOut.close();
                  
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileXLS.getAbsolutePath());
                  

                 
        }
    }
    
     private Double recalculaCantidad(Double cant){
         Double retorno=0.0;
         retorno=cant/30;
         retorno=retorno/2;
         DecimalFormat df = new DecimalFormat("#.00");
         String i=df.format(retorno);
         i=i.replace(',', '.');
         retorno=Double.parseDouble(i);
         retorno=(Double) 0.5 * Math.round(retorno * 2);
         return retorno;
    }

   
   public void procesarExcelDos() throws IOException, ClassNotFoundException, SQLException{
    
    javax.swing.filechooser.FileNameExtensionFilter filterXls = new javax.swing.filechooser.FileNameExtensionFilter("Documentosxcel 95/2003", "xls");

        File fileXLS = null;
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filterXls);
        fc.setSelectedFile(fileXLS);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
     
        int seleccion = fc.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            FileOutputStream fileOut = null;
            
            String[] headers = new String[]{
                "Día",//0
                "Fecha",//1
                "Marca",//2
                "Diferencia",//3
                "Supervisado",//4
                "Responsable",//5
                "Fecha de Actualización",//6
                "Tipo",//7
                "ID",//8
                "Numero de Funcionario",//9
                "Códigos",//10
                "Procesado"//11
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
                          
               Row fi=hoja.createRow(0);
               Cell cel = fi.createCell(0);
               
               if(f!=null){
               cel.setCellValue("Reporte de marcas del perídodo del "+this.formateo(this.txtFechaDesde.getDate())+" al "+this.formateo(this.txtFechahasta.getDate())+" de "+ f.getNomCompleto());
               }
               else{
               cel.setCellValue("Reporte de amrcas del perídodo del "+this.formateo(this.txtFechaDesde.getDate())+" al "+this.formateo(this.txtFechahasta.getDate())+" de todos los funcionarios");    
               }
              for(int i=2;i<=marcas.size()+1;i++){
                 
                  Row fila = hoja.createRow(i);
                  
                  
                  for(int c=0;c<headers.length;c++){
                    Cell celda = fila.createCell(c);
                 
                    hoja.setColumnWidth(c, 5000);
                    
                        if(i==2){
                            celda.setCellValue(headers[c]);

                        }
                                              
                            else{
                            Marca m=marcas.get(i-2);
                                    switch(c)
                                    {                                       
                                        case 0:
                                            String dia=this.obtenerDia(m.getMarcaFecha());
                                            celda.setCellValue(dia);
                                            break;
                                        case 1:
                                            String fecha=this.obtenerFecha(m.getMarcaFecha());
                                            celda.setCellValue(fecha);
                                            break;
                                        case 2:
                                            String Hora=this.obtenerHora(m.getMarcaFecha());
                                            celda.setCellValue(Hora);
                                            break;
                                        case 3:
                                            celda.setCellValue(m.getIncongruencia());
                                            break;   
                                        case 4:
                                            if(m.getSupervisado()==0){
                                            celda.setCellValue("NO");
                                            }
                                            else{
                                             celda.setCellValue("SI");
                                            }
                                            break;
                                        case 5:
                                            celda.setCellValue(m.getResponsable());
                                            break;
                                        case 6:
                                            celda.setCellValue(this.formateo(m.getFecha()));
                                            break; 
                                        case 7:
                                            celda.setCellValue(m.getTipo());
                                            break; 
                                        case 8:
                                            celda.setCellValue(m.getId());
                                            break; 
                                        case 9:
                                            celda.setCellValue(m.getFunCod());
                                            break; 
                                        case 10:
                                            ArrayList<Integer> codigos=this.cod.obtenerCodigos(m.getFunCod(),m.getId());
                                            if(codigos.size()>0){
                                             celda.setCellValue(this.formateaCodigos(codigos));
                                             }
                                             else{
                                             celda.setCellValue("");    
                                             }
                                             break; 
                                        case 11:
                                            celda.setCellValue(this.formateo(m.getProcesado()));
                                            break; 
                                        
                                    }
                                }

                    }
              
              }
              libro.write(fileOut);
            
            
        
          
                
  //Cerramos nuestro archivo
                    fileOut.close();
                  
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileXLS.getAbsolutePath());
                  

                 
        }
    }

   private String formateo(Date hoy){
      String retorno="";
      if(hoy!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
         retorno=formateador.format(hoy);
      }
      return retorno;
     }  
   
   private String obtenerDia(Timestamp marcaFecha) {
        String retorno="";
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(marcaFecha);
     
        switch(cal.get(Calendar.DAY_OF_WEEK)){
            case 1:
                retorno="Domingo";
            break;
            case 2: 
                retorno="Lunes";   
            break;
            case 3:
                retorno="Martes";
            break;
            case 4: 
                retorno="Miercoles";   
            break;
            case 5:
                retorno="Jueves";
            break;
            case 6: 
                retorno="Viernes";   
            break;
            case 7: 
                retorno="Sábado";   
            break;
        }
        
        return retorno;
        
    }
    
       private String obtenerFecha(Timestamp marcaFecha) {
      Date date = new Date(marcaFecha.getTime());
        return this.formateo(date);
    }

    private String obtenerHora(Timestamp marcaFecha) {
        String retorno="";
      if(marcaFecha!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
         retorno=formateador.format(marcaFecha);
      }
      
      return retorno;
    }
    
        private String formateaCodigo(ArrayList<Integer> codigos,String fecha) {
        String retorno=null;
        if(codigos.size()>0){
            retorno="";
            for(int i=0;i<codigos.size();i++){
                retorno+=codigos.get(i).toString()+", ";
                for(int a=0;a<codes.size();a++){
                    if(codigos.get(i)==codes.get(a).getCod()){
                        codes.get(a).getFecha().add(fecha);
                    }
                }
            }
        }
        if(retorno!=null){
        retorno=retorno.substring(0, retorno.length()-2);
        }
        return retorno;
    }
        
        private String formateaCodigos(ArrayList<Integer> codigos) {
        String retorno=null;
        if(codigos.size()>0){
            retorno="";
            for(int i=0;i<codigos.size();i++){
                retorno+=codigos.get(i).toString()+", ";
            }
        }
        if(retorno!=null){
        retorno=retorno.substring(0, retorno.length()-2);
        }
        return retorno;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonIcon btnExc;
    private javax.swing.JCheckBox checkPorCodigo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JCheckBox jSup;
    private org.edisoncor.gui.textField.TextFieldRound txtCod;
    private com.toedter.calendar.JDateChooser txtFechaDesde;
    private com.toedter.calendar.JDateChooser txtFechahasta;
    // End of variables declaration//GEN-END:variables
}
