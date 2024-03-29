package com.rest.service;

import com.rest.client.SystemClient;
import com.rest.controller.UsersController;
import com.rest.model.Folder;
import com.rest.model.SearchResultEntry;
import com.rest.model.User;
import com.rest.model.UserFile;
import com.sun.jersey.spi.resource.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
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
    public Response index1(@PathParam("cfi") String currentFolderIndex, @Context HttpServletRequest request, 
    @Context HttpServletResponse response, @Context SecurityContext sec) {
        try{
            UsersController uc = new UsersController();       
            User currentUser = (User)uc.getUsers().get(sec.getUserPrincipal().getName()); 
            request.getSession().setAttribute("user", currentUser);  
            
            systemClient = new SystemClient(currentUser,request,response);
            String userRemainingSpace = systemClient.getUserRemainingSpace();
            request.getSession().setAttribute("remainingSpace", userRemainingSpace);
            
            List<Folder> folders = new ArrayList<>();
//            folders.addAll((List<Folder>)request.getSession().getAttribute("folders"));
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
            return Response.seeOther(new URI("../home.jsp")).build();
        } catch(Exception e){
            return Response.ok(":(").build();
        }
    }
    
    @GET
    @Path("/myfolders/{mode}")
    @Produces("text/html")
    public Response index(@PathParam("mode") String mode, @Context HttpServletRequest request, 
    @Context HttpServletResponse response, @Context SecurityContext sec) {
        try{
            UsersController uc = new UsersController();       
            User currentUser = (User)uc.getUsers().get(sec.getUserPrincipal().getName()); 
            request.getSession().setAttribute("user", currentUser);       
            
            systemClient = new SystemClient(currentUser,request,response);   
            String userRemainingSpace = systemClient.getUserRemainingSpace();
            request.getSession().setAttribute("remainingSpace", userRemainingSpace);
            
            List<Folder> folders = new ArrayList<>();            
            System.out.println("before folders");
            folders.addAll(systemClient.getFolderList());
            request.getSession().setAttribute("folders", folders);
            
            List<Folder> foldersDM = new ArrayList<>(); 
            for(Folder f : folders){
                if(f.getUser().getLogin().equals(currentUser.getLogin())){
                    if(mode.equals("all")){
                        foldersDM.add(f);
                    } else if(mode.equals("shared")){
                        if(!f.getShared().isEmpty()){
                            foldersDM.add(f);
                        }
                    }
                } else {
                    if(mode.equals("other")){
                        foldersDM.add(f);
                    }
                }
            }
            request.getSession().setAttribute("foldersdm", foldersDM);
            System.out.println("after folders");
        } catch(Exception e){
            System.out.println("request ain't workin' "+e.getMessage());
        }
        //return a page
        try{
            return Response.seeOther(new URI("../myfolders.jsp")).build();
        } catch(Exception e){
            return Response.ok(":(").build();
        }
    }
    
    @GET
    @Path("/download/{login}/{filePath}/{fileName}")
    public Response download(@PathParam("login") String login, @PathParam("filePath") String filePath, @PathParam("fileName") String fileName, @Context HttpServletRequest request, 
        @Context HttpServletResponse response, @Context SecurityContext sec){
        
        try{
            UsersController uc = new UsersController();       
            User currentUser = (User)uc.getUsers().get(sec.getUserPrincipal().getName()); 
            request.getSession().setAttribute("user", currentUser);       
            
            systemClient = new SystemClient(currentUser,request,response);   
            File file = systemClient.downloadFile(login,filePath,fileName);
            
            if(file == null){
                System.out.println("FILE is null");
            } else {
                return Response.ok().entity(new FileInputStream(file)).build();
//                request.getSession().setAttribute("file", file);
            }
        } catch(Exception e){
            
        }
        
        return null;
    }
    
    @POST
    @Path("/search")
    public Response search(@FormParam("searchPhrase") String searchPhrase, @FormParam("searchby") String searchby,
    @Context HttpServletRequest request, @Context HttpServletResponse response,@Context SecurityContext sec){
                
        try{
            UsersController uc = new UsersController();       
            User currentUser = (User)uc.getUsers().get(sec.getUserPrincipal().getName()); 
            SystemClient sc = new SystemClient(currentUser,request,response);
            List<SearchResultEntry> result = new ArrayList<>();
            result.addAll(sc.search(searchPhrase, searchby));
            request.getSession().setAttribute("searchResults", result);
            System.out.println("Search in CLientService "+result.size());
            return Response.seeOther(new URI("../search.jsp")).build();
        } catch(Exception e){
            System.out.println("Error in Search: "+e.getMessage());
            return Response.serverError().build();
        }
    }
}
