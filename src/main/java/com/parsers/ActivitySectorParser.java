package com.parsers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.main.Job;

public class ActivitySectorParser extends Parser {
  private static final Set<String> AVAILABLE_SECTORS = Set.of(
        "Activités associatives",
        "Administration publique",
        "Aéronautique, navale",
        "Agriculture, pêche, aquaculture",
        "Agroalimentaire",
        "Ameublement, décoration",
        "Automobile, matériels de transport, réparation",
        "Banque, assurance, finances",
        "BTP, construction",
        "Centres d´appel, hotline, call center",
        "Chimie, pétrochimie, matières premières, mines",
        "Conseil, audit, comptabilité",
        "Distribution, vente, commerce de gros",
        "Édition, imprimerie",
        "Éducation, formation",
        "Electricité, eau, gaz, nucléaire, énergie",
        "Environnement, recyclage",
        "Equip. électriques, électroniques, optiques, précision",
        "Equipements mécaniques, machines",
        "Espaces verts, forêts, chasse",
        "Évènementiel, hôte(sse), accueil",
        "Hôtellerie, restauration",
        "Immobilier, architecture, urbanisme",
        "Import, export",
        "Industrie pharmaceutique",
        "Industrie, production, fabrication, autres",
        "Informatique, SSII, Internet",
        "Ingénierie, études développement",
        "Intérim, recrutement",
        "Location",
        "Luxe, cosmétiques",
        "Maintenance, entretien, service après vente",
        "Manutention",
        "Marketing, communication, médias",
        "Métallurgie, sidérurgie",
        "Nettoyage, sécurité, surveillance",
        "Papier, bois, caoutchouc, plastique, verre, tabac",
        "Produits de grande consommation",
        "Qualité, méthodes",
        "Recherche et développement",
        "Santé, pharmacie, hôpitaux, équipements médicaux",
        "Secrétariat",
        "Services aéroportuaires et maritimes",
        "Services autres",
        "Services collectifs et sociaux, services à la personne",
        "Sport, action culturelle et sociale",
        "Télécom",
        "Textile, habillement, cuir, chaussures",
        "Tourisme, loisirs",
        "Transports, logistique, services postaux"
    );

    private static final Map<Pattern, String> SECTOR_PATTERNS = Map.ofEntries(
        Map.entry(Pattern.compile("centre"), "Centres d´appel, hotline, call center"),
        Map.entry(Pattern.compile("btp"), "BTP, construction"),
        Map.entry(Pattern.compile("comptabilité|conseil|consulting"), "Conseil, audit, comptabilité"),
        Map.entry(Pattern.compile("intérim|recrutement|débutant|fraichement"), "Intérim, recrutement"),
        Map.entry(Pattern.compile("tourisme|hôtellerie|restauration"), "Tourisme, loisirs"),
        Map.entry(Pattern.compile("santé|pharmaceutique|médical"), "Santé, pharmacie, hôpitaux, équipements médicaux"),
        Map.entry(Pattern.compile("éducation|formation"), "Éducation, formation"),
        Map.entry(Pattern.compile("banque|assurance|finances"), "Banque, assurance, finances"),
        Map.entry(Pattern.compile("automobile"), "Automobile, matériels de transport, réparation"),
        Map.entry(Pattern.compile("informatique|offshoring"), "Informatique, SSII, Internet"),
        Map.entry(Pattern.compile("aéronautique|navale"), "Aéronautique, navale"),
        Map.entry(Pattern.compile("autres|extraction|mines|indifférent|^$"), "Services autres"),
        Map.entry(Pattern.compile("grande distribution|distribution"), "Distribution, vente, commerce de gros"),
        Map.entry(Pattern.compile("immobilier"), "Immobilier, architecture, urbanisme"),
        Map.entry(Pattern.compile("energie|gaz|electricité"), "Electricité, eau, gaz, nucléaire, énergie"),
        Map.entry(Pattern.compile("alimentaire|agroalimentaire"), "Agroalimentaire"),
        Map.entry(Pattern.compile("agriculture|environnement"), "Agriculture, pêche, aquaculture"),
        Map.entry(Pattern.compile("import|export"), "Import, export"),
        Map.entry(Pattern.compile("marketing|publicité|pub|communication|multimédia|audiovisuel|allemand|anglais"), "Marketing, communication, médias"),
        Map.entry(Pattern.compile("transports|transport|ferroviaire"), "Transports, logistique, services postaux"),
        Map.entry(Pattern.compile("textile"), "Textile, habillement, cuir, chaussures"),
        Map.entry(Pattern.compile("secrétariat"), "Secrétariat"),
        Map.entry(Pattern.compile("service après"), "Maintenance, entretien, service après vente"),
        Map.entry(Pattern.compile("electro-mécanique|equipements"), "Equipements mécaniques, machines"),
        Map.entry(Pattern.compile("ingénierie"), "Ingénierie, études développement"),
        Map.entry(Pattern.compile("ameublement"), "Ameublement, décoration"),
        Map.entry(Pattern.compile("papier|plasturgie"), "Papier, bois, caoutchouc, plastique, verre, tabac"),
        Map.entry(Pattern.compile("nettoyage"), "Nettoyage, sécurité, surveillance"),
        Map.entry(Pattern.compile("chimie"), "Chimie, pétrochimie, matières premières, mines"),
        Map.entry(Pattern.compile("luxe"), "Luxe, cosmétiques"),
        Map.entry(Pattern.compile("telecom"), "Télécom"),
        Map.entry(Pattern.compile("édition|imprimerie"), "Édition, imprimerie"),
        Map.entry(Pattern.compile("associatives"), "Activités associatives"),
        Map.entry(Pattern.compile("métallurgie "), "Métallurgie, sidérurgie"),
        Map.entry(Pattern.compile("juridique "), "Services collectifs et sociaux, services à la personne"),
        Map.entry(Pattern.compile("industrie métallurgique"), "Services autres"),
        Map.entry(Pattern.compile("equip"), "Equip. électriques, électroniques, optiques, précision")
    );
    private static String mapSector(String activitySector) {
      for (Map.Entry<Pattern, String> entry : SECTOR_PATTERNS.entrySet()) {
          if (entry.getKey().matcher(activitySector).find()) {
              return entry.getValue();
          }
      }
      return activitySector;
  }
  public static void parseActivitySector(List<Job> jobs){
    for (Job job : jobs) {
      String activitySector = job.getActivitySector();
      if (activitySector == null || activitySector.isBlank()) {
          job.setActivitySector("Services autres");
          continue;
      }
      activitySector = activitySector.toLowerCase().trim();
      activitySector = mapSector(activitySector);

      if (!AVAILABLE_SECTORS.contains(activitySector)) {
          job.setActivitySector(activitySector);
         
      }
  }
  }
}
