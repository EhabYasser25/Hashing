package Hashing;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface HashTable {
    boolean insert(String s);
    int batchInsert(ArrayList<String> s);
    boolean delete(String s);
    int batchDelete(ArrayList<String> s);
    boolean search(String s);
}
