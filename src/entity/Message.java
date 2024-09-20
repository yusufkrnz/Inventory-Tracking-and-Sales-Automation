package entity;

import java.lang.ref.SoftReference;
import java.sql.Timestamp;

public class Message {

//mysql dedeki message veri tabanının sütunları
    //id	userName	name	code	title	subject


    private int id;
    private String userName;
    private String name;
    private String code;
    private String title;

    private String subject;
    private Timestamp salesTime;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void setSalesTime(Timestamp salesTime) {
        this.salesTime = salesTime;
    }

    public Timestamp getSalesTime() {
        return salesTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", salesTime=" + salesTime +
                '}';
    }
}
