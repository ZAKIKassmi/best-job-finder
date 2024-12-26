package com.ai;

import java.util.ArrayList;

import com.main.TestJob;

import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class RandomForestPrediction {
    private RandomForest forest;
    private Instances trainingDataset;
    private ArrayList<Attribute> attributes;

    public RandomForestPrediction(int classIndex) {
        // Initialize the model
        forest = new RandomForest();
        setupAttributes(classIndex);
        
        // Configure RandomForest parameters
        forest.setNumIterations(100);  // Number of trees in the forest
        forest.setNumFeatures(3); // Number of features to consider for splits
    }

    private void setupAttributes(int classIndex) {
        attributes = new ArrayList<>();

        ArrayList<String> sectorValues = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            sectorValues.add(String.valueOf(i));
        }

        ArrayList<String> experienceValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            experienceValues.add(String.valueOf(i));
        }
        
        ArrayList<String> studyValues = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            studyValues.add(String.valueOf(i));
        }

        ArrayList<String> contractValues = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            contractValues.add(String.valueOf(i));
        }

        ArrayList<String> remoteValues = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            remoteValues.add(String.valueOf(i));
        }

        ArrayList<String> cityValues = new ArrayList<>();
        for (int i = 0; i < 43; i++) {
            cityValues.add(String.valueOf(i));
        }

        // Add all attributes
        attributes.add(new Attribute("activitySector", sectorValues));
        attributes.add(new Attribute("requiredExperience", experienceValues));
        attributes.add(new Attribute("studyLevel", studyValues));
        attributes.add(new Attribute("contractType", contractValues));
        attributes.add(new Attribute("remoteWork", remoteValues));
        attributes.add(new Attribute("city", cityValues));

        // Create empty training dataset
        trainingDataset = new Instances("JobData", attributes, 0);
        trainingDataset.setClassIndex(classIndex);
    }

    public void addTrainingData(TestJob job) {
        // Check if any value in the required fields is null
        Integer sectorValue = HashMapData.sectorMapName.get(job.getActivitySector().trim().toLowerCase());
        if (sectorValue == null)
            return;

        Integer experienceValue = HashMapData.experienceMapName.get(job.getRequiredExperience().trim().toLowerCase());
        if (experienceValue == null)
            return;

        Integer studyValue = HashMapData.studyMapName.get(job.getStudyLevel().trim().toLowerCase());
        if (studyValue == null)
            return;

        Integer contractValue = HashMapData.contractMapName.get(job.getContractType().trim().toLowerCase());
        if (contractValue == null)
            return;

        Integer remoteValue = HashMapData.remoteMapName.get(job.getRemoteWork().trim().toLowerCase());
        if (remoteValue == null)
            return;

        Integer cityValue = HashMapData.cityMapName.get(job.getCity().toLowerCase());
        if (cityValue == null)
            return;

        Instance instance = new DenseInstance(6);
        instance.setDataset(trainingDataset);

        instance.setValue(attributes.get(0), sectorValue);
        instance.setValue(attributes.get(1), experienceValue);
        instance.setValue(attributes.get(2), studyValue);
        instance.setValue(attributes.get(3), contractValue);
        instance.setValue(attributes.get(4), remoteValue);
        instance.setValue(attributes.get(5), cityValue);

        trainingDataset.add(instance);
    }

    public void trainModel() throws Exception {
        forest.buildClassifier(trainingDataset);
    }

    public String predictActivitySector(String experience,
            String study, String remote, String contractType, String city) throws Exception {
        Instance testInstance = new DenseInstance(6);
        testInstance.setDataset(trainingDataset);

        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);

        double prediction = forest.classifyInstance(testInstance);
        return trainingDataset.classAttribute().value((int) prediction);
    }

    public String predictExperience(String sector,
            String study, String remote, String contractType, String city) throws Exception {
        Instance testInstance = new DenseInstance(6);
        testInstance.setDataset(trainingDataset);

        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(2), study);
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);

        double prediction = forest.classifyInstance(testInstance);
        return trainingDataset.classAttribute().value((int) prediction);
    }

    public String predictStudyLevel(String sector,
            String experience, String remote, String contractType, String city) throws Exception {
        Instance testInstance = new DenseInstance(6);
        testInstance.setDataset(trainingDataset);

        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);

        double prediction = forest.classifyInstance(testInstance);
        return trainingDataset.classAttribute().value((int) prediction);
    }

    public String predictContractType(String sector, String experience,
            String study, String remote, String city) throws Exception {
        Instance testInstance = new DenseInstance(6);
        testInstance.setDataset(trainingDataset);

        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);

        double prediction = forest.classifyInstance(testInstance);
        return trainingDataset.classAttribute().value((int) prediction);
    }

    public String predictRemoteWork(String sector, String experience,
            String study, String contractType, String city) throws Exception {
        Instance testInstance = new DenseInstance(6);
        testInstance.setDataset(trainingDataset);

        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(5), city);

        double prediction = forest.classifyInstance(testInstance);
        return trainingDataset.classAttribute().value((int) prediction);
    }

    public String predictCity(String sector, String experience,
            String study, String contractType, String remoteWork) throws Exception {
        Instance testInstance = new DenseInstance(6);
        testInstance.setDataset(trainingDataset);

        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(4), remoteWork);

        double prediction = forest.classifyInstance(testInstance);
        return trainingDataset.classAttribute().value((int) prediction);
    }

    // Get information about the random forest model
    public String getModelInfo() {
        return forest.toString();
    }
}
