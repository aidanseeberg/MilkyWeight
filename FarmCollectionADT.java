/*
 * Outlines the necessary functions of the data structure.
 * A FarmCollection must be a scalable system to store Farm objects.
 * It must sort/search through Farm objects by their id.
 */
public interface FarmCollectionADT {
  
  /*
   * Adds an entry to the system with the given fields
   */
  public void insert(String farmID, Farm farm) throws IllegalNullKeyException;
  
  /*
   * Removes and returns an entry form the system
   * Returns null if the entry is not found
   */
  public boolean remove(String farmID) throws IllegalNullKeyException;
  
  /*
   *Returns the entry object with the given farm ID
   *Returns null if the entry is not found
   */
  public Farm get(String farmID) throws IllegalNullKeyException, KeyNotFoundException;
  
}
