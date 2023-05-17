package Hashing;

import java.util.ArrayList;

public class StaticHashTable1 implements HashTable{
    /**
     * elementCount: How many elements are actually in the table.
     * rehashes: To keep track of the number of rehashes we needed to perform.
     * elementArray: The actual data structure in which we store the data.
     * hash: The current hashing function.
     * */

    private int elementCount, rehashes;
    private final String[] elementArray;
    private MatrixHash hash;

    public StaticHashTable1(int initialSize) throws Exception {
        if (initialSize < 0) throw new Exception("Negative initial size for a hash table.");
        elementCount = 0;
        int nSquared = (int) Math.pow(initialSize,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (nSquared != 0)? (int) Math.ceil(Math.log(nSquared)/Math.log(2)) : 0;
        // Set table size to 2^b
        hash = new MatrixHash(b);
        elementArray = new String[(int)Math.pow(2,b)];
    }

    public StaticHashTable1(ArrayList<String> initialList) {
        rehashes = -1;
        int index;
        boolean collisions = true;
        // Set element count to the size of the list.
        elementCount = initialList.size();
        int nSquared = (int) Math.pow(elementCount,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (nSquared != 0)? (int) Math.ceil(Math.log(nSquared)/Math.log(2)) : 0;
        // Set table size to 2^b.
        elementArray = new String[(int)Math.pow(2,b)];
        // Keep finding random hash functions until no collisions are reached.
        while(collisions){
            hash = new MatrixHash(b);
            collisions = false; rehashes++;
            for (String entry : initialList) {
                index = hash.getHashValue(entry);
                // Only insert if the element is in an empty spot.
                if (elementArray[index] == null)
                    elementArray[index] = entry;
                else if (!elementArray[index].equals(entry)){
                    // A collision has occurred between two different strings.
                    collisions = true;
                    break;
                }
            }
        }
    }

    @Override
    public boolean insert(String s) {
        int index = hash.getHashValue(s);
        // Insertion fails if slot is full.
        if (elementArray[index] != null) return false;
        elementArray[index] = s;
        elementCount++;
        return true;
    }

    @Override
    public boolean delete(String s) {
        int index = hash.getHashValue(s);
        // Deletion fails if element doesn't exist.
        if (elementArray[index] == null) return false;
        elementArray[index] = null;
        elementCount--;
        return true;
    }

    @Override
    public int batchInsert(ArrayList<String> s) {
        int successes = 0;
        for (String entry : s)
            if (insert(entry)) successes++;
        return successes;
    }

    @Override
    public int batchDelete(ArrayList<String> s) {
        int successes = 0;
        for (String entry : s)
            if (delete(entry)) successes++;
        return successes;
    }

    @Override
    public boolean search(String s) {
        int index = hash.getHashValue(s);
        if (elementArray[index] == null) return false;
        return elementArray[index].equals(s);
    }

    public int numberOfElements(){
        return elementCount;
    }

    public int tableSize(){
        return elementArray.length;
    }

    public int getRehashes() { return rehashes; }
}
