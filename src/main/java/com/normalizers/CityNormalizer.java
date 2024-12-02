package com.normalizers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.main.Job;

public class CityNormalizer extends Normalizer{
  private static final Set<String> availableCities = Set.of(
        "rabat",
        "sale",
        "nador",
        "casablanca",
        "tanger",
        "tetouan",
        "mohammedia",
        "agadir",
        "marrakech",
        "temara",
        "benguerir",
        "laayoune",
        "oujda",
        "kenitra",
        "dakhla",
        "settat",
        "guelmim",
        "errachidia",
        "ifrane",
        "berrechid",
        "skhirate",
        "fes",
        "meknes",
        "taliouine",
        "mansouria",
        "bouskoura",
        "beni mellal",
        "el jadida",
        "guercif",
        "khemisset",
        "nouacer",
        "taghazout",
        "al hoceima",
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
        "kalaa des sraghna"
    );

    private static final Map<String, String> cityNormalizationMap = Map.ofEntries(
        Map.entry("salé", "sale"),
        Map.entry("technopolis salé", "sale"),
        Map.entry("salé - technopolis", "sale"),
        Map.entry("technopolis", "sale"),
        Map.entry("salé technopolis", "sale"),
        Map.entry("sala al jadida", "sale"),
        Map.entry("kénitra", "kenitra"),
        Map.entry("kénitra et régions", "kenitra"),
        Map.entry("meknès", "meknes"),
        Map.entry("laâyoune", "laayoune"),
        Map.entry("laâyoune & tanger-tétouan-al hoceïma", "laayoune"),
        Map.entry("fès", "fes"),
        Map.entry("benguérir", "benguerir"),
        Map.entry("mohammédia", "mohammedia"),
        Map.entry("tétouan", "tetouan"),
        Map.entry("témara", "temara"),
        Map.entry("skhirat", "skhirate"),
        Map.entry("fès & meknès", "fes"),
        Map.entry("fès & tanger-tétouan-al hoceïma", "fes"),
        Map.entry("kénitra / tanger", "kenitra"),
        Map.entry("béni mellal", "beni mellal"),
        Map.entry("béni mellal-khénifra", "beni mellal"),
        Map.entry("béni mellal-khénifra - casablanca-mohammedia - laâyoune - marrakech...", "beni mellal"),
        Map.entry("béni mellal-khénifra - casablanca-mohammedia - laâyoune &...", "beni mellal"),
        Map.entry("cdi| f/h | casablanca", "casablanca"),
        Map.entry("csablanca", "casablanca"),
        Map.entry("khémisset", "khemisset"),
        Map.entry("mohamedia-chellalate", "mohammedia"),
        Map.entry("nouaceur", "nouacer"),
        Map.entry("province de guelmim", "guelmim"),
        Map.entry("villes du royaume", ""),
        Map.entry("toutes les villes", ""),
        Map.entry("tout le maroc", ""),
        Map.entry("international", ""),
        Map.entry("dubai", ""),
        Map.entry("maroc", ""),
        Map.entry("tunis", ""),
        Map.entry("abidjan", ""),
        Map.entry("les berges du lac", ""),
        Map.entry("la soukra", ""),
        Map.entry("dakar", ""),
        Map.entry("douala", ""),
        Map.entry("centre urbain nord", ""),
        Map.entry("riyadh", ""),
        Map.entry("nord", ""),
        Map.entry("sud", ""),
        Map.entry("plusieurs villes du maroc", ""),
        Map.entry("lüneburg", ""),
        Map.entry("divers", ""),
        Map.entry("calgary", "")
        
    );

     public static void normalizeCity(List<Job> jobs){
        Iterator<Job> iterator = jobs.iterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            if (job.getCity() != null) {
                String normalizedCity = cityNormalizationMap.getOrDefault(job.getCity().toLowerCase(), job.getCity().toLowerCase());
                if (normalizedCity.length() > 0) {
                    if (normalizedCity.equals("beni mellal") 
                        || normalizedCity.equals("el jadida") 
                        || normalizedCity.equals("al hoceima")
                        || normalizedCity.equals("jorf lasfar")
                        || normalizedCity.equals("had soualem")
                        || normalizedCity.equals("tit mellil")
                        || normalizedCity.equals("ain harrouda")
                        || normalizedCity.equals("cabo negro")
                        || normalizedCity.equals("sidi rahal")
                        || normalizedCity.equals("dar bouazza")
                        || normalizedCity.equals("ben guerir")
                        || normalizedCity.equals("kalaa des sraghna")
                        ){
                            job.setCity(normalizedCity);
                            continue;
                        }

                    String extractedCity = extractUsingRegex(normalizedCity, "^\\w+");
                    if (extractedCity != null && availableCities.contains(extractedCity)) {
                        job.setCity(extractedCity);
                    }
                } else {
                    //remove if city does not exist or if the city is outside Morocco
                    iterator.remove(); 
                }
            }
        }
    }
}
