package Service;
import Hashing.HashTable;

import java.util.Scanner;
public class CommandInvoker {
    public CLICommand invoke(Commands command) {
        return switch (command) {
            case REHASHES -> new Rehashes();
            case SEARCH -> new Search();
            case INSERT -> new Insert();
            case DELETE -> new Delete();
            case BATCHINSERT -> new BatchInsert();
            case BATCHDELETE -> new BatchDelete();
        };
    }
}

class Rehashes implements CLICommand {
    @Override
    public long execute(HashTable table) {
        System.out.println("The table has been rehashed " + table.getRehashes() + " times.");
        return 0;
    }
}

class Search implements CLICommand {
    private final Scanner sc = new Scanner(System.in);
    @Override
    public long execute(HashTable table) {
        System.out.print("Enter Word to search: ");
        String word = sc.nextLine();
        if (word.length() > table.getMaxStrLen()){
            System.out.println("The word entered exceeds the character limit.");
            return 0;
        }
        boolean found;
        long startTime = System.nanoTime();
        found = table.search(word);
        long endTime = System.nanoTime();
        if(found){
            System.out.println("Word " + word + " exists.");
        } else {
            System.out.println("Word " + word + " does not exist.");
        }
        return (endTime - startTime) / 1000;
    }
}

class Insert implements CLICommand {
    private final Scanner sc = new Scanner(System.in);
    @Override
    public long execute(HashTable table) {
        System.out.print("Enter Word to insert: ");
        String word = sc.nextLine();
        if (word.length() > table.getMaxStrLen()){
            System.out.println("The word entered exceeds the character limit.");
            return 0;
        }
        boolean found;
        long startTime = System.nanoTime();
        found = table.insert(word);
        long endTime = System.nanoTime();
        if(found) {
            System.out.println("Word " + word + " inserted successfully.");
        } else {
            System.out.println("Insertion failed due to word already existing or reaching size limit.");
        }
        return (endTime - startTime) / 1000;
    }
}

class Delete implements CLICommand {
    private final Scanner sc = new Scanner(System.in);
    @Override
    public long execute(HashTable table) {
        System.out.print("Enter Word to delete: ");
        String word = sc.nextLine();
        if (word.length() > table.getMaxStrLen()){
            System.out.println("The word entered exceeds the character limit.");
            return 0;
        }
        boolean found;
        long startTime = System.nanoTime();
        found = table.delete(word);
        long endTime = System.nanoTime();
        if(found) {
            System.out.println("Word " + word + " deleted successfully.");
        } else {
            System.out.println("Word " + word + " does not exist.");
        }
        return  (endTime - startTime) / 1000;
    }
}
