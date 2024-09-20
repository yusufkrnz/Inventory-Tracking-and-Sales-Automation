package dao;

import core.Database;
import entity.Basket;
import entity.Cart;
import entity.Customer;
import entity.Message;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MessageDao {

    private Connection connection;
    private ProductDao productDao;
    private CustomerDao customerDao;


    public MessageDao() {

        this.connection = Database.getInstance();
        this.productDao = new ProductDao();
        this.customerDao = new CustomerDao();
    }



    public boolean save(Message message) throws SQLException {
        String query = "INSERT INTO analyzes " +
                "(" +
                "id" +
                "userName" +
                "name" +
                "code" +
                "title" +
                "subject" +
                ")" +
                " VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, message.getId());
            pr.setString(2, message.getUserName());
            pr.setString(3, message.getName());
            pr.setString(4, message.getCode());
            pr.setString(5, message.getTitle());
            pr.setString(6, message.getSubject());
            return pr.executeUpdate() != -1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public Message match(ResultSet rs) throws SQLException {
        Message message = new Message();

        /**

         ResultSet Nesnesinden Verileri Çekme:

         cart.setId(rs.getInt("id"));
         ResultSet nesnesinden
         id sütunundaki değeri alır
         ve Cart nesnesinin
         id özelliğine atar.

         */

        message.setId(rs.getInt("id"));
        message.setUserName(rs.getString("userName"));
        message.setName(rs.getString("name"));
        message.setCode(rs.getString("code"));
        message.setTitle(rs.getString("title"));
        message.setSubject(rs.getString("subject"));



        return message;
    }
    public boolean send() throws SQLException {
        return send();
    }

    public ArrayList<Message> findAll() {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            //sorgumuzu oluşturduk
            //ve her userları dön ve
            //user  array listesine at
            //ve return user ları dön .
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM analyzes");
            while (rs.next()) {
                messages.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return messages;
    }
    public ArrayList<Message> findAllCombined() {
        ArrayList<Message> combinedMessages = new ArrayList<>();
        try {
            // 'analyzes' tablosundaki verileri al
            ResultSet rsMessages = this.connection.createStatement().executeQuery("SELECT * FROM analyzes");
            while (rsMessages.next()) {
                Message message = new Message();
                // analyzes tablosundan gerekli verileri al

                message.setSalesTime(rsMessages.getTimestamp("sales_time"));
                message.setId(rsMessages.getInt("id"));  // id sütunu
                message.setUserName(rsMessages.getString("user_name"));  // kullanıcı ismi sütunu
                message.setName(rsMessages.getString("name"));  // müşteri ismi sütunu
                message.setCode(rsMessages.getString("category"));  // kategori sütunu
                message.setTitle(rsMessages.getString("product_name"));  // ürün ismi sütunu
                message.setSubject(rsMessages.getString("product_price"));  // ürün fiyatı sütunu

                combinedMessages.add(message);  // Listeye ekle
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return combinedMessages;
    }

}
