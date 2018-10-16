
package Dominio;

import Dominio.Codigo;
import Dominio.Declaracion;
import Dominio.Funcionario;
import Dominio.Ingreso;
import Dominio.Irpf;
import Dominio.PersACargo;
import Dominio.ResumenLiquidacion;
import Dominio.Retencion;
import Dominio.irpfDetallado;
import Persistencia.BDExcepcion;
import Persistencia.Conexion;
import Persistencia.PersistenciaFuncionario;
import Persistencia.PersistenciaLiquidacion;
import Presentacion.Liquidaciones.InternalLiquidaSueldo;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static java.time.temporal.ChronoUnit.*;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;


public class Liquidacion {
    
    private Funcionario f;
    private Integer tipoLiq;//1 Sueldo //4 Vacacional
    private Double cantidad;
    private Double valorUnitario;
    private Double importe;
    private Date fecha;
    private Date fechaLiq;
    private Codigo codigo;
    private Integer cod;
    private double importeNoRet;
    private Timestamp fechaPrelacion;
    
    private ArrayList<irpfDetallado> irpfDetallado;
    
    public Liquidacion(Funcionario f, Integer tipoLiq, Codigo codigo, Double cantidad, Double valorUnitario, Double importe, Date fecha,double importeNoRet) {
        this.f = f;
        this.tipoLiq = tipoLiq;
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.importe = importe;
        this.fecha = fecha;
        this.importeNoRet = importeNoRet;
      
    }

    public Liquidacion() {
    }

    
    public Timestamp getFechaPrelacion() {
        return fechaPrelacion;
    }

    public void setFechaPrelacion(Timestamp fechaPrelacion) {
        this.fechaPrelacion = fechaPrelacion;
    }
    
    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }
    
   
    
    

    public double getImporteNoRet() {
        return importeNoRet;
    }

    public void setImporteNoRet(double importeNoRet) {
        this.importeNoRet = importeNoRet;
    }

    
    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }
        
    public Funcionario getF() {
        return f;
    }

    public void setF(Funcionario f) {
        this.f = f;
    }

    public Integer getTipoLiq() {
        return tipoLiq;
    }

    public void setTipoLiq(Integer tipoLiq) {
        this.tipoLiq = tipoLiq;
    }

   
    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaLiq() {
        return fechaLiq;
    }

    public void setFechaLiq(Date fechaLiq) {
        this.fechaLiq = fechaLiq;
    }

    public ArrayList<irpfDetallado> getIrpfDetallado() {
        return irpfDetallado;
    }

    public void setIrpfDetallado(ArrayList<irpfDetallado> irpfDetallado) {
        this.irpfDetallado = irpfDetallado;
    }
       
    
    
        

    
    
}
