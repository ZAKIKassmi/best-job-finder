package com.parsers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.main.Job;

public class StudyLevelParser extends Parser {
    private static final Set<String> VALID_STUDY_LEVELS = Set.of(
        "bac",
        "bac +1 et plus",
        "bac +2 et plus",
        "bac +3 et plus",
        "bac +4 et plus",
        "bac +5 et plus",
        "doctorat",
        "autodidacte",
        "qualification avant bac"
    );

    public static Map<Pattern, String> studyLevelMapper = Map.ofEntries(
        Map.entry(Pattern.compile("bac", Pattern.CASE_INSENSITIVE), "bac"),
        Map.entry(Pattern.compile("autodidacte", Pattern.CASE_INSENSITIVE), "autodidacte"),
        Map.entry(Pattern.compile("niveau bac|qualification",Pattern.CASE_INSENSITIVE), "qualification avant bac"),
        Map.entry(Pattern.compile("doctorat|doctrat", Pattern.CASE_INSENSITIVE), "doctorat"),
        Map.entry(Pattern.compile("bac\\+1|bac +1", Pattern.CASE_INSENSITIVE), "bac +1 et plus"),
        Map.entry(Pattern.compile("bac\\+2|bac +2", Pattern.CASE_INSENSITIVE), "bac +2 et plus"),
        Map.entry(Pattern.compile("bac\\+3|bac +3", Pattern.CASE_INSENSITIVE), "bac +3 et plus"),
        Map.entry(Pattern.compile("bac\\+4|bac +4", Pattern.CASE_INSENSITIVE), "bac +4 et plus"),
        Map.entry(Pattern.compile("bac\\+5|bac +5", Pattern.CASE_INSENSITIVE), "bac +5 et plus")
    );

    public static void parseStudyLevel(List<Job> jobs) {
        for (Job job : jobs) {
            String studyLevel = job.getStudyLevel().toLowerCase();

            if(VALID_STUDY_LEVELS.contains(studyLevel)){
                job.setStudyLevel(studyLevel);
            }
            else{
                
                String parsedStudyLevel = null;  
                for (Map.Entry<Pattern, String> entry : studyLevelMapper.entrySet()) {
                    if (entry.getKey().matcher(studyLevel).find()) {
                        parsedStudyLevel = entry.getValue();
                        break;  
                    }
                }
    
                if (parsedStudyLevel != null && VALID_STUDY_LEVELS.contains(parsedStudyLevel)) {
                    job.setStudyLevel(parsedStudyLevel);
                } else {
                    job.setStudyLevel(null);
                }
            }


        }
    }
}
