package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.scrap.EmploiScrapper;
import com.scrap.MJob;
import com.ui.dashboard.DashboardApp;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    private static final List<Job> jobs = Collections.synchronizedList(new ArrayList<>());
    @Override
    public void start(Stage primaryStage) {
        DashboardApp dashboard = new DashboardApp();
        dashboard.start(primaryStage);
    }
    public static void main(String[] args) {

        

        // Application.launch(args);

        try {
            // RekruteScrapper.startScrapping(jobs);
            // MJob.startScrapping(jobs);
            EmploiScrapper.startScrapping(jobs);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        // for (Job job : jobs) {
        //     System.out.println(job.newToString());
        // }
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
      
        // ArrayList<TestJob> jobs = DatabaseServices.getAllJobs();
        // if(jobs == null){
        //     System.out.println("No jobs found");
        //     return;
        // }
        // for(TestJob job: jobs){
        //     System.out.println(job.toString());
        // }
        // Classifier.TrainModel(jobs);
        
        

        
    }
}
