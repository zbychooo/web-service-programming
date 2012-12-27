/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilities;

/**
 *
 * @author Zbyszko
 */
public class DBConnectorTester {
    
    public static void main(String[] args) {
        System.out.println("Hello");
        
               
        DBConnector db = new DBConnector();
        System.out.println("CHECK DATABASE: " + db.isConnection());
    }
}
