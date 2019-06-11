package p2.writeup;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;

public class CompareAVLandBST {
    
    final static int NUM_TESTS = 10;
    final static int NUM_WARMUP = 3;
    
    public static void main(String[] args) {
        insertBST();
        findBST();
        insertAVL();
        findAVL();
            
    }
    
    public static void insertAVL() {
        // create new AVL tree
        AVLTree<Integer,Integer> avlTree = new AVLTree<Integer,Integer>();
        // add items to tree
        double totalTime = 0;

        for(int i = 0; i < NUM_TESTS; i++) {
            long startTime = System.currentTimeMillis();
            for(int j = 0; j < 100000; j++) {
                avlTree.insert(j, j);
            }        
            long endTime = System.currentTimeMillis();
            if(NUM_WARMUP <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
        System.out.println(averageRuntime);         
    }
    
    
    
    public static void insertBST() {
        // create new AVL tree
        BinarySearchTree<Integer,Integer> bstTree = new  BinarySearchTree<Integer,Integer>();
        // add items to tree
        double totalTime = 0;

        for(int i = 0; i < NUM_TESTS; i++) {
            long startTime = System.currentTimeMillis();
            for(int j = 0; j < 100000; j++) {
                bstTree.insert(j, j);
            }        
            long endTime = System.currentTimeMillis();
            if(NUM_WARMUP <= i) {
               // System.out.println(endTime - startTime);
                totalTime += (endTime - startTime);
            }
        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
        System.out.println(averageRuntime);         
    }
    
    public static void findBST() {
        // create new AVL tree
        BinarySearchTree<Integer,Integer> bstTree = new  BinarySearchTree<Integer,Integer>();
        // add items to tree
        double totalTime = 0;
        for(int j = 0; j < 1000; j++) {
            bstTree.insert(j, j);
        }   
        for(int i = 0; i < NUM_TESTS; i++) {
            long startTime = System.currentTimeMillis();
            for(int j = 0; j < 1000; j++) {
                bstTree.find(j);
            }        
            long endTime = System.currentTimeMillis();
            if(NUM_WARMUP <= i) {
               // System.out.println(endTime - startTime);
                totalTime += (endTime - startTime);
            }
        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
        System.out.println(averageRuntime);         
    }
    
    public static void findAVL() {
        // create new AVL tree
        AVLTree<Integer,Integer> avlTree = new AVLTree<Integer,Integer>();
        // add items to tree
        double totalTime = 0;
        for(int j = 0; j < 1000; j++) {
            avlTree.insert(j, j);
        }  
        for(int i = 0; i < NUM_TESTS; i++) {
            long startTime = System.currentTimeMillis();
            for(int j = 0; j < 1000; j++) {
                avlTree.find(j);
            }        
            long endTime = System.currentTimeMillis();
            if(NUM_WARMUP <= i) {
               // System.out.println(endTime - startTime);
                totalTime += (endTime - startTime);
            }
        }
        double averageRuntime = totalTime / (NUM_TESTS - NUM_WARMUP);
        System.out.println(averageRuntime);         
    }
}
