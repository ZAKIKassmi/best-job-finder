package com.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseServices {
  public static void createDatabaseSchema(){
    try (Connection connection = DatabaseConnection.getConnection()){

      String createTableSQL = """
        CREATE TABLE IF NOT EXISTS jobs (
          id SERIAL PRIMARY KEY,
          job_title TEXT NOT NULL,
          city VARCHAR(100) NOT NULL,
          activity_sector VARCHAR(255),
          function VARCHAR(255),
          required_experience VARCHAR(50),
          study_level VARCHAR(50),
          contract_type VARCHAR(20),
          searched_profile TEXT,
          remote_work VARCHAR(50)
          );
        """;

      Statement statement = connection.createStatement();
      statement.execute(createTableSQL);
      System.out.println("Table has been created successfully");

    }
    catch(SQLException e){
      System.err.println("Oops! something went wrong while creating table. Error: "+e.getMessage());
    }
    finally{
      DatabaseConnection.closeConnection();
    }
  }
}
