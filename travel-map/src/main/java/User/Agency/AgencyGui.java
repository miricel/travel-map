package User.Agency;

import Essentials.TicketGui;
import com.mysql.jdbc.Connection;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.*;
import Component.BackgroundList;
import Component.BackgroundPanel;
import Component.myRowJPanel;
import chat.Client.ConnectChatWindow;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class AgencyGui {

    private JFrame frame;
    private Agency agency;
    private int index;
    private Connection con;
    private int width = 1000;
    private int height = 700;
    private BufferedImage backgroundimg = resize(ImageIO.read(new File("resources/transports2.jpg")),width,width);


    public AgencyGui(Connection con, int id) throws SQLException, IOException {
        this.con = con;
        agency = new Agency(con, id);
        homePageWindow();
    }

    public JFrame getFrame() {
        return frame;
    }

    private ImageIcon resizedImage(int w, int h, ImageIcon image) {

        Image ri = image.getImage();
        Image modified = ri.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(modified);
        return imageIcon;
    }

    private static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private Color transparentColor(Color color, int transparencyGradient) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparencyGradient);
    }

    public void homePageWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);

    }

    public void profileWindow() throws SQLException {
        frame = new JFrame();
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);

        JLabel lblProfilePic = new JLabel("");
        lblProfilePic.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        lblProfilePic.setBounds(182, 51, 86, 80);
        frame.getContentPane().add(lblProfilePic);

        try {
            ImageIcon pic = agency.getProfilePicture();
            ImageIcon scaled = resizedImage(lblProfilePic.getWidth(), lblProfilePic.getHeight(), pic);
            lblProfilePic.setIcon(scaled);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        JLabel lblCover = new JLabel("");
        lblCover.setBounds(0, 19, 450, 152);
        frame.getContentPane().add(lblCover);

        ImageIcon cover = new ImageIcon(this.getClass().getResource("/fade.png"));
        cover = resizedImage(lblCover.getWidth(), lblCover.getHeight(), cover);
        lblCover.setIcon(cover);

        try {
            String s = agency.getStringColumn("name");
            JLabel lblNameSurname = new JLabel(s);
            lblNameSurname.setHorizontalAlignment(SwingConstants.CENTER);
            lblNameSurname.setBounds(142, 136, 170, 15);
            frame.getContentPane().add(lblNameSurname);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void settingsWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);

        ImageIcon set = new ImageIcon(this.getClass().getResource("/setting.png"));
        set = resizedImage(17, 17, set);

        final JPanel[] panel = new JPanel[5];
        JTextPane[] textPane = new JTextPane[5];
		/*panel[0].setName("Username");
		panel[1].setName("Password");
		panel[2].setName("Name");
		panel[3].setName("Surname");
		*/
        final JLabel[] photolabel = {new JLabel()};
        final int[] correctheight = {200};
        try {
            ImageIcon image = agency.getProfilePicture();

            BufferedImage bi = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            image.paintIcon(null, g, 0,0);
            g.dispose();

            correctheight[0] = image.getIconHeight()*200/image.getIconWidth();

            photolabel[0] = new JLabel(new ImageIcon(resize(bi,200, correctheight[0])));
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        photolabel[0].setBorder(new LineBorder(new Color(0, 0, 0), 2));
        photolabel[0].setPreferredSize(new Dimension(200, correctheight[0]));

        JButton btnUploadPic = new JButton("Upload picture");
        btnUploadPic.setForeground(new Color(255, 255, 255));
        btnUploadPic.setBackground(Color.DARK_GRAY);
        btnUploadPic.setFont(new Font("Liberation Sans", Font.BOLD, 14));
        final JLabel finalLabel = photolabel[0];
        btnUploadPic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                String path = f.getAbsolutePath();
                try {
                    agency.uploadPic(path);
                } catch (FileNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                try {
                    ImageIcon image = agency.getProfilePicture();
                    correctheight[0] = image.getIconHeight()*200/image.getIconWidth();
                    image = resizedImage(200, correctheight[0], image);
                    finalLabel.setIcon(image);
                    finalLabel.setPreferredSize(new Dimension(200, correctheight[0]));
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnUploadPic.setPreferredSize(new Dimension(150,40));

        JPanel photoPanel = new JPanel();
        photoPanel.setBounds(86,130,210,300);
        photoPanel.setOpaque(false);
        photoPanel.add(photolabel[0]);
        photoPanel.add(btnUploadPic);
        contentPanel.add(photoPanel);

        JLabel lblNewLabel = new JLabel("Name");
        lblNewLabel.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lblNewLabel.setBounds(490, 125, 200, 30);
        contentPanel.add(lblNewLabel);

        JLabel lblMyname = new JLabel(agency.getStringColumn("name"));
        lblMyname.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblMyname.setBounds(490, 155, 200, 30);
        contentPanel.add(lblMyname);

        JLabel lblNewLabel_1 = new JLabel("Password");
        lblNewLabel_1.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lblNewLabel_1.setBounds(490, 205, 200, 30);
        contentPanel.add(lblNewLabel_1);

        JLabel lblMysurname = new JLabel("********");
        lblMysurname.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblMysurname.setBounds(490, 235, 200, 30);
        contentPanel.add(lblMysurname);

        JLabel lblUsername = new JLabel("Country");
        lblUsername.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lblUsername.setBounds(490, 285, 200, 30);
        contentPanel.add(lblUsername);

        JLabel lblNewLabel_2 = new JLabel(agency.getStringColumn("country"));
        lblNewLabel_2.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblNewLabel_2.setBounds(490, 315, 200, 30);
        contentPanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Instagram account");
        lblNewLabel_3.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lblNewLabel_3.setBounds(490, 365, 200, 30);
        contentPanel.add(lblNewLabel_3);

        JLabel lblCountry = new JLabel("@"+agency.getStringColumn("insta"));
        lblCountry.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblCountry.setBounds(490, 395, 200, 30);
        contentPanel.add(lblCountry);

        final JButton[] btn = new JButton[5];
        final int[] isPressed = new int[5];
        for (int k = 0, j = 127; k < 4; k++, j = j + 80) {
            final int i = k;
            btn[i] = new JButton();
            btn[i].setBounds(815, j, 30, 30);
            btn[i].setIcon(set);
            btn[i].setContentAreaFilled(false);
            btn[i].setBorder(null);
            btn[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (isPressed[i] % 2 == 0) {
                        panel[i].setVisible(true);
                    }

                    isPressed[i]++;
                }
            });
            contentPanel.add(btn[i]);
        }
        btn[0].setName("username");
        btn[1].setName("password");
        btn[2].setName("name");
        btn[3].setName("surname");

        JButton btnNewButton = new JButton("Save Changes");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int j = 0; j < 5; j++)
                    if (isPressed[j] % 2 != 0)
                        try {
                            agency.setStringColumn(btn[j].getName(), "laal");
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
            }
        });
        btnNewButton.setBackground(SystemColor.desktop);
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setFont(new Font("Liberation Sans", Font.BOLD, 15));
        btnNewButton.setBounds(425, 544, 150, 40);
        contentPanel.add(btnNewButton);


    }

    private void myHeader() {

        index = 0;
        Color color = new Color(0, 0, 0);
        color = transparentColor(color, 200);

        JPanel myHeader = new JPanel();
        myHeader.setBounds(0, 0, 450, 246);
        myHeader.setLayout(null);
        myHeader.setOpaque(false);
        frame.getContentPane().add(myHeader);

        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(null);
        panelHeader.setBackground(new Color(204, 51, 102));
        panelHeader.setBounds(0, 0, 450, 27);
        myHeader.add(panelHeader);

        JLabel lblMenu = new JLabel("MENU");
        lblMenu.setForeground(new Color(255, 255, 255));
        lblMenu.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        lblMenu.setBounds(38, 0, 51, 27);
        panelHeader.add(lblMenu);

        JButton btnMenu = new JButton("||||");
        btnMenu.setBorderPainted(false);
        btnMenu.setBounds(-13, -2, 63, 27);
        panelHeader.add(btnMenu);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setForeground(new Color(255, 255, 255));
        btnMenu.setFont(new Font("Jamrul", Font.BOLD, 14));

        JLabel lblUser = new JLabel();
        try {
            String s = agency.getStringColumn("username");
            s = s + "@traveler";
            lblUser.setText(s);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        lblUser.setForeground(UIManager.getColor("EditorPane.foreground"));
        lblUser.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        lblUser.setBounds(316, 6, 122, 15);
        panelHeader.add(lblUser);

        final JPanel panelMenu = new JPanel();
        panelMenu.setLayout(null);
        panelMenu.setBorder(null);
        panelMenu.setVisible(false);
        panelMenu.setBackground(color);
        panelMenu.setBounds(0, 27, 139, 187);
        myHeader.add(panelMenu);

        JButton btnNewButton_1 = new JButton("Home");
        btnNewButton_1.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_1.setForeground(new Color(255, 255, 255));
        btnNewButton_1.setContentAreaFilled(false);
        btnNewButton_1.setBorderPainted(false);
        btnNewButton_1.setBounds(2, 0, 137, 27);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                try {
                    homePageWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        panelMenu.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("My Agency profile");
        btnNewButton_2.setContentAreaFilled(false);
        btnNewButton_2.setBorderPainted(false);
        btnNewButton_2.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_2.setForeground(new Color(255, 255, 255));
        btnNewButton_2.setBounds(2, 27, 137, 27);
        panelMenu.add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                try {
                    profileWindow();
                } catch (SQLException e) {
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
                frame.setVisible(false);
                try {
                    ticketsWindow();
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

        JButton btnNewButton_6 = new JButton("Settings");
        btnNewButton_6.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
        btnNewButton_6.setForeground(new Color(255, 255, 255));
        btnNewButton_6.setContentAreaFilled(false);
        btnNewButton_6.setBorderPainted(false);
        btnNewButton_6.setBounds(2, 108, 137, 27);
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                try {
                    settingsWindow();
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
                frame.setVisible(false);
                try {
                    chatWindow();
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
        panelMenu.add(btnNewButton_8);


        btnMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                index++;
                if (index % 2 == 0)
                    panelMenu.setVisible(false);
                else panelMenu.setVisible(true);
            }
        });

    }

    public void chatWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(100, 100, 450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        myHeader();
        final String username = agency.getStringColumn("agencies", "username");
        final String password = agency.getStringColumn("agencies", "password");

        JButton Message = new JButton("Message");
        Message.setFont(new Font("Liberation Sans", Font.BOLD, 12));
        Message.setBackground(SystemColor.desktop);
        Message.setForeground(Color.WHITE);
        Message.setBounds(149, 40, 138, 25);
        frame.getContentPane().add(Message);
        Message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ConnectChatWindow(username, password, con);
            }
        });

        JButton someoneElse = new JButton("Not You?");
        someoneElse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnectChatWindow connectChatWindow = new ConnectChatWindow(con);
            }
        });

        someoneElse.setBackground(SystemColor.desktop);
        someoneElse.setForeground(new Color(255, 255, 255));
        someoneElse.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        someoneElse.setBounds(149, 122, 138, 25);
        frame.getContentPane().add(someoneElse);
    }

    public void ticketsWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);

        DefaultListModel<JPanel> listModel = new DefaultListModel<>();
        JList<JPanel> jPanelJList = new JList<>(listModel);

        Statement mystate = null;
        try {
            mystate = con.createStatement();

            String query = "SELECT * FROM tickets where transport_agnecies_id = "+ agency.getID();
            ResultSet myRS = mystate.executeQuery(query);
            while (myRS.next()) {
                TicketGui ticketGui = new TicketGui(con, myRS.getInt("id"));
                listModel.addElement(ticketGui);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(mystate != null) mystate.close();
            }catch(Exception e)
            {
                System.out.println(">Error: " + e);
            }
        }

        myRowJPanel row = new myRowJPanel();
        jPanelJList.setCellRenderer(row);
        jPanelJList.setOpaque(false);
        jPanelJList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        jPanelJList.setVisibleRowCount(1);
        JScrollPane scroll = new JScrollPane(jPanelJList);
        scroll.setBounds(0,130,1000,450);
        scroll.setBorder(new EmptyBorder(0,35,0,35));
        //  Color color = new Color(225, 100, 225);
        // color = transparentColor(color, 100);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        contentPanel.add(scroll);

    }

    private void transportsWindow() {

    }

}