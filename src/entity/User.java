package entity;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private String mail;
    private String password;
    private Role role; // Yeni alan eklendi

    public enum Role {
        ADMIN,
        STOCK_MANAGER,
        USER
    }

    public User() {
    }

    public User(int id, String name, String mail, String password, Role role) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +  // Yeni alan eklendi
                '}';
    }


}
