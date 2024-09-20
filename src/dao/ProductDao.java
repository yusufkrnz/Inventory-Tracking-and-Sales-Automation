package dao;

import core.Database;
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


    public boolean save(Product product) {
        String query = "INSERT INTO product (name, code, price, category, supplier, stock, uniqcode) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pr = this.connection.prepareStatement(query)) {
            pr.setString(1, product.getName());
            pr.setString(2, product.getCode());
            pr.setInt(3, product.getPrice());
            pr.setString(4, product.getCategory().toString());
            pr.setString(5, product.getSupplier());
            pr.setInt(6, product.getStock());
            pr.setString(7, product.getUniqcode());

            int result = pr.executeUpdate();
            if (result != -1) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace(); // Loglama yapabilir veya özel bir durum fırlatabilirsiniz
        }
        return false; // Başarısız olan durumlar için false döner
    }


    public boolean update(Product product) {
        String query = "UPDATE product SET " +
                "name = ?, " +
                "code = ?, " +
                "price = ?, " +
                "category = ?, " +
                "supplier = ?, " +
                "stock = ?, " +
                "uniqcode = ? " +  // Virgül kaldırıldı
                "WHERE id = ?";   // WHERE koşulu burada
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, product.getName());
            pr.setString(2, product.getCode());
            pr.setInt(3, product.getPrice());
            pr.setString(4, product.getCategory().toString());
            pr.setString(5, product.getSupplier());
            pr.setInt(6, product.getStock());
            pr.setString(7, product.getUniqcode());
            pr.setInt(8, product.getId());

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

        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setCode(rs.getString("code"));
        product.setPrice(rs.getInt("price"));

        // Enum dönüşümünü güvenli bir şekilde yapma
        String categoryString = rs.getString("category");
        try {
            product.setCategory(Product.Type.valueOf(categoryString));
        } catch (IllegalArgumentException e) {
            // Enum'da tanımlı olmayan bir değerle karşılaşıldı
            System.out.println("Unknown category type: " + categoryString);
            product.setCategory(Product.Type.SmartAssistants); // Varsayılan bir değer kullanabilirsiniz
        }

        product.setSupplier(rs.getString("supplier")); // `supplier` enum değilse
        product.setStock(rs.getInt("stock"));
        product.setUniqcode(rs.getString("uniqcode"));

        return product;
    }


    /** ------<---Stok UYARI-->---Sembol işaretleri işlemleri----------*/


}
