package User.Traveller;
import Component.BackgroundList;
import Component.BackgroundPanel;
import Essentials.*;
import User.Utility;
import Component.AddElement;
import User.Utility;
import Component.myRowJPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;
import chat.Client.ConnectChatWindow;
import com.mysql.jdbc.Connection;
import User.Guest.GuestGui;

import javax.swing.border.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.LineBorder;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class TravelerGui {

    public static class userButton extends JButton {
        public int id;

        public userButton(int id) {
            this.id = id;
        }
    }

    private BufferedImage backgroundimg = resize(ImageIO.read(new File("resources/transports2.jpg")),1000,700);
    public JFrame frame;
	public Traveler traveler;
	private int index;
	private boolean flag = false;
	private Connection con;
	private String path;
    private int startW = 350;
    private int startH = 150;

    private static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

	public TravelerGui(Connection con, int id) throws SQLException, IOException {
		this.con = con;
		traveler = new Traveler(con, id);
		homePageWindow();
	}

	private ImageIcon resizedImage(int w, int h, ImageIcon image) {

		Image ri = image.getImage();
		Image modified = ri.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(modified);
		return imageIcon;
	}

	private Color transparentColor(Color color, int transparencyGradient) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparencyGradient);
	}

	public void homePageWindow() {

		frame = new JFrame();
		frame.setBounds(startW, startH, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg, 1000, 700);
        contentPanel.setBounds(0,0, 1000, 700);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        try {
            contentPanel.myHeader(traveler.getStringColumn("username"),this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel panelContent = new JPanel();
		panelContent.setLayout(null);
		panelContent.setOpaque(false);
		panelContent.setBounds(1, 12, 987, 658);
		contentPanel.add(panelContent);

		JLabel lblImage = new JLabel();
		lblImage.setBorder(null);
		lblImage.setBounds(48, -40, 940, 698);
		panelContent.add(lblImage);

		ImageIcon map = new ImageIcon(this.getClass().getResource("/map.png"));
		ImageIcon scaled = resizedImage(900, 698, map);
		lblImage.setIcon(scaled);

		JLabel lblWhereYouWant = new JLabel("I want to travel to:");
		lblWhereYouWant.setFont(new Font("Liberation Sans Narrow", Font.BOLD, 20));
		lblWhereYouWant.setBounds(224, 564, 189, 38);
		panelContent.add(lblWhereYouWant);

		JLabel lblchooseACountry = new JLabel("(Choose a country)");
		lblchooseACountry.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		lblchooseACountry.setBounds(224, 593, 155, 15);
		panelContent.add(lblchooseACountry);

		JTextPane textPane = new JTextPane();
		textPane.setForeground(SystemColor.controlShadow);
		textPane.setFont(new Font("Liberation Sans", Font.PLAIN, 22));
		textPane.setBorder(new LineBorder(SystemColor.inactiveCaptionBorder, 2, true));
		textPane.setBounds(384, 567, 264, 38);
		panelContent.add(textPane);

		JButton btnNewButton_9 = new JButton("Submit");
		btnNewButton_9.setForeground(SystemColor.text);
		btnNewButton_9.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		btnNewButton_9.setBackground(SystemColor.inactiveCaption);
		btnNewButton_9.setBounds(672, 567, 107, 38);
		panelContent.add(btnNewButton_9);

	}

    public void settingsWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(startW, startH, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg, 1000, 700);
        contentPanel.setBounds(0,0, 1000, 700);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        try {
            contentPanel.myHeader(traveler.getStringColumn("username"),this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTextPane []change = new JTextPane[5];

        ImageIcon set = new ImageIcon(this.getClass().getResource("/setting.png"));
        set = resizedImage(20, 20, set);

        JLabel label = new JLabel();
        label.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        label.setBounds(45, 83, 150, 150);
        try {
            ImageIcon image = traveler.getProfilePicture();
            image = resizedImage(label.getWidth(), label.getHeight(), image);
            label.setIcon(image);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        contentPanel.add(label);

        JButton btnUploadPic = new JButton("Upload profile pic");
        btnUploadPic.setForeground(new Color(255, 255, 255));
        btnUploadPic.setBackground(Color.DARK_GRAY);
        btnUploadPic.setFont(new Font("Liberation Sans", Font.BOLD, 12));
        btnUploadPic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                String path = f.getAbsolutePath();
                try {
                    traveler.uploadPic(path);
                } catch (FileNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                try {
                    ImageIcon image = resizedImage(label.getHeight(), label.getWidth(), traveler.getProfilePicture());
                    label.setIcon(image);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnUploadPic.setBounds(45, 265, 150, 35);
        contentPanel.add(btnUploadPic);

        JLabel lblNewLabel = new JLabel("Userame");
        lblNewLabel.setFont(new Font("Liberation Sans", Font.BOLD, 16));
        lblNewLabel.setBounds(322, 95, 393, 15);
        contentPanel.add(lblNewLabel);

        JLabel lblMyname = new JLabel(traveler.getStringColumn("username"));
        lblMyname.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        lblMyname.setBounds(322, 130, 170, 15);
        contentPanel.add(lblMyname);

        JLabel lblNewLabel_1 = new JLabel("Password");
        lblNewLabel_1.setFont(new Font("Liberation Sans", Font.BOLD, 16));
        lblNewLabel_1.setBounds(322, 195, 170, 15);
        contentPanel.add(lblNewLabel_1);

        JLabel lblMysurname = new JLabel("********");
        lblMysurname.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        lblMysurname.setBounds(322, 230, 170, 15);
        contentPanel.add(lblMysurname);

        JLabel lblUsername = new JLabel("Name");
        lblUsername.setFont(new Font("Liberation Sans", Font.BOLD, 16));
        lblUsername.setBounds(322, 295, 170, 15);
        contentPanel.add(lblUsername);

        JLabel lblNewLabel_2 = new JLabel(traveler.getStringColumn("name"));
        lblNewLabel_2.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        lblNewLabel_2.setBounds(322, 330, 170, 15);
        contentPanel.add(lblNewLabel_2);

        JLabel lblPassword = new JLabel("Surname");
        lblPassword.setFont(new Font("Liberation Sans", Font.BOLD, 16));
        lblPassword.setBounds(322, 395, 170, 15);
        contentPanel.add(lblPassword);

        JLabel label_1 = new JLabel(traveler.getStringColumn("surname"));
        label_1.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        label_1.setBounds(322, 430, 170, 15);
        contentPanel.add(label_1);

        JLabel lblNewLabel_3 = new JLabel("Default departure city");
        lblNewLabel_3.setFont(new Font("Liberation Sans", Font.BOLD, 16));
        lblNewLabel_3.setBounds(322, 495, 170, 15);
        contentPanel.add(lblNewLabel_3);

        JLabel lblMycity = new JLabel(traveler.getStringColumn("departureCity"));
        lblMycity.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        lblMycity.setBounds(322, 530, 170, 15);
        contentPanel.add(lblMycity);

        JButton []btn = new JButton[5];
        int []isPressed = new int[5];
        for(int k = 0, j = 120; k < 5; k++, j = j + 100) {
            final int i = k;
            btn[i]= new JButton();
            btn[i].setBounds(800, j, 60, 25);
            btn[i].setIcon(set);
            btn[i].setContentAreaFilled(false);
            btn[i].setBorder(null);
            btn[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if(isPressed[i]%2 == 0) {
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
        btn[0].setName("username");
        btn[1].setName("password");
        btn[2].setName("name");
        btn[3].setName("surname");
        btn[4].setName("departureCity");

        JPasswordField textField = new JPasswordField();
        textField.setBounds(231, 617, 188, 25);
        contentPanel.add(textField);
        textField.setColumns(10);

        JLabel lblIntroduceCurrentPasssword = new JLabel("Current passsword");
        lblIntroduceCurrentPasssword.setFont(new Font("Liberation Sans", Font.BOLD, 15));
        lblIntroduceCurrentPasssword.setBounds(233, 584, 150, 15);
        contentPanel.add(lblIntroduceCurrentPasssword);

        JButton btnNewButton = new JButton("Save Changes");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flag = false;

                try {
                    if(traveler.isCorrectPassword(Utility.hashPassword(textField.getText())))
                        for(int j = 0; j < 5; j++)
                            if(isPressed[j]%2 != 0)
                                try {
                                    traveler.setStringColumn(btn[j].getName(), change[j].getText());
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
        btnNewButton.setBounds(577, 607, 159, 35);
        contentPanel.add(btnNewButton);

        for(int i = 0; i < 5; i++) {
            change[i]= new JTextPane();
            change[i].setBounds(322, 125+i*100, 170, 20);
            change[i].setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            change[i].setVisible(false);
            contentPanel.add(change[i]);
        }

        JPanel label_2 = new JPanel();
        Color c = new Color(255, 255, 255);
        c = transparentColor(c, 150);
        label_2.setBackground(c);
        label_2.setBounds(281, 83, 619, 475);
        contentPanel.add(label_2);
	}

    public void profileWindow() {

        frame = new JFrame();
        frame.setBounds(startW, startH, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg, 1000, 700);
        contentPanel.setBounds(0,0, 1000, 700);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        try {
            contentPanel.myHeader(traveler.getStringColumn("username"),this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ImageIcon pic = traveler.getProfilePicture();
            JLabel lblProfilePic = new JLabel();
            lblProfilePic.setBounds(421, 110, 139, 139);
            lblProfilePic.setBorder(new LineBorder(new Color(0, 0, 0), 2));
            ImageIcon scaled = resizedImage(lblProfilePic.getWidth(), lblProfilePic.getHeight(), pic);
            lblProfilePic.setIcon(scaled);
            contentPanel.add(lblProfilePic);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        try {
            String s = traveler.getStringColumn("name");
            s = s + " ";
            s = s + traveler.getStringColumn("surname");
            JLabel lblNameSurname = new JLabel(s);
            lblNameSurname.setFont(new Font("Dialog", Font.BOLD, 18));
            lblNameSurname.setHorizontalAlignment(SwingConstants.CENTER);
            lblNameSurname.setBounds(337, 273, 303, 15);
            contentPanel.add(lblNameSurname);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBorder(new LineBorder(new Color(169, 169, 169)));
            panel.setBackground(Color.DARK_GRAY);
            panel.setBounds(224, 321, 170, 86);
            contentPanel.add(panel);

            JButton btnNewButton_11 = new JButton();
            btnNewButton_11.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                }
            });
            btnNewButton_11.setBounds(41, 0, 89, 60);
            ImageIcon ticket = new ImageIcon(this.getClass().getResource("/ticket.png"));
            ticket = resizedImage(40, 30, ticket);
            btnNewButton_11.setIcon(ticket);
            btnNewButton_11.setContentAreaFilled(false);
            btnNewButton_11.setBorder(null);
            panel.add(btnNewButton_11);

            JLabel lblMyTickets = new JLabel("My tickets");
            lblMyTickets.setForeground(new Color(255, 255, 255));
            lblMyTickets.setHorizontalAlignment(SwingConstants.CENTER);
            lblMyTickets.setHorizontalTextPosition(SwingConstants.CENTER);
            lblMyTickets.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
            lblMyTickets.setBounds(0, 51, 170, 35);
            panel.add(lblMyTickets);

            JPanel panel_1 = new JPanel();
            panel_1.setLayout(null);
            panel_1.setBorder(new LineBorder(new Color(169, 169, 169)));
            panel_1.setBackground(Color.DARK_GRAY);
            panel_1.setBounds(419, 321, 170, 86);
            contentPanel.add(panel_1);

            JButton btnNewButton_10 = new JButton();
            btnNewButton_10.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                }
            });
            btnNewButton_10.setBounds(57, -12, 66, 86);
            ImageIcon message = new ImageIcon(this.getClass().getResource("/message.png"));
            message = resizedImage(50, 40, message);
            btnNewButton_10.setIcon(message);
            btnNewButton_10.setContentAreaFilled(false);
            btnNewButton_10.setBorder(null);
            panel_1.add(btnNewButton_10);

            JLabel lblMyMessages = new JLabel("My messages");
            lblMyMessages.setForeground(Color.WHITE);
            lblMyMessages.setHorizontalAlignment(SwingConstants.CENTER);
            lblMyMessages.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
            lblMyMessages.setBounds(0, 49, 170, 37);
            panel_1.add(lblMyMessages);

            JPanel panel_2 = new JPanel();
            panel_2.setLayout(null);
            panel_2.setBorder(new LineBorder(new Color(169, 169, 169)));
            panel_2.setBackground(Color.DARK_GRAY);
            panel_2.setBounds(614, 321, 170, 86);
            contentPanel.add(panel_2);

            JButton btnNewButton_12 = new JButton();
            btnNewButton_12.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    try {
                        settingsWindow();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            btnNewButton_12.setBounds(44, 0, 90, 63);
            ImageIcon settings = new ImageIcon(this.getClass().getResource("/settings.png"));
            settings = resizedImage(40, 30, settings);
            btnNewButton_12.setIcon(settings);
            btnNewButton_12.setContentAreaFilled(false);
            btnNewButton_12.setBorder(null);
            panel_2.add(btnNewButton_12);

            JLabel lblMySettings = new JLabel("My settings");
            lblMySettings.setForeground(Color.WHITE);
            lblMySettings.setHorizontalAlignment(SwingConstants.CENTER);
            lblMySettings.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
            lblMySettings.setBounds(0, 51, 170, 35);
            panel_2.add(lblMySettings);

            JEditorPane editorPane = new JEditorPane();
            editorPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            editorPane.setForeground(SystemColor.inactiveCaption);
            editorPane.setBounds(79, 516, 465, 122);
            editorPane.setText("Your review..");
            contentPanel.add(editorPane);

            JTextPane txtpnTitle = new JTextPane();
            txtpnTitle.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            txtpnTitle.setText("Title");
            txtpnTitle.setForeground(SystemColor.inactiveCaption);
            txtpnTitle.setBounds(79, 471, 286, 37);
            contentPanel.add(txtpnTitle);

            JButton btnNewButton_13 = new JButton();
            btnNewButton_13.setBackground(new Color(0, 0, 102));
            btnNewButton_13.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.showOpenDialog(null);
                    File f = chooser.getSelectedFile();
                    path = f.getAbsolutePath();
                }
            });
            btnNewButton_13.setBounds(390, 470, 52, 37);
            ImageIcon photo = new ImageIcon(this.getClass().getResource("/photo.png"));
            photo = resizedImage(30, 30, photo);
            btnNewButton_13.setIcon(photo);
            contentPanel.add(btnNewButton_13);

            JButton btnNewButton_14 = new JButton("Post");
            btnNewButton_14.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Review r = new Review(traveler.con);
                    try {
                        r.newReview(traveler.id, path, txtpnTitle.getText(), editorPane.getText());
                    } catch (FileNotFoundException | SQLException e1) {
                        JOptionPane.showMessageDialog(null, "Please upload a pic!");
                        //e1.printStackTrace();
                    }
                }
            });
            btnNewButton_14.setBackground(new Color(0, 0, 102));
            btnNewButton_14.setForeground(SystemColor.text);
            btnNewButton_14.setFont(new Font("Liberation Sans", Font.BOLD, 15));
            btnNewButton_14.setBounds(454, 471, 90, 37);
            contentPanel.add(btnNewButton_14);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel lblCover = new JLabel("");
        lblCover.setBounds(0, 24, 1000, 700);
        contentPanel.add(lblCover);

        ImageIcon cover = new ImageIcon(this.getClass().getResource("/fade.png"));
        cover = resizedImage(lblCover.getWidth(), lblCover.getHeight(), cover);
        lblCover.setIcon(cover);

    }

    public void ticketsWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg, 1000, 700);
        contentPanel.setBounds(0,0, 1000, 700);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(traveler.getStringColumn("username"),this);


        AddElement tickets = new AddElement(1,0,1,1,1000,450,35,50, 110);
        tickets.setOpaque(false);

        Statement mystate = null;
        try {
            mystate = con.createStatement();

            String query = "SELECT * FROM tickets where travelers_id = "+ traveler.id;
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

	public void feedWindow() throws SQLException, IOException {
		frame = new JFrame();
		frame.setBounds(startW, startH, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);

        BackgroundPanel contentPanel= new BackgroundPanel(backgroundimg, 1000, 700);
        contentPanel.setBounds(0,0, 1000, 700);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(traveler.getStringColumn("username"),this);

        AddElement review = null;
        try {
            review = new AddElement(0,1,1,1,1000,637,0,0, 25);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        review.setOpaque(false);
        try {
            Review r = new Review(traveler.con);
            r.getReviews('>', -1);

            while(r.result.next()) {
                ReviewGui rg = new ReviewGui(frame);
                rg.id = r.getId();
                rg.travelerPic = r.getProfilePicTraveler();
                rg.namesurname = r.getNameSurnameTraveler();
                rg.title = r.getTitle();
                rg.content = r.getContent();
                rg.pic = r.getPic();
                rg.buildReviewPanel();
                review.addNew(rg);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        contentPanel.add(review);
	}

    public void seeTransportsWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        BackgroundPanel contentPanel = new BackgroundPanel(backgroundimg, 1000, 700);
        contentPanel.setBounds(0,0, 1000, 700);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(traveler.getStringColumn("username"),this);

        AddElement transports = null;
        try {
            transports = new AddElement(0,1,1,1,1000,460,90,90, 110);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        transports.setOpaque(false);
        Statement mystate = null;
        try {
            mystate = con.createStatement();

            String query = "SELECT * FROM transport";
            ResultSet myRS = mystate.executeQuery(query);
            while (myRS.next()) {
                TransportTravelerGui transportGui = new TransportTravelerGui(con, myRS.getInt("id"), traveler.id);
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

	private void chatWindow() throws SQLException {

        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        BackgroundPanel contentPanel = new BackgroundPanel(backgroundimg, 1000, 700);
        contentPanel.setBounds(0,0, 1000, 700);
        frame.getContentPane().add(contentPanel);
        contentPanel.setLayout(null);

        contentPanel.myHeader(traveler.getStringColumn("username"),this);
		final String username = traveler.getStringColumn("travelers", "username");
		final String password = traveler.getStringColumn("travelers", "password");

		JButton Message = new JButton("Message");
		Message.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		Message.setBackground(SystemColor.desktop);
		Message.setForeground(Color.WHITE);
		Message.setBounds(149, 40, 138, 25);
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
		someoneElse.setBounds(149, 122, 138, 25);
		contentPanel.add(someoneElse);
	}
	
}