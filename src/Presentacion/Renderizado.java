
package Presentacion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class Renderizado extends DefaultTableCellRenderer {
    private int columna ;

public Renderizado(int Colpatron)
{
    this.columna = Colpatron;
}



@Override
public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
{        
    setBackground(Color.white);
    setForeground(Color.BLACK);
    table.setForeground(Color.black);
    super.getTableCellRendererComponent(table, value, selected, focused, row, column);
    if(table.getValueAt(row,columna).toString()!=""){
        if(Integer.valueOf(table.getValueAt(row,columna).toString())<=0)
        {
            setBackground(Color.red);
            setForeground(Color.WHITE);
        }
        else{
            if(Integer.valueOf(table.getValueAt(row,columna).toString())<=7&&Integer.valueOf(table.getValueAt(row,columna).toString())>0){
               setBackground(Color.yellow); 
               setForeground(Color.BLACK);
            }
        }
    }
    else{
        setBackground(Color.white); 
        setForeground(Color.BLACK);
    }
    return this;
}
}