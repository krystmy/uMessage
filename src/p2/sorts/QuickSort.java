package p2.sorts;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        sortHelper(array, 0, array.length - 1, comparator);
    }
    
    public static <E> void sortHelper(E[] array, int low, int high, Comparator<E> c) {
        if(low < high) {
            int idx = split(array, low, high, c);
            sortHelper(array, low, idx-1, c);
            sortHelper(array, idx+1, high, c);
        }
    }
    
    public static <E> int split(E[] array, int low, int high, Comparator<E> c) {
        E pivot = array[high];
        int i = low - 1;
        for(int j = low; j < high; j++) {           
            if(c.compare(array[j], pivot) > 0) {
                continue;
            } else {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            } 
        }
        E temp = array[i+1];
        array[i+1] = array[high];
        array[high] = temp;
        return i+1;
    }
}
