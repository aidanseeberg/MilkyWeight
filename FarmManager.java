package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * The class that handles data parsing and reports. Computes report to pass
 * along to the UI to display to the user.
 * 
 * @author Aidan Seeberg
 *
 */
public class FarmManager {

  public static FarmCollection farms;

  /**
   * Default constructor
   */
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

        // System.out.println(line);
        // use comma as separator
        String[] entryLine = line.split(",");
        Entry entry = null;
        try {
          entry = new Entry(entryLine[0], entryLine[1],
              Double.parseDouble(entryLine[2]));



          if (entry.getDate().split("-").length != 3)
            throw new IllegalArgumentException();
          if (entry.getID().equals(""))
            throw new IllegalArgumentException();
          if (!entry.getID().contains("Farm"))
            throw new IllegalArgumentException();
          // System.out.println(entry);
          farms.add(entry);
          // System.out.println(entry.toString() + " added");

        } catch (ArrayIndexOutOfBoundsException e1) {
          System.out.println("array index out of bounds");

        } catch (NumberFormatException e) {
          System.out
              .println("entry's weight cannot be parsed, skipping entry.");
        } catch (IllegalArgumentException e2) {
          System.out.println("data not in correct format");

        } catch (IllegalNullKeyException e3) {
          System.out.println("cannot add null entry");

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


  /**
   * Returns a Farm Report given a specific id and year
   * 
   * @param id
   * @param year
   * @return
   * @throws IllegalNullKeyException
   * @throws KeyNotFoundException
   */
  public String[][] getFarmReport(String id, String year)
      throws IllegalNullKeyException, KeyNotFoundException {

    String[][] report = new String[12][3]; // column one is month, column two is
                                           // total weight, column three is
                                           // percentage
    for (int i = 0; i < 12; i++) {
      // first column
      report[i][0] = "" + (i + 1);
      // second column
      report[i][1] = "" + farms.get(id).getWeightForMonth(i + 1, year);
      // third column
      double totalWeight = farms.GetAllWeightForMonth(i + 1, year);
      double farmWeight = farms.get(id).getWeightForMonth(i + 1, year);

      double percentage = (farmWeight / totalWeight) * 100;
      String shortDec = String.format("%.2f", percentage);
      report[i][2] = shortDec;
    }
    return report;
  }

  /*
   * Displays list of total weight and percentage of total weight for each farm
   * for a given year
   */
  public String[][] getAnnualReport(String year) {
    // Number of farms
    int numFarms = farms.numKeys();
    // List containing all farms from the FarmCollection
    // Order by natural comparator (compareTo in Farm.java)
    List<Farm> farmList = farms.farmList();
    farmList.sort(null);

    // Determine total weight for all farms for the given year
    double totalWeightForYear = 0.0;
    for (int i = 1; i <= 12; i++) {
      totalWeightForYear += farms.GetAllWeightForMonth(i, year);
    }

    String[][] report = new String[numFarms][3]; // column one is farmID, column
                                                 // two is
                                                 // total weight, column three
                                                 // is
                                                 // percentage of total weight
    for (int i = 0; i < numFarms; i++) {
      Farm current = farmList.remove(0);
      double totalFarmWeight = 0;
      for (int j = 1; j <= 12; j++) {
        totalFarmWeight += current.getWeightForMonth(j, year);
      }

      // first column - Farm ID
      report[i][0] = current.getID(); // Next farm in sequence
      // second column - Farm's total weight for year
      report[i][1] = "" + totalFarmWeight;
      // third column - Percentage of total weight for that year
      double percentage = (totalFarmWeight / totalWeightForYear) * 100;
      String shortDec = String.format("%.2f", percentage);
      report[i][2] = shortDec;
    }
    return report;
  }

  /**
   * Produces a monthly report given a specific year and month
   * 
   * @param year
   * @param month
   * @return
   */
  public String[][] getMonthlyReport(String year, String month) {
    // Number of farms
    int numFarms = farms.numKeys();
    // Get all farms in a list
    List<Farm> farmList = farms.farmList();
    farmList.sort(null);

    double totalWeightForMonth = 0.0;

    int monthInInt = Integer.parseInt(month);

    totalWeightForMonth += farms.GetAllWeightForMonth(monthInInt, year);
    /**
     * creates report String each row represents a farm column 1 = ID column 2 =
     * Weight of this farm column 3 = Weight of this farm/Total Weight
     */
    String[][] report = new String[farmList.size()][3];

    for (int i = 0; i < numFarms; i++) {
      // Gets current farm
      Farm current = farmList.remove(0);
      // Adds its ID to the array
      report[i][0] = (String) current.getID();
      // Adds its weight to the array
      double currWeight = current.getWeightForMonth(monthInInt, year);
      report[i][1] = "" + current.getWeightForMonth(monthInInt, year);
      // Adds its percentage of total to the array
      double percentage = (currWeight / totalWeightForMonth) * 100;
      String shortDec = String.format("%.2f", percentage);
      report[i][2] = shortDec;

    }
    return report;
  }

  /*
   * Prompt user for start date (year-month-day) and end month-day, Then display
   * the total milk weight per farm and the percentage of the total for each
   * farm over that date range. The list must be sorted by Farm ID, or you can
   * prompt for ascending or descending order by weight or percentage.
   */
  public String[][] getDateRangeReport(String start, String end) {
    // List containing all farms from the FarmCollection
    // Order by natural comparator (compareTo in Farm.java)
    List<Farm> farmList = farms.farmList();
    farmList.sort(null);
    /*
     * create report String each row represents a farm column 1 = ID column 2 =
     * Weight of this farm in range column 3 = Weight of this farm/Total Weight
     * in range
     */
    int numFarms = farmList.size();
    String[][] report = new String[numFarms][3];

    // Determine total weight over range
    double totalWeight = 0;
    for (int i = 0; i < numFarms; i++) {
      totalWeight += farmList.get(i).getFarmWeightForRange(start, end);
    }

    // Create report array
    for (int i = 0; i < numFarms; i++) {
      // Gets current farm
      Farm current = farmList.remove(0);
      // Adds its ID to the array
      report[i][0] = (String) current.getID();
      // Adds its weight to the array
      double currWeight = current.getFarmWeightForRange(start, end);
      report[i][1] = "" + currWeight;
      // Adds its percentage of total to the array
      double percentage = ((currWeight / totalWeight) * 100);
      String shortDec = String.format("%.2f", percentage);
      report[i][2] = shortDec;
    }
    return report;
  }

}
