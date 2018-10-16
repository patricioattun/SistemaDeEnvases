
package Dominio;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Marca {
    private Long id;
    private Integer funCod;
    private Timestamp marcaFecha;
    private Integer supervisado;
    private String responsable;
    private Date fecha;
    private String tipo;
    private Date fechaUso;
    private String horaUso;
    private Integer incongruencia;
    private Date procesado;
    private Integer editada;
    private String observacion;

    public Integer getEditada() {
        return editada;
    }

    public void setEditada(Integer editada) {
        this.editada = editada;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    
    
    
    
    public Date getProcesado() {
        return procesado;
    }

    public void setProcesado(Date procesado) {
        this.procesado = procesado;
    }
            
    @Override
    public String toString() {
        return  "Fecha:" + this.formateoFecha(fechaUso) + ", Hora:" + horaUso;
    }

    public Integer getIncongruencia() {
        return incongruencia;
    }

    public void setIncongruencia(Integer incongruencia) {
        this.incongruencia = incongruencia;
    }

    
    
    public String getHoraUso() {
        return horaUso;
    }

    public void setHoraUso(String horaUso) {
        this.horaUso = horaUso;
    }

    
    
    public Date getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(Date fechaUso) {
        this.fechaUso = fechaUso;
    }

    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
         
    
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSupervisado() {
        return supervisado;
    }

    public void setSupervisado(Integer supervisado) {
        this.supervisado = supervisado;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
  
    public Integer getFunCod() {
        return funCod;
    }

    public void setFunCod(Integer funCod) {
        this.funCod = funCod;
    }

    public Timestamp getMarcaFecha() {
        return marcaFecha;
    }

    public void setMarcaFecha(Timestamp marcaFecha) {
        this.marcaFecha = marcaFecha;
    }
    

    private String formateoFecha(Date hoy){
      String retorno="";
      if(hoy!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
         retorno=formateador.format(hoy);
      }
      return retorno;
     }  
    
    private String formateoHora(Date hoy){
      String retorno="";
      if(hoy!=null){   
        SimpleDateFormat formateador = new SimpleDateFormat("HH/mm/ss");
         retorno=formateador.format(hoy);
      }
      return retorno;
     }  
    
}
