
package Dominio;

import java.util.ArrayList;


public class IngresoMarca {
    private Long id;
    private Integer codFunc;
    private Double cantidad;
    private Integer codigo;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(Integer codFunc) {
        this.codFunc = codFunc;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

   
}
