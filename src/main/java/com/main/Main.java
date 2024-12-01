package com.main;

import java.util.List;

import com.parser.Parser;
import com.utils.JsonHandler;

public class Main {
    
    public static void main(String[] args) {

        List<Job> jobs = JsonHandler.getAllJobs();
        Parser.parseContractType(jobs);
        for(Job job: jobs){
            System.out.println(job.getContractType());
        }
        
       
    }
}
