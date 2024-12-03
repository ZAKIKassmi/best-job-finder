package com.normalizers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.main.Job;

public class RemoteParser extends Parser {
  private static final Set<String> REMOTE_VALID_STRING= Set.of(
        "hybride",
        "non",
        "oui"
    );

    private static final Map<String, String> NORMALIZE_REMOTE_STRING = Map.of(
        "oui 100%","oui"
    );

    public static void parseRemoteWork(List<Job> jobs){
      for(Job job : jobs){
        if(job.getRemoteWork() != null){
            String remote = job.getRemoteWork().toLowerCase();
            String normalizedRemote = NORMALIZE_REMOTE_STRING.getOrDefault(remote, remote);
            if(REMOTE_VALID_STRING.contains(normalizedRemote.toLowerCase())){
                job.setRemoteWork(normalizedRemote.toLowerCase());
            }
        }
        else{
            job.setRemoteWork("non");
        }
      }
    }
}
