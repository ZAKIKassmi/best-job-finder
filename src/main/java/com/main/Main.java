package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ai.HashMapData;
import com.ai.Prediction;
import com.db.DatabaseServices;
import com.ui.dashboard.DashboardApp;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    private static final List<Job> jobs = Collections.synchronizedList(new ArrayList<>());
    public static  List<Job> rekrute = Collections.synchronizedList(new ArrayList<>());
    public static  List<Job> emploi = Collections.synchronizedList(new ArrayList<>());
    public static  List<Job> mjobs = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void start(Stage primaryStage) {
        DashboardApp dashboard = new DashboardApp();
        dashboard.start(primaryStage);
    }
    
    public static void main(String[] args) {

        
        // try{
        //     System.out.println("Scrapping Rekrute...");
        //     RekruteScrapper.startScrapping(rekrute);
        //     System.out.println("Rekrute scrapping completed");


        //     System.out.println("Scrapping emploit...");
        //     EmploiScrapper.startScrapping(emploi);
        //     System.out.println("Emploi scrapping completed");


        //     System.out.println("Scrapping mjobs");
        //     MJob.startScrapping(mjobs);
        //     System.out.println("M-jobs scrapping completed");
        // }
        // catch(InterruptedException e){
        //     System.out.println("Oops! something went wrong while scrapping. Error: "+e.getMessage());
        // }

        // List<Job> rekrute = JsonHandler.getAllJobs("rekrute.json");
        // List<Job> emploi = JsonHandler.getAllJobs("emploi.json");
        // List<Job> mjobs = JsonHandler.getAllJobs("mjobs.json");
        

        // System.out.println("Parsing rekrute...");
        // RekruteParsers.parseAll(rekrute);
        // System.out.println("Parsing rekrute completed...");


        // System.out.println("Parsing emploi...");
        // EmploiParsers.parseAll(emploi);
        // System.out.println("Parsing emploi completed...");


        // System.out.println("Parsing mjobs...");
        // MjobParsers.parseAll(mjobs);
        // System.out.println("Parsing mjobs completed");

        

        

        // Application.launch(args);



        


        //create table
        // DatabaseServices.createDatabaseSchema();

        // System.out.println("Inserting to database...");
        // DatabaseServices.insertJobsList(rekrute);
        // DatabaseServices.insertJobsList(emploi);
        // DatabaseServices.insertJobsList(mjobs);
        // System.out.println("Inserting to the database is completed");
        // MainInterface.main(args);
         
        // DatabaseConnection.closeDataSource();


        //Train the model using Classifier class that contains models
      
        ArrayList<TestJob> jobs = DatabaseServices.getAllJobs();
        if(jobs == null){
            System.out.println("No jobs found");
            return;
        }

        // Classifier.TrainModel(jobs);
        Prediction predictor = new Prediction();
        for (TestJob job : jobs) {
            if (job.getCity() == null || job.getCity().isEmpty() ||
                job.getActivitySector() == null || job.getActivitySector().isEmpty() || 
                job.getRequiredExperience() == null || job.getRequiredExperience().isEmpty() || 
                job.getStudyLevel() == null || job.getStudyLevel().isEmpty() || 
                job.getContractType() == null || job.getContractType().isEmpty() || 
                job.getRemoteWork() == null || job.getRemoteWork().isEmpty()) {
                continue;
            }
            predictor.addTrainingData(job);
        }

        try {
            predictor.trainModel();
            String predictedContract = predictor.predictContractType(
            "9",           
            "5",    
            "1",     
            "0",       
            "3"         
            );
            System.out.println("predicted: "+HashMapData.contractMap.get(predictedContract));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        

        
        // Instances instance = JobDatasetCreator.createTrainingDataset(jobs);
        // if(instance != null){
        //     Prediction.predictAttribute("contracttype", jobs.get(50), instance);
        // }
        // else{
        //     System.out.println("null values found");
        // }
        
        // try {
        //     Project2.start();
        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }
        

        
    }
}
