package com.parsers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.main.Job;

public class ContractTypeParser extends Parser {
    private static final Set<String> validContractTypes = Set.of(
            "cdi",
            "cdd",
            "intérim",
            "autre",
            "stage",
            "freelance",
            "lettre d'engagement",
            "statutaire",
            "temps partiel");
    
    private static final Map<Pattern, String> contractMapper = Map.ofEntries(
        Map.entry(Pattern.compile("intérim|interim", Pattern.CASE_INSENSITIVE), "intérim"),
        Map.entry(Pattern.compile("freelance|free lance", Pattern.CASE_INSENSITIVE), "freelance"),
        Map.entry(Pattern.compile("stage", Pattern.CASE_INSENSITIVE), "stage"),
        Map.entry(Pattern.compile("lettre", Pattern.CASE_INSENSITIVE), "lettre d'engagement"),
        Map.entry(Pattern.compile("statutaire", Pattern.CASE_INSENSITIVE), "statutaire"),
        Map.entry(Pattern.compile("temps partiel", Pattern.CASE_INSENSITIVE), "temps partiel"),
        Map.entry(Pattern.compile("cdd",Pattern.CASE_INSENSITIVE), "cdd"),
        Map.entry(Pattern.compile("cdi", Pattern.CASE_INSENSITIVE), "cdi"),
        Map.entry(Pattern.compile("autre", Pattern.CASE_INSENSITIVE), "autre")

    );

    public static void parseContractType(List<Job> jobs) {
        for (Job job : jobs) {
            String contractType = job.getContractType();
            if (contractType == null || contractType.isBlank()) {
                job.setContractType("autre");
                continue;
            }
    
            // Try to find an exact match using regex first
            String mappedContract = null;
            for (Map.Entry<Pattern, String> entry : contractMapper.entrySet()) {
                if (entry.getKey().matcher(contractType).find()) {
                    mappedContract = entry.getValue();
                    break;
                }
            }
            if(mappedContract != null && validContractTypes.contains(mappedContract)){
                job.setContractType(mappedContract); 
            }
            else{
                job.setContractType("autre");
            }
           
        }
    
        // Debugging: Print distinct normalized contract types
        // HashSet<String> values = new HashSet<>();
        // for (Job job : jobs) {
        //     values.add(job.getContractType());
        // }
        // for (String value : values) {
        //     System.out.println(value);
        // }
    }
    
}
