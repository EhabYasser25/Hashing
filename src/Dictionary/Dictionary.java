package Dictionary;
import Hashing.HashTable;
import Hashing.PerfectHashTable1;
import Hashing.PerfectHashTable2;
import Service.CLICommand;
import Service.CommandInvoker;
import Service.Commands;
import Service.FileManager;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary implements IDictionary {
    HashTable ht;
    CommandInvoker invoker = new CommandInvoker();
    CLICommand command;
    Commands eCommand;
    int tableType = 0;

    public void startProgram() {
        printIntro();
        initiate();
        System.out.println("""
            Please select an option:
            1. Insert Word
            2. Delete Word
            3. Batch Insert
            4. Batch Delete
            5. Search
            6. Size
            0. Exit""");
        programLoop();
    }

    public void initiate(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("""
                    Choose Perfect Hash Table Type:
                    1. O(N^2) space
                    2. O(N) space
                    > """);
            int option = sc.nextInt(), maxStrLen;
            if (option == 1)
                this.tableType = 1;
            else if (option == 2)
                this.tableType = 2;
            else
                throw new RuntimeException();
            System.out.println("""
                    Enter the maximum length of words to be hashed:
                    > """);
            maxStrLen = sc.nextInt();
            if (maxStrLen < 1) throw new RuntimeException();
            System.out.println("""
                    Start with an empty table or read words from a file?
                    1. Empty table
                    2. Read from file
                    > """);
            option = sc.nextInt();

            switch (option) {
                case 1 ->
                    ht = (tableType == 1) ?
                            new PerfectHashTable1(0, maxStrLen) : new PerfectHashTable2(0, maxStrLen);

                case 2 -> {
                    String filePath;
                    System.out.println("""
                        Enter the name of the file containing words:
                        > """);
                    filePath = sc.nextLine();
                    ArrayList<String> entries = FileManager.readFile(filePath);
                    ht = (tableType == 1) ?
                            new PerfectHashTable1(entries, maxStrLen) : new PerfectHashTable2(entries, maxStrLen);
                }

                default -> throw new RuntimeException();
            }
        }catch(Exception e){
            System.out.println("Invalid input! try again");
            initiate();
        }
    }

    public void programLoop() {
        int option = -1;
        Scanner sc = new Scanner(System.in);
        while(option != 0) {
            System.out.print("> ");
            option = sc.nextInt();
            if(setCommand(option) == -1) continue;
            command = invoker.invoke(eCommand);
            long time = command.execute(ht);
            System.out.println("Time in microseconds: " + time);
        }
    }

    public int setCommand(int option) {
        if (option == 0) System.exit(0);
        if (option > 6 || option < 0){
            System.out.println("Invalid input!!");
            return -1;
        }
        eCommand = Commands.values()[option-1];
        return 0;
    }

    public void printIntro() {
        System.out.println("\n\nNo fancy intro this time, estantego enta ya bashmohandes\n\n");
    }
}