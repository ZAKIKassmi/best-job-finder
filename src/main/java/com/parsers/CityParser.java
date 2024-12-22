package com.parsers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.main.Job;

public class CityParser extends Parser{
  private static final Set<String> VALID_CITIES = Set.of(
        "rabat",
        "salé",
        "nador",
        "casablanca",
        "tanger",
        "tétouan",
        "mohammédia",
        "agadir",
        "marrakech",
        "témara",
        "benguérir",
        "laâyoune",
        "oujda",
        "kénitra",
        "dakhla",
        "settat",
        "guelmim",
        "errachidia",
        "ifrane",
        "berrechid",
        "skhirate",
        "fès",
        "meknès",
        "taliouine",
        "mansouria",
        "bouskoura",
        "béni mellal",
        "el jadida",
        "guercif",
        "khémisset",
        "nouacer",
        "taghazout",
        "hoceima",
        "bouznika",
        "safi",
        "zenata",
        "larache",
        "jorf lasfar",
        "had soualem",
        "tit mellil",
        "ain harrouda",
        "cabo negro",
        "sidi rahal",
        "dar bouazza",
        "ben guerir",
        "kalaa des sraghna",
        "all"
    );  
    private static final Map<Pattern, String> CITY_MAPPER = Map.ofEntries(
        Map.entry(Pattern.compile("salé|sale|sala|technopolis", Pattern.CASE_INSENSITIVE), "salé"),
        Map.entry(Pattern.compile("rabat", Pattern.CASE_INSENSITIVE), "rabat"),
        Map.entry(Pattern.compile("kénitra|kenitra", Pattern.CASE_INSENSITIVE), "kénitra"),
        Map.entry(Pattern.compile("marrakech", Pattern.CASE_INSENSITIVE), "marrakech"),
        Map.entry(Pattern.compile("meknès|meknes|meknas", Pattern.CASE_INSENSITIVE), "meknès"),
        Map.entry(Pattern.compile("tétouan|tetouan", Pattern.CASE_INSENSITIVE), "tétouan"),
        Map.entry(Pattern.compile("tanger", Pattern.CASE_INSENSITIVE), "tanger"),
        Map.entry(Pattern.compile("fès|fes|fez", Pattern.CASE_INSENSITIVE), "fès"),
        Map.entry(Pattern.compile("Laayoune", Pattern.CASE_INSENSITIVE), "laâyoune"),
        Map.entry(Pattern.compile("benguerir|benguérir", Pattern.CASE_INSENSITIVE), "benguérir"),
        Map.entry(Pattern.compile("mohammedia|mohammédia|mohamedia", Pattern.CASE_INSENSITIVE), "mohammédia"),
        Map.entry(Pattern.compile("casablanca|csablanca|csablanca", Pattern.CASE_INSENSITIVE), "casablanca"),
        Map.entry(Pattern.compile("temara|témara", Pattern.CASE_INSENSITIVE), "témara"),
        Map.entry(Pattern.compile("skhirat|skhirate", Pattern.CASE_INSENSITIVE), "skhirate"),
        Map.entry(Pattern.compile("béni mellal|beni mellal", Pattern.CASE_INSENSITIVE), "béni mellal"),
        Map.entry(Pattern.compile("khémisset|khemisset", Pattern.CASE_INSENSITIVE), "khémisset"),
        Map.entry(Pattern.compile("nouaceur|nouacer", Pattern.CASE_INSENSITIVE), "nouacer"),
        Map.entry(Pattern.compile("guelmim", Pattern.CASE_INSENSITIVE), "guelmim"),
        Map.entry(Pattern.compile("hoceima", Pattern.CASE_INSENSITIVE), "hoceima"),

        Map.entry(Pattern.compile("maroc|international|villes", Pattern.CASE_INSENSITIVE), "all")
    );
    

    public static void parseCity(List<Job> jobs) {
        for (Job job : jobs) {
            String city = job.getCity();
            if (city == null) {
                job.setCity(null);
                continue;
            }

            city = city.toLowerCase().trim();

            if (VALID_CITIES.contains(city)) {
                job.setCity(city);
            } else {
                String mappedCity = null;

                for (Map.Entry<Pattern, String> entry : CITY_MAPPER.entrySet()) {
                    if (entry.getKey().matcher(city).find()) {
                        mappedCity = entry.getValue();
                        break;
                    }
                }

                if (mappedCity != null && VALID_CITIES.contains(mappedCity)) {
                    job.setCity(mappedCity);
                } else {
                    // System.out.println("Job: " + job.getCity() + " normalized: " +
                    // mappedCity);
                    job.setCity(null);
                }
            }
        }

        // HashSet<String> values = new HashSet<>();
        // for(Job job: jobs){
        //     values.add(job.getCity());
        // }
        // for(String value: values){
        //     System.out.println(value);
        // }

    }
}
