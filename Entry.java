package application;

/**
 * Class that contains data for each unique data entry. Each entry contains a
 * Farm id, a date, and a weight
 * 
 * @author Aidan Seeberg
 *
 */
public class Entry {

  private String id;
  private String date;
  private double weight;

  /**
   * Parameterized constructor of the entry ckass
   * 
   * @param date
   * @param id
   * @param weight
   */
  public Entry(String date, String id, double weight) {
    this.date = date;
    this.id = id;
    this.weight = weight;
  }

  /**
   * returns farm id
   * 
   * @return
   */
  public String getID() {
    return id;
  }

  /**
   * returns year-month-day
   * 
   * @return
   */
  public String getDate() {
    return date;
  }

  /**
   * returns double
   * 
   * @return
   */
  public double getWeight() {
    return weight;
  }

  /**
   * returns a string representation of the Entry object
   */
  public String toString() {

    return date + ": " + id + ", " + weight;
  }
}
