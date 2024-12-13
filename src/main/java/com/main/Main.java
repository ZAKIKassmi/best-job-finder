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
        // Load all jobs from json
        List<Job> jobs = JsonHandler.getAllJobs();

        // Parse job-related fields
        ActivitySectorParser.parseActivitySector(jobs);
        CityParser.parseCity(jobs);
        ExperienceParser.parseExperience(jobs);
        ContractTypeParser.parseContractType(jobs);
        StudyLevelParser.parseStudyLevel(jobs);
        System.out.println("All items has been parsed successfully");


        //create table
        DatabaseServices.createDatabaseSchema();

        //use mutli-threading to insert jobs
        ExecutorService executorService = Executors.newFixedThreadPool(4); 

        for (Job job : jobs) {
            executorService.submit(() -> DatabaseServices.insertJob(job));
        }
        executorService.shutdown();
        
    }
}
