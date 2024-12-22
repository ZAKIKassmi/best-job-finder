package com.parsers.emploi;

import java.util.List;

import com.main.Job;
import com.parsers.Parser;

public class EmploiContractTypeParser extends Parser {
  public static void parseContractType(List<Job> jobs) {
        for (Job job : jobs) {
            String contractType = job.getContractType();
            if (contractType == null || contractType.isBlank()) {
                job.setContractType(null);
                continue;
            }
            
            String mappedContract = mapContractType(contractType);

            if(mappedContract != null && VALID_CONTRACT_TYPES.contains(mappedContract)){
                job.setContractType(mappedContract); 
            }
            else{
                job.setContractType(null);
            }
        }
    
       
    }
}
