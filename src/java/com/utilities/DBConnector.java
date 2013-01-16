package com.utilities;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * External database credentials and connection methods
 * @author Zbyszko
 */
public class DBConnector {

    //DO NOT CREATE SETTERS AND/OR GETTERS FOR THESE FIELDS!
    private String driverName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/a2l_restcloud";//"jdbc:mysql://www.a2l.pl:3306/a2l_restcloud";
    private String uid = "root";//"a2l_restcloud";
    private String pwd = "";//"restcloud!!2012";
    //---------------------------------------------------------
    private Connection connection;

    /**
     * Checks if database connection is possible
     * @return 
     */
    public boolean isConnection() {
        
        try {
            Class.forName(driverName);
            connection = (Connection) DriverManager.getConnection(url, uid, pwd);

           PreparedStatement statement = connection.prepareStatement("select * from tuser");
           ResultSet rs = statement.executeQuery();
           while(rs.next()) {
               System.out.print(rs.getString("id"));
               System.out.println(" " + rs.getString("tname"));
           }
           rs.close();
           statement.close();   
           connection.close();        
        } catch (ClassNotFoundException exc) {  // brak klasy sterownika
            System.out.println("No class driver");
            System.out.println(exc);
            return false;
        } catch (SQLException exc) {  // nieudane połączenie
            System.out.println("Connection failed, url: " + url);
            System.out.println(exc);
            return false;
        }

        return true;
    }

    /**
     * Connects to database.
     * @return Connection Object.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driverName);
        connection = (Connection) DriverManager.getConnection(url, uid, pwd);
        return connection;
    }

    /**
     * Closes the database connection.
     * @throws SQLException 
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
