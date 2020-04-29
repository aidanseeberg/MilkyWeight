package application;

public class Farm implements Comparable<Farm> {

  private String iD;
  private HashTable<String, Double> entries;

  // Creates a new Farm object with the given ID number
  public Farm(String id) {
    iD = id;
    entries = new HashTable<String, Double>();
  }

  /**
   * returns the ID number of this farm
   * 
   * @return
   */
  public String getID() {
    return iD;
  }

  /**
   * Adds entry to the farm's hashtable
   * 
   * @param date
   * @param weight
   * @throws IllegalNullKeyException
   */
  public void addEntry(String date, double weight)
      throws IllegalNullKeyException {
    entries.insert(date, weight);
  }

  /**
   * Gets an entry based on the given date from the farm's hastable
   * 
   * @param date
   * @return
   * @throws IllegalNullKeyException
   * @throws KeyNotFoundException
   */
  public Entry getEntry(String date)
      throws IllegalNullKeyException, KeyNotFoundException {
    double weight = entries.get(date);

    Entry theEntry = new Entry(date, this.getID(), weight);

    return theEntry;
  }


  public double getWeightForMonth(int month, String year) {
    return entries.getMonthWeight(entries, month, year);
  }
  
  /*
   * Obtains the farm's total weight over the given start (year-month-day) and end (month-day)
   */
  public double getFarmWeightForRange(String start, String end) {
    return entries.getWeightForRange(start, end);
  }
  
  /*
   * Compares Farms by ID
   */
  @Override
  public int compareTo(Farm f) {

    String a = getID();
    String b = f.getID();

    Integer x = 0;
    Integer y = 0;

    if (getID().contains("Farm")) {
      a = getID().split(" ")[1];
      try {
        x = Integer.parseInt(a);
      } catch (NumberFormatException e) {
      }

    }
    if (f.getID().contains("Farm")) {
      b = f.getID().split(" ")[1];
      try {
        y = Integer.parseInt(b);
      } catch (NumberFormatException e2) {
      }
    }
    if (x.compareTo(y) != 0)
      return x.compareTo(y);

    return a.compareTo(b);
  }



}
