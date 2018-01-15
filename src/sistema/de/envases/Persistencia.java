
package sistema.de.envases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Persistencia {
     private Conexion conexion;
     
     
     
     public Envase buscaVsupInvenarti(String barra) throws ClassNotFoundException, SQLException{
            Envase envase=null;
            Connection cnn=null;
            cnn=conexion.Cadena();
            cnn.setAutoCommit(false);
            String bar=this.buscaVsupEnvase(barra,cnn);
            String consulta="Select DESCRIPCION,P_VENTA from VSUP_INVENARTI WHERE BARRAS=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setString(1, bar);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                envase=new Envase();
                envase.setBarra(bar);
                envase.setDescripcion(rs.getString("DESCRIPCION").trim());
                envase.setPrecio(rs.getDouble("P_VENTA"));
            }
            cnn.commit();
            cnn.close();
            rs.close();
            
            return envase;
     }
     
     
     private String buscaVsupEnvase(String barra, Connection cnn) throws ClassNotFoundException, SQLException{
        String retorno=null;
        String consulta="Select BARRAS_ENVASE from VSUP_ENVASES WHERE BARRAS_LIQUIDO=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, barra);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            retorno=rs.getString("BARRAS_ENVASE").trim();
        }
        return retorno;
     }

    Integer insertarTicket(ArrayList<Envase> envases) throws SQLException {
          Connection cnn=null;
          PreparedStatement ps=null;
          Integer nroTicket=null;
        try {
             cnn=conexion.Cadena(); 
             Date d=new Date();
             Timestamp hoy=this.armaTimestamp(d);
             cnn.setAutoCommit(false);
             nroTicket=this.buscarNroTicket(cnn);
             Integer vencimiento=this.diasVencimiento(cnn);
             for(Envase e:envases){
                 String insert="insert into VSUP_ENVASES_MOVS(FECHA,TICKET,BARRAS,DESCRIPCION,IMPORTE,CANTIDAD,TOTAL,PROCESADO,NOTA_ASOCIADA,ORIGEN,ACTIVO,VENCIMIENTO)"+"values(?,?,?,?,?,?,?,?,?,?,?,?)";
                 ps=cnn.prepareStatement(insert);
                 ps.setTimestamp(1, hoy);
                 ps.setInt(2,nroTicket);
                 ps.setString(3, e.getBarra());
                 ps.setString(4, e.getDescripcion());
                 ps.setDouble(5, e.getPrecio()/e.getCantidad());
                 ps.setInt(6, e.getCantidad());
                 ps.setDouble(7, e.getPrecio());
                 ps.setInt(8,0);
                 ps.setInt(9,0);
                 ps.setInt(10, 0);
                 ps.setInt(11,1);
                 ps.setInt(12, vencimiento);
                 ps.executeUpdate();
             }
             cnn.commit();
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
         finally{
             cnn.close();
             ps.close();
         }
         
        return nroTicket;
               
    }    

    private Integer buscarNroTicket(Connection cnn) throws SQLException {
        Integer retorno=null;
        String consulta="Select NUMERO from VSUP_ENVASES_NRO_TICKET";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            retorno=rs.getInt("NUMERO");
        }
        this.actualizaNumTicket(retorno,cnn);
        return retorno+1;
    }
    
    public Integer buscarNro() throws SQLException {
        Integer retorno=null; 
        Connection cnn=null;
        try {
             
             cnn=conexion.Cadena();
             
             String consulta="Select NUMERO from VSUP_ENVASES_NRO_TICKET";
             PreparedStatement ps=cnn.prepareStatement(consulta);
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
                 retorno=rs.getInt("NUMERO");
             }
             
            
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Persistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
        finally{
            if(cnn!=null){
                cnn.close();
            }
        }
          return retorno;
    }

    private Integer diasVencimiento(Connection cnn) throws SQLException {
        Integer retorno=null;
        String consulta="Select VALOR from ACE_CONSTANTES WHERE NOM_CONST=?";
        PreparedStatement ps=cnn.prepareStatement(consulta);
        ps.setString(1, "VENCIMIENTO_DEVOLUCION_ENVASES");
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            retorno=rs.getInt("VALOR");
        }
        return retorno;
    }
    
     private Timestamp armaTimestamp(Date date){
    Calendar c=Calendar.getInstance();
    c.setTimeInMillis(date.getTime());
    Timestamp ts = new java.sql.Timestamp(date.getTime());
    ts.setTime(c.getTimeInMillis());
    return ts;
    }

    private void actualizaNumTicket(Integer retorno, Connection cnn) throws SQLException {
        PreparedStatement ps=null;
        String insert="UPDATE VSUP_ENVASES_NRO_TICKET SET NUMERO=? WHERE NUMERO=?";
        ps=cnn.prepareStatement(insert);
        ps.setInt(1, retorno+1);
        ps.setInt(2, retorno);
        ps.executeUpdate();
    }
    
    public ArrayList<MovEnvases> traerEnvases(Integer num) throws ClassNotFoundException, SQLException{
        Connection cnn=null;
        cnn=conexion.Cadena();
        ArrayList<MovEnvases> movimientos=new ArrayList<>();
            String consulta="Select FECHA,TICKET,BARRAS,DESCRIPCION,IMPORTE,CANTIDAD,TOTAL,VENCIMIENTO,ACTIVO,ORIGEN,NOTA_ASOCIADA from VSUP_ENVASES_MOVS WHERE TICKET=?";
            PreparedStatement ps=cnn.prepareStatement(consulta);
            ps.setInt(1, num);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                MovEnvases mov=new MovEnvases();
                mov.setFecha(rs.getTimestamp("FECHA"));
                mov.setTicket(rs.getInt("TICKET"));
                mov.setBarras(rs.getString("BARRAS").trim());
                mov.setDescripcion(rs.getString("DESCRIPCION").trim());
                mov.setImporte(rs.getDouble("IMPORTE"));
                mov.setCantidad(rs.getInt("CANTIDAD"));
                mov.setTotal(rs.getDouble("TOTAL"));
                mov.setVencimiento(rs.getInt("VENCIMIENTO"));
                mov.setActivo(rs.getInt("ACTIVO"));
                mov.setOrigen(rs.getInt("ORIGEN"));
                mov.setNota(rs.getLong("NOTA_ASOCIADA"));
                movimientos.add(mov);
            }
            
         if(cnn!=null){
             cnn.close();
             ps.close();
         }
         
         return movimientos;
    }
    
    
    public Integer anularTicket(Integer num) throws SQLException, ClassNotFoundException {
        Connection cnn=null;
        cnn=conexion.Cadena();
        Integer anula=0;
        
        PreparedStatement ps=null;
        String insert="UPDATE VSUP_ENVASES_MOVS SET ACTIVO=? WHERE TICKET=? AND NOTA_ASOCIADA=? AND ACTIVO=?";
        ps=cnn.prepareStatement(insert);
        ps.setInt(1, 0);
        ps.setInt(2, num);
        ps.setInt(3, 0);
        ps.setInt(4, 1);
        anula=ps.executeUpdate();
        if(cnn!=null){
            cnn.close();
        }
        return anula;
    }
}
