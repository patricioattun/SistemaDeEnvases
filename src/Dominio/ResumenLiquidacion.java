
package Dominio;

import java.util.ArrayList;
import java.util.Date;


public class ResumenLiquidacion {
    private Funcionario funcionario;
    private double totalHaberes;
    private double totalDescuentos;
    private double liquidoLegal;
    private double liquidoMinimo;
    private double liquidoFinal;
    private Date fecha;
    private double totalHaberesBps;
    private double totalHaberesIrpf;
    private double totalNoRet;
    private double tipoLiq;
    private CentroCosto centro;
    
    //Atributos solo usable para carga de totales generales para PDF
    private double dipaico;
    private double casenpace;
    private double fonasa;
    private double frl;
    private double redondeos;
    private double irpf;
    private Integer totalFunc;
    
    private ArrayList<Liquidacion> liquidaciones;

    public ResumenLiquidacion() {
        
    }

    public ArrayList<Liquidacion> getLiquidaciones() {
        return liquidaciones;
    }

    public void setLiquidaciones(ArrayList<Liquidacion> liquidaciones) {
        this.liquidaciones = liquidaciones;
    }

    
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public double getTotalHaberes() {
        return totalHaberes;
    }

    public void setTotalHaberes(double totalHaberes) {
        this.totalHaberes = totalHaberes;
    }

    public double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public double getLiquidoLegal() {
        return liquidoLegal;
    }

    public void setLiquidoLegal(double liquidoLegal) {
        this.liquidoLegal = liquidoLegal;
    }

    public double getLiquidoMinimo() {
        return liquidoMinimo;
    }

    public void setLiquidoMinimo(double liquidoMinimo) {
        this.liquidoMinimo = liquidoMinimo;
    }

    public double getLiquidoFinal() {
        return liquidoFinal;
    }

    public void setLiquidoFinal(double liquidoFinal) {
        this.liquidoFinal = liquidoFinal;
    }

   
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotalHaberesBps() {
        return totalHaberesBps;
    }

    public void setTotalHaberesBps(double totalHaberesBps) {
        this.totalHaberesBps = totalHaberesBps;
    }

    public double getTotalHaberesIrpf() {
        return totalHaberesIrpf;
    }

    public void setTotalHaberesIrpf(double totalHaberesIrpf) {
        this.totalHaberesIrpf = totalHaberesIrpf;
    }

    public double getTotalNoRet() {
        return totalNoRet;
    }

    public void setTotalNoRet(double totalNoRet) {
        this.totalNoRet = totalNoRet;
    }

    public double getTipoLiq() {
        return tipoLiq;
    }

    public void setTipoLiq(double tipoLiq) {
        this.tipoLiq = tipoLiq;
    }

    public double getDipaico() {
        return dipaico;
    }

    public void setDipaico(double dipaico) {
        this.dipaico = dipaico;
    }

    public double getCasenpace() {
        return casenpace;
    }

    public void setCasenpace(double casenpace) {
        this.casenpace = casenpace;
    }

    public double getFonasa() {
        return fonasa;
    }

    public void setFonasa(double fonasa) {
        this.fonasa = fonasa;
    }

    public double getFrl() {
        return frl;
    }

    public void setFrl(double frl) {
        this.frl = frl;
    }

    public double getRedondeos() {
        return redondeos;
    }

    public void setRedondeos(double redondeos) {
        this.redondeos = redondeos;
    }

    public Integer getTotalFunc() {
        return totalFunc;
    }

    public void setTotalFunc(Integer totalFunc) {
        this.totalFunc = totalFunc;
    }

    public double getIrpf() {
        return irpf;
    }

    public void setIrpf(double irpf) {
        this.irpf = irpf;
    }

    public CentroCosto getCentro() {
        return centro;
    }

    public void setCentro(CentroCosto centro) {
        this.centro = centro;
    }
    
    

    
    
    public ResumenLiquidacion(Funcionario funcionario, double totalHaberes, double totalDescuentos, double liquidoLegal, double liquidoMinimo, double liquidoFinal, Date fecha, double totalHaberesBps, double totalHaberesIrpf, double totalNoRet) {
        this.funcionario = funcionario;
        this.totalHaberes = totalHaberes;
        this.totalDescuentos = totalDescuentos;
        this.liquidoLegal = liquidoLegal;
        this.liquidoMinimo = liquidoMinimo;
        this.liquidoFinal = liquidoFinal;
        this.fecha = fecha;
        this.totalHaberesBps = totalHaberesBps;
        this.totalHaberesIrpf = totalHaberesIrpf;
        this.totalNoRet = totalNoRet;
    }
    
    
    
}
