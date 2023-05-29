package Hashing;
import java.util.ArrayList;

public class PerfectHashTable2 implements HashTable{
    private int elementCount, rehashes;
    private final DynamicHashTable[] table;
    private MatrixHash primaryHash;
    private final int maxStrBits;

    public PerfectHashTable2(int initialLevel1Size, int initialLevel2Size, int maxStrLen) throws Exception {
        int b = (int) Math.ceil(Math.log(initialLevel1Size)/Math.log(2));
        int roundedUpSize = (int) Math.pow(2,b);
        maxStrBits = maxStrLen * 8;
        table = new DynamicHashTable[roundedUpSize];
        primaryHash = new MatrixHash(b, maxStrBits);
        elementCount = 0;
        rehashes = 0;
        for (int i=0 ; i<roundedUpSize ; i++)
            table[i] = new DynamicHashTable(initialLevel2Size, maxStrLen);
    }

    public PerfectHashTable2(int initialLevel1Size, int maxStrLen) throws Exception {
        int b = (int) Math.ceil(Math.log(initialLevel1Size)/Math.log(2));
        int roundedUpSize = (int) Math.pow(2,b);
        maxStrBits = maxStrLen * 8;
        table = new DynamicHashTable[roundedUpSize];
        primaryHash = new MatrixHash(b, maxStrBits);
        elementCount = 0;
        rehashes = 0;
        for (int i=0 ; i<roundedUpSize ; i++)
            table[i] = new DynamicHashTable(0, maxStrLen);
    }

    public PerfectHashTable2(ArrayList<String> entries, int maxStrLen) {
        int entriesSize = entries.size();
        int b = (int) Math.ceil(Math.log(entriesSize)/Math.log(2));
        int roundedUpSize = (int) Math.pow(2,b);
        maxStrBits = maxStrLen * 8;
        table = new DynamicHashTable[roundedUpSize];
        elementCount = entriesSize;
        rehashes = -1;
        ArrayList<String>[] temp;
        // generate a primary hash function that satisfies the condition that Σ(ni^2) < 2N
        while (true){
            primaryHash = new MatrixHash(b, maxStrBits);
            rehashes++;
            temp = new ArrayList[roundedUpSize];
            for(int i=0 ; i < roundedUpSize ; i++) temp[i] = new ArrayList<>();
            for(String entry : entries) temp[primaryHash.getHashValue(entry)].add(entry);
            int tempElementsSquaredSum = 0;
            for(ArrayList<String> bucket : temp)
                tempElementsSquaredSum += Math.pow(bucket.size(), 2);
            if(tempElementsSquaredSum < 3 * temp.length)
                break;
        }
        for(int i = 0; i < roundedUpSize; i++)
            table[i] = new DynamicHashTable(temp[i], maxStrLen);
    }

    @Override
    public boolean insert(String s) {
        int index = primaryHash.getHashValue(s);
        if (index == -1) return false; // Exceeds character limit
        boolean success = table[index].insert(s);
        if (success) elementCount++;
        return success;
    }

    @Override
    public boolean delete(String s) {
        int index = primaryHash.getHashValue(s);
        if (index == -1) return false; // Exceeds character limit
        boolean success = table[index].delete(s);
        if (success) elementCount--;
        return success;
    }

    @Override
    public int batchInsert(ArrayList<String> s) {
        int successes = 0;
        for (String entry : s) if (insert(entry)) successes++;
        return successes;
    }

    @Override
    public int batchDelete(ArrayList<String> s) {
        int successes = 0;
        for (String entry : s) if (delete(entry)) successes++;
        return successes;
    }

    @Override
    public boolean search(String s) {
        int index = primaryHash.getHashValue(s);
        if (index == -1) return false; // Exceeds character limit
        return table[index].search(s);
    }

    public int numberOfElements() {return elementCount;}

    @Override
    public int getRehashes() {
        int total = rehashes; // Level 1 construction rehashes.
        for(DynamicHashTable t : table) total += t.getRehashes();
        return total;
    }

    public int tableSize() {
        int size = 0;
        for (DynamicHashTable t : table)
            size += t.tableSize();
        return  size;
    }

    public int primaryTableSize() {
        return table.length;
    }
}
