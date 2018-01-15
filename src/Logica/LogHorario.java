
package Logica;

import Dominio.Horario;
import Persistencia.PersistenciaFuncionario;
import Persistencia.PersistenciaHorario;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class LogHorario {
    private PersistenciaHorario pers;
    private PersistenciaFuncionario func;
    public LogHorario() {
        this.pers=new PersistenciaHorario();
        this.func=new PersistenciaFuncionario();
    }
    
    
    public ArrayList<Horario> cargaComboHorario() throws SQLException, ClassNotFoundException{
    
    return pers.cargaComboHorarios();
     }
    
    public Boolean altaHorario(Horario h) throws ClassNotFoundException, SQLException, ParseException{
        Boolean alta=false;
         
//        Horario h=new Horario();
//        h.setHoraEntrada(convertir(strEntrada));
//        h.setHoraSalidaInter(convertir(strSalidaInter));
//        h.setHoraEntradaInter(convertir(strEntradaInter));
//        h.setHoraSalida(convertir(strSalida));
//        h.setDescripcion(strDias);
//        h.setCodDias(codDias);
        if(!this.existe(h)){
        alta=pers.altaHorario(h);
        
        }
        return alta;
    }
     
     
     public Time convertir(String str) throws ParseException{
         Time res=null;
         if(!str.equals("")){
         res=Time.valueOf(str);
         }
         return res;
     }
     
     
   private Boolean existe(Horario h) throws SQLException, ClassNotFoundException{
       Boolean esta=false;
       ArrayList<Horario> hor=this.pers.cargaComboHorarios();
       Integer i=0;
//           Integer HcodDias=h.getCodDias();
//           String Hdesc=h.getDescripcion();
//           Time Hentrada =h.getHoraEntrada();
//           Time Hsalida=h.getHoraSalida();
//           Time HentradaInter=h.getHoraEntradaInter();
//           Time HsalidaInter=h.getHoraSalidaInter();
       while(i<hor.size()&&!esta){
                Horario x=hor.get(i);
                
                    if(h.getDescripcion().equals(x.getDescripcion())){
                        if(h.getHoraEntrada().equals(x.getHoraEntrada())){
                            if(h.getHoraEntradaInter().equals(x.getHoraEntradaInter())){
                                if(h.getHoraSalida().equals(x.getHoraSalida())){
                                    if(h.getHoraSalidaInter().equals(x.getHoraSalidaInter())){
                                        esta=true;
                                    }
                                }
                            }
                        }
                    }
                
                      
           
           i++;
       }
//       while(i<hor.size()&&!esta){
//           Integer codDias=hor.get(i).getCodDias();
//           String desc=hor.get(i).getDescripcion();
//           Time entrada =hor.get(i).getHoraEntrada();
//           Time salida=hor.get(i).getHoraSalida();
//           Time entradaInter=hor.get(i).getHoraEntradaInter();
//           Time salidaInter=hor.get(i).getHoraSalidaInter();
//           
//        
//           if(codDias.equals(HcodDias)){
//                   if(desc.equals(Hdesc)){
//                       if(entrada.equals(Hentrada)){
//                           if(salida.equals(Hsalida)){
//                               if(this.nullOno(HentradaInter, entradaInter)){
//                                  if (this.nullOno(salidaInter,HsalidaInter)) {
//                                    esta=true;
//               }
//                               }
//                           }
//                       }
//                   }
//           
//              
//           }
//          
//          i++;
//       
//       }   
      
       return esta;
   }
   
//   public Boolean nullOno(Time entrada1, Time entrada2){
//         if(entrada1==null){
//           if(entrada2==null){
//               return true;
//           }
//           else{
//               if(entrada2!=null){
//                   return false;
//               }
//           }
//       }
//         else{
//             if(entrada1!=null){
//                 if(entrada2!=null){
//                     if(entrada2.equals(entrada1)){
//                         return true;
//                     }
//                     else{
//                         return false;
//                     }
//                 }
//                 else{
//                     return false;
//                 }
//             }
//         }
//        return null;
//   }

    public Boolean eliminarHorario(Integer cod) throws SQLException, ClassNotFoundException {
       return pers.eliminarHorario(cod);
       
        
    }

    public boolean modificarHorario(Horario h) throws ParseException, SQLException, ClassNotFoundException {
         Boolean mod=false;
         
   
        if(!this.existe(h)){
        mod=pers.modificarHorario(h);
        
        }
        return mod;
    }

    public Integer horarioExisteparaFunc(Integer cod) throws ClassNotFoundException, SQLException {
        return func.horarioExisteEnFunc(cod);
    }
     
     
    
    
    
}
