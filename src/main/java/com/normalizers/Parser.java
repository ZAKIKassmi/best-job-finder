package com.normalizers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    
    public static String extractUsingRegex(String input, String regex) {
        if (input == null || input.isEmpty()) {
            return null; // Avoid processing null or empty input
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        return null;
    }
   


    // private static String standardizeRemoteWork(String remoteWork) {
    //     if (remoteWork == null || remoteWork.equalsIgnoreCase("Non")) return "On-site";
    //     if (remoteWork.equalsIgnoreCase("Hybride")) return "Hybrid";
    //     if (remoteWork.equalsIgnoreCase("Oui")) return "Remote";
    //     return "unknown";
    // }

    // private static String limitStringLength(String input, int maxLength) {
    //     if (input == null) return "No description available";
    //     return input.length() > maxLength ? input.substring(0, maxLength) + "..." : input;
    // }

  

}
