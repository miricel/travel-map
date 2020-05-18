package User.Agency;

import Essentials.TicketGui;
import Essentials.Transport;
import Essentials.TransportGui;
import User.Utility;
import com.mysql.jdbc.Connection;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.*;
import Component.AddElement;
import Component.BackgroundPanel;
import chat.Client.ConnectChatWindow;

import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;


public class AgencyGui {

    private JFrame frame;
    private Agency agency;
    private int index;
    private Connection con;
    private int width = 1000;
    private int height = 700;
    private BufferedImage backgroundimg = resize(ImageIO.read(new File("resources/transports2.jpg")),width,width);
    private int startW = 350;
    private int startH = 150;
    private boolean flag = false;

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
        frame.setBounds(startW, startH, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);

        JButton seeTickets = new JButton("Approve Tickets");
        seeTickets.setFont(new Font("Liberation Sans", Font.BOLD, 12));
        seeTickets.setBackground(SystemColor.desktop);
        seeTickets.setForeground(Color.WHITE);
        seeTickets.setBounds(430, 200, 140, 25);
        seeTickets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    ticketsWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton seeTransports = new JButton("See Transports");
        seeTransports.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    seeTransportsWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        seeTransports.setBackground(SystemColor.desktop);
        seeTransports.setForeground(new Color(255, 255, 255));
        seeTransports.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        seeTransports.setBounds(430, 300, 140, 25);

        JButton addTransports = new JButton("Add Transport");
        addTransports.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    addTransport();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (FontFormatException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        addTransports.setBackground(SystemColor.desktop);
        addTransports.setForeground(new Color(255, 255, 255));
        addTransports.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        addTransports.setBounds(430, 400, 140, 25);

        contentPanel.add(addTransports);
        contentPanel.add(seeTickets);
        contentPanel.add(seeTransports);

    }

    public void addTransport() throws SQLException, IOException, FontFormatException {

        frame = new JFrame();
        frame.setBounds(startW, startH, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);

        Font font = Font.createFont(Font.TRUETYPE_FONT, TransportGui.class.getResourceAsStream("/romi.ttf"));
        font = font.deriveFont(24f);

        JPanel up = new JPanel();
        JPanel right = new JPanel();
        JPanel left = new JPanel();

        JPanel destination = new JPanel();
        JPanel origin = new JPanel();
        JPanel others = new JPanel();

        Dimension dim = new Dimension(100,  26);

        /**others*/

        //capacity
        JLabel caplbl = new JLabel("Number of Seats:");
        caplbl.setFont(font.deriveFont(Font.BOLD));
        caplbl.setPreferredSize(dim);
        caplbl.setForeground(Color.black);

        //type - jlist?
        JLabel type = new JLabel("Transport Mean:");
        type.setFont(font.deriveFont(Font.BOLD));
        type.setPreferredSize(dim);
        type.setForeground(Color.black);

        // price
        JLabel pricelbl = new JLabel("Price:");
        pricelbl.setFont(font.deriveFont(Font.BOLD));
        pricelbl.setPreferredSize(dim);
        pricelbl.setForeground(Color.black);

        others.add(caplbl);
        others.add(type);
        others.add(pricelbl);

        others.setLayout(new BoxLayout(others,BoxLayout.Y_AXIS));
        others.setBounds(100,90,800,260);
        others.setBackground(new Color(180,120,125));
        others.setBorder(new EmptyBorder(0,100,0,0));

        /**destination*/

        //city
        JLabel citylbl = new JLabel("To::");
        citylbl.setFont(font.deriveFont(Font.BOLD));
        citylbl.setPreferredSize(dim);
        citylbl.setForeground(Color.black);
        //day
        JLabel daylbl = new JLabel("Arrival day:");
        daylbl.setFont(font.deriveFont(Font.BOLD));
        daylbl.setPreferredSize(dim);
        daylbl.setForeground(Color.black);
        //hour
        JLabel hourlbl = new JLabel("Arrival hour:");
        hourlbl.setFont(font.deriveFont(Font.BOLD));
        hourlbl.setPreferredSize(dim);
        hourlbl.setForeground(Color.black);

        destination.add(citylbl);
        destination.add(daylbl);
        destination.add(hourlbl);

        destination.setLayout(new BoxLayout(destination,BoxLayout.Y_AXIS));
        destination.setBounds(505,360,395,250);
        destination.setBackground(new Color(180,120,125));
        destination.setBorder(new EmptyBorder(0,100,0,0));

        /**origin*/

        //city
        JLabel ociytlbl = new JLabel("From:");
        ociytlbl.setFont(font.deriveFont(Font.BOLD));
        ociytlbl.setPreferredSize(dim);
        ociytlbl.setForeground(Color.black);
        //day
        JLabel ocitylbl = new JLabel("Departure day:");
        ocitylbl.setFont(font.deriveFont(Font.BOLD));
        ocitylbl.setPreferredSize(dim);
        ocitylbl.setForeground(Color.black);
        //hour
        JLabel ohourlbl = new JLabel("Departure hour:");
        ohourlbl.setFont(font.deriveFont(Font.BOLD));
        ohourlbl.setPreferredSize(dim);
        ohourlbl.setForeground(Color.black);

        origin.add(ociytlbl);
        origin.add(ocitylbl);
        origin.add(ohourlbl);

        origin.setLayout(new BoxLayout(origin,BoxLayout.Y_AXIS));
        origin.setBounds(100,360,395,250);
        origin.setBackground(new Color(180,120,125));
        origin.setBorder(new EmptyBorder(0,100,0,0));

        contentPanel.add(others);
        contentPanel.add(origin);
        contentPanel.add(destination);

      /*  up.setBounds(100,80,800,200);
        left.setBounds(100,80,800,200);
        right.setBounds(100,80,800,200);
        others.setBackground(new Color(180,120,125));
        others.setBackground(new Color(180,120,125));
        others.setBackground(new Color(180,120,125));
*/


    }

    public void settingsWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(startW, startH, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);

        JTextPane []change = new JTextPane[5];

        ImageIcon set = new ImageIcon(this.getClass().getResource("/setting.png"));
        set = resizedImage(21, 21, set);

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
        photoPanel.setBounds(100,176,210,300);
        photoPanel.setOpaque(false);
        photoPanel.add(photolabel[0]);
        photoPanel.add(btnUploadPic);
        contentPanel.add(photoPanel);

        int x = 490;
        int plus = 30;
        int pl = 10;
        int y = 125;

        JLabel lblNewLabel = new JLabel("Name");
        lblNewLabel.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lblNewLabel.setBounds(x, y, 200, 30);
        contentPanel.add(lblNewLabel);
        y+=plus;

        JLabel lblMyname = new JLabel(agency.getStringColumn("name"));
        lblMyname.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblMyname.setBounds(x, y, 200, 30);
        contentPanel.add(lblMyname);
        y+=plus+pl;

        JLabel lbluserrname = new JLabel("Username");
        lbluserrname.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lbluserrname.setBounds(x, y, 200, 30);
        contentPanel.add(lbluserrname);
        y+=plus;

        JLabel lblmysurname = new JLabel(agency.getStringColumn("username"));
        lblmysurname.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblmysurname.setBounds(x, y, 200, 30);
        contentPanel.add(lblmysurname);
        y+=plus+pl;

        JLabel lblNewLabel_1 = new JLabel("Password");
        lblNewLabel_1.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lblNewLabel_1.setBounds(x, y, 200, 30);
        contentPanel.add(lblNewLabel_1);
        y+=plus;

        JLabel lblMysurname = new JLabel("********");
        lblMysurname.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblMysurname.setBounds(x, y, 200, 30);
        contentPanel.add(lblMysurname);
        y+=plus+pl;

        JLabel lblUsername = new JLabel("Country");
        lblUsername.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lblUsername.setBounds(x, y, 200, 30);
        contentPanel.add(lblUsername);
        y+=plus;

        JLabel lblNewLabel_2 = new JLabel(agency.getStringColumn("country"));
        lblNewLabel_2.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblNewLabel_2.setBounds(x, y, 200, 30);
        contentPanel.add(lblNewLabel_2);
        y+=plus+pl;

        JLabel lblNewLabel_3 = new JLabel("Instagram account");
        lblNewLabel_3.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        lblNewLabel_3.setBounds(x, y, 200, 30);
        contentPanel.add(lblNewLabel_3);
        y+=plus;

        JLabel lblCountry = new JLabel("@"+agency.getStringColumn("insta"));
        lblCountry.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
        lblCountry.setBounds(x, y, 200, 30);
        contentPanel.add(lblCountry);
        y+=plus;

        final JButton[] btn = new JButton[5];
        final int[] isPressed = new int[5];
        for (int k = 0, j = 127; k < 5; k++, j = j + 80) {
            final int i = k;
            btn[i] = new JButton();
            btn[i].setBounds(850, j, 30, 30);
            btn[i].setIcon(set);
            btn[i].setContentAreaFilled(false);
            btn[i].setBorder(null);
            btn[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (isPressed[i] % 2 == 0) {
                        change[i].setText(null);
                        change[i].setVisible(true);
                        change[i].setOpaque(true);
                    }
                    else change[i].setVisible(false);
                    isPressed[i]++;
                }
            });
            contentPanel.add(btn[i]);
        }
        btn[0].setName("name");
        btn[1].setName("username");
        btn[2].setName("password");
        btn[3].setName("country");
        btn[4].setName("insta");

        JPasswordField textField = new JPasswordField();
        textField.setBounds(300, 560, 190, 25);
        contentPanel.add(textField);
        textField.setColumns(10);

        JLabel lblIntroduceCurrentPasssword = new JLabel("Current passsword");
        lblIntroduceCurrentPasssword.setFont(new Font("Liberation Sans", Font.BOLD, 15));
        lblIntroduceCurrentPasssword.setBounds(303, 539, 150, 20);
        contentPanel.add(lblIntroduceCurrentPasssword);

        JButton btnNewButton = new JButton("Save Changes");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flag = false;

                try {
                    if(agency.isCorrectPassword(Utility.hashPassword(textField.getText())))
                        for(int j = 0; j < 5; j++)
                            if(isPressed[j]%2 != 0)
                                try {
                                    agency.setStringColumn(btn[j].getName(), change[j].getText());
                                } catch (SQLException e1) {
                                    flag = true;
                                    JOptionPane.showMessageDialog(null, "This username is already taken!");
                                    frame.setVisible(false);
                                    e1.printStackTrace();
                                }
                } catch (HeadlessException | NoSuchAlgorithmException | SQLException e2) {
                    JOptionPane.showMessageDialog(null, "Wrong password!");
                    flag = true;
                    frame.setVisible(false);
                    e2.printStackTrace();
                }
                if(!flag) {
                    JOptionPane.showMessageDialog(null, "Changes performed!");
                    frame.setVisible(false);
                }
                try {
                    settingsWindow();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });
        btnNewButton.setBackground(SystemColor.desktop);
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setFont(new Font("Liberation Sans", Font.BOLD, 15));
        btnNewButton.setBounds(520, 535, 170, 50);
        contentPanel.add(btnNewButton);

        for(int i = 0; i < 5; i++) {
            change[i]= new JTextPane();
            change[i].setBounds(x, 155+i*(70), 170, 25);
            change[i].setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            change[i].setVisible(false);
            contentPanel.add(change[i]);
        }

        JPanel label_2 = new JPanel();
        Color c = new Color(255, 255, 255);
        c = transparentColor(c, 150);
        label_2.setBackground(c);
        label_2.setBounds(66, 83, 850, 525);
        contentPanel.add(label_2);


    }

    public void chatWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(startW, startW, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);

        final String username = agency.getStringColumn("agencies", "username");
        final String password = agency.getStringColumn("agencies", "password");

        JButton Message = new JButton("Message");
        Message.setFont(new Font("Liberation Sans", Font.BOLD, 12));
        Message.setBackground(SystemColor.desktop);
        Message.setForeground(Color.WHITE);
        Message.setBounds(430, 200, 140, 25);
        contentPanel.add(Message);
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
        someoneElse.setBounds(430, 300, 140, 25);
        contentPanel.add(someoneElse);
    }

    public void ticketsWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(startW, startH, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);


        AddElement tickets = new AddElement(1,0,1,1,1000,450,35,50);
        tickets.setOpaque(false);

        Statement mystate = null;
        try {
            mystate = con.createStatement();

            String query = "SELECT * FROM tickets where transport_agnecies_id = "+ agency.getID();
            ResultSet myRS = mystate.executeQuery(query);
            while (myRS.next()) {
                TicketGui ticketGui = new TicketGui(con, myRS.getInt("id"));
                tickets.addNew(ticketGui);
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
        contentPanel.add(tickets);

    }

    public void seeTransportsWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(startW, startH, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);


        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg,width,width);
        contentPanel.setBounds(0,0,width,height);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(agency.getStringColumn("username"),this);


        AddElement transports = null;
        try {
            transports = new AddElement(0,1,1,1,1000,460,90,90);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        transports.setOpaque(false);
        Statement mystate = null;
        try {
            mystate = con.createStatement();

            String query = "SELECT * FROM transport where agnecies_id = "+ agency.getID();
            ResultSet myRS = mystate.executeQuery(query);
            while (myRS.next()) {
                TransportGui transportGui = new TransportGui(con, myRS.getInt("id"));
                transports.addNew(transportGui);
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
        contentPanel.add(transports);

    }

    public Connection getCon() {
        return con;
    }
}