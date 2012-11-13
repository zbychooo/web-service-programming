package app3;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Atuan
 */
@Path("/libsupportApp3")
public class LibrarySupportService {
    
    @GET
    @Path("/count")
    public String countBooks(){
        return Integer.toString(Library.getInstance().getBooks().size());
    }
    
    @GET
    @Path("/availableids")
    public String availableIds(){
        String res = "Ids: ";
        for(String s : Library.getInstance().getBooks().keySet()){
            res += s;
            res += " ";
        }
        return res;
    }
}
