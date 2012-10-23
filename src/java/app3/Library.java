package app3;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Atuan
 */
public class Library {
    private static Map<String,Book> books = new HashMap<>();
    private static Library instance = null;
    
    private Library(){
    }
    
    public static Library getInstance(){
        if(instance == null){
            instance = new Library();
        }
        return instance;
    }
    
    public Map<String,Book> getBooks(){
        return books;
    }
}
