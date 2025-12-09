package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    public static Connection getDBConnection() throws SQLException{
        String DB_USER = "product_manager_user";
        String DB_PASSWORD = "123456";
        String JDBC_URL = "jdbc:postgrsql://localhost:5432/product_management_db";
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
}
