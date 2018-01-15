

package presentacion;

import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class BarraMenu extends JMenuBar{
    
    public BarraMenu() {
        init();
    }

    private void init() {
        
        JMenu menu;
        JMenuItem itemMenu;
        
        //menu mantenimiento
        menu = new JMenu("Mantenimiento");
      
        itemMenu = new JMenuItem("Alta Funcionario");
        itemMenu.setName("AltaFuncionario");
        menu.add(itemMenu);
        
        itemMenu = new JMenuItem("Mantenimiento Funcionario");
        itemMenu.setName("ModificarFuncionario");
        menu.add(itemMenu);
        
         
        itemMenu = new JMenuItem("Mantenimiento Horario");
        itemMenu.setName("ModificarHorario");
        menu.add(itemMenu);
        
    
        
        itemMenu = new JMenuItem("Mantenimiento Banco");
        itemMenu.setName("ModificarBanco");
        menu.add(itemMenu);
       
       
        this.add(menu);
        
        //menu consultas
        menu = new JMenu("Liquidaciones");
        
        itemMenu = new JMenuItem("Carga de Códigos Horario");
        itemMenu.setName("cargaCodHora");
        menu.add(itemMenu);
        
        itemMenu = new JMenuItem("Carga de Códigos");
        itemMenu.setName("cargaCod");
        menu.add(itemMenu);
               
        this.add(menu);
        
        //menu reportes
        menu = new JMenu("Reportes");
        
        itemMenu = new JMenuItem("Vencimiento Carne de Salud");
        itemMenu.setName("VencimientoCarne");
        menu.add(itemMenu);
        
        itemMenu = new JMenuItem("Listado de Funcionarios");
        itemMenu.setName("ListadoFuncionarios");
        menu.add(itemMenu);
        
         
        this.add(menu);
        
   
        
        
     
        
        
      
        
        
        
       
        
    }
    
    
}