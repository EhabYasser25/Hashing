package Hashing;
import java.util.ArrayList;

public class DynamicHashTable implements HashTable {
    /**
     * elementCount: How many elements are actually in the table.
     * rehashes: To keep track of the number of rehashes we needed to perform.
     * currentBound: The current N that we use to decide if we will resize or not.
     * elementArray: The actual data structure in which we store the data.
     * hash: The current hashing function.
     * */
    private int elementCount, rehashes, currentBound;
    private String[] elementArray;
    private MatrixHash hash;
    private final int maxStrBits;

    public DynamicHashTable(ArrayList<String> initialList, int maxStrLen) {
        maxStrBits = maxStrLen * 8;
        rehashes = -1;
        hashElements(initialList, true);
    }

    public DynamicHashTable(int initialSize, int maxStrLen) throws Exception {
        if (initialSize < 0) throw new Exception("Negative initial size for a hash table.");
        elementCount = 0;
        currentBound = initialSize;
        maxStrBits = maxStrLen * 8;
        int nSquared = (int) Math.pow(initialSize,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (nSquared != 0)? (int) Math.ceil(Math.log(nSquared)/Math.log(2)) : 0;
        // Set table size to 2^b
        hash = new MatrixHash(b, this.maxStrBits);
        elementArray = new String[(int)Math.pow(2,b)];
    }

    private int hashElements(ArrayList<String> elementsToHash, boolean init) {
        int index, fails = 0;
        boolean collisions = true;
        ArrayList<String> nonNull;
        if (init)
            nonNull = new ArrayList<>(elementsToHash);
        else {
            nonNull = new ArrayList<>();
            for (String s : elementArray) if (s != null) nonNull.add(s);
            nonNull.addAll(elementsToHash);
        }
        // Set element count to the size of the list.
        elementCount = nonNull.size();
        currentBound = elementCount;
        int nSquared = (int) Math.pow(elementCount,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (nSquared != 0)? (int) Math.ceil(Math.log(nSquared)/Math.log(2)) : 0;
        // Keep finding random hash functions until no collisions are reached.
        while(collisions){
            // Set table size to 2^b.
            elementArray = new String[(int)Math.pow(2,b)];
            hash = new MatrixHash(b, this.maxStrBits);
            collisions = false; fails = 0;
            for (String entry : nonNull) {
                index = hash.getHashValue(entry);
                // Only insert if the element is in an empty spot.
                if (index == -1) // Exceeds character limit
                    fails++;
                else if (elementArray[index] == null)
                    elementArray[index] = entry;
                else if (elementArray[index].equals(entry)) // Element already exists
                    fails++;
                else{ // A collision has occurred
                    collisions = true;
                    break;
                }
            }
        }
        rehashes++;
        return fails;
    }

    @Override
    public boolean insert(String s) {
        int index = hash.getHashValue(s);
        if (index == -1) return false; // Exceeds limit
        // Element does not exist
        if (elementArray[index] != null) {
            if (elementArray[index].equals(s)) return false; // Element already exists.
            ArrayList<String> temp = new ArrayList<>();
            temp.add(s);
            hashElements(temp,false);
        }else{
            elementArray[index] = s;
            elementCount++;
        }
        return true;
    }

    @Override
    public boolean delete(String s){
        int index = hash.getHashValue(s);
        // Exceeds character limit, not found, or not the same element.
        if (index == -1 || elementArray[index] == null || !elementArray[index].equals(s)) return false;
        elementArray[index] = null;
        elementCount--;
        return true;
    }

    @Override
    public int batchInsert(ArrayList<String> s){
        // Calculate the new count after inserting the elements.
        int newCount = elementCount + s.size();
        int successes = 0;
        // If the new count will be greater than the current bound,
        // rehash with a new list composed of original + new elements.
        if (newCount > currentBound) {
            int fails = hashElements(s, false);
            successes = s.size() - fails;
        }else{
            for (String entry : s) {
                if (insert(entry)) successes++;
            }
        }
        return successes;
    }

    @Override
    public int batchDelete(ArrayList<String> s){
        int successes = 0;
        for (String entry: s) {
            if (delete(entry)) successes++;
        }
        return successes;
    }

    @Override
    public boolean search(String s){
        int index = hash.getHashValue(s);
        return (elementArray[index] != null && elementArray[index].equals(s));
    }

    @Override
    public int getRehashes() { return rehashes; }

    public int numberOfElements(){
        return elementCount;
    }

    public int tableSize(){
        return elementArray.length;
    }
}