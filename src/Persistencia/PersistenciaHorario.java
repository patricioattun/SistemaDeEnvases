
package Persistencia;

import Dominio.Horario;
import Logica.LogUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;


public class PersistenciaHorario {
     private Conexion conexion;
     private ArrayList<Horario> horarios;
     

 
    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }
  
     //Carga combo horario
     public ArrayList<Horario> cargaComboHorarios() throws SQLException, ClassNotFoundException{
        horarios=new ArrayList<>();
        Connection cnn=null;
        Horario horario=null; 
        cnn=conexion.Cadena();
        
        String consulta="Select * from PERS_HORARIOS_NUEVOS order by DESCRIPCION";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            horario=new Horario();
            horario.setCodigo(rs.getInt("CODIGO"));
            horario.setHoraEntrada(rs.getTime("HORAENTRADA"));
            horario.setHoraSalida(rs.getTime("HORASALIDA"));
            horario.setDescripcion(rs.getString("DESCRIPCION"));
            //horario.setCodDias(rs.getInt("CODIGO_DIAS"));
            
            if(rs.getTime("HORASALIDAINTER")!=null && rs.getTime("HORAENTRADAINTER")!=null){
                   horario.setHoraSalidaInter(rs.getTime("HORASALIDAINTER"));
                   horario.setHoraEntradaInter(rs.getTime("HORAENTRADAINTER"));
                   horarios.add(horario);
            }
            else{
                horario.setHoraSalidaInter(null);
                horario.setHoraEntradaInter(null);
                horarios.add(horario);
            }
            }        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            
         return horarios;
    }
    
     //alta horario
    public Boolean altaHorario(Horario h) throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        Boolean alta=false;
        Integer i=-1;
        Time t;
        Integer ultCod=null;
         try{
             cnn=conexion.Cadena();
             String insert="insert into PERS_HORARIOS_NUEVOS(CODIGO,HORAENTRADA, HORASALIDAINTER, HORAENTRADAINTER, HORASALIDA, DESCRIPCION)"+"values(?,?,?,?,?,?)";
             PreparedStatement ps=cnn.prepareStatement(insert);
             ultCod=this.buscarCodigo();
             ps.setInt(1, ultCod);
             ps.setTime(2, h.getHoraEntrada());
             ps.setTime(3, h.getHoraSalidaInter());
             ps.setTime(4, h.getHoraEntradaInter());
             ps.setTime(5, h.getHoraSalida());
             ps.setString(6, h.getDescripcion());
             //ps.setInt(6, h.getCodDias());
             
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

    public Horario buscarHorario(Integer codigo) {
         Horario h=null;
         Boolean esta=false;
         Integer i=0;
         while(i<this.horarios.size()&& !esta){
             if(horarios.get(i).getCodigo().equals(codigo)){
                 h=horarios.get(i);
                 esta=true;
             }
             i++;
         }
         return h; 
    }

//ELIMINAR HORARIO
    public boolean eliminarHorario(Integer cod) throws SQLException {
         Connection cnn=null;
        Boolean baja=false;
        Integer i=0;
        Time t;
         try{
             cnn=conexion.Cadena();
             String delete="delete from PERS_HORARIOS_NUEVOS where CODIGO=?";
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

    public Boolean modificarHorario(Horario h) throws SQLException {
       Connection cnn=null;
        Boolean mod=false;
        Integer i=0;
        Time t;
         try{
             cnn=conexion.Cadena();
             String modifica="UPDATE PERS_HORARIOS_NUEVOS set HORAENTRADA=?, HORASALIDAINTER=?, HORAENTRADAINTER=?, HORASALIDA=?,DESCRIPCION=?  where CODIGO=?";
            
             PreparedStatement ps=cnn.prepareStatement(modifica);
              
             
             ps.setTime(1, h.getHoraEntrada());
             ps.setTime(2, h.getHoraSalidaInter());
             ps.setTime(3, h.getHoraEntradaInter());
             ps.setTime(4, h.getHoraSalida());
             ps.setString(5, h.getDescripcion());
             //ps.setInt(6, h.getCodDias());
             ps.setInt(6, h.getCodigo());
             
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

    private Integer buscarCodigo() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Integer ultCod=null;
        cnn=conexion.Cadena();
        String consulta="Select max(CODIGO) from PERS_HORARIOS_NUEVOS";
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
            
         return ultCod+1;  }
     
}
