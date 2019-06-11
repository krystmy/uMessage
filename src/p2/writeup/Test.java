package p2.writeup;



import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;

public class Test {
    public static void main(String[] args) {
       // AVLTree<Integer, Integer> tree = new AVLTree<>();
       
        AVLTree<Integer, Integer> tree = new AVLTree<>();
        tree.insert(14, 2);
        tree.insert(31, 1);
        tree.insert(13, 2);
        
        /*int n = 46;
        long startTime = System.currentTimeMillis();
        // Add them
        for (int i = 0; i < 5 * n; i++) {
            /*if(i >= 44 * 5) {
                System.err.println(i);
            }*/
         /*   int k = (i % n) * 37 % n;
            String str = String.format("%05d", k);
            for (int j = 0; j < k + 1; j ++)
                incCount(tree, str);
        }
        long endTime = System.currentTimeMillis();
        System.err.println(endTime - startTime);
        // Delete them all
        boolean passed = true;
        int totalCount = 0;
        for (Item<String, Integer> dc : tree) {
            passed &= (Integer.parseInt(dc.key) + 1) * 5 == dc.value;
            totalCount += dc.value;
        }*/
        
        
        // Check for accuracy
     /*   passed &= totalCount == (n * (n + 1)) / 2 * 5;
        passed &= tree.size() == n;
        passed &= tree.find("00851") == 4260;
        */
        
        
       

       /* tree.insert(10, 9);
        tree.insert(14, 2);
        tree.insert(31, 1);
        tree.insert(13, 2);
        tree.insert(12, 1);
        tree.insert(11, 1);
        tree.insert(9, 1);
        tree.insert(8, 1);
        tree.insert(7, 1);
        tree.insert(6, 1);
        tree.insert(5, 1);
        tree.insert(4, 1);
        tree.insert(3, 1);
        tree.insert(2, 1);
        tree.insert(1, 1);
        tree.insert(0, 1);*/
        
        
        
        
    }
    
    
    private static <E extends Comparable<E>> void incCount(Dictionary<E, Integer> tree, E key) {
        Integer value = tree.find(key);
        if (value == null) {
            tree.insert(key, 1);
        } else {
            tree.insert(key, value + 1);
        }
    }
    
    

}
