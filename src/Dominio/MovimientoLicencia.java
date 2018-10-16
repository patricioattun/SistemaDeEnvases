
package Dominio;

import java.util.Date;


public class MovimientoLicencia {
    private Integer codigo;
    private Date fechaIni;
    private Date fechaFin;
    private Integer saldoAño;
    private Integer diasTomados;
    private Integer saldoPos;
    private Date fechaMovimiento;
    private Integer añoSaldo;
    private Funcionario funcionario;
    private Integer tipoLic;
    private Integer codMovLic;
    private Integer referencia;
    private Long marcaId;

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }
    
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

    
    public Integer getTipoLic() {
        return tipoLic;
    }

    public void setTipoLic(Integer tipoLic) {
        this.tipoLic = tipoLic;
    }

    public Integer getSaldoPos() {
        return saldoPos;
    }

    public void setSaldoPos(Integer saldoPos) {
        this.saldoPos = saldoPos;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    public Integer getSaldoAño() {
        return saldoAño;
    }

    public void setSaldoAño(Integer saldoAño) {
        this.saldoAño = saldoAño;
    }

    public Integer getDiasTomados() {
        return diasTomados;
    }

    public void setDiasTomados(Integer diasTomados) {
        this.diasTomados = diasTomados;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Integer getAñoSaldo() {
        return añoSaldo;
    }

    public void setAñoSaldo(Integer añoSaldo) {
        this.añoSaldo = añoSaldo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return this.añoSaldo.toString();
    }
        
    
    
}
