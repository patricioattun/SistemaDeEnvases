
package Dominio;

import Persistencia.Conexion;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sucursal {
    private Integer numero;
    private String nombre; //Longitud:50
    private Conexion conexion;
    //Acceder a tabla SUCS_SUCURSALES_ACR

     //<editor-fold defaultstate="collapsed" desc="Propiedades">
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //</editor-fold>
    
    
    
    public ArrayList<Sucursal> cargaComboSucursal() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        Sucursal suc=null; 
        ArrayList<Sucursal> sucursales=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from SUCS_SUCURSALES_ACR";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            suc=new Sucursal();
            suc.setNumero(rs.getInt("NUMERO"));
            suc.setNombre(rs.getString("NOMBRE").trim());
            
            sucursales.add(suc);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return sucursales;
    }
    
     public String toString()
    {
     return numero.toString() + " - " + nombre;
    }
}

