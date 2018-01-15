
package sistema.de.envases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
            private static Connection cnn=null;
    
    public static Connection Cadena() throws ClassNotFoundException, SQLException{
        
        Connection cnn=null;
        
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        cnn=DriverManager.getConnection("jdbc:db2://10.136.1.2:50000/ACE1","admin","desadb2");

        return cnn;
    
    }
    
    
}
