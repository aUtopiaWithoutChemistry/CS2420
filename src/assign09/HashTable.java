package assign09;

import java.util.*;

/**
 * This is an implementation of Provided Map Interface, using hashCode to store, locate,
 * and retrieve MapEntries, which are key-value pairs. In the table, we use separate
 * chaining to deal with the collision, and the load factor can be set to customized value
 * using constructor with parameter.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-01
 *
 * @param <K> type of keys in this hash table
 * @param <V> type of values in this hash table
 */
public class HashTable<K, V> implements Map<K, V> {
    // Since each chain is a small collection of unpredictable size, Java's LinkedList should be used for each chain.
    private Object[] keyValuePairs;
    private final double loadFactor;
    private int cellNumber, count;
    private int numberOfCollisions;

    /**
     * The default (zero parameter) constructor creates a hash table with a maximum load factor of 10.0.
     */
    public HashTable() {
        this(10.0);
    }

    /**
     * The other constructor takes one double-type parameter that is a user-specified value for the
     * maximum load factor. The load factor must not be less than 1.0. If the parameter is less
     * than 1.0, an IllegalArgumentException is thrown.
     *
     * @param loadFactor the maximum load factor for this hashTable
     */
    public HashTable(double loadFactor) {
        if (loadFactor < 1.0) throw new IllegalArgumentException();
        cellNumber = 10;
        count = 0;
        keyValuePairs = new Object[cellNumber];
        for (int i = 0; i < cellNumber; i++) {
            keyValuePairs[i] = new LinkedList<MapEntry<K, V>>();
        }
        this.loadFactor = loadFactor;
    }

    /**
     * The customized toString method for debugging, it prints current cell number, and
     * current load factor.
     *
     * @return A string that shows the cell number and load factor of current hash table.
     */
    @Override
    public String toString() {
        return "cell number is: " + cellNumber + ", current load factor is: " + (double)count / cellNumber;
    }

    /**
     * Removes all mappings from this map.
     */
    @Override
    public void clear() {
        count = 0;
        numberOfCollisions = 0;
        for (int i = 0; i < cellNumber; i++) {
            keyValuePairs[i] = new LinkedList<MapEntry<K, V>>();
        }
    }

    /**
     * Determines whether this map contains the specified key.
     *
     * @param key - the key being searched for
     * @return true if this map contains the key, false otherwise
     */
    @Override
    public boolean containsKey(K key) {
        int index = getIndex(key);
        for (MapEntry<K, V> entry : (LinkedList<MapEntry<K, V>>)keyValuePairs[index]) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether this map contains the specified value.
     *
     * @param value - the value being searched for
     * @return true if this map contains one or more keys to the specified value,
     * 	       false otherwise
     */
    @Override
    public boolean containsValue(V value) {
        for (Object pairs : keyValuePairs) {
            for (MapEntry<K, V> pair : (LinkedList<MapEntry<K, V>>)pairs) {
                if (value.equals(pair.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a list view of the mappings contained in this map, where the ordering of
     * mapping in the list is insignificant.
     *
     * @return a List object containing all mapping (i.e., entries) in this map
     */
    @Override
    public List<MapEntry<K, V>> entries() {
        List<MapEntry<K, V>> returnList = new ArrayList<>();
        for (Object keyValuePair : keyValuePairs) {
            for (MapEntry<K, V> pair : (LinkedList<MapEntry<K, V>>)keyValuePair) {
                returnList.add(pair);
            }
        }
        return returnList;
    }

    /**
     * Gets the value to which the specified key is mapped.
     *
     * @param key - the key for which to get the mapped value
     * @return the value to which the specified key is mapped, or null if this map
     *         contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (containsKey(key)) {
            int index = getIndex(key);
            for (MapEntry<K, V> pair : (LinkedList<MapEntry<K, V>>)keyValuePairs[index]) {
                if (key.equals(pair.getKey())) {
                    return pair.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Determines whether this map contains any mappings.
     *
     * @return true if this map contains no mappings, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * (I.e., if the key already exists in this map, resets the value;
     * otherwise adds the specified key-value pair.)
     *
     * @param key - the key for which to update the value (if exists)
     *              or to be added to the table
     * @param value - the value to be mapped to the key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    @Override
    public V put(K key, V value) {
        // count add one if there isn't an entry have this key
        if (!containsKey(key)) {
            if ((double) (count + 1) > loadFactor * cellNumber) {
                rehashing(doubleTheTable());
            }
            count++;
        }
        int index = getIndex(key);
        LinkedList<MapEntry<K, V>> chain = getChain(index);
        resetNumberOfCollisions();
        // when there is no entry in the chain
        if (chain.isEmpty()) {
            chain.add(new MapEntry<>(key, value));
        }
        // when there is entry in the chain
        else {
            for (MapEntry<K, V> pair : chain) {
                // if this key exist
                if (pair.getKey().equals(key)) {
                    V returnValue = pair.getValue();
                    pair.setValue(value);
                    return returnValue;
                }
                // if the key does not exist, add one to collision
                numberOfCollisions++;
            }
            chain.add(new MapEntry<>(key, value));
        }
        return null;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key - the key to be removed.
     * @return the previous value associated with key, or null if there was no
     *         mapping for key.
     */
    @Override
    public V remove(K key) {
        int index = getIndex(key);
        if (containsKey(key)) {
            count--;
        }
        // remove the MapEntry in keyValuePairs
        V value = get(key);
        LinkedList<MapEntry<K, V>> chain = (LinkedList<MapEntry<K, V>>)keyValuePairs[index];
        chain.remove(new MapEntry<>(key, get(key)));
        return value;
    }

    /**
     * Determines the number of mappings in this map.
     *
     * @return the number of mappings in this map
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Determines the number of collisions
     *
     * @return the number of collisions
     */
    protected int getNumberOfCollisions() {
        return this.numberOfCollisions;
    }

    /**
     * Reset the number of collisions
     */
    protected void resetNumberOfCollisions() {
        this.numberOfCollisions = 0;
    }

    /**
     * A private helper method to get the correct index in keyValuePairs to store the MapEntry.
     *
     * @param key the key
     * @return the index in the table to store this key and its value's pair.
     */
    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % cellNumber;
    }

    /**
     * A private helper method to get the LinkedList in given cell of the table
     *
     * @param index the index
     * @return the linkedList at the corresponding index
     */
    private LinkedList<MapEntry<K, V>> getChain(int index) {
        return (LinkedList<MapEntry<K, V>>) keyValuePairs[index];
    }

    /**
     * A private helper method to double the cell of this hash table,
     * when the load factor reach the maximum value. It will return
     * all the entries need to be rehashed.
     *
     * @return a list of MapEntries needs to be rehashed
     */
    private List<MapEntry<K, V>> doubleTheTable() {
        cellNumber *= 2;
        // keep track of current entries
        List<MapEntry<K, V>> entries = entries();
        keyValuePairs = new Object[cellNumber];
        for (int i = 0; i < cellNumber; i++) {
            keyValuePairs[i] = new LinkedList<MapEntry<K, V>>();
        }
        count = 0;
        numberOfCollisions = 0;
        return entries;
    }

    /**
     * A private helper method to rehash all the MapEntries in the given List.
     *
     * @param entries all the MapEntries that need to be rehashed.
     */
    private void rehashing(List<MapEntry<K, V>> entries) {
        for (MapEntry<K, V> entry : entries) {
            put(entry.getKey(), entry.getValue());
        }
    }
}
