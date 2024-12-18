package com.ai;

import java.util.ArrayList;
import java.util.Random;

import com.main.TestJob;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

public class Classifier {
  
  // Method to train the model and predict next jobs
  public static void TrainModel(ArrayList<TestJob> jobs) {
    ArrayList<Attribute> attributes = new ArrayList<>();
    attributes.add(new Attribute("city", (ArrayList<String>) null));
    attributes.add(new Attribute("activitySector", (ArrayList<String>) null));
    attributes.add(new Attribute("requiredExperience", (ArrayList<String>) null));
    attributes.add(new Attribute("studyLevel", (ArrayList<String>) null));
    attributes.add(new Attribute("contractType", (ArrayList<String>) null));
    attributes.add(new Attribute("remoteWork", (ArrayList<String>) null));

    Instances dataset = new Instances("Jobs", attributes, 0);
    dataset.setClassIndex(attributes.size() - 1);

    for (TestJob job : jobs) {
      if (job.getCity() == null || job.getActivitySector() == null || job.getRequiredExperience() == null
          || job.getStudyLevel() == null || job.getContractType() == null || job.getRemoteWork() == null) {
        continue;
      }

      double[] instanceValues = new double[attributes.size()];
      instanceValues[0] = dataset.attribute(0).addStringValue(job.getCity());
      instanceValues[1] = dataset.attribute(1).addStringValue(job.getActivitySector());
      instanceValues[2] = dataset.attribute(2).addStringValue(job.getRequiredExperience());
      instanceValues[3] = dataset.attribute(3).addStringValue(job.getStudyLevel());
      instanceValues[4] = dataset.attribute(4).addStringValue(job.getContractType());
      instanceValues[5] = dataset.attribute(5).addStringValue(job.getRemoteWork());

      dataset.add(new DenseInstance(1.0, instanceValues));
    }

    try {
      // Apply StringToNominal filter
      StringToNominal filter = new StringToNominal();
      filter.setInputFormat(dataset);
      filter.setOptions(new String[]{"-R", "first-last"});
      Instances filteredDataset = Filter.useFilter(dataset, filter);

      // Train the J48 model
      J48 tree = new J48();
      tree.buildClassifier(filteredDataset);
      System.out.println("Model trained successfully!");

      Evaluation eval_roc = new Evaluation(filteredDataset);
      eval_roc.crossValidateModel(tree, filteredDataset, 10, new Random(1));
      // System.out.println(eval_roc.toSummaryString());
      // double[][] testingjobs = eval_roc.confusionMatrix();
      // System.out.println(eval_roc.toMatrixString());
      
      // Predict next 20 jobs
      PredictNextJobs(tree, filteredDataset);

    } catch (Exception e) {
      System.out.println("Error while training the model: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Method to predict characteristics of the next 20 jobs
  public static void PredictNextJobs(J48 tree, Instances dataset) {
    try {
      System.out.println("\nPredicted next 20 job characteristics:\n");
      
      for (int jobNum = 1; jobNum <= 20; jobNum++) {
        // Create a new instance for each job
        DenseInstance nextJob = createNewJobInstance(dataset);
        
        // Get probability distribution for the instance
        double[] probabilities = tree.distributionForInstance(nextJob);
        
        // Print job number
        System.out.println("Job Offer #" + jobNum + ":");
        System.out.println("--------------------");
        
        // Print job attributes
        for (int i = 0; i < nextJob.numAttributes(); i++) {
          System.out.println(dataset.attribute(i).name() + ": " + 
              (nextJob.isMissing(i) ? "Missing" : nextJob.stringValue(i)));
        }

        // Print probability distribution
        System.out.println("\nPrediction confidence:");
        for (int i = 0; i < probabilities.length; i++) {
          System.out.printf("Class %d: %.2f%%\n", i, probabilities[i] * 100);
        }
        System.out.println("\n");
      }
      
    } catch (Exception e) {
      System.out.println("Error in job prediction: " + e.getMessage());
      e.printStackTrace();
    }
  }

  // Helper method to create a new job instance
  private static DenseInstance createNewJobInstance(Instances dataset) {
    DenseInstance newJob = new DenseInstance(dataset.numAttributes());
    newJob.setDataset(dataset);

    // Generate values for each attribute based on most common values in dataset
    for (int i = 0; i < dataset.numAttributes(); i++) {
      Attribute attr = dataset.attribute(i);
      if (attr.type() == Attribute.NOMINAL) {
        int numValues = attr.numValues();
        if (numValues > 0) {
          // Randomly select a value from the possible values for this attribute
          int randomIndex = (int) (Math.random() * numValues);
          newJob.setValue(i, attr.value(randomIndex));
        }
      }
    }

    return newJob;
  }
}