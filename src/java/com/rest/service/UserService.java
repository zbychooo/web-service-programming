/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.service;

import com.rest.controller.UsersController;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Zbyszko
 */
public class UserService {

    @POST
    @Path("/register")
    public Response addUser(@FormParam("login") String login, 
                            @FormParam("password") String password,
                            @FormParam("confirm_password") String confirmPassword,
                            @FormParam("username") String username) {
        
        String returnMessage = UsersController.checkUser(login, password, confirmPassword, username);
        
        return null;
    }
}
