package com.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.scrap.EmploiScrapper;
import com.scrap.MJob;
import com.scrap.RekruteScrapper;

public class Main {
    public static void saveJobsToJson(List<Job> jobs, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Format JSON lisible

        try {
            File file = new File(filePath);
            List<Job> allJobs = new ArrayList<Job>();

            if(file.exists()){
                try{
                    allJobs = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Job.class));
                }
                catch(IOException e){
                    System.out.println("Oops! Error reading existing jobs from JSON file: "+e.getMessage());
                }
            }
            allJobs.addAll(jobs);
            objectMapper.writeValue(new File(filePath), allJobs);
            System.out.println("Jobs have been saved successfully." + filePath);
        } catch (IOException e) {
            System.err.println("Oops! something went wrong while writing jobs to json. Error: " + e.getMessage());
        }
    }
    public static void main(String[] args) {

        // String filePath = "./src/main/resources/jobs.json";
        

        // try {
        //     String jsonArrayString =  new String(Files.readAllBytes(Paths.get(filePath)));
        //     JSONArray jsonArray = new JSONArray(jsonArrayString);
        //     System.out.println("Lenght of the array is: "+ jsonArray.length());
        // } catch (IOException e) {
        // }
        try {
            List<Job> jobs = MJob.startScrapping();
            String filePath = "./src/main/resources/mjobs.json";
            saveJobsToJson(jobs, filePath);
        } catch (InterruptedException e) {
            System.out.println("Oops! something went wrong! Error -> "+e.getMessage());
        }
       
    }
}
