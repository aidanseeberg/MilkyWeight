
public class Farm {
  
  private String iD;
  private HashTable<String, Integer> entrys; 
 
  //Creates a new Farm object with the given ID number
  public Farm (String id) {
    iD = id; 
    entrys = new HashTable<String,Integer>(); 
  }
  
  /**
   * returns the ID number of this farm 
   * @return
   */
  public String getID() {
    return iD; 
  }
  /**
   * Adds entry to the farm's hashtable
   * @param date
   * @param weight
   * @throws IllegalNullKeyException
   */
  public void addEntry(String date, Integer weight) throws IllegalNullKeyException {
    entrys.insert(date, weight);
  }
  
  /**
   * Gets an entry based on the given date from the farm's hastable
   * @param date
   * @return
   * @throws IllegalNullKeyException
   * @throws KeyNotFoundException
   */
  public Entry getEntry(String date) throws IllegalNullKeyException, KeyNotFoundException {
    Integer weight = entrys.get(date); 
    
    Entry theEntry = new Entry(date, this.getID(), weight); 
    
    return theEntry; 
  }
  
  
  public int getWeight() {
    return entrys.getAllWeight(); 
  }
  
  public int monthGetWeight(String month) {
    return entrys.getMonthWeight(month); 
  }
 
  


  
/**
* Generic HashTable Holds key value pairs of type K and V
* 
* @author zolo Also holds its current capacity and loadFactorThreshold which once reached, expands
*         the size of the table
* @param <K>
* @param <V>
*/
private class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

 private int currentCapacity; // current max capacity of the table
 private double loadFactorThreshold; // load factor threshold of the table which once exceeded,
                                     // expands the table
 private int numberOfItems; // current number of items in the table
 private Object[] table; // the table in which the dataNodes are put in

 /**
  * The private data node class which are what makes up the hashTable
  * 
  * @author zolo
  *
  */
 public class dataNode {
   private K key;
   private V value;
   private dataNode nextNode;

   /**
    * constructor of the dataNode
    * 
    * @param key
    * @param value
    */
   public dataNode(K key, V value) {
     this.key = key;
     this.value = value;
     nextNode = null;
   }

   /**
    * 
    * @return the data value of the node
    */
   public V getData() {
     return value;
   }

   /**
    * 
    * @return key of this node
    */
   public K getKey() {
     return key;
   }

   /**
    * 
    * @return next node if it exists, if it does not, return null
    */
   public dataNode getNextNode() {
     return nextNode;
   }

   /**
    * Sets the current node's nextNode.
    * 
    * @param insertNode
    */
   public void setNextNode(dataNode insertNode) {
     this.nextNode = insertNode;
   }

   /**
    * Checks if the node has a nextNode
    * 
    * @return false if null true if the node has a nextNode
    */
   public boolean checkIfNextNull() {
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
   public void setData(V data) {
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
  * @throws IllegalNullKeyException when inserting the existing nodes into the new expanded table
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
   dataNode currentNode = null;
   while (i < tempTable.length) {
     if (tempTable[i] != null) {
       currentNode = (dataNode) tempTable[i];
       this.insert(currentNode.getKey(), currentNode.getData());
       count++;
       if (currentNode.getNextNode() != null) {
         while (!currentNode.checkIfNextNull()) {
           dataNode childNode = currentNode.getNextNode();
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
  * inserts a new element into the table by first finding its hashIndex and then inserting it to
  * that location
  */
 @SuppressWarnings("unchecked")
 @Override
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
     table[hashIndex] = new dataNode(key, value);
   }
   dataNode foundNode = (dataNode) table[hashIndex];
   if ((table[hashIndex] != null) && foundNode.getKey().equals(key)) {
     foundNode.setData(value);
   } else {
     @SuppressWarnings("unchecked")
     dataNode currentNode = (dataNode) table[hashIndex];
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
       currentNode = (dataNode) table[hashIndex];
       while (currentNode.getNextNode() != null) {
         currentNode = currentNode.getNextNode();
       }
       dataNode nextNode = new dataNode(key, value);
       currentNode.setNextNode(nextNode);
     }

   }
   numberOfItems++;

 }

 /**
  * Removes the designated element corresponding to the param key throws IllegalNullKeyException if
  * key is null returns false if key is not found and true if the key is found and removed
  */
 @Override
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
   dataNode indexNode = (dataNode) table[hashIndex];
   if (indexNode.getKey().equals(key) && indexNode.getNextNode() == null) {
     numberOfItems--;
     table[hashIndex] = null;
     return true;
   } else {
     while (indexNode.getNextNode() != null) {
       dataNode childNode = indexNode.getNextNode();
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
  * Returns the data value of the key throws IllegalNullKeyException is key is null throws
  * KeyNotFoundException is key is not found in the table
  */
 @Override
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
     dataNode foundNode = (dataNode) table[hashIndex];
     if (foundNode.getKey().equals(key)) {
       return foundNode.getData();
     } else if (foundNode.getNextNode() != null) {
       while (foundNode != null) {
         if (foundNode.getKey().equals(key)) { // changed from foundNode.getNextNode
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
 @Override
 public int numKeys() {
   return numberOfItems;
 }

 /**
  * returns the load factor threshold for this hashtable
  */
 @Override
 public double getLoadFactorThreshold() {
   return this.loadFactorThreshold;
 }

 /**
  * returns the current load factor of the table
  */
 @Override
 public double getLoadFactor() {
   return (double) numberOfItems / (double) currentCapacity;
 }

 /**
  * returns the current capacity of the table
  */
 @Override
 public int getCapacity() {
   return currentCapacity;
 }

 /**
  * Tells the grader what kind of collision resolution scheme
  */
 @Override
 public int getCollisionResolution() {
   return 5;
 }
 
 @SuppressWarnings("unchecked")
public int getAllWeight() {
   int totalWeight = 0; 
   int sizeOfTable = entrys.table.length; 
   
   for(int i = 0; i<sizeOfTable;i++) {
     if(entrys.table[i] == null) {
       
     }
     else {
       boolean lastNodeFound = false; 
       dataNode currNode = (HashTable<K, V>.dataNode) entrys.table[i]; 
       while(!lastNodeFound) {
         totalWeight = totalWeight + (Integer)currNode.getData(); 
         currNode = currNode.getNextNode(); 
         if(currNode == null) {
           lastNodeFound = true; 
         }
         else {
           
         }
       }
     }
   }
   return totalWeight; 
 }
 
 @SuppressWarnings("unchecked")
public int getMonthWeight(String month) {
   int totalWeight = 0; 
   int sizeOfTable = entrys.table.length; 
   
   for(int i = 0; i<sizeOfTable;i++) {
     if(entrys.table[i] == null) {
       
     }
     else {
       boolean lastNodeFound = false; 
       dataNode currNode = (HashTable<K, V>.dataNode) entrys.table[i]; 
       while(!lastNodeFound) {
         String currNodeKey = (String)currNode.getKey(); 
         if(currNodeKey.contains(month)) {
         totalWeight = totalWeight + (Integer)currNode.getData(); 
         }
         currNode = currNode.getNextNode(); 
         if(currNode == null) {
           lastNodeFound = true; 
         }
         else {
           
         }
       }
     }
   }
   return totalWeight; 
 }
 
}

}
