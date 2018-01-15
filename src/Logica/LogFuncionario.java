
package Logica;



import Dominio.Banco;
import Dominio.Cargo;
import Dominio.CentroCosto;
import Dominio.Codigo;
import Dominio.CodigoDesvinc;
import Dominio.Departamento;
import Dominio.EstadoCivil;
import Dominio.Fallecimiento;
import Dominio.Feriado;
import Dominio.Funcionario;
import Dominio.Horario;
import Dominio.Licencia;
import Dominio.LicenciaAdelantada;
import Dominio.MovimientoLicencia;
import Dominio.Sns;
import Dominio.Sucursal;
import Persistencia.Conexion;
import Persistencia.PersistenciaCombos;
import Persistencia.PersistenciaFuncionario;
import Persistencia.PersistenciaHorario;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogFuncionario {
    
    private PersistenciaFuncionario pers;
    private PersistenciaCombos c;
    private PersistenciaHorario hor;
    private ArrayList<Feriado> listaFeriados;
    private Conexion conexion;
    
    public LogFuncionario() throws ClassNotFoundException, SQLException {
        this.pers=new PersistenciaFuncionario();
        this.c=new PersistenciaCombos();
        this.hor= new PersistenciaHorario();
        this.listaFeriados=this.listarTodosFeriados();
    }

    public PersistenciaCombos getC() {
        return c;
    }

    public void setC(PersistenciaCombos c) {
        this.c = c;
    }
    
   
     
     public ArrayList<Sucursal> cargaComboSucursal() throws SQLException, ClassNotFoundException{
        
         return c.cargaComboSucursal();
     }
     
     public ArrayList<Departamento> cargaComboDepto() throws SQLException, ClassNotFoundException{
        
         return c.cargaComboDepto();
     }
     
     public ArrayList<CentroCosto> cargaComboCentro() throws SQLException, ClassNotFoundException{
         
         return c.cargaComboCentro();
     }
        public ArrayList<Cargo> cargaComboCargo() throws SQLException, ClassNotFoundException{
          
         return c.cargaComboCargo();
     }
     public ArrayList<Banco> cargaComboBanco() throws SQLException, ClassNotFoundException{
        
         return c.cargaComboBanco();
     }
     public ArrayList<CodigoDesvinc> cargaComboCodigoDesvinc() throws SQLException, ClassNotFoundException{
        
         return c.cargaComboCodigoDesvinc();
     }
     
     public CodigoDesvinc buscarCodigoDesvinc(Integer cod) throws SQLException, ClassNotFoundException{
        
         return c.buscarCodDes(cod);
     }
     public ArrayList<EstadoCivil> cargaComboEstado() throws SQLException, ClassNotFoundException{
        
         return c.cargaComboEstadoCivil();
     }
     public ArrayList<Sns> cargaComboSns() throws SQLException, ClassNotFoundException{
        
         return c.cargaComboSns();
     }

    public Integer funcionarioExiste(Integer numFunc) throws SQLException, ClassNotFoundException {
       return pers.funcionarioExiste(numFunc);
    }

    public Boolean altaFuncionario(String numFunc, String apellidoUno, String apellidoDos, String nombreUno, String nombreDos, String direccion, String localidad, Departamento depto, String telefono, String celular, String cedula, String credencial, Date fechaNac, EstadoCivil estado, Character sexo, String iniciales, Date fechaIngreso, Cargo cargo, Date vigenciaCargo, Double sueldo, CentroCosto centro, String baseHoraria, String baseHoras, Sucursal sucursal, Sns sns, Banco banco, Double numeroCuenta, String cuenta, Integer afap, ArrayList<Horario> listaHorarios, Date vencimientoCarne) throws SQLException, ClassNotFoundException {
        Funcionario f=new Funcionario();
        Boolean alta=false;
       Integer num=Integer.valueOf(numFunc);
       Integer ced=Integer.valueOf(cedula);
       Integer horas=Integer.valueOf(baseHoras);
       Integer horaria=Integer.valueOf(baseHoraria);
       
           f.setCodFunc(num);
           f.setApellido1(apellidoUno);
           f.setApellido2(apellidoDos);
           f.setNombre1(nombreUno);
           f.setNombre2(nombreDos);
           f.setDireccion(direccion);
           f.setLocalidad(localidad);
           f.setDepartamento(depto);
           f.setTelefono(telefono);
           f.setCelular(celular);
           f.setCedula(ced);
           f.setCredencial(credencial);
           f.setFechaNac(fechaNac);
           f.setEstadoCivil(estado);
           f.setFechaIngreso(fechaIngreso);
           f.setSexo(sexo);
           f.setIniciales(iniciales);
           f.setCargo(cargo);
           f.setVigenciaCargo(vigenciaCargo);
           f.setSueldoCargo(sueldo);
           f.setCentroCosto(centro);
           f.setBaseHoraria(horaria);
           f.setBaseHoras(horas);
           f.setLugarTrabajo(sucursal);
           f.setSns(sns);
           f.setBanco(banco);
           f.setCuenta(numeroCuenta);
           f.setTipoCuenta(cuenta);
           f.setAfap(afap);
           f.setHorarios(listaHorarios);
           f.setVencimientoCarne(vencimientoCarne);
           alta=this.pers.altaFuncionario(f);
       
       return alta;
    }

    public Funcionario buscarFuncionario(String numFunc) throws ClassNotFoundException, SQLException {
        Integer codigo=Integer.valueOf(numFunc);
        Funcionario f=this.pers.buscarFuncionario(codigo);
        return f;
    }

    public boolean modFuncionario(Funcionario fv,String numFunc, String apellidoUno, String apellidoDos, String nombreUno, String nombreDos, String direccion, String localidad, Departamento depto, String telefono, String celular, String cedula, String credencial, Date fechaNac, EstadoCivil estado, Character sexo, String iniciales, Date fechaIngreso, Date fechaEgreso, Cargo cargo, Date vigenciaCargo, Double sueldo, CentroCosto centro, String baseHoraria, String baseHoras, Sucursal sucursal, Sns sns, Banco banco, Double numeroCuenta, String cuenta, Integer afap, ArrayList<Horario> listaHorarios, Date vencimientoCarne,CodigoDesvinc cod) throws SQLException {
       Funcionario f=new Funcionario();
       Boolean alta=false;
       Integer num=Integer.valueOf(numFunc);
       Integer ced=Integer.valueOf(cedula);
       Integer horas=Integer.valueOf(baseHoras);
       Integer horaria=Integer.valueOf(baseHoraria);
       
           f.setCodFunc(num);
           f.setApellido1(apellidoUno);
           f.setApellido2(apellidoDos);
           f.setNombre1(nombreUno);
           f.setNombre2(nombreDos);
           f.setDireccion(direccion);
           f.setLocalidad(localidad);
           f.setDepartamento(depto);
           f.setTelefono(telefono);
           f.setCelular(celular);
           f.setCedula(ced);
           f.setCredencial(credencial);
           f.setFechaNac(fechaNac);
           f.setEstadoCivil(estado);
           f.setFechaIngreso(fechaIngreso);
           f.setFechaEgreso(fechaEgreso);
           f.setSexo(sexo);
           f.setIniciales(iniciales);
           f.setCargo(cargo);
           f.setVigenciaCargo(vigenciaCargo);
           f.setSueldoCargo(sueldo);
           f.setCentroCosto(centro);
           f.setBaseHoraria(horaria);
           f.setBaseHoras(horas);
           f.setLugarTrabajo(sucursal);
           f.setSns(sns);
           f.setBanco(banco);
           f.setCuenta(numeroCuenta);
           f.setTipoCuenta(cuenta);
           f.setAfap(afap);
           f.setHorarios(listaHorarios);
           f.setVencimientoCarne(vencimientoCarne);
           f.setCodigoDesvinc(cod);
           alta=this.pers.modFuncionario(f,fv);
       
       return alta;

    }
    
    public ArrayList<Funcionario> vencimientoCarne() throws ClassNotFoundException, SQLException{
      return  this.pers.vencimientoCarne();
    }
    public ArrayList<Funcionario> listadoFuncionariosActivos(String filtro) throws ClassNotFoundException, SQLException{
        return this.pers.listarFuncionariosActivos(filtro);
    }  
    public ArrayList<Funcionario> listadoFuncionarios(String filtro) throws ClassNotFoundException, SQLException{
        return this.pers.listarFuncionarios(filtro);
    }
    public ArrayList<Funcionario> listadoFuncionariosInactivos(String filtro) throws ClassNotFoundException, SQLException{
        return this.pers.listarFuncionariosInactivos(filtro);
    }
    public ArrayList<Funcionario> listadoFuncionariosActivosPorSuc(Integer cod,String filtro) throws ClassNotFoundException, SQLException{
        return this.pers.listarFuncionariosActivosPorSuc(cod,filtro);
    }
    public ArrayList<Funcionario> listadoFuncionariosInactivosPorSuc(Integer cod, String filtro) throws ClassNotFoundException, SQLException{
        return this.pers.listarFuncionariosInactivosPorSuc(cod,filtro);
    }
    public ArrayList<Funcionario> listadoFuncionariosPorSuc(Integer cod, String filtro) throws ClassNotFoundException, SQLException{
        return this.pers.listarFuncionariosPorSuc(cod,filtro);
    }

    public ArrayList<Funcionario> listadoVencidos() throws ClassNotFoundException, SQLException {
        return  this.pers.listadoVencidos();
    }
    public ArrayList<Licencia> listadoLicencia(String str) throws ClassNotFoundException, SQLException {
        Integer año=Integer.valueOf(str)+1;
        return  this.pers.listarLicencia(año);
    }
    public ArrayList<Licencia> licenciaPorCod(String codigo)throws ClassNotFoundException, SQLException {
        Integer cod=Integer.valueOf(codigo);
        return  this.pers.licenciaPorCod(cod);
    }
    public Licencia Licencia(String codigo) throws ClassNotFoundException, SQLException, ParseException {
        Integer cod=Integer.valueOf(codigo);
        return  this.pers.licencia(cod,this.calcular1());
    }
    public Licencia LicenciaAño(String codigo,String año) throws ClassNotFoundException, SQLException, ParseException {
        Integer cod=Integer.valueOf(codigo);
        return  this.pers.licencia(cod,this.calcular2(año));
    }
    public Funcionario funcParcial(String codigo) throws ClassNotFoundException, SQLException{
        Integer cod=Integer.valueOf(codigo);
        return this.pers.funcionarioParcial(cod);
    }
    public Funcionario funcBasico(String codigo) throws ClassNotFoundException, SQLException{
        Integer cod=Integer.valueOf(codigo);
        return this.pers.funcionarioBasico(cod);
    }
    public boolean insertarLicencia() throws SQLException, ClassNotFoundException, ParseException{
        Connection cnn=null;
        cnn=conexion.Cadena();
        ArrayList<Licencia> lista=this.calculaLicencia(cnn);
        int i=0;
        boolean alta=false;
        if(this.pers.eliminarLicencia(this.calcular(),cnn)){
        ArrayList<Licencia> listado=this.listadoLicencia(this.obtenerAño1(this.calcular3()));
        if(listado.isEmpty()){
            for(i=0;i<lista.size();i++){
                this.pers.insertarLicencia(lista.get(i),cnn);
            }
        }
        else{
               for(i=0;i<lista.size();i++){
                     if(this.estaEnLicencia(listado, lista.get(i))){
                            this.pers.actualizarLicenciaFechaCargada(lista.get(i));
                        }
                        else{
                          
                            this.pers.insertarLicencia(lista.get(i),cnn);
                        }
                     
                }
            
        }
     
        if(i==lista.size()){
        alta=true;
        }
        
        }
        if(cnn!=null){
            cnn.close();
        }
        return alta;
    }
    
    private boolean estaEnLicencia(ArrayList<Licencia> listado,Licencia lic){
        boolean esta=false;
        int i=0;
        while(i<listado.size()&&!esta){
              if(listado.get(i).getFuncionario().getCodFunc().equals(lic.getFuncionario().getCodFunc())&& listado.get(i).getAño().equals(lic.getAño())){
                  esta=true;
              }
               i++;             
            }
        return esta;
    }
    
    public ArrayList<Licencia> cargaComboLicencia(String cod) throws ClassNotFoundException, SQLException{
        Integer c=Integer.valueOf(cod);
        return this.pers.cargaComboLicencia(c);
    }
    public ArrayList<Licencia> cargaComboLicenciaPasado(String cod) throws ClassNotFoundException, SQLException{
        Integer c=Integer.valueOf(cod);
        return this.pers.cargaComboLicenciaPasado(c);
    }
    
    ///MODIFICACIONES LICENCIA///
    public Licencia licenciaActualFunc(Integer cod) throws ClassNotFoundException, SQLException{
        
        return this.pers.licenciaActualFunc(cod);
        
    }
    
    public boolean actualizarLicenciaActual(String dias, Licencia l,MovimientoLicencia m) throws ClassNotFoundException, SQLException {
        Integer d=Integer.valueOf(dias);
        Date hoy=new Date();
        MovimientoLicencia Mov=new MovimientoLicencia();
                 Mov.setAñoSaldo(l.getAño()+1);
                 Mov.setDiasTomados(d*-1);
                 Mov.setFechaIni(m.getFechaIni());
                 Mov.setFechaFin(m.getFechaFin());
                 Mov.setReferencia(m.getReferencia());
                 Mov.setFechaMovimiento(hoy);
                 Mov.setFuncionario(l.getFuncionario());
                 Mov.setSaldoAño(l.getSaldo());
                 Mov.setSaldoPos(l.getSaldo()+d);
                 Mov.setTipoLic(10);
                 return this.pers.insertarMovLicencia(Mov);
    }
    
    
    
    ///------------------------------///
    
    public boolean insertarFeriado(String desc, Date fecha,Integer tipo) throws ClassNotFoundException, SQLException{
        Feriado f=new Feriado();
        f.setFecha(fecha);
        f.setAnio(Integer.valueOf(this.obtenerAño1(fecha)));
        f.setTipo(tipo);
        f.setDescripcion(desc);
        return this.pers.insertarFeriado(f);
    }
    public boolean modificarFeriado(Feriado f) throws ClassNotFoundException, SQLException {
    f.setAnio(Integer.valueOf(this.obtenerAño1(f.getFecha())));
    return this.pers.modificarFeriado(f);
    }
      
    public boolean eliminarFeriado(Feriado f) throws ClassNotFoundException, SQLException {
        return this.pers.eliminarFeriado(f);
    } 
    
    public ArrayList<Feriado> listarFeriados(Integer anio) throws SQLException, ClassNotFoundException, ParseException{
        Date d=new Date();
        Integer año=Integer.valueOf(this.obtenerAño1(d));
        return this.pers.listarFeriados(anio);
    }
    public ArrayList<String> cargaComboFeriadoAñosDistintos() throws ClassNotFoundException, SQLException{
        return this.pers.cargaComboFeriadoAñosDistintos();
    }
    //********CALCULO DIAS DE LICENCIA*********//
    private Date calcular() throws ParseException{
        Date d=new Date();
        String t=this.obtenerAño1(d);
        String s="31/12/"+t;
        Date ult=this.stringADate(s);
        return ult;
    }
    private Date calcular1() throws ParseException{
        Date d=new Date();
        String t=this.obtenerAño(d);
        String s="31/12/"+t;
        Date ult=this.stringADate(s);
        return ult;
    }
    private Date calcular2(String año) throws ParseException{
        String s="31/12/"+año;
        Date ult=this.stringADate(s);
        return ult;
    }
    private Date calcular3() throws ParseException{
        Date d=new Date();
        String t=this.obtenerAño2(d);
        String s="31/12/"+t;
        Date ult=this.stringADate(s);
        return ult;
    }
    private String obtenerAño1(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.YEAR));
        return t;
    }
    private String obtenerAño2(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.YEAR));
        return t;
    }
    private String obtenerAño(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.YEAR)-1);
        return t;
    }
    private String obtenerMes(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.MONTH));
        return t;
    }
    private String obtenerDia(Date d){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String t=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return t;
      }
    private Date stringADate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(s);
        return date;
    }
    
    public ArrayList<Licencia> calculaLicencia(Connection cnn) throws ParseException, ClassNotFoundException, SQLException{
        Integer añosTrab=null;
        Integer diasGenerados=null;
        Date d=this.calcular();//fecha de calculo 31/12/yyyy
        Date h=new Date();//fecha de hoy
        Calendar c = Calendar.getInstance(); 
//        c.setTime(d); 
//        c.add(Calendar.DATE, -365);
//        d = c.getTime();
        Date r=new Date();
        String año=this.obtenerAño(r);
        ArrayList<Funcionario> lista=this.pers.listarFuncionariosActivosParaLic();
        ArrayList<Licencia> lice=new ArrayList<>();
        for(int i=0;i<lista.size();i++){
            Licencia l=new Licencia();
           añosTrab=this.calcularAñosTrabajados(d,lista.get(i).getFechaIngreso());
           if(añosTrab>=5){
              diasGenerados=(añosTrab/4)+20;
           }
           else if(añosTrab>0){
               diasGenerados=20;
           }
           else{
               diasGenerados=this.calculoTardio(lista.get(i).getFechaIngreso(), d);
           }
           
           l.setDiasGenerados(diasGenerados);
           l.setFuncionario(lista.get(i));
           Date p=new Date();
           l.setAño(Integer.valueOf(this.obtenerAño1(p))+1);
           //l.setAño(2018);
           l.setFechaGen(d);
           if(l.getFuncionario().getCodFunc()==2140){
               String rt="";
           }
           l.setDiasDescuento(this.calcularDiasDescuento(lista.get(i),d,cnn,diasGenerados));
           lice.add(l);
        }
      return lice;
    }
    
    private Integer calcularDiasDescuento(Funcionario f,Date fechaLiq,Connection cnn,Integer diasGenerados){
        Integer faltas=0;
        try {
            Date fechaAux=this.obtieneFechaAux(f,fechaLiq);
            Calendar liq = Calendar.getInstance();
            liq.setTime(fechaLiq);
            faltas=this.pers.obtenerFaltas(f,liq.get(Calendar.YEAR),fechaAux,cnn);
            
        } catch (SQLException ex) {
            Logger.getLogger(LogFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.calculoDiasDescuento(faltas, diasGenerados);
    }
    
    private Integer calculoDiasDescuento(Integer faltas,Integer diasGenerados){
        Integer retorno = ((faltas*diasGenerados)/360);
        return retorno;
    }
    
    private Date obtieneFechaAux(Funcionario f,Date fechaLiq){
        Date fechaAux=f.getFechaIngreso();
        Calendar aux = Calendar.getInstance(); 
        aux.setTime(fechaAux); 
        Calendar liq = Calendar.getInstance(); 
        liq.setTime(fechaLiq); 
        if(aux.get(Calendar.YEAR)==liq.get(Calendar.YEAR)){
            fechaAux=this.buscoUltimoDiaMes(fechaAux);
        }
        
        return fechaAux;
    }
    
    private Date buscoUltimoDiaMes(Date fechaAux){
        Calendar aux = Calendar.getInstance(); 
        aux.setTime(fechaAux);
        Integer dia=0;
        switch(aux.get(Calendar.MONTH)){
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                dia=31;
            break;
            case 2:
                if(aux.get(Calendar.YEAR)%4==0){
                    dia=29;
                }
                else{
                    dia=28;
                }
            break;
            case 4: case 6: case 9: case 11:
                dia=30;
            break;
            default:
            break;
        }
        aux.set(Calendar.DAY_OF_MONTH,dia);
        return aux.getTime();
    }
    public Licencia calculaLicenciaIndividual(Integer cod, Date fecha) throws ParseException, ClassNotFoundException, SQLException{
        Integer añosTrab=null;
        Integer diasGenerados=null;
        Licencia l=null;

        Funcionario f=this.pers.funcionarioParcial(cod);
        if(f!=null){       
           l=new Licencia();
           añosTrab=this.calcularAñosTrabajados(fecha,f.getFechaIngreso());
           if(añosTrab>=5){
              diasGenerados=(añosTrab/4)+20;
           }
           else if(añosTrab>0){
               diasGenerados=20;
           }
           else{
               diasGenerados=this.calculoTardio(f.getFechaIngreso(), fecha);
           }
           l.setDiasGenerados(diasGenerados);
           l.setFuncionario(f);
           Date p=new Date();
           l.setFechaGen(fecha);
           l.setAño(Integer.valueOf(this.obtenerAño(fecha))+1);
        }
        
        return l;
    }

    private Integer calcularAñosTrabajados(Date d, Date fechaIngreso) {
     Date x=d;
     Calendar c = Calendar.getInstance(); 
     c.setTime(x); 
     c.add(Calendar.DATE, 2);
     x = c.getTime();
     Integer años=Integer.valueOf(this.obtenerAño(x))-Integer.valueOf(this.obtenerAño(fechaIngreso));
     if(Integer.valueOf(this.obtenerMes(x))<Integer.valueOf(this.obtenerMes(fechaIngreso))){
         años=años-1;
     }
     else if(Integer.valueOf(this.obtenerMes(x))==Integer.valueOf(this.obtenerMes(fechaIngreso))){
                if(Integer.valueOf(this.obtenerDia(x))<Integer.valueOf(this.obtenerDia(fechaIngreso))){
                    años=años-1;
                }
     }
     return años;
    }
    

    private int calculoTardio(Date fechaIngreso, Date d) {
        long fin=0;
        Integer dias=this.diferenciaDeDias(d,fechaIngreso);
        Double auxiliar = (dias * 0.05479);
        
        DecimalFormat df = new DecimalFormat("####.00");
        String aux = df.format(auxiliar);
        String aux2=this.cambiarPunto(aux);
        Double a=Double.parseDouble(aux2);
        Integer b=a.intValue();
        Double s=a-b;
        if(!s.equals(0)){
            Double redondeo=1-s;
            fin=Math.round(auxiliar+redondeo);
        }        
        return (int)fin;
    }

    private Integer diferenciaDeDias(Date d, Date fechaIngreso) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
     int dias=(int) ((d.getTime()-fechaIngreso.getTime())/86400000);
     return dias;
    }

    private String cambiarPunto(String aux) {
        
        String replace = aux.replace(",", ".");
        return replace;
    }

    public boolean actualizarLic(Date fechaIni, Date fechaFin,Licencia lic) throws ParseException, SQLException {
             lic.setSaldo(lic.getSaldo()-lic.getDiasDescuento());
        return this.pers.actualizarLicencia(fechaIni,fechaFin,lic);
    }

    public boolean insertarMovLicencia(Date ini, Date fin, Integer saldo, Integer diasTomar, Date hoy, Integer añoSacar, Funcionario Func, Integer saldoPos,Integer tipoLic) throws ClassNotFoundException, SQLException {
            MovimientoLicencia Mov=new MovimientoLicencia();
            Mov.setAñoSaldo(añoSacar);
            Mov.setDiasTomados(diasTomar);
            Mov.setFechaFin(fin);
            Mov.setFechaIni(ini);
            Mov.setFechaMovimiento(hoy);
            Mov.setFuncionario(Func);
            Mov.setSaldoPos(saldoPos);
            Mov.setSaldoAño(saldo);
            Mov.setTipoLic(tipoLic);
            Mov.setReferencia(0);
           return this.pers.insertarMovLicencia(Mov);
    }
    
    public MovimientoLicencia consultaAdelantadoDos(String codFunc) throws ClassNotFoundException, SQLException{
         Integer cod=Integer.valueOf(codFunc);
           Date fecha=new Date();
           Integer año=Integer.valueOf(this.obtenerAño(fecha));
           año=año+2;
           Licencia l=this.pers.licenciaActualFunc(cod);
           return pers.consultaAdelantado(cod,año);
    }
    
    public MovimientoLicencia consultaAdelantado(String codFunc) throws SQLException, ClassNotFoundException {
           Integer cod=Integer.valueOf(codFunc);
           Date fecha=new Date();
           Integer año=Integer.valueOf(this.obtenerAño(fecha));
           año=año+2;
           Licencia l=this.pers.licenciaActualFunc(cod);
           if(l!=null){
               if(l.getFechaIni()!=null){
                    if(l.getFechaIni().before(fecha)){
                        if(this.convertirFecha(fecha).equals(this.convertirFecha(l.getFechaIni()))){
                        return pers.consultaAdelantado(cod,año);
                        }
                        else{
                            return pers.consultaAdelantado(cod,año);
                        }
                    }
                    else{
                        return pers.consultaAdelantado(cod,año-1);
                    }
               }
               else{
                   MovimientoLicencia lic=new MovimientoLicencia();
                   return lic;
               }
           }
           else{
               return null;
               
           }
    }
   private String convertirFecha(Date fecha){
   String str=null;
        if(fecha!=null){
         SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
         str= sdf.format(fecha);
    }
        return str;
   }

    public boolean insertarLicAdelantada(Integer año, Date ini, Date fin, Integer diasTomar, Funcionario func,Integer referencia) throws ClassNotFoundException, SQLException {
         MovimientoLicencia lic=new MovimientoLicencia();
                Date d=new Date();
                lic.setAñoSaldo(año);
                lic.setDiasTomados(diasTomar);
                lic.setFechaFin(fin);
                lic.setFechaIni(ini);
                lic.setFechaMovimiento(d);
                lic.setFuncionario(func);
                lic.setReferencia(referencia);
                return pers.insertarLicAdelantada(lic);
    }

    public ArrayList<MovimientoLicencia> listarLicAdelantada(String codigo, MovimientoLicencia mov) throws ClassNotFoundException, SQLException {
         Integer cod=null;
         if(!codigo.equals("")){
         cod=Integer.valueOf(codigo);
         }
         return pers.listarLicAdelantada(cod,mov);
   }

    public ArrayList<MovimientoLicencia> movimientoLic(String cod,MovimientoLicencia mov) throws ClassNotFoundException, SQLException {
         
         Integer codigo=null;
         if(!cod.equals("")){
         codigo=Integer.valueOf(cod);
         }
         return pers.movimientoLic(codigo,mov);
    }
    public ArrayList<MovimientoLicencia> ultimoMovimiento(String cod,Integer tipo, Integer anio) throws ClassNotFoundException, SQLException{
        Integer codigo=null;
        Integer tip=null;
        ArrayList<MovimientoLicencia> mov=null;
         if(!cod.equals("")){
            codigo=Integer.valueOf(cod);
            mov=this.pers.ultimoMovimiento(codigo, tipo, anio+1);

         }
         return mov;
    }
    
    public ArrayList<MovimientoLicencia> comboMovimientoAñoLic() throws ClassNotFoundException, SQLException{
        return this.pers.comboMovimientoAñoLic();
    }
    
    
    public ArrayList<String> cargaComboLicAñosDistintos() throws ClassNotFoundException, SQLException {
        return this.pers.cargaComboLicAñosDistintos();
    }

    public ArrayList<Feriado> listarTodosFeriados() throws ClassNotFoundException, SQLException {
        return this.pers.listarTodosFeriados();
    }
     public ArrayList<Feriado> listarFeriadosRango(Date desde,Date hasta) throws ClassNotFoundException, SQLException {
        return this.pers.listarFeriadosRango(desde,hasta);
    }

    public ArrayList<Licencia> licenciaAnteriorFunc(Integer codFunc) throws ClassNotFoundException, SQLException {
        
        return this.pers.licenciaAnteriorFunc(codFunc);
    }

    public Integer saldoLicenciaCodigo(String codFunc, Integer cod) throws ClassNotFoundException, SQLException {
        Integer codF=Integer.valueOf(codFunc);
        return this.pers.saldoLicenciaCodigo(codF, cod);
    }

 
 //PAQUETE CALCULA FECHA FIN AUTOMATICO//
    public Date obtieneFechaFin(Date fechaIni, Integer dias,ArrayList<Horario>horarios){
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(fechaIni); 
      calendar.add(Calendar.DAY_OF_YEAR, dias-1);  
      Date fechaFinParcial=calendar.getTime(); 
      Integer dif=this.diferenciaDias(fechaIni, fechaFinParcial, horarios);
      calendar.setTime(fechaFinParcial); 
      calendar.add(Calendar.DAY_OF_YEAR, dif);  
      Date fechaFin=calendar.getTime();
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(fechaFin);
      
      Integer domingos=this.diferenciaDias(fechaIni, cal.getTime(), horarios);
      Integer difFinal=domingos-dif;
      cal.add(Calendar.DAY_OF_YEAR, difFinal);
      Integer domingos2=this.diferenciaDias(fechaIni, cal.getTime(), horarios);      
      difFinal=domingos2-domingos;
      cal.add(Calendar.DAY_OF_YEAR, difFinal);
      
//      if(cal.get(Calendar.DAY_OF_WEEK)==1){
//          cal.add(Calendar.DAY_OF_YEAR, -1);
//      }
      Date retorno=cal.getTime();
      return retorno;
    }
    
    private Integer diferenciaDias(Date fechaIni, Date fechaFin,ArrayList<Horario>horarios) {
        Integer real=0;
        int dias=(int) ((fechaFin.getTime()-fechaIni.getTime())/86400000);
        dias+=1;
 	Integer domingos=this.obtieneDomingoySabado(fechaIni, fechaFin,horarios);
        Integer feriados=this.compararFechas(fechaIni, fechaFin).size();
        real=domingos+feriados;
        
        return real;
    }
    
    private Integer obtieneDomingoySabado(Date fechaIni, Date fechaFin,ArrayList<Horario>horarios){
    Integer contador=0;
    Calendar c1 = Calendar.getInstance();
    c1.setTime(fechaIni);
    Calendar c2 = Calendar.getInstance();
    c2.setTime(fechaFin);
    ArrayList<Date> listaFechas = new ArrayList<Date>();
    boolean Domingo=this.diaEsta(horarios,"DOMINGO");
    //boolean Sabado=this.diaEsta(horarios,"SABADO");
    
    while (!c1.after(c2)) {
        listaFechas.add(c1.getTime());
        c1.add(Calendar.DAY_OF_MONTH, 1);
        
    }
    
    for (Iterator<Date> it = listaFechas.iterator(); it.hasNext();) {
          Date date = it.next();
          GregorianCalendar cal = new GregorianCalendar();
          cal.setTime(date);
          
          if(!Domingo){
            if(cal.get(Calendar.DAY_OF_WEEK)==1){
                  contador++;
            }	
          }
//          if(!Sabado){
//            if(cal.get(Calendar.DAY_OF_WEEK)==7){
//                  contador++;
//          }
//       }
       
    }
     return contador;
  }
   
    private ArrayList<Feriado> compararFechas(Date fechaIni, Date fechaFin) {
        Integer dias=0;
        ArrayList<Feriado> aux=new ArrayList<>();
        
        for(Feriado f:this.listaFeriados){
            if((fechaIni.equals(f.getFecha())||fechaIni.before(f.getFecha()))&& (fechaFin.equals(f.getFecha())||fechaFin.after(f.getFecha()))){
                aux.add(f);
            }
        }
        
        return aux;
    }
    
    private boolean diaEsta(ArrayList<Horario> horarios, String dia) {
        boolean esta=false;
        int i=0;
        while(i<horarios.size()&&!esta){
            if(horarios.get(i).getDescripcion().equals(dia)){
                esta=true;
            }
            i++;
        }
         return esta;
    }    
    
   public ArrayList<Fallecimiento> cargaComboFalle() throws ClassNotFoundException, SQLException {
        return this.pers.cargaComboFalle();
    }

    public boolean existeCodigoDesvinc(Integer cod) throws ClassNotFoundException, SQLException {
        return this.pers.existeCodigoDesvinc(cod);
    }
    
    public boolean CodigoDesvincUsado(Integer cod) throws ClassNotFoundException, SQLException {
        return this.pers.CodigoDesvincUsado(cod);
    }

    public boolean altaCodigoDesvinc(CodigoDesvinc c) throws SQLException {
            return this.pers.altaCodigoDesvinc(c);
    }

    public Integer modificaCodDesvinc(Integer idVieja, CodigoDesvinc codi) throws SQLException {
       return this.pers.modificaCodDesvinc(idVieja,codi);
    }

    public boolean bajaCodigoDesvinc(Integer id) throws SQLException {
         return this.pers.bajaCodigoDesvinc(id);
    }

public String fechaLiquidacion() throws ClassNotFoundException, SQLException{
    return this.pers.fechaLiquidacion();
}
  
public ArrayList<Funcionario> listarFuncionariosActivosLike(String filtro) throws ClassNotFoundException, SQLException{
    return this.pers.listarFuncionariosActivosLike(filtro);
}
   
        
    
   

    
    
}
