package com.main;

import java.util.ArrayList;

import com.db.DatabaseServices;

public class Main {

    public static void main(String[] args) {

    
        // Load all jobs from json
        // List<Job> jobs = JsonHandler.getAllJobs();

        // // Parse job-related fields
        // ActivitySectorParser.parseActivitySector(jobs);
        // CityParser.parseCity(jobs);
        // ExperienceParser.parseExperience(jobs);
        // ContractTypeParser.parseContractType(jobs);
        // StudyLevelParser.parseStudyLevel(jobs);
        // RemoteParser.parseRemoteWork(jobs);
        // System.out.println("All items has been parsed successfully");


        // //create table
        // DatabaseServices.createDatabaseSchema();

        // //use mutli-threading to insert jobs
        // ExecutorService executorService = Executors.newFixedThreadPool(4); 

        // for (Job job : jobs) {
        //     executorService.submit(() -> DatabaseServices.insertJob(job));
        // }
        // executorService.shutdown();
        

        // MainInterface.main(args);
      
        ArrayList<TestJob> jobs = DatabaseServices.getAllJobs();
        if(jobs == null){
            System.out.println("No jobs found");
            return;
        }
        for(TestJob job: jobs){
            System.out.println(job.toString());
        }
        // Classifier.TrainModel(jobs);
        
        

        
    }
}
