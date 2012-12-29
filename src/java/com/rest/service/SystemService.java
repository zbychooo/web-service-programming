package com.rest.service;

import com.rest.controller.ErrorsController;
import com.rest.controller.SystemController;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * System service class.
 *
 * @author Zbyszko
 */
@Path("/systemService")
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

    @GET
    @Path("/createFolder")
    public Response createFolder(@Context SecurityContext sec) {

        boolean isCreated = systemController.createFolder(
                "folder",
                sec.getUserPrincipal().getName()
                );
        
        if (isCreated) {
            return Response.ok().entity("isCreated: " + isCreated).build();
        }
        return Response.ok().entity("Error: " + ErrorsController.FOLDER_ALREADY_EXISTS).build();
    }

    @GET
    @Path("/uploadFile")
    public Response uploadFile() {
        System.out.println("upload file");
        return null;
    }

    @GET
    @Path("/getAvailableStorageSize")
    public Response getAvailableStorageSize(@Context SecurityContext sec) {

        Long l = systemController.getFolderSize(sec.getUserPrincipal().getName()); 
        System.out.println("size" + l.toString());
        return Response.ok().entity("storage: " + l).build();
    }
}
