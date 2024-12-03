package com.main;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.utils.JsonHandler;

public class Main { 
    private static final Set<String> AVAILABLE_SECTORS = Set.of(
        "bac",
        "bac +1",
        "bac +2",
        "bac +3",
        "bac +4",
        "bac +5",
        "doctrat",
        "autodidacte",
        "niveau bac",
        "all"
    );

    public static Map<String, String> nomalizationMap = Map.ofEntries(
        Map.entry("bac+3", "bac +3")
        
    );
    

    public static void main(String[] args) {
        List<Job> jobs = JsonHandler.getAllJobs();

        HashSet<String> values = new HashSet<>();

        for(Job job : jobs){
            String activitySector = job.getActivitySector().toLowerCase();
            activitySector = nomalizationMap.getOrDefault(activitySector, activitySector);
            if(activitySector != null && !AVAILABLE_SECTORS.contains(activitySector)){
                values.add(activitySector);
            }
        }

        
        // for(Job job: jobs){
        //     values.add(job.getStudyLevel());
        // }

        for(String value: values){
            System.out.println(value);
        }

    }

   
}
