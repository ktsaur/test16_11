package ru.kpfu.entity;

public class User {
    private int user_id;
    private String login;
    private String password;
    private String email;

    public User(int user_id, String login, String password, String email) {
        this.user_id = user_id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
