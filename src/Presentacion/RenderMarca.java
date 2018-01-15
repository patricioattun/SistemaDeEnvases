
package Presentacion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;


public class RenderMarca extends DefaultTableCellRenderer {
    private int columnaTipo;
    private int columnaSuper;
    private int columnaInco;
    public RenderMarca(int tipo, int supervisado,int inco)
        {
            this.columnaTipo = tipo;
            this.columnaSuper=supervisado;
            this.columnaInco=inco;
        }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    setBackground(Color.white);
    setForeground(Color.BLACK);
    setBorder(BorderFactory.createLineBorder(Color.black)); 
    table.setForeground(Color.black);
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
    setBorder(BorderFactory.createLineBorder(Color.black));
    if(!"0".equals(table.getValueAt(row,columnaInco).toString())&& "NO".equals(table.getValueAt(row,columnaSuper).toString())){
            setBackground(Color.orange);
            setForeground(Color.BLACK);
            table.setForeground(Color.black);
    }
    else if("LICENCIA".equals(table.getValueAt(row,columnaTipo).toString()) && "NO".equals(table.getValueAt(row,columnaSuper).toString())){
        
            setBackground(Color.yellow);
            setForeground(Color.BLACK);
            
            table.setForeground(Color.black);
          }
    else if("RELOJ SUP".equals(table.getValueAt(row,columnaTipo).toString()) && "NO".equals(table.getValueAt(row,columnaSuper).toString()))
    {
        setBackground(Color.red); 
        setForeground(Color.BLACK);
       
        table.setForeground(Color.black);
    }
    else if("VACIA".equals(table.getValueAt(row,columnaTipo).toString()) && "NO".equals(table.getValueAt(row,columnaSuper).toString()))
    {
        setBackground(Color.cyan); 
        setForeground(Color.BLACK);
        
        table.setForeground(Color.black);
    }
    else if("FALTA".equals(table.getValueAt(row,columnaTipo).toString()) && "NO".equals(table.getValueAt(row,columnaSuper).toString()))
    {
        setBackground(Color.blue); 
        setForeground(Color.white);
        
        table.setForeground(Color.black);
    }
    else if("FERIADO".equals(table.getValueAt(row,columnaTipo).toString()) && "NO".equals(table.getValueAt(row,columnaSuper).toString()))
    {
        setBackground(Color.LIGHT_GRAY); 
        setForeground(Color.white);
        
        table.setForeground(Color.black);
    } 
    else{
//        setBackground(Color.WHITE); 
//        setForeground(Color.BLACK);
    }
    if (table.isRowSelected(row)){
    setBackground(Color.GREEN);
    setForeground(Color.BLACK);
    setBorder(BorderFactory.createLineBorder(Color.black));
    }
            
    return this;
        
    }
    
    
}
