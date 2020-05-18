package Essentials;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Review {

    private Connection con;
    public ResultSet result;

    public Review(Connection con) {
        this.con = con;
    }

    public void getReviews(char sign, int id) throws SQLException {
        Statement mystate = (Statement) con.createStatement();
        String query = "SELECT * FROM Reviews";
        query = query + " WHERE id"+sign+id;
        ResultSet myRS = mystate.executeQuery(query);
        result = myRS;
    }

    public String getTitle() throws SQLException {
        return result.getString("title");
    }

    public String getContent() throws SQLException {
        return result.getString("content");
    }

    public int getId() throws SQLException {
        return result.getInt("id");
    }

    public String getNameSurnameTraveler() throws SQLException {
        Statement mystate = (Statement) con.createStatement();
        String query = "SELECT name, surname FROM Travelers WHERE id = " + this.getId();
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next()) {
            return myRS.getString("name")+ " " + myRS.getString("surname");
        }
        else {
            throw new SQLException();
        }
    }

    public ImageIcon getProfilePicTraveler() throws SQLException, IOException {
        Statement mystate = (Statement) con.createStatement();
        String query = "SELECT profilePic FROM Travelers";
        query = query + " WHERE  id="+this.getId();
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next()) {
            Blob blob = (Blob) myRS.getBlob("profilePic");
            InputStream is = blob.getBinaryStream();
            BufferedImage bi = ImageIO.read(is);
            Image image = bi;
            ImageIcon ic = new ImageIcon(image);
            return ic;
        }
        throw new SQLException();
    }

    public ImageIcon getPic() throws SQLException, IOException {
        Blob blob = (Blob) result.getBlob("pic");
        InputStream is = blob.getBinaryStream();
        BufferedImage bi = ImageIO.read(is);
        Image image = bi;
        ImageIcon ic = new ImageIcon(image);
        return ic;
    }

    public void newReview(int travelerId, String picPath, String title, String content) throws SQLException, FileNotFoundException {
        InputStream in = new FileInputStream(picPath);
        System.out.println(travelerId +" "+picPath+" "+ title+" "+content);
        String query="INSERT INTO Reviews (pic, title, id, content) VALUES (?, ?, ?, ?)";
        PreparedStatement mystate = (PreparedStatement) con.prepareStatement(query);
        mystate.setBlob(1, in);
        mystate.setString(2, title);
        mystate.setInt(3, travelerId);
        mystate.setString(4, content);
        mystate.execute();
        mystate.close();
    }
}
