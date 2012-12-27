package com.rest.controller;

/**
 *
 * @author Zbyszko
 */
public class UsersController {
    
    static public String checkUser(String login, String password, 
            String confirmPassword, String username){
        
        if(!password.equals(confirmPassword)) {
            return ErrorsController.INCONSISTENT_PASSWORDS;
        }
        
        
        
        return "OK";
    }
}
