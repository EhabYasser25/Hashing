package Hashing;

import java.util.ArrayList;

public class PerfectHashTable2 implements HashTable{
    private int elementCount, rehashes;
    private PerfectHashTable1[] table;
    private MatrixHash hash;
    public PerfectHashTable2(ArrayList<String> entries) {
        this.hashElements(entries);
    }

    private void hashElements(ArrayList<String> entries){
        ArrayList<String>[] temp;
        int entriesSize = entries.size();
        // generate a hash function that satisfies the condition that Σ(ni^2) < 4N
        while (true){
            this.hash = new MatrixHash((int) Math.ceil(Math.log(entriesSize)/Math.log(2)));
            temp = new ArrayList[entriesSize];
            for(int i=0 ; i < entriesSize ; i++) temp[i] = new ArrayList<>();
            for(String entry : entries) temp[hash.getHashValue(entry)].add(entry);
            int tempElementsSquaredSum = 0;
            for(ArrayList<String> bucket : temp)
                tempElementsSquaredSum += Math.pow(bucket.size(), 2);
            if(tempElementsSquaredSum < 4 * temp.length)
                break;
        }
        table = new PerfectHashTable1[entriesSize];
        elementCount = 0;
        for(int i = 0; i < entriesSize; i++) {
            table[i] = new PerfectHashTable1(temp[i]);
            elementCount += table[i].numberOfElements();
        }
    }

    @Override
    public boolean insert(String s){
        boolean success = table[hash.getHashValue(s)].insert(s);
        if (success)
            elementCount++;
        return success;
    }

    @Override
    public boolean delete(String s){
        boolean success = table[hash.getHashValue(s)].delete(s);
        if (success)
            elementCount--;
        return success;
    }

    @Override
    public int batchInsert(ArrayList<String> s){
        int successes = 0;
        for(String delValue : s)
            if(insert(delValue)) successes++;
        return successes;
    }

    @Override
    public int batchDelete(ArrayList<String> s){
        int successes = 0;
        for(String delValue : s)
            if(delete(delValue)) successes++;
        return successes;
    }

    @Override
    public boolean search(String s){
        return this.table[hash.getHashValue(s)].search(s);
    }
}
