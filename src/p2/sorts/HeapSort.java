package p2.sorts;

import java.util.Comparator;
import datastructures.worklists.MinFourHeap;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    @SuppressWarnings("unchecked")
    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap(comparator);
        // insert each element into a MinFourHeap
        for(int i = 0; i < array.length; i++) {
            heap.add( array[i]);
        }
        // remove each element from heap and store in original array
        for(int j = 0; j < array.length; j++) {
            array[j] = heap.next();
        }
    }
}