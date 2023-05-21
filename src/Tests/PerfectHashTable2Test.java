package Tests;

import Hashing.PerfectHashTable2;

import Service.FileManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PerfectHashTable2Test {

    @Test
    public void testInsertion() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(10,6);
        Assertions.assertEquals(0, hashTable.numberOfElements());
        Assertions.assertEquals(16, hashTable.tableSize());

        boolean success1 = hashTable.insert("apple");
        Assertions.assertTrue(success1);
        Assertions.assertEquals(1, hashTable.numberOfElements());

        boolean success2 = hashTable.insert("banana");
        Assertions.assertTrue(success2);
        Assertions.assertEquals(2, hashTable.numberOfElements());

        boolean success3 = hashTable.insert("cherry");
        Assertions.assertTrue(success3);
        Assertions.assertEquals(3, hashTable.numberOfElements());
    }

    @Test
    public void testDeletion() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(10, 6);
        hashTable.insert("apple");
        hashTable.insert("banana");
        hashTable.insert("cherry");

        Assertions.assertEquals(3, hashTable.numberOfElements());

        boolean success1 = hashTable.delete("banana");
        Assertions.assertTrue(success1);
        Assertions.assertEquals(2, hashTable.numberOfElements());

        boolean success2 = hashTable.delete("kiwi");
        Assertions.assertFalse(success2);
        Assertions.assertEquals(2, hashTable.numberOfElements());
    }

    @Test
    public void testSearch() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(10, 6);
        hashTable.insert("apple");
        hashTable.insert("banana");
        hashTable.insert("cherry");

        System.out.println(hashTable.getRehashes());

        Assertions.assertTrue(hashTable.search("banana"));
        Assertions.assertFalse(hashTable.search("kiwi"));
    }

    @Test
    public void testBatchInsertion() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(10, 6);
        Assertions.assertEquals(0, hashTable.numberOfElements());

        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("cherry");

        int successCount = hashTable.batchInsert(fruits);
        Assertions.assertEquals(3, successCount);
        Assertions.assertEquals(3, hashTable.numberOfElements());
    }

    @Test
    public void testBatchDeletion() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(10, 6);
        hashTable.insert("apple");
        hashTable.insert("banana");
        hashTable.insert("cherry");

        Assertions.assertEquals(3, hashTable.numberOfElements());

        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("banana");
        fruits.add("kiwi");

        int successCount = hashTable.batchDelete(fruits);
        Assertions.assertEquals(1, successCount);
        Assertions.assertEquals(2, hashTable.numberOfElements());
    }

    @Test
    public void testInsertionCollision() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(1, 6);
        Assertions.assertEquals(0, hashTable.numberOfElements());

        boolean success1 = hashTable.insert("apple");
        Assertions.assertTrue(success1);
        Assertions.assertEquals(1, hashTable.primaryTableSize());
        Assertions.assertEquals(1, hashTable.tableSize());
        Assertions.assertEquals(1, hashTable.numberOfElements());

        boolean success2 = hashTable.insert("banana");
        Assertions.assertTrue(success2);
        Assertions.assertEquals(1, hashTable.primaryTableSize());
        Assertions.assertEquals(4, hashTable.tableSize());
        Assertions.assertEquals(2, hashTable.numberOfElements());

        boolean success3 = hashTable.insert("cherry");
        Assertions.assertTrue(success3);
        Assertions.assertEquals(1, hashTable.primaryTableSize());
        Assertions.assertEquals(16, hashTable.tableSize());
        Assertions.assertEquals(3, hashTable.numberOfElements());
    }

    @Test
    public void testDeletionNonexistent() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(10, 6);
        hashTable.insert("apple");
        hashTable.insert("banana");
        hashTable.insert("cherry");

        Assertions.assertEquals(3, hashTable.numberOfElements());

        boolean success = hashTable.delete("kiwi");
        Assertions.assertFalse(success);
        Assertions.assertEquals(3, hashTable.numberOfElements());
    }

    @Test
    public void testBatchInsertionPartial() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(4, 6);
        hashTable.insert("cherry");
        Assertions.assertEquals(1, hashTable.numberOfElements());

        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("cherry");
        fruits.add("kiwi");
        fruits.add("mango");

        int successCount = hashTable.batchInsert(fruits);
        Assertions.assertEquals(4, successCount);
        Assertions.assertEquals(5, hashTable.numberOfElements());
    }

    @Test
    public void testBatchDeletionPartial() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(5, 6);
        hashTable.insert("apple");
        hashTable.insert("banana");
        hashTable.insert("cherry");
        hashTable.insert("kiwi");
        hashTable.insert("mango");

        Assertions.assertEquals(5, hashTable.numberOfElements());

        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("banana");
        fruits.add("kiwi");
        fruits.add("grape");

        int successCount = hashTable.batchDelete(fruits);
        Assertions.assertEquals(2, successCount);
        Assertions.assertEquals(3, hashTable.numberOfElements());
    }

    @Test
    public void testRehashing() throws Exception {
        PerfectHashTable2 hashTable = new PerfectHashTable2(1000000, 15);
        Assertions.assertEquals(0, hashTable.numberOfElements());
        Assertions.assertEquals(1048576, hashTable.tableSize());

        ArrayList<String> words = (ArrayList<String>) FileManager.readFile("./random_strings.txt");
        hashTable.batchInsert(words);
        Assertions.assertEquals(1000000, hashTable.numberOfElements());
//        System.out.println(hashTable.getRehashes());
        Assertions.assertTrue(hashTable.getRehashes() > 0);
    }

    @Test
    public void testEmptyTable() throws Exception {
        PerfectHashTable2 table = new PerfectHashTable2(10, 6);
        Assertions.assertEquals(0, table.numberOfElements());
        Assertions.assertEquals(16, table.tableSize());
        Assertions.assertEquals(0, table.getRehashes());
    }

    @Test
    public void testFullTable() throws Exception {
        PerfectHashTable2 table = new PerfectHashTable2(1, 6);
        table.insert("hello");
        Assertions.assertEquals(1, table.numberOfElements());
        Assertions.assertEquals(1, table.tableSize());
        Assertions.assertEquals(0, table.getRehashes());
    }
}
