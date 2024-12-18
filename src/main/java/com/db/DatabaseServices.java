package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.main.Job;
import com.main.TestJob;

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
        String query = "SELECT city, COUNT(*) as total_jobs FROM jobs WHERE city IS NOT NULL GROUP BY city ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par secteur d'activité
    public static Map<String, Integer> getJobsByActivitySector() {
        String query = "SELECT activity_sector, COUNT(*) AS total_jobs FROM jobs WHERE activity_sector IS NOT NULL GROUP BY activity_sector ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par expérience requise
    public static Map<String, Integer> getJobsByExperience() {
        String query = "SELECT required_experience, COUNT(*) AS total_jobs FROM jobs WHERE required_experience IS NOT NULL GROUP BY required_experience ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par niveau d'études
    public static Map<String, Integer> getJobsByStudyLevel() {
        String query = "SELECT study_level, COUNT(*) AS total_jobs FROM jobs WHERE study_level IS NOT NULL GROUP BY study_level ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par type de contrat
    public static Map<String, Integer> getJobsByContractType() {
        String query = "SELECT contract_type, COUNT(*) AS total_jobs FROM jobs WHERE contract_type IS NOT NULL GROUP BY contract_type ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer le nombre d'offres par télétravail
    public static Map<String, Integer> getJobsByRemoteWork() {
        String query = "SELECT remote_work, COUNT(*) AS total_jobs FROM jobs WHERE remote_work IS NOT NULL GROUP BY remote_work ORDER BY total_jobs DESC";
        return executeQuery(query);
    }

    // Récupérer les offres par expérience et type de contrat
    public static Map<String, Map<String, Integer>> getJobsByExperienceAndContractType() {
        String query = """
            SELECT required_experience, contract_type, COUNT(*) AS total_jobs
            FROM jobs
            WHERE required_experience IS NOT NULL AND contract_type IS NOT NULL
            GROUP BY required_experience, contract_type
            HAVING COUNT(*) > 100
            ORDER BY required_experience, total_jobs DESC;
        """;
        return executeComplexQuery(query);
    }

    // Récupérer les offres par ville et expérience requise
    public static Map<String, Map<String, Integer>> getJobsByCityAndExperience() {
        String query = """
            SELECT city, required_experience, COUNT(*) AS total_jobs
            FROM jobs
            WHERE city IS NOT NULL AND required_experience IS NOT NULL
            GROUP BY city, required_experience
            HAVING COUNT(*) >= 60
            ORDER BY city, total_jobs DESC;
        """;
        return executeComplexQuery(query);
    }

    // Récupérer les offres par niveau d'études et télétravail
    public static Map<String, Map<String, Integer>> getJobsByStudyLevelAndRemoteWork() {
        String query = """
            SELECT study_level, remote_work, COUNT(*) AS total_jobs
            FROM jobs
            WHERE study_level IS NOT NULL AND remote_work IS NOT NULL
            GROUP BY study_level, remote_work
            ORDER BY study_level, total_jobs DESC;
        """;
        return executeComplexQuery(query);
    }

    // Récupérer les offres par type de contrat et télétravail
    public static Map<String, Map<String, Integer>> getJobsByContractTypeAndRemoteWork() {
        String query = """
            SELECT contract_type, remote_work, COUNT(*) AS total_jobs
            FROM jobs
            WHERE contract_type IS NOT NULL AND remote_work IS NOT NULL
            GROUP BY contract_type, remote_work
            HAVING COUNT(*) > 20
            ORDER BY contract_type, total_jobs DESC;
        """;
        return executeComplexQuery(query);
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
            System.out.println(e.getMessage());
        }
        return result;
    }

    // Méthode générique pour exécuter une requête complexe et retourner les résultats sous forme de Map imbriquée
    private static Map<String, Map<String, Integer>> executeComplexQuery(String query) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String key1 = rs.getString(1); // Première colonne (ex : required_experience, city, etc.)
                String key2 = rs.getString(2); // Deuxième colonne (ex : contract_type, remote_work, etc.)
                int value = rs.getInt(3);      // Troisième colonne (total_jobs)

                result.putIfAbsent(key1, new HashMap<>());
                result.get(key1).put(key2, value);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    public static ArrayList<TestJob> getAllJobs(){
        ArrayList<TestJob> jobs = new ArrayList<>();
        String query = """
                SELECT city, activity_sector, required_experience, study_level, contract_type, remote_work, searched_profile, job_description
                FROM jobs
                """;
        try(Connection connection = DatabaseConnection.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery(query);
            while(response.next()){
                TestJob job = new TestJob();
                job.setCity(response.getString("city"));
                job.setActivitySector(response.getString("activity_sector"));
                job.setRequiredExperience(response.getString("required_experience"));
                job.setStudyLevel(response.getString("study_level"));
                job.setContractType(response.getString("contract_type"));
                job.setRemoteWork(response.getString("remote_work"));
                jobs.add(job);
            }
            return jobs;
        }
        catch(SQLException e){
            System.out.println("Oops! something went wrong while fetching all jobs. Error: "+e.getMessage());
        }
        return null;
    }
}

