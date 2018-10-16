package Persistencia;

import Dominio.Banco;
import Dominio.Cargo;
import Dominio.CentroCosto;
import Dominio.CodigoDesvinc;
import Dominio.CodigoPdf;
import Dominio.Declaracion;
import Dominio.Departamento;
import Dominio.EstadoCivil;
import Dominio.Fallecimiento;
import Dominio.Feriado;
import Dominio.Funcionario;
import Dominio.Horario;
import Dominio.Licencia;
import Dominio.Liquidacion;
import Dominio.MovimientoLicencia;
import Dominio.Parametro;
import Dominio.PersonasACargo;
import Dominio.Relacion;
import Dominio.Sns;
import Dominio.Sucursal;
import Presentacion.frmPrin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenciaFuncionario {
    private Conexion conexion;
    private ArrayList<CentroCosto> centros;
    private ArrayList<Banco> bancos;
    private ArrayList<Departamento> deptos;
    private ArrayList<Cargo> cargos;
    private ArrayList<Sucursal> sucursales;
    private ArrayList<EstadoCivil> estados;
    private ArrayList<Sns> snss;
    private ArrayList<Horario> horarios;
    private ArrayList<CodigoDesvinc> codigosDes;
    private PersistenciaCombos combo;
    private PersistenciaHorario hor;
    private frmPrin frm;
    
  
    
    
    
    public Integer funcionarioExiste(Integer numFunc) throws SQLException, ClassNotFoundException {
        Connection cnn=null;
        cnn=Conexion.Cadena();
        String consulta="Select CODIGO from PERS_FUNCIONARIOS where CODIGO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, numFunc);
        ResultSet rs=ps.executeQuery();
        Integer size=0;
        while (rs.next()) {
                size++;
            }
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            return size;
    }

    public Boolean altaFuncionario(Funcionario f) throws SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer a=0;
        Time t;
        ArrayList<Horario> horarios=f.getHorarios();
         try{
             cnn=conexion.Cadena();
             cnn.setAutoCommit(false);
             String insert="insert into PERS_FUNCIONARIOS(CODIGO,APELLIDO1,APELLIDO2,NOMBRE1,NOMBRE2,DIRECCION,LOCALIDAD,DEPARTAMENTO,TELEFONO,CELULAR,CEDULA,CREDENCIAL,FECHA_NAC,EST_CIVIL,SEXO,INICIALES,FECHA_INGRESO,COD_CARGO,SUELDO_CARGO,CENTRO_COSTO,VIGENCIA_CARGO,BASE_HORARIA,BASE_HORAS,LUGAR_TRABAJO,AFAP,CUENTA,SOCIO,HORARIO,SNS,BANCO,VENCIMIENTO_CARNE,TIPO_CUENTA)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //
            //,COD_CARGO,SUELDO_CARGO,CENTRO_COSTO,VIGENCIA_CARGO,BASE_HORARIA,BASE_HORAS,LUGAR_TRABAJO,AFAP,CUENTA,SOCIO,HORARIO,SNS
             
             PreparedStatement psFunc=cnn.prepareStatement(insert);
             
             psFunc.setInt(1, f.getCodFunc());
             psFunc.setString(2, f.getApellido1());
             psFunc.setString(3, f.getApellido2());
             psFunc.setString(4, f.getNombre1());
             psFunc.setString(5, f.getNombre2());
             psFunc.setString(6, f.getDireccion());
             psFunc.setString(7, f.getLocalidad());
             psFunc.setInt(8, f.getDepartamento().getCodigo());
             psFunc.setString(9, f.getTelefono());
             psFunc.setString(10, f.getCelular());
             psFunc.setInt(11, f.getCedula());
             psFunc.setString(12, f.getCredencial());
             psFunc.setString(13, this.convertirFecha(f.getFechaNac()));
             psFunc.setInt(14, f.getEstadoCivil().getCodigo());
             psFunc.setString(15, String.valueOf(f.getSexo()));
             psFunc.setString(16, f.getIniciales());
             psFunc.setString(17, this.convertirFecha(f.getFechaIngreso()));
             psFunc.setInt(18, f.getCargo().getCodigo());
             psFunc.setDouble(19, f.getSueldoCargo());
             psFunc.setInt(20, f.getCentroCosto().getCodigo());
             psFunc.setString(21, this.convertirFecha(f.getVigenciaCargo()));
             psFunc.setInt(22, f.getBaseHoraria());
             psFunc.setInt(23, f.getBaseHoras());
             psFunc.setInt(24, f.getLugarTrabajo().getNumero());
             psFunc.setInt(25, f.getAfap());
             psFunc.setDouble(26, f.getCuenta());
             psFunc.setInt(27, 0);
             psFunc.setInt(28,0);
             Integer snss=0;
             if(f.getSns()!=null){
                 snss=f.getSns().getCodigo();
             }
             psFunc.setInt(29, snss);
             Integer bank=0;
             if(f.getBanco()!=null){
                 bank=f.getBanco().getCodigo();
             }
             psFunc.setInt(30, bank);
             psFunc.setString(31, this.convertirFecha(f.getVencimientoCarne()));
             psFunc.setString(32, f.getTipoCuenta());
             psFunc.executeUpdate();
             
             for(Integer i=0;i<horarios.size();i++){
                 this.altaHoraFunc(f.getCodFunc(), horarios.get(i), cnn);
             }
              alta=true;
             this.insertarAltaAuditoria("",cnn,"Alta Funcionario",f.getCodFunc()); 
//             this.insertarAltaAuditoria(String.valueOf(f.getBaseHoraria()),cnn,"Alta Base Horaria",f.getCodFunc()); 
//             this.insertarAltaAuditoria(String.valueOf(f.getBaseHoras()),cnn,"Alta Base Horas",f.getCodFunc());
//             this.insertarAltaAuditoria(String.valueOf(f.getSns().getCodigo()), cnn,"Alta SNS",f.getCodFunc());
//             this.insertarAltaAuditoria(this.convertirFecha(f.getFechaIngreso()),cnn,"Alta Fecha Ingreso",f.getCodFunc());
//             this.insertarAltaAuditoria(String.valueOf(f.getCargo().getCodigo()),cnn,"Alta Cargo",f.getCodFunc()); 
             cnn.commit();
            

         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
                if(cnn!=null){
                        cnn.close();
         }
     }
         return alta;
    }
    
    private void altaHoraFunc(Integer cod, Horario hor, Connection cnn) throws SQLException{
        
        
        String insert1="insert into PERS_HORARIOS_FUNC(CODIGOFUNC,CODIGOHORA)"+"values(?,?)";
         PreparedStatement psHora=cnn.prepareStatement(insert1);
         psHora.setInt(1, cod);
         psHora.setInt(2, hor.getCodigo());
         psHora.executeUpdate();
    }
    private void borrarHoraFunc(Integer cod, Connection cnn) throws SQLException{
        String delete="delete from PERS_HORARIOS_FUNC where CODIGOFUNC=?";
        PreparedStatement borrar=cnn.prepareStatement(delete);
        borrar.setInt(1, cod);
        borrar.executeUpdate();
    }
    
    
   private String convertirFecha(Date fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
    }
        
    return str;
    }
    private Date stringADate(String s) throws ParseException{
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(s);
    return date;
    }
    
    public Integer horarioExisteEnFunc(Integer cod) throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        cnn=conexion.Cadena();
        
        String consulta="Select * from PERS_HORARIOS_FUNC where CODIGOHORA=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
        Integer size=0;
        while (rs.next()) {
                size++;
            }  
       
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            
         return size;
    }

    public int bancoAsignado(Integer cod) throws ClassNotFoundException, SQLException {
         Connection cnn=null;
        cnn=conexion.Cadena();
        
        String consulta="Select * from PERS_FUNCIONARIOS where BANCO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
        Integer size=0;
        while (rs.next()) {
                size++;
            }  
       
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            
         return size;
    }

    public Funcionario buscarFuncionario(Integer codigo) throws ClassNotFoundException, SQLException {
       // this.cargarListas();
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f=new Funcionario();
        String consulta="Select * from PERS_FUNCIONARIOS where CODIGO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f.setAfap(rs.getInt("AFAP"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setBanco(this.buscarBanco(rs.getInt("BANCO")));
               f.setBaseHoraria(rs.getInt("BASE_HORARIA"));
               f.setBaseHoras(rs.getInt("BASE_HORAS"));
               f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
               f.setCedula(rs.getInt("CEDULA"));
               f.setCelular(rs.getString("CELULAR"));
               f.setCentroCosto(this.buscarCentro(rs.getInt("CENTRO_COSTO")));
               f.setCredencial(rs.getString("CREDENCIAL"));
               f.setCuenta(rs.getDouble("CUENTA"));               
               f.setDepartamento(this.buscarDepto(rs.getInt("DEPARTAMENTO")));
               f.setDireccion(rs.getString("DIRECCION"));
               f.setEstadoCivil(this.buscarEstado(rs.getInt("EST_CIVIL")));
               f.setFechaEgreso(rs.getDate("FECHA_EGRESO"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setFechaNac(rs.getDate("FECHA_NAC"));
               f.setIniciales(rs.getString("INICIALES"));
               f.setLocalidad(rs.getString("LOCALIDAD"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setSexo(rs.getString("SEXO").trim().charAt(0));
               f.setSns(this.buscarSns(rs.getInt("SNS")));
               f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
               f.setTelefono(rs.getString("TELEFONO"));
               f.setTipoCuenta(rs.getString("TIPO_CUENTA"));
               f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
               f.setVigenciaCargo(rs.getDate("VIGENCIA_CARGO"));
               f.setCodigoDesvinc(this.buscarCodigoDesvinc(rs.getInt("COD_DESVINCULACION")));
               f.setHorarios(this.buscarHorarios(cnn,codigo));
               f.setCodFunc(codigo);
           }
       
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            
         return f;
    
    }
    
    public ArrayList<Funcionario> listarFuncionariosActivos(String filtro) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
        if(filtro.equals("")){
        consulta="Select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NULL order by codigo";
        ps=cnn.prepareStatement(consulta);
        }
        else{
        consulta="Select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NULL AND APELLIDO1 LIKE ? OR APELLIDO2 LIKE ? OR NOMBRE1 LIKE ? OR NOMBRE2 LIKE ? order by codigo";    
        ps=cnn.prepareStatement(consulta);
        ps.setString(1, "%"+filtro+"%");
        ps.setString(2, "%"+filtro+"%");
        ps.setString(3, "%"+filtro+"%");
        ps.setString(4, "%"+filtro+"%");
        }
     
            
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setAfap(rs.getInt("AFAP"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setBanco(this.buscarBanco(rs.getInt("BANCO")));
               f.setBaseHoraria(rs.getInt("BASE_HORARIA"));
               f.setBaseHoras(rs.getInt("BASE_HORAS"));
               f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
               f.setCedula(rs.getInt("CEDULA"));
               f.setCelular(rs.getString("CELULAR"));
               f.setCentroCosto(this.buscarCentro(rs.getInt("CENTRO_COSTO")));
               f.setCredencial(rs.getString("CREDENCIAL"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setDepartamento(this.buscarDepto(rs.getInt("DEPARTAMENTO")));
               f.setDireccion(rs.getString("DIRECCION"));
               f.setEstadoCivil(this.buscarEstado(rs.getInt("EST_CIVIL")));
               f.setFechaEgreso(rs.getDate("FECHA_EGRESO"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setFechaNac(rs.getDate("FECHA_NAC"));
               f.setIniciales(rs.getString("INICIALES"));
               f.setLocalidad(rs.getString("LOCALIDAD"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setSexo(rs.getString("SEXO").charAt(0));
               f.setSns(this.buscarSns(rs.getInt("SNS")));
               f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
               f.setTelefono(rs.getString("TELEFONO"));
               f.setTipoCuenta(rs.getString("TIPO_CUENTA"));
               f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
               f.setVigenciaCargo(rs.getDate("VIGENCIA_CARGO"));
               f.setHorarios(this.buscarHorarios(cnn,rs.getInt("CODIGO")));
               f.setCodFunc(rs.getInt("CODIGO"));
               
               
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
              Integer fds=lista.size();
         return lista;
    
    }
    
     public ArrayList<Funcionario> listarFuncionariosActivosReLiq() throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
       
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
    
        consulta="select * from pers_funcionarios where (fecha_egreso is null) and codigo not in(select cod_func from pers_excepciones_reqliquidacion) order by codigo";
        ps=cnn.prepareStatement(consulta);
             
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setAfap(rs.getInt("AFAP"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setBanco(this.buscarBanco(rs.getInt("BANCO")));
               f.setBaseHoraria(rs.getInt("BASE_HORARIA"));
               f.setBaseHoras(rs.getInt("BASE_HORAS"));
               f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
               f.setCedula(rs.getInt("CEDULA"));
               f.setCelular(rs.getString("CELULAR"));
               f.setCentroCosto(this.buscarCentro(rs.getInt("CENTRO_COSTO")));
               f.setCredencial(rs.getString("CREDENCIAL"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setDepartamento(this.buscarDepto(rs.getInt("DEPARTAMENTO")));
               f.setDireccion(rs.getString("DIRECCION"));
               f.setEstadoCivil(this.buscarEstado(rs.getInt("EST_CIVIL")));
               f.setFechaEgreso(rs.getDate("FECHA_EGRESO"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setFechaNac(rs.getDate("FECHA_NAC"));
               f.setIniciales(rs.getString("INICIALES"));
               f.setLocalidad(rs.getString("LOCALIDAD"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setSexo(rs.getString("SEXO").charAt(0));
               f.setSns(this.buscarSns(rs.getInt("SNS")));
               f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
               f.setTelefono(rs.getString("TELEFONO"));
               f.setTipoCuenta(rs.getString("TIPO_CUENTA"));
               f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
               f.setVigenciaCargo(rs.getDate("VIGENCIA_CARGO"));
               f.setHorarios(this.buscarHorarios(cnn,rs.getInt("CODIGO")));
               f.setCodFunc(rs.getInt("CODIGO"));
               
               
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
              
         return lista;
    
    }
    
    public ArrayList<Funcionario> listarFuncionariosActivosPorSuc(Integer cod,String filtro) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
        if(filtro.equals("")){
        consulta="Select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NULL AND LUGAR_TRABAJO=? order by codigo";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        }
        else{ 
        consulta="Select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NULL AND LUGAR_TRABAJO=? and (APELLIDO1 LIKE ? OR APELLIDO2 LIKE ? OR NOMBRE1 LIKE ? OR NOMBRE2 LIKE?) order by codigo";    
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ps.setString(2, "%"+filtro+"%");
        ps.setString(3, "%"+filtro+"%");
        ps.setString(4, "%"+filtro+"%");
        ps.setString(5, "%"+filtro+"%");
        }
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setAfap(rs.getInt("AFAP"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setBanco(this.buscarBanco(rs.getInt("BANCO")));
               f.setBaseHoraria(rs.getInt("BASE_HORARIA"));
               f.setBaseHoras(rs.getInt("BASE_HORAS"));
               f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
               f.setCedula(rs.getInt("CEDULA"));
               f.setCelular(rs.getString("CELULAR"));
               f.setCentroCosto(this.buscarCentro(rs.getInt("CENTRO_COSTO")));
               f.setCredencial(rs.getString("CREDENCIAL"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setDepartamento(this.buscarDepto(rs.getInt("DEPARTAMENTO")));
               f.setDireccion(rs.getString("DIRECCION"));
               f.setEstadoCivil(this.buscarEstado(rs.getInt("EST_CIVIL")));
               f.setFechaEgreso(rs.getDate("FECHA_EGRESO"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setFechaNac(rs.getDate("FECHA_NAC"));
               f.setIniciales(rs.getString("INICIALES"));
               f.setLocalidad(rs.getString("LOCALIDAD"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setSexo(rs.getString("SEXO").charAt(0));
               f.setSns(this.buscarSns(rs.getInt("SNS")));
               f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
               f.setTelefono(rs.getString("TELEFONO"));
               f.setTipoCuenta(rs.getString("TIPO_CUENTA"));
               f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
               f.setVigenciaCargo(rs.getDate("VIGENCIA_CARGO"));
               f.setHorarios(this.buscarHorarios(cnn,rs.getInt("CODIGO")));
               f.setCodFunc(rs.getInt("CODIGO"));
               
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
              Integer fds=lista.size();
         return lista;
    
    }
    
    public ArrayList<Funcionario> listarFuncionarios(String filtro) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
        if(filtro.equals("")){
        consulta="Select * from PERS_FUNCIONARIOS order by codigo";
        ps=cnn.prepareStatement(consulta);
        }
        else{
        consulta="Select * from PERS_FUNCIONARIOS WHERE APELLIDO1 LIKE ? OR APELLIDO2 LIKE ? OR NOMBRE1 LIKE ? OR NOMBRE2 LIKE ? order by codigo";    
        ps=cnn.prepareStatement(consulta);
        ps.setString(1, "%"+filtro+"%");
        ps.setString(2, "%"+filtro+"%");
        ps.setString(3, "%"+filtro+"%");
        ps.setString(4, "%"+filtro+"%");
        }
       
        
        
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setAfap(rs.getInt("AFAP"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setBanco(this.buscarBanco(rs.getInt("BANCO")));
               f.setBaseHoraria(rs.getInt("BASE_HORARIA"));
               f.setBaseHoras(rs.getInt("BASE_HORAS"));
               f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
               f.setCedula(rs.getInt("CEDULA"));
               f.setCelular(rs.getString("CELULAR"));
               f.setCentroCosto(this.buscarCentro(rs.getInt("CENTRO_COSTO")));
               f.setCredencial(rs.getString("CREDENCIAL"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setDepartamento(this.buscarDepto(rs.getInt("DEPARTAMENTO")));
               f.setDireccion(rs.getString("DIRECCION"));
               f.setEstadoCivil(this.buscarEstado(rs.getInt("EST_CIVIL")));
               f.setFechaEgreso(rs.getDate("FECHA_EGRESO"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setFechaNac(rs.getDate("FECHA_NAC"));
               f.setIniciales(rs.getString("INICIALES"));
               f.setLocalidad(rs.getString("LOCALIDAD"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setSexo(rs.getString("SEXO").charAt(0));
               f.setSns(this.buscarSns(rs.getInt("SNS")));
               f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
               f.setTelefono(rs.getString("TELEFONO"));
               f.setTipoCuenta(rs.getString("TIPO_CUENTA"));
               f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
               f.setVigenciaCargo(rs.getDate("VIGENCIA_CARGO"));
               f.setHorarios(this.buscarHorarios(cnn,rs.getInt("CODIGO")));
               f.setCodFunc(rs.getInt("CODIGO"));
               
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
              Integer fds=lista.size();
         return lista;
    
    }
       
    public ArrayList<Funcionario> listarFuncionariosInactivos(String filtro) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
        if(filtro.equals("")){
        consulta="Select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NOT NULL order by codigo";
        ps=cnn.prepareStatement(consulta);
        }
        else{
        consulta="Select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NOT NULL AND APELLIDO1 LIKE ? OR APELLIDO2 LIKE ? OR NOMBRE1 LIKE ? OR NOMBRE2 LIKE ? order by codigo";    
        ps=cnn.prepareStatement(consulta);
        ps.setString(1, "%"+filtro+"%");
        ps.setString(2, "%"+filtro+"%");
        ps.setString(3, "%"+filtro+"%");
        ps.setString(4, "%"+filtro+"%");
        }
        
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setAfap(rs.getInt("AFAP"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setBanco(this.buscarBanco(rs.getInt("BANCO")));
               f.setBaseHoraria(rs.getInt("BASE_HORARIA"));
               f.setBaseHoras(rs.getInt("BASE_HORAS"));
               f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
               f.setCedula(rs.getInt("CEDULA"));
               f.setCelular(rs.getString("CELULAR"));
               f.setCentroCosto(this.buscarCentro(rs.getInt("CENTRO_COSTO")));
               f.setCredencial(rs.getString("CREDENCIAL"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setDepartamento(this.buscarDepto(rs.getInt("DEPARTAMENTO")));
               f.setDireccion(rs.getString("DIRECCION"));
               f.setEstadoCivil(this.buscarEstado(rs.getInt("EST_CIVIL")));
               f.setFechaEgreso(rs.getDate("FECHA_EGRESO"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setFechaNac(rs.getDate("FECHA_NAC"));
               f.setIniciales(rs.getString("INICIALES"));
               f.setLocalidad(rs.getString("LOCALIDAD"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setSexo(rs.getString("SEXO").charAt(0));
               f.setSns(this.buscarSns(rs.getInt("SNS")));
               f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
               f.setTelefono(rs.getString("TELEFONO"));
               f.setTipoCuenta(rs.getString("TIPO_CUENTA"));
               f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
               f.setVigenciaCargo(rs.getDate("VIGENCIA_CARGO"));
               f.setHorarios(this.buscarHorarios(cnn,rs.getInt("CODIGO")));
               f.setCodFunc(rs.getInt("CODIGO"));
               
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
              Integer fds=lista.size();
         return lista;
    
    }
    
    public ArrayList<Funcionario> listarFuncionariosInactivosPorSuc(Integer cod, String filtro) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
        if(filtro.equals("")){
        consulta="Select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NOT NULL AND LUGAR_TRABAJO=? order by codigo";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        }
        else{
        consulta="Select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NOT NULL AND LUGAR_TRABAJO=? AND (APELLIDO1 LIKE ? OR APELLIDO2 LIKE ? OR NOMBRE1 LIKE ? OR NOMBRE2 LIKE ?) order by codigo";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ps.setString(2, "%"+filtro+"%");
        ps.setString(3, "%"+filtro+"%");
        ps.setString(4, "%"+filtro+"%");
        ps.setString(5, "%"+filtro+"%");  
        }
       
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setAfap(rs.getInt("AFAP"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setBanco(this.buscarBanco(rs.getInt("BANCO")));
               f.setBaseHoraria(rs.getInt("BASE_HORARIA"));
               f.setBaseHoras(rs.getInt("BASE_HORAS"));
               f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
               f.setCedula(rs.getInt("CEDULA"));
               f.setCelular(rs.getString("CELULAR"));
               f.setCentroCosto(this.buscarCentro(rs.getInt("CENTRO_COSTO")));
               f.setCredencial(rs.getString("CREDENCIAL"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setDepartamento(this.buscarDepto(rs.getInt("DEPARTAMENTO")));
               f.setDireccion(rs.getString("DIRECCION"));
               f.setEstadoCivil(this.buscarEstado(rs.getInt("EST_CIVIL")));
               f.setFechaEgreso(rs.getDate("FECHA_EGRESO"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setFechaNac(rs.getDate("FECHA_NAC"));
               f.setIniciales(rs.getString("INICIALES"));
               f.setLocalidad(rs.getString("LOCALIDAD"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setSexo(rs.getString("SEXO").charAt(0));
               f.setSns(this.buscarSns(rs.getInt("SNS")));
               f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
               f.setTelefono(rs.getString("TELEFONO"));
               f.setTipoCuenta(rs.getString("TIPO_CUENTA"));
               f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
               f.setVigenciaCargo(rs.getDate("VIGENCIA_CARGO"));
               f.setHorarios(this.buscarHorarios(cnn,rs.getInt("CODIGO")));
               f.setCodFunc(rs.getInt("CODIGO"));
               
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
              Integer fds=lista.size();
         return lista;
    
    }
   
    public ArrayList<Funcionario> listarFuncionariosPorSuc(Integer cod, String filtro) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
        if(filtro.equals("")){
        consulta="Select * from PERS_FUNCIONARIOS WHERE LUGAR_TRABAJO=? order by codigo";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        }
        else{
        consulta="Select * from PERS_FUNCIONARIOS WHERE LUGAR_TRABAJO=? AND( APELLIDO1 LIKE ? OR APELLIDO2 LIKE ? OR NOMBRE1 LIKE ? OR NOMBRE2 LIKE ?) order by codigo";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ps.setString(2, "%"+filtro+"%");
        ps.setString(3, "%"+filtro+"%");
        ps.setString(4, "%"+filtro+"%");
        ps.setString(5, "%"+filtro+"%");    
        }
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setAfap(rs.getInt("AFAP"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setBanco(this.buscarBanco(rs.getInt("BANCO")));
               f.setBaseHoraria(rs.getInt("BASE_HORARIA"));
               f.setBaseHoras(rs.getInt("BASE_HORAS"));
               f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
               f.setCedula(rs.getInt("CEDULA"));
               f.setCelular(rs.getString("CELULAR"));
               f.setCentroCosto(this.buscarCentro(rs.getInt("CENTRO_COSTO")));
               f.setCredencial(rs.getString("CREDENCIAL"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setDepartamento(this.buscarDepto(rs.getInt("DEPARTAMENTO")));
               f.setDireccion(rs.getString("DIRECCION"));
               f.setEstadoCivil(this.buscarEstado(rs.getInt("EST_CIVIL")));
               f.setFechaEgreso(rs.getDate("FECHA_EGRESO"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setFechaNac(rs.getDate("FECHA_NAC"));
               f.setIniciales(rs.getString("INICIALES"));
               f.setLocalidad(rs.getString("LOCALIDAD"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setSexo(rs.getString("SEXO").charAt(0));
               f.setSns(this.buscarSns(rs.getInt("SNS")));
               f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
               f.setTelefono(rs.getString("TELEFONO"));
               f.setTipoCuenta(rs.getString("TIPO_CUENTA"));
               f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
               f.setVigenciaCargo(rs.getDate("VIGENCIA_CARGO"));
               f.setHorarios(this.buscarHorarios(cnn,rs.getInt("CODIGO")));
               f.setCodFunc(rs.getInt("CODIGO"));
               
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
              Integer fds=lista.size();
         return lista;
    
    }
    
    
    public void cargarListas() throws SQLException, ClassNotFoundException {
    combo=new PersistenciaCombos();
    hor=new PersistenciaHorario();
    if(codigosDes==null&&bancos==null&&cargos==null&&centros==null&&deptos==null&&estados==null&&snss==null&&sucursales==null&&horarios==null){
    this.bancos=combo.cargaComboBanco();
    this.cargos=combo.cargaComboCargo();
    this.centros=combo.cargaComboCentro();
    this.deptos=combo.cargaComboDepto();
    this.estados=combo.cargaComboEstadoCivil();
    this.snss=combo.cargaComboSns();
    this.sucursales=combo.cargaComboSucursal();
    this.horarios=hor.cargaComboHorarios();
    this.codigosDes=combo.cargaComboCodigoDesvinc();
    }
    }

    private Banco buscarBanco(Integer cod) throws SQLException, ClassNotFoundException {
        
    if(bancos==null){
    combo=new PersistenciaCombos();
    this.bancos=combo.cargaComboBanco();
    }
    Banco b=null;
        Integer i=0;
        Boolean bo=false;
        while(i<bancos.size()&&!bo){
            if(bancos.get(i).getCodigo().equals(cod)){
                b=bancos.get(i);
                bo=true;
                      
            }
            i++;
        }
        return b;
    }
    
    

    private Cargo buscarCargo(int cod) throws SQLException, ClassNotFoundException {
     if(cargos==null){
     combo=new PersistenciaCombos();
     this.cargos=combo.cargaComboCargo();
        } 
     Cargo c=null;
        Integer i=0;
        Boolean bo=false;
        while(i<cargos.size()&&!bo){
            if(cargos.get(i).getCodigo().equals(cod)){
                c=cargos.get(i);
                bo=true;
                      
            }
            i++;
        }
        return c;      
    }

    private CentroCosto buscarCentro(int cod) throws SQLException, ClassNotFoundException {
    if(centros==null){
    combo=new PersistenciaCombos();    
    this.centros=combo.cargaComboCentro();
    }    
    CentroCosto c=null;
        Integer i=0;
        Boolean bo=false;
        while(i<centros.size()&&!bo){
            if(centros.get(i).getCodigo().equals(cod)){
                c=centros.get(i);
                bo=true;
                      
            }
            i++;
        }
        return c;        
    }

    private Departamento buscarDepto(int cod) throws SQLException, ClassNotFoundException {
    if(deptos==null){
    combo=new PersistenciaCombos();    
    this.deptos=combo.cargaComboDepto();
    }
    Departamento d=null;
        Integer i=0;
        Boolean bo=false;
        while(i<deptos.size()&&!bo){
            if(deptos.get(i).getCodigo().equals(cod)){
                d=deptos.get(i);
                bo=true;
                      
            }
            i++;
        }
        return d;        
    }

    private EstadoCivil buscarEstado(int cod) throws SQLException, ClassNotFoundException {
    
    if(estados==null){
    combo=new PersistenciaCombos();  
    this.estados=combo.cargaComboEstadoCivil();
    }    
    EstadoCivil e=null;
        Integer i=0;
        Boolean bo=false;
        while(i<estados.size()&&!bo){
            if(estados.get(i).getCodigo().equals(cod)){
                e=estados.get(i);
                bo=true;
                      
            }
            i++;
        }
        return e;        
    }

  private Sucursal buscarSucursal(int cod) throws SQLException, ClassNotFoundException {
  if(sucursales==null){
    combo=new PersistenciaCombos();  
    this.sucursales=combo.cargaComboSucursal();
  }
    Sucursal s=null;
        Integer i=0;
        Boolean bo=false;
        while(i<sucursales.size()&&!bo){
            if(sucursales.get(i).getNumero().equals(cod)){
                s=sucursales.get(i);
                bo=true;
                      
            }
            i++;
        }
        return s;    
    }

    private Sns buscarSns(int cod) throws SQLException, ClassNotFoundException {
    if(snss==null){
    combo=new PersistenciaCombos();
    this.snss=combo.cargaComboSns();
    }
    Sns s=null;
        Integer i=0;
        Boolean bo=false;
        while(i<snss.size()&&!bo){
            if(snss.get(i).getCodigo().equals(cod)){
                s=snss.get(i);
                bo=true;
                      
            }
            i++;
        }
        return s;    
    }

    private ArrayList<Horario> buscarHorarios(Connection cnn,Integer codigo) throws ClassNotFoundException, SQLException {
      if(horarios==null){
       hor=new PersistenciaHorario();
       this.horarios=hor.cargaComboHorarios();
      }
       ArrayList<Horario> hor=new ArrayList<>();
        Horario h=null;
        
//        Connection cnn=null;
//        cnn=conexion.Cadena();
        Integer size=0;
        
        String consulta="Select * from PERS_HORARIOS_FUNC where CODIGOFUNC=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();
        while (rs.next()) {
                Integer i=0;
                Boolean b=false;
                while(i<horarios.size()&&!b){
                    if(horarios.get(i).getCodigo()==rs.getInt("CODIGOHORA")){
                        h=horarios.get(i);
                        hor.add(h);
                        b=true;
                        
                    }
                    i++;
                }
                    
            } 
      
            ps.close();
            rs.close();
    
            
            
        return hor;
    }

    public Boolean modFuncionario(Funcionario f,Funcionario fv) throws SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer a=0;
        Time t;
        ArrayList<Horario> horarios=f.getHorarios();
         try{
             cnn=conexion.Cadena();
             cnn.setAutoCommit(false);
             String insert="update PERS_FUNCIONARIOS set CODIGO=?,APELLIDO1=?,APELLIDO2=?,NOMBRE1=?,NOMBRE2=?,DIRECCION=?,LOCALIDAD=?,DEPARTAMENTO=?,TELEFONO=?,CELULAR=?,CEDULA=?,CREDENCIAL=?,FECHA_NAC=?,EST_CIVIL=?,SEXO=?,INICIALES=?,FECHA_INGRESO=?,FECHA_EGRESO=?,COD_CARGO=?,SUELDO_CARGO=?,CENTRO_COSTO=?,VIGENCIA_CARGO=?,BASE_HORARIA=?,BASE_HORAS=?,LUGAR_TRABAJO=?,AFAP=?,CUENTA=?,SOCIO=?,HORARIO=?,SNS=?,BANCO=?,VENCIMIENTO_CARNE=?,TIPO_CUENTA=?,COD_DESVINCULACION=? where CODIGO=?";
            //,COD_CARGO,SUELDO_CARGO,CENTRO_COSTO,VIGENCIA_CARGO,BASE_HORARIA,BASE_HORAS,LUGAR_TRABAJO,AFAP,CUENTA,SOCIO,HORARIO,SNS
             
             PreparedStatement psFunc=cnn.prepareStatement(insert);
             
             psFunc.setInt(1, f.getCodFunc());
             psFunc.setString(2, f.getApellido1());
             psFunc.setString(3, f.getApellido2());
             psFunc.setString(4, f.getNombre1());
             psFunc.setString(5, f.getNombre2());
             psFunc.setString(6, f.getDireccion());
             psFunc.setString(7, f.getLocalidad());
             psFunc.setInt(8, f.getDepartamento().getCodigo());
             psFunc.setString(9, f.getTelefono());
             psFunc.setString(10, f.getCelular());
             psFunc.setInt(11, f.getCedula());
             psFunc.setString(12, f.getCredencial());
             psFunc.setString(13, this.convertirFecha(f.getFechaNac()));
             psFunc.setInt(14, f.getEstadoCivil().getCodigo());
             psFunc.setString(15, String.valueOf(f.getSexo()));
             psFunc.setString(16, f.getIniciales());
             psFunc.setString(17, this.convertirFecha(f.getFechaIngreso()));
             psFunc.setString(18, this.convertirFecha(f.getFechaEgreso()));
             psFunc.setInt(19, f.getCargo().getCodigo());
             psFunc.setDouble(20, f.getSueldoCargo());
             psFunc.setInt(21, f.getCentroCosto().getCodigo());
             psFunc.setString(22, this.convertirFecha(f.getVigenciaCargo()));
             psFunc.setInt(23, f.getBaseHoraria());
             psFunc.setInt(24, f.getBaseHoras());
             psFunc.setInt(25, f.getLugarTrabajo().getNumero());
             psFunc.setInt(26, f.getAfap());
             psFunc.setDouble(27, f.getCuenta());
             psFunc.setInt(28, 0);
             psFunc.setInt(29,0);
             Integer snss=0;
             if(f.getSns()!=null){
                 snss=f.getSns().getCodigo();
             }
             psFunc.setInt(30, snss);
             Integer bank=0;
             if(f.getBanco()!=null){
                 bank=f.getBanco().getCodigo();
             }
             psFunc.setInt(31, bank);
             psFunc.setString(32, this.convertirFecha(f.getVencimientoCarne()));
             psFunc.setString(33, f.getTipoCuenta());
             Integer co=-1;
             if(f.getCodigoDesvinc()!=null){
                 co=f.getCodigoDesvinc().getId();
             }
             psFunc.setInt(34, co);
             psFunc.setInt(35, f.getCodFunc());
            
            psFunc.executeUpdate();
             
             this.borrarHoraFunc(f.getCodFunc(), cnn);
             
             for(Integer i=0;i<horarios.size();i++){
                 this.altaHoraFunc(f.getCodFunc(), horarios.get(i), cnn);
             }
             alta=true;
             if(!String.valueOf(f.getSueldoCargo()).trim().equals(String.valueOf(fv.getSueldoCargo()).trim())){
             this.insertarModAuditoria(String.valueOf(f.getSueldoCargo()),String.valueOf(fv.getSueldoCargo()),cnn,"Modificacion Sueldo",f.getCodFunc()); 
             }
             if(!String.valueOf(f.getBaseHoraria()).trim().equals(String.valueOf(fv.getBaseHoraria()).trim())){
             this.insertarModAuditoria(String.valueOf(f.getBaseHoraria()),String.valueOf(fv.getBaseHoraria()),cnn,"Modificacion Base Horaria",f.getCodFunc()); 
             }
             if(!String.valueOf(f.getBaseHoras()).trim().equals(String.valueOf(fv.getBaseHoras()).trim())){
             this.insertarModAuditoria(String.valueOf(f.getBaseHoras()),String.valueOf(fv.getBaseHoras()),cnn,"Modificacion Base Horas",f.getCodFunc());
             }
             if(!String.valueOf(f.getSns().getCodigo()).trim().equals(String.valueOf(fv.getSns().getCodigo()).trim())){
             this.insertarModAuditoria(String.valueOf(f.getSns().getCodigo()),String.valueOf(fv.getSns().getCodigo()), cnn,"Modificacion SNS",f.getCodFunc());
             }
             if(!this.convertirFecha(f.getFechaIngreso()).trim().equals(this.convertirFecha(fv.getFechaIngreso()).trim())){
             this.insertarModAuditoria(this.convertirFecha(f.getFechaIngreso()),this.convertirFecha(fv.getFechaIngreso()),cnn,"Modificacion Fecha Ingreso",f.getCodFunc());
             }
             if(!String.valueOf(f.getCargo().getCodigo()).trim().equals(String.valueOf(fv.getCargo().getCodigo()).trim())){
             this.insertarModAuditoria(String.valueOf(f.getCargo().getCodigo()),String.valueOf(fv.getCargo().getCodigo()),cnn,"Modificacion Cargo",f.getCodFunc()); 
             }
             if(fv.getFechaEgreso()!=f.getFechaEgreso()){
             if(fv.getFechaEgreso() == null){    
                this.insertarModAuditoria(this.convertirFecha(f.getFechaEgreso()),"",cnn,"Baja de Funcionario",f.getCodFunc()); 
             }
             else if(f.getFechaEgreso()==null){
                this.insertarModAuditoria("",this.convertirFecha(fv.getFechaEgreso()),cnn,"Reingreso de funcionario",f.getCodFunc()); 
             }
             }
             
             
             
             cnn.commit();
             

         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
               
     }
         return alta;
    
    }
    
    public Boolean modFuncionarioSecretaria(Funcionario f) throws SQLException {
       Boolean alta=false;
        try {
            Connection cnn=null;
                
            ArrayList<Horario> horarios=f.getHorarios();
            
            cnn=conexion.Cadena();
            cnn.setAutoCommit(false);
            String insert="update PERS_FUNCIONARIOS set VENCIMIENTO_CARNE=? where CODIGO=?";
            //,COD_CARGO,SUELDO_CARGO,CENTRO_COSTO,VIGENCIA_CARGO,BASE_HORARIA,BASE_HORAS,LUGAR_TRABAJO,AFAP,CUENTA,SOCIO,HORARIO,SNS
            
            PreparedStatement psFunc=cnn.prepareStatement(insert);
            
            psFunc.setString(1, this.convertirFecha(f.getVencimientoCarne()));
            psFunc.setInt(2, f.getCodFunc());
            psFunc.executeUpdate();
            
            this.borrarHoraFunc(f.getCodFunc(), cnn);
            
            for(Integer i=0;i<horarios.size();i++){
                this.altaHoraFunc(f.getCodFunc(), horarios.get(i), cnn);
            }
            alta=true;
            
            
            cnn.commit();
            
            if(cnn!=null){
                cnn.close();
                psFunc.close();
            }
            
            
            
            
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
         return alta;
    }
            
    
    public ArrayList<Funcionario> vencimientoCarne() throws ClassNotFoundException, SQLException{
        ArrayList<Funcionario> listado=null;
        Funcionario f=null;
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        String consulta="select * from PERS_FUNCIONARIOS where FECHA_EGRESO IS NULL order by VENCIMIENTO_CARNE";
        //where VENCIMIENTO_CARNE<=? OR VENCIMIENTO_CARNE<=? and VENCIMIENTO_CARNE>?
//        Date fechaActual = new Date(); 
//        String actual=this.convertirFecha(fechaActual);
//        Date semanaAntes=this.sumarDiasFecha(fechaActual);
//        String semana=this.convertirFecha(semanaAntes);
        PreparedStatement ps=cnn.prepareStatement(consulta);
//        ps.setString(1, actual);
//        ps.setString(2, semana);
//        ps.setString(3, actual);
        ResultSet rs=ps.executeQuery();
        listado=new ArrayList<>();
        while(rs.next()){
            f=new Funcionario();
            f.setCodFunc(rs.getInt("CODIGO"));
            f.setApellido1(rs.getString("APELLIDO1"));
            f.setApellido2(rs.getString("APELLIDO2"));
            f.setNombre1(rs.getString("NOMBRE1"));
            f.setNombre2(rs.getString("NOMBRE2"));
            f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
            listado.add(f);
        }
        
        if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
        
        return listado;
    }
    
    public ArrayList<Funcionario> listadoSueldo() throws ClassNotFoundException, SQLException{
        ArrayList<Funcionario> listado=null;
        Funcionario f=null;
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        String consulta="select CODIGO,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,SUELDO_CARGO,COD_CARGO from PERS_FUNCIONARIOS where FECHA_EGRESO IS NULL order by CODIGO";
   
        PreparedStatement ps=cnn.prepareStatement(consulta);

        ResultSet rs=ps.executeQuery();
        listado=new ArrayList<>();
        while(rs.next()){
            f=new Funcionario();
            f.setCodFunc(rs.getInt("CODIGO"));
            f.setApellido1(rs.getString("APELLIDO1"));
            f.setApellido2(rs.getString("APELLIDO2"));
            f.setNombre1(rs.getString("NOMBRE1"));
            f.setNombre2(rs.getString("NOMBRE2"));
            f.setSueldoCargo(rs.getDouble("SUELDO_CARGO"));
            f.setCargo(this.buscarCargo(rs.getInt("COD_CARGO")));
            listado.add(f);
        }
        
        if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
        
        return listado;
    }

    public Date sumarDiasFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
	calendar.setTime(fecha); // Configuramos la fecha que se recibe
	calendar.add(Calendar.DAY_OF_YEAR, 7);  // numero de días a añadir, o restar en caso de días<0
	return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	
 
	
 }

    public ArrayList<Funcionario> listadoVencidos() throws ClassNotFoundException, SQLException {
        ArrayList<Funcionario> listado=null;
        Funcionario f=null;
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        String consulta="select * from PERS_FUNCIONARIOS where VENCIMIENTO_CARNE<=? OR VENCIMIENTO_CARNE<=? and VENCIMIENTO_CARNE>? and FECHA_EGRESO IS NULL order by VENCIMIENTO_CARNE";
        Date fechaActual = new Date(); 
        String actual=this.convertirFecha(fechaActual);
        Date semanaAntes=this.sumarDiasFecha(fechaActual);
        String semana=this.convertirFecha(semanaAntes);
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, actual);
        ps.setString(2, semana);
        ps.setString(3, actual);
        ResultSet rs=ps.executeQuery();
        listado=new ArrayList<>();
        while(rs.next()){
            f=new Funcionario();
            f.setCodFunc(rs.getInt("CODIGO"));
            f.setApellido1(rs.getString("APELLIDO1"));
            f.setApellido2(rs.getString("APELLIDO2"));
            f.setNombre1(rs.getString("NOMBRE1"));
            f.setNombre2(rs.getString("NOMBRE2"));
            f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
            listado.add(f);
        }
        
        if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
        
        return listado;
    }
    
    
    public ArrayList<Funcionario> listarFuncionariosActivosParaLic() throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
      
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
       
        consulta="Select CODIGO,FECHA_INGRESO from PERS_FUNCIONARIOS where FECHA_EGRESO IS NULL order by codigo";
        ps=cnn.prepareStatement(consulta);
   
     
            
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
              
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setCodFunc(rs.getInt("CODIGO"));
               
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
             
         return lista;
    
    }
    
     public ArrayList<Funcionario> listarFuncionariosActivosLike(String filtro) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
      
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
       if(!filtro.equals("")){
        filtro=filtro.toUpperCase();
        consulta="Select CODIGO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2 from PERS_FUNCIONARIOS where (APELLIDO1 LIKE ? OR APELLIDO2 LIKE ? OR NOMBRE1 LIKE ? OR NOMBRE2 LIKE ?) order by codigo";
        ps=cnn.prepareStatement(consulta);
        ps.setString(1, "%"+filtro+"%");
        ps.setString(2, "%"+filtro+"%");
        ps.setString(3, "%"+filtro+"%");
        ps.setString(4, "%"+filtro+"%");
        
        }
       else{
        consulta="Select CODIGO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2 from PERS_FUNCIONARIOS order by codigo";
        ps=cnn.prepareStatement(consulta);
       }
        ResultSet rs=ps.executeQuery();
         while (rs.next()){
               f=new Funcionario();
              
               f.setCodFunc(rs.getInt("CODIGO"));
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               lista.add(f);
           }
     
        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
             
         return lista;
    
    }
    
    
       public ArrayList<Funcionario> listarFuncionariosParaLic() throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
      
        Funcionario f;
        ArrayList<Funcionario> lista=new ArrayList<>();
        PreparedStatement ps=null;
       
        consulta="Select CODIGO from PERS_FUNCIONARIOS where order by codigo";
        ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setCodFunc(rs.getInt("CODIGO"));
               lista.add(f);
           }
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            }
      return lista;
    }
    
    
    
    //*******LICENCIA*****//
    public ArrayList<Licencia> listarLicencia(Integer año) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
      
        Licencia l;
        ArrayList<Licencia> lista=new ArrayList<>();
        PreparedStatement ps=null;
        consulta="Select * from PERS_LICENCIA_GENERADA WHERE ANIO=? order by CODFUNC";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, año);
        ResultSet rs=ps.executeQuery();
           while (rs.next()){
               l=new Licencia();
               l.setAño(rs.getInt("ANIO"));
               l.setFechaIni(rs.getDate("FECHAINI"));
               l.setFechaFin(rs.getDate("FECHAFIN"));
               l.setId(rs.getInt("CODIGO"));
               l.setDiasGenerados(rs.getInt("DIAS_GENERADOS"));
               l.setDiasDescuento(rs.getInt("DIAS_DESCUENTO"));
               Funcionario g=this.funcionarioParcialFull(rs.getInt("CODFUNC"));
               l.setFuncionario(g);
               if(g==null){
                   Integer fr=rs.getInt("CODFUNC");
                   String str="";
               }
               l.setFechaGen(rs.getDate("FECHAGEN"));
               lista.add(l);
           }
     
        
           
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
             
         return this.chequeaCambio(lista,año);
    
    }

    public Funcionario funcionarioParcial(int codigo)throws BDExcepcion {
        Funcionario f=null;
        try {
            Connection cnn=null;
            cnn=conexion.Cadena();
            Integer size=0;
                        
            String consulta="Select CEDULA,CODIGO,FECHA_INGRESO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2, LUGAR_TRABAJO,CUENTA,VENCIMIENTO_CARNE from PERS_FUNCIONARIOS where CODIGO=? and FECHA_EGRESO IS NULL";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codigo);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()){
                f=new Funcionario();
                f.setApellido1(rs.getString("APELLIDO1"));
                f.setApellido2(rs.getString("APELLIDO2"));
                f.setCedula(rs.getInt("CEDULA"));
                f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
                f.setNombre1(rs.getString("NOMBRE1"));
                f.setNombre2(rs.getString("NOMBRE2"));
                f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
                f.setCodFunc(codigo);
                f.setHorarios(this.buscarHorarios(cnn,codigo));
                f.setCuenta(rs.getDouble("CUENTA"));
                f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
            }
            
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
                
            }
            
            
        } catch (ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
        return f;
    }
    
    public Funcionario funcionarioParcialTodos(int codigo)throws BDExcepcion {
        Funcionario f=null;
        try {
            Connection cnn=null;
            cnn=conexion.Cadena();
            Integer size=0;
                        
            String consulta="Select CEDULA,CODIGO,FECHA_INGRESO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2, LUGAR_TRABAJO,CUENTA,VENCIMIENTO_CARNE from PERS_FUNCIONARIOS where CODIGO=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codigo);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()){
                f=new Funcionario();
                f.setApellido1(rs.getString("APELLIDO1"));
                f.setApellido2(rs.getString("APELLIDO2"));
                f.setCedula(rs.getInt("CEDULA"));
                f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
                f.setNombre1(rs.getString("NOMBRE1"));
                f.setNombre2(rs.getString("NOMBRE2"));
                f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
                f.setCodFunc(codigo);
                f.setHorarios(this.buscarHorarios(cnn,codigo));
                f.setCuenta(rs.getDouble("CUENTA"));
                f.setVencimientoCarne(rs.getDate("VENCIMIENTO_CARNE"));
            }
            
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
                
            }
            
            
        } catch (ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
        return f;
    }
    
    
    
    
    public Funcionario funcionarioVale(int codigo, Connection cnn) throws ClassNotFoundException, SQLException {
        Funcionario f=null;
        String consulta="Select CODIGO,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,CUENTA from PERS_FUNCIONARIOS where CODIGO=? and FECHA_EGRESO IS NULL";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setCodFunc(rs.getInt("CODIGO"));
           }
           
           
            ps.close();
            rs.close();
       return f;
    }
    
       public Funcionario funcionarioBasico(int codigo) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f=null;
        String consulta="Select CODIGO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2 from PERS_FUNCIONARIOS where CODIGO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setCodFunc(codigo);
          }
           
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            
         return f;
    }
       
     public Funcionario funcionarioBasicoMasivo(int codigo, Connection cnn) throws BDExcepcion {
  
        try {
            Integer size=0;
            Funcionario f=null;
            String consulta="Select cedula,CODIGO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2 from PERS_FUNCIONARIOS where CODIGO=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codigo);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()){
                f=new Funcionario();
                f.setApellido1(rs.getString("APELLIDO1"));
                f.setApellido2(rs.getString("APELLIDO2"));
                f.setNombre1(rs.getString("NOMBRE1"));
                f.setNombre2(rs.getString("NOMBRE2"));
                f.setCodFunc(codigo);
               
            }
            
            
            ps.close();
            rs.close();
            

            return f;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
     
    public Funcionario funcionarioParaLiq(int codigo, Connection cnn) throws BDExcepcion {
  
        try {
            Integer size=0;
            Funcionario f=null;
            String consulta="Select sns,CUENTA,BANCO,cedula,fecha_ingreso,lugar_trabajo,CODIGO,cod_cargo,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2 from PERS_FUNCIONARIOS where CODIGO=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codigo);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()){
                f=new Funcionario();
                f.setApellido1(rs.getString("APELLIDO1"));
                f.setApellido2(rs.getString("APELLIDO2"));
                f.setNombre1(rs.getString("NOMBRE1"));
                f.setNombre2(rs.getString("NOMBRE2"));
                f.setCargo(this.buscoCargo(cnn,rs.getInt("cod_cargo")));
                f.setCodFunc(codigo);
                f.setFechaIngreso(rs.getDate("fecha_ingreso")); 
                f.setCedula(rs.getInt("CEDULA"));
                Sucursal s = new Sucursal();
                s.setNumero(rs.getInt("lugar_trabajo"));
                f.setCuenta(rs.getDouble("CUENTA"));
                f.setBanco(this.buscarBancoVale(rs.getInt("BANCO"), cnn));
                f.setSns(this.cargaSns(rs.getInt("SNS"), cnn)); 
                f.setLugarTrabajo(s);
            }
            
            
            ps.close();
            rs.close();
            

            return f;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }
    
    
    public Sns cargaSns(Integer cod, Connection cnn) throws SQLException{
 
        Sns sns=null; 
    
        String consulta="Select * from PERS_SNS where codigo=? order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
            if(rs.next()){
            sns=new Sns();
            sns.setCodigo(rs.getInt("CODIGO"));
            sns.setDescripcion(rs.getString("DESCRIPCION").trim());
            sns.setCaja(rs.getDouble("CAJA"));
            sns.setFonasa(rs.getDouble("FONASA"));
            
            }        
             ps.close();
             rs.close();
          
         return sns;
    }
     
     public Funcionario funcionarioBasicoVale(int codigo,Connection cnn) throws ClassNotFoundException, SQLException {
  
        Funcionario f=null;
        String consulta="Select CUENTA,CODIGO,BANCO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2 from PERS_FUNCIONARIOS where CODIGO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setCuenta(rs.getDouble("CUENTA"));
               f.setBanco(this.buscarBancoVale(rs.getInt("BANCO"),cnn));
               f.setCodFunc(codigo);
          }
           
           
            ps.close();
            rs.close();
         
            
         return f;
    }
    
    private Funcionario funcionarioParcialFull(int codigo) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        Funcionario f=null;
        String consulta="Select CEDULA,CODIGO,FECHA_INGRESO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2, LUGAR_TRABAJO from PERS_FUNCIONARIOS where CODIGO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codigo);
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               f=new Funcionario();
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setCedula(rs.getInt("CEDULA"));
               f.setFechaIngreso(rs.getDate("FECHA_INGRESO"));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setCodFunc(codigo);
               f.setHorarios(this.buscarHorarios(cnn,codigo));
           }
           
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            
         return f;
    }
    
    
    
       public ArrayList<Funcionario> listaFuncionarioParcial() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer size=0;
        ArrayList<Funcionario> listado=new ArrayList<>();
        String consulta="Select CODIGO,trim(NOMBRE1) as nombre1,trim(NOMBRE2) as nombre2,trim(APELLIDO1) as apellido1,trim(APELLIDO2) as apellido2, LUGAR_TRABAJO from PERS_FUNCIONARIOS where FECHA_EGRESO IS NULL order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
              Funcionario f=new Funcionario();
               f.setApellido1(rs.getString("APELLIDO1"));
               f.setApellido2(rs.getString("APELLIDO2"));
               f.setNombre1(rs.getString("NOMBRE1"));
               f.setNombre2(rs.getString("NOMBRE2"));
               f.setLugarTrabajo(this.buscarSucursal(rs.getInt("LUGAR_TRABAJO")));
               f.setCodFunc(rs.getInt("CODIGO"));
               f.setHorarios(this.buscarHorarios(cnn,f.getCodFunc()));
               listado.add(f);
           }
           
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            
         return listado;
    }
       public ArrayList<Integer> listaCodFunc() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer i=0;
        ArrayList<Integer> listado=new ArrayList<>();
        String consulta="Select CODIGO from PERS_FUNCIONARIOS  order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();

           while (rs.next()){
               i=rs.getInt("CODIGO");
               listado.add(i);
           }
           
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
            
         return listado;
    }
       
       
    
    public ArrayList<Licencia> licenciaPorCod(Integer codigo)throws BDExcepcion{
        ArrayList<Licencia> lista=new ArrayList<>();
        try {
            String consulta="";
            Connection cnn=null;
            cnn=conexion.Cadena();
            Date d=new Date();
            Integer año=this.obtenerAño(d);
            Licencia l;
            
            PreparedStatement ps=null;
            consulta="Select * from PERS_LICENCIA_GENERADA WHERE CODFUNC=? AND ANIO<=? order by anio desc";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codigo);
            ps.setInt(2, año);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                l=new Licencia();
                l.setAño(rs.getInt("ANIO")-1);
                l.setFechaIni(rs.getDate("FECHAINI"));
                l.setFechaFin(rs.getDate("FECHAFIN"));
                l.setId(rs.getInt("CODIGO"));
                l.setDiasGenerados(rs.getInt("DIAS_GENERADOS"));
                l.setSaldo(rs.getInt("SALDO"));
                l.setFuncionario(this.funcionarioParcial(rs.getInt("CODFUNC")));
                l.setFechaGen(rs.getDate("FECHAGEN"));
                l.setDiasDescuento(rs.getInt("DIAS_DESCUENTO"));
                lista.add(l);
            }
            
            
            
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
                
            }
            
            
        } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
        return lista;
    }
  
     public Licencia licencia(Integer codigo,Date d)throws BDExcepcion{
        Licencia l=null;
         try {
            String consulta="";
            Connection cnn=null;
            cnn=conexion.Cadena();
            
            PreparedStatement ps=null;
            consulta="Select * from PERS_LICENCIA_GENERADA where CODFUNC=? and FECHAGEN=? order by CODFUNC";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codigo);
            ps.setString(2, this.convertirFecha(d));
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                l=new Licencia();
                l.setAño(rs.getInt("ANIO"));
                l.setFechaIni(rs.getDate("FECHAINI"));
                l.setFechaFin(rs.getDate("FECHAFIN"));
                l.setId(rs.getInt("CODIGO"));
                l.setSaldo(rs.getInt("SALDO"));
                l.setDiasGenerados(rs.getInt("DIAS_GENERADOS"));
                l.setFuncionario(this.funcionarioParcial(rs.getInt("CODFUNC")));
            }
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
                
            }
            
            
        } catch (ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    return l;
    }

    public boolean insertarLicencia(Licencia l,Connection cnn) throws SQLException, ClassNotFoundException {
      
        Boolean alta=false;
        Integer contador=0;
          cnn.setAutoCommit(true);
             String insert="insert into PERS_LICENCIA_GENERADA(CODIGO,ANIO,DIAS_GENERADOS,CODFUNC,FECHAGEN,FECHAINGRESO,SALDO,DIAS_DESCUENTO)"+"values(?,?,?,?,?,?,?,?)";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
               
             int ultcod=this.buscarCodigo();
             psFunc.setInt(1, ultcod);
             psFunc.setInt(2, l.getAño());
             psFunc.setInt(3, l.getDiasGenerados());
             psFunc.setInt(4, l.getFuncionario().getCodFunc());
             psFunc.setString(5, this.convertirFecha(l.getFechaGen()));
             psFunc.setString(6, this.convertirFecha(l.getFuncionario().getFechaIngreso()));
             psFunc.setInt(7, this.calcularSaldoAdelantado(l,cnn));
             psFunc.setInt(8, l.getDiasDescuento());
             psFunc.executeUpdate();
             contador++;
             
             if (contador==1){
                 alta=true;
             }
            cnn.commit();
           
           return alta;
    }
         
         
    private Integer buscarCodigo() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Integer ultCod=null;
        cnn=conexion.Cadena();
        String consulta="SELECT MAX(CODIGO) FROM PERS_LICENCIA_GENERADA";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            ultCod=rs.getInt("1");
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return ultCod+1;  
    }
    
    public boolean trancaLicencia(Date d) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        boolean esta=true;
        int t=0;
        cnn=conexion.Cadena();
        String consulta="SELECT CODIGO FROM PERS_LICENCIA_GENERADA WHERE FECHAGEN=? ";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, this.convertirFecha(d));
        ResultSet rs=ps.executeQuery();
          
        while(rs.next()&&t==0){
              t++;
          }
        if(t==0){
          esta=false;
        }
                  
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return esta;  
    }
    public ArrayList<Licencia> cargaComboLicencia(Integer cod)throws BDExcepcion{
         ArrayList<Licencia> lista=new ArrayList<>();
        try {
            String consulta="";
            Connection cnn=null;
            Date d=new Date();
            Integer año=Integer.valueOf(this.obtenerAño1(d));
            cnn=conexion.Cadena();
            Licencia l;
           
            PreparedStatement ps=null;
            consulta="Select * from PERS_LICENCIA_GENERADA WHERE CODFUNC=? and SALDO>0 and anio=? and fechaini>? order by ANIO";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ps.setInt(2, año);
            ps.setString(3, this.convertirFecha(d));
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                l=new Licencia();
                l.setAño(rs.getInt("ANIO")-1);
                l.setFechaIni(rs.getDate("FECHAINI"));
                l.setFechaFin(rs.getDate("FECHAFIN"));
                l.setId(rs.getInt("CODIGO"));
                l.setSaldo(rs.getInt("SALDO"));
                l.setFuncionario(this.funcionarioParcial(rs.getInt("CODFUNC")));
                lista.add(l);
            }
            
            
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
                
            }
            
           
        } catch (ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
     return lista;
    }
    public ArrayList<Licencia> cargaComboLicenciaPasado(Integer cod)throws BDExcepcion{
        ArrayList<Licencia> lista=new ArrayList<>();
        try {
            String consulta="";
            Connection cnn=null;
            Date d=new Date();
            Integer año=Integer.valueOf(this.obtenerAño1(d));
            cnn=conexion.Cadena();
            Licencia l;
            
            PreparedStatement ps=null;
            consulta="Select * from PERS_LICENCIA_GENERADA WHERE CODFUNC=? and SALDO>0 and anio<? order by ANIO";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ps.setInt(2, año);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                l=new Licencia();
                l.setAño(rs.getInt("ANIO")-1);
                l.setFechaIni(rs.getDate("FECHAINI"));
                l.setFechaFin(rs.getDate("FECHAFIN"));
                l.setId(rs.getInt("CODIGO"));
                l.setSaldo(rs.getInt("SALDO"));
                l.setFuncionario(this.funcionarioParcial(rs.getInt("CODFUNC")));
                lista.add(l);
            }
            Licencia e=this.cargoSaldoAñoCorriente(cnn, cod);
            if(e!=null){
                lista.add(e);
            }
            
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
                
            }
            
            
        } catch (ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    return lista;
    }
    
       public Licencia licenciaActualFunc(Integer cod)throws BDExcepcion {
        Licencia l=null;
         try {
            String consulta="";
            Connection cnn=null;
            Date d=new Date();
            Integer año=Integer.valueOf(this.obtenerAño1(d));
            cnn=conexion.Cadena();
            
            PreparedStatement ps=null;
            consulta="Select * from PERS_LICENCIA_GENERADA WHERE CODFUNC=? and anio=?";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ps.setInt(2, año);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                l=new Licencia();
                l.setAño(rs.getInt("ANIO")-1);
                l.setId(rs.getInt("CODIGO"));
                l.setFechaIni(rs.getDate("FECHAINI"));
                l.setFechaGen(rs.getDate("FECHAGEN"));
                l.setSaldo(rs.getInt("SALDO"));
                l.setFuncionario(this.funcionarioParcial(rs.getInt("CODFUNC")));
                
            }
            
            
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
                
            }
            
           
        } catch (ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
          throw new BDExcepcion(ex.getMessage());
        }
     return l;
    }
       
       public ArrayList<Licencia> licenciaAnteriorFunc(Integer cod)throws BDExcepcion{
        ArrayList<Licencia> lista=new ArrayList<>();
         try {
            String consulta="";
            Connection cnn=null;
            Date d=new Date();
            Integer año=Integer.valueOf(this.obtenerAño1(d));
            cnn=conexion.Cadena();
            Licencia l=null;
            
            PreparedStatement ps=null;
            consulta="Select * from PERS_LICENCIA_GENERADA WHERE CODFUNC=? and anio<?";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ps.setInt(2, año);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                l=new Licencia();
                l.setAño(rs.getInt("ANIO")-1);
                l.setId(rs.getInt("CODIGO"));
                l.setFechaGen(rs.getDate("FECHAGEN"));
                l.setSaldo(rs.getInt("SALDO"));
                l.setFuncionario(this.funcionarioParcial(rs.getInt("CODFUNC")));
                lista.add(l);
            }
            
            
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
                
            }
            
            
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
        return lista;
    }   

    public boolean actualizarLicencia(Date fechaIni, Date fechaFin, Licencia lic) throws BDExcepcion {
        try {
            Connection cnn=null;
            Boolean alta=false;
            Integer a=0;
            
            
            cnn=conexion.Cadena();
            String insert="update PERS_LICENCIA_GENERADA set FECHAINI=?,FECHAFIN=?,SALDO=?,DIAS_DESCUENTO=? WHERE CODFUNC=? AND ANIO=?";
            PreparedStatement psFunc=cnn.prepareStatement(insert);
            
            psFunc.setString(1, this.convertirFecha(fechaIni));
            psFunc.setString(2,this.convertirFecha(fechaFin));
            psFunc.setInt(3, lic.getSaldo());
            psFunc.setInt(4, lic.getDiasDescuento());
            psFunc.setInt(5, lic.getFuncionario().getCodFunc());
            psFunc.setInt(6, lic.getAño()+1);
            a=psFunc.executeUpdate();
            if(a==1){
                alta=true;
            }
            if(cnn!=null){
                psFunc.close();
                cnn.close();
            }
            
            
            return alta;    
        } catch (ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }
    
     public boolean actualizarLicenciaFechaCargada(Licencia lic) throws SQLException {
     Connection cnn=null;
        Boolean alta=false;
        Integer a=0;
        
        try{
             cnn=conexion.Cadena();
             String insert="update PERS_LICENCIA_GENERADA set DIAS_DESCUENTO=?,DIAS_GENERADOS=? WHERE CODFUNC=? AND ANIO=?";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
             psFunc.setInt(1, lic.getDiasDescuento());
             psFunc.setInt(2, lic.getDiasGenerados());
             psFunc.setInt(3, lic.getFuncionario().getCodFunc());
             psFunc.setInt(4, lic.getAño());
             a=psFunc.executeUpdate();
             if(a==1){
                 alta=true;
             }
             if(cnn!=null){
                psFunc.close();
                cnn.close();
            }

         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
               
     }
         return alta;    
    }

    public boolean insertarMovLicencia(MovimientoLicencia Mov) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer contador=0;
        cnn=conexion.Cadena();
            cnn.setAutoCommit(false);
             String insert="insert into PERS_MOVIMIENTOS_LICENCIA(CODIGO,ANIOSALDO,FECHAINI,FECHAFIN,FECHAMOV,SALDOANIO,DIAS_TOMADOS,SALDO_POSTERIOR,CODFUNC,TIPOLIC,COD_MOV_LIC,REFERENCIA,ADELANTADA,ID_MARCA)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
               
             int ultcod=this.buscarCodigoMov();
             psFunc.setInt(1, ultcod);
             //Año de goce
             psFunc.setInt(2, Mov.getAñoSaldo());
             psFunc.setString(3, this.convertirFecha(Mov.getFechaIni()));
             psFunc.setString(4, this.convertirFecha(Mov.getFechaFin()));
             psFunc.setString(5, this.convertirFecha(Mov.getFechaMovimiento()));
             psFunc.setInt(6, Mov.getSaldoAño());
             psFunc.setInt(7, Mov.getDiasTomados());
             psFunc.setInt(8, Mov.getSaldoPos());
             psFunc.setInt(9, Mov.getFuncionario().getCodFunc());
             psFunc.setInt(10, Mov.getTipoLic());
             if(Mov.getReferencia()==0){
             psFunc.setInt(11, this.obtenerUltCodLic(Mov));
             }
             else{
              psFunc.setInt(11,0);
             }
             psFunc.setInt(12, Mov.getReferencia());
             psFunc.setInt(13, 0);
             if(Mov.getMarcaId()!=null){
             psFunc.setLong(14, Mov.getMarcaId());
             }
             else{
              psFunc.setLong(14, -1);    
             }
             psFunc.executeUpdate();
             
             if(Mov.getTipoLic()==10){
                if(this.modificarSaldoLicencia(Mov.getAñoSaldo(),Mov.getFuncionario(),Mov.getSaldoPos(),cnn)){
                contador++;
                }
             }
             if(Mov.getTipoLic()==1000){
                if(this.modificarSaldoLicencia(Mov.getAñoSaldo(),Mov.getFuncionario(),Mov.getSaldoPos(),cnn)){
                contador++;
                }
             }
             
             else{
                 contador++;
             }
             cnn.commit();
             if (contador>0){
                 alta=true;
             }
            
           if(cnn!=null){
              cnn.close();
         }
           return alta;
    }
    
    private Integer buscarCodigoMov() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Integer ultCod=null;
        cnn=conexion.Cadena();
        String consulta="SELECT MAX(CODIGO) FROM PERS_MOVIMIENTOS_LICENCIA";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            ultCod=rs.getInt("1");
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return ultCod+1;  
    }

    private boolean modificarSaldoLicencia(Integer añoSaldo, Funcionario funcionario, Integer saldoPos,Connection cnn) throws SQLException {
       boolean alta=false;
       Integer a=0;
            String insert="update PERS_LICENCIA_GENERADA set SALDO=? WHERE CODFUNC=? AND ANIO=?";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
             
             psFunc.setInt(1, saldoPos);
             psFunc.setInt(2,funcionario.getCodFunc());
             psFunc.setInt(3, añoSaldo);
             
             a=psFunc.executeUpdate();
             if(a==1){
                 alta=true;
             }
            
                psFunc.close();
               
            
             return alta;    
    }

    public int calcularSaldoAdelantado(Licencia l, Connection cnn) throws SQLException {
        String consulta="";
        Integer saldo=l.getDiasGenerados();
        Integer retorno=0;
        PreparedStatement ps=null;
        consulta="Select SUM(DIAS_TOMADOS) AS DIAS from PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? AND ANIOSALDO=? AND ADELANTADA=1";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, l.getFuncionario().getCodFunc());
        ps.setInt(2, l.getAño());
        ResultSet rs=ps.executeQuery();
           while (rs.next()){
             retorno=rs.getInt("DIAS");
           }
        retorno=saldo-retorno;
           
            ps.close();
            rs.close();
        return retorno;
    }
    public int calcularAdelantado(Integer año,Integer cod) throws SQLException, ClassNotFoundException {
        Connection cnn=null;
        String consulta="";
        cnn=conexion.Cadena();
        Integer retorno=0;
        PreparedStatement ps=null;
        consulta="Select SUM(DIAS_TOMADOS) AS DIAS from PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? AND ANIOSALDO=? AND ADELANTADA=1";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ps.setInt(2, año);
        ResultSet rs=ps.executeQuery();
           while (rs.next()){
             retorno=rs.getInt("DIAS");
           }
                   
            ps.close();
            rs.close();
        return retorno;
    }

    public MovimientoLicencia consultaAdelantado(Integer cod, Integer año)throws BDExcepcion {
        try {
            String consulta="";
            Connection cnn=null;
            cnn=conexion.Cadena();
            Integer retorno=0;
            MovimientoLicencia lic=new MovimientoLicencia();
            PreparedStatement ps=null;
            consulta="Select SUM(DIAS_TOMADOS) AS DIAS from PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? AND ANIOSALDO=? and ADELANTADA=?";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ps.setInt(2, año);
            ps.setInt(3, 1);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                retorno=rs.getInt("DIAS");
                lic.setAñoSaldo(año);
                lic.setDiasTomados(retorno);
                lic.setFuncionario(this.funcionarioParcial(cod));
            }
            if(cnn!=null){
                cnn.close();
            }           
            ps.close();
            rs.close();
            return lic;
        } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    public boolean insertarLicAdelantada(MovimientoLicencia lic,Long id) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer contador=0;
        cnn=conexion.Cadena();
             String insert="insert into PERS_MOVIMIENTOS_LICENCIA(CODIGO,ANIOSALDO,FECHAINI,FECHAFIN,FECHAMOV,DIAS_TOMADOS,CODFUNC,COD_MOV_LIC,REFERENCIA,ADELANTADA,TIPOLIC,ID_MARCA)"+"values(?,?,?,?,?,?,?,?,?,?,?,?)";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
               
             int ultcod=this.buscarCodigoMov();
             psFunc.setInt(1, ultcod);
             psFunc.setInt(2, lic.getAñoSaldo());
             psFunc.setString(3, this.convertirFecha(lic.getFechaIni()));
             psFunc.setString(4, this.convertirFecha(lic.getFechaFin()));
             psFunc.setString(5, this.convertirFecha(lic.getFechaMovimiento()));
             psFunc.setInt(6, lic.getDiasTomados());
             psFunc.setInt(7, lic.getFuncionario().getCodFunc());
             MovimientoLicencia m=new MovimientoLicencia();
             m.setFechaFin(lic.getFechaFin());
             m.setFechaIni(lic.getFechaIni());
             if(lic.getReferencia()==0){
             psFunc.setInt(8, this.obtenerUltCodLic(m));
             }
             else{
                 psFunc.setInt(8, 0);
             }
             psFunc.setInt(9,lic.getReferencia());
             psFunc.setInt(10, 1);
             psFunc.setInt(11, 1001);
             psFunc.setLong(12, id);
             contador=psFunc.executeUpdate();
             
             if (contador==1){
                 alta=true;
             }
            
           if(cnn!=null){
              cnn.close();
         }
           return alta;
    }

    

    public ArrayList<MovimientoLicencia> listarLicAdelantada(Integer cod,MovimientoLicencia mov)throws BDExcepcion{
        try {
            Connection cnn=null;
            ResultSet rs=null;
            PreparedStatement ps;
            ArrayList<MovimientoLicencia> lista=new ArrayList<>();
            cnn=conexion.Cadena();
            if(cod!=null){
                if(mov!=null){
                    String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA WHERE ANIOSALDO=? AND CODFUNC=? AND ADELANTADA=?";
                    ps=cnn.prepareStatement(consulta);
                    ps.setInt(1, mov.getAñoSaldo()+1);
                    ps.setInt(2, cod);
                    ps.setInt(3, 1);
                    rs=ps.executeQuery();
                }
                else{
                    String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? AND ADELANTADA=?";
                    ps=cnn.prepareStatement(consulta);
                    ps.setInt(1, cod);
                    ps.setInt(2, 1);
                    rs=ps.executeQuery();
                }
            }
            else{
                if(mov!=null){
                    String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA WHERE ANIOSALDO=? AND ADELANTADA=? ORDER BY CODFUNC";
                    ps=cnn.prepareStatement(consulta);
                    ps.setInt(1, mov.getAñoSaldo()+1);
                    ps.setInt(2, 1);
                    rs=ps.executeQuery();
                }
                else{
                    String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA where ADELANTADA=?";
                    ps=cnn.prepareStatement(consulta);
                    ps.setInt(1, 1);
                    rs=ps.executeQuery();
                }
                
            }
            

            while(rs.next()){
                MovimientoLicencia lic=new MovimientoLicencia();
                lic.setAñoSaldo(rs.getInt("ANIOSALDO"));
                lic.setDiasTomados(rs.getInt("DIAS_TOMADOS"));
                lic.setFechaFin(rs.getDate("FECHAFIN")); 
                lic.setFechaIni(rs.getDate("FECHAINI"));
                lic.setFechaMovimiento(rs.getDate("FECHAMOV"));
                lic.setFuncionario(this.funcionarioParcialTodos(rs.getInt("CODFUNC")));
                lic.setCodMovLic(rs.getInt("COD_MOV_LIC"));
                lic.setCodigo(rs.getInt("CODIGO"));
                lic.setReferencia(rs.getInt("REFERENCIA"));
                lista.add(lic);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return lista;
        } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
        }
         }
    
    public ArrayList<MovimientoLicencia> ultimoMovimiento(Integer cod,Integer tipo,Integer anio)throws BDExcepcion{
        try {
            Connection cnn=null;
            MovimientoLicencia lic=null;
            ArrayList<MovimientoLicencia> lista=new ArrayList<>();
            cnn=conexion.Cadena();
            PreparedStatement ps;
            ResultSet rs=null;
            String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? AND TIPOLIC=? and aniosaldo=? and REFERENCIA=0 and adelantada=? order by fechamov";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ps.setInt(2, tipo);
            ps.setInt(3, anio);
            ps.setInt(4, 0);
            rs=ps.executeQuery();
            while(rs.next()){
                lic=new MovimientoLicencia();
                lic.setAñoSaldo(rs.getInt("ANIOSALDO"));
                lic.setCodigo(rs.getInt("CODIGO"));
                lic.setDiasTomados(rs.getInt("DIAS_TOMADOS"));
                lic.setSaldoAño(rs.getInt("SALDOANIO"));
                lic.setSaldoPos(rs.getInt("SALDO_POSTERIOR"));
                lic.setFechaFin(rs.getDate("FECHAFIN")); 
                lic.setFechaIni(rs.getDate("FECHAINI"));
                lic.setFechaMovimiento(rs.getDate("FECHAMOV"));
                lic.setFuncionario(this.funcionarioParcial(rs.getInt("CODFUNC")));
                lic.setTipoLic(rs.getInt("TIPOLIC"));
                lic.setCodMovLic(rs.getInt("COD_MOV_LIC"));
                lic.setReferencia(rs.getInt("REFERENCIA"));
                lista.add(lic);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return lista;
        } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
        
    }
    
    public ArrayList<MovimientoLicencia> listadoMovimientosMarcas(Integer cod,Date desde) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        MovimientoLicencia lic=null;
        ArrayList<MovimientoLicencia> lista=new ArrayList<>();
        cnn=conexion.Cadena();
        PreparedStatement ps;
        ResultSet rs=null;
        String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? and fechaini>=? order by fechaini";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ps.setString(2, this.convertirFecha(desde));
        rs=ps.executeQuery();
        while(rs.next()){
               lic=new MovimientoLicencia();
                lic.setAñoSaldo(rs.getInt("ANIOSALDO"));
                lic.setCodigo(rs.getInt("CODIGO"));
                lic.setDiasTomados(rs.getInt("DIAS_TOMADOS"));
                lic.setSaldoAño(rs.getInt("SALDOANIO"));
                lic.setSaldoPos(rs.getInt("SALDO_POSTERIOR"));
                lic.setFechaFin(rs.getDate("FECHAFIN")); 
                lic.setFechaIni(rs.getDate("FECHAINI"));
                lic.setTipoLic(rs.getInt("TIPOLIC"));
                lic.setCodMovLic(rs.getInt("COD_MOV_LIC"));
                lic.setReferencia(rs.getInt("REFERENCIA"));
                lista.add(lic);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return lista;
        
    }
    
    public ArrayList<MovimientoLicencia> movimientoLic(Integer cod,MovimientoLicencia mov)throws BDExcepcion{
        try {
            Connection cnn=null;
            ArrayList<MovimientoLicencia> lista=new ArrayList<>();
            cnn=conexion.Cadena();
            PreparedStatement ps;
            ResultSet rs=null;
            if(cod!=null){
                if(mov==null){
                    String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? and adelantada=0 ORDER BY ANIOSALDO,FECHAMOV DESC,TIPOLIC";
                    ps=cnn.prepareStatement(consulta);
                    ps.setInt(1, cod);
                    rs=ps.executeQuery();
                }
                else{
                    String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? and adelantada=0 AND ANIOSALDO=? ORDER BY FECHAMOV DESC,TIPOLIC";
                    ps=cnn.prepareStatement(consulta);
                    ps.setInt(1, cod);
                    ps.setInt(2, mov.getAñoSaldo()+1);
                    rs=ps.executeQuery();
                }
            }
            else{
                if(mov==null){
                    String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA where adelantada=0 ORDER BY ANIOSALDO,FECHAMOV DESC,TIPOLIC";
                    ps=cnn.prepareStatement(consulta);
                    rs=ps.executeQuery();
                }
                else{
                    String consulta="SELECT * FROM PERS_MOVIMIENTOS_LICENCIA WHERE ANIOSALDO=? and adelantada=0 ORDER BY FECHAMOV DESC,TIPOLIC";
                    ps=cnn.prepareStatement(consulta);
                    ps.setInt(1, mov.getAñoSaldo()+1);
                    rs=ps.executeQuery();
                }
            }
            while(rs.next()){
                MovimientoLicencia lic=new MovimientoLicencia();
          
                lic.setCodigo(rs.getInt("CODIGO"));
                lic.setDiasTomados(rs.getInt("DIAS_TOMADOS"));
                lic.setSaldoAño(rs.getInt("SALDOANIO"));
                lic.setSaldoPos(rs.getInt("SALDO_POSTERIOR"));
                lic.setFechaFin(rs.getDate("FECHAFIN")); 
                lic.setFechaIni(rs.getDate("FECHAINI"));
                lic.setFechaMovimiento(rs.getDate("FECHAMOV"));
                lic.setFuncionario(this.funcionarioParcialTodos(rs.getInt("CODFUNC")));
                lic.setTipoLic(rs.getInt("TIPOLIC"));
                lic.setCodMovLic(rs.getInt("COD_MOV_LIC"));
                if(lic.getTipoLic()==1000){
                    lic.setAñoSaldo(rs.getInt("ANIOSALDO")-1);
                }
                else{
                    lic.setAñoSaldo(rs.getInt("ANIOSALDO"));
                }
                lista.add(lic);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return lista;
        } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }
    
       public ArrayList<MovimientoLicencia> comboMovimientoAñoLic() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        ArrayList<MovimientoLicencia> lista=new ArrayList<>();
        cnn=conexion.Cadena();
        PreparedStatement ps;
        ResultSet rs;
      
        String consulta="SELECT DISTINCT ANIOSALDO FROM PERS_MOVIMIENTOS_LICENCIA where adelantada=0 ORDER BY ANIOSALDO";
        ps=cnn.prepareStatement(consulta);
        rs=ps.executeQuery();    
        
            while(rs.next()){
                MovimientoLicencia lic=new MovimientoLicencia();
                lic.setAñoSaldo(rs.getInt("ANIOSALDO")-1);
                lista.add(lic);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         MovimientoLicencia l=new MovimientoLicencia();
         if(lista.size()>0){
         Integer año=lista.get(lista.size()-1).getAñoSaldo();
         l.setAñoSaldo(año+1);  
         lista.add(l);
         }
//         else{
//         Date hoy=new Date();
//         Integer anio=this.obtenerAño(hoy);
//         l.setAñoSaldo(anio+1);  
//         lista.add(l);
//         }
            return lista;
    }

    private ArrayList<Licencia> chequeaCambio(ArrayList<Licencia> lista, Integer año) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
            
        PreparedStatement ps=null;
        
        for(Licencia l:lista){
        consulta="Select DIAS_GENERADOS from PERS_LICENCIA_GENERADA WHERE ANIO=? AND CODFUNC=?";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, año-1);
        ps.setInt(2, l.getFuncionario().getCodFunc());
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
        int gen=l.getDiasGenerados();
        int gen2=rs.getInt("DIAS_GENERADOS");
        if(gen>gen2){
            l.setCambio(true);
        }
        
        }
        }
            if(cnn!=null){
               // ps.close();
               
                cnn.close();
            }
        
        
//        while(rs.next()){
//            int i=0;
//            boolean encuentra=false;
//                while(i<lista.size() && !encuentra){
//                    int s=this.funcionarioParcial(rs.getInt("CODFUNC")).getCodFunc();
//                    int t=lista.get(i).getFuncionario().getCodFunc();
//                       if(s==t){
//                        encuentra=true;
//                        if(rs.getInt("DIAS_GENERADOS")<lista.get(i).getDiasGenerados()){
//                           lista.get(i).setCambio(true);
//                        }
//                        }
//                       i++;
//                    }
//        }
    
    
        return lista;
}

    public ArrayList<String> cargaComboLicAñosDistintos() throws ClassNotFoundException, SQLException {
        ArrayList<String> lista=new ArrayList<>();
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        PreparedStatement ps=null;
        consulta="Select distinct(FECHAGEN) from PERS_LICENCIA_GENERADA";
        ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            String año=this.obtenerAño1(rs.getDate("FECHAGEN"));
            lista.add(año);
        }
        if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
        }
        
        return lista;
    }
    
    private String obtenerAño1(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.YEAR));
        return t;
    }

    public Boolean eliminarLicencia(Date calcular,Connection cnn) throws SQLException {

        Boolean alta=false;
        Integer a=-1;
        
        String insert="delete from PERS_LICENCIA_GENERADA WHERE FECHAGEN=? and fechaini is null and fechafin is null and saldo=dias_generados";
        PreparedStatement psFunc=cnn.prepareStatement(insert);
        psFunc.setString(1, this.convertirFecha(calcular));
        a=psFunc.executeUpdate();
        if(a>-1){
            alta=true;
        }
        psFunc.close();
         return alta;    
    }
    
    public boolean insertarFeriado(Feriado f) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer contador=0;
        cnn=conexion.Cadena();
             String insert="insert into PERS_FERIADOS(CODIGO,DESCRIPCION,FECHA,ANIO,TIPO)"+"values(?,?,?,?,?)";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
               
             int ultcod=this.buscarCodigoFeriado();
             psFunc.setInt(1, ultcod);
             psFunc.setString(2, f.getDescripcion());
             psFunc.setString(3, this.convertirFecha(f.getFecha()));
             psFunc.setInt(4, f.getAnio());
             psFunc.setInt(5, f.getTipo());
             contador=psFunc.executeUpdate();
             cnn.commit();
             if (contador==1){
                 alta=true;
             }
            
           if(cnn!=null){
              cnn.close();
         }
           return alta;
    }
    
    public ArrayList<Feriado> listarFeriados(Integer año) throws SQLException, ClassNotFoundException, ParseException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer retorno=0;
        ArrayList<Feriado> feriados=new ArrayList<>();    
        Feriado f=null;
        PreparedStatement ps=null;
        consulta="Select * FROM PERS_FERIADOS WHERE ANIO=? ORDER BY FECHA";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, año);
        ResultSet rs=ps.executeQuery();
           while (rs.next()){
              f=new Feriado();
              f.setAnio(rs.getInt("ANIO"));
              f.setCodigo(rs.getInt("CODIGO"));
              f.setDescripcion(rs.getString("DESCRIPCION"));
              f.setFecha(rs.getDate("FECHA"));
              f.setTipo(rs.getInt("TIPO"));
              feriados.add(f);
           }
            if(cnn!=null){
                cnn.close();
            }           
            ps.close();
            rs.close();
        return feriados;
    }
    
    private int buscarCodigoFeriado() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Integer ultCod=null;
        cnn=conexion.Cadena();
        String consulta="SELECT MAX(CODIGO) FROM PERS_FERIADOS";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            ultCod=rs.getInt("1");
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return ultCod+1;  
    }
    
    public ArrayList<String> cargaComboFeriadoAñosDistintos() throws ClassNotFoundException, SQLException {
        ArrayList<String> lista=new ArrayList<>();
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        PreparedStatement ps=null;
        consulta="Select distinct(ANIO) from PERS_FERIADOS ORDER BY ANIO DESC ";
        ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            String año=String.valueOf(rs.getInt("ANIO"));
            lista.add(año);
        }
        if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
        }
        
        return lista;
    }

    public boolean modificarFeriado(Feriado f) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer contador=0;
        cnn=conexion.Cadena();
             String insert="update PERS_FERIADOS SET CODIGO=?,DESCRIPCION=?,FECHA=?,ANIO=?,TIPO=? WHERE CODIGO=?";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
               
             
             psFunc.setInt(1, f.getCodigo());
             psFunc.setString(2, f.getDescripcion());
             psFunc.setString(3, this.convertirFecha(f.getFecha()));
             psFunc.setInt(4, f.getAnio());
             psFunc.setInt(5, f.getTipo());
             psFunc.setInt(6, f.getCodigo());
             
             contador=psFunc.executeUpdate();
             cnn.commit();
             if (contador==1){
                 alta=true;
             }
            
           if(cnn!=null){
              cnn.close();
         }
           return alta;
    }

    public boolean eliminarFeriado(Feriado f) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer contador=0;
        cnn=conexion.Cadena();
             String insert="DELETE FROM PERS_FERIADOS WHERE CODIGO=?";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
             psFunc.setInt(1, f.getCodigo());
             contador=psFunc.executeUpdate();
             cnn.commit();
             if (contador==1){
                 alta=true;
             }
            
           if(cnn!=null){
              cnn.close();
         }
           return alta;
    }

    public ArrayList<Feriado> listarTodosFeriados() throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer retorno=0;
        ArrayList<Feriado> feriados=new ArrayList<>();    
        Feriado f=null;
        PreparedStatement ps=null;
        consulta="Select * FROM PERS_FERIADOS ORDER BY FECHA";
        ps=cnn.prepareStatement(consulta);
       
        ResultSet rs=ps.executeQuery();
           while (rs.next()){
              f=new Feriado();
              f.setAnio(rs.getInt("ANIO"));
              f.setCodigo(rs.getInt("CODIGO"));
              f.setDescripcion(rs.getString("DESCRIPCION"));
              f.setFecha(rs.getDate("FECHA"));
              f.setTipo(rs.getInt("TIPO"));
              feriados.add(f);
           }
            if(cnn!=null){
                cnn.close();
            }           
            ps.close();
            rs.close();
        return feriados;
    }
    
    public ArrayList<Feriado> listarFeriadosRango(Date desde, Date hasta) throws ClassNotFoundException, SQLException {
        String consulta="";
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer retorno=0;
        ArrayList<Feriado> feriados=new ArrayList<>();    
        Feriado f=null;
        PreparedStatement ps=null;
        consulta="Select * FROM PERS_FERIADOS where fecha>=? and fecha<=? ORDER BY FECHA";
        ps=cnn.prepareStatement(consulta);
        ps.setString(1, this.convertirFecha(desde));
        ps.setString(2, this.convertirFecha(hasta));
       
       
        ResultSet rs=ps.executeQuery();
           while (rs.next()){
              f=new Feriado();
              f.setAnio(rs.getInt("ANIO"));
              f.setCodigo(rs.getInt("CODIGO"));
              f.setDescripcion(rs.getString("DESCRIPCION"));
              f.setFecha(rs.getDate("FECHA"));
              f.setTipo(rs.getInt("TIPO"));
              feriados.add(f);
           }
            if(cnn!=null){
                cnn.close();
            }           
            ps.close();
            rs.close();
        return feriados;
    }

    public boolean actualizarLicenciaActual(Integer d, Licencia l) throws SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer a=0;
        cnn.setAutoCommit(false);
        Date hoy=new Date();
        try{
             cnn=conexion.Cadena();
             String insert="update PERS_LICENCIA_GENERADA set SALDO=? WHERE CODFUNC=? AND FECHAGEN=?";
             PreparedStatement psFunc=cnn.prepareStatement(insert);
             psFunc.setInt(1, l.getSaldo()+d);
             psFunc.setInt(2,l.getFuncionario().getCodFunc());
             psFunc.setString(3, this.convertirFecha(l.getFechaGen()));
             a=psFunc.executeUpdate();
             if(a==1){
                 MovimientoLicencia Mov=new MovimientoLicencia();
                 Mov.setAñoSaldo(l.getAño());
                 Mov.setDiasTomados(d*-1);
                 Mov.setFechaMovimiento(hoy);
                 Mov.setFuncionario(l.getFuncionario());
                 Mov.setSaldoAño(l.getSaldo());
                 Mov.setSaldoPos(l.getSaldo()+d);
                 this.insertarMovLicencia(Mov);
                 alta=true;
             }
             if(cnn!=null){
                psFunc.close();
                cnn.close();
            }

         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
               
     }
         return alta;    
        
    }

    public Integer saldoLicenciaCodigo(Integer codF, Integer cod) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer resultado=0;
        Date d=new Date();
        Integer año=this.obtenerAño(d);
        String consulta="SELECT SUM(DIAS_TOMADOS) as TOTAL FROM PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? AND TIPOLIC=? AND ANIOSALDO=? and adelantada=0";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, codF);
        ps.setInt(2, cod);
        ps.setInt(3, año);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            resultado=rs.getInt("TOTAL");
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return resultado;  
    }

    private int obtenerUltCodLic(MovimientoLicencia Mov) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer resultado=0;
        PreparedStatement ps=null;
        ResultSet rs=null;
        if(Mov.getFechaIni()==null&&Mov.getFechaFin()==null){
            resultado=0;
        }
        else{
        this.incrementarCodigoLic("LICENCIA");
        String consulta="SELECT * FROM PERS_LIC_COD_PDF WHERE DESCRIPCION=?";
        ps=cnn.prepareStatement(consulta);
        ps.setString(1, "LICENCIA");
        rs=ps.executeQuery();
             while(rs.next()){
                resultado=rs.getInt("CODIGO");
             }
             
        }
        if(cnn!=null){
//                ps.close();
//                rs.close();
                cnn.close();
            }
        return resultado;
    }

    public CodigoPdf obtenerUltCod(String desc) throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        CodigoPdf c=null;    
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_LIC_COD_PDF WHERE DESCRIPCION=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, desc);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new CodigoPdf();
            c.setCodigo(rs.getInt("CODIGO"));
            c.setDescripcion(rs.getString("DESCRIPCION").trim());
            c.setFecha(rs.getDate("FECHA"));
          
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return c;
    }
       
    
    private boolean incrementarCodigoLic(String desc) throws SQLException, ClassNotFoundException {
       Connection cnn=null;
        boolean update=false;
        Integer contador=0;
        CodigoPdf c=this.obtenerUltCod(desc);
        Date hoy=new Date();
        PreparedStatement ps;
        cnn=conexion.Cadena();
        if(this.obtenerAño(hoy)>this.obtenerAño(c.getFecha())){
            String consulta="Update PERS_LIC_COD_PDF set FECHA=?,CODIGO=? WHERE DESCRIPCION=?";
            ps=cnn.prepareStatement(consulta);
            ps.setString(1, this.convertirFecha(hoy));
            ps.setInt(2,5000);
            ps.setString(3, desc);
            contador=ps.executeUpdate();
        }
        else{
            String consulta="Update PERS_LIC_COD_PDF set FECHA=?,CODIGO=? WHERE DESCRIPCION=?";
            ps=cnn.prepareStatement(consulta);
            String fecha=this.convertirFecha(hoy);
            ps.setString(1, fecha);
            Integer in=c.getCodigo()+1;
            ps.setInt(2,in);
            ps.setString(3, desc);
            contador=ps.executeUpdate();
        }       
               
            if(contador==1){
                update=true;
            }
            if(cnn!=null){
                ps.close();
                cnn.close();
            }
         return update;
    }
    
    private Integer obtenerAño(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        Integer t=Integer.valueOf(calendar.get(Calendar.YEAR));
        return t;
    }
    
    
    public ArrayList<Fallecimiento> cargaComboFalle() throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        Fallecimiento c=null;
        cnn=conexion.Cadena();
        ArrayList<Fallecimiento> listaFalle=new ArrayList<>();
        String consulta="Select * from PERS_FALLECIMIENTO_LICENCIA ORDER BY CODIGO";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            c=new Fallecimiento();
            c.setCodigo(rs.getInt("CODIGO"));
            c.setParentesco(rs.getString("PARENTESCO"));
            c.setValor(rs.getInt("VALOR"));
            listaFalle.add(c);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return listaFalle;
    }

    public void insertarAltaAuditoria(String info, Connection cnn,String desc,Integer codFunc) throws SQLException, ClassNotFoundException {
        this.frm=frmPrin.instancia();
        String responsable=this.frm.getUsuario().getNombre();
        Date fecha=new Date();
        java.sql.Timestamp des = this.convertirATimestamp(fecha);
        String insert="insert into PERS_AUDITORIA(ID,DESCRIPCION,DESPUES,FECHA,RESPONSABLE,COD_FUNC)"+"values(?,?,?,?,?,?)";
            PreparedStatement psFunc=cnn.prepareStatement(insert);
             int ultcod=this.buscarCodigoAudit(cnn);
             psFunc.setInt(1, ultcod);
             psFunc.setString(2, desc);
             psFunc.setString(3, info);
             psFunc.setTimestamp(4, des);
             psFunc.setString(5, responsable);
             psFunc.setInt(6,codFunc);
             psFunc.executeUpdate();
             
          psFunc.close();
          
    }
    
    
    private Timestamp convertirATimestamp(Date date){
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(date.getTime());
    Timestamp ts = new java.sql.Timestamp(date.getTime());
    ts.setTime(c.getTimeInMillis());
    return ts;
    }
    
    
    private int buscarCodigoAudit(Connection cnn) throws ClassNotFoundException, SQLException {
        Integer ultCod=null;
        String consulta="SELECT MAX(ID) FROM PERS_AUDITORIA";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            ultCod=rs.getInt("1");
            }        
                ps.close();
                rs.close();
        
            return ultCod+1;  
    }

    public void insertarModAuditoria(String nuevo, String viejo, Connection cnn, String desc, Integer codFunc) throws ClassNotFoundException, SQLException {
    this.frm=frmPrin.instancia();
        String responsable=this.frm.getUsuario().getNombre();
        Date fecha=new Date();
        java.sql.Timestamp des = this.convertirATimestamp(fecha);
        String insert="insert into PERS_AUDITORIA(ID,DESCRIPCION,ANTES,DESPUES,FECHA,RESPONSABLE,COD_FUNC)"+"values(?,?,?,?,?,?,?)";
            PreparedStatement psFunc=cnn.prepareStatement(insert);
             int ultcod=this.buscarCodigoAudit(cnn);
             psFunc.setInt(1, ultcod);
             psFunc.setString(2, desc);
             psFunc.setString(3, viejo);
             psFunc.setString(4, nuevo);
             psFunc.setTimestamp(5, des);
             psFunc.setString(6, responsable);
             psFunc.setInt(7,codFunc);
             psFunc.executeUpdate();
             
          psFunc.close();    
    }

    public boolean existeCodigoDesvinc(Integer cod) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        boolean existe=false;
        String consulta="Select * from PERS_CODIGOS_DESVINCULACION WHERE ID=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
                existe=true;
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return existe;
    }
    
    public boolean CodigoDesvincUsado(Integer cod) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        boolean existe=false;
        String consulta="Select COD_DESVINCULACION from PERS_FUNCIONARIOS WHERE COD_DESVINCULACION=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
                existe=true;
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return existe;
    }

    public boolean altaCodigoDesvinc(CodigoDesvinc cod) throws SQLException {
        Connection cnn=null;
        Boolean alta=false;
        Integer i=0;
     
         try{
             cnn=conexion.Cadena();
             String insert="insert into PERS_CODIGOS_DESVINCULACION(ID,DESCRIPCION)"+"values(?,?)";
             PreparedStatement ps=cnn.prepareStatement(insert);
             ps.setInt(1, cod.getId() );
             ps.setString(2, cod.getDescripcion());
             i=ps.executeUpdate();
             if(i==1){
             alta=true;
             }
         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
                if(cnn!=null){
                   cnn.close();
                   
         }
     }
         return alta;
    }

    public Integer modificaCodDesvinc(Integer idVieja, CodigoDesvinc codi) throws SQLException {
    
        Connection cnn=null;
        Integer i=0;
        try{
             cnn=conexion.Cadena();
           
                    if(CodigoDesvincUsado(idVieja)==false){
                       String modifica="UPDATE PERS_CODIGOS_DESVINCULACION set ID=?, DESCRIPCION=? where ID=?";
                       PreparedStatement ps=cnn.prepareStatement(modifica);
                       ps.setInt(1, codi.getId());
                       ps.setString(2, codi.getDescripcion());
                       ps.setInt(3, idVieja);
                       i=ps.executeUpdate();

                    }
                    else{
                    String modifica="UPDATE PERS_CODIGOS_DESVINCULACION set DESCRIPCION=? where ID=?";
                       PreparedStatement ps=cnn.prepareStatement(modifica);
                       ps.setString(1, codi.getDescripcion());
                       ps.setInt(2, idVieja);
                       i=ps.executeUpdate();
                       if(i==1){
                       i=5;
                       }    
                    }
             
                 
             
         }
         catch(ClassNotFoundException e1){
            i=10;
         }
         finally{
                if(cnn!=null){
                        cnn.close();
         }
     }
         return i;
        
    }

    public boolean bajaCodigoDesvinc(Integer id) throws SQLException {
        Connection cnn=null;
        Boolean baja=false;
        Integer i=0;
     
         try{
             cnn=conexion.Cadena();
             String insert="delete from PERS_CODIGOS_DESVINCULACION where id=?";
             PreparedStatement ps=cnn.prepareStatement(insert);
             ps.setInt(1, id);
             i=ps.executeUpdate();
             if(i==1){
             baja=true;
             }
         }
         catch(ClassNotFoundException e1){
            
         }
         finally{
                if(cnn!=null){
                   cnn.close();
                   
         }
     }
         return baja;
    }
       public String CodigoDesvincDesc(Integer cod) throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        String existe="";
        String consulta="Select DESCRIPCION from PERS_CODIGOS_DESVINCULACION WHERE ID=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, cod);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
                existe=rs.getString("DESCRIPCION");
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return existe;
    }
    
    

    private CodigoDesvinc buscarCodigoDesvinc(int cod) throws SQLException, ClassNotFoundException {
    if(codigosDes==null){
    combo=new PersistenciaCombos();
    this.codigosDes=combo.cargaComboCodigoDesvinc();
    }
    CodigoDesvinc c=null;
        Integer i=0;
        Boolean bo=false;
        while(i<codigosDes.size()&&!bo){
            if(codigosDes.get(i).getId().equals(cod)){
                c=codigosDes.get(i);
                bo=true;
                      
            }
            i++;
        }
        return c;
    }
    
    
    public String fechaLiquidacion() throws ClassNotFoundException, SQLException{
      Connection cnn=null;
        cnn=conexion.Cadena();
        String existe="";
        String consulta="Select VALOR from PERS_PARAMETROS WHERE NOMBRE=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, "FECHALIQ");
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
                existe=rs.getString("VALOR").trim();
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return existe;  
    }
    
    public String cargoParametro(String nombre) throws ClassNotFoundException, SQLException{
      Connection cnn=null;
        cnn=conexion.Cadena();
        String existe="";
        String consulta="Select VALOR from PERS_PARAMETROS WHERE NOMBRE=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, nombre);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
                existe=rs.getString("VALOR");
            }        
            if(existe!=null){
                if(!"".equals(existe)){
                    existe = existe.trim();
                }
            }else{
                existe="";
            }
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return existe;  
    }

    public Integer obtenerFaltas(Funcionario f, Integer añoLiq,Date fechaAux,Connection cnn) throws SQLException {
        Integer faltas=0;
        String consulta="Select sum(cantidad) as total from pers_hist_liquidaciones where tipoliq=1 and (cod_mov=18 or cod_mov=25 or cod_mov=26) and cod_func=? and (year(fechaliq)=?) and fechaliq>?";
        
//        busco = "select sum(cantidad) as total from pers_hist_liquidaciones " & _
//            "where ((tipoliq=1) and (cod_mov=18 or cod_mov=25 or cod_mov=26) " & _
//            " and (cod_func=" & codfun & ") and (year(fechaliq)=" & año & ") " & _
//            " and (fechaliq>'" & fechaux & "'))"
            
        
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setInt(1, f.getCodFunc());
        ps.setInt(2, añoLiq);
        ps.setString(3, this.convertirFecha(fechaAux));
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
                faltas=rs.getInt("total");
            }        
           
                ps.close();
                rs.close();
         
         return faltas;  
    }
    
    
    ///DECLARACIONES
    
    public Declaracion cargoDeclaracion(Integer codFunc){
       Declaracion d = null;
        try {
            Connection cnn=null;
            cnn=conexion.Cadena();
            String consulta="Select * from PERS_DECLARACIONES WHERE CODFUNC=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                d = new Declaracion();
                d.setCodFunc(rs.getInt("CODFUNC"));
                d.setTipoDoc(rs.getString("TIPODOC"));
                d.setNroDoc(rs.getString("NRODOC"));
                d.setPais(rs.getString("PAIS"));
                d.setVigencia(rs.getInt("VIGENCIA"));
                d.setPersonasACargo(rs.getInt("PERSONASACARGO"));
                d.setCatcjpu(rs.getInt("CATCJPU"));
                d.setReintcjpu(rs.getDouble("REINTCJPU"));
                d.setFondosolcjpu(rs.getString("FONDOSOLCJPU"));
                d.setAdicionalcjpu(rs.getInt("ADICIONALCJPU")); 
                d.setMinimoimp(rs.getInt("MINNOIMP"));
                d.setFechaRecepcion(rs.getDate("FECHARECEPCION"));
                d.setTipoDocConyu(rs.getString("TIPODOCCONYU"));
                d.setNroDocConyu(rs.getString("NRODOCCONYU"));
                d.setPaisConyu(rs.getString("PAISCONYU"));
                d.setApellidoUnoConyu(rs.getString("APELLIDO1CONYU"));
                d.setApellidoDosConyu(rs.getString("APELLIDO2CONYU"));
                d.setNombreUnoConyu(rs.getString("NOMBRE1CONYU"));
                d.setNombreDosConyu(rs.getString("NOMBRE2CONYU"));
                d.setFechaNacConyu(rs.getDate("FECHA_NACCONYU"));
                d.setNacionalConyu(rs.getString("NACIONALIDADCONYU"));
                String sexoConyu = rs.getString("SEXOCONYU");
                if(sexoConyu!=null){
                d.setSexoConyu(rs.getString("SEXOCONYU").charAt(0));
                }
                else{
                d.setSexoConyu(' ');    
                }
                d.setFamiliar(rs.getInt("FAMILIAR"));
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
    
     public ArrayList<PersonasACargo> cargoPersonas(Integer codFunc){
      ArrayList<PersonasACargo> lista = new ArrayList<>();
        try {
            Connection cnn=null;
            cnn=conexion.Cadena();
            String consulta="select * from pers_persacargo WHERE CODFUNC=? order by ordinal";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, codFunc);
            
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                PersonasACargo p = new PersonasACargo();
                Relacion r = new Relacion();
                p.setCodFunc(rs.getInt("CODFUNC"));
                p.setOrdinal(rs.getInt("ORDINAL"));
                p.setTipoDoc(rs.getString("TIPODOC"));
                p.setNroDoc(rs.getString("NRODOC"));
                p.setPais(rs.getString("PAIS"));
                p.setFecha_nac(rs.getDate("FECHA_NAC"));
                p.setNacionalidad(rs.getString("NACIONALIDAD"));
                p.setSexo(rs.getString("SEXO").charAt(0)); 
                p.setRelacion(rs.getString("RELACION").charAt(0));
                p.setSistSalud(rs.getString("SISTSALUD"));
                p.setPjedist(rs.getInt("PJEDIST"));
                p.setDiscapacidad(rs.getInt("DISCAPACIDAD"));
                p.setApellidoUno(rs.getString("APELLIDO1"));
                p.setApellidoDos(rs.getString("APELLIDO2"));
                p.setNombreUno(rs.getString("NOMBRE1"));
                p.setNombreDos(rs.getString("NOMBRE2"));
                lista.add(p);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public Integer insertaPersonasAcargo(PersonasACargo p, Integer codFunc, Integer ordinal, Connection cnn) {
         Integer a=0;
        try {
               
            String insert="insert into PERS_PERSACARGO(CODFUNC,ORDINAL,TIPODOC,NRODOC,PAIS,FECHA_NAC,NACIONALIDAD,SEXO,RELACION,SISTSALUD,PJEDIST,DISCAPACIDAD,APELLIDO1,APELLIDO2,NOMBRE1,NOMBRE2)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement psFunc=cnn.prepareStatement(insert);
            
            psFunc.setInt(1, codFunc);
            psFunc.setInt(2, ordinal);
            psFunc.setString(3, p.getTipoDoc().trim());
            psFunc.setString(4,p.getNroDoc().trim());
            psFunc.setString(5, p.getPais().trim());
            psFunc.setString(6, this.convertirFecha(p.getFecha_nac()));
            psFunc.setString(7, p.getNacionalidad().trim());
            psFunc.setString(8, p.getSexo().toString().trim());
            psFunc.setString(9, p.getRelacion().toString().trim());
            psFunc.setString(10, p.getSistSalud().trim());
            psFunc.setInt(11, p.getPjedist());
            psFunc.setInt(12, p.getDiscapacidad());
            psFunc.setString(13, p.getApellidoUno().trim());
            if(p.getApellidoDos()!=null){
            psFunc.setString(14, p.getApellidoDos().trim());
            }
            else{
            psFunc.setString(14, p.getApellidoDos());
            }
            psFunc.setString(15, p.getNombreUno().trim());
            if(p.getNombreDos()!=null){
            psFunc.setString(16, p.getNombreDos().trim());
            }
            else{
             psFunc.setString(16, p.getNombreDos());    
            }
            a=psFunc.executeUpdate();
            
            psFunc.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
               
    }
            
                        
            
           
  

    public boolean ingresoDeclaracion(Declaracion d, ArrayList<PersonasACargo> lista)throws BDExcepcion {
        Boolean alta=false;
         Integer a=0;
        try {
            Connection cnn=null;
            PreparedStatement ps = null;
            cnn=conexion.Cadena();
            cnn.setAutoCommit(false);
                this.borrarDeclaracion(d.getCodFunc(), cnn);
            
                String insert="insert into PERS_DECLARACIONES(CODFUNC,TIPODOC,NRODOC,PAIS,VIGENCIA,PERSONASACARGO,CATCJPU,REINTCJPU,FONDOSOLCJPU,ADICIONALCJPU,MINNOIMP,FECHARECEPCION,TIPODOCCONYU,NRODOCCONYU,PAISCONYU,APELLIDO1CONYU,APELLIDO2CONYU,NOMBRE1CONYU,NOMBRE2CONYU,FECHA_NACCONYU,NACIONALIDADCONYU,SEXOCONYU,FAMILIAR)"+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps=cnn.prepareStatement(insert);
                
                ps.setInt(1, d.getCodFunc());
                ps.setString(2, d.getTipoDoc());
                ps.setString(3, d.getNroDoc());
                ps.setString(4, d.getPais());
                ps.setInt(5, d.getVigencia());
                ps.setInt(6, d.getPersonasACargo());
                ps.setInt(7, d.getCatcjpu());
                ps.setDouble(8, d.getReintcjpu());
                ps.setString(9, d.getFondosolcjpu());
                ps.setInt(10, d.getAdicionalcjpu());
                ps.setInt(11, d.getMinimoimp());
                ps.setString(12, this.convertirFecha(d.getFechaRecepcion()));
                ps.setString(13, d.getTipoDocConyu());
                ps.setString(14, d.getNroDocConyu());
                ps.setString(15, d.getPaisConyu());
                ps.setString(16, d.getApellidoUnoConyu());
                ps.setString(17, d.getApellidoDosConyu());
                ps.setString(18, d.getNombreUnoConyu());
                ps.setString(19, d.getNombreDosConyu());
                if(d.getFechaNacConyu()!=null){
                ps.setString(20, this.convertirFecha(d.getFechaNacConyu()));
                }
                else{
                ps.setString(20, null);    
                }
                ps.setString(21, d.getNacionalConyu());
                if(d.getSexoConyu()!=null){
                ps.setString(22, String.valueOf(d.getSexoConyu()));
                }
                else{
                ps.setString(22, null);
                }
                if(d.getFamiliar()==null){
                     ps.setInt(23, 0);
                }else{
                ps.setInt(23, d.getFamiliar());
                }
                a=ps.executeUpdate();
            
          
                if(lista!=null){
                    if(lista.size()>0){
                        this.borrarPersonasACargo(d.getCodFunc(), cnn);
                        int ordinal=1;
                        int contador =0;
                        for(PersonasACargo p:lista){
                            contador+=this.insertaPersonasAcargo(p, d.getCodFunc(), ordinal, cnn);
                            ordinal++;
                        }
                    }
                }
                
                cnn.commit();
                if(cnn!=null){
                    cnn.close();
                    ps.close();
                }
                if(a>0){
                    alta=true;
                }
                
               } catch (ClassNotFoundException | SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
        return alta;
    }
    
    
    public Integer borrarPersonasACargo(Integer codFunc, Connection cnn){
          Integer a = 1;
        try {
            String delete = "DELETE FROM PERS_PERSACARGO WHERE CODFUNC=?";
            PreparedStatement ps=cnn.prepareStatement(delete);
            ps.setInt(1, codFunc);
           a+=ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }
    
    public Integer borrarDeclaracion(Integer codFunc, Connection cnn){
     Integer a = 0;
        try {
            String delete = "DELETE FROM PERS_DECLARACIONES WHERE CODFUNC=?";
            PreparedStatement ps=cnn.prepareStatement(delete);
            ps.setInt(1, codFunc);
            a=ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }
    
     public Integer actualizaSalario(ArrayList<Funcionario> listado, String antiguedad,String porcentaje,Integer tipo){
       int count = 0;
         try {
                       
            Connection cnn=conexion.Cadena();
            cnn.setAutoCommit(false);
                              
            String sql = "update pers_funcionarios set SUELDO_CARGO=? WHERE CODIGO=?";
            PreparedStatement ps=cnn.prepareStatement(sql.toUpperCase());
            final int batchSize = 500;
            for (Funcionario f: listado) {
                
                ps.setDouble(1, f.getSueldoCargo());
                ps.setInt(2,f.getCodFunc());           
                ps.addBatch();
                if(tipo==1){
                this.insertarModAuditoria(f.getSueldoCargo().toString(), f.getCuenta().toString(), cnn, "Aumento salarial del "+porcentaje+ "%", f.getCodFunc());
                }
                else{
                this.insertarModAuditoria(f.getSueldoCargo().toString(), f.getCuenta().toString(), cnn, "Decremento salarial del "+porcentaje+ "%", f.getCodFunc());    
                }
                if(++count % batchSize == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch(); 
            this.actualizaAntiguedad(antiguedad,cnn);
            cnn.commit();
            ps.close();
            cnn.close();
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
         return count;
    }
     
     
      public String valorDeAntiguedad() throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        cnn=conexion.Cadena();
        String ret="";
        String consulta="Select VALOR from PERS_PARAMETROS WHERE NOMBRE=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, "ANTIGUEDAD");
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
                ret=rs.getString("VALOR").trim();
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return ret;  
    }

    private void actualizaAntiguedad(String str,Connection cnn) {
        try {
            String sql = "update pers_parametros set valor=? WHERE nombre=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
             ps.setString(1, str);
             ps.setString(2, "ANTIGUEDAD");
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean actualizaParametros(String valorNuevo, String valorViejo, String nombre)throws BDExcepcion {
      boolean cambio=false;
        try {
            Connection cnn=null;
            cnn=conexion.Cadena();
            cnn.setAutoCommit(false);
            String sql = "update pers_parametros set valor=? WHERE nombre=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, valorNuevo);
            ps.setString(2, nombre);
            if(ps.executeUpdate()==1){
                cambio=true;
                this.insertarModAuditoria(valorNuevo,valorViejo, cnn, "Actualiza parametro "+nombre, 0);
            }
            cnn.commit();
            if(cnn!=null){
                ps.close();
                cnn.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
        return cambio;
    }
    
    public boolean actualizaParametro(String valor, String nombre)throws BDExcepcion {
      boolean cambio=false;
        try {
            Connection cnn=null;
            cnn=conexion.Cadena();
            cnn.setAutoCommit(false);
            String sql = "update pers_parametros set valor=? WHERE nombre=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, valor);
            ps.setString(2, nombre);
            if(ps.executeUpdate()==1){
                cambio=true;
            }
            cnn.commit();
            if(cnn!=null){
                ps.close();
                cnn.close();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
        return cambio;
    }

    
    //VALES
    public String cargoCuentaAce() {
        String ret="";
        try {
            Connection cnn=null;
            cnn=conexion.Cadena();
            
            String consulta="Select VALOR from PERS_PARAMETROS WHERE NOMBRE=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, "CUENTAABN");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                ret=rs.getString("VALOR").trim();
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
          
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
          return ret.trim();  
    }

    public String cargoRutAce() {
       String ret="";
        try {
            Connection cnn=null;
            cnn=conexion.Cadena();
            
            String consulta="Select VALOR from CFE_PARAMETROS WHERE IDENTIFICADOR=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, "Rut Emisor");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                ret=rs.getString("VALOR").trim();
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
          
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
          return ret.trim();  
    }

    private Banco buscarBancoVale(int cod, Connection cnn) {
        Banco b=null;
        try {
            String consulta="Select sucursal,codigo_bcu,nombre from PERS_BANCOS WHERE CODIGO=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                b = new Banco();
                b.setSucursal(rs.getInt("SUCURSAL"));
                b.setCodBcu(rs.getString("CODIGO_BCU"));
                b.setNombre(rs.getString("NOMBRE"));
            }
            
            ps.close();
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public boolean borrarLicAdelantada(Long id, Integer funCod, Integer codigo) {
         Boolean alta=false;
        try {
            Connection cnn=null;
           
            Integer i=-1;
            
            cnn=conexion.Cadena();
          
            String borrar="DELETE FROM PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? and TIPOLIC=? AND ID_MARCA=?";
            PreparedStatement ps=cnn.prepareStatement(borrar);
            ps.setInt(1, funCod);
            ps.setInt(2, codigo);
            ps.setLong(3, id);
            i=ps.executeUpdate();
            if(i==1){
                alta=true;
                
            }
            
            
            
            if(cnn!=null){
                cnn.close();
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return alta;
    }

    private Licencia cargoSaldoAñoCorriente(Connection cnn, Integer cod)throws BDExcepcion {
        try {
            String consulta="";
            Date d=new Date();
            Integer año=Integer.valueOf(this.obtenerAño1(d));
            Licencia l=null;
            PreparedStatement ps=null;
            consulta="Select * from PERS_LICENCIA_GENERADA WHERE CODFUNC=? and SALDO>0 and anio=? and fechaini<? order by ANIO";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ps.setInt(2, año);
            ps.setString(3, this.convertirFecha(d));
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                l=new Licencia();
                l.setAño(rs.getInt("ANIO")-1);
                l.setFechaIni(rs.getDate("FECHAINI"));
                l.setFechaFin(rs.getDate("FECHAFIN"));
                l.setId(rs.getInt("CODIGO"));
                l.setSaldo(rs.getInt("SALDO"));
                l.setFuncionario(this.funcionarioParcial(rs.getInt("CODFUNC")));
            }
            
            ps.close();
            rs.close();
            return l;
        } catch (SQLException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

    public Integer obtieneAñoSaldo(Long id, Integer funCod, Integer codigo) throws ClassNotFoundException, SQLException {
        Integer i=null;
        Connection cnn=null;
        MovimientoLicencia lic=null;
        ArrayList<MovimientoLicencia> lista=new ArrayList<>();
        cnn=conexion.Cadena();
        PreparedStatement ps;
        ResultSet rs=null;
        String consulta="SELECT aniosaldo FROM PERS_MOVIMIENTOS_LICENCIA WHERE CODFUNC=? AND TIPOLIC=? and ID_MARCA=?";
        ps=cnn.prepareStatement(consulta);
        ps.setInt(1, funCod);
        ps.setInt(2, codigo);
        ps.setLong(3, id);
        rs=ps.executeQuery();
        while(rs.next()){
               
               i=rs.getInt("ANIOSALDO");
                
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return i;
    }
    
    public ArrayList<Parametro> cargaParametros()throws BDExcepcion{
        try {
            ArrayList<Parametro> parametros = new ArrayList<>();
            Connection cnn=null;
            Parametro p=null;
            cnn=conexion.Cadena();
            PreparedStatement ps;
            ResultSet rs=null;
            String consulta="select * from pers_parametros where modificable=1 order by NOMBRE";
            ps=cnn.prepareStatement(consulta);
            rs=ps.executeQuery();
            while(rs.next()){
                p=new Parametro();
                p.setNombre(rs.getString("NOMBRE").trim());
                p.setValor(rs.getString("VALOR").trim());
                p.setModificable(rs.getInt("MODIFICABLE"));
                parametros.add(p);
            }
            
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            return parametros;
        } catch (ClassNotFoundException ex) {
            throw new BDExcepcion(ex.getMessage());
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

  

    boolean estaActivo(String trim, Connection cnn)throws BDExcepcion {
         try {
            boolean ret=false;
            PreparedStatement ps;
            ResultSet rs=null;
            Integer cod = Integer.parseInt(trim);
            String consulta="select * from pers_funcionarios where codigo=? and fecha_egreso is null";
            ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            rs=ps.executeQuery();
            if(rs.next()){
                ret = true;
            }
            else{
                ret= false;
            }
            
         
            return ret;
        } catch (SQLException ex) {
            throw new BDExcepcion(ex.getMessage());
        }
    }

    private Cargo buscoCargo(Connection cnn, int cod)throws BDExcepcion {
   
        try {
            Cargo cargo=null;
            String consulta="Select * from PERS_CARGOS where codigo=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, cod);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                cargo=new Cargo();
                cargo.setCodigo(rs.getInt("CODIGO"));
                cargo.setNombre(rs.getString("NOMBRE").trim());
                
            }
            
            ps.close();
            rs.close();
            

            
            return cargo;
        } catch (SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
        }
    }

    public Date fechaLiquidacionEnLiq()throws BDExcepcion {
         try {
            ArrayList<Liquidacion> liqs=new ArrayList<>();
            Connection cnn=null;
            cnn=Conexion.Cadena();
            Date fecha=null;    
            String consulta="select FECHA from pers_liquidaciones";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            
            ResultSet rs=ps.executeQuery();
          
            if (rs.next()) {
               fecha=rs.getDate("FECHA");
            }
            
            
            ps.close();
            rs.close();
            cnn.close();
            
            return fecha;
        } catch (ClassNotFoundException | SQLException ex) {
             throw new BDExcepcion(ex.getMessage());
        }
    }

    public boolean fechaLiquidacionHistorica(String fechaLiq)throws BDExcepcion {
       try {
            boolean ret=true;
            Connection cnn=null;
            cnn=Conexion.Cadena();
            String sql = "select * from pers_hist_liquidaciones where fechaliq=?";
            PreparedStatement ps=cnn.prepareStatement(sql);
            ps.setString(1, fechaLiq);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                ret=false;
            }
            ps.close();
            rs.close();
            cnn.close();
            
            
            return ret;
        } catch (SQLException | ClassNotFoundException ex) {
           throw new BDExcepcion(ex.getMessage());
        }
    }

   


    
    }
    



            
        
   



