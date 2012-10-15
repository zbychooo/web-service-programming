package app3;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;

/**
 *
 * @author Atuan
 */
@Path("/library")
public class LibraryService {
    
    public LibraryService(){
    }
        
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getBook(@QueryParam("id") String id){
        Library lib = Library.getInstance();
        if(lib.getBooks().isEmpty()){
            return "There are no books in library";
        }
        if(id == null || id.isEmpty()){
            return "No id was specified";
        }
        Book book = lib.getBooks().get(id);
        if(book != null){
            return "Selected book:\n id: "+id+" title: "+book.getTitle()+" author: "+book.getAuthor();
        } else {
            return "There is no book with such id.";
        }
    }
    
    @POST
    public String postBook(@FormParam("id") String id,
        @FormParam("title") String title, 
        @FormParam("author") String author)
    {
        Library lib = Library.getInstance();
        if(lib.getBooks().containsKey(id)){
            return "Thre is already a book with such id.";
        }
        
        Library.getInstance().getBooks().put(id, new Book(id,title,author));
        return "Book has been added\r\n id: "+id+" title: "+title+" author: "+author;
    }
    
    @DELETE
    public String deleteBook(@QueryParam("id") String id){        
        if(id.isEmpty() || id == null){
            return "No id was specified";
        }
        
        if(Library.getInstance().getBooks().containsKey(id)){
            Library.getInstance().getBooks().remove(id);
            return "Book with id:"+id+" removed.";
        }
        
        return "There is no book with id="+id;
    }
    
    @PUT
    public String putBook(@FormParam("id") String id,
        @FormParam("title") String title, 
        @FormParam("author") String author)
    {
        return postBook(id,title,author);
    }
}
