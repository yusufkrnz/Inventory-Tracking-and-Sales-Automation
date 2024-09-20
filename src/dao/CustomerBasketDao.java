package dao;

import core.Database;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class CustomerBasketDao {


    private ProductDao productDao;
    private Connection connection;


    public CustomerBasketDao() {
        //Database nesnesindeki getInstance metodunu kullanarak
        // connection nesnesine bağlantı ataması yaptık
        // (singleton pattern )
        this.connection= Database.getInstance();
       // Java'da nesneler heap alanında tutulur.
        // Stack, metod çağrıları ve yerel değişkenler için kullanılır.
        // new ProductDao() ifadesi,
        // bellekte heap alanında bir nesne oluşturur.
        this.productDao=new ProductDao();

    }



}
