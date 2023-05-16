package Hashing;

public interface HashTable {
    boolean insert(String s);
    boolean batchInsert(String[] s);
    boolean delete(String s);
    boolean batchDelete(String[] s);
    boolean search(String s);
}
