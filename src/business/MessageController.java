package business;

import dao.MessageDao;
import entity.Basket;
import entity.Customer;
import entity.Message;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;

public class MessageController {
    private static MessageController instance;

    private final MessageDao messageDao ;

    public MessageController(){
        this.messageDao=new MessageDao();
    }


    public boolean save(Message message) throws SQLException {
        return this.messageDao.save(message);
    }

    public ArrayList<Message> findAll() {
        return this.messageDao.findAll();

    }

    public ArrayList<Message> findAllCombined() {
        return this.messageDao.findAllCombined();
    }
    public static MessageController getInstance() {
        if (instance == null) {
            instance = new MessageController();
        }
        return instance;
    }


}
