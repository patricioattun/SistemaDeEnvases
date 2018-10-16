
package Logica;

import Dominio.CodigoPdf;
import Dominio.Declaracion;
import Dominio.Funcionario;
import Dominio.Horario;
import Dominio.Licencia;
import Dominio.Liquidacion;
import Dominio.MovimientoLicencia;
import Dominio.PersonasACargo;
import Dominio.ResumenLiquidacion;
import Dominio.Sucursal;
import Dominio.irpfDetallado;
import Persistencia.BDExcepcion;
import Persistencia.PersistenciaFuncionario;
import Persistencia.PersistenciaLiquidacion;
import com.itextpdf.text.BadElementException;
import static com.itextpdf.text.BaseColor.BLACK;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
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
import java.math.BigDecimal;
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
    
    private String formateoHora(Date hoy){
      String str="----------";
        if(hoy!=null){
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
      
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
    
    private String decimalesLargo(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00000000000",simbolo);
    return df.format(sueldo);
    }
    
    
    //DECLARACIONES
    public void armoDeclaracion(File filePDF, Funcionario f,Declaracion d, ArrayList<PersonasACargo> lista) throws FileNotFoundException, DocumentException{
       Document document = new Document();
       PdfWriter.getInstance(document, new FileOutputStream(filePDF));
       document.open();
       this.armoCabezalDeclaracion(document,f);
       Paragraph rubro1 = new Paragraph();
                 rubro1.setAlignment(Element.ALIGN_LEFT);
                 rubro1.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                 rubro1.add("RUBRO 1 - IDENTIFICACIÓN");
                 rubro1.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                 rubro1.add("\n");
                 rubro1.add("NOMBRES: "+f.getApellido1()+" "+f.getApellido2()+" "+f.getNombre1()+" "+f.getNombre2());
                  rubro1.add("\n");
                 String vig = d.getVigencia().toString();
                 rubro1.add("TIPO: "+d.getTipoDoc()+"  NRO.DOC: "+d.getNroDoc().trim()+"  PAIS: "+ d.getPais().trim()+"  VIG. DESDE: 0" + vig.substring(5, 6)+"-"+vig.substring(0,4));
                 rubro1.add("\n \n");
       Paragraph rubro2 = new Paragraph();
                 rubro2.setAlignment(Element.ALIGN_LEFT);
                 rubro2.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                 rubro2.add("RUBRO 2 - ATENCIÓN MEDICA DE PERSONAS A CARGO");
                 rubro2.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                 rubro2.add("\n");   
                 for(PersonasACargo p: lista){
                     String str="";
                    if(p.getDiscapacidad()==0){
                        str="NO";
                    }
                    else{
                        str="SI";
                    }
                 rubro2.add("TIPO: "+p.getTipoDoc().trim()+"  NRO. DOC: "+p.getNroDoc().trim()+"  PAIS: "+ p.getPais().trim()+"  FECHA NAC.: "+this.formateo(p.getFecha_nac()));
                 rubro2.add("\n");   
                 rubro2.add("NAC.: "+p.getNacionalidad().trim()+"  SEXO: "+p.getSexo().toString()+"  REL.: "+p.getRelacion().toString()+"  S. SALUD: "+ p.getSistSalud().trim()+"  % ATRIB.: "+p.getPjedist()+"  DISC.: "+str);
                 String ape2="";
                 String nom2="";
                 if(p.getApellidoDos()!=null){
                     ape2=p.getApellidoDos().trim();
                 }
                 if(p.getNombreDos()!=null){
                     nom2=p.getNombreDos().trim();
                 }
                 rubro2.add("\n");
                 rubro2.add("NOMBRES: "+p.getNombreUno().trim()+" "+nom2+" "+p.getApellidoUno().trim()+" "+ape2);
                 rubro2.add("\n \n");
                 }
       Paragraph rubro3 = new Paragraph();
                 rubro3.setAlignment(Element.ALIGN_LEFT);
                 rubro3.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                 rubro3.add("RUBRO 3 - DEDUCCIONES PROFESIONALES");
                 rubro3.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                 rubro3.add("\n");
                 String adic="";
                 if(d.getAdicionalcjpu()==0){
                     adic="NO";
                 }
                 else{
                     adic="SI";
                 }
                 rubro3.add("CAT.: "+ d.getCatcjpu()+"  REINT. APORTES: "+d.getReintcjpu()+"  FONDO SOL.: "+d.getFondosolcjpu().trim()+" BPC"+"  ADIC. F. SOL.: "+adic);
                 rubro3.add("\n \n");
                 
       Paragraph rubro4 = new Paragraph();
                 rubro4.setAlignment(Element.ALIGN_LEFT);
                 rubro4.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                 rubro4.add("RUBRO 4 - CONTRIBUYENTES CON MAS DE UN INGRESO");
                 rubro4.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                 rubro4.add("\n");
                 String min="";
                 if(d.getMinimoimp()==0){
                     min="NO";
                 }
                 else{
                     min="SI";
                 }
                 rubro4.add("EXONERAR MINIMO NO IMPONIBLE: "+min);
                 rubro4.add("\n");
                 String fam="";
                 if(d.getFamiliar()==0){
                     fam="NO";
                 }
                 else{
                     fam="SI";
                 }
                 rubro4.add("NÚCLEO FAMILIAR: "+fam);
                 rubro4.add("\n \n");
                 
       Paragraph rubro5 = new Paragraph();
                 rubro5.setAlignment(Element.ALIGN_LEFT);
                 rubro5.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                 rubro5.add("RUBRO 5 - DATOS DEL CÓNYUGE");
                 rubro5.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                 rubro5.add("\n");
                 if(d.getNroDocConyu()!=null){
                     if(!d.getNroDocConyu().equals("")){
                           String ape2Con="";
                           String nom2Con="";
                           if(d.getApellidoDosConyu()!=null){
                               ape2Con=d.getApellidoDosConyu().trim();
                           }
                           if(d.getNombreDosConyu()!=null){
                               nom2Con=d.getNombreDosConyu().trim();
                           }
                         rubro5.add("TIPO: "+d.getTipoDocConyu().trim()+"  NRO. DOC.: "+d.getNroDoc().trim()+"  PAIS: "+d.getPaisConyu().trim());
                         rubro5.add("\n");
                         rubro5.add("NOMBRES: "+d.getNombreUnoConyu().trim()+" "+nom2Con+" "+d.getApellidoUnoConyu().trim()+" "+ape2Con);
                         rubro5.add("\n");
                         rubro5.add("FECHA NAC.: "+this.formateo(d.getFechaNacConyu())+"  NAC.: "+ d.getNacionalConyu().trim()+"  SEXO: "+d.getSexoConyu());
                         rubro5.add("\n \n");        
                     }
                 }
               Paragraph rubro6 = new Paragraph();
                         rubro6.setAlignment(Element.ALIGN_LEFT);
                         rubro6.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                         rubro6.add("RUBRO 7 - CONSTANCIA DE RECEPCIÓN");
                         rubro6.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                         rubro6.add("\n");
                         rubro6.add("FECHA DE RECEPCION: "+this.formateo(d.getFechaRecepcion()));
                         rubro6.add("\n \n");
                        
                LineSeparator linea = new LineSeparator();
                Paragraph chunk= new Paragraph("Asociación Cooperativa Electoral, 8 de Octubre 2897, Montevideo \n Teléfono 2487 2711");       
                chunk.setAlignment(Element.ALIGN_CENTER);
       document.add(rubro1);
       if(lista!=null){
           if(lista.size()>0){
                 document.add(rubro2);
           }
       }
       document.add(rubro3);
       document.add(rubro4);
       if(d.getNroDocConyu()!=null){
           if(!d.getNroDocConyu().equals("")){
                document.add(rubro5);
           }
       }
       document.add(rubro6);
       document.add(linea);
       document.add(chunk);
       document.close(); 
    }
    
    
     private void armoCabezalDeclaracion(Document document,Funcionario f) throws BadElementException, DocumentException{
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
       dependencia.setFont(FontFactory.getFont("Arial", 14,Font.BOLD));
       dependencia.add("ASOCIACIÓN COOPERATIVA ELECTORAL");
       dependencia.add("\n \n");
       dependencia.add("DECLARACION INFORMATIVA ** 3100 ** AL: "+ this.formateo(hoy));
       dependencia.add("\n \n");
       dependencia.add("CODIGO DE FUNCIONARIO: "+ f.getCodFunc());
       dependencia.add("\n \n");
       document.add(dependencia);
      
       
    }

     private String formateoFecha(String fecha){
      String str="";
      SimpleDateFormat formateador = new SimpleDateFormat("dd/MMMM/yyyy");
      str=formateador.format(fecha);
      return str;
     }
     
     
    public void crearResumenLiquidacion(String fecha, ArrayList<Liquidacion> liquidaciones, FileOutputStream filePDF, ResumenLiquidacion resumen) throws FileNotFoundException, DocumentException {
       Document document = new Document(PageSize.A4, 10, 10, 10, 10);
       PdfWriter.getInstance(document, filePDF);
       document.open();
       LineSeparator linea = new LineSeparator();
       Paragraph titulo = new Paragraph();
                 titulo.setAlignment(Element.ALIGN_CENTER);
                 titulo.setFont(FontFactory.getFont("Courier", 16,Font.BOLD));
                 titulo.add("RESUMEN DE LIQUIDACION CORRESPONDIENTE AL: "+fecha);
                 titulo.add("\n \n");
                 
                 
       Paragraph cabezal = new Paragraph();
                 cabezal.setAlignment(Element.ALIGN_LEFT);
                 cabezal.setFont(FontFactory.getFont("Courier", 13));
                 cabezal.add("CODIGO   DESCRIPCION               TOTAL        APLICADO      NO RETENIDO");
                 cabezal.add("\n \n");
                 
       document.add(titulo);
       document.add(linea);
       document.add(cabezal);
       Paragraph lineas;
       int li=0;
       for(Liquidacion l: liquidaciones){
           if(li>=35){
              document.newPage();
              document.add(titulo);
              document.add(linea);
              document.add(cabezal);
              li=0;
              
           }
           if(l.getCodigo().getCod()>300){
           lineas = new Paragraph();
           lineas.setAlignment(Element.ALIGN_LEFT);
           lineas.setFont(FontFactory.getFont("Courier", 12));
           Integer cod=l.getCodigo().getCod();
           String nom=l.getCodigo().getDescripcion().trim();
           String total=this.decimales(l.getImporte());
           String noRet = this.decimales(l.getImporteNoRet());
           String apli = this.decimales(l.getImporte()-l.getImporteNoRet());
           //String linea=String.format("%1$-11s", "    "+a.getComprobanteInterno())+String.format("%1$-65s", a.getDescripcion().trim())+String.format("%1$-8s", this.decimales(a.getStock()))+String.format("%1$-14s", this.decimales(a.getCosto()))+String.format("%1$-1s", this.decimales(a.getStock()*a.getCosto()));
           String lin=String.format("%1$-10s","  "+cod)+String.format("%1$-24s", nom)+String.format("%12s", total)+String.format("%15s", apli)+String.format("%15s", noRet);
           lineas.add(lin);
           lineas.add("\n");
           document.add(lineas);
           li++;
           }else{
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_LEFT);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                Integer cod=l.getCodigo().getCod();
                String nom=l.getCodigo().getDescripcion().trim();
                String total=this.decimales(l.getImporte());
                
                //String linea=String.format("%1$-11s", "    "+a.getComprobanteInterno())+String.format("%1$-65s", a.getDescripcion().trim())+String.format("%1$-8s", this.decimales(a.getStock()))+String.format("%1$-14s", this.decimales(a.getCosto()))+String.format("%1$-1s", this.decimales(a.getStock()*a.getCosto()));
                String lin=String.format("%1$-10s","  "+cod)+String.format("%1$-24s", nom)+String.format("%12s", total)+String.format("%15s", "")+String.format("%15s", "");
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
                li++;
           }
       }          
       document.newPage();
       document.add(titulo);
       document.add(linea);
       
       cabezal = new Paragraph();
                 cabezal.setAlignment(Element.ALIGN_LEFT);
                 cabezal.setFont(FontFactory.getFont("Courier", 13));
                 cabezal.add("TOTALES GENERALES");
                 cabezal.add("\n \n");
                 
       document.add(cabezal);
       document.add(linea);
       
       //GANADO
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                String nom="GANADO";
                String total=this.decimales(resumen.getTotalHaberes());
                String lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
       //GANADO BPS
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="GANADO BPS";
                total=this.decimales(resumen.getTotalHaberesBps());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
       //GANADO IRPF
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="GANADO IRPF";
                total=this.decimales(resumen.getTotalHaberesIrpf());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
       //DIPAICO
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="DIPAICO";
                total=this.decimales(resumen.getDipaico());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
       //CASENPACE
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="CASENPACE";
                total=this.decimales(resumen.getCasenpace());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
       //FONASA
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="FONASA";
                total=this.decimales(resumen.getFonasa());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
       //FRL
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="FRL";
                total=this.decimales(resumen.getFrl());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
      //IRPF
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="IRPF";
                total=this.decimales(resumen.getIrpf());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
      //REDONDEOS
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="REDONDEOS";
                total=this.decimales(resumen.getRedondeos());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
      //DESCUENTOS
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="DESCUENTOS";
                total=this.decimales(resumen.getTotalDescuentos());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);
     //LIQUIDO
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="LIQUIDO";
                total=this.decimales(resumen.getLiquidoFinal());
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n");
                document.add(lineas);     
     //FUNCIONARIOS PROCESADOS
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                nom="FUNCIONARIOS PROCESADOS";
                total=resumen.getTotalFunc().toString();
                lin=String.format("%1$-40s", nom)+String.format("%12s", total);
                lineas.add(lin);
                lineas.add("\n \n");
                document.add(lineas);  
        
        document.add(linea);
        document.add(new Phrase(Chunk.NEWLINE));
        
                lineas = new Paragraph();
                lineas.setAlignment(Element.ALIGN_CENTER);
                lineas.setFont(FontFactory.getFont("Courier", 12));
                Date hoy = new Date();
                nom="Este listado fue emitido el día: "+ this.formateo(hoy) + " a la hora: "+this.formateoHora(hoy);
                lineas.add(nom);
                document.add(lineas);
        document.close(); 
    }

    public void crearDetalleIrpf(String fecha, FileOutputStream filePDF, ArrayList<Liquidacion> detallado) throws DocumentException {
       Document document = new Document(PageSize.A4, 15, 15, 15, 15);
       PdfWriter.getInstance(document, filePDF);
       document.open();
       LineSeparator linea = new LineSeparator();
       Paragraph titulo = new Paragraph();
                 titulo.setAlignment(Element.ALIGN_CENTER);
                 titulo.setFont(FontFactory.getFont("Courier", 16,Font.BOLD));
                 titulo.add("DETALLE DE IRPF APLICADO EN LA LIQUIDACION DE: "+fecha);
                 titulo.add("\n \n");
       Integer codFun =0;
       Integer tipo = 0;
       
       for(Liquidacion liq:detallado){
           if(!codFun.equals(liq.getF().getCodFunc())){
               document.newPage();
               document.add(titulo);
               Paragraph func = new Paragraph();
                  func.setAlignment(Element.ALIGN_CENTER);
                  func.setFont(FontFactory.getFont("Courier", 14,Font.BOLD));
                  func.add("FUNCIONARIO:  "+liq.getF().getCodFunc()+" *** "+liq.getF().getNomCompletoApe());
                  func.add("\n \n");
                  document.add(func);
                  
                  codFun=liq.getF().getCodFunc();
                  
                  boolean tit1 =false;
                  boolean tit2 =false;
                  boolean tit3 =false;
                  boolean tit4 =false;
                  boolean tit5 =false;
                  
                  for(irpfDetallado det: liq.getIrpfDetallado()){
                      if(det.getTipo()==1){
                          if(!tit1){
                            Paragraph t1 = new Paragraph();
                            t1.setAlignment(Element.ALIGN_LEFT);
                            t1.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                            t1.add("RETENCIONES IRPF");
                            t1.add("\n");
                            document.add(t1);
                            tit1=true;
                          }
                          Paragraph d1 = new Paragraph();
                            d1.setAlignment(Element.ALIGN_LEFT);
                            d1.setFont(FontFactory.getFont("Courier", 12));
                            String lin=String.format("%1$-10s","  "+det.getOrdinal())+String.format("%1$-30s", det.getLeyenda())+String.format("%25s", this.decimales(det.getImporte()));
                            d1.add(lin);
                            d1.add("\n");
                            document.add(d1);
                      }else if(det.getTipo()==2){
                          if(!tit2){
                            document.add(new Phrase(Chunk.NEWLINE));  
                            Paragraph t2 = new Paragraph();
                            t2.setAlignment(Element.ALIGN_LEFT);
                            t2.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                            t2.add("DEDUCCIONES IRPF");
                            t2.add("\n");
                            document.add(t2);
                            tit2=true;
                          }
                          Paragraph d2 = new Paragraph();
                            d2.setAlignment(Element.ALIGN_LEFT);
                            d2.setFont(FontFactory.getFont("Courier", 12));
                            String lin=String.format("%1$-10s","  "+det.getOrdinal())+String.format("%1$-30s", det.getLeyenda())+String.format("%25s", this.decimales(det.getImporte()));
                            d2.add(lin);
                            d2.add("\n");
                            document.add(d2);
                      }else if(det.getTipo()==3){
                            if(!tit3){
                            document.add(new Phrase(Chunk.NEWLINE));  
                            Paragraph t3 = new Paragraph();
                            t3.setAlignment(Element.ALIGN_LEFT);
                            t3.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                            t3.add("IRPF MENSUAL");
                            t3.add("\n");
                            document.add(t3);
                            tit3=true;
                          }
                          Paragraph d3 = new Paragraph();
                            d3.setAlignment(Element.ALIGN_LEFT);
                            d3.setFont(FontFactory.getFont("Courier", 12));
                            String lin=String.format("%1$-10s","  "+det.getOrdinal())+String.format("%1$-30s", det.getLeyenda())+String.format("%25s", this.decimales(det.getImporte()));
                            d3.add(lin);
                            d3.add("\n");
                            document.add(d3);
                      }else if(det.getTipo()==4){
                          if(!tit4){
                            document.add(new Phrase(Chunk.NEWLINE));  
                            Paragraph t4 = new Paragraph();
                            t4.setAlignment(Element.ALIGN_LEFT);
                            t4.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                            t4.add("ANTICIPOS IRPF");
                            t4.add("\n");
                            document.add(t4);
                            tit4=true;
                          }
                          Paragraph d4 = new Paragraph();
                            d4.setAlignment(Element.ALIGN_LEFT);
                            d4.setFont(FontFactory.getFont("Courier", 12));
                            String lin=String.format("%1$-10s","  "+det.getOrdinal())+String.format("%1$-30s", det.getLeyenda())+String.format("%25s", this.decimales(det.getImporte()));
                            d4.add(lin);
                            d4.add("\n");
                            document.add(d4);
                      }else if(det.getTipo()==5){
                            if(!tit5){
                            document.add(new Phrase(Chunk.NEWLINE));  
                            Paragraph t5 = new Paragraph();
                            t5.setAlignment(Element.ALIGN_LEFT);
                            t5.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                            t5.add("TOTALES IRPF");
                            t5.add("\n");
                            document.add(t5);
                            tit5=true;
                          }
                            if(det.getOrdinal()==0){
                            Paragraph d5 = new Paragraph();
                            d5.setAlignment(Element.ALIGN_LEFT);
                            d5.setFont(FontFactory.getFont("Courier", 12));
                            String lin=String.format("%1$-10s","  "+det.getOrdinal())+String.format("%1$-30s", det.getLeyenda())+String.format("%25s", this.decimales(det.getImporte()));
                            d5.add(lin);
                            d5.add("\n");
                            document.add(d5);
                            }else{
                            Paragraph d5 = new Paragraph();
                            d5.setAlignment(Element.ALIGN_LEFT);
                            d5.setFont(FontFactory.getFont("Courier", 12));
                            String lin=String.format("%1$-10s","  "+det.getOrdinal())+String.format("%1$-30s", det.getLeyenda())+String.format("%25s", this.decimalesLargo(det.getImporte()));
                            d5.add(lin);
                            d5.add("\n");
                            document.add(d5);
                            }
                      }
                      
                  }
                  
           }
          
        
           
       }
       
       document.close(); 
    }

    public void crearDetalleLiquidacion(String fecha, FileOutputStream filePDF, ArrayList<ResumenLiquidacion> resumenes) throws DocumentException {
       Document document = new Document(PageSize.A4, 15, 10, 15, 10);
       PdfWriter.getInstance(document, filePDF);
       document.open();
       LineSeparator linea = new LineSeparator();
       Paragraph titulo = new Paragraph();
                 titulo.setAlignment(Element.ALIGN_CENTER);
                 titulo.setFont(FontFactory.getFont("Courier", 16,Font.BOLD));
                 titulo.add("DETALLE DE LIQUIDACION CORRESPONDIENTE AL: "+fecha);
                 titulo.add("\n \n");
                 
        Integer codFun=0;
        
        for(ResumenLiquidacion res:resumenes){
            Funcionario f=res.getFuncionario();
        
            int lineas=0;
            Liquidacion liRedondeo=null;
            if(!f.getCodFunc().equals(codFun)){
               codFun=res.getFuncionario().getCodFunc();
               
               document.newPage();
               document.add(titulo);
               document.add(linea);
               Paragraph func = new Paragraph();
                  func.setAlignment(Element.ALIGN_CENTER);
                  func.setFont(FontFactory.getFont("Courier", 12));
                  func.add(codFun+"  "+f.getNomCompletoApe()+"  *** "+f.getCargo().getNombre()+" ***");
                  func.add("\n \n");
                  document.add(func);
                  codFun=f.getCodFunc();
               document.add(linea);
               
               Paragraph cabe = new Paragraph();
                  cabe.setAlignment(Element.ALIGN_LEFT);
                  cabe.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                  String lin="Cod     Descripción               Cant.       Haber          Debe         Pend";
                  cabe.add(lin);
                  cabe.add("\n \n");
                  document.add(cabe);
                  
                   for(Liquidacion li: res.getLiquidaciones()){
                       if(lineas>=40){
                            Paragraph l = new Paragraph();
                            l.setAlignment(Element.ALIGN_RIGHT);
                            l.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                            lin="Sigue en la próxima página...";
                            l.add(lin);
                            document.add(l);
                           document.newPage();
                           lineas=0;
                       }
                       Paragraph codi = new Paragraph();
                       codi.setAlignment(Element.ALIGN_LEFT);
                       codi.setFont(FontFactory.getFont("Courier", 12));
                       int cod=li.getCodigo().getCod();
                       String des=li.getCodigo().getDescripcion();
                       double cant = li.getCantidad();
                       double haber = li.getImporte();
                       double noRet = li.getImporteNoRet();
                       if(cod!=600){
                           if(noRet==0){
                                if(li.getCodigo().isHaber()){
                                    String l=String.format("%3s",cod)+String.format("%1$-28s", "     "+des)+String.format("%7s", this.decimales(cant))+String.format("%15s", this.decimales(haber));
                                    codi.add(l);
                                    codi.add("\n");
                                    document.add(codi);
                                    lineas++;
                                   
                                }else if(li.getCodigo().isDebe()){
                                    String l=String.format("%3s",cod)+String.format("%1$-28s", "     "+des)+String.format("%7s", this.decimales(cant))+String.format("%28s", this.decimales(haber));
                                    codi.add(l);
                                    codi.add("\n");
                                    document.add(codi);
                                    lineas++;
                                   
                                }
                           }else{
                               double dif=haber-noRet;
                               String l=String.format("%3s",cod)+String.format("%1$-28s", "     "+des)+String.format("%7s", this.decimales(cant))+String.format("%28s", this.decimales(dif))+String.format("%12s", this.decimales(noRet));
                               codi.add(l);
                               codi.add("\n");
                               document.add(codi);
                               lineas++;
                               
                           }
                       }else{
                           liRedondeo=li;
                       }
                       
                   }
                   if(lineas>=40){
                       Paragraph l = new Paragraph();
                        l.setAlignment(Element.ALIGN_RIGHT);
                        l.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                        lin="Sigue en la próxima página...";
                        l.add(lin);
                        document.add(l);
                       document.newPage();
                       lineas=0;
                   }
                  Paragraph subTo = new Paragraph();
                  subTo.add("\n");
                  document.add(subTo);
                  subTo.setAlignment(Element.ALIGN_LEFT);
                  subTo.setFont(FontFactory.getFont("Courier", 12));
                  String l=String.format("%1$-28s", "        "+"SUBTOTALES")+String.format("%25s", this.decimales(res.getTotalHaberes()))+String.format("%13s", this.decimales(res.getTotalDescuentos()))+String.format("%12s", this.decimales(res.getTotalNoRet()));
                  subTo.add(l);
                  subTo.add("\n \n");
                  document.add(subTo);
                  lineas++;
                  if(lineas>=40){
                      Paragraph R = new Paragraph();
                        R.setAlignment(Element.ALIGN_RIGHT);
                        R.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                        lin="Sigue en la próxima página...";
                        R.add(lin);
                        document.add(R);
                     document.newPage();
                     lineas=0;
                  }
                  if(liRedondeo!=null){
                  Paragraph redo = new Paragraph();
                  redo.setAlignment(Element.ALIGN_LEFT);
                  redo.setFont(FontFactory.getFont("Courier", 12));
                  l=String.format("%1$-28s", "        "+"REDONDEO")+String.format("%25s", this.decimales(liRedondeo.getImporte()));
                  redo.add(l);
                  redo.add("\n");
                  document.add(redo);
                  lineas++;
                  }else{
                  Paragraph redo = new Paragraph();
                  redo.setAlignment(Element.ALIGN_LEFT);
                  redo.add("\n");
                  document.add(redo);
                  lineas++;
                  }
                  if(lineas>=40){
                     Paragraph R = new Paragraph();
                    R.setAlignment(Element.ALIGN_RIGHT);
                    R.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                    lin="Sigue en la próxima página...";
                    R.add(lin);
                    document.add(R);
                     document.newPage();
                     lineas=0;
                     
                  }
                  Paragraph totLiq = new Paragraph();
                  totLiq.setAlignment(Element.ALIGN_LEFT);
                  totLiq.setFont(FontFactory.getFont("Courier", 12));
                  l=String.format("%1$-28s", "        "+"TOTAL LIQUIDO")+String.format("%25s", this.decimales(res.getLiquidoFinal()));
                  totLiq.add(l);
                  totLiq.add("\n \n");
                  document.add(totLiq);
                  lineas++;
                  if(lineas>=40){
                      Paragraph R = new Paragraph();
                      R.setAlignment(Element.ALIGN_RIGHT);
                      R.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                      lin="Sigue en la próxima página...";
                      R.add(lin);
                      document.add(R);
                      document.newPage();
                      lineas=0;
                  }
                  
                  Paragraph tot = new Paragraph();
                  tot.setAlignment(Element.ALIGN_LEFT);
                  tot.setFont(FontFactory.getFont("Courier", 12));
                  l=String.format("%1$-28s", "        "+"TOTAL HABERES BPS: ")+String.format("%25s", this.decimales(res.getTotalHaberesBps()));
                  tot.add(l);
                  tot.add("\n");
                  document.add(tot);
                  lineas++;
                  
                   if(lineas>=40){
                       Paragraph R = new Paragraph();
                        R.setAlignment(Element.ALIGN_RIGHT);
                        R.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                        lin="Sigue en la próxima página...";
                        R.add(lin);
                        document.add(R);
                       document.newPage();
                       lineas=0;
                   }
                  tot = new Paragraph();
                  tot.setAlignment(Element.ALIGN_LEFT);
                  tot.setFont(FontFactory.getFont("Courier", 12));
                  l=String.format("%1$-28s", "        "+"TOTAL HABERES IRPF: ")+String.format("%25s", this.decimales(res.getTotalHaberesIrpf()));
                  tot.add(l);
                  tot.add("\n");
                  document.add(tot);
                  lineas++;
                  
                  if(lineas>=40){
                      Paragraph R = new Paragraph();
                        R.setAlignment(Element.ALIGN_RIGHT);
                        R.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                        lin="Sigue en la próxima página...";
                        R.add(lin);
                        document.add(R);
                       document.newPage();
                       lineas=0;
                  }
                  
                  tot = new Paragraph();
                  tot.setAlignment(Element.ALIGN_LEFT);
                  tot.setFont(FontFactory.getFont("Courier", 12));
                  l=String.format("%1$-28s", "        "+"LIQUIDO LEGAL: ")+String.format("%25s", this.decimales(res.getLiquidoLegal()));
                  tot.add(l);
                  tot.add("\n");
                  document.add(tot);
                  lineas++;
                  
                  if(lineas>=40){
                      Paragraph R = new Paragraph();
                        R.setAlignment(Element.ALIGN_RIGHT);
                        R.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                        lin="Sigue en la próxima página...";
                        R.add(lin);
                        document.add(R);
                     document.newPage();
                     lineas=0;
                  }
                  
                  tot = new Paragraph();
                  tot.setAlignment(Element.ALIGN_LEFT);
                  tot.setFont(FontFactory.getFont("Courier", 12));
                  l=String.format("%1$-28s", "        "+"LIQUIDO MINIMO: ")+String.format("%25s", this.decimales(res.getLiquidoMinimo()));
                  tot.add(l);
                  tot.add("\n");
                  document.add(tot);
                  lineas++;
            }
        }
        
        document.close();
    }

    public void crearDescuentosPdf(String fecha, FileOutputStream filePDF, ArrayList<Liquidacion> descuentos) throws DocumentException {
       Document document = new Document(PageSize.A4, 15, 10, 15, 10);
       PdfWriter.getInstance(document, filePDF); 
       document.open();
       LineSeparator linea = new LineSeparator();
       int li=0;
       int cod=0;
       int pag=1;
       double totalDescuentos=0;
       double totalNoRetenido=0;
       
       for(Liquidacion l:descuentos){
           if(li>=40){
                Paragraph p = new Paragraph();
                p.setAlignment(Element.ALIGN_RIGHT);
                p.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                String lin="SIGUE EN LA HOJA SIGUIENTE";
                p.add("\n");
                p.add(lin);
                document.add(p);
                document.newPage();
                li=0;
                pag++;
                Paragraph titulo = new Paragraph();
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                titulo.add("DESCUENTOS EFECTUADOS EN LA LIQUIDACION DE: "+fecha+"          HOJA "+pag);
                document.add(titulo);
                titulo = new Paragraph();
                 titulo.setAlignment(Element.ALIGN_LEFT);
                 titulo.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                 titulo.add("         "+l.getCodigo().toString());
                 titulo.add("\n");
                 titulo.add(linea);
                 titulo.add("\n");
                 document.add(titulo);
                 titulo = new Paragraph();
                 titulo.setAlignment(Element.ALIGN_LEFT);
                 titulo.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                 titulo.add("NRO.  NOMBRE                                 A DESCONTAR                   NO RET.");
                 titulo.add("\n");
                 document.add(titulo);
            }
           
           if(cod!=l.getCodigo().getCod()){
               if(totalDescuentos>0){
                Paragraph p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(FontFactory.getFont("Courier", 11));
                String lin=String.format("%1$-30s","                TOTAL DESCUENTOS")+String.format("%24s", this.decimales(totalDescuentos))+String.format("%26s", this.decimales(totalNoRetenido));
                p.add("\n \n");
                p.add(lin);
                p.add("\n \n");
                document.add(p);
                p = new Paragraph();
                p.setAlignment(Element.ALIGN_LEFT);
                p.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                lin=String.format("%1$-30s","                TOTAL RETENIDO")+String.format("%35s", this.decimales(totalDescuentos-totalNoRetenido));
               
                p.add(lin);
                document.add(p);
               }
                 totalDescuentos=0;
                 totalNoRetenido=0;
                 li=0;
                 pag=1;
                 document.newPage();
                 Paragraph titulo = new Paragraph();
                 titulo.setAlignment(Element.ALIGN_CENTER);
                 titulo.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                 titulo.add("DESCUENTOS EFECTUADOS EN LA LIQUIDACION DE: "+fecha+"          HOJA "+pag);
                 document.add(titulo);
                 titulo = new Paragraph();
                 titulo.setAlignment(Element.ALIGN_LEFT);
                 titulo.setFont(FontFactory.getFont("Courier", 12,Font.BOLD));
                 titulo.add("         "+l.getCodigo().toString());
                 titulo.add("\n");
                 titulo.add(linea);
                 titulo.add("\n");
                 document.add(titulo);
                 titulo = new Paragraph();
                 titulo.setAlignment(Element.ALIGN_LEFT);
                 titulo.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
                 titulo.add("NRO.  NOMBRE                                 A DESCONTAR                   NO RET.");
                 titulo.add("\n");
                 document.add(titulo);
                 cod=l.getCodigo().getCod();
           }
            
           if(l.getImporteNoRet()>0){
                Paragraph line = new Paragraph();
                line.setAlignment(Element.ALIGN_LEFT);
                line.setFont(FontFactory.getFont("Courier", 11));
                String s=String.format("%4s",l.getF().getCodFunc())+String.format("%1$-41s","  "+l.getF().getNomCompletoApe())+String.format("%11s", this.decimales(l.getImporte()))+String.format("%26s", this.decimales(l.getImporteNoRet()));
                line.add(s);
                line.add("\n");
                document.add(line);
                li++;
                totalDescuentos+=l.getImporte();
                totalNoRetenido+=l.getImporteNoRet();
           }else{
                Paragraph line = new Paragraph();
                line.setAlignment(Element.ALIGN_LEFT);
                line.setFont(FontFactory.getFont("Courier", 11));
                String s=String.format("%4s",l.getF().getCodFunc())+String.format("%1$-41s","  "+l.getF().getNomCompletoApe())+String.format("%11s", this.decimales(l.getImporte()));
                line.add(s);
                line.add("\n");
                document.add(line);
                li++;
                totalDescuentos+=l.getImporte();
               
           }
       }
        Paragraph p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 11));
        String lin=String.format("%1$-30s","                TOTAL DESCUENTOS")+String.format("%24s", this.decimales(totalDescuentos))+String.format("%26s", this.decimales(totalNoRetenido));
        p.add("\n \n");
        p.add(lin);
        p.add("\n \n");
        document.add(p);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
        lin=String.format("%1$-30s","                TOTAL RETENIDO")+String.format("%35s", this.decimales(totalDescuentos-totalNoRetenido));

        p.add(lin);
        document.add(p);
        document.close();
    }

    public void crearRecibosDeSueldos(String fecha, FileOutputStream filePDF, ArrayList<ResumenLiquidacion> resumenes) throws BDExcepcion, DocumentException, ClassNotFoundException {
       Document document = new Document(PageSize.A4, 15, 10, 15, 10);
       PdfWriter.getInstance(document, filePDF); 
       document.open();
       
        PersistenciaLiquidacion perLiq = new PersistenciaLiquidacion();
        String numBps=perLiq.cargoParametroStringConConexion("BPS");
        String numBse=perLiq.cargoParametroStringConConexion("BSE");
        
        int lineas=0;
        int codFunc=0;
        double totalHaberes=0;
        double totalDescuentos=0;
        for(ResumenLiquidacion r: resumenes){
            Funcionario f =r.getFuncionario(); 
            if(codFunc!=f.getCodFunc()){
                totalHaberes=0;
                totalDescuentos=0;
                document.newPage();
                this.armoCabezalRecibo(f,numBps,numBse,fecha,document);
                lineas=0;
                
                for(Liquidacion li: r.getLiquidaciones()){
                       if(lineas>=30){
                           document.newPage();
                           this.armoCabezalRecibo(f,numBps,numBse,fecha,document);
                           lineas=0;
                       }
                       Paragraph codi = new Paragraph();
                       codi.setAlignment(Element.ALIGN_LEFT);
                       codi.setFont(FontFactory.getFont("Courier", 10));
                       int cod=li.getCodigo().getCod();
                       String des=li.getCodigo().getDescripcion();
                       double cant = li.getCantidad();
                       double valorU = li.getValorUnitario();
                       double haber = li.getImporte();
                       double noRet = li.getImporteNoRet();
                       if(cod==10){
                           int i =8;
                       }
                       if(noRet==0){
                              if(cod!=207&&cod!=201&&cod!=203&&cod!=204&&cod!=205&&cod!=600){
                                    if(li.getCodigo().isHaber()){
                                        String l=String.format("%5s",cant)+String.format("%1$-30s", "     "+des)+String.format("%12s", this.decimales(valorU))+String.format("%15s", this.decimales(haber));
                                        codi.add(l);
                                        codi.add("\n");
                                        document.add(codi);
                                        lineas++;
                                        totalHaberes+=haber;

                                    }else if(li.getCodigo().isDebe()){
                                        if(cod>=1 && cod<=200){
                                            if(valorU>0){
                                                valorU=valorU*-1;
                                            }
                                            if(haber>0){
                                                haber=haber*-1;
                                            }
                                            String l=String.format("%5s",cant)+String.format("%1$-30s", "     "+des)+String.format("%12s", this.decimales(valorU))+String.format("%15s", this.decimales(haber));
                                            codi.add(l);
                                            codi.add("\n");
                                            document.add(codi);
                                            lineas++;
                                            totalHaberes+=haber;
                                        }else{
                                            String l=String.format("%5s",cant)+String.format("%1$-30s", "     "+des)+String.format("%12s", this.decimales(valorU))+String.format("%31s", this.decimales(haber));
                                            codi.add(l);
                                            codi.add("\n");
                                            document.add(codi);
                                            lineas++;
                                            totalDescuentos+=haber;
                                        }
                                    }
                              }else{
                                  if(li.getCodigo().isHaber()){
                                        String l=String.format("%5s",cant)+String.format("%1$-30s", "     "+des)+String.format("%12s", "")+String.format("%15s", this.decimales(haber));
                                        codi.add(l);
                                        codi.add("\n");
                                        document.add(codi);
                                        lineas++;
                                        if(cod!=600){
                                        totalHaberes+=haber;
                                        }

                                    }else if(li.getCodigo().isDebe()){
                                        String l=String.format("%5s",cant)+String.format("%1$-30s", "     "+des)+String.format("%12s", "")+String.format("%31s", this.decimales(haber));
                                        codi.add(l);
                                        codi.add("\n");
                                        document.add(codi);
                                        lineas++;
                                        if(cod!=600){
                                        totalDescuentos+=haber;
                                        }
                                    }
                              }
                           }else{
                               double dif=haber-noRet;
                               String l=String.format("%5s",cant)+String.format("%1$-30s", "     "+des)+String.format("%12s", this.decimales(valorU))+String.format("%31s", this.decimales(dif))+String.format("%17s", this.decimales(noRet));
                               codi.add(l);
                               codi.add("\n");
                               document.add(codi);
                               lineas++;
                               totalDescuentos+=dif;
                           }
                       
                       
            }
            
            
        }
        
        Paragraph p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
        String l=String.format("%5s","")+String.format("%1$-30s", "    TOTAL HABERES")+String.format("%22s", this.decimales(totalHaberes));
        p.add("\n");
        p.add(l);
        p.add("\n");
        document.add(p);
        lineas++;
        
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
        l=String.format("%5s","")+String.format("%1$-30s", "    TOTAL DESCUENTOS")+String.format("%36s", this.decimales(totalDescuentos));
        p.add(l);
        p.add("\n\n");
        document.add(p);
        lineas++;
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 11,Font.BOLD));
        double liq = totalHaberes-totalDescuentos;
        liq=Math.ceil(liq);
        l=String.format("%5s","")+String.format("%1$-30s", "    TOTAL LIQUIDO")+String.format("%22s", this.decimales(liq));
        p.add(l);
        p.add("\n\n\n\n");
        document.add(p);
        lineas+=3;
        
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 11));
        l="LA EMPRESA DECLARA HABER EFECTUADO LOS APORTES DE SEGURIDAD SOCIAL CORRESPONDIENTES A\n" +
          "LOS HABERES LIQUIDADOS EL MES ANTERIOR\n" +
          "GRUPO: 10 SUBGRUPO: 24 MTSS: 15421\n" +
          "IMPORTE ACREDITADO PREVIAMENTE EN CUENTA "+f.getCuenta().intValue()+" DEL BANCO "+ f.getBanco().toString();
        p.add(l);
        document.add(p);
        
        }
        document.close();
    }

    private void armoCabezalRecibo(Funcionario f, String numBps, String numBse, String fecha, Document document) throws DocumentException {
        LineSeparator linea = new LineSeparator(1,100, BLACK,0,-3);
        Paragraph p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 14, Font.BOLD));
        String lin="ASOCIACIÓN COOPERATIVA ELECTORAL";
        p.add(lin);
        p.add("\n");
        document.add(p);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 10));
        lin="CASA CENTRAL: AV. 8 DE OCTUBRE 2897-MONTEVIDEO                              R.U.T. 210362180016";
        p.add(lin);
        p.add("\n");
        document.add(p);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 12, Font.BOLD));
        lin="LIQUIDACIÓN DE HABERES                                          DECRETO 337/992";
        p.add(lin);
        p.add("\n");
        document.add(p);
        document.add(linea);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 10));
        lin="NOMBRE                                   CONCEPTO DE LIQ.                           FUNCIONARIO";
        p.add(lin);
        p.add("\n");
        document.add(p);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 10));
        lin=String.format("%1$-45s",f.getNomCompletoApe())+ String.format("%1$-20s", this.armaFecha(fecha))+String.format("%27s", f.getCodFunc()); 
        p.add(lin);
        p.add("\n");
        document.add(p);
        document.add(linea);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 10));
        lin="AFILIACION B.P.S.               CARPETA B.S.E.               CARGO                 CAT. LABORAL";
        p.add(lin);
        p.add("\n");
        document.add(p);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 10));
        lin=String.format("%1$-34s",numBps)+ String.format("%1$-25s",numBse)+ String.format("%1$-26s",f.getCargo().toString())+"MENSUAL";
        p.add(lin);
        p.add("\n");
        document.add(p);
        document.add(linea);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 10));
        lin="FECHA DE INGRESO                 CED. DE IDENTIDAD                                LUGAR DE PAGO";
        p.add(lin);
        p.add("\n");
        document.add(p);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 10));
        String st = String.valueOf(f.getLugarTrabajo().getNumero());
        if(f.getLugarTrabajo().getNumero()<10){
            st="0"+st;
        }
        lin=String.format("%1$-36s",this.formateo(f.getFechaIngreso()))+ String.format("%1$-52s",f.getCedula())+ st;
        p.add(lin);
        p.add("\n");
        document.add(p);
        document.add(linea);
        p = new Paragraph();
        p.setAlignment(Element.ALIGN_LEFT);
        p.setFont(FontFactory.getFont("Courier", 10, Font.BOLD));
        lin="UNID.     CONCEPTO                   VALOR UNIT.      HABERES       DESCUENTOS      NO RETENIDO";
        p.add(lin);
        p.add("\n");
        document.add(p);
    }

    private String armaFecha(String fecha) {
        String mes = fecha.substring(3, 5);
        String año = fecha.substring(6, 10);
        String retorno="";
        switch (mes) {
            case "01":
                retorno="ENE/"+año;
                break;
            case "02":
                retorno="FEB/"+año; 
                break;
            case "03":
                retorno="MAR/"+año;
                break;
            case "04":
                retorno="ABR/"+año;
                break;
            case "05":
                retorno="MAY/"+año; 
                break;
            case "6":
                retorno="JUN/"+año;
                break;
            case "07":
                retorno="JUL/"+año;
                break;
            case "08":
                retorno="AGO/"+año; 
                break;
            case "09":
                retorno="SET/"+año;
                break;
            case "10":
                retorno="OCT/"+año;
                break;
            case "11":
                retorno="NOV/"+año; 
                break;
            case "12":
                retorno="DIC/"+año;
                break;
            default:
                break;
        }
       return retorno; 
    }
    
   

    
    
     
}
