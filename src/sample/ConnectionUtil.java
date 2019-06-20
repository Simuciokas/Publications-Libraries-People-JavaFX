/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection conDB()
    {
        /* try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6", "root", "root");
            return con;
        } catch (SQLException ex) {
            System.err.println("ConnectionUtil : "+ex.getMessage());
           return null;
        } */
        return null;
    }
}