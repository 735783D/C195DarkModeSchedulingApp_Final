package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**This class creates and closes the connection to a MySQL Database of the user's choice by changing the variable parameters. */
public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql";
    private static final String ipAddress = "://localhost:3306/";
    private static final String dbName = "client_schedule";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone=SERVER";
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "sqlUser";
    private static Connection conn = null;
    public static Connection getConnection(){
        return conn;
    }

    /** This method begins a connection with the MYSQL Database specified above.
     * @return Connection conn to Database */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);

            conn = DriverManager.getConnection(jdbcURL, username, "Passw0rd!");

            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    /** This method closes the connection with the aforementioned MYSQL Database. */
    public static void closeConnection() {

        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


