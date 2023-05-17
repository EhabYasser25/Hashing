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
    private ArrayList<String> elementArray;
    private MatrixHash hash;

    public DynamicHashTable(ArrayList<String> initialList) {
        rehashes = -1;
        hashElements(initialList, true);
    }

    public DynamicHashTable(int initialSize) throws Exception {
        if (initialSize < 0) throw new Exception("Negative initial size for a hash table.");
        elementCount = 0;
        currentBound = initialSize;
        int nSquared = (int) Math.pow(initialSize,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (nSquared != 0)? (int) Math.ceil(Math.log(nSquared)/Math.log(2)) : 0;
        // Set table size to 2^b
        hash = new MatrixHash(b);
        elementArray = new ArrayList<>((int)Math.pow(2,b));
    }

    private int hashElements(ArrayList<String> elementsToHash, boolean init) {
        int index, fails = 0;
        boolean collisions = true;
        ArrayList<String> nonNull;
        if (init)
            nonNull = new ArrayList<>(elementsToHash);
        else {
            nonNull = new ArrayList<>();
            for (String s : elementsToHash) if (s != null) nonNull.add(s);
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
            elementArray = new ArrayList<>((int)Math.pow(2,b));
            hash = new MatrixHash(b);
            collisions = false; fails = 0;
            for (String entry : nonNull) {
                index = hash.getHashValue(entry);
                // Only insert if the element is in an empty spot.
                if (elementArray.get(index) == null)
                    elementArray.set(index, entry);
                else if (elementArray.get(index).equals(entry)) // Element already exists
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
        // Element does not exist
        if (elementArray.get(index) != null) {
            if (elementArray.get(index).equals(s)) return false; // Element already exists.
            // TODO Change to deep copy if needed
            ArrayList<String> temp = new ArrayList<>(elementArray);
            temp.add(s);
            hashElements(temp,false);
        }else{
            elementArray.set(index,s);
            elementCount++;
        }
        return true;
    }

    @Override
    public boolean delete(String s){
        int index = hash.getHashValue(s);
        if (elementArray.get(index) == null) return false;
        elementArray.set(index, null);
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
            ArrayList<String> temp = new ArrayList<>(elementArray);
            temp.addAll(s);
            int fails = hashElements(temp, false);
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
        return (elementArray.get(index) != null && elementArray.get(index).equals(s));
    }

    public int numberOfElements(){
        return elementCount;
    }

    public int tableSize(){
        return elementArray.size();
    }

    public int getRehashes() { return rehashes; }
}