
package Dominio;

import Persistencia.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Permiso {
    private Integer codigo;
    private String perfil; //Longitud: 20
    private Conexion conexion;
    
    //Crear tabla PERS_PERMISOS_USUARIOS

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }


    
    public Permiso cargaPermiso(Integer id) throws SQLException, ClassNotFoundException {
       Connection cnn=null;
         Permiso per=null;
       try{
         
           cnn=conexion.Cadena();
           String consulta="Select * from PERS_PERMISOS where CODIGO=?";
           PreparedStatement ps=cnn.prepareStatement(consulta);
           ps.setInt(1, id);

           ResultSet rs=ps.executeQuery();
           if(rs.next()){
               per=new Permiso();
               per.setCodigo(rs.getInt("USUARIO"));
               per.setPerfil(rs.getString("PERFIL").trim());
                    
           }                        
        }
       catch(SQLException e1){
           throw new SQLException(e1.getMessage());
       }
       finally{
               if(cnn!=null){
                   cnn.close();
              return per;
           }
       }
        return per;
           
    }
}
