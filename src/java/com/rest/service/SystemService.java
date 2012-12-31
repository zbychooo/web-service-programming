package com.rest.service;

import com.rest.controller.ErrorsController;
import com.rest.controller.SystemController;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * System service class.
 *
 * @author Zbyszko
 */
@Path("/systemService")
@Singleton
public class SystemService {

    SystemController systemController = new SystemController();
    
    //------------------- [FOLDERS] --------------------------------------------
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


        boolean isCreated = systemController.createFolder(
                folderName,
                sec.getUserPrincipal().getName());

        if (isCreated) {
            long dirId = systemController.addFileInfoToDB(folderName, 0, "-");
            systemController.joinFileAndOwner(dirId, sec.getUserPrincipal().getName());
            return Response.ok().entity("isCreated: " + isCreated).build();
        }

        return Response.ok().entity("Error: " + ErrorsController.FOLDER_ALREADY_EXISTS).build();
    }

    @GET
    @Path("/openFolder/{directoryName}")
    @Produces(MediaType.TEXT_HTML)
    public Response openFolder(@PathParam("directoryName") String directoryName) {
        
        return Response.ok().entity("<h1>AAA</h1> <p> bbbb</p>").build();
    }

    @GET
    @Path("/deleteFolder/{directoryName}")
    public Response deleteFolder(@PathParam("directoryName") String directoryName) {
        return null;
    }

    //--------------------- [FILES] ---------------------------
    @POST
    @Path("/uploadFile")
    @Produces("text/plain")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@FormDataParam("file") InputStream in,
            @FormDataParam("file") FormDataContentDisposition info,
            @FormDataParam("tags") String tags, @FormDataParam("path") String path,
            @Context SecurityContext sec) {

        path = ""; //TODO: zmienic!!!
        String userlogin = sec.getUserPrincipal().getName();
        if (userlogin == null) {
            return Response.serverError().build();
        }
        tags = tags.toUpperCase();
        long fileSize = systemController.uploadFile(in, info.getFileName(), path, userlogin);

        if (fileSize == -1) {
            return Response.ok().entity("Error: " + ErrorsController.UPLOAD_ERROR).build();
        }

        // zapisanie informacji o pliku w bazie danych
        long fileId = systemController.addFileInfoToDB(info.getFileName(), fileSize, tags);
        // zapisanie informacji o właścicielu pliku
        systemController.joinFileAndOwner(fileId, userlogin);

        return Response.ok().entity("File " + info.getFileName() + " is up.").build();
    }

    @GET
    @Path("/downloadFile/{fileName}")
    public Response downloadFile(@PathParam("fileName") String fileName) {
        return null;
    }

    @GET
    @Path("/deleteFile/{currentPath}/{fileName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteFile(@PathParam("fileName") String fileName, @PathParam("currentPath") String path) {
        //rozdzielnik dla path? 
        boolean isDeleted = systemController.deleteFile(path, fileName);
        
        if(isDeleted) {
            return Response.ok().entity("File has been deleted.").build();
        }     
        return Response.ok().entity(ErrorsController.DELETION_ERROR).build();
    }
    
    @GET
    @Path("/shareFile/{fileName}")
    public Response shareFile(@PathParam("fileName") String fileName) {
        return null;
    }
    
    @GET
    @Path("/unshareFile/{fileName}")
    public Response unshareFile(@PathParam("fileName") String fileName) {
        return null;
    }
    
    @POST
    @Path("/tagFile")
    public Response tagFile(){
        return null;
    }

    //------------------------------ [SYSTEM] ---------------------------------------
    @GET
    @Path("/getRemainingStorageSize")
    @Produces("text/plain")
    public Response getRemainingStorageSize(@Context SecurityContext sec) {

        try {
            long folderSize = systemController.getFolderSize(sec.getUserPrincipal().getName());
            long availableSpace = SystemController.MAX_STORAGE - folderSize;
            Double availableSpaceD = availableSpace / 1000.0;

            return Response.ok().entity(Double.toString(availableSpaceD)).build();
        } catch (Exception e) {
            return Response.ok().entity("0").build();
        }
    }
}
