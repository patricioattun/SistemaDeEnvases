
package Persistencia;

import Dominio.Codigo;
import Dominio.CodigoPrelacion;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Dominio.IngresoMarca;
import Dominio.LineaArchivoBanco;
import Dominio.Marca;
import Dominio.Retencion;
import Dominio.TipoVale;
import Dominio.Vale;
import Presentacion.frmPrin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;

public class PersistenciaCodigo {
     private Conexion conexion;
     private PersistenciaFuncionario pers=new PersistenciaFuncionario();
     
             
    public ArrayList<Codigo> cargaComboCodigoLic() throws BDExcepcion{
         try {
             Connection cnn=null;
             ArrayList<Codigo> lista=new ArrayList<>();
             Codigo c=null;
             cnn=conexion.Cadena();
             String consulta="Select * from PERS_CODIGOS WHERE TIPOLIC=? order by codigo";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, 1);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 c=new Codigo();
                 c.setCod(rs.getInt("CODIGO"));
                 c.setDescripcion(rs.getString("DESCRIPCION").trim());
                 c.setTipo(rs.getInt("TIPOLIC"));
                 c.setValor(rs.getInt("VALOR"));
                 lista.add(c);
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return lista;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }
    
    public ArrayList<Codigo> cargaComboCodigoFull() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        ArrayList<Codigo> lista=new ArrayList<>();
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_CODIGOS WHERE TIPOLIC=? or TIPOLIC=? order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, 1);
        ps.setInt(2, 2);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            c.setTipo(rs.getInt("TIPOLIC"));
            c.setValor(rs.getInt("VALOR"));
            lista.add(c);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return lista;
    }
              
    public Codigo obtenerCodigoTipo(Integer tipoLic) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="Select c.codigo,c.descripcion,c.tipolic,c.valor,t.tipo_unidad from PERS_CODIGOS c,pers_cod_tipo_tope t WHERE c.CODIGO=? and c.codigo=t.codigo AND (c.TIPOLIC=? or c.TIPOLIC=?)";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, tipoLic);
        ps.setInt(2, 1);
        ps.setInt(3, 2);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            c.setTipo(rs.getInt("TIPOLIC"));
            c.setValor(rs.getInt("VALOR"));
            c.setTipoUnidad(rs.getInt("TIPO_UNIDAD"));
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return c; 
    }
    
    public ArrayList<Codigo> obtenerListadoCodigo() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Codigo c=null;    
        ArrayList<Codigo> listado=new ArrayList<>();
        cnn=conexion.Cadena();
        String consulta="Select codigo,tipo_unidad from pers_cod_tipo_tope";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setTipoUnidad(rs.getInt("TIPO_UNIDAD"));
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return listado; 
    }
    
       public Codigo obtenerCodigoTipoTope(Integer cod) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="Select t.codigo,t.tipo_unidad,m.descripcion from pers_cod_tipo_tope t, pers_Codigos m where t.codigo=? and m.codigo=t.codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setLong(1, cod );
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setTipoUnidad(rs.getInt("TIPO_UNIDAD"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return c; 
    }
    
    public boolean insertarCodigoMarca(Long id, Integer codFunc,Integer codigo,Double cantidad,Date fecha) throws SQLException{
        Connection cnn=null;
        Boolean alta=false;
        Integer i=-1;
        try{
             cnn=conexion.Cadena();
             //cnn.setAutoCommit(false);
             String insert="insert into PERS_INGRESOS_MARCAS(ID,CODFUNC,CODIGO,CANTIDAD,FECHA_MARCA)"+"values(?,?,?,?,?)";
             PreparedStatement ps=cnn.prepareStatement(insert);
             ps.setLong(1, id );
             ps.setInt(2, codFunc);
             ps.setInt(3, codigo);
             ps.setDouble(4,cantidad);
             ps.setString(5, this.convertirFecha(fecha));
             i=ps.executeUpdate();
             if(i==1){ 
             alta=true;
             
             }
         }
         catch(ClassNotFoundException e1){
            String capturo="";
         }
         finally{
                if(cnn!=null){
                        cnn.close();
         }
     }
         return alta;
    }

    public ArrayList<Integer> obtenerCodigos(Integer funCod, Long id) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Codigo c=null;
        ArrayList<Integer> codigos=new ArrayList<>();
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_INGRESOS_MARCAS WHERE ID=? AND CODFUNC=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setLong(1, id);
        ps.setInt(2, funCod);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
              codigos.add(rs.getInt("CODIGO"));
            
           }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return codigos; 
    }
    
     
    
    public boolean bloqueoLiq() throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        boolean permite=false;
        cnn=conexion.Cadena();
        String consulta="Select VALOR from PERS_PARAMETROS WHERE NOMBRE=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, "BLOQUEO_SECRETARIA");
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            String ret=rs.getString("VALOR").trim();
                if(ret.equals("1")){
                    permite=true;
                }
            
           }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return permite; 
    }
    
    public ArrayList<Ingreso> traeIngresos() throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        Date fecha=new Date();
        String str=this.fijaDia(fecha);
        cnn=conexion.Cadena();
        
        ArrayList<Ingreso> ingresos=new ArrayList<>();
        String consulta="Select FECHA,COD_FUNC,COD_MOV,CANTIDAD from PERS_INGRESOS WHERE FECHA=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, str);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
               Ingreso ing=new Ingreso();
               ing.setFecha(rs.getDate("FECHA"));
               ing.setCodFunc(rs.getInt("COD_FUNC"));
               ing.setCodMov(this.obtenerCodigoTipoLi(rs.getInt("COD_MOV"),cnn));
               ing.setCantidad(rs.getDouble("CANTIDAD"));
               ingresos.add(ing);
           }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
        return ingresos;
    }
    
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
    
    private Timestamp fijaHoraHasta(Date date){
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(date.getTime());
    c.set(Calendar.HOUR_OF_DAY, 23);
    c.set(Calendar.MINUTE, 59);
    c.set(Calendar.SECOND, 59);
    c.set(Calendar.MILLISECOND, 0);
    Timestamp ts = new java.sql.Timestamp(date.getTime());
    ts.setTime(c.getTimeInMillis());
    return ts;
    }

    public boolean borrarCodigoMarca(Long id, Integer funCod, Integer codigo) throws SQLException {
      Connection cnn=null;
        Boolean alta=false;
        Integer i=-1;
        try{
             cnn=conexion.Cadena();
             //cnn.setAutoCommit(false);
             String borrar="DELETE FROM PERS_INGRESOS_MARCAS WHERE ID=? and CODFUNC=? and CODIGO=?";
             PreparedStatement ps=cnn.prepareStatement(borrar);
             ps.setLong(1, id);
             ps.setInt(2, funCod);
             ps.setInt(3, codigo);
             i=ps.executeUpdate();
             if(i==1){ 
             alta=true;
             
             }
         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
                if(cnn!=null){
                        cnn.close();
         }
     }
         return alta;
    }

    public ArrayList<Codigo> traerCodigosPeriodo(Date desde, Date hasta, Integer cod) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Codigo c=null;
        ArrayList<Codigo> codigos=new ArrayList<>();
        cnn=conexion.Cadena();
        String consulta="SELECT distinct(Codigo),sum(cantidad) as cant FROM PERS_INGRESOS_MARCAS where codfunc=? and codfunc in(select codFunc from pers_Marcas where Marca>=? and marca<=?) and id in (select id from pers_Marcas where Marca>=? and marca<=? and codfunc=?) group by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setInt(1, cod);
        ps.setTimestamp(2, des);
        ps.setTimestamp(3, has);
        ps.setTimestamp(4, des);
        ps.setTimestamp(5, has);
        ps.setInt(6, cod);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();    
            c.setCod(rs.getInt("CODIGO"));
            c.setValor(rs.getInt("CANT"));
            codigos.add(c);
            
           }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return codigos; 
    }

    public Codigo obtenerCodigoTipoLi(Integer codigo, Connection cnn) throws ClassNotFoundException, SQLException {
        Codigo c=null;
        if(cnn==null){
        cnn=conexion.Cadena();
        }
        String consulta="Select codigo,descripcion,tipolic,tipovalor from PERS_CODIGOS WHERE CODIGO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            c.setTipo(rs.getInt("TIPOLIC"));
            c.setTipoValor(rs.getInt("TIPOVALOR"));
            //c.setTipoUnidad(rs.getInt("TIPOUNIDAD"));
            }        
            
                ps.close();
                rs.close();
        
         return c;   
    }
    
    public Codigo obtenerCodigoTipoLiq(Integer codigo) throws BDExcepcion {
        Codigo c=null;
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             //String consulta="Select codigo,descripcion,tipolic,tipovalor from PERS_CODIGOS WHERE CODIGO=? and TIPOLIC is not null and FIJO=0 and ingresomanual=1";
             String consulta="Select codigo,descripcion,tipolic,tipovalor from PERS_CODIGOS WHERE CODIGO=? and FIJO=0 and ingresomanual=1";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, codigo);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 c=new Codigo();
                 c.setCod(rs.getInt("CODIGO"));
                 c.setDescripcion(rs.getString("DESCRIPCION").trim());
                 c.setTipo(rs.getInt("TIPOLIC"));
                 c.setTipoValor(rs.getInt("TIPOVALOR"));
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }   
            
         } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
          return c;
    }
    
    public Codigo obtenerCodigoTipoLiq1(Integer codigo) throws BDExcepcion {
        Codigo c=null;
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             //String consulta="Select codigo,descripcion,tipolic,tipovalor from PERS_CODIGOS WHERE CODIGO=? and TIPOLIC is not null and FIJO=0 and ingresomanual=1";
             String consulta="Select codigo,descripcion,tipolic,tipovalor from PERS_CODIGOS WHERE CODIGO=? and FIJO=0 and ingresomanual=1 and retencion_fija=0";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, codigo);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 c=new Codigo();
                 c.setCod(rs.getInt("CODIGO"));
                 c.setDescripcion(rs.getString("DESCRIPCION").trim());
                 c.setTipo(rs.getInt("TIPOLIC"));
                 c.setTipoValor(rs.getInt("TIPOVALOR"));
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }   
            
         } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
          return c;
    }

    public ArrayList<Codigo> carcaComboCodigoFullLiq() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        ArrayList<Codigo> lista=new ArrayList<>();
        Codigo c=null;    
        cnn=conexion.Cadena();
        //String consulta="Select * from PERS_CODIGOS WHERE TIPOLIC is not null and FIJO=0 AND INGRESOMANUAL=1 order by codigo";
        String consulta="Select * from PERS_CODIGOS WHERE FIJO=0 AND INGRESOMANUAL=1 order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
       
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            lista.add(c);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return lista;
    }
    
    public ArrayList<Codigo> cargaComboCodigoLike(String desc) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        ArrayList<Codigo> lista=new ArrayList<>();
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_CODIGOS WHERE TIPOLIC is null and FIJO=0 and (descripcion like ? or descripcion like ?) order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
         ps.setString(1, "%"+desc.toUpperCase()+"%");
         ps.setString(2, "%"+desc.toLowerCase()+"%");
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            lista.add(c);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return lista;
    }

    public boolean estaEnPersIngresos(Funcionario f, Codigo cod)throws BDExcepcion{
         try {
             Connection cnn=null;
             ArrayList<Codigo> lista=new ArrayList<>();
             boolean esta=false;
             cnn=conexion.Cadena();
             String consulta="Select * from PERS_INGRESOS WHERE COD_FUNC=? and COD_MOV=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, f.getCodFunc());
             ps.setInt(2, cod.getCod());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 esta=true;
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return esta;
         } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }

    public ArrayList<Ingreso> traePersIngresos(Integer codFunc)throws BDExcepcion {
         try {
             Connection cnn=null;
             Date fecha=new Date();
             String str=this.fijaDia(fecha);
             cnn=conexion.Cadena();
             
             ArrayList<Ingreso> ingresos=new ArrayList<>();
             String consulta="Select FECHA,COD_FUNC,COD_MOV,CANTIDAD,IMPORTE from PERS_INGRESOS WHERE COD_FUNC=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, codFunc);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 Ingreso ing=new Ingreso();
                 ing.setFecha(rs.getDate("FECHA"));
                 ing.setCodFunc(rs.getInt("COD_FUNC"));
                 ing.setCodMov(this.obtenerCodigoTipoLi(rs.getInt("COD_MOV"),cnn));
                 ing.setCantidad(rs.getDouble("CANTIDAD"));
                 ing.setImporte(rs.getDouble("IMPORTE"));
                 ingresos.add(ing);
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return ingresos;
         } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }

    public boolean borrarCodigoEnPersIngresos(Ingreso ingres)throws BDExcepcion{
       Boolean alta=false;
        try {
             Connection cnn=null;
             Integer i=-1;
             cnn=conexion.Cadena();
             
             String borrar="DELETE FROM PERS_INGRESOS WHERE COD_FUNC=? and COD_MOV=?";
             PreparedStatement ps=cnn.prepareStatement(borrar);
             int codFunc=ingres.getCodFunc();
             int mov=ingres.getCodMov().getCod();
             ps.setInt(1,codFunc);
             ps.setInt(2, mov);
             i=ps.executeUpdate();
             if(i==1){
                 alta=true;
                 
             }
             
             if(cnn!=null){
                 cnn.close();
             }
             
             
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
         return alta;
    }

    public int insertarEnPersIngresos(ArrayList<Ingreso> codigos,String fechaLiq) throws SQLException {
        Connection cnn=null;
        Integer ret=0;
        String insert="";
        PreparedStatement ps=null;
        try{
             cnn=conexion.Cadena();
             cnn.setAutoCommit(false);
             for(Ingreso i:codigos){
                
                     insert="insert into PERS_INGRESOS(FECHA,COD_FUNC,COD_MOV,CANTIDAD,IMPORTE)"+"values(?,?,?,?,?)";
                     ps=cnn.prepareStatement(insert);
                     ps.setString(1, fechaLiq);
                     ps.setInt(2, i.getCodFunc());
                     ps.setInt(3, i.getCodMov().getCod());
                     ps.setDouble(4,i.getCantidad());
                     ps.setDouble(5,i.getImporte());
                     ret+=ps.executeUpdate();
                     
//                     if(i.getCodMov().getCod()==401){
//                        this.actualizaVales(i,cnn,fechaLiq);
//                     }
             }
             
             
            cnn.commit();
         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
                if(cnn!=null){
                   cnn.close();
            }
     }
         return ret;
    }
    
    
    private String convertirFecha(Date fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
        }
        return str;
     }
    
  private String convertirFechaYHora(Date fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"); 
         str= sdf.format(fecha);
        }
        return str;
     }

    public int actualizaPersIngresos(Ingreso ing, Double d) throws BDExcepcion {
         try {
             Connection cnn=null;
             int upd=0;
             String insert="";
             PreparedStatement psFunc=null;
             cnn=conexion.Cadena();
             if(ing.getCodMov().getTipoValor().equals(0)){
                 insert="update PERS_INGRESOS set CANTIDAD=? WHERE COD_FUNC=? AND COD_MOV=?";
                 psFunc=cnn.prepareStatement(insert);
                 psFunc.setDouble(1, d);
                 psFunc.setInt(2,ing.getCodFunc());
                 psFunc.setInt(3, ing.getCodMov().getCod());
             }
             else{
                 insert="update PERS_INGRESOS set IMPORTE=? WHERE COD_FUNC=? AND COD_MOV=?";
                 psFunc=cnn.prepareStatement(insert);
                 psFunc.setDouble(1, d);
                 psFunc.setInt(2,ing.getCodFunc());
                 psFunc.setInt(3, ing.getCodMov().getCod());
             }
             
             upd=psFunc.executeUpdate();
             
             if(cnn!=null){
                 psFunc.close();
                 cnn.close();
             }
             return upd;    
         } catch (ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
         }
    }
    
    public ArrayList<Ingreso> traeIngresosPorCod(Integer cod)throws BDExcepcion{
         try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             ArrayList<Ingreso> ingresos=new ArrayList<>();
             String consulta="Select FECHA,COD_FUNC,COD_MOV,CANTIDAD,IMPORTE from PERS_INGRESOS WHERE COD_MOV=? order by cod_func";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, cod);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 Ingreso ing=new Ingreso();
                 ing.setFecha(rs.getDate("FECHA"));
                 ing.setFunc(this.pers.funcionarioBasicoMasivo(rs.getInt("COD_FUNC"),cnn));
                 ing.setCodMov(this.obtenerCodigoTipoLi(rs.getInt("COD_MOV"),cnn));
                 ing.setCantidad(rs.getDouble("CANTIDAD"));
                 ing.setImporte(rs.getDouble("IMPORTE"));
                 ingresos.add(ing);
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return ingresos;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }
    
     public boolean hayCodigoEnIngreso(Integer cod) throws BDExcepcion{
         boolean esta=false;
         try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             
             String consulta="Select distinct(COD_MOV) from PERS_INGRESOS WHERE COD_MOV=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, cod);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 esta=true;
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
            
         } catch (ClassNotFoundException ex) {
              throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
         
          return esta;
    }
    
    
    //CODIGOS FIJOS
    public ArrayList<Codigo> cargaComboCodigoFijo() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        ArrayList<Codigo> lista=new ArrayList<>();
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_CODIGOS WHERE FIJO=1 order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            c.setTipoValor(rs.getInt("TIPOVALOR"));
            lista.add(c);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return lista;
    }
    
    public ArrayList<Ingreso> cargaMovimientosFijo(Integer cod) throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        ArrayList<Ingreso> lista=new ArrayList<>();
        Ingreso i=null;
        Codigo c=null;
        Funcionario f=null;
        cnn=conexion.Cadena();
        String consulta="SELECT c.codfunc,c.codmov,c.valor, c.fechacargado,f.nombre1,f.nombre2,f.apellido1,f.apellido2 FROM PERS_CODIGOS_FIJOS c,pers_funcionarios f where c.codmov=? and c.codfunc=f.codigo and f.fecha_egreso is null order by c.codfunc";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            i=new Ingreso();
            c=new Codigo();
            f=new Funcionario();
            f.setNombre1(rs.getString("NOMBRE1"));
            f.setNombre2(rs.getString("NOMBRE2"));
            f.setApellido1(rs.getString("APELLIDO1"));
            f.setApellido2(rs.getString("APELLIDO2"));
            f.setCodFunc(rs.getInt("CODFUNC"));
            i.setFunc(f);
            c.setCod(rs.getInt("CODMOV"));
            i.setCodMov(c);
            i.setCantidad(rs.getDouble("VALOR"));
            i.setFecha(rs.getDate("FECHACARGADO"));
            lista.add(i);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return lista;
    }

    public boolean estaEnCodigosFijos(Funcionario f, Codigo cod) throws BDExcepcion {
         try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             int contador=0;
             boolean esta=false;
             String consulta="Select * from PERS_CODIGOS_FIJOS WHERE CODFUNC=? AND CODMOV=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, f.getCodFunc());
             ps.setInt(2, cod.getCod());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 contador++;
             }
             if(contador>0){
                 esta=true;
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return esta;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }
    
    public boolean insertaEnCodigosFijos(Ingreso ing) throws BDExcepcion {
         try {
             Connection cnn=null;
             Integer suma=0;
             boolean ret=false;
             String insert="";
             PreparedStatement ps=null;
             cnn=conexion.Cadena();
             insert="insert into PERS_CODIGOS_FIJOS(CODFUNC,CODMOV,VALOR,FECHACARGADO)"+"values(?,?,?,?)";
             ps=cnn.prepareStatement(insert);
             ps.setInt(1, ing.getFunc().getCodFunc());
             ps.setInt(2, ing.getCodMov().getCod());
             ps.setDouble(3, ing.getCantidad());
             ps.setString(4,this.convertirFecha(ing.getFecha()));
             suma+=ps.executeUpdate();
             if(suma>0){
                 ret=true;
             }
             if(cnn!=null){
                 cnn.close();
             }
             return ret;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }
    
    public boolean actualizaEnCodigosFijos(Ingreso ing) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Integer suma=0;
        boolean ret=false;
        String insert="";
        PreparedStatement ps=null;
        cnn=conexion.Cadena();
            insert="update PERS_CODIGOS_FIJOS set VALOR=?, FECHACARGADO=? WHERE CODFUNC=? AND CODMOV=?";
            ps=cnn.prepareStatement(insert);
            ps.setDouble(1, ing.getCantidad());
            ps.setString(2,this.convertirFecha(ing.getFecha())); 
            ps.setInt(3, ing.getCodFunc());
            ps.setInt(4, ing.getCodMov().getCod());
            suma+=ps.executeUpdate();
         if(suma>0){
             ret=true;
         }                           
       if(cnn!=null){
             cnn.close();
       }
           return ret;
    }

    public boolean borrarCodigoEnPersCodFijo(Ingreso ingres)  throws BDExcepcion {
         try {
             Connection cnn=null;
             Boolean alta=false;
             Integer i=-1;
             
             cnn=conexion.Cadena();
             
             String borrar="DELETE FROM PERS_CODIGOS_FIJOS WHERE CODFUNC=? and CODMOV=?";
             PreparedStatement ps=cnn.prepareStatement(borrar);
             int codFunc=ingres.getCodFunc();
             int mov=ingres.getCodMov().getCod();
             ps.setInt(1,codFunc);
             ps.setInt(2, mov);
             i=ps.executeUpdate();
             if(i==1){
                 alta=true;
                 
             }
             
             
             if(cnn!=null){
                 cnn.close();
                 
             }
             return alta;
         } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }

    public ArrayList<Ingreso> cargaMovimientosFijoFunc(Integer codFunc) throws BDExcepcion{
         try {
             Connection cnn=null;
             ArrayList<Ingreso> lista=new ArrayList<>();
             Ingreso i=null;
             Codigo c=null;
             cnn=conexion.Cadena();
             String consulta="SELECT c.codmov,c.fechacargado,c.valor,p.descripcion FROM PERS_CODIGOS_FIJOS c, PERS_CODIGOS p where c.codfunc=? and p.codigo=c.codmov order by codmov";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, codFunc);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 i=new Ingreso();
                 c=new Codigo();
                 c.setCod(rs.getInt("CODMOV"));
                 c.setDescripcion(rs.getString("DESCRIPCION").trim());
                 i.setCodMov(c);
                 i.setCantidad(rs.getDouble("VALOR"));
                 i.setFecha(rs.getDate("FECHACARGADO"));
                 lista.add(i);
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return lista;
         } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }
    
    
    //RETENCIONES FIJAS
    public ArrayList<Codigo> cargaComboRetencionesFijo() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        ArrayList<Codigo> lista=new ArrayList<>();
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="select codigo,descripcion from pers_codigos where retencion_fija=1 order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            lista.add(c);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return lista;
    }
    
    public ArrayList<Codigo> cargaComboGrupoDosYTres() throws BDExcepcion{
         try {
             Connection cnn=null;
             ArrayList<Codigo> lista=new ArrayList<>();
             Codigo c=null;
             cnn=conexion.Cadena();
             String consulta="select codigo,descripcion from pers_codigos where grupo=2 or grupo=3 order by grupo,codigo";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 c=new Codigo();
                 c.setCod(rs.getInt("CODIGO"));
                 c.setDescripcion(rs.getString("DESCRIPCION").trim());
                 lista.add(c);
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return lista;
         } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }
    
    public ArrayList<Retencion> listarRetenciones(int activa,Funcionario f, Codigo cod) throws BDExcepcion{
         try {
             Connection cnn=null;
             ArrayList<Retencion> lista=new ArrayList<>();
             Retencion r=null;
             cnn=conexion.Cadena();
             PreparedStatement ps=null;
             ResultSet rs=null;
             
             if(f==null && cod==null){
                 //activa en 2 es todo, en este caso trae 1 o 0(activa o inactiva)
                 switch (activa) {
                     case 0:
                     {
                         String consulta="select * from pers_retenciones_fijas where activa=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, activa);
                         break;
                     }
                     case 1:
                     {
                         String consulta="select * from pers_retenciones_fijas where activa=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, activa);
                         break;
                     }
                     case 2:
                     {
                         String consulta="select * from pers_retenciones_fijas order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         break;
                     }
                     default:
                         break;
                 }
             }
             else if(f!=null && cod==null){
                 //activa en 2 es todo, en este caso trae 1 o 0(activa o inactiva)
                 switch (activa) {
                     case 0:
                     {
                         String consulta="select * from pers_retenciones_fijas where activa=? and cod_func=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, activa);
                         ps.setInt(2, f.getCodFunc());
                         break;
                     }
                     case 1:
                     {
                         String consulta="select * from pers_retenciones_fijas where activa=? and cod_func=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, activa);
                         ps.setInt(2, f.getCodFunc());
                         break;
                     }
                     case 2:
                     {
                         String consulta="select * from pers_retenciones_fijas where cod_func=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, f.getCodFunc());
                         break;
                     }
                     default:
                         break;
                 }
             }
             else if(f!=null && cod!=null){
                 switch (activa) {
                     case 0:
                     {
                         String consulta="select * from pers_retenciones_fijas where activa=? and cod_func=? and cod_mov=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, activa);
                         ps.setInt(2, f.getCodFunc());
                         ps.setInt(3, cod.getCod());
                         break;
                     }
                     case 1:
                     {
                         String consulta="select * from pers_retenciones_fijas where activa=? and cod_func=? and cod_mov=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, activa);
                         ps.setInt(2, f.getCodFunc());
                         ps.setInt(3, cod.getCod());
                         break;
                     }
                     case 2:
                     {
                         String consulta="select * from pers_retenciones_fijas where cod_func=? and cod_mov=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, f.getCodFunc());
                         ps.setInt(2, cod.getCod());
                         break;
                     }
                     default:
                         break;
                 }
             }
             else if(f==null && cod!=null){
                 switch (activa) {
                     case 0:
                     {
                         String consulta="select * from pers_retenciones_fijas where activa=? and cod_mov=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, activa);
                         ps.setInt(2, cod.getCod());
                         break;
                     }
                     case 1:
                     {
                         String consulta="select * from pers_retenciones_fijas where activa=? and cod_mov=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, activa);
                         ps.setInt(2, cod.getCod());
                         break;
                     }
                     case 2:
                     {
                         String consulta="select * from pers_retenciones_fijas where cod_mov=? order by cod_func";
                         ps=cnn.prepareStatement(consulta);
                         ps.setInt(1, cod.getCod()); 
                         break;
                     }
                     default:
                         break;
                 }
             }
             rs=ps.executeQuery();
             
             while(rs.next()){
                 r=new Retencion();
                 r.setCod(this.obtenerCodigoTipoLi(rs.getInt("cod_mov"),cnn));
                 r.setFunc(this.pers.funcionarioBasico(rs.getInt("cod_func")));
                 r.setImporte(rs.getDouble("importe"));
                 r.setPorcentaje(rs.getDouble("porcentaje"));
                 r.setOtros(rs.getInt("otros"));
                 r.setSueldo(rs.getInt("sueldo"));
                 r.setTipo(rs.getInt("tipo"));
                 r.setActiva(rs.getInt("activa"));
                 lista.add(r);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return lista;
         } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
        
    }

    public boolean ingresarRetencionFija(Funcionario f, Codigo c, int sueldo, int agui, int tipo,double valor) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Integer suma=0;
        boolean ret=false;
        String insert="";
        PreparedStatement ps=null;
        cnn=conexion.Cadena();
            insert="insert into PERS_RETENCIONES_FIJAS(COD_FUNC,COD_MOV,ORDEN,SOBRE,TIPO,IMPORTE,PORCENTAJE,ACTIVA,SUELDO,OTROS)"+"values(?,?,?,?,?,?,?,?,?,?)";
            ps=cnn.prepareStatement(insert);
            ps.setInt(1, f.getCodFunc());
            ps.setInt(2, c.getCod());
            ps.setInt(3, 1);
            ps.setInt(4, 0); 
            ps.setInt(5, tipo);
            if(tipo==0){
                ps.setDouble(6, valor ); 
                ps.setDouble(7, 0);
            }
            else{
                ps.setDouble(6, 0); 
                ps.setDouble(7, valor);
            }
            ps.setInt(8, 1);
            ps.setInt(9, sueldo);
            ps.setInt(10, agui);
            suma+=ps.executeUpdate();
         if(suma>0){
             ret=true;
         }                           
       if(cnn!=null){
             cnn.close();
       }
           return ret;
    }
    
     public boolean estaEnRetFijas(Funcionario f, Codigo cod) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        int contador=0;
        boolean esta=false;
        String consulta="Select * from PERS_RETENCIONES_FIJAS WHERE COD_FUNC=? AND COD_MOV=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, f.getCodFunc());
        ps.setInt(2, cod.getCod());
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            contador++;
            }  
        if(contador>0){
            esta=true;
        }
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return esta;
    }

    public boolean eliminarRetencionFija(Retencion ret) throws SQLException, ClassNotFoundException {
      Connection cnn=null;
        Boolean alta=false;
        Integer i=0;
             cnn=conexion.Cadena();
            
             String borrar="UPDATE PERS_RETENCIONES_FIJAS SET ACTIVA=? WHERE COD_FUNC=? and COD_MOV=?";
             PreparedStatement ps=cnn.prepareStatement(borrar);
             if(ret.getActiva()==1){
             ps.setInt(1, 0);
             }
             else{
             ps.setInt(1, 1);    
             }
             ps.setInt(2, ret.getFunc().getCodFunc());
             ps.setInt(3, ret.getCod().getCod());
             i=ps.executeUpdate();
                if(i==1){ 
                alta=true;

                }
     
      
             if(cnn!=null){
                 cnn.close();
               }
       return alta;
    }

    public boolean actualizaRetencion(Retencion r) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        int upd=0;
        boolean retorno=false;
        String insert="";
        PreparedStatement psFunc=null;
           cnn=conexion.Cadena();
             insert="update PERS_RETENCIONES_FIJAS set TIPO=?, IMPORTE=?, PORCENTAJE=?, SUELDO=?, OTROS=?  WHERE COD_FUNC=? AND COD_MOV=?";
             psFunc=cnn.prepareStatement(insert);
             psFunc.setInt(1, r.getTipo());
             psFunc.setDouble(2,r.getImporte());
             psFunc.setDouble(3,r.getPorcentaje());
             psFunc.setInt(4, r.getSueldo());
             psFunc.setInt(5, r.getOtros());
             psFunc.setInt(6, r.getFunc().getCodFunc());
             psFunc.setInt(7, r.getCod().getCod());
             upd=psFunc.executeUpdate();
             if(upd==1){
                 retorno=true;
             }
             
             if(cnn!=null){
                psFunc.close();
                cnn.close();
            }
        return retorno;    
    }

    public Integer totalPeriodo(Timestamp desde, Timestamp hasta, Marca m,Integer cod, Connection cnn) {
          Integer retorno = 0;
        try {
             ArrayList<Marca> marcas=new ArrayList<>();
             PreparedStatement ps=null;
            
             String consulta="SELECT sum(cantidad) as cant FROM PERS_INGRESOS_MARCAS where codfunc=? and codigo=?  and codfunc in(select codFunc from pers_Marcas where Marca>=? and marca<=?) and id in (select id from pers_Marcas where Marca>=? and marca<=? and codfunc=?) group by codigo";
             
             ps=cnn.prepareStatement(consulta);
             java.sql.Timestamp des = this.fijaHoraDesde(desde);
             java.sql.Timestamp has = this.fijaHoraHasta(hasta);
             ps.setInt(1, m.getFunCod());
             ps.setInt(2, cod);
             ps.setTimestamp(3, des);
             ps.setTimestamp(4, has);
             ps.setTimestamp(5, des);
             ps.setTimestamp(6, has);
             ps.setInt(7, m.getFunCod());
             
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 retorno = rs.getInt("cant");  
             }
             ps.close();
             rs.close();
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return retorno;
    }

    public Integer obtengoTope(Integer cod, Connection cnn) {
        Integer retorno = 0;
        try {
                          
             PreparedStatement ps=null;
             String consulta="SELECT * FROM PERS_Parametros where nombre=?";
             ps=cnn.prepareStatement(consulta);
             ps.setString(1, "TOPE_CODIGO_"+cod.toString());
             
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 retorno = Integer.valueOf(rs.getString("VALOR").trim());  
             }
             ps.close();
             rs.close();
            
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return retorno;
    }

    public ArrayList<Ingreso> cargoVales(Date fechaIni, Date fechaFin,Codigo cod,TipoVale tipo)throws BDExcepcion {
        ArrayList<Ingreso> vales = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             cod.setTipo(1);
             PreparedStatement ps=null;
             if(tipo==null){
             String consulta="Select * from PERS_VALES WHERE FECHA>=? AND FECHA<=? AND TIPO=1 AND FECHALIQ IS NULL order by codfun";
             ps=cnn.prepareStatement(consulta);
             String ini=this.convertirFecha(fechaIni);
             String fin=this.convertirFecha(fechaFin);
             ps.setString(1, ini);
             ps.setString(2, fin);
             }
             else{
             String consulta="select codfun,sum(importevale) importevale,nombres,cuenta from pers_vales WHERE FECHA>=? AND FECHA<=? AND TIPO=4 AND FECHALIQ IS NULL group by codfun,nombres,cuenta order by codfun";
             ps=cnn.prepareStatement(consulta);
             String ini=this.convertirFecha(fechaIni);
             String fin=this.convertirFecha(fechaFin);
             ps.setString(1, ini);
             ps.setString(2, fin);
             }
             
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 Ingreso ing = new Ingreso();
                 ing.setCodFunc(rs.getInt("CODFUN"));
                 ing.setFunc(this.pers.funcionarioBasicoMasivo(rs.getInt("CODFUN"),cnn)); 
                 ing.setImporte(rs.getDouble("IMPORTEVALE")); 
                 ing.setCodMov(cod);
                 //ing.setFecha(rs.getDate("FECHA"));
                 ing.setCantidad(1.0);
                
              
                 vales.add(ing);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             
         } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
         return vales;
    }

    private void actualizaVales(Ingreso i, Connection cnn,String fecha) {
         try {
             String insert="";
             PreparedStatement psFunc=null;
             int cont=0;
             insert="update PERS_VALES set FECHALIQ=?,USUARIO=? WHERE CODFUN=? AND FECHA=?";
             psFunc=cnn.prepareStatement(insert);
             psFunc.setString(1,fecha);
             psFunc.setString(2,frmPrin.instancia().getUsuario().getNombre());
             psFunc.setInt(3,i.getCodFunc());
             psFunc.setString(4,this.convertirFecha(i.getFecha()));
                        
             cont=psFunc.executeUpdate();
             
             psFunc.close();
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
               
    }

    public ArrayList<TipoVale> cargoTipoVales() {
      ArrayList<TipoVale> tipos = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             String consulta="Select * from PERS_VALES_TIPO";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 TipoVale tipo=new TipoVale();
                    tipo.setCodigo(rs.getInt("CODIGO"));
                    tipo.setNombre(rs.getString("NOMBRE"));
                 tipos.add(tipo);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return tipos;
    }

    public ArrayList<Vale> cargoValesFecha(String fecha,TipoVale tipo) {
        ArrayList<Vale> vales = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             String consulta="Select * from PERS_VALES WHERE FECHA=? and TIPO=? order by codfun";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setString(1, fecha);
             ps.setInt(2, tipo.getCodigo());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 Vale v = new Vale();
                 v.setCodFunc(rs.getInt("CODFUN"));
                 v.setF(this.pers.funcionarioBasicoVale(rs.getInt("CODFUN"),cnn));
                 v.setCuenta(rs.getDouble("CUENTA"));
                 v.setFecha(rs.getDate("FECHA")); 
                 v.setFechaLiq(rs.getDate("FECHALIQ"));
                 v.setImporteVale(rs.getDouble("IMPORTEVALE"));
                 v.setNombre(rs.getString("NOMBRES")); 
                 v.setTipo(this.buscaTipoVale(rs.getInt("TIPO"),cnn));
                 v.setUsuario(rs.getString("USUARIO"));
                 v.setEnPers(1);
                 vales.add(v);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return vales;
    }

        public ArrayList<Object> cargoValesFechaObject(String fecha,TipoVale tipo) {
        ArrayList<Object> vales = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             String consulta="Select * from PERS_VALES WHERE FECHA=? AND CUENTA<>0 and TIPO=? order by codfun";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setString(1, fecha);
             ps.setInt(2, tipo.getCodigo());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 Vale v = new Vale();
                 v.setCodFunc(rs.getInt("CODFUN"));
                 v.setF(this.pers.funcionarioBasicoVale(rs.getInt("CODFUN"),cnn));
                 v.setCuenta(rs.getDouble("CUENTA"));
                 v.setFecha(rs.getDate("FECHA")); 
                 v.setFechaLiq(rs.getDate("FECHALIQ"));
                 v.setImporteVale(rs.getDouble("IMPORTEVALE"));
                 v.setNombre(rs.getString("NOMBRES")); 
                 v.setTipo(this.buscaTipoVale(rs.getInt("TIPO"),cnn));
                 v.setUsuario(rs.getString("USUARIO"));
                 vales.add(v);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return vales;
    }
    
    private TipoVale buscaTipoVale(int tipo, Connection cnn) {
         TipoVale tip=null; 
        try {
             String consulta="Select * from PERS_VALES_TIPO where CODIGO=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, tipo);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 tip=new TipoVale();
                 tip.setCodigo(rs.getInt("CODIGO"));
                 tip.setNombre(rs.getString("NOMBRE"));
                 
             }
            
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
        return tip;
    }

    public ArrayList<Integer> ingresarVales(ArrayList<Vale> vales, TipoVale tipo, String fecha) {
      ArrayList<Integer> listado=new ArrayList<>();
        try {
             Integer insert=0;
             Integer update=0;
             Integer liq=0;
          
             Connection cnn=null;
             cnn=conexion.Cadena();
             String consulta="";
             PreparedStatement ps = null;
             
             
             for(Vale v: vales){
                 if(v.getFechaLiq()==null){
                     if(v.getEnPers()==1){
                         //actualiza lo que esta en pers vales y que aun no fue liquidado
                         consulta="UPDATE PERS_VALES SET IMPORTEVALE=? WHERE CODFUN=? AND FECHA=? AND TIPO=? AND USUARIO=?";
                         ps=cnn.prepareStatement(consulta);
                         ps.setDouble(1, v.getImporteVale());
                         ps.setInt(2, v.getCodFunc());
                         ps.setString(3,this.convertirFecha(v.getFecha()));
                         ps.setInt(4, v.getTipo().getCodigo());
                         ps.setString(5, frmPrin.instancia().getUsuario().getNombre());
                         update+=ps.executeUpdate();
                         
                     }
                     else{
                         //insertar lo que viene de excel y los ingresos a mano
                         consulta="INSERT INTO PERS_VALES (FECHA,CODFUN,CUENTA,IMPORTEVALE,NOMBRES,TIPO,USUARIO)"+"values(?,?,?,?,?,?,?)";
                         ps=cnn.prepareStatement(consulta);
                         ps.setString(1, fecha);
                         ps.setInt(2, v.getCodFunc());
                         ps.setDouble(3, v.getCuenta());
                         ps.setDouble(4, v.getImporteVale());
                         ps.setString(5, v.getNombre());
                         ps.setInt(6, v.getTipo().getCodigo());
                         ps.setString(7, frmPrin.instancia().getUsuario().getNombre());
                         insert+=ps.executeUpdate();
                     }
                 }
                 else{
                     liq++;
                 }
             }
             
             listado.add(insert);
             listado.add(update);
             listado.add(liq);
             
             if(cnn!=null){
                 cnn.close();
             }
             if(ps!=null){
                  ps.close();
             }
             
            
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return listado;
    }

    public ArrayList<TipoVale> traeDistintosTipos(String fecha) {
        ArrayList<TipoVale> tipos = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             String consulta="Select distinct(tipo) from PERS_VALES WHERE FECHA=? ";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setString(1, fecha);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
               TipoVale t = new TipoVale();
               t.setCodigo(rs.getInt("TIPO"));
               tipos.add(t);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return tipos;
    }
    
    public ArrayList<Object> cargoLiquidacionFecha(String fecha) {
        ArrayList<Object> liquidaciones = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             String consulta="select r.cod_func as cod,r.liquido_final liquido,r.tipoliq as tipoliq from pers_funcionarios f inner join pers_resumen r on r.cod_func=f.codigo where f.fecha_egreso Is Null AND f.cuenta<>0 and r.liquido_final>0 order by CODIGO";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             //ps.setString(1, fecha);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 LineaArchivoBanco liq = new LineaArchivoBanco();
                 liq.setF(this.pers.funcionarioBasicoVale(rs.getInt("cod"),cnn));
                 liq.setLiquidoFinal(rs.getDouble("liquido"));
                 liq.setTipoLiq(rs.getInt("TIPOLIQ"));
               
                 liquidaciones.add(liq);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return liquidaciones;
    }

    public Integer borrarEnPersIngresos(Integer cod, String fechaLiq) throws SQLException {
        Connection cnn=null;
  
        Integer i=0;
        try{
             cnn=conexion.Cadena();
             //cnn.setAutoCommit(false);
             String borrar="DELETE FROM PERS_INGRESOS WHERE COD_MOV=? and FECHA=?";
             PreparedStatement ps=cnn.prepareStatement(borrar);
             ps.setInt(1, cod);
             ps.setString(2, fechaLiq);
             i=ps.executeUpdate();
          
         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
                if(cnn!=null){
                 cnn.close();
         }
     }
         return i;
    }

    public void insertarCodigoMarca(Long obtenerMaxCodFunc, Integer funCod, Integer tipoLic, double d) {
        
    }

    public String buscoNombreTabla(String fecha) {
       String retorno="";
        try {
             Connection cnn=conexion.CadenaHist();
             String consulta = "SELECT * FROM SYSIBM.SYSTABLES WHERE NAME like ?";
           
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setString(1, "%"+fecha+"%");
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 retorno=rs.getString("NAME");
             }
             
             if(cnn!=null){
                 cnn.close();
                 ps.close();
                 rs.close();
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
        return retorno;
    }

    public ArrayList<Ingreso> cargoRetencionesAce(String nombreTabla,Codigo cod)throws BDExcepcion {
          ArrayList<Ingreso> vales = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.CadenaHist();
             Connection cnn2=null;
             cnn2=conexion.Cadena();
             String consulta="SELECT * FROM "+nombreTabla+" WHERE TESORERIA=? and planillado>0 order by cobro" ;
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, 400);
        
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 String cobroAux;
                 Ingreso ing = new Ingreso();
                 String cobro = rs.getString("COBRO");
                 cobroAux=cobro;
                 if("201507".equals(cobro)){
                     cobroAux="2700";
                 }                 
                 if(rs.getDouble("PLANILLADO")>0&&this.pers.estaActivo(cobroAux.trim(),cnn2)){
                    if("201507".equals(cobro)){
                       ing.setCodFunc(2700);
                       ing.setFunc(this.pers.funcionarioBasicoMasivo(2700,cnn2)); 
                       ing.setImporte(rs.getDouble("PLANILLADO")); 
                       ing.setCantidad(1.0);
                       ing.setCodMov(cod);
                    }
                    else{
                    ing.setCodFunc(Integer.valueOf(cobro));
                    ing.setFunc(this.pers.funcionarioBasicoMasivo(Integer.valueOf(cobro),cnn2)); 
                    ing.setImporte(rs.getDouble("PLANILLADO")); 
                    ing.setCantidad(1.0);
                    ing.setCodMov(cod);

                    }
                    vales.add(ing);
                 }
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
                 cnn2.close();
             }
             
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
              throw new BDExcepcion(ex.getMessage());
         }
         vales.sort((o1, o2) -> o1.compareTo(o2));
         return vales;
    }

    public boolean eliminaCodigoEnIngreso(Integer cod) {
       boolean retorno=false;
        try {
             Connection cnn=conexion.Cadena();
             String consulta = "DELETE FROM PERS_INGRESOS WHERE COD_MOV=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, cod);
             Integer i=ps.executeUpdate();
             if(i>0){
                 retorno=true;
             }
             
             if(cnn!=null){
                 cnn.close();
                 ps.close();
                 
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
        return retorno;
    }

    public ArrayList<Object> cargoLiquidacionFechaSinCuenta(String fecha) {
         ArrayList<Object> liquidaciones = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             String consulta="select r.cod_func as cod,r.liquido_final liquido,r.tipoliq as tipoliq from pers_funcionarios f inner join pers_resumen r on r.cod_func=f.codigo where f.fecha_egreso Is Null AND f.cuenta=0 and r.liquido_final>0 order by CODIGO";
             PreparedStatement ps=cnn.prepareStatement(consulta);
            // ps.setDouble(1, 0);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 LineaArchivoBanco liq = new LineaArchivoBanco();
                 liq.setF(this.pers.funcionarioBasicoVale(rs.getInt("cod"),cnn));
                 liq.setLiquidoFinal(rs.getDouble("liquido"));
                 liq.setTipoLiq(rs.getInt("TIPOLIQ"));
               
                 liquidaciones.add(liq);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return liquidaciones;
    }

    public ArrayList<Object> cargoValesFechaObjectSinCuenta(String fecha) {
         ArrayList<Object> vales = new ArrayList<>();
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             String consulta="Select * from PERS_VALES WHERE FECHA=? AND CUENTA=0 order by codfun";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setString(1, fecha);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 Vale v = new Vale();
                 v.setCodFunc(rs.getInt("CODFUN"));
                 v.setF(this.pers.funcionarioBasicoVale(rs.getInt("CODFUN"),cnn));
                 v.setCuenta(rs.getDouble("CUENTA"));
                 v.setFecha(rs.getDate("FECHA")); 
                 v.setFechaLiq(rs.getDate("FECHALIQ"));
                 v.setImporteVale(rs.getDouble("IMPORTEVALE"));
                 v.setNombre(rs.getString("NOMBRES")); 
                 v.setTipo(this.buscaTipoVale(rs.getInt("TIPO"),cnn));
                 v.setUsuario(rs.getString("USUARIO"));
                 vales.add(v);
             }
             
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(PersistenciaCodigo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return vales;
    }

    public int bloqueoContaduria() throws BDExcepcion {
         try {
             Connection cnn=null;
             
             cnn=conexion.Cadena();
             int retorno=-1;
             String consulta="Select VALOR from PERS_PARAMETROS WHERE NOMBRE=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setString(1, "BLOQUEO_CONTADURIA");
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 String ret=rs.getString("VALOR").trim();
                 retorno=Integer.parseInt(ret);
                 
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             } 
             return retorno;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }

    public ArrayList<CodigoPrelacion> cargoPrelacionCodigos(Codigo codigo)throws BDExcepcion {
        try {
             Connection cnn=null;
             ArrayList<CodigoPrelacion> lista=new ArrayList<>();
             CodigoPrelacion c=null;
             cnn=conexion.Cadena();
             cnn.setAutoCommit(false);
             String consulta="Select * from PERS_CODIGOS_PRELACION WHERE CODIGO=? order by CODFUNC";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, codigo.getCod());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 c=new CodigoPrelacion();
                 c.setCod(codigo); 
                 c.setF(this.pers.funcionarioBasicoMasivo(rs.getInt("CODFUNC"), cnn));
                 c.setFecha(rs.getTimestamp("FECHA"));
                 c.setPersistencia(true);
                 lista.add(c);
             }
             cnn.commit();
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return lista;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }

    public boolean insertoCodigoPrelacion(CodigoPrelacion codPrel) throws BDExcepcion{
         try {
             Connection cnn=null;
             Boolean alta=false;
             Integer i=-1;
             
             cnn=conexion.Cadena();
             //cnn.setAutoCommit(false);
             String insert="insert into PERS_CODIGOS_PRELACION(CODFUNC,CODIGO,FECHA)"+"values(?,?,?)";
             PreparedStatement ps=cnn.prepareStatement(insert);
             ps.setInt(1, codPrel.getF().getCodFunc());
             ps.setInt(2, codPrel.getCod().getCod());
             ps.setTimestamp(3, codPrel.getFecha());
             i=ps.executeUpdate();
             if(i==1){
                 alta=true;
                 this.pers.insertarAltaAuditoria(this.convertirFechaYHora(codPrel.getFecha()), cnn,"Inserto Prelacion Codigo "+codPrel.getCod(), codPrel.getF().getCodFunc());
             }
             
             if(cnn!=null){
                 cnn.close();
                 ps.close();
             }
             
             return alta;
         } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }

    public boolean actualizoCodigoPrelacion(CodigoPrelacion codPrel, Timestamp fechaNueva)throws BDExcepcion {
         try {
             Connection cnn=null;
             Integer suma=0;
             boolean ret=false;
             String insert="";
             PreparedStatement ps=null;
             cnn=conexion.Cadena();
             insert="update PERS_CODIGOS_PRELACION set FECHA=? WHERE CODFUNC=? AND CODIGO=?";
             ps=cnn.prepareStatement(insert);
             ps.setTimestamp(1, fechaNueva);
             ps.setInt(2, codPrel.getF().getCodFunc());
             ps.setInt(3, codPrel.getCod().getCod());
             
             suma+=ps.executeUpdate();
             if(suma>0){
                 ret=true;
                 this.pers.insertarModAuditoria(this.convertirFechaYHora(fechaNueva), this.convertirFechaYHora(codPrel.getFecha()), cnn,"Actualizo Prelación Codigo "+codPrel.getCod(), codPrel.getF().getCodFunc());
             }
             if(cnn!=null){
                 cnn.close();
                 ps.close();
             }
             return ret;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }

    public ArrayList<CodigoPrelacion> cargoPrelacionFuncionario(Funcionario f) throws BDExcepcion{
        try {
             Connection cnn=null;
             ArrayList<CodigoPrelacion> lista=new ArrayList<>();
             CodigoPrelacion c=null;
             cnn=conexion.Cadena();
             cnn.setAutoCommit(false);
             String consulta="Select * from PERS_CODIGOS_PRELACION WHERE CODFUNC=? order by CODIGO";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, f.getCodFunc());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 c=new CodigoPrelacion();
                 c.setCod(this.cargaCodigo(rs.getInt("CODIGO"), cnn)); 
                 c.setF(f); 
                 c.setFecha(rs.getTimestamp("FECHA"));
                 c.setPersistencia(true);
                 lista.add(c);
             }
             cnn.commit();
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return lista;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }
    
        public Codigo cargaCodigo(Integer cod,Connection cnn) throws BDExcepcion, SQLException{
        Codigo c=null;    
        String consulta="Select * from PERS_CODIGOS WHERE CODIGO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Codigo();
            c.setCod(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            c.setTipoValor(rs.getInt("TIPOVALOR"));
            }        
      
         return c;
    }

    public boolean estaEnPersIngresos(CodigoPrelacion prel) throws BDExcepcion{
        try {
             boolean esta=false;
             Connection cnn=null;
             cnn=conexion.Cadena();
           
             String consulta="Select * from PERS_INGRESOS WHERE COD_FUNC=? AND COD_MOV=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, prel.getF().getCodFunc());
             ps.setInt(2, prel.getCod().getCod());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                esta=true;
             }
       
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return esta;
         } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }

    public boolean estaActivaEnRetencionesFijas(CodigoPrelacion prel)throws BDExcepcion {
           try {
             boolean esta=false;
             Connection cnn=null;
             cnn=conexion.Cadena();
           
             String consulta="Select * from PERS_RETENCIONES_FIJAS WHERE COD_FUNC=? AND COD_MOV=? AND ACTIVA=1";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, prel.getF().getCodFunc());
             ps.setInt(2, prel.getCod().getCod());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                esta=true;
             }
       
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return esta;
         } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }

    public boolean estaEnPersHistLiquidaciones(CodigoPrelacion prel)throws BDExcepcion {
       try {
             boolean esta=false;
             Connection cnn=null;
             cnn=conexion.Cadena();
           
             String consulta="Select * from PERS_HIST_LIQUIDACIONES WHERE COD_FUNC=? AND COD_MOV=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, prel.getF().getCodFunc());
             ps.setInt(2, prel.getCod().getCod());
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                esta=true;
             }
       
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }
             return esta;
         } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
         }
    }

    public boolean eliminarPrelacion(CodigoPrelacion prel) throws BDExcepcion {
         try {
             Connection cnn=null;
             Boolean alta=false;
             Integer contador=0;
             cnn=conexion.Cadena();
             String insert="DELETE FROM PERS_CODIGOS_PRELACION WHERE CODFUNC=? AND CODIGO=?";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
             psFunc.setInt(1, prel.getF().getCodFunc());
             psFunc.setInt(2, prel.getCod().getCod());
             contador=psFunc.executeUpdate();
             cnn.commit();
             if (contador==1){
                 alta=true;
             }
             
             if(cnn!=null){
                 cnn.close();
                 psFunc.close();
             }
             return alta;
         } catch (ClassNotFoundException ex) {
             throw new BDExcepcion(ex.getMessage());
         } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
    }

    public Codigo obtenerCodigo(Integer codigo)throws BDExcepcion {
        Codigo c=null;
        try {
             Connection cnn=null;
             cnn=conexion.Cadena();
             //String consulta="Select codigo,descripcion,tipolic,tipovalor from PERS_CODIGOS WHERE CODIGO=? and TIPOLIC is not null and FIJO=0 and ingresomanual=1";
             String consulta="Select codigo,descripcion,tipolic,tipovalor from PERS_CODIGOS WHERE CODIGO=?";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ps.setInt(1, codigo);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 c=new Codigo();
                 c.setCod(rs.getInt("CODIGO"));
                 c.setDescripcion(rs.getString("DESCRIPCION").trim());
                 c.setTipo(rs.getInt("TIPOLIC"));
                 c.setTipoValor(rs.getInt("TIPOVALOR"));
             }
             if(cnn!=null){
                 ps.close();
                 rs.close();
                 cnn.close();
             }   
            
         } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
         }
          return c;
    }
    
   
      
        
    }
    

    
    
    
    

