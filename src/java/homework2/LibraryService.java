package homework2;

import com.sun.jersey.spi.resource.Singleton;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Atuan
 */
@Singleton
@Path("/lib")
public class LibraryService {
    
    private static Map<Long, Book> books = new HashMap<>();
            
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBook(@QueryParam("id") long id){
        if(books.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Book book = books.get(Long.valueOf(id));
        if(book != null){
            return Response.status(Response.Status.OK).entity(book.toString()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    public Response postBook(@FormParam("id") long id,
        @FormParam("title") String title, 
        @FormParam("author") String author)
    {
        if(books.containsKey(id)){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        Book book = new Book(id,title,author);
        books.put(Long.valueOf(id), book);
        return Response.status(Response.Status.OK).entity(book.toString()).build();
    }
    
    @DELETE
    public Response deleteBook(@QueryParam("id") long id){         
        if(books.containsKey(id)){
            books.remove(id);
            return Response.status(Response.Status.OK).build();
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @PUT
    public Response putBook(JAXBElement<Book> b){
        Book book = b.getValue();
        if(book != null){
            books.put(Long.valueOf(book.getId()), book);
            return Response.status(Response.Status.OK).entity(book.toString()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    
    @GET
    @Path("/count")
    public String countBooks(){
        return Integer.toString(books.size());
    }
    
    @GET
    @Path("/availableids")
    public String availableIds(){
        String res = "Ids: ";
        for(long s : books.keySet()){
            res += s;
            res += " ";
        }
        return res;
    }
}
