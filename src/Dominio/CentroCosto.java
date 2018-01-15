
package Dominio;

import Persistencia.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CentroCosto {
    private Integer codigo;
    private String nombre;
    private Conexion conexion;
   //<editor-fold defaultstate="collapsed" desc="Propiedades">
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //</editor-fold>
    
        public String toString()
    {
     return codigo.toString() + " - " + nombre;
    }
        
}
