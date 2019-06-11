package p2.sorts;

import java.util.Comparator;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<E>(comparator);
        // if k is larger than array
        if(k >= array.length) {
            k = array.length;
        }
        // add first k elements to heap
        for(int i = 0; i < k; i++) {
            heap.add(array[i]);
        }
        // if any of remaining array elements are > than heap's min
        for(int j = k; j < array.length; j++) {
            if(comparator.compare(array[j], heap.peek()) > 0) {
                // delete min
                heap.next();
                // add value from array
                heap.add(array[j]);
            }
        }
        
        // add all items from heap back to array
        for(int m = 0; m < k; m++) {
            array[m] = heap.next();
        }
        // remaining elements in array = null
        for(int n = k; n < array.length; n++) {
            array[n] = null;
        }
    }
}