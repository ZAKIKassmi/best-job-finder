package com.parsers;

import java.util.List;
import java.util.Set;

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
        "temps partiel"
    );

    public static void parseContractType(List<Job> jobs){
        // String extractedValue = extractContractType(input, "^\\w+");
        for(Job job: jobs){
        String contractType = job.getContractType().toLowerCase();
        if(!validContractTypes.contains(contractType)){
            if("free lance".equals(contractType)){
                job.setContractType(contractType.replace("free lance", "freelance"));
            }
            else{
                job.setContractType(extractUsingRegex(contractType, "^\\w+"));
            }
            
        }
        else{
            job.setContractType(contractType.toLowerCase());
        }
        }

    }
}
