package Analysis;
import Hashing.PerfectHashTable1;
import Hashing.PerfectHashTable2;
import Service.BatchInsert;
import Service.CommandInvoker;
import Service.Commands;
import Service.FileManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Analysis {
    @Test
    void HashSearch() throws Exception {
        List<Long> hash_search_times = new ArrayList<>();
        String word = "hgjukytghu";
        for(int i = 1; i <= 120; i++) {
            long time = 0;
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\Comaprison\\SO" + Integer.toString(i) + ".txt");
            PerfectHashTable2 table = new PerfectHashTable2(1000 * i, 10);
            table.batchInsert((ArrayList<String>) words);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.search(word);
                long end = System.nanoTime();
                time += (end - start);
            }
            hash_search_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(hash_search_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\HashSearchComparison.txt");
    }

    @Test
    void search_analysis() throws Exception {
        table1SearchAnalysis();
        table2SearchAnalysis();
    }

    void table1SearchAnalysis() throws Exception {
        List<Long> table1_times = new ArrayList<>();
        String word = "etygvtcnspuzzwaceuudeuusrzxhti";
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable1 table = new PerfectHashTable1(150 * i, 32);
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\SO\\SO" + Integer.toString(i) + ".txt");
            table.batchInsert((ArrayList<String>) words);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.search(word);
                long end = System.nanoTime();
                time += (end - start);
            }
            System.gc();
            table1_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table1_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T1Search.txt");
    }

    void table2SearchAnalysis() throws Exception {
        List<Long> table2_times = new ArrayList<>();
        String word = "etygvtcnspuzzwaceuudeuusrzxhti";
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable2 table = new PerfectHashTable2(150 * i, 32);
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\SO\\SO" + Integer.toString(i) + ".txt");
            table.batchInsert((ArrayList<String>) words);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.search(word);
                long end = System.nanoTime();
                time += (end - start);
            }
            System.gc();
            table2_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table2_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T2Search.txt");
    }

    @Test
    void insert_analysis() throws Exception {
        table1InsertAnalysis();
        table2InsertAnalysis();
    }

    void table1InsertAnalysis() throws Exception {
        List<Long> table1_times = new ArrayList<>();
        String word = "etygvtcnspuzzwaceuudeuusrzxhti";
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable1 table = new PerfectHashTable1(150 * i, 32);
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\SO\\SO" + Integer.toString(i) + ".txt");
            table.batchInsert((ArrayList<String>) words);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.insert(word);
                long end = System.nanoTime();
                time += (end - start);
            }
            table1_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table1_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T1Insert.txt");
    }

    void table2InsertAnalysis() throws Exception {
        List<Long> table2_times = new ArrayList<>();
        String word = "etygvtcnspuzzwaceuudeuusrzxhti";
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable2 table = new PerfectHashTable2(150 * i, 32);
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\SO\\SO" + Integer.toString(i) + ".txt");
            table.batchInsert((ArrayList<String>) words);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.insert(word);
                long end = System.nanoTime();
                time += (end - start);
            }
            table2_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table2_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T2Insert.txt");
    }

    @Test
    void delete_analysis() throws Exception {
        table1DeleteAnalysis();
        table2DeleteAnalysis();
    }

    void table1DeleteAnalysis() throws Exception {
        List<Long> table1_times = new ArrayList<>();
        String word = "etygvtcnspuzzwaceuudeuusrzxhti";
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable1 table = new PerfectHashTable1(150 * i, 32);
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\SO\\SO" + Integer.toString(i) + ".txt");
            table.batchInsert((ArrayList<String>) words);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.delete(word);
                long end = System.nanoTime();
                time += (end - start);
            }
            table1_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table1_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T1Delete.txt");
    }

    void table2DeleteAnalysis() throws Exception {
        List<Long> table2_times = new ArrayList<>();
        String word = "etygvtcnspuzzwaceuudeuusrzxhti";
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable2 table = new PerfectHashTable2(150 * i, 32);
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\SO\\SO" + Integer.toString(i) + ".txt");
            table.batchInsert((ArrayList<String>) words);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.delete(word);
                long end = System.nanoTime();
                time += (end - start);
            }
            table2_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table2_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T2Delete.txt");
    }

    @Test
    void batch_insert_analysis() throws Exception {
        table1BInsertAnalysis();
        table2BInsertAnalysis();
    }

    void table1BInsertAnalysis() throws Exception {
        List<Long> table1_times = new ArrayList<>();
        ArrayList<String> base = (ArrayList<String>) FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\bInsert.txt");
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable1 table = new PerfectHashTable1(150 * i, 32);
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\DS\\DS" + Integer.toString(i) + ".txt");
            table.batchInsert(base);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.batchInsert((ArrayList<String>) words);
                long end = System.nanoTime();
                time += (end - start);
            }
            System.gc();
            table1_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table1_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T1BInsert.txt");
    }

    void table2BInsertAnalysis() throws Exception {
        List<Long> table2_times = new ArrayList<>();
        ArrayList<String> base = (ArrayList<String>) FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\bInsert.txt");
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable2 table = new PerfectHashTable2(150 * i, 32);
            ArrayList<String> words = (ArrayList<String>) FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\DS\\DS" + Integer.toString(i) + ".txt");
            table.batchInsert(base);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.batchInsert(words);
                long end = System.nanoTime();
                time += (end - start);
            }
            table2_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table2_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T2BInsert.txt");
    }

    @Test
    void batch_delete_analysis() throws Exception {
        table1BDeleteAnalysis();
        table2BDeleteAnalysis();
    }

    void table1BDeleteAnalysis() throws Exception {
        List<Long> table1_times = new ArrayList<>();
        ArrayList<String> base = (ArrayList<String>) FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\bDelete.txt");
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable1 table = new PerfectHashTable1(150 * i, 32);
            List<String> words = FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\DS\\DS" + Integer.toString(i) + ".txt");
            table.batchInsert(base);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.batchDelete((ArrayList<String>) words);
                long end = System.nanoTime();
                time += (end - start);
            }
            System.gc();
            table1_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table1_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T1BDelete.txt");
    }

    void table2BDeleteAnalysis() throws Exception {
        List<Long> table2_times = new ArrayList<>();
        ArrayList<String> base = (ArrayList<String>) FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\bDelete.txt");
        for(int i = 1; i <= 100; i++) {
            long time = 0;
            PerfectHashTable2 table = new PerfectHashTable2(150 * i, 32);
            ArrayList<String> words = (ArrayList<String>) FileManager.readFile("D:\\CSED\\semester4\\Plotter\\New_Data\\DS\\DS" + Integer.toString(i) + ".txt");
            table.batchInsert(base);
            for(int j = 0; j < 10; j++) {
                long start = System.nanoTime();
                table.batchDelete(words);
                long end = System.nanoTime();
                time += (end - start);
            }
            table2_times.add(time / 10);
            System.out.println(i);
        }
        FileManager.writeLongToFile(table2_times, "D:\\CSED\\semester4\\Plotter\\New_Data\\T2BDelete.txt");
    }

}
