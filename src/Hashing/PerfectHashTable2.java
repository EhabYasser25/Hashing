package Hashing;
import java.util.ArrayList;

public class PerfectHashTable2 implements HashTable{
    private int elementCount, rehashes;
    private final DynamicHashTable[] table;
    private MatrixHash primaryHash;
    private final int maxStrBits;

    public PerfectHashTable2(int initialLevel1Size, int initialLevel2Size, int maxStrLen) throws Exception {
        rehashes = 0;
        elementCount = 0;
        maxStrBits = maxStrLen * 8;
        primaryHash = new MatrixHash((int) Math.ceil(Math.log(initialLevel1Size)/Math.log(2)), maxStrBits);
        table = new DynamicHashTable[initialLevel1Size];
        for (int i=0 ; i<initialLevel1Size ; i++)
            table[i] = new DynamicHashTable(initialLevel2Size, maxStrLen);
    }

    public PerfectHashTable2(int initialLevel1Size, int maxStrLen) throws Exception {
        elementCount = 0;
        rehashes = 0;
        maxStrBits = maxStrLen * 8;
        table = new DynamicHashTable[initialLevel1Size];
        primaryHash = new MatrixHash((int) Math.ceil(Math.log(initialLevel1Size)/Math.log(2)), maxStrBits);
        for (int i=0 ; i<initialLevel1Size ; i++)
            table[i] = new DynamicHashTable(10, maxStrLen);
    }

    public PerfectHashTable2(ArrayList<String> entries, int maxStrLen) {
        rehashes = -1;
        elementCount = entries.size();
        maxStrBits = maxStrLen * 8;
        ArrayList<String>[] temp;
        int entriesSize = entries.size();
        // generate a primary hash function that satisfies the condition that Σ(ni^2) < 2N
        while (true){
            primaryHash = new MatrixHash((int) Math.ceil(Math.log(entriesSize)/Math.log(2)), maxStrBits);
            rehashes++;
            temp = new ArrayList[entriesSize];
            for(int i=0 ; i < entriesSize ; i++) temp[i] = new ArrayList<>();
            for(String entry : entries) temp[primaryHash.getHashValue(entry)].add(entry);
            int tempElementsSquaredSum = 0;
            for(ArrayList<String> bucket : temp)
                tempElementsSquaredSum += Math.pow(bucket.size(), 2);
            if(tempElementsSquaredSum < 2 * temp.length)
                break;
        }
        table = new DynamicHashTable[entriesSize];
        for(int i = 0; i < entriesSize; i++)
            table[i] = new DynamicHashTable(temp[i], maxStrLen);
    }

    @Override
    public boolean insert(String s) {
        boolean success = table[primaryHash.getHashValue(s)].insert(s);
        if (success) elementCount++;
        return success;
    }

    @Override
    public boolean delete(String s) {
        boolean success = table[primaryHash.getHashValue(s)].delete(s);
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
    public boolean search(String s) {return table[primaryHash.getHashValue(s)].search(s);}

    public int numberOfElements() {return elementCount;}

    public int tableSize() {
        int size = 0;
        for (DynamicHashTable t : table)
            size += t.tableSize();
        return  size;
    }

    public int getRehashes() {
        int total = rehashes; // Level 1 construction rehashes.
        for(DynamicHashTable t : table) total += t.getRehashes();
        return total;
    }
}
