package view;

import business.UsersController;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    private JPanel container;
    private JLabel lbl_title;
    private JPanel pnl_bottom;
    private JTextField fld_mail;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private JPasswordField fld_passwrd;
    private JButton btn_login;
    private UsersController usersController;

    public LoginUI() {
        this.usersController = new UsersController();
        this.add(container);
        this.setTitle("Bright Stok");
        this.setSize(500, 500);

        // Ekranın ortalanması için gerekli işlem
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        this.btn_login.addActionListener(e -> {
            // Boş alan kontrolü
            JTextField[] checkList = {this.fld_passwrd, this.fld_mail};
            if (!Helper.isEmailValid(this.fld_mail.getText())) {
                Helper.showMsg("Geçerli bir Eposta giriniz");
            } else if (Helper.isFieldListEmpty(checkList)) {
                Helper.showMsg("Tüm alanları doldurunuz");
            } else {
                User user = this.usersController.findByLogin(this.fld_mail.getText(), this.fld_passwrd.getText());
                if (user == null) {
                    Helper.showMsg("Kullanıcı bulunamadı!");
                } else {
                    this.dispose();
                    DashboardUI dashboardUI = new DashboardUI(user);
                    dashboardUI.setVisible(true);

                    // Kullanıcının rolüne göre farklı ekranlar gösterme
                    switch (user.getRole()) {
                        case ADMIN:
                            dashboardUI.showAdminPanel();
                            break;
                        case STOCK_MANAGER:
                            dashboardUI.showStockManagerPanel();
                            break;
                        case USER:
                            dashboardUI.showUserPanel();
                            break;
                        default:
                            Helper.showMsg("Geçersiz rol!");
                    }
                }
            }
        });
    }
}
