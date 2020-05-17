package User.Traveller;
import Component.BackgroundPanel;
import Essentials.Review;
import User.Utility;
import Component.myRowJPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.*;
import chat.Client.ConnectChatWindow;
import com.mysql.jdbc.Connection;
import User.Guest.GuestGui;

import javax.swing.border.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.security.NoSuchAlgorithmException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class TravelerGui {

    public class userButton extends JButton {
        public int id;

        public userButton(int id) {
            this.id = id;
        }
    }

	private JFrame frame;
	private Traveler traveler;
	private int index;
	private boolean flag = false;
	private Connection con;
	private String path;


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

	private void myHeader() {

		index = 0;
		Color color = new Color(0, 0, 0);
		color = transparentColor(color, 200);

		JPanel myHeader = new JPanel();
		myHeader.setBounds(0, 0, 1000, 261);
		myHeader.setLayout(null);
		myHeader.setOpaque(false);
		frame.getContentPane().add(myHeader);

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
			String s = traveler.getStringColumn("username");
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
				frame.setVisible(false);
				homePageWindow();
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
				frame.setVisible(false);
				profileWindow();
			}
		});

		JButton btnNewButton_3 = new JButton("Search users");
		btnNewButton_3.setContentAreaFilled(false);
		btnNewButton_3.setBorderPainted(false);;
		btnNewButton_3.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		btnNewButton_3.setForeground(new Color(255, 250, 250));
		btnNewButton_3.setBounds(2, 54, 137, 27);
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
				frame.setVisible(false);
				try {
					feedWindow();
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
				frame.setVisible(false);
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
				frame.setVisible(false);
				try {
					settingsWindow();
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
				frame.setVisible(false);
				try {
					GuestGui guest = new GuestGui(traveler.con);
				} catch (SQLException e) {
					e.printStackTrace();
				}
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

	private void homePageWindow() {

		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		myHeader();

		JPanel panelContent = new JPanel();
		panelContent.setLayout(null);
		panelContent.setBounds(1, 12, 987, 658);
		frame.getContentPane().add(panelContent);

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

	private void settingsWindow() throws SQLException {

		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		myHeader();

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
		frame.getContentPane().add(label);

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
		frame.getContentPane().add(btnUploadPic);

		JLabel lblNewLabel = new JLabel("Userame");
		lblNewLabel.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		lblNewLabel.setBounds(322, 95, 393, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblMyname = new JLabel(traveler.getStringColumn("username"));
		lblMyname.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
		lblMyname.setBounds(322, 130, 170, 15);
		frame.getContentPane().add(lblMyname);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		lblNewLabel_1.setBounds(322, 195, 170, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblMysurname = new JLabel("********");
		lblMysurname.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
		lblMysurname.setBounds(322, 230, 170, 15);
		frame.getContentPane().add(lblMysurname);

		JLabel lblUsername = new JLabel("Name");
		lblUsername.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		lblUsername.setBounds(322, 295, 170, 15);
		frame.getContentPane().add(lblUsername);

		JLabel lblNewLabel_2 = new JLabel(traveler.getStringColumn("name"));
		lblNewLabel_2.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(322, 330, 170, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblPassword = new JLabel("Surname");
		lblPassword.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		lblPassword.setBounds(322, 395, 170, 15);
		frame.getContentPane().add(lblPassword);

		JLabel label_1 = new JLabel(traveler.getStringColumn("surname"));
		label_1.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
		label_1.setBounds(322, 430, 170, 15);
		frame.getContentPane().add(label_1);

		JLabel lblNewLabel_3 = new JLabel("Default departure city");
		lblNewLabel_3.setFont(new Font("Liberation Sans", Font.BOLD, 16));
		lblNewLabel_3.setBounds(322, 495, 170, 15);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblMycity = new JLabel(traveler.getStringColumn("departureCity"));
		lblMycity.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
		lblMycity.setBounds(322, 530, 170, 15);
		frame.getContentPane().add(lblMycity);

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
			frame.getContentPane().add(btn[i]);
		}
		btn[0].setName("username");
		btn[1].setName("password");
		btn[2].setName("name");
		btn[3].setName("surname");
		btn[4].setName("departureCity");

		JPasswordField textField = new JPasswordField();
		textField.setBounds(231, 617, 188, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblIntroduceCurrentPasssword = new JLabel("Current passsword");
		lblIntroduceCurrentPasssword.setFont(new Font("Liberation Sans", Font.BOLD, 15));
		lblIntroduceCurrentPasssword.setBounds(233, 584, 150, 15);
		frame.getContentPane().add(lblIntroduceCurrentPasssword);

		JButton btnNewButton = new JButton("Save Changes");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = false;

				for(int j = 0; j < 5; j++)
					if(isPressed[j]%2 != 0)
						try {
							traveler.setStringColumn(btn[j].getName(), Utility.hashPassword(change[j].getText()));
						} catch (SQLException e1) {
							flag = true;
							JOptionPane.showMessageDialog(null, "Wrong input!");
							frame.setVisible(false);
							try {
								settingsWindow();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							e1.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				if(!flag) {
					JOptionPane.showMessageDialog(null, "Changes performed!");
					try {
						settingsWindow();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBackground(SystemColor.desktop);
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Liberation Sans", Font.BOLD, 15));
		btnNewButton.setBounds(577, 607, 159, 35);
		frame.getContentPane().add(btnNewButton);

		for(int i = 0; i < 5; i++) {
			change[i]= new JTextPane();
			change[i].setBounds(322, 125+i*100, 170, 20);
			change[i].setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			change[i].setVisible(false);
			frame.getContentPane().add(change[i]);
		}

		JPanel label_2 = new JPanel();
		Color c = new Color(255, 255, 255);
		c = transparentColor(c, 150);
		label_2.setBackground(c);
		label_2.setBounds(281, 83, 619, 475);
		frame.getContentPane().add(label_2);

		JLabel background = new JLabel();
		background.setBounds(0, 0, 1000, 706);
		background.setBorder(null);
		frame.getContentPane().add(background);
		ImageIcon scaled = new ImageIcon(this.getClass().getResource("/transports2.jpg"));
		scaled = resizedImage(background.getWidth(), background.getHeight(), scaled);
		background.setIcon(scaled);

	}

    private void profileWindow() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(null);

        myHeader();

        try {
            ImageIcon pic = traveler.getProfilePicture();
            JLabel lblProfilePic = new JLabel();
            lblProfilePic.setBounds(421, 110, 139, 139);
            lblProfilePic.setBorder(new LineBorder(new Color(0, 0, 0), 2));
            ImageIcon scaled = resizedImage(lblProfilePic.getWidth(), lblProfilePic.getHeight(), pic);
            lblProfilePic.setIcon(scaled);
            frame.getContentPane().add(lblProfilePic);
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
            frame.getContentPane().add(lblNameSurname);

            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBorder(new LineBorder(new Color(169, 169, 169)));
            panel.setBackground(Color.DARK_GRAY);
            panel.setBounds(224, 321, 170, 86);
            frame.getContentPane().add(panel);

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
            frame.getContentPane().add(panel_1);

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
            frame.getContentPane().add(panel_2);

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
            frame.getContentPane().add(editorPane);

            JTextPane txtpnTitle = new JTextPane();
            txtpnTitle.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            txtpnTitle.setText("Title");
            txtpnTitle.setForeground(SystemColor.inactiveCaption);
            txtpnTitle.setBounds(79, 471, 286, 37);
            frame.getContentPane().add(txtpnTitle);

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
            frame.getContentPane().add(btnNewButton_13);

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
            frame.getContentPane().add(btnNewButton_14);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel lblCover = new JLabel("");
        lblCover.setBounds(0, 24, 1000, 700);
        frame.getContentPane().add(lblCover);

        ImageIcon cover = new ImageIcon(this.getClass().getResource("/fade.png"));
        cover = resizedImage(lblCover.getWidth(), lblCover.getHeight(), cover);
        lblCover.setIcon(cover);

        JLabel background = new JLabel();
        background.setBounds(0, 0, 1000, 706);
        background.setBorder(null);
        frame.getContentPane().add(background);
        ImageIcon scaled = new ImageIcon(this.getClass().getResource("/transports2.jpg"));
        scaled = resizedImage(background.getWidth(), background.getHeight(), scaled);
        background.setIcon(scaled);

    }

	private void feedWindow() throws SQLException, IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);

		myHeader();

		Color c = new Color(255, 255, 255);
		c = transparentColor(c, 190);

		Review r = new Review(traveler.con);
		r.getReviews('>', -1);

		DefaultListModel<JPanel> listModel = new DefaultListModel<>();
		JList<JPanel> jPanelJList = new JList<>(listModel);

		int i = 60;

		while(r.result.next()) {
			JPanel mainPanel = new JPanel();
			mainPanel.setBounds(100, i, 750, 270);
			mainPanel.setOpaque(false);
			mainPanel.setLayout(null);
			frame.getContentPane().add(mainPanel);

			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(new Color(255, 255, 255));
			panel.setBounds(30, 0, 300, 40);
			panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			mainPanel.add(panel);

			userButton btnNewButton_8 = new userButton(r.getId());
			btnNewButton_8.setIcon(resizedImage(25, 25, r.getProfilePicTraveler()));
			btnNewButton_8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.setVisible(false);
					/*try {
						travelerProfile(btnNewButton_8.id);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
			});
			btnNewButton_8.setBounds(12, 7, 25, 25);
			panel.add(btnNewButton_8);

			JLabel lblNewLabel_5 = new JLabel();
			lblNewLabel_5.setText(r.getNameSurnameTraveler());
			lblNewLabel_5.setFont(new Font("Liberation Sans", Font.PLAIN, 17));
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_5.setBounds(42, 10, 169, 15);
			panel.add(lblNewLabel_5);

			JPanel p = new JPanel();
			p.setBackground(c);
			p.setLayout(null);
			p.setBounds(0, 20, 750, 270);
			mainPanel.add(p);

			JLabel label = new JLabel();
			label.setBounds(40, 40, 200, 200);
			label.setIcon(resizedImage(170, 170, r.getPic()));
			p.add(label);

			JLabel lblTitle = new JLabel();
			lblTitle.setText(r.getTitle());
			lblTitle.setFont(new Font("Liberation Sans", Font.BOLD, 17));
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitle.setBounds(330, 25, 262, 15);
			p.add(lblTitle);

			JEditorPane content = new JEditorPane();
			content.setText(r.getContent());
			content.setOpaque(false);
			content.setForeground(Color.GRAY);
			content.setEditable(false);
			content.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
			content.setBounds(260, 50, 440, 170);
			p.add(content);

			listModel.addElement(mainPanel);
			i = i + 310;
		}

		r.result.close();

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

		frame.getContentPane().add(scroll);

		JLabel background = new JLabel();
		background.setBounds(0, 0, 1000, 706);
		background.setBorder(null);
		frame.getContentPane().add(background);
		ImageIcon scaled = new ImageIcon(this.getClass().getResource("/transports2.jpg"));
		scaled = resizedImage(background.getWidth(), background.getHeight(), scaled);
		background.setIcon(scaled);

	}

	private void chatWindow() throws SQLException {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		myHeader();
		final String username = traveler.getStringColumn("travelers", "username");
		final String password = traveler.getStringColumn("travelers", "password");

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
	
}