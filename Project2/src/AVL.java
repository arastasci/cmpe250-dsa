import javax.management.InstanceAlreadyExistsException;
import java.io.FileWriter;
import java.io.IOException;

public class AVL extends BST {

    AVL(String fileName) throws IOException {
        super();
        logger = new FileWriter(fileName + "_avl.txt");
    }

    int height(Node node)
    {
        if (node == null)
            return 0;
        return node.height;
    }

    public void reveal(){
        reveal(root, 0 );
        System.out.println("-------------");
    }
    public void reveal(Node root, int depth) {
        if(root == null)return;
        reveal(root.right, depth + 1);
        for(int i = 0; i < depth; i++)  System.out.print("   ");
        System.out.println(root.IP);
        System.out.println();
        reveal(root.left, depth + 1);

    }

    public void add(String IP) throws InstanceAlreadyExistsException {
        root = add(root, IP);
    }
    private Node add(Node node, String IP) throws InstanceAlreadyExistsException {
        if (node == null)
            return new Node(IP);
        logAddition(node, IP);
        if (node.compareTo(IP) > 0)
            node.left = add(node.left, IP);
        else if (node.compareTo(IP) < 0)
            node.right = add(node.right, IP);
        else
            throw new InstanceAlreadyExistsException();
;
        node.height = Math.max(height(node.left),height(node.right)) + 1;
        int balance = getBalance(node);

        // there are 4 cases Left Left Case
        if (balance > 1 && node.left.compareTo(IP) > 0)
        {
            logRotation("right");
            return rightRotate(node);
        }
        if (balance < -1 && node.right.compareTo(IP) < 0 ){
            logRotation("left");
            return leftRotate(node);
        }
        if (balance > 1 && node.left.compareTo(IP) < 0)
        {
            logRotation("left-right");
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && node.right.compareTo(IP) > 0)
        {
            logRotation("right-left");
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;

    }
    public void delete(String IP){
        root = delete(root, IP, false);
    }
    Node delete(Node node, String IP, boolean isSuccessor)
    {
        if (node == null)
            return null;

        if ( node.compareTo(IP) > 0)
            node.left = delete(node.left, IP, isSuccessor);
        else if (node.compareTo(IP) < 0)
            node.right = delete(node.right, IP, isSuccessor);
        else {
            // has one or no child
            if(node.left == null || node.right == null){
                if(node.left == null && node.right == null){
                    if(!isSuccessor)
                        logDeletion(findParent(node), node.IP, DeletionEnum.leaf);
                    return null;
                }
                if(!isSuccessor)
                    logDeletion(findParent(node), node.IP, DeletionEnum.singleChild);
                if(node.left == null) return node.right;
                return node.left;
            }
            String inorderSuccessor = findRightMin(node);
            // has two children and must be replaced by the inorder successor (minimum of the right subtree)
            if(!isSuccessor)
                logDeletion(findParent(node), node.IP, DeletionEnum.nonLeaf, inorderSuccessor);
            node.IP = inorderSuccessor;
            node.right = delete(node.right, node.IP, true); // deletes the inorder successor
        }
        node.height = Math.max(height(node.left),height(node.right)) + 1;
        int balance = getBalance(node);
        if (balance > 1 && getBalance(node.left) >= 0){
            logRotation("right");
            return rightRotate(node);
        }

        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0)
        {
            logRotation("left-right");
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0){
            logRotation("left");
            return leftRotate(node);
        }

        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0)
        {
            logRotation("right-left");
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
    void logRotation(String rotation){
        logToFile("Rebalancing: " + rotation + " rotation");
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    int getBalance(Node node){
        if(node == null) return 0;
        return height(node.left) - height((node.right));
    }
}
