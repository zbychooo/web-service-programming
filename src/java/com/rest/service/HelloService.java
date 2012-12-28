package com.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Zbyszko
 */

@Path("/hello")
public class HelloService {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/sayHello")
    public String sayHello(@Context SecurityContext sec){
        System.out.println("is secure? " + sec.isSecure());
        
        return "Hello World " + sec.getUserPrincipal().getName()+" !";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/sayBye")
    public String sayGoodbye(@Context SecurityContext sec) {
        System.out.println("is secure? " + sec.isSecure());
        return "Goodbye " + sec.getUserPrincipal().getName() + " scheme" 
                + sec.getAuthenticationScheme();
        //return "bye!";
    }
}
