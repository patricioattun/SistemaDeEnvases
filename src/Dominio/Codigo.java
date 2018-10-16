
package Dominio;

import java.util.ArrayList;

public class Codigo {
    private Integer cod;
    private String descripcion;
    private Integer tipoValor;
    private boolean ingresoManual;
    private boolean debe;
    private boolean haber;
    private boolean imponibleBps;
    private boolean imponibleIrpf;
    private boolean acumula;
    private boolean fijo;
    private double alcance;
    private Integer valor;
    private Integer tipo;
    private Long retencionFija;
    private Integer grupo;
    private ArrayList<String> fecha;
    private Integer tipoUnidad;

    public boolean isIngresoManual() {
        return ingresoManual;
    }

    public void setIngresoManual(boolean ingresoManual) {
        this.ingresoManual = ingresoManual;
    }

    public boolean isDebe() {
        return debe;
    }

    public void setDebe(boolean debe) {
        this.debe = debe;
    }

    public boolean isHaber() {
        return haber;
    }

    public void setHaber(boolean haber) {
        this.haber = haber;
    }

    public boolean isImponibleBps() {
        return imponibleBps;
    }

    public void setImponibleBps(boolean imponibleBps) {
        this.imponibleBps = imponibleBps;
    }

    public boolean isImponibleIrpf() {
        return imponibleIrpf;
    }

    public void setImponibleIrpf(boolean imponibleIrpf) {
        this.imponibleIrpf = imponibleIrpf;
    }

    public boolean isAcumula() {
        return acumula;
    }

    public void setAcumula(boolean acumula) {
        this.acumula = acumula;
    }

    public boolean isFijo() {
        return fijo;
    }

    public void setFijo(boolean fijo) {
        this.fijo = fijo;
    }

    public double getAlcance() {
        return alcance;
    }

    public void setAlcance(double alcance) {
        this.alcance = alcance;
    }

    public Long getRetencionFija() {
        return retencionFija;
    }

    public void setRetencionFija(Long retencionFija) {
        this.retencionFija = retencionFija;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }
    

    
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
