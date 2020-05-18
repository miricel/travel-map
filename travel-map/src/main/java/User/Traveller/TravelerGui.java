package User.Traveller;
import User.Utility;
import java.awt.*;
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
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.mysql.jdbc.Connection;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class TravelerGui {

	private JFrame frame;
	private Traveler traveler;
	private int index;
	private boolean flag = false;
	private Connection con;


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
		Color color = new Color(0,0,0);
		color = transparentColor(color, 200);

		JPanel myHeader = new JPanel();
		myHeader.setBounds(0, 0, 450, 215);
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

		JButton btnMenu =new JButton("||||");
		btnMenu.setBorderPainted(false);
		btnMenu.setBounds(-13, -2, 63, 27);
		panelHeader.add(btnMenu);
		btnMenu.setContentAreaFilled(false);
		btnMenu.setForeground(new Color(255, 255, 255));
		btnMenu.setFont(new Font("Jamrul", Font.BOLD, 14));

		try {
			JLabel lblUser = new JLabel();
			lblUser.setText(traveler.getStringColumn("username") + "@traveler");
			lblUser.setForeground(UIManager.getColor("EditorPane.foreground"));
			lblUser.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
			lblUser.setBounds(316, 6, 122, 15);
			panelHeader.add(lblUser);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JTextPane txtpnSearchUser = new JTextPane();
		txtpnSearchUser.setForeground(Color.BLACK);
		txtpnSearchUser.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		txtpnSearchUser.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtpnSearchUser.setBackground(new Color(214, 61, 112));
		txtpnSearchUser.setBounds(116, 5, 106, 17);
		panelHeader.add(txtpnSearchUser);

		JButton lblNewLabel_4= new JButton();
		lblNewLabel_4.setContentAreaFilled(false);
		lblNewLabel_4.setBorder(null);
		ImageIcon search = new ImageIcon(this.getClass().getResource("/setting.png"));
		search = resizedImage(15, 15, search);
		lblNewLabel_4.setIcon(search);
		lblNewLabel_4.setBounds(222, 0, 25, 27);
		panelHeader.add(lblNewLabel_4);


		final JPanel panelMenu = new JPanel();
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
				//profileWindow();
			}
		});

		JButton btnNewButton_3 = new JButton("Search users");
		btnNewButton_3.setContentAreaFilled(false);
		btnNewButton_3.setBorderPainted(false);;
		btnNewButton_3.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		btnNewButton_3.setForeground(new Color(255, 250, 250));
		btnNewButton_3.setBounds(2, 54, 137, 27);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				try {
					chatWindow();
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
				frame.setVisible(false);

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
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		myHeader();
		
		JPanel panelContent = new JPanel();
		panelContent.setLayout(null);
		panelContent.setBounds(1, 12, 449, 399);
		frame.getContentPane().add(panelContent);
		
		JLabel lblImage = new JLabel();
		lblImage.setBorder(null);
		lblImage.setBounds(0, -40, 452, 427);
		panelContent.add(lblImage);	
		
		ImageIcon map = new ImageIcon(this.getClass().getResource("/map.png"));
		ImageIcon scaled = resizedImage(460, 400, map);	
		lblImage.setIcon(scaled);
		
		JLabel lblWhereYouWant = new JLabel("I want to travel to:");
		lblWhereYouWant.setFont(new Font("Liberation Sans Narrow", Font.BOLD, 17));
		lblWhereYouWant.setBounds(38, 292, 132, 38);
		panelContent.add(lblWhereYouWant);		
		
		JLabel lblchooseACountry = new JLabel("(Choose a country)");
		lblchooseACountry.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		lblchooseACountry.setBounds(43, 321, 107, 15);
		panelContent.add(lblchooseACountry);
		
		JTextPane textPane = new JTextPane();
		textPane.setForeground(SystemColor.controlShadow);
		textPane.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
		textPane.setBorder(new LineBorder(SystemColor.inactiveCaptionBorder, 2, true));
		textPane.setBounds(188, 308, 146, 28);
		panelContent.add(textPane);
		
		JButton btnNewButton_9 = new JButton("Submit");
		btnNewButton_9.setForeground(SystemColor.text);
		btnNewButton_9.setFont(new Font("Liberation Sans", Font.PLAIN, 11));
		btnNewButton_9.setBackground(SystemColor.inactiveCaption);
		btnNewButton_9.setBounds(346, 308, 66, 28);
		panelContent.add(btnNewButton_9);
		
	}

	private void settingsWindow() throws SQLException {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		myHeader();
		
		final JTextPane []change = new JTextPane[5];
		
		ImageIcon set = new ImageIcon(this.getClass().getResource("/setting.png"));
		set = resizedImage(12, 12, set);
	
		final JLabel label = new JLabel();
		label.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		label.setBounds(34, 48, 93, 94);
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
		
		JButton btnUploadPic = new JButton("Upload pic");
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
		btnUploadPic.setBounds(34, 150, 93, 25);
		frame.getContentPane().add(btnUploadPic);
		
		JLabel lblNewLabel = new JLabel("Userame");
		lblNewLabel.setFont(new Font("Liberation Sans", Font.BOLD, 13));
		lblNewLabel.setBounds(162, 60, 170, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblMyname = new JLabel(traveler.getStringColumn("username"));
		lblMyname.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblMyname.setBounds(162, 80, 170, 15);
		frame.getContentPane().add(lblMyname);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Liberation Sans", Font.BOLD, 13));
		lblNewLabel_1.setBounds(162, 110, 170, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblMysurname = new JLabel("********");
		lblMysurname.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblMysurname.setBounds(162, 130, 170, 15);
		frame.getContentPane().add(lblMysurname);
		
		JLabel lblUsername = new JLabel("Name");
		lblUsername.setFont(new Font("Liberation Sans", Font.BOLD, 13));
		lblUsername.setBounds(162, 160, 170, 15);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblNewLabel_2 = new JLabel(traveler.getStringColumn("name"));
		lblNewLabel_2.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(162, 180, 170, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblPassword = new JLabel("Surname");
		lblPassword.setFont(new Font("Liberation Sans", Font.BOLD, 13));
		lblPassword.setBounds(162, 210, 170, 15);
		frame.getContentPane().add(lblPassword);
		
		JLabel label_1 = new JLabel(traveler.getStringColumn("surname"));
		label_1.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		label_1.setBounds(162, 230, 170, 15);
		frame.getContentPane().add(label_1);
		
		JLabel lblNewLabel_3 = new JLabel("Default departure city");
		lblNewLabel_3.setFont(new Font("Liberation Sans", Font.BOLD, 13));
		lblNewLabel_3.setBounds(162, 260, 170, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblMycity = new JLabel(traveler.getStringColumn("departureCity"));
		lblMycity.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblMycity.setBounds(162, 280, 170, 15);
		frame.getContentPane().add(lblMycity);
		
		final JTextField textField = new JTextField();
		textField.setBounds(73, 325, 125, 17);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblIntroduceCurrentPasssword = new JLabel("Current passsword");
		lblIntroduceCurrentPasssword.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		lblIntroduceCurrentPasssword.setBounds(80, 307, 118, 15);
		frame.getContentPane().add(lblIntroduceCurrentPasssword);
		
		final JButton []btn = new JButton[5];
		final int []isPressed = new int[5];
		for(int k = 0, j = 75; k < 5; k++, j = j + 50) {
			final int i = k;
			btn[i]= new JButton();
			btn[i].setBounds(358, j, 60, 25);
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
		
		JButton btnNewButton = new JButton("Save Changes");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = false;
				try {
					traveler.isCorrectPassword(Utility.hashPassword(textField.getText()));
					for(int j = 0; j < 5; j++)
						if(isPressed[j]%2 != 0)
							traveler.setStringColumn(btn[j].getName(), change[j].getText());
				} catch (NoSuchAlgorithmException | SQLException e3) {
					flag = true;
					JOptionPane.showMessageDialog(null, "Wrong input!");
					frame.setVisible(false);
					try {
						settingsWindow();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
		btnNewButton.setFont(new Font("Liberation Sans", Font.BOLD, 13));
		btnNewButton.setBounds(254, 317, 138, 25);
		frame.getContentPane().add(btnNewButton);
		
		for(int i = 0; i < 5; i++) {
			change[i]= new JTextPane();
			change[i].setBounds(162, 80+i*50, 170, 20);
			change[i].setVisible(false);
			frame.getContentPane().add(change[i]);
		}
			
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