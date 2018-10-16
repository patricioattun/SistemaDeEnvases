
package Dominio;

import java.sql.Timestamp;
import java.util.Date;


public class CodigoPrelacion {
    private Timestamp fecha;
    private Codigo cod;
    private Funcionario f;
    private boolean persistencia;

    public boolean isPersistencia() {
        return persistencia;
    }

    public void setPersistencia(boolean persistencia) {
        this.persistencia = persistencia;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Codigo getCod() {
        return cod;
    }

    public void setCod(Codigo cod) {
        this.cod = cod;
    }

    public Funcionario getF() {
        return f;
    }

    public void setF(Funcionario f) {
        this.f = f;
    }

       
}
