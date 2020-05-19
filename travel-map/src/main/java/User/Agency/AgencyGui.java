package User.Agency;

import Essentials.TicketGui;
import Essentials.Transport;
import Essentials.TransportGui;
import User.Utility;
import com.mysql.jdbc.Connection;


import java.awt.*;
import java.awt.event.*;
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

    public void homePageWindow() throws SQLException, IOException {

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
        seeTickets.setBackground(new Color(204, 51, 102));
        seeTickets.setForeground(Color.WHITE);
        seeTickets.setBounds(400, 260, 200, 25);
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

        BufferedImage   saveimg =  resize(ImageIO.read(new File("resources/tick.png")),190,190);
        JButton Tick = new JButton(new ImageIcon(saveimg));
        Tick.setContentAreaFilled(false);
        Tick.setBackground(new Color(180,120,125));
        Tick.setBorder(null);
        Tick.setBounds(400,60,200,200);
        Tick.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    ticketsWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPanel.add(Tick);

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
        seeTransports.setBackground(new Color(204, 51, 102));
        seeTransports.setForeground(new Color(255, 255, 255));
        seeTransports.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        seeTransports.setBounds(150, 550, 200, 25);
        BufferedImage   saveimg3 =  resize(ImageIO.read(new File("resources/tr.png")),190,190);
        JButton See = new JButton(new ImageIcon(saveimg3));
        See.setContentAreaFilled(false);
        See.setBackground(new Color(180,120,125));
        See.setBorder(null);
        See.setBounds(150,350,200,200);
        See.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    seeTransportsWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPanel.add(See);


        BufferedImage   saveimg2 =  resize(ImageIO.read(new File("resources/addtt.png")),190,190);
        JButton Add = new JButton(new ImageIcon(saveimg2));
        Add.setContentAreaFilled(false);
        Add.setBackground(new Color(180,120,125));
        Add.setBorder(null);
        Add.setBounds(650,350,200,200);
        Add.addActionListener(new ActionListener() {
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

        addTransports.setBackground((new Color(204, 51, 102)));
        addTransports.setForeground(new Color(255, 255, 255));
        addTransports.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        addTransports.setBounds(650,550,200,25);
        contentPanel.add(Add);

        contentPanel.add(addTransports);
        contentPanel.add(seeTickets);
        contentPanel.add(seeTransports);

        JPanel trans3 = new JPanel();
        trans3.setBackground(new Color(200,200,200,150));
        trans3.setBounds(600,340,300,260);
        contentPanel.add(trans3);

        JPanel trans = new JPanel();
        trans.setBackground(new Color(200,200,200,150));
        trans.setBounds(350,60,300,250);
        contentPanel.add(trans);
        JPanel trans2 = new JPanel();
        trans2.setBackground(new Color(200,200,200,150));
        trans2.setBounds(100,340,300,260);
        contentPanel.add(trans2);

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
        font = font.deriveFont(21f);

        JPanel up = new JPanel();
        JPanel right = new JPanel();
        JPanel left = new JPanel();

        JPanel destination = new JPanel();
        JPanel origin = new JPanel();
        JPanel others = new JPanel();

        int x = 250;
        int y = 26;

        /**others*/

        //capacity
        JPanel capanel = new JPanel();
        JLabel caplbl = new JLabel("Number of Seats:");
        caplbl.setFont(font.deriveFont(Font.BOLD));
        caplbl.setBounds(43,20,x,y);
        caplbl.setForeground(Color.black);
        JTextPane captxt = new JTextPane();
        captxt.setBounds(40,50,x,y);
        capanel.setBounds(40,50,x,y);
        capanel.setBackground(new Color(255,255,255,150));
        captxt.setOpaque(false);
        captxt.setFont(font.deriveFont(17f));
        captxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(captxt.getText().equals("Capacity"))
                    captxt.setText("");
            }

            public void mouseExited(MouseEvent e){
                if(captxt.getText().equals(""))
                    captxt.setText("Capacity");
            }

        });
        captxt.setText("Capacity");


        //type
        JLabel type = new JLabel("Transport Mean:");
        type.setFont(font.deriveFont(Font.BOLD));
        type.setBounds(43,90,x,y);
        type.setForeground(Color.black);

        BufferedImage boat = RoundImage("resources/boatmini.png",70);
        BufferedImage plane = RoundImage("resources/planemini.png",70);
        BufferedImage train = RoundImage("resources/trainmini.png",70);
        BufferedImage bus = RoundImage("resources/busmini.png",70);

        JButton []btn = new JButton[4];
        btn[0]= new JButton(new ImageIcon(bus));
        btn[1]= new JButton(new ImageIcon(train));
        btn[2]= new JButton(new ImageIcon(plane));
        btn[3]= new JButton(new ImageIcon(boat));
        final String[] trmean = {"plane"};
        int []isPressed = new int[4];
        for(int k = 0, j = 40; k < 4; k++, j = j + 85) {
            final int i = k;
            btn[i].setBounds(j, 115, 70, 70);
            btn[i].setContentAreaFilled(false);
            btn[i].setBackground(new Color(180,120,125));
            btn[i].setBorder(null);
            btn[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if(isPressed[i]%2 == 0) {
                        trmean[0] = btn[i].getName();
                        btn[i].setOpaque(true);
                        for(int m = 0; m<4; m++){
                            if(m != i) {
                                btn[m].setOpaque(false);
                                btn[m].revalidate();
                                btn[m].repaint();
                            }
                        }
                    }
                    isPressed[i]++;
                }
            });
            others.add(btn[i]);
        }
        btn[0].setName("bus");
        btn[1].setName("train");
        btn[2].setName("plane");
        btn[3].setName("ferryboat");


        // price
        JPanel prpanel = new JPanel();
        JLabel pricelbl = new JLabel("Price:");
        pricelbl.setFont(font.deriveFont(Font.BOLD));
        pricelbl.setBounds(43,190,x,y);
        pricelbl.setForeground(Color.black);
        JTextPane pricetxt = new JTextPane();
        pricetxt.setBounds(40,215,x,y);
        prpanel.setBounds(40,215,x,y);
        prpanel.setBackground(new Color(255,255,255,150));
        pricetxt.setOpaque(false);
        pricetxt.setFont(font.deriveFont(17f));
        pricetxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(pricetxt.getText().equals("Price $"))
                    pricetxt.setText("");
            }

            public void mouseExited(MouseEvent e){
                if(pricetxt.getText().equals(""))
                    pricetxt.setText("Price $");
            }

        });
        pricetxt.setText("Price $");

        others.add(caplbl);
        others.add(captxt);
        others.add(type);
        others.add(pricelbl);
        others.add(pricetxt);
        others.add(prpanel);
        others.add(capanel);

        others.setLayout(null);
        others.setBounds(100,90,800,260);
        up.setBounds(100,90,800,260);
        others.setOpaque(false);
        others.setBorder(new EmptyBorder(15,100,0,0));
        up.setBorder(new EmptyBorder(0,100,0,0));

        /**destination*/

/*
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JPanel destcal = new JPanel();
        destcal.setOpaque(false);
        destcal.add(datePicker);
        destcal.setBounds(43,115,x,y+10);
       Date selectedDate = (Date) datePicker.getModel().getValue();



        destination.add(destcal);
        */

        destination.setLayout(null);
        //city
        JPanel frompnl = new JPanel();
        JLabel fromlbl = new JLabel("To:");
        fromlbl.setFont(font.deriveFont(Font.BOLD));
        fromlbl.setBounds(43,20,x,y);
        fromlbl.setForeground(Color.black);
        JTextPane fromtxt = new JTextPane();
        fromtxt.setBounds(40,50,x,y);
        frompnl.setBounds(40,50,x,y);
        frompnl.setBackground(new Color(255,255,255,150));
        fromtxt.setOpaque(false);
        fromtxt.setFont(font.deriveFont(17f));
        fromtxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(fromtxt.getText().equals("Destination City"))
                    fromtxt.setText("");
            }

            public void mouseExited(MouseEvent e){
                if(fromtxt.getText().equals(""))
                    fromtxt.setText("Destination City");
            }

        });
        fromtxt.setText("Destination City");
        destination.add(fromtxt);
        destination.add(frompnl);
        destination.add(fromlbl);

        //day
        JPanel daypnl = new JPanel();
        JLabel daylbl = new JLabel("Arrival day:");
        daylbl.setFont(font.deriveFont(Font.BOLD));
        daylbl.setBounds(43,90,x,y);
        daylbl.setForeground(Color.black);
        JTextPane daytxt = new JTextPane();
        daytxt.setBounds(40,120,x,y);
        daypnl.setBounds(40,120,x,y);
        daypnl.setBackground(new Color(255,255,255,150));
        daytxt.setOpaque(false);
        daytxt.setFont(font.deriveFont(17f));
        daytxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(daytxt.getText().equals("YYYY-MM-DD"))
                daytxt.setText("");
            }

            public void mouseExited(MouseEvent e){
                if(daytxt.getText().equals(""))
                    daytxt.setText("YYYY-MM-DD");
            }

        });
        daytxt.setText("YYYY-MM-DD");
        destination.add(daytxt);
        destination.add(daypnl);
        destination.add(daylbl);
        //hour

       /* Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        SpinnerDateModel mod = new SpinnerDateModel();
        mod.setValue(calendar.getTime());

        JSpinner spinner = new JSpinner(mod);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);

        spinner.setEditor(editor);
        spinner.setBounds(40,190,x,y);


        destination.add(spinner);

        */

        JPanel hpnl = new JPanel();
        JLabel hlbl = new JLabel("Arrival hour:");
        hlbl.setFont(font.deriveFont(Font.BOLD));
        hlbl.setBounds(43,160,x,y);
        hlbl.setForeground(Color.black);
        JTextPane htxt = new JTextPane();
        htxt.setBounds(40,190,x,y);
        hpnl.setBounds(40,190,x,y);
        hpnl.setBackground(new Color(255,255,255,150));
        htxt.setOpaque(false);
        htxt.setFont(font.deriveFont(17f));
        htxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(htxt.getText().equals("hh:mm"))
                    htxt.setText("");
            }

            public void mouseExited(MouseEvent e){
                if(htxt.getText().equals(""))
                    htxt.setText("hh:mm");
            }

        });
        htxt.setText("hh:mm");
        destination.add(htxt);
        destination.add(hpnl);
        destination.add(hlbl);

        destination.setBounds(505,360,395,250);
        right.setBounds(505,360,395,250);
        destination.setOpaque(false);
        destination.setBorder(new EmptyBorder(0,100,0,0));
        right.setBorder(new EmptyBorder(0,100,0,0));

        /**origin*/
        origin.setLayout(null);

        //city
        JPanel topnl = new JPanel();
        JLabel tolbl = new JLabel("From:");
        tolbl.setFont(font.deriveFont(Font.BOLD));
        tolbl.setBounds(43,20,x,y);
        tolbl.setForeground(Color.black);
        JTextPane totxt = new JTextPane();
        totxt.setBounds(40,50,x,y);
        topnl.setBounds(40,50,x,y);
        topnl.setBackground(new Color(255,255,255,150));
        totxt.setOpaque(false);
        totxt.setFont(font.deriveFont(17f));
        totxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(totxt.getText().equals("City"))
                    totxt.setText("");
            }

            public void mouseExited(MouseEvent e){
                if(totxt.getText().equals(""))
                    totxt.setText("City");
            }

        });
        totxt.setText("City");
        origin.add(tolbl);
        origin.add(totxt);
        origin.add(topnl);
        //hour

       /* Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);

        SpinnerDateModel mod2 = new SpinnerDateModel();
        mod2.setValue(calendar2.getTime());

        JSpinner spinner2 = new JSpinner(mod2);

        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(spinner2, "HH:mm:ss");
        DateFormatter formatter2 = (DateFormatter)editor2.getTextField().getFormatter();
        formatter2.setAllowsInvalid(false); // this makes what you want
        formatter2.setOverwriteMode(true);

        spinner2.setEditor(editor2);
        spinner2.setBounds(40,190,x,y);
        origin.add(spinner2);

        */

        JPanel hpnl2 = new JPanel();
        JLabel hlbl2 = new JLabel("Departure hour:");
        hlbl2.setFont(font.deriveFont(Font.BOLD));
        hlbl2.setBounds(43,160,x,y);
        hlbl2.setForeground(Color.black);
        JTextPane htxt2 = new JTextPane();
        htxt2.setBounds(40,190,x,y);
        hpnl2.setBounds(40,190,x,y);
        hpnl2.setBackground(new Color(255,255,255,150));
        htxt2.setOpaque(false);
        htxt2.setFont(font.deriveFont(17f));
        htxt2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(htxt2.getText().equals("hh:mm"))
                    htxt2.setText("");
            }

            public void mouseExited(MouseEvent e){
                if(htxt2.getText().equals(""))
                    htxt2.setText("hh:mm");
            }

        });
        htxt2.setText("hh:mm");
        origin.add(htxt2);
        origin.add(hpnl2);
        origin.add(hlbl2);


        //day

      /*  UtilDateModel model2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
        JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        JPanel destcal2 = new JPanel();
        destcal2.setOpaque(false);
        destcal2.add(datePicker2);
        destcal2.setBounds(43,115,x,y+10);

        origin.setLayout(null);
        origin.add(destcal2);
        */


        JPanel daypnl2 = new JPanel();
        JLabel daylbl2 = new JLabel("Departure day:");
        daylbl2.setFont(font.deriveFont(Font.BOLD));
        daylbl2.setBounds(43,90,x,y);
        daylbl2.setForeground(Color.black);
        JTextPane daytxt2 = new JTextPane();
        daytxt2.setBounds(40,120,x,y);
        daypnl2.setBounds(40,120,x,y);
        daypnl2.setBackground(new Color(255,255,255,150));
        daytxt2.setOpaque(false);
        daytxt2.setFont(font.deriveFont(17f));
        daytxt2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(daytxt2.getText().equals("YYYY-MM-DD"))
                    daytxt2.setText("");
            }

            public void mouseExited(MouseEvent e){
                if(daytxt2.getText().equals(""))
                    daytxt2.setText("YYYY-MM-DD");
            }

        });
        daytxt2.setText("YYYY-MM-DD");
        origin.add(daytxt2);
        origin.add(daypnl2);
        origin.add(daylbl2);





        origin.setBounds(100,360,395,250);
        left.setBounds(100,360,395,250);
        origin.setOpaque(false);
        origin.setBorder(new EmptyBorder(0,100,0,0));
        left.setBorder(new EmptyBorder(0,100,0,0));


        up.setBackground(new Color(180,120,125,140));
        left.setBackground(new Color(180,120,125,140));
        right.setBackground(new Color(180,120,125,140));

        contentPanel.add(others);
        contentPanel.add(origin);
        contentPanel.add(destination);
        contentPanel.add(up);
        contentPanel.add(left);
        contentPanel.add(right);


/**origin - 2*/
        BufferedImage   saveimg =  resize(ImageIO.read(new File("resources/arrows.png")),110,110);
        JButton save = new JButton(new ImageIcon(saveimg));
        save.setContentAreaFilled(false);
        save.setBackground(new Color(180,120,125));
        save.setBorder(null);
        save.setBounds(550,80,100,100);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Transport.addTransport(pricetxt.getText(),
                            captxt.getText(),
                            trmean[0],
                            fromtxt.getText(),
                            totxt.getText(),
                            daytxt2.getText(),
                            daytxt.getText(),htxt2.getText(), htxt.getText(),agency.id,con);
                    seeTransportsWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Wrong input!!");
                }

            }
        });

        others.add(save);


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

    public void chatWindow() throws SQLException, IOException {

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

        final String username = agency.getStringColumn("agencies", "username");
        final String password = agency.getStringColumn("agencies", "password");

        JButton Message = new JButton("Message");
        Message.setFont(new Font("Liberation Sans", Font.BOLD, 12));
        Message.setBackground(new Color(204, 51, 102));
        Message.setForeground(Color.WHITE);
        Message.setBounds(400, 290, 200, 25);
        contentPanel.add(Message);
        Message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ConnectChatWindow(username, password, con);
            }
        });
        BufferedImage   saveimg =  resize(ImageIO.read(new File("resources/message.png")),190,190);
        JButton Tick = new JButton(new ImageIcon(saveimg));
        Tick.setContentAreaFilled(false);
        Tick.setBackground(new Color(180,120,125));
        Tick.setBorder(null);
        Tick.setBounds(400,90,200,200);
        Tick.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ConnectChatWindow(username, password, con);
            }
        });
        contentPanel.add(Tick);
        JPanel trans = new JPanel();
        trans.setBackground(new Color(200,200,200,150));
        trans.setBounds(350,80,300,250);
        contentPanel.add(trans);

        JButton someoneElse = new JButton("Not You?");
        someoneElse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnectChatWindow connectChatWindow = new ConnectChatWindow(con);
            }
        });

        someoneElse.setBackground(new Color(204, 51, 102));
        someoneElse.setForeground(new Color(255, 255, 255));
        someoneElse.setFont(new Font("Liberation Sans", Font.BOLD, 13));
        someoneElse.setBounds(400, 500, 200, 25);
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

    private BufferedImage RoundImage(String addr ,int size){
        BufferedImage foto = null;
        try {
            foto = resize(ImageIO.read(new File(addr)),size,size);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int diameter = Math.min(foto.getWidth(), foto.getHeight());
        BufferedImage mask = new BufferedImage(foto.getWidth(), foto.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
        g2d.dispose();

        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();
        applyQualityRenderingHints(g2d);
        int x = (diameter - foto.getWidth()) / 2;
        int y = (diameter - foto.getHeight()) / 2;
        g2d.drawImage(foto, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();

        return masked;
    }

    private void applyQualityRenderingHints(Graphics2D g2d) {

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    }
}