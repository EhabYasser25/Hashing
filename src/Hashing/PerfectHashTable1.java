package Hashing;
import java.util.ArrayList;
import java.util.Arrays;

public class PerfectHashTable1 implements HashTable {
    /**
     * elementCount: How many elements are actually in the table.
     * currentSize: The size of the allocated table for the array.
     * maxStrLen: Max bits of the represented strings.
     * elementArray: The actual data structure in which we store the data.
     * hash: The current hashing function.
     * rehashes: To keep track of the number of rehashes we needed to perform.
     * */
    int elementCount, currentSize, maxStrLen, rehashes;
    String[] elementArray;
    MatrixHash hash;

    public PerfectHashTable1(ArrayList<String> initialList, int maxStrLen) {
        this.maxStrLen = maxStrLen;
        rehashes = -1;
        hashElements(initialList);
    }

    public PerfectHashTable1(int initialSize, int maxStrLen) {
        this.maxStrLen = maxStrLen;
        elementCount = 0;
        int nSquared = (int) Math.pow(initialSize,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (int) Math.ceil(Math.log(nSquared)/Math.log(2));
        // Set table size to 2^b
        hash = new MatrixHash(b, this.maxStrLen);
        elementArray = new String[(int)Math.pow(2,b)];
        currentSize = elementArray.length;
    }

    private void hashElements(ArrayList<String> elementsToHash) {
        int index;
        boolean collisions = true;
        // Set element count to the size of the list of elements.
        elementCount = elementsToHash.size();
        int nSquared = (int) Math.pow(elementCount,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (int) Math.ceil(Math.log(nSquared)/Math.log(2));
        // Set table size to 2^b.
        elementArray = new String[(int)Math.pow(2,b)];
        currentSize = elementArray.length;
        // Keep finding random hash functions until no collisions are reached.
        while(collisions){
            hash = new MatrixHash(b, this.maxStrLen);
            collisions = false;
            for (String entry : elementsToHash) {
                if (entry == null) {
                    elementCount--;
                    continue;
                }
                index = hash.getStringKey(entry);
                // Only insert if the element is in an empty spot.
                if (elementArray[index] == null) elementArray[index] = entry;
                else{ // Collision found, pick another hash function and try again.
                    collisions = true;
                    break;
                }
            }
        }
        rehashes++;
    }

    @Override
    public boolean insert(String s) {
        int index = hash.getStringKey(s);
        // Element does not exist
        if (elementArray[index] != null) {
            if (elementArray[index].equals(s)) return false; // Element already exists.
            ArrayList<String> temp = (ArrayList<String>) Arrays.asList(elementArray);
            temp.add(s);
            hashElements(temp);
        }else{
            elementArray[index] = s;
            elementCount++;
        }
        return true;
    }

    @Override
    public boolean delete(String s){
        int index = hash.getStringKey(s);
        if (elementArray[index] == null) return false;
        elementArray[index] = null;
        elementCount--;
        return true;
    }

    @Override
    public int batchInsert(String[] s){
        int successes = 0;
        for (String entry: s) {
            if (insert(entry)) successes++;
        }
        return successes;
    }

    @Override
    public int batchDelete(String[] s){
        int successes = 0;
        for (String entry: s) {
            if (delete(entry)) successes++;
        }
        return successes;
    }

    @Override
    public boolean search(String s){
        int index = hash.getStringKey(s);
        return (elementArray[index] != null && elementArray[index].equals(s));
    }
}