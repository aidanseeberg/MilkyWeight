package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class FarmManager {

  public static FarmCollection farms;

  public FarmManager() {
    farms = new FarmCollection();
  }

  /**
   * Upload data .csv file and creates Entry objects to
   * 
   * @param inputFile
   */
  public void uploadData(File inputFile) {

    BufferedReader br = null;
    String line = "";

    try {

      br = new BufferedReader(new FileReader(inputFile));
      while ((line = br.readLine()) != null) {

        // use comma as separator
        String[] entryLine = line.split(",");
        try {
          Entry entry = new Entry(entryLine[0], entryLine[1],
              Double.parseDouble(entryLine[2]));

          farms.add(entry);
          System.out.println(entry.toString() + " added");

        } catch (Exception e) {
          System.out.println("entry not valid");
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public double[][] getFarmReport(String id, String year)
      throws IllegalNullKeyException, KeyNotFoundException {

    double[][] report = new double[12][3]; // column one is month, column two is
                                           // total weight, column three is
                                           // percentage
    for (int i = 0; i < 12; i++) {
      // first column
      report[i][0] = i + 1;
      // second columnm
      report[i][1] = farms.get(id).getWeightForMonth(i + 1, year);
      // third column
      double totalWeight = farms.GetAllWeightForMonth(i + 1, year);
      double farmWeight = farms.get(id).getWeightForMonth(i + 1, year);

      report[i][2] = 100 * (farmWeight / totalWeight);
    }
    return report;
  }
  
  /*
   * Displays list of total weight and percentage of total weight
   * for each farm for a given year
   */
  public Object[][] getAnnualReport(String year) {
    // Number of farms
    int numFarms = farms.numKeys();
    // List containing all farms from the FarmCollection
    // Order by natural comparator (compareTo in Farm.java)
    List<Farm> farmList = farms.farmList();
    farmList.sort(null);
    
    //Determine total weight for all farms for the given year
    double totalWeightForYear = 0.0;
    for (int i = 1; i <= 12; i++) {
      totalWeightForYear += farms.GetAllWeightForMonth(i, year);
    }
    
    Object[][] report = new Object[numFarms][3]; // column one is farmID, column two is
                                                 // total weight, column three is
                                                 // percentage of total weight 
    for (int i = 0; i < numFarms; i++) {
      Farm current = farmList.remove(0);
      double totalFarmWeight = 0;
      for (int j = 1; j <= 12; j++) {
        totalFarmWeight += current.getWeightForMonth(1, year);
      }
      
      // first column - Farm ID
      report[i][0] = current.getID(); // Next farm in sequence
      // second column - Farm's total weight for year
      report[i][1] = totalFarmWeight;
      // third column - Percentage of total weight for that year
      report[i][1] = totalFarmWeight/totalWeightForYear*100;
    }
    return report;
  }


}


