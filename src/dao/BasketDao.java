package dao;

import com.mysql.cj.exceptions.DataReadException;
import core.Database;
import entity.Basket;
import entity.Customer;
import entity.Product;

import java.awt.font.TextHitInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasketDao {
    private Connection connection;
    private ProductDao productDao;

    public BasketDao() {
        this.connection = Database.getInstance();
        this.productDao=new ProductDao();
    }

    public ArrayList<Basket> findAll() {
        ArrayList<Basket> baskets = new ArrayList<>();
        try {
            //sorgumuzu oluşturduk
            //ve her userları dön ve
            //user  array listesine at
            //ve return user ları dön .
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM basket");
            while (rs.next()) {
                baskets.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return baskets;
    }


    public boolean save(Basket basket) throws SQLException {
        String query = "INSERT INTO basket " +
                "(" +
                "product_id" +
                ")" +
                " VALUES (?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, basket.getProductId());
            return pr.executeUpdate() != -1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public Basket match(ResultSet rs)throws SQLException{

        Basket basket=new Basket();
        basket.setId(rs.getInt("id"));
        basket.setProductId(rs.getInt("product_id"));
        basket.setProduct(this.productDao.getById(rs.getInt("product_id")));
        return basket;
    }
    public boolean clear() {
        String query = "DELETE FROM basket ";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            return pr.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }


}
