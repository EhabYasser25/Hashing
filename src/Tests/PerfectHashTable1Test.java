package Tests;

import Hashing.PerfectHashTable1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PerfectHashTable1Test {

    @Test
    public void testInsertion() throws Exception {
        PerfectHashTable1 hashTable = new PerfectHashTable1(10);
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
        PerfectHashTable1 hashTable = new PerfectHashTable1(10);
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
        PerfectHashTable1 hashTable = new PerfectHashTable1(10);
        System.out.println(1);
        hashTable.insert("apple");
        System.out.println(2);
        hashTable.insert("banana");
        System.out.println(3);
        hashTable.insert("cherry");
        System.out.println(4);

        System.out.println(hashTable.getRehashes());

        Assertions.assertTrue(hashTable.search("banana"));
        System.out.println(5);
        Assertions.assertFalse(hashTable.search("kiwi"));
        System.out.println(6);
    }
    
}
