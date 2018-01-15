
package Dominio;

import java.util.Calendar;
import java.util.Date;


public class Licencia {
    
    private Integer id;
    private Integer año;
    private Date fechaIni;
    private Date fechaFin;
    private Integer saldo;
    private Integer diasGenerados;
    private Funcionario funcionario;
    private Date fechaGen;
    private Integer diasDescuento;
    private boolean cambio=false;

    public Integer getDiasDescuento() {
        return diasDescuento;
    }

    public void setDiasDescuento(Integer diasDescuento) {
        this.diasDescuento = diasDescuento;
    }

    
    
    public boolean getCambio() {
        return cambio;
    }

    public void setCambio(boolean cambio) {
        this.cambio = cambio;
    }

    public Date getFechaGen() {
        return fechaGen;
    }

    public void setFechaGen(Date fechaGenerado) {
        this.fechaGen= fechaGenerado;
    }
    
    public Integer getDiasGenerados() {
        return diasGenerados;
    }

    public void setDiasGenerados(Integer diasGenerados) {
        this.diasGenerados = diasGenerados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    @Override
    public String toString(){
        return this.getAño().toString();
    }
    
    public String obtenerAño(){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaGen);
        String t=String.valueOf(calendar.get(Calendar.YEAR));
        return t;
    }
}
