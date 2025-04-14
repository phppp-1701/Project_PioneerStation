package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    private ConnectDB() {} // Private constructor to enforce singleton pattern

    public static ConnectDB getInstance() {
        return instance;
    }

    public void connect() throws SQLException {
        if (con == null || con.isClosed()) { // Check if the connection is closed
            String url = "jdbc:sqlserver://localhost:1433;databaseName=pioneer_station";
            String user = "sa";
            String password = "tuan123";
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công!");
        }
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) { // Ensure connection is valid before returning
            getInstance().connect(); // Call connect to establish a connection
        }
        return con;
    }
}
