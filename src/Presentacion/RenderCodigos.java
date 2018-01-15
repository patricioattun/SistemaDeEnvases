
package Presentacion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


public class RenderCodigos extends DefaultTableCellRenderer {
    private int enBase;
    
    
    public RenderCodigos(int base)
        {
            this.enBase = base;
            
        }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    setBackground(Color.white);
    setForeground(Color.BLACK);
    setBorder(BorderFactory.createLineBorder(Color.black)); 
    table.setForeground(Color.black);
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
    setBorder(BorderFactory.createLineBorder(Color.black));
    if("1".equals(table.getValueAt(row,enBase).toString())){
            setBackground(Color.LIGHT_GRAY);
            setForeground(Color.BLACK);
            table.setForeground(Color.black);
            
    }
        switch (column) {
            case 2:
            case 3:
                setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            case 1:
                setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case 0:
                setHorizontalAlignment(SwingConstants.CENTER);
                break;
            default:
                break;
        }
    if (table.isRowSelected(row)){
    setBackground(Color.GREEN);
    setForeground(Color.BLACK);
    setBorder(BorderFactory.createLineBorder(Color.black));
    }
   
            
    return this;
        
    }
}
