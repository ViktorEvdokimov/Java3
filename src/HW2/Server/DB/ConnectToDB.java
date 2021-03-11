package HW2.Server.DB;

import java.sql.*;

public class ConnectToDB {
    private static final String url = "jdbc:mysql://localhost:3306/Java3";
    private static final String user = "root";
    private static final String pass = "root";
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public ConnectToDB() {
        try {
            con= DriverManager.getConnection(url, user, pass);
            stmt=con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("SWW on connecting to DB", e);
        }
    }

    public boolean createNewClient (String login, String password, String nickname) throws SQLException {
            con= DriverManager.getConnection(url, user, pass);
            stmt=con.createStatement();
            rs = stmt.executeQuery(String.format("SELECT * FROM java3.Loginbase " +
                    "WHERE Login='%s'", login));
            if (rs.next()) return false;
            rs = stmt.executeQuery(String.format("SELECT * FROM java3.Loginbase " +
                        "WHERE Nickname='%s'", nickname));
            if (rs.next()) return false;
            stmt.executeUpdate(String.format("INSERT INTO java3.Loginbase (Login, Password, Nickname) " +
                    "VALUES ('%s','%s','%s')", login,password,nickname));
            return true;
    }

    public void closeConnection (){
        try {
            con.close();
            stmt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getNicknameByLogin (String login, String password) throws SQLException {

            con= DriverManager.getConnection(url, user, pass);
            stmt=con.createStatement();
            rs = stmt.executeQuery(String.format("SELECT * FROM java3.Loginbase " +
                    "WHERE Login='%s' AND Password='%s'", login, password));
            String nickname;
            if (rs.next()) {
                nickname = rs.getString("Nickname");
            } else nickname=null;
            return nickname;
    }
}
