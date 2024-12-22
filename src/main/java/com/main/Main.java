package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.ui.dashboard.DashboardApp;
import com.utils.JsonHandler;

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

        /*****Rekrute******/
        /* 
        RekruteRemoteParser.parseRemoteWork(jobs);
        RekruteContractTypeParser.parseContractType(jobs);
        publication date, salary, applyBefore verified and parsed at scrapping
        RekruteActivitySectorParser.parseActivitySector(jobs);
        RekruteCityParser.parseCity(jobs);
        RekruteExperienceParser.parseExperience(jobs);
        RekruteStudyLevelParser.parseStudyLevel(jobs);
        RekruteFunctionParser.parseFunction(jobs);*/
        
        
        /*****MJOB******/
        /*
        MjobFunctionParser.parseFunction(jobs);
        MjobCityParser.parseCity(jobs);
        MjobContractType.parseContractType(jobs);
        // Remote work verified
        MjobExperienceParser.parseExperience(jobs);
        MjobStudyLeverParser.parseStudyLevel(jobs);
        */


        //to be checked if we have enough time
        // MjobActivitySectorParser.parseActivitySector(jobs);
        

        // try{
        //     EmploiScrapper.startScrapping(jobs);
        //     System.out.println("total jobs: "+ jobs.size());
        //     JsonHandler.saveJobsToJson(jobs, "./src/main/resources/emploi.json");
        // }   
        // catch(InterruptedException e){
        //     System.out.println(e);
        // }

        List<Job> jobs = JsonHandler.getAllJobs();
        HashSet<String> values = new HashSet<>();

        //working
        // EmploiStudyLevelParser.parseStudyLevel(jobs);
        // EmploiRequiredExperience.parseRequiredExperience(jobs);
        // EmploitCityParser.parseCity(jobs);
        //function is already done
        // EmploiContractTypeParser.parseContractType(jobs);

        
        
        //test


        //verify

        int count= 0;
        for(Job job:jobs){
            if(job.getRemoteWork() == null){
                count++;
            }
            values.add(job.getRemoteWork());
        }


        for(String value: values){
            if(value != null){
                System.out.println(value.toLowerCase());
            }
        }
        System.out.println("Total null jobs: "+count+"\nSet size: "+ values.size());

        

        // Application.launch(args);

        // try {
        //     // System.out.println("Currently scrapping rekrute");
        //     // RekruteScrapper.startScrapping(jobs);
        //     // System.out.println("scrapping completed. Total jobs: "+jobs.size());
        //     // MJob.startScrapping(jobs);
        //     // EmploiScrapper.startScrapping(jobs);
        //     // JsonHandler.saveJobsToJson(jobs, "./src/main/resources/rekrute.json");

        // } catch (InterruptedException e) {
        //     System.out.println(e.getMessage());
        // }

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
