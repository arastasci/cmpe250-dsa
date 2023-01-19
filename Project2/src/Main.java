import javax.management.InstanceAlreadyExistsException;
import java.io.File;  // Import the File class

import java.util.Scanner; // Import the Scanner class to read text files


public class Main {
    public static void main(String[] args) {
        try {
            File input = new File(args[0]);
            String[] fileName = args[1].split("\\.");

            BST bst = new BST(fileName[0]);
            AVL avl = new AVL(fileName[0]);

            Scanner inputReader = new Scanner(input);
            String root = inputReader.nextLine();
            bst.add(root);
            avl.add(root);
            while (inputReader.hasNextLine()) {
                String data = inputReader.nextLine();
                processInput(bst, avl, data.split(" "));
            }
            inputReader.close();
            bst.getLogger().close();
            avl.getLogger().close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    static void processInput(BST bst, AVL avl, String[] commands){
        switch (commands[0]){
            case "ADDNODE":
                try{
                    bst.add(commands[1]);
                    avl.add(commands[1]);

                  //  avl.reveal();
                }catch (InstanceAlreadyExistsException e){
                    System.out.println("IP "+ commands[1] + " already in the tree." );
                }
                break;

            case "DELETE":
                bst.delete(commands[1]);
                avl.delete(commands[1]);

               // avl.reveal();
                break;

            case "SEND":
                bst.sendMessage(commands[1], commands[2]);
                avl.sendMessage(commands[1], commands[2]);

             //   avl.reveal();
                break;
        }
    }
}
