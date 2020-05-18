package Essentials;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class ReviewGui extends JPanel {

    public int id;
    public String content, title, namesurname;
    public ImageIcon pic, travelerPic;
    public JFrame frame;

    public class userButton extends JButton {
        public int id;

        public userButton(int id) {
            this.id = id;
        }
    }

    public ReviewGui(JFrame frame) {
        this.frame = frame;
    }

    private Color transparentColor(Color color, int transparencyGradient) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparencyGradient);
    }

    private ImageIcon resizedImage(int w, int h, ImageIcon image) {
        Image ri = image.getImage();
        Image modified = ri.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(modified);
        return imageIcon;
    }

    public void buildReviewPanel() {

        Color c = new Color(255, 255, 255);
        c = transparentColor(c, 190);
        this.setPreferredSize(new Dimension(600, 330));
        this.setOpaque(false);
        this.setLayout(null);
        this.setBorder(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(130, 20, 300, 40);
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        this.add(panel);

        userButton btnNewButton_8 = new userButton(id);
        btnNewButton_8.setIcon(resizedImage(25, 25, this.travelerPic));
        btnNewButton_8.setBounds(12, 7, 25, 25);
        btnNewButton_8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                System.out.println(btnNewButton_8.id);
            }
        });
        panel.add(btnNewButton_8);

        JLabel lblNewLabel_5 = new JLabel();
        lblNewLabel_5.setText(this.namesurname);
        lblNewLabel_5.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5.setBounds(42, 10, 169, 15);
        panel.add(lblNewLabel_5);

        JPanel p = new JPanel();
        p.setBackground(c);
        p.setLayout(null);
        p.setBounds(90, 35, 750, 270);
        this.add(p);

        JLabel label = new JLabel();
        label.setBounds(40, 40, 200, 200);
        label.setIcon(resizedImage(170, 170, this.pic));
        p.add(label);

        JLabel lblTitle = new JLabel();
        lblTitle.setText(this.title);
        lblTitle.setFont(new Font("Liberation Sans", Font.BOLD, 17));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(330, 25, 262, 15);
        p.add(lblTitle);

        JEditorPane content = new JEditorPane();
        content.setText(this.content);
        content.setOpaque(false);
        content.setForeground(Color.GRAY);
        content.setEditable(false);
        content.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        content.setBounds(260, 50, 440, 170);
        p.add(content);

    }

}

