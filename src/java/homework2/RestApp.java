package homework2;

import com.sun.jersey.api.core.PackagesResourceConfig;
import javax.ws.rs.ApplicationPath;

/**
 *
 * @author Atuan
 */
@ApplicationPath("/rs")
public class RestApp extends PackagesResourceConfig{
    public RestApp(){
        super("com.rest.service");
    }
}
