package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.db.DatabaseConnection;
import com.db.DatabaseServices;
import com.parsers.emploi.EmploiParsers;
import com.parsers.mjobs.MjobParsers;
import com.parsers.rekrute.RekruteParsers;
import com.ui.MainInterface;
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

        

        

        List<Job> rekrute = JsonHandler.getAllJobs("rekrute.json");
        List<Job> emploi = JsonHandler.getAllJobs("emploi.json");
        List<Job> mjobs = JsonHandler.getAllJobs("mjobs.json");
        System.out.println("Parsing rekrute...");
        RekruteParsers.parseAll(rekrute);
        System.out.println("Parsing emploi...");
        EmploiParsers.parseAll(emploi);
        System.out.println("Parsing mjobs...");
        MjobParsers.parseAll(mjobs);
        System.out.println("Parsing completed");

        

        

        // Application.launch(args);


        


        // //create table
        DatabaseServices.createDatabaseSchema();

        
        DatabaseServices.insertJobsList(rekrute);
        DatabaseServices.insertJobsList(emploi);
        DatabaseServices.insertJobsList(mjobs);
        MainInterface.main(args);
         
        DatabaseConnection.closeDataSource();


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
