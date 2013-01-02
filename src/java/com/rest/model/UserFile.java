package com.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zbyszko
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserFile {
    
    @XmlAttribute
    private Long id;
    @XmlElement
    private String fileName;
    @XmlElement
    private Long fileSize;
    @XmlElement
    private String dateStamp;
//    @XmlElementWrapper(name="tags")
//    @XmlElement(name="tag")
    @XmlElement
    private String tagName;
    @XmlElement
    private String directPath;

    public UserFile() {
    }

    public UserFile(Long id, String fileName, Long fileSize, String dateStamp, String tagName, String directPath) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.dateStamp = dateStamp;
        this.tagName = tagName;
        this.directPath = directPath;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public String getTagName() {
        return tagName;
    }

    public String getDirectPath() {
        return directPath;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public void setDirectPath(String directPath) {
        this.directPath = directPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "UserFiles{" + "id=" + id + ", fileName=" + fileName + ", fileSize=" + fileSize + ", dateStamp=" + dateStamp + ", tagName=" + tagName + ", directPath=" + directPath + '}';
    }
}
