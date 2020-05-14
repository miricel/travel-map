package Component;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

class Button extends JButton {

    JLabel lbl;
    private Color colo1,colo2;
    private String str;

    public Button(final Color colo1, final Color colo2, String str){

        this.colo1 = colo1;
        this.colo2 = colo2;
        this.str = str;

        setBackground(colo1);
        setLayout(null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                    setBackground(colo2);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(colo1);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(colo2.darker());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(colo1);
                }

        });


        lbl = new JLabel(str);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 25));
        lbl.setBounds(50, 0, 230, 50);
        lbl.setHorizontalAlignment(JTextField.CENTER);
        add(lbl);
    }

    public void setText(String str){
        this.str = str;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return (new Dimension(200, 20));
    }

}
