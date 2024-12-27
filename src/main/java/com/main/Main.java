package com.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ai.HashMapData;
import com.ai.RandomForestPrediction;
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

       







        

        

        Application.launch(args);



        









        
      
        ArrayList<TestJob> jobs = DatabaseServices.getAllJobs();
        if(jobs == null){
            System.out.println("No jobs found");
            return;
        }

       
        RandomForestPrediction predictor = new RandomForestPrediction(1);
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
            // predictor.trainModel();
            //if target === 3
            // String predictedContract = predictor.predictContractType("9", "4", "1", "0", "3");
            // System.out.println("predicted: "+HashMapData.contractMap.get(predictedContract));

            //if target === 4
            // String predictedRemote = predictor.predictRemoteWork("10", "4", "1", "1", "3");
            // System.out.println("predicted: "+HashMapData.remoteMap.get(predictedRemote));

            //if target === 5
            // String predictCity = predictor.predictCity("3", "4", "4", "2", "1");
            // System.out.println("predicted: "+HashMapData.cityMap.get(predictCity));
            //if traget === 0
            // String predictSector = predictor.predictActivitySector("2", "0", "2", "5", "3");
            // System.out.println("predicted: "+HashMapData.sectorMap.get(predictSector));
            //if target === 1
            String predictExperience = predictor.predictExperience("26", "4", "2", "0", "3");
            System.out.println("predicted: "+HashMapData.experienceMap.get(predictExperience));
            //if target === 2
            // String predictStudyLevel = predictor.predictStudyLevel("26", "4", "2", "0", "3");
            // System.out.println("predicted: "+HashMapData.studyMap.get(predictStudyLevel));





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
