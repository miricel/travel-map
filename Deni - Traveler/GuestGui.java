
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import com.mysql.jdbc.Connection;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class GuestGui {

	private JFrame frame;
	private Guest client;

	public GuestGui(Connection con) throws SQLException {		
		client = new Guest(con);
		logInWindow();	
	}
	
	private Image resizedImage(int w, int h, ImageIcon image) {
		Image ri = image.getImage();
		Image modified = ri.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return modified;
	}
	
	private Color transparentColor(Color color, int transparencyGradient) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), transparencyGradient);
	}
	
	private void logInWindow() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		Color color = new Color(225, 225, 225);
		color = transparentColor(color, 100);
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.setName("username");
		usernamePanel.setBounds(83, 61, 304, 28);
		usernamePanel.setBackground(color);
		frame.getContentPane().add(usernamePanel);
		
		JPanel passPanel = new JPanel();
		passPanel.setName("password");
		passPanel.setBounds(83, 115, 304, 28);
		passPanel.setBackground(color);
		frame.getContentPane().add(passPanel);
		
		JTextPane textUsername = new JTextPane();
		textUsername.setBounds(83, 61, 304, 28);
		textUsername.setOpaque(false);
		frame.getContentPane().add(textUsername);
		
		JTextPane textPassword = new JTextPane();
		textPassword.setBounds(83, 115, 304, 28);
		textPassword.setOpaque(false);
		frame.getContentPane().add(textPassword);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(90, 44, 91, 15);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(90, 98, 70, 15);
		frame.getContentPane().add(lblPassword);
	
		JButton btnLogInWindow = new JButton("LOG IN");
		btnLogInWindow.setForeground(Color.WHITE);
		btnLogInWindow.setOpaque(false);
		btnLogInWindow.setContentAreaFilled(false);
		btnLogInWindow.setBorderPainted(false);
		btnLogInWindow.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnLogInWindow.setBounds(80, 12, 91, 25);
		frame.getContentPane().add(btnLogInWindow);
		
		JButton btnRegisterWindow = new JButton("REGISTER");
		btnRegisterWindow.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnRegisterWindow.setForeground(color);
		btnRegisterWindow.setBounds(173, 12, 91, 21);
		btnRegisterWindow.setContentAreaFilled(false);
		btnRegisterWindow.setBorderPainted(false);
		frame.getContentPane().add(btnRegisterWindow);
		btnRegisterWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				registerUserWindow();
			}
		});
		
		JLabel lblImRegisteringAs = new JLabel("I'm logging in as:");
		lblImRegisteringAs.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblImRegisteringAs.setForeground(new Color(255, 255, 255));
		lblImRegisteringAs.setBounds(93, 150, 171, 15);
		frame.getContentPane().add(lblImRegisteringAs);
		
		JRadioButton agency = new JRadioButton("AGENCY");
		agency.setForeground(new Color(255, 255, 255));
		agency.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		agency.setBounds(138, 165, 85, 23);
		agency.setContentAreaFilled(false);
		frame.getContentPane().add(agency);
		agency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agency.setSelected(true);
			}
		});
		
		JRadioButton traveler = new JRadioButton("TRAVELER");
		traveler.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		traveler.setContentAreaFilled(false);
		traveler.setForeground(new Color(255, 255, 255));
		traveler.setBounds(242, 165, 91, 23);
		frame.getContentPane().add(traveler);
		traveler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traveler.setSelected(true);
			}
		});
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(traveler);
		bg.add(agency);
		
		JButton btnLogIn = new JButton("LOG IN");
		btnLogIn.setBorder(null);
		btnLogIn.setForeground(Color.WHITE);
		btnLogIn.setBackground(new Color(204, 51, 102));
		btnLogIn.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnLogIn.setBounds(182, 193, 117, 28);
		frame.getContentPane().add(btnLogIn);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
						
					    if(traveler.isSelected()) {
					    	
					    	    int tempID = client.login("Travelers", textUsername.getText(), textPassword.getText());
		                        frame.setVisible(false);
								TravelerGui travelerGui = new TravelerGui(client.getConnection(), tempID);
							
						}
						else {
								int tempID = client.login("Agency", textUsername.getText(), textPassword.getText());
		                        frame.setVisible(false);
								//AgencyGui agencyGui = new AgencyGui(client.getConnection(), tempID);
						}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Wrong input!");
					frame.setVisible(false);
					logInWindow();
				} 
			}
		});
		
		JLabel lblBackground = new JLabel();
		lblBackground.setBorder(null);
		
		ImageIcon i = new ImageIcon(this.getClass().getResource("/login.jpg"));
		Image scaled = resizedImage(460, 300, i);
		i = new ImageIcon(scaled);
		
		lblBackground.setIcon(i);
		lblBackground.setBounds(0, -48, 453, 336);
		frame.getContentPane().add(lblBackground);
	}
	
	private void registerUserWindow() {	
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		Color color = new Color(225, 225, 225);
		color = transparentColor(color, 100);
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.setName("username");
		usernamePanel.setBounds(83, 61, 304, 28);
		usernamePanel.setBackground(color);
		frame.getContentPane().add(usernamePanel);
		
		JPanel passPanel = new JPanel();
		passPanel.setName("password");
		passPanel.setBounds(83, 115, 304, 28);
		passPanel.setBackground(color);
		frame.getContentPane().add(passPanel);
		
		JTextPane textUsername = new JTextPane();
		textUsername.setBounds(83, 61, 304, 28);
		textUsername.setOpaque(false);
		frame.getContentPane().add(textUsername);
		
		JTextPane textPassword = new JTextPane();
		textPassword.setBounds(83, 115, 304, 28);
		textPassword.setOpaque(false);
		frame.getContentPane().add(textPassword);
	
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(90, 44, 91, 15);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(90, 98, 70, 15);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogInWindow = new JButton("LOG IN");
		btnLogInWindow.setForeground(color);
		btnLogInWindow.setOpaque(false);
		btnLogInWindow.setContentAreaFilled(false);
		btnLogInWindow.setBorderPainted(false);
		btnLogInWindow.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnLogInWindow.setBounds(80, 12, 91, 25);
		frame.getContentPane().add(btnLogInWindow);
		btnLogInWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				logInWindow();
			}
		});
		
		JButton btnRegisterWindow = new JButton("REGISTER");
		btnRegisterWindow.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnRegisterWindow.setForeground(Color.WHITE);
		btnRegisterWindow.setBounds(173, 12, 91, 21);
		btnRegisterWindow.setContentAreaFilled(false);
		btnRegisterWindow.setBorderPainted(false);
		frame.getContentPane().add(btnRegisterWindow);
		
		JLabel lblImRegisteringAs = new JLabel("I'm registering as:");
		lblImRegisteringAs.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblImRegisteringAs.setForeground(new Color(255, 255, 255));
		lblImRegisteringAs.setBounds(93, 150, 171, 15);
		frame.getContentPane().add(lblImRegisteringAs);
		
		JRadioButton agency = new JRadioButton("AGENCY");
		agency.setForeground(new Color(255, 255, 255));
		agency.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		agency.setBounds(138, 165, 85, 23);
		agency.setContentAreaFilled(false);
		frame.getContentPane().add(agency);
		agency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agency.setSelected(true);
			}
		});
		
		JRadioButton traveler = new JRadioButton("TRAVELER");
		traveler.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		traveler.setContentAreaFilled(false);
		traveler.setForeground(new Color(255, 255, 255));
		traveler.setBounds(242, 165, 91, 23);
		frame.getContentPane().add(traveler);
		traveler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traveler.setSelected(true);
			}
		});
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(traveler);
		bg.add(agency);
		
		JButton btnNext = new JButton("NEXT");
		btnNext.setBorder(null);
		btnNext.setBackground(new Color(204, 51, 102));
		btnNext.setForeground(new Color(255, 255, 255));
		btnNext.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnNext.setBounds(182, 193, 117, 28);
		frame.getContentPane().add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						
						int tempID;

						if(traveler.isSelected()) {
							tempID = client.registerUser("Travelers", textUsername.getText() , textPassword.getText());
							frame.setVisible(false);
							registerTravelerWindow(tempID);
						}
						else if(agency.isSelected()) {
							
						}
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Wrong input!");
						frame.setVisible(false);
						registerUserWindow();
						e1.getMessage();
					} 
				}	
		});
		
		JLabel lblBackground = new JLabel();
		lblBackground.setBorder(null);
		
		ImageIcon i = new ImageIcon(this.getClass().getResource("/login.jpg"));
		Image scaled = resizedImage(460, 300, i);
		i = new ImageIcon(scaled);
		
		lblBackground.setIcon(i);
		lblBackground.setBounds(0, -48, 453, 336);
		frame.getContentPane().add(lblBackground);	
	}
	
	private void registerAgencyWindow() {	
	}
	
	private void registerTravelerWindow(int tempID) {	
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		Color color = new Color(225, 225, 225);
		color = transparentColor(color, 100);
		
		JPanel namePanel = new JPanel();
		namePanel.setName("name");
		namePanel.setBounds(83, 61, 304, 28);
		namePanel.setBackground(color);
		frame.getContentPane().add(namePanel);
		
		JPanel surnamePanel = new JPanel();
		surnamePanel.setName("surname");
		surnamePanel.setBounds(83, 115, 304, 28);
		surnamePanel.setBackground(color);
		frame.getContentPane().add(surnamePanel);
		
		JPanel defDepartureCityPanel = new JPanel();
		defDepartureCityPanel.setName("surname");
		defDepartureCityPanel.setBounds(83, 168, 304, 28);
		defDepartureCityPanel.setBackground(color);
		frame.getContentPane().add(defDepartureCityPanel);
		
		JTextPane textName = new JTextPane();
		textName.setBounds(83, 61, 304, 28);
		textName.setOpaque(false);
		frame.getContentPane().add(textName);
		
		JTextPane textSurname = new JTextPane();
		textSurname.setBounds(83, 115, 304, 28);
		textSurname.setOpaque(false);
		frame.getContentPane().add(textSurname);
		
		JTextPane textDefaultDepartureCity = new JTextPane();
		textDefaultDepartureCity.setBounds(83, 168, 304, 28);
		textDefaultDepartureCity.setOpaque(false);
		frame.getContentPane().add(textDefaultDepartureCity);
	
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(90, 44, 91, 15);
		frame.getContentPane().add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblSurname.setForeground(Color.WHITE);
		lblSurname.setBounds(90, 98, 70, 15);
		frame.getContentPane().add(lblSurname);
		
		JButton btnLogInWindow = new JButton("LOG IN");
		btnLogInWindow.setForeground(color);
		btnLogInWindow.setOpaque(false);
		btnLogInWindow.setContentAreaFilled(false);
		btnLogInWindow.setBorderPainted(false);
		btnLogInWindow.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnLogInWindow.setBounds(80, 12, 91, 25);
		frame.getContentPane().add(btnLogInWindow);
		btnLogInWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				logInWindow();
			}
		});
		
		JButton btnRegisterWindow = new JButton("REGISTER");
		btnRegisterWindow.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnRegisterWindow.setForeground(Color.WHITE);
		btnRegisterWindow.setBounds(173, 12, 91, 21);
		btnRegisterWindow.setContentAreaFilled(false);
		btnRegisterWindow.setBorderPainted(false);
		frame.getContentPane().add(btnRegisterWindow);
		btnRegisterWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				registerUserWindow();
			}
		});
		
		JLabel lblDefaultDepartureCity = new JLabel("Default Departure City");
		lblDefaultDepartureCity.setFont(new Font("Liberation Sans", Font.PLAIN, 12));
		lblDefaultDepartureCity.setForeground(new Color(255, 255, 255));
		lblDefaultDepartureCity.setBounds(93, 150, 171, 15);
		frame.getContentPane().add(lblDefaultDepartureCity);
		
		JButton btnRegisterTraveler = new JButton("REGISTER");
		btnRegisterTraveler.setBorder(null);
		btnRegisterTraveler.setBackground(new Color(204, 51, 102));
		btnRegisterTraveler.setForeground(new Color(255, 255, 255));
		btnRegisterTraveler.setFont(new Font("Liberation Sans", Font.BOLD, 12));
		btnRegisterTraveler.setBounds(173, 211, 117, 28);
		frame.getContentPane().add(btnRegisterTraveler);
		btnRegisterTraveler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						client.registerTraveler(tempID, textName.getText(), textSurname.getText(), textDefaultDepartureCity.getText());
						JOptionPane.showMessageDialog(null, "You've been registered!");
						frame.setVisible(false);
						logInWindow();
					} catch (SQLException e1) {
						e1.getMessage();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}		
		});
		
		JLabel lblBackground = new JLabel();
		lblBackground.setBorder(null);
		
		ImageIcon i = new ImageIcon(this.getClass().getResource("/login.jpg"));
		Image scaled = resizedImage(460, 300, i);
		i = new ImageIcon(scaled);
		
		lblBackground.setIcon(i);
		lblBackground.setBounds(0, -48, 453, 336);
		frame.getContentPane().add(lblBackground);
	}
	
}
