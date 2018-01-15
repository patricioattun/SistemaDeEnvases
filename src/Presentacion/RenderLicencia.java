
package Presentacion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderLicencia extends DefaultTableCellRenderer {
    private int columna ;
    public RenderLicencia(int Colpatron)
{
    this.columna = Colpatron;
}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    setBackground(Color.white);
    setForeground(Color.BLACK);
    table.setForeground(Color.black);
    
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
    if(table.getValueAt(row,columna).toString()=="true"){
            setBackground(Color.red);
            setForeground(Color.WHITE);
            
            table.setForeground(Color.black);
            setBorder(BorderFactory.createLineBorder(Color.black));
            if(column==0||column==2||column==3){
                setHorizontalAlignment(SwingConstants.RIGHT);
            }
            else if(column==5){
                 setHorizontalAlignment(SwingConstants.LEFT);
            }
    }
    else{
        setBackground(Color.white); 
        setForeground(Color.BLACK);
        table.setForeground(Color.black);
        setBorder(BorderFactory.createLineBorder(Color.black));
        if(column==0||column==2||column==3){
                setHorizontalAlignment(SwingConstants.RIGHT);
        }
        else if(column==5){
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
