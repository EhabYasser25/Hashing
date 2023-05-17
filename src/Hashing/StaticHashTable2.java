package Hashing;

import java.util.ArrayList;

public class StaticHashTable2 implements HashTable{
    private int elementCount, rehashes, totalSize;
    private final StaticHashTable1[] table;
    private MatrixHash primaryHash;

    public StaticHashTable2(int initialLevel1Size, int initialLevel2Size) throws Exception {
        elementCount = 0;
        rehashes = 0;
        totalSize = 0;
        table = new StaticHashTable1[initialLevel1Size];
        for (int i=0 ; i<initialLevel1Size ; i++) {
            table[i] = new StaticHashTable1(initialLevel2Size);
            totalSize += table[i].tableSize();
        }
    }

    public StaticHashTable2(ArrayList<String> entries) {
        rehashes = -1;
        elementCount = entries.size();
        ArrayList<String>[] temp;
        int entriesSize = entries.size();
        // generate a primary hash function that satisfies the condition that Σ(ni^2) < 4N
        while (true){
            this.primaryHash = new MatrixHash((int) Math.ceil(Math.log(entriesSize)/Math.log(2)));
            rehashes++;
            temp = new ArrayList[entriesSize];
            for(int i=0 ; i < entriesSize ; i++) temp[i] = new ArrayList<>();
            for(String entry : entries) temp[primaryHash.getHashValue(entry)].add(entry);
            int tempElementsSquaredSum = 0;
            for(ArrayList<String> bucket : temp)
                tempElementsSquaredSum += Math.pow(bucket.size(), 2);
            if(tempElementsSquaredSum < 4 * temp.length)
                break;
        }
        table = new StaticHashTable1[entriesSize];
        for(int i = 0; i < entriesSize; i++) {
            table[i] = new StaticHashTable1(temp[i]);
            totalSize = table[i].tableSize();
            rehashes += table[i].getRehashes();
        }
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
    public boolean search(String s) {
        return table[primaryHash.getHashValue(s)].search(s);
    }

    public int numberOfElements(){
        return elementCount;
    }

    public int tableSize(){
        return totalSize;
    }

    public int getRehashes() { return rehashes; }
}
