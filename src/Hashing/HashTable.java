package Hashing;

public interface HashTable {
    boolean insert(String s);
    int batchInsert(String[] s);
    boolean delete(String s);
    int batchDelete(String[] s);
    boolean search(String s);
}
