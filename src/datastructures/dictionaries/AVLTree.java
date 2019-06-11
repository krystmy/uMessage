package datastructures.dictionaries;


import cse332.datastructures.trees.BinarySearchTree;
import datastructures.worklists.ArrayStack;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

@SuppressWarnings("unchecked")
public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V>  {

    /**
     * Create an empty AVL tree.
     */
    public AVLTree() {
        super();
        this.root = null;
    }

    public class AVLNode extends BSTNode {
        int height;

        public AVLNode(K key, V value) {
            super(key, value);
            height = 0;
        }
        
        public AVLNode() {
            super(null,null);
            height = 0;
        }
    }
    

    public int height(AVLNode t) {
        return t == null ? -1 : t.height;
    }
    
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        ArrayStack<AVLNode> stack = new ArrayStack<AVLNode>();
        AVLNode prev = null;
        int direction = 0;
        V returnVal;
        int child = -1;
        if(root == null) { // if tree is empty, insert at root
            root = new AVLNode(key, value);
            size++;
        }
        AVLNode current = (AVLNode) root;
        while (current != null) {// while there are more nodes       
            stack.add(current);// add current to stack
            // compare key with current's key, returns -1, 0, or 1
            direction = Integer.signum(key.compareTo(current.key));  
            if (direction == 0) {// We found the key!
                break;// exit loop
            }
            else {// else go to the right for 1 or left for 0
                child = Integer.signum(direction + 1);
                prev = current;
                current = (AVLNode) current.children[child];// current moves to child
            }
        }       
        // either direction == 0 or current == null
        // if direction == 0, update value
        if(current != null) {//key exists, replaces old value
            V prevV = current.value;
            current.value = value;
            returnVal = prevV;
            // else, reached the end of the tree, make new node
        } else {//key doesn't exist, insert it
            size++;
            current = new AVLNode(key, value);  
            if(prev != null) {
                prev.children[child] = current;
            }
            returnVal = value;
           
        }       
        // move current to stack to begin working back up to root checking height
        
        //Checks if we need to update the height as we go up the tree or not
        boolean incHeight = false;
        current = stack.next();       
        if(prev != null && (prev.children[0] == null || prev.children[1] == null)) {
            incHeight = true;
            current.height = Math.max(height((AVLNode) current.children[0]), height((AVLNode) current.children[1])) + 1;
        }
        if(incHeight) {
            while(Math.abs(height((AVLNode) current.children[0]) - height((AVLNode) current.children[1])) < 2 && stack.hasWork()) {
                current = stack.next();
                current.height = Math.max(height((AVLNode) current.children[0]), height((AVLNode) current.children[1])) + 1;
            }
            if(stack.hasWork()) {
                prev = stack.next();
            } else {
                prev = null;
            }
            // stack is either empty (no imbalance) or there is a height imbalance
            int children = 0;
            if(prev!= null && prev.children[1] == current) {
                children = 1;
            }
            
            if(height((AVLNode) current.children[0]) - height((AVLNode) current.children[1]) > 1) {
                if(height((AVLNode) current.children[0].children[0]) >= (height((AVLNode) current.children[0].children[1]))) {
                    current = rotateR(current);
                } else {
                    current = doubleRight(current);
                }         
            } else {
                if(height((AVLNode) current.children[1]) - height((AVLNode) current.children[0]) > 1) {
                    if(height((AVLNode) current.children[1].children[1]) >= height((AVLNode) current.children[1].children[0])) {
                        current = rotateL(current);
                    } else {
                        current = doubleLeft(current);
                    }
                }
            }
            if(prev == null) {
                root = current;
            } else {
                prev.children[children] = current;
            }
        }
        return returnVal;      
    }

    private AVLNode rotateR(AVLNode node) {       
        AVLNode temp = (AVLNode) node.children[0];// left child of node 
        node.children[0] = temp.children[1];// sets node's left child to be temp's right child
        temp.children[1] = node;// set temp's right child to be node
        // set height of temp and node
        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        temp.height = Math.max(height((AVLNode) temp.children[0]), node.height) + 1;
        return temp;
    }

    private AVLNode doubleLeft(AVLNode node) {
        node.children[1] = rotateR((AVLNode) node.children[1]);
        return rotateL(node);
    }

    private AVLNode rotateL(AVLNode node) {  
        AVLNode temp = (AVLNode) node.children[1];// right child of node
        node.children[1] = (AVLNode) temp.children[0];// sets node's right child to be temp's left child
        temp.children[0] = node;// set temp's left child to be node
        // set height of temp and node
        node.height = Math.max(height((AVLNode) node.children[0]), height((AVLNode) node.children[1])) + 1;
        temp.height = Math.max(node.height, height((AVLNode) temp.children[1])) + 1;
        return temp;
    }

    private AVLNode doubleRight(AVLNode node) {
        node.children[0] = rotateL((AVLNode) node.children[0]);
        return rotateR(node);
    }


}