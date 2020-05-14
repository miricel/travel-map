package Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class myRowJPanel extends JPanel implements ListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        //add((JPanel)value);
       /* String user = value.toString();

        final Color background1 = color1;
        final Color background2 =  color2;
        setBorder(BorderFactory.createLineBorder(new Color(204, 51, 102)));
        setLayout(null);

        lbl.setText(user);
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl.setForeground(Color.BLACK);
        lbl.setOpaque(false);
        lbl.setBounds(10,3,200,25);
        add(lbl);

        */
        return (JPanel) value;

    }


}