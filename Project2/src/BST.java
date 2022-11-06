import javax.management.InstanceAlreadyExistsException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;


public class BST {
    protected static class Node{
        String IP;
        Node left;
        Node right;
        int height;
        Node(String IP){
            this(IP, null, null);
        }
        Node(String IP, Node left, Node right){
            this.IP = IP;
            this.left = left;
            this.right = right;
            height = 1;
        }


        public int compareTo(Node node){
            return this.IP.compareTo(node.IP);
        }
        public int compareTo(String IP){
            return this.IP.compareTo(IP);
        }
        public void empty(){
            left = null;
            right = null;
        }

    }

    protected Node root;

    protected FileWriter logger;
    public FileWriter getLogger(){
        return logger;
    }
    BST(){

    }
    BST(String fileName) throws IOException {
        logger = new FileWriter(fileName + "_BST.txt");
    }

    public boolean find(String IP) {
        Node cur = root;
        while(cur != null){
            if( cur.compareTo(IP) < 0){
                cur = cur.left;
            }
            else if(cur.compareTo(IP) > 0){
                cur = cur.right;
            }
            else return true;
        }
        return false;
    }



    public void add(String IP) throws InstanceAlreadyExistsException {
        Node node = new Node(IP);
        Node cur = root;
        if(root == null){
            root = node;
            return;
        }
        while(true){
            logAddition(cur, IP);
            if( cur.compareTo(node) > 0){
                if(cur.left != null)
                cur = cur.left;
                else {
                    cur.left = node;
                    return;
                }
            }
            else if(cur.compareTo(node) < 0){
                if(cur.right != null)
                cur = cur.right;
                else {
                    cur.right = node;
                    return;
                }
            }
            else throw new InstanceAlreadyExistsException();
            // to see if we already have an element there, which shouldn't be the case.
        }

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
            if(!isSuccessor)
            logDeletion(findParent(node), node.IP, DeletionEnum.nonLeaf, inorderSuccessor);
            // has two children and must be replaced by the inorder successor (minimum of the right subtree)
            node.IP = inorderSuccessor;
            node.right = delete(node.right, node.IP, true); // deletes the inorder successor
        }


        return node;
    }
    protected Node findParent(Node node) throws NoSuchElementException {
        return findParent(node.IP);
    }
    protected Node findParent(String IP) throws NoSuchElementException {
        Node cur = root;
        while(cur != null){
            if(cur.left != null){
                if(Objects.equals(cur.left.IP, IP)) return cur;
            }
            if(cur.right != null){
                if(Objects.equals(cur.right.IP, IP)) return cur;
            }
            if(cur.compareTo(IP) < 0) cur = cur.right;
            else cur = cur.left;
        }
        throw new NoSuchElementException();
    }
    /**
     * finds the minimum of the right subtree
     */
    protected String findRightMin(Node node){
        Node cur = node.right;
        while(cur.left != null){
            cur = cur.left;
        }
        return cur.IP;
    }
    public void sendMessage(String senderIP, String receiverIP){
        // find LCA and find path
        Node cur = this.root;
        while(cur != null){
            if(cur.compareTo(senderIP) > 0 && cur.compareTo(receiverIP) > 0){
                cur = cur.right;
            }
            else if(cur.compareTo(senderIP) < 0 && cur.compareTo(receiverIP) < 0){
                cur = cur.left;
            }
            else break;
        }
        Node LCA = cur;
        logToFile(senderIP +": Sending message to: " + receiverIP);
        transmitMessageSTLCA(LCA, senderIP, receiverIP);
        transmitMessageLCATR(LCA, senderIP, receiverIP);
        logToFile(receiverIP + ": Received message from: " + senderIP);
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

    protected void transmitMessageLCATR(Node LCA, String senderIP, String receiverIP){
        Node cur = LCA;
        Node prev = LCA;

        if(cur.compareTo(receiverIP) < 0){
            cur = cur.right;
        }
        else if(cur.compareTo(receiverIP) > 0){
            cur = cur.left;
        }

        while(cur != null){
            if(cur.compareTo(receiverIP) == 0) break;
            logTransmission(cur, prev, senderIP, receiverIP);
            prev = cur;
            if(cur.compareTo(receiverIP) < 0){
                cur = cur.right;
            }
            else if(cur.compareTo(receiverIP) > 0){
                cur = cur.left;
            }


        }
    }
    protected void transmitMessageSTLCA(Node LCA, String senderIP, String receiverIP){
        Stack<Node> stack = new Stack<>();
        Node cur = LCA;
        // LCA is included
        while(cur != null){
            stack.push(cur);
            if(cur.compareTo(senderIP) > 0){
                cur = cur.left;
            }
            else if(cur.compareTo(senderIP) < 0){
                cur = cur.right;
            }
            else break;
        }
        if(stack.empty()) return;
        Node prev = stack.pop();
        while(!stack.empty()){
            Node top = stack.pop();
            logTransmission(top, prev, senderIP, receiverIP);
            prev = top;
        }
    }
    void logToFile(String line){
        try{
            logger.write(line + "\n");
        }
        catch (IOException e){
            System.out.println("err writing to file");
        }
    }
    public void logTransmission(Node curNode, Node prevNode, String senderIP, String receiverIP){
        logToFile(curNode.IP + ": Transmission from: " + prevNode.IP + " receiver: " + receiverIP + " sender:" + senderIP);
    }
    public void logDeletion(Node parent, String deletedIP, DeletionEnum deletionEnum){
        logDeletion(parent, deletedIP, deletionEnum, "");
    }
    public void logDeletion(Node parent, String deletedIP, DeletionEnum deletionEnum, String replacedIP){

        switch (deletionEnum){
            case leaf:
                logToFile(parent.IP + ": Leaf Node Deleted: " + deletedIP);
                break;
            case singleChild:
                logToFile(parent.IP + ": Node with single child Deleted: " + deletedIP);
                break;
            case nonLeaf:
                logToFile(parent.IP + ": Non Leaf Node Deleted; removed: " + deletedIP + " replaced: " + replacedIP);
                break;
        }
    }
    public void logAddition(Node parent, String addedIP){
        logToFile(parent.IP + ": New node being added with IP:" + addedIP);
    }

}

enum DeletionEnum{
    leaf,
    singleChild,
    nonLeaf
}