
package Persistencia;

import Dominio.Banco;
import Dominio.Cargo;
import Dominio.CentroCosto;
import Dominio.CodigoDesvinc;
import Dominio.Departamento;
import Dominio.EstadoCivil;
import Dominio.Sns;
import Dominio.Sucursal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersistenciaCombos {
    private Conexion conexion;
    private ArrayList<CentroCosto> centros;
    private ArrayList<Banco> bancos;
    private ArrayList<Departamento> deptos;
    private ArrayList<Cargo> cargos;
    private ArrayList<Sucursal> sucursales;
    private ArrayList<EstadoCivil> estados;
    private ArrayList<Sns> snss;
    private PersistenciaBanco perBan;
    private ArrayList<CodigoDesvinc> codigos;
    public PersistenciaCombos() {
        
    }

    public ArrayList<CodigoDesvinc> getCodigos() {
        return codigos;
    }

    public void setCodigos(ArrayList<CodigoDesvinc> codigos) {
        this.codigos = codigos;
    }

    public ArrayList<CentroCosto> getCentros() {
        return centros;
    }

    public void setCentros(ArrayList<CentroCosto> centros) {
        this.centros = centros;
    }

    public ArrayList<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(ArrayList<Banco> bancos) {
        this.bancos = bancos;
    }

    public ArrayList<Departamento> getDeptos() {
        return deptos;
    }

    public void setDeptos(ArrayList<Departamento> deptos) {
        this.deptos = deptos;
    }

    public ArrayList<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(ArrayList<Cargo> cargos) {
        this.cargos = cargos;
    }

    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public ArrayList<EstadoCivil> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<EstadoCivil> estados) {
        this.estados = estados;
    }

    public ArrayList<Sns> getSnss() {
        return snss;
    }

    public void setSnss(ArrayList<Sns> snss) {
        this.snss = snss;
    }
       
    public ArrayList<CentroCosto> cargaComboCentro() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        CentroCosto cen =null; 
        centros=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_CENTROCOSTO order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            cen=new CentroCosto();
            cen.setCodigo(rs.getInt("CODIGO"));
            cen.setNombre(rs.getString("NOMBRE").trim());
            
            centros.add(cen);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return centros;
    }
    
    public ArrayList<Cargo> cargaComboCargo() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        Cargo cargo=null; 
        cargos=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_CARGOS order by NOMBRE";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            cargo=new Cargo();
            cargo.setCodigo(rs.getInt("CODIGO"));
            cargo.setNombre(rs.getString("NOMBRE").trim());
            
            cargos.add(cargo);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
            
         return cargos;
    }
    
    
    public ArrayList<Banco> cargaComboBanco() throws SQLException, ClassNotFoundException{
       PersistenciaBanco perBan=new PersistenciaBanco();
        bancos=perBan.cargaComboBanco();
            
        return bancos;
    }

    public PersistenciaBanco getPerBan() {
        return perBan;
    }

    public void setPerBan(PersistenciaBanco perBan) {
        this.perBan = perBan;
    }
     
     
      public ArrayList<Departamento> cargaComboDepto() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        Departamento depto=null; 
        deptos=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_DEPARTAMENTOS order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            depto=new Departamento();
            depto.setCodigo(rs.getInt("CODIGO"));
            depto.setNombre(rs.getString("NOMBRE").trim());
            
            deptos.add(depto);
            }        
            if(cnn!=null){
            ps.close();
            rs.close();
            cnn.close();
            
            }
         return deptos;
    }
     

     
     public ArrayList<Sucursal> cargaComboSucursal() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        Sucursal suc=null; 
        sucursales=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from SUCS_SUCURSALES_ACR order by numero";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            suc=new Sucursal();
            suc.setNumero(rs.getInt("NUMERO"));
            suc.setNombre(rs.getString("NOMBRE").trim());
            
            sucursales.add(suc);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return sucursales;
    }
     
      public ArrayList<EstadoCivil> cargaComboEstadoCivil() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        EstadoCivil estado=null; 
        estados=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_ESTCIVIL order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            estado=new EstadoCivil();
            estado.setCodigo(rs.getInt("CODIGO"));
            estado.setNombre(rs.getString("NOMBRE").trim());
            
            estados.add(estado);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return estados;
    }
      
     public ArrayList<Sns> cargaComboSns() throws SQLException, ClassNotFoundException{
        Connection cnn=null;
        Sns sns=null; 
        snss=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_SNS order by codigo";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            sns=new Sns();
            sns.setCodigo(rs.getInt("CODIGO"));
            sns.setDescripcion(rs.getString("DESCRIPCION").trim());
            sns.setCaja(rs.getDouble("CAJA"));
            sns.setFonasa(rs.getDouble("FONASA"));
            
            snss.add(sns);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return snss;
    }
      
      //BUESQUEDAS
         public Departamento buscarDepto(Integer cod) throws ClassNotFoundException, SQLException{
        this.deptos=this.cargaComboDepto();
         Departamento depto=null;
         Boolean esta=false;
         Integer i=0;
         while(i<this.deptos.size()&& !esta){
             if(deptos.get(i).getCodigo().equals(cod)){
                 depto=deptos.get(i);
                 esta=true;
             }
             i++;
         }
         return depto;

//         Connection cnn=null;
//        Departamento depto=null; 
//        cnn=conexion.Cadena();
//        String consulta="Select * from PERS_DEPARTAMENTOS where CODIGO=?";
//        PreparedStatement ps=cnn.prepareStatement(consulta);
//        ps.setInt(1, cod);
//        ResultSet rs=ps.executeQuery();
//            if(rs.next()){
//            depto=new Departamento();
//            depto.setCodigo(rs.getInt("CODIGO"));
//            depto.setNombre(rs.getString("NOMBRE").trim());
//            }        
//            if(cnn!=null){
//                ps.close();
//                rs.close();
//                cnn.close();
//            }
//            
//         return depto;
     }
           
     public Banco buscarBanco(Integer cod){
         Banco banco=null;
         Boolean esta=false;
         Integer i=0;
         while(i<this.bancos.size()&& !esta){
             if(bancos.get(i).getCodigo().equals(cod)){
                 banco=bancos.get(i);
                 esta=true;
             }
             i++;
         }
         return banco;
     }

    public CentroCosto buscarCentro(Integer centro) {
        CentroCosto cen=null;
         Boolean esta=false;
         Integer i=0;
         while(i<this.centros.size()&& !esta){
             if(centros.get(i).getCodigo().equals(centro)){
                 cen=centros.get(i);
                 esta=true;
             }
             i++;
         }
         return cen;
    }

    public EstadoCivil buscarEstado(Integer estado) {
         EstadoCivil es=null;
         Boolean esta=false;
         Integer i=0;
         while(i<this.estados.size()&& !esta){
             if(estados.get(i).getCodigo().equals(estado)){
                 es=estados.get(i);
                 esta=true;
             }
             i++;
         }
         return es;
    }

    public Cargo buscarCargo(Integer cargo) {
     Cargo car=null;
         Boolean esta=false;
         Integer i=0;
         while(i<this.cargos.size()&& !esta){
             if(cargos.get(i).getCodigo().equals(cargo)){
                 car=cargos.get(i);
                 esta=true;
             }
             i++;
         }
         return car;    
    }

    public Sucursal buscarSucursal(Integer sucursal) {
      Sucursal suc=null;
         Boolean esta=false;
         Integer i=0;
         while(i<this.sucursales.size()&& !esta){
             if(sucursales.get(i).getNumero().equals(sucursal)){
                 suc=sucursales.get(i);
                 esta=true;
             }
             i++;
         }
         return suc; 
    }

    public Sns buscarSns(Integer sns) {
    Sns s=null;
         Boolean esta=false;
         Integer i=0;
         while(i<this.snss.size()&& !esta){
             if(snss.get(i).getCodigo().equals(sns)){
                 s=snss.get(i);
                 esta=true;
             }
             i++;
         }
         return s; 
    }

    public ArrayList<CodigoDesvinc> cargaComboCodigoDesvinc() throws ClassNotFoundException, SQLException {
        Connection cnn=null;
        CodigoDesvinc c=null; 
        codigos=new ArrayList();
        cnn=conexion.Cadena();
        String consulta="Select * from PERS_CODIGOS_DESVINCULACION order by ID";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
            while(rs.next()){
            //c=new CodigoDesvinc(rs.getInt("ID"),rs.getString("DESCRIPCION").trim());
            c=new CodigoDesvinc();
            c.setDescripcion(rs.getString("DESCRIPCION"));
            c.setId(rs.getInt("ID"));
            codigos.add(c);
            }        
            if(cnn!=null){
                ps.close();
                rs.close();
                cnn.close();
            }
         return codigos;
    }
    
  public CodigoDesvinc buscarCodDes(Integer sns) throws ClassNotFoundException, SQLException {
    CodigoDesvinc s=null;
         Boolean esta=false;
         Integer i=0;
         if(codigos==null){
             codigos=this.cargaComboCodigoDesvinc();
         }
         while(i<this.codigos.size()&& !esta){
             if(codigos.get(i).getId().equals(sns)){
                 s=codigos.get(i);
                 esta=true;
             }
             i++;
         }
         return s; 
    }

}
