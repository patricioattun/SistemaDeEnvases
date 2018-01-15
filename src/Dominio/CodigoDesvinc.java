
package Dominio;

public class CodigoDesvinc {
    private Integer id;
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripción) {
        this.descripcion = descripción;
    }

    public CodigoDesvinc() {
     
    }
    
    

    @Override
    public String toString() {
        return  id + " - " + descripcion;
    }
    
    
}
