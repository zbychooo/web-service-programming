package com.rest.service;

import com.rest.controller.ErrorsController;
import com.rest.controller.SystemController;
import com.sun.jersey.spi.resource.Singleton;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

    @GET
    @Path("/uploadFile")
    public Response uploadFile() {
        System.out.println("upload file");
        return null;
    }

    @GET
    @Path("/getAvailableStorageSize")
    public Response getAvailableStorageSize(@Context SecurityContext sec) {
        //cos nie konca dobrze liczy
        long l = systemController.getFolderSize(sec.getUserPrincipal().getName());
        long ile = SystemController.MAX_STORAGE - l;
        return Response.ok().entity("storage (kB): size: " + l + " " + ile
                + " max: " + SystemController.MAX_STORAGE).build();
    }
}
