package homework2;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

/**
 *
 * @author Atuan
 */
public class LibraryServiceClient {
  public static void main(String[] args) {
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);
    WebResource service = client.resource(getBaseURI());
    String appPath = "rs";
    String servicePath = "lib";
    
    // Create one book
    Book book = new Book(11, "book1","author1");
    service.path(appPath).path(servicePath)
        .accept(MediaType.TEXT_XML)
        .put(book);
    
    // List all available ids
    System.out.println(service.path(appPath).path(servicePath+"/availableids")
        .accept(MediaType.TEXT_PLAIN).get(String.class));

    // delete book with id 11
    service.path(appPath).path(servicePath).queryParam("id", "11")
            .delete();
    
    // Get the all todos, id 1 should be deleted
    System.out.println(service.path(appPath).path(servicePath+"/availableids")
        .accept(MediaType.TEXT_PLAIN).get(String.class));

    // Create a Book
    Form form = new Form();
    form.add("id", "4");
    form.add("title", "book4");
    form.add("author", "author4");
    
    ClientResponse response = service.path(appPath).path(servicePath)
        .type(MediaType.APPLICATION_FORM_URLENCODED)
        .post(ClientResponse.class, form);
    
    System.out.println("Form response " + response.getEntity(String.class));
    // List all available ids.
    System.out.println(service.path(appPath).path(servicePath+"/availableids")
        .accept(MediaType.TEXT_PLAIN).get(String.class));

  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:8080/RestWS").build();
  }

} 
