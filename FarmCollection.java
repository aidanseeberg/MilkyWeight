/*
 * A data structure that implements FarmDataADT using a hashtable.
 */
public class FarmCollection implements FarmCollectionADT {
  
  //Inner Node class to pair Farms and comparable IDs
  //Allows Farms to point left/right
  private class Node {
    private String key;
    private Farm value;
    private Node next;
    
    //Two argument constructor
    private Node(Farm value) {
      this.value = value;
      key = value.getID(); // Set the key to be the Farm's ID
      next = null;
    }
    
    //"Getter" methods
    private String getKey() { return key; }
    private Farm getValue() { return value; }
    private Node getNext() { return next; }

    //"Setter" methods
    private void setKey(String key) { this.key = key; }
    private void setValue(Farm value) { this.value = value; }
    private void setNext(Node next) { this.next = next; }

  }
  
  //Data Field Members
  private double loadFactorThreshold;
  private int tableSize;
  private int size;
  private Object[] hashTable;
      
  /**
   *  Default no-argument constructor
   */
  public FarmCollection() {
    loadFactorThreshold = 0.75;
    tableSize = 11;
    size = 0;
    hashTable = new Object[tableSize];
  }
  
  /**
   * Two argument HashTable constructor
   * @param initialCapacity
   * @param loadFactorThreshold
   */
  public FarmCollection(int initialCapacity, double loadFactorThreshold) {
    this.loadFactorThreshold = loadFactorThreshold;
    tableSize = initialCapacity;
    size = 0;
    hashTable = new Object[tableSize];
  }
  
  /**
   * Hashes the given key to determine the table index to store in
   * @param key - key to hash
   * @return index to store key in 
   */
  private int hash(String key) {
    int hashCode = Math.abs(key.hashCode());
    return hashCode % tableSize;
  }

  /**
   * Add the key,value pair to the data structure and increase the number of keys.
   * If key is already in data structure, replace value with new value
   * @param key to be added
   * @param value to be added
   * @throws IllegalNullKeyException if key passed is null
   */
  public void insert(String key, Farm value) throws IllegalNullKeyException {      
    // Check for exceptions
    if(key == null) { throw new IllegalNullKeyException("Passed key is null"); }
    
    // Create and insert a node into the hashTable array
    Node newNode = new Node(value);
    
    if(hashTable[hash(key)] == null) { 
      // Procedure if this is the first node at the index
      hashTable[hash(key)] = newNode;
    } else { 
      // Procedure to handle collisions or duplicates further in chain
      Node current = (Node) hashTable[hash(key)];
      
      // Search for duplicate
      while(current != null) {
        if(current.getKey() == key) {
          current.setValue(value);
          return; // To avoid another insert and incrementing size
        }
        current = current.getNext();
      }
      // Insert node at end of chain if duplicate was not found
      current = (Node) hashTable[hash(key)]; // Reset 
      
      while(current.getNext() != null) {
        current = current.getNext();
      }
      current.setNext(newNode);
    }
    // Increment size
    size++;
    // Resize and rehash if load factor threshold is passed
    if(getLoadFactor() >= getLoadFactorThreshold()) { resize(); }
  }
  
  /**
   * Resizes the table and rehashes all nodes 
   */
  private void resize() {
    // Increase tableSize, preserve old array, and resize/reset hashTable
    tableSize = tableSize*2 + 1;
    Object[] oldHashTable = hashTable;
    Object[] newHashTable = new Object[tableSize];
    hashTable = newHashTable;
    size = 0;
    
    // Iterate through every node and rehash into the new table
    for(int i = 0; i < oldHashTable.length; i++) {
      Node current = (Node) oldHashTable[i];
      while(current != null) {
        try {
          insert(current.getKey(), current.getValue());
        } catch(IllegalNullKeyException e) { System.out.println(e.getMessage());}
        current = current.getNext();
      }
    }
  }

  /**
   * If key is found,
   * remove the key,value pair from the data structure
   * decrease number of keys and return true
   * @param key to be removed
   * @throws IllegalNullKeyException if key passed is null
   * @return true if key is successfully removed, false if key is not found
   */
  public boolean remove(String key) throws IllegalNullKeyException {
    // Check for exceptions
    if(key == null) { throw new IllegalNullKeyException("Passed key is null"); }
    
    // Attempt to find and remove the passed key
    if(hashTable[hash(key)] == null) { 
      // Procedure if no key is found at index
      return false;
    } else { 
      if(((Node)hashTable[hash(key)]).getKey() == key) {
        // Procedure if key is first node in chain
        hashTable[hash(key)] = ((Node)hashTable[hash(key)]).getNext();
      } else {
        // Procedure to determine if key exists further in chain
        Node current = (Node)hashTable[hash(key)];
        Boolean removed = false;
        
        while(current.getNext() != null) {
          if(current.getNext().getKey() == key) {
            current.setNext(current.getNext().getNext());
            removed = true;
          }
          current = current.getNext();
        }
        // Return false if key was not removed in while loop
        if(!removed) {
          return false;
        }
      }
      //Decrement size and return after key is removed
      size--;
      return true;
    }
    
  }

  /**
   * Returns the value associated with the specified key
   * Does not remove key or decrease number of keys
   * @param key to retrieve
   * @throws IllegalNullKeyException if passed key is null
   * @throws KeyNotFoundException if key is not found
   * @return value associated with given key
   */
  public Farm get(String key) throws IllegalNullKeyException, KeyNotFoundException {
    // Check for exceptions
    if(key == null) { throw new IllegalNullKeyException("Passed key is null"); }
    
    //Search for key 
    Node current = (Node)hashTable[hash(key)];
    while(current != null) {
      if(current.getKey() == key) {
        return ((Node)hashTable[hash(key)]).getValue();
      }
      current = current.getNext();
    }
    
    //Throw exception if key was not found
    throw new KeyNotFoundException("Key could not be found");
  }

  /**
   * Returns the number of key,value pairs in the data structure
   * @return the number of key, value pairs in the data structure
   */
  public int numKeys() {
    return size;
  }

  /**
   * Returns the load factor threshold that was
   * passed into the constructor when creating
   * the instance of the HashTable.
   * When the current load factor is greater than or
   * equal to the specified load factor threshold,
   * the table is resized and elements are rehashed.
   * @return the Load Factor Threshold
   */
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  /**
   * Returns the current load factor for this hash table
   * load factor = number of items / current table size
   * @return the load factor of this 
   */
  public double getLoadFactor() {
    return (double)size/(double)tableSize;
  }

  /**
   * Return the current Capacity (table size)
     of the hash table array.
    
     The initial capacity must be a positive integer, 1 or greater
     and is specified in the constructor.
    
     REQUIRED: When the load factor threshold is reached,
     the capacity must increase to: 2 * capacity + 1
    
   * Once increased, the capacity never decreases
   * @return the current table size of the hash table array
   */
  public int getCapacity() {
    return tableSize;
  }
  
  /**
   * 
   * Returns the total weight of all farms within that given month/year
   * 
   * @param month,year - date of total weight needed
   * @return total weight
   */
  public double GetAllWeightForMonth(int month, String year) {
    double totalWeight = 0;
    int sizeOfTable = hashTable.length;

    for (int i = 0; i < sizeOfTable; i++) {
      if (hashTable[i] == null) {

      } else {
        boolean lastNodeFound = false;
        Node currNode = (Node) hashTable[i];
        while (!lastNodeFound) {
          totalWeight += currNode.getValue().getWeightForMonth(month, year);
          currNode = currNode.getNext();
          if (currNode == null) {
            lastNodeFound = true;
          } else {

          }
        }
      }
    }
    return totalWeight;
  }
  
  
  // For immediate testing
  public static void main(String[] args) {
    Farm farm110 = new Farm("110");
    Farm farm111 = new Farm("111");
    Farm farm112 = new Farm("112");
    Farm farm113 = new Farm("113");
    Farm farm114 = new Farm("114");
    Farm farm115 = new Farm("115");
    Farm farm116 = new Farm("116");
    Farm farm117 = new Farm("117");
    Farm farm118 = new Farm("118");
    Farm farm119 = new Farm("119");
    Farm farm120 = new Farm("120");
    
    FarmCollection test = new FarmCollection();
    try {
      test.insert(farm110.getID(), farm110);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
  }
  
}
