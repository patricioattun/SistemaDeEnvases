
package Dominio;

import java.util.ArrayList;

public class Codigo {
    private Integer cod;
    private String descripcion;
    private Integer valor;
    private Integer tipo;
    private ArrayList<String> fecha;
    private Integer tipoUnidad;
    private Integer tipoValor;

    public Integer getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(Integer tipoValor) {
        this.tipoValor = tipoValor;
    }
    
    
    
    
    public Integer getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(Integer tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }
  
    public Codigo() {
        this.fecha = new ArrayList<>();
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public ArrayList<String> getFecha() {
        return fecha;
    }

    public void setFecha(ArrayList<String> fecha) {
        this.fecha = fecha;
    }

    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.cod+" - "+ this.descripcion;
    }
    
    
}
