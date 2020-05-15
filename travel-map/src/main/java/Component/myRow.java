package Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.*;

public class myRow extends JPanel implements ListCellRenderer{

    //private final ArrayList<String> users;
    private Color color1;
    private Color color2;
    private JLabel lbl = new JLabel();

    public myRow(){
       // this.users = users;
        setBackground(new Color(223,170,180));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        setPreferredSize(new Dimension(300,30));
        String user = value.toString();

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

        return this;

    }

   /* private boolean isOnline(String user) {
        for(String username: users){
            if(username.equals(user))
            {
                System.out.println(">>"+username);
                return true;
            }
        }
        return false;
    }

    */
}
