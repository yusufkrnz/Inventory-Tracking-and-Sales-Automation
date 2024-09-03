package business;

import core.Helper;
import dao.UserDao;
import entity.User;

public class UsersController {

                         /**  HATA YERİ */
    private final UserDao userDao = new UserDao();

    public User findByLogin(String mail, String password) {
        if (!Helper.isEmailValid(mail) || password == null || password.trim().isEmpty()) {
            return null;
        }
        return this.userDao.findByLogin(mail, password);
    }
}
