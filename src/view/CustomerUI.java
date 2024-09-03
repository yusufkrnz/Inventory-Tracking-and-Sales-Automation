/* package view;

import business.CustomerController;
import entity.Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerUI extends JFrame {

    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_name;
    private JTextField fld_customer_name;
    private JComboBox cmb_customer_type;
    private JLabel lbl_customer_type;
    private JLabel lbl_customer_phone;
    private JTextField fld_customer_phone;
    private JTextField textField1;
    private JLabel lbl_customer_mail;
    private JLabel lbl_customer_address;
    private JTextArea tarea_customer_address;
    private JButton btn_customer_save;
    private Customer customer;
    private CustomerController customerController;

    public CustomerUI(Customer customer) {

        this.customer = customer;
        this.customerController = new CustomerController();

        container = new JPanel();
        this.add(container);
        this.setTitle("Müşteri Ekle/Düzenle");
        this.setSize(400, 500);

        // Ekranın ortalanması için gerekli işlem
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

     //   if (this.customer.getId())
        System.out.println(this.customer);
    }
}
*/

package view;

import business.CustomerController;
import core.Helper;
import entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CustomerUI extends JFrame {

    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_name;
    private JTextField fld_customer_name;
    private JComboBox<Customer.TYPE_> cmb_customer_type;
    private JLabel lbl_customer_type;
    private JLabel lbl_customer_phone;
    private JTextField fld_customer_phone;
    private JLabel lbl_customer_mail;
    private JTextField fld_customer_mail;
    private JLabel lbl_customer_address;
    private JTextArea tarea_customer_address;
    private JButton btn_customer_save;
    private JTextField textField1;
    private Customer customer;
    private CustomerController customerController;

    public CustomerUI(Customer customer) {


        this.customer = customer;
        this.customerController = new CustomerController();

        container = new JPanel();

        container.setLayout(new GridLayout(8, 2, 10, 8)); // 8 satır, 2 sütun, 10 piksel boşluk

        lbl_title = new JLabel("Müşteri Ekle/Düzenle");

        lbl_title.setFont(new Font("Arial", Font.BOLD, 16));
        lbl_title.setHorizontalAlignment(SwingConstants.CENTER);

        lbl_name = new JLabel("İsim:");
        fld_customer_name = new JTextField();
        lbl_customer_type = new JLabel("Müşteri Türü:");
        cmb_customer_type = new JComboBox<>(Customer.TYPE_.values());

        lbl_customer_phone = new JLabel("Telefon:");
        fld_customer_phone = new JTextField();
        lbl_customer_mail = new JLabel("E-posta:");

        fld_customer_mail = new JTextField();

        lbl_customer_address = new JLabel("Adres:");
        tarea_customer_address = new JTextArea(4, 20);
        btn_customer_save = new JButton("Kaydet");

        container.add(lbl_title);
        container.add(new JLabel()); // Boş yer tutucu
        container.add(lbl_name);
        container.add(fld_customer_name);
        container.add(lbl_customer_type);
        container.add(cmb_customer_type);
        container.add(lbl_customer_phone);
        container.add(fld_customer_phone);
        container.add(lbl_customer_mail);
        container.add(fld_customer_mail);
        container.add(lbl_customer_address);
        container.add(new JScrollPane(tarea_customer_address));
        container.add(new JLabel()); // Boş yer tutucu
        container.add(btn_customer_save);

        this.add(container);
        this.setTitle("Müşteri Ekle/Düzenle");
        this.setSize(400, 500);

        // Ekranın ortalanması için gerekli işlem
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        if (this.customer.getId() == 0) {
            this.lbl_title.setText("Müşteri Ekle");
        } else {
            this.lbl_title.setText("Müşteri Düzenle");

            this.fld_customer_name.setText(this.customer.getName());
            this.fld_customer_mail.setText(this.customer.getMail());
            this.fld_customer_phone.setText(this.customer.getPhone());
            this.tarea_customer_address.setText(this.customer.getAddress());
            this.cmb_customer_type.getModel().setSelectedItem(this.customer.getType());


        }


        this.btn_customer_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_customer_name, this.fld_customer_phone};
            if (Helper.isFieldListEmpty(checkList)) {
                Helper.showMsg("fill");

            } else if (!Helper.isFieldEmpty(this.fld_customer_mail) && !Helper.isEmailValid(this.fld_customer_mail.getText())) {
                Helper.showMsg("Geçerli bi e-posta giriniz !");

            } else {
                boolean result=false;
                this.customer.setName(this.fld_customer_name.getText());
                this.customer.setPhone(this.fld_customer_phone.getText());
                this.customer.setMail(this.fld_customer_mail.getText());
                this.customer.setAddress(this.tarea_customer_address.getText());
                this.customer.setType((Customer.TYPE_) this.cmb_customer_type.getSelectedItem());


                try {
                    if (this.customer.getId() == 0) {
                        result = this.customerController.save(this.customer);
                    }else {
                        result=this.customerController.update(this.customer);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace(); // Hata mesajını yazdırın
                    Helper.showMsg("error");
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
