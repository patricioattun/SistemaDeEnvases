
package Dominio;

public class Retencion {
    private Funcionario func;
    private Codigo cod;
    private Integer tipo;
    private Double importe;
    private Double porcentaje;
    private Integer sueldo;
    private Integer otros;
    private Integer activa;

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
    }
    
    

    public Funcionario getFunc() {
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
    }

    public Codigo getCod() {
        return cod;
    }

    public void setCod(Codigo cod) {
        this.cod = cod;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getSueldo() {
        return sueldo;
    }

    public void setSueldo(Integer sueldo) {
        this.sueldo = sueldo;
    }

    public Integer getOtros() {
        return otros;
    }

    public void setOtros(Integer otros) {
        this.otros = otros;
    }
    
    
    
}
