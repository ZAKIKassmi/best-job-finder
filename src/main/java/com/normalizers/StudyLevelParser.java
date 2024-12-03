package com.normalizers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.main.Job;
//TODO COMPLETE THIS;
public class StudyLevelParser extends Parser {
    private static final Set<String> AVAILABLE_STUDY_LEVELS = Set.of(
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
        Map.entry("bac+3", "bac +3"),
        Map.entry("niveau d?études requis : bac+3", "bac +3"),
        Map.entry("bac+4", "bac +4"),
        Map.entry("niveau d?études requis : bac+4", "bac +4"),
        Map.entry("bac+5 et plus", "doctrat"),
        Map.entry("bac +5 et plus", "doctrat"),
        Map.entry("niveau d?études requis : bac", "bac"),
        Map.entry("bac+1", "bac +1"),
        Map.entry("niveau d?études requis : bac+1", "bac +1"),
        Map.entry("niveau d?études requis : bac+2", "bac +2"),
        Map.entry("qualification avant bac", "niveau bac"),
        Map.entry("niveau d?études requis : qualification avant bac & bac", "bac"),


        //To be removed
        Map.entry("français / anglais", ""),
        Map.entry("espagnol", ""),
        Map.entry("français", ""),
        Map.entry("arabe / français", ""),
        // Map.entry("arabe / français", ""),
        Map.entry("anglais", "")
    );


    public static void parseStudyLevel(List<Job> jobs){
      HashSet<String> values = new HashSet<>();

        for(Job job : jobs){
            String studyLevel = job.getStudyLevel().toLowerCase();
            studyLevel = nomalizationMap.getOrDefault(studyLevel, studyLevel);
            if(studyLevel != null && !AVAILABLE_STUDY_LEVELS.contains(studyLevel)){
                values.add(studyLevel);
            }
        }
    }
}
