
package Logica;

import Dominio.Codigo;
import Dominio.Liquidacion;
import Persistencia.BDExcepcion;
import Persistencia.PersistenciaLiquidacion;
import java.util.ArrayList;


public class LogLiquidacion {
  private PersistenciaLiquidacion pers;

    public LogLiquidacion() {
        this.pers = new PersistenciaLiquidacion();
    }

    public ArrayList<Integer> cargoAñosLiquidaciones() throws BDExcepcion {
      return this.pers.cargoAñosLiquidaciones();
    }

    public ArrayList<Liquidacion> cargoLiquidacionesPorCodigos(int mes, int año, ArrayList<Codigo> codigos) throws BDExcepcion {
        return this.pers.cargoLiquidacionesPorCodigos(mes,año,codigos);
    }
  
}
