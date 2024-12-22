package com.parsers.rekrute;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.main.Job;
import com.parsers.Parser;

public class RekruteContractTypeParser extends Parser {
    
    public static void parseContractType(List<Job> jobs) {
        for (Job job : jobs) {
            String contractType = job.getContractType();
            if (contractType == null || contractType.isBlank()) {
                job.setContractType(null);
                continue;
            }
            
            String mappedContract = null;
            for (Map.Entry<Pattern, String> entry : CONTRACT_TYPE_MAPPER.entrySet()) {
                if (entry.getKey().matcher(contractType).find()) {
                    mappedContract = entry.getValue();
                    break;
                }
            }
            if(mappedContract != null && VALID_CONTRACT_TYPES.contains(mappedContract)){
                job.setContractType(mappedContract); 
            }
            else{
                job.setContractType(null);
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
