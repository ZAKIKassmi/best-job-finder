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





 

  public void setSearchedProfile(String searchedProfile){
    this.searchedProfile = searchedProfile;
  }

  public String getSearchedProfile(){
    return this.searchedProfile;
  }
  
  public void setJobDescription(String jobDescription){
    this.jobDescription = jobDescription;
  }

  public String getJobDescription(){
    return this.jobDescription;
  }

  public void setRemoteWork(String remoteWork){
    this.remoteWork = remoteWork;
  }

  public String getRemoteWork(){
    return this.remoteWork;
  }

  public void setCity(String city){
    this.city = city;
  }

  public String getCity(){
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
      ",\n remoteWork='" + getRemoteWork() + "'\n" +
      "}";
  }
  
  
}
