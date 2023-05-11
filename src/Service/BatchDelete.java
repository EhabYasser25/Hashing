package Service;
import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class BatchDelete implements CLICommand {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public long execute(/* INSERT HASH TABLE*/) {
        System.out.print("Enter File path to the file path: ");
        System.out.print("> ");
        String path = sc.nextLine();
        FileManager fileReader = new FileManager();
        List<String> words = fileReader.readFile(path);
        long startTime = System.nanoTime();
        Point feedback = batchDelete(/* INSERT HASH TABLE*/ words); //TODO add hash table param to batch delete
        long endTime = System.nanoTime();
        System.out.println("Number of words deleted: " + feedback.x + "\nNumber of words not found: " + feedback.y);
        return (endTime - startTime) / 1000;
    }

    public Point batchDelete(/* INSERT HASH TABLE*/ List<String> items) {
        int found = 0, notFound = 0;
        for (String item : items) {
            //TODO implement delete method in hash table such that it returns boolean
//            if (tree.delete(item)) found++;
//            else notFound++;
        }
        return new Point(found, notFound);
    }
}