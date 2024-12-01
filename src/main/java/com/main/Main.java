package com.main;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parser.Parser;
import com.utils.JsonHandler;

public class Main {
    private static Set<String> availableCities = Set.of(
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

    public static String extractCity(String input, String regex) {
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

    public static void main(String[] args) {
        List<Job> jobs = JsonHandler.getAllJobs();
        Parser.parseContractType(jobs);
        Parser.parseCity(jobs);
        for(Job job : jobs){
            System.out.println("Contract: "+job.getContractType()+"\nCity: "+job.getCity());
        }

    }

   
}
