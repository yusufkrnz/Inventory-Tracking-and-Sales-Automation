package dao;

import core.Database;
import entity.Customer;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDao {

    private Connection connection;

    public ProductDao() {
        this.connection = Database.getInstance();
    }


    public boolean save(Product product) throws SQLException {
        String query = "INSERT INTO product ( name, code, price, stock ,uniqcode ) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, product.getName());
            pr.setString(2, product.getCode());
            pr.setInt(3, product.getPrice());
            pr.setInt(4, product.getStock());
            pr.setString(5,product.getUniqcode());
            return pr.executeUpdate() != -1;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean update(Product product) {
        String query = "UPDATE product SET " +
                "name = ?, " +
                "code = ?, " +
                "price = ?, " +
                "stock = ?,  " +
                "uniqcode= ? " +
                "WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, product.getName());
            pr.setString(2, product.getCode());
            pr.setInt(3, product.getPrice());
            pr.setInt(4, product.getStock());
            pr.setString(5,product.getUniqcode());
            pr.setInt(6, product.getId());


            return pr.executeUpdate() > 0;  // Güncellenen satır sayısını kontrol eder


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;  // Hata durumunda false döner
    }

    public Product getById(int id) {
        Product product = null;
        String query = "SELECT * FROM product WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                product = this.match(rs);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return product;
    }

    public ArrayList<Product> findAll() {
        ArrayList<Product> products = new ArrayList<>();
        try {

            //sorgumuzu oluşturduk
            //ve her userları dön ve
            //user  array listesine at
            //ve return user ları dön .

            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM product");
            while (rs.next()) {
                products.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return products;
    }

    public ArrayList<Product> query(String query) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                products.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return products;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM product WHERE id=?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public Product match(ResultSet rs) throws SQLException {
        Product product = new Product();

        // customer_id tablosunda sütun adlarının `customer` tablosundakilerden farklı olabileceğini göz önünde bulundurun
        product.setId(rs.getInt("id")); // ID sütunu her iki tabloda da mevcut olmalı
        product.setName(rs.getString("name"));
        product.setCode(rs.getString("code"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setUniqcode(rs.getString("uniqcode"));

        return product;
    }



    /** ------<---Stok UYARI-->---Sembol işaretleri işlemleri----------*/



}
