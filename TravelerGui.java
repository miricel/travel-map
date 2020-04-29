import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.mysql.jdbc.Connection;
import javax.swing.JScrollPane;

public class TravelerGui {

	private JFrame frame;
	private Traveler traveler;

	public TravelerGui(Connection con, int id) {
		traveler = new Traveler(con, id);
		homePageWindow();
	}
	private Image resizedImage(int w, int h, ImageIcon image) {
		
		Image ri = image.getImage();
		Image modified = ri.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		
		return modified;
	}

	private void homePageWindow() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		
		JLabel p = new JLabel();
		ImageIcon i = new ImageIcon(this.getClass().getResource("/map.png"));
	
		Image scaled = resizedImage(460, 400, i);
		i = new ImageIcon(scaled);

		
		p.setIcon(i);
		p.setBounds(-3, 63, 453, 318);
		frame.getContentPane().add(p);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(220, 20, 60));
		panel.setBounds(0, 0, 450, 27);
		frame.getContentPane().add(panel);
		
		JPopupMenu popupMenu = new JPopupMenu("|||");
		popupMenu.setSize(70, 30);
		popupMenu.setLocation(0, 0);
		JMenuItem review = new JMenuItem("Reviews");
		JMenuItem tickets = new JMenuItem("Tickets");
		popupMenu.add(review);
		addPopup(panel, popupMenu);
		panel.add(popupMenu);
	
		
		JLabel lblWhereYouWant = new JLabel("Where you want to travel?Choose your country!");
		lblWhereYouWant.setFont(new Font("Liberation Sans Narrow", Font.BOLD, 17));
		lblWhereYouWant.setBounds(51, 49, 345, 14);
		frame.getContentPane().add(lblWhereYouWant);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(434, 25, 16, 345);
		frame.getContentPane().add(scrollPane);
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
