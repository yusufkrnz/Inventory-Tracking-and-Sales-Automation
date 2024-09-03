import business.ProductController;
import business.UsersController;
import core.Database;
import core.Helper;
import entity.User;
import view.DashboardUI;
import view.LoginUI;

import java.sql.Connection;

public class App {

    public static void main(String[] args) {

        Helper.setTheme();//tema aktif ettik

        LoginUI loginUI=new LoginUI();

                /** HATA YERİ*/

        UsersController usersController = new UsersController();

        User user = usersController.findByLogin("mustafa@java.com", "123123");
        DashboardUI dashboardUI = new DashboardUI(user);
        ProductController productController=new ProductController();
        productController.updateProductSymbols();

        // Ürünleri görüntüleme veya başka işlemler yapma


    }
}
