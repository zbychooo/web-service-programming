package com.rest.service;

import com.rest.controller.ErrorsController;
import com.rest.controller.SystemController;
import com.rest.model.Folder;
import com.rest.model.User;
import com.rest.model.UserFile;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public Response createFolder(@FormParam("folderName") String folderName, @Context SecurityContext sec) {

        String login = sec.getUserPrincipal().getName();

        boolean isCreated = systemController.createFolder(
                folderName, login);
        
        String path = login +  "//" + folderName;
        if (isCreated) {
            Long folderId = systemController.addFolderInfoToDB(folderName, path);
            systemController.joinFolderAndUser(folderId, login, 1);
            
            return Response.ok().entity("isCreated: " + isCreated).build();
        }
        return Response.ok().entity("Error: " + ErrorsController.FOLDER_ALREADY_EXISTS).build();
    }

    @POST
    @Path("/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@FormDataParam("file") InputStream in, 
        @FormDataParam("file") FormDataContentDisposition info, 
        @FormDataParam("tag") String tag, @FormDataParam("path") String path,
        @Context SecurityContext sec) {

        String userlogin = sec.getUserPrincipal().getName();
        path = userlogin + "//" + path; 
        
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
        systemController.joinFileAndFolder(fileId, path);

        return Response.ok().entity("File is up.").build();
    }

    @POST
    @Path("/shareFolder")
    public Response shareFile(@FormParam("filePath") String filePath, @FormParam("shareLogin") String shareLogin, @Context SecurityContext sec) {
        //check if is owner
        //join user and folder
        //systemController.joinFolderAndUser(Long.MIN_VALUE, filePath, 0);
        
        return null;
    }
    
    @GET
    @Path("/unshareFolder/{filePath}")
     public Response unshareFile(@PathParam("filePath") String filePath){
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
            
            return Response.ok().entity(Double.toString(availableSpaceD)).build();
        } catch(Exception e){
            return Response.ok().entity("0").build();
        }
    }
    
    @GET
    @Path("/myfolders")
    @Produces(MediaType.APPLICATION_XML) 
//    public Response getCurrentUserFolders(@Context SecurityContext sec){
    public List<Folder> getCurrentUserFolders(@Context SecurityContext sec){
        List<Folder> folders = new ArrayList<>(); 
        
        List<UserFile> files2 = new ArrayList<>();
//        List<String> tags2 = new ArrayList<>();
//        tags2.add("TV-Series");
//        tags2.add("Favorites");
        Date date = new java.util.Date();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate = dateFormat.format(date);
        
        files2.add(new UserFile(Long.valueOf(1), "S01E01.avi", Long.valueOf(351310000),currentDate,"TV-Series",""));        
        files2.add(new UserFile(Long.valueOf(2), "S01E02.avi", Long.valueOf(350610000),currentDate,"TV-Series",""));        
        files2.add(new UserFile(Long.valueOf(3), "S01E03.avi", Long.valueOf(351610070),currentDate,"TV-Series",""));
        folders.add(new Folder(Long.valueOf(177),
                new User(Long.valueOf(10),"seba1","","Seba","user"),"The Vampire Diaries",new ArrayList<User>(),files2));
        
        List<UserFile> files1 = new ArrayList<>();
        files1.add(new UserFile(Long.valueOf(1), "01 3 Hours.mp3",Long.valueOf(7310000),currentDate,"Music",""));
        
        files1.add(new UserFile(Long.valueOf(2), "01 Common exchange.mp3",Long.valueOf(6610000),currentDate,"Music",""));
        folders.add(new Folder(Long.valueOf(173),
                new User(Long.valueOf(9),"seba","seba","Seba","user"),"folderek",new ArrayList<User>(),files1));
        
        folders.add(new Folder(Long.valueOf(174),new User(),"kolejny glupi rok",new ArrayList<User>()));
        
        folders.addAll(systemController.getUserFolders(new User(Long.valueOf(10),"grimm","grimm","Grimm","user")));
        
//        ListResponse listResponse = new ListResponse(folders);
//        GenericEntity entity = new GenericEntity<List<Folder>>(folders) {};
        System.out.println("MY FOLDERS "+folders.size());
        return folders;
//        return Response.ok(folders, MediaType.APPLICATION_XML).build();
//        return Response.ok(folders.get(0), MediaType.APPLICATION_XML).build();
    }
    
}
