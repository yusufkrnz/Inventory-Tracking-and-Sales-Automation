package view;

import business.BasketController;
import business.CartController;
import core.Helper;
import entity.Basket;
import entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CartUI extends JFrame {
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_customer_name;
    private JTextField fld_cart_date;
    private JTextArea tarea_cart_note;
    private JButton btn_cart;
    private Customer customer;
private BasketController basketController;
private CartController cartController;

    /**

      cartuı içinde bir tane customer almak zorunda çünkü customera ürünleri ekleyeceğiz.

      customer yoksa ürün seçilemez çünkü

     */

    public CartUI(Customer customer) {
        this.customer=customer;
        this.cartController=new CartController();
        this.basketController=new BasketController();

        this.add(container);
        this.setTitle("Sipariş Oluştur");
        this.setSize(300, 350);

        // Ekranın ortalanması için gerekli işlem
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        if (customer.getId() == 0){
            Helper.showMsg("Lütfen geçerli bir müşteri seçiniz ! ");
            dispose();
        }
        ArrayList<Basket> baskets=this.basketController.findAll();
        if (baskets.size()==0){
            Helper.showMsg("Lütfen sepete ürün ekleyiniz ! ");
            dispose();
        }

        this.lbl_customer_name.setText("Müşteri : "+this.customer.getName());


    }

}
