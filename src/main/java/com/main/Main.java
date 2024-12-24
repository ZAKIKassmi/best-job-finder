package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


        

        // System.out.println("Parsing rekrute...");
        // RekruteParsers.parseAll(rekrute);
        // System.out.println("Parsing rekrute completed...");


        // System.out.println("Parsing emploi...");
        // EmploiParsers.parseAll(emploi);
        // System.out.println("Parsing emploi completed...");


        // System.out.println("Parsing mjobs...");
        // MjobParsers.parseAll(mjobs);
        // System.out.println("Parsing mjobs completed");

        

        

        Application.launch(args);


        


        //create table
        // DatabaseServices.createDatabaseSchema();

        // System.out.println("Inserting to database...");
        // DatabaseServices.insertJobsList(rekrute);
        // DatabaseServices.insertJobsList(emploi);
        // DatabaseServices.insertJobsList(mjobs);
        // System.out.println("Inserting to the database is completed");
        // MainInterface.main(args);
         
        // DatabaseConnection.closeDataSource();


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
