package Persistencia;

import Dominio.Codigo;
import Dominio.Funcionario;
import Dominio.IngresoMarca;
import Dominio.Marca;
import Presentacion.frmPrin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class PersistenciaTripaliare {
    private Conexion conexion;
    private final frmPrin frm; 
    private Integer contador=0;
    private Integer ctr=0;
    private PersistenciaCodigo persCod;
    private String hoy;
    Connection cnn=null;
    ArrayList<Marca> marcas=null;
  //  Connection cn=null;
    public PersistenciaTripaliare() throws ClassNotFoundException, SQLException {
        this.frm = frmPrin.instancia();
        hoy=this.convertirFecha();
        this.cnn=Conexion.Cadena();
        
    }
    private String convertirFecha(){
   String str=null;
   Date fecha=new Date();
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
    }
         return str;
    }
    
    public boolean cargarMarcas(Date desde,Date hasta,ArrayList<Integer> listado) throws SQLException, ClassNotFoundException{
        marcas=traeMarcas(desde,hasta);
        this.eliminarMarcasNoFunc(listado);
        ArrayList<Marca> marcasLocal=this.traerTablaLocal(desde, hasta,null);
        ArrayList<Marca> auxiliar=new ArrayList<>();
        this.eliminarMarca();
         
        for(Marca m:marcas){
            for(Marca s:marcasLocal){
                if(!s.getTipo().equals("FALTA")){ 
                    if((m.getFunCod().equals(s.getFunCod())&&m.getMarcaFecha().equals(s.getMarcaFecha()))){
                        auxiliar.add(m);
                    }

                }
            
            }
        }
        for(Marca m:marcas){
            if(!auxiliar.contains(m)){
                m.setSupervisado(1);
                contador+=this.insertarMarcas(m);
                             
            }
        }
        return true;
    }
    
    private void renuevaConexion() throws SQLException, ClassNotFoundException{
       if(cnn!=null){
            if(cnn.isClosed()){
               cnn=Conexion.Cadena();
            }
            if(contador>=750){
                cnn.close();
                cnn=Conexion.Cadena();
                contador=0;
            }
       }
       else{
           cnn=Conexion.Cadena();
       }
    }
     
    public void cargarMarcass(Date desde,Date hasta) throws SQLException, ClassNotFoundException, BDExcepcion{
        //this.limpiar();
        ArrayList<Marca> marcas=traeMarcas(desde,hasta);
        //ArrayList<Marca> marcasLocal=this.traerTablaLocal(desde, hasta,null);
        for(Marca m:marcas){
             contador+=this.insertarMarcas(m);
            
        }
      
    }
    
    //TRAE LAS MARCAS DESDE TRIPALIARE
    public ArrayList<Marca> traeMarcas(Date desde,Date hasta) throws SQLException, ClassNotFoundException {
        Connection cn=null;
        ArrayList<Marca> marcas=new ArrayList<>();
        cn=Conexion.CadenaPos();
        String consulta="Select funcod, marfec from s_marcas where marfec>=? and marfec<=? order by funcod, marfec";
        PreparedStatement ps=cn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1,  des);
        ps.setTimestamp(2, has);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
           Marca marca=new Marca();
            marca.setFunCod(Integer.valueOf(rs.getString("funcod").trim()));
            marca.setSupervisado(0);
            marca.setResponsable(frm.getUsuario());
            marca.setMarcaFecha(rs.getTimestamp("marfec"));
            marca.setTipo("RELOJ");
            marcas.add(marca);
            }
            if(cn!=null){
            ps.close();
            rs.close();
            cn.close();
            
            }
            return marcas;
    }
    
    
    
    //OBTIENE EL ULTIMO CODIGO DE MARCA POR FUNCIONARIO
    public Long obtenerMaxCodFunc(Integer funCod) throws ClassNotFoundException, SQLException {
        Connection c=null;
        c=Conexion.Cadena();
        String consulta="Select max(id) as max from Pers_marcas where codFunc=?";
        contador++;
        PreparedStatement ps=c.prepareStatement(consulta);
        ps.setInt(1,  funCod);
        ResultSet rs=ps.executeQuery();
        Long retorno=null;
        while (rs.next()) {
           retorno=rs.getLong("max");
            }
            
            ps.close();
            rs.close();
                     
           
            return retorno+1;
    }
    
public Date sumarDiasFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
	calendar.setTime(fecha); // Configuramos la fecha que se recibe
	calendar.add(Calendar.DAY_OF_YEAR, -30);  // numero de días a añadir, o restar en caso de días<0
	return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
}
 private String convertirFecha(Date fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
        }
    
    return str;
    }
 
//TRAE TODAS LAS MARCAS DE LA BASE DE DATOS LOCAL
    public ArrayList<Marca> traerTablaLocal(Date desde,Date hasta,Funcionario f) throws ClassNotFoundException, SQLException {
        ArrayList<Marca> marcas=new ArrayList<>();
        PreparedStatement ps=null;
        if(desde!=null &&hasta!=null&&f==null){
        String consulta="Select * from Pers_marcas where marca>=? and marca<=? order by codFunc, marca";
        this.renuevaConexion();
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        }
        else if(desde!=null &&hasta!=null&&f!=null){
                String consulta="Select * from Pers_marcas where codfunc=? and marca>=? and marca<=? order by marca";
                this.renuevaConexion();
                ps=cnn.prepareStatement(consulta);
                java.sql.Timestamp des = this.fijaHoraDesde(desde);
                java.sql.Timestamp has = this.fijaHoraHasta(hasta);
                ps.setInt(1, f.getCodFunc());
                ps.setTimestamp(2, des);
                ps.setTimestamp(3, has);
                }
                else{
                    String consulta="Select * from Pers_marcas order by codFunc, marca";
                    this.renuevaConexion();
                    ps=cnn.prepareStatement(consulta);
                }
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
           Marca marca=new Marca();
            marca.setFunCod(rs.getInt("CODFUNC"));
            marca.setSupervisado(rs.getInt("SUPERVISADO"));
            marca.setResponsable(rs.getString("RESPONSABLE"));
            marca.setMarcaFecha(rs.getTimestamp("MARCA"));
            marca.setId(rs.getLong("ID"));
            marca.setFecha(rs.getDate("FECHA"));
            marca.setTipo(rs.getString("TIPO"));
            marca.setIncongruencia(rs.getInt("INCONGRUENCIA"));
            marca.setProcesado(rs.getDate("PROCESADO"));
            marca.setEditada(rs.getInt("EDITADA"));
            marcas.add(marca);
            }
            
            ps.close();
            rs.close();
            
            return marcas;
    }
    
        public ArrayList<Marca> traerTablaFiltro(Date desde,Date hasta,Funcionario f,Integer sup) throws ClassNotFoundException, SQLException {
        ArrayList<Marca> marcas=new ArrayList<>();
        PreparedStatement ps=null;
        if(desde!=null &&hasta!=null&&f==null){
            if(sup==1){
                String consulta="Select * from Pers_marcas where marca>=? and marca<=? order by codFunc, marca";
                this.renuevaConexion();
                ps=cnn.prepareStatement(consulta);
                java.sql.Timestamp des = this.fijaHoraDesde(desde);
                java.sql.Timestamp has = this.fijaHoraHasta(hasta);
                ps.setTimestamp(1, des);
                ps.setTimestamp(2, has);
            }
            else{
             String consulta="Select * from Pers_marcas where marca>=? and marca<=? and supervisado=? order by codFunc, marca";
                this.renuevaConexion();
                ps=cnn.prepareStatement(consulta);
                java.sql.Timestamp des = this.fijaHoraDesde(desde);
                java.sql.Timestamp has = this.fijaHoraHasta(hasta);
                ps.setTimestamp(1, des);
                ps.setTimestamp(2, has);   
                ps.setInt(3, sup); 
            }
        }
        else if(desde!=null &&hasta!=null&&f!=null){
            if(sup==1){
                String consulta="Select * from Pers_marcas where codfunc=? and marca>=? and marca<=?  order by marca";
                this.renuevaConexion();
                ps=cnn.prepareStatement(consulta);
                java.sql.Timestamp des = this.fijaHoraDesde(desde);
                java.sql.Timestamp has = this.fijaHoraHasta(hasta);
                ps.setInt(1, f.getCodFunc());
                ps.setTimestamp(2, des);
                ps.setTimestamp(3, has);
                }
            else{
                String consulta="Select * from Pers_marcas where codfunc=? and marca>=? and marca<=? and supervisado=?  order by marca";
                this.renuevaConexion();
                ps=cnn.prepareStatement(consulta);
                java.sql.Timestamp des = this.fijaHoraDesde(desde);
                java.sql.Timestamp has = this.fijaHoraHasta(hasta);
                ps.setInt(1, f.getCodFunc());
                ps.setTimestamp(2, des);
                ps.setTimestamp(3, has);
                ps.setInt(4, sup); 
            }
        }
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
           Marca marca=new Marca();
            marca.setFunCod(rs.getInt("CODFUNC"));
            marca.setSupervisado(rs.getInt("SUPERVISADO"));
            marca.setResponsable(rs.getString("RESPONSABLE"));
            marca.setMarcaFecha(rs.getTimestamp("MARCA"));
            marca.setId(rs.getLong("ID"));
            marca.setFecha(rs.getDate("FECHA"));
            marca.setTipo(rs.getString("TIPO"));
            marca.setIncongruencia(rs.getInt("INCONGRUENCIA"));
            marca.setProcesado(rs.getDate("PROCESADO"));
            marcas.add(marca);
            }
            
            ps.close();
            rs.close();
            
            return marcas;
    }
    
     public ArrayList<Marca> traerTablaLocalParcial(Date desde,Date hasta) throws ClassNotFoundException, SQLException {
        ArrayList<Marca> marcas=new ArrayList<>();
        PreparedStatement ps=null;
        
        String consulta="Select ID,CODFUNC,PROCESADO from Pers_marcas where marca>=? and marca<=? order by codFunc";
        this.renuevaConexion();
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
              
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
           Marca marca=new Marca();
            marca.setId(rs.getLong("ID"));
            marca.setFunCod(rs.getInt("CODFUNC"));
            marca.setProcesado(rs.getDate("PROCESADO"));
            marcas.add(marca);
            }
            
            ps.close();
            rs.close();
            
            return marcas;
    }
  
    
    public Integer insertarMarcas(Marca m) throws ClassNotFoundException, SQLException{
        Integer retorno=0;
       if(m.getTipo().equals("RELOJ")){
            String consulta="Insert into Pers_marcas(ID,CODFUNC,MARCA,SUPERVISADO,RESPONSABLE,FECHA,TIPO)"+"values(?,?,?,?,?,?,?)";
            this.renuevaConexion();
            PreparedStatement ps=cnn.prepareStatement(consulta);
                ps.setLong(1, this.obtenerMaxCodFunc(m.getFunCod()));
                ps.setInt(2, m.getFunCod());
                ps.setTimestamp(3, m.getMarcaFecha());
                ps.setInt(4, m.getSupervisado());
                ps.setString(5, m.getResponsable());
                ps.setString(6, hoy);
                ps.setString(7, m.getTipo());
                retorno+=ps.executeUpdate();
        }
       else{
           String consulta="Insert into Pers_marcas(ID,CODFUNC,SUPERVISADO,MARCA,TIPO,FECHA,RESPONSABLE)"+"values(?,?,?,?,?,?,?)";
           this.renuevaConexion(); 
           PreparedStatement ps=cnn.prepareStatement(consulta);
                ps.setLong(1, this.obtenerMaxCodFunc(m.getFunCod()));
                ps.setInt(2, m.getFunCod());
                ps.setInt(3, m.getSupervisado());
                //ps.setString(5, m.getResponsable());
                ps.setTimestamp(4, m.getMarcaFecha());
                ps.setString(5, m.getTipo());
                ps.setString(6, hoy);
                ps.setString(7, frm.getUsuario());
                retorno=ps.executeUpdate();
       }
           return retorno;
                       
    }
    
    public Integer actualizarMarca(Marca m,Marca marca) throws SQLException, ClassNotFoundException{
       
        Integer retorno=0;
        PreparedStatement ps=null;
        if(marca==null){
        String consulta="update Pers_marcas set SUPERVISADO=?,TIPO=?,FECHA=?,RESPONSABLE=?,INCONGRUENCIA=?,MARCA=? where id=? and codfunc=?";
        contador++;
        this.renuevaConexion();    
        ps=cnn.prepareStatement(consulta);
                ps.setLong(1, m.getSupervisado());
                ps.setString(2, m.getTipo());
                ps.setString(3, this.convertirFecha(m.getFecha()));
                ps.setString(4, frm.getUsuario());
                ps.setInt(5, m.getIncongruencia());
                ps.setTimestamp(6,m.getMarcaFecha());
                ps.setLong(7, m.getId());
                ps.setInt(8, m.getFunCod());
                
                retorno=ps.executeUpdate();
        }
        else{
        String consulta="update Pers_marcas set SUPERVISADO=?,TIPO=?,FECHA=?,RESPONSABLE=?,INCONGRUENCIA=?,MARCA=?,EDITADA=? where id=? and codfunc=?";
        contador++;
        this.renuevaConexion();    
        ps=cnn.prepareStatement(consulta);
                ps.setLong(1, m.getSupervisado());
                ps.setString(2, m.getTipo());
                ps.setString(3, this.convertirFecha(m.getFecha()));
                ps.setString(4, frm.getUsuario());
                ps.setInt(5, m.getIncongruencia());
                ps.setTimestamp(6,m.getMarcaFecha());
                ps.setInt(7, 1);
                ps.setLong(8, m.getId());
                ps.setInt(9, m.getFunCod());
                
                retorno=ps.executeUpdate();
                if(retorno>0){
                    this.referenciaEnTripa(marca,m); 
                }
        }
        
        if(cnn!=null){
           ps.close();
           cnn.close();
         }
               
        return retorno;
    }
    
    private void referenciaEnTripa(Marca marca,Marca m) throws ClassNotFoundException, SQLException{
        Connection cn=null;
        Integer ret=0;
        cn=Conexion.CadenaPos();
        String consulta="update s_marcas s set marfec=to_timestamp(?, 'yyyy/mm/dd HH24:MI:SS') where s.marfec<=? and s.marfec>=? and CAST(s.funcod AS integer)=?";
        PreparedStatement ps=cn.prepareStatement(consulta);
        java.sql.Timestamp des = marca.getMarcaFecha();
        java.sql.Timestamp nuev= m.getMarcaFecha();
                
        ps.setString(1,  this.fijaHora(nuev));
        ps.setTimestamp(2,  des);
        ps.setTimestamp(3,  des);
        ps.setInt(4, marca.getFunCod());
        ret+=ps.executeUpdate();
        
         if(cnn!=null){
           ps.close();
           cnn.close();
         }
        
        
    }
    
    private String fijaHora(Timestamp marca){
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(marca.getTime());
    Timestamp ts = new java.sql.Timestamp(marca.getTime());
    String S = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(marca);
    return S;
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
          
    public ArrayList<IngresoMarca> obtenerIngresosMarca(Date desde,Date hasta,Integer codfunc) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        ArrayList<IngresoMarca> ingresos=new ArrayList<>();
        cnn=conexion.Cadena();
        String consulta="select sum(m.cantidad) as cantidad, m.codigo from pers_ingresos_marcas m inner join pers_marcas s on m.id=s.id and m.codfunc=s.codfunc and(s.MARCA>? AND s.MARCA<?) AND s.PROCESADO IS NULL and m.codfunc=? group by m.codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        ps.setInt(3, codfunc);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            IngresoMarca m=new IngresoMarca();
            m.setCodFunc(codfunc);
            m.setCodigo(rs.getInt("CODIGO"));
            m.setCantidad(rs.getDouble("CANTIDAD"));
            ingresos.add(m);
           }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return ingresos; 
    }
    
    public Integer obtieneDiferencia() throws SQLException, ClassNotFoundException{
        Integer retorno=0;
        String consulta="select valor from PERS_PARAMETROS where nombre=?";
        contador++;
        this.renuevaConexion();    
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, "MARGEN_MARCA");
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            retorno=rs.getInt("valor");
        }        
        this.cerrarConexion();
        return retorno;
               
    }
    
    public void cerrarConexion() throws SQLException{
        cnn.close();
    }

    public Integer procesar(Date desde, Date hasta) throws SQLException, ClassNotFoundException {
        Connection c=null;
        c=conexion.Cadena();
        Date fecha=new Date();
        Integer ret=0;
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        String consulta="update Pers_marcas set PROCESADO=? where PROCESADO IS NULL AND (marca>=? and marca<=?)";
        PreparedStatement ps=c.prepareStatement(consulta);
                ps.setString(1,this.fijaDia(fecha));
                ps.setTimestamp(2, des);
                ps.setTimestamp(3, has);
                ret+=ps.executeUpdate();
        
        if(cnn!=null){
            cnn.close();
            ps.close();
        }
               
       return ret;
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

    
       
    
     public Integer insertarEnIngresos(IngresoMarca m,String fe) throws SQLException{
        Connection cnn=null;
        Integer ret=0;
        
        try{
             cnn=conexion.Cadena();
             String insert="insert into PERS_INGRESOS(FECHA,COD_FUNC,COD_MOV,CANTIDAD)"+"values(?,?,?,?)";
             PreparedStatement ps=cnn.prepareStatement(insert);
           
                ps.setString(1, fe);
                ps.setInt(2, m.getCodFunc());
                ps.setInt(3, m.getCodigo());
                ps.setDouble(4,m.getCantidad());
             ret+=ps.executeUpdate();
             
                 
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

    private void eliminarMarca() throws SQLException, ClassNotFoundException {
        Connection cn=null;
        cn=Conexion.Cadena();
        String delete="delete from PERS_MARCAS where TIPO=? AND SUPERVISADO=? AND EDITADA IS NULL AND PROCESADO IS NULL";
        PreparedStatement borrar=cn.prepareStatement(delete);
        borrar.setString(1, "FALTA");
        borrar.setInt(2, 0);
        ctr=borrar.executeUpdate();
        
        cn.close();
        
    }
    
    public ArrayList<Integer> funcDistintos(Date desde,Date hasta) throws SQLException, ClassNotFoundException {
        Connection cn=null;
        Integer i=null;
        ArrayList<Integer> funcionarios=new ArrayList<>();
        cn=Conexion.CadenaPos();
        String consulta="Select distinct(funcod) from s_marcas where marfec>=? and marfec<=? order by funcod";
        PreparedStatement ps=cn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1,  des);
        ps.setTimestamp(2, has);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
            i=Integer.valueOf(rs.getString("funcod").trim());
            funcionarios.add(i);
            }
            if(cn!=null){
            ps.close();
            rs.close();
            cn.close();
            
            }
            return funcionarios;
    }

    private void eliminarMarcasNoFunc(ArrayList<Integer> listado) {
       ArrayList<Marca>marc=null;
        if(listado!=null){
            marc=new ArrayList<>();
        for(Integer i:listado){
            for(Marca m:marcas){
                if(m.getFunCod().equals(i)){
                    marc.add(m);
                }
            }
        }
        for(Marca s:marc){
            marcas.remove(s);
        }
       }
    }
    
    
    public Timestamp ultimaMarca() throws SQLException, ClassNotFoundException {
        Connection cn=null;
        Timestamp ultfecha=null;
        cn=Conexion.CadenaPos();
        String consulta="Select max(marfec) from s_marcas";
        PreparedStatement ps=cn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
          
            ultfecha=this.fijaHoraHasta(rs.getTimestamp("max"));
     
            }
            if(cn!=null){
            ps.close();
            rs.close();
            cn.close();
            
            }
            return ultfecha;
    }
    
    public Integer sinSupervisar(Date desde,Date hasta) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        ArrayList<IngresoMarca> ingresos=new ArrayList<>();
        cnn=conexion.Cadena();
        Integer retorno=-1;
        String consulta="SELECT COUNT(*) AS CANTIDAD from PERS_MARCAS where marca>=? and marca<=? AND SUPERVISADO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        ps.setInt(3, 0);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            retorno=rs.getInt("CANTIDAD");
           }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return retorno; 
    }

    public ArrayList<Marca> codigosFuncionarios(Funcionario f,Date desde,Date hasta,Integer sup) throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        Marca m=null;
        cnn=conexion.Cadena();
        ArrayList<Marca> marcas=new ArrayList<>();
        PreparedStatement ps=null;
        String consulta="";
        if(f==null&&sup==1){       
        consulta="SELECT distinct(m.codfunc),e.codigo FROM PERS_MARCAS m,pers_ingresos_marcas e where m.marca>=? and m.marca<=? and m.id=e.id and m.codfunc=e.codfunc and e.codigo<>10 order by e.codigo";
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        }
        else if(f==null&&sup==0){
        consulta="SELECT distinct(m.codfunc),e.codigo FROM PERS_MARCAS m,pers_ingresos_marcas e where m.marca>=? and m.marca<=? and m.supervisado=? and m.id=e.id and m.codfunc=e.codfunc and e.codigo<>10 order by e.codigo";
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        ps.setInt(3, sup);
        }
        else if(f!=null&&sup==1){
        consulta="SELECT distinct(m.codfunc),e.codigo FROM PERS_MARCAS m,pers_ingresos_marcas e where m.marca>=? and m.marca<=? and m.codfunc=? and m.id=e.id and m.codfunc=e.codfunc and e.codigo<>10 order by e.codigo";
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has); 
        ps.setInt(3, f.getCodFunc());
        }
        else if(f!=null && sup==0){
        consulta="SELECT distinct(m.codfunc),e.codigo FROM PERS_MARCAS m,pers_ingresos_marcas e where m.marca>=? and m.marca<=? and m.supervisado=? and m.codfunc=? and m.id=e.id and m.codfunc=e.codfunc and e.codigo<>10 order by e.codigo";
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        ps.setInt(3, sup);
        ps.setInt(4, f.getCodFunc());
        }
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            m=new Marca();
            m.setFunCod(rs.getInt("CODFUNC"));
            m.setIncongruencia(rs.getInt("CODIGO"));
            marcas.add(m);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return marcas;
    }
    
    public ArrayList<Marca> codigoDiezDiasGenerado(Funcionario f,Integer mes) throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        Marca m=null;
        cnn=conexion.Cadena();
        ArrayList<Marca> marcas=new ArrayList<>();
        PreparedStatement ps=null;
        String consulta="";
        Integer total=0;
        if(f==null){
            consulta="select dias_generados,dias_descuento,codfunc from pers_licencia_generada where month(fechaini)=?";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, mes);
        }
        else{
            consulta="select dias_generados,dias_descuento,codfunc from pers_licencia_generada where month(fechaini)=1 and codfunc=?";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, mes);
            ps.setInt(2, f.getCodFunc());
        }
        
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            m=new Marca();
            //se usa marca como objeto contenedor para poder integrarlo a las marcas
            //los atributos no reflejan lo que llevan adentro
            //solo importa el tipo de datos
            m.setFunCod(rs.getInt("CODFUNC"));
            total=rs.getInt("DIAS_GENERADOS")-rs.getInt("DIAS_DESCUENTO");
            m.setEditada(total);
            m.setIncongruencia(10);
            marcas.add(m);
            }      
             if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return marcas;
        
    }
    
    
    public ArrayList<Codigo> codigosDistintos(Funcionario f,Date desde,Date hasta,Integer sup) throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        Codigo m=null;
        cnn=conexion.Cadena();
        ArrayList<Codigo> codigos=new ArrayList<>();
        PreparedStatement ps=null;
        String consulta="";
        this.persCod=new PersistenciaCodigo();
        if(f==null&&sup==1){       
        consulta="SELECT distinct(e.codigo) FROM PERS_MARCAS m,pers_ingresos_marcas e where m.marca>=? and m.marca<=? and m.id=e.id and m.codfunc=e.codfunc and e.codigo<>10 order by e.codigo";
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        }
        else if(f==null&&sup==0){
        consulta="SELECT distinct(e.codigo) FROM PERS_MARCAS m,pers_ingresos_marcas e where m.marca>=? and m.marca<=? and m.supervisado=? and m.id=e.id and m.codfunc=e.codfunc and e.codigo<>10 order by e.codigo";
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        ps.setInt(3, sup);
        }
        else if(f!=null&&sup==1){
        consulta="SELECT distinct(e.codigo) FROM PERS_MARCAS m,pers_ingresos_marcas e where m.marca>=? and m.marca<=? and m.codfunc=? and m.id=e.id and m.codfunc=e.codfunc and e.codigo<>10 order by e.codigo";
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has); 
        ps.setInt(3, f.getCodFunc());
        }
        else if(f!=null && sup==0){
        consulta="SELECT distinct(e.codigo) FROM PERS_MARCAS m,pers_ingresos_marcas e where m.marca>=? and m.marca<=? and m.supervisado=? and m.codfunc=? and m.id=e.id and m.codfunc=e.codfunc and e.codigo<>10 order by e.codigo";
        ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        ps.setInt(3, sup);
        ps.setInt(4, f.getCodFunc());
        }
        m=this.persCod.obtenerCodigoTipoTope(10);
        codigos.add(m);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            m=this.persCod.obtenerCodigoTipoTope(rs.getInt("CODIGO"));
            codigos.add(m);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            
         return codigos;
    }
    
    public Double CantidadCodigo(Date desde,Date hasta,Integer codFunc,Integer codigo) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        Double retorno=null;
        String consulta="select  sum(e.cantidad) as cantidad from pers_ingresos_marcas e,pers_marcas m where m.marca>=? and m.marca<=? and e.codfunc=? and e.codigo=? and m.codfunc=e.codfunc and m.id=e.id";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        java.sql.Timestamp des = this.fijaHoraDesde(desde);
        java.sql.Timestamp has = this.fijaHoraHasta(hasta);
        ps.setTimestamp(1, des);
        ps.setTimestamp(2, has);
        ps.setInt(3, codFunc);
        ps.setInt(4, codigo);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            retorno=rs.getDouble("CANTIDAD");
           }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return retorno; 
    }

    public boolean generarMarcaManual(Marca m) throws ClassNotFoundException, SQLException {
         Integer retorno=0;
         boolean ret=false;
         Connection cnn=null;
         cnn=conexion.Cadena();
            String consulta="Insert into Pers_marcas(ID,CODFUNC,MARCA,SUPERVISADO,RESPONSABLE,FECHA,TIPO)"+"values(?,?,?,?,?,?,?)";
            
            PreparedStatement ps=cnn.prepareStatement(consulta);
                ps.setLong(1, this.obtenerMaxCodFunc(m.getFunCod()));
                ps.setInt(2, m.getFunCod());
                ps.setTimestamp(3, m.getMarcaFecha());
                ps.setInt(4, m.getSupervisado());
                ps.setString(5, frm.getUsuario());
                ps.setString(6, hoy);
                ps.setString(7, m.getTipo());
                retorno+=ps.executeUpdate();
        
                if(retorno==1){
                    ret=true;
                }
                if(cnn!=null){
                ps.close();
                cnn.close();
            }
                
           return ret;
    }
  
}
