
package Dominio;



public class EstadoCivil {
    private Integer codigo;
    private String nombre;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String toString()
    {
     return codigo.toString() + " - " + nombre;
    }
    
    
}
