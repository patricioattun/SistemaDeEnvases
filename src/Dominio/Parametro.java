
package Dominio;

public class Parametro {
    private String nombre;
    private String valor;
    private String valorNuevo;
    private Integer modificable;

    public Integer getModificable() {
        return modificable;
    }

    public void setModificable(Integer modificable) {
        this.modificable = modificable;
    }
    
    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
