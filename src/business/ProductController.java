package business;

import core.Helper;
import core.Item;
import dao.ProductDao;
import entity.Customer;
import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    private final ProductDao productDao = new ProductDao();

    public ArrayList<Product> findAll() {
        return this.productDao.findAll();
    }

    public Product getById(int id) {
        return this.productDao.getById(id);
    }

    public boolean update(Product product) {
        // Öncelikle product null mı kontrol ediyoruz
        if (product == null) {
            Helper.showMsg("Güncelleme işlemi için geçersiz ürün!");
            return false;
        }

        // Ürün veritabanında mevcut mu kontrol ediyoruz
        if (this.getById(product.getId()) == null) {
            Helper.showMsg(product.getId() + " ID kayıtlı ürün bulunamadı!");
            return false;
        }

        // Kategori null mı kontrol ediyoruz
        if (product.getCategory() == null) {
            Helper.showMsg("Ürün kategorisi tanımlı değil!");
            return false;
        }

        // Güncelleme işlemi4
        return this.productDao.update(product);
    }


    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + "ID kayıtlı ürün bulunamadı!");
            return false;
        }
        return this.productDao.delete(id);
    }

    public boolean save(Product product) {

            product.setUniqcode(determineSymbol(product.getStock()));
            return this.productDao.save(product);

    }



    public ArrayList<Product> filter(String name, String code, Item isStock) {
        String query = "SELECT * FROM product";

        ArrayList<String> whereList = new ArrayList<>();

        if (name.length() > 0) {
            whereList.add("name LIKE '%" + name + "%'");
        }
        if (code.length() > 0) {
            whereList.add("code LIKE '%" + code + "%'");
        }

        if (isStock != null) {
            if (isStock.getKey() == 1) {
                whereList.add("stock > 0");
            } else {
                whereList.add("stock <= 0");
            }
        }

        if (!whereList.isEmpty()) {
            query += " WHERE " + String.join(" AND ", whereList);
        }


        return this.productDao.query(query);
    }


    /**
     * --------SEMBOL İŞLEMLERİ ---
     */

    public void updateProductSymbols() {
        ArrayList<Product> productList = this.findAll();
        for (Product product : productList) {
            String symbol = determineSymbol(product.getStock());
            product.setUniqcode(symbol);
            this.update(product);  // Ürünü veritabanında güncelle
        }
    }

    private String determineSymbol(int stock) {
        if (stock < 50) {
            return "\u26A0";
        } else if (stock >= 50 && stock < 100) {
            return "\uD83D\uDE41";  // Üzgün surat (😕)
        } else if (stock >= 100 && stock < 200) {
            return "\uD83D\uDE42";  // Yarı gülümseyen surat (😊)
        } else if (stock >= 200 && stock < 300) {
            return "\uD83D\uDE0A";  // Gülümseyen surat (😃)
        } else {
            return "\uD83D\uDE04";  // Çok gülümseyen surat (😄)
        }
    }



}
