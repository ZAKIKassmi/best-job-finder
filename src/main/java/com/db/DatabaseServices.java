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
        // Méthodes pour récupérer les données nécessaires aux graphiques

    // Récupérer le nombre d'offres par ville
    public static Map<String, Integer> getJobsByCity() {
        String query = "SELECT city, COUNT(*) as total_jobs FROM jobs GROUP BY city ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par secteur d'activité
    public static Map<String, Integer> getJobsByActivitySector() {
        String query = "SELECT activity_sector, COUNT(*) AS total_jobs FROM jobs GROUP BY activity_sector ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par expérience requise
    public static Map<String, Integer> getJobsByExperience() {
        String query = "SELECT required_experience, COUNT(*) AS total_jobs FROM jobs GROUP BY required_experience ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par niveau d'études
    public static Map<String, Integer> getJobsByStudyLevel() {
        String query = "SELECT study_level, COUNT(*) AS total_jobs FROM jobs GROUP BY study_level ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par type de contrat
    public static Map<String, Integer> getJobsByContractType() {
        String query = "SELECT contract_type, COUNT(*) AS total_jobs FROM jobs GROUP BY contract_type ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par télétravail
    public static Map<String, Integer> getJobsByRemoteWork() {
        String query = "SELECT remote_work, COUNT(*) AS total_jobs FROM jobs GROUP BY remote_work ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Méthode générique pour exécuter une requête SQL et retourner les résultats sous forme de Map
    private static Map<String, Integer> executeQuery(String query) {
        Map<String, Integer> result = new HashMap<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Parcourir les résultats de la requête et les ajouter dans la Map
            while (rs.next()) {
                String key = rs.getString(1);  // Première colonne (ex : city, activity_sector, etc.)
                int value = rs.getInt(2);      // Deuxième colonne (total_jobs)
                result.put(key, value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
