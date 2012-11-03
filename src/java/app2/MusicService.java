package app2;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Atuan
 */
@Path("/music")
public class MusicService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayDefault(){
        return "This is music service";
    }
    
    @POST
    public String postSongInfo(@FormParam("title") String title,
      @FormParam("artist") String artist,
      @FormParam("album") String album){
        String result = "\""+title+"\" by "+artist+" from \""+album+"\" album.";
        return result;
    }
        
    // This method is called if XML is request
//    @GET
//    @Produces(MediaType.TEXT_XML)
//    public String sayXMLGoodbye() {
//        return "<?xml version=\"1.0\"?>" + "<goodbye> Goodbye Cruel World" + "</goodbye>";
//    }

    // This method is called if HTML is request
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public String sayHtmlGoodbye() {
//        return "<html> " + "<title>" + "Goodbye Cruel World" + "</title>"
//            + "<body><h1>" + "Goodbye Cruel World" + "</body></h1>" + "</html> ";
//    }
    
    
    
}
