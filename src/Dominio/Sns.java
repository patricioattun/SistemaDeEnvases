
package Dominio;


public class Sns {
    private Integer codigo;
    private String descripcion; //longitud:50
    private Double fonasa;
    private Double caja;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getFonasa() {
        return fonasa;
    }

    public void setFonasa(Double fonasa) {
        this.fonasa = fonasa;
    }

    public Double getCaja() {
        return caja;
    }

    public void setCaja(Double caja) {
        this.caja = caja;
    }
    
    public String toString()
    {
     return codigo.toString() + " - " + descripcion;
    }
    
}
