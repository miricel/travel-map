package ptMiri;

import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.mysql.jdbc.Connection;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.JTextPane;

public class TravelerGui {

	private JFrame frame;

	public TravelerGui(Connection con, int id) throws SQLException, IOException {
		homePageWindow();
	}
	
	private ImageIcon resizedImage(int w, int h, ImageIcon image) {
		
		Image ri = image.getImage();
		Image modified = ri.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(modified);
		return imageIcon;
	}
	
private void homePageWindow() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
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
	
}

