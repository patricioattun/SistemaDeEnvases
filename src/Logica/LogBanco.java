
package Logica;

import Dominio.Banco;
import Dominio.Departamento;
import Persistencia.PersistenciaBanco;
import Persistencia.PersistenciaCombos;
import Persistencia.PersistenciaFuncionario;
import java.sql.SQLException;


public class LogBanco {
    private LogFuncionario log;
    
    private PersistenciaBanco perban;
    private PersistenciaFuncionario func;

    public LogBanco() {
        func=new PersistenciaFuncionario();
        perban=new PersistenciaBanco();
    }
    
    
    
    public boolean existe(LogFuncionario LogFun,Integer suc, String nombre) throws SQLException, ClassNotFoundException{
        PersistenciaCombos com=LogFun.getC();
        perban=new PersistenciaBanco();
        perban.cargaComboBanco();
        return perban.existe(suc, nombre);
    }

    public boolean alta(String nombre, Integer sucursal, Departamento depto, String localidad, String direccion, String telef) throws SQLException {
       Boolean alta=false;
       Banco banco=new Banco();
       banco.setDireccion(direccion);
       banco.setLocalidad(localidad);
       banco.setNombre(nombre);
       banco.setSucursal(sucursal);
       banco.setTelefono(telef);
       banco.setDepartamento(depto);
       return perban.altaBanco(banco);
        
    }

    public int bancoAsignado(Integer cod) throws ClassNotFoundException, SQLException {
      return func.bancoAsignado(cod);
    }

    public boolean eliminaBanco(Integer cod) throws SQLException {
        return this.perban.eliminaBanco(cod);
    }

    public boolean modificabanco(Integer cod, String nombre, Integer sucursal, Departamento depto, String localidad, String direccion, String telef) throws SQLException {
     Boolean alta=false;
       Banco banco=new Banco();
       banco.setDireccion(direccion);
       banco.setLocalidad(localidad);
       banco.setNombre(nombre);
       banco.setSucursal(sucursal);
       banco.setTelefono(telef);
       banco.setDepartamento(depto);
       banco.setCodigo(cod);       
       return perban.modificaBanco(banco);
    }
}
