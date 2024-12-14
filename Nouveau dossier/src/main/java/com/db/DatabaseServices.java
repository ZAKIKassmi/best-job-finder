package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.main.Job;

public class DatabaseServices {

    // Create the schema (jobs table)
    public static void createDatabaseSchema() {
        try (Connection connection = DatabaseConnection.getConnection()) {

            String createJobsTable = """
                CREATE TABLE IF NOT EXISTS jobs (
                  id SERIAL PRIMARY KEY,
                  title VARCHAR(255),
                  city VARCHAR(255),
                  activity_sector VARCHAR(255),
                  required_experience VARCHAR(255),
                  study_level VARCHAR(255),
                  contract_type VARCHAR(255),
                  remote_work VARCHAR(255),
                  searched_profile TEXT,
                  job_description TEXT
                );
            """;

            Statement statement = connection.createStatement();
            statement.execute(createJobsTable);
            System.out.println("Table has been created successfully");

        } catch (SQLException e) {
            System.err.println("Oops! something went wrong while creating table. Error: " + e.getMessage());
        }
    }

    // Insert a job record into the jobs table
    public static void insertJob(Job job) {
        String insertIntoTableSQL = """
            INSERT INTO jobs (title, city, activity_sector, required_experience, study_level, contract_type, remote_work, searched_profile, job_description) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertIntoTableSQL)) {

            // Set values for the prepared statement
            preparedStatement.setString(1, job.getJobTitle());
            preparedStatement.setString(2, job.getCity());
            preparedStatement.setString(3, job.getActivitySector());
            preparedStatement.setString(4, job.getRequiredExperience());
            preparedStatement.setString(5, job.getStudyLevel());
            preparedStatement.setString(6, job.getContractType());
            preparedStatement.setString(7, job.getRemoteWork());
            preparedStatement.setString(8, job.getSearchedProfile());
            preparedStatement.setString(9, job.getJobDescription());

            // Execute the insert operation
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                // System.out.println("A new job was inserted successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Oops! Something went wrong while inserting the job. Error: " + e.getMessage());
        }
    }
    //Nombre total de jobs par ville
    public static Map<String, Integer> getTotalJobsPerCity() {
    String query = """
        SELECT city, COUNT(*) as total_jobs
        FROM jobs
        GROUP BY city
        ORDER BY total_jobs DESC;
    """;

    Map<String, Integer> results = new HashMap<>();
    try (Connection connection = DatabaseConnection.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        while (resultSet.next()) {
            results.put(resultSet.getString("city"), resultSet.getInt("total_jobs"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return results; 
    }
    //Répartition des jobs par type de contrat
    public static Map<String, Integer> getJobsPerContractType() {
        String query = """
            SELECT contract_type, COUNT(*) as total_jobs
            FROM jobs
            GROUP BY contract_type
            ORDER BY total_jobs DESC;
        """;
    
        Map<String, Integer> results = new HashMap<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                results.put(resultSet.getString("contract_type"), resultSet.getInt("total_jobs"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    //Répartition des jobs par niveau d'expérience
    public static Map<String, Integer> getJobsPerExperienceLevel() {
        String query = """
            SELECT required_experience, COUNT(*) as total_jobs
            FROM jobs
            GROUP BY required_experience
            ORDER BY total_jobs DESC;
        """;
    
        Map<String, Integer> results = new HashMap<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                results.put(resultSet.getString("required_experience"), resultSet.getInt("total_jobs"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    


}
