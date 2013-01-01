package com.rest.service;

import com.rest.controller.ErrorsController;
import com.rest.controller.SystemController;
import com.rest.model.Folder;
import com.rest.model.User;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;
import java.io.InputStream;
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
    public Response createFolder(@FormParam("foldername") String folderName, @Context SecurityContext sec) {

        //dziala, ale trzeba sie zalogowac...
        boolean isCreated = systemController.createFolder(
                folderName,
                sec.getUserPrincipal().getName());

        if (isCreated) {
            return Response.ok().entity("isCreated: " + isCreated).build();
        }
        return Response.ok().entity("Error: " + ErrorsController.FOLDER_ALREADY_EXISTS).build();
    }

    @POST
    @Path("/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@FormDataParam("file") InputStream in, 
        @FormDataParam("file") FormDataContentDisposition info, 
        @FormDataParam("tags") String tags, @FormDataParam("path") String path,
        @Context SecurityContext sec) {

        path = ""; //TODO: zmienic!!!
        String userlogin = sec.getUserPrincipal().getName();
        if(userlogin==null){
            return Response.serverError().build();
        }
        tags = tags.toUpperCase();            
        boolean isUploaded = systemController.uploadFile(in, info.getFileName(), path, userlogin);
        
        if (!isUploaded) {
            return Response.ok().entity("Error: " + ErrorsController.UPLOAD_ERROR).build();
        }
        
        // zapisanie informacji o pliku w bazie danych
        long fileId = systemController.addFileInfoToDB(info.getFileName(), info.getSize(), tags);
        // zapisanie informacji o właścicielu pliku
        systemController.joinFileAndOwner(fileId, userlogin);

        return Response.ok().entity("File is up.").build();
    }

    @GET
    @Path("/get/{resource}")
    public Response getUserFileList(@PathParam("resource") String resource){
        //get files by tag or get users files
        return null;
    }
        
    
    @GET
    @Path("/getAvailableStorageSize")
    @Produces("text/plain")
    public Response getAvailableStorageSize(@Context SecurityContext sec) {
        
        long folderSize = systemController.getFolderSize(sec.getUserPrincipal().getName());
        long availableSpace = SystemController.MAX_STORAGE - folderSize;
        
        String outMessage = "Main folder size: " + folderSize + "kb \n" +
                "Max: " + SystemController.MAX_STORAGE + "kb \n" +
                "Available: " + availableSpace + "kb.";
        
        return Response.ok().entity(outMessage).build();
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
        
        List<com.rest.model.File> files2 = new ArrayList<>();
        List<String> tags2 = new ArrayList<>();
        tags2.add("TV-Series");
        tags2.add("Favorites");
        files2.add(new com.rest.model.File(Long.valueOf(1), "S01E01.avi", new Date(),Long.valueOf(351310000),tags2));        
        files2.add(new com.rest.model.File(Long.valueOf(2), "S01E02.avi", new Date(),Long.valueOf(350610000),tags2));        
        files2.add(new com.rest.model.File(Long.valueOf(3), "S01E03.avi", new Date(),Long.valueOf(351610070),tags2));
        folders.add(new Folder(Long.valueOf(177),
                new User(Long.valueOf(10),"seba1","","Seba","user"),"The Vampire Diaries",true,files2));
        
        List<com.rest.model.File> files1 = new ArrayList<>();
        files1.add(new com.rest.model.File(Long.valueOf(1), "01 3 Hours.mp3", new Date(),Long.valueOf(7310000),new ArrayList<String>()));
        List<String> tags = new ArrayList<>();
        tags.add("MUSIC");
        files1.add(new com.rest.model.File(Long.valueOf(2), "01 Common exchange.mp3", new Date(),Long.valueOf(6610000),tags));
        folders.add(new Folder(Long.valueOf(173),
                new User(Long.valueOf(9),"seba","seba","Seba","user"),"folderek",false,files1));
        
        folders.add(new Folder(Long.valueOf(174),new User(),"kolejny glupi rok",true));
        
//        ListResponse listResponse = new ListResponse(folders);
//        GenericEntity entity = new GenericEntity<List<Folder>>(folders) {};
        System.out.println("MY FOLDERS "+folders.size());
        return folders;
//        return Response.ok(folders, MediaType.APPLICATION_XML).build();
//        return Response.ok(folders.get(0), MediaType.APPLICATION_XML).build();
    }
    
}
