package view;

import business.BasketController;
import business.CustomerController;
import business.MessageController;
import business.ProductController;
import core.Helper;
import core.Item;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


public class DashboardUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JPanel pnl_customer_filter;
    private JTextField fld_f_customer_name;
    private JComboBox<String> cmb_f_customer_type;
    private JButton btn_customer_new;
    private JButton btn_customer_filter_reset;
    private JButton btn_customer_filter;
    private JLabel lbl_f_customer_name;
    private JLabel lbl_f_customer_type;
    private JPanel pnl_manager_product;
    private JScrollPane scrl_product;
    private JTable tbl_product;
    private JPanel pnl_product_filter;
    private JTextField fld_f_product_name;
    private JTextField fld_f_product_code;
    private JComboBox<Item> cmb_f_product_stock;
    private JButton btn_product_filter;
    private JButton btn_product_filter_reset;
    private JButton btn_product_new;
    private JLabel lbl_f_product_name;
    private JLabel lbl_f_product_stock;
    private JLabel lbl_f_product_code;
    private JPanel pnl_basket;
    private JScrollPane scrl_basket;
    private JPanel pnl_basket_top;
    private JLabel lbl_;
    private JComboBox<Item> cmb_basket_customer;
    private JButton btn_basket_reset;
    private JButton btn_basket_new;
    private JLabel lbl_basket_price;
    private JLabel lbl_basket_count;
    private JTable tbl_basket;
    private JPanel pnl_customer_basket;
    private JLabel lbl_customer_basket;
    private JLabel lbl_customer_basket_amount;
    private JTable table1;
    private JButton btn_c_basket_take;
    private JPanel pnl_analyzes;
    private JLabel lbl_customer_analyzes;
    private JLabel lbl_s_manager_analyzes;
    private JScrollPane scrl_c_analyzes;
    private JScrollPane scrl_stock_analyzes;
    private JTable tbl_cstmr_analyzes;
    private JTable tbl_stock_analyzes;
    private JPanel pnl_cstmr_anlyzes;
    private JPanel pnl_smanager_analyzes;
    private JPanel pnl_User_message;
    private JTable tbl_m_user;
    private JScrollPane scrl_user_message;
    private JComboBox cmb_c_f_category;
    private JLabel lbl_customer_categoryy;
    private JPanel pnl_m_title;
    private JPanel pnl_m_container;
    private JLabel Kategori;
    private DefaultTableModel tmdl_message = new DefaultTableModel(); // Mesaj tablosu için model

    private JPopupMenu popup_product = new JPopupMenu();
    private User user;
    private CustomerController customerController;
    private ProductController productController;
    private BasketController basketController;
    private DefaultTableModel tmdl_customer;
    private JPopupMenu popup_customer;
    private DefaultTableModel tmdl_product = new DefaultTableModel();
    private DefaultTableModel tmdl_basket = new DefaultTableModel();

    private JTextArea fld_user_m_area; // Mesajları göstermek için bir JTextArea

    private MessageController messageController;

    public DashboardUI(User user) {

        if (user == null) {
            Helper.showMsg("error");
            dispose();
            return;
        }


        fld_user_m_area = new JTextArea();


        /**     ROLE göre görünürlük ayarlama          */

        this.user = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        this.basketController = new BasketController();


        // Add container to the JFrame
        this.add(container);

        this.setTitle("Stok ve Satış Takip Sistemi");
        this.setSize(1300, 750);

        // Ekranın ortalanması için gerekli işlem
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        this.lbl_welcome.setText("Hoş Geldin : " + this.user.getName());
        this.btn_logout.addActionListener(e -> {
            dispose();
            new LoginUI(); // LoginUI nesnesini oluştur
        });


        // JTextArea'yı oluştur ve ekle
        fld_user_m_area = new JTextArea();
        fld_user_m_area.setEditable(false); // Kullanıcı mesajları değiştiremesin
        JScrollPane scrollPane = new JScrollPane(fld_user_m_area);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);


        //CUSTOMER TAB

        initializeCustomerTable();
        loadCustomerTable(null);
        loadCustomerPopupMenu();
        loadCustomerButtonEvent();
        this.cmb_f_customer_type.setModel(new DefaultComboBoxModel<>(
                Arrays.stream(Customer.TYPE_.values())  // Enum değerlerini alıyoruz
                        .map(Enum::name)                 // Her birini String'e dönüştürüyoruz
                        .toArray(String[]::new)          // Diziye çeviriyoruz
        ));


        this.cmb_f_customer_type.setSelectedItem(null);

        //PRODUCT TAB
        loadProductTable(null);
        loadProductPopupMenu();
        loadProductButtonEvent();
        loadProductCategoryCombo();

        //mesaj operasyonları
        initializeMessageTable();
        loadMessageTable();


        this.cmb_c_f_category.addItem(new Item(1, " Smart Assistants "));
        this.cmb_c_f_category.addItem(new Item(2, " Personel Care "));
        this.cmb_c_f_category.addItem(new Item(3, " Smart Devices "));
        this.cmb_c_f_category.addItem(new Item(4, " Kitchen Helpers "));

        this.cmb_f_product_stock.addItem(new Item(1, "Stokta var"));
        this.cmb_f_product_stock.addItem(new Item(2, "Stokta yok!"));
        this.cmb_f_product_stock.setSelectedItem(null);

        //BASKET TAB
        loadBasketTable();
        loadBasketButtonEvent();
        loadBasketCustomerCombo();


        this.btn_basket_new.addActionListener(e -> {
            Item selectedCustomer = (Item) this.cmb_basket_customer.getSelectedItem();
            if (selectedCustomer == null) {
                Helper.showMsg("Lütfen bir müşteri seçiniz ! ");
            } else {
                Customer customer = this.customerController.getById(selectedCustomer.getKey());
                ArrayList<Basket> baskets = this.basketController.findAll();

                if (customer.getId() == 0) {
                    Helper.showMsg("Böyle bi müşteri bulunamadı !");
                } else if (baskets.size() == 0) {
                    Helper.showMsg("Lütfen sepete ürün ekleyinşz ! ");
                } else {
                    CartUI cartUI = new CartUI(customer);

                }

            }
        });
    }

    private void loadMessageTable() {
    }

    private void loadProductCategoryCombo() {
    }

    // Mesaj eklemek için addMessage metodu
    private void loadMessageSend() {
        // analyzes veri tabınından verileri tbl_m_user a çekmek istiyorum .

    }

    private void loadBasketCustomerCombo() {
        ArrayList<Customer> customers = this.customerController.findAll();
        this.cmb_basket_customer.removeAllItems();
        for (Customer customer : customers) {
            int comboKey = customer.getId();
            String comboValue = customer.getName();
            this.cmb_basket_customer.addItem(new Item(comboKey, comboValue));

        }

        this.cmb_basket_customer.setSelectedItem(null);
    }


    private void loadBasketButtonEvent() {
        this.btn_basket_reset.addActionListener(e -> {
            if (this.basketController.clear()) {
                Helper.showMsg("done");
                loadBasketTable();
            } else {
                Helper.showMsg("error");
            }

        });
    }

    /**
     * Sütunların Tanımlanması:
     * clearModel.setColumnIdentifiers(columnBasket);
     * satırı ile sütunlar tabloya tanımlandı.
     * Modelin Tabloya Atanması:
     * Tüm işlemler tamamlandıktan sonra,
     * this.tbl_basket.setModel(clearModel);
     * ile model tabloya atanıyor.
     */
    private void initializeMessageTable() {
        String[] columnMessage = {"İşlem Zamanı", "Ürün ID", "Müşteri ismi", "Kategori", "Ürün İsmi", "Ürün Fiyatı", "Sepet Tutarı", "Stok Durumu", "Tedarikçi"};
        DefaultTableModel tmdl_message = new DefaultTableModel(null, columnMessage);

       tbl_m_user.setModel(tmdl_message);


    }
    private void loadMessageTable(ArrayList<Message> messages) {
        if (messages == null) {
            messages = this.messageController.findAll();
        }

        // Tabloyu temizle
        tmdl_message.setRowCount(0);

        for (Message message : messages) {
            Object[] rowObject = {

                    message.getId(),
                    message.getUserName(),

                    message.getName(),

            };
            tmdl_message.addRow(rowObject);
        }

        tbl_m_user.setModel(tmdl_message);
        tbl_m_user.getTableHeader().setReorderingAllowed(false);
        tbl_m_user.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_m_user.setEnabled(false);
    }

    private void loadBasketTable() {
        Object[] columnBasket = {"ID", "Ürün Adı", "Ürün Kodu", "Fiyat", "Stok", "Durum"};

        // Basket listesini al
        ArrayList<Basket> baskets = this.basketController.findAll();

        // Mevcut tablo modelini al ve sıfırla
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_basket.getModel();
        clearModel.setRowCount(0);

        // Tablo modeline sütunları ekle
        clearModel.setColumnIdentifiers(columnBasket);

        int totalPrice = 0;
        for (Basket basket : baskets) {
            Object[] rowObject = {
                    basket.getId(),
                    basket.getProduct().getName(),
                    basket.getProduct().getCode(),
                    basket.getProduct().getPrice(),
                    basket.getProduct().getStock(),
            };
            clearModel.addRow(rowObject);

            totalPrice += basket.getProduct().getPrice();
        }

        // Toplam fiyat ve ürün sayısını güncelle
        this.lbl_basket_price.setText(String.valueOf(totalPrice));
        this.lbl_basket_count.setText(String.valueOf(baskets.size()));

        // Tabloya modeli ata
        this.tbl_basket.setModel(clearModel);

        // Diğer tablo ayarlarını yap
        this.tbl_basket.getTableHeader().setReorderingAllowed(false);
        this.tbl_basket.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_basket.setEnabled(false);
    }
    private void loadCustomerTable(ArrayList<Customer> customers) {
        if (customers == null) {
            customers = this.customerController.findAll();
        }
        ArrayList<Customer> customers1 = this.customerController.findAllCombined();


        // Tabloyu temizle
        tmdl_customer.setRowCount(0);
        for (Customer customer : customers) {
            Object[] rowObject = {
                    customer.getId(),
                    customer.getName(),
                    customer.getType(),
                    customer.getPhone(),
                    customer.getMail(),
                    customer.getAddress()
            };
            tmdl_customer.addRow(rowObject);
        }

        tbl_customer.setModel(tmdl_customer);
        tbl_customer.getTableHeader().setReorderingAllowed(false);
        tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_customer.setEnabled(false);
    }
    private void initializeCustomerTable() {
        String[] columnNames = {"ID", "Müşteri Adı", "Tipi", "Telefon", "E-Posta", "Adres"};
        tmdl_customer = new DefaultTableModel(null, columnNames);
        tbl_customer.setModel(tmdl_customer);
    }
    private void loadProductButtonEvent() {


        this.btn_product_new.addActionListener(e -> {
            ProductUI productUI = new ProductUI(new Product());
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                    loadBasketTable();
                }
            });
        });


        this.btn_product_filter.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filter(
                    this.fld_f_product_name.getText(),
                    this.fld_f_product_code.getText(),
                    (Item) this.cmb_f_product_stock.getSelectedItem()
            );
            loadProductTable(filteredProducts);
            loadBasketTable();
        });


        this.btn_product_filter_reset.addActionListener(e -> {
            this.fld_f_product_name.setText(null);
            this.fld_f_product_code.setText(null);
            this.cmb_f_product_stock.setSelectedItem(null);
            loadProductTable(null);
        });


    }


    private void loadCustomerButtonEvent() {
        this.btn_customer_new.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                }
            });
        });

        this.btn_customer_filter.addActionListener(e -> {
            String selectedType = (String) this.cmb_f_customer_type.getSelectedItem();
            Customer.TYPE_ customerType = null;

            if (selectedType != null) {
                try {
                    customerType = Customer.TYPE_.valueOf(selectedType);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Geçersiz müşteri tipi: " + selectedType);
                }
            }

            ArrayList<Customer> filteredCustomers = this.customerController.filter(
                    this.fld_f_customer_name.getText(),
                    customerType
            );
            loadCustomerTable(filteredCustomers);
        });
        this.btn_customer_filter_reset.addActionListener(e -> {
            loadCustomerTable(null);
            this.fld_f_customer_name.setText(null);
            this.cmb_f_customer_type.setSelectedItem(null);
        });


    }



    private void loadCustomerPopupMenu() {
        this.tbl_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_customer.rowAtPoint(e.getPoint());
                tbl_customer.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        popup_customer = new JPopupMenu();
        this.popup_customer.add("Güncelle").addActionListener(e -> {

            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
            Customer editedCustomer = this.customerController.getById(selectId);
            CustomerUI customerUI = new CustomerUI(editedCustomer);
            System.out.println(selectId);
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
                }
            });
        });


        this.popup_customer.add("Sil").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
            Helper.showMsg("done");
            loadCustomerTable(null);
            loadBasketCustomerCombo();
            if (Helper.confirm("sure")) {

                if (this.customerController.delete(selectId)) {
                } else {
                    Helper.showMsg("error");
                }

            }

        });
        tbl_customer.setComponentPopupMenu(popup_customer);
    }

    /**
     * -----------Mesaj işlemleri  --------------------
     */

    // fld_user_m_area'ya metin eklemek için public bir metot


    private void loadProductPopupMenu() {

        this.tbl_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });


        //MESAJLAŞMA OLAYI BAŞLANGIC


        this.popup_product.add("Mesaj Gönder !").addActionListener(e -> {
            // Seçilen ürünün ID'sini al
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(), 0).toString());

            // Ürün bilgilerini al
            Product messageProduct = this.productController.getById(selectId);

            // Mesaj için yeni bir Message nesnesi oluştur
            Message message = new Message();
            message.setId(selectId); // ID'yi ayarla
            message.setCode(messageProduct.getCode());
            message.setName(messageProduct.getName());

            // Mesaj UI'sını oluştur
            MessageUI messageUI = new MessageUI(message);

            // Mesaj UI'sında mevcut bilgileri gösterebilmek için gerekli ek işlemleri yapın
            messageUI.fld_m_title.setText("Ürün Bilgisi: " + messageProduct.getName());
        });


        this.popup_product.add(" Sepete Ekle ! ").addActionListener(e -> {
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(), 0).toString());
            Product basketProduct = this.productController.getById(selectId);
            if (basketProduct.getStock() <= 0) {
                Helper.showMsg("Bu ürün stokta yoktur!");
            } else {
                Basket basket = new Basket(basketProduct.getId());

                try {
                    if (this.basketController.save(basket)) {
                        Helper.showMsg("done");
                        loadBasketTable();
                    } else {
                        Helper.showMsg("error");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

        this.popup_product.add("Güncelle").addActionListener(e -> {
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(), 0).toString());
            ProductUI productUI = new ProductUI(this.productController.getById(selectId));
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                    loadBasketTable();
                }
            });
        });
        this.popup_product.add("Sil").addActionListener(e -> {
            //hatalı kullanım
            //  int selectId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(),0,toString());
            int selectId = Integer.parseInt(this.tbl_product.getValueAt(this.tbl_product.getSelectedRow(), 0).toString());

            if (Helper.confirm("sure")) {
                if (this.productController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadProductTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }


        });
        this.tbl_product.setComponentPopupMenu(this.popup_product);

    }


    private void loadProductTable(ArrayList<Product> products) {


        Object[] columnProduct = {"ID", "Ürün Adı", "Ürün Kodu", "Fiyat", "Kategori", "Stok", "Satıcı", "Durum"};

        if (products == null) {
            products = this.productController.findAll();
        }
        ArrayList<Customer> customers1 = this.customerController.findAllCombined();
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_product.getModel();
        clearModel.setRowCount(0);

        this.tmdl_product.setColumnIdentifiers(columnProduct);
        for (Product product : products) {
            Object[] rowObject = {

                    product.getId(),
                    product.getName(),
                    product.getCode(),
                    product.getPrice(),
                    product.getCategory(),
                    product.getStock(),
                    product.getSupplier(),
                    product.getUniqcode()

            };
            this.tmdl_product.addRow(rowObject);
        }
        this.tbl_product.setModel(tmdl_product);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_product.setEnabled(false);
    }


    // Admin Panelini Göster

    public void showAdminPanel() {
        System.out.println("Admin Paneli Açıldı");
    }


    // Stok Yöneticisi Panelini Göster
    public void showStockManagerPanel() {
        System.out.println("Stok Yöneticisi Paneli Açıldı");
    }


    // Kullanıcı Panelini Göster
    public void showUserPanel() {
        System.out.println("Kullanıcı Paneli Açıldı");
    }


}
