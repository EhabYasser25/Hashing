package Service;
import Hashing.HashTable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BatchDelete implements CLICommand {
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
        Point feedback = batchDelete(table, words);
        long endTime = System.nanoTime();
        System.out.println("Number of words deleted successfully: " + feedback.x +
                "\nNumber of words that could not be deleted: " + feedback.y);
        return (endTime - startTime) / 1000;
    }

    public Point batchDelete(HashTable table, ArrayList<String> items) {
        int successes = table.batchDelete(items);
        return new Point(successes, items.size()-successes);
    }
}