package com.rest.service;

import com.rest.controller.SystemController;
import com.rest.controller.UsersController;
import com.sun.jersey.api.JResponse;
import com.sun.jersey.spi.resource.Singleton;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * User Service class.
 *
 * @author Zbyszko
 */
@Path("/userService")
@Singleton
public class UserService {

    UsersController usersController = new UsersController();
    SystemController systemController = new SystemController();
    
    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(@FormParam("login") String login,
            @FormParam("password") String password,
            @FormParam("confirm_password") String confirmPassword,
            @FormParam("username") String username) {
        
        
        String returnMessage = usersController.checkUser(login, password, confirmPassword, username);
        System.out.println("hello: " + returnMessage);
        if (returnMessage.equals("OK")) {
            usersController.add(login, password, username);
            systemController.createSpace(login);
        }
        try{
            return Response.seeOther(new URI("../index.jsp")).build();
        } catch(Exception e){
            return Response.ok().entity("System return message: " + returnMessage).build();
        }
    }

    @GET
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginUser(@Context SecurityContext sec) {
        String out = "Hello " + sec.getUserPrincipal().getName()
                + ", is secure? " + sec.isSecure();
        System.out.println(out);
        
        return Response.status(Response.Status.OK).entity("OK").build();
    }
    
    @GET
    @Path("/getUserLogin")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUserLogin(@Context SecurityContext sec){
        return Response.ok().entity(sec.getUserPrincipal().getName()).build();
    }
    
    @GET
    @Path("/logout")
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@Context HttpServletRequest request, @Context HttpServletResponse response, @Context SecurityContext sec){        
        try{
            request.logout();         
            response.sendRedirect("../../");
            return "";
        } catch(Exception e){
            return "";
        }
    }
}
