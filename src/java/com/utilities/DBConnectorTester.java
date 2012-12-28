package com.utilities;

public class DBConnectorTester {
    
    public static void main(String[] args) {
        System.out.println("Tester,");
        
               
        //DBConnector db = new DBConnector();
        //System.out.println("CHECK DATABASE: " + db.isConnection());

        String s = "adminpass";
        String sOut = Hash.md5(s);
        System.out.println("out: " + sOut);
    }
}
