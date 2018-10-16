
package Presentacion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


public class RenderVales extends DefaultTableCellRenderer{
     private int columna ;

public RenderVales(int Colpatron)
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
    if("1".equals(table.getValueAt(row,columna).toString())){
     
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.BLACK);
            setBorder(BorderFactory.createLineBorder(Color.black));
        if(column==0||column==1||column==2){
                setHorizontalAlignment(SwingConstants.RIGHT);
        }
        else{
            setHorizontalAlignment(SwingConstants.LEFT);
        }
        
    }
    else{
        
        setBackground(Color.white); 
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.black));
        if(column==0||column==1||column==2){
                setHorizontalAlignment(SwingConstants.RIGHT);
        }
        else{
            setHorizontalAlignment(SwingConstants.LEFT);
        }
    }
    
    if (table.isRowSelected(row)){
    setBackground(Color.GREEN);
    setForeground(Color.BLACK);
    setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    return this;
}
}
