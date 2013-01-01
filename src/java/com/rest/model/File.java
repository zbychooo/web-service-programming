package com.rest.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Atuan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class File {
    @XmlAttribute
    private Long id;
    @XmlElement
    private String name;
    @XmlElement
    private Date added;
    @XmlElement
    private Long size;
    @XmlElementWrapper(name="tags")
    @XmlElement(name="tag")
    private List<String> tags;

    public File() {
        tags = new ArrayList<>();
    }

    public File(Long id, String name, Date added, Long size, List<String> tags) {
        this.id = id;
        this.name = name;
        this.added = added;
        this.size = size;
        this.tags = tags;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    
}
