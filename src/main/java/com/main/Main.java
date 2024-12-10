package com.main;

import java.util.List;

import com.parsers.ActivitySectorParser;
import com.utils.JsonHandler;

public class Main {
    

    

    public static void main(String[] args) {
        List<Job> jobs = JsonHandler.getAllJobs();

       ActivitySectorParser.parseActivitySector(jobs);
        

        for(Job job:jobs){
            System.out.println(job.getActivitySector());
        }

    }
}
