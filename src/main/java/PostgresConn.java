import pojo.RegisterUser;

import java.sql.*;
import java.util.*;

public class PostgresConn {

    public static void main(String[] args) {

        PostgresConn pgcon = new PostgresConn();
        pgcon.connect();

    }

    public Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String password = "postgres";

            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public long insertUserDetails(RegisterUser registerUser) {

        String query = "insert into \"registerUser\" (\"clientUserIdStr\",\"inviteCode\",\"status\",\"inviteUrl\",\"userId\")"
                + "values(?,?,?,?,?)";

        int affectedRows = 0;
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, registerUser.getClientUserIdStr());
            pstmt.setString(2, registerUser.getInviteCode());
            pstmt.setString(3, registerUser.getStatus());
            pstmt.setString(4, registerUser.getInviteUrl());
            pstmt.setInt(5, registerUser.getUserId());

            affectedRows = pstmt.executeUpdate();
            System.out.println("Im here!");
            if (affectedRows == 0) {
                System.out.println("Nothing Affected");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return affectedRows;
    }

    public int getUserCount() {

        String query = "Select count(*) from \"registerUser\"";
        int count = 0;

        try (Connection connect = connect();
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return count;

    }

    public void displayUsers() {

        String query = "Select * from \"registerUser\"";

        try (Connection connect = connect();
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            utilDisplayUsers(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void utilDisplayUsers(ResultSet rs) throws SQLException {

        while (rs.next()) {
            System.out.println("Client ID : " + rs.getString("clientUserIdStr") + "\t" + "\tInvite Code : "
                    + rs.getString("inviteCode") + "\t" + "\tStatus : " + rs.getString("status"));
        }
    }

    public ArrayList<RegisterUser> getUsers(){

        String query = "Select * from \"registerUser\"";

        ArrayList<RegisterUser> usersList = new ArrayList<>();
        try (Connection connect = connect();
             Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
                while(rs.next()){
                    RegisterUser user = new RegisterUser();
                    user.setClientUserIdStr(rs.getString("clientUserIdStr"));
                    user.setInviteCode(rs.getString("inviteCode"));
                    user.setInviteUrl(rs.getString("inviteUrl"));
                    user.setStatus(rs.getString("status"));
                    user.setUserId(rs.getInt("userId"));

                    usersList.add(user);
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usersList;
    }

}