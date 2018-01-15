
package Persistencia;

import Dominio.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PersistenciaUsuario {
    
    private Conexion conexion;
  
    public Usuario login(String usuario, String contrasenia) throws SQLException, ClassNotFoundException {
       Connection cnn=null;
       Usuario us=null;
       try{
           cnn=conexion.Cadena();
           String consulta="Select * from PERS_USUARIOS where TRIM (USUARIO)=? and TRIM(CONTRASEÑA)=?";
           PreparedStatement ps=cnn.prepareStatement(consulta);
           ps.setString(1, usuario);
           ps.setString(2, contrasenia);
           ResultSet rs=ps.executeQuery();
           if(rs.next()){
               us=new Usuario(rs.getString("USUARIO").trim(),rs.getString("CONTRASEÑA").trim());
               
              // us.getPermiso().cargaPermiso(rs.getInt("PERMISO"));
            }        
        }
       catch(SQLException e1){
           throw new SQLException(e1.getMessage());
       }
       finally{
               if(cnn!=null){
                   cnn.close();
              return us;
           }
       }
       return us;
    }
}
