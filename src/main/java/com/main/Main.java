package com.main;

import java.util.List;

import com.utils.JsonHandler;

public class Main {
    

    public static void main(String[] args) {
        List<Job> jobs = JsonHandler.getAllJobs();
        // Normalizer.parseContractType(jobs);
        // Normalizer.parseCity(jobs);
        // for(Job job : jobs){
        //     System.out.println("Contract: "+job.getContractType()+"\nCity: "+job.getCity());
        // }

    }

   
}
