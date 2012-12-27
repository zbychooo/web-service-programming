/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.controller;

/**
 *
 * @author Zbyszko
 */
public class UserController {
    
    static public int checkUser(String login, String password, 
            String confirmPassword, String username){
        
        if(!password.equals(confirmPassword)) {
            //password are not the same
            return -1;
        }
        
        
        
        return 0;
    }
}
