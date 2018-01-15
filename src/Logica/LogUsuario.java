
package Logica;


import Dominio.Usuario;
import Persistencia.PersistenciaUsuario;
import java.sql.SQLException;

public class LogUsuario {

    private PersistenciaUsuario pers;
     private static LogUsuario instancia=null;
     
    public LogUsuario() {
        this.pers=new PersistenciaUsuario();
    }
//Singleton
     public static LogUsuario getInstancia() {
        if(instancia==null){
            instancia = new LogUsuario();
        }
        return instancia;
     }
    
         //Login
     public Usuario login(String usuario, String contrasenia) throws SQLException, ClassNotFoundException{
           return pers.login(usuario, contrasenia);
      
         }
}
