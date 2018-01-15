
package Dominio;

import Persistencia.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Horario {
    
    private Integer codigo;
    private Time horaEntrada;
    private Time horaSalidaInter;
    private Time horaEntradaInter;
    private Time horaSalida;
    private String descripcion;
    
    
    
    //analizar bien sobre como manejar los horarios
    //horarios en Montevideo y en el Interior
    //horarios con hora de ingreso y salida
    
//<editor-fold defaultstate="collapsed" desc="Propiedades">
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalidaInter() {
        return horaSalidaInter;
    }

    public void setHoraSalidaInter(Time horaSalidaInter) {
        this.horaSalidaInter = horaSalidaInter;
    }

    public Time getHoraEntradaInter() {
        return horaEntradaInter;
    }

    public void setHoraEntradaInter(Time horaEntradaInter) {
        this.horaEntradaInter = horaEntradaInter;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }






    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    



    //</editor-fold>

    public Horario(Integer cod,Time horaEntrada, Time horaSalidaInter, Time horaEntradaInter, Time horaSalida, String descripcion) {
        this.codigo=cod;
        this.horaEntrada = horaEntrada;
        this.horaSalidaInter = horaSalidaInter;
        this.horaEntradaInter = horaEntradaInter;
        this.horaSalida = horaSalida;
        this.descripcion = descripcion;
     
    }

    public Horario() {
    }
    
        public Horario(Time horaEntrada, Time horaSalidaInter, Time horaEntradaInter, Time horaSalida, String descripcion) {
      
        this.horaEntrada = horaEntrada;
        this.horaSalidaInter = horaSalidaInter;
        this.horaEntradaInter = horaEntradaInter;
        this.horaSalida = horaSalida;
        this.descripcion = descripcion;
        ;
    }
    
    
      public String toString()
    {
        if(this.convertir(horaSalidaInter).equals("00:00:00") && this.convertir(horaEntradaInter).equals("00:00:00")){
             return codigo.toString()+"-"+ horaEntrada.toString() +"|"+ horaSalida.toString() + " - " +descripcion ;

             }
        else{
            return codigo.toString()+"-"+ horaEntrada.toString() + "|" + horaSalidaInter.toString() + "|" + horaEntradaInter.toString() +"|"+ horaSalida.toString() + " - " +descripcion ;
   
                   }
    } 
      
   public String convertir(Time hora){
       String s=String.valueOf(hora);
       return s;
   }
}
