
package Logica;

import Dominio.CodigoPdf;
import Dominio.Funcionario;
import Dominio.Horario;
import Dominio.Licencia;
import Dominio.MovimientoLicencia;
import Dominio.Sucursal;
import Persistencia.PersistenciaFuncionario;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
//import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;



public class logPdf {
    private LogCodigo logCod;
    private PersistenciaFuncionario pers;
    private LogFuncionario log;
    public logPdf() throws ClassNotFoundException, SQLException {
        this.logCod=new LogCodigo();
        this.pers=new PersistenciaFuncionario();
        this.log=new LogFuncionario();
    }
    
    private static final String iTextExampleImage = "/JAVA/Escritorio/Sistema de Sueldos/src/Imagenes/logo.png";
    String[] headers = new String[]{
                "Nombre",//0
                "Fecha Ingreso",//1
                "Fecha Desde",//2
                "Fecha Hasta",//3
                "Dias",
                "Firma"
    };
    
    
    public void createPDF(File pdfNewFile,ArrayList<Licencia> listado, Sucursal suc, String año, String mes) throws FileNotFoundException, DocumentException, BadElementException {
       Document document = new Document();
       PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
       document.open();
       this.armoCabezal(document,listado,suc,año,mes);
       Paragraph texto = new Paragraph(); 
       String linea = "";
       int cantLineas=0;   
       
       for(Licencia l:listado){
          if(cantLineas>15){
                    document.newPage();
                    cantLineas=0;
                    armoCabezal(document,listado,suc,año,mes);
                }
          Date hoy=new Date();
          if(l.getFechaIni()!=null){
          String func=l.getFuncionario().getLugarTrabajo().getNombre();
          String s=suc.getNombre();
          if(this.comparaFechas(l.getFechaIni(),mes)&& func==s){
          texto = new Paragraph();            
                texto.setAlignment(Element.ALIGN_LEFT);
                texto.setFont(FontFactory.getFont("Courier", 10, Font.BOLD));
                
                //CON EL SIGNO - AGREGAS ESPACIOS AL FINAL DE LA CADENA 
                //DE LO CONTRARIO LOS PONE AL INICIO                
                linea=String.format("%1$-20s",(l.getFuncionario().getNomApe())) + String.format("%1$-17s", this.formateo(l.getFuncionario().getFechaIngreso()))
                + String.format("%1$-14s",this.formateo(l.getFechaIni())) + String.format("%1$-15s",this.formateo(l.getFechaFin()))+String.format("%1$-5s",l.getDiasGenerados())+"________________";
                document.add(new Phrase(Chunk.NEWLINE));
                texto.add(linea);    
                document.add(texto);
                
                cantLineas++;
          }
       }
       }

       document.close();             

    }
    
    
    private void armoCabezal(Document document, ArrayList<Licencia> listado,Sucursal suc,String año,String m) throws BadElementException, DocumentException{
        Image image;
        try {  
            image = Image.getInstance(iTextExampleImage);
            image.scaleToFit(150, 750);
            document.add(image);
        } catch (IOException ex) {
            Logger.getLogger(logPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       Date hoy=new Date();
       Paragraph mes = new Paragraph(m);
       Paragraph dependencia = new Paragraph("DEPENDENCIA: "+suc.getNombre()+", "+this.formateo(hoy));
       Paragraph chunk1 = new Paragraph("COMUNICADO DE LICENCIA ANUAL");
       Paragraph chunk2 = new Paragraph("GENERADO EN EL AÑO "+año);
       Paragraph chunk3= new Paragraph("Nombre                       Fecha Ingreso       Fecha Inicio      Fecha Fin          Días             Firma ");       
       dependencia.setAlignment(Element.ALIGN_RIGHT);
       mes.setAlignment(Element.ALIGN_RIGHT);
       chunk1.setAlignment(Element.ALIGN_CENTER);
       chunk2.setAlignment(Element.ALIGN_CENTER);
       document.add(mes);
       document.add(dependencia);
       document.add(new Phrase(Chunk.NEWLINE));
       document.add(chunk1);
       document.add(chunk2);
       document.add(new Phrase(Chunk.NEWLINE));
       document.add(chunk3);
       //document.add(new Phrase(Chunk.NEWLINE));
    }
    private String obtenerMes(Date hoy){
      SimpleDateFormat formateador = new SimpleDateFormat("MMMMMMMMMMMMM");
      Calendar cal = Calendar.getInstance();
      cal.setTimeZone(TimeZone.getDefault());
      return formateador.format(hoy).toUpperCase();
     }
    private String formateo(Date hoy){
      String str="----------";
        if(hoy!=null){
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
      
        str=formateador.format(hoy);
        }
        return str;
     }

    private boolean comparaFechas(Date fechaIni, String mes) {
    String ini=this.obtenerMesNum(fechaIni);
    String mesTexto=this.obtenerMesEscrito(ini);
    String aIni=this.obtenerAño(fechaIni);
    if(mesTexto.equals(mes)){
        return true;
    }
    else{
        return false;
    }
    }
    
    private String obtenerMesNum(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.MONTH)+1);
        return t;
    }
    
    private String obtenerAño(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.YEAR));
        return t;
    }

    public void createPDFLicencia(File pdfNewFile, MovimientoLicencia mov,String observa,String ref) throws FileNotFoundException, DocumentException, SQLException, ClassNotFoundException, ParseException {
       Document document = new Document();
       PdfPTable table = new PdfPTable(1);
       table.setWidthPercentage(100 / 5.23f);
       table.setWidths(new int[]{1});
       table.setHorizontalAlignment(Element.ALIGN_RIGHT);
       PdfPCell cell;
       
       PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
       document.open();
        Image image;
        try {  
            image = Image.getInstance(iTextExampleImage);
            image.scaleToFit(150, 750);
            document.add(image);
        } catch (IOException ex) {
            Logger.getLogger(logPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date hoy=new Date();
        
        Font fue=new Font();
        fue.setSize(20);
        Paragraph lic = new Paragraph("LICENCIA",fue);
        //String cod=String.valueOf(mov.getCodMovLic());
        Paragraph codigo=new Paragraph(ref);
        codigo.setAlignment(Element.ALIGN_RIGHT);
        lic.setAlignment(Element.ALIGN_CENTER);
         //NOMBRE Y NUMERO
        Paragraph Num = new Paragraph();            
                Num.setAlignment(Element.ALIGN_LEFT);
                Num.setFont(FontFactory.getFont("Courier", 14));
        String linea=mov.getFuncionario().getCodFunc().toString();
        Num.add(linea);
        
        Paragraph texto = new Paragraph();            
                texto.setAlignment(Element.ALIGN_LEFT);
                texto.setFont(FontFactory.getFont("Courier", 14));
        String line=mov.getFuncionario().getNomCompleto();
        texto.add(line);
        
        Paragraph cabFunc=new Paragraph("FUNCIONARIO: ");
        Paragraph cabNom=new Paragraph("NOMBRE: ");
        cabFunc.add(Num);
        cabNom.add(texto);
        //----------
        Paragraph fecha = new Paragraph();            
                fecha.setAlignment(Element.ALIGN_RIGHT);
                fecha.setFont(FontFactory.getFont("Courier", 14));
                String lineFecha=this.formateo(mov.getFechaMovimiento());
        fecha.add(lineFecha);
        
        LineSeparator separador = new LineSeparator();
        Paragraph tipoLic = new Paragraph();            
                tipoLic.setAlignment(Element.ALIGN_LEFT);
                tipoLic.setFont(FontFactory.getFont("Courier", 14));
        String codLinea=this.logCod.obtenerCodigoTipo(mov.getTipoLic()).toString();
        tipoLic.add(codLinea);
        Paragraph tipoCod=new Paragraph("TIPO: ");        
        tipoCod.add(tipoLic);
        //TABLA FECHAS
        cell = new PdfPCell(new Phrase("DESDE"));
        cell.setMinimumHeight(15);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(this.formateo(mov.getFechaIni())));
        cell.setMinimumHeight(30);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("HASTA"));
        cell.setMinimumHeight(15);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(this.formateo(mov.getFechaFin())));
        cell.setMinimumHeight(30);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(mov.getDiasTomados()+" Días"));
        cell.setMinimumHeight(30);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        Paragraph nota = new Paragraph("NOTA: Si se trata de un aviso telefonico, lo firmará su receptor, indicando la hora exacta en que fue recepcionado.");
        Paragraph diasSuc = new Paragraph();            
                diasSuc.setAlignment(Element.ALIGN_RIGHT);
                diasSuc.setFont(FontFactory.getFont("Courier", 14,Font.BOLD));
        String diasLinea=mov.getDiasTomados().toString();
        diasSuc.add(diasLinea);
        Paragraph Suc = new Paragraph("Total de días de Licencia autorizados: ");
        Suc.add(diasSuc);
        Paragraph sucursal = new Paragraph();            
                sucursal.setAlignment(Element.ALIGN_RIGHT);
                sucursal.setFont(FontFactory.getFont("Courier", 14,Font.BOLD));
        String lineaSuc=mov.getFuncionario().getLugarTrabajo().getNombre();
        sucursal.add(lineaSuc);
        Paragraph Sucs = new Paragraph("SUCURSAL: ");
        Sucs.add(sucursal);
        Paragraph sobrelinea = new Paragraph("___________________________                                                ___________________________");
        Paragraph firmantes = new Paragraph("          Firma del Funcionario                                                                       Jefe de Sección");
        Paragraph adjuntan = new Paragraph("Se adjuntan los certificados correspondientes y se procederá a realizar las notaciones pertinentes previo a su archivo.");
        Paragraph observaciones=new Paragraph("OBSERVACIONES:");
        observaciones.setFont(FontFactory.getFont("Courier", 14,Font.BOLD));
        Paragraph obs=null;
        Paragraph obs1=null;
        obs=new Paragraph(observa);
//        if(mov.getTipoLic().equals(10)){
//            if(mov.getSaldoPos()!=null){
//            obs=new Paragraph(mov.getSaldoAño()+" Dias de Licencia generado año "+mov.getAñoSaldo()+".");
//            String codf=String.valueOf(mov.getFuncionario().getCodFunc());
//            String añoSaldo=String.valueOf(mov.getAñoSaldo());
//            Licencia f=this.log.LicenciaAño(codf,añoSaldo);
//            obs1=new Paragraph(this.pers.calcularAdelantado(mov.getAñoSaldo()+1,mov.getFuncionario().getCodFunc())+" Días a cuenta, " + "Saldo "+mov.getSaldoPos()+" días.");
//           // VER SALDO SI SE DEVUELVEN DIAS
//            }
//            else{
//                obs=new Paragraph("10 "+" Dias de Licencia adelanto de año "+mov.getAñoSaldo()+".");
//                Integer adel=this.pers.calcularAdelantado(mov.getAñoSaldo()+1,mov.getFuncionario().getCodFunc());
//                obs1=new Paragraph(adel+" Días a cuenta tomados, " + "Saldo "+(10-adel)+" días.");
//            }
//        }
//        else{
//            obs=new Paragraph(mov.getSaldoAño()+" Dias de Licencia año "+mov.getAñoSaldo());
//            obs1=new Paragraph("Saldo "+mov.getSaldoPos()+" días.");
//            
//        }
        
        
        
        document.add(lic);
        //document.add(new Phrase(Chunk.NEWLINE));
        document.add(codigo);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(cabFunc);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(cabNom);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(fecha);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(separador);
        document.add(tipoCod);
        document.add(table);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(separador);
        document.add(nota);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(Suc);
        document.add(Sucs);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(separador);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(sobrelinea);
        document.add(firmantes);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(adjuntan);
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(separador);
        document.add(observaciones);
        document.add(obs);
       // document.add(obs1);
//        document.add(cabecera);

        document.close(); 
    }

    private String obtenerMesEscrito(String m) {
        String ret="";
        
        switch(m){
            case "1":
                ret="ENERO";
            break;
            case "2":
                ret="FEBRERO";
            break;
            case "3":
                ret="MARZO";
            break;
            case "4":
                ret="ABRIL";
            break;
            case "5":
                ret="MAYO";
            break;
            case "6":
                ret="JUNIO";
            break;
            case "7":
                ret="JULIO";
            break;
            case "8":
                ret="AGOSTO";
            break;
            case "9":
                ret="SEPTIEMBRE";
            break;
            case "10":
                ret="OCTUBRE";
            break;
            case "11":
                ret="NOVIEMBRE";
            break;
            case "12":
                ret="DICIEMBRE";
            break;
            default:
            break;
        }
        return ret;
    }

    public void createPDFFichaFunc(File filePDF, Funcionario f) throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException {
       Document document = new Document();
       PdfWriter.getInstance(document, new FileOutputStream(filePDF));
       document.open();
       this.armoCabezalFichaFunc(document,f);
       Paragraph datos = new Paragraph();
                 datos.setAlignment(Element.ALIGN_LEFT);
                 datos.setFont(FontFactory.getFont("Courier", 16,Font.BOLD));
                 datos.add("DATOS PERSONALES:");
                 datos.add("\n \n");
                 
       Paragraph texto = new Paragraph();            
                texto.setAlignment(Element.ALIGN_LEFT);
                texto.setFont(FontFactory.getFont("Courier", 14));
       texto.add("*Dirección: "+f.getDireccion());
       texto.add("\n");
       texto.add("*Departamento: "+f.getDepartamento().toString());
       texto.add("\n");
       texto.add("*Localidad: "+f.getLocalidad());
       texto.add("\n");
       texto.add("*Fecha de Nacimiento: "+this.formateo(f.getFechaNac()));
       texto.add("\n");
       texto.add("*Estado Civil: "+f.getEstadoCivil());
       texto.add("\n");
       texto.add("*Cédula: "+f.getCedula());
       texto.add("\n");
       if(!f.getCredencial().trim().equals("")){
           texto.add("*Credencial: "+f.getCredencial());
           texto.add("\n");
       }
       else{
           texto.add("*Credencial: -----------");
           texto.add("\n");
       }
       texto.add("*Iniciales: "+f.getIniciales());
       texto.add("\n");
       if(!f.getCelular().trim().equals("")){
            texto.add("*Celular: "+f.getCelular());
            texto.add("\n");
       }
       else{
            texto.add("*Celular: -----------");
            texto.add("\n");
    
       }
       texto.add("*Telefono: "+f.getTelefono());
       texto.add("\n");
           String sexo="";
       if(f.getSexo().equals('M')){
           sexo="Hombre";
       }
       else{
           sexo="Mujer";
       }
       texto.add("*Sexo: "+sexo);
       texto.add("\n");
       texto.add("*Vencimiento Carné de salud: "+this.formateo(f.getVencimientoCarne()));
       texto.add("\n \n");
       Paragraph datosl = new Paragraph();
                 datosl.setAlignment(Element.ALIGN_LEFT);
                 datosl.setFont(FontFactory.getFont("Courier", 16,Font.BOLD));
                 datosl.add("DATOS LABORALES:");
                 datosl.add("\n \n");
       Paragraph texto1 = new Paragraph();            
                texto1.setAlignment(Element.ALIGN_LEFT);
                texto1.setFont(FontFactory.getFont("Courier", 14));
       texto1.add("*Fecha de Ingreso: "+this.formateo(f.getFechaIngreso()));
       texto1.add("\n");
       texto1.add("*Fecha de Egreso: "+this.formateo(f.getFechaEgreso()));
       texto1.add("\n");
       if(f.getFechaEgreso()!=null){
           texto1.add("*Motivo de Egreso: "+ this.pers.CodigoDesvincDesc(f.getCodigoDesvinc().getId()));
       }
       texto1.add("*Cargo: "+f.getCargo().toString());
       texto1.add("\n");
       texto1.add("*Vigencia Cargo: "+this.formateo(f.getVigenciaCargo()));
       texto1.add("\n");
       texto1.add("*Sueldo: "+this.decimales(f.getSueldoCargo()));
       texto1.add("\n");
       texto1.add("*Base Horaria: "+f.getBaseHoraria());
       texto1.add("\n");
       texto1.add("*Base Horas: "+ f.getBaseHoras());
       texto1.add("\n");
       texto1.add("*Sucursal: "+f.getLugarTrabajo().toString());
       texto1.add("\n");
       texto1.add("*Centro Costo: "+f.getCentroCosto().toString());
       texto1.add("\n");
       texto1.add("*Banco: "+f.getBanco().toString());
       texto1.add("\n");
       String str=String.valueOf(f.getCuenta());
       str=str.substring(0, str.length()-2);
       texto1.add("*Nro. de Cuenta: "+str);
       texto1.add("\n");
       String tipo="";
       if(f.getTipoCuenta().trim().equals("CC")){
           tipo="Cuenta Corriente";
       }
       else if(f.getTipoCuenta().trim().equals("CA")){
           tipo="Caja de Ahorro";
       }
       texto1.add("*Tipo de cuenta: "+tipo);
       texto1.add("\n");
       String afap="";
       if(f.getAfap().equals(1)){
           afap="Si";
       }
       else if(f.getAfap().equals(0)){
           afap="No";
       }
       texto1.add("*Afap: "+afap);
       texto1.add("\n");
       texto1.add("*SNS: "+f.getSns().toString());
       texto1.add("\n");
       String hora="";
       for(int i=0;i<f.getHorarios().size();i++){
           if(f.getHorarios().size()==1){
           hora+=f.getHorarios().get(i).toString();
           }
           else{
              if(i==0){
                 hora+=f.getHorarios().get(i).toString()+"\n"; 
              }
              else if(i==1){
                  hora+="           "+f.getHorarios().get(i).toString(); 
              }
           }
       }
     
       texto1.add("*Horario: "+hora+"\n \n");
       LineSeparator linea = new LineSeparator();
       Paragraph chunk= new Paragraph("Asociación Cooperativa Electoral, 8 de Octubre 2897, Montevideo \n Teléfono 2487 2711");       
       chunk.setAlignment(Element.ALIGN_CENTER);
       document.add(datos);
       document.add(texto);
       document.add(datosl);
       document.add(texto1);
       document.add(linea);
       document.add(chunk);
       document.close();  
    }
    
     private void armoCabezalFichaFunc(Document document,Funcionario f) throws BadElementException, DocumentException{
        Image image;
        try {  
            image = Image.getInstance(iTextExampleImage);
            image.scaleToFit(150, 750);
            document.add(image);
        } catch (IOException ex) {
            Logger.getLogger(logPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       Date hoy=new Date();
      
       Paragraph dependencia = new Paragraph();
       dependencia.setAlignment(Element.ALIGN_CENTER);
       dependencia.setFont(FontFactory.getFont("Arial", 16,Font.BOLD));
       dependencia.add("ASOCIACIÓN COOPERATIVA ELECTORAL");
       dependencia.add("\n \n");
       dependencia.add("FICHA DE FUNCIONARIO");
       dependencia.add("\n \n");
       dependencia.add("GENERADO EL "+ this.formateo(hoy));
       dependencia.add("\n \n");
       dependencia.add(f.getNomCompletoYNum());
       dependencia.add("\n \n");
       document.add(dependencia);
      
       
    }
     
    private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
}
}
