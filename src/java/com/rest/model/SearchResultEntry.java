package com.rest.model;

import javax.xml.bind.annotation.*;

/**
 *
 * @author Atuan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResultEntry {
    @XmlElement
    private String fileName;
    @XmlElement
    private Long fileSize;
    @XmlElement
    private String dateStamp;
    @XmlElement
    private String tagName;
    @XmlElement
    private String directPath;
    @XmlElement
    private String folderName;
    @XmlAttribute
    private Long folderId;
    @XmlElement
    private User owner;

    public SearchResultEntry() {
    }

    public SearchResultEntry(String fileName, Long fileSize, String dateStamp, String tagName, 
            String directPath, String folderName, Long folderId) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.dateStamp = dateStamp;
        this.tagName = tagName;
        this.directPath = directPath;
        this.folderName = folderName;
        this.folderId = folderId;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getDirectPath() {
        return directPath;
    }

    public void setDirectPath(String directPath) {
        this.directPath = directPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
