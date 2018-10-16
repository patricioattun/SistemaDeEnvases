
package Dominio;

import java.util.ArrayList;


public class LineaArchivoBanco {
    private Funcionario f;
    private Double liquidoFinal;
    private Integer tipoLiq;
    private ArrayList<Codigo> codigos;
    private Double importe;
    
    public Funcionario getF() {
        return f;
    }

    public void setF(Funcionario f) {
        this.f = f;
    }

    public Double getLiquidoFinal() {
        return liquidoFinal;
    }

    public void setLiquidoFinal(Double liquidoFinal) {
        this.liquidoFinal = liquidoFinal;
    }

    public Integer getTipoLiq() {
        return tipoLiq;
    }

    public void setTipoLiq(Integer tipoLiq) {
        this.tipoLiq = tipoLiq;
    }
    
    
    
    
}
