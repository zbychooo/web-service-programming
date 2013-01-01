package com.rest.service;

import com.rest.client.SystemClient;
import com.rest.controller.UsersController;
import com.rest.model.User;
import com.sun.jersey.spi.resource.Singleton;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Atuan
 */
@Singleton
@Path("/")
public class ClientService {
    
    private SystemClient systemClient;
    
    @GET
    @Path("/home/{cfi}")
    @Produces("text/html")
    public Response index1(@PathParam("cfi") String currentFolderIndex, @Context HttpServletRequest request, @Context SecurityContext sec) {
        //initialize model
        Map<String, Object> map = new HashMap<String, Object>();
        //add stuff necessary for the page to display correctly
        SystemService ss = new SystemService();
//        if(sec.getUserPrincipal() == null){
//            return Response.ok(new Viewable("/login")).build();
//        }
        Response temp = ss.getRemainingStorageSize(sec);
        System.out.println("actually inside the method "+(String)temp.getEntity());
        map.put("remainingSpace", (String)temp.getEntity());
        try{
            UsersController uc = new UsersController();
            User currentUser = (User)uc.getUsers().get(sec.getUserPrincipal().getName());
            request.getSession().setAttribute("user", currentUser);
            request.getSession().setAttribute("remainingSpace", (String)temp.getEntity());
            System.out.println("request is working :| ");
            systemClient = new SystemClient(currentUser);
            request.getSession().setAttribute("folders", systemClient.getFolderList());
            System.out.println("before cfi casting");
            request.getSession().setAttribute("currentFolderIndex", Long.valueOf(currentFolderIndex));
            System.out.println("request2");
        } catch(Exception e){
            System.out.println("request ain't workin'.");
        }
        //return a page
        try{
            return Response.temporaryRedirect(new URI("../home.jsp")).build();
        } catch(Exception e){
            return Response.ok(":(").build();
        }
    }
}
