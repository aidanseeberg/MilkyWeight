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


  public int getWeightForMonth(int month, String year) {
    return entries.getMonthWeight(entries, month, year);
  }
  
  /*
   * Compares Farms by ID
   */
  @Override
  public int compareTo(Farm f) {
    return getID().compareTo(f.getID());
  }



}
