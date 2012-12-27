package com.rest.service;

import com.rest.controller.UsersController;
import com.sun.jersey.spi.resource.Singleton;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Zbyszko
 */
@Path("/userService")
@Singleton
public class UserService {

    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(@FormParam("login") String login,
            @FormParam("password") String password,
            @FormParam("confirm_password") String confirmPassword,
            @FormParam("username") String username) {
 
        UsersController usersController = new UsersController();
        String returnMessage = usersController.checkUser(login, password, confirmPassword, username);

        if(returnMessage.equals("OK")) {
            usersController.add(login, password, username);
        }
        return Response.ok().entity("System return message: " + returnMessage).build();
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginUser(@FormParam("login") String login, 
            @FormParam("password") String password) {
        
        UsersController usersController = new UsersController();
        usersController.verifyCredentials(login, password);
        
        return null;
    }
}
