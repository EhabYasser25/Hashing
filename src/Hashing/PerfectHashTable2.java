package Hashing;

import java.util.ArrayList;
import java.util.Arrays;

public class PerfectHashTable2 implements HashTable{
    MatrixHash function;
    PerfectHashTable1[] table;
    int maxKeyBitLength;
    String[] keys;
    public PerfectHashTable2(String[] entries, int maxLen) {
        this.init(entries, maxLen);
    }

    private void init(String[] entries, int maxLen){
        this.maxKeyBitLength = maxLen;
        this.keys = entries;
        ArrayList<String>[] temp;

        // generate a hash function that satisfies the condition that Σ(ni^2) < 4N
        while (true){
            this.function = new MatrixHash((int) Math.ceil(Math.log(entries.length)/Math.log(2)), maxKeyBitLength);
            temp = new ArrayList[entries.length];
            for(int i=0 ; i < entries.length ; i++) temp[i] = new ArrayList<>();
            for(String entry : entries) temp[function.getStringKey(entry)].add(entry);
            int tempElementsSquaredSum = 0;
            for(ArrayList<String> bucket : temp)
                tempElementsSquaredSum += Math.pow(bucket.size(), 2);
            if(tempElementsSquaredSum < 4 * temp.length)
                break;
        }

        this.table = new PerfectHashTable1[entries.length];

        for(int i = 0; i < entries.length; i++)
            this.table[i] = new PerfectHashTable1(temp[i].toArray(new String[0]), this.maxKeyBitLength);
    }

    @Override
    public boolean insert(String s){
        return this.table[this.function.getStringKey(s)].insert(s);
    }

    @Override
    public boolean delete(String s){
        return this.table[this.function.getStringKey(s)].delete(s);
    }

    @Override
    public int batchInsert(String[] s){
        String[] totalKeys = new String[s.length + this.keys.length];
        System.arraycopy(this.keys, 0, totalKeys, 0, this.keys.length);
        System.arraycopy(s, 0, totalKeys, this.keys.length, s.length);
        this.init(totalKeys, this.maxKeyBitLength);
        return 0;
    }

    @Override
    public int batchDelete(String[] s){
        ArrayList<String> filteredKeys = new ArrayList<>(Arrays.asList(this.keys));
        for(String delKey : s)
            filteredKeys.remove(delKey);
        this.init(filteredKeys.toArray(new String[0]), this.maxKeyBitLength);
        return 0;
    }

    @Override
    public boolean search(String s){
        return this.table[function.getStringKey(s)].search(s);
    }
}
