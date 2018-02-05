
package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
            private static Connection cnn=null;
    
    public static Connection Cadena() throws ClassNotFoundException, SQLException{
        
        Connection cnn=null;
        
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        cnn=DriverManager.getConnection("jdbc:db2://10.136.1.3:50000/ACE1","admin","desadb2");
        return cnn;
    }
    
    public static Connection CadenaPos() throws ClassNotFoundException, SQLException{
        
        Connection cnn=null;
        
        Class.forName("org.postgresql.Driver");
        //String str="ID=postgres;Password=almeja;Host=localhost;Port=5432;Database=Tripaliare;Pooling=true;Min Pool Size=0;Max Pool Size=100;Connection Lifetime=0;";
        cnn=DriverManager.getConnection("jdbc:postgresql://10.136.1.3:5432/postgres","postgres","almeja");
        
        return cnn;
    
    }
}
