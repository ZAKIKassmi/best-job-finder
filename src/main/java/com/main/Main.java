package com.main;

import java.util.List;

import com.utils.JsonHandler;

public class Main {
    

    public static void main(String[] args) {
        List<Job> jobs = JsonHandler.getAllJobs();
        System.out.println("Total Jobs before: "+ jobs.size());
        // CityNormalizer.normalizeCity(jobs);
        // ExperienceNormalizer.normalizeExperience(jobs);
        System.out.println("Total Jobs after: "+ jobs.size());

        

    }

   
}
