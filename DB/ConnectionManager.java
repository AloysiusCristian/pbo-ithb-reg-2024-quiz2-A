package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.*;

public class ConnectionManager {

    private static Connection con;
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost/Quiz2_PBO_reg_2024_NIM";
    private static String username = "root";
    private static String password = "";

    private static Connection logOn() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(null, "Error Ocurred when login" + ex);
        }
        return con;
    }

    private static void logOff() {
        try {
            con.close();
            System.out.println("Log off berhasil!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error Ocurred when login" + ex);
        }
    }

    public static Connection connect() {
        if(con == null){
            con = logOn();
        }
        return con;
    }

    public static void disconnect() {
        try {
            logOff();
        } catch (Exception ex) {
            System.out.println("Error occured when connecting to database");
        }
    }
}