package com.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Atuan
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Folder {
    @XmlAttribute
    private Long id;
//    @XmlElement
//    private User user;
    @XmlElement
    private String name;
    @XmlElement
    private Boolean shared;

    public Folder(){}    
    
    public Folder(Long id, //User user,
            String name, Boolean shared){
        this.id = id;
//        this.user = user;
        this.name = name;
        this.shared = shared;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    @Override
    public String toString() {
        return "Folder{" + "id=" + id + ", name=" + name + ", shared=" + shared + '}';
    }
    
    
}
