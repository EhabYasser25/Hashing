package Service;
import Hashing.HashTable;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class BatchInsert implements CLICommand {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public long execute(HashTable table) {
        System.out.print("Enter File path to the file path: ");
        System.out.print("> ");
        String path = sc.nextLine();
        FileManager fileReader = new FileManager();
        List<String> words = fileReader.readFile(path);
        new Point();
        long startTime = System.nanoTime();
        Point feedback = batchInsert(table, words); //TODO add hash table param to batch delete
        long endTime = System.nanoTime();
        System.out.println("Number of words inserted: " + feedback.y + "\nNumber of words found: " + feedback.x);
        return (endTime - startTime) / 1000;
    }

    public Point batchInsert(HashTable table, List<String> items) {
        int found = 0, notFound = 0;
        for (String item : items) {
            //TODO implement delete method in hash table such that it returns boolean
            if (table.insert(item)) notFound++;
            else found++;
        }
//        System.out.println(tree.getSize());
        return new Point(found, notFound);
    }
}