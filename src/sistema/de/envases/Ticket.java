
package sistema.de.envases;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BarcodePDF417;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.PlatformUtil;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.print.Doc;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class Ticket {
    Persistencia pers=new Persistencia();
    private static final String iTextExampleImage = "/JAVA/Escritorio/Sistema de Envases/src/Imagenes/logo.png";
    private static final String linux = "/opt/SistemaDeEnvases/logo.png";
    
    public boolean createPDF(File pdfNewFile,Integer nro,ArrayList<MovEnvases> movimientos,Integer reimp) throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException, BadElementException, IOException, PrintException {
       boolean arma=false;
       Document document =null;
       Double total=0.0;
       float fijo=375;
       if(movimientos.size()>0){
                    float varia=25*movimientos.size();
                    Font f=new Font(FontFamily.HELVETICA,8f,Font.NORMAL,BaseColor.BLACK);
                    document = new Document();
                    PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
                    Rectangle one = new Rectangle(225,fijo+varia);
                    document.setPageSize(one);
                    document.setMargins(10, 10, 10, 10);

                    document.open();

                    this.armoCabezal(document,movimientos,reimp);
                    Paragraph texto; 
                    Paragraph cantidad;
                    Paragraph tot;
                    String linea = "";

               for(MovEnvases l:movimientos){
                  texto = new Paragraph(l.toString(),f);            
                  texto.setAlignment(Element.ALIGN_LEFT);

                  cantidad=new Paragraph("         "+l.toString2()+"                                              "+this.decimales(l.getTotal()),f);
                  texto.setAlignment(Element.ALIGN_LEFT);

                  document.add(texto);
                  document.add(cantidad);
                  total+=l.getTotal();
                  }
               tot=new Paragraph("                                                           TOTAL: "+this.decimales(total),f);
               //tot.setAlignment(Element.ALIGN_RIGHT);
               document.add(new Phrase(Chunk.NEWLINE));
               Paragraph chunk8=new Paragraph("---------------------------------------------------");
               chunk8.setAlignment(Element.ALIGN_CENTER);
               document.add(chunk8);
               document.add(tot);

               PdfContentByte cb=new PdfContentByte(writer);
               Barcode128 barcode128 = new Barcode128();
               barcode128.setCode("D"+nro.toString());
               barcode128.setFont(null);
               barcode128.setCodeType(Barcode.CODE128);
               Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
               code128Image.setAlignment(Element.ALIGN_CENTER);
               document.add(code128Image);

               Paragraph chunk0 = new Paragraph("Presentar este ticket al cajero",f);
               Paragraph chunk1 = new Paragraph("*** GRACIAS POR SU PREFERENCIA ***",f);
               chunk0.setAlignment(Element.ALIGN_CENTER);
               chunk1.setAlignment(Element.ALIGN_CENTER);

               document.add(new Phrase(Chunk.NEWLINE));
               document.add(chunk0);
               document.add(new Phrase(Chunk.NEWLINE));
               document.add(chunk1);
                             
               arma=true;
              
               
       }    
     
       if(document!=null){
           document.close();  
       }
              
       return arma;
    }
    
    
    private void armoCabezal(Document document,ArrayList<MovEnvases>movimientos,Integer reimp) throws DocumentException, BadElementException, IOException{
       Font f=new Font(FontFamily.HELVETICA,8f,Font.NORMAL,BaseColor.BLACK);
       Image image;
            if(PlatformUtil.isWindows()){
                image = Image.getInstance(iTextExampleImage);
                image.scaleToFit(90, 420);
                image.setAlignment(Element.ALIGN_CENTER);
                document.add(image);
            }
            else{
                image = Image.getInstance(linux);
                image.scaleToFit(90, 420);
                image.setAlignment(Element.ALIGN_CENTER);
                document.add(image);
            }
         
       
       Paragraph chunk0 = new Paragraph("ASOCIACION COOPERATIVA ELECTORAL",f);
       Paragraph chunk1 = new Paragraph("8 de Octubre 2897",f);
       Paragraph chunk2 = new Paragraph("MONTEVIDEO - URUGUAY",f);
       Paragraph chunk3= new Paragraph("RUT 210362180016",f);
       Paragraph chunk4= new Paragraph(this.convertirFecha(movimientos.get(0).getFecha()),f);
       Paragraph chunk5= new Paragraph("**** RECEPCION DE ENVASES ****",f);
       Paragraph chunk6= new Paragraph(" Referencia: "+ movimientos.get(0).getTicket(),f);
       Paragraph chunk7= new Paragraph(" Vencimiento: "+ movimientos.get(0).getVencimiento() + " Días",f);
       Paragraph chunk8=new Paragraph("---------------------------------------------------");
       Paragraph chunk9=new Paragraph("REIMPRESION DE DOCUMENTO",f);
       
  
       chunk0.setAlignment(Element.ALIGN_CENTER);
       chunk1.setAlignment(Element.ALIGN_CENTER);
       chunk2.setAlignment(Element.ALIGN_CENTER);
       chunk3.setAlignment(Element.ALIGN_CENTER);
       chunk4.setAlignment(Element.ALIGN_CENTER);
       chunk5.setAlignment(Element.ALIGN_CENTER);
       chunk6.setAlignment(Element.ALIGN_LEFT);
       chunk7.setAlignment(Element.ALIGN_LEFT);
       chunk8.setAlignment(Element.ALIGN_CENTER);
       chunk9.setAlignment(Element.ALIGN_CENTER);
       
       document.add(chunk0);
       document.add(chunk1);
       document.add(chunk2);
       document.add(new Phrase(Chunk.NEWLINE));
       document.add(chunk3);
       document.add(new Phrase(Chunk.NEWLINE));
       document.add(chunk4);
       document.add(new Phrase(Chunk.NEWLINE));
       document.add(chunk5);
       document.add(new Phrase(Chunk.NEWLINE));
       document.add(chunk6);
       document.add(chunk7);
       document.add(chunk8);
       if(reimp==1){
           document.add(chunk9);
           document.add(chunk8);
       }
            
    }
    
    private String decimales(Double sueldo) {
    DecimalFormatSymbols simbolo= new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
                
    DecimalFormat df=new DecimalFormat("#,###,##0.00",simbolo);
    return df.format(sueldo);
}
     private String convertirFecha(Timestamp fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
         str= sdf.format(fecha);
        }
    
    return str;
    }
    
    
    
    
    
}


