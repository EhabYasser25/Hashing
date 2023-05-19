package Tests;

import Hashing.PerfectHashTable1;

import Service.FileManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PerfectHashTable1Test {

    @Test
    public void testInsertion() throws Exception {
        PerfectHashTable1 hashTable = new PerfectHashTable1(10,6);
        Assertions.assertEquals(0, hashTable.numberOfElements());
        Assertions.assertEquals(128, hashTable.tableSize());

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
        PerfectHashTable1 hashTable = new PerfectHashTable1(10,6);
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
        PerfectHashTable1 hashTable = new PerfectHashTable1(10,6);
        hashTable.insert("apple");
        hashTable.insert("banana");
        hashTable.insert("cherry");

        System.out.println(hashTable.getRehashes());

        Assertions.assertTrue(hashTable.search("banana"));
        Assertions.assertFalse(hashTable.search("kiwi"));
    }

    @Test
    public void testBatchInsertion() throws Exception {
        PerfectHashTable1 hashTable = new PerfectHashTable1(10,6);
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
        PerfectHashTable1 hashTable = new PerfectHashTable1(10,6);
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
        PerfectHashTable1 hashTable = new PerfectHashTable1(1,6);
        Assertions.assertEquals(0, hashTable.numberOfElements());

        boolean success1 = hashTable.insert("apple");
        Assertions.assertTrue(success1);
        Assertions.assertEquals(1, hashTable.numberOfElements());

        boolean success2 = hashTable.insert("banana");
        Assertions.assertFalse(success2);
        Assertions.assertEquals(1, hashTable.numberOfElements());

        boolean success3 = hashTable.insert("cherry");
        Assertions.assertFalse(success3);
        Assertions.assertEquals(1, hashTable.numberOfElements());
    }

    @Test
    public void testDeletionNonexistent() throws Exception {
        PerfectHashTable1 hashTable = new PerfectHashTable1(10,6);
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
        PerfectHashTable1 hashTable = new PerfectHashTable1(4,6);
        hashTable.insert("cherry");
        Assertions.assertEquals(1, hashTable.numberOfElements());

        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("cherry");
        fruits.add("kiwi");
        fruits.add("mango");

        int successCount = hashTable.batchInsert(fruits);
        Assertions.assertEquals(3, successCount);
        Assertions.assertEquals(4, hashTable.numberOfElements());
    }

    @Test
    public void testBatchDeletionPartial() throws Exception {
        PerfectHashTable1 hashTable = new PerfectHashTable1(5,6);
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
        PerfectHashTable1 hashTable = new PerfectHashTable1(15000,6);
        Assertions.assertEquals(0, hashTable.numberOfElements());
        Assertions.assertEquals(268435456, hashTable.tableSize());

        FileManager fileReader = new FileManager();
        ArrayList<String> words = fileReader.readFile("./random_strings.txt");
        hashTable.batchInsert(words);
        System.out.println(hashTable.getRehashes());
        Assertions.assertEquals(15000, hashTable.numberOfElements());
    }

    @Test
    public void testRehashes() throws Exception {
        PerfectHashTable1 table = new PerfectHashTable1(10,7);
        table.insert("hello");
        table.insert("world");
        table.insert("goodbye");
    }

    @Test
    public void testEmptyTable() throws Exception {
        PerfectHashTable1 table = new PerfectHashTable1(10,7);
        Assertions.assertEquals(0, table.numberOfElements());
        Assertions.assertEquals(128, table.tableSize());
        Assertions.assertEquals(0, table.getRehashes());
    }

    @Test
    public void testFullTable() throws Exception {
        PerfectHashTable1 table = new PerfectHashTable1(1,5);
        table.insert("hello");
        Assertions.assertEquals(1, table.numberOfElements());
        Assertions.assertEquals(1, table.tableSize());
        Assertions.assertEquals(0, table.getRehashes());
    }
}
