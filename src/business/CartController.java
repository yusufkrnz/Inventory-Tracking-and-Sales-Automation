package business;

import dao.CartDao;
import entity.Cart;

import java.sql.SQLException;
import java.util.ArrayList;

public class CartController {


    private final CartDao cartDao = new CartDao();

    public boolean save(Cart cart) throws SQLException {
        return this.cartDao.save(cart);
    }

    public ArrayList<Cart> findAll() {

        return this.cartDao.findAll();
    }


}
