package Dictionary;

import Service.CLICommand;
import Service.CommandInvoker;
import Service.Commands;

import java.util.Scanner;

public class Dictionary implements IDictionary {

    CommandInvoker invoker = new CommandInvoker();

    CLICommand command;

    Commands eCommand;

    public void startProgram() {
        Scanner sc = new Scanner(System.in);
        printIntro();
        System.out.println("Choose Dictionary tree:\n1. AVL tree\n2. RB tree");
        System.out.print("> ");
        int option = sc.nextInt();
        initiate(option);
        System.out.println("Please select an option:");
        System.out.println("1. Insert Word");
        System.out.println("2. Delete Word");
        System.out.println("3. Batch Insert");
        System.out.println("4. Batch Delete");
        System.out.println("5. Search");
        System.out.println("6. Size");
        System.out.println("0. Exit");
        programLoop();
    }

    public void initiate(int option) {
//        if(option == 1) {
//            this.avl = new AVL<>();
//            this.treeType = TreeType.AVL;
//        } else if(option == 2) {
//            this.rb = new RB<>();
//            this.treeType = TreeType.RB;
//        }
    }

    public void programLoop() {
        int option = -1;
        Scanner sc = new Scanner(System.in);
        while(option != 0) {
            System.out.print("> ");
            option = sc.nextInt();
            if(setCommand(option) == -1) continue;
            command = invoker.invoke(eCommand);
//            long time = 0;
//            switch (treeType) {
//                case BST -> time = command.execute(bst);
//                case AVL -> time = command.execute(avl);
//                case RB -> time = command.execute(rb);
//                default -> System.out.println("Tree type not supported!");
//            }
//            switch (treeType) {
//                case BST -> bst.visit(VisitType.DFS);
//                case AVL -> avl.visit(VisitType.DFS);
//                case RB -> rb.visit(VisitType.DFS);
//            }
//            System.out.println("Time in microseconds: " + time);
        }
    }

    public int setCommand(int option) {
        if (option == 0) System.exit(0);
        if (option > 7 || option < 0){
            System.out.println("Invalid input!!");
            return -1;
        }
        eCommand = Commands.values()[option-1];
        return 0;
    }

    public void printIntro() {

    }
}