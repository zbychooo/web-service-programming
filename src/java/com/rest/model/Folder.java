package com.rest.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Atuan
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Folder {
    @XmlAttribute
    private Long id;
    @XmlElement
    private User user;
    @XmlElement
    private String name;
    @XmlElement
    private Boolean shared;
    
    @XmlElementWrapper(name="files")
    @XmlElement(name="file")
    private List<File> files;

    public Folder(){
        this.files = new ArrayList<>();
    }    
    
    public Folder(Long id, User user,
            String name, Boolean shared){
        this.id = id;
        this.user = user;
        this.name = name;
        this.shared = shared;
        this.files = new ArrayList<>();
    }

    public Folder(Long id, User user, String name, Boolean shared, List<File> files) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.shared = shared;
        this.files = files;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Folder{" + "id=" + id + ", name=" + name + ", shared=" + shared + '}';
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
    
    
}