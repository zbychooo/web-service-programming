package com.rest.model;

/**
 * User model class.
 * @author Zbyszko
 */
public class User {
    
    private Long uid;
    private String login;
    private String password;
    private String username;
    private String role;

    public User(Long uid, String login, String password, String username, String role) {
        this.uid = uid;
        this.login = login;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        return "User{" + "uid=" + uid + ", login=" + login + ", password=" + password + ", username=" + username + ", role=" + role + '}';
    }
       
}
