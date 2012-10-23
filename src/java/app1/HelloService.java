package app1;

import javax.ws.rs.Path;
import javax.ws.rs.GET;

/**
 *
 * @author Atuan
 */
@Path("/hello")
public class HelloService {
    @GET
    public String sayHello(){
        return "Hello world";
    }
    
}
