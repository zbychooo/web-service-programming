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
        
        if(login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()){
            return ErrorsController.EMPTY_FIELDS;
        }
        
        if(!login.matches("[a-zA-Z0-9]{3,20}")) {
            return ErrorsController.IMPROPER_STRING_LENGTH_OR_FORMAT;
        }
        
        if(!username.matches("[a-zA-Z]+")) {
            return ErrorsController.ONLY_LETTERS_ALLOWED;
        }
        
        
        
        return "OK";
    }
}
