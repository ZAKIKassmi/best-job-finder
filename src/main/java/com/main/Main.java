package com.main;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.db.DatabaseServices;
import com.parsers.ActivitySectorParser;
import com.parsers.CityParser;
import com.parsers.ContractTypeParser;
import com.parsers.ExperienceParser;
import com.parsers.StudyLevelParser;
import com.utils.JsonHandler;

public class Main {

    public static void main(String[] args) {
        // Load all jobs
        List<Job> jobs = JsonHandler.getAllJobs();

        // Parse job-related fields
        ActivitySectorParser.parseActivitySector(jobs);
        CityParser.parseCity(jobs);
        ContractTypeParser.parseContractType(jobs);
        StudyLevelParser.parseStudyLevel(jobs);
        ExperienceParser.parseExperience(jobs);
        System.out.println("All items has been parsed successfully");
        // Create the database schema
        DatabaseServices.createDatabaseSchema();

        // Use a thread pool for multi-threaded insertion
        ExecutorService executorService = Executors.newFixedThreadPool(4); // Adjust the thread pool size as needed

        for (Job job : jobs) {
            executorService.submit(() -> DatabaseServices.insertJob(job));
        }

        // Shutdown the executor service to release resources
        executorService.shutdown();
    }
}
