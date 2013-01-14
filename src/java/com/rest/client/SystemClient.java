package com.rest.client;

import com.rest.model.Folder;
import com.rest.model.SearchResultEntry;
import com.rest.model.User;
import com.rest.model.UserFile;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.representation.Form;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

/**
 *
 * @author Atuan
 */
public class SystemClient {
    private Client client;
    private WebResource webResource;
    private String URL_LOGIN = "http://localhost:8080/RESTCloud/j_security_check";
    private String URL_BASE = "http://localhost:8080/RESTCloud/";
    private User currentUser;
    private HttpServletRequest wsRequest;
    private HttpServletResponse wsResponse;
    
    public SystemClient(User user, HttpServletRequest request,HttpServletResponse response){
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        client.addFilter(new SystemClientFilter());
        currentUser = user;    
        wsRequest = request;
        wsResponse = response;
        try{
            request.login(user.getLogin(), user.getLogin());
        } catch(Exception e){
            System.out.println("Except: "+e.getMessage());
        }
        try{
            System.out.println("AUTH: "+request.authenticate(response));
        } catch(Exception e){
            System.out.println("Except: "+e.getMessage());
        }
        
        webResource = client.resource(URL_BASE+"rest/systemService/myfolderss");
        
        WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_XML);
        for(Cookie c : request.getCookies()){
            builder = builder.cookie(new NewCookie(c.getName(),c.getValue()));
        }
        try{
            String str = builder.get(String.class);
            System.out.println("str: "+str);
        } catch(Exception e){
            System.out.println("Exception in getfolderlist "+e.getMessage());
        }
    }
    
    public SystemClient(User user){
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        currentUser = user;        

//        String URL_DATA = "http://localhost:8080/RESTCloud/rest/systemService/foldername";
//        String URL_DATA1 = "http://localhost:8080/RESTCloud/rest/systemService/folder";
//        String URL_DATA2 = "http://localhost:8080/RESTCloud/rest/systemService/myfolders";
//        String URL_DATA3 = "http://localhost:8080/RESTCloud/rest/systemService/myfolders1";

        System.out.println("filter before");
        // add a filter to set cookies received from the server and to check if login has been triggered
        client.addFilter(new ClientFilter() {
            private ArrayList<Object> cookies;

            @Override
            public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
                if (cookies != null) {
                    request.getHeaders().put("Cookie", cookies);
                }
                ClientResponse response1 = getNext().handle(request);
                // copy cookies
                if (response1.getCookies() != null) {
                    if (cookies == null) {
                        cookies = new ArrayList<Object>();
                    }
                    // A simple addAll just for illustration (should probably check for duplicates and expired cookies)
                    cookies.addAll(response1.getCookies());
                }
                return response1;
            }
        });
        
        System.out.println("filter added"); 
//        client.resource(URL_DATA3).get(String.class);
////        client.resource(URL_BASE).get(String.class);
////        client.addFilter(new LoggingFilter());
//        // Login:
//        webResource = client.resource(URL_LOGIN);
//
//        com.sun.jersey.api.representation.Form form = new Form();
//        form.putSingle("j_username", username);
//        form.putSingle("j_password", "seba1");
//        ClientResponse resp = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,form);
//        
//        System.out.println("after post");
////        System.out.println("resp: "+resp.getEntity(String.class));
//        System.out.println(resp.getClientResponseStatus().toString());
//        
//        // Get the protected web page:
//        //FOLDERNAME
//        webResource = client.resource(URL_DATA3);
//        WebResource.Builder builder = webResource.getRequestBuilder();
//        for(NewCookie c : resp.getCookies()){
//            if(c.getName().equals("JSESSIONID")){
//                System.out.println("COOKIE");
//                builder = builder.cookie(c);
//            }
//        }
//        ClientResponse response=null;
//        try{
//        response = builder.get(ClientResponse.class);
//        System.out.println("the end of the world");
//        System.out.println(response.getEntity(String.class));
//        System.out.println("cold "+ response.getClientResponseStatus().getReasonPhrase());
//        } catch(Exception e){
//            System.out.println("crying and crying "+e.getMessage());
//        }
//        
//        //FOLDER
////        webResource = client.resource(URL_DATA1);
////        builder = webResource.getRequestBuilder();
////        for(NewCookie c : response.getCookies()){
////            if(c.getName().equals("JSESSIONID")){
////                System.out.println("COOKIE");
////                builder = builder.cookie(c);
////            }
////        }
////        try{
////            response = builder.get(ClientResponse.class);
////        System.out.println("the end of the world1");
////        System.out.println("cold1 "+ response.getClientResponseStatus().getReasonPhrase());
////        System.out.println(response.getEntity(Folder.class));
////        } catch(Exception e){
////            System.out.println("crying and crying1 "+e.getMessage());
////        }
//        
//        //FOLDERNAME1
//        webResource = client.resource(URL_DATA1);
//        builder = webResource.getRequestBuilder();
//        for(NewCookie c : resp.getCookies()){
//            if(c.getName().equals("JSESSIONID")){
//                System.out.println("COOKIE");
//                builder = builder.cookie(c);
//            }
//        }
//        try{
//            
//          builder.get(ClientResponse.class);
////        System.out.println("the end of the world3");
////        System.out.println("cold3 "+ response.getClientResponseStatus().getReasonPhrase());
////        System.out.println(response.getEntity(String.class));
//        webResource = client.resource(URL_LOGIN);
//        webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,form);
//        webResource = client.resource(URL_DATA1);
//        builder = webResource.getRequestBuilder();
//        for(NewCookie c : resp.getCookies()){
//            if(c.getName().equals("JSESSIONID")){
//                System.out.println("COOKIE");
//                builder = builder.cookie(c);
//            }
//        }
//            response = builder.get(ClientResponse.class);
//        System.out.println("the end of the world3");
//        System.out.println("cold5 "+ response.getClientResponseStatus().getReasonPhrase());
//        System.out.println(response.getEntity(Folder.class).getName());
//        } catch(Exception e){
//            System.out.println("crying and crying3 "+e.getMessage());
//        }
//        
//        //LIST
//        webResource = client.resource(URL_DATA2);
//        builder = webResource.getRequestBuilder();
//        for(NewCookie c : resp.getCookies()){
//            if(c.getName().equals("JSESSIONID")){
//                System.out.println("COOKIE");
//                builder = builder.cookie(c);
//            }
//        }
//        try{
//            
//          builder.get(ClientResponse.class);
////        System.out.println("the end of the world3");
////        System.out.println("cold3 "+ response.getClientResponseStatus().getReasonPhrase());
////        System.out.println(response.getEntity(String.class));
//        webResource = client.resource(URL_LOGIN);
//        webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,form);
//        webResource = client.resource(URL_DATA2);
//        builder = webResource.getRequestBuilder();
//        for(NewCookie c : resp.getCookies()){
//            if(c.getName().equals("JSESSIONID")){
//                System.out.println("COOKIE");
//                builder = builder.cookie(c);
//            }
//        }
//            response = builder.get(ClientResponse.class);
//        System.out.println("the end of the world3");
//        System.out.println("cold5 "+ response.getClientResponseStatus().getReasonPhrase());
//        List<Folder> folders = response.getEntity(new GenericType<List<Folder>>(){});
//        System.out.println("FOL: "+(folders==null));
//        System.out.println("SIZE: " + folders.size());
//        } catch(Exception e){
//            System.out.println("crying_and_crying3 "+e.getMessage());
//        }
        
        webResource = client.resource(URL_BASE);
    }
    
    public List<Folder> getFolderList(){
        System.out.println("Get_FOLDER_LIST()");
        WebResource.Builder builder = prepareWSRequest("rest/systemService/myfolders");
        try{
            List<Folder> result = builder.get(new GenericType<List<Folder>>(){});
            if(result != null){
                return result;
            }
        } catch(Exception e){
            System.out.println("Exception in getfolderlist "+e.getMessage());
        }
        return new ArrayList<>();
    }
    
    public String getUserRemainingSpace(){
        System.out.println("Get_USER_REMAINING_SPACE()");
        WebResource.Builder builder = prepareWSRequest("rest/systemService/getRemainingStorageSize");
        try{
            String result = builder.get(String.class);
            if(result != null){
                return result;
            }
        } catch(Exception e){
            System.out.println("Exception in getuserremainingspace "+e.getMessage());
        }
        return "";
    }
    
    public File downloadFile(String filePath,String fileName){
        System.out.println("DOWNLOAD()");
        WebResource.Builder builder = prepareWSRequest("rest/systemService/downloadFile/"+filePath+"/"+fileName);
        try{
            File result = builder.get(File.class);
            if(result != null){
                return result;
            }
        } catch(Exception e){
            System.out.println("Exception in download "+e.getMessage());
        }
        return null;
    }    
    
    private WebResource.Builder prepareRequest(String address){
        //make first request, that do not really counts
        client.resource(URL_BASE+address).get(ClientResponse.class);
        
        // Login:
        webResource = client.resource(URL_LOGIN);
        com.sun.jersey.api.representation.Form form = new Form();
        form.putSingle("j_username", currentUser.getLogin());
        form.putSingle("j_password", currentUser.getPassword());
        ClientResponse resp = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,form);
        
        //get cookie from the response and return it with Requestbuilder
        webResource = client.resource(URL_BASE+address);
        WebResource.Builder builder = webResource.getRequestBuilder();
        for(NewCookie c : resp.getCookies()){
            if(c.getName().equals("JSESSIONID")){
                System.out.println("COOKIE");
                builder = builder.cookie(c);
            }
        }   
        return builder;
    }
    
    private WebResource.Builder prepareWSRequest(String address){
             
        if(!wsRequest.isRequestedSessionIdValid()){
            try{
                wsRequest.login(currentUser.getLogin(), currentUser.getPassword());
            }catch(Exception e){
                System.out.println("Except: "+e.getMessage());
            }
            try{
                System.out.println("AUTH: "+wsRequest.authenticate(wsResponse));
            } catch(Exception e){
                System.out.println("Except: "+e.getMessage());
            }
        }
        webResource = client.resource(URL_BASE+address);
        WebResource.Builder builder = webResource.getRequestBuilder();
        
        for(Cookie c : wsRequest.getCookies()){
            builder = builder.cookie(new NewCookie(c.getName(),c.getValue()));
        }
        return builder;
    }
    
    public List<SearchResultEntry> search(String phrase, String criteria){
        List<SearchResultEntry> result = new ArrayList<>();
        System.out.println("SEARCH()");
        WebResource.Builder builder = prepareWSRequest("rest/systemService/search/"+phrase+"/"+criteria);
        try{
            result.addAll(builder.get(new GenericType<List<SearchResultEntry>>(){}));
        } catch(Exception e){
            System.out.println("Exception in download "+e.getMessage());
        }
        return result;
    }
}
