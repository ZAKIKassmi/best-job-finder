package com.main;

public class Job {
  private String jobTitle;
  private String activitySector;
  private String function;
  private String requiredExperience;
  private String studyLevel;
  private String contractType;
  private String searchedProfile;
  private String remoteWork;
  private String city;
  private String jobDescription;

  private String siteWeb;
  private String imageUrl;
  private String jobPageUrl;
  private String entreprise;
  private String entrepriseAddress;
  private String publicationDate;
  private String entrepriseDescription;
  private String softSkills;
  private Double salary;
  private String region;
  private String hardSkills;
  private String language;

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  public String getPublicationDate() {
    return this.publicationDate;
  }

  public void setSearchedProfile(String searchedProfile) {
    this.searchedProfile = searchedProfile;
  }

  public String getSearchedProfile() {
    return this.searchedProfile;
  }

  public void setJobDescription(String jobDescription) {
    this.jobDescription = jobDescription;
  }

  public String getJobDescription() {
    return this.jobDescription;
  }

  public void setRemoteWork(String remoteWork) {
    this.remoteWork = remoteWork;
  }

  public String getRemoteWork() {
    return this.remoteWork;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCity() {
    return this.city;
  }

  public String getJobTitle() {
    return this.jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getActivitySector() {
    return this.activitySector;
  }

  public void setActivitySector(String activitySector) {
    this.activitySector = activitySector;
  }

  public String getFunction() {
    return this.function;
  }

  public void setFunction(String function) {
    this.function = function;
  }

  public String getRequiredExperience() {
    return this.requiredExperience;
  }

  public void setRequiredExperience(String requiredExperience) {
    this.requiredExperience = requiredExperience;
  }

  public String getStudyLevel() {
    return this.studyLevel;
  }

  public void setStudyLevel(String studyLevel) {
    this.studyLevel = studyLevel;
  }

  public String getContractType() {
    return this.contractType;
  }

  public void setContractType(String contractType) {
    this.contractType = contractType;
  }

  @Override
  public String toString() {
    return "{" +
        " jobTitle='" + getJobTitle() + "'" +
        ",\n city='" + getCity() + "'" +
        ",\n activitySector='" + getActivitySector() + "'" +
        ",\n function='" + getFunction() + "'" +
        ",\n requiredExperience='" + getRequiredExperience() + "'" +
        ",\n studyLevel='" + getStudyLevel() + "'" +
        ",\n contractType='" + getContractType() + "'" +
        ",\n searchedProfile='" + getSearchedProfile() + "'" +
        ",\n jobDescription='" + getJobDescription() + "'" +
        ",\n remoteWork='" + getRemoteWork() + "'" +
        ",\n entreprise: '" + getEntreprise() + "'" +
        ",\n address: '" + getEntrepriseAddress() + "'" +
        ",\n soft skills: '" + getSoftSkills() + "'" +
        ",\n hard skills: '" + getHardSkills() + "'" +
        ",\n image: '" + getImageUrl() + "'" +
        ",\n description de l'entreprise: '" + getEntrepriseDescription() + "'" +
        ",\n date: '" + getPublicationDate() + "'" +
        ",\n langue: '" + getLanguage() + "'" +
        ",\n salary: '" + getSalary() + "'" +
        "}";
  }

  public String newToString() {
    return "{" +
        " entreprise: '" + getEntreprise() + "'" +
        ",\n address: '" + getEntrepriseAddress() + "'" +
        ",\n soft skills: '" + getSoftSkills() + "'" +
        ",\n image: '" + getImageUrl() + "'" +
        ",\n description de l'entreprise: '" + getEntrepriseDescription() + "'" +
        ",\n date: '" + getPublicationDate() + "'" +
        ",\n langue: '" + getLanguage() + "'" +
        ",\n salary: '" + getSalary() + "'" +
        "}";
  }

  public String getEntreprise() {
    return entreprise;
  }

  public void setEntreprise(String entreprise) {
    this.entreprise = entreprise;
  }

  public String getEntrepriseAddress() {
    return entrepriseAddress;
  }

  public void setEntrepriseAddress(String entrepriseAddress) {
    this.entrepriseAddress = entrepriseAddress;
  }

  public String getSoftSkills() {
    return softSkills;
  }

  public void setSoftSkills(String softSkills) {
    this.softSkills = softSkills;
  }

  public String getJobPageUrl() {
    return jobPageUrl;
  }

  public void setJobPageUrl(String jobPageUrl) {
    this.jobPageUrl = jobPageUrl;
  }

  public String getSiteWeb() {
    return siteWeb;
  }

  public void setSiteWeb(String siteWeb) {
    this.siteWeb = siteWeb;
  }

  public Double getSalary() {
    return salary;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getHardSkills() {
    return hardSkills;
  }

  public void setHardSkills(String hardSkills) {
    this.hardSkills = hardSkills;
  }

  public String getEntrepriseDescription() {
    return entrepriseDescription;
  }

  public void setEntrepriseDescription(String entrepriseDescription) {
    this.entrepriseDescription = entrepriseDescription;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

}
