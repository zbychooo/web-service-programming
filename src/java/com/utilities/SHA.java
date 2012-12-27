package com.utilities;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zbyszko
 */
public class SHA {

    public static String encrypt(String x) {
        String result = "";
        
        try {
            java.security.MessageDigest d = null;
            d = java.security.MessageDigest.getInstance("SHA1");
            d.reset();
            d.update(x.getBytes());
            
            byte by[] = d.digest();

            for (int i = 0; i < by.length; i++) {
                result +=
                        Integer.toString((by[i] & 0xff), 16);
            }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SHA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;        
    }
}
