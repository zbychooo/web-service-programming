package com.rest.model;

/**
 *
 * @author Zbyszko
 */
public class User {
    
    private Long uid;
    private String login;
    private String password;
    private String username;

    public User(String login, String password, String username) {
        this.login = login;
        this.password = password;
        this.username = username;
    }

    public Long getUid() {
        return uid;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" + "uid=" + uid + ", login=" + login + ", password=" + password + ", username=" + username + '}';
    }
    
}
