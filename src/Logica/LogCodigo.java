
package Logica;

import Dominio.Codigo;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Dominio.Marca;
import Dominio.Retencion;
import Persistencia.Conexion;
import Persistencia.PersistenciaCodigo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class LogCodigo {
    private PersistenciaCodigo pers;
    private Conexion conexion;
    public LogCodigo() {
        this.pers=new PersistenciaCodigo();
    }
    
    
    public ArrayList<Codigo> cargaComboCodigoLic() throws SQLException, ClassNotFoundException{
        return this.pers.cargaComboCodigoLic();
    }
    
       
    public Codigo obtenerCodigoTipo(Integer tipoLic) throws ClassNotFoundException, SQLException {
        return pers.obtenerCodigoTipo(tipoLic);
    }

    public ArrayList<Codigo> cargaComboCodigoFull() throws SQLException, ClassNotFoundException {
       return this.pers.cargaComboCodigoFull();
    }
    
     public boolean insertarCodigoMarca(Long id, Integer codFunc,Integer codigo,Double cantidad) throws SQLException{
         boolean es=this.pers.insertarCodigoMarca(id, codFunc, codigo, cantidad);
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

    public Codigo obtenerCodigoTipoLiq(Integer codigo) throws ClassNotFoundException, SQLException {
        return pers.obtenerCodigoTipoLiq(codigo);
    }

    public ArrayList<Codigo> cargaComboCodigoFullLiq() throws ClassNotFoundException, SQLException {
        return this.pers.carcaComboCodigoFullLiq();
    }
    public ArrayList<Codigo> cargaComboCodigoLike(String desc) throws ClassNotFoundException, SQLException {
        return this.pers.cargaComboCodigoLike(desc);
    }

    public boolean estaEnPersIngresos(Funcionario f, Codigo cod) throws ClassNotFoundException, SQLException {
       return this.pers.estaEnPersIngresos(f,cod);
    }

    public ArrayList<Ingreso> traePersIngresos(Integer codFunc) throws ClassNotFoundException, SQLException {
    return this.pers.traePersIngresos(codFunc);
    }

    public boolean borrarCodigoEnPersIngresos(Ingreso ingres) throws SQLException {
    return this.pers.borrarCodigoEnPersIngresos(ingres);
    }

    public int insertarEnPersIngresos(ArrayList<Ingreso> codigos) throws SQLException {
    return this.pers.insertarEnPersIngresos(codigos);
    }

    public int actualizarPersIngresos(Ingreso ing, Double d) throws SQLException, ClassNotFoundException {
        return this.pers.actualizaPersIngresos(ing,d);
    }
    public ArrayList<Ingreso> traeIngresosPorCod(Integer cod) throws ClassNotFoundException, SQLException{
        return this.pers.traeIngresosPorCod(cod);
    }
    public ArrayList<Codigo> cargaComboCodigoFijo() throws SQLException, ClassNotFoundException{
        return this.pers.cargaComboCodigoFijo();
    }
    public ArrayList<Ingreso> cargaMovimientosFijo(Integer cod) throws SQLException, ClassNotFoundException{
        return this.pers.cargaMovimientosFijo(cod);
    }

    public boolean estaEnCodigosFijos(Funcionario f, Codigo cod) throws ClassNotFoundException, SQLException {
        return this.pers.estaEnCodigosFijos(f,cod);
    }

    public boolean insertaEnCodigosFijos(Ingreso ing) throws ClassNotFoundException, SQLException {
        return this.pers.insertaEnCodigosFijos(ing);
    }
    public boolean actualizaEnCodigosFijos(Ingreso ing) throws ClassNotFoundException, SQLException {
        return this.pers.actualizaEnCodigosFijos(ing);
    }

    public boolean borrarCodigoEnPersCodFijo(Ingreso ingres) throws SQLException, SQLException {
        return this.pers.borrarCodigoEnPersCodFijo(ingres);
    }

    public ArrayList<Ingreso> cargaMovimientosFijoFunc(Integer codFunc) throws ClassNotFoundException, SQLException {
        return this.pers.cargaMovimientosFijoFunc(codFunc);
    }
     public ArrayList<Codigo> cargaComboRetencionesFijo() throws SQLException, ClassNotFoundException{
         return  this.pers.cargaComboRetencionesFijo();
    }
    public ArrayList<Retencion> listarRetenciones(int activa,Funcionario f, Codigo cod) throws ClassNotFoundException, SQLException{
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
            if(c.get(Calendar.DAY_OF_MONTH)>21){
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
            if(c.get(Calendar.DAY_OF_MONTH)>21){
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
    
}
