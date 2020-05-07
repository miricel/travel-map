import java.awt.EventQueue;
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;

public class Main {
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					String url = "jdbc:mysql://localhost:3306/BookingApp";
					Connection con = (Connection) DriverManager.getConnection(url, "denisa", "denisa");
					GuestGui window = new GuestGui(con);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
