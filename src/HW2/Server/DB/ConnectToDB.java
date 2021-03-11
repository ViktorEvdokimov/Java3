package HW2.Server.DB;

import java.sql.*;

public class ConnectToDB {
    private static final String url = "jdbc:mysql://localhost:3306/Java3";
    private static final String user = "root";
    private static final String pass = "root";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {
        try {
            con= DriverManager.getConnection(url, user, pass);
            stmt=con.createStatement();
            stmt.executeUpdate("INSERT INTO LoginBase (Login, Password, Nickname) VALUES ('L1', 'P1', 'N1')");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
