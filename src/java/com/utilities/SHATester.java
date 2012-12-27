/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilities;

import com.utilities.SHA;

/**
 *
 * @author Zbyszko
 */
public class SHATester {
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("Hello!");
        String s = "userpass";
        String sOut = SHA.encrypt(s);
        System.out.println("sOut: " + sOut);
        
    }
}
