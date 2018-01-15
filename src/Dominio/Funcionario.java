
package Dominio;

import java.util.ArrayList;
import java.util.Date;


public class Funcionario {
    private Integer codFunc;
    private String apellido1; //Longitud: 35
    private String apellido2;//Longitud: 35
    private String nombre1; //Longitud: 35
    private String nombre2; //Longitud: 35
    private String direccion; //Longitud: 50
    private String localidad; //Longitud: 20
    private String telefono; //Longitud: 20
    private String celular; //Longitud: 20
    private Integer cedula;
    private String credencial; //Longitud: 10
    private Date fechaNac;
    private Date vencimientoCarne;
    private Character sexo; 
    private String iniciales; //Longitud: 12
    private Date fechaIngreso;
    private Date fechaEgreso;
    private Double sueldoCargo;
    private Date vigenciaCargo;
    private Integer baseHoraria;
    private Integer baseHoras;
    private Integer afap;
    private Double cuenta;
    private Integer numSocio=0;
    private String tipoCuenta;
  
    //Referencias
    private Banco banco;
    private Departamento departamento;
    private EstadoCivil estadoCivil;
    private Cargo cargo;
    private CentroCosto centroCosto;
    private Sucursal lugarTrabajo;
    private ArrayList<Horario> horarios;
    private Sns sns;
    private CodigoDesvinc codigoDesvinc;

    public CodigoDesvinc getCodigoDesvinc() {
        return codigoDesvinc;
    }

    public void setCodigoDesvinc(CodigoDesvinc codigoDesvinc) {
        this.codigoDesvinc = codigoDesvinc;
    }
    
    

    public Date getVencimientoCarne() {
        return vencimientoCarne;
    }

    public void setVencimientoCarne(Date vencimientoCarne) {
        this.vencimientoCarne = vencimientoCarne;
    }

    
    
    public Integer getCodFunc() {
        return codFunc;
    }

    public void setCodFunc(Integer codFunc) {
        this.codFunc = codFunc;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public Double getSueldoCargo() {
        return sueldoCargo;
    }

    public void setSueldoCargo(Double sueldoCargo) {
        this.sueldoCargo = sueldoCargo;
    }

    public Date getVigenciaCargo() {
        return vigenciaCargo;
    }

    public void setVigenciaCargo(Date vigenciaCargo) {
        this.vigenciaCargo = vigenciaCargo;
    }

    public Integer getBaseHoraria() {
        return baseHoraria;
    }

    public void setBaseHoraria(Integer baseHoraria) {
        this.baseHoraria = baseHoraria;
    }

    public Integer getBaseHoras() {
        return baseHoras;
    }

    public void setBaseHoras(Integer baseHoras) {
        this.baseHoras = baseHoras;
    }

    public Integer getAfap() {
        return afap;
    }

    public void setAfap(Integer afap) {
        this.afap = afap;
    }

    public Double getCuenta() {
        return cuenta;
    }

    public void setCuenta(Double cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getNumSocio() {
        return numSocio;
    }

    public void setNumSocio(Integer numSocio) {
        this.numSocio = numSocio;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public CentroCosto getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(CentroCosto centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Sucursal getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(Sucursal lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }

    public Sns getSns() {
        return sns;
    }

    public void setSns(Sns sns) {
        this.sns = sns;
    }
    
    public String getNomCompleto(){
        String nom="";
        if(this.nombre2!=null && this.apellido2!=null){
        nom= this.nombre1.trim()+" "+this.nombre2.trim()+" "+this.apellido1.trim()+" "+this.apellido2.trim();
        }
        if(this.nombre2==null&&this.apellido2!=null){
        nom= this.nombre1.trim()+" "+this.apellido1.trim()+" "+this.apellido2.trim();
        }
        if(this.apellido2==null&&this.nombre2!=null){
        nom= this.nombre1.trim()+" "+this.nombre2.trim()+" "+this.apellido1.trim();
        }
        return nom;
    }
    
    public String getNomCompletoYNum(){
        String nom="";
        if(this.nombre2!=null && this.apellido2!=null){
        nom=this.codFunc+" - "+this.nombre1.trim()+" "+this.nombre2.trim()+" "+this.apellido1.trim()+" "+this.apellido2.trim();
        }
        if(this.nombre2==null&&this.apellido2!=null){
        nom=this.codFunc+" - "+this.nombre1.trim()+" "+this.apellido1.trim()+" "+this.apellido2.trim();
        }
        if(this.apellido2==null&&this.nombre2!=null){
        nom=this.codFunc+" - "+this.nombre1.trim()+" "+this.nombre2.trim()+" "+this.apellido1.trim();
        }
        return nom;
    }
    
     public String getNomApe(){
        return this.apellido1.trim()+", "+this.nombre1.trim();
    }
    
    
}

