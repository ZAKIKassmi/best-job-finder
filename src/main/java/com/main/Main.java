package com.main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.utils.JsonHandler;

public class Main {
    private static final Set<String> REMOTE_VALID_STRING= Set.of(
        "hybride",
        "non",
        "oui"
    );

    private static final Map<String, String> NORMALIZE_REMOTE_STRING = Map.of(
        "oui 100%","oui"
    );

    public static void main(String[] args) {
        List<Job> jobs = JsonHandler.getAllJobs();
        // ContractTypeNormalizer.normalizeContractType(jobs);
        // CityNormalizer.normalizeCity(jobs);
        // RemoteNormalizer.normalizeRemoteWork(jobs);
        
  

    }

   
}
