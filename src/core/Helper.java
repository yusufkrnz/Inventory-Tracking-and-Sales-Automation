package core;

import com.mysql.cj.CacheAdapter;
import com.mysql.cj.protocol.x.XMessage;

import javax.swing.*;

public class Helper {

    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    //       FARKLI BİR KULLANIM
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    // helper da yardımcı bi sınıf oluşturduk çünkü:
    // LoginUI da sürekli aynı şeyi
    //yazmamış olmak için
    public static boolean isFieldEmpty(JTextField field) {
        //sağ da solda boşlukta kabul etmiyoruz ve
        // trim metodunu kullanıyoruz.
        //TRİM LE TEMİZLİK VAKTİ ;)
        return field.getText().trim().isEmpty();
    }

    // bütün field ları kontrol edecek bi helper oluşturuyoruz
    // arraylarden oluşan fieldları tarayacağız

    public static boolean isFieldListEmpty(JTextField[] fields) {
        for (JTextField field : fields) {
            if (isFieldEmpty(field))
                return true;
        }
        return false;
    }

    // mail valid etmek

    /**
     * KONTROL BLOKLARI  !! EKSİĞİM BÜYÜK GMAİL.COM YAZINCADA ALIYOR
     * YADA
     *
     * @FLKGJDFJGLŞD.COM YAZSAMDA
     * KABUL EDİYOR
     * DÜZELT
     */
    public static boolean isEmailValid(String mail) {
        // info@gmail.com,
        // @ olacak , @ ' ten önce bi değer ,
        // sonra nokta olacak ve
        // bir değer olacak

        if (mail == null || mail.trim().isEmpty()) return false;

        if (!mail.contains("@")) return false;

        String[] parts = mail.split("@");

        if (parts.length != 2) return false;

        if (parts[0].trim().isEmpty() || parts.toString().isEmpty()) return false;

        if (!parts[1].contains(".")) return false;

        return true;


    }

    public static void optionPaneDialogTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");



    }

    // kullanıcıya pop-up bildiirm atmak
    public static void showMsg(String message) {

        String msg;
        String title;

        optionPaneDialogTR();

        switch (message) {
            case "fill" -> {
                msg = "Lütfen tüm alanları  doldurunuz. ";
                title = "HATA!";

            }
            case "done" -> {
                msg = "İşlem başarılı ! ";
                title = "Sonuç";
            }
            case "eror" -> {
                msg = "Bir hata oluştu !";
                title = "HATA!";
            }
            default -> {
                msg = message;
                title = "Mesaj";
            }
        }


        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        optionPaneDialogTR();

        String msg;

        if (str.equals("sure")) {
            msg = "Bu işlemi gerçekleştirmek istedğiniz emin misiniz ?";

        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Emin misiniz?", JOptionPane.YES_NO_OPTION) == 0;
    }

}

