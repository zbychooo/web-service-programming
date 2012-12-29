/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

import java.io.File;

/**
 * System Controller class.
 *
 * @author Zbyszko
 */
public class SystemController {

    private static final int MAX_STORAGE = 10;
    private static final String MAIN_STORAGE_FOLDER = "D:\\RESTCloudStorage\\";

    public SystemController() {
    }

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
     * @return
     */
    public long getFolderSize(String directoryName) {

        File directory = new File(MAIN_STORAGE_FOLDER + directoryName);
        return this.calculateFolderSize(directory);
    }

    private long calculateFolderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += calculateFolderSize(file);
            }
        }
        return length/(1024); //in MB
    }

//    public void deleteFolder(File directory) {
//        File[] files = directory.listFiles();
//        if (files != null) { //some JVMs return null for empty dirs
//            for (File f : files) {
//                if (f.isDirectory()) {
//                    deleteFolder(f);
//                } else {
//                    f.delete();
//                }
//            }
//        }
//        directory.delete();
//    }
}
