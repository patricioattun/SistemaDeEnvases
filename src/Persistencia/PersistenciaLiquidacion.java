
package Persistencia;

import Dominio.CentroCosto;
import Dominio.Codigo;
import Dominio.Declaracion;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Dominio.Irpf;
import Dominio.Licencia;

import Dominio.PersACargo;
import Dominio.ResumenLiquidacion;
import Dominio.Retencion;
import Dominio.irpfDetallado;
import Dominio.Liquidacion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;





public class PersistenciaLiquidacion {
    private PersistenciaCodigo perCod;
    private PersistenciaFuncionario perFunc;
    private  Connection cnn=null;
    //VARIABLES GLOBALES//
    private boolean hacerAjusteAnual;
    private ArrayList<Funcionario> funcionarios;
    private double primaAntiguedad;
    private double cantina;
    private double sindical;
    private double bpcActual;
    private double bpcActualFecha;
    private double hogarConstituido;
    private double hogarConstituidoOld;
    private double seguro;
    private double seguroAce;
    private ArrayList<Irpf> irpfRetenciones;
    private ArrayList<Irpf> irpfDeducciones;
    private ArrayList<Codigo> codigos;
    private Integer anosAntiguedad;
    private boolean tieneCantina;
    private boolean tieneSindical;
    private boolean tieneHogar;
    private double tieneQuebranto;
    private double montoImponibleSeguro; 
    private boolean tieneSeguro;
    private ArrayList<Ingreso> ingresos=null;
    private boolean reciboEncero;
    private Liquidacion tieneHogarConstituido;
    private Liquidacion antiguedad;
    private Integer contador = 0;
    private double topeAfap;
    private double dipaico;
    private double frl;
    private double totalHaberesBps;
    private double totalHaberesBpsOld;
    private double totalHaberesNoBps;
    private double totalHaberesIrpf;
    private double totalHaberesGeneral;
    private double totDipaico;
    private double tasaDipaico;
    private double impDipaico;
    private double impDipaico1;
    private double totDipaico1;
    private double tasaDipaico1;
    private double difDipaico;
    private double fonasa;
    private double impFonasa;
    private double impFonasa1;
    private double impFrl;
    private double impFrl1;
    private double impIrpf;
    private Liquidacion quebranto;
    private double impDesc;
    private double depQueb;
    private double casenpaceVoluntario;
    private double porcentajeCasenpaceVol;
    private double irpfNeto;
    private double irpfTasa;
    private double montoGratificacionesRaras;
    private double liquidoLegal;
    private double montoAjuste;
    private double seg_ace;
    private double porcentajeLiquidoMinimo;
    private double liquidoMinimo;
    private double descontableExtra;
    private double porcentajeMinimoExtra;
    private double porcentajeSacec;
    private double liquidoFinal;
    private double liquidoDescontable;
    private double tasaDescuentoGeneral;
    private double hogarDescuentos;
    private double restoRetencion;
    private double descuentoRechazado;
    private double descuentoAplicado;
    private ArrayList<Liquidacion> liquidaciones;
    private ArrayList<Liquidacion> liquidacionesMem;
    Comparator<Liquidacion> comparato;
    private double restoVale;
    private double importeADescontar;
    private double totalDescuentos;
    private double redondeo;
    private double totalNoRetenido;
    private String cuantasBPC;
    Comparator<Ingreso> comparator;
    private Date fechaGeneracion;
    //------------------------------//

    public PersistenciaLiquidacion(Date fechaLiq, int tipo) throws ClassNotFoundException, SQLException, BDExcepcion, ParseException {
         if(tipo==1){//sueldo comun
         this.cargoPreparativos(fechaLiq);
         cnn=Conexion.Cadena();
         cnn.setAutoCommit(false);
         }else if(tipo==2){//reliquida sueldo
             this.cargoPreparativosReLiq(fechaLiq);
             cnn=Conexion.Cadena();
             cnn.setAutoCommit(false);
         }else if(tipo==3){
             this.cargoPreparativosVacacionales(fechaLiq);
             cnn=Conexion.Cadena();
             cnn.setAutoCommit(false);
         }
    }
    
    

    public PersistenciaLiquidacion() {
        this.perCod=new PersistenciaCodigo();
    }
    
    //-------------------------------------------LIQUIDACION---------------------------------------------------//
    
    //PREPARATIVOS

  private void cargoPreparativos(Date fechaLiq) throws ClassNotFoundException, SQLException, BDExcepcion{
    cnn=Conexion.Cadena();
    cnn.setAutoCommit(false);
    this.verificoAjusteAnual(fechaLiq);
    this.primaAntiguedad = this.cargoParametro("ANTIGUEDAD");
    this.cantina = this.cargoParametro("CANTINA");
    this.sindical = this.cargoParametro("SINDICAL");
  
    this.cargoBpcActual();
    this.hogarConstituido= this.bpcActual*0.1;
    this.hogarConstituido = this.fijarNumero(this.hogarConstituido, 2);
    
    this.seguro = this.cargoParametro("SEGURO");
    this.seguroAce = this.cargoParametro("SEGUROACE");
    this.cuantasBPC = this.cargoParametroString("ADICIONAL CJPPU"); 
    this.topeAfap = this.cargoParametro("TOPEAFAP");
    this.dipaico = this.cargoParametro("DIPAICO");
    this.frl = this.cargoParametro("FRL");           
    this.porcentajeCasenpaceVol = this.cargoParametro("CASENPACEVOL");
    this.porcentajeLiquidoMinimo = this.cargoParametro("PORC_LIQ_MIN");
    this.porcentajeSacec = this.cargoParametro("PORC_SACEC");
    this.porcentajeMinimoExtra = 0.05;
    this.cargoIrpfRetenciones();
    this.cargoIrpfDeducciones();
    this.codigos = this.cargoCodigosPers();
    
    cnn.commit();
    
    if(cnn!=null){
       cnn.close();
    }
  }
  
  private void cargoPreparativosVacacionales(Date fechaLiq) throws ClassNotFoundException, SQLException, BDExcepcion{
    cnn=Conexion.Cadena();
    cnn.setAutoCommit(false);
    this.verificoAjusteAnual(fechaLiq);
    this.primaAntiguedad = this.cargoParametro("ANTIGUEDAD");
    this.cantina = this.cargoParametro("CANTINA");
    this.sindical = this.cargoParametro("SINDICAL");
  
    this.cargoBpcActual();
    this.hogarConstituido= this.bpcActual*0.1;
    this.hogarConstituido = this.fijarNumero(this.hogarConstituido, 2);
    
    this.seguro = this.cargoParametro("SEGURO");
    this.seguroAce = this.cargoParametro("SEGUROACE");
    this.cuantasBPC = this.cargoParametroString("ADICIONAL CJPPU"); 
    this.topeAfap = this.cargoParametro("TOPEAFAP");
    this.dipaico = this.cargoParametro("DIPAICO");
    this.frl = this.cargoParametro("FRL");           
    this.porcentajeCasenpaceVol = this.cargoParametro("CASENPACEVOL");
    this.porcentajeLiquidoMinimo = this.cargoParametro("PORC_LIQ_MIN");
    this.porcentajeSacec = this.cargoParametro("PORC_SACEC");
    this.porcentajeMinimoExtra = 0.05;
    this.cargoIrpfRetenciones();
    this.cargoIrpfDeducciones();
    this.codigos = this.cargoCodigosPers();
    this.fechaGeneracion=this.armoFechaGeneracion(fechaLiq);
    cnn.commit();
    
    if(cnn!=null){
       cnn.close();
    }
  }
  
  private void cargoPreparativosReLiq(Date fechaLiq) throws ClassNotFoundException, SQLException, BDExcepcion, ParseException{
      cnn=Conexion.Cadena();
      cnn.setAutoCommit(false);
      this.verificoAjusteAnual(fechaLiq);
      this.primaAntiguedad = this.cargoParametroReLiq("ANTIGUEDAD",fechaLiq);
      this.primaAntiguedad = this.fijarNumero(this.primaAntiguedad, 0);
      this.cargoBpcActual();
      this.hogarConstituido= this.bpcActual*0.1;
      this.hogarConstituido = this.fijarNumero(this.hogarConstituido, 2);
      this.cargoBpcActualFecha(fechaLiq);
      this.hogarConstituidoOld = this.bpcActualFecha*0.1;
      this.hogarConstituidoOld = this.fijarNumero(this.hogarConstituidoOld, 2);
      this.hogarConstituido = this.hogarConstituido-this.hogarConstituidoOld;
      this.hogarConstituido = this.fijarNumero(this.hogarConstituido, 2);
      this.seguro = this.cargoParametroReLiqDos("SEGURO",fechaLiq);
      this.seguroAce = this.cargoParametroReLiqDos("SEGUROACE", fechaLiq);
      this.cargoIrpfRetencionesOld(fechaLiq);
      this.cargoIrpfDeduccionesOld(fechaLiq);
      this.porcentajeLiquidoMinimo = this.cargoParametro("PORC_LIQ_MIN");
      
      this.topeAfap = this.cargoParametroReLiq("TOPEAFAP",fechaLiq);
      this.dipaico = this.cargoParametroReLiq("DIPAICO",fechaLiq);
      this.frl = this.cargoParametroReLiq("FRL",fechaLiq);         
  }
    
 
            
//PROCESO DE LIQUIDACION            
public void liquidar(Funcionario f, Date fechaLiq, Integer tipo,boolean reLiq) throws ClassNotFoundException, SQLException, BDExcepcion, ParseException{
    if(f!=null){
        if(tipo==1){
        this.comienzoLiquidación(f,fechaLiq);
        cnn.commit();
        this.eliminacionesFinales(f);
        }else if(tipo==2){
            this.Reliquidar(f, fechaLiq);
            cnn.commit();
            this.eliminacionesFinalesReLiq(f);
        }else if(tipo==3){
            this.liquidarVacacionales(f, fechaLiq);
            cnn.commit();
            this.eliminacionesVacacionales(f);
        }else if (tipo==4){
            this.ReliquidarVacacionales(f, fechaLiq);
            cnn.commit();
            this.eliminacionesVacacionales(f);
        }else if (tipo==5){
            this.liquidarAguinaldo(f, fechaLiq,reLiq);
            cnn.commit(); 
            this.eliminacionesFinalesAguinaldo(f);
        }
    }
    if(cnn!=null){
       cnn.close();
    }
   
    
}    


    private void comienzoLiquidación(Funcionario k, Date fecha) throws BDExcepcion, ParseException, SQLException, ClassNotFoundException {
       this.limpioTablas(k);
       this.reconecto();
       Codigo c = null;
       Liquidacion l = null;
       liquidacionesMem=new ArrayList<>();
       
       //Liquidacion(Funcionario f, Integer tipoLiq, Codigo codigo, Double cantidad, Double valorUnitario, Double importe, Date fecha,Integer importeNoRet);
//       Date fecha = new Date();
       
        //-----INSERTO SUELDO 1--------//
       c=this.buscarCodigo(1);
       l = new Liquidacion(k,1,c,1.0,k.getSueldoCargo(),k.getSueldoCargo(),fecha,0);
       contador+=this.insertoLiquidacion(l);
       this.liquidacionesMem.add(l);
       //------------------------------//
       
       //-----INSERTO ANTIGUEDAD 43----//
       this.anosAntiguedad = this.calculoAntiguedad(k.getFechaIngreso(),fecha);
       if(this.anosAntiguedad>0){
           c=this.buscarCodigo(43);
           double antiguedadTotal = this.anosAntiguedad*this.primaAntiguedad;
           antiguedadTotal = this.fijarNumero(antiguedadTotal, 2);
           l = new Liquidacion(k,1,c,Double.parseDouble(anosAntiguedad.toString()),antiguedadTotal,antiguedadTotal,fecha,0);
           contador+=this.insertoLiquidacion(l);
           this.liquidacionesMem.add(l);
       }
       //------------------------------//
       
       //------INSERTO CANTINA 530-----//
       this.tieneCantina = this.tieneCodFijo(k.getCodFunc(),530);
       if(this.tieneCantina){
           c=this.buscarCodigo(530);
           l = new Liquidacion(k,1,c,1.0,cantina,cantina,fecha,0);
           contador+=this.insertoLiquidacion(l);
           this.liquidacionesMem.add(l);
       }
       //------------------------------//
       
       //------INSERTO SINDICAL 335-----//
       this.tieneSindical = this.tieneCodFijo(k.getCodFunc(),335);
       if(this.tieneSindical){
           c=this.buscarCodigo(335);
           l = new Liquidacion(k,1,c,1.0,sindical,sindical,fecha,0);
           contador+=this.insertoLiquidacion(l);
           this.liquidacionesMem.add(l);
       }
       //------------------------------//
       
       //------INSERTO HOGAR CONSTITUIDO 42 -----//
       this.tieneHogar = this.tieneCodFijo(k.getCodFunc(),42);
       if(this.tieneHogar){
           c=this.buscarCodigo(42);
           l = new Liquidacion(k,1,c,1.0,hogarConstituido,hogarConstituido,fecha,0);
           contador+=this.insertoLiquidacion(l);
           this.liquidacionesMem.add(l);
       }
       //------------------------------//
       
       //------INSERTO QUEBRANTO 44 -----//
       this.tieneQuebranto= this.cargoQuebranto(k.getCodFunc());
//       if(this.tieneQuebranto>0){
//           java.math.BigDecimal valor = new java.math.BigDecimal(this.tieneQuebranto);
//           String str=String.valueOf(valor.divide(new BigDecimal("100")));
//           double divisor =Double.parseDouble(str);
//           double quebranto = k.getSueldoCargo()*divisor;
//           quebranto=this.fijarNumero(quebranto, 2);
//           c=this.buscarCodigo(44);
//           l = new Liquidacion(k,1,c,1.0,quebranto,quebranto,fecha,0);
//           contador+=this.insertoLiquidacion(l);
//           this.liquidacionesMem.add(l);
//       }

        if(this.tieneQuebranto>0){
        java.math.BigDecimal valorDivisor = new java.math.BigDecimal(this.tieneQuebranto);
        valorDivisor=valorDivisor.divide(new BigDecimal("100"));    
        java.math.BigDecimal sueldo = new java.math.BigDecimal(k.getSueldoCargo());
        
        java.math.BigDecimal queb = sueldo.multiply(valorDivisor);
        
        double quebranto=queb.doubleValue();
        quebranto=this.fijarNumero(quebranto,2);
        c=this.buscarCodigo(44);
        l = new Liquidacion(k,1,c,1.0,quebranto,quebranto,fecha,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
        }
       //------------------------------//
       
       //------INSERTO REDUCCION 103 -----//
       if(k.getBaseHoraria()!=39){
           Double aux = k.getSueldoCargo()/39;
           aux=aux*k.getBaseHoraria();
           Double reduccion = k.getSueldoCargo()-aux;
           reduccion = Double.parseDouble(String.valueOf(reduccion.intValue()));
           c=this.buscarCodigo(103);
           l = new Liquidacion(k,1,c,1.0,reduccion,reduccion,fecha,0);
           contador+=this.insertoLiquidacion(l);
           this.liquidacionesMem.add(l);
       }
       //------------------------------//
       
       //------INSERTO SEGURO 355 -----////PONER CODIGO 355
       this.tieneSeguro=this.tieneCodFijo(k.getCodFunc(),355);
       
       if(this.tieneSeguro){
           this.montoImponibleSeguro = this.buscoSueldoYreduccion(k.getCodFunc());
           double seg=seguro*this.montoImponibleSeguro;
           seg=this.fijarNumero(seg, 2);
           c=this.buscarCodigo(355);
           l = new Liquidacion(k,1,c,1.0,seg,seg,fecha,0);
           contador+=this.insertoLiquidacion(l);
           this.liquidacionesMem.add(l);
       
       //------------------------------//
       
       //------INSERTO SEGURO ACE 49 -----//
       
       
           double seAce=seguroAce*this.montoImponibleSeguro;
           seAce=this.fijarNumero(seAce, 2);
           c=this.buscarCodigo(49);
           l = new Liquidacion(k,1,c,1.0,seAce,seAce,fecha,0);
           contador+=this.insertoLiquidacion(l);
           this.liquidacionesMem.add(l);
       }
       //------------------------------//
       
       //----------------CARGA DE MOVIMIENTOS DESDE INGRESOS A LIQUIDACIONES--------------------//
       this.cargoIngresos(k);
       if(ingresos!=null){
           if(ingresos.size()>0){
               for(Ingreso i: ingresos){
                   Double importe = this.fijarNumero(i.getImporte(), 2);
                   double sueldoRed=this.buscoSueldoYreduccion(k.getCodFunc());
                   if(null!=i.getCod())switch (i.getCod()) {
                       case 11: //Licencia por enfermedad
                           Calendar e=Calendar.getInstance();
                           e.setTime(fecha);
                           double aux = this.enfermedadAnterior(k,e.get(Calendar.YEAR));
                           if(aux>=30){
                               importe=i.getCantidad()*(sueldoRed/30);
                               importe=this.fijarNumero(importe, 2);
                           }else if((aux+i.getCantidad())>=30){
                               importe=((aux+i.getCantidad())-30) * (sueldoRed/30);
                               importe=this.fijarNumero(importe, 2);
                           }
                           break;
                       case 13://Maternidad
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 15: //Licencia paternidad 5 dias paga ace, el resto se descuenta lo paga bps
                           importe=(i.getCantidad()-5)*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 18://Licencia sin sueldo
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 20://Feriado no laborable
                           importe=(i.getCantidad()*(sueldoRed/30))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 21://Feriado laborable
                           importe=(i.getCantidad()*(sueldoRed/30))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 22://jornal guardia
                           importe=(i.getCantidad()*(sueldoRed/30))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 23://Compensacion
                           importe = this.fijarNumero(i.getImporte(), 2);
                           break;
                       case 24://Promedio horas extras de licencia
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 25://Inasistencia
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 26://Suspensiones
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 27://Inasistencia por paro
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 30://Complemento nocturno
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*0.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 31://Horas extras simples
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 32://Horas extras feriado no laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*4.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 33://Horas extras feriado laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*3.5;
                           importe=this.fijarNumero(importe, 2);
                           break; 
                       case 36://Horas de licencia sin sueldo
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras());
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 37://Descuento horario por paro
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras());
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 40://Llegadas tarde
                           if(i.getCantidad()>30){
                           importe=((i.getCantidad()-30)*(k.getSueldoCargo()/(k.getBaseHoras()*60)));
                           importe=this.fijarNumero(importe, 2);
                           }
                           break;
                       case 46://Promedio horas extras feriado no laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*4.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 47://Promedio horas extras feriado laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*3.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 59://Ley 19161
                           importe=i.getCantidad()*((k.getSueldoCargo()/k.getBaseHoras())*3.5);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 61://Horas extras simples nocturnas
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras())*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 62://Horas extras feriado no laborable nocturno
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras())*4.5 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 63://Horas extras feriado laborable nocturno
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras())*3.5 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 70://Feriado no laborable nocturno
                           importe=i.getCantidad()*(k.getSueldoCargo()/30)*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 71://Feriado laborable noc
                           importe=i.getCantidad()*(sueldoRed/30)*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       default:
                           break;
                   }
                    importe=this.fijarNumero(importe, 2);
                    i.setCodMov(this.buscarCodigo(i.getCod()));
                                                    
                    l = new Liquidacion(k,1,i.getCodMov(),i.getCantidad(),importe,importe,fecha,0);
                    contador+=this.insertoLiquidacion(l);
                    this.liquidacionesMem.add(l);
               }
           }
       }
       
        this.reciboEncero=this.buscoReciboEnCero(k);
        this.tieneHogarConstituido = this.tieneHogarConstituido(k);
        this.antiguedad = this.antiguedad(k);
         this.quebranto = this.tieneQuebranto(k);
        
        if(reciboEncero && tieneHogarConstituido!=null){
            if(tieneHogarConstituido.getImporte()!=0){
                c=this.buscarCodigo(29);
                l = new Liquidacion(k,1,c,1.0,this.fijarNumero(tieneHogarConstituido.getImporte(),2),this.fijarNumero(tieneHogarConstituido.getImporte(),2),fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
            
        }
        if(reciboEncero && antiguedad!=null){
            if(antiguedad.getImporte()!=0){
                c=this.buscarCodigo(28);
                l = new Liquidacion(k,1,c,antiguedad.getCantidad(),this.fijarNumero(antiguedad.getImporte(),2),this.fijarNumero(antiguedad.getImporte(),2),fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
        }
        if(reciboEncero && quebranto!=null){
            this.eliminoDeLiquidacion(k,44);
        }
       //---------------------------------------------------------------------------------------//
       
       //----------------------------DEPOSITO DE QUEBRANTO DE CAJA------------------------------//
       
          this.totalHaberesBps = this.cargoTotalHaberesBps(k);
          //this.totalHaberesBps=this.fijarNumero(this.totalHaberesBps, 2);
          this.totalHaberesIrpf = this.cargoTotalHaberesIrpf(k);
          //this.totalHaberesIrpf = this.fijarNumero(this.totalHaberesIrpf, 2);
          this.totalHaberesGeneral = this.cargoTotalHaberesGeneral(k);
          //this.totalHaberesGeneral = this.fijarNumero(this.totalHaberesGeneral, 2);
          this.totalHaberesNoBps = this.totalHaberesGeneral-totalHaberesBps;
          this.totalHaberesNoBps = this.fijarNumero(this.totalHaberesNoBps, 2);
          
       if(quebranto!=null){
           if(this.fijarNumero(this.totalHaberesBps,2)>=this.fijarNumero(this.topeAfap,2)){
               if(k.getAfap()==1){
                   this.totDipaico=this.topeAfap*this.dipaico;
                   this.totDipaico=fijarNumero(totDipaico, 2);
                   
                   this.tasaDipaico=this.totDipaico/this.totalHaberesBps;
                   //this.tasaDipaico=fijarNumero(tasaDipaico, 2);
                   
               }
               else{
                   tasaDipaico = dipaico;
                   //tasaDipaico = this.fijarNumero(tasaDipaico, 2);
               }
           }
           else{
               tasaDipaico = dipaico;
               //tasaDipaico = this.fijarNumero(tasaDipaico, 2);
           }
       
       
       //this.impDipaico=this.quebranto.getImporte()*tasaDipaico;
       String t = String.valueOf(tasaDipaico);
   
       java.math.BigDecimal tasa = new java.math.BigDecimal(t);
       java.math.BigDecimal quebr = new java.math.BigDecimal(String.valueOf(quebranto.getImporte())); 
       java.math.BigDecimal impd = tasa.multiply(quebr);
       this.impDipaico = impd.doubleValue();
       this.impDipaico=this.fijarNumero(impDipaico, 2);
       
//       BigDecimal res = impo.subtract(impd);
//       res=res.divide(div);
//       this.depQueb=res.doubleValue();
       
       //this.impDipaico=this.fijarNumero(impDipaico, 2);
       
       t = String.valueOf(totalHaberesBps);
       java.math.BigDecimal totHab = new java.math.BigDecimal(String.valueOf(t)); 
       java.math.BigDecimal impd1 = tasa.multiply(totHab);
       this.impDipaico1 = impd1.doubleValue();
       this.impDipaico1=this.fijarNumero(impDipaico1, 2);
       
       
       
//       this.impDipaico1=this.totalHaberesBps*tasaDipaico;
//       this.impDipaico1=this.fijarNumero(impDipaico1, 2);
       
       this.fonasa=k.getSns().getFonasa();
       
       if(this.totalHaberesBps<(2.5*this.bpcActual)){
           Integer sns=k.getSns().getCodigo();
           if(sns==31 || sns==33 || sns==35 || sns==37){
               fonasa=fonasa-0.02;
           }
           else{
               fonasa=fonasa-0.005;
           }
       }
      // this.fonasa=this.fijarNumero(fonasa, 2);
       
      t = String.valueOf(fonasa);
      java.math.BigDecimal fona = new java.math.BigDecimal(t);
      java.math.BigDecimal impFo = fona.multiply(quebr);
      this.impFonasa = impFo.doubleValue();
      this.impFonasa=this.fijarNumero(impFonasa, 2);
      
      
//       this.impFonasa=this.quebranto.getImporte()*fonasa;
//       this.impFonasa=this.fijarNumero(impFonasa, 2);
//       

    
      java.math.BigDecimal impFo1 = fona.multiply(totHab);
      this.impFonasa1 = impFo1.doubleValue();
      this.impFonasa1=this.fijarNumero(impFonasa1, 2);
      
//       this.impFonasa1=this.totalHaberesBps*fonasa;
//       this.impFonasa1=this.fijarNumero(impFonasa1, 2);
       
      t = String.valueOf(this.frl);
      java.math.BigDecimal fr = new java.math.BigDecimal(t);
      java.math.BigDecimal impFr = fr.multiply(quebr);
      this.impFrl = impFr.doubleValue();
      this.impFrl=this.fijarNumero( this.impFrl, 2);

//       this.impFrl=this.quebranto.getImporte()*this.frl;
//       this.impFrl=this.fijarNumero(impFrl, 2);
//       
      
      java.math.BigDecimal impFr1 = fr.multiply(totHab);
      this.impFrl1 = impFr1.doubleValue();
      this.impFrl1=this.fijarNumero( this.impFrl1, 2);
      
//       this.impFrl1=this.totalHaberesBps*this.frl;
//       this.impFrl1=this.fijarNumero(impFrl1, 2);
       
       if(this.hacerAjusteAnual){
           this.calcularIrpfAnual(k, fecha,false);
       }else{
           this.calcularIrpfMensual(k, fecha,false);
       }
       
       this.impIrpf=this.quebranto.getImporte()*this.irpfTasa;
       this.impIrpf=this.fijarNumero(impIrpf, 2);
       
       this.impDesc=this.impDipaico+this.impFrl+this.impFonasa+this.impIrpf;
       this.impDesc=this.fijarNumero(this.impDesc, 2);
       
       double queb = this.fijarNumero(this.quebranto.getImporte(),2);
//       java.math.BigDecimal impo = new java.math.BigDecimal(queb);
//       java.math.BigDecimal impd = new java.math.BigDecimal(String.valueOf(impDesc)); 
//       java.math.BigDecimal div = new java.math.BigDecimal(2);
//       
//       BigDecimal res = impo.subtract(impd);
//       res=res.divide(div);
//       this.depQueb=res.doubleValue();
       queb=queb-impDesc;
       queb=this.fijarNumero(queb, 2);
       queb=queb/2;
       
       this.depQueb=this.fijarNumero(queb,2);
       
       c=this.buscarCodigo(315);
       l = new Liquidacion(k,1,c,1.0,depQueb,depQueb,fecha,0);
       contador+=this.insertoLiquidacion(l);
       this.liquidacionesMem.add(l);
    }
       //---------------------------------------------------------------------------------------//
      
       //----------------------------CARGA DE RETENCIONES FIJAS------------------------------//
       ArrayList<Integer> retenciones = this.cargoRetencionesFijas(k);
       if(retenciones.size()>0){
           for(Integer r:retenciones){
                c=this.buscarCodigo(r);
                l = new Liquidacion(k,1,c,1.0,0.0,0.0,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
           }
           
       }
       //---------------------------------------------------------------------------------------//
       
       //----------------------------------CARGA DE VALES---------------------------------------//
       ArrayList<Integer> vales = this.cargovalesDeLiquidaciones(k);
       if(vales.size()>0){
           for(Integer v:vales){
                c=this.buscarCodigo(501);
                l = new Liquidacion(k,1,c,1.0,0.0,0.0,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
           }
           
       }
       //---------------------------------------------------------------------------------------//
       
       //----------------------------------PROCESO DE LIQUIDACION---------------------------------------//
       
       //APLICO IMPUESTOS
       if(this.totalHaberesBps>=topeAfap){
           if(k.getAfap()==1){
//               this.impDipaico=this.topeAfap*this.dipaico;
//               this.impDipaico=this.fijarNumero(this.impDipaico, 2);
               
                String di=String.valueOf(dipaico);
                String t=String.valueOf(topeAfap);
                java.math.BigDecimal dip= new java.math.BigDecimal(di);
                java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
                java.math.BigDecimal tot = totHabBps.multiply(dip);
                impDipaico=tot.doubleValue(); 
                this.impDipaico=this.fijarNumero(this.impDipaico, 2);
           }
           else{
//               this.impDipaico=this.totalHaberesBps*this.dipaico;
//               this.impDipaico=this.fijarNumero(this.impDipaico, 2);
               
                String di=String.valueOf(dipaico);
                String t=String.valueOf(totalHaberesBps);
                java.math.BigDecimal dip= new java.math.BigDecimal(di);
                java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
                java.math.BigDecimal tot = totHabBps.multiply(dip);
                impDipaico=tot.doubleValue();
                this.impDipaico=this.fijarNumero(this.impDipaico, 2);
           }
           
       }
       else{
//           this.impDipaico=this.totalHaberesBps*this.dipaico;
//           this.impDipaico=this.fijarNumero(this.impDipaico, 2);//OK
             String di=String.valueOf(dipaico);
             String t=String.valueOf(totalHaberesBps);
             java.math.BigDecimal dip= new java.math.BigDecimal(di);
             java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
             java.math.BigDecimal tot = totHabBps.multiply(dip);
             impDipaico=tot.doubleValue();
             //this.impDipaico=this.fijarNumero(this.impDipaico, 2);
             this.impDipaico=this.fijarNumero(impDipaico, 2);
      
       }
       
      this.casenpaceVoluntario = this.totalHaberesBps*this.porcentajeCasenpaceVol;
      this.casenpaceVoluntario = this.fijarNumero(this.casenpaceVoluntario, 2);//OK
      
      this.fonasa=k.getSns().getFonasa();
      
      if(this.totalHaberesBps<=(2.5*this.bpcActual)){
          Integer sns = k.getSns().getCodigo();
          if(sns==32||sns==31){
              this.fonasa=0.03;
          }else if(sns==33 || sns==34){
              this.fonasa=0.05;
          }
          
      }
      
  
      java.math.BigDecimal dip= BigDecimal.valueOf(fonasa);
      java.math.BigDecimal totHabBps = BigDecimal.valueOf(totalHaberesBps);
      java.math.BigDecimal tot = totHabBps.multiply(dip);
      
      
   
      
//      this.impFonasa=this.totalHaberesBps*this.fonasa;

      impFonasa=tot.doubleValue();
      this.impFonasa=this.fijarNumero(this.impFonasa, 2);//OK
      
      this.impFrl=this.totalHaberesBps*this.frl;
      this.impFrl=this.fijarNumero(this.impFrl,2);//OK
      
      if(this.hacerAjusteAnual){
           this.calcularIrpfAnual(k, fecha,true);
      }else{
          this.calcularIrpfMensual(k,fecha,true);
      }
      
      //dipaico
      c=this.buscarCodigo(201);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impDipaico, 2),this.fijarNumero(impDipaico, 2),fecha,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      //fonasa
      c=this.buscarCodigo(205);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFonasa, 2),this.fijarNumero(impFonasa, 2),fecha,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      //frl
      c=this.buscarCodigo(204);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFrl, 2),this.fijarNumero(impFrl, 2),fecha,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      //irpfneto
      c=this.buscarCodigo(207);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(irpfNeto, 2),this.fijarNumero(irpfNeto, 2),fecha,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      if(casenpaceVoluntario!=0){
          c=this.buscarCodigo(515);
          l = new Liquidacion(k,1,c,1.0,this.fijarNumero(casenpaceVoluntario, 2),this.fijarNumero(casenpaceVoluntario, 2),fecha,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }
      
      //CALCULO DE LIQUIDO LEGAL Y LIQUIDO MINIMO
      this.montoGratificacionesRaras=this.cargoGratificacionesRaras(k);
      this.montoGratificacionesRaras=this.fijarNumero(this.montoGratificacionesRaras, 2);
      
      this.montoAjuste=this.cargoMontoAjuste(k);
      this.montoAjuste=this.fijarNumero(this.montoAjuste, 2);
      this.liquidoLegal=this.totalHaberesBps-(impDipaico+irpfNeto+impFrl+impFonasa)+montoGratificacionesRaras-montoAjuste;
      this.liquidoLegal=this.fijarNumero(this.liquidoLegal, 2);
      
      if(k.getCodFunc()==5300){
          this.seg_ace=this.totalSeguroAce(k);
          this.liquidoLegal=(totalHaberesGeneral-seg_ace)-(impDipaico+irpfNeto+impFrl+impFonasa)+montoGratificacionesRaras;
          this.liquidoLegal=this.fijarNumero(this.liquidoLegal, 2);
      }
      
      if(liquidoLegal>montoGratificacionesRaras){
          this.liquidoMinimo=liquidoLegal*porcentajeLiquidoMinimo;
          this.liquidoMinimo=this.fijarNumero(this.liquidoMinimo, 2);
          this.descontableExtra=porcentajeMinimoExtra*liquidoLegal;
          this.descontableExtra = this.fijarNumero(this.descontableExtra, 2);
          this.liquidoDescontable = this.liquidoLegal-this.liquidoMinimo;
          this.liquidoDescontable=this.fijarNumero(this.liquidoDescontable, 2);
          this.liquidoFinal = this.fijarNumero(liquidoLegal, 2);
      }
      else{
          liquidoMinimo=0;
          descontableExtra=0;
          liquidoDescontable=0;
          liquidoFinal=this.fijarNumero(this.montoGratificacionesRaras, 2);
      }
      
      //RETENCIONES JUDICIALES ALIMENTICIAS
      
      ArrayList<Retencion> retenJud = this.cargoRetencionesJudiciales(k);
      
      for(Retencion r : retenJud){
          double importeRetencion=0;
          if(r.getTipo()==0){//retencion de tipo fijo
                importeRetencion=this.fijarNumero(r.getImporte(),2);
          }else{//es una retencion con porcentaje
               if(r.getSobre()==0){//se aplica el porcentaje sobre el total de haberes
                   importeRetencion=this.totalHaberesBps*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
               }else{//se aplica el porcentaje sobre el liquido legal
                   importeRetencion=this.liquidoLegal*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
                   
                        if(k.getCodFunc()==3540){
                           Liquidacion liq = this.cargoLineaLiq(k,42);
                           if(liq.getImporte()!=0){
                               tasaDescuentoGeneral=1-(liquidoLegal/totalHaberesBps);
                               //tasaDescuentoGeneral=this.fijarNumero(tasaDescuentoGeneral, 2);
                               hogarDescuentos=liq.getImporte()*tasaDescuentoGeneral;
                               hogarDescuentos=this.fijarNumero(hogarDescuentos, 2);
                               restoRetencion=(liq.getImporte()-hogarDescuentos)*0.15;
                               //restoRetencion=this.fijarNumero(restoRetencion, 2);
                               importeRetencion=importeRetencion+restoRetencion;
                               importeRetencion=this.fijarNumero(importeRetencion, 2);
                           }
                        }else{
                              if(k.getCodFunc()==3160){
                                Liquidacion liq = this.cargoLineaLiq(k,42);
                                    if(liq.getImporte()!=0){
                                        tasaDescuentoGeneral=1-(liquidoLegal/totalHaberesBps);
                                        //tasaDescuentoGeneral=this.fijarNumero(tasaDescuentoGeneral, 2);
                                        hogarDescuentos=liq.getImporte()*tasaDescuentoGeneral;
                                        hogarDescuentos=this.fijarNumero(hogarDescuentos, 2);
                                        restoRetencion=(liq.getImporte()-hogarDescuentos)*0.25;
                                        //restoRetencion=this.fijarNumero(restoRetencion, 2);
                                        importeRetencion=importeRetencion+restoRetencion;
                                        importeRetencion=this.fijarNumero(importeRetencion, 2);
                                    }
                                }
                       }
               }
           
               if(liquidoDescontable>=importeRetencion){
                   liquidoDescontable=liquidoDescontable-importeRetencion;
                   liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                   liquidoFinal=liquidoFinal-importeRetencion;
                   liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                   descuentoRechazado=0;
                   descuentoAplicado=this.fijarNumero(importeRetencion, 2);                           
               }else{
                   descuentoRechazado=importeRetencion-liquidoDescontable;
                   descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                   descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                   liquidoFinal=liquidoFinal-descuentoAplicado;
                   liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                   liquidoDescontable=0;
               }
               
               
               
               
               
          }
          //INSERTO
          c=this.buscarCodigo(310);
          l = new Liquidacion(k,1,c,1.0,this.fijarNumero(importeRetencion, 2),this.fijarNumero(importeRetencion, 2),fecha,0);
          contador+=this.insertoLiquidacion(l);
         this.liquidacionesMem.add(l);
     }
      
      //APLICO CODIGOS DE DESCUENTOS
      liquidaciones =this.cargoDescuentos(k);
      this.cargoCodigos();
      this.reordeno();
      int r=1;
      for(Liquidacion s:liquidaciones){
          if(s.getCodigo().getCod()==461){
              int tt=0;
          }
          r+=this.actualizoOrdenLiq(s,r,k);
          contador++;
          if(s.getCod()==401){//vales
              if(s.getImporte()>liquidoMinimo){
                  restoVale=s.getImporte()-liquidoMinimo;
                  restoVale=this.fijarNumero(restoVale, 2);
                  importeADescontar=this.fijarNumero(liquidoMinimo, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  restoVale=0;
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }
          }else if(s.getCod()==501){//resto de vales
              if(restoVale!=0){
                  importeADescontar=this.fijarNumero(restoVale,2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  importeADescontar=0;
              }
          }else{
              if(s.getImporte()!=0){
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }else{
                  importeADescontar=this.cargoImporteRetFija(k,s.getCod(),totalHaberesBps,liquidoLegal);
                  importeADescontar=this.fijarNumero(importeADescontar, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }
          }
          
          if(s.getCod()!=401){
              if(s.getCod()==355){
                  this.seg_ace=this.totalSeguroAce(k);
                  
                  if((liquidoDescontable+seg_ace)>=importeADescontar){
                      liquidoDescontable=(liquidoDescontable+seg_ace)-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=(liquidoFinal+seg_ace)-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{
                      if(k.getCodFunc()==10000){
                          liquidoDescontable=-47.11;
                          descuentoRechazado=importeADescontar-(liquidoDescontable+seg_ace);
                          descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                          descuentoAplicado=seg_ace+liquidoDescontable;
                          descuentoAplicado=this.fijarNumero(descuentoAplicado, 2);
                          liquidoFinal=0;
                          liquidoDescontable=0;
                      }else{
                          descuentoRechazado=importeADescontar-(liquidoDescontable+seg_ace);
                          descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                          descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                          liquidoFinal=liquidoFinal-descuentoAplicado;
                          liquidoFinal=this.fijarNumero(liquidoFinal, 0);
                          liquidoDescontable=0;
                      }
                  }
              }else{
                  //aca cambio de 5 por ciento extra de liquido minimo
                  if(liquidoDescontable>=importeADescontar){         
                      liquidoDescontable=liquidoDescontable-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=liquidoFinal-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{
                      //no alcanza para descontar pero si es codigo 332 o 410 puedo ir un 5% mas
                      if(s.getCod()==332 || s.getCod()==410){
                          if((liquidoDescontable+descontableExtra)>=importeADescontar){
                              descontableExtra=(liquidoDescontable+descontableExtra)-importeADescontar;
                              descontableExtra=this.fijarNumero(descontableExtra, 2);
                              liquidoFinal=liquidoFinal-importeADescontar;
                              liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                              descuentoRechazado=0;
                              descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                          }else{
                               descuentoRechazado=importeADescontar-(liquidoDescontable+descontableExtra);
                               descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                               descuentoAplicado=liquidoDescontable+descontableExtra;
                               descuentoAplicado=this.fijarNumero(descuentoAplicado, 2);
                               liquidoFinal=liquidoFinal-descuentoAplicado;
                               liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                               descontableExtra=0;
                          }
                          liquidoDescontable = 0;
                      }else{
                            descuentoRechazado=importeADescontar-liquidoDescontable;
                            descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                            descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                            liquidoFinal=liquidoFinal-descuentoAplicado;
                            liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                            liquidoDescontable=0;
                      }
                  }
              }
          }else{
              if(liquidoFinal>=importeADescontar){                  
                    liquidoFinal=liquidoFinal-importeADescontar;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                    descuentoRechazado=0;
                    descuentoAplicado=this.fijarNumero(importeADescontar, 2);
              }else{       
                    descuentoRechazado=importeADescontar-liquidoFinal;
                    descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                    descuentoAplicado=this.fijarNumero(liquidoFinal, 2);
                    liquidoFinal=liquidoFinal-descuentoAplicado;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
              }
          }
          
         contador+=this.actualizoLiquidacion2(k,s.getCod(),descuentoRechazado); 
      }
      
      totalDescuentos=totalHaberesGeneral-liquidoFinal;
      totalDescuentos=this.fijarNumero(totalDescuentos, 2);
      
      //calculo e inserto redondeos
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      Double val = liquidoFinal;
      int valLiqFinal = val.intValue();
      
      if((liquidoFinal-valLiqFinal)!=0){
          redondeo = 1-(liquidoFinal-valLiqFinal);
          redondeo=this.fijarNumero(redondeo, 2);
          c=this.buscarCodigo(600);
          l = new Liquidacion(k,1,c,1.0,redondeo,redondeo,fecha,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }else{
          redondeo=0;
      }
      
      liquidoFinal=liquidoFinal+redondeo;
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      totalNoRetenido=this.buscarTotalNoRetenido(k);
      
      double veinteTotalHaberesBps=this.totalHaberesBps*porcentajeSacec;
      veinteTotalHaberesBps = this.fijarNumero(veinteTotalHaberesBps, 2);
      Liquidacion li = this.obtengoLiq(461);
      if(li!=null){
      if(this.fijarNumero(li.getImporte(), 2)>veinteTotalHaberesBps){
        l.setImporte(veinteTotalHaberesBps);
        contador+=this.actualizoIngreso(k, 461, veinteTotalHaberesBps );
        cnn.commit();
        cnn.close();
        cnn=null;
        this.comienzoLiquidación(k, fecha);
       }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fecha, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
      }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fecha, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
     
     
      
      //-----------------------------------------------------------------------------------------------//
             
      
    }

    private Liquidacion obtengoLiq(Integer cod){
        int i =0;
        boolean esta=false;
        Liquidacion l=null;
        while(i<liquidacionesMem.size() && !esta){
            if(liquidacionesMem.get(i).getCodigo().getCod().equals(cod)){
                l=liquidacionesMem.get(i);
                esta=true;
            }
            i++;
        }
        return l;
    }
    
     private Liquidacion eliminoLiq(Integer cod){
        int i =0;
        boolean esta=false;
        Liquidacion l=null;
        while(i<liquidaciones.size() && !esta){
            if(liquidaciones.get(i).getCodigo().getCod().equals(cod)){
                liquidaciones.remove(liquidaciones.get(i));
                esta=true;
            }
            i++;
        }
        return l;
    }
    
    private Codigo buscarCodigo(int cod) {
        Codigo c =null;
        int i = 0;
        while(i<this.codigos.size() && c==null){
            if(codigos.get(i).getCod()==cod){
                c=codigos.get(i);
            }
            i++;
        }
        return c;
        
    }
    
    
    private void reconecto() throws SQLException, ClassNotFoundException{
        
      if(cnn!=null){
            if(cnn.isClosed()){
                cnn=Conexion.Cadena();
                cnn.setAutoCommit(false);
                contador=0;
            }
            if(contador>=800){
                cnn.commit();
                cnn.close();
                cnn=Conexion.Cadena();
                cnn.setAutoCommit(false);
                contador=0;
            }
       }
       else{
           cnn=Conexion.Cadena();
           cnn.setAutoCommit(false);
           contador=0;
       }
     
    }

    private Integer calculoAntiguedad(Date fechaIngreso, Date fechaLiq) {
        Date fechaAux;
        Integer retorno;
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(fechaIngreso.getTime());
        Integer dia = c.get(Calendar.DAY_OF_MONTH);
        Integer mes = c.get(Calendar.MONTH);
        if(dia<=7 && mes ==0){
            fechaAux=fechaIngreso;
        }
        else{
            fechaAux=new Date();
            Calendar a=Calendar.getInstance();
            a.setTimeInMillis(fechaIngreso.getTime());
            a.set(Calendar.DAY_OF_MONTH, 01);
            a.set(Calendar.MONTH, 0);
            a.set(Calendar.YEAR, a.get(Calendar.YEAR)+1);
            fechaAux.setTime(a.getTimeInMillis());
        }
        Calendar s=Calendar.getInstance();
        s.setTimeInMillis(fechaLiq.getTime());
        
        Calendar r=Calendar.getInstance();
        r.setTimeInMillis(fechaAux.getTime());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ChronoLocalDate to = ChronoLocalDate.from(formatter.parse(this.convertirFecha(fechaLiq)));
        ChronoLocalDate from = ChronoLocalDate.from(formatter.parse(this.convertirFecha(fechaAux)));
        ChronoPeriod period = ChronoPeriod.between(from, to);
        retorno =(int) (long) period.get(YEARS);
        
        if(retorno<0){
            retorno = 0;
        }
        return retorno;
    }
  
   private double redondearDecimales(double valorInicial, int numeroDecimales){
//       double parteEntera, resultado;
//        resultado = valorInicial;
//        parteEntera = Math.floor(resultado);
//        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
//        resultado=Math.round(resultado);
//        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
//        return resultado;

        double resultado;
        double parteEntera;
        resultado = valorInicial;
        Double val = resultado;
	parteEntera = val.intValue();
        
        java.math.BigDecimal ent = new java.math.BigDecimal(parteEntera);
        java.math.BigDecimal res = new java.math.BigDecimal(String.valueOf(resultado)); 
        
        BigDecimal dec = res.subtract(ent);
        
        resultado=dec.doubleValue();
        parteEntera=ent.doubleValue();
        
        resultado=resultado*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        resultado=this.fijarNumero(resultado, 2);
        return resultado;
   } 
   
//   public double fijarNumero(double numero, int digitos) {
//        double resultado;
//        resultado = numero * Math.pow(10, digitos);
//        resultado = Math.round(resultado);
//        resultado = resultado/Math.pow(10, digitos);
//        
//        resultado=this.fijarNumero2(numero, 2);
//        return resultado;
//   }
   
   
   public double fijarNumero(double numero, int digitos) {
        double resultado;
        BigDecimal num = new java.math.BigDecimal(String.valueOf(numero)); 
        BigDecimal pow = new java.math.BigDecimal(Math.pow(10, digitos));
        BigDecimal res = num.multiply(pow);
        resultado = res.doubleValue();
        resultado = Math.round(resultado);
        res=new java.math.BigDecimal(String.valueOf(resultado)); 
        res=res.divide(pow);
        resultado = res.doubleValue();
        return resultado;
   }
   
   private double fijarNumero2(double valorInicial, int numeroDecimales){
        double resultado;
        double parteEntera;
        resultado = valorInicial;
        Double val = resultado;
	parteEntera = val.intValue();
        
        java.math.BigDecimal ent = new java.math.BigDecimal(parteEntera);
        java.math.BigDecimal res = new java.math.BigDecimal(String.valueOf(resultado)); 
        
        BigDecimal dec = res.subtract(ent);
        
        resultado=dec.doubleValue();
        parteEntera=ent.doubleValue();
        
        resultado=resultado*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
   } 
    
    private Boolean esNum(String num){
        Boolean es=true;
        Integer i=0;
        if(num.equals("")){
            es=false;
        }
        while(i<num.length() && es){
            if(!Character.isDigit(num.charAt(i))){
                es=false;
            }
            i++;
        }
        return es;
    }

  private void calcularIrpfAnual(Funcionario k, Date fechaLiq, boolean normal) throws BDExcepcion {
     irpfDetallado det;
     double impIrpfTramo = 0;
     double impIrpfTotal = 0;
     double importeRetenciones = 0;
     double tasita = 0;
     Calendar c = Calendar.getInstance();
     c.setTime(fechaLiq);
     Integer anio = c.get(Calendar.YEAR);
     Integer mes = c.get(Calendar.MONTH)+1;
     
     String leyenda="";
     double importe_mensual = 0;
     double acumulado_anual = 0; // sin aguinaldo y vacacional
     double acumulado_aguivac = 0; // aguinaldos y vacacional
     double imponiblediciembre = 0; //monto imponible de irpf diciembre para usar con nucleo familiar
     double mensualaguivac = 0; // para obtener cada mes de aguinaldo y vac
     double maximopermitido = 0; //para obtener 20 de imponible de diciembre para usar en nucleo familiar
     double totalFonasa = 0;
     double totalFrl = 0;
     double valor = 0;
     double valor1 = 0;
     double valor2 = 0;
     double importeDeducciones = 0;
     double irpfRet = 0;
     double irpfDed = 0;
     double totalIrpf = 0;
     double dtoFamiliar = 0;
     double restoIrpf = 0;
     double totalAnticipado = 0;
     double tasa =0;
     Declaracion dec = this.cargoDatosCjPpu(k);
     
     for(int i=1;i<=12;i++){
         //sin aguinaldo ni salario vacacional
     importe_mensual=this.buscoImporteMensualImponibleIrpf(k,anio,i,this.hacerAjusteAnual);
     if(i==12){
         //al importe mensual  sumo los haberes de diciembre
            importe_mensual = importe_mensual + totalHaberesIrpf;
            imponiblediciembre = importe_mensual;
     }
        //'grabo el importe mensual en irpf detallado
        leyenda="MONTO SIN AGUI-VAC GANADO EN: "+i+"/"+anio;
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, importe_mensual);
        contador+=this.graboLogIrpf(det,normal);
        
        acumulado_anual = acumulado_anual + importe_mensual;
        acumulado_anual =this.fijarNumero(acumulado_anual, 2);
     
        //suma de aguinaldos y sal vac, por si los preciso despues
        mensualaguivac = buscoAguinaldoVacacionalDelAño(k, anio, i);
        acumulado_aguivac = acumulado_aguivac + mensualaguivac;
        if(i==12){
           imponiblediciembre = imponiblediciembre + mensualaguivac;
        }
     }
     
   //SI TIENE MINIMO NO IMPONIBLE SUMAR BPCS
        double exoneraMinimo=exoneraMinimoImp(k);
        if(exoneraMinimo==1){
           double complemento_nimnoimp; 
           BigDecimal doce = new BigDecimal("12");
           BigDecimal siete = new BigDecimal("7");
           BigDecimal bpc = new BigDecimal(bpcActual);
           BigDecimal complemento = siete.multiply(bpc);
           complemento = complemento.multiply(doce);
           
           complemento_nimnoimp = complemento.doubleValue();
           complemento_nimnoimp = this.fijarNumero(complemento_nimnoimp, 2);
           //grabo el importe mensual en irpf detallado
           leyenda = "COMPLEMENTO FICTICIO P/MIN. NO IMP.";
           det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, complemento_nimnoimp);
           contador+=this.graboLogIrpf(det,normal);
 
           acumulado_anual = acumulado_anual + complemento_nimnoimp;
            
        }
        leyenda = "HABERES GRAVADOS POR IRPF";
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, acumulado_anual);
        contador+=this.graboLogIrpf(det,normal);
        
        double saldoHaberes = acumulado_anual;
        int i =0;
        while(saldoHaberes>0){
             if(saldoHaberes > (this.irpfRetenciones.get(i).getDifBpcAnual()*bpcActual)){
                impIrpfTramo =(this.irpfRetenciones.get(i).getDifBpcAnual()*bpcActual)*this.irpfRetenciones.get(i).getPorcentaje();
                impIrpfTramo=this.fijarNumero(impIrpfTramo, 2);
                leyenda=String.valueOf(this.fijarNumero(this.irpfRetenciones.get(i).getDifBpcAnual()*bpcActual, 2)) + " AL "+this.irpfRetenciones.get(i).getPorcentaje();
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, impIrpfTramo);
                contador+=this.graboLogIrpf(det,normal);
                
                tasita=this.irpfRetenciones.get(i).getPorcentaje();
                impIrpfTotal=impIrpfTotal+impIrpfTramo;
                saldoHaberes=saldoHaberes-(this.irpfRetenciones.get(i).getDifBpcAnual()*bpcActual);
           }
           else{
                impIrpfTramo=saldoHaberes*this.irpfRetenciones.get(i).getPorcentaje();
                impIrpfTramo=this.fijarNumero(impIrpfTramo, 2);
                impIrpfTotal=impIrpfTotal+ impIrpfTramo;
                leyenda=String.valueOf(this.fijarNumero(saldoHaberes, 2)) + " AL "+this.irpfRetenciones.get(i).getPorcentaje();
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, impIrpfTramo);
                contador+=this.graboLogIrpf(det,normal);
                tasita=this.irpfRetenciones.get(i).getPorcentaje();
                saldoHaberes=saldoHaberes-(this.irpfRetenciones.get(i).getDifBpcAnual()*bpcActual);
           }
         i++;
        }
        
        leyenda = "TASA MAXIMA UTILIZADA: ";
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, tasita);
        contador+=this.graboLogIrpf(det,normal);
        
        importeRetenciones = impIrpfTotal;
         
        //grabar total de importeretenciones
        leyenda = "RETENCIONES IRPF ANUAL";
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, importeRetenciones);
        contador+=this.graboLogIrpf(det,normal);
        
        //SI LAS RETENCIONES DAN 0, NO SE APLICAN DEDUCCIONES
        if(importeRetenciones!=0){
            double acudedu=0;
            totDipaico=0;                       
            for(i=1;i<=12;i++){
                importe_mensual = this.buscoImporteMensualDipaico(k,i,anio,this.hacerAjusteAnual);
                if(i==12){
                    if(normal){
                        importe_mensual=importe_mensual+impDipaico;
                    }
                    else{
                        importe_mensual=importe_mensual+impDipaico1;
                     }
                }
                leyenda="DIPAICO APORTADO EN: "+i+"/"+anio;
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importe_mensual);
                contador+=this.graboLogIrpf(det,normal);
                
                acudedu=acudedu+importe_mensual;
                acudedu=this.fijarNumero(acudedu, 2);
                
                totDipaico=totDipaico+importe_mensual;
                totDipaico=this.fijarNumero(totDipaico, 2);
            }
            leyenda="TOTAL DIPAICO: "+anio;
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, totDipaico);
            contador+=this.graboLogIrpf(det,normal);
            
            importe_mensual =0;
            totalFonasa =0;
            
            for(i=1;i<=12;i++){
                importe_mensual = cargoFonasaPrevio(k, i, anio, hacerAjusteAnual);
                if(i==12){
                    //al importe mensual que sale del aguinaldo sumo los haberes de diciembre
                    if(normal){
                        importe_mensual=importe_mensual+impFonasa;
                    }
                    else{
                        importe_mensual=importe_mensual+impFonasa1;
                     }
                }
                //grabo el importe mensual en irpf detallado
                leyenda="FONASA APORTADO EN: "+i+"/"+anio;
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importe_mensual);
                contador+=this.graboLogIrpf(det,normal);
                
                //sumo al acumulado semestral
                acudedu = acudedu + importe_mensual;
                acudedu = this.fijarNumero(acudedu, 2);
                totalFonasa=totalFonasa+importe_mensual;
                totalFonasa=this.fijarNumero(totalFonasa, 2);
            }
            leyenda="TOTAL FONASA: "+anio;
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, totalFonasa);
            contador+=this.graboLogIrpf(det,normal);
            
            importe_mensual = 0;
            totalFrl = 0;
            
            for(i=1;i<=12;i++){
                importe_mensual = cargoFrlPrevio(k, i, anio, hacerAjusteAnual);
                if(i==12){
                    //al importe mensual que sale del aguinaldo sumo los haberes de diciembre
                    if(normal){
                        importe_mensual=importe_mensual+impFrl;
                    }
                    else{
                        importe_mensual=importe_mensual+impFrl1;
                     }
                }
                //grabo el importe mensual en irpf detallado
                leyenda="F.R.L. APORTADO EN: "+i+"/"+anio;
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importe_mensual);
                contador+=this.graboLogIrpf(det,normal);
                
                //sumo al acumulado semestral
                acudedu = acudedu + importe_mensual;
                acudedu = this.fijarNumero(acudedu, 2);
                totalFrl=totalFrl+importe_mensual;
                totalFrl=this.fijarNumero(totalFrl, 2);
            }
            
            //grabo el importe mensual en irpf detallado
            leyenda="TOTAL F.R.L.: "+anio;
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, totalFrl);
            contador+=this.graboLogIrpf(det,normal);
            
            ArrayList<PersACargo> personas = this.cargoPersonasACargo(k);
         
         if(personas.size()>0){
             for(PersACargo p:personas) {
                 double acuHijo =0;
                 Date vigencia = buscoVigenciaDeclaracion(k,anio,fechaLiq);
                 
                    for(i=1;i<=12;i++){
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, 01);
                        cal.set(Calendar.MONTH, i-1);
                        cal.set(Calendar.YEAR, anio);
                        Date fechaAnalizar = cal.getTime();
                        
                        fechaAnalizar = this.buscoUltimoDiaMes(fechaAnalizar);
                        
                        Calendar calAn = Calendar.getInstance();
                        calAn.setTime(fechaAnalizar);
                        int mesAn = calAn.get(Calendar.MONTH)+1;
                        int anioAn = calAn.get(Calendar.YEAR);
                        String agrega=mesAn+"-"+anioAn;
                        
                        if(p.getRelacion()=='H'){
                            if(p.getDiscapacidad()==1){//hijo con discapacidad
                                Integer meses = this.calculoCuantosMesesHijo(vigencia,p.getFechaNac(),0,fechaAnalizar,k);
                                if(p.getPjeDist()==100){
                                     valor=((26*bpcActual)/12)*meses;
                                     //ESTE REDONDEO SE AGREGA RESPECTO DEL SISTEMA ANTERIOR PARA SUBSANAR DIFERENCIAS CON BPS
                                     //valor = Math.round(valor);
                                     valor=this.fijarNumero(valor, 2);
                                     acuHijo=acuHijo+valor;
                                     if(valor!=0){
                                        leyenda="HIJO CON DISCAPACIDAD - 100% * "+agrega;
                                        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                        contador+=this.graboLogIrpf(det,normal);
                                     }
                                }else{
                                     valor=(((26*bpcActual)/12)/2)*meses;
                                      //ESTE REDONDEO SE AGREGA RESPECTO DEL SISTEMA ANTERIOR PARA SUBSANAR DIFERENCIAS CON BPS
                                     //valor = Math.round(valor);
                                     valor=this.fijarNumero(valor, 2);
                                     acuHijo=acuHijo+valor;
                                     if(valor!=0){
                                        leyenda="HIJO CON DISCAPACIDAD - 50% * "+agrega;
                                        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                        contador+=this.graboLogIrpf(det,normal);
                                     }
                                }
                            }else{ //hijo sin discapacidad
                                Integer meses = this.calculoCuantosMesesHijo(vigencia,p.getFechaNac(),1,fechaAnalizar,k);
                                Date fechaAux = fechaAnalizar;
                                Integer edad = this.calculoEdad(p.getFechaNac(), fechaAux);
                                if(edad<18){
                                    if(p.getPjeDist()==100){
                                        valor=((13*bpcActual)/12)*meses;
                                         //ESTE REDONDEO SE AGREGA RESPECTO DEL SISTEMA ANTERIOR PARA SUBSANAR DIFERENCIAS CON BPS
                                       // valor = Math.round(valor);
                                        valor=this.fijarNumero(valor, 2);
                                        acuHijo=acuHijo+valor;
                                        if(valor!=0){
                                            leyenda="HIJO - 100% * "+agrega;
                                            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                            contador+=this.graboLogIrpf(det,normal);
                                        }
                                    }else{
                                        valor=(((13*bpcActual)/12)/2)*meses;
                                        //ESTE REDONDEO SE AGREGA RESPECTO DEL SISTEMA ANTERIOR PARA SUBSANAR DIFERENCIAS CON BPS
                                       // valor = Math.round(valor);
                                        valor=this.fijarNumero(valor, 2);
                                        acuHijo=acuHijo+valor;
                                        if(valor!=0){
                                            leyenda="HIJO - 50% * "+agrega;
                                            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                            contador+=this.graboLogIrpf(det,normal);
                                        }
                                    }
                                }else{
                                    
                                        leyenda="HIJO CUMPLIO MAYORIA EDAD * "+agrega;
                                        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, 0);
                                        contador+=this.graboLogIrpf(det,normal);
                                    
                                }
                            }
                        }else if(p.getRelacion()=='C' || p.getRelacion()=='T'){
                            Integer meses = this.calculoCuantosMesesHijo(vigencia,p.getFechaNac(),0,fechaAnalizar,k);
                            if(p.getPjeDist()==100){
                                valor=((26*bpcActual)/12)*meses;
                                 //ESTE REDONDEO SE AGREGA RESPECTO DEL SISTEMA ANTERIOR PARA SUBSANAR DIFERENCIAS CON BPS
                               // valor = Math.round(valor);
                                valor=this.fijarNumero(valor, 2);
                                acuHijo=acuHijo+valor;
                                 if(valor!=0){
                                        leyenda="HIJO TUTELA/CURATELA 100% * "+agrega;
                                        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                        contador+=this.graboLogIrpf(det,normal);
                                  }
                            }else{
                                valor=(((26*bpcActual)/12)/2)*meses;
                                 //ESTE REDONDEO SE AGREGA RESPECTO DEL SISTEMA ANTERIOR PARA SUBSANAR DIFERENCIAS CON BPS
                                //valor = Math.round(valor);
                                valor=this.fijarNumero(valor, 2);
                                acuHijo=acuHijo+valor;
                                if(valor!=0){
                                    leyenda="HIJO TUTELA/CURATELA 50% * "+agrega;
                                    det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                    contador+=this.graboLogIrpf(det,normal);
                                }
                            }
                        }
                    }
                    acudedu = acudedu+acuHijo;
                    if(acuHijo!=0){
                        leyenda="HIJO";
                        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, acuHijo);
                        contador+=this.graboLogIrpf(det,normal);
                    }
                 }
             }
         
         if(dec!=null){
         if(dec.getCatcjpu()!=0){
             if(k.getCodFunc()==1300){
                 valor1=this.fijarNumero(62640, 2);
                 acudedu = acudedu + valor1 + valor2;
                 if(valor!=0){
                     leyenda="CJPPU-REINT AP. CAT 4 $10440 * 6 MESES";
                     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor1);
                     contador+=this.graboLogIrpf(det,normal);
                 }
             }
         }
         if(!"0".equals(dec.getFondosolcjpu())){
             int pos = dec.getFondosolcjpu().indexOf('/');
             String a = dec.getFondosolcjpu().substring(0, pos);
             String b = dec.getFondosolcjpu().substring(pos+1,dec.getFondosolcjpu().length());
             int arriba = Integer.parseInt(a);
             int abajo = Integer.parseInt(b);
             valor=(arriba/abajo)*bpcActual;
             valor=this.fijarNumero(valor, 0);
             Date vigencia = buscoVigenciaDeclaracion(k,anio,fechaLiq);
             Integer meses = this.calculoCuantosMeses(vigencia, fechaLiq);
             valor=valor/12*meses;
             valor=this.fijarNumero(valor, 2);
             acudedu = acudedu + valor;
             leyenda="CJPPU-FONDO SOL. "+dec.getFondosolcjpu()+" BPC * "+meses+" MESES";
             
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
             contador+=this.graboLogIrpf(det,normal);
         }
         
         int adic=dec.getAdicionalcjpu();
         if(adic!=0){
             Date vigencia = buscoVigenciaDeclaracion(k,anio,fechaLiq);
             Integer meses = this.calculoCuantosMeses(vigencia, fechaLiq);
             
             int pos = cuantasBPC.indexOf('/');
             String a = cuantasBPC.substring(0, pos);
             String b = cuantasBPC.substring(pos+1,cuantasBPC.length());
             double arr = Double.parseDouble(a);
             double aba = Double.parseDouble(b);
             valor = arr/aba;
             valor=valor*bpcActual;
             valor=this.fijarNumero(valor, 0);
             valor=valor/12;
             valor=this.fijarNumero(valor, 2);
             valor=valor*meses;
             
             valor=this.fijarNumero(valor, 2);

             acudedu = acudedu + valor;
             if(valor!=0){
             leyenda="ADICIONAL FONDO SOL. * "+meses+" MESES";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
             contador+=this.graboLogIrpf(det,normal);
             }
         }
         }
         saldoHaberes=acudedu;
         saldoHaberes=this.fijarNumero(saldoHaberes, 2);
         leyenda="MONTO DEDUCCIONES A PROCESAR";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, saldoHaberes);
         contador+=this.graboLogIrpf(det,normal);
         
         if(acumulado_anual<=this.fijarNumero((180*bpcActual), 2)){
             impIrpfTotal=saldoHaberes*0.1;
             impIrpfTotal=this.fijarNumero(impIrpfTotal, 2);
             leyenda=this.fijarNumero(saldoHaberes, 2) + "AL 10%";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impIrpfTotal);
             contador+=this.graboLogIrpf(det,normal);
         }else{
             impIrpfTotal=saldoHaberes*0.08;
             impIrpfTotal=this.fijarNumero(impIrpfTotal, 2);
             leyenda=this.fijarNumero(saldoHaberes, 2) + "AL 8%";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impIrpfTotal);
             contador+=this.graboLogIrpf(det,normal);
         }
             importeDeducciones=impIrpfTotal;
        }else{
            importeDeducciones=0;
        }
        
        irpfRet=importeRetenciones;
        
        if(importeDeducciones>importeRetenciones){
            //grabar el importe de deducciones calculado
            leyenda="DEDUCCIONES IRPF CALCULADO";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importeDeducciones);
            contador+=this.graboLogIrpf(det,normal);
            
            //grabar el importe deducciones reducido
            leyenda="DEDUCCIONES IRPF NO CONSIDERADO";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importeDeducciones-importeRetenciones);
            contador+=this.graboLogIrpf(det,normal);
            
            importeDeducciones=importeRetenciones;
        }
        
        irpfDed=importeDeducciones;
        leyenda="TOTAL DE DEDUCCIONES IRPF";
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importeDeducciones);
        contador+=this.graboLogIrpf(det,normal);
        
        totalIrpf= importeRetenciones - importeDeducciones;
        leyenda="TOTAL NETO IRPF ANUAL";
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, totalIrpf);
        contador+=this.graboLogIrpf(det,normal);
        
        if(totalIrpf<=0){
            if(totalIrpf<0){
                totalIrpf=0;
                leyenda="IRPF NETO ANUAL MODIFICADO";
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, totalIrpf);
                contador+=this.graboLogIrpf(det,normal);
            }
            leyenda="IRPF NETO ANUAL - NO AGUI. VAC.";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, 0);
            contador+=this.graboLogIrpf(det,normal);
        }else{
            leyenda="IMPONIBLE AGUI. Y VAC.";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, acumulado_aguivac);
            contador+=this.graboLogIrpf(det,normal);
            
            restoIrpf=acumulado_aguivac*tasita;
            restoIrpf=this.fijarNumero(restoIrpf, 2);
            leyenda="IMPUESTO SOBRE AGUI. Y VAC.";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, restoIrpf);
            contador+=this.graboLogIrpf(det,normal);
            
            totalIrpf = totalIrpf + restoIrpf;
            leyenda="TOTAL NETO IRPF ANUAL";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, totalIrpf);
            contador+=this.graboLogIrpf(det,normal);      
            
            if(dec!=null){
                if(dec.getFamiliar()!=null){
                     if(dec.getFamiliar()==1){
                           //poner dto de 5%
                           dtoFamiliar=totalIrpf*0.05;
                           dtoFamiliar=this.fijarNumero(dtoFamiliar, 2);
                           maximopermitido=imponiblediciembre*0.2;
                           maximopermitido=this.fijarNumero(maximopermitido, 2);
                           
                           if(dtoFamiliar<=maximopermitido){
                               leyenda="NUCLEO FAMILIAR";
                               det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, dtoFamiliar);
                               contador+=this.graboLogIrpf(det,normal);     
                               
                               totalIrpf=totalIrpf-dtoFamiliar;
                               totalIrpf=this.fijarNumero(totalIrpf, 2);
                           }else{
                               leyenda="NUCLEO FAMILIAR TOPEADO";
                               det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, maximopermitido);
                               contador+=this.graboLogIrpf(det,normal);  
                               totalIrpf=totalIrpf-maximopermitido;
                               totalIrpf=this.fijarNumero(totalIrpf, 2);
                           }
                           
                           //grabar nuevo irpf
                           leyenda="TOTAL NETO IRPF";
                           det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, totalIrpf);
                           contador+=this.graboLogIrpf(det,normal);  
                     }
                }
            }
            importe_mensual = 0;
            totalAnticipado = 0;
             
            for(i=1;i<=12;i++){
                importe_mensual=cargoImporteMensualIrpf(k, i, anio, hacerAjusteAnual);
                if(importe_mensual!=0){
                    leyenda="IRPF ANTICIPADO EN : "+i+"/"+anio;
                    det=new irpfDetallado(k.getCodFunc(), fechaLiq, 4, anio, leyenda, importe_mensual);
                    contador+=this.graboLogIrpf(det,normal);  
                }
                
                totalAnticipado=totalAnticipado+importe_mensual;
                totalAnticipado=this.fijarNumero(totalAnticipado, 2);
            }
            
            leyenda="IRPF ANTICIPADO ANUAL";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 4, anio, leyenda, totalAnticipado);
            contador+=this.graboLogIrpf(det,normal);  
            
            totalIrpf=totalIrpf-totalAnticipado;
            totalIrpf=this.fijarNumero(totalIrpf, 2);
            leyenda="IRPF NETO ANUAL";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, totalIrpf);
            contador+=this.graboLogIrpf(det,normal);  
            
            if(totalIrpf<0){
                totalIrpf = 0;
                leyenda="IRPF NETO ANUAL MODIFICADO";
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, totalIrpf);
                contador+=this.graboLogIrpf(det,normal);  
            }
        }
        if(totalIrpf>0){
           tasa=totalIrpf/totalHaberesIrpf;
        }else{
            tasa=0;
        }
        
        irpfTasa=tasa;
        leyenda="TASA FINAL IRPF";
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, tasa);
        contador+=this.graboLogIrpf(det,normal);  
        
        irpfNeto=totalIrpf;
    }
    
    
    private void calcularIrpfMensual(Funcionario k, Date fechaLiq, boolean normal) throws BDExcepcion, ParseException {
      
     irpfDetallado det;
     double impIrpfTramo = 0;
     double impIrpfTotal = 0;
     double importeRetenciones = 0;
     double importeDeducciones = 0;
     Declaracion dec = this.cargoDatosCjPpu(k);
     Calendar c = Calendar.getInstance();
     c.setTime(fechaLiq);
     Integer anio = c.get(Calendar.YEAR);
     Integer mes = c.get(Calendar.MONTH)+1;
     String leyenda="";
     
     double importeMensual=this.buscoImporteMensualImponibleIrpf(k,anio,mes,this.hacerAjusteAnual);
     
     leyenda="HABERES PREVIOS DEL MES";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, importeMensual);
     contador+=this.graboLogIrpf(det,normal);
     
     leyenda="HABERES GRAVADOS ACTUALES";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, this.totalHaberesIrpf);
     contador+=this.graboLogIrpf(det,normal);
     
     double acumuladoMensual = this.totalHaberesIrpf+importeMensual;
     //acumuladoMensual=this.fijarNumero(acumuladoMensual, 2);
     double extraPalIrpf=0;
     if(acumuladoMensual>(10*this.bpcActual)){
         double imponibleBpsAnterior = this.buscoImponibleBpsAnterior(k, anio, mes);
         imponibleBpsAnterior = this.fijarNumero(imponibleBpsAnterior, 2);
         
         extraPalIrpf = (this.totalHaberesBps+imponibleBpsAnterior);
         BigDecimal extra = new BigDecimal(String.valueOf(extraPalIrpf));
         BigDecimal cero = new BigDecimal("0.06");
         extra = extra.multiply(cero);
         extraPalIrpf =extra.doubleValue();
         extraPalIrpf = this.fijarNumero(extraPalIrpf, 2);
     }
     leyenda="ALICUOTA POR AGUINALDO";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, extraPalIrpf);
     contador+=this.graboLogIrpf(det,normal);
    
     acumuladoMensual = acumuladoMensual + extraPalIrpf;
     
     //BUSCAR LEYENDA "LICENCIA NO IMPONIBLE"
     
     leyenda="HABERES GRAVADOS POR IRPF";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, acumuladoMensual);
     contador+=this.graboLogIrpf(det,normal);
     
     double exoneraMinimo = this.exoneraMinimoImp(k);
     if(exoneraMinimo==1 &&this.diferenciaDeFechas(fechaLiq)<0){
         double complementoMinNoImp = 7*bpcActual;
         Double val = complementoMinNoImp;
	 int result = val.intValue();
         complementoMinNoImp =result;
         leyenda="COMPLEMENTO FICTICIO P/MIN. NO IMP.";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, complementoMinNoImp);
         contador+=this.graboLogIrpf(det,normal);
         
         acumuladoMensual=acumuladoMensual+complementoMinNoImp;
         acumuladoMensual=this.fijarNumero(acumuladoMensual,2);
     }
     
     double saldoHaberes = acumuladoMensual;
     int i =0;
     while(saldoHaberes>0){
       
           if(saldoHaberes > (this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual)){
                impIrpfTramo =(this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual)*this.irpfRetenciones.get(i).getPorcentaje();
                impIrpfTramo=this.fijarNumero(impIrpfTramo, 2);
                leyenda=String.valueOf(this.fijarNumero(this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual, 2)) + " AL "+this.irpfRetenciones.get(i).getPorcentaje();
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, impIrpfTramo);
                contador+=this.graboLogIrpf(det,normal);
                
                impIrpfTotal=impIrpfTotal+impIrpfTramo;
                saldoHaberes=saldoHaberes-(this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual);
           }
           else{
                impIrpfTramo=saldoHaberes*this.irpfRetenciones.get(i).getPorcentaje();
                impIrpfTramo=this.fijarNumero(impIrpfTramo, 2);
                impIrpfTotal=impIrpfTotal+ impIrpfTramo;
                leyenda=String.valueOf(this.fijarNumero(saldoHaberes, 2)) + " AL "+this.irpfRetenciones.get(i).getPorcentaje();
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, impIrpfTramo);
                contador+=this.graboLogIrpf(det,normal);
                saldoHaberes=saldoHaberes-(this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual);
           }
         i++;
     }
     
   
     importeRetenciones=impIrpfTotal;
     leyenda="TOTAL DE RETENCIONES IRPF";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, importeRetenciones);
     contador+=this.graboLogIrpf(det,normal);
     
     //SI LAS RETENCIONES DAN 0 NO SE APLICA DEDUCCIONES
     
     if(importeRetenciones!=0){
         double acudedu=0;
         double dipaicoPrevio = this.buscoImporteMensualDipaico(k,mes,anio,this.hacerAjusteAnual);
         dipaicoPrevio=this.fijarNumero(dipaicoPrevio, 2);
         if(dipaicoPrevio!=0){
             leyenda="DIPAICO PREVIO";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, dipaicoPrevio);
             contador+=this.graboLogIrpf(det,normal);
         }
         if(normal){
            acudedu = acudedu + impDipaico + dipaicoPrevio;
            leyenda="DIPAICO";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impDipaico);
            contador+=this.graboLogIrpf(det,normal);
         }else{
            acudedu = acudedu + impDipaico1 + dipaicoPrevio;
            leyenda="DIPAICO";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impDipaico1);
            contador+=this.graboLogIrpf(det,normal);
         }
         double casenpacePrevio = this.buscoCasenpasePrevio(k,mes,anio,this.hacerAjusteAnual);
         casenpacePrevio=this.fijarNumero(casenpacePrevio, 2);
         if(casenpacePrevio!=0){
             leyenda="CASENPACE PREVIO";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, casenpacePrevio);
             contador+=this.graboLogIrpf(det,normal);
         }
         
         acudedu = acudedu+casenpacePrevio; //carga imp disse e inserta pero no se usa
         
         double fonasaPrevio = this.cargoFonasaPrevio(k,mes,anio,this.hacerAjusteAnual);
         fonasaPrevio=this.fijarNumero(fonasaPrevio, 2);
         if(fonasaPrevio!=0){
             leyenda="FONASA PREVIO";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, fonasaPrevio);
             contador+=this.graboLogIrpf(det,normal);
         }
         
         if(normal){
            acudedu = acudedu+impFonasa+fonasaPrevio;
            leyenda="FONASA";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impFonasa);
            contador+=this.graboLogIrpf(det,normal);
         }else{
            acudedu = acudedu+impFonasa1+fonasaPrevio;
            leyenda="FONASA";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impFonasa1);
            contador+=this.graboLogIrpf(det,normal);
         }
         double frlPrevio = this.cargoFrlPrevio(k,mes,anio,this.hacerAjusteAnual);
         frlPrevio=this.fijarNumero(frlPrevio, 2);
         if(frlPrevio!=0){
             leyenda="FRL PREVIO";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, frlPrevio);
             contador+=this.graboLogIrpf(det,normal);
         }
         if(normal){
            acudedu=acudedu+frlPrevio+impFrl;
            leyenda="FRL";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impFrl);
            contador+=this.graboLogIrpf(det,normal);
         }else{
            acudedu=acudedu+frlPrevio+impFrl1;
            leyenda="FRL";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impFrl1);
            contador+=this.graboLogIrpf(det,normal);
         }
         //----------------------//
         ArrayList<PersACargo> personas = this.cargoPersonasACargo(k);
         
         if(personas.size()>0){
             for(PersACargo p:personas) {
                 double valor=0;
                 if(p.getRelacion()=='H'){//HIJO
                     if(p.getDiscapacidad()==1){//HIJO CON DISCAPACIDAD
                         if(p.getPjeDist()==100){
                             valor=((26*bpcActual)/12)+0.01;
                             valor = Math.round(valor);
                             valor=this.fijarNumero(valor, 2);
                             acudedu = acudedu+valor;
                             leyenda="HIJO CON DISCAPACIDAD - 100%";
                             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                             contador+=this.graboLogIrpf(det,normal);
                         }else{
                             valor=(((26*bpcActual)/12)/2)+0.01;
                             valor = Math.round(valor);
                             valor=this.fijarNumero(valor, 2);
                             acudedu = acudedu+valor;
                             leyenda="HIJO CON DISCAPACIDAD - 50%";
                             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                             contador+=this.graboLogIrpf(det,normal);
                         }
                     }else{//HIJO SIN DISCAPACIDAD
                         Integer edad = this.calculoEdad(p.getFechaNac(),fechaLiq);
                         if(edad<18){
                             if(p.getPjeDist()==100){
                                valor=((13*bpcActual)/12)+0.01;
                                valor = Math.round(valor);
                                valor=this.fijarNumero(valor, 2);
                                acudedu = acudedu+valor;
                                leyenda="HIJO - 100%";
                                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                contador+=this.graboLogIrpf(det,normal);
                             }else{
                                valor=(((13*bpcActual)/12)/2)+0.01;
                                valor = Math.round(valor);
                                valor=this.fijarNumero(valor, 2);
                                acudedu = acudedu+valor;
                                leyenda="HIJO - 50%";
                                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                contador+=this.graboLogIrpf(det,normal);
                             }
                         }else if(edad==18){
                             leyenda="HIJO CUMPLIO MAYORIA EDAD";
                             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, 0);
                             contador+=this.graboLogIrpf(det,normal);
                         }
                     }
                 }else if(p.getRelacion()=='C' || p.getRelacion()=='T'){
                     if(p.getPjeDist()==100){
                            valor=((26*bpcActual)/12)+0.01;
                            valor = Math.round(valor);
                            valor=this.fijarNumero(valor, 2);
                            acudedu = acudedu+valor;
                            leyenda="HIJO TUTELA/CURATELA 100%";
                            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                            contador+=this.graboLogIrpf(det,normal);
                     }else{
                         valor=(((26*bpcActual)/12)/2)+0.01;
                         valor = Math.round(valor);
                         valor=this.fijarNumero(valor, 2);
                         acudedu = acudedu+valor;
                         leyenda="HIJO TUTELA/CURATELA 50%";
                         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                         contador+=this.graboLogIrpf(det,normal);
                     }
                 }
             }
         }
          //----------------------//
          //----------------------//
        
         if(dec!=null){
             double valor=0;
             //cjppu importe reint
             if(dec.getCatcjpu()!=0){
                 valor = this.buscoImportereintParaCategoria(dec.getCatcjpu());
                 valor=this.fijarNumero(valor, 2);
                 acudedu = acudedu + valor;
                 leyenda="CJPPU-REINT. APORTES CAT. "+dec.getCatcjpu();
                 det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                 contador+=this.graboLogIrpf(det,normal);
             }
             //cjppu fondo solidaridad
             if(!"0".equals(dec.getFondosolcjpu())){
                 int pos = dec.getFondosolcjpu().indexOf('/');
                 String a = dec.getFondosolcjpu().substring(0, pos);
                 String b = dec.getFondosolcjpu().substring(pos+1,dec.getFondosolcjpu().length());
                 int arriba = Integer.parseInt(a);
                 int abajo = Integer.parseInt(b);
                 valor=(arriba/abajo)*bpcActual;
                 valor=this.fijarNumero(valor, 0);
                 valor=valor/12;
                 valor=this.fijarNumero(valor, 2);
                 acudedu = acudedu + valor;
                 leyenda="CJPPU-FONDO SOL. "+dec.getFondosolcjpu()+"BPC";
                 det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                 contador+=this.graboLogIrpf(det,normal);
              }
             //cjppu adicionarl fondo solidaridad
             int adic=dec.getAdicionalcjpu();
             if(adic!=0){
                // valor=dec.getAdicionalcjpu();
                 int pos = cuantasBPC.indexOf('/');
                 String a = cuantasBPC.substring(0, pos);
                 String b = cuantasBPC.substring(pos+1,cuantasBPC.length());
                 double arr = Double.parseDouble(a);
                 double aba = Double.parseDouble(b);
                 valor = arr/aba;
                 valor=valor*bpcActual;
                 valor=this.fijarNumero(valor, 0);
                 valor=valor/12;
                 valor=this.fijarNumero(valor, 2);
                                 
                 acudedu = acudedu + valor;
                 leyenda="ADICIONAL FONDO SOL.";
                 det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                 contador+=this.graboLogIrpf(det,normal);
             }
         }
         
         //----------------------//
         saldoHaberes = acudedu;
         //saldoHaberes = this.fijarNumero(saldoHaberes, 2);
         leyenda="MONTO DEDUCCIONES A PROCESAR";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, saldoHaberes);
         contador+=this.graboLogIrpf(det,normal);
         
         Double val = 15*bpcActual;
         val=this.fijarNumero(val, 2);
	 int result = val.intValue();
         
         if(this.totalHaberesIrpf<=(result)){
             impIrpfTotal=saldoHaberes*0.1;
             impIrpfTotal=this.fijarNumero(impIrpfTotal, 2);
             leyenda=saldoHaberes +" AL 10%";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impIrpfTotal);
             contador+=this.graboLogIrpf(det,normal);
         }
         else{
             impIrpfTotal=saldoHaberes*0.08;
             impIrpfTotal=this.fijarNumero(impIrpfTotal, 2);
             leyenda=saldoHaberes +" AL 8%";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impIrpfTotal);
             contador+=this.graboLogIrpf(det,normal);
         }
         
         importeDeducciones = impIrpfTotal;
         
     }else{
          importeDeducciones = 0;
     }
     double irpfRet = importeRetenciones;
     
     if(importeDeducciones>importeRetenciones){
         //grabar el importe de deducciones calculado
         leyenda="DEDUCCIONES IRPF CALCULADO";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importeDeducciones);
         contador+=this.graboLogIrpf(det,normal);
         //grabar el importe deducciones reducido
         leyenda="DEDUCCIONES IRPF NO CONSIDERADO";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, (importeDeducciones-importeRetenciones));
         contador+=this.graboLogIrpf(det,normal);
         
         importeDeducciones = importeRetenciones;
      }
     
     double irpfDed = importeDeducciones;
     //grabar total de importeretenciones
     leyenda="TOTAL DE DEDUCCIONES IRPF";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importeDeducciones);
     contador+=this.graboLogIrpf(det,normal);
    
     double totalIrpf = importeRetenciones-importeDeducciones;
     totalIrpf=this.fijarNumero(totalIrpf, 2);
     
     leyenda="TOTAL NETO IRPF ANUAL";
     totalIrpf=this.fijarNumero(totalIrpf, 2);
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, totalIrpf);
     contador+=this.graboLogIrpf(det,normal);
     
     if(dec!=null){
        if(dec.getFamiliar()!=null){
            if(dec.getFamiliar()==1){
                //poner dto de 5%
                double dtoFamiliar=totalIrpf*0.05;
                dtoFamiliar=this.fijarNumero(dtoFamiliar, 2);
                leyenda="NUCLEO FAMILIAR";
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, dtoFamiliar);
                contador+=this.graboLogIrpf(det,normal);

                totalIrpf=totalIrpf-dtoFamiliar;
                totalIrpf=this.fijarNumero(totalIrpf, 2);
                leyenda="TOTAL NETO IRPF";
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, totalIrpf);
                contador+=this.graboLogIrpf(det,normal);
          }
        }
     }
     if(!"15/06/2012".equals(this.convertirFecha(fechaLiq))){
         importeMensual=this.cargoImporteMensualIrpf(k, mes, anio, hacerAjusteAnual);
         importeMensual=this.fijarNumero(importeMensual, 2); 
     }
     else{
         importeMensual=0;
     }
     
     if(importeMensual!=0){
         leyenda="IRPF ANTICIPADO : ";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 4, anio, leyenda, importeMensual);
         contador+=this.graboLogIrpf(det,normal);
     }
     totalIrpf=totalIrpf-importeMensual;
     totalIrpf=this.fijarNumero(totalIrpf, 2);
     leyenda="IRPF NETO";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, totalIrpf);
     contador+=this.graboLogIrpf(det,normal);
     
     if(totalIrpf<0){
        leyenda="IRPF NETO MODIFICADO";
        totalIrpf=0;
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, totalIrpf);
        contador+=this.graboLogIrpf(det,normal);
     }
     double tasa=0;
     if(totalIrpf>0){
         tasa=totalIrpf/this.totalHaberesIrpf;
         //tasa=this.fijarNumero(tasa, 2);
     }
     
        leyenda="TASA FINAL IRPF";
        this.irpfTasa=tasa;
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, tasa);
        contador+=this.graboLogIrpf(det,normal);
        this.irpfNeto=totalIrpf;
    }
    
    private void calcularIrpfMensualOld(Funcionario k, Date fechaLiq, boolean normal) throws BDExcepcion, ParseException {
      
     irpfDetallado det;
     double impIrpfTramo = 0;
     double impIrpfTotal = 0;
     double importeRetenciones = 0;
     double importeDeducciones = 0;
     Declaracion dec = this.cargoDatosCjPpu(k);
     Calendar c = Calendar.getInstance();
     c.setTime(fechaLiq);
     Integer anio = c.get(Calendar.YEAR);
     Integer mes = c.get(Calendar.MONTH)+1;
     String leyenda="";
     
     double importeMensual=this.buscoImporteMensualImponibleIrpfOld(k,anio,mes);
     
     leyenda="HABERES PREVIOS DEL MES";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, importeMensual);
     contador+=this.graboLogIrpf(det,normal);
     
     leyenda="HABERES GRABADOS ACTUALES";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, this.totalHaberesIrpf);
     contador+=this.graboLogIrpf(det,normal);
     
     double acumuladoMensual = this.totalHaberesIrpf+importeMensual;
     //acumuladoMensual=this.fijarNumero(acumuladoMensual, 2);
     double extraPalIrpf=0;
     if(acumuladoMensual>(10*this.bpcActual)){
         double imponibleBpsAnterior = this.buscoImponibleBpsAnterior(k, anio, mes);
         imponibleBpsAnterior = this.fijarNumero(imponibleBpsAnterior, 2);
         
         extraPalIrpf = (this.totalHaberesBps+imponibleBpsAnterior);
         BigDecimal extra = new BigDecimal(String.valueOf(extraPalIrpf));
         BigDecimal cero = new BigDecimal("0.06");
         extra = extra.multiply(cero);
         extraPalIrpf =extra.doubleValue();
         extraPalIrpf = this.fijarNumero(extraPalIrpf, 2);
     }
     leyenda="ALICUOTA POR AGUINALDO";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, extraPalIrpf);
     contador+=this.graboLogIrpf(det,normal);
    
     acumuladoMensual = acumuladoMensual + extraPalIrpf;
     
     //BUSCAR LEYENDA "LICENCIA NO IMPONIBLE"
     
     leyenda="HABERES GRAVADOS POR IRPF";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, acumuladoMensual);
     contador+=this.graboLogIrpf(det,normal);
     
     double exoneraMinimo = this.exoneraMinimoImp(k);
     double saldoHaberes =0;
     
     if(exoneraMinimo==1 &&this.diferenciaDeFechas(fechaLiq)<0){
     
         leyenda="EXONERA MIN. NO IMP.";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, 0);
         contador+=this.graboLogIrpf(det,normal);
    
     saldoHaberes = acumuladoMensual;
     int i =0;
        while(saldoHaberes>0){

              if(saldoHaberes > (this.irpfDeducciones.get(i).getDifBpcMensual()*bpcActual)){
                   impIrpfTramo =(this.irpfDeducciones.get(i).getDifBpcMensual()*bpcActual)*this.irpfDeducciones.get(i).getPorcentaje();
                   impIrpfTramo=this.fijarNumero(impIrpfTramo, 2);
                   leyenda=String.valueOf(this.fijarNumero(this.irpfDeducciones.get(i).getDifBpcMensual()*bpcActual, 2)) + " AL "+this.irpfDeducciones.get(i).getPorcentaje();
                   det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, impIrpfTramo);
                   contador+=this.graboLogIrpf(det,normal);

                   impIrpfTotal=impIrpfTotal+impIrpfTramo;
                   saldoHaberes=saldoHaberes-(this.irpfDeducciones.get(i).getDifBpcMensual()*bpcActual);
              }
              else{
                   impIrpfTramo=saldoHaberes*this.irpfDeducciones.get(i).getPorcentaje();
                   impIrpfTramo=this.fijarNumero(impIrpfTramo, 2);
                   impIrpfTotal=impIrpfTotal+ impIrpfTramo;
                   leyenda=String.valueOf(this.fijarNumero(saldoHaberes, 2)) + " AL "+this.irpfDeducciones.get(i).getPorcentaje();
                   det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, impIrpfTramo);
                   contador+=this.graboLogIrpf(det,normal);
                   saldoHaberes=saldoHaberes-(this.irpfDeducciones.get(i).getDifBpcMensual()*bpcActual);
              }
            i++;
        }
        importeRetenciones=impIrpfTotal;
     }else{
         saldoHaberes=acumuladoMensual;
        int i =0;
        while(saldoHaberes>0){

              if(saldoHaberes > (this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual)){
                   impIrpfTramo =(this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual)*this.irpfRetenciones.get(i).getPorcentaje();
                   impIrpfTramo=this.fijarNumero(impIrpfTramo, 2);
                   leyenda=String.valueOf(this.fijarNumero(this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual, 2)) + " AL "+this.irpfRetenciones.get(i).getPorcentaje();
                   det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, impIrpfTramo);
                   contador+=this.graboLogIrpf(det,normal);

                   impIrpfTotal=impIrpfTotal+impIrpfTramo;
                   saldoHaberes=saldoHaberes-(this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual);
              }
              else{
                   impIrpfTramo=saldoHaberes*this.irpfRetenciones.get(i).getPorcentaje();
                   impIrpfTramo=this.fijarNumero(impIrpfTramo, 2);
                   impIrpfTotal=impIrpfTotal+ impIrpfTramo;
                   leyenda=String.valueOf(this.fijarNumero(saldoHaberes, 2)) + " AL "+this.irpfRetenciones.get(i).getPorcentaje();
                   det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, impIrpfTramo);
                   contador+=this.graboLogIrpf(det,normal);
                   saldoHaberes=saldoHaberes-(this.irpfRetenciones.get(i).getDifBpcMensual()*bpcActual);
              }
            i++;
        }
        importeRetenciones=impIrpfTotal;
     }
   
     
     leyenda="TOTAL DE RETENCIONES IRPF";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 1, anio, leyenda, importeRetenciones);
     contador+=this.graboLogIrpf(det,normal);
     
     //SI LAS RETENCIONES DAN 0 NO SE APLICA DEDUCCIONES
     
     if(importeRetenciones!=0){
         double acudedu=0;
         double dipaicoPrevio = this.buscoImporteMensualDipaico(k,mes,anio,this.hacerAjusteAnual);
         dipaicoPrevio=this.fijarNumero(dipaicoPrevio, 2);
         if(dipaicoPrevio!=0){
             leyenda="DIPAICO PREVIO";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, dipaicoPrevio);
             contador+=this.graboLogIrpf(det,normal);
         }
         if(normal){
            acudedu = acudedu + impDipaico + dipaicoPrevio;
            leyenda="DIPAICO";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impDipaico);
            contador+=this.graboLogIrpf(det,normal);
         }else{
            acudedu = acudedu + impDipaico1 + dipaicoPrevio;
            leyenda="DIPAICO";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impDipaico1);
            contador+=this.graboLogIrpf(det,normal);
         }
         double casenpacePrevio = this.buscoCasenpasePrevio(k,mes,anio,this.hacerAjusteAnual);
         casenpacePrevio=this.fijarNumero(casenpacePrevio, 2);
         if(casenpacePrevio!=0){
             leyenda="CASENPACE PREVIO";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, casenpacePrevio);
             contador+=this.graboLogIrpf(det,normal);
         }
         
         acudedu = acudedu+casenpacePrevio; //carga imp disse e inserta pero no se usa
         
         double fonasaPrevio = this.cargoFonasaPrevio(k,mes,anio,this.hacerAjusteAnual);
         fonasaPrevio=this.fijarNumero(fonasaPrevio, 2);
         if(fonasaPrevio!=0){
             leyenda="FONASA PREVIO";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, fonasaPrevio);
             contador+=this.graboLogIrpf(det,normal);
         }
         
         if(normal){
            acudedu = acudedu+impFonasa+fonasaPrevio;
            leyenda="FONASA";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impFonasa);
            contador+=this.graboLogIrpf(det,normal);
         }else{
            acudedu = acudedu+impFonasa1+fonasaPrevio;
            leyenda="FONASA";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impFonasa1);
            contador+=this.graboLogIrpf(det,normal);
         }
         
         double frlPrevio = this.cargoFrlPrevio(k,mes,anio,this.hacerAjusteAnual);
         frlPrevio=this.fijarNumero(frlPrevio, 2);
         if(frlPrevio!=0){
             leyenda="FRL PREVIO";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, frlPrevio);
             contador+=this.graboLogIrpf(det,normal);
         }
         if(normal){
            acudedu=acudedu+frlPrevio+impFrl;
            leyenda="FRL";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impFrl);
            contador+=this.graboLogIrpf(det,normal);
         }else{
            acudedu=acudedu+frlPrevio+impFrl1;
            leyenda="FRL";
            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impFrl1);
            contador+=this.graboLogIrpf(det,normal);
         }
         //----------------------//
         ArrayList<PersACargo> personas = this.cargoPersonasACargo(k);
         
         if(personas.size()>0){
             for(PersACargo p:personas) {
                 double valor=0;
                 if(p.getRelacion()=='H'){//HIJO
                     if(p.getDiscapacidad()==1){//HIJO CON DISCAPACIDAD
                         if(p.getPjeDist()==100){
                             valor=((26*bpcActual)/12)+0.01;
                             valor = Math.round(valor);
                             valor=this.fijarNumero(valor, 2);
                             acudedu = acudedu+valor;
                             leyenda="HIJO CON DISCAPACIDAD - 100%";
                             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                             contador+=this.graboLogIrpf(det,normal);
                         }else{
                             valor=(((26*bpcActual)/12)/2)+0.01;
                             valor = Math.round(valor);
                             valor=this.fijarNumero(valor, 2);
                             acudedu = acudedu+valor;
                             leyenda="HIJO CON DISCAPACIDAD - 50%";
                             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                             contador+=this.graboLogIrpf(det,normal);
                         }
                     }else{//HIJO SIN DISCAPACIDAD
                         Integer edad = this.calculoEdad(p.getFechaNac(),fechaLiq);
                         if(edad<18){
                             if(p.getPjeDist()==100){
                                valor=((13*bpcActual)/12)+0.01;
                                valor = Math.round(valor);
                                valor=this.fijarNumero(valor, 2);
                                acudedu = acudedu+valor;
                                leyenda="HIJO - 100%";
                                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                contador+=this.graboLogIrpf(det,normal);
                             }else{
                                valor=(((13*bpcActual)/12)/2)+0.01;
                                valor = Math.round(valor);
                                valor=this.fijarNumero(valor, 2);
                                acudedu = acudedu+valor;
                                leyenda="HIJO - 50%";
                                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                                contador+=this.graboLogIrpf(det,normal);
                             }
                         }else{
                             leyenda="HIJO CUMPLIO MAYORIA EDAD";
                             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, 0);
                             contador+=this.graboLogIrpf(det,normal);
                         }
                     }
                 }else if(p.getRelacion()=='C' || p.getRelacion()=='T'){
                     if(p.getPjeDist()==100){
                            valor=((26*bpcActual)/12)+0.01;
                            valor = Math.round(valor);
                            valor=this.fijarNumero(valor, 2);
                            acudedu = acudedu+valor;
                            leyenda="HIJO TUTELA/CURATELA 100%";
                            det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                            contador+=this.graboLogIrpf(det,normal);
                     }else{
                         valor=(((26*bpcActual)/12)/2)+0.01;
                         valor = Math.round(valor);
                         valor=this.fijarNumero(valor, 2);
                         acudedu = acudedu+valor;
                         leyenda="HIJO TUTELA/CURATELA 50%";
                         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                         contador+=this.graboLogIrpf(det,normal);
                     }
                 }
             }
         }
          //----------------------//
          //----------------------//
        
         if(dec!=null){
             double valor=0;
             //cjppu importe reint
             if(dec.getCatcjpu()!=0){
                 valor = this.buscoImportereintParaCategoria(dec.getCatcjpu());
                 valor=this.fijarNumero(valor, 2);
                 acudedu = acudedu + valor;
                 leyenda="CJPPU-REINT. APORTES CAT. "+dec.getCatcjpu();
                 det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                 contador+=this.graboLogIrpf(det,normal);
             }
             //cjppu fondo solidaridad
             if(!"0".equals(dec.getFondosolcjpu())){
                 int pos = dec.getFondosolcjpu().indexOf('/');
                 String a = dec.getFondosolcjpu().substring(0, pos);
                 String b = dec.getFondosolcjpu().substring(pos+1,dec.getFondosolcjpu().length());
                 int arriba = Integer.parseInt(a);
                 int abajo = Integer.parseInt(b);
                 valor=(arriba/abajo)*bpcActual;
                 valor=this.fijarNumero(valor, 0);
                 valor=valor/12;
                 valor=this.fijarNumero(valor, 2);
                 acudedu = acudedu + valor;
                 leyenda="CJPPU-FONDO SOL. "+dec.getFondosolcjpu()+"BPC";
                 det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                 contador+=this.graboLogIrpf(det,normal);
              }
             //cjppu adicionarl fondo solidaridad
             int adic=dec.getAdicionalcjpu();
             if(adic!=0){
                // valor=dec.getAdicionalcjpu();
                 int pos = cuantasBPC.indexOf('/');
                 String a = cuantasBPC.substring(0, pos);
                 String b = cuantasBPC.substring(pos+1,cuantasBPC.length());
                 double arr = Double.parseDouble(a);
                 double aba = Double.parseDouble(b);
                 valor = arr/aba;
                 valor=valor*bpcActual;
                 valor=this.fijarNumero(valor, 0);
                 valor=valor/12;
                 valor=this.fijarNumero(valor, 2);
                                 
                 acudedu = acudedu + valor;
                 leyenda="ADICIONAL FONDO SOL.";
                 det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, valor);
                 contador+=this.graboLogIrpf(det,normal);
             }
         }
         
         //----------------------//
         saldoHaberes = acudedu;
         //saldoHaberes = this.fijarNumero(saldoHaberes, 2);
         leyenda="MONTO DEDUCCIONES A PROCESAR";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, saldoHaberes);
         contador+=this.graboLogIrpf(det,normal);
         
         Double val = 15*bpcActual;
         val=this.fijarNumero(val, 2);
	 int result = val.intValue();
         
         if(acumuladoMensual<=(result)){
             impIrpfTotal=saldoHaberes*0.1;
             impIrpfTotal=this.fijarNumero(impIrpfTotal, 2);
             leyenda=saldoHaberes +" AL 10%";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impIrpfTotal);
             contador+=this.graboLogIrpf(det,normal);
         }
         else{
             impIrpfTotal=saldoHaberes*0.08;
             impIrpfTotal=this.fijarNumero(impIrpfTotal, 2);
             leyenda=saldoHaberes +" AL 8%";
             det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, impIrpfTotal);
             contador+=this.graboLogIrpf(det,normal);
         }
         
         importeDeducciones = impIrpfTotal;
         
     }else{
          importeDeducciones = 0;
     }
     double irpfRet = importeRetenciones;
     
     if(importeDeducciones>importeRetenciones){
         //grabar el importe de deducciones calculado
         leyenda="DEDUCCIONES IRPF CALCULADO";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importeDeducciones);
         contador+=this.graboLogIrpf(det,normal);
         //grabar el importe deducciones reducido
         leyenda="DEDUCCIONES IRPF NO CONSIDERADO";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, (importeDeducciones-importeRetenciones));
         contador+=this.graboLogIrpf(det,normal);
         
         importeDeducciones = importeRetenciones;
      }
     
     double irpfDed = importeDeducciones;
     //grabar total de importeretenciones
     leyenda="TOTAL DE DEDUCCIONES IRPF";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 2, anio, leyenda, importeDeducciones);
     contador+=this.graboLogIrpf(det,normal);
    
     double totalIrpf = importeRetenciones-importeDeducciones;
     //totalIrpf=this.fijarNumero(totalIrpf, 2);
     
     leyenda="TOTAL NETO IRPF";
     totalIrpf=this.fijarNumero(totalIrpf, 2);
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, totalIrpf);
     contador+=this.graboLogIrpf(det,normal);
     
     if(dec!=null){
        if(dec.getFamiliar()!=null){
            if(dec.getFamiliar()==1){
                //poner dto de 5%
                double dtoFamiliar=totalIrpf*0.05;
                dtoFamiliar=this.fijarNumero(dtoFamiliar, 2);
                leyenda="NUCLEO FAMILIAR";
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, dtoFamiliar);
                contador+=this.graboLogIrpf(det,normal);

                totalIrpf=totalIrpf-dtoFamiliar;
                totalIrpf=this.fijarNumero(totalIrpf, 2);
                leyenda="TOTAL NETO IRPF";
                det=new irpfDetallado(k.getCodFunc(), fechaLiq, 3, anio, leyenda, totalIrpf);
                contador+=this.graboLogIrpf(det,normal);
          }
        }
     }

     
    importeMensual = 0;
    importeMensual = this.cargoImporteMensualIrpf(k, mes, anio, hacerAjusteAnual);
    

    if(importeMensual!=0){
         leyenda="IRPF ANTICIPADO : ";
         det=new irpfDetallado(k.getCodFunc(), fechaLiq, 4, anio, leyenda, importeMensual);
         contador+=this.graboLogIrpf(det,normal);
     }
     totalIrpf=totalIrpf-importeMensual;
     totalIrpf=this.fijarNumero(totalIrpf, 2);
     leyenda="IRPF NETO";
     det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, totalIrpf);
     contador+=this.graboLogIrpf(det,normal);
     
     if(totalIrpf<0){
        leyenda="IRPF NETO MODIFICADO";
        totalIrpf=0;
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, totalIrpf);
        contador+=this.graboLogIrpf(det,normal);
     }
     double tasa=0;
     if(totalIrpf>0){
         tasa=totalIrpf/this.totalHaberesIrpf;
         //tasa=this.fijarNumero(tasa, 2);
     }
     
        leyenda="TASA FINAL IRPF";
        this.irpfTasa=tasa;
        det=new irpfDetallado(k.getCodFunc(), fechaLiq, 5, anio, leyenda, tasa);
        contador+=this.graboLogIrpf(det,normal);
        this.irpfNeto=totalIrpf;
    }
    
    
    
    
    private Integer diferenciaDeFechas(Date fechaLiq) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaFinal=dateFormat.parse("30/09/2007");
        int dias=(int) ((fechaFinal.getTime()-fechaLiq.getTime())/86400000);
          return dias;

	}

    private Integer calculoEdad(Date fechaNac, Date fechaLiq) {
        Integer edad;
        
        Calendar cLiq =Calendar.getInstance();
        cLiq.setTime(fechaLiq);
        cLiq.set(Calendar.DAY_OF_MONTH,01);
        cLiq.add(Calendar.DAY_OF_MONTH, -1);
        
        Calendar cNac = Calendar.getInstance();
        cNac.setTime(fechaNac);
        
        edad = cLiq.get(Calendar.YEAR)-cNac.get(Calendar.YEAR);
        
        int mesLiq=cLiq.get(Calendar.MONTH);
        int mesNac=cNac.get(Calendar.MONTH);
        
        if(cLiq.get(Calendar.MONTH)<cNac.get(Calendar.MONTH)){
            edad = edad-1;
        }
        else{
            if(cLiq.get(Calendar.MONTH)==cNac.get(Calendar.MONTH)){
                if(cLiq.get(Calendar.DAY_OF_MONTH)<cNac.get(Calendar.DAY_OF_MONTH)){
                     edad = edad-1;
                }
            }
        }
        return edad;
    }

    private void cargoCodigos() {
        for(Liquidacion l:liquidaciones){
            l.setCodigo(this.buscarCodigo(l.getCod()));
        }
    }
      
   private void reordeno() {
        ArrayList<Liquidacion> aux2=this.obtengoGrupo(2);
        ArrayList<Liquidacion> aux3=this.obtengoGrupo(3);
        ArrayList<Liquidacion> auxTotal = new ArrayList<>();
        boolean pronto2=false;
        boolean pronto3=false;
        for(Liquidacion l: liquidaciones){
            if(null==l.getCodigo().getGrupo()){
                auxTotal.add(l);
            }else switch (l.getCodigo().getGrupo()) {
                case 2:
                    if(!pronto2){
                        for(Liquidacion l2:aux2){
                               auxTotal.add(l2);
                        }
                    }   
                    pronto2=true;
                    break;
                case 3:
                    if(!pronto3){
                        for(Liquidacion l3:aux3){
                               auxTotal.add(l3);
                        }
                    }   
                    pronto3=true;
                    break;
                default:
                    auxTotal.add(l);
                    break;
            }
        }
        liquidaciones=auxTotal;
    }

    private ArrayList<Liquidacion> obtengoGrupo(int i) {
 
        
        ArrayList<Liquidacion> ret=new ArrayList<>();
        for(Liquidacion l:liquidaciones){
            if(l.getCodigo().getGrupo()==i){
                ret.add(l);
            }
        }
        
       this.comparato = new Comparator<Liquidacion>() {
           @Override
            public int compare(Liquidacion a, Liquidacion b) {
                 int  resultado =  a.getFechaPrelacion().compareTo(b.getFechaPrelacion());
                    if ( resultado != 0 ) { return resultado; }
                                
                return resultado;
            }
        };
       
         ret.sort(comparato);
        return ret;
    }
    
     private void verificoAjusteAnual(Date fechaLiq) {
       String stFechaLiq = this.convertirFecha(fechaLiq);
       String stFechaAjuste = this.convertirFecha(this.fijaFechaAjuste(fechaLiq));
       this.hacerAjusteAnual = stFechaAjuste.equals(stFechaLiq);
    }
   
  private String convertirFecha(Date fecha){
    String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
        }
    return str;
  }
  
  private Date fijaFechaAjuste(Date date){
    Date fechaAjuste = new Date();
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(date.getTime());
    c.set(Calendar.DAY_OF_MONTH, 31);
    c.set(Calendar.MONTH, 11);
  
    fechaAjuste.setTime(c.getTimeInMillis());
    return fechaAjuste;
    }


    
    //--------------------------------------------------------------------------------------------------------//
    
    
    public double cargoParametro(String nombre)throws BDExcepcion {
       try {
            double retorno = 0.0;
            String consulta="Select VALOR from PERS_PARAMETROS where NOMBRE=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1,nombre);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                String valor=rs.getString("VALOR").trim(); 
                retorno = Double.parseDouble(valor);
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoParametroReLiq(String nombre,Date fechaLiq)throws BDExcepcion{
        try {
            double retorno = 0;
            double actual=0;
            double vieja=0;
            String consulta="Select VALOR from PERS_PARAMETROS where NOMBRE=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1,nombre);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                String valor=rs.getString("VALOR").trim(); 
                actual = Double.parseDouble(valor);
                actual=this.fijarNumero(actual, 2);
            }
            
            consulta="Select VALOR from PERS_HIST_PARAMETROS where NOMBRE=? and fechaliq=?";
            ps=cnn.prepareStatement(consulta);
            ps.setString(1,nombre);
            ps.setString(2, this.convertirFecha(fechaLiq)); 
            rs=ps.executeQuery();
            if(rs.next()) {
                String valor=rs.getString("VALOR").trim(); 
                vieja = Double.parseDouble(valor);
                vieja=this.fijarNumero(vieja, 2);
            }
            retorno=actual-vieja;
            
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public double cargoParametroReLiqDos(String nombre,Date fechaLiq)throws BDExcepcion{
        try {
            double retorno = 0;
      
            String consulta="Select VALOR from PERS_HIST_PARAMETROS where NOMBRE=? and fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1,nombre);
            ps.setString(2, this.convertirFecha(fechaLiq)); 
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                String valor=rs.getString("VALOR").trim(); 
                retorno = Double.parseDouble(valor);
                
            }
                         
            
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public void cargoBpcActual() throws BDExcepcion {
        try {
            String consulta="Select * from PERS_VALORES_BPC order by fecha desc";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                String valor=rs.getString("VALOR").trim(); 
                bpcActual = Double.parseDouble(valor);
            }
            ps.close();
            rs.close();
           
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public void cargoBpcActualFecha(Date fechaLiq) throws BDExcepcion, ParseException{
         try {
            boolean encontre=false;
            String consulta="Select * from PERS_VALORES_BPC order by fecha";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            while(rs.next()&& !encontre) {
                if(this.diferenciaDeFechas(fechaLiq, rs.getDate("Fecha"))<=0){
                    String valor=rs.getString("VALOR").trim(); 
                    bpcActualFecha = Double.parseDouble(valor);
                    this.bpcActualFecha=this.fijarNumero(this.bpcActualFecha, 2);
                }else{
                    encontre=true;
                }
            }
            ps.close();
            rs.close();
           
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    private Integer diferenciaDeFechas(Date fechaLiq, Date fecha) throws ParseException{
        int dias=(int) ((fecha.getTime()-fechaLiq.getTime())/86400000);
        return dias;

	}

    public void cargoIrpfRetenciones()throws BDExcepcion {
       try {
            irpfRetenciones = new ArrayList<>();
            String consulta="Select * from PERS_IRPF_RETENCIONES order by HASTABPCANUAL";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                Irpf i = new Irpf();
                i.setHastaBpcAnual(rs.getDouble("HASTABPCANUAL"));
                i.setHastaBpcMensual(rs.getDouble("HASTABPCMENSUAL")); 
                i.setPorcentaje(rs.getDouble("PORCENTAJE"));
                i.setDifBpcAnual(rs.getDouble("DIFBPCANUAL"));
                i.setDifBpcMensual(rs.getDouble("DIFBPCMENSUAL"));
                irpfRetenciones.add(i);
            }
            ps.close();
            rs.close();
            
           } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
      public void cargoIrpfRetencionesOld(Date fechaLiq)throws BDExcepcion {
       try {
            irpfRetenciones = new ArrayList<>();
            String consulta="Select * from PERS_hist_IRPF_RETENCIONES where fechaLiq=? order by HASTABPCANUAL";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, this.convertirFecha(fechaLiq));
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                Irpf i = new Irpf();
                i.setHastaBpcAnual(rs.getDouble("HASTABPCANUAL"));
                i.setHastaBpcMensual(rs.getDouble("HASTABPCMENSUAL")); 
                i.setPorcentaje(rs.getDouble("PORCENTAJE"));
                i.setDifBpcAnual(rs.getDouble("DIFBPCANUAL"));
                i.setDifBpcMensual(rs.getDouble("DIFBPCMENSUAL"));
                irpfRetenciones.add(i);
            }
            ps.close();
            rs.close();
            
           } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public void cargoIrpfDeducciones() throws BDExcepcion{
        try {
            irpfDeducciones = new ArrayList<>();
            String consulta="Select * from PERS_IRPF_DEDUCCIONES order by HASTABPCANUAL";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                Irpf i = new Irpf();
                i.setHastaBpcAnual(rs.getDouble("HASTABPCANUAL"));
                i.setHastaBpcMensual(rs.getDouble("HASTABPCMENSUAL")); 
                i.setPorcentaje(rs.getDouble("PORCENTAJE"));
                i.setDifBpcAnual(rs.getDouble("DIFBPCANUAL"));
                i.setDifBpcMensual(rs.getDouble("DIFBPCMENSUAL"));
                irpfDeducciones.add(i);
            }
            ps.close();
            rs.close();
                       
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public void cargoIrpfDeduccionesOld(Date fechaLiq) throws BDExcepcion{
        try {
            irpfDeducciones = new ArrayList<>();
            String consulta="Select * from PERS_hist_IRPF_DEDUCCIONES where fechaLiq=? order by HASTABPCANUAL";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, this.convertirFecha(fechaLiq));
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                Irpf i = new Irpf();
                i.setHastaBpcAnual(rs.getDouble("HASTABPCANUAL"));
                i.setHastaBpcMensual(rs.getDouble("HASTABPCMENSUAL")); 
                i.setPorcentaje(rs.getDouble("PORCENTAJE"));
                i.setDifBpcAnual(rs.getDouble("DIFBPCANUAL"));
                i.setDifBpcMensual(rs.getDouble("DIFBPCMENSUAL"));
                irpfDeducciones.add(i);
            }
            ps.close();
            rs.close();
                       
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public void limpioTablas(Funcionario f) throws BDExcepcion, ClassNotFoundException{
        try {
            Connection cnnn;
            cnnn=Conexion.Cadena();
            String borro ="";
            PreparedStatement ps;
            int e=0;
            if(f==null){
                borro="delete  from pers_liquidaciones";
                ps=cnnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="delete  from pers_resumen";
                ps=cnnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="delete  from pers_irpf_detallado";
                ps=cnnn.prepareStatement(borro);
                ps.executeUpdate();
                
            }
            else{
                borro="delete  from pers_liquidaciones where cod_func=?";
                ps=cnnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="delete  from pers_resumen where cod_func=?";
                ps=cnnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="delete  from pers_irpf_detallado where codfun=?";
                ps=cnnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
            }
            
            
            if(cnnn!=null){
                cnnn.close();
                ps.close();
            }
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
               
    }
    
    public ArrayList<Codigo> cargoCodigosPers()throws BDExcepcion {
  
        try {
            ArrayList<Codigo> lista=new ArrayList<>();
            Codigo c=null;
            
            String consulta="Select * from PERS_CODIGOS order by CODIGO";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                c=new Codigo();
                c.setCod(rs.getInt("CODIGO"));
                c.setDescripcion(rs.getString("DESCRIPCION").trim());
                c.setTipoValor(rs.getInt("TIPOVALOR"));
                c.setIngresoManual(this.armoBooleanos(rs.getInt("INGRESOMANUAL")));
                c.setDebe(this.armoBooleanos(rs.getInt("DEBE")));
                c.setHaber(this.armoBooleanos(rs.getInt("HABER")));
                c.setImponibleBps(this.armoBooleanos(rs.getInt("IMPONIBLEBPS")));
                c.setImponibleIrpf(this.armoBooleanos(rs.getInt("IMPONIBLEIRPF")));
                c.setAcumula(this.armoBooleanos(rs.getInt("ACUMULA")));
                c.setFijo(this.armoBooleanos(rs.getInt("FIJO")));
                c.setAlcance(rs.getDouble("ALCANCE"));
                c.setValor(rs.getInt("VALOR"));
                c.setTipo(rs.getInt("TIPOLIC"));
                c.setRetencionFija(rs.getLong("RETENCION_FIJA"));
                c.setGrupo(rs.getInt("GRUPO"));
                lista.add(c);
            }        
            ps.close();
            rs.close();
            return lista;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean armoBooleanos(int num) {
        return num != 0;
    }

    public Integer insertoLiquidacion(Liquidacion l) throws BDExcepcion{
        try {
            Integer retorno = 0;
           
            String inserto="Insert into PERS_LIQUIDACIONES(COD_FUNC,COD_MOV,CANTIDAD,VALOR_U,IMPORTE,ACUMULA,DEBE,HABER,IMPONIBLEBPS,IMPONIBLEIRPF,FECHA,IMPORTE_NO_RETENIDO,TIPOLIQ)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps=cnn.prepareStatement(inserto);
            ps.setInt(1, l.getF().getCodFunc());
            ps.setInt(2, l.getCodigo().getCod());
            ps.setDouble(3, l.getCantidad());
            ps.setDouble(4, l.getValorUnitario());
            ps.setDouble(5, l.getImporte());
            ps.setString(6, this.desarmoBooleano(l.getCodigo().isAcumula()));
            ps.setString(7, this.desarmoBooleano(l.getCodigo().isDebe()));
            ps.setString(8, this.desarmoBooleano(l.getCodigo().isHaber()));
            ps.setString(9, this.desarmoBooleano(l.getCodigo().isImponibleBps()));
            ps.setString(10, this.desarmoBooleano(l.getCodigo().isImponibleIrpf()));
            ps.setString(11,this.convertirFecha(l.getFecha()));
            ps.setDouble(12, l.getImporteNoRet());
            ps.setInt(13, l.getTipoLiq());
            retorno+=ps.executeUpdate();
            
            ps.close();
           
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private String desarmoBooleano(boolean bandera) {
        if(bandera){
            return "SI";
        }
        else{
            return "NO";
        }
    }
    
 
    public boolean tieneCodFijo(Integer codFunc,Integer cod)throws BDExcepcion {
          try {
            boolean retorno = false;
            String consulta="Select * from PERS_CODIGOS_FIJOS WHERE CODMOV=? AND CODFUNC=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,cod);
            ps.setInt(2, codFunc);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                retorno=true;
            }
            else{
                retorno=false;
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public boolean tieneCodFijoHist(Integer codFunc,Integer cod,Date fechaLiq)throws BDExcepcion {
          try {
            boolean retorno = false;
            String consulta="Select * from PERS_hist_CODIGOS_FIJOS WHERE (CODMOV=? AND CODFUNC=? AND FECHALIQ=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,cod);
            ps.setInt(2, codFunc);
            ps.setString(3, this.convertirFecha(fechaLiq));
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                retorno=true;
            }
            else{
                retorno=false;
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoQuebranto(Integer codFunc) throws BDExcepcion{
          try {
            double retorno = 0;
            String consulta="Select * from PERS_CODIGOS_FIJOS WHERE CODMOV=? AND CODFUNC=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,44);
            ps.setInt(2, codFunc);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                retorno=rs.getDouble("VALOR");
            }
            else{
                retorno=0;
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public double cargoQuebrantoOld(Integer codFunc,Date fechaLiq) throws BDExcepcion{
          try {
            double retorno = 0;
            String consulta="Select * from PERS_HIST_CODIGOS_FIJOS WHERE (CODMOV=? AND CODFUNC=? AND FECHALIQ=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,44);
            ps.setInt(2, codFunc);
            ps.setString(3, this.convertirFecha(fechaLiq));
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                retorno=rs.getDouble("VALOR");
            }
            else{
                retorno=0;
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }


    public double buscoSueldoYreduccion(Integer codFunc)throws BDExcepcion {
       try {
            double retorno = 0;
            String consulta="select cod_mov,importe from pers_liquidaciones WHERE COD_FUNC=? and (cod_mov=103 or cod_mov=1) order by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                if(rs.getInt("COD_MOV")==1){
                 retorno+=rs.getDouble("IMPORTE");
                }else{
                 retorno-=rs.getDouble("IMPORTE");
                }
            }
         
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public void cargoIngresos(Funcionario k) throws BDExcepcion{
       try {
            ingresos = new ArrayList<>();
            String consulta="select * from pers_ingresos WHERE COD_FUNC=? order by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
               Ingreso i = new Ingreso();
               i.setFecha(rs.getDate("FECHA"));  
               i.setCodFunc(rs.getInt("COD_FUNC")); 
               i.setCod(rs.getInt("COD_MOV"));
               i.setCantidad(rs.getDouble("CANTIDAD"));
               i.setImporte(rs.getDouble("IMPORTE"));
               ingresos.add(i);
            }
         
            ps.close();
            rs.close();
           
           
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public void cargoIngresosReLiq(Funcionario k) throws BDExcepcion{
       try {
            ingresos = new ArrayList<>();
            String consulta="select * from pers_ingresos WHERE (COD_FUNC=? and cod_func not in(select cod_func from pers_excepciones_reqliquidacion) ) order by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
               Ingreso i = new Ingreso();
               i.setFecha(rs.getDate("FECHA"));  
               i.setCodFunc(rs.getInt("COD_FUNC")); 
               i.setCod(rs.getInt("COD_MOV"));
               i.setCantidad(rs.getDouble("CANTIDAD"));
               i.setImporte(rs.getDouble("IMPORTE"));
               ingresos.add(i);
            }
         
            ps.close();
            rs.close();
           
           
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double enfermedadAnterior(Funcionario k, int ano)throws BDExcepcion {
         try {
            double retorno=0;
            String consulta="SELECT SUM(Cantidad) as cant FROM pers_Historico_enfermedad WHERE Cod_Func =? AND Cod_Mov = 11 and year(fecha)=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, ano);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                retorno=rs.getDouble("cant");
            }
         
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public double enfermedadAnteriorOld(Funcionario k, int ano,Date fechaLiq)throws BDExcepcion {
         try {
            double retorno=0;
            String consulta="SELECT SUM(Cantidad) as cant FROM pers_Historico_enfermedad WHERE (Cod_Func = ?) AND (Cod_Mov = 11) and (year(fecha)=?) and (fecha<?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, ano);
            ps.setString(3, this.convertirFecha(fechaLiq));
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                retorno=rs.getDouble("cant");
            }
         
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public boolean buscoReciboEnCero(Funcionario k)throws BDExcepcion {
       try {
            double total=0;
            boolean retorno =false;
            String consulta="select cod_mov,importe,debe,haber from pers_liquidaciones where ((imponiblebps='SI') and (cod_mov between 1 and 200) and (cod_mov<>42) and (cod_mov<>43) and (cod_mov<>44)and (cod_mov<>45) and (cod_mov<>2) and (cod_mov<>86) and (cod_func=?)) order by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
         
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                double s=rs.getDouble("IMPORTE");
                int cod = rs.getInt("COD_MOV");
                if("SI".equals(rs.getString("DEBE").trim())){
                    total=total-rs.getDouble("IMPORTE");
                }
                if("SI".equals(rs.getString("HABER").trim())){
                    total=total+rs.getDouble("IMPORTE");
                }
            }
            ps.close();
            rs.close();
            
            if(total<=0){
                retorno=true;
            }else{
                retorno=false;
            }
            
           
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public Liquidacion tieneHogarConstituido(Funcionario k) throws BDExcepcion {
        try {
            Liquidacion retorno = null;
            String consulta="select * from pers_liquidaciones where ((cod_mov=42) and (cod_func=?))";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
         
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                retorno = new Liquidacion();
                retorno.setCantidad(rs.getDouble("CANTIDAD"));
                retorno.setImporte(rs.getDouble("IMPORTE"));
                
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public Liquidacion antiguedad(Funcionario k)throws BDExcepcion {
      try {
            Liquidacion retorno = null;
            String consulta="select * from pers_liquidaciones where ((cod_mov=43) and (cod_func=?))";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
         
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                retorno = new Liquidacion();
                retorno.setCantidad(rs.getDouble("CANTIDAD"));
                retorno.setImporte(rs.getDouble("IMPORTE"));
                
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public Liquidacion tieneQuebranto(Funcionario k) throws BDExcepcion{
     try {
            Liquidacion retorno = null;
            String consulta="select * from pers_liquidaciones where ((cod_mov=44) and (cod_func=?))";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
         
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                retorno = new Liquidacion();
                retorno.setCantidad(rs.getDouble("CANTIDAD"));
                retorno.setImporte(rs.getDouble("IMPORTE"));
                
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public void eliminoDeLiquidacion(Funcionario k, int cod)throws BDExcepcion {
        try {
            String consulta="delete from pers_Liquidaciones where (cod_func=? and cod_mov=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, cod);
            ps.executeUpdate();
            ps.close();
         
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoTotalHaberesBps(Funcionario k)throws BDExcepcion {
        try {
            double totalHaberes=0;
            String consulta="select * from pers_liquidaciones where (cod_func=? and cod_mov<200 and imponiblebps='SI') order by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
         
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
               if(rs.getString("DEBE").trim().equals("SI")){
                   totalHaberes=this.fijarNumero(totalHaberes-rs.getDouble("IMPORTE"),2);
               }
               if(rs.getString("HABER").trim().equals("SI")){
                   totalHaberes=this.fijarNumero(totalHaberes+rs.getDouble("IMPORTE"),2);
               }
            }
         
            ps.close();
            rs.close();
            
            return totalHaberes;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public double buscoTotalHaberesBpsAguinaldoOld(Funcionario k, Date fechaLiq)throws BDExcepcion{
        try {
            double totalHaberes=0;
            String consulta="select total_haberes_bps from pers_hist_resumen where (cod_func=? and fechaliq=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setString(2, this.convertirFecha(fechaLiq));
         
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                totalHaberes=this.fijarNumero(rs.getDouble("total_haberes_bps"),2);
            }
         
            ps.close();
            rs.close();
            
            return totalHaberes;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoTotalHaberesIrpf(Funcionario k) throws BDExcepcion, ClassNotFoundException{
         try {
           
            double totalHaberes=0;
            String consulta="select * from pers_liquidaciones where (cod_func=? and cod_mov<200 and imponibleirpf='SI') order by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
         
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                double imp=rs.getDouble("IMPORTE");
                int cod = rs.getInt("COD_MOV"); 
               if(rs.getString("DEBE").trim().equals("SI")){
                   totalHaberes=this.fijarNumero(totalHaberes-rs.getDouble("IMPORTE"),2);
               }
               if(rs.getString("HABER").trim().equals("SI")){
                   totalHaberes=this.fijarNumero(totalHaberes+rs.getDouble("IMPORTE"),2);
               }
            }
           
            ps.close();
            rs.close();
            
            return totalHaberes;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoTotalHaberesGeneral(Funcionario k) throws BDExcepcion{
        try {
            double totalHaberes=0;
            String consulta="select * from pers_liquidaciones where (cod_func=? and cod_mov<200) order by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
         
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
               if(rs.getString("DEBE").trim().equals("SI")){
                   totalHaberes=this.fijarNumero(totalHaberes-rs.getDouble("IMPORTE"),2);
               }
               if(rs.getString("HABER").trim().equals("SI")){
                   totalHaberes=this.fijarNumero(totalHaberes+rs.getDouble("IMPORTE"),2);
               }
            }
         
            ps.close();
            rs.close();
            
            return totalHaberes;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double buscoImporteMensualImponibleIrpf(Funcionario k, Integer anio, Integer mes, boolean ajusteAnual)throws BDExcepcion {
       try {
        double imponibleIrpfIngreso = 0;
        PreparedStatement ps;
        ResultSet rs;
        double total=0;
        double total1;
        String consulta;
        if(ajusteAnual){
            //ANUAL NO CONSIDERA AGUINALDO NI VACACIONALES
            consulta="select sum(total_haberes_irpf) as total from pers_hist_resumen where (cod_func=? and month(fechaliq)= ? and year(fechaliq)=? AND (tipoliq=1 or tipoliq=5))";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
            rs=ps.executeQuery();
            if (rs.next()) {
                 total=rs.getDouble("total"); 
             }
            //BUSCO SI TIENE LIQUIDACIONES POR EGRESO, Y DE ESAS LIQUIDACIONES ME QUEDO CON LO QUE ES SUELDO
             consulta ="select sum(importe) as total1 from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)= ? and year(fechaliq)= ? AND (tipoliq=3) and rtrim(ucase(imponibleirpf))='SI' AND (COD_MOV=1 OR COD_MOV=6))";
             ps=cnn.prepareStatement(consulta);
             ps.setInt(1, k.getCodFunc());
             ps.setInt(2, mes);
             ps.setInt(3, anio);
             rs=ps.executeQuery();
            if (rs.next()) {
                 imponibleIrpfIngreso=rs.getDouble("total1"); 
             }
        }
        else{
            consulta="select sum(total_haberes_irpf) as total from pers_hist_resumen where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and tipoliq<>2)";
            ps=cnn.prepareStatement(consulta);
             ps.setInt(1, k.getCodFunc());
             ps.setInt(2, mes);
             ps.setInt(3, anio);
             rs=ps.executeQuery();
            if (rs.next()) {
                total=rs.getDouble("total"); 
            }
        }
        ps.close();
        rs.close();
        imponibleIrpfIngreso=this.fijarNumero(total,2)+imponibleIrpfIngreso;
        return imponibleIrpfIngreso;
       }
        catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
       
    }
    
    public double buscoImporteMensualImponibleIrpfOld(Funcionario k, Integer anio, Integer mes)throws BDExcepcion {
       try {
        double imponibleIrpfIngreso = 0;
        PreparedStatement ps;
        ResultSet rs;
        double total=0;
        String consulta;

            consulta="select sum(total_haberes_irpf) as total from pers_hist_resumen where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and tipoliq<>2)";
            ps=cnn.prepareStatement(consulta);
             ps.setInt(1, k.getCodFunc());
             ps.setInt(2, mes);
             ps.setInt(3, anio);
             rs=ps.executeQuery();
            if (rs.next()) {
                 total=rs.getDouble("total"); 
            }
        
        ps.close();
        rs.close();
        imponibleIrpfIngreso=this.fijarNumero(total,2);
        return imponibleIrpfIngreso;
       }
       catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
       }
       
    }

    public ArrayList<Integer> cargoRetencionesFijas(Funcionario k) throws BDExcepcion{
          try {
            ArrayList<Integer> retenciones = new ArrayList<>();
            
            String consulta="select * from pers_retenciones_fijas where (activa=1 and sueldo=1) and cod_func=? order by cod_mov,orden";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             retenciones.add(rs.getInt("COD_MOV"));
            }
         
            ps.close();
            rs.close();
            
            return retenciones;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public ArrayList<Integer> cargoRetencionesFijasVacacional(Funcionario k) throws BDExcepcion{
          try {
            ArrayList<Integer> retenciones = new ArrayList<>();
            
            String consulta="select * from pers_retenciones_fijas where (activa=1 and otros=1) and cod_func=? order by cod_mov,orden";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             retenciones.add(rs.getInt("COD_MOV"));
            }
         
            ps.close();
            rs.close();
            
            return retenciones;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
     
     public ArrayList<Integer> cargoRetencionesFijasVacacionalReLiq(Funcionario k,Date fechaLiq) throws BDExcepcion{
          try {
            ArrayList<Integer> retenciones = new ArrayList<>();
            
            String consulta="select * from pers_hist_retenciones_fijas where (activa=1 and otros=1 and fechaliq=?) and cod_func=? order by cod_func,cod_mov,orden";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, this.convertirFecha(fechaLiq));
            ps.setInt(2, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             retenciones.add(rs.getInt("COD_MOV"));
            }
         
            ps.close();
            rs.close();
            
            return retenciones;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public ArrayList<Integer> cargoHistRetencionesFijas(Funcionario k, Date fechaLiq) throws BDExcepcion{
          try {
            ArrayList<Integer> retenciones = new ArrayList<>();
            
            String consulta="select * from pers_hist_retenciones_fijas where (activa=1 and sueldo=1 and tipo=1 and fechaliq=?) and cod_func not in(select cod_func from pers_excepciones_reqliquidacion) order by cod_func,cod_mov,orden";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, this.convertirFecha(fechaLiq));
            ps.setInt(2, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             retenciones.add(rs.getInt("COD_MOV"));
            }
         
            ps.close();
            rs.close();
            
            return retenciones;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<Integer> cargovalesDeLiquidaciones(Funcionario k)throws BDExcepcion {
        try {
            ArrayList<Integer> vales = new ArrayList<>();
            
            String consulta="select * from pers_liquidaciones where cod_mov=401 and cod_func=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             vales.add(rs.getInt("COD_MOV"));
            }
         
            ps.close();
            rs.close();
            
            return vales;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public ArrayList<Integer> cargovalesDeReLiquidaciones(Funcionario k)throws BDExcepcion {
        try {
            ArrayList<Integer> vales = new ArrayList<>();
            
            String consulta="select * from pers_liquidaciones where (cod_mov=401) and cod_func not in(select cod_func from pers_excepciones_reqliquidacion)  order by cod_func";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             vales.add(rs.getInt("COD_MOV"));
            }
         
            ps.close();
            rs.close();
            
            return vales;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public Integer graboLogIrpf(irpfDetallado det, boolean grabo)throws BDExcepcion{
       Integer ret=0;
        if(grabo){
                       
           try {
               Integer ordinal = this.cargoOrdinal(det.getCodFunc(),det.getTipo(),det.getFecha());
               String inserto="Insert into PERS_IRPF_DETALLADO(codfun,fecha,tipo,ordinal,leyenda,importe)"+"values(?,?,?,?,?,?)";
               PreparedStatement ps=cnn.prepareStatement(inserto);
               ps.setInt(1, det.getCodFunc());
               ps.setString(2,this.convertirFecha(det.getFecha()));
               ps.setInt(3, det.getTipo());
               ps.setInt(4, ordinal);
               ps.setString(5, det.getLeyenda());
               ps.setDouble(6, det.getImporte());
               ret=ps.executeUpdate();
               ps.close();
           } catch (SQLException ex) {
               throw new BDExcepcion(ex.getMessage());
           }
      }
       return ret; 
    }
    
       private Integer cargoOrdinal(Integer codFunc, Integer tipo,Date fecha) throws BDExcepcion{
          try {
            Integer ordinal = 0;
            String consulta="select max(ordinal)as numero from pers_irpf_detallado where (codfun=? and fecha=? and tipo=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            ps.setString(2, this.convertirFecha(fecha));
            ps.setInt(3, tipo);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                if(rs.getString("numero")!=null){
                    ordinal=rs.getInt("numero")+1;
                } 
                else{
                switch (tipo) {
                    case 1:
                        ordinal=0;
                        break;
                    case 2:
                        ordinal=1;
                        break;
                    case 3:
                        ordinal=0;
                        break;
                    case 4:
                        ordinal=1;
                        break;    
                    default:
                        break;
                }
            }
            }
            else{
                switch (tipo) {
                    case 1:
                        ordinal=0;
                        break;
                    case 2:
                        ordinal=1;
                        break;
                    case 3:
                        ordinal=0;
                        break;
                    case 4:
                        ordinal=1;
                        break;    
                    default:
                        break;
                }
            }
         
            ps.close();
            rs.close();
            
            return ordinal;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double buscoImponibleBpsAnterior(Funcionario k, Integer anio, Integer mes)throws BDExcepcion {
       try {
           double retorno =0;
            
            String consulta="select sum(total_haberes_bps) as total from pers_hist_resumen where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and tipoliq<>2)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             retorno = rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

public double exoneraMinimoImp(Funcionario k)throws BDExcepcion {
      try {
           double retorno =0;
            
            String consulta="select minnoimp from pers_declaraciones where codfunc=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             retorno = rs.getDouble("minnoimp");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

public double buscoImporteMensualDipaico(Funcionario k, Integer mes, Integer anio,boolean hacerAjusteAnual)throws BDExcepcion {
      try {
           double retorno =0;
           String consulta ="";
           PreparedStatement ps;
           ResultSet rs;
           if(hacerAjusteAnual){
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=201)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
           }
           else{
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=201 and tipoliq<>2)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);   
           }
               
            rs=ps.executeQuery();
            while (rs.next()) {
             retorno = rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double buscoCasenpasePrevio(Funcionario k, Integer mes, Integer anio, boolean hacerAjusteAnual)throws BDExcepcion  {
        try {
           double retorno =0;
           String consulta ="";
           PreparedStatement ps;
           ResultSet rs;
           if(hacerAjusteAnual){
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=202)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
           }
           else{
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=202 and tipoliq<>2)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);   
           }
               
            rs=ps.executeQuery();
            while (rs.next()) {
             retorno = rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoFonasaPrevio(Funcionario k, Integer mes, Integer anio, boolean hacerAjusteAnual)throws BDExcepcion {
        try {
           double retorno =0;
           String consulta ="";
           PreparedStatement ps;
           ResultSet rs;
           if(hacerAjusteAnual){
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=205)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
           }
           else{
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=205 and tipoliq<>2)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);   
           }
               
            rs=ps.executeQuery();
            while (rs.next()) {
             retorno = rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoFrlPrevio(Funcionario k, Integer mes, Integer anio, boolean hacerAjusteAnual) throws BDExcepcion{
          try {
           double retorno =0;
           String consulta ="";
           PreparedStatement ps;
           ResultSet rs;
           if(hacerAjusteAnual){
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=204)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
           }
           else{
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=204 and tipoliq<>2)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);   
           }
               
            rs=ps.executeQuery();
            while (rs.next()) {
             retorno = rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<PersACargo> cargoPersonasACargo(Funcionario k) throws BDExcepcion{
         try {
           ArrayList<PersACargo> retorno =new ArrayList<>();
            
            String consulta="select codfunc,ordinal,fecha_nac,relacion,pjedist,discapacidad from pers_persacargo where codfunc=? order by ordinal";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             PersACargo p = new PersACargo();
             p.setCodFunc(rs.getInt("codfunc"));
             p.setOrdinal(rs.getInt("ordinal")); 
             p.setFechaNac(rs.getDate("fecha_nac"));
             p.setRelacion(rs.getString("relacion").trim().charAt(0)); 
             p.setPjeDist(rs.getInt("pjedist"));
             p.setDiscapacidad(rs.getInt("discapacidad"));
             retorno.add(p);
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public Declaracion cargoDatosCjPpu(Funcionario k)throws BDExcepcion {
        try {
            Declaracion d=null;
            
            String consulta="select catcjpu,fondosolcjpu,adicionalcjpu,familiar from pers_declaraciones where codfunc=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             d = new Declaracion();
             d.setCatcjpu(rs.getInt("catcjpu"));
             d.setFondosolcjpu(rs.getString("fondosolcjpu").trim());
             d.setAdicionalcjpu(rs.getInt("adicionalcjpu"));
             d.setFamiliar(rs.getInt("familiar"));
            
            }
                    
            ps.close();
            rs.close();
            
            return d;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double buscoImportereintParaCategoria(Integer catcjpu) throws BDExcepcion{
        try {
            double retorno=0;
            String consulta="select valor from pers_catcjppu where codigo=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, catcjpu);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             retorno=rs.getDouble("valor");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public double cargoImporteMensualIrpf(Funcionario k, Integer mes, Integer anio, boolean hacerAjusteAnual) throws BDExcepcion{
          try {
           double retorno =0;
           String consulta ="";
           PreparedStatement ps;
           ResultSet rs;
           if(hacerAjusteAnual){
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=207)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
           }
           else{
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and cod_mov=207 and tipoliq<>2)";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);   
           }
               
            rs=ps.executeQuery();
            while (rs.next()) {
             retorno = rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoMontoAjuste(Funcionario k) throws BDExcepcion{
       try {
            double retorno=0;
            String consulta="select sum(importe) as total from pers_liquidaciones where cod_func=? and (cod_mov=206 or cod_mov=208)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             retorno=rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoGratificacionesRaras(Funcionario k)throws BDExcepcion {
        try {
            double retorno=0;
            String consulta="select sum(importe) as total from pers_liquidaciones where cod_func=? and (cod_mov=54 or cod_mov=88 or cod_mov=89)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             retorno=rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double totalSeguroAce(Funcionario k) throws BDExcepcion{
       try {
            double retorno=0;
            String consulta="select importe from pers_liquidaciones where (cod_func=? and cod_mov=49)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             retorno=rs.getDouble("importe");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<Retencion> cargoRetencionesJudiciales(Funcionario k)throws BDExcepcion {
        try {
            ArrayList<Retencion> retenciones = new ArrayList<>();
            String consulta="select * from pers_retenciones_judiciales where (activa=1 and cod_func=? and sueldo=1) order by cod_func,orden";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             Retencion r = new Retencion();
             r.setActiva(rs.getInt("ACTIVA")); 
             r.setImporte(rs.getDouble("IMPORTE"));
             r.setOrden(rs.getInt("ORDEN"));
             r.setOtros(rs.getInt("OTROS"));
             r.setPorcentaje(rs.getDouble("PORCENTAJE"));
             r.setSobre(rs.getInt("SOBRE"));
             r.setSueldo(rs.getInt("SUELDO"));
             r.setTipo(rs.getInt("TIPO"));
             retenciones.add(r);
                     
            }
                    
            ps.close();
            rs.close();
            
            return retenciones;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public ArrayList<Retencion> cargoRetencionesJudicialesVacacional(Funcionario k)throws BDExcepcion {
        try {
            ArrayList<Retencion> retenciones = new ArrayList<>();
            String consulta="select * from pers_retenciones_judiciales where (activa=1 and cod_func=? and otros=1) order by cod_func,orden";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             Retencion r = new Retencion();
             r.setActiva(rs.getInt("ACTIVA")); 
             r.setImporte(rs.getDouble("IMPORTE"));
             r.setOrden(rs.getInt("ORDEN"));
             r.setOtros(rs.getInt("OTROS"));
             r.setPorcentaje(rs.getDouble("PORCENTAJE"));
             r.setSobre(rs.getInt("SOBRE"));
             r.setSueldo(rs.getInt("SUELDO"));
             r.setTipo(rs.getInt("TIPO"));
             retenciones.add(r);
                     
            }
                    
            ps.close();
            rs.close();
            
            return retenciones;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public ArrayList<Retencion> cargoRetencionesJudicialesVacacionalReLiq(Funcionario k,Date fechaLiq)throws BDExcepcion {
        try {
            //---------cambio de fecha a un dia antes----///
            Calendar cal =Calendar.getInstance();
            cal.setTime(fechaLiq);
            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-1);
            Date fecha = cal.getTime();
            ArrayList<Retencion> retenciones = new ArrayList<>();
            String consulta="select * from pers_hist_retenciones_judiciales where (activa=1 and cod_func=? and otros=1 and fechaliq=?) order by cod_func,orden";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setString(2, this.convertirFecha(fecha));
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             Retencion r = new Retencion();
             r.setActiva(rs.getInt("ACTIVA")); 
             r.setImporte(rs.getDouble("IMPORTE"));
             r.setOrden(rs.getInt("ORDEN"));
             r.setOtros(rs.getInt("OTROS"));
             r.setPorcentaje(rs.getDouble("PORCENTAJE"));
             r.setSobre(rs.getInt("SOBRE"));
             r.setSueldo(rs.getInt("SUELDO"));
             r.setTipo(rs.getInt("TIPO"));
             retenciones.add(r);
                     
            }
                    
            ps.close();
            rs.close();
            
            return retenciones;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public ArrayList<Retencion> cargoRetencionesJudicialesReLiq(Funcionario k, Date fechaLiq)throws BDExcepcion {
        try {
            ArrayList<Retencion> retenciones = new ArrayList<>();
            String consulta="select * from pers_hist_retenciones_judiciales where (activa=1 and cod_func=? and sueldo=1 and tipo=1 and fechaliq=?)order by cod_func,orden";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setString(2, this.convertirFecha(fechaLiq));
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             Retencion r = new Retencion();
             r.setActiva(rs.getInt("ACTIVA")); 
             r.setImporte(rs.getDouble("IMPORTE"));
             r.setOrden(rs.getInt("ORDEN"));
             r.setOtros(rs.getInt("OTROS"));
             r.setPorcentaje(rs.getDouble("PORCENTAJE"));
             r.setSobre(rs.getInt("SOBRE"));
             r.setSueldo(rs.getInt("SUELDO"));
             r.setTipo(rs.getInt("TIPO"));
             retenciones.add(r);
                     
            }
                    
            ps.close();
            rs.close();
            
            return retenciones;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public Liquidacion cargoLineaLiq(Funcionario k, int cod) throws BDExcepcion{
       try {
            Liquidacion liq = null;
            String consulta="select cantidad,importe from pers_liquidaciones where ((cod_func=?) and (cod_mov=?))";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, cod);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             liq = new Liquidacion();
             liq.setCantidad(rs.getDouble("CANTIDAD"));
             liq.setImporte(rs.getDouble("IMPORTE"));
            }
                    
            ps.close();
            rs.close();
            
            return liq;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<Liquidacion> cargoDescuentos(Funcionario k)throws BDExcepcion {
         try {
            ArrayList<Liquidacion> lineas = new ArrayList<>();
            Liquidacion liq=null;
            String consulta="select * from pers_liquidaciones l, pers_codigos c where l.cod_func=? and l.cod_mov>310 and c.codigo=l.cod_mov order by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             liq = new Liquidacion();
             liq.setCantidad(rs.getDouble("CANTIDAD"));
             liq.setImporte(rs.getDouble("IMPORTE"));
             liq.setCod(rs.getInt("COD_MOV"));
             liq.setFechaPrelacion(this.cargoFechaPrel(rs.getInt("COD_MOV"),k.getCodFunc()));
             lineas.add(liq);
            }
                    
            ps.close();
            rs.close();
            
            return lineas;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private Timestamp cargoFechaPrel(int cod, Integer codFunc)throws BDExcepcion {
       try {
            Timestamp fecha = null;
            String consulta="select fecha from pers_codigos_prelacion where codfunc=? and codigo=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            ps.setInt(2, cod);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
             fecha=rs.getTimestamp("FECHA");
            }
                    
            ps.close();
            rs.close();
            
            return fecha;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public Integer actualizoLiquidacion(Funcionario f, Integer cod, double Importe)throws BDExcepcion{
         try {
           Integer contador=0;
                 
            String insert="update pers_liquidaciones set importe=?, valor_u=? where (cod_func=? and cod_mov=?)";
            PreparedStatement ps=cnn.prepareStatement(insert);
            
            ps.setDouble(1, Importe);
            ps.setDouble(2, Importe);
            ps.setInt(3, f.getCodFunc());
            ps.setInt(4, cod);
            
            contador=ps.executeUpdate();
            ps.close();
                  
            return contador;    
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public Integer actualizoLiquidacion2(Funcionario f, Integer cod, double descuentoRechazado)throws BDExcepcion{
         try {
           Integer contador=0;
                 
            String insert="update pers_liquidaciones set importe_no_retenido=? where (cod_func=? and cod_mov=?)";
            PreparedStatement ps=cnn.prepareStatement(insert);
            
            ps.setDouble(1, descuentoRechazado);
            ps.setInt(2, f.getCodFunc());
            ps.setInt(3, cod);
            
            contador=ps.executeUpdate();
            ps.close();
               
        
            
            
            return contador;    
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    public double cargoImporteRetFija(Funcionario k, Integer cod, double totalHaberesBps, double liquidoLegal) throws BDExcepcion{
       try {
            double retorno=0;
            String consulta="select * from pers_retenciones_fijas where (activa=1 and cod_func=? and cod_mov=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, cod);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             if(rs.getInt("tipo")==0){//es una retencion de importe fijo
                 retorno=rs.getDouble("IMPORTE");
             }else{
                 if(rs.getInt("sobre")==0){//se aplica el porcentaje sobre el total de haberes
                     retorno=totalHaberesBps*rs.getDouble("PORCENTAJE"); 
                 }else{//se aplica el porcentaje sobre el liquido legal
                     retorno=liquidoLegal*rs.getDouble("PORCENTAJE");
                 }
             }
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public double cargoImporteRetFijaReLiq(Funcionario k, Integer cod, double totalHaberesBps, double liquidoLegal, Date fechaLiq) throws BDExcepcion{
       try {
            double retorno=0;
            String consulta="select * from pers_hist_retenciones_fijas where (activa=1 and  tipo=1 and cod_func=? and cod_mov=? and fechaliq=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, cod);
            ps.setString(3, this.convertirFecha(fechaLiq));
            
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             if(rs.getInt("tipo")==0){//es una retencion de importe fijo
                 retorno=rs.getDouble("IMPORTE");
             }else{
                 if(rs.getInt("sobre")==0){//se aplica el porcentaje sobre el total de haberes
                     retorno=totalHaberesBps*rs.getDouble("PORCENTAJE"); 
                 }else{//se aplica el porcentaje sobre el liquido legal
                     retorno=liquidoLegal*rs.getDouble("PORCENTAJE");
                 }
             }
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public double buscarTotalNoRetenido(Funcionario k)throws BDExcepcion {
         try {
            double retorno=0;
            String consulta="select sum(importe_no_retenido) as total from pers_liquidaciones where (cod_func=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             retorno=rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public Integer insertoResumen(ResumenLiquidacion resumen)throws BDExcepcion{
        try {
            Integer retorno = 0;
            
            String inserto="insert into pers_resumen (fecha,cod_func,total_haberes,total_descuentos,liquido_legal,liquido_minimo,liquido_final,total_haberes_bps,total_haberes_irpf,centrocosto,totalnoret)"+"values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps=cnn.prepareStatement(inserto);
            ps.setString(1,this.convertirFecha(resumen.getFecha()));
            ps.setInt(2, resumen.getFuncionario().getCodFunc());
            ps.setDouble(3, resumen.getTotalHaberes());
            ps.setDouble(4, resumen.getTotalDescuentos());
            ps.setDouble(5, resumen.getLiquidoLegal());
            ps.setDouble(6, resumen.getLiquidoMinimo());
            ps.setDouble(7, resumen.getLiquidoFinal());
            ps.setDouble(8, resumen.getTotalHaberesBps());
            ps.setDouble(9, resumen.getTotalHaberesIrpf());
            ps.setInt(10, resumen.getFuncionario().getCentroCosto().getCodigo());
            ps.setDouble(11, resumen.getTotalNoRet());
            retorno+=ps.executeUpdate();
            
            ps.close();
           
            return retorno;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    public void eliminacionesFinales(Funcionario f) throws BDExcepcion {
       try {
            Connection cnn=null;
            
            String borro ="";
            PreparedStatement ps;
            cnn=Conexion.Cadena();
            cnn.setAutoCommit(false);
            if(f==null){
                borro="delete  from pers_liquidaciones where cod_mov=501 and importe=0";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="delete  from pers_liquidaciones where cod_mov>310 and importe=0";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="update pers_resumen set tipoliq=1";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="update pers_liquidaciones set tipoliq=1";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
            }
            else{
                int e=0;
                borro="delete  from pers_liquidaciones where cod_mov=501 and importe=0 and cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="delete  from pers_liquidaciones where cod_mov>310 and importe=0 and cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="update pers_resumen set tipoliq=1 where cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="update pers_liquidaciones set tipoliq=1 where cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
            }
            cnn.commit();
            
            if(cnn!=null){
                cnn.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public void eliminacionesFinalesAguinaldo(Funcionario f) throws BDExcepcion {
       try {
            Connection cnn=null;
            
            String borro ="";
            PreparedStatement ps;
            cnn=Conexion.Cadena();
            cnn.setAutoCommit(false);
            if(f==null){
                borro="delete  from pers_liquidaciones where cod_mov=501 and importe=0";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="delete  from pers_liquidaciones where cod_mov>310 and importe=0";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="update pers_resumen set tipoliq=2";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="update pers_liquidaciones set tipoliq=2";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
            }
            else{
                int e=0;
                borro="delete  from pers_liquidaciones where cod_mov=501 and importe=0 and cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="delete  from pers_liquidaciones where cod_mov>310 and importe=0 and cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="update pers_resumen set tipoliq=2 where cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="update pers_liquidaciones set tipoliq=2 where cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
            }
            cnn.commit();
            
            if(cnn!=null){
                cnn.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public void eliminacionesFinalesReLiq(Funcionario f) throws BDExcepcion {
       try {
            Connection cnn=null;
            
            String borro ="";
            PreparedStatement ps;
            cnn=Conexion.Cadena();
            cnn.setAutoCommit(false);
            if(f==null){
                borro="delete  from pers_liquidaciones where cod_mov=501 and importe=0";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="delete  from pers_liquidaciones where cod_mov>310 and importe=0";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="update pers_resumen set tipoliq=5";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="update pers_liquidaciones set tipoliq=5";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
            }
            else{
                int e=0;
                borro="delete  from pers_liquidaciones where cod_mov=501 and importe=0 and cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="delete  from pers_liquidaciones where cod_mov>310 and importe=0 and cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="update pers_resumen set tipoliq=5 where cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="update pers_liquidaciones set tipoliq=5 where cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
            }
            cnn.commit();
            
            if(cnn!=null){
                cnn.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public void eliminacionesVacacionales(Funcionario f) throws BDExcepcion {
       try {
            Connection cnn=null;
            
            String borro ="";
            PreparedStatement ps;
            cnn=Conexion.Cadena();
            cnn.setAutoCommit(false);
            if(f==null){
                borro="delete  from pers_liquidaciones where cod_mov=501 and importe=0";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="delete  from pers_liquidaciones where cod_mov>310 and importe=0";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="update pers_resumen set tipoliq=4";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
                borro="update pers_liquidaciones set tipoliq=4";
                ps=cnn.prepareStatement(borro);
                ps.executeUpdate();
                
            }
            else{
                int e=0;
                borro="delete  from pers_liquidaciones where cod_mov=501 and importe=0 and cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="delete  from pers_liquidaciones where cod_mov>310 and importe=0 and cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="update pers_resumen set tipoliq=4 where cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
                
                borro="update pers_liquidaciones set tipoliq=4 where cod_func=?";
                ps=cnn.prepareStatement(borro);
                ps.setInt(1, f.getCodFunc());
                e+=ps.executeUpdate();
            }
            cnn.commit();
            
            if(cnn!=null){
                cnn.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<Ingreso> verificoPrelacion(Funcionario f)throws BDExcepcion {
        try {
        perCod = new PersistenciaCodigo();
        perFunc = new PersistenciaFuncionario();
        Connection cnn=null;
        String ingreso ="";
        String retenciones ="";
        PreparedStatement psIng=null;
        PreparedStatement psRet=null;
        cnn=Conexion.Cadena();
        cnn.setAutoCommit(false);
        ArrayList<Ingreso> ingresos=new ArrayList<>();
        if(f==null){
            ingreso="select * from pers_ingresos i, pers_codigos c where i.cod_mov>310 and i.cod_mov=c.codigo and (c.grupo=2 or c.grupo=3) and i.cod_mov not in(select codigo from pers_codigos_prelacion p where i.cod_func = p.codfunc)";
            psIng=cnn.prepareStatement(ingreso);
            
            retenciones="select * from pers_retenciones_fijas i, pers_codigos c where i.cod_mov>310 and i.cod_mov=c.codigo and (c.grupo=2 or c.grupo=3) and i.activa=1 and i.cod_mov not in(select codigo from pers_codigos_prelacion p where i.cod_func = p.codfunc)";
            psRet=cnn.prepareStatement(retenciones);
        }else{
            ingreso="select * from pers_ingresos i, pers_codigos c where i.cod_mov>310 and i.cod_mov=c.codigo and (c.grupo=2 or c.grupo=3)and i.cod_func=? and i.cod_mov not in(select codigo from pers_codigos_prelacion p where i.cod_func = p.codfunc)";
            psIng=cnn.prepareStatement(ingreso);
            psIng.setInt(1, f.getCodFunc());
            
            retenciones="select * from pers_retenciones_fijas i, pers_codigos c where i.cod_mov>310 and i.cod_mov=c.codigo and (c.grupo=2 or c.grupo=3) and i.activa=1 and i.cod_func=? and i.cod_mov not in(select codigo from pers_codigos_prelacion p where i.cod_func = p.codfunc)";
            psRet=cnn.prepareStatement(retenciones);
            psRet.setInt(1, f.getCodFunc());
        }
        
        ResultSet rs=psIng.executeQuery();
        while(rs.next()){
            Ingreso i=new Ingreso();
            i.setCod(rs.getInt("COD_MOV"));
            i.setCodFunc(rs.getInt("COD_FUNC"));
            i.setEnPers(rs.getInt("GRUPO"));
            i.setCodMov(perCod.cargaCodigo(i.getCod(), cnn));
            i.setFunc(perFunc.funcionarioBasicoMasivo(i.getCodFunc(), cnn)); 
            ingresos.add(i);
        }
        
        rs=psRet.executeQuery();
          while(rs.next()){
            Ingreso i=new Ingreso();
            i.setCod(rs.getInt("COD_MOV"));
            i.setCodFunc(rs.getInt("COD_FUNC"));
            i.setEnPers(rs.getInt("GRUPO")); 
            i.setCodMov(perCod.cargaCodigo(i.getCod(), cnn));
            i.setFunc(perFunc.funcionarioBasicoMasivo(i.getCodFunc(), cnn)); 
            ingresos.add(i);
        }
                
            cnn.commit();
            
            if(cnn!=null){
                cnn.close();
                psIng.close();
                psRet.close();
                rs.close();
            }
            
            this.comparator = new Comparator<Ingreso>() {
           @Override
            public int compare(Ingreso a, Ingreso b) {
                 int  resultado =  a.getCodFunc().compareTo(b.getCodFunc());
                    if ( resultado != 0 ) { return resultado; }
                resultado =  a.getCod().compareTo(b.getCod());
                    if ( resultado != 0 ) { return resultado; }
                
                return resultado;
            }
        };
       
        ingresos.sort(comparator);
        return ingresos;
            
          
         } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private Integer actualizoIngreso(Funcionario f, int cod, double Importe)throws BDExcepcion {
       try {
           Integer contador=0;
                 
            String insert="update pers_Ingresos set importe=? where cod_func=? and cod_mov=?";
            PreparedStatement ps=cnn.prepareStatement(insert);
            
            ps.setDouble(1, Importe);
            ps.setInt(2, f.getCodFunc());
            ps.setInt(3, cod);
            
            contador=ps.executeUpdate();
            ps.close();
                  
            return contador;    
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }
 
    private Integer actualizoOrdenLiq(Liquidacion liq, Integer ord, Funcionario f)throws BDExcepcion{
        try {
           Integer contador=0;
                 
            String insert="update pers_Liquidaciones set orden=? where cod_func=? and cod_mov=?";
            PreparedStatement ps=cnn.prepareStatement(insert);
            
            ps.setInt(1, ord);
            ps.setInt(2, f.getCodFunc());
            ps.setInt(3, liq.getCod());
            
            contador=ps.executeUpdate();
            ps.close();
                  
            return contador;    
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    
    //-------------------------------RESUMEN Y DETALLE--------------------------------------------------//
    public ArrayList<Liquidacion> cargoResumen() throws BDExcepcion{
       try {
            Connection cnn=null;
            cnn=Conexion.Cadena();
            
            ArrayList<Liquidacion> liquidaciones = new ArrayList<>();
            String consulta="select cod_mov,sum(importe) as totimp,sum(importe_no_retenido) as totnoret from pers_liquidaciones where ((cod_mov<200 or cod_mov>300) and (cod_mov<>600) or (cod_mov=206 or cod_mov=208)) group by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
         
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                Liquidacion liq = new Liquidacion();
                liq.setCodigo(this.perCod.cargaCodigo(rs.getInt("COD_MOV"), cnn));
                liq.setImporte(rs.getDouble("totimp"));
                liq.setImporteNoRet(rs.getDouble("totnoret"));
                liquidaciones.add(liq);
            }
                    
            ps.close();
            rs.close();
            
            return liquidaciones;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ResumenLiquidacion cargoTotalesGenerales()throws BDExcepcion{
       try {
            Connection cnn=null;
            cnn=Conexion.Cadena();
            
            ResumenLiquidacion resumen = null;
            String consulta="select sum(total_haberes) as totalHaberes, sum(total_haberes_bps) as totalBps, sum(total_haberes_irpf) as totalIrpf,sum(total_descuentos) as totalDescuentos, sum(liquido_final) as totalLiquido, count(*) as totalFunc from pers_resumen";
            PreparedStatement ps=cnn.prepareStatement(consulta);
         
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                resumen = new ResumenLiquidacion();
                resumen.setTotalHaberes(rs.getDouble("totalhaberes"));
                resumen.setTotalHaberesBps(rs.getDouble("totalbps"));
                resumen.setTotalHaberesIrpf(rs.getDouble("totalirpf"));
                resumen.setTotalDescuentos(rs.getDouble("totaldescuentos"));
                resumen.setLiquidoFinal(rs.getDouble("totalLiquido"));
                resumen.setTotalFunc(rs.getInt("totalFunc")); 
                resumen.setDipaico(this.cargoTotal(cnn,201));
                resumen.setCasenpace(this.cargoTotal(cnn,202));
                resumen.setFonasa(this.cargoTotal(cnn, 205));
                resumen.setFrl(this.cargoTotal(cnn,204));
                resumen.setIrpf(this.cargoTotal(cnn,207));
                resumen.setRedondeos(this.cargoTotal(cnn,600)); 
            }
                    
            ps.close();
            rs.close();
            
            return resumen;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private double cargoTotal(Connection cnn, int cod)throws BDExcepcion {
      try {
            double retorno=0;
            String consulta="select sum(importe) as total from pers_liquidaciones where cod_mov=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
             retorno=rs.getDouble("total");
            }
                    
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public ArrayList<Liquidacion> cargoLiqIrpf()throws BDExcepcion {
       try {
            Connection cnn=null;
            cnn=Conexion.Cadena();
            ArrayList<Liquidacion> liquid = new ArrayList<>();
            Liquidacion liq = null;
            cnn.setAutoCommit(false);
            String consulta="select distinct(codfun) from pers_irpf_detallado order by codfun";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            perFunc=new PersistenciaFuncionario();
            while (rs.next()) {
                liq = new Liquidacion();
                liq.setF(this.perFunc.funcionarioBasicoMasivo(rs.getInt("CODFUN"), cnn));
                liq.setIrpfDetallado(this.cargoIrpfDetallado(cnn, rs.getInt("codfun"),""));
                liquid.add(liq);
            }
            cnn.commit();
            cnn.close();
            ps.close();
            rs.close();
            
            return liquid;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    
    private ArrayList<irpfDetallado> cargoIrpfDetallado(Connection cnn, Integer cod,String fecha)throws BDExcepcion {
       try {
            
            ArrayList<irpfDetallado> detalles = new ArrayList<>();
            irpfDetallado det = null;
            String consulta;
            PreparedStatement ps;
            if(!"".equals(fecha)){
            consulta="select * from pers_HIST_irpf_detallado where codfun=? AND FECHALIQ=? order by tipo, ordinal";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ps.setString(2, fecha);
            }else{
            consulta="select * from pers_irpf_detallado where codfun=? order by tipo, ordinal";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            }
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                det = new irpfDetallado();
                det.setTipo(rs.getInt("TIPO"));
                det.setOrdinal(rs.getInt("ORDINAL"));
                det.setLeyenda(rs.getString("LEYENDA").trim());
                det.setImporte(rs.getDouble("IMPORTE"));
                detalles.add(det);
            }
                    
            ps.close();
            rs.close();
            
            return detalles;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<ResumenLiquidacion> cargoDetalleLiquidacion()throws BDExcepcion, ClassNotFoundException {
         try {
           
            cnn=Conexion.Cadena();
            ArrayList<ResumenLiquidacion> resumenes = new ArrayList<>();
            ResumenLiquidacion res = null;
            this.codigos=this.cargoCodigosPers();
            perFunc=new PersistenciaFuncionario();
            cnn.setAutoCommit(false);
            
            String consulta="select * from pers_resumen order by cod_func";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                res = new ResumenLiquidacion();
                res.setFuncionario(this.perFunc.funcionarioParaLiq(rs.getInt("COD_FUNC"), cnn));
                res.setLiquidoFinal(rs.getDouble("LIQUIDO_FINAL"));
                res.setTotalHaberesBps(rs.getDouble("TOTAL_HABERES_BPS"));
                res.setTotalHaberesIrpf(rs.getDouble("TOTAL_HABERES_IRPF"));
                res.setLiquidoLegal(rs.getDouble("LIQUIDO_LEGAL"));
                res.setLiquidoMinimo(rs.getDouble("LIQUIDO_MINIMO"));
                res.setTotalDescuentos(rs.getDouble("total_descuentos"));
                res.setTotalHaberes(rs.getDouble("total_haberes"));
                res.setTotalNoRet(rs.getDouble("totalnoret"));
                res.setLiquidaciones(this.armoLiquidaciones(cnn,rs.getInt("COD_FUNC")));
                
                resumenes.add(res);
            }
            
            cnn.commit();
            cnn.close();
            ps.close();
            rs.close();
            
            return resumenes;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private ArrayList<Liquidacion> armoLiquidaciones(Connection cnn, int cod) throws SQLException {
        ArrayList<Liquidacion> liqs=new ArrayList<>();
        String consulta="select * from pers_liquidaciones where cod_func=? and (cod_mov between 1 and 200) and (ucase(rtrim(HABER))='SI')order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,"");
        consulta="select * from pers_liquidaciones where cod_func=? and (cod_mov between 1 and 200) and (ucase(rtrim(DEBE))='SI') order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,"");
        consulta="select * from pers_liquidaciones where cod_func=? and (cod_mov between 201 and 300)order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,"");
        consulta="select * from pers_liquidaciones where cod_func=? and (cod_mov between 301 and 410) order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,"");
        consulta ="select * from pers_liquidaciones where cod_func=? and ((cod_mov between 411 and 500) or cod_mov=518) order by orden";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,"");
        consulta="select * from pers_liquidaciones where cod_func=? and (cod_mov between 501 and 600) and cod_mov<>518 order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,"");
        
        return liqs;
    }

    private ArrayList<Liquidacion> cargoLiquidaciones(Connection cnn, String consulta, ArrayList<Liquidacion> liqs, int cod,String fecha) throws SQLException {
        PreparedStatement ps=cnn.prepareStatement(consulta);
        if(fecha==""){
        ps.setInt(1, cod);
        }else{
            ps.setInt(1, cod);
            ps.setString(2, fecha);
        }
        ResultSet rs=ps.executeQuery();
        Liquidacion l;
            while (rs.next()) {
                l = new Liquidacion();
                l.setCodigo(this.buscarCodigo(rs.getInt("cod_mov")));
                l.setCantidad(rs.getDouble("cantidad"));
                l.setImporte(rs.getDouble("IMPORTE")); 
                l.setImporteNoRet(rs.getDouble("IMPORTE_NO_RETENIDO"));
                l.setValorUnitario(rs.getDouble("valor_u"));
                                
               liqs.add(l);
            }
        ps.close();
        rs.close();
        
        return liqs;
    }

    public ArrayList<Liquidacion> cargoDescuentos()throws BDExcepcion {
        try {
            ArrayList<Liquidacion> liqs=new ArrayList<>();
            
            cnn=Conexion.Cadena();
            cnn.setAutoCommit(false);
            perFunc=new PersistenciaFuncionario();
            this.codigos=this.cargoCodigosPers();
            String consulta="select * from pers_liquidaciones where (cod_mov>300 and cod_mov<600) order by cod_mov,cod_func";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            
            ResultSet rs=ps.executeQuery();
            Liquidacion l;
            while (rs.next()) {
                l = new Liquidacion();
                l.setCodigo(this.buscarCodigo(rs.getInt("cod_mov")));
                l.setF(this.perFunc.funcionarioBasicoMasivo(rs.getInt("COD_FUNC"), cnn));
                l.setImporte(rs.getDouble("IMPORTE")); 
                l.setImporteNoRet(rs.getDouble("IMPORTE_NO_RETENIDO"));
                
                liqs.add(l);
            }
            
            
            ps.close();
            rs.close();
            
            return liqs;
        } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
        }
    }

    public String cargoParametroString(String nombre)throws BDExcepcion {
       try {
            String retorno="";
            String consulta="Select VALOR from PERS_PARAMETROS where NOMBRE=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1,nombre);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                retorno=rs.getString("VALOR").trim(); 
               
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    public String cargoParametroStringConConexion(String nombre)throws BDExcepcion, ClassNotFoundException {
       try {
            cnn=Conexion.Cadena();
            cnn.setAutoCommit(false);
            String retorno="";
            String consulta="Select VALOR from PERS_PARAMETROS where NOMBRE=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1,nombre);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                retorno=rs.getString("VALOR").trim(); 
               
            }
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<Liquidacion> cargoResumenHistorico(String fecha) throws BDExcepcion{
       try {
            Connection cnn=null;
            cnn=Conexion.Cadena();
            
            ArrayList<Liquidacion> liquidaciones = new ArrayList<>();
            String consulta="select cod_mov,sum(importe) as totimp,sum(importe_no_retenido) as totnoret from pers_hist_liquidaciones where ((cod_mov<200 or cod_mov>300) and (cod_mov<>600) or (cod_mov=206 or cod_mov=208)) and fechaliq=? group by cod_mov";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, fecha);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                Liquidacion liq = new Liquidacion();
                liq.setCodigo(this.perCod.cargaCodigo(rs.getInt("COD_MOV"), cnn));
                liq.setImporte(rs.getDouble("totimp"));
                liq.setImporteNoRet(rs.getDouble("totnoret"));
                liquidaciones.add(liq);
            }
                    
            ps.close();
            rs.close();
            
            return liquidaciones;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<Liquidacion> cargoLiqIrpfHisorico(String fecha) throws BDExcepcion{
            try {
            Connection cnn=null;
            cnn=Conexion.Cadena();
            ArrayList<Liquidacion> liquid = new ArrayList<>();
            Liquidacion liq = null;
            cnn.setAutoCommit(false);
            String consulta="select distinct(codfun) from pers_HIST_IRPF_DETALLADO where fechaliq=? order by codfun";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, fecha); 
            ResultSet rs=ps.executeQuery();
            perFunc=new PersistenciaFuncionario();
            while (rs.next()) {
                liq = new Liquidacion();
                liq.setF(this.perFunc.funcionarioBasicoMasivo(rs.getInt("CODFUN"), cnn));
                liq.setIrpfDetallado(this.cargoIrpfDetallado(cnn, rs.getInt("codfun"),fecha));
                liquid.add(liq);
            }
            cnn.commit();
            cnn.close();
            ps.close();
            rs.close();
            
            return liquid;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<ResumenLiquidacion> cargoDetalleLiquidacionHistorico(String fecha)throws BDExcepcion {
         try {
           
            cnn=Conexion.Cadena(); 
            ArrayList<ResumenLiquidacion> resumenes = new ArrayList<>();
            ResumenLiquidacion res = null;
            this.codigos=this.cargoCodigosPers();
            perFunc=new PersistenciaFuncionario();
            cnn.setAutoCommit(false);
            
            String consulta="select * from pers_hist_resumen where fechaliq=? order by cod_func";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, fecha);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                res = new ResumenLiquidacion();
                res.setFuncionario(this.perFunc.funcionarioParaLiq(rs.getInt("COD_FUNC"), cnn));
                res.setLiquidoFinal(rs.getDouble("LIQUIDO_FINAL"));
                res.setTotalHaberesBps(rs.getDouble("TOTAL_HABERES_BPS"));
                res.setTotalHaberesIrpf(rs.getDouble("TOTAL_HABERES_IRPF"));
                res.setLiquidoLegal(rs.getDouble("LIQUIDO_LEGAL"));
                res.setLiquidoMinimo(rs.getDouble("LIQUIDO_MINIMO"));
                res.setTotalDescuentos(rs.getDouble("total_descuentos"));
                res.setTotalHaberes(rs.getDouble("total_haberes"));
                res.setTotalNoRet(rs.getDouble("totalnoret"));
                res.setLiquidaciones(this.armoLiquidacionesHistoricas(cnn,rs.getInt("COD_FUNC"),fecha));
                
                resumenes.add(res);
            }
            
            cnn.commit();
            cnn.close();
            ps.close();
            rs.close();
            
            return resumenes;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     private ArrayList<Liquidacion> armoLiquidacionesHistoricas(Connection cnn, int cod,String fecha) throws SQLException {
        ArrayList<Liquidacion> liqs=new ArrayList<>();
        String consulta="select * from pers_hist_liquidaciones where cod_func=? and (cod_mov between 1 and 200) and (ucase(rtrim(HABER))='SI') and fechaliq=? order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,fecha);
        consulta="select * from pers_hist_liquidaciones where cod_func=? and (cod_mov between 1 and 200) and (ucase(rtrim(DEBE))='SI') and fechaliq=? order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,fecha);
        consulta="select * from pers_hist_liquidaciones where cod_func=? and (cod_mov between 201 and 300) and fechaliq=? order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,fecha);
        consulta="select * from pers_hist_liquidaciones where cod_func=? and (cod_mov between 301 and 410) and fechaliq=? order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,fecha);
        consulta ="select * from pers_hist_liquidaciones where cod_func=? and ((cod_mov between 411 and 500) or cod_mov=518) and fechaliq=? order by orden";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,fecha);
        consulta="select * from pers_hist_liquidaciones where cod_func=? and (cod_mov between 501 and 600) and cod_mov<>518 and fechaliq=? order by cod_mov";
        liqs=this.cargoLiquidaciones(cnn,consulta,liqs,cod,fecha);
        
        return liqs;
    }

    public ArrayList<Liquidacion> cargoDescuentosHistoricos(String fecha) throws BDExcepcion{
         try {
            ArrayList<Liquidacion> liqs=new ArrayList<>();
            
            cnn=Conexion.Cadena();
            cnn.setAutoCommit(false);
            perFunc=new PersistenciaFuncionario();
            this.codigos=this.cargoCodigosPers();
            String consulta="select * from pers_hist_liquidaciones where (cod_mov>300 and cod_mov<600) and fechaliq=? order by cod_mov,cod_func";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, fecha);
            ResultSet rs=ps.executeQuery();
            Liquidacion l;
            while (rs.next()) {
                l = new Liquidacion();
                l.setCodigo(this.buscarCodigo(rs.getInt("cod_mov")));
                l.setF(this.perFunc.funcionarioBasicoMasivo(rs.getInt("COD_FUNC"), cnn));
                l.setImporte(rs.getDouble("IMPORTE")); 
                l.setImporteNoRet(rs.getDouble("IMPORTE_NO_RETENIDO"));
                
                liqs.add(l);
            }
            
            
            ps.close();
            rs.close();
            
            return liqs;
        } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<ResumenLiquidacion> cargoTotalesGanados(String fecha)throws BDExcepcion {
       try {
            cnn=Conexion.Cadena(); 
            ArrayList<ResumenLiquidacion> resumenes = new ArrayList<>();
            ResumenLiquidacion res = null;
            PreparedStatement ps;
            String consulta;
            perFunc=new PersistenciaFuncionario();
            cnn.setAutoCommit(false);
            if(!"".equals(fecha)){
            consulta="select * from pers_hist_resumen where fechaliq=? order by cod_func";
            ps=cnn.prepareStatement(consulta);
            ps.setString(1, fecha);
            }else{
            consulta="select * from pers_resumen order by cod_func";
            ps=cnn.prepareStatement(consulta);
            }
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                res = new ResumenLiquidacion();
                res.setFuncionario(this.perFunc.funcionarioParaLiq(rs.getInt("COD_FUNC"), cnn));
                res.setTotalHaberesBps(rs.getDouble("TOTAL_HABERES_BPS"));
                res.setTotalHaberesIrpf(rs.getDouble("TOTAL_HABERES_IRPF"));
                res.setTotalHaberes(rs.getDouble("total_haberes"));
                res.setCentro(this.cargaCentro(rs.getInt("centrocosto"), cnn));
                              
                resumenes.add(res);
            }
            
            cnn.commit();
            cnn.close();
            ps.close();
            rs.close();
            
            return resumenes;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
 
     public CentroCosto cargaCentro(Integer codigo,Connection cnn) throws SQLException, ClassNotFoundException{
       
        CentroCosto cen =null; 
       
       
        String consulta="Select * from PERS_CENTROCOSTO where codigo=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();
            if(rs.next()){
            cen=new CentroCosto();
            cen.setCodigo(rs.getInt("CODIGO"));
            cen.setNombre(rs.getString("NOMBRE").trim());
            }        
         
                ps.close();
                rs.close();
       
    
         return cen;
    }

    public ArrayList<ResumenLiquidacion> cargoTotalesGanadosCentro(String fecha)throws BDExcepcion {
        try {
            cnn=Conexion.Cadena(); 
            ArrayList<ResumenLiquidacion> resumenes = new ArrayList<>();
            ResumenLiquidacion res = null;
            PreparedStatement ps;
            String consulta;
            perFunc=new PersistenciaFuncionario();
            cnn.setAutoCommit(false);
            if(!"".equals(fecha)){
            consulta="select * from pers_hist_resumen where fechaliq=? order by centrocosto, cod_func";
            ps=cnn.prepareStatement(consulta);
            ps.setString(1, fecha);
            }else{
            consulta="select * from pers_resumen order by centrocosto, cod_func";
            ps=cnn.prepareStatement(consulta);
            }
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                res = new ResumenLiquidacion();
                res.setFuncionario(this.perFunc.funcionarioParaLiq(rs.getInt("COD_FUNC"), cnn));
                res.setTotalHaberesBps(rs.getDouble("TOTAL_HABERES_BPS"));
                res.setTotalHaberesIrpf(rs.getDouble("TOTAL_HABERES_IRPF"));
                res.setTotalHaberes(rs.getDouble("total_haberes"));
                res.setCentro(this.cargaCentro(rs.getInt("centrocosto"), cnn));
                              
                resumenes.add(res);
            }
            
            cnn.commit();
            cnn.close();
            ps.close();
            rs.close();
            
            return resumenes;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public ArrayList<ResumenLiquidacion> cargoTotalesGanadosYDescuentos(String fecha)throws BDExcepcion {
        try {
            Connection cn=Conexion.Cadena(); 
            ArrayList<ResumenLiquidacion> resumenes = new ArrayList<>();
            ResumenLiquidacion res = null;
            PreparedStatement ps;
            String consulta;
           
            perFunc=new PersistenciaFuncionario();
            cn.setAutoCommit(false);
          
            consulta="select * from pers_funcionarios order by codigo";
            ps=cn.prepareStatement(consulta);
           
            ResultSet rs=ps.executeQuery();
            cnn=Conexion.Cadena(); 
            cnn.setAutoCommit(false);
            while (rs.next()) {
               this.reconecto();
                res = new ResumenLiquidacion();
                res.setFuncionario(this.perFunc.funcionarioParaLiq(rs.getInt("CODIGO"), cnn));
                
                res.setTotalHaberesBps(this.cargoTotalImporteResumen("TOTAL_HABERES_BPS",rs.getInt("CODIGO"),fecha,cnn));
                res.setTotalHaberesIrpf(this.cargoTotalImporteResumen("TOTAL_HABERES_IRPF", rs.getInt("CODIGO"), fecha,cnn));
                res.setTotalHaberes(this.cargoTotalImporteResumen("TOTAL_HABERES",rs.getInt("CODIGO"),fecha,cnn));
                res.setLiquidoFinal(this.cargoTotalImporteResumen("LIQUIDO_FINAL",rs.getInt("CODIGO"),fecha,cnn)); 
                
                res.setCentro(this.cargaCentro(rs.getInt("centro_costo"), cnn));
                
                res.setDipaico(this.cargoTotalImporte(rs.getInt("CODIGO"),201,fecha,cnn));
                res.setCasenpace(this.cargoTotalImporte(rs.getInt("CODIGO"),515,fecha,cnn));
                res.setFonasa(this.cargoTotalImporte(rs.getInt("CODIGO"),205,fecha,cnn));
                res.setFrl(this.cargoTotalImporte(rs.getInt("CODIGO"),204,fecha,cnn)); 
                res.setIrpf(this.cargoTotalImporte(rs.getInt("CODIGO"),207,fecha,cnn));
                contador+=20;
                resumenes.add(res);
            }
            cnn.commit();
            cn.commit();
            cn.close();
            ps.close();
            rs.close();
            
            return resumenes;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private double cargoTotalImporte(int codFunc, int cod,String fecha,Connection cn)throws BDExcepcion, SQLException, ClassNotFoundException {
   
       String consulta;
       PreparedStatement ps;
       double importe=0;
        String m=fecha.substring(3, 5);
        String a=fecha.substring(6, 10);
        Integer mes = Integer.valueOf(m);
        Integer año =Integer.valueOf(a);
        
      consulta = "select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fecha)=? and year(fecha)=? and cod_mov=?)";
           ps=cn.prepareStatement(consulta);
           ps.setInt(1, codFunc);
           ps.setInt(2, mes);
           ps.setInt(3, año);
           ps.setInt(4, cod);
      ResultSet rs=ps.executeQuery();
      if(rs.next()){
           importe+=rs.getDouble("total");
      }
      consulta = "select sum(importe) as total from pers_liquidaciones where (cod_func=? and month(fecha)=? and year(fecha)=? and  cod_mov=?)";
           ps=cn.prepareStatement(consulta);
           ps.setInt(1, codFunc);
           ps.setInt(2, mes);
           ps.setInt(3, año);
           ps.setInt(4, cod);
       
       rs=ps.executeQuery();
       if(rs.next()){
           importe+=rs.getDouble("total");
       }
       
       ps.close();
       rs.close();
   
       return importe;
    }

    private double cargoTotalImporteResumen(String columna, int codFunc, String fecha,Connection cn) throws BDExcepcion, SQLException, ClassNotFoundException{
    
       String consulta;
       PreparedStatement ps;
       String m=fecha.substring(3, 5);
       String a=fecha.substring(6, 10);
       Integer mes = Integer.valueOf(m);
       Integer año =Integer.valueOf(a);
       double importe=0;
        consulta = "select sum("+columna+") as total from pers_hist_resumen where (cod_func=? and month(fecha)=? and year(fecha)=?)";
           ps=cn.prepareStatement(consulta);
           ps.setInt(1, codFunc);
           ps.setInt(2, mes);
           ps.setInt(3, año);
      ResultSet rs=ps.executeQuery();
      if(rs.next()){
           importe+=rs.getDouble("total");
      }
           consulta = "select sum("+columna+") as total from pers_resumen where (cod_func=? and month(fecha)=? and year(fecha)=?)";
           ps=cn.prepareStatement(consulta);
           ps.setInt(1, codFunc);
           ps.setInt(2, mes);
           ps.setInt(3, año);
       
       rs=ps.executeQuery();
       if(rs.next()){
           importe+=rs.getDouble("total");
       }
       
       ps.close();
       rs.close();
    
       return importe;
    }
//------------------------------------------------------------------------------------------------------------//
    public ArrayList<Integer> cargoAñosLiquidaciones() throws BDExcepcion{
        try {
            ArrayList<Integer> años = new ArrayList<>();
            Connection cn=Conexion.Cadena();
            String consulta="select distinct(year(fechaliq)) as ano from pers_hist_liquidaciones order by year(fechaliq)desc";
            PreparedStatement ps=cn.prepareStatement(consulta);
            ResultSet rs =ps.executeQuery();
            while (rs.next()) {
                Integer año = rs.getInt("ANO");
                años.add(año);
            }
            cn.close();
            ps.close();
            rs.close();
            
            return años;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    
    }

    public ArrayList<Liquidacion> cargoLiquidacionesPorCodigos(int mes, int año, ArrayList<Codigo> codigos)throws BDExcepcion {
        try {
            Connection cn=Conexion.Cadena();
            String consulta;
            PreparedStatement ps=null;
            ResultSet rs=null;
            ArrayList<Liquidacion> liquidaciones = new ArrayList<>();
            Liquidacion l;
            this.perFunc= new PersistenciaFuncionario();
            for(Codigo c:codigos){
                consulta="select * from pers_hist_liquidaciones Where cod_mov=? and month(fechaliq)=? and year(fechaliq)=? order by cod_func";
                ps=cn.prepareStatement(consulta);
                ps.setInt(1, c.getCod());
                ps.setInt(2, mes);
                ps.setInt(3, año);
                rs =ps.executeQuery();
                while (rs.next()) {
                    l=new Liquidacion();
                    l.setFechaLiq(rs.getDate("fechaliq"));
                    l.setCodigo(c);
                    l.setF(this.perFunc.funcionarioParaLiq(rs.getInt("COD_FUNC"), cn));
                    l.setImporte(rs.getDouble("importe"));
                    l.setImporteNoRet(rs.getDouble("importe_no_retenido"));
                    liquidaciones.add(l);
                }
                
                consulta="select * from pers_liquidaciones Where cod_mov=? and month(fecha)=? and year(fecha)=? order by cod_func";
                ps=cn.prepareStatement(consulta);
                ps.setInt(1, c.getCod());
                ps.setInt(2, mes);
                ps.setInt(3, año);
                rs =ps.executeQuery();
                while (rs.next()) {
                    l=new Liquidacion();
                    l.setFechaLiq(rs.getDate("fecha"));
                    l.setCodigo(c);
                    l.setF(this.perFunc.funcionarioParaLiq(rs.getInt("COD_FUNC"), cn));
                    l.setImporte(rs.getDouble("importe"));
                    l.setImporteNoRet(rs.getDouble("importe_no_retenido"));
                    liquidaciones.add(l);
                }
                
            }
            
            rs.close();
            ps.close();
            cn.close();
            
            return liquidaciones;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private double buscoAguinaldoVacacionalDelAño(Funcionario k, Integer anio, int mes)throws BDExcepcion {
        try {
            double imponibleIrpfIngreso = 0;
            double retorno = 0;
            String consulta;
            PreparedStatement ps=null;
            ResultSet rs=null;
            consulta="select sum(importe) as total from pers_hist_liquidaciones where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? AND (tipoliq=3) and  rtrim(ucase(imponibleirpf))='SI' AND (COD_MOV=3 OR COD_MOV=7))";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
            rs =ps.executeQuery();
            while (rs.next()) {
                imponibleIrpfIngreso=rs.getDouble("total");
                imponibleIrpfIngreso=this.fijarNumero(imponibleIrpfIngreso, 2);
            }
            consulta="select sum(total_haberes_irpf) as total from pers_hist_resumen where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? AND (tipoliq=2 or tipoliq=4))";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, mes);
            ps.setInt(3, anio);
            rs =ps.executeQuery();
            while (rs.next()) {
                retorno=imponibleIrpfIngreso+rs.getDouble("total");
                retorno=this.fijarNumero(retorno, 2);
            }
            
            rs.close();
            ps.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }


    private Date buscoVigenciaDeclaracion(Funcionario k, int anio, Date fechaLiq)throws BDExcepcion {
        try {
            String consulta;
            PreparedStatement ps=null;
            ResultSet rs=null;
            Date fecha =null;
            consulta="select vigencia from pers_declaraciones where codfunc=?";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            rs =ps.executeQuery();
            if(rs.next()){
                int resultado = rs.getInt("vigencia");
                String res = String.valueOf(resultado);
                int año = Integer.parseInt(res.substring(0,4));
                int mes =Integer.parseInt(res.substring(5,6));
                
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 01);
                cal.set(Calendar.MONTH,mes-1);
                cal.set(Calendar.YEAR, año);
                fecha=cal.getTime();
                if(año<anio){
                    cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_MONTH, 01);
                    cal.set(Calendar.MONTH,0);
                    cal.set(Calendar.YEAR, anio);
                    fecha=cal.getTime();
                }
            }else{
                Calendar calLiq = Calendar.getInstance();
                calLiq.setTime(fechaLiq);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, calLiq.get(Calendar.DAY_OF_MONTH)+1);
                cal.set(Calendar.MONTH,calLiq.get(Calendar.MONTH));
                cal.set(Calendar.YEAR, calLiq.get(Calendar.YEAR));
                fecha=cal.getTime();
            }
            
            rs.close();
            ps.close();
            
            return fecha;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
        
    }

    private Date buscoUltimoDiaMes(Date fechaAnalizar) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(fechaAnalizar);
       int dia =0;
       switch (cal.get(Calendar.MONTH)+1){
           case 1: case 3: case 5: case 7: case 8: case 10:case 12:
               dia=31;
           break;
           case 2:
               if((cal.get(Calendar.YEAR)%4)==0){
                   dia=29;
               }else{
                   dia=28;
               }
           break;
           case 4: case 6: case 9: case 11:
               dia=30;
       }
       cal.set(Calendar.DAY_OF_MONTH, dia);
       return cal.getTime();
       
    }

    private Integer calculoCuantosMesesHijo(Date vigencia, Date fechaNac, int controloEdad, Date fechaAnalizar, Funcionario k) throws BDExcepcion {
       Date fechaInicio;
       Date fechaAux;
       Integer retorno;
       Integer cuantos;
        int dias=(int) ((vigencia.getTime()-fechaNac.getTime())/86400000);
        if(dias<0){
            //fechanac es posterior a fechavig
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaNac);
            cal.set(Calendar.DAY_OF_MONTH,01);
            fechaInicio = cal.getTime();
        }else{
            fechaInicio=vigencia;
        }
        dias=(int) ((fechaInicio.getTime()-fechaAnalizar.getTime())/86400000);
        if(dias<0){
            //fecha analizada es posterior a inicio(vigencia o nacimiento)
            if(controloEdad==0){
                retorno=1;
            }else{
                fechaAux=fechaAnalizar;
                Integer edad = this.calculoEdad(fechaNac, fechaAux);
                if(edad<18){
                    retorno=1;
                }else{
                    retorno=0;
                }
           }
        }else{
            //en ese mes no le corresponde el hijo
            retorno=0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaAnalizar);
        if(retorno==1 && cal.get(Calendar.MONTH)!=11){
            //verifico que haya trabajado algo ese mes
            Integer trabajoEseMes = this.buscoSiTrabajoEseMes(k,fechaAnalizar);
            if(trabajoEseMes==0){
                retorno=0;
            }
        }
        return retorno;
    }

    private Integer buscoSiTrabajoEseMes(Funcionario k, Date fechaAnalizar)throws BDExcepcion {
        try {
            Integer retorno=0;
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaAnalizar);
            String consulta="select cod_func from pers_hist_resumen where (cod_func=? and month(fechaliq)=? and year(fechaliq)=? and (tipoliq=1 or tipoliq=3))";
            PreparedStatement ps = cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, cal.get(Calendar.MONTH)+1);
            ps.setInt(3, cal.get(Calendar.YEAR));
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                retorno=1;
            }
            rs.close();
            ps.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private Integer calculoCuantosMeses(Date vigencia, Date fechaLiq) {
        Date fechaIni = vigencia;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaIni); 
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-1);
        fechaIni=cal.getTime();
        
        Calendar inicio = new GregorianCalendar();
        Calendar fin = new GregorianCalendar();
        inicio.setTime(fechaIni);
        fin.setTime(fechaLiq);
        int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
        int difM = difA * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH);
       
        return difM;
    }
    
    //-----------------RE LIQUIDACION DE SUELDOS-----------------------//

    public void Reliquidar(Funcionario f, Date fechaLiq) throws BDExcepcion, SQLException, ClassNotFoundException, ParseException {
          if(f!=null){
                this.comienzoReLiquidación(f,fechaLiq);
                cnn.commit();
                this.eliminacionesFinales(f);
            }
            if(cnn!=null){
               cnn.close();
            }
    }

    private void comienzoReLiquidación(Funcionario k, Date fecha) throws BDExcepcion, ClassNotFoundException, SQLException, ParseException {
       this.limpioTablas(k);
       this.reconecto();
       Codigo c = null;
       Liquidacion l = null;
       liquidacionesMem=new ArrayList<>();
       
       //Liquidacion(Funcionario f, Integer tipoLiq, Codigo codigo, Double cantidad, Double valorUnitario, Double importe, Date fecha,Integer importeNoRet);
       
       
        //-----INSERTO SUELDO 1--------//
       double sueldo = this.cargoSueldoReliq(k,fecha);
       if(sueldo>0){
       c=this.buscarCodigo(1);
       l = new Liquidacion(k,1,c,1.0,sueldo,sueldo,fecha,0);
       contador+=this.insertoLiquidacion(l);
       this.liquidacionesMem.add(l);
       }
       //-----INSERTO ANTIGUEDAD 43----//
       if(sueldo>0 && primaAntiguedad>0){
            this.anosAntiguedad = this.calculoAntiguedad(k.getFechaIngreso(),fecha);
            if(this.anosAntiguedad>0){
                c=this.buscarCodigo(43);
                double antiguedadTotal = this.anosAntiguedad*this.primaAntiguedad;
                antiguedadTotal = this.fijarNumero(antiguedadTotal, 2);
                l = new Liquidacion(k,1,c,Double.parseDouble(anosAntiguedad.toString()),antiguedadTotal,antiguedadTotal,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
           
       }
       //------------------------------//
       
        //------INSERTO HOGAR CONSTITUIDO 42 -----//
       if(sueldo>0 && hogarConstituido>0){
            this.tieneHogar = this.tieneCodFijoHist(k.getCodFunc(),42,fecha);
            if(this.tieneHogar){
                c=this.buscarCodigo(42);
                l = new Liquidacion(k,1,c,1.0,hogarConstituido,hogarConstituido,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
        }
       //------------------------------//
       //------INSERTO QUEBRANTO 44 -----//
       if(sueldo>0){
            this.tieneQuebranto= this.cargoQuebrantoOld(k.getCodFunc(),fecha);

            if(this.tieneQuebranto>0){
            java.math.BigDecimal valorDivisor = new java.math.BigDecimal(this.tieneQuebranto);
            valorDivisor=valorDivisor.divide(new BigDecimal("100"));    
            java.math.BigDecimal suel = new java.math.BigDecimal(sueldo);

            java.math.BigDecimal queb = suel.multiply(valorDivisor);

            double quebranto=queb.doubleValue();
            quebranto=this.fijarNumero(quebranto,2);
            c=this.buscarCodigo(44);
            l = new Liquidacion(k,1,c,1.0,quebranto,quebranto,fecha,0);
            contador+=this.insertoLiquidacion(l);
            this.liquidacionesMem.add(l);
            }
       }
       //------------------------------//
       //------INSERTO REDUCCION 103 -----//
        if(sueldo>0){
            Integer baseHorariaOld = this.cargoBaseHorariaVieja(fecha,k);
            if(baseHorariaOld!=39){
                Double aux = sueldo/39;
                aux=aux*baseHorariaOld;
                Double reduccion = sueldo-aux;
                reduccion = Double.parseDouble(String.valueOf(reduccion.intValue()));
                c=this.buscarCodigo(103);
                l = new Liquidacion(k,1,c,1.0,reduccion,reduccion,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
        }
       //------------------------------//
       //------INSERTO SEGURO 355 -----////PONER CODIGO 355
       if(sueldo>0){
            this.tieneSeguro=this.tieneCodFijoHist(k.getCodFunc(),355,fecha);

            if(this.tieneSeguro){
                this.montoImponibleSeguro = this.buscoSueldoYreduccion(k.getCodFunc());
                double seg=seguro*this.montoImponibleSeguro;
                seg=this.fijarNumero(seg, 2);
                c=this.buscarCodigo(355);
                l = new Liquidacion(k,1,c,1.0,seg,seg,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
       //------------------------------//

       //------INSERTO SEGURO ACE 49 -----//

                double seAce=seguroAce*this.montoImponibleSeguro;
                seAce=this.fijarNumero(seAce, 2);
                c=this.buscarCodigo(49);
                l = new Liquidacion(k,1,c,1.0,seAce,seAce,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
         }
       //------------------------------//
       //----------------CARGA DE MOVIMIENTOS DESDE INGRESOS A LIQUIDACIONES--------------------//
       this.cargoIngresosReLiq(k);
       if(ingresos!=null){
           if(ingresos.size()>0){
               for(Ingreso i: ingresos){
                   Double importe = this.fijarNumero(i.getImporte(), 2);
                   double sueldoRed=this.buscoSueldoYreduccion(k.getCodFunc());
                   double baseHorasVieja=this.buscoBaseHorasVieja(k.getCodFunc(),fecha);
                   
                   if(null!=i.getCod())switch (i.getCod()) {
                       case 11: //Licencia por enfermedad
                           Calendar e=Calendar.getInstance();
                           e.setTime(fecha);
                           double aux = this.enfermedadAnteriorOld(k,e.get(Calendar.YEAR),fecha);
                           if(aux>=30){
                               importe=i.getCantidad()*(sueldoRed/30);
                               importe=this.fijarNumero(importe, 2);
                           }else if((aux+i.getCantidad())>=30){
                               importe=((aux+i.getCantidad())-30) * (sueldoRed/30);
                               importe=this.fijarNumero(importe, 2);
                           }
                           break;
                       case 13://Maternidad
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 15: //Licencia paternidad 5 dias paga ace, el resto se descuenta lo paga bps
                           importe=(i.getCantidad()-5)*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 18://Licencia sin sueldo
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 20://Feriado no laborable
                           importe=(i.getCantidad()*(sueldoRed/30))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 21://Feriado laborable
                           importe=(i.getCantidad()*(sueldoRed/30))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 22://jornal guardia
                           importe=(i.getCantidad()*(sueldoRed/30))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 23://Compensacion
                           importe = this.fijarNumero(i.getImporte(), 2);
                           break;
                       case 24://Promedio horas extras de licencia
                           importe=(i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 25://Inasistencia
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 26://Suspensiones
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 27://Inasistencia por paro
                           importe=i.getCantidad()*(sueldoRed/30);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 30://Complemento nocturno
                           importe=(i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja))*0.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 31://Horas extras simples
                           importe=(i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 32://Horas extras feriado no laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja))*4.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 33://Horas extras feriado laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja))*3.5;
                           importe=this.fijarNumero(importe, 2);
                           break; 
                       case 36://Horas de licencia sin sueldo
                           importe=i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 37://Descuento horario por paro
                           importe=i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 40://Llegadas tarde
                           if(i.getCantidad()>30){
                           importe=((i.getCantidad()-30)*(k.getSueldoCargo()/(baseHorasVieja*60)));
                           importe=this.fijarNumero(importe, 2);
                           }
                           break;
                       case 46://Promedio horas extras feriado no laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja))*4.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 47://Promedio horas extras feriado laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja))*3.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 59://Ley 19161
                           importe=i.getCantidad()*((k.getSueldoCargo()/baseHorasVieja)*3.5);
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 61://Horas extras simples nocturnas
                           importe=i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja)*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 62://Horas extras feriado no laborable nocturno
                           importe=i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja)*4.5 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 63://Horas extras feriado laborable nocturno
                           importe=i.getCantidad()*(k.getSueldoCargo()/baseHorasVieja)*3.5 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 70://Feriado no laborable nocturno
                           importe=i.getCantidad()*(k.getSueldoCargo()/30)*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       case 71://Feriado laborable noc
                           importe=i.getCantidad()*(sueldoRed/30)*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                       default:
                           break;
                   }
                    importe=this.fijarNumero(importe, 2);
                    i.setCodMov(this.buscarCodigo(i.getCod()));
                                                    
                    l = new Liquidacion(k,1,i.getCodMov(),i.getCantidad(),importe,importe,fecha,0);
                    contador+=this.insertoLiquidacion(l);
                    this.liquidacionesMem.add(l);
               }
           }
       }
       
        this.reciboEncero=this.buscoReciboEnCero(k);
        this.tieneHogarConstituido = this.tieneHogarConstituido(k);
        this.antiguedad = this.antiguedad(k);
        this.quebranto = this.tieneQuebranto(k);
        
        if(reciboEncero && tieneHogarConstituido!=null){
            if(tieneHogarConstituido.getImporte()!=0){
                c=this.buscarCodigo(29);
                l = new Liquidacion(k,1,c,1.0,this.fijarNumero(tieneHogarConstituido.getImporte(),2),this.fijarNumero(tieneHogarConstituido.getImporte(),2),fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
            
        }
        if(reciboEncero && antiguedad!=null){
            if(antiguedad.getImporte()!=0){
                c=this.buscarCodigo(28);
                l = new Liquidacion(k,1,c,antiguedad.getCantidad(),this.fijarNumero(antiguedad.getImporte(),2),this.fijarNumero(antiguedad.getImporte(),2),fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
        }
        if(reciboEncero && quebranto!=null){
            this.eliminoDeLiquidacion(k,44);
        }
       //---------------------------------------------------------------------------------------//
       
       //----------------------------DEPOSITO DE QUEBRANTO DE CAJA------------------------------//
       
          this.totalHaberesBps = this.cargoTotalHaberesBps(k);
          this.totalHaberesIrpf = this.cargoTotalHaberesIrpf(k);
          this.totalHaberesGeneral = this.cargoTotalHaberesGeneral(k);
          this.totalHaberesNoBps = this.totalHaberesGeneral-totalHaberesBps;
          this.totalHaberesNoBps = this.fijarNumero(this.totalHaberesNoBps, 2);
          this.totalHaberesBpsOld = this.cargoTotalHaberesBpsOld(k,fecha);
          
       if(quebranto!=null){
          if(k.getAfap()==1){
           if(this.fijarNumero(this.totalHaberesBps+this.totalHaberesBpsOld,2)>=this.fijarNumero(this.topeAfap,2)){
               if(this.fijarNumero(this.totalHaberesBpsOld,2)>=this.fijarNumero(this.topeAfap,2)){
                   this.totDipaico=this.topeAfap*this.dipaico;
                   this.totDipaico=fijarNumero(totDipaico, 2);
                   
                   this.tasaDipaico=this.totDipaico/(this.totalHaberesBps+totalHaberesBpsOld);
                   this.tasaDipaico1=0;
                   
               }
               else{
                   this.difDipaico=topeAfap-totalHaberesBpsOld;
                   this.totDipaico1=this.fijarNumero(topeAfap*dipaico, 2)-this.fijarNumero(totalHaberesBpsOld*dipaico, 2);
                   this.totDipaico1=this.fijarNumero(totDipaico1, 2);
                   this.tasaDipaico1=totDipaico1/totalHaberesBps;
                   this.totDipaico=this.fijarNumero(topeAfap*dipaico, 2);
                   this.tasaDipaico=totDipaico/(totalHaberesBps+totalHaberesBpsOld);    
               }
           }
           else{
               tasaDipaico = dipaico;
               tasaDipaico1 = tasaDipaico;
            }
         }else{
               tasaDipaico = dipaico;
               tasaDipaico1 = tasaDipaico;
          }
       
       //this.impDipaico=this.quebranto.getImporte()*tasaDipaico;
       String t = String.valueOf(tasaDipaico);   
       java.math.BigDecimal tasa = new java.math.BigDecimal(t);
       java.math.BigDecimal quebr = new java.math.BigDecimal(String.valueOf(quebranto.getImporte())); 
       java.math.BigDecimal impd = tasa.multiply(quebr);
       this.impDipaico = impd.doubleValue();
       this.impDipaico=this.fijarNumero(impDipaico, 2);
       

       t = String.valueOf(totalHaberesBps);
       java.math.BigDecimal tasa1 = new java.math.BigDecimal(String.valueOf(tasaDipaico1));
       java.math.BigDecimal totHab = new java.math.BigDecimal(String.valueOf(t)); 
       java.math.BigDecimal impd1 = tasa1.multiply(totHab);
       this.impDipaico1 = impd1.doubleValue();
       this.impDipaico1=this.fijarNumero(impDipaico1, 2);
       
       
       this.fonasa=this.buscoFonasaViejo(k.getCodFunc(),fecha);
       
       if((this.totalHaberesBps+totalHaberesBpsOld)<(2.5*this.bpcActual)){
           Integer sns=k.getSns().getCodigo();
           if(sns==31 || sns==33 || sns==35 || sns==37){
               fonasa=fonasa-0.02;
           }
           else{
               fonasa=fonasa-0.005;
           }
       }
             
      t = String.valueOf(fonasa);
      java.math.BigDecimal fona = new java.math.BigDecimal(t);
      java.math.BigDecimal impFo = fona.multiply(quebr);
      this.impFonasa = impFo.doubleValue();
      this.impFonasa=this.fijarNumero(impFonasa, 2);
      
      

    
      java.math.BigDecimal impFo1 = fona.multiply(totHab);
      this.impFonasa1 = impFo1.doubleValue();
      this.impFonasa1=this.fijarNumero(impFonasa1, 2);
      
  
      t = String.valueOf(this.frl);
      java.math.BigDecimal fr = new java.math.BigDecimal(t);
      java.math.BigDecimal impFr = fr.multiply(quebr);
      this.impFrl = impFr.doubleValue();
      this.impFrl=this.fijarNumero( this.impFrl, 2);

  
      java.math.BigDecimal impFr1 = fr.multiply(totHab);
      this.impFrl1 = impFr1.doubleValue();
      this.impFrl1=this.fijarNumero( this.impFrl1, 2);
 
       
       if(this.hacerAjusteAnual){
           this.calcularIrpfAnual(k, fecha,false);
       }else{
           this.calcularIrpfMensualOld(k, fecha,false);
       }
       
       this.impIrpf=this.quebranto.getImporte()*this.irpfTasa;
       this.impIrpf=this.fijarNumero(impIrpf, 2);
       
       this.impDesc=this.impDipaico+this.impFrl+this.impFonasa+this.impIrpf;
       this.impDesc=this.fijarNumero(this.impDesc, 2);
       
       double queb = this.fijarNumero(this.quebranto.getImporte(),2);
//       java.math.BigDecimal impo = new java.math.BigDecimal(queb);
//       java.math.BigDecimal impd = new java.math.BigDecimal(String.valueOf(impDesc)); 
//       java.math.BigDecimal div = new java.math.BigDecimal(2);
//       
//       BigDecimal res = impo.subtract(impd);
//       res=res.divide(div);
//       this.depQueb=res.doubleValue();
       queb=queb-impDesc;
       queb=this.fijarNumero(queb, 2);
       queb=queb/2;
       
       this.depQueb=this.fijarNumero(queb,2);
       
       c=this.buscarCodigo(315);
       l = new Liquidacion(k,1,c,1.0,depQueb,depQueb,fecha,0);
       contador+=this.insertoLiquidacion(l);
       this.liquidacionesMem.add(l);
    }
       //---------------------------------------------------------------------------------------//
        
      //----------------------------CARGA DE RETENCIONES FIJAS------------------------------//
       ArrayList<Integer> retenciones = this.cargoHistRetencionesFijas(k,fecha);
       if(retenciones.size()>0){
           for(Integer r:retenciones){
                c=this.buscarCodigo(r);
                l = new Liquidacion(k,1,c,1.0,0.0,0.0,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
           }
           
       }
       //---------------------------------------------------------------------------------------//
       
       //----------------------------------CARGA DE VALES---------------------------------------//
       ArrayList<Integer> vales = this.cargovalesDeReLiquidaciones(k);
       if(vales.size()>0){
           for(Integer v:vales){
                c=this.buscarCodigo(501);
                l = new Liquidacion(k,1,c,1.0,0.0,0.0,fecha,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
           }
           
       }
       //---------------------------------------------------------------------------------------//
       
       //----------------------------------PROCESO DE LIQUIDACION---------------------------------------//
       
       //APLICO IMPUESTOS
 
           if(k.getAfap()==1){
               if(this.fijarNumero(totalHaberesBps+totalHaberesBpsOld,2)>=this.fijarNumero(topeAfap, 2)){
                 if(this.fijarNumero(totalHaberesBpsOld,2)>=this.fijarNumero(topeAfap, 2)){
                    
                    this.impDipaico=this.fijarNumero(0, 2);
                 }else{
                    String to=String.valueOf(totalHaberesBpsOld);
                    String t=String.valueOf(topeAfap);
                    String di =String.valueOf(dipaico);
                    
                    java.math.BigDecimal ta= new java.math.BigDecimal(t);
                    java.math.BigDecimal totHabBps = new java.math.BigDecimal(to);
                    java.math.BigDecimal tot = ta.subtract(totHabBps);
                    difDipaico=tot.doubleValue(); 
                    
                    java.math.BigDecimal dip= new java.math.BigDecimal(di);
                    tot=ta.multiply(dip);
                    double aux=tot.doubleValue();
                    aux=this.fijarNumero(aux, 2);
                    
                    tot=dip.multiply(totHabBps);
                    double aux2=tot.doubleValue();
                    aux2=this.fijarNumero(aux2, 2);
                    impDipaico=aux-aux2;
                    impDipaico=this.fijarNumero(impDipaico, 2);
                 }
               }else{
                    String to=String.valueOf(totalHaberesBpsOld);
                    String di =String.valueOf(dipaico);
                    java.math.BigDecimal dip= new java.math.BigDecimal(di);
                    java.math.BigDecimal totHabBps = new java.math.BigDecimal(to);
                    java.math.BigDecimal tot = dip.multiply(totHabBps);
                    impDipaico =tot.doubleValue();
                    impDipaico=this.fijarNumero(impDipaico, 2);
                             
               }
           }
           else{
                String to=String.valueOf(totalHaberesBpsOld);
                String di =String.valueOf(dipaico);
                java.math.BigDecimal dip= new java.math.BigDecimal(di);
                java.math.BigDecimal totHabBps = new java.math.BigDecimal(to);
                java.math.BigDecimal tot = dip.multiply(totHabBps);
                impDipaico =tot.doubleValue();
                impDipaico=this.fijarNumero(impDipaico, 2);
           }
//kk
       
      this.casenpaceVoluntario = this.totalHaberesBps*this.porcentajeCasenpaceVol;
      this.casenpaceVoluntario = this.fijarNumero(this.casenpaceVoluntario, 2);//OK
      
      this.fonasa=this.buscoFonasaViejo(k.getCodFunc(),fecha);
     
      if((this.totalHaberesBps+totalHaberesBpsOld)<=(2.5*this.bpcActual)){
          Integer sns = k.getSns().getCodigo();
          if(sns==33||sns==31||sns==35||sns==37){
              this.fonasa=fonasa-0.02;
          }else {
              this.fonasa=fonasa-0.005;
          }
          
      }
        
      java.math.BigDecimal dip= BigDecimal.valueOf(fonasa);
      java.math.BigDecimal totHabBps = BigDecimal.valueOf(totalHaberesBps);
      java.math.BigDecimal tot = totHabBps.multiply(dip);
      
      
      impFonasa=tot.doubleValue();
      this.impFonasa=this.fijarNumero(this.impFonasa, 2);//OK
      
      java.math.BigDecimal f= BigDecimal.valueOf(frl);
      tot = totHabBps.multiply(f);
      
      this.impFrl=tot.doubleValue();
      this.impFrl=this.fijarNumero(this.impFrl,2);//OK
      
      if(this.hacerAjusteAnual){
           //this.calcularIrpfAnual(k, fechaLiq,true);
      }else{
          this.calcularIrpfMensualOld(k,fecha,true);
      }
      
      //dipaico
      c=this.buscarCodigo(201);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impDipaico, 2),this.fijarNumero(impDipaico, 2),fecha,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      //fonasa
      c=this.buscarCodigo(205);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFonasa, 2),this.fijarNumero(impFonasa, 2),fecha,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      //frl
      c=this.buscarCodigo(204);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFrl, 2),this.fijarNumero(impFrl, 2),fecha,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      //irpfneto
      c=this.buscarCodigo(207);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(irpfNeto, 2),this.fijarNumero(irpfNeto, 2),fecha,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      if(casenpaceVoluntario!=0){
          c=this.buscarCodigo(515);
          l = new Liquidacion(k,1,c,1.0,this.fijarNumero(casenpaceVoluntario, 2),this.fijarNumero(casenpaceVoluntario, 2),fecha,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }
      
      
      //CALCULO DE LIQUIDO LEGAL Y LIQUIDO MINIMO
      this.montoGratificacionesRaras=this.cargoGratificacionesRaras(k);
      this.montoGratificacionesRaras=this.fijarNumero(this.montoGratificacionesRaras, 2);
    
      
      this.liquidoLegal=this.totalHaberesBps-(impDipaico+irpfNeto+impFrl+impFonasa)+montoGratificacionesRaras;
      this.liquidoLegal=this.fijarNumero(this.liquidoLegal, 2);

      

     
          this.liquidoMinimo=liquidoLegal*porcentajeLiquidoMinimo;
          this.liquidoMinimo=this.fijarNumero(this.liquidoMinimo, 2);
          
          this.descontableExtra=porcentajeMinimoExtra*liquidoLegal;
          this.descontableExtra = this.fijarNumero(this.descontableExtra, 2);
          
          this.liquidoDescontable = this.liquidoLegal-this.liquidoMinimo;
          this.liquidoDescontable=this.fijarNumero(this.liquidoDescontable, 2);
          this.liquidoFinal = this.fijarNumero(liquidoLegal, 2);
     
          
      
      
      //RETENCIONES JUDICIALES ALIMENTICIAS
      
      ArrayList<Retencion> retenJud = this.cargoRetencionesJudicialesReLiq(k,fecha);
      
      for(Retencion r : retenJud){
          double importeRetencion=0;
          if(r.getTipo()==0){//retencion de tipo fijo
                importeRetencion=this.fijarNumero(r.getImporte(),2);
          }else{//es una retencion con porcentaje
               if(r.getSobre()==0){//se aplica el porcentaje sobre el total de haberes
                   importeRetencion=this.totalHaberesBps*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
               }else{//se aplica el porcentaje sobre el liquido legal
                   importeRetencion=this.liquidoLegal*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
                   
                        if(k.getCodFunc()==3540){
                           Liquidacion liq = this.cargoLineaLiq(k,42);
                           if(liq.getImporte()!=0){
                               tasaDescuentoGeneral=1-(liquidoLegal/totalHaberesBps);
                               //tasaDescuentoGeneral=this.fijarNumero(tasaDescuentoGeneral, 2);
                               hogarDescuentos=liq.getImporte()*tasaDescuentoGeneral;
                               hogarDescuentos=this.fijarNumero(hogarDescuentos, 2);
                               restoRetencion=(liq.getImporte()-hogarDescuentos)*0.15;
                               //restoRetencion=this.fijarNumero(restoRetencion, 2);
                               importeRetencion=importeRetencion+restoRetencion;
                               importeRetencion=this.fijarNumero(importeRetencion, 2);
                           }
                        }else{
                              if(k.getCodFunc()==3160){
                                Liquidacion liq = this.cargoLineaLiq(k,42);
                                    if(liq.getImporte()!=0){
                                        tasaDescuentoGeneral=1-(liquidoLegal/totalHaberesBps);
                                        //tasaDescuentoGeneral=this.fijarNumero(tasaDescuentoGeneral, 2);
                                        hogarDescuentos=liq.getImporte()*tasaDescuentoGeneral;
                                        hogarDescuentos=this.fijarNumero(hogarDescuentos, 2);
                                        restoRetencion=(liq.getImporte()-hogarDescuentos)*0.25;
                                        //restoRetencion=this.fijarNumero(restoRetencion, 2);
                                        importeRetencion=importeRetencion+restoRetencion;
                                        importeRetencion=this.fijarNumero(importeRetencion, 2);
                                    }
                                }
                       }
               }
           
               if(liquidoDescontable>=importeRetencion){
                   liquidoDescontable=liquidoDescontable-importeRetencion;
                   liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                   liquidoFinal=liquidoFinal-importeRetencion;
                   liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                   descuentoRechazado=0;
                   descuentoAplicado=this.fijarNumero(importeRetencion, 2);                           
               }else{
                   descuentoRechazado=importeRetencion-liquidoDescontable;
                   descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                   descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                   liquidoFinal=liquidoFinal-descuentoAplicado;
                   liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                   liquidoDescontable=0;
               }
               
               
               
               
               
          }
          //INSERTO
          c=this.buscarCodigo(310);
          l = new Liquidacion(k,1,c,1.0,this.fijarNumero(importeRetencion, 2),this.fijarNumero(importeRetencion, 2),fecha,0);
          contador+=this.insertoLiquidacion(l);
         this.liquidacionesMem.add(l);
     }
      
      //APLICO CODIGOS DE DESCUENTOS
      liquidaciones =this.cargoDescuentos(k);
      this.cargoCodigos();
      this.reordeno();
      int r=1;
      for(Liquidacion s:liquidaciones){
          r+=this.actualizoOrdenLiq(s,r,k);
          contador++;
          if(s.getCod()==401){//vales
              if(s.getImporte()>liquidoMinimo){
                  restoVale=s.getImporte()-liquidoMinimo;
                  restoVale=this.fijarNumero(restoVale, 2);
                  importeADescontar=this.fijarNumero(liquidoMinimo, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  restoVale=0;
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }
          }else if(s.getCod()==501){//resto de vales
              if(restoVale!=0){
                  importeADescontar=this.fijarNumero(restoVale,2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  importeADescontar=0;
              }
          }else{
              if(s.getImporte()!=0){
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }else{
                  importeADescontar=this.cargoImporteRetFijaReLiq(k,s.getCod(),totalHaberesBps,liquidoLegal,fecha);
                  importeADescontar=this.fijarNumero(importeADescontar, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }
          }
         
          if(s.getCod()!=401){
              if(s.getCod()==355){
                  this.seg_ace=this.totalSeguroAce(k);
                  if((liquidoDescontable+seg_ace)>=importeADescontar){
                      liquidoDescontable=(liquidoDescontable+seg_ace)-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=(liquidoFinal+seg_ace)-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{
                        descuentoRechazado=importeADescontar-(liquidoDescontable+seg_ace);
                        descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                        descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                        liquidoFinal=liquidoFinal-descuentoAplicado;
                        liquidoFinal=this.fijarNumero(liquidoFinal, 0);
                        liquidoDescontable=0;
                      
                  }
              }else{
                  //aca cambio de 5 por ciento extra de liquido minimo
                  if(liquidoDescontable>=importeADescontar){         
                      liquidoDescontable=liquidoDescontable-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=liquidoFinal-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{
                        descuentoRechazado=importeADescontar-liquidoDescontable;
                        descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                        descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                        liquidoFinal=liquidoFinal-descuentoAplicado;
                        liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                        liquidoDescontable=0;
                      
                  }
              }
          }else{
              if(liquidoFinal>=importeADescontar){                  
                    liquidoFinal=liquidoFinal-importeADescontar;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                    descuentoRechazado=0;
                    descuentoAplicado=this.fijarNumero(importeADescontar, 2);
              }else{       
                    descuentoRechazado=importeADescontar-liquidoFinal;
                    descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                    descuentoAplicado=this.fijarNumero(liquidoFinal, 2);
                    liquidoFinal=liquidoFinal-descuentoAplicado;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
              }
          }
          
         contador+=this.actualizoLiquidacion2(k,s.getCod(),descuentoRechazado); 
      }
      
      
      totalDescuentos=totalHaberesGeneral-liquidoFinal;
      totalDescuentos=this.fijarNumero(totalDescuentos, 2);
      
      //calculo e inserto redondeos
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      Double val = liquidoFinal;
      int valLiqFinal = val.intValue();
      
      if((liquidoFinal-valLiqFinal)!=0){
          redondeo = 1-(liquidoFinal-valLiqFinal);
          redondeo=this.fijarNumero(redondeo, 2);
          c=this.buscarCodigo(600);
          l = new Liquidacion(k,1,c,1.0,redondeo,redondeo,fecha,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }else{
          redondeo=0;
      }
      
      liquidoFinal=liquidoFinal+redondeo;
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      totalNoRetenido=this.buscarTotalNoRetenido(k);
      k.getCentroCosto().setCodigo(this.buscoCentroCostoOld(k,fecha));
      
      double veinteTotalHaberesBps=this.totalHaberesBps*porcentajeSacec;
      veinteTotalHaberesBps = this.fijarNumero(veinteTotalHaberesBps, 2);
      Liquidacion li = this.obtengoLiq(461);
      if(li!=null){
      if(this.fijarNumero(li.getImporte(), 2)>veinteTotalHaberesBps){
        l.setImporte(veinteTotalHaberesBps);
        contador+=this.actualizoIngreso(k, 461, veinteTotalHaberesBps );
        cnn.commit();
        cnn.close();
        cnn=null;
        this.comienzoLiquidación(k, fecha);
       }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fecha, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
      }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fecha, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
    }

    private double cargoSueldoReliq(Funcionario k, Date fechaLiq)throws BDExcepcion {
        try {
            double retorno = 0;
      
            String consulta="select sueldo_cargo from pers_hist_funcionarios where (codigo=? and fechaliq=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,k.getCodFunc());
            ps.setString(2, this.convertirFecha(fechaLiq)); 
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                retorno=rs.getDouble("sueldo_cargo"); 
                retorno=this.fijarNumero(retorno, 2);
            }
            
            if(retorno==0 || k.getSueldoCargo()==0){
                
            }else{
                retorno=k.getSueldoCargo()-retorno;
                retorno=this.fijarNumero(retorno, 0);
            }
            
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private Integer cargoBaseHorariaVieja(Date fechaLiq, Funcionario k)throws BDExcepcion {
         try {
            Integer retorno = 44;
      
            String consulta="select base_horaria from pers_hist_funcionarios where (codigo=? and fechaliq=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,k.getCodFunc());
            ps.setString(2, this.convertirFecha(fechaLiq)); 
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                retorno=rs.getInt("base_horaria"); 
            }
            
           
            
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private double buscoBaseHorasVieja(Integer codFunc, Date fechaLiq)throws BDExcepcion  {
        try {
            double retorno = 240;
      
            String consulta="select base_horas from pers_hist_funcionarios where (codigo=? and fechaliq=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,codFunc);
            ps.setString(2, this.convertirFecha(fechaLiq)); 
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                retorno=rs.getDouble("base_horas"); 
            }
                       
            ps.close();
            rs.close();
            
            return retorno;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private double cargoTotalHaberesBpsOld(Funcionario k, Date fechaLiq)throws BDExcepcion {
        try {
            double totalHaberes=0;
            String consulta="select total_haberes_bps from pers_hist_resumen where (cod_func=? and fechaliq=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setString(2, this.convertirFecha(fechaLiq));
         
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                   totalHaberes=rs.getDouble("total_haberes_bps");
            }
         
            ps.close();
            rs.close();
            
            return totalHaberes;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private double buscoFonasaViejo(Integer codFunc, Date fechaLiq)throws BDExcepcion {
         try {
            double retorno =0;
            int aux=0;
            String consulta="select sns from pers_hist_funcionarios where (codigo=? and fechaliq=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,codFunc);
            ps.setString(2, this.convertirFecha(fechaLiq)); 
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                aux=rs.getInt("sns"); 
            }
            consulta="select fonasa from pers_sns where codigo=?";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1,aux);
            rs=ps.executeQuery();
            if(rs.next()) {
                retorno=rs.getDouble("fonasa"); 
            }
            
            ps.close();
            rs.close();
            
            return retorno;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private Integer buscoCentroCostoOld(Funcionario k,Date fechaLiq)throws BDExcepcion{
        try {
           
            int aux=0;
            String consulta="select centro_costo from pers_hist_funcionarios where (codigo=? and fechaliq=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1,k.getCodFunc());
            ps.setString(2, this.convertirFecha(fechaLiq)); 
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                aux=rs.getInt("centro_costo"); 
            }
           
            ps.close();
            rs.close();
            
            return aux;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

     //-----------------------------------------------------------------------------------------------------//
    //--------------------------------------LIQUIDACION DE VACACIONALES--------------------------------------//

    private Date armoFechaGeneracion(Date fechaLiq) {
        Date retorno;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaLiq);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.MONTH,11);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)-1 );
        retorno=cal.getTime();
        
        return retorno;
    }
    
    public void liquidarVacacionales(Funcionario k, Date fechaLiq) throws BDExcepcion, ParseException, SQLException, ClassNotFoundException{
        this.limpioTablas(k); 
        this.limpioPromedios(k);
        this.cargoPromedios(k,fechaLiq);
        this.reconecto();
        Codigo c = null;
        Liquidacion l = null;
        liquidacionesMem=new ArrayList<>();
        double valorAntiguedadTotal = 0;
        Licencia lic;
        double montoVacacional=k.getSueldoCargo();
        double montoDescuento;
        this.anosAntiguedad=this.calculoAntiguedad(k.getFechaIngreso(), fechaLiq);
        
        if(anosAntiguedad>0){
            valorAntiguedadTotal=this.fijarNumero(this.primaAntiguedad*anosAntiguedad,2);
            montoVacacional=this.fijarNumero(montoVacacional+valorAntiguedadTotal, 2);
        }
        
        if(tieneCodFijo(k.getCodFunc(), 42)){
            montoVacacional=this.fijarNumero(montoVacacional+hogarConstituido, 2);
        }
        
        if(k.getBaseHoraria()!=39){
            double aux=k.getSueldoCargo()/39;
            aux=aux*k.getBaseHoraria();
            aux=(this.fijarNumero(aux, 2));
            int reduccion=(int) (k.getSueldoCargo()-aux);
            montoVacacional=this.fijarNumero(montoVacacional-reduccion, 2);
        }
        
       lic=this.cargaLicencia(k.getCodFunc(),fechaGeneracion);
        
      if(lic!=null){
        if(lic.getDiasGenerados()<20){
            montoVacacional=montoVacacional/20;
            montoVacacional=montoVacacional*lic.getDiasGenerados();
            montoVacacional=this.fijarNumero(montoVacacional, 2);
        }
        
      c=this.buscarCodigo(4);
      l = new Liquidacion(k,1,c,Double.valueOf(lic.getDiasGenerados()),this.fijarNumero(montoVacacional, 2),this.fijarNumero(montoVacacional, 2),fechaLiq,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
         
        if(lic.getDiasDescuento()!=0){
            montoDescuento=montoVacacional/lic.getDiasGenerados();
            montoDescuento=montoDescuento*lic.getDiasDescuento();
            montoDescuento=this.fijarNumero(montoDescuento, 2);
            
            c=this.buscarCodigo(50);
            l = new Liquidacion(k,1,c,Double.valueOf(lic.getDiasDescuento()),this.fijarNumero(montoDescuento, 2),this.fijarNumero(montoDescuento, 2),fechaLiq,0);
            contador+=this.insertoLiquidacion(l);
            this.liquidacionesMem.add(l);
        }
     
       }
       
      this.cargoIngresos(k);
      
      if(ingresos!=null){
           if(ingresos.size()>0){
               for(Ingreso i: ingresos){
                   Double importe = this.fijarNumero(i.getImporte(), 2);
                 
                   if(null!=i.getCod())switch (i.getCod()) {
                      case 24://Promedio horas extras de licencia
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                 
                      case 46://Promedio horas extras feriado no laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*4.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 47://Promedio horas extras feriado laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*3.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 81://prom hs extras simples NOCTURNAS
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras())*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 82://PROM hs extras feriado no lab NOC
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras())*4.5 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 83://PROM Horas Extras Feriados Laborables
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras())*3.5 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      default:
                           break;
                   }
                    importe=this.fijarNumero(importe, 2);
                    i.setCodMov(this.buscarCodigo(i.getCod()));
                  
                    
                    l = new Liquidacion(k,1,i.getCodMov(),i.getCantidad(),importe,importe,fechaLiq,0);
                    contador+=this.insertoLiquidacion(l);
                    this.liquidacionesMem.add(l);
               }
           }
       }
     
      boolean tieneSalarioVac=this.tieneSalarioVacEnLiq(k);
       //----------------------------CARGA DE RETENCIONES FIJAS------------------------------//
       ArrayList<Integer> retenciones = this.cargoRetencionesFijasVacacional(k);
       if(retenciones.size()>0){
           if(tieneSalarioVac){
                for(Integer r:retenciones){
                     c=this.buscarCodigo(r);
                     l = new Liquidacion(k,1,c,1.0,0.0,0.0,fechaLiq,0);
                     contador+=this.insertoLiquidacion(l);
                     this.liquidacionesMem.add(l);
                }
           }
       }
       //---------------------------------------------------------------------------------------//
       //----------------------------------CARGA DE VALES---------------------------------------//
       ArrayList<Integer> vales = this.cargovalesDeLiquidaciones(k);
       if(vales.size()>0){
           for(Integer v:vales){
                c=this.buscarCodigo(501);
                l = new Liquidacion(k,1,c,1.0,0.0,0.0,fechaLiq,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
           }
           
       }
       //---------------------------------------------------------------------------------------//
         this.totalHaberesBps = this.cargoTotalHaberesBps(k);
          //this.totalHaberesBps=this.fijarNumero(this.totalHaberesBps, 2);
          this.totalHaberesIrpf = this.cargoTotalHaberesIrpf(k);
          //this.totalHaberesIrpf = this.fijarNumero(this.totalHaberesIrpf, 2);
          this.totalHaberesGeneral = this.cargoTotalHaberesGeneral(k);
          //this.totalHaberesGeneral = this.fijarNumero(this.totalHaberesGeneral, 2);
          this.totalHaberesNoBps = this.totalHaberesGeneral-totalHaberesBps;
          this.totalHaberesNoBps = this.fijarNumero(this.totalHaberesNoBps, 2);
       //----------------------------------PROCESO DE LIQUIDACION---------------------------------------//
       
       //APLICO IMPUESTOS
       if(this.totalHaberesBps>=topeAfap){
           if(k.getAfap()==1){
                String di=String.valueOf(dipaico);
                String t=String.valueOf(topeAfap);
                java.math.BigDecimal dip= new java.math.BigDecimal(di);
                java.math.BigDecimal totAf = new java.math.BigDecimal(t);
                java.math.BigDecimal tot = totAf.multiply(dip);
                impDipaico=tot.doubleValue(); 
                this.impDipaico=this.fijarNumero(this.impDipaico, 2);
           }
           else{
                String di=String.valueOf(dipaico);
                String t=String.valueOf(totalHaberesBps);
                java.math.BigDecimal dip= new java.math.BigDecimal(di);
                java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
                java.math.BigDecimal tot = totHabBps.multiply(dip);
                impDipaico=tot.doubleValue();
                this.impDipaico=this.fijarNumero(this.impDipaico, 2);
           }
           
       }
       else{
             String di=String.valueOf(dipaico);
             String t=String.valueOf(totalHaberesBps);
             java.math.BigDecimal dip= new java.math.BigDecimal(di);
             java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
             java.math.BigDecimal tot = totHabBps.multiply(dip);
             impDipaico=tot.doubleValue();
             this.impDipaico=this.fijarNumero(impDipaico, 2);
      
       }
       
//      this.casenpaceVoluntario = this.totalHaberesBps*this.porcentajeCasenpaceVol;
//      this.casenpaceVoluntario = this.fijarNumero(this.casenpaceVoluntario, 2);//OK
      
      this.fonasa=k.getSns().getFonasa();
      
      if(this.totalHaberesBps<=(2.5*this.bpcActual)){
             this.fonasa=0.025;
      }
      
  
      java.math.BigDecimal dip= BigDecimal.valueOf(fonasa);
      java.math.BigDecimal totHabBps = BigDecimal.valueOf(totalHaberesBps);
      java.math.BigDecimal tot = totHabBps.multiply(dip);

      impFonasa=tot.doubleValue();
      this.impFonasa=this.fijarNumero(this.impFonasa, 2);//OK
      
      this.impFrl=this.totalHaberesBps*this.frl;
      this.impFrl=this.fijarNumero(this.impFrl,2);//OK
       
      this.calcularIrpfMensual(k,fechaLiq,true);
      
      
      //dipaico
      if(impDipaico!=0){
        c=this.buscarCodigo(201);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impDipaico, 2),this.fijarNumero(impDipaico, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      }
      //fonasa
      if(impFonasa!=0){
        c=this.buscarCodigo(205);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFonasa, 2),this.fijarNumero(impFonasa, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      }
      //frl
      if(impFrl!=0){
        c=this.buscarCodigo(204);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFrl, 2),this.fijarNumero(impFrl, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      }
      //irpfneto
      c=this.buscarCodigo(207);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(irpfNeto, 2),this.fijarNumero(irpfNeto, 2),fechaLiq,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
          
      //CALCULO DE LIQUIDO LEGAL Y LIQUIDO MINIMO
    
      
      this.liquidoLegal=this.totalHaberesGeneral-(impDipaico+irpfNeto+impFrl+impFonasa);
      this.liquidoLegal=this.fijarNumero(this.liquidoLegal, 2);
      
      this.liquidoMinimo=liquidoLegal*porcentajeLiquidoMinimo;
      this.liquidoMinimo=this.fijarNumero(liquidoMinimo, 2);
      
      this.liquidoDescontable=liquidoLegal-liquidoMinimo;
      this.liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
      this.liquidoFinal=this.fijarNumero(liquidoLegal, 2);
          
      //RETENCIONES JUDICIALES ALIMENTICIAS
      ArrayList<Retencion> retenJud = this.cargoRetencionesJudicialesVacacional(k);
      
      for(Retencion r : retenJud){
          if(tieneSalarioVac){
          double importeRetencion=0;
          if(r.getTipo()==0){//retencion de tipo fijo
                importeRetencion=this.fijarNumero(r.getImporte(),2);
          }else{//es una retencion con porcentaje
               if(r.getSobre()==0){//se aplica el porcentaje sobre el total de haberes
                   importeRetencion=this.totalHaberesBps*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
               }else{//se aplica el porcentaje sobre el liquido legal
                   importeRetencion=this.liquidoLegal*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
                   
                        if(k.getCodFunc()==3540){
                           Liquidacion liq = this.cargoLineaLiq(k,42);
                           if(liq.getImporte()!=0){
                               tasaDescuentoGeneral=1-(liquidoLegal/totalHaberesBps);
                               //tasaDescuentoGeneral=this.fijarNumero(tasaDescuentoGeneral, 2);
                               hogarDescuentos=liq.getImporte()*tasaDescuentoGeneral;
                               hogarDescuentos=this.fijarNumero(hogarDescuentos, 2);
                               restoRetencion=(liq.getImporte()-hogarDescuentos)*0.15;
                               //restoRetencion=this.fijarNumero(restoRetencion, 2);
                               importeRetencion=importeRetencion+restoRetencion;
                               importeRetencion=this.fijarNumero(importeRetencion, 2);
                           }
                        }else{
                              if(k.getCodFunc()==3160){
                                Liquidacion liq = this.cargoLineaLiq(k,42);
                                    if(liq.getImporte()!=0){
                                        tasaDescuentoGeneral=1-(liquidoLegal/totalHaberesBps);
                                        //tasaDescuentoGeneral=this.fijarNumero(tasaDescuentoGeneral, 2);
                                        hogarDescuentos=liq.getImporte()*tasaDescuentoGeneral;
                                        hogarDescuentos=this.fijarNumero(hogarDescuentos, 2);
                                        restoRetencion=(liq.getImporte()-hogarDescuentos)*0.25;
                                        //restoRetencion=this.fijarNumero(restoRetencion, 2);
                                        importeRetencion=importeRetencion+restoRetencion;
                                        importeRetencion=this.fijarNumero(importeRetencion, 2);
                                    }
                                }
                       }
              }
                            
          }
          if(k.getCodFunc()==3400){
              if(liquidoFinal>=importeRetencion){
                  liquidoFinal=liquidoFinal-importeRetencion;
                  liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                  descuentoRechazado=0;
                  descuentoAplicado=this.fijarNumero(importeRetencion, 2);
                  liquidoDescontable=liquidoDescontable-importeRetencion;
                  liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                  if(liquidoDescontable<0){
                      liquidoDescontable=0;
                  }
              }else{
                  descuentoRechazado=importeRetencion-liquidoFinal;
                  descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                  descuentoAplicado=this.fijarNumero(liquidoFinal, 2);
                  liquidoFinal=liquidoFinal-descuentoAplicado;
                  liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                  liquidoDescontable=0;
              }
          }else{
                if(liquidoDescontable>=importeRetencion){
                     liquidoDescontable=liquidoDescontable-importeRetencion;
                     liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                     liquidoFinal=liquidoFinal-importeRetencion;
                     liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                     descuentoRechazado=0;
                     descuentoAplicado=this.fijarNumero(importeRetencion, 2);                           
                 }else{
                     descuentoRechazado=importeRetencion-liquidoDescontable;
                     descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                     descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                     liquidoFinal=liquidoFinal-descuentoAplicado;
                     liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                     liquidoDescontable=0;
                }
          }
          //INSERTO
          c=this.buscarCodigo(310);
          l = new Liquidacion(k,1,c,1.0,this.fijarNumero(importeRetencion, 2),this.fijarNumero(importeRetencion, 2),fechaLiq,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }
     }
      
      //APLICO CODIGOS DE DESCUENTOS
      liquidaciones =this.cargoDescuentos(k);
      this.cargoCodigos();
      this.reordeno();
      int r=1;
      
      for(Liquidacion s:liquidaciones){
       
          r+=this.actualizoOrdenLiq(s,r,k);
          contador++;
          if(s.getCod()==518){
              int ss=0;
          }
          
          if(s.getCod()==401){//vales
              if(s.getImporte()>liquidoMinimo){
                  restoVale=s.getImporte()-liquidoMinimo;
                  restoVale=this.fijarNumero(restoVale, 2);
                  importeADescontar=this.fijarNumero(liquidoMinimo, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  restoVale=0;
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }
          }else if(s.getCod()==501){//resto de vales
              if(restoVale!=0){
                  importeADescontar=this.fijarNumero(restoVale,2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  importeADescontar=0;
              }
          }else{
              if(s.getImporte()!=0){
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }else{
                  importeADescontar=this.cargoImporteRetFija(k,s.getCod(),totalHaberesGeneral,liquidoLegal);
                  importeADescontar=this.fijarNumero(importeADescontar, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }
          }
          
          if(s.getCod()!=401){
              if(s.getCod()==355){
                  this.seg_ace=this.totalSeguroAce(k);
                  
                  if((liquidoDescontable+seg_ace)>=importeADescontar){
                      liquidoDescontable=(liquidoDescontable+seg_ace)-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=(liquidoFinal+seg_ace)-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{        
                        descuentoRechazado=importeADescontar-(liquidoDescontable+seg_ace);
                        descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                        descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                        liquidoFinal=liquidoFinal-descuentoAplicado;
                        liquidoFinal=this.fijarNumero(liquidoFinal, 0);
                        liquidoDescontable=0;                   
                  }
              }else{
                  //aca cambio de 5 por ciento extra de liquido minimo
                  if(liquidoDescontable>=importeADescontar){         
                      liquidoDescontable=liquidoDescontable-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=liquidoFinal-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{
                        descuentoRechazado=importeADescontar-liquidoDescontable;
                        descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                        descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                        liquidoFinal=liquidoFinal-descuentoAplicado;
                        liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                        liquidoDescontable=0;
                      
                  }
              }
          }else{
              if(liquidoFinal>=importeADescontar){                  
                    liquidoFinal=liquidoFinal-importeADescontar;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                    descuentoRechazado=0;
                    descuentoAplicado=this.fijarNumero(importeADescontar, 2);
              }else{       
                    descuentoRechazado=importeADescontar-liquidoFinal;
                    descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                    descuentoAplicado=this.fijarNumero(liquidoFinal, 2);
                    liquidoFinal=liquidoFinal-descuentoAplicado;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
              }
          }
          
         contador+=this.actualizoLiquidacion2(k,s.getCod(),descuentoRechazado); 
      }
      
      totalDescuentos=totalHaberesGeneral-liquidoFinal;
      totalDescuentos=this.fijarNumero(totalDescuentos, 2);
      
      //calculo e inserto redondeos
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      Double val = liquidoFinal;
      int valLiqFinal = val.intValue();
      
      if((liquidoFinal-valLiqFinal)!=0){
          redondeo = 1-(liquidoFinal-valLiqFinal);
          redondeo=this.fijarNumero(redondeo, 2);
          c=this.buscarCodigo(600);
          l = new Liquidacion(k,1,c,1.0,redondeo,redondeo,fechaLiq,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }else{
          redondeo=0;
      }
      
      liquidoFinal=liquidoFinal+redondeo;
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      totalNoRetenido=this.buscarTotalNoRetenido(k);
      
      double veinteTotalHaberesBps=this.totalHaberesBps*porcentajeSacec;
      veinteTotalHaberesBps = this.fijarNumero(veinteTotalHaberesBps, 2);
      Liquidacion li = this.obtengoLiq(461);
      if(li!=null){
      if(this.fijarNumero(li.getImporte(), 2)>veinteTotalHaberesBps){
        l.setImporte(veinteTotalHaberesBps);
        contador+=this.actualizoIngreso(k, 461, veinteTotalHaberesBps );
        cnn.commit();
        cnn.close();
        cnn=null;
        this.comienzoLiquidación(k, fechaLiq); 
       }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fechaLiq, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
      }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fechaLiq, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
  }
    
    //-----------------------------------------------------------------------------------------------//
   
    //-----------------------------------------------------------------------------------------------------//
    //--------------------------------------RE LIQUIDACION DE VACACIONALES--------------------------------------//

       
    public void ReliquidarVacacionales(Funcionario k, Date fechaLiq) throws BDExcepcion, ParseException, SQLException, ClassNotFoundException{
        this.limpioTablas(k); 
        this.limpioPromedios(k);
       // this.cargoPromedios(k,fechaLiq);
        this.reconecto();
        Codigo c = null;
        Liquidacion l = null;
        liquidacionesMem=new ArrayList<>();
        double valorAntiguedadTotal = 0;
        Licencia lic;
        double montoVacacional=k.getSueldoCargo();
        double montoDescuento;
        double montoHaberesOriginal;
        double montoVacacionalReLiq;
        double montoDescuentosOriginal;
        double montoDescuentosReLiq;
        
        this.anosAntiguedad=this.calculoAntiguedad(k.getFechaIngreso(), fechaLiq);
        
        if(anosAntiguedad>0){
            valorAntiguedadTotal=this.fijarNumero(this.primaAntiguedad*anosAntiguedad,2);
            montoVacacional=this.fijarNumero(montoVacacional+valorAntiguedadTotal, 2);
        }
        
        if(tieneCodFijo(k.getCodFunc(), 42)){
            montoVacacional=this.fijarNumero(montoVacacional+hogarConstituido, 2);
        }
        
        if(k.getBaseHoraria()!=39){
            double aux=k.getSueldoCargo()/39;
            aux=aux*k.getBaseHoraria();
            aux=(this.fijarNumero(aux, 2));
            int reduccion=(int) (k.getSueldoCargo()-aux);
            montoVacacional=this.fijarNumero(montoVacacional-reduccion, 2);
        }
        
      lic=this.cargaLicencia(k.getCodFunc(),fechaGeneracion);
        
      if(lic!=null){
        if(lic.getDiasGenerados()<20){
            montoVacacional=montoVacacional/20;
            montoVacacional=montoVacacional*lic.getDiasGenerados();
            montoVacacional=this.fijarNumero(montoVacacional, 2);
        }
        
        montoHaberesOriginal=this.buscoSalarioVacacionalPagado(k.getCodFunc(),4,fechaLiq);
        montoHaberesOriginal=montoHaberesOriginal+this.buscoSalarioVacacionalPagado(k.getCodFunc(),51,fechaLiq);
        montoVacacionalReLiq=montoVacacional-montoHaberesOriginal;
        montoVacacionalReLiq=this.fijarNumero(montoVacacionalReLiq, 2);
        
        if(montoVacacionalReLiq!=0){
            c=this.buscarCodigo(51);
            l = new Liquidacion(k,1,c,Double.valueOf(lic.getDiasGenerados()),this.fijarNumero(montoVacacionalReLiq, 2),this.fijarNumero(montoVacacionalReLiq, 2),fechaLiq,0);
            contador+=this.insertoLiquidacion(l);
            this.liquidacionesMem.add(l);
        }
        
        
        if(lic.getDiasGenerados()!=0){
            montoDescuento=montoVacacional/lic.getDiasGenerados();
            montoDescuento=montoDescuento*lic.getDiasDescuento();
            montoDescuento=this.fijarNumero(montoDescuento, 2);
            
            montoDescuentosOriginal=this.buscoSalarioVacacionalPagado(k.getCodFunc(), 50, fechaLiq);
            montoDescuentosOriginal=montoDescuentosOriginal+this.buscoSalarioVacacionalPagado(k.getCodFunc(), 52, fechaLiq);
            montoDescuentosReLiq=montoDescuento-montoDescuentosOriginal;
            montoDescuentosReLiq=this.fijarNumero(montoDescuentosReLiq,2);
            if(montoDescuentosReLiq!=0){            
                c=this.buscarCodigo(52);
                l = new Liquidacion(k,1,c,Double.valueOf(lic.getDiasDescuento()),this.fijarNumero(montoDescuentosReLiq, 2),this.fijarNumero(montoDescuentosReLiq, 2),fechaLiq,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
            }
        }
     
       }
       
      this.cargoIngresos(k);
      
      if(ingresos!=null){
           if(ingresos.size()>0){
               for(Ingreso i: ingresos){
                   Double importe = this.fijarNumero(i.getImporte(), 2);
                   
                   if(null!=i.getCod())switch (i.getCod()) {
                      case 24://Promedio horas extras de licencia
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                 
                      case 46://Promedio horas extras feriado no laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*4.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 47://Promedio horas extras feriado laborable
                           importe=(i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras()))*3.5;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 61://hs extras simples NOCTURNAS
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras())*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 70://feriado no lab NOC
                           importe=i.getCantidad()*(k.getSueldoCargo()/30)*2*1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 62://hs extras feriado no lab NOC
                           importe=i.getCantidad()*(k.getSueldoCargo()/k.getBaseHoras())*4.5 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      case 71://feriado  lab NOC
                           importe=i.getCantidad()*(k.getSueldoCargo()/30)*2 *1.2;
                           importe=this.fijarNumero(importe, 2);
                           break;
                      default:
                           break;
                   }
                    importe=this.fijarNumero(importe, 2);
                    i.setCodMov(this.buscarCodigo(i.getCod()));
                  
                    
                    l = new Liquidacion(k,1,i.getCodMov(),i.getCantidad(),importe,importe,fechaLiq,0);
                    contador+=this.insertoLiquidacion(l);
                    this.liquidacionesMem.add(l);
               }
           }
       }
     
      boolean tieneReLiqSalarioVac=this.tieneReLiqSalarioVacEnLiq(k);
       //----------------------------CARGA DE RETENCIONES FIJAS------------------------------//
       ArrayList<Integer> retenciones = this.cargoRetencionesFijasVacacionalReLiq(k,fechaLiq);
       if(retenciones.size()>0){
           if(tieneReLiqSalarioVac){
                for(Integer r:retenciones){
                     c=this.buscarCodigo(r);
                     l = new Liquidacion(k,1,c,1.0,0.0,0.0,fechaLiq,0);
                     contador+=this.insertoLiquidacion(l);
                     this.liquidacionesMem.add(l);
                }
           }
       }
       //---------------------------------------------------------------------------------------//
       //----------------------------------CARGA DE VALES---------------------------------------//
       ArrayList<Integer> vales = this.cargovalesDeLiquidaciones(k);
       if(vales.size()>0){
           for(Integer v:vales){
                c=this.buscarCodigo(501);
                l = new Liquidacion(k,1,c,1.0,0.0,0.0,fechaLiq,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
           }
           
       }
       //---------------------------------------------------------------------------------------//
         this.totalHaberesBps = this.cargoTotalHaberesBps(k);
        
          this.totalHaberesIrpf = this.cargoTotalHaberesIrpf(k);
         
          this.totalHaberesGeneral = this.cargoTotalHaberesGeneral(k);
          double valorDia=this.buscoValorDiaLicenciareLiq(k.getCodFunc());
          this.totalHaberesNoBps = this.totalHaberesGeneral-totalHaberesBps;
          this.totalHaberesNoBps = this.fijarNumero(this.totalHaberesNoBps, 2);
       //----------------------------------PROCESO DE LIQUIDACION---------------------------------------//
       
       //APLICO IMPUESTOS
       if(this.totalHaberesBps>=topeAfap){
           if(k.getAfap()==1){
                String di=String.valueOf(dipaico);
                String t=String.valueOf(topeAfap);
                java.math.BigDecimal dip= new java.math.BigDecimal(di);
                java.math.BigDecimal totAf = new java.math.BigDecimal(t);
                java.math.BigDecimal tot = totAf.multiply(dip);
                impDipaico=tot.doubleValue(); 
                this.impDipaico=this.fijarNumero(this.impDipaico, 2);
           }
           else{
                String di=String.valueOf(dipaico);
                String t=String.valueOf(totalHaberesBps);
                java.math.BigDecimal dip= new java.math.BigDecimal(di);
                java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
                java.math.BigDecimal tot = totHabBps.multiply(dip);
                impDipaico=tot.doubleValue();
                this.impDipaico=this.fijarNumero(this.impDipaico, 2);
           }
           
       }
       else{
             String di=String.valueOf(dipaico);
             String t=String.valueOf(totalHaberesBps);
             java.math.BigDecimal dip= new java.math.BigDecimal(di);
             java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
             java.math.BigDecimal tot = totHabBps.multiply(dip);
             impDipaico=tot.doubleValue();
             this.impDipaico=this.fijarNumero(impDipaico, 2);
      
       }
       

      this.fonasa=k.getSns().getFonasa();
      
      if(this.totalHaberesBps<=(2.5*this.bpcActual)){
             this.fonasa=0.025;
      }
      
  
      java.math.BigDecimal dip= BigDecimal.valueOf(fonasa);
      java.math.BigDecimal totHabBps = BigDecimal.valueOf(totalHaberesBps);
      java.math.BigDecimal tot = totHabBps.multiply(dip);

      impFonasa=tot.doubleValue();
      this.impFonasa=this.fijarNumero(this.impFonasa, 2);//OK
      
      this.impFrl=this.totalHaberesBps*this.frl;
      this.impFrl=this.fijarNumero(this.impFrl,2);//OK
       
      this.irpfNeto=0;
            
      //dipaico
      if(impDipaico!=0){
        c=this.buscarCodigo(201);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impDipaico, 2),this.fijarNumero(impDipaico, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      }
      //fonasa
      if(impFonasa!=0){
        c=this.buscarCodigo(205);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFonasa, 2),this.fijarNumero(impFonasa, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      }
      //frl
      if(impFrl!=0){
        c=this.buscarCodigo(204);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFrl, 2),this.fijarNumero(impFrl, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      }
      //irpfneto
      c=this.buscarCodigo(207);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(irpfNeto, 2),this.fijarNumero(irpfNeto, 2),fechaLiq,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      //CALCULO DE LIQUIDO LEGAL Y LIQUIDO MINIMO
          
      this.liquidoLegal=this.totalHaberesGeneral-(impDipaico+irpfNeto+impFrl+impFonasa);
      this.liquidoLegal=this.fijarNumero(this.liquidoLegal, 2);
      
      this.liquidoMinimo=liquidoLegal*porcentajeLiquidoMinimo;
      this.liquidoMinimo=this.fijarNumero(liquidoMinimo, 2);
      
      this.liquidoDescontable=liquidoLegal-liquidoMinimo;
      this.liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
      this.liquidoFinal=this.fijarNumero(liquidoLegal, 2);
          
      //RETENCIONES JUDICIALES ALIMENTICIAS
      ArrayList<Retencion> retenJud = this.cargoRetencionesJudicialesVacacionalReLiq(k,fechaLiq);
      
      for(Retencion r : retenJud){
          if(tieneReLiqSalarioVac){
          double importeRetencion=0;
          if(r.getTipo()==0){//retencion de tipo fijo
                importeRetencion=this.fijarNumero(r.getImporte(),2);
          }else{//es una retencion con porcentaje
               if(r.getSobre()==0){//se aplica el porcentaje sobre el total de haberes
                   importeRetencion=this.totalHaberesBps*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
               }else{//se aplica el porcentaje sobre el liquido legal
                   importeRetencion=this.liquidoLegal*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
               }
                            
          }
         
          
            if(liquidoDescontable>=importeRetencion){
                 liquidoDescontable=liquidoDescontable-importeRetencion;
                 liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                 liquidoFinal=liquidoFinal-importeRetencion;
                 liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                 descuentoRechazado=0;
                 descuentoAplicado=this.fijarNumero(importeRetencion, 2);                           
             }else{
                 descuentoRechazado=importeRetencion-liquidoDescontable;
                 descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                 descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                 liquidoFinal=liquidoFinal-descuentoAplicado;
                 liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                 liquidoDescontable=0;
            }
          
          //INSERTO
          c=this.buscarCodigo(310);
          l = new Liquidacion(k,1,c,1.0,this.fijarNumero(importeRetencion, 2),this.fijarNumero(importeRetencion, 2),fechaLiq,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }
     }
      
      //APLICO CODIGOS DE DESCUENTOS
      liquidaciones =this.cargoDescuentos(k);
      this.cargoCodigos();
      this.reordeno();
      int r=1;
      
      for(Liquidacion s:liquidaciones){
          r+=this.actualizoOrdenLiq(s,r,k);
          contador++;
          
          if(s.getCod()==401){//vales
              if(s.getImporte()>liquidoMinimo){
                  restoVale=s.getImporte()-liquidoMinimo;
                  restoVale=this.fijarNumero(restoVale, 2);
                  importeADescontar=this.fijarNumero(liquidoMinimo, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  restoVale=0;
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }
          }else if(s.getCod()==501){//resto de vales
              if(restoVale!=0){
                  importeADescontar=this.fijarNumero(restoVale,2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  importeADescontar=0;
              }
          }else{
              if(s.getImporte()!=0){
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }else{
                  importeADescontar=this.cargoImporteRetFija(k,s.getCod(),totalHaberesBps,liquidoLegal);
                  importeADescontar=this.fijarNumero(importeADescontar, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }
          }
          
          
          if(s.getCod()!=401){
              if(s.getCod()==355){
                  this.seg_ace=this.totalSeguroAce(k);
                  
                  if((liquidoDescontable+seg_ace)>=importeADescontar){
                      liquidoDescontable=(liquidoDescontable+seg_ace)-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=(liquidoFinal+seg_ace)-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{        
                        descuentoRechazado=importeADescontar-(liquidoDescontable+seg_ace);
                        descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                        descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                        liquidoFinal=liquidoFinal-descuentoAplicado;
                        liquidoFinal=this.fijarNumero(liquidoFinal, 0);
                        liquidoDescontable=0;                   
                  }
              }else{
                  //aca cambio de 5 por ciento extra de liquido minimo
                  if(liquidoDescontable>=importeADescontar){         
                      liquidoDescontable=liquidoDescontable-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=liquidoFinal-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{
                        descuentoRechazado=importeADescontar-liquidoDescontable;
                        descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                        descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                        liquidoFinal=liquidoFinal-descuentoAplicado;
                        liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                        liquidoDescontable=0;
                      
                  }
              }
          }else{
              if(liquidoFinal>=importeADescontar){                  
                    liquidoFinal=liquidoFinal-importeADescontar;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                    descuentoRechazado=0;
                    descuentoAplicado=this.fijarNumero(importeADescontar, 2);
              }else{       
                    descuentoRechazado=importeADescontar-liquidoFinal;
                    descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                    descuentoAplicado=this.fijarNumero(liquidoFinal, 2);
                    liquidoFinal=liquidoFinal-descuentoAplicado;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
              }
          }
          
         contador+=this.actualizoLiquidacion2(k,s.getCod(),descuentoRechazado); 
      }
      
      totalDescuentos=totalHaberesGeneral-liquidoFinal;
      totalDescuentos=this.fijarNumero(totalDescuentos, 2);
      
      //calculo e inserto redondeos
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      Double val = liquidoFinal;
      int valLiqFinal = val.intValue();
      
      if((liquidoFinal-valLiqFinal)!=0){
          redondeo = 1-(liquidoFinal-valLiqFinal);
          redondeo=this.fijarNumero(redondeo, 2);
          c=this.buscarCodigo(600);
          l = new Liquidacion(k,1,c,1.0,redondeo,redondeo,fechaLiq,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }else{
          redondeo=0;
      }
      
      liquidoFinal=liquidoFinal+redondeo;
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      totalNoRetenido=this.buscarTotalNoRetenido(k);
      
      double veinteTotalHaberesBps=this.totalHaberesBps*porcentajeSacec;
      veinteTotalHaberesBps = this.fijarNumero(veinteTotalHaberesBps, 2);
      Liquidacion li = this.obtengoLiq(461);
      
      if(li!=null){
      if(this.fijarNumero(li.getImporte(), 2)>veinteTotalHaberesBps){
        l.setImporte(veinteTotalHaberesBps);
        contador+=this.actualizoIngreso(k, 461, veinteTotalHaberesBps );
        cnn.commit();
        cnn.close();
        cnn=null;
        this.comienzoLiquidación(k, fechaLiq); 
       }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fechaLiq, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
      }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fechaLiq, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
  }
   //--------------------------------------------------------------------------------------------------------//
   
    private Licencia cargaLicencia(Integer codFunc, Date fechaGeneracion)throws BDExcepcion {
        try {
            Licencia lic=null;
            String consulta="select dias_generados,dias_descuento from pers_licencia_generada where (codfunc=? and fechagen=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            ps.setString(2, this.convertirFecha(fechaGeneracion)); 
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                lic=new Licencia();
                lic.setDiasGenerados(rs.getInt("dias_generados")); 
                lic.setDiasDescuento(rs.getInt("dias_descuento"));
            }
           
            ps.close();
            rs.close();
            
            return lic;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean tieneSalarioVacEnLiq(Funcionario k) throws BDExcepcion{
         try {
            boolean tiene=false;
            String consulta="select * from pers_liquidaciones where (cod_mov=4) and (cod_func=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                tiene=true;
            }
           
            ps.close();
            rs.close();
            
            return tiene;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     private boolean tieneReLiqSalarioVacEnLiq(Funcionario k) throws BDExcepcion{
         try {
            boolean tiene=false;
            String consulta="select * from pers_liquidaciones where (cod_mov=51) and (cod_func=?)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                tiene=true;
            }
           
            ps.close();
            rs.close();
            
            return tiene;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private void limpioPromedios(Funcionario k) throws BDExcepcion{
        try {
            String consulta="delete from pers_ingresos where cod_func=? and(cod_mov=24 or cod_mov=46 or cod_mov=47 or cod_mov=81 or cod_mov=82 or cod_mov=83)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.executeUpdate();
            ps.close();
                 
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private void cargoPromedios(Funcionario k,Date fechaLiq) throws BDExcepcion{
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaLiq);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-1);
        Date fechaIni=cal.getTime();
        
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.MONTH, 11);
        Date fechaFin=cal.getTime();
        
        BigDecimal once = new BigDecimal("11");
        BigDecimal cant;
        
        double cant24=this.cargoCantidadHoras(k,31,fechaIni,fechaFin);
        cant24= cant24/11;
        this.insertoPromedio(k,24,cant24);
        
        double cant46=this.cargoCantidadHoras(k,32,fechaIni,fechaFin);
        cant46= cant46/11;
        this.insertoPromedio(k,46,cant46);
        
        double cant47=this.cargoCantidadHoras(k,33,fechaIni,fechaFin);
        cant47= cant47/11;
        this.insertoPromedio(k,47,cant47);
        
        double cant81=this.cargoCantidadHoras(k,61,fechaIni,fechaFin);
        cant81= cant81/11;
        this.insertoPromedio(k,81,cant81);
        
        double cant82=this.cargoCantidadHoras(k,62,fechaIni,fechaFin);
        cant82= cant82/11;
        this.insertoPromedio(k,82,cant82);
        
        double cant83=this.cargoCantidadHoras(k,63,fechaIni,fechaFin);
        cant83= cant83/11;
        this.insertoPromedio(k,83,cant83);
        
    }

    private double cargoCantidadHoras(Funcionario k, int cod, Date fechaIni, Date fechaFin)throws BDExcepcion {
        try {
            
            double retorno=0;
            String consulta="select sum(cantidad) as total from pers_hist_liquidaciones where ((cod_func=?) and (cod_mov=?) and (fecha between ? and ?) and tipoliq=1)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, k.getCodFunc());
            ps.setInt(2, cod);
            ps.setString(3, this.convertirFecha(fechaIni));
            ps.setString(4, this.convertirFecha(fechaFin));
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                retorno=rs.getDouble("total");
            }
           
            ps.close();
            rs.close();
            
            return retorno;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private void insertoPromedio(Funcionario k, int cod, double cant)throws BDExcepcion {
        try {
            if(cant>0){
            Date fecha=new Date();
            String inserto="Insert into PERS_Ingresos(FECHA,COD_FUNC,COD_MOV,CANTIDAD,IMPORTE)"+"values(?,?,?,?,?)";
            PreparedStatement ps=cnn.prepareStatement(inserto);
            ps.setString(1, this.convertirFecha(fecha));
            ps.setInt(2,k.getCodFunc());
            ps.setInt(3, cod);
            ps.setDouble(4, this.fijarNumero(cant,2));
            ps.setDouble(5, 0);
            
            ps.executeUpdate();
            
            ps.close();
           
            }
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private double buscoSalarioVacacionalPagado(Integer codFunc, int cod, Date fechaLiq) throws BDExcepcion{
        try {
            
            Calendar cal =Calendar.getInstance();
            cal.setTime(fechaLiq);
            int anio=cal.get(Calendar.YEAR);
            
            double retorno=0;
            String consulta="select sum(importe) as total from pers_hist_liquidaciones where ((year(fechaliq)=?) and (tipoliq=4) and (cod_mov=?) and (cod_func=?))";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, anio);
            ps.setInt(2, cod);
            ps.setInt(3, codFunc);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                retorno=rs.getDouble("total");
            }
           
            ps.close();
            rs.close();
            
            return retorno;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private double buscoValorDiaLicenciareLiq(Integer codFunc) throws BDExcepcion{
        try {
            
            double retorno=0;
            String consulta="select importe,cantidad from pers_liquidaciones where (cod_func=? and cod_mov=51)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                retorno=rs.getDouble("importe")/rs.getDouble("cantidad"); 
            }
           
            ps.close();
            rs.close();
            
            return retorno;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private void liquidarAguinaldo(Funcionario k, Date fechaLiq,boolean reLiq) throws BDExcepcion, ClassNotFoundException, ParseException, SQLException, SQLException {
     Liquidacion l = null;
     Codigo c=null;
     this.cargoIngresos(k);
     liquidacionesMem=new ArrayList<>();
      if(ingresos!=null){
           if(ingresos.size()>0){
               for(Ingreso i: ingresos){
                   Double importe = this.fijarNumero(i.getImporte(), 2);
                   i.setCodMov(this.buscarCodigo(i.getCod()));
                   l = new Liquidacion(k,1,i.getCodMov(),i.getCantidad(),importe,importe,fechaLiq,0);
                   contador+=this.insertoLiquidacion(l);
                   this.liquidacionesMem.add(l);
               }
           }
       }
      
        //----------------------------CARGA DE RETENCIONES FIJAS------------------------------//
       ArrayList<Integer> retenciones = this.cargoRetencionesFijasVacacional(k);
       if(retenciones.size()>0){
                for(Integer r:retenciones){
                     c=this.buscarCodigo(r);
                     l = new Liquidacion(k,1,c,1.0,0.0,0.0,fechaLiq,0);
                     contador+=this.insertoLiquidacion(l);
                     this.liquidacionesMem.add(l);
                }
           
       }
       //---------------------------------------------------------------------------------------//
       
      //----------------------------------CARGA DE VALES---------------------------------------//
       ArrayList<Integer> vales = this.cargovalesDeLiquidaciones(k);
       if(vales.size()>0){
           for(Integer v:vales){
                c=this.buscarCodigo(501);
                l = new Liquidacion(k,1,c,1.0,0.0,0.0,fechaLiq,0);
                contador+=this.insertoLiquidacion(l);
                this.liquidacionesMem.add(l);
           }
           
       }
       //---------------------------------------------------------------------------------------//
      
       //---------------------------------------------------------------------------------------//
         this.totalHaberesBps = this.cargoTotalHaberesBps(k);
         this.totalHaberesIrpf = this.cargoTotalHaberesIrpf(k);
         this.totalHaberesGeneral = this.cargoTotalHaberesGeneral(k);
         this.totalHaberesNoBps = this.totalHaberesGeneral-totalHaberesBps;
         this.totalHaberesNoBps = this.fijarNumero(this.totalHaberesNoBps, 2);
         this.totalHaberesBpsOld = this.buscoTotalHaberesBpsAguinaldoOld(k,fechaLiq); 
       //----------------------------------PROCESO DE LIQUIDACION---------------------------------------//
       
       //APLICO IMPUESTOS
       if(!reLiq){
            if((this.totalHaberesBps*2)>=topeAfap){
                if(k.getAfap()==1){
                     String di=String.valueOf(dipaico);
                     String t=String.valueOf(topeAfap);
                     java.math.BigDecimal dip= new java.math.BigDecimal(di);
                     java.math.BigDecimal totAf = new java.math.BigDecimal(t);
                     totAf=totAf.divide(new BigDecimal("2"));
                     java.math.BigDecimal tot = totAf.multiply(dip);
                     impDipaico=tot.doubleValue(); 
                     this.impDipaico=this.fijarNumero(this.impDipaico, 2);
                }
                else{
                     String di=String.valueOf(dipaico);
                     String t=String.valueOf(totalHaberesBps);
                     java.math.BigDecimal dip= new java.math.BigDecimal(di);
                     java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
                     java.math.BigDecimal tot = totHabBps.multiply(dip);
                     impDipaico=tot.doubleValue();
                     this.impDipaico=this.fijarNumero(this.impDipaico, 2);
                }

            }
            else{
                  String di=String.valueOf(dipaico);
                  String t=String.valueOf(totalHaberesBps);
                  java.math.BigDecimal dip= new java.math.BigDecimal(di);
                  java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
                  java.math.BigDecimal tot = totHabBps.multiply(dip);
                  impDipaico=tot.doubleValue();
                  this.impDipaico=this.fijarNumero(impDipaico, 2);
           }
      }else{
           if((this.totalHaberesBpsOld+totalHaberesBps)*2>=topeAfap){
               if(k.getAfap()==1){
                     String di=String.valueOf(dipaico);
                     String t=String.valueOf(topeAfap);
                     java.math.BigDecimal dip= new java.math.BigDecimal(di);
                     java.math.BigDecimal totAf = new java.math.BigDecimal(t);
                     totAf=totAf.divide(new BigDecimal("2"));
                     java.math.BigDecimal tot = totAf.multiply(dip);
                     impDipaico=tot.doubleValue(); 
                     this.impDipaico=this.fijarNumero(this.impDipaico, 2);
                     if(this.totalHaberesBpsOld>=(topeAfap/2)){
                         impDipaico=this.fijarNumero(0, 2);
                     }else{
                         String told=String.valueOf(totalHaberesBpsOld);
                         java.math.BigDecimal totOld= new java.math.BigDecimal(told);
                         tot=totOld.multiply(dip);
                         double aux=tot.doubleValue();
                         aux=this.fijarNumero(aux, 2);
                         impDipaico=impDipaico-aux; 
                         impDipaico=this.fijarNumero(impDipaico, 2);
                     }
               }else{
                    String di=String.valueOf(dipaico);
                     String t=String.valueOf(totalHaberesBps);
                     java.math.BigDecimal dip= new java.math.BigDecimal(di);
                     java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
                     java.math.BigDecimal tot = totHabBps.multiply(dip);
                     impDipaico=tot.doubleValue();
                     this.impDipaico=this.fijarNumero(this.impDipaico, 2);
               }
           }else{
                String di=String.valueOf(dipaico);
                String t=String.valueOf(totalHaberesBps);
                java.math.BigDecimal dip= new java.math.BigDecimal(di);
                java.math.BigDecimal totHabBps = new java.math.BigDecimal(t);
                java.math.BigDecimal tot = totHabBps.multiply(dip);
                impDipaico=tot.doubleValue();
                this.impDipaico=this.fijarNumero(this.impDipaico, 2);
           }
      }
      
      this.casenpaceVoluntario = this.totalHaberesBps*this.porcentajeCasenpaceVol;
      this.casenpaceVoluntario = this.fijarNumero(this.casenpaceVoluntario, 2);//OK

      this.fonasa=k.getSns().getFonasa();
            
      java.math.BigDecimal dip= BigDecimal.valueOf(fonasa);
      java.math.BigDecimal totHabBps = BigDecimal.valueOf(totalHaberesBps);
      java.math.BigDecimal tot = totHabBps.multiply(dip);

      impFonasa=tot.doubleValue();
      this.impFonasa=this.fijarNumero(this.impFonasa, 2);//OK
      
      this.impFrl=this.totalHaberesBps*this.frl;
      this.impFrl=this.fijarNumero(this.impFrl,2);//OK
       
      this.calcularIrpfMensual(k, fechaLiq, true); 
            
      //dipaico
      
        c=this.buscarCodigo(201);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impDipaico, 2),this.fijarNumero(impDipaico, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      
      //fonasa
      
        c=this.buscarCodigo(205);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFonasa, 2),this.fijarNumero(impFonasa, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      
      //frl
     
        c=this.buscarCodigo(204);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(impFrl, 2),this.fijarNumero(impFrl, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      
      //irpfneto
      c=this.buscarCodigo(207);
      l = new Liquidacion(k,1,c,1.0,this.fijarNumero(irpfNeto, 2),this.fijarNumero(irpfNeto, 2),fechaLiq,0);
      contador+=this.insertoLiquidacion(l);
      this.liquidacionesMem.add(l);
      
      if(casenpaceVoluntario!=0){
        c=this.buscarCodigo(515);
        l = new Liquidacion(k,1,c,1.0,this.fijarNumero(casenpaceVoluntario, 2),this.fijarNumero(casenpaceVoluntario, 2),fechaLiq,0);
        contador+=this.insertoLiquidacion(l);
        this.liquidacionesMem.add(l);
      }
      
      //CALCULO DE LIQUIDO LEGAL Y LIQUIDO MINIMO
          
      this.liquidoLegal=this.totalHaberesGeneral-(impDipaico+irpfNeto+impFrl+impFonasa);
      this.liquidoLegal=this.fijarNumero(this.liquidoLegal, 2);

      if(k.getCodFunc()==5300){
          this.liquidoLegal=this.totalHaberesGeneral-(impDipaico+irpfNeto+impFrl+impFonasa);
      }
      
      this.liquidoMinimo=liquidoLegal*porcentajeLiquidoMinimo;
      this.liquidoMinimo=this.fijarNumero(liquidoMinimo, 2);
      
      this.liquidoDescontable=liquidoLegal-liquidoMinimo;
      this.liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
      this.liquidoFinal=this.fijarNumero(liquidoLegal, 2);
          
      //RETENCIONES JUDICIALES ALIMENTICIAS
      ArrayList<Retencion> retenJud = this.cargoRetencionesJudicialesVacacional(k);
      
      for(Retencion r : retenJud){
         
          double importeRetencion=0;
          if(r.getTipo()==0){//retencion de tipo fijo
                importeRetencion=this.fijarNumero(r.getImporte(),2);
          }else{//es una retencion con porcentaje
               if(r.getSobre()==0){//se aplica el porcentaje sobre el total de haberes
                   importeRetencion=this.totalHaberesBps*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
               }else{//se aplica el porcentaje sobre el liquido legal
                   importeRetencion=this.liquidoLegal*r.getPorcentaje();
                   importeRetencion=this.fijarNumero(importeRetencion, 2);
                    
                   if(k.getCodFunc()==3540){
                           Liquidacion liq = this.cargoLineaLiq(k,42);
                           if(liq.getImporte()!=0){
                               tasaDescuentoGeneral=1-(liquidoLegal/totalHaberesBps);
                               hogarDescuentos=liq.getImporte()*tasaDescuentoGeneral;
                               hogarDescuentos=this.fijarNumero(hogarDescuentos, 2);
                               restoRetencion=(liq.getImporte()-hogarDescuentos)*0.15;
                               importeRetencion=importeRetencion+restoRetencion;
                               importeRetencion=this.fijarNumero(importeRetencion, 2);
                           }
                        }else{
                              if(k.getCodFunc()==3160){
                                Liquidacion liq = this.cargoLineaLiq(k,42);
                                    if(liq.getImporte()!=0){
                                        tasaDescuentoGeneral=1-(liquidoLegal/totalHaberesBps);
                                        hogarDescuentos=liq.getImporte()*tasaDescuentoGeneral;
                                        hogarDescuentos=this.fijarNumero(hogarDescuentos, 2);
                                        restoRetencion=(liq.getImporte()-hogarDescuentos)*0.25;
                                        importeRetencion=importeRetencion+restoRetencion;
                                        importeRetencion=this.fijarNumero(importeRetencion, 2);
                                    }
                                }
                       }
               }
                            
          }
                   
            if(k.getCodFunc()==3400){
              if(liquidoFinal>=importeRetencion){
                  liquidoFinal=liquidoFinal-importeRetencion;
                  liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                  descuentoRechazado=0;
                  descuentoAplicado=this.fijarNumero(importeRetencion, 2);
                  liquidoDescontable=liquidoDescontable-importeRetencion;
                  liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                  if(liquidoDescontable<0){
                      liquidoDescontable=0;
                  }
              }else{
                  descuentoRechazado=importeRetencion-liquidoFinal;
                  descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                  descuentoAplicado=this.fijarNumero(liquidoFinal, 2);
                  liquidoFinal=liquidoFinal-descuentoAplicado;
                  liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                  liquidoDescontable=0;
              }
          }else{
                if(liquidoDescontable>=importeRetencion){
                     liquidoDescontable=liquidoDescontable-importeRetencion;
                     liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                     liquidoFinal=liquidoFinal-importeRetencion;
                     liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                     descuentoRechazado=0;
                     descuentoAplicado=this.fijarNumero(importeRetencion, 2);                           
                 }else{
                     descuentoRechazado=importeRetencion-liquidoDescontable;
                     descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                     descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                     liquidoFinal=liquidoFinal-descuentoAplicado;
                     liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                     liquidoDescontable=0;
                }
          }
          //INSERTO
          c=this.buscarCodigo(310);
          l = new Liquidacion(k,1,c,1.0,this.fijarNumero(importeRetencion, 2),this.fijarNumero(importeRetencion, 2),fechaLiq,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      
     }
      
      //APLICO CODIGOS DE DESCUENTOS
      liquidaciones =this.cargoDescuentos(k);
      this.cargoCodigos();
      this.reordeno();
      int r=1;
      
      for(Liquidacion s:liquidaciones){
          r+=this.actualizoOrdenLiq(s,r,k);
          contador++;
          
          if(s.getCod()==401){//vales
              if(s.getImporte()>liquidoMinimo){
                  restoVale=s.getImporte()-liquidoMinimo;
                  restoVale=this.fijarNumero(restoVale, 2);
                  importeADescontar=this.fijarNumero(liquidoMinimo, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  restoVale=0;
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }
          }else if(s.getCod()==501){//resto de vales
              if(restoVale!=0){
                  importeADescontar=this.fijarNumero(restoVale,2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }else{
                  importeADescontar=0;
              }
          }else{
              if(s.getImporte()!=0){
                  importeADescontar=this.fijarNumero(s.getImporte(), 2);
              }else{
                  importeADescontar=this.cargoImporteRetFija(k,s.getCod(),totalHaberesBps,liquidoLegal);
                  importeADescontar=this.fijarNumero(importeADescontar, 2);
                  contador+=this.actualizoLiquidacion(k,s.getCod(),importeADescontar);
              }
          }
          
          if(s.getCod()!=401){
              if(s.getCod()==355){
                  this.seg_ace=this.totalSeguroAce(k);
                  
                  if((liquidoDescontable+seg_ace)>=importeADescontar){
                      liquidoDescontable=(liquidoDescontable+seg_ace)-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=(liquidoFinal+seg_ace)-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{        
                        descuentoRechazado=importeADescontar-(liquidoDescontable+seg_ace);
                        descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                        descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                        liquidoFinal=liquidoFinal-descuentoAplicado;
                        liquidoFinal=this.fijarNumero(liquidoFinal, 0);
                        liquidoDescontable=0;                   
                  }
              }else{
                  //aca cambio de 5 por ciento extra de liquido minimo
                  if(liquidoDescontable>=importeADescontar){         
                      liquidoDescontable=liquidoDescontable-importeADescontar;
                      liquidoDescontable=this.fijarNumero(liquidoDescontable, 2);
                      liquidoFinal=liquidoFinal-importeADescontar;
                      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                      descuentoRechazado=0;
                      descuentoAplicado=this.fijarNumero(importeADescontar, 2);
                  }else{
                        descuentoRechazado=importeADescontar-liquidoDescontable;
                        descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                        descuentoAplicado=this.fijarNumero(liquidoDescontable, 2);
                        liquidoFinal=liquidoFinal-descuentoAplicado;
                        liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                        liquidoDescontable=0;
                      
                  }
              }
          }else{
              if(liquidoFinal>=importeADescontar){                  
                    liquidoFinal=liquidoFinal-importeADescontar;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
                    descuentoRechazado=0;
                    descuentoAplicado=this.fijarNumero(importeADescontar, 2);
              }else{       
                    descuentoRechazado=importeADescontar-liquidoFinal;
                    descuentoRechazado=this.fijarNumero(descuentoRechazado, 2);
                    descuentoAplicado=this.fijarNumero(liquidoFinal, 2);
                    liquidoFinal=liquidoFinal-descuentoAplicado;
                    liquidoFinal=this.fijarNumero(liquidoFinal, 2);
              }
          }
          
         contador+=this.actualizoLiquidacion2(k,s.getCod(),descuentoRechazado); 
      }
      
      totalDescuentos=totalHaberesGeneral-liquidoFinal;
      totalDescuentos=this.fijarNumero(totalDescuentos, 2);
      
      //calculo e inserto redondeos
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      Double val = liquidoFinal;
      int valLiqFinal = val.intValue();
      
      if((liquidoFinal-valLiqFinal)!=0){
          redondeo = 1-(liquidoFinal-valLiqFinal);
          redondeo=this.fijarNumero(redondeo, 2);
          c=this.buscarCodigo(600);
          l = new Liquidacion(k,1,c,1.0,redondeo,redondeo,fechaLiq,0);
          contador+=this.insertoLiquidacion(l);
          this.liquidacionesMem.add(l);
      }else{
          redondeo=0;
      }
      
      liquidoFinal=liquidoFinal+redondeo;
      liquidoFinal=this.fijarNumero(liquidoFinal, 2);
      totalNoRetenido=this.buscarTotalNoRetenido(k);
      
      double veinteTotalHaberesBps=this.totalHaberesBps*porcentajeSacec;
      veinteTotalHaberesBps = this.fijarNumero(veinteTotalHaberesBps, 2);
      Liquidacion li = this.obtengoLiq(461);
      
      if(li!=null){
      if(this.fijarNumero(li.getImporte(), 2)>veinteTotalHaberesBps){
        l.setImporte(veinteTotalHaberesBps);
        contador+=this.actualizoIngreso(k, 461, veinteTotalHaberesBps );
        cnn.commit();
        cnn.close();
        cnn=null;
        this.comienzoLiquidación(k, fechaLiq); 
       }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fechaLiq, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
      }else{
        ResumenLiquidacion resumen= new ResumenLiquidacion(k, totalHaberesGeneral, totalDescuentos, liquidoLegal, liquidoMinimo,liquidoFinal, fechaLiq, totalHaberesBps, totalHaberesIrpf, totalNoRetenido);
        contador+=this.insertoResumen(resumen); 
        resumen.setLiquidaciones(liquidacionesMem);
      }
      
    }
    
    //-------------------INCORPORACION---------------------------------------//

     private Date stringADate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(s);
        return date;
    }
     
    public boolean fechaValida(String fechaLiq)throws BDExcepcion {
       try {
       boolean valida = false;
       String fec="";
       Date fecHist=null;
       Connection cnn=null;
       cnn=Conexion.Cadena();
       int resultado=0;
       String sql = "select valor from pers_parametros where (ucase(rtrim(nombre))='ULT_FECHA_HISTORICA')";
       PreparedStatement ps=cnn.prepareStatement(sql); 
       ResultSet rs=ps.executeQuery();
       if(rs.next()){
           fec=rs.getString("VALOR").trim();
           fecHist=this.stringADate(fec);  
       }else{
           resultado=1;
       } 
       Date fecLiq=this.stringADate(fechaLiq); 
       
       if(resultado==0){
           if(this.diferenciaDeFechas(fecHist, fecLiq)<=0){
               resultado=2;
           }else{
               Integer meses=this.obtengoDiferenciaMeses(fecHist,fecLiq); 
               if(meses<0 && meses>1){
                   resultado=3;
               }else{
                   resultado=0;
               }
           }
       }
       if(resultado==0){
           valida=true;
       }
       if(cnn!=null){
                ps.close();
                cnn.close();
       }
       return valida;
       } catch (SQLException | ClassNotFoundException | ParseException ex) {
            throw new BDExcepcion(ex.getMessage());
       }
    }

    private Integer obtengoDiferenciaMeses(Date fecHist, Date fecLiq) {
        Calendar inicio = new GregorianCalendar();
        Calendar fin = new GregorianCalendar();
        inicio.setTime(fecHist);
        fin.setTime(fecLiq);
        int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
        int difM = difA * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH);
        return difM;
    }

    public void incorporacion(String fechaAct) throws SQLException, ClassNotFoundException,BDExcepcion {
       this.perFunc=new PersistenciaFuncionario();
       cnn=Conexion.Cadena();
       cnn.setAutoCommit(false);
       try{
       int resultado=0;
       Date fecLiq=this.stringADate(fechaAct); 
       
       
       //ENFERMEDAD DE LIQUIDACIONES A PERS_HISTORICO_ENFERMEDAD
       int tipoLiq=this.cargoTipoLiq(cnn); 
       if(tipoLiq!=4 && tipoLiq!=5){
           if(!actualizoHistoricoEnfermedad(cnn,fechaAct)){
                throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
           }
       }
        if(actualizoHistLiquidaciones(cnn,fechaAct)){
            if(actualizoHistFuncionarios(cnn,fechaAct)){
                if(actualizoHistDeclaraciones(cnn,fechaAct)){
                    if(actualizoHistPersaCargo(cnn,fechaAct)){
                        if(actualizoHistIngresos(cnn,fechaAct))
                            if(actualizoHistResumen(cnn,fechaAct)){ 
                                if(actualizoHistIrpfDetallado(cnn,fechaAct)){
                                    if(actualizoHistCodigosFijos(cnn,fechaAct)){
                                        if(actualizoHistCatCjppu(cnn,fechaAct)){  
                                            if(actualizoHistIrpfDeducciones(cnn,fechaAct)){
                                                if(actualizoHistIrpfRetenciones(cnn,fechaAct)){
                                                    if(actualizoHistParametros(cnn,fechaAct)){
                                                        if(actualizoHistRetencionesFijas(cnn,fechaAct)){
                                                            if(!actualizoHistretencionesJudiciales(cnn,fechaAct)){
                                                                throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                                                            }
                                                        }else{
                                                           throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                                                        }
                                                    }else{
                                                        throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                                                    }
                                                }else{
                                                    throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                                                }
                                            }else{
                                                throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                                            }
                                        }else{
                                            throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                                        }
                                    }else{
                                        throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                                    }
                                }else{
                                    throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                                }
                            }else{
                                throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                            }
                    }else{
                        throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                    }
                }else{
                    throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
                }
            }else{
                throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
            }
        }else{
            throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
        }
            if(tipoLiq==4 && tipoLiq==5){
               if(!insertoEnPersReliq(cnn,fechaAct,tipoLiq)){ 
                   throw new SQLException("Ya fue hecha una incorporación con la misma fecha. No se producirán cambios");
               }
           }
            
           this.vacioLiquidaciones();
           this.vacioIngresos();
           this.vacioResumen();
           this.vacioIrpfDetallado();
           this.perFunc.actualizaParametros(fechaAct,"", "ULT_FECHA_HISTORICA");
           
           cnn.commit();
       }catch (SQLException ex) {
           this.cnn.rollback(); 
           throw new SQLException(ex.getMessage());
       }catch (ParseException | BDExcepcion ex) {
            this.cnn.rollback(); 
            throw new BDExcepcion(ex.getMessage());
            
        }
        
    }
    
    private int cargoTipoLiq(Connection cnn) throws SQLException {
       int res=0;
       String sql = "select tipoliq from pers_resumen";
       PreparedStatement ps=cnn.prepareStatement(sql); 
       ResultSet rs=ps.executeQuery();
       if(rs.next()){
           res=rs.getInt("tipoLiq");
       }
       return res;
    }

    private boolean actualizoHistoricoEnfermedad(Connection cnn,String fecLiq)throws BDExcepcion {
        try {
            boolean ret=true;
            String sql = "select * from pers_historico_enfermedad where (fecha=?)";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fecLiq);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            if(ret){
                sql = "select cod_func,cantidad,fecha from pers_liquidaciones where (cod_mov=11 and fecha=?) order by cod_func";
                ps=cnn.prepareStatement(sql);
                ps.setString(1, fecLiq);
                rs=ps.executeQuery();
                while(rs.next()){
                    sql="Insert into pers_historico_enfermedad (fecha,cod_func,cod_mov,cantidad)"+"values(?,?,?,?)";
                    PreparedStatement pr=cnn.prepareStatement(sql);
                    pr.setString(1, fecLiq);
                    pr.setInt(2, rs.getInt("cod_func"));
                    pr.setInt(3, 11);
                    pr.setDouble(4, rs.getDouble("cantidad"));
                    pr.executeUpdate();
                }
            }
            rs.close();
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistLiquidaciones(Connection cnn, String fechaAct)throws BDExcepcion{
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_liquidaciones where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_liquidaciones select cod_func,cod_mov,cantidad,valor_u,importe,acumula,debe,haber,imponiblebps,imponibleirpf,fecha,importe_no_retenido,fecha,tipoliq from pers_liquidaciones";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    
    private boolean actualizoHistFuncionarios(Connection cnn, String fechaAct)throws BDExcepcion{
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_funcionarios where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                //  EN PRODUCION FALTA EL CAMPO COD_DESVINCULACION
                sql = "insert into pers_hist_funcionarios select codigo,apellido1,apellido2,nombre1,nombre2,direccion,localidad,departamento,telefono,celular,cedula,credencial,fecha_nac,est_civil,sexo,iniciales,fecha_ingreso,fecha_egreso,cod_cargo,sueldo_cargo,centro_costo,vigencia_cargo,base_horaria,base_horas,lugar_trabajo,afap,cuenta,socio,horario,?,SNS,banco,vencimiento_carne,tipo_cuenta,cod_desvinculacion from pers_funcionarios";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistDeclaraciones(Connection cnn, String fechaAct)throws BDExcepcion {
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_declaraciones where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_declaraciones select codfunc,tipodoc,nrodoc,pais,vigencia,personasacargo,catcjpu,reintcjpu,fondosolcjpu,adicionalcjpu,minnoimp,fecharecepcion,tipodocconyu,nrodocconyu,paisconyu,apellido1conyu,apellido2conyu,nombre1conyu,nombre2conyu,fecha_nacconyu,nacionalidadconyu,sexoconyu,? from pers_declaraciones";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistPersaCargo(Connection cnn, String fechaAct)throws BDExcepcion {
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_persacargo where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_persacargo select codfunc,ordinal,tipodoc,nrodoc,pais,fecha_nac,nacionalidad,sexo,relacion,sistsalud,pjedist,discapacidad,apellido1,apellido2,nombre1,nombre2,? from pers_persacargo";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistIngresos(Connection cnn, String fechaAct)throws BDExcepcion {
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_ingresos where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_ingresos select fecha,cod_func,cod_mov,cantidad,importe,? from pers_ingresos";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistResumen(Connection cnn, String fechaAct)throws BDExcepcion {
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_resumen where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_resumen select cod_func,total_haberes,total_descuentos,liquido_legal,liquido_minimo,liquido_final,centrocosto,fecha,total_haberes_bps,total_haberes_irpf,totalnoret,?,tipoliq from pers_resumen";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistIrpfDetallado(Connection cnn, String fechaAct)throws BDExcepcion {
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_irpf_detallado where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_irpf_detallado select codfun,fecha,tipo,ordinal,leyenda,importe,? from pers_irpf_detallado";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistCodigosFijos(Connection cnn, String fechaAct)throws BDExcepcion{
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_codigos_fijos where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_codigos_fijos select codfunc,codmov,valor,fechacargado,? from pers_codigos_fijos";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistCatCjppu(Connection cnn, String fechaAct)throws BDExcepcion{
       try {
            boolean ret=true;
            String sql = "select * from pers_hist_catcjppu where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_catcjppu select codigo,nombre,valor,? from pers_catcjppu";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistIrpfDeducciones(Connection cnn, String fechaAct)throws BDExcepcion {
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_irpf_deducciones where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_irpf_deducciones select hastabpcanual,hastabpcmensual,porcentaje,difbpcanual,difbpcmensual,? from pers_irpf_deducciones";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistIrpfRetenciones(Connection cnn, String fechaAct)throws BDExcepcion {
       try {
            boolean ret=true;
            String sql = "select * from pers_hist_irpf_retenciones where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_irpf_retenciones select hastabpcanual,hastabpcmensual,porcentaje,difbpcanual,difbpcmensual,? from pers_irpf_retenciones";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistParametros(Connection cnn, String fechaAct)throws BDExcepcion {
       try {
            boolean ret=true;
            String sql = "select * from pers_hist_parametros where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_parametros select nombre,valor,? from pers_parametros";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistRetencionesFijas(Connection cnn, String fechaAct)throws BDExcepcion {
         try {
            boolean ret=true;
            String sql = "select * from pers_hist_retenciones_fijas where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_retenciones_fijas select cod_func,cod_mov,orden,sobre,tipo,importe,porcentaje,activa,?,sueldo,otros from pers_retenciones_fijas";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean actualizoHistretencionesJudiciales(Connection cnn, String fechaAct)throws BDExcepcion {
        try {
            boolean ret=true;
            String sql = "select * from pers_hist_retenciones_judiciales where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaAct);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            
            if(ret){
                sql = "insert into pers_hist_retenciones_judiciales select cod_func,orden,sobre,tipo,importe,porcentaje,activa,?,sueldo,otros from pers_retenciones_judiciales";
                PreparedStatement pr=cnn.prepareStatement(sql);
                pr.setString(1, fechaAct);
                pr.executeUpdate();
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    private void vacioLiquidaciones()throws BDExcepcion {
        try {
            String delete="delete from pers_liquidaciones";
            PreparedStatement borrar=cnn.prepareStatement(delete); 
            borrar.executeUpdate();
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private void vacioIngresos() throws BDExcepcion{
       try {
            String delete="delete from pers_ingresos";
            PreparedStatement borrar=cnn.prepareStatement(delete); 
            borrar.executeUpdate();
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private void vacioResumen()throws BDExcepcion {
         try {
            String delete="delete from pers_resumen";
            PreparedStatement borrar=cnn.prepareStatement(delete); 
            borrar.executeUpdate();
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private void vacioIrpfDetallado() throws BDExcepcion{
         try {
            String delete="delete from pers_irpf_detallado";
            PreparedStatement borrar=cnn.prepareStatement(delete); 
            borrar.executeUpdate();
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private boolean insertoEnPersReliq(Connection cnn, String fechaAct,Integer tipoLiq) throws BDExcepcion{
        try {
            boolean ret=true;
            int i=0;
            Date hoy = new Date();
            String fec=this.convertirFecha(hoy);
            String sql = "insert into pers_reliquidaciones (tipoliq,fechaliq,fechahecho)"+"values(?,?,?)";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setInt(1, tipoLiq);
            ps.setString(2, fechaAct);
            ps.setString(3, fec);
            i=ps.executeUpdate();
            
            if(i==0){
                ret=false;
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    public boolean hayRegistrosEnLiquidacion()throws BDExcepcion {
        try {
            Connection cnn=null;
            cnn=Conexion.Cadena(); 
            boolean ret=false;
            String sql = "select * from pers_liquidaciones";
            PreparedStatement ps=cnn.prepareStatement(sql);
           
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=true;
            }
            
            ps.close();
            rs.close();
            cnn.close();
            
            return ret;
        } catch (SQLException | ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

   
    
}
