package dao;

import com.mysql.cj.util.DnsSrv;
import core.Database;
import entity.Customer;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDao {

    private Connection connection;

    //database connection sağladık
    public CustomerDao() {
        this.connection = Database.getCustomerManageeInstance();
    }


    /**
     * CUSTOMER I ALIYORUZ
     */
    public ArrayList<Customer> findAll() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {

            //sorgumuzu oluşturduk
            //ve her userları dön ve
            //user  array listesine at
            //ve return user ları dön .

            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                customers.add(this.match(rs));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return customers;
    }

    /**
     * CUTOMER VE CUSTOMER_İD İ ALIYORUZZ
     */
    public ArrayList<Customer> findAllCombined() {
        ArrayList<Customer> combinedCustomers = new ArrayList<>();
        try {
            // customer tablosundan verileri al
            ResultSet rsCustomer = this.connection.createStatement().executeQuery("SELECT * FROM customer");
            while (rsCustomer.next()) {
                Customer customer = this.match(rsCustomer);
                System.out.println("Customer from 'customer': " + customer);
                combinedCustomers.add(customer);
            }

            // customer_id tablosundan verileri al
            ResultSet rsCustomerId = this.connection.createStatement().executeQuery("SELECT * FROM customer");
            while (rsCustomerId.next()) {
                Customer customer = this.match(rsCustomerId);
                System.out.println("Customer from 'customer_id': " + customer);
                combinedCustomers.add(customer);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return combinedCustomers;
    }

    public Customer getById(int id) {
        Customer customer = null;
        String query = "SELECT * FROM customer WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                customer = this.match(rs);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return customer;
    }

    public boolean update(Customer customer) {
        String query = "UPDATE customer SET " +
                "name = ?, " +
                "type = ?, " +
                "phone = ?, " +
                "mail = ?, " +
                "address = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, customer.getName());
            pr.setString(2, customer.getType().toString());
            pr.setString(3, customer.getPhone());
            pr.setString(4, customer.getMail());
            pr.setString(5, customer.getAddress());
            pr.setInt(6, customer.getId());

            return pr.executeUpdate() > 0;  // Güncellenen satır sayısını kontrol eder


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;  // Hata durumunda false döner
    }

    public boolean delete(int id) {
        String query = "DELETE FROM customer WHERE id=?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }
                                //search işlemleri
    public ArrayList<Customer> query(String query){
        ArrayList<Customer> customers=new ArrayList<>();
        try {


        ResultSet rs=this.connection.createStatement().executeQuery(query);
        while (rs.next()){
            customers.add(this.match(rs));
        }
     }catch (SQLException exception){
        exception.printStackTrace();
        }
        return customers;
     }

    public boolean save(Customer customer) throws SQLException {
        String query = "INSERT INTO customer (name, type, phone, mail, address) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, customer.getName());
            pr.setString(2, customer.getType().toString());
            pr.setString(3, customer.getPhone());
            pr.setString(4, customer.getMail());
            pr.setString(5, customer.getAddress());
            return pr.executeUpdate() != -1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }


    public Customer match(ResultSet rs) throws SQLException {
        Customer customer = new Customer();

        // customer_id tablosunda sütun adlarının `customer` tablosundakilerden farklı olabileceğini göz önünde bulundurun
        customer.setId(rs.getInt("id")); // ID sütunu her iki tabloda da mevcut olmalı
        customer.setName(rs.getString("name"));
        String typeString = rs.getString("type");
        customer.setPhone(rs.getString("phone"));
        customer.setMail(rs.getString("mail"));
        customer.setAddress(rs.getString("address"));

        if (typeString != null) {
            try {
                customer.setType(Customer.TYPE_.valueOf(typeString));
            } catch (IllegalArgumentException e) {
                System.out.println("Geçersiz TYPE: " + typeString);
                customer.setType(null); // veya uygun bir varsayılan değer
            }
        } else {
            customer.setType(null); // veya uygun bir varsayılan değer
        }

        return customer;
    }

}