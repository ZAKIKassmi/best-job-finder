package com.parsers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.main.Job;

public class StudyLevelParser extends Parser {
    private static final Set<String> AVAILABLE_STUDY_LEVELS = Set.of(
        "bac",
        "bac +1 et plus",
        "bac +2 et plus",
        "bac +3 et plus",
        "bac +4 et plus",
        "bac +5 et plus",
        "doctrat",
        "autodidacte",
        "niveau bac"
    );

    public static Map<String, String> nomalizationMap = Map.ofEntries(
        //To be removed
        Map.entry("français / anglais", ""),
        Map.entry("espagnol", ""),
        Map.entry("français", ""),
        Map.entry("arabe / français", ""),
        // Map.entry("arabe / français", ""),
        Map.entry("anglais", "")
    );


    public static void parseStudyLevel(List<Job> jobs){
        Iterator<Job> jobsIterator = jobs.iterator();
        while(jobsIterator.hasNext()){
            Job job = jobsIterator.next();
            String studyLevel = job.getStudyLevel().trim().toLowerCase();
            studyLevel = nomalizationMap.getOrDefault(studyLevel, studyLevel);
            if(studyLevel.contains("autodidacte")){
                studyLevel = "autodidacte";
            }
            else if(studyLevel.contains("qualification")){
                studyLevel = "niveau bac";
            }
            else if(studyLevel.contains("bac")){
                studyLevel = "bac";
            }
            else if (studyLevel.contains("bac+1") || studyLevel.contains("bac +1")) {
                studyLevel = "bac +1 et plus";
            }
            else if(studyLevel.contains("bac+2") || studyLevel.contains("bac +2")){
                studyLevel = "bac +2 et plus";
            }
            else if(studyLevel.contains("bac +3") || studyLevel.contains("bac+3")){
                studyLevel = "bac +3 et plus";
            }
            else if(studyLevel.contains("bac +4") || studyLevel.contains("bac+4")){
                studyLevel  = "bac +4 et plus";
            }
            else if(studyLevel.contains("bac+5") || studyLevel.contains("bac +5")){
                studyLevel = "bac +5 et plus";
            }
            if(studyLevel.length() == 0){
                jobsIterator.remove();
            }
            if(studyLevel.length()>0 && AVAILABLE_STUDY_LEVELS.contains(studyLevel)){
                job.setStudyLevel(studyLevel);
            }


        }
        
    }
}
