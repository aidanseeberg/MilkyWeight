package application;

/**
 * Generic HashTable Holds key value pairs of type K and V
 * 
 * @author zolo Also holds its current capacity and loadFactorThreshold which
 *         once reached, expands the size of the table
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> {

  private int currentCapacity; // current max capacity of the table
  private double loadFactorThreshold; // load factor threshold of the table
                                      // which once exceeded,
                                      // expands the table
  private int numberOfItems; // current number of items in the table
  private Object[] table; // the table in which the DataNodes are put in

  /**
   * The private data node class which are what makes up the hashTable
   * 
   * @author zolo
   *
   */
  private class DataNode {
    private K key;
    private V value;
    private DataNode nextNode;

    /**
     * constructor of the DataNode
     * 
     * @param key
     * @param value
     */
    private DataNode(K key, V value) {
      this.key = key;
      this.value = value;
      nextNode = null;
    }

    /**
     * 
     * @return the data value of the node
     */
    private V getData() {
      return value;
    }

    /**
     * 
     * @return key of this node
     */
    private K getKey() {
      return key;
    }

    /**
     * 
     * @return next node if it exists, if it does not, return null
     */
    private DataNode getNextNode() {
      return nextNode;
    }

    /**
     * Sets the current node's nextNode.
     * 
     * @param insertNode
     */
    private void setNextNode(DataNode insertNode) {
      this.nextNode = insertNode;
    }

    /**
     * Checks if the node has a nextNode
     * 
     * @return false if null true if the node has a nextNode
     */
    private boolean checkIfNextNull() {
      if (this.getNextNode() == null) {
        return true;
      }
      return false;
    }

    /**
     * Sets data of the current node
     * 
     * @param data
     */
    private void setData(V data) {
      value = data;
    }
  }

  /**
   * default constructor for the HashTable
   */
  public HashTable() {
    loadFactorThreshold = 0.75;
    currentCapacity = 31;
    numberOfItems = 0;
    table = new Object[31];
  }

  // initial capacity and load factor threshold
  // threshold is the load factor that causes a resize and rehash
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    this.currentCapacity = initialCapacity;
    this.loadFactorThreshold = loadFactorThreshold;
    numberOfItems = 0;
    table = new Object[initialCapacity];
  }

  /**
   * returns the hashCode of the key
   * 
   * @param key
   * @return int
   */
  private int getHashCode(K key) {
    return key.hashCode();
  }

  /**
   * returns the hashIndex of the hashCode
   * 
   * @param hashCode
   * @return int
   */
  private int getHashIndex(int hashCode) {
    return Math.abs(hashCode % currentCapacity);
  }

  /**
   * Increases the table capacity by currentCapacity*2 + 1
   * 
   * @param capacity
   * @throws IllegalNullKeyException when inserting the existing nodes into the
   *                                 new expanded table
   */
  private void increaseTableSize(int capacity) throws IllegalNullKeyException {
    Object[] tempTable = table;
    Object[] expandedTable = new Object[(capacity * 2) + 1];
    this.currentCapacity = (capacity * 2) + 1;
    table = expandedTable;
    rehash(tempTable);

  }


  /**
   * rehash's the current table's elements into the new expanded table.
   * 
   * @param tempTable
   * @throws IllegalNullKeyException
   */
  @SuppressWarnings("unchecked")
  private void rehash(Object[] tempTable) throws IllegalNullKeyException {
    int count = 0;
    int i = 0;
    DataNode currentNode = null;
    while (i < tempTable.length) {
      if (tempTable[i] != null) {
        currentNode = (DataNode) tempTable[i];
        this.insert(currentNode.getKey(), currentNode.getData());
        count++;
        if (currentNode.getNextNode() != null) {
          while (!currentNode.checkIfNextNull()) {
            DataNode childNode = currentNode.getNextNode();
            this.insert(childNode.getKey(), childNode.getData());
            currentNode.setNextNode(null);
            currentNode = childNode;
            count++;
          }
        }
      }
      i++;
    }
  }

  /**
   * inserts a new element into the table by first finding its hashIndex and
   * then inserting it to that location
   */
  @SuppressWarnings("unchecked")
  public void insert(K key, V value) throws IllegalNullKeyException {
    if (this.getLoadFactor() > loadFactorThreshold) {
      this.increaseTableSize(currentCapacity);
    }
    if (key == null) {
      throw new IllegalNullKeyException("Cannot insert null key");
    }
    int hashCode = getHashCode(key);
    int hashIndex = getHashIndex(hashCode);
    if (table[hashIndex] == null) {
      table[hashIndex] = new DataNode(key, value);
    }
    DataNode foundNode = (DataNode) table[hashIndex];
    if ((table[hashIndex] != null) && foundNode.getKey().equals(key)) {
      foundNode.setData(value);
    } else {
      @SuppressWarnings("unchecked")
      DataNode currentNode = (DataNode) table[hashIndex];
      boolean dataSet = false;
      while (!dataSet && currentNode != null) {
        if (currentNode.getKey().equals(key)) {
          currentNode.setData(value);
          dataSet = true;
        } else {
          currentNode = currentNode.getNextNode();
        }
      }
      if (!dataSet) {
        currentNode = (DataNode) table[hashIndex];
        while (currentNode.getNextNode() != null) {
          currentNode = currentNode.getNextNode();
        }
        DataNode nextNode = new DataNode(key, value);
        currentNode.setNextNode(nextNode);
      }

    }
    numberOfItems++;

  }

  /**
   * Removes the designated element corresponding to the param key throws
   * IllegalNullKeyException if key is null returns false if key is not found
   * and true if the key is found and removed
   */
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null) {
      throw new IllegalNullKeyException("Illegal Null Key");
    }
    int hashCode = getHashCode(key);
    int hashIndex = getHashIndex(hashCode);
    if (table[hashIndex] == null) {
      return false;
    }
    @SuppressWarnings("unchecked")
    DataNode indexNode = (DataNode) table[hashIndex];
    if (indexNode.getKey().equals(key) && indexNode.getNextNode() == null) {
      numberOfItems--;
      table[hashIndex] = null;
      return true;
    } else {
      while (indexNode.getNextNode() != null) {
        DataNode childNode = indexNode.getNextNode();
        if (childNode.getKey().equals(key)) {
          numberOfItems--;
          indexNode.setNextNode(childNode.getNextNode());
        } else {
          indexNode = indexNode.getNextNode();
        }
        return true;
      }

    }
    return false;
  }

  /**
   * Returns the data value of the key throws IllegalNullKeyException is key is
   * null throws KeyNotFoundException is key is not found in the table
   */
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {

    if (key == null) {
      throw new IllegalNullKeyException("Cannot get null key");
    }
    if (numberOfItems == 0) {
      throw new KeyNotFoundException("Key not found");
    }
    int hashCode = getHashCode(key);
    int hashIndex = getHashIndex(hashCode);

    if (table[hashIndex] == null) {
      throw new KeyNotFoundException("Key not found");
    } else {
      @SuppressWarnings("unchecked")
      DataNode foundNode = (DataNode) table[hashIndex];
      if (foundNode.getKey().equals(key)) {
        return foundNode.getData();
      } else if (foundNode.getNextNode() != null) {
        while (foundNode != null) {
          if (foundNode.getKey().equals(key)) { // changed from
                                                // foundNode.getNextNode
            return foundNode.getData(); // changed from foundNode.getNextNode
          } else {
            foundNode = foundNode.getNextNode();
          }
        }
      }
      throw new KeyNotFoundException("Key not found");
    }
  }

  /**
   * returns current number of keys in the table
   */
  public int numKeys() {
    return numberOfItems;
  }

  /**
   * returns the load factor threshold for this hashtable
   */
  public double getLoadFactorThreshold() {
    return this.loadFactorThreshold;
  }

  /**
   * returns the current load factor of the table
   */
  public double getLoadFactor() {
    return (double) numberOfItems / (double) currentCapacity;
  }

  /**
   * returns the current capacity of the table
   */
  public int getCapacity() {
    return currentCapacity;
  }

  /**
   * Tells the grader what kind of collision resolution scheme
   */
  public int getCollisionResolution() {
    return 5;
  }

  @SuppressWarnings("unchecked")
  public int getAllWeight(HashTable<String, Double> entries) {
    int totalWeight = 0;
    int sizeOfTable = numKeys();

    for (int i = 0; i < sizeOfTable; i++) {
      if (entries.table[i] == null) {

      } else {
        boolean lastNodeFound = false;
        DataNode currNode = (HashTable<K, V>.DataNode) entries.table[i];
        while (!lastNodeFound) {
          totalWeight = totalWeight + (Double) currNode.getData();
          currNode = currNode.getNextNode();
          if (currNode == null) {
            lastNodeFound = true;
          } else {

          }
        }
      }
    }
    return totalWeight;
  }

  @SuppressWarnings("unchecked")
  public double getMonthWeight(HashTable<String, Double> entries, int month,
      String year) {
    double totalWeight = 0;
    int sizeOfTable = entries.table.length;

    for (int i = 0; i < sizeOfTable; i++) {
      if (entries.table[i] == null) {

      } else {
        boolean lastNodeFound = false;
        DataNode currNode = (HashTable<K, V>.DataNode) entries.table[i];
        while (!lastNodeFound) {
          String currNodeKey = (String) currNode.getKey();
          if (currNodeKey.contains(year + "-" + month + "-")) {
            totalWeight = totalWeight + (Double) currNode.getData();
          }
          currNode = currNode.getNextNode();
          if (currNode == null) {
            lastNodeFound = true;
          } else {

          }
        }
      }
    }
    return totalWeight;
  }
  
  /*
   * Obtains the farm's total weight over the given start (year-month-day) and end (month-day)
   */
  @SuppressWarnings("unchecked")
  public double getWeightForRange(String start, String end) {
    // Split at regex '-' to parse values 
    String[] parseStart = start.split("-");
    int year = Integer.parseInt(parseStart[0]);
    int startMonth = Integer.parseInt(parseStart[1]);
    int startDay = Integer.parseInt(parseStart[2]);
    String[] parseEnd = end.split("-");
    int endMonth = Integer.parseInt(parseEnd[0]);
    int endDay = Integer.parseInt(parseEnd[1]);
    
    // Determine total weight
    double totalWeight = 0;
    
      // Traverse through every spot in the hashTable
    for (int i = 0; i < getCapacity(); i ++) {
      //Add all keys that may be in this spot 
      DataNode current = (DataNode)table[i];
      while(current != null) {
        String[] currentDate = ((String)current.getKey()).split("-");
        
        int currentYear = Integer.parseInt(currentDate[0]);
        int currentMonth = Integer.parseInt(currentDate[1]);
        int currentDay = Integer.parseInt(currentDate[2]);
        if (currentYear == year) { // Ensure year is same
          if(startMonth == endMonth && currentMonth == startMonth) { // If all months are same, day must be closely compared
            if(currentDay >= startDay && currentDay <= endDay) {
              totalWeight += (double)current.getData();
            }
          }
          else if(currentMonth == startMonth) { // If month is same as start only, day must be greater
            if(currentDay >= startDay) {
              totalWeight += (double)current.getData();
            }
          } 
          else if(currentMonth == endMonth) { // If month is same as end only, day must be less
            if(currentDay <= endDay) {
              totalWeight += (double)current.getData();
            }
          }
          else if(currentMonth > startMonth && currentMonth < endMonth){  // If month is some other time in range, day doesn't matter
            totalWeight += (double)current.getData();
          }
        }
        current = current.getNextNode();
      }
    }
    
    return totalWeight;
  }

}

