package Logica;
import Dominio.Funcionario;
import Dominio.LineaArchivoBanco;
import Dominio.TipoVale;
import Dominio.Vale;
import Persistencia.PersistenciaFuncionario;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class ArmoTxtxBanco {
private PersistenciaFuncionario pers;
private BufferedWriter bfwriter;
private Sheet hoja;
private Sheet hojaSinCuenta;

private String[] headers;
private FileOutputStream fileOut = null;
private Workbook libro;
private Workbook libroSinCuenta;
private int i =5;
private int s =5;

private Double suma=0.0;
private TipoVale tipoVale;
private String nombre="";
private String titulo;
private String nombreSinCuenta="";
private String tituloSinCuenta;
    public ArmoTxtxBanco() {
        this.pers = new PersistenciaFuncionario();
    }

    
public String armoTxtValesBanco(Date fechaLiq,Date fechaInfo,ArrayList<Object> valesBase,Integer tipo,ArrayList<Object> sinCuenta){
    FileWriter fileWr = null;
    String str="";
    File file = null;
    if(tipo==1){
        nombre=this.cargoTipoLiq(valesBase);
        titulo="DETALLE DE DEPÓSITO DE LIQUIDACION DE "+nombre;
    }
    else{
        this.cargoTipoVale(valesBase);
        nombre="VALESACE_"+tipoVale.getNombre();
        titulo="DETALLE DE DEPÓSITO DE VALES DE "+tipoVale.getNombre()+" ACE";
    }
    
    try {
        file= new File("C:\\SUELDOSLIST\\"+nombre+"_"+this.formateoFechaInfo2(fechaInfo)+".txt");
        this.armoExcel(fechaInfo);
        fileWr = new FileWriter(file);
        bfwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Cp1252"));
        this.armoCabezal(fechaLiq,fechaInfo,valesBase,tipo); //del txt
        bfwriter.newLine();
        this.cargoLíneas(valesBase,tipo);//del txt y excel
        
        this.cargoTotalExcel();
        libro.write(fileOut);
        fileOut.close();
        if(sinCuenta.size()>0){
          this.armoExcelSinCuenta(fechaInfo, sinCuenta, tipo);
        }
        
    } catch (IOException ex) {
        str="Ha ocurrido un problema";
    } finally {
        try {
            bfwriter.close();
            fileWr.close();
             str="Se ha creado exitosamente el documento";
        } catch (IOException ex) {
            str="Ha ocurrido un problema";
        }
    }
    return str;
}

    private String formateoFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String t=sdf.format(fecha);
        return t;
    }

    private void armoCabezal(Date fechaLiq,Date fechaInfo,ArrayList<Object> vales,Integer tipo) throws IOException {
        String cabezal="C000201";//La C es de Santander, se setea la sucursal de ACE en santander y el tipo de moneda 01=Pesos
        cabezal+=this.cargoCuenta();//carga cuenta de ace
        cabezal+=this.cargoRut();//carga rut de ace
        cabezal+=this.formateoFechaInfo(fechaInfo); //carga fecha informado
        cabezal+=String.format("%04d",vales.size());//carga cantidad de lineas 
        if(tipo==1){
            cabezal+=this.sumaLiq(vales); //suma los montos de todos los vales
        }
        else{
            cabezal+=this.sumaVales(vales); //suma los montos de todos los vales
        }
        bfwriter.write(cabezal);
    }
    
    
    private String cargoCuenta(){
        String str = this.pers.cargoCuentaAce();
               
        str=String.format("%035d",Integer.valueOf(str));        
        
        return str;
    }
    
    private String cargoRut(){
        String str = this.pers.cargoRutAce();
        return str;
    }
    
    private String formateoFechaInfo(Date fechaInfo){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInfo);
        String ano=String.valueOf(calendar.get(Calendar.YEAR));
        ano=ano.substring(2, 4);
        String mes=String.valueOf(calendar.get(Calendar.MONTH)+1);
        mes=String.format("%02d",Integer.valueOf(mes));       
        String dia=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        dia=String.format("%02d",Integer.valueOf(dia));   
        return ano+mes+dia;
    }
    
    private String formateoFechaInfo2(Date fechaInfo){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInfo);
        String ano=String.valueOf(calendar.get(Calendar.YEAR));
        String mes=String.valueOf(calendar.get(Calendar.MONTH)+1);
        mes=String.format("%02d",Integer.valueOf(mes));       
        String dia=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        dia=String.format("%02d",Integer.valueOf(dia));   
        return ano+mes+dia;
    }
    
    private String sumaVales(ArrayList<Object> vales){
        Double total=0.0;
        for(Object v:vales){
            total+=((Vale)v).getImporteVale();
        }
        String strTotal="";
        strTotal=String.format("%013d",total.intValue());
        strTotal+="00";
        return strTotal;
    }
    
    private String sumaLiq(ArrayList<Object> liq){
        Double total=0.0;
        for(Object l:liq){
            total+=((LineaArchivoBanco)l).getLiquidoFinal();
        }
        String strTotal="";
        strTotal=String.format("%013d",total.intValue());
        strTotal+="00";
        return strTotal;
    }

    private void cargoLíneas(ArrayList<Object> valesBase,Integer tipo) {
       String linea="";
       if(tipo==1){
           for(Object o:valesBase){
               try {
                   LineaArchivoBanco l=((LineaArchivoBanco)o);
                   linea+="D";
                   linea+=String.format("%03d",Integer.valueOf(l.getF().getBanco().getCodBcu()));
                   linea+=String.format("%04d",l.getF().getBanco().getSucursal());
                   linea+="01";
                   DecimalFormat df = new DecimalFormat("##0");
                   linea+=String.format("%035d",Long.valueOf(df.format(l.getF().getCuenta())));
                   linea+=String.format("%013d",l.getLiquidoFinal().intValue());
                   suma+=l.getLiquidoFinal();
                   linea+="00";
                   String nombre = l.getF().getNomCompletoApe().trim().replace("  ", " ");
                   if(nombre.length()>=35){
                       nombre=nombre.substring(0,35);
                   }
                   linea+=String.format("%-35s", nombre);
                   bfwriter.write(linea);
                   bfwriter.newLine();
                   linea="";
                   this.cargaExcel(nombre,l.getLiquidoFinal(),i,l.getF().getCodFunc(),l.getF(),l.getF().getCuenta());
                   i++;
               } catch (IOException ex) {
                   Logger.getLogger(ArmoTxtxBanco.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }
       else{
       for(Object o:valesBase){
           Vale v=((Vale)o);
           try {
               linea+="D";
               linea+=String.format("%03d",Integer.valueOf(v.getF().getBanco().getCodBcu()));
               linea+=String.format("%04d",v.getF().getBanco().getSucursal());
               linea+="01";
               DecimalFormat df = new DecimalFormat("##0");
               linea+=String.format("%035d",Long.valueOf(df.format(v.getCuenta())));
               linea+=String.format("%013d",v.getImporteVale().intValue());
               suma+=v.getImporteVale();
               linea+="00";
               String nombre = v.getNombre().trim().replace("  ", " ");
               if(nombre.length()>=35){
                   nombre=nombre.substring(0,35);
               }
               linea+=String.format("%-35s", nombre);
               bfwriter.write(linea);
               bfwriter.newLine();
               linea="";
               this.cargaExcel(nombre,v.getImporteVale(),i,v.getCodFunc(),v.getF(),v.getCuenta());
               i++;
               
           } catch (IOException ex) {
               Logger.getLogger(ArmoTxtxBanco.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
    }
    }
    
    private void armoExcel(Date fechaInfo) throws FileNotFoundException{
        File fileXLS = new File("C:\\SUELDOSLIST\\"+nombre+"_"+this.formateoFechaInfo2(fechaInfo)+".xls");
       
            
            headers = new String[]{
                "Nº Func",//0
                "Nombre",//1
                "Sucursal",//2
                "NºCuenta",//3
                "Importe"//4
           };
        fileOut = new FileOutputStream(fileXLS);
        libro= new HSSFWorkbook();
        hoja = libro.createSheet("Mi hoja de trabajo 1");
        Row fi=hoja.createRow(1);
        
        HSSFCellStyle styleTitulo = (HSSFCellStyle) libro.createCellStyle();
        Font fontTit = libro.createFont();
        fontTit.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fontTit.setFontHeightInPoints((short) 13);
        styleTitulo.setAlignment(HorizontalAlignment.LEFT);
        styleTitulo.setFont(fontTit);
        
        Cell cel = fi.createCell(0);
        cel.setCellStyle(styleTitulo);
        cel.setCellValue(titulo+" "+this.formateoFecha(fechaInfo));  
          
        HSSFCellStyle style = (HSSFCellStyle) libro.createCellStyle();
        Font font = libro.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        
        
        Row cabezal=hoja.createRow(4);
        Cell celNum = cabezal.createCell(0);
        Cell celNom = cabezal.createCell(1);
        Cell celSuc = cabezal.createCell(2);
        Cell celCta = cabezal.createCell(3);
        Cell celImp = cabezal.createCell(4);
       
        celNum.setCellStyle(style);
        celNom.setCellStyle(style);
        celSuc.setCellStyle(style);
        celCta.setCellStyle(style);
        celImp.setCellStyle(style);

        celNum.setCellValue(headers[0]);
        celNom.setCellValue(headers[1]);
        celSuc.setCellValue(headers[2]);
        celCta.setCellValue(headers[3]);
        celImp.setCellValue(headers[4]);
    }
    
     private void armoExcelSinCuenta(Date fechaInfo,ArrayList<Object> sinCuenta,Integer tipo) throws FileNotFoundException{
    try {
        File fileXLSSinCuenta = new File("C:\\SUELDOSLIST\\"+nombre+"_"+this.formateoFechaInfo2(fechaInfo)+"-Funcionarios sin cuenta.xls");
        
        
        headers = new String[]{
            "Nº Func",//0
            "Nombre",//1
            "Sucursal",//2
            "Importe"//3
        };
        FileOutputStream fileOutSinCuenta = new FileOutputStream(fileXLSSinCuenta);
        libroSinCuenta= new HSSFWorkbook();
        hojaSinCuenta = libroSinCuenta.createSheet("Mi hoja de trabajo 1");
        Row fi=hojaSinCuenta.createRow(1);
        
        HSSFCellStyle styleTitulo = (HSSFCellStyle) libroSinCuenta.createCellStyle();
        Font fontTit = libroSinCuenta.createFont();
        fontTit.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fontTit.setFontHeightInPoints((short) 13);
        styleTitulo.setAlignment(HorizontalAlignment.LEFT);
        styleTitulo.setFont(fontTit);
        
        Cell cel = fi.createCell(0);
        cel.setCellStyle(styleTitulo);
        cel.setCellValue(titulo+" "+this.formateoFecha(fechaInfo)+" (Funcionarios sin cuenta)");
        
        HSSFCellStyle style = (HSSFCellStyle) libroSinCuenta.createCellStyle();
        Font font = libroSinCuenta.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        
        
        Row cabezal=hojaSinCuenta.createRow(4);
        Cell celNum = cabezal.createCell(0);
        Cell celNom = cabezal.createCell(1);
        Cell celSuc = cabezal.createCell(2);
        Cell celImp = cabezal.createCell(3);
       
        celNum.setCellStyle(style);
        celNom.setCellStyle(style);
        celSuc.setCellStyle(style);
        celImp.setCellStyle(style);

        celNum.setCellValue(headers[0]);
        celNom.setCellValue(headers[1]);
        celSuc.setCellValue(headers[2]);
        celImp.setCellValue(headers[3]);
        if(tipo==1){
           for(Object o:sinCuenta){
               LineaArchivoBanco l=((LineaArchivoBanco)o);
                  String nombre = l.getF().getNomCompletoApe().trim().replace("  ", " ");
                   if(nombre.length()>=35){
                       nombre=nombre.substring(0,35);
                   }
                this.cargaExcelSinCuenta(nombre,l.getLiquidoFinal(),s,l.getF().getCodFunc(),l.getF(),hojaSinCuenta);
                s++;
           }
        }
        else{
            for(Object o:sinCuenta){
               Vale v=((Vale)o);
               String nombre = v.getNombre().trim().replace("  ", " ");
               if(nombre.length()>=35){
                   nombre=nombre.substring(0,35);
               }
               this.cargaExcelSinCuenta(nombre,v.getImporteVale(),s,v.getCodFunc(),v.getF(),hojaSinCuenta);
               s++;
            }
        }
        libroSinCuenta.write(fileOutSinCuenta);
        fileOutSinCuenta.close();
    } catch (IOException ex) {
        Logger.getLogger(ArmoTxtxBanco.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    private void cargaExcel(String nombre, Double importe,int i,Integer codFunc,Funcionario f,Double cuenta) {
        HSSFCellStyle styleRight = (HSSFCellStyle) libro.createCellStyle();
        Font fontRight = libro.createFont();
        fontRight.setBoldweight(Font.BOLDWEIGHT_BOLD);
        styleRight.setAlignment(HorizontalAlignment.RIGHT);
        styleRight.setFont(fontRight);
        Row fila = hoja.createRow(i);
        for(int c=0;c<headers.length;c++){
                    Cell celda = fila.createCell(c);
                
                    
            switch (c) {
                case 0:
                    hoja.setColumnWidth(c, 2000);
                    celda.setCellValue(codFunc);
                    break;
                case 1:
                    hoja.setColumnWidth(c, 11000);
                    celda.setCellValue(nombre);
                    break;
                case 2:
                    hoja.setColumnWidth(c, 4000);
                    celda.setCellValue(f.getBanco().getSucursal()+"-"+f.getBanco().getNombre());
                    break;
                case 3:
                    hoja.setColumnWidth(c, 3000);
                    DecimalFormat df = new DecimalFormat("##0");
                    celda.setCellValue(Long.valueOf(df.format(cuenta)));
                    break;
                case 4:
                    hoja.setColumnWidth(c, 3300);
                    celda.setCellStyle(styleRight);
                    celda.setCellValue(this.decimales(importe));
                    break;
                default:
                    break;
            }
        }
    }
    
        
    private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
}

    private void cargoTotalExcel() {
        HSSFCellStyle style = (HSSFCellStyle) libro.createCellStyle();
        Font font = libro.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font);
        
        HSSFCellStyle styleRight = (HSSFCellStyle) libro.createCellStyle();
        Font fontRight = libro.createFont();
        fontRight.setBoldweight(Font.BOLDWEIGHT_BOLD);
        styleRight.setAlignment(HorizontalAlignment.RIGHT);
        styleRight.setFont(fontRight);
        
        Row cabezal=hoja.createRow(i+2);
        Cell celNom = cabezal.createCell(3);
        Cell celImp = cabezal.createCell(4);
        celNom.setCellStyle(style);
        celImp.setCellStyle(styleRight);
        celNom.setCellValue("TOTAL");
        celImp.setCellValue(this.decimales(suma));
    }

    private void cargoTipoVale(ArrayList<Object> valesBase) {
        if(valesBase!=null){
            if(valesBase.size()>0){
                this.tipoVale=((Vale)valesBase.get(0)).getTipo();
            }
        }
    }

    private String cargoTipoLiq(ArrayList<Object> valesBase) {
        String ret="";
        Integer tipo=null;
        if(valesBase!=null){
            if(valesBase.size()>0){
                tipo=((LineaArchivoBanco)valesBase.get(0)).getTipoLiq();
            }
        }
        if(tipo!=null){
            switch (tipo) {
                case 1:
                    ret="SUELDOS ACE";
                    break;
                case 3:
                    ret="AGUINALDOS ACE";
                    break;
                case 4:
                    ret="VACACIONALES ACE";
                    break;
                case 5:
                    ret="RELIQSUELDOS ACE";
                    break;
                default:
                    break;
            }
        }
        return ret;
    }

    private void cargaExcelSinCuenta(String nombre, Double importe, int i, Integer codFunc, Funcionario f,Sheet hojaSinCuenta) {
        HSSFCellStyle styleRight = (HSSFCellStyle) libroSinCuenta.createCellStyle();
        Font fontRight = libroSinCuenta.createFont();
        fontRight.setBoldweight(Font.BOLDWEIGHT_BOLD);
        styleRight.setAlignment(HorizontalAlignment.RIGHT);
        styleRight.setFont(fontRight);
        Row fila = hojaSinCuenta.createRow(i);
        for(int c=0;c<headers.length;c++){
                    Cell celda = fila.createCell(c);
                
                    
            switch (c) {
                case 0:
                    hojaSinCuenta.setColumnWidth(c, 2000);
                    celda.setCellValue(codFunc);
                    break;
                case 1:
                    hojaSinCuenta.setColumnWidth(c, 11000);
                    celda.setCellValue(nombre);
                    break;
                case 2:
                    hojaSinCuenta.setColumnWidth(c, 4000);
                    celda.setCellValue(f.getBanco().getSucursal()+"-"+f.getBanco().getNombre());
                    break;
                case 3:
                    hojaSinCuenta.setColumnWidth(c, 3300);
                    celda.setCellStyle(styleRight);
                    celda.setCellValue(this.decimales(importe));
                    break;
               default:
                    break;
            }
        }
    }
  
}
