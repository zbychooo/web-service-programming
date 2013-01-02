package com.rest.controller;

import com.utilities.DBConnector;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * System Controller class.
 *
 * @author Zbyszko
 */
public class SystemController {

    public static final long MAX_STORAGE = 204800; //200MB = 2048kB
    private static final String MAIN_STORAGE_FOLDER = "D:\\RESTCloudStorage\\";

    /**
     * Creates a directory where the user files will be stored.
     *
     * @param UserSpaceName Name of space (name is the same as login).
     * @return true if the space is not created yet, otherwise false.
     */
    public boolean createSpace(String UserSpaceName) {

        UserSpaceName = this.MAIN_STORAGE_FOLDER + UserSpaceName;
        File f = new File(UserSpaceName);
        if (f.mkdir()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates user's personal directory(folder).
     *
     * @param UserFolderName Name of folder to create
     * @param UserSpaceName User personal space name (user's login)
     * @return true is the folder has been created, otherwise false.
     */
    public boolean createFolder(String UserFolderName, String UserSpaceName) {

        return this.createSpace(UserSpaceName + "\\" + UserFolderName);
    }

    /**
     * Gets the size of given directory.
     *
     * @param directoryName
     * @return size in kB
     */
    public long getFolderSize(String directoryName) {

        File directory = new File(MAIN_STORAGE_FOLDER + directoryName);
        return (this.calculateFolderSize(directory, 0) / 1024);
    }

    private Long calculateFolderSize(File directory, long length) {

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += calculateFolderSize(file, length);
            }
        }
        return length;
    }

    /**
     * Uploads file.
     *
     * @param in
     * @param info
     * @param path
     * @param userlogin
     * @return
     */
    public Long uploadFile(InputStream in, String fileName,
            String path, String userlogin) {

        String fdir = MAIN_STORAGE_FOLDER + path + "//" + fileName;
        System.out.println("fdir:" + fdir);
        File file = new File(fdir);

        if (file.exists()) {
            try {
                throw new FileAlreadyExistsException(file.getName());
            } catch (FileAlreadyExistsException ex) {
                Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            OutputStream out = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

            return file.length();
        } catch (IOException ex) {
            Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Long.valueOf(-1);
    }

    private String getCurrentDateStamp() {
        Date date = new java.util.Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    private Long getUserId(String login){
        
        long userID = -1;
        try {
            DBConnector db = new DBConnector();
            String sqlQuery = "select id from users where login='" + login + "'";

            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    userID = rs.getLong(1);
                }
            }
            System.out.println("userid: " + userID);
            db.closeConnection();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userID;
    }
    
    public Long addFileInfoToDB(String fileName, Long fileSize, String tags, String path) {

        long id = 0;
        try {
            DBConnector db = new DBConnector();

            String currentDate = this.getCurrentDateStamp();
            String sqlQuery = "insert into files(fileName, fileSize, dateStamp, tagName, directPath) values(?,?,?,?,?)";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                statement.setString(1, fileName);
                statement.setLong(2, fileSize);
                statement.setString(3, currentDate);
                statement.setString(4, tags);
                statement.setString(5, path);
                statement.executeUpdate();
                statement.close();
            }

            String sqlQuery2 = "select id from files where dateStamp='" + currentDate + "'";

            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery2)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    id = rs.getLong(1);
                }
            }
            db.closeConnection();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public void joinFileAndOwner(long fileID, String login) {

        try {
            long userID = getUserId(login);
            DBConnector db = new DBConnector();
            String sqlQuery2 = "insert into files_users(userId, fileId, isOwner) values(?,?,?)";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery2)) {
                statement.setLong(1, userID);
                statement.setLong(2, fileID);
                statement.setString(3, "TRUE");
                statement.executeUpdate();
                statement.close();
            }

            db.closeConnection();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Long addFolderInfoToDB(String folderName, String path) {

        long id = 0;
        try {
            DBConnector db = new DBConnector();

            String currentDate = this.getCurrentDateStamp();
            String sqlQuery = "insert into folders(name, dateStamp, directPath) values(?,?,?)";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                statement.setString(1, folderName);
                statement.setString(2, currentDate);
                statement.setString(3, path);
                statement.executeUpdate();
                statement.close();
            }

            String sqlQuery2 = "select id from folders where dateStamp='" + currentDate + "'";

            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery2)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    id = rs.getLong(1);
                }
            }
            db.closeConnection();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public void joinFolderAndOwner(Long folderID, String login) {

        try {
            long userID = getUserId(login);
            DBConnector db = new DBConnector();
            String sqlQuery2 = "insert into folders_users(userId, folderId, isOwner) values(?,?,?)";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery2)) {
                statement.setLong(1, userID);
                statement.setLong(2, folderID);
                statement.setString(3, "TRUE");
                statement.executeUpdate();
                statement.close();
            }
            db.closeConnection();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteFileFromDB(String path, String fileName, String login) {

        String sqlQuery;
        String isOwner = "FALSE";
        Long fileID = Long.valueOf(-1);
        Long userID;
        
        try {
            DBConnector db = new DBConnector();
            System.out.println("check, path:" + path + " filename:" + fileName + " login:" + login);
            sqlQuery = "select id from files where fileName='" + fileName + "' and directPath='" + path + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    fileID = rs.getLong(1);
                }
            }
            
            //checks onwership:
            userID = getUserId(login);
            System.out.println("userid: " + userID + " fileID: " + fileID);
            
            sqlQuery = "select isOwner from files_users where userId='" + userID + "' and fileId='" + fileID + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    isOwner = rs.getString(1);
                }
            }
            
            System.out.println("isOwner: " + isOwner);
            if(isOwner.equals("FALSE")) {
                return false;
            }
                       
            //System.out.println("DELETE: fileID " + fileID + "\npath: " + path + "\nfilename: " + fileName);
            sqlQuery = "delete from files where fileName='" + fileName + "' and directPath='" + path + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                statement.executeUpdate();
            }

            sqlQuery = "delete from files_users where fileId='" + fileID + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                statement.executeUpdate();
            }

            db.closeConnection();
            return true;         
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteFile(String path, String fileName) {
        //System.out.println(MAIN_STORAGE_FOLDER + path + "\\" + fileName);
        File file = new File(MAIN_STORAGE_FOLDER + path + "\\" + fileName);
        return file.delete();
    }

    /**
     * @deprecated 
     * @param path
     * @return 
     */
    public boolean deleteFolder(String path) {
        path = MAIN_STORAGE_FOLDER + path;
        return this.deleteFolder(new File(path));
    }

    /**
     * @deprecated 
     * @param directory
     * @return 
     */
    private boolean deleteFolder(File directory) {

        File[] files = directory.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        return directory.delete();
    }
}
