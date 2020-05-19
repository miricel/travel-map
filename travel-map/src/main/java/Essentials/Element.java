package Essentials;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.*;

public class Element {

    protected int id;
    protected static Connection con;

    public Element(java.sql.Connection con, int id) {
        this.con = (Connection) con;
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public int getIdUser(String tableType, String username) throws SQLException {
        Statement mystate = con.createStatement();
        String query = "SELECT id FROM "+tableType;
        query = query + " WHERE username='"+username+"'";
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next())
            return myRS.getInt(1);
        else throw new SQLException();
    }

    public void deleteRow(String tableType) throws SQLException {
        String query="DELETE FROM " + tableType + " WHERE id=" + id;
        PreparedStatement mystate = (PreparedStatement) con.prepareStatement(query);
        mystate.execute();
        mystate.close();
    }

    public String getStringColumn(String tableType, String column) throws SQLException {
        Statement mystate = con.createStatement();
        String query = "SELECT "+column+" FROM "+tableType;
        query = query + " WHERE id="+id;
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next())
            return myRS.getString(1);
        else throw new SQLException();

    }

    public int getIntColumn(String tableType, String column) throws SQLException {
        Statement mystate = con.createStatement();
        String query = "SELECT "+column+" FROM "+tableType;
        query = query + " WHERE id="+id;
        System.out.println(query);
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next())
            return myRS.getInt(1);
        else throw new SQLException();
    }

    public void setDoubleColumn(String tableType, String column, Double newData) throws SQLException {
        String query="UPDATE " + tableType + " SET "+ column + " = ? "+"WHERE id=" + id;
        PreparedStatement mystate = (PreparedStatement) con.prepareStatement(query);
        mystate.setDouble(1, newData);
        mystate.execute();
        mystate.close();
    }

    public void setStringColumn(String tableType, String column, String newData) throws SQLException {
        String query="UPDATE " + tableType + " SET "+ column + " = ? "+" WHERE id=" + id;
        PreparedStatement mystate = (PreparedStatement) con.prepareStatement(query);
        mystate.setString(1, newData);
        mystate.execute();
        mystate.close();
    }


    public void setIntColumn(String tableType, String column, int newData) throws SQLException {
        String query="UPDATE " + tableType + " SET "+ column + " = ? "+" WHERE id=" + id;
        PreparedStatement mystate = (PreparedStatement) con.prepareStatement(query);
        mystate.setInt(1, newData);
        mystate.execute();
        mystate.close();
    }

    protected Timestamp getDateTimeColumn(String tableType, String column) throws SQLException {
        Statement mystate = con.createStatement();
        String query = "SELECT "+column+" FROM "+tableType;
        query = query + " WHERE id="+id;
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next())
            return myRS.getTimestamp(column);
        else throw new SQLException();
    }


    protected Timestamp getDateTimeColumn(String tableType, String column,int newid) throws SQLException {
        Statement mystate = con.createStatement();
        String query = "SELECT "+column+" FROM "+tableType;
        query = query + " WHERE id="+newid;
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next())
            return myRS.getTimestamp(column);
        else throw new SQLException();
    }

    protected ResultSet getAllData(String tableType) throws SQLException{
        Statement mystate = con.createStatement();
        String query = "SELECT * FROM "+tableType;
        query = query + " WHERE id="+id;
        ResultSet myRS = mystate.executeQuery(query);
        if(myRS.next())
            return myRS;
        else throw new SQLException();
    }
}
