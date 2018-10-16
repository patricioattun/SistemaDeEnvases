
package Persistencia;

import Dominio.Banco;
import Dominio.CodigoBcu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenciaBanco {
    
    private Conexion conexion;
    private ArrayList<Banco> bancos;
    private PersistenciaCombos perCom;
    
    
    public ArrayList<Banco> cargaComboBanco() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        Banco banco=null; 
        perCom=new PersistenciaCombos();
        bancos=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_BANCOS order by sucursal";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            banco=new Banco();
            CodigoBcu CodigoBcu = new CodigoBcu();
            banco.setDepartamento(perCom.buscarDepto(rs.getInt("DEPARTAMENTO")));
            banco.setCodigo(rs.getInt("CODIGO"));
            banco.setSucursal(rs.getInt("SUCURSAL"));
            banco.setNombre(rs.getString("NOMBRE"));
            banco.setLocalidad(rs.getString("LOCALIDAD"));
            banco.setDireccion(rs.getString("DIRECCION"));
            banco.setTelefono(rs.getString("TELEFONO"));
            banco.setCodigoBcu(this.buscaCodigoBcu(cnn, rs.getString("CODIGO_BCU")));
           
            
            bancos.add(banco);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            
         return bancos;
    }
    
    public boolean existe(Integer sucursal, String nombre){
        
        Boolean esta=false;
        Integer i=0;
        while(i<bancos.size()&&!esta){
            if(bancos.get(i).getSucursal().equals(sucursal)&&bancos.get(i).getNombre().equals(nombre)){
                esta=true;
            }
           i++; 
        }
        return esta;
    }

    public boolean altaBanco(Banco b) throws SQLException {
      Connection cnn=null;
        Boolean alta=false;
        Integer i=-1;
        Time t;
        Integer codBanco=null;
         try{
             cnn=conexion.Cadena();
             String insert="insert into PERS_BANCOS(CODIGO,SUCURSAL,NOMBRE,DEPARTAMENTO,LOCALIDAD,DIRECCION,TELEFONO,CODIGO_BCU)"+"values(?,?,?,?,?,?,?,?)";
             PreparedStatement ps=cnn.prepareStatement(insert);
             codBanco=this.buscarUltimoCod();
             ps.setInt(1, codBanco );
             ps.setInt(2, b.getSucursal());
             ps.setString(3, b.getNombre());
             ps.setInt(4,b.getDepartamento().getCodigo());
             ps.setString(5, b.getLocalidad());
             ps.setString(6,b.getDireccion());
             ps.setString(7, b.getTelefono());
             ps.setString(8, b.getCodigoBcu().getCodigo());
             i=ps.executeUpdate();
             alta=true;
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

    public boolean eliminaBanco(Integer cod) throws SQLException {
        Connection cnn=null;
        Boolean baja=false;
        Integer i=0;
        Time t;
         try{
             cnn=conexion.Cadena();
             String delete="delete from PERS_BANCOS where CODIGO=?";
             PreparedStatement ps=cnn.prepareStatement(delete);
            
             ps.setInt(1,cod);
             
             
             i=ps.executeUpdate();
             if(i!=0){
             baja=true;
             }
             
         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
                if(cnn!=null){
                        cnn.close();
         }
     }
         return baja;
    }

    public boolean modificaBanco(Banco b) throws SQLException {
        Connection cnn=null;
        Boolean mod=false;
        Integer i=0;
        try{
             cnn=conexion.Cadena();
             String modifica="UPDATE PERS_BANCOS set SUCURSAL=?, NOMBRE=?, DEPARTAMENTO=?, LOCALIDAD=?,DIRECCION=?,  TELEFONO=?, CODIGO_BCU=? where CODIGO=?";
            
             PreparedStatement ps=cnn.prepareStatement(modifica);
             
             ps.setInt(1, b.getSucursal());
             ps.setString(2, b.getNombre());
             ps.setInt(3, b.getDepartamento().getCodigo());
             ps.setString(4, b.getLocalidad());
             ps.setString(5,b.getDireccion());
             ps.setString(6, b.getTelefono());
             ps.setString(7, b.getCodigoBcu().getCodigo());
             ps.setInt(8, b.getCodigo());
          
             
             
             i=ps.executeUpdate();
             if(i!=0){
             mod=true;
             }
         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
                if(cnn!=null){
                        cnn.close();
         }
     }
         return mod;
    }

    private Integer buscarUltimoCod() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Integer ultCod=null;
        cnn=conexion.Cadena();
        String consulta="Select max(CODIGO) from PERS_BANCOS";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            ultCod=rs.getInt("1");
                
     
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            
         return ultCod+1;
    }

    private CodigoBcu buscaCodigoBcu(Connection cnn, String codigo) {
        CodigoBcu cod=null;
        try {
            String consulta="Select * from PERS_BANCOS_COD_BCU WHERE CODIGO=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, codigo );
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                cod=new CodigoBcu();
                cod.setCodigo(rs.getString("CODIGO"));
                cod.setNombre(rs.getString("NOMBRE"));
            }
            
           
            
           
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
       return cod;
    }
    
    public ArrayList<CodigoBcu> listarCodigoBcu() throws ClassNotFoundException {
        CodigoBcu cod=null;
        Connection cnn=null;
        ArrayList<CodigoBcu> codigos = new ArrayList<>();
        
        try {
            cnn=conexion.Cadena();
            String consulta="Select * from PERS_BANCOS_COD_BCU ORDER BY CODIGO";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                cod=new CodigoBcu();
                cod.setCodigo(rs.getString("CODIGO"));
                cod.setNombre(rs.getString("NOMBRE"));
                codigos.add(cod);
            }
            
           if(cnn!=null){
               cnn.close();
               rs.close();
               ps.close();
           }
            
           
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
       return codigos;
    }
    
    
    
}
