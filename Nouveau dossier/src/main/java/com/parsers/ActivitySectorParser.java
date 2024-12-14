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
        Map.entry(Pattern.compile("centre", Pattern.CASE_INSENSITIVE), "Centres d´appel, hotline, call center"),
        Map.entry(Pattern.compile("btp", Pattern.CASE_INSENSITIVE), "BTP, construction"),
        Map.entry(Pattern.compile("comptabilité|conseil|consulting", Pattern.CASE_INSENSITIVE), "Conseil, audit, comptabilité"),
        Map.entry(Pattern.compile("intérim|recrutement|débutant|fraichement", Pattern.CASE_INSENSITIVE), "Intérim, recrutement"),
        Map.entry(Pattern.compile("tourisme|hôtellerie|restauration", Pattern.CASE_INSENSITIVE), "Tourisme, loisirs"),
        Map.entry(Pattern.compile("santé|pharmaceutique|médical", Pattern.CASE_INSENSITIVE), "Santé, pharmacie, hôpitaux, équipements médicaux"),
        Map.entry(Pattern.compile("éducation|formation", Pattern.CASE_INSENSITIVE), "Éducation, formation"),
        Map.entry(Pattern.compile("banque|assurance|finances", Pattern.CASE_INSENSITIVE), "Banque, assurance, finances"),
        Map.entry(Pattern.compile("automobile", Pattern.CASE_INSENSITIVE), "Automobile, matériels de transport, réparation"),
        Map.entry(Pattern.compile("informatique|offshoring", Pattern.CASE_INSENSITIVE), "Informatique, SSII, Internet"),
        Map.entry(Pattern.compile("aéronautique|navale", Pattern.CASE_INSENSITIVE), "Aéronautique, navale"),
        Map.entry(Pattern.compile("autres|extraction|mines|indifférent|^$", Pattern.CASE_INSENSITIVE), "Services autres"),
        Map.entry(Pattern.compile("grande distribution|distribution", Pattern.CASE_INSENSITIVE), "Distribution, vente, commerce de gros"),
        Map.entry(Pattern.compile("immobilier", Pattern.CASE_INSENSITIVE), "Immobilier, architecture, urbanisme"),
        Map.entry(Pattern.compile("energie|gaz|electricité", Pattern.CASE_INSENSITIVE), "Electricité, eau, gaz, nucléaire, énergie"),
        Map.entry(Pattern.compile("alimentaire|agroalimentaire", Pattern.CASE_INSENSITIVE), "Agroalimentaire"),
        Map.entry(Pattern.compile("agriculture|environnement", Pattern.CASE_INSENSITIVE), "Agriculture, pêche, aquaculture"),
        Map.entry(Pattern.compile("import|export", Pattern.CASE_INSENSITIVE), "Import, export"),
        Map.entry(Pattern.compile("marketing|publicité|pub|communication|multimédia|audiovisuel|allemand|anglais", Pattern.CASE_INSENSITIVE), "Marketing, communication, médias"),
        Map.entry(Pattern.compile("transports|transport|ferroviaire", Pattern.CASE_INSENSITIVE), "Transports, logistique, services postaux"),
        Map.entry(Pattern.compile("textile", Pattern.CASE_INSENSITIVE), "Textile, habillement, cuir, chaussures"),
        Map.entry(Pattern.compile("secrétariat", Pattern.CASE_INSENSITIVE), "Secrétariat"),
        Map.entry(Pattern.compile("service après", Pattern.CASE_INSENSITIVE), "Maintenance, entretien, service après vente"),
        Map.entry(Pattern.compile("electro-mécanique|equipements", Pattern.CASE_INSENSITIVE), "Equipements mécaniques, machines"),
        Map.entry(Pattern.compile("ingénierie", Pattern.CASE_INSENSITIVE), "Ingénierie, études développement"),
        Map.entry(Pattern.compile("ameublement", Pattern.CASE_INSENSITIVE), "Ameublement, décoration"),
        Map.entry(Pattern.compile("papier|plasturgie", Pattern.CASE_INSENSITIVE), "Papier, bois, caoutchouc, plastique, verre, tabac"),
        Map.entry(Pattern.compile("nettoyage", Pattern.CASE_INSENSITIVE), "Nettoyage, sécurité, surveillance"),
        Map.entry(Pattern.compile("chimie", Pattern.CASE_INSENSITIVE), "Chimie, pétrochimie, matières premières, mines"),
        Map.entry(Pattern.compile("luxe", Pattern.CASE_INSENSITIVE), "Luxe, cosmétiques"),
        Map.entry(Pattern.compile("telecom", Pattern.CASE_INSENSITIVE), "Télécom"),
        Map.entry(Pattern.compile("édition|imprimerie", Pattern.CASE_INSENSITIVE), "Édition, imprimerie"),
        Map.entry(Pattern.compile("associatives", Pattern.CASE_INSENSITIVE), "Activités associatives"),
        Map.entry(Pattern.compile("métallurgie", Pattern.CASE_INSENSITIVE), "Métallurgie, sidérurgie"),
        Map.entry(Pattern.compile("juridique", Pattern.CASE_INSENSITIVE), "Services collectifs et sociaux, services à la personne"),
        Map.entry(Pattern.compile("industrie métallurgique", Pattern.CASE_INSENSITIVE), "Services autres"),
        Map.entry(Pattern.compile("equip", Pattern.CASE_INSENSITIVE), "Equip. électriques, électroniques, optiques, précision")
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
      String activitySector = job.getActivitySector().toLowerCase();
      if(AVAILABLE_SECTORS.contains(activitySector)){
        job.setActivitySector(activitySector);
      }
      else{
          activitySector = activitySector.trim();
          activitySector = mapSector(activitySector);
         if(activitySector!=null && AVAILABLE_SECTORS.contains(activitySector)){
            job.setActivitySector(activitySector);
         }
         else{
            job.setActivitySector(null);
         }
      }
  }
  }
}
