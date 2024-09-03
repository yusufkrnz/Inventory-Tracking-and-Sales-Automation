package business;

import dao.BasketDao;
import entity.Basket;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class BasketController {

    private final BasketDao basketDao=new BasketDao();


    public boolean save(Basket basket) throws SQLException {
        return this.basketDao.save(basket);
    }
    public ArrayList<Basket> findAll() {
        return this.basketDao.findAll();

    }

    public boolean clear(){
        return this.basketDao.clear();
    }


}
