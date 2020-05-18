package Main;

import java.awt.EventQueue;
import java.sql.DriverManager;

import User.Guest.GuestGui;
import com.mysql.jdbc.Connection;

public class Main {
	static String url;
	static Connection con;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					url = "jdbc:mysql://localhost:3306/BookingApp";
					con = (Connection) DriverManager.getConnection(url, "denisa", "denisa");
					GuestGui window = new GuestGui(con);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
