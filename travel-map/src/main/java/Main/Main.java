package Main;
import java.awt.EventQueue;
import java.sql.DriverManager;

import User.Guest.GuestGui;
import com.mysql.jdbc.Connection;

public class Main {


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    String url = "jdbc:mysql://localhost:3306/travel-map";
                    Connection con = (Connection) DriverManager.getConnection(url, "root", "TravelMap");
                    GuestGui window = new GuestGui(con);




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        /*DBConnect db = new DBConnect();
        db.Execute("Select * from travelers");
    */}
}
