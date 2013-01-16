package com.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User model class.
 * @author Zbyszko
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    
    @XmlAttribute
    private Long uid;
    @XmlElement
    private String login;
    @XmlElement
    private String password;
    @XmlElement
    private String username;
    @XmlElement
    private String role;

    public User(){}
    
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
