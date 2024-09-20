package view;

import dao.MessageDao;
import entity.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MessageUI extends JFrame {

    private JPanel container;
    private JTextField fld_p_id;
    private JTextField fld_c_name;
    private JTextField fld_p_code;
    private JLabel lbl_title;
    private JLabel lbl_p_id;
    private JLabel lbl_c_name;
    private JLabel lbl_p_code;
    private JTextField fld_p_name;
    private JLabel lbl_p_name;
    JTextField fld_m_title;
    private JLabel lbl_m_title;
    private JTextField fld_subject;
    private JLabel lbl_subject;
    private JButton btn_send;
    private Message message;

    public MessageUI(Message message) {
        this.message = message;

      this.add(container);

        this.setTitle("Mesaj Gönder");
        this.setSize(400, 400);

        // Ekranın ortalanması için gerekli işlem
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        // Mesaj bilgilerini UI'da göster
        fld_p_id.setText(String.valueOf(message.getId()));
        fld_p_code.setText(message.getUserName());
        fld_p_name.setText(message.getName());
        fld_m_title.setText(message.getTitle());
        fld_subject.setText(message.getSubject());

        this.btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mesaj gönderme işlemini burada yap
                // Örneğin MessageDao'yu kullanarak mesajı veritabanına kaydedebilirsiniz
                try {
                    MessageDao messageDao = new MessageDao();
                    message.setTitle(fld_m_title.getText());
                    message.setSubject(fld_subject.getText());
                    boolean result = messageDao.save(message);
                    if (result) {
                        JOptionPane.showMessageDialog(MessageUI.this, "Mesaj başarıyla gönderildi.");
                        dispose(); // Pencereyi kapat
                    } else {
                        JOptionPane.showMessageDialog(MessageUI.this, "Mesaj gönderilirken bir hata oluştu.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MessageUI.this, "Veritabanı hatası: " + ex.getMessage());
                }
            }
        });
    }
}
