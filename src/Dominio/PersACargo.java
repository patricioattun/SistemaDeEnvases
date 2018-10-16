
package Dominio;

import java.util.Date;


public class PersACargo {
    private Integer codFunc;
    private Integer ordinal;
    private Date fechaNac;
    private char relacion;
    private Integer pjeDist;
    private Integer discapacidad;

    public Integer getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(Integer codFunc) {
        this.codFunc = codFunc;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public char getRelacion() {
        return relacion;
    }

    public void setRelacion(char relacion) {
        this.relacion = relacion;
    }

    public Integer getPjeDist() {
        return pjeDist;
    }

    public void setPjeDist(Integer pjeDist) {
        this.pjeDist = pjeDist;
    }

    public Integer getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(Integer discapacidad) {
        this.discapacidad = discapacidad;
    }
    
    
    
}
