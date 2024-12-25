package com.ai;

import java.util.ArrayList;
import java.util.Random;

import com.main.TestJob;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

public class Classifier {
  
  public static void TrainModel(ArrayList<TestJob> jobs) {
    ArrayList<Attribute> attributes = new ArrayList<>();
    attributes.add(new Attribute("city", (ArrayList<String>) null));
    attributes.add(new Attribute("activitySector", (ArrayList<String>) null));
    attributes.add(new Attribute("requiredExperience", (ArrayList<String>) null));
    attributes.add(new Attribute("studyLevel", (ArrayList<String>) null));
    attributes.add(new Attribute("contractType", (ArrayList<String>) null));
    attributes.add(new Attribute("remoteWork", (ArrayList<String>) null));

    Instances dataset = new Instances("Jobs", attributes, 0);
    dataset.setClassIndex(attributes.size() - 6);
    int count = 0;
    for (TestJob job : jobs) {
      if (job.getCity() == null || job.getActivitySector() == null || job.getRequiredExperience() == null
          || job.getStudyLevel() == null || job.getContractType() == null || job.getRemoteWork() == null) {
        continue;
      }
      count++;
      double[] instanceValues = new double[attributes.size()];
      instanceValues[0] = dataset.attribute(0).addStringValue(job.getCity());
      instanceValues[1] = dataset.attribute(1).addStringValue(job.getActivitySector());
      instanceValues[2] = dataset.attribute(2).addStringValue(job.getRequiredExperience());
      instanceValues[3] = dataset.attribute(3).addStringValue(job.getStudyLevel());
      instanceValues[4] = dataset.attribute(4).addStringValue(job.getContractType());
      instanceValues[5] = dataset.attribute(5).addStringValue(job.getRemoteWork());

      dataset.add(new DenseInstance(1.0, instanceValues));
    }

    System.out.println("Total valid jobs: "+count);

    try {
      // Apply StringToNominal filter
      StringToNominal filter = new StringToNominal();
      filter.setInputFormat(dataset);
      filter.setOptions(new String[]{"-R", "first-last"});
      Instances filteredDataset = Filter.useFilter(dataset, filter);

      Object[][] classifiers = new Object[][] {
            {new J48(), "Decision Tree"},
            {new NaiveBayes(), "Naive Bayes"},
            {new SMO(), "Support Vector Machine (SMO)"},
            {new IBk(), "K-Nearest Neighbors (IBk)"}
      };
      for (Object[] classifier : classifiers) {
        System.out.println("\n===========================================");
        System.out.println("Classifier: " + classifier[1]);
        System.out.println("===========================================");
        
        // Perform cross-validation
        Evaluation evaluate = new Evaluation(filteredDataset);
        evaluate.crossValidateModel((weka.classifiers.Classifier)classifier[0], 
                                 filteredDataset, 10, new Random(1));
        
        System.out.println("\nSummary: \n");
        System.out.println(evaluate.toSummaryString());
        System.out.println("\nConfusion Matrix: \n");
        System.out.println(evaluate.toMatrixString());
        System.out.println("Predictions");
        
        
    }

      
    } catch (Exception e) {
      System.out.println("Error while training the model: " + e.getMessage());
      System.out.println(e.getMessage());
    }
  }

  
}