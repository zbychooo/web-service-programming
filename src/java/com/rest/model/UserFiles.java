package com.rest.model;

/**
 *
 * @author Zbyszko
 */
public class UserFiles {
    private long id;
    private String fileName;
    private long fileSize;
    private String dateStamp;
    private String tagName;
    private String directPath;

    public UserFiles(long id, String fileName, long fileSize, String dateStamp, String tagName, String directPath) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.dateStamp = dateStamp;
        this.tagName = tagName;
        this.directPath = directPath;
    }

    public long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
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

    @Override
    public String toString() {
        return "UserFiles{" + "id=" + id + ", fileName=" + fileName + ", fileSize=" + fileSize + ", dateStamp=" + dateStamp + ", tagName=" + tagName + ", directPath=" + directPath + '}';
    }
}
