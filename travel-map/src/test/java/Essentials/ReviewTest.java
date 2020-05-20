package Essentials;

import com.mysql.jdbc.Connection;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.SQLException;


public class ReviewTest {
    @Test
    public void test() throws SQLException {
        Connection con;
        String url = "jdbc:mysql://localhost:3306/BookingApp";
        con = (Connection) DriverManager.getConnection(url, "denisa", "denisa");
        Review r = new Review(con);

        r.getReviews('>', -1);

        while(r.result.next()) {
            System.out.println(r.getId());
            System.out.println(r.getNameSurnameTraveler());
            System.out.println(r.getTitle());
            System.out.println(r.getContent());
        }
    }

}
