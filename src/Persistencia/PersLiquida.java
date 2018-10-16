
package Persistencia;

import Dominio.Funcionario;
import Dominio.Ingreso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PersLiquida {
    
    
    
    
    public ArrayList<Funcionario> cargoPromedioHorasExtras(Date fechaLiq)throws BDExcepcion{
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaLiq);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.MONTH, 0);
            cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)-1);
            Date fechaIni=cal.getTime();

            cal.set(Calendar.DAY_OF_MONTH, 31);
            cal.set(Calendar.MONTH, 11);
            Date fechaFin=cal.getTime();
            
            String ini=this.convertirFecha(fechaIni);
            String fin=this.convertirFecha(fechaFin);
            
            String consulta="";
            Connection cnn=null;
            cnn=Conexion.Cadena();
            cnn.setAutoCommit(false); 
            Funcionario f;
            ArrayList<Funcionario> lista=new ArrayList<>();
            PreparedStatement ps=null;
            
            consulta="Select CODIGO, APELLIDO1,APELLIDO2,NOMBRE1,NOMBRE2 from PERS_FUNCIONARIOS order by codigo";
            ps=cnn.prepareStatement(consulta);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()){
                f=new Funcionario();
                f.setCodFunc(rs.getInt("CODIGO"));
                f.setNombre1(rs.getString("NOMBRE1").trim());
                f.setNombre2(rs.getString("NOMBRE2")); 
                f.setApellido1(rs.getString("APELLIDO1").trim());
                f.setApellido2(rs.getString("APELLIDO2"));
                ArrayList<Ingreso> ingresos =this.cargoHorasExtras(rs.getInt("CODIGO"), ini, fin, cnn);
                if(ingresos.size()>0){
                    f.setIngresos(ingresos);
                    lista.add(f);
                }
            }
            cnn.commit();
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return lista;
        } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
        }
    }
    
    
    
    public ArrayList<Ingreso> cargoHorasExtras(Integer cod,String ini, String fin,Connection cnn) throws BDExcepcion{

        
        
        ArrayList<Ingreso> ingresos=new ArrayList<>();
        
        Ingreso i=new Ingreso();
        i.setCod(31);
        i.setEnPers(24);
        double cant24=this.cargoCantidadHoras(cod,31,ini,fin,cnn);
        i.setCantidad(cant24);
        cant24=cant24/11;
        i.setImporte(cant24);
        ingresos.add(i);
        
        i=new Ingreso();
        i.setCod(32);
        i.setEnPers(46);
        double cant46=this.cargoCantidadHoras(cod,32,ini,fin,cnn);
        i.setCantidad(cant46);
        cant46=cant46/11;
        i.setImporte(cant46);
        ingresos.add(i);
       
        i=new Ingreso();
        i.setCod(33);
        i.setEnPers(47); 
        double cant47=this.cargoCantidadHoras(cod,33,ini,fin,cnn);
        i.setCantidad(cant47);
        cant47=cant47/11;
        i.setImporte(cant47);
        ingresos.add(i);
        
        i=new Ingreso();
        i.setCod(61);
        i.setEnPers(81); 
        double cant81=this.cargoCantidadHoras(cod,61,ini,fin,cnn);
        i.setCantidad(cant81);
        cant81=cant81/11;
        i.setImporte(cant81);
        ingresos.add(i);
        
        i=new Ingreso();
        i.setCod(62);
        i.setEnPers(82);
        double cant82=this.cargoCantidadHoras(cod,62,ini,fin,cnn);
        i.setCantidad(cant82); 
        cant82=cant82/11;
        i.setImporte(cant82);
        ingresos.add(i);
        
        i=new Ingreso();
        i.setCod(63);
        i.setEnPers(83);
        double cant83=this.cargoCantidadHoras(cod,63,ini,fin,cnn);
        i.setCantidad(cant83);
        cant83=cant83/11;
        i.setImporte(cant83);
        ingresos.add(i);
        
        if(cant24==0 && cant46==0 && cant47==0 && cant81==0 && cant82==0 && cant83==0){
            ingresos.clear();
        }
        
        return ingresos;
        
    }

    private double cargoCantidadHoras(int codFunc, int cod, String ini, String fin, Connection cnn)throws BDExcepcion {
        try {
            double retorno=0;
            String consulta="select sum(cantidad) as total from pers_hist_liquidaciones where ((cod_func=?) and (cod_mov=?) and (fecha between ? and ?) and tipoliq=1)";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            ps.setInt(2, cod);
            ps.setString(3, ini);
            ps.setString(4, fin);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                retorno=rs.getDouble("total");
            }
           
            ps.close();
            rs.close();
            
            return retorno;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
     private ArrayList<Ingreso> cargoCantidadHoras1(int codFunc, String ini, String fin, Connection cnn)throws BDExcepcion {
        try {
        
            Ingreso i;
            double cant24=0, cant46=0, cant47=0, cant81=0,cant82=0,cant83=0;
         
            ArrayList<Ingreso> ingresos = new ArrayList<>();
            String consulta="select cod_func,\n" +
            "(select sum(cantidad) from pers_hist_liquidaciones where ((cod_func=? and cod_mov=31) and (fecha between ? and ?) and tipoliq=1)) as col31,\n" +
            "(select sum(cantidad) from pers_hist_liquidaciones where ((cod_func=? and cod_mov=32) and (fecha between ? and ?) and tipoliq=1)) as col32,\n" +
            "(select sum(cantidad) from pers_hist_liquidaciones where ((cod_func=? and cod_mov=33) and (fecha between ? and ?) and tipoliq=1)) as col33,\n" +
            "(select sum(cantidad) from pers_hist_liquidaciones where ((cod_func=? and cod_mov=61) and (fecha between ? and ?) and tipoliq=1)) as col61,\n" +
            "(select sum(cantidad) from pers_hist_liquidaciones where ((cod_func=? and cod_mov=62) and (fecha between ? and ?) and tipoliq=1)) as col62,\n" +
            "(select sum(cantidad) from pers_hist_liquidaciones where ((cod_func=? and cod_mov=63) and (fecha between ? and ?) and tipoliq=1)) as col63\n" +
            "\n" +
            "from pers_hist_liquidaciones a where ((cod_func=?) and (fecha between ? and ?) and tipoliq=1) group by cod_func";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            ps.setString(2, ini);
            ps.setString(3, fin);
            ps.setInt(4, codFunc);
            ps.setString(5, ini);
            ps.setString(6, fin);
            ps.setInt(7, codFunc);
            ps.setString(8, ini);
            ps.setString(9, fin);
            ps.setInt(10, codFunc);
            ps.setString(11, ini);
            ps.setString(12, fin);
            ps.setInt(13, codFunc);
            ps.setString(14, ini);
            ps.setString(15, fin);
            ps.setInt(16, codFunc);
            ps.setString(17, ini);
            ps.setString(18, fin);
            ps.setInt(19, codFunc);
            ps.setString(20, ini);
            ps.setString(21, fin);
            
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                i=new Ingreso();
                i.setCod(31);
                i.setEnPers(24); 
                cant24=rs.getDouble("col31");
                i.setCantidad(cant24);
                i.setImporte(i.getCantidad()/11);
                ingresos.add(i);
                
                i=new Ingreso();
                i.setCod(32);
                i.setEnPers(46); 
                cant46=rs.getDouble("col32");
                i.setCantidad(cant46);
                i.setImporte(i.getCantidad()/11);
                ingresos.add(i);
                
                i=new Ingreso();
                i.setCod(33);
                i.setEnPers(47); 
                cant47=rs.getDouble("col33");
                i.setCantidad(cant47);
                i.setImporte(i.getCantidad()/11);
                ingresos.add(i);
                
                i=new Ingreso();
                i.setCod(61);
                i.setEnPers(81); 
                cant81=rs.getDouble("col61");
                i.setCantidad(cant81);
                i.setImporte(i.getCantidad()/11);
                ingresos.add(i);
                
                i=new Ingreso();
                i.setCod(62);
                i.setEnPers(82); 
                cant82=rs.getDouble("col62");
                i.setCantidad(cant82);
                i.setImporte(i.getCantidad()/11);
                ingresos.add(i);
                
                i=new Ingreso();
                i.setCod(63);
                i.setEnPers(83); 
                cant83=rs.getDouble("col63");
                i.setCantidad(cant83);
                i.setImporte(i.getCantidad()/11);
                ingresos.add(i);
            }
           
            ps.close();
            rs.close();
            
            if(cant24==0 && cant46==0 && cant47==0 && cant81==0 && cant82==0 && cant83==0){
            ingresos.clear();
            }
            
            return ingresos;
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    private String convertirFecha(Date fecha){
        String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
        }
    return str;
  }

   
}
