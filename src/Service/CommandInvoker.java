package Service;
import java.util.Scanner;
public class CommandInvoker {
    public CLICommand invoke(Commands command) {
        return switch (command) {
            case SEARCH -> new Search();
            case INSERT -> new Insert();
            case DELETE -> new Delete();
            case BATCHINSERT -> new BatchInsert();
            case BATCHDELETE -> new BatchDelete();
        };
    }
}

class Search implements CLICommand {
    private final Scanner sc = new Scanner(System.in);
    @Override
    public long execute(/* INSERT HASH TABLE*/) {
        System.out.print("Enter Word to search: ");
        String word = sc.nextLine();
        boolean found;
        long startTime = System.nanoTime();
//        found = tree.search(word);
        found = false; //TODO replace this line with search method
        long endTime = System.nanoTime();
        if(found) {
            System.out.println("Word " + word + " Exists!!");
        } else {
            System.out.println("Word " + word + " does not exist");
        }
        return (endTime - startTime) / 1000;
    }
}

class Insert implements CLICommand {
    private final Scanner sc = new Scanner(System.in);
    @Override
    public long execute(/* INSERT HASH TABLE*/) {
        System.out.print("Enter Word to insert: ");
        String word = sc.nextLine();
        boolean found;
        long startTime = System.nanoTime();
//        found = tree.insert(word);
        found = false; //TODO replace this line with insert method
        long endTime = System.nanoTime();
        if(found) {
            System.out.println("Word " + word + " inserted successfully!!");
        } else {
            System.out.println("Word " + word + " already exists");
        }
        return (endTime - startTime) / 1000;
    }
}

class Delete implements CLICommand {
    private final Scanner sc = new Scanner(System.in);
    @Override
    public long execute(/* INSERT HASH TABLE*/) {
        System.out.print("Enter Word to delete: ");
        String word = sc.nextLine();
        boolean found;
        long startTime = System.nanoTime();
//        found = tree.delete(word);
        found = false; //TODO replace this line with delete method
        long endTime = System.nanoTime();
        if(found) {
            System.out.println("Word " + word + " deleted successfully!!");
        } else {
            System.out.println("Word " + word + " does not exist!");
        }
        return  (endTime - startTime) / 1000;
    }
}

//class Size implements CLICommands {
//    @Override
//    public long execute(BST<String> tree) {
//        long startTime = System.nanoTime();
//        System.out.println("Number of saved words = " + tree.getSize());
//        long endTime = System.nanoTime();
//        return (endTime - startTime) / 1000;
//    }
//}