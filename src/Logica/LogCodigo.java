
package Logica;

import Dominio.Codigo;
import Dominio.CodigoPrelacion;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Dominio.Marca;
import Dominio.Retencion;
import Dominio.TipoVale;
import Dominio.Vale;
import Persistencia.BDExcepcion;
import Persistencia.Conexion;
import Persistencia.PersistenciaCodigo;
import Presentacion.Liquidaciones.ModalFechasVales;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogCodigo {
    private PersistenciaCodigo pers;
    private Conexion conexion;
    public int bloqueoContaduria;
 
    public LogCodigo() {
        this.pers=new PersistenciaCodigo();
    }
    
    
    public ArrayList<Codigo> cargaComboCodigoLic() throws BDExcepcion {
        return this.pers.cargaComboCodigoLic();
    }
    
       
    public Codigo obtenerCodigoTipo(Integer tipoLic) throws ClassNotFoundException, SQLException {
        return pers.obtenerCodigoTipo(tipoLic);
    }

    public ArrayList<Codigo> cargaComboCodigoFull() throws SQLException, ClassNotFoundException {
       return this.pers.cargaComboCodigoFull();
    }
    
     public boolean insertarCodigoMarca(Long id, Integer codFunc,Integer codigo,Double cantidad,Date fecha) throws SQLException{
         boolean es=this.pers.insertarCodigoMarca(id, codFunc, codigo, cantidad,fecha);
//         if(codigo==10){
//             
//         }
         return es;
     }

    public ArrayList<Integer> obtenerCodigos(Integer funCod, Long id) throws ClassNotFoundException, SQLException {
        return this.pers.obtenerCodigos(funCod,id);
    }
    
    public boolean bloqueoLiq() throws ClassNotFoundException, SQLException{
        return this.pers.bloqueoLiq();
    }

    public boolean borrarCodigoMarca(Long id, Integer funCod, Integer codigo) throws SQLException {
        return this.pers.borrarCodigoMarca(id,funCod,codigo);
    }

    public Codigo obtenerCodigoTipoLiq(Integer codigo) throws BDExcepcion{
        return pers.obtenerCodigoTipoLiq(codigo);
    }
    public Codigo obtenerCodigoTipoLiq1(Integer codigo) throws BDExcepcion{
        return pers.obtenerCodigoTipoLiq1(codigo);
    }

    public ArrayList<Codigo> cargaComboCodigoFullLiq() throws ClassNotFoundException, SQLException {
        return this.pers.carcaComboCodigoFullLiq();
    }
    public ArrayList<Codigo> cargaComboCodigoLike(String desc) throws ClassNotFoundException, SQLException {
        return this.pers.cargaComboCodigoLike(desc);
    }

    public boolean estaEnPersIngresos(Funcionario f, Codigo cod) throws BDExcepcion{
       return this.pers.estaEnPersIngresos(f,cod);
    }

    public ArrayList<Ingreso> traePersIngresos(Integer codFunc) throws BDExcepcion {
    return this.pers.traePersIngresos(codFunc);
    }

    public boolean borrarCodigoEnPersIngresos(Ingreso ingres) throws BDExcepcion{
    return this.pers.borrarCodigoEnPersIngresos(ingres);
    }

    public int insertarEnPersIngresos(ArrayList<Ingreso> codigos,String fechaLiq) throws SQLException {
    return this.pers.insertarEnPersIngresos(codigos,fechaLiq);
    }

    public int actualizarPersIngresos(Ingreso ing, Double d) throws BDExcepcion {
        return this.pers.actualizaPersIngresos(ing,d);
    }
    public ArrayList<Ingreso> traeIngresosPorCod(Integer cod) throws BDExcepcion {
        return this.pers.traeIngresosPorCod(cod);
    }
    public ArrayList<Codigo> cargaComboCodigoFijo() throws SQLException, ClassNotFoundException{
        return this.pers.cargaComboCodigoFijo();
    }
    public ArrayList<Ingreso> cargaMovimientosFijo(Integer cod) throws SQLException, ClassNotFoundException{
        return this.pers.cargaMovimientosFijo(cod);
    }

    public boolean estaEnCodigosFijos(Funcionario f, Codigo cod) throws BDExcepcion {
        return this.pers.estaEnCodigosFijos(f,cod);
    }

    public boolean insertaEnCodigosFijos(Ingreso ing) throws BDExcepcion{
        return this.pers.insertaEnCodigosFijos(ing);
    }
    public boolean actualizaEnCodigosFijos(Ingreso ing) throws ClassNotFoundException, SQLException {
        return this.pers.actualizaEnCodigosFijos(ing);
    }

    public boolean borrarCodigoEnPersCodFijo(Ingreso ingres)  throws BDExcepcion {
        return this.pers.borrarCodigoEnPersCodFijo(ingres);
    }

    public ArrayList<Ingreso> cargaMovimientosFijoFunc(Integer codFunc) throws BDExcepcion{
        return this.pers.cargaMovimientosFijoFunc(codFunc);
    }
     public ArrayList<Codigo> cargaComboRetencionesFijo() throws SQLException, ClassNotFoundException{
         return  this.pers.cargaComboRetencionesFijo();
    }
    public ArrayList<Retencion> listarRetenciones(int activa,Funcionario f, Codigo cod) throws BDExcepcion{
        return this.pers.listarRetenciones(activa, f, cod);
    }

    public boolean ingresarRetencionFija(Funcionario f, Codigo c, int sueldo, int agui, int tipo,String valor) throws ClassNotFoundException, SQLException {
        Double dValor;
        if(tipo==0){
            dValor = Double.parseDouble(valor);
        }
        else{
            dValor = Double.parseDouble(valor)/100;
        }
        
        return this.pers.ingresarRetencionFija(f,c,sueldo,agui,tipo,dValor);
    }
    
    public boolean estaEnRetFijas(Funcionario f, Codigo cod) throws ClassNotFoundException, SQLException {
        return this.pers.estaEnRetFijas(f,cod);
    }

    public boolean eliminarRetencionFija(Retencion ret) throws SQLException, ClassNotFoundException {
        return this.pers.eliminarRetencionFija(ret);
    }

    public boolean actualizaRetencion(Retencion r) throws ClassNotFoundException, SQLException {
        return this.pers.actualizaRetencion(r);
    }
    
    public Integer calculoTopesCodigos(Marca m, Integer cod) throws ClassNotFoundException, SQLException{
        Integer retorno = 0;
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer tope = this.pers.obtengoTope(cod,cnn);
        if(tope!=0){
        Timestamp desde = this.calcularDesde(m.getFechaUso(),cod);
        Timestamp hasta = this.calcularHasta(m.getFechaUso(),cod);
        Integer cantPeriodo = this.pers.totalPeriodo(desde,hasta,m,cod,cnn);
        
        retorno = tope-cantPeriodo;
        }
        cnn.close();
        return retorno;
    }

    private Timestamp calcularDesde(Date fecha, Integer cod) {
        Timestamp ts= null;
        if(cod==35){
            Calendar c=Calendar.getInstance();
            c.setTimeInMillis(fecha.getTime());
            c.set(Calendar.DAY_OF_MONTH, 01);
            c.set(Calendar.MONTH, 01-1);
            ts = new java.sql.Timestamp(fecha.getTime());
            ts.setTime(c.getTimeInMillis());
        }
        else if(cod==36){
            Calendar c=Calendar.getInstance();
            c.setTimeInMillis(fecha.getTime());
            if(c.get(Calendar.DAY_OF_MONTH)>=21){
                c.set(Calendar.DAY_OF_MONTH, 21);
                c.set(Calendar.MONTH, c.get(Calendar.MONTH));
                ts = new java.sql.Timestamp(fecha.getTime());
                ts.setTime(c.getTimeInMillis());
            }
            else{
                c.set(Calendar.DAY_OF_MONTH, 21);
                c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
                ts = new java.sql.Timestamp(fecha.getTime());
                ts.setTime(c.getTimeInMillis());
            }
        }
        
        return ts;
    }
    
    private Timestamp fijaHoraDesde(Date date){
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

    private Timestamp calcularHasta(Date fecha, Integer cod) {
       Timestamp ts= null;
        if(cod==35){
            Calendar c=Calendar.getInstance();
            c.setTimeInMillis(fecha.getTime());
            c.set(Calendar.DAY_OF_MONTH, 31);
            c.set(Calendar.MONTH, 11);
            ts = new java.sql.Timestamp(fecha.getTime());
            ts.setTime(c.getTimeInMillis());
            
        }
        else if(cod==36){
            Calendar c=Calendar.getInstance();
            c.setTimeInMillis(fecha.getTime());
            if(c.get(Calendar.DAY_OF_MONTH)>=21){
                c.set(Calendar.DAY_OF_MONTH, 20);
                c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
                ts = new java.sql.Timestamp(fecha.getTime());
                ts.setTime(c.getTimeInMillis());
            }
            else{
                c.set(Calendar.DAY_OF_MONTH, 20);
                c.set(Calendar.MONTH, c.get(Calendar.MONTH));
                ts = new java.sql.Timestamp(fecha.getTime());
                ts.setTime(c.getTimeInMillis());
            }
        }
        
        return ts;
    }

    private void getFunCod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Ingreso> cargoVales(String fechaLiq,Codigo cod,TipoVale tipo) throws BDExcepcion {
       Date fechaIni=null;
       Date fechaFin=null;
       if(!this.esFechaAguinaldo(fechaLiq)){
       fechaIni=this.armarFechaIni(fechaLiq);
       fechaFin=this.armaFecha(fechaLiq);  
       tipo=null;
       }
       else{
       fechaIni=this.armarFechaIniAgui(fechaLiq);
       fechaFin=this.armaFecha(fechaLiq);   
       tipo.setCodigo(4);
       }
       
       ArrayList<Ingreso> vales=this.pers.cargoVales(fechaIni,fechaFin,cod,tipo);
       
       return vales;
    }
    
    private Date armarFechaIni(String fechaLiq){
    Calendar c=Calendar.getInstance();
    Date fecha = this.armaFecha(fechaLiq);
    c.setTimeInMillis(fecha.getTime());
    c.set(Calendar.MONTH, c.get(Calendar.MONTH));
    c.set(Calendar.DAY_OF_MONTH, 01);
    c.set(Calendar.YEAR, c.get(Calendar.YEAR));
    Date ts = new java.sql.Date(fecha.getTime());
    ts.setTime(c.getTimeInMillis());
    return ts;
    }
     
    private Date buscoUltimoDiaMes(Date fechaAux){
        Calendar aux = Calendar.getInstance(); 
        aux.setTime(fechaAux);
        Integer dia=0;
        int mes=aux.get(Calendar.MONTH);
        switch(mes){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                dia=31;
            break;
            case 2:
                if(aux.get(Calendar.YEAR)%4==0){
                    dia=29;
                }
                else{
                    dia=28;
                }
            break;
            case 4: case 6: case 9: case 11:
                dia=30;
            break;
            default:
            break;
        }
        aux.set(Calendar.DAY_OF_MONTH,dia);
        
        return aux.getTime();
    }
    
     public Date armaFecha(String fechaLiq){
        Date date =null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date= formatter.parse(fechaLiq);
          
                    } catch (ParseException ex) {
            Logger.getLogger(ModalFechasVales.class.getName()).log(Level.SEVERE, null, ex);
        }
          return date;
    }

    public ArrayList<TipoVale> cargoTipoVales() {
      return this.pers.cargoTipoVales();
    }

    public ArrayList<Vale> cargoValesFecha(String fecha,TipoVale tipo) {
       return this.pers.cargoValesFecha(fecha,tipo);
    }

     public ArrayList<Object> cargoValesFechaObject(String fecha,TipoVale tipo) {
       return this.pers.cargoValesFechaObject(fecha,tipo);
    }
    
    public ArrayList<Integer> ingresarVales(ArrayList<Vale> vales, TipoVale tipo, String fecha) {
       return this.pers.ingresarVales(vales,tipo,fecha);
    }

    public boolean chequeaTiposDistintos(TipoVale tipo, String fecha) {
        ArrayList<TipoVale> tipos = this.pers.traeDistintosTipos(fecha);
        boolean ret=false;
        if(tipos.size()==1){
            if(!tipos.get(0).getCodigo().equals(tipo.getCodigo())){
                ret=false;
            }
            else{
                ret=true;
            }
        }
        else{
            ret=true;
        }
        return ret;
    }

    public ArrayList<Object> cargoLiquidacionFecha(String fecha) {
        return this.pers.cargoLiquidacionFecha(fecha);
    }

    public Integer borrarEnPersIngresos(Integer cod, String fechaLiq) throws SQLException {
       return this.pers.borrarEnPersIngresos(cod,fechaLiq);
    }

    public String buscoTablaRetAce(String fecha) {
        fecha=formateaFechaNombre(fecha);
        if(fecha.length()==6){
            fecha="ace_socios_"+fecha;
            fecha=this.pers.buscoNombreTabla(fecha.toUpperCase());
        }
        return fecha;
    }

    private String formateaFechaNombre(String fecha) {
        fecha=fecha.replace("/", "");
        String mes=fecha.substring(2, 4);
        String año=fecha.substring(4, 8);
        Integer m = Integer.valueOf(mes)-1;
        if(m<10){
            mes="0"+String.valueOf(m);
        }
        String retorno=año+mes;
        return retorno;
    }

    public ArrayList<Ingreso> cargoRetencionesAce(String nombreTabla,Codigo cod) throws BDExcepcion {
        return this.pers.cargoRetencionesAce(nombreTabla,cod);
    }

    public Boolean hayCodigoEnIngreso(Codigo cod) throws BDExcepcion {
        return this.pers.hayCodigoEnIngreso(cod.getCod());
    }

    public boolean eliminaCodigoEnIngreso(Codigo cod) {
      return this.pers.eliminaCodigoEnIngreso(cod.getCod());
    }

    public ArrayList<Object> cargoLiquidacionFechaSinCuenta(String fecha) {
        return this.pers.cargoLiquidacionFechaSinCuenta(fecha);
    }

    public ArrayList<Object> cargoValesFechaObjectSinCuenta(String fecha) {
        return this.pers.cargoValesFechaObjectSinCuenta(fecha);
    }

    public boolean esFechaAguinaldo(String text) {
       boolean retorno=false;
          
        Calendar c=Calendar.getInstance();
        Date fecha = this.armaFecha(text);
        c.setTimeInMillis(fecha.getTime());
        c.set(Calendar.MONTH, c.get(Calendar.MONTH));
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        Date ts = new java.sql.Date(fecha.getTime());
        ts.setTime(c.getTimeInMillis());
        
        int dia=c.get(Calendar.DAY_OF_MONTH);
        int mes=c.get(Calendar.MONTH)+1;
        
        if(dia==15&&(mes==12||mes==06)){
            retorno=true;
        }
       
        return retorno;
    }

    private Date armarFechaIniAgui(String fechaLiq) {
        Calendar c=Calendar.getInstance();
        Date fecha = this.armaFecha(fechaLiq);
        Date ts=null;
        c.setTimeInMillis(fecha.getTime());
        int mes=c.get(Calendar.MONTH)+1;
        if(mes==06){
        c.set(Calendar.MONTH,01);
        c.set(Calendar.DAY_OF_MONTH, 01);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        ts = new java.sql.Date(fecha.getTime());
        ts.setTime(c.getTimeInMillis());
        }
        else if(mes==12){
        c.set(Calendar.MONTH,06);
        c.set(Calendar.DAY_OF_MONTH, 01);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        ts = new java.sql.Date(fecha.getTime());
        ts.setTime(c.getTimeInMillis());
        }
        return ts;
    }

    public int bloqueoContaduria() throws BDExcepcion {
       return this.pers.bloqueoContaduria();
    }

    public ArrayList<Codigo> cargaGrupoDosYTres() throws BDExcepcion {
        return this.pers.cargaComboGrupoDosYTres();
    }

    public ArrayList<CodigoPrelacion> cargoPrelacionCodigos(Codigo codigo) throws BDExcepcion {
        return this.pers.cargoPrelacionCodigos(codigo);
    }

    public boolean insertoCodigoPrelacion(CodigoPrelacion codPrel) throws BDExcepcion {
       return this.pers.insertoCodigoPrelacion(codPrel);
    }

    public boolean actualizoCodigoPrelacion(CodigoPrelacion codPrel, Timestamp fechaNueva) throws BDExcepcion {
       return this.pers.actualizoCodigoPrelacion(codPrel, fechaNueva);
    }

    public ArrayList<CodigoPrelacion> cargoPrelacionFuncionario(Funcionario f) throws BDExcepcion {
       return this.pers.cargoPrelacionFuncionario(f);
    }

    public boolean esPosibleEliminar(CodigoPrelacion prel) throws BDExcepcion{
        boolean es=true;
        if(!this.pers.estaEnPersIngresos(prel)){
            if(!this.pers.estaActivaEnRetencionesFijas(prel)){
                if(!this.pers.estaEnPersHistLiquidaciones(prel)){
                    
                }
                else{
                    es=false;
                    throw new BDExcepcion("Este código tiene registros en la tabla histórica de liquidaciones y no se puede eliminar");
                }
            }
            else{
                es=false;
                throw new BDExcepcion("Este código está activo en retenciones fijas y no se puede eliminar");
            }
        }
        else{
            es=false;
            throw new BDExcepcion("Este código tiene registros en la tabla de ingresos manuales y no se puede eliminar");
        }
        return es;
    }

    public boolean eliminarPrelacion(CodigoPrelacion prel) throws BDExcepcion {
        return this.pers.eliminarPrelacion(prel);
    }

    public Codigo obtenerCodigo(Integer codigo) throws BDExcepcion {
        return this.pers.obtenerCodigo(codigo);
    }
    
}
