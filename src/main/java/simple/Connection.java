package simple;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    public static void main(String[] args) throws SQLException {
        System.out.println("Before connection");
        java.sql.Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:1050/solar?useSSL=false&serverTimezone=UTC", "root", "06021988abc");
        System.out.println("Connected");
        con.close();
    }
}
