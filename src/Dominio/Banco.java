
package Dominio;



public class Banco {
    private Integer codigo;
    private Integer sucursal;
    private String nombre; //Longitud: 35
    private Departamento departamento;
    private String localidad; //Longitud: 25
    private String direccion;//Longitud: 50
    private String telefono;//Longitud: 20
    private CodigoBcu codigoBcu;
    private String codBcu;
     //<editor-fold defaultstate="collapsed" desc="Propiedades">

    public String getCodBcu() {
        return codBcu;
    }

    public void setCodBcu(String codBcu) {
        this.codBcu = codBcu;
    }

    
    public CodigoBcu getCodigoBcu() {
        return codigoBcu;
    }

    public void setCodigoBcu(CodigoBcu codigoBcu) {
        this.codigoBcu = codigoBcu;
    }

    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
      
    
    
    public Integer getSucursal() {
        return sucursal;
    }

    public void setSucursal(Integer sucursal) {
        this.sucursal = sucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
 //</editor-fold>
    
   public String toString()
    {
     return sucursal.toString() + " - " + nombre;
    }
    
   
}
