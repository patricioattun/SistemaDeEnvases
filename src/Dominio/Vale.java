
package Dominio;

import java.util.Date;


public class Vale {
    private Date fecha;
    private Integer codFunc;
    private Double cuenta;
    private Double importeVale;
    private String nombre;
    private Date fechaLiq;
    private TipoVale tipo;
    private String usuario;
    private Funcionario f;
    private int enPers;

    public int getEnPers() {
        return enPers;
    }

    public void setEnPers(int enPers) {
        this.enPers = enPers;
    }

      
    public Funcionario getF() {
        return f;
    }

    public void setF(Funcionario f) {
        this.f = f;
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

    public Double getCuenta() {
        return cuenta;
    }

    public void setCuenta(Double cuenta) {
        this.cuenta = cuenta;
    }

    public Double getImporteVale() {
        return importeVale;
    }

    public void setImporteVale(Double importeVale) {
        this.importeVale = importeVale;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaLiq() {
        return fechaLiq;
    }

    public void setFechaLiq(Date fechaLiq) {
        this.fechaLiq = fechaLiq;
    }

    public TipoVale getTipo() {
        return tipo;
    }

    public void setTipo(TipoVale tipo) {
        this.tipo = tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
}
