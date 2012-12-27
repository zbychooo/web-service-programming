package com.rest.controller;

import com.rest.model.User;
import com.utilities.DBConnector;
import com.utilities.SHA;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for users. 
 * @author Zbyszko
 */
public class UsersController {

    private Map<String, User> users;
    private Long lastId;
    
    /**
     * Inits user controller.
     */
    public UsersController() {
        try {
            users = new HashMap<>();
            DBConnector db = new DBConnector();
            
               PreparedStatement statement = db.getConnection().prepareStatement("select * from users");
               ResultSet rs = statement.executeQuery();
               while(rs.next()) {
                   users.put(
                           rs.getString("login"), 
                           new User(rs.getLong("id"), rs.getString("login"), 
                                rs.getString("password"), rs.getString("username"))
                           );
                   lastId = rs.getLong("id");
               }
               rs.close();
               statement.close();
               db.closeConnection();
               
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Verify the data given by the user in form.
     * @param login
     * @param password
     * @param confirmPassword
     * @param username
     * @return {String} Informs about data correctness.
     */
    public String checkUser(String login, String password, 
            String confirmPassword, String username) {

        if(!password.equals(confirmPassword)) {
            return ErrorsController.INCONSISTENT_PASSWORDS;
        }
        if (login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
            return ErrorsController.EMPTY_FIELDS;
        }

        if (!login.matches("[a-zA-Z0-9]{4,25}")) {
            return ErrorsController.IMPROPER_STRING_LENGTH_OR_FORMAT + login;
        }

        if (!username.matches("[a-zA-Z]+")) {
            return ErrorsController.ONLY_LETTERS_ALLOWED + username;
        }
        
        if(users.containsKey(login)) {
            return ErrorsController.LOGIN_TAKEN;
        }

        return "OK";
    }
    
    /**
     * Add new user to database and users hashmap
     * @param login
     * @param password
     * @param username 
     */
    public void add(String login, String password,String username) {
        
        password = SHA.encrypt(password);
        try {
            lastId += 1;
            User user = new User(lastId, login, password, username);
            users.put(login, user);
            
            DBConnector db = new DBConnector();
            
            String sqlQuery = "insert into users(login, password, username) values(?,?,?)";
            try(PreparedStatement statement = db.getConnection().prepareStatement(sqlQuery)){
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getUsername());
                statement.executeUpdate();
                statement.close();
            }
            db.closeConnection();
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String verifyCredentials(String login, String password) {
        password = SHA.encrypt(password);
        
        User user = users.get(login);
        if((user == null) || (!user.getPassword().equals(password))){
            return ErrorsController.INVALID_CREDENTIALS;
        }
        return "OK";
    }
}
