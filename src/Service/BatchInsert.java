package Service;
import Hashing.HashTable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BatchInsert implements CLICommand {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public long execute(HashTable table) {
        System.out.println("Enter file path that contains the list of words ");
        System.out.print("> ");
        String path = sc.nextLine();
        ArrayList<String> words = FileManager.readFile(path);
        if(words == null)
            return 0;
        long startTime = System.nanoTime();
        Point feedback = batchInsert(table, words);
        long endTime = System.nanoTime();
        System.out.println("Number of words inserted successfully: " + feedback.y +
                "\nNumber of words that could not be inserted: " + feedback.x);
        return (endTime - startTime) / 1000;
    }

    public Point batchInsert(HashTable table, ArrayList<String> items) {
        int successes = table.batchInsert(items);
        return new Point(items.size()-successes, successes);
    }
}