package view;

import business.ProductController;
import core.Helper;
import entity.Product;

import javax.swing.*;
import java.awt.*;

public class ProductUI extends JFrame {

    private JPanel container;
    private JLabel lbl_p_title;
    private JLabel lbl_p_name;
    private JTextField fld_p_name;
    private JLabel lbl_p_code;
    private JTextField fld_p_code;
    private JLabel lbl_p_price;
    private JTextField fld_p_price;
    private JLabel lbl_p_stock;
    private JTextField fld_p_stock;
    private JLabel lbl_p_category;
    private JComboBox<Product.Type> cmb_p_category;
    private JLabel lbl_p_supplier;
    private JTextField fld_p_supplier;
    private JButton btn_p_add;
    private Product product;
    private ProductController productController;

    public ProductUI(Product product) {
        this.product = product;
        this.productController = new ProductController();

        container = new JPanel();
        container.setLayout(new GridLayout(8, 2, 10, 8)); // 8 satır, 2 sütun, 10 piksel boşluk

        lbl_p_title = new JLabel("Ürün Ekle/Düzenle");
        lbl_p_title.setFont(new Font("Arial", Font.BOLD, 16));
        lbl_p_title.setHorizontalAlignment(SwingConstants.CENTER);

        lbl_p_name = new JLabel("Ürün Adı:");
        fld_p_name = new JTextField();

        lbl_p_code = new JLabel("Ürün Kodu:");
        fld_p_code = new JTextField();

        lbl_p_price = new JLabel("Fiyat:");
        fld_p_price = new JTextField();

        lbl_p_stock = new JLabel("Stok Miktarı:");
        fld_p_stock = new JTextField();

        lbl_p_category = new JLabel("Kategori:");
        cmb_p_category = new JComboBox<>(Product.Type.values()); // Enum değerlerini doğrudan ekliyoruz

        lbl_p_supplier = new JLabel("Tedarikçi:");
        fld_p_supplier = new JTextField();

        btn_p_add = new JButton("Kaydet");

        // Bileşenleri panele ekleme
        container.add(lbl_p_title);
        container.add(new JLabel()); // Boş yer tutucu
        container.add(lbl_p_name);
        container.add(fld_p_name);
        container.add(lbl_p_code);
        container.add(fld_p_code);
        container.add(lbl_p_price);
        container.add(fld_p_price);
        container.add(lbl_p_stock);
        container.add(fld_p_stock);
        container.add(lbl_p_category);
        container.add(cmb_p_category);
        container.add(lbl_p_supplier);
        container.add(fld_p_supplier);
        container.add(new JLabel()); // Boş yer tutucu
        container.add(btn_p_add);

        this.add(container);
        this.setTitle("Ürün Ekle/Düzenle");
        this.setSize(400, 500);

        // Ekranın ortalanması için gerekli işlem
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        // Varsayılan değerlerin ayarlanması
        if (this.product.getId() == 0) {
            this.lbl_p_title.setText("Ürün Ekle");
        } else {
            this.lbl_p_title.setText("Ürün Düzenle");

            this.fld_p_name.setText(this.product.getName());
            this.fld_p_code.setText(this.product.getCode());
            this.fld_p_price.setText(String.valueOf(this.product.getPrice()));
            this.fld_p_stock.setText(String.valueOf(this.product.getStock()));
            this.cmb_p_category.setSelectedItem(this.product.getCategory()); // Enum seçim ayarı
            this.fld_p_supplier.setText(this.product.getSupplier());
        }

        this.btn_p_add.addActionListener(e -> {
            JTextField[] checkList = {this.fld_p_name, this.fld_p_code, this.fld_p_price, this.fld_p_stock};
            if (Helper.isFieldListEmpty(checkList)) {
                Helper.showMsg("fill");
            } else {
                boolean result = false;
                this.product.setName(this.fld_p_name.getText());
                this.product.setCode(this.fld_p_code.getText());
                this.product.setPrice(Integer.parseInt(this.fld_p_price.getText()));
                this.product.setStock(Integer.parseInt(this.fld_p_stock.getText()));
                this.product.setCategory((Product.Type) this.cmb_p_category.getSelectedItem());
                this.product.setSupplier(this.fld_p_supplier.getText());

                if (this.product.getId() == 0) {
                    result = this.productController.save(this.product);
                } else {
                    result = this.productController.update(this.product);
                }

                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
}







