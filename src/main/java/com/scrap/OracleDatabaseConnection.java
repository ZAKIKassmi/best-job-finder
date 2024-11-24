package com.scrap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class OracleDatabaseConnection {
  private static final Dotenv dotenv = Dotenv.load();
  private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:free";
  private static final String DBUSER = dotenv.get("DB_USER");
  private static final String DBPASSWORD = dotenv.get("DB_PASSWORD");
  private static Connection connection = null;

  // Load Oracle JDBC Driver
  static {
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
  }


  // Get connection
  public static Connection getConnection() throws SQLException {
    if(connection == null){
      connection = DriverManager.getConnection(URL, DBUSER, DBPASSWORD);
      return connection;
    }
    return connection;
  }

  public static void closeConnection(Connection connection) {
    try {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }
}
