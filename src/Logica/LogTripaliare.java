
package Logica;

import Dominio.Codigo;
import Dominio.Feriado;
import Dominio.Funcionario;
import Dominio.Horario;
import Dominio.IngresoMarca;
import Dominio.Marca;
import Dominio.MovimientoLicencia;
import Persistencia.BDExcepcion;
import Persistencia.Conexion;
import Persistencia.PersistenciaCodigo;
import Persistencia.PersistenciaFuncionario;
import Persistencia.PersistenciaTripaliare;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;


public class LogTripaliare {
    private PersistenciaTripaliare trip;
    private PersistenciaFuncionario pers;
    private PersistenciaCodigo codPer;
    private Conexion conexion;
    private Date hoy;
    Connection cnn=null;
    Integer inserts=0;
    Iterator<Date> it=null;
    ArrayList<String> listaAuxiliar=null;
    private ArrayList<Feriado> listaFeriados;
    private Integer diferenciaMarca;
     ArrayList<Funcionario> listadoFunc;
    public LogTripaliare() throws ClassNotFoundException, SQLException {
        this.trip = new PersistenciaTripaliare();
        this.pers=new PersistenciaFuncionario();
        this.codPer=new PersistenciaCodigo();
        this.hoy=new Date();
        listadoFunc=this.pers.listaFuncionarioParcial();
        this.diferenciaMarca=trip.obtieneDiferencia();
    }  
    
    public ArrayList<Integer> funcionariosSoloTrip(Date desde,Date hasta) throws SQLException, ClassNotFoundException{
        ArrayList<Integer>listaTrip=this.trip.funcDistintos(desde, hasta);
        ArrayList<Integer>listaFun=this.pers.listaCodFunc();
        ArrayList<Integer> listado=new ArrayList<>();
        
        for(Integer i:listaTrip){
            if(!listaFun.contains(i)){
                listado.add(i);
            }
        }
        return listado;
    }
    
    
    public boolean prueba(Date desde,Date hasta,ArrayList<Integer> listado) throws SQLException, ClassNotFoundException, BDExcepcion, BDExcepcion{
        
        boolean retorno=this.trip.cargarMarcas(desde,hasta,listado);
        
       
        ArrayList<Date> listaFechas=this.iteracionFechas(desde,hasta);
        
        this.comparaFunYMarcas(desde, hasta,listaFechas);
        this.comparaConHorarios(desde,hasta,listaFechas);
        this.trip.cerrarConexion();
        return retorno;
    }
    
    
    public void comparaFunYMarcas(Date desde,Date hasta,ArrayList<Date> listaFechas) throws ClassNotFoundException, SQLException{
        cnn=conexion.Cadena();
        Marca marca=null;
        
        
        this.listaFeriados=this.pers.listarFeriadosRango(desde, hasta);
        Integer contador=0;
        for(Funcionario f:listadoFunc){
            if(f.getCodFunc()==6100){
            String str= "prueba";
            }
             listaAuxiliar=new ArrayList<>();
            ArrayList<Marca> marcas=this.trip.traerTablaLocal(desde, hasta,f);
            if(f.getLugarTrabajo().getNumero()==1){
              if(marcas.size()>0){
                for (it = listaFechas.iterator(); it.hasNext();) {
                        Date date = it.next();
                            String fec=this.convertirFecha(date);
                           for(Marca m:marcas){
                               String marc=this.obtenerFechaTimestamp(m.getMarcaFecha());
                               if(!m.getTipo().equals("LICENCIA")&&!m.getTipo().equals("FALTA")&&!m.getTipo().equals("FERIADO")&&!m.getTipo().equals("MANUAL")){
//                                   
                                        if(marc.equals(fec)){
                                            contador++;
                                            if(!listaAuxiliar.contains(fec)){
                                                listaAuxiliar.add(fec);
                                            }
                                   
                                  }
//                             
                           }
                           }
                           //GENERA MARCAS FALTANTES PARA FUNCIONARIOS DE MONTEVIDEO
                          
                           switch(contador){
                                       case 1:
                                           marca=new Marca();
                                           marca.setFunCod(f.getCodFunc());
                                           marca.setMarcaFecha(this.fijaHora(date));
                                           marca.setTipo("VACIA");
                                           marca.setFecha(hoy);
                                           marca.setSupervisado(0);
                                           this.trip.insertarMarcas(marca);
                                       break;
                                       case 3:
                                           marca=new Marca();
                                           marca.setFunCod(f.getCodFunc());
                                           marca.setMarcaFecha(this.fijaHora(date));
                                           marca.setTipo("VACIA");
                                           marca.setFecha(hoy);
                                           marca.setSupervisado(0);
                                           this.trip.insertarMarcas(marca);
                                       break;
                                       case 5:
                                           marca=new Marca();
                                           marca.setFunCod(f.getCodFunc());
                                           marca.setMarcaFecha(this.fijaHora(date));
                                           marca.setTipo("VACIA");
                                           marca.setFecha(hoy);
                                           marca.setSupervisado(0);
                                           this.trip.insertarMarcas(marca);
                                       break;
                                           
                                   }
                           contador=0;
                                                     
                
            }
             //this.insertaFaltas(listaAuxiliar,listaFechas,f,cnn);
          }
            } //GENERA MARCAS PARA FUNCIONARIOS DEL INTERIOR
            else{
                if(f.getHorarios().size()>0){
                    for (it = listaFechas.iterator(); it.hasNext();) {
                         Date date = it.next();
                         GregorianCalendar cal = new GregorianCalendar();
                         cal.setTime(date);
                        for(Horario h:f.getHorarios()){
                             if(h.getDescripcion().equals("SABADO")&&cal.get(Calendar.DAY_OF_WEEK)==7){
                                if(this.encuentraMarca(this.fijaHoraInterior(date,h.getHoraEntrada()),marcas)==null){
                                    marca=new Marca();
                                    marca.setFunCod(f.getCodFunc());
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraEntrada()));
                                    marca.setTipo("INTERIOR");
                                    marca.setFecha(hoy);
                                    marca.setSupervisado(0);
                                    this.trip.insertarMarcas(marca);
                                }
                                if(this.encuentraMarca(this.fijaHoraInterior(date,h.getHoraSalida()),marcas)==null){
                                    marca=new Marca();
                                    marca.setFunCod(f.getCodFunc());
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraSalida()));
                                    marca.setTipo("INTERIOR");
                                    marca.setFecha(hoy);
                                    marca.setSupervisado(0);
                                    this.trip.insertarMarcas(marca);
                                }
                            }
                            else if(h.getDescripcion().equals("DOMINGO")&&cal.get(Calendar.DAY_OF_WEEK)==1){
                                   if(this.encuentraMarca(this.fijaHoraInterior(date,h.getHoraEntrada()),marcas)==null){
                                    marca=new Marca();
                                    marca.setFunCod(f.getCodFunc());
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraEntrada()));
                                    marca.setTipo("INTERIOR");
                                    marca.setFecha(hoy);
                                    marca.setSupervisado(0);
                                    this.trip.insertarMarcas(marca);
                                }
                                if(this.encuentraMarca(this.fijaHoraInterior(date,h.getHoraSalida()),marcas)==null){
                                    marca=new Marca();
                                    marca.setFunCod(f.getCodFunc());
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraSalida()));
                                    marca.setTipo("INTERIOR");
                                    marca.setFecha(hoy);
                                    marca.setSupervisado(0);
                                    this.trip.insertarMarcas(marca);
                                }
                            }
                            else if(h.getDescripcion().equals("LUNES A VIERNES")&&cal.get(Calendar.DAY_OF_WEEK)!=1&&cal.get(Calendar.DAY_OF_WEEK)!=7){
                                    if(this.encuentraMarca(this.fijaHoraInterior(date,h.getHoraEntrada()),marcas)==null){
                                    marca=new Marca();
                                    marca.setFunCod(f.getCodFunc());
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraEntrada()));
                                    marca.setTipo("INTERIOR");
                                    marca.setFecha(hoy);
                                    marca.setSupervisado(0);
                                    this.trip.insertarMarcas(marca);
                                }
                                if(this.encuentraMarca(this.fijaHoraInterior(date,h.getHoraSalida()),marcas)==null){
                                    marca=new Marca();
                                    marca.setFunCod(f.getCodFunc());
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraSalida()));
                                    marca.setTipo("INTERIOR");
                                    marca.setFecha(hoy);
                                    marca.setSupervisado(0);
                                    this.trip.insertarMarcas(marca);
                                }
                            }
                      
                        }
                      }
                }
                 
           }
        this.verificaLicencias(f,marcas,desde,listaFechas,listaAuxiliar);
    }
        
  }
    private ArrayList<Date> iteracionFechas(Date desde, Date hasta) {
            Calendar des = Calendar.getInstance();
            des.setTime(desde);
            Calendar has = Calendar.getInstance();
            has.setTime(hasta);
            ArrayList<Date> listaFechas = new ArrayList<Date>();
            while (!des.after(has)) {
                listaFechas.add(des.getTime());
                des.add(Calendar.DAY_OF_MONTH, 1);
            }
            
        return listaFechas;
    }
    
    private String convertirFecha(Date fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
        }
    
    return str;
    }
    
    private String obtenerFechaTimestamp(Timestamp fecha){
        Date date = new Date(fecha.getTime());
        return this.convertirFecha(date);
    }
    
    private Timestamp fijaHora(Date date){
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(date.getTime());
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    Timestamp ts = new java.sql.Timestamp(date.getTime());
    ts.setTime(c.getTimeInMillis());
    return ts;
    }

    private Timestamp fijaHoraInterior(Date date, Time hora) {
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(date.getTime()); 
        Calendar h=Calendar.getInstance();
        h.setTimeInMillis(hora.getTime()); 
        c.set(Calendar.HOUR_OF_DAY, h.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, h.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, h.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, 0);
        Timestamp ts = new java.sql.Timestamp(date.getTime());
        ts.setTime(c.getTimeInMillis());
        return ts;
    }

    private Marca encuentraMarca(Timestamp marca,ArrayList<Marca> marcas) {
       Integer i=0;
       Marca esta=null;
       if(marcas.size()>0){
            while(i<marcas.size()&&esta==null){
                if(this.obtenerFechaTimestamp(marcas.get(i).getMarcaFecha()).equals(this.obtenerFechaTimestamp(marca))){
                    esta=marcas.get(i);
                }
                i++;
            }
       }
       return esta;
    }
    
   private Marca encuentraMarcaSuper(Timestamp date, ArrayList<Marca> marcas) {
       Integer i=0;
       Marca esta=null;
       if(marcas.size()>0){
            while(i<marcas.size()&&esta==null){
                String fecha=this.convertirFecha(marcas.get(i).getMarcaFecha());
                String fecLic=this.convertirFecha(date);
                if(fecha.equals(fecLic))
                {
                    esta=marcas.get(i);
                }
                i++;
            }
       }
       return esta;
    }
    
   

    private void verificaLicencias(Funcionario f, ArrayList<Marca> marcas, Date desde,ArrayList<Date> listaFechas,ArrayList<String> listaAuxiliar) throws ClassNotFoundException, SQLException {
        ArrayList<MovimientoLicencia> listadoMov=this.pers.listadoMovimientosMarcas(f.getCodFunc(), desde);
        GregorianCalendar cal = new GregorianCalendar();
        ArrayList<String> listaAux=new ArrayList<>();
        if(listadoMov.size()>0){
        for(MovimientoLicencia m:listadoMov){
            ArrayList<Date> periodoLic=this.iteracionFechas(this.fijaHora(m.getFechaIni()), this.fijaHora(m.getFechaFin()));
            if(periodoLic.size()>0){
            for(Iterator<Date> li=periodoLic.iterator();li.hasNext();){
                    Date per = li.next();
                    String lic=this.convertirFecha(per);
                    cal.setTime(per);
                    if(cal.get(Calendar.DAY_OF_WEEK)!=1){
                            for (it = listaFechas.iterator(); it.hasNext();) {
                                     Date date = it.next();
                                     if(lic.equals(this.convertirFecha(date))){
                                         if(this.encuentraMarca(this.fijaHora(per), marcas)==null){
                                                                                                  
                                                Marca marca=new Marca();
                                                marca.setFunCod(f.getCodFunc());
                                                marca.setMarcaFecha(this.fijaHora(per));
                                                marca.setTipo("LICENCIA");
                                                marca.setFecha(hoy);
                                                marca.setSupervisado(0);
                                                //this.codPer.insertarCodigoMarca(this.trip.obtenerMaxCodFunc(f.getCodFunc()),marca.getFunCod(),m.getTipoLic(),1.0);
                                                this.trip.insertarMarcas(marca);
                                                listaAux.add(lic);
                                             
                                             }
                                         else{
                                             Marca sup=this.encuentraMarcaSuper(this.fijaHora(per), marcas);
                                             if(sup!=null && sup.getTipo().equals("RELOJ")){
                                             sup.setTipo("RELOJ SUP");
                                             sup.setSupervisado(0);
                                                 if(sup.getFunCod()==510&& (sup.getId()==146 || sup.getId()==145|| sup.getId()==147||sup.getId()==148)){
                                                int e=0;
                                            }
                                             this.trip.actualizarMarca(sup,null);
                                             }
                                         }
                                     }
                                                                     
                            }
            }
            }
        }
        }
        }
        
        this.insertaFaltas(listaAuxiliar, listaFechas, listaAux,f,marcas);
        
    }



    public ArrayList<Marca> traerTablaFiltro(Date desde, Date hasta, Funcionario f,Integer sup) throws ClassNotFoundException, SQLException {
    
            return this.trip.traerTablaFiltro(desde, hasta, f,sup);
      
    }
    
    public Integer actualizaMarca(Marca m,Marca marcavieja) throws SQLException, ClassNotFoundException{
       cnn=conexion.Cadena();
       return this.trip.actualizarMarca(m,marcavieja);
    }

    private void insertaFaltas(ArrayList<String> listaAuxiliar, ArrayList<Date> listaFechas, ArrayList<String> listaAux, Funcionario f,ArrayList<Marca> marcas) throws ClassNotFoundException, SQLException {
        
        GregorianCalendar cal = new GregorianCalendar();
        for (it = listaFechas.iterator(); it.hasNext();) {
            Date date=it.next();
            String fec=this.convertirFecha(date);
            cal.setTime(date);   
                if(cal.get(Calendar.DAY_OF_WEEK)!=1){
                    if(!listaAuxiliar.contains(fec)){
                        if(!listaAux.contains(fec)){
                            Timestamp marc=this.fijaHora(date);
                            if(f.getLugarTrabajo().getNumero()==1){
                                if(this.encuentraMarca(marc, marcas)==null){
                                    if(this.esFeriado(date)==false){
                                        Marca m=new Marca();
                                        m.setFunCod(f.getCodFunc());
                                        m.setTipo("FALTA");
                                        m.setFecha(hoy);
                                        m.setSupervisado(0);
                                        this.insertaMarcasFaltaFuncMdv(f,m,cal,date,marcas);
                                       }
                                    else{
                                        Marca m=new Marca();
                                        m.setMarcaFecha(marc);
                                        m.setFunCod(f.getCodFunc());
                                        m.setTipo("FERIADO");
                                        m.setFecha(hoy);
                                        m.setSupervisado(0);
                                        this.trip.insertarMarcas(m);
                                    }
                                }
                            }
                            else{
                                Marca ma=this.encuentraMarca(marc, marcas);
                                if(ma!=null){
                                    if(ma.getTipo().equals("INTERIOR")){
                                        if(this.esFeriado(date)){
                                            ma.setTipo("FERIADO");
                                            ma.setFecha(hoy);
                                            ma.setSupervisado(0); 
                                            if(ma.getFunCod()==510&& (ma.getId()==146 || ma.getId()==145|| ma.getId()==147||ma.getId()==148)){
                                                int e=0;
                                            }
                                            this.trip.actualizarMarca(ma,null);
                                        }
                                    }
                                }
                            }
                        }
                    }
               }
        }
    }
    
    private void insertaMarcasFaltaFuncMdv(Funcionario f,Marca marca,Calendar cal,Date date,ArrayList<Marca> marcas) throws ClassNotFoundException, SQLException{
         if(f.getHorarios().size()>0){
                       for(Horario h:f.getHorarios()){
                             if(h.getDescripcion().equals("SABADO")&&cal.get(Calendar.DAY_OF_WEEK)==7){
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraEntrada()));
                                    this.trip.insertarMarcas(marca);
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraSalida()));
                                    this.trip.insertarMarcas(marca);
                                
                            }
                            else if(h.getDescripcion().equals("DOMINGO")&&cal.get(Calendar.DAY_OF_WEEK)==1){
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraEntrada()));
                                    this.trip.insertarMarcas(marca);
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraSalida()));
                                    this.trip.insertarMarcas(marca);
                                
                            }
                            else if(h.getDescripcion().equals("LUNES A VIERNES")&&cal.get(Calendar.DAY_OF_WEEK)!=1&&cal.get(Calendar.DAY_OF_WEEK)!=7){
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraEntrada()));
                                    this.trip.insertarMarcas(marca);
                                    marca.setMarcaFecha(this.fijaHoraInterior(date,h.getHoraSalida()));
                                    this.trip.insertarMarcas(marca);
                                
                            }
                      
                        }
                      
                }
   }

    private boolean esFeriado(Date date) {
       boolean es=false;
       Integer i=0;
       while(i<this.listaFeriados.size()&&!es){
           if(this.convertirFecha(listaFeriados.get(i).getFecha()).equals(this.convertirFecha(date))){
               es=true;
           }
           i++;
       }
       return es;
    }

    private Integer conteoMarcas(ArrayList<Marca> marcas, String marc) {
        Integer retorno=0;
        for(Marca m:marcas){
            if(marc.equals(this.obtenerFechaTimestamp(m.getMarcaFecha()))){
                retorno++;
            }
        }
        return retorno;
    }
    
    
    //COMPARACION CON HORARIOS

    private void comparaConHorarios(Date desde, Date hasta,ArrayList<Date> listaFechas) throws ClassNotFoundException, SQLException {
        
        for(Funcionario f: listadoFunc){
            if(f.getLugarTrabajo().getNumero()==1 && f.getCodFunc()!=1200){
                ArrayList<Marca> marcas=this.trip.traerTablaLocal(desde, hasta,f);
                for (it = listaFechas.iterator(); it.hasNext();) {
                     Date date = it.next();
                     String fec=this.convertirFecha(date);
                     ArrayList<Marca>marcs=this.cantMarcas(marcas, fec);
                    
                     if(f.getCodFunc()==150){
                         String agua="";
                     }
                     switch (marcs.size()) {
                        case 2:
                            if(marcs.get(0).getTipo().equals("VACIA")){
                                if(this.esSalida(marcs.get(1),f)){
                                this.fijaIncongruencia(marcs.get(1),f,"Salida");
                                }
                                else{
                                this.fijaIncongruencia(marcs.get(1),f,"Entrada");    
                                }
                            }
                            else{
                             this.fijaIncongruencia(marcs.get(0),f,"Entrada");
                             this.fijaIncongruencia(marcs.get(1),f,"Salida");
                            }
                           
                            break;
                        case 4:
                            if(marcs.get(0).getTipo().equals("VACIA")){
                                if(this.esSalida(marcs.get(1),f)){
                                    this.fijaIncongruencia(marcs.get(1),f,"Salida");
                                    if(!this.esSalida(marcs.get(3),f)){
                                       this.fijaIncongruencia(marcs.get(3),f,"Entrada");
                                    }
                                }
                                else{
                                    this.fijaIncongruencia(marcs.get(1),f,"Entrada");     
                                    if(this.esSalida(marcs.get(3),f)){
                                       this.fijaIncongruencia(marcs.get(3),f,"Salida");
                                    }
                                }
                            }
                            else{
                            this.fijaIncongruencia(marcs.get(0),f,"Entrada");    
                            this.fijaIncongruencia(marcs.get(3),f,"Salida");
                            }
                            
                            break;
                        case 6:
                               if(marcs.get(0).getTipo().equals("VACIA")){
                                if(this.esSalida(marcs.get(1),f)){
                                    this.fijaIncongruencia(marcs.get(1),f,"Salida");
                                    if(!this.esSalida(marcs.get(5),f)){
                                       this.fijaIncongruencia(marcs.get(5),f,"Entrada");
                                    }
                                }
                                else{
                                    this.fijaIncongruencia(marcs.get(1),f,"Entrada");     
                                    if(this.esSalida(marcs.get(5),f)){
                                       this.fijaIncongruencia(marcs.get(5),f,"Salida");
                                    }
                                }
                            }
                            else{
                            this.fijaIncongruencia(marcs.get(0),f,"Entrada");
                            this.fijaIncongruencia(marcs.get(5),f,"Salida");
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
    
    private ArrayList<Marca> cantMarcas(ArrayList<Marca>marcas, String fec){
        ArrayList<Marca>marcs=new ArrayList<>();        
            for(Marca m:marcas){
                String marc=this.obtenerFechaTimestamp(m.getMarcaFecha());
                if(fec.equals(marc)){
                    marcs.add(m);
                }
            }
            return marcs;
        }

    private void fijaIncongruencia(Marca m, Funcionario f,String str) throws SQLException, ClassNotFoundException {
        if(m.getIncongruencia()==0){    
            Date date = new Date(m.getMarcaFecha().getTime());
            Time time=new Time(m.getMarcaFecha().getTime());
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
                for(Horario h:f.getHorarios()){
                    if(str.equals("Entrada")){
                        if(h.getDescripcion().equals("SABADO")&&cal.get(Calendar.DAY_OF_WEEK)==7){
                           this.comparaFechas(time, h.getHoraEntrada(), m);
                        }
                        else if(h.getDescripcion().equals("DOMINGO")&&cal.get(Calendar.DAY_OF_WEEK)==1){
                           this.comparaFechas(time, h.getHoraEntrada(), m);
                        }
                        else if(h.getDescripcion().equals("LUNES A VIERNES")&&cal.get(Calendar.DAY_OF_WEEK)!=1&&cal.get(Calendar.DAY_OF_WEEK)!=7){
                           this.comparaFechas(time, h.getHoraEntrada(), m);
                        }
                }
                    else if(str.equals("Salida")){
                        if(h.getDescripcion().equals("SABADO")&&cal.get(Calendar.DAY_OF_WEEK)==7){
                           this.comparaFechas(time, h.getHoraSalida(), m);
                        }
                        else if(h.getDescripcion().equals("DOMINGO")&&cal.get(Calendar.DAY_OF_WEEK)==1){
                           this.comparaFechas(time, h.getHoraSalida(), m);
                        }
                        else if(h.getDescripcion().equals("LUNES A VIERNES")&&cal.get(Calendar.DAY_OF_WEEK)!=1&&cal.get(Calendar.DAY_OF_WEEK)!=7){
                           this.comparaFechas(time, h.getHoraSalida(), m);
                        }
                    }
                    
                
                }
        }
    }
    
    private void comparaFechas(Time time, Time entrada, Marca m) throws SQLException, ClassNotFoundException{
        Integer dif=0;  
        Date fecha1=this.armaFecha(time);
        Date fecha2=this.armaFecha(entrada);
        //SI HORA DE MARCA ES MAYOR A HORA DE HORARIO llega tarde
        if(fecha1.after(fecha2)){
        dif=this.obtieneDiferencia(fecha1, fecha2);
        }
        //SI HORA DE HORARIO ES MAYOR A HORA DE MARCA entra antes
        else{
        dif=this.obtieneDiferencia(fecha2, fecha1);
        dif=dif*-1;
       }
        this.actualizaDiferenciaMinutos(dif, m);
}
    
    
    private void actualizaDiferenciaMinutos(Integer dif, Marca m) throws SQLException, ClassNotFoundException{
              if(dif>0||dif<0){
                        //parametrizar el tiempo
                        //if(dif>=this.diferenciaMarca){
                            m.setIncongruencia(dif);
                            if(!m.getTipo().equals("FALTA")){
                                 m.setSupervisado(0);
                            }
                            if(m.getFunCod()==510&&(m.getId()==146 || m.getId()==145|| m.getId()==147||m.getId()==148)){
                                                int e=0;
                                            }
                            this.trip.actualizarMarca(m,null);
                       // }
                    }
                    else{
                        //if(dif<(this.diferenciaMarca*-1)){
                           m.setIncongruencia(dif);
                            if(!m.getTipo().equals("FALTA")){
                                 m.setSupervisado(0);
                           }
                            if(m.getFunCod()==510 && (m.getId()==146 || m.getId()==145|| m.getId()==147||m.getId()==148)){
                                                int e=0;
                                            }
                           this.trip.actualizarMarca(m,null);
                        //}
                    }
    }
    
    private Date armaFecha(Time time){
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(time.getTime());
    c.set(Calendar.MONTH, 1);
    c.set(Calendar.DAY_OF_MONTH, 1);
    c.set(Calendar.YEAR, 0001);
    Date ts = new java.sql.Date(time.getTime());
    ts.setTime(c.getTimeInMillis());
    return ts;
    }
    
    private int obtieneDiferencia(Date fecha1, Date fecha2){
        int diferencia=(int) ((fecha1.getTime()-fecha2.getTime())/1000);
 
        int dias=0;
        int horas=0;
        int minutos=0;
        if(diferencia>86400) {
            dias=(int)Math.floor(diferencia/86400);
            diferencia=diferencia-(dias*86400);
            dias=(dias*24)*60;
        }
        if(diferencia>3600) {
            horas=(int)Math.floor(diferencia/3600);
            diferencia=diferencia-(horas*3600);
            horas=horas*60;
        }
        if(diferencia>=60) {
            minutos=(int)Math.floor(diferencia/60);
            diferencia=diferencia-(minutos*60);
        }
           minutos=minutos+horas+dias;
    return minutos;
    }

    private boolean esSalida(Marca m, Funcionario f) {
    
        boolean retorno=false;    
        Date date = new Date(m.getMarcaFecha().getTime());
            Time time=new Time(m.getMarcaFecha().getTime());
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
                for(Horario h:f.getHorarios()){
                     if(h.getDescripcion().equals("SABADO")&&cal.get(Calendar.DAY_OF_WEEK)==7){
                         retorno=this.cercania(time,date,h.getHoraEntrada(),h.getHoraSalida());
                     }
                     else if(h.getDescripcion().equals("DOMINGO")&&cal.get(Calendar.DAY_OF_WEEK)==2){
                         retorno=this.cercania(time,date,h.getHoraEntrada(),h.getHoraSalida());
                     }
                     else if(h.getDescripcion().equals("LUNES A VIERNES")&&cal.get(Calendar.DAY_OF_WEEK)!=1&&cal.get(Calendar.DAY_OF_WEEK)!=7){
                         retorno=this.cercania(time,date,h.getHoraEntrada(),h.getHoraSalida());
                     }
                }
                return retorno;
    }

private boolean cercania(Time time, Date date, Time horaEntrada, Time horaSalida) {
        boolean retorno=false;  
        Date tim=this.armaFecha(time);    
        Date ent=this.armaFecha(horaEntrada);
        Date sal=this.armaFecha(horaSalida);
                if(tim.before(ent)){
                             retorno=false;
                         }
                else if(tim.equals(ent)){
                    retorno=false;
                }
                         else if(tim.after(sal)){
                             retorno=true;
                         }
                         else if(tim.equals(sal)){
                             retorno=true;
                         }
                         
                         else{
                             GregorianCalendar entrada = new GregorianCalendar();
                                entrada.setTime(horaEntrada);
                                GregorianCalendar salida = new GregorianCalendar();
                                salida.setTime(horaSalida);
                            if(this.obtieneDiferencia(sal, tim)<this.obtieneDiferencia(tim,ent)){
                                 retorno=true;
                             
                              }
                            else{
                                retorno=false;
                            }
                         }
        return retorno;
    }

    public ArrayList<Integer> procesar(Date desde, Date hasta) throws SQLException, ClassNotFoundException {
        ArrayList<Funcionario> funcionarios=this.pers.listarFuncionariosParaLic();
        Integer ret=0;
        Integer pro=0;
        ArrayList<Integer>rets=new ArrayList<>();
        ArrayList<Codigo> codigos=this.codPer.obtenerListadoCodigo();
        Date fecha=new Date();
        String fe=this.fijaDia(fecha);
        for(Funcionario f:funcionarios){
                ArrayList<IngresoMarca> ingresos=this.trip.obtenerIngresosMarca(desde, hasta,f.getCodFunc());
                for(IngresoMarca m:ingresos){
                  ret+=this.trip.insertarEnIngresos(this.chequeaCodigo(m, codigos),fe);
                }
        }
        pro+=this.trip.procesar(desde,hasta);
        rets.add(ret);
        rets.add(pro);
        return rets;
    }
        
    private IngresoMarca chequeaCodigo(IngresoMarca m, ArrayList<Codigo> codigos){
        Integer i=0;
        boolean encuentra=false;
        while(i<codigos.size()&&!encuentra){
            if(codigos.get(i).getCod().equals(m.getCodigo())){
                if(codigos.get(i).getTipoUnidad().equals(0)){
                   m.setCantidad(this.recalculaCantidad(m.getCantidad()));
                }
                encuentra=true;
            }
            i++;
        }
        return m;
    }
    
    private Double recalculaCantidad(Double cant){
         Double retorno=0.0;
         retorno=cant/30;
         DecimalFormat df = new DecimalFormat("#.00");
         String i=df.format(retorno);
         i=i.replace(',', '.');
         retorno=Double.parseDouble(i);
         retorno=(Double) 0.5 * Math.round(retorno * 2);
         return retorno;
    }
    
//    private Double recalculaCantidad(Double cant){
//        Double retorno=0.0;
//         DecimalFormat df = new DecimalFormat("#.00");
////         Decimal i=df.format(cant);
////        
////         return (float) (Math.ceil(x * 2) / 2);
//    }
    
     public String fijaDia(Date fecha){
        String str="";
        if(fecha!=null){
        Calendar calendar = Calendar.getInstance();
	calendar.setTime(fecha); // Configuramos la fecha que se recibe
	calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date d=calendar.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
        str= sdf.format(d);
           }
        return str;
         }
   public Date ultimaMarca() throws SQLException, ClassNotFoundException{
      Date fecha=this.trip.ultimaMarca();
      
      return fecha;
   }
   
    public Integer sinSupervisar(Date desde,Date hasta) throws ClassNotFoundException, SQLException{
        return this.trip.sinSupervisar(desde, hasta);
    }

    public ArrayList<Codigo> traerCodigosPeriodo(Date desde, Date hasta, String cod) throws ClassNotFoundException, SQLException {
       Integer codigo=null;
        if(!cod.equals("")){
            codigo=Integer.valueOf(cod);
        }    
        return this.codPer.traerCodigosPeriodo(desde,hasta,codigo);
    }
    
   public ArrayList<Marca> codigosFuncionarios(Funcionario f,Date desde,Date hasta,Integer sup) throws ClassNotFoundException, SQLException{
       ArrayList<Marca> marcas = this.trip.codigosFuncionarios(f, desde, hasta, sup);
       ArrayList<Marca> marcasDiez=this.trip.codigoDiezDiasGenerado(f, this.obtieneMes());
       return fusionaMarcas(marcas,marcasDiez);
   } 
   
   private ArrayList<Marca> fusionaMarcas(ArrayList<Marca> marcas, ArrayList<Marca> marcasDiez){
       if(!marcasDiez.isEmpty()){
            for(Marca m : marcasDiez){
                    marcas.add(m);
            }
       }
       return marcas;
   }
   
    private Integer obtieneMes(){
    Date fecha = new Date();
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(fecha.getTime());
    int mes = c.get(Calendar.MONTH)+1;
    return mes;
    }
   public ArrayList<Codigo> codigosDistintos(Funcionario f,Date desde,Date hasta,Integer sup) throws ClassNotFoundException, SQLException{
       return this.trip.codigosDistintos(f, desde, hasta, sup);
   }
   public Double CantidadCodigo(Date desde,Date hasta,Integer codFunc,Integer codigo) throws ClassNotFoundException, SQLException{
       return this.trip.CantidadCodigo(desde, hasta, codFunc, codigo);
   }

    public boolean generarMarcaManual(Marca m) throws ClassNotFoundException, SQLException {
        return this.trip.generarMarcaManual(m);
    }
   
   
   
   
   
}
