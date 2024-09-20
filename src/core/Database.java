package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 public class Database {
    private static Connection connection;
    // Singleton getInstance metodu
    public static Connection getInstance() {
        if (connection == null) {
            try {
                // Bağlantı bilgilerini güncelleyin
                String url = "jdbc:mysql://localhost:3306/your_database";
                String user = "your_username";
                String password = "your_password";
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    private static Database customerManageeInstance = null;
    private static Database productInstance = null;

    
    private final String DB_URL_CUSTOMER_MANAGEE = "jdbc:mysql://localhost:3306/customermanagee";
    private final String DB_URL_PRODUCT = "jdbc:mysql://localhost:3306/product";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "rootroot";

    private Database(String url) {
        try {
            this.connection = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Connection getCustomerManageeInstance() {
        if (customerManageeInstance == null) {
            customerManageeInstance = new Database("jdbc:mysql://localhost:3306/customermanagee");
        } else {
            try {
                if (customerManageeInstance.getConnection().isClosed()) {
                    customerManageeInstance = new Database("jdbc:mysql://localhost:3306/customermanagee");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return customerManageeInstance.getConnection();
    }

    public static synchronized Connection getProductDatabaseInstance() {
        if (productInstance == null) {
            productInstance = new Database("jdbc:mysql://localhost:3306/product");
        } else {
            try {
                if (productInstance.getConnection().isClosed()) {
                    productInstance = new Database("jdbc:mysql://localhost:3306/product");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return productInstance.getConnection();
    }

    public  Connection Instance() {
        return null;
    }

    public static Connection getConnection() {
        return connection;
    }

}
