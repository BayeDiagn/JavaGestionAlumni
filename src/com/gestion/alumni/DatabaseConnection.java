package com.gestion.alumni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connection() throws SQLException{
             try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url="jdbc:mysql://localhost:8889/GestAlumni";
		String login="root";
		String password="root";
			
		 Connection connected =DriverManager.getConnection(url,login,password);
                    return connected;            
             } catch (ClassNotFoundException e) {
			e.printStackTrace();
            }
        return null;
    }
}
