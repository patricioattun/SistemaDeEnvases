
package Persistencia;

import Dominio.Permiso;
import Dominio.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenciaUsuario {
    
    private Conexion conexion;
  
    public Usuario login(String usuario, String contrasenia) throws SQLException, ClassNotFoundException {
       Connection cnn=null;
       Usuario us=null;
       try{
           cnn=conexion.Cadena();
           cnn.setAutoCommit(false);
           String consulta="Select * from PERS_USUARIOS where TRIM (USUARIO)=? and TRIM(CONTRASEÑA)=?";
           PreparedStatement ps=cnn.prepareStatement(consulta);
           ps.setString(1, usuario);
           ps.setString(2, contrasenia);
           ResultSet rs=ps.executeQuery();
           if(rs.next()){
               us=new Usuario(rs.getString("USUARIO").trim(),rs.getString("CONTRASEÑA").trim(),this.cargoPermiso(rs.getInt("PERMISO"),cnn)); 
            }  
           cnn.commit();
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

    private Permiso cargoPermiso(int id,Connection cnn) {
     Permiso per=null;
        try {
            String consulta="Select * from PERS_PERMISOS where PERMISO=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, id);
            
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                per=new Permiso();
                per.setPermiso(rs.getInt("PERMISO"));
                per.setNombre(rs.getString("NOMBRE").trim());
                
            }
              
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        return per;
    }
}
