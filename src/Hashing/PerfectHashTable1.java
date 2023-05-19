package Hashing;

import java.util.ArrayList;

public class PerfectHashTable1 implements HashTable{
    /**
     * elementCount: How many elements are actually in the table.
     * rehashes: To keep track of the number of rehashes we performed from initiation of the table.
     * initialSize: The initial number of elements (N) which the elementCount cannot exceed.
     * bits: The number of rows needed to construct the matrix hash OR #bits that the hashing
     *       function maps to.
     * elementArray: The actual data structure in which we store the data.
     * hash: The current hashing function.
     * */
    private int elementCount, rehashes;
    private final int elementBound, bits;
    private String[] elementArray;
    private MatrixHash hash;
    private final int maxStrBits;

    public PerfectHashTable1(int initialSize, int maxStrLen) throws Exception {
        if (initialSize < 0) throw new Exception("Negative initial size for a hash table.");
        elementBound = initialSize;
        elementCount = 0;
        int nSquared = (int) Math.pow(initialSize,2);
        // Get table size as the next power of 2 after the initial table size.
        bits = (nSquared != 0)? (int) Math.ceil(Math.log(nSquared)/Math.log(2)) : 0;
        maxStrBits = maxStrLen * 8;
        // Set table size to 2^b
        hash = new MatrixHash(bits, maxStrBits);
        elementArray = new String[(int)Math.pow(2,bits)];
    }

    public PerfectHashTable1(ArrayList<String> initialList, int maxStrLen) {
        elementBound = initialList.size();
        maxStrBits = maxStrLen * 8;
        // Set rehashes to -1 because the first hashing doesn't count.
        rehashes = -1;
        int index;
        boolean collisions = true;
        // Set element count to the size of the list.
        elementCount = initialList.size();
        int nSquared = (int) Math.pow(elementCount,2);
        // Get table size as the next power of 2 after the initial table size.
        bits = (nSquared != 0)? (int) Math.ceil(Math.log(nSquared)/Math.log(2)) : 0;
        // Keep finding random hash functions until no collisions are reached.
        while(collisions){
            // Set table size to 2^b.
            elementArray = new String[(int)Math.pow(2,bits)];
            hash = new MatrixHash(bits, maxStrBits);
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

    private void addRehash(String additional) {
        int index;
        boolean collisions = true;
        ArrayList<String> nonNull = new ArrayList<>();
        // Get non-null elements in the array.
        for (String s : elementArray) if (s != null) nonNull.add(s);
        nonNull.add(additional);
        // Set element count to the size of the non-null list.
        elementCount = nonNull.size();
        // Keep finding random hash functions until no collisions are reached.
        while(collisions){
            // Reset the main array and the hash function.
            int s = elementArray.length;
            elementArray = null;
            System.gc();
            elementArray = new String[s];
            hash = new MatrixHash(bits, maxStrBits);
            collisions = false; rehashes++;
            for (String entry : nonNull) {
                index = hash.getHashValue(entry);
                // Only insert if the element is in an empty spot.
                if (elementArray[index] == null)
                    elementArray[index] = entry;
                else{
                    collisions = true;
                    break;
                }
            }
        }
    }

    @Override
    public boolean insert(String s) {
        int index = hash.getHashValue(s);
        // Insertion fails if the list already has n elements.
        if (elementCount == elementBound) return false;
        if (elementArray[index] != null){
            // Element already exists.
            if (elementArray[index].equals(s)) return false;
            // Element does not exist but causes a collision, so we add it and rehash
            else addRehash(s);
        }else{
            elementArray[index] = s;
            elementCount++;
        }
        return true;
    }

    @Override
    public boolean delete(String s) {
        int index = hash.getHashValue(s);
        // Deletion fails if element doesn't exist or
        // there's a different element in its slot.
        if (elementArray[index] == null || !elementArray[index].equals(s)) return false;
        elementArray[index] = null;
        elementCount--;
        return true;
    }

    @Override
    public int batchInsert(ArrayList<String> s) {
        int successes = 0;
        for (String entry : s)
            if (insert(entry))
                successes++;
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
