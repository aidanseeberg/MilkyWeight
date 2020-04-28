package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FarmManager {

  public static FarmCollection farms;


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

          // farms.add(entry);
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


  public double[][] getFarmReport(String id, String year) {

    double[][] report = new double[12][3]; // column one is month, column two is
                                           // total weight, column three is
                                           // percentage
    for (int i = 0; i < 12; i++) {
      // first column
      report[i][0] = i + 1;
      // second columnm
      // report[i][1] = farms.get(id).
      // third column
      // report[i][1] = farms.get(id).getWeightOfMonth()
    }
    return report;
  }


}


