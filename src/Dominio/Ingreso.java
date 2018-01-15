
package Dominio;

import java.util.Date;


public class Ingreso {
    private Date fecha;
    private Integer codFunc;
    private Codigo codMov;
    private Double cantidad;
    private Double Importe;
    private Integer EnPers;
    private Funcionario func;

    public Funcionario getFunc() {
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
    }
    
    

    public Integer getEnPers() {
        return EnPers;
    }

    public void setEnPers(Integer EnPers) {
        this.EnPers = EnPers;
    }
    
    

    public Double getImporte() {
        return Importe;
    }

    public void setImporte(Double Importe) {
        this.Importe = Importe;
    }
        
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(Integer codFunc) {
        this.codFunc = codFunc;
    }

    public Codigo getCodMov() {
        return codMov;
    }

    public void setCodMov(Codigo codMov) {
        this.codMov = codMov;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return this.codMov.toString()+"\n - Funcionario Nº: "+this.getCodFunc();
    }
    
    
    
}
