package com.utilities;

/**
 * Temporal class to run and test the DBConnector class methods.
 * @author Zbyszko
 */
public class DBConnectorTester {
    
    public static void main(String[] args) {
        System.out.println("Hello");
        
               
        DBConnector db = new DBConnector();
        System.out.println("CHECK DATABASE: " + db.isConnection());
    }
}