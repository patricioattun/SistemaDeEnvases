
package Dominio;

import java.util.Date;


public class LicenciaAdelantada {
    private Integer codigo;
    private Integer año;
    private Date fechaIni;
    private Date fechaFin;
    private Integer diasPedidos;
    private Date fechaMov;
    private Funcionario funcionario;
    private Integer codMovLic;
    private Integer referencia;

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }
    
    
    public Integer getCodMovLic() {
        return codMovLic;
    }

    public void setCodMovLic(Integer codMovLic) {
        this.codMovLic = codMovLic;
    }
    
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDiasPedidos() {
        return diasPedidos;
    }

    public void setDiasPedidos(Integer diasPedidos) {
        this.diasPedidos = diasPedidos;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

}
