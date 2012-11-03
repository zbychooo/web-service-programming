/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package homework3;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Zbyszko
 */


@Path("/uploadService")
public class UploaderService {
    
    private static final String PATH = "C://store/";
    
    @POST
    @Path("/uploadAndShow")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("text/plain")
    public InputStream uploadAndShow(@FormDataParam("file") InputStream in, 
        @FormDataParam("file") FormDataContentDisposition info) throws FileAlreadyExistsException{
        
        String path = PATH + info.getFileName();
        File file = new File(path);
        
        if(file.exists()) {
            throw new FileAlreadyExistsException(file.getName());
        }
        
        try {
            OutputStream out = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            while((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            out.flush();
            out.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UploaderService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex1) {
            Logger.getLogger(UploaderService.class.getName()).log(Level.SEVERE, null, ex1);
        }
        
        //Show file content    
        
        FileInputStream is;
        try{
            is = new FileInputStream(path);
        }catch(FileNotFoundException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return is;

    }
}
