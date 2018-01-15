
package Persistencia;

import Dominio.Codigo;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Dominio.IngresoMarca;
import Dominio.Retencion;
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

public class PersistenciaCodigo {
     private Conexion conexion;
     private PersistenciaFuncionario pers=new PersistenciaFuncionario();
     
             
    public ArrayList<Codigo> cargaComboCodigoLic() throws SQLException, ClassNotFoundException{
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
    
    public boolean insertarCodigoMarca(Long id, Integer codFunc,Integer codigo,Double cantidad) throws SQLException{
        Connection cnn=null;
        Boolean alta=false;
        Integer i=-1;
        try{
             cnn=conexion.Cadena();
             //cnn.setAutoCommit(false);
             String insert="insert into PERS_INGRESOS_MARCAS(ID,CODFUNC,CODIGO,CANTIDAD)"+"values(?,?,?,?)";
             PreparedStatement ps=cnn.prepareStatement(insert);
             ps.setLong(1, id );
             ps.setInt(2, codFunc);
             ps.setInt(3, codigo);
             ps.setDouble(4,cantidad);
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
        ps.setString(1, "BLOQUEO_LIQ");
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
    
    public Codigo obtenerCodigoTipoLiq(Integer codigo) throws ClassNotFoundException, SQLException {
      Connection cnn=null;
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="Select codigo,descripcion,tipolic,tipovalor from PERS_CODIGOS WHERE CODIGO=? and TIPOLIC is null and FIJO=0 and ingresomanual=1";
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
         return c;   
    }

    public ArrayList<Codigo> carcaComboCodigoFullLiq() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        ArrayList<Codigo> lista=new ArrayList<>();
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_CODIGOS WHERE TIPOLIC is null and FIJO=0 AND INGRESOMANUAL=1 order by codigo";
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

    public boolean estaEnPersIngresos(Funcionario f, Codigo cod) throws ClassNotFoundException, SQLException {
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
    }

    public ArrayList<Ingreso> traePersIngresos(Integer codFunc) throws ClassNotFoundException, SQLException {
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
    }

    public boolean borrarCodigoEnPersIngresos(Ingreso ingres) throws SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer i=-1;
        try{
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

    public int insertarEnPersIngresos(ArrayList<Ingreso> codigos) throws SQLException {
        Connection cnn=null;
        Integer ret=0;
        String insert="";
        PreparedStatement ps=null;
        try{
             cnn=conexion.Cadena();
             for(Ingreso i:codigos){
                
                     insert="insert into PERS_INGRESOS(FECHA,COD_FUNC,COD_MOV,CANTIDAD,IMPORTE)"+"values(?,?,?,?,?)";
                     ps=cnn.prepareStatement(insert);
                     ps.setString(1, this.convertirFecha(i.getFecha()));
                     ps.setInt(2, i.getCodFunc());
                     ps.setInt(3, i.getCodMov().getCod());
                     ps.setDouble(4,i.getCantidad());
                     ps.setDouble(5,i.getImporte());

                     ret+=ps.executeUpdate();
             }
             
             
                              
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

    public int actualizaPersIngresos(Ingreso ing, Double d) throws SQLException, ClassNotFoundException {
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
    }
    
    public ArrayList<Ingreso> traeIngresosPorCod(Integer cod) throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        cnn=conexion.Cadena();
        ArrayList<Ingreso> ingresos=new ArrayList<>();
        String consulta="Select FECHA,COD_FUNC,COD_MOV,CANTIDAD,IMPORTE from PERS_INGRESOS WHERE COD_MOV=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
               Ingreso ing=new Ingreso();
               ing.setFecha(rs.getDate("FECHA"));
               ing.setFunc(this.pers.funcionarioBasico(rs.getInt("COD_FUNC")));
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

    public boolean estaEnCodigosFijos(Funcionario f, Codigo cod) throws ClassNotFoundException, SQLException {
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
    }
    
    public boolean insertaEnCodigosFijos(Ingreso ing) throws ClassNotFoundException, SQLException {
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
    }
    
    public boolean actualizaEnCodigosFijos(Ingreso ing) throws ClassNotFoundException, SQLException {
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
    }

    public boolean borrarCodigoEnPersCodFijo(Ingreso ingres) throws SQLException {
         Connection cnn=null;
        Boolean alta=false;
        Integer i=-1;
        try{
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

    public ArrayList<Ingreso> cargaMovimientosFijoFunc(Integer codFunc) throws ClassNotFoundException, SQLException {
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
    }
    
    
    //RETENCIONES FIJAS
    public ArrayList<Codigo> cargaComboRetencionesFijo() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        ArrayList<Codigo> lista=new ArrayList<>();
        Codigo c=null;    
        cnn=conexion.Cadena();
        String consulta="select codigo,descripcion from pers_codigos where retencion=1 order by codigo";
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
    
    public ArrayList<Retencion> listarRetenciones(int activa,Funcionario f, Codigo cod) throws ClassNotFoundException, SQLException{
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
            
             String borrar="DELETE FROM PERS_RETENCIONES_FIJAS WHERE COD_FUNC=? and COD_MOV=?";
             PreparedStatement ps=cnn.prepareStatement(borrar);
             ps.setInt(1, ret.getFunc().getCodFunc());
             ps.setInt(2, ret.getCod().getCod());
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
             insert="update PERS_RETENCIONES_FIJAS set TIPO=?, IMPORTE=?, PORCENTAJE=?, ACTIVA=?, SUELDO=?, OTROS=?  WHERE COD_FUNC=? AND COD_MOV=?";
             psFunc=cnn.prepareStatement(insert);
             psFunc.setInt(1, r.getTipo());
             psFunc.setDouble(2,r.getImporte());
             psFunc.setDouble(3,r.getPorcentaje());
             psFunc.setInt(4, r.getActiva());
             psFunc.setInt(5, r.getSueldo());
             psFunc.setInt(6, r.getOtros());
             psFunc.setInt(7, r.getFunc().getCodFunc());
             psFunc.setInt(8, r.getCod().getCod());
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
}
