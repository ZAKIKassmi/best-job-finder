package com.parsers.rekrute;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.main.Job;
import com.parsers.Parser;

public class RekruteFunctionParser extends  Parser {
   

    protected static final Map<Pattern, String> FUNCTION_MAPPER = Map.ofEntries(
        // Supply Chain & Purchasing
        Map.entry(Pattern.compile("achats|supply chain", Pattern.CASE_INSENSITIVE), 
                 "achats"),
        
        // Commercial & Sales
        Map.entry(Pattern.compile("commercial|vente|export|distribution|administration des ventes|sav", Pattern.CASE_INSENSITIVE), 
                 "commercial, vente"),
        
        // Finance & Management
        Map.entry(Pattern.compile("gestion|comptabilité|finance|audit|conseil|banque|assurance", Pattern.CASE_INSENSITIVE), 
                 "gestion, comptabilité, finance"),
        
        // IT & Technology
        Map.entry(Pattern.compile("informatique|electronique|multimédia|internet|télécoms|réseaux", Pattern.CASE_INSENSITIVE), 
                 "informatique, nouvelles technologies"),
        
        // Legal
        Map.entry(Pattern.compile("juridique|avocat|juriste|fiscaliste", Pattern.CASE_INSENSITIVE), 
                 "juridique"),
        
        // Management
        Map.entry(Pattern.compile("direction|management|dirigeants|responsable de département", Pattern.CASE_INSENSITIVE), 
                 "management, direction générale"),
        
        // Marketing & Communication
        Map.entry(Pattern.compile("communication|publicité|rp|marketing|journalisme|traduction", Pattern.CASE_INSENSITIVE), 
                 "marketing, communication"),
        
        // Healthcare & Social
        Map.entry(Pattern.compile("médical|paramédical|santé|social", Pattern.CASE_INSENSITIVE), 
                 "métiers de la santé et du social"),
        
        // Services
        Map.entry(Pattern.compile("coursier|gardiennage|propreté|call centers|caméraman|monteur|preneur de son|imprimerie", Pattern.CASE_INSENSITIVE), 
                 "métiers des services"),
        
        // Construction & Architecture
        Map.entry(Pattern.compile("btp|travaux|chantiers|electricité|urbanisme|architecture", Pattern.CASE_INSENSITIVE), 
                 "métiers du btp"),
        
        // Production & Quality
        Map.entry(Pattern.compile("production|qualité|maintenance|sécurité|industrialisation|méthodes|process|environnement", Pattern.CASE_INSENSITIVE), 
                 "production, maintenance, qualité"),
        
        // R&D & Project Management
        Map.entry(Pattern.compile("r&d|gestion projet|etudes|projet", Pattern.CASE_INSENSITIVE), 
                 "r&d, gestion de projets"),
        
        // HR & Training
        Map.entry(Pattern.compile("rh|ressources humaines|formation|personnel|enseignement", Pattern.CASE_INSENSITIVE), 
                 "rh, formation"),
        
        // Administrative Support
        Map.entry(Pattern.compile("assistanat|services généraux|assistanat de direction", Pattern.CASE_INSENSITIVE), 
                 "secrétariat, assistanat"),
        
        // Call Centers
        Map.entry(Pattern.compile("call centers|télémarketing", Pattern.CASE_INSENSITIVE), 
                 "télémarketing, téléassistance"),
        
        // Tourism & Hospitality
        Map.entry(Pattern.compile("tourisme|hôtellerie|restauration", Pattern.CASE_INSENSITIVE), 
                 "tourisme, hôtellerie, restauration"),
        
        // Transport & Logistics
        Map.entry(Pattern.compile("logistique|transport", Pattern.CASE_INSENSITIVE), 
                 "transport, logistique")
    );


    public static String findClosestMatch(String input) {
        // Check for pattern matches
        for (Map.Entry<Pattern, String> entry : FUNCTION_MAPPER.entrySet()) {
            if (entry.getKey().matcher(input).find()) {
                return entry.getValue();
            }
        }
        
        // Fallback to similarity matching if no pattern match is found
        return findMostSimilarFunction(input);
    }
    
    private static String findMostSimilarFunction(String input) {
        String mostSimilar = VALID_FUNCTIONS.iterator().next();
        int maxSimilarity = 0;
        
        for (String function : VALID_FUNCTIONS) {
            int similarity = calculateSimilarity(input, function);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                mostSimilar = function;
            }
        }
        
        return mostSimilar;
    }
    
    private static int calculateSimilarity(String str1, String str2) {
        Set<String> words1 = new HashSet<>(Arrays.asList(str1.split("\\s+|/|,")));
        Set<String> words2 = new HashSet<>(Arrays.asList(str2.split("\\s+|/|,")));
        
        int commonWords = 0;
        for (String word : words1) {
            if (words2.contains(word)) {
                commonWords++;
            }
        }
        
        return commonWords;
    }


    public static void parseFunction(List<Job> jobs){
      for(Job job: jobs){
        job.setFunction(findClosestMatch(job.getFunction()));
      }
    }

}
