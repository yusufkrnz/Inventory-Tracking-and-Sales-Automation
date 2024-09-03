package dao;

import core.Database;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {

    private Connection connection;

    public UserDao() {/** HATA DÜZELTİLDİ */
        this.connection = Database.getCustomerManageeInstance(); // Burada doğru metodu çağırıyoruz
    }

    public User findByLogin(String mail, String password) {
        User user = null;
        String query = "SELECT * FROM user WHERE mail = ? AND password = ?";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, mail);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                user = this.match(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    public User match(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setMail(rs.getString("mail"));

        // Role verisini string olarak al ve enum türüne dönüştür
        String roleString = rs.getString("role");

        User.Role role;

        // Enum dönüşümünde hata yakalamak için try-catch bloğu kullan
        try {
            role = User.Role.valueOf(roleString.toUpperCase()); // Dönüştürme işlemi
        } catch (IllegalArgumentException e) {
            // Hatalı enum değeri ile karşılaşıldığında loglama yap
            System.err.println("Geçersiz rol değeri: " + roleString);
            role = User.Role.USER; // Varsayılan rol değerini belirle
        }

        user.setRole(role);

        return user;


    }
}
