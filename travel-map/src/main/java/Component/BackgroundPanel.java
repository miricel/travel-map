package Component;

import User.Agency.AgencyGui;
import User.Guest.Guest;
import User.Guest.GuestGui;
import User.Traveller.TravelerGui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class BackgroundPanel extends JPanel {
    private final int width;
    private final int height;
    private BufferedImage background;
    private int index;

    public BackgroundPanel(String image, int width, int height) {

        this.width = width;
        this.height = height;
        try {
            background = ImageIO.read(new File(image));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private ImageIcon resizedImage(int w, int h, ImageIcon image) {

        Image ri = image.getImage();
        Image modified = ri.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(modified);
        return imageIcon;
    }

    public BackgroundPanel(BufferedImage backgroundimg, int width, int height) {
        this.width = width;
        this.height = height;
        this.background = backgroundimg;
    }

    public void myHeader(String username, final TravelerGui travelerGui) {

        index = 0;
        Color color = new Color(0, 0, 0);
        color = transparentColor(color, 200);

        JPanel myHeader = new JPanel();
        myHeader.setBounds(0, 0, 1000, 261);
        myHeader.setLayout(null);
        myHeader.setOpaque(false);
        travelerGui.frame.getContentPane().add(myHeader);

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(204, 51, 102));
        panelHeader.setBounds(0, 0, 1000, 27);
        myHeader.add(panelHeader);

        JLabel lblMenu = new JLabel("MENU");
        lblMenu.setForeground(new Color(255, 255, 255));
        lblMenu.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        lblMenu.setBounds(38, 0, 51, 27);
        panelHeader.add(lblMenu);

        JButton btnMenu =new JButton("||||");
        btnMenu.setBorderPainted(false);
        btnMenu.setBounds(-13, -2, 63, 27);
        panelHeader.add(btnMenu);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setForeground(new Color(255, 255, 255));
        btnMenu.setFont(new Font("Jamrul", Font.BOLD, 14));

        JLabel lblUser = new JLabel();
        try {
            String s = travelerGui.traveler.getStringColumn("username");
            s = s + "@traveler";
            lblUser.setText(s);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        lblUser.setForeground(UIManager.getColor("EditorPane.foreground"));
        lblUser.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        lblUser.setBounds(827, 6, 142, 15);
        panelHeader.add(lblUser);

        JTextPane txtpnSearchUser = new JTextPane();
        txtpnSearchUser.setForeground(Color.BLACK);
        txtpnSearchUser.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        txtpnSearchUser.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        txtpnSearchUser.setBackground(new Color(214, 61, 112));
        txtpnSearchUser.setBounds(284, 6, 191, 17);
        panelHeader.add(txtpnSearchUser);

        JButton lblNewLabel_4= new JButton();
        lblNewLabel_4.setContentAreaFilled(false);
        lblNewLabel_4.setBorder(null);
        ImageIcon search = new ImageIcon(this.getClass().getResource("/search.png"));
        search = resizedImage(15, 15, search);
        lblNewLabel_4.setIcon(search);
        lblNewLabel_4.setBounds(487, 0, 25, 27);
        panelHeader.add(lblNewLabel_4);


        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(null);
        panelMenu.setBorder(null);
        panelMenu.setVisible(false);
        panelMenu.setBackground(color);
        panelMenu.setBounds(0, 27, 139, 219);
        myHeader.add(panelMenu);

        JButton btnNewButton_1 = new JButton("Home");
        btnNewButton_1.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_1.setForeground(new Color(255, 255, 255));
        btnNewButton_1.setContentAreaFilled(false);
        btnNewButton_1.setBorderPainted(false);
        btnNewButton_1.setBounds(2, 0, 137, 27);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                travelerGui.frame.setVisible(false);
                travelerGui.homePageWindow();
            }
        });
        panelMenu.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("My profile");
        btnNewButton_2.setContentAreaFilled(false);
        btnNewButton_2.setBorderPainted(false);;
        btnNewButton_2.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_2.setForeground(new Color(255, 255, 255));
        btnNewButton_2.setBounds(2, 27, 137, 27);
        panelMenu.add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                travelerGui.frame.setVisible(false);
                travelerGui.profileWindow();
            }
        });

        JButton btnNewButton_3 = new JButton("Search Transport");
        btnNewButton_3.setContentAreaFilled(false);
        btnNewButton_3.setBorderPainted(false);;
        btnNewButton_3.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_3.setForeground(new Color(255, 250, 250));
        btnNewButton_3.setBounds(2, 54, 137, 27);
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                travelerGui.frame.setVisible(false);
                try {
                    travelerGui.seeTransportsWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        panelMenu.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("Feed");
        btnNewButton_4.setContentAreaFilled(false);
        btnNewButton_4.setBorderPainted(false);;
        btnNewButton_4.setForeground(new Color(255, 255, 255));
        btnNewButton_4.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_4.setBounds(2, 81, 137, 27);
        panelMenu.add(btnNewButton_4);
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                travelerGui.frame.setVisible(false);
                try {
                    travelerGui.feedWindow();
                } catch (SQLException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        JButton btnNewButton_5 = new JButton("Find tickets");
        btnNewButton_5.setContentAreaFilled(false);
        btnNewButton_5.setBorderPainted(false);;
        btnNewButton_5.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_5.setForeground(new Color(255, 255, 255));
        btnNewButton_5.setBounds(2, 108, 137, 27);
        panelMenu.add(btnNewButton_5);
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                travelerGui.frame.setVisible(false);
            }
        });

        JButton btnNewButton_6 = new JButton("Settings");
        btnNewButton_6.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_6.setForeground(new Color(255, 255, 255));
        btnNewButton_6.setContentAreaFilled(false);
        btnNewButton_6.setBorderPainted(false);
        btnNewButton_6.setBounds(2, 135, 137, 27);
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                travelerGui.frame.setVisible(false);
                try {
                    travelerGui.settingsWindow();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        panelMenu.add(btnNewButton_6);

        JButton btnNewButton_7 = new JButton("Log out");
        btnNewButton_7.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_7.setForeground(new Color(255, 255, 255));
        btnNewButton_7.setContentAreaFilled(false);
        btnNewButton_7.setBorderPainted(false);
        btnNewButton_7.setBounds(2, 162, 137, 27);
        btnNewButton_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                travelerGui.frame.setVisible(false);
            }
        });
        panelMenu.add(btnNewButton_7);

        btnMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                index++;
                if(index%2 == 0)
                    panelMenu.setVisible(false);
                else panelMenu.setVisible(true);
            }
        });

    }

    public void myHeader(String username, final AgencyGui agencyGui) {

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, width, height);
        panel.setOpaque(false);
        panel.setLayout(null);
        final int[] index = {0};
        Color color = new Color(0, 0, 0);
        color = transparentColor(color, 200);
        int h = 40;

        JPanel myHeader = new JPanel();
        myHeader.setBounds(0, 0, width, height);
        myHeader.setLayout(null);
        myHeader.setOpaque(false);
        panel.add(myHeader);

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(204, 51, 102));
        panelHeader.setBounds(0, 0, width, h);
        myHeader.add(panelHeader);

        JLabel lblMenu = new JLabel("MENU");
        lblMenu.setForeground(new Color(255, 255, 255));
        lblMenu.setFont(new Font("Liberation Sans", Font.BOLD, 15));
        lblMenu.setBounds(38, 2, 51, 30);
        panelHeader.add(lblMenu);

        JButton btnMenu = new JButton("||||");
        btnMenu.setBorderPainted(false);
        btnMenu.setBounds(-13, 0, 63, 35);
        panelHeader.add(btnMenu);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setForeground(new Color(255, 255, 255));
        btnMenu.setFont(new Font("Jamrul", Font.BOLD, 16));

        JLabel lblUser = new JLabel();
        String s = username + "@traveler";
        lblUser.setText(s);
        lblUser.setForeground(UIManager.getColor("EditorPane.foreground"));
        lblUser.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblUser.setBounds((width-150), 6, 122, 28);
        panelHeader.add(lblUser);

        final JPanel panelMenu = new JPanel();
        panelMenu.setLayout(null);
        panelMenu.setBorder(null);
        panelMenu.setVisible(false);
        panelMenu.setBackground(color);
        panelMenu.setBounds(0, h, 139, 187);
        myHeader.add(panelMenu);

        JButton btnNewButton_1 = new JButton("Home");
        btnNewButton_1.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_1.setForeground(new Color(255, 255, 255));
        btnNewButton_1.setContentAreaFilled(false);
        btnNewButton_1.setBorderPainted(false);
        btnNewButton_1.setBounds(2, 0, 137, 27);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agencyGui.getFrame().setVisible(false);
                try {
                    agencyGui.homePageWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        panelMenu.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Add Transport");
        btnNewButton_2.setContentAreaFilled(false);
        btnNewButton_2.setBorderPainted(false);
        btnNewButton_2.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_2.setForeground(new Color(255, 255, 255));
        btnNewButton_2.setBounds(2, 27, 137, 27);
        panelMenu.add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agencyGui.getFrame().setVisible(false);
                try {
                    agencyGui.addTransport();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnNewButton_3 = new JButton("Tickets");
        btnNewButton_3.setContentAreaFilled(false);
        btnNewButton_3.setBorderPainted(false);
        btnNewButton_3.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_3.setForeground(new Color(255, 250, 250));
        btnNewButton_3.setBounds(2, 54, 137, 27);
        panelMenu.add(btnNewButton_3);
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agencyGui.getFrame().setVisible(false);
                try {
                    agencyGui.ticketsWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnNewButton_4 = new JButton("Transports");
        btnNewButton_4.setContentAreaFilled(false);
        btnNewButton_4.setBorderPainted(false);
        btnNewButton_4.setForeground(new Color(255, 255, 255));
        btnNewButton_4.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_4.setBounds(2, 81, 137, 27);
        panelMenu.add(btnNewButton_4);
        btnNewButton_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
            agencyGui.getFrame().setVisible(false);
            try {
                agencyGui.seeTransportsWindow();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    });

        JButton btnNewButton_6 = new JButton("Settings");
        btnNewButton_6.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_6.setForeground(new Color(255, 255, 255));
        btnNewButton_6.setContentAreaFilled(false);
        btnNewButton_6.setBorderPainted(false);
        btnNewButton_6.setBounds(2, 108, 137, 27);
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agencyGui.getFrame().setVisible(false);
                try {
                    agencyGui.settingsWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        panelMenu.add(btnNewButton_6);

        JButton btnNewButton_7 = new JButton("Messages");
        btnNewButton_7.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_7.setForeground(new Color(255, 255, 255));
        btnNewButton_7.setContentAreaFilled(false);
        btnNewButton_7.setBorderPainted(false);
        btnNewButton_7.setBounds(2, 135, 137, 27);
        btnNewButton_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agencyGui.getFrame().setVisible(false);
                try {
                    agencyGui.chatWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        panelMenu.add(btnNewButton_7);

        JButton btnNewButton_8 = new JButton("Log Out");
        btnNewButton_8.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_8.setForeground(new Color(255, 255, 255));
        btnNewButton_8.setContentAreaFilled(false);
        btnNewButton_8.setBorderPainted(false);
        btnNewButton_8.setBounds(2, 162, 137, 27);
        btnNewButton_8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agencyGui.getFrame().setVisible(false);
                try {
                    new GuestGui(agencyGui.getCon()).logInWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        panelMenu.add(btnNewButton_8);


        btnMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                index[0]++;
                if (index[0] % 2 == 0)
                    panelMenu.setVisible(false);
                else panelMenu.setVisible(true);
            }
        });

        add(panel);
    }

    private Color transparentColor(Color color, int transparencyGradient) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparencyGradient);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            int x = getWidth() - background.getWidth();
            int y = getHeight() - background.getHeight();
            g2d.drawImage(background, x, y, this);
            g2d.dispose();
        }
    }
}
