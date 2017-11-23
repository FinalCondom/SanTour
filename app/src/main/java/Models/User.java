package Models;

/**
 * Created by kevin on 17.11.2017.
 */

public class User {

    private int id_user;
    private String name;
    private String password;
    private String mail;
    private String phoneNumber;


    public User(int id_user, String name,String password,String mail,String phoneNumber) {

     this.id_user=id_user;
     this.name=name;
     this.password=password;
     this.mail=mail;
     this.phoneNumber=phoneNumber;

    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

