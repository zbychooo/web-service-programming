package com.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class offers methods of hashing given string
 * @author Zbyszko
 */
public class Hash {

    /**
     * String hashing using sha1 hash function.
     * @param input String to hash
     * @return Hashed string
     */
    public static String SHA1(String input) {
        String result = "";

        try {
            java.security.MessageDigest d = null;
            d = java.security.MessageDigest.getInstance("SHA1");
            d.reset();
            d.update(input.getBytes());
            
            byte by[] = d.digest();
            for (int i = 0; i < by.length; i++) {
                result += Integer.toString((by[i] & 0xff), 16);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * String hashing using md5 hash function.
     * @param input String to hash
     * @return Hashed string
     */
    public static String md5(String input) {

        String md5 = null;

        if (null == input) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
