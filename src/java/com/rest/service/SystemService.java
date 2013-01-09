package com.rest.service;

import com.rest.controller.ErrorsController;
import com.rest.controller.SystemController;
import com.rest.model.Folder;
import com.rest.model.User;
import com.rest.model.UserFile;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * System service class.
 *
 * @author Zbyszko
 */
@Path("/systemService")
@Singleton
public class SystemService {

    SystemController systemController = new SystemController();

    @GET
    @Path("/createSpace")
    public Response createSpace(@Context SecurityContext sec) {

        boolean isCreated = systemController.createSpace(sec.getUserPrincipal().getName());

        if (isCreated) {
            return Response.ok().entity("isCreated: " + isCreated).build();
        }
        return Response.ok().entity("Error: " + ErrorsController.SPACE_ALREADY_CREATED).build();
    }

    @POST
    @Path("/createFolder")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createFolder(@FormParam("folderName") String folderName, @Context HttpServletRequest request,
    @Context SecurityContext sec) {
        
        String login = sec.getUserPrincipal().getName();

        boolean isCreated = systemController.createFolder(
                folderName, login);
        
        String path = login +  "//" + folderName;
        
        if (isCreated) {
            Long folderId = systemController.addFolderInfoToDB(folderName, path);
            systemController.joinFolderAndUser(folderId, login, 1);
            try{
                return Response.seeOther(new URI("../")).build();                 
            } catch(Exception ex){
                return Response.ok().entity("Created").build();
            }
        }
        return Response.ok().entity("Error: " + ErrorsController.FOLDER_ALREADY_EXISTS).build();
    }

    @POST
    @Path("/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@FormDataParam("file") InputStream in, 
        @FormDataParam("file") FormDataContentDisposition info, 
        @FormDataParam("tag") String tag, @FormDataParam("path") String path,
        @Context HttpServletRequest request,
        @Context SecurityContext sec) {

        String userlogin = sec.getUserPrincipal().getName();
        String folderName = path;
        path = userlogin + "//" + folderName; 
        //TODO: sprawdzic czy nie folder usera nie przekracza max. pojemności!!!1
        
        if(userlogin==null){
            return Response.serverError().build();
        }
        tag = tag.toUpperCase();            
        Long fileSize = systemController.uploadFile(in, info.getFileName(), path, userlogin);
        
        if (fileSize== -1) {
            return Response.ok().entity("Error: " + ErrorsController.UPLOAD_ERROR).build();
        }
        
        // zapisanie informacji o pliku w bazie danych
        Long fileId = systemController.addFileInfoToDB(info.getFileName(), fileSize, tag, path);
        // zapisanie informacji o właścicielu pliku
        systemController.joinFileAndFolder(fileId, folderName);

        try{
            List<Folder> folders = (List<Folder>)request.getSession().getAttribute("folders");
            if(folders!=null){
                    for(Folder f : folders){
                        if(info.getFileName().equals(f.getName())){
                            System.out.println("URI: "+new URI("rest/home/"+f.getId()));
                            return Response.seeOther(new URI("rest/home/"+f.getId())).build();                            
                        }
                    }
                return Response.seeOther(new URI("../")).build();
                }
        } catch(Exception ex){
            try{
                return Response.seeOther(new URI("../")).build();
            } catch(Exception e){
                System.out.println("Exceptions everywhere: "+e.getMessage());
            }
        }
        return Response.ok().entity("File is up.").build();
    }

    /**
     * 
     * @param filePath tylko nazwa folderu!!!
     * @param shareLogin
     * @param sec
     * @return 
     */
    @POST
    @Path("/shareFolder")
    public Response shareFile(@FormParam("filePath") String filePath, @FormParam("shareLogin") String shareLogin, @Context SecurityContext sec) {
        
        //System.out.println("\n ----->" + filePath + " " + shareLogin);
        Long userId = systemController.getUserId(sec.getUserPrincipal().getName());
        long folderId = systemController.getFolderId(filePath,userId);
//        filePath = sec.getUserPrincipal().getName() + "//" + filePath;
        if(folderId!=0){
            boolean isOwner = systemController.isFolderOwner(folderId, sec.getUserPrincipal().getName());  
            if(isOwner){
                systemController.joinFolderAndUser(folderId, shareLogin, 0);
            }
            else {
                return Response.ok().entity("You are not owner of this folder").build();
            }
        } else {
            return Response.ok().entity("Cannot get folder id").build();
        }    
        return Response.ok().entity("Folder(s) has been shared.").build();
    }
    
    @POST
    @Path("/unshareFolder")
     public Response unshareFile(@FormParam("filePath") String filePath, @Context SecurityContext sec){
        Long userId = systemController.getUserId(sec.getUserPrincipal().getName());
        long folderId = systemController.getFolderId(filePath,userId);
        long folderId = systemController.getFolderId(userLogin + "//" + filePath);
        if(folderId!=0){
            boolean isOwner = systemController.isFolderOwner(folderId, userLogin);    
            if(isOwner){
                systemController.disjoinFolderAndNoOwnerUsers(folderId, userLogin);
            }
            else {
                return Response.ok().entity("user is not owner of this file").build();
            }
        } else {
            return Response.ok().entity("cannot get folder id").build();
        }    
        return Response.ok().entity("Folder has been UNSHARED.").build();
    }   
    
    @POST
    @Path("/search")
    @Produces(MediaType.TEXT_PLAIN)
    public Response search(@FormParam("searchPhrase") String phrase){
        
        ArrayList<UserFile> results = systemController.search(phrase);
        return Response.ok().entity("Results: \n" + results.toString()).build();
    } 
    
    @GET
    @Path("/downloadFile/{path}/{fileName}")
    public File downloadFile(@PathParam("path") String path, @PathParam("fileName") String fileName, @Context SecurityContext sec){
        System.out.println("DOWNLOAD_FILE");
        String login = sec.getUserPrincipal().getName();
        boolean hasPermision = systemController.canBeDownloaded(path, login);
        if(hasPermision) {
            System.out.println("Has permission");
            return systemController.getDirectFilePath(login, path, fileName);
        }
        return null;
    }
    
    @GET
    @Path("/deleteFolder/{folderPath}")
    public Response deleteFolder(@PathParam("folderPath") String path, @Context SecurityContext sec){
        path = sec.getUserPrincipal().getName() + "//" + path;
        
        
        boolean isDeleted = systemController.deleteFolderFromDB(path, sec.getUserPrincipal().getName());
        if(!isDeleted){
            return Response.ok().entity(ErrorsController.DELETION_ERROR).build();
        }
        isDeleted = systemController.deleteFolder(path);
        
        if(!isDeleted){
            return Response.ok().entity(ErrorsController.DELETION_ERROR).build();
        }
        
        return Response.ok().entity("ok").build();
    }
    
    @GET
    @Path("/deleteFile/{folderPath}/{fileName}")
    public Response deleteFile(@PathParam("folderPath") String path, @PathParam("fileName") String fileName, @Context SecurityContext sec){

        String login = sec.getUserPrincipal().getName();
        path = login + "//" + path; 
        
        boolean isDeleted = systemController.deleteFileFromDB(path, fileName, login);
        System.out.println("check: " + isDeleted);
        if(!isDeleted) {
            return Response.ok().entity(ErrorsController.DELETION_ERROR).build();
        }
        isDeleted = systemController.deleteFile(path, fileName);
        
        if(!isDeleted){
            return Response.ok().entity(ErrorsController.DELETION_ERROR).build();
        }
        
        return Response.ok().entity("ok").build();
    }
    
    @GET
    @Path("/getRemainingStorageSize")
    @Produces("text/plain")
    public Response getRemainingStorageSize(@Context SecurityContext sec) {
        
        try{
            long folderSize = systemController.getFolderSize(sec.getUserPrincipal().getName());
            long availableSpace = SystemController.MAX_STORAGE - folderSize;
            Double availableSpaceD = availableSpace / 1000.0;            
            System.out.println("AVAILABLE: "+folderSize+" - "+availableSpace+" - "+availableSpaceD);
            
            return Response.ok().entity(Double.toString(availableSpaceD)).build();
        } catch(Exception e){
            return Response.ok().entity("0").build();
        }
    }
    
    @GET
    @Path("/myfolders")
    @Produces(MediaType.APPLICATION_XML) 
    public List<Folder> getCurrentUserFolders(@Context SecurityContext sec){
        List<Folder> folders = new ArrayList<>(); 
        System.out.println("SEC: "+sec.getUserPrincipal().getName());
        User user = systemController.getUser(sec.getUserPrincipal().getName(), null);
        folders.addAll(systemController.getUserFolders(user));
//        folders.addAll(systemController.getUserFolders(user,false));
//        for(Folder f : folders){
//            System.out.println("NAME: "+f.getName()+" - "+f.getDateStamp()+" - "+f.getDirectPath());
//            System.out.println("FOL - USER: "+f.getId()+" - "+f.getUser().getLogin()+" - "+f.getUser().getUid());
//            for(UserFile file : f.getFiles()){
//                System.out.println("FILE: "+file.getFileName());
//            }
//            for(User u : f.getShared()){
//                System.out.println("SHUSER: "+u.getLogin()+" - "+u.getUid());
//            }
//        }
        System.out.println("MY FOLDERS "+folders.size());
        return folders;
    }
    
    @GET
    @Path("/myfolderss")
    @Produces(MediaType.APPLICATION_XML) 
    public String testGet(@Context SecurityContext sec){
        
        return "bourne";
    }
    
}
