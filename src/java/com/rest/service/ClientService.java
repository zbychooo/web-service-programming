package com.rest.service;

import com.rest.client.SystemClient;
import com.rest.controller.UsersController;
import com.rest.model.Folder;
import com.rest.model.User;
import com.sun.jersey.spi.resource.Singleton;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        //add stuff necessary for the page to display correctly
        SystemService ss = new SystemService();
        Response temp = ss.getRemainingStorageSize(sec);
        try{
            UsersController uc = new UsersController();
            User currentUser = (User)uc.getUsers().get(sec.getUserPrincipal().getName());
//            request.getSession().setAttribute("user", currentUser);
            request.getSession().setAttribute("remainingSpace", (String)temp.getEntity());
            System.out.println("request is working :| ");
            systemClient = new SystemClient(currentUser);
            List<Folder> folders = new ArrayList<>();
            folders.addAll(systemClient.getFolderList());
            request.getSession().setAttribute("folders", folders);
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
    
    @GET
    @Path("/myfolders/{mode}")
    @Produces("text/html")
    public Response index(@PathParam("mode") String mode, @Context HttpServletRequest request, @Context SecurityContext sec) {
        SystemService ss = new SystemService();
        Response temp = ss.getRemainingStorageSize(sec);
        try{
            UsersController uc = new UsersController();
            User currentUser = (User)uc.getUsers().get(sec.getUserPrincipal().getName());
            request.getSession().setAttribute("user", currentUser);
            request.getSession().setAttribute("remainingSpace", (String)temp.getEntity());
            systemClient = new SystemClient(currentUser);
            List<Folder> folders = new ArrayList<>();
            
            for(Folder f : systemClient.getFolderList()){
                if(mode.equals("all")){
                    folders.add(f);
                } else if(mode.equals("shared")){
                    if(!f.getShared().isEmpty()){
                        folders.add(f);
                    }
                }
            }
            request.getSession().setAttribute("folders", folders);
            System.out.println("after folders");
        } catch(Exception e){
            System.out.println("request ain't workin'.");
        }
        //return a page
        try{
            return Response.temporaryRedirect(new URI("../myfolders.jsp")).build();
        } catch(Exception e){
            return Response.ok(":(").build();
        }
    }
}
