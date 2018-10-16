
package Dominio;

import java.util.Date;

public class irpfDetallado {
    private Integer codFunc;
    private Date fecha; 
    private Integer tipo;
    private Integer ordinal;
    private String leyenda;
    private double importe;
    private Funcionario func;

    public irpfDetallado(Integer codFunc, Date fecha, Integer tipo, Integer ordinal, String leyenda, double importe) {
        this.codFunc = codFunc;
        this.fecha = fecha;
        this.tipo = tipo;
        this.ordinal = ordinal;
        this.leyenda = leyenda;
        this.importe = importe;
    }

    public irpfDetallado() {
       
    }

    public Integer getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(Integer codFunc) {
        this.codFunc = codFunc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Funcionario getFunc() {
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
    }
    
    
    
    
    
}
