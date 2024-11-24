package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String URL = "jdbc:postgresql://localhost:5432/java_project";
  private static final String USER = dotenv.get("DB_USER");
  private static final String PASSWORD = dotenv.get("DB_PASSWORD");
  private static Connection connection = null;

  // loads the PostgreSQL JDBC driver class into the JVM at runtime.
  static {
    try {
        Class.forName("org.postgresql.Driver");
        //ensure that the driver is loaded into memory
    } catch (ClassNotFoundException e) {
      System.out.println("Oops! something went wrong while creating driver class. \nError: "+e.getMessage());
      
    }
  }


  public static Connection getConnection() throws SQLException {
    if(connection == null || connection.isClosed()){
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    return connection;
  }

  public static void closeConnection(){
    try{
      if(connection != null && !connection.isClosed()){
        connection.close();
      }
    }
    catch (SQLException e){
      System.out.println("Oops! something went wrong while closing the connection. \nError"+e.getMessage());
   
    }
  }


}
