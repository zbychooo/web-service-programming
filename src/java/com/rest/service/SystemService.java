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
        tags = tags.toUpperCase();            
        boolean isUploaded = systemController.uploadFile(in, info.getFileName(), path, userlogin);
        
        if (!isUploaded) {
            return Response.ok().entity("Error: " + ErrorsController.UPLOAD_ERROR).build();
        }
              
        systemController.addFileInfoToDB(info.getFileName(), info.getSize(), tags);

        return Response.ok().entity("File is up, tags: " + tags 
                                            + ", login: " + userlogin).build();
    }

//    @GET
//    @Path("/getContent/{user}")
//    public Response getUserFileList(@PathParam("user") String userLogin){
//        
//        
//        return null;
//    }
    
//    @GET
//    @Path("/getTag/{tag}")
//    public Response getTags(@PathParam("tag") String tag){
//        return null;
//    }
    
    
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
}
