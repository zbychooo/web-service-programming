package com.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * System service class.
 * @author Zbyszko
 */
@Path("/systemService")
public class SystemService {

    @GET
    @Path("/createSpace")
    public Response createSpace() {
        System.out.println("create space");
        return null;
    }

    @GET
    @Path("/uploadFile")
    public Response uploadFile() {
        System.out.println("upload file");
        return null;
    }

    @GET
    @Path("/deleteFile")
    public Response deleteFile() {
        System.out.println("delete file");
        return null;
    }
}
