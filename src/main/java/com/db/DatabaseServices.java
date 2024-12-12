package com.db;

import com.main.Job;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseServices {

    // Create the schema (jobs table)
    public static void createDatabaseSchema() {
        try (Connection connection = DatabaseConnection.getConnection()) {

            String createJobsTable = """
                CREATE TABLE IF NOT EXISTS jobs (
                  id SERIAL PRIMARY KEY,
                  city VARCHAR(255),
                  activity_sector VARCHAR(255),
                  required_experience VARCHAR(255),
                  study_level VARCHAR(255),
                  contract_type VARCHAR(255),
                  remote_work VARCHAR(255)
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
            INSERT INTO jobs (city, activity_sector, required_experience, study_level, contract_type, remote_work) 
            VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertIntoTableSQL)) {

            // Set values for the prepared statement
            preparedStatement.setString(1, job.getCity());
            preparedStatement.setString(2, job.getActivitySector());
            preparedStatement.setString(3, job.getRequiredExperience());
            preparedStatement.setString(4, job.getStudyLevel());
            preparedStatement.setString(5, job.getContractType());
            preparedStatement.setString(6, job.getRemoteWork());

            // Execute the insert operation
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new job was inserted successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Oops! Something went wrong while inserting the job. Error: " + e.getMessage());
        }
    }
}
