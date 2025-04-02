package assign09;

import java.util.*;

/**
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-01
 *
 * @param <K> type of keys in this hash table
 * @param <V> type of values in this hash table
 */
public class HashTable<K, V> implements Map<K, V> {
    // The chains must be stored in a basic array. Do not store them in an ArrayList or any other data structure.
    // Since each chain is a small collection of unpredictable size, Java's LinkedList should be used for each chain.
    private Object[] keyValuePairs;
    private final double loadFactor;
    private int cellNumber, count;
    private int numberOfCollisions;

    // The default (zero parameter) constructor creates a hash table with a maximum load factor of 10.0.
    public HashTable() {
        this(10.0);
    }

    /*
    The other constructor takes one double-type parameter that is a user-specified value for the
    maximum load factor.  The load factor must not be less than 1.0.  If the parameter is less
    than 1.0, an IllegalArgumentException is thrown.
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

    @Override
    public String toString() {
        return "cell number is: " + cellNumber + ", current load factor is: " + (double)count / cellNumber;
    }

    @Override
    public void clear() {
        count = 0;
        numberOfCollisions = 0;
        for (int i = 0; i < cellNumber; i++) {
            keyValuePairs[i] = new LinkedList<MapEntry<K, V>>();
        }
    }

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

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

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

    @Override
    public int size() {
        return count;
    }

    protected int getNumberOfCollisions() {
        return this.numberOfCollisions;
    }

    protected void resetNumberOfCollisions() {
        this.numberOfCollisions = 0;
    }

    /**
     * A private helper method to get the correct index in keyValuePairs to store the MapEntry
     *
     * @param key the key
     * @return the index in the table to store this key and its value's pair
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

    /*
    The load factor of the hash table must never exceed the maximum value.
    If an operation resulted in the load factor exceeding the maximum,
    the array holding the chains must double in length and all entries
    must be rehashed.
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

    private void rehashing(List<MapEntry<K, V>> entries) {
        for (MapEntry<K, V> entry : entries) {
            put(entry.getKey(), entry.getValue());
        }
    }
}
