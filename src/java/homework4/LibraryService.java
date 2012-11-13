package homework4;

import com.sun.jersey.spi.resource.Singleton;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Atuan
 */
@Singleton
@Path("/library")
public class LibraryService {
    
    private static Map<Long, Book> books = new HashMap<>();
    private static Map<Long, Date> lastModified = new HashMap<>();
                
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public Response getBook(@PathParam("id") String id, @Context Request req){
        System.out.println("Books: "+books.size()+" "+id);
        if(books.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Book book = books.get(Long.valueOf(id));
        EntityTag tag = new EntityTag(id);
        
        CacheControl cc = new CacheControl();
	cc.setMaxAge(30);

        ResponseBuilder builder = req.evaluatePreconditions(tag);
        
        if(builder != null && book != null){
            // Preconditions met!
            System.out.println("Preconditions met!");
            builder.cacheControl(cc);
            return builder.status(Status.OK).entity(book.toString()).build();
        }
        
        if(book != null){
            // Preconditions NOT met!
            System.out.println("Preconditions NOT MET!");
//            book.setId(lastId);
            builder = Response.ok(book.toString(), MediaType.TEXT_PLAIN);
            builder.cacheControl(cc);
            builder.tag(tag);
            return builder.build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @POST
    public Response postBook(@FormParam("id") String id,
        @FormParam("title") String title, 
        @FormParam("author") String author, 
        @Context Request req)
    {
        ResponseBuilder builder;
                
        if(lastModified.containsKey(Long.valueOf(id))){
             builder = req.evaluatePreconditions(lastModified.get(Long.valueOf(id)));
             if(builder != null){
                 System.out.println("Not matches");
                 builder = Response.ok(books.get(Long.valueOf(id)).toString(), MediaType.TEXT_PLAIN);
                 return builder.build();
             }
             System.out.println("Nothing has been changed since the request was made.");
        }
        
        Book book = new Book(Long.valueOf(id),title,author);
        books.put(Long.valueOf(id), book);
        //setTimestamp
        builder = Response.ok(book.toString(), MediaType.TEXT_PLAIN);
        Date date= new Date();
        builder.lastModified(date);
        lastModified.put(Long.valueOf(id), date);
        
        return builder.build();
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
    @Path("/damage")
    public String doDamage(){
        Book book = new Book(1,"this","is damage");
        books.put(Long.valueOf("1"), null);
        return "damage done";
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
