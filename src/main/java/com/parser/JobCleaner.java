package com.parser;

import java.util.List;
import java.util.stream.Collectors;

import com.main.Job;

public class JobCleaner {

    public static List<Job> cleanJobs(List<Job> jobs) {
        // Supprimer les doublons
        List<Job> uniqueJobs = jobs.stream()
                .distinct()
                .collect(Collectors.toList());

        // Nettoyage et standardisation
        uniqueJobs.forEach(job -> {
            // Standardiser les villes
            job.setCity(standardizeCity(job.getCity()));

            // Convertir remoteWork en booléen ou catégorie
            job.setRemoteWork(standardizeRemoteWork(job.getRemoteWork()));

            // Limiter la longueur du profil recherché
            job.setSearchedProfile(limitStringLength(job.getSearchedProfile(), 500));
        });

        return uniqueJobs;
    }

    private static String standardizeCity(String city) {
        if (city == null) return "unknown";
        city = city.trim().toLowerCase();
        switch (city) {
            case "casablanca":
            case "casa":
            case "casa blanca":
                return "Casablanca";

            case "rabat":
            case "ar-rabat":
            case "arrebat":
                return "Rabat";
            
            case "sale":
            case "sala":
            case "sla":
                return "Sale";

            case "marrakech":
            case "marrakesh":
            case "morrakoch":
                return "Marrakech";

            case "fes":
            case "fez":
            case "fas":
                return "Fès";

            case "tanger":
            case "tangier":
                return "Tanger";

            case "tetouan":
            case "tétouan":
                return "Tétouan";

            case "meknes":
            case "meknès":
            case "maknas":
            case "meknas":
                return "Meknès";

            case "oujda":
            case "wejda":
            case "wajda":
                return "Oujda";

            case "el jadida":
            case "jjedida":
            case "al jadida":
            case "aljadida":
            case "eljadida":
                return "El Jadida";

            case "safi":
            case "asfi":
            case "asfie":
            case "asafi":
                return "Safi";

            case "laayoune":
            case "laâyoune":
                return "Laâyoune";

            case "dakhla":
                return "Dakhla";

            case "temara":
            case "témara":
                return "Témara";

            case "kenitra":
            case "kénitra":
                return "Kénitra";

            case "settat":
                return "Settat";

            default:
                // Capitaliser si la ville n'est pas dans le switch
                return capitalize(city);
        }
    }

    private static String standardizeRemoteWork(String remoteWork) {
        if (remoteWork == null || remoteWork.equalsIgnoreCase("Non")) return "On-site";
        if (remoteWork.equalsIgnoreCase("Hybride")) return "Hybrid";
        if (remoteWork.equalsIgnoreCase("Oui")) return "Remote";
        return "unknown";
    }

    private static String limitStringLength(String input, int maxLength) {
        if (input == null) return "No description available";
        return input.length() > maxLength ? input.substring(0, maxLength) + "..." : input;
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
