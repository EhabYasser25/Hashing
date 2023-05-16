package Hashing;

import java.util.ArrayList;

public class PerfectHashTable2 implements HashTable{
    MatrixHash initialHash;
    MatrixHash[] secondaryHashes;
    int maxStrLen;
    int size;
    public PerfectHashTable2(String[] entries, int maxLen) {
        maxStrLen = maxLen;
        size = entries.length;
        initialHash = new MatrixHash((int) Math.ceil(Math.log(size)/Math.log(2)), maxStrLen);
        ArrayList<String>[] temp = new ArrayList[size];
        for(int i=0 ; i<size ; i++) temp[i] = new ArrayList<>();
        for(String entry : entries) temp[initialHash.getStringKey(entry)].add(entry);

    }

    @Override
    public boolean insert(String s){
        return false;
    }

    @Override
    public boolean delete(String s){
        return false;
    }

    @Override
    public int batchInsert(String[] s){
        return 0;
    }

    @Override
    public int batchDelete(String[] s){
        return 0;
    }

    @Override
    public boolean search(String s){
        return false;
    }
}
