package com.rest.controller;

import com.rest.model.Folder;
import com.rest.model.User;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private Long getUserId(String login) {

        long userID = -1;
        try {
            DBConnector db = new DBConnector();
            String sqlQuery = "select id from users where login='" + login + "'";

            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    userID = rs.getLong(1);
                }
                statement.close();
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
                statement.close();
            }
            db.closeConnection();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public void joinFileAndFolder(long fileID, String path) {

        long folderID = 0;
        try {
            DBConnector db = new DBConnector();
            String sqlQuery = "select id from folders where directPath='" + path + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    folderID = rs.getLong(1);
                }
                statement.close();
            }

            String sqlQuery2 = "insert into folders_files(fileId, folderId) values(?,?)";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery2)) {
                statement.setLong(1, fileID);
                statement.setLong(2, folderID);
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
            String sqlQuery = "insert into users_folders(userId, folderId, isOwner) values(?,?,?)";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                statement.setLong(1, userID);
                statement.setLong(2, folderID);
                statement.setLong(3, 1);
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
        int isOwner = 0;
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
                statement.close();
            }

            //checks onwership:
            userID = getUserId(login);
            System.out.println("userid: " + userID + " fileID: " + fileID);
            long folderID = 0;
            sqlQuery = "select id from folders where directPath='" + path + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    folderID = rs.getInt(1);
                }
                statement.close();
            }

            sqlQuery = "select isOwner from users_folders where userId='" + userID + "' and folderId='" + folderID + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    isOwner = rs.getInt(1);
                }
                statement.close();
            }

            System.out.println("isOwner: " + isOwner);
            if (isOwner == 0) {
                return false;
            }

            //System.out.println("DELETE: fileID " + fileID + "\npath: " + path + "\nfilename: " + fileName);
            sqlQuery = "delete from files where fileName='" + fileName + "' and directPath='" + path + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                statement.executeUpdate();
                statement.close();
            }

            sqlQuery = "delete from folders_files where fileId='" + fileID + "'";
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                statement.executeUpdate();
                statement.close();
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

    public boolean deleteFolder(String path) {
        path = MAIN_STORAGE_FOLDER + path;
        return this.deleteFolder(new File(path));
    }

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

    public List<Folder> getUserFolders(User user) {
        List<Folder> folders = new ArrayList<>();
        try {
            DBConnector db = new DBConnector();
//            
            //get from folders_sers the connections
            //store them all
            //get the folder objects from those 
            String query = "select * from folders_users where userId=?";
            try (PreparedStatement statement = db.getConnection().prepareStatement(query)) {
                statement.setLong(1, user.getUid());
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    long fid = rs.getLong("folderId");
                    Boolean owner = rs.getBoolean("isOwner");
                    if (owner) {
                        try (PreparedStatement statement1 = db.getConnection().prepareStatement("select * from folders where id=?")) {
                            statement1.setLong(1, fid);
                            ResultSet rs1 = statement1.executeQuery();
                            while (rs1.next()) {
                                Folder currentFolder = new Folder(rs1.getLong("id"), user, rs1.getString("name"),
                                        rs1.getString("dateStamp"), rs1.getString("directPath"));
                                //get users that can read this folder
                                try (PreparedStatement statement2 = db.getConnection().prepareStatement("select * from folders_users where folderId=?")) {
                                    statement2.setLong(1, fid);
                                    ResultSet rs2 = statement2.executeQuery();
                                    List<User> sharingUsers = new ArrayList<>();
                                    while (rs2.next()) {
                                        sharingUsers.add(getUser(rs2.getLong("userId"), db));
                                    }
                                    currentFolder.setShared(sharingUsers);
                                }
                                //finally add to the list
                                folders.add(currentFolder);
                            }
                        }
                    }
                }
                rs.close();
            }

            db.closeConnection();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return folders;
    }

    private User getUser(Long uid, DBConnector db) {
        User user = null;
        String sqlQuery = "select * from users where login=?";
        try {
            try (PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)) {
                statement.setLong(1, uid);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    user = new User(uid, rs.getString("login"), rs.getString("password"), rs.getString("username"), rs.getString("role"));
                }
                rs.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
