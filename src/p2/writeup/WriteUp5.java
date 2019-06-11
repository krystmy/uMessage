package p2.writeup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;


public class WriteUp5 {
    public final static int START = 5;
    public final static int END = 10;
    public static void main(String[] args) throws FileNotFoundException {
        
        BST();
        AVLT();
        CHT();
        HTM();
    
    }
    
    private static AlphabeticString a(String s) {
        return new AlphabeticString(s);
    }
    
    public static void HTM() throws FileNotFoundException {
        double totalTime = 0; 
        for (int i = 1; i <= END; i++) {
            Scanner scan = new Scanner(new File("alice.txt"));
            HashTrieMap<Character, AlphabeticString, Integer> HTM = new HashTrieMap<>(AlphabeticString.class);
            long startTime = System.currentTimeMillis();
            while(scan.hasNext()) {
                AlphabeticString s = a(scan.next());
                if(HTM.find(s) != null) {
                    HTM.insert(s, HTM.find(s) + 1);
                } else {
                    HTM.insert(s, 1);
                }
                
            }
            long endTime = System.currentTimeMillis();
            if (START < i) { // Throw away first NUM_WARMUP runs to exclude JVM warmup 
                totalTime += (endTime - startTime);
            }
            scan.close();
        }
        
        double averageRuntime = totalTime / 5;   
        System.out.println("HTM avg time = " + averageRuntime);
        
    }
    
    
    public static void CHT() throws FileNotFoundException {
        double totalTime = 0; 
        for (int i = 1; i <= END; i++) {
            Scanner scan = new Scanner(new File("alice.txt"));
            ChainingHashTable<String, Integer> CHT = 
                    new ChainingHashTable<String, Integer>(() -> new MoveToFrontList<String, Integer>());
            long startTime = System.currentTimeMillis();
            while(scan.hasNext()) {
                String s = scan.next();
                if(CHT.find(s) != null) {
                    CHT.insert(s, CHT.find(s) + 1);
                } else {
                    CHT.insert(s, 1);
                }
                
            }
            long endTime = System.currentTimeMillis();
            if (START < i) { // Throw away first NUM_WARMUP runs to exclude JVM warmup 
                totalTime += (endTime - startTime);
            }
            scan.close();
        }
        
        double averageRuntime = totalTime / 5;   
        System.out.println("CHT avg time = " + averageRuntime);
        
    }
    
    public static void AVLT() throws FileNotFoundException {
        double totalTime = 0; 
        for (int i = 1; i <= END; i++) {
            Scanner scan = new Scanner(new File("alice.txt"));
            AVLTree<String, Integer> avt = new AVLTree<String, Integer>();
            long startTime = System.currentTimeMillis();
            while(scan.hasNext()) {
                String s = scan.next();
                if(avt.find(s) != null) {
                    avt.insert(s, avt.find(s) + 1);
                } else {
                    avt.insert(s, 1);
                }
                
            }
            long endTime = System.currentTimeMillis();
            if (START < i) { // Throw away first NUM_WARMUP runs to exclude JVM warmup 
                totalTime += (endTime - startTime);
            }
            scan.close();
        }
        
        double averageRuntime = totalTime / 5;   
        System.out.println("AVL avg time = " + averageRuntime);
        
    }
    
    
    public static void BST() throws FileNotFoundException {
        double totalTime = 0; 
        for (int i = 1; i <= END; i++) {
            Scanner scan = new Scanner(new File("alice.txt"));
            BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>();
            long startTime = System.currentTimeMillis();
            while(scan.hasNext()) {
                String s = scan.next();
                if(bst.find(s) != null) {
                    bst.insert(s, bst.find(s) + 1);
                } else {
                    bst.insert(s, 1);
                }
                
            }
            long endTime = System.currentTimeMillis();
            if (START < i) { // Throw away first NUM_WARMUP runs to exclude JVM warmup 
                totalTime += (endTime - startTime);
            }
            scan.close();
        }
        
        double averageRuntime = totalTime / 5;   
        System.out.println("BST avg time = " + averageRuntime);
        
    }
    
}
