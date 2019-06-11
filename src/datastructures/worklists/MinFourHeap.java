package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;


/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
@SuppressWarnings("unchecked")

public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int cap;
    private Comparator<E> comp;
    
    
	public MinFourHeap(Comparator<E> c) {
    	size = 0;
    	cap = 10;
    	data = (E[])new Object[cap];
    	comp = c;
    }

    @Override
    public boolean hasWork() {
    	return size > 0;
    }

    @Override
    public void add(E work) {
    	if(size ==  cap) {
    		cap = cap * 4;
    		E[] newData = (E[])new Object[cap];
    		for(int i = 0; i < size; i++) {
    			newData[i] = data[i];
    		}
			data = newData;
    	} 
    	int index = percolateUp(size, work);
    	data[index] = work;
    	size++;
    }
    
    private int percolateUp(int index, E work ) {
	    while(index > 0 && comp.compare(work, data[(index - 1)/4]) < 0  ) {
	    	data[index] = data[(index - 1)/4];
	    	index = (index - 1)/ 4;
    	}
    	return index;
    }
    
    @Override
    public E peek() {
        if(!hasWork()) {
    		throw new NoSuchElementException("The worklist is empty");
    	}
    	return data[0];
    }

    
    // my part
    /**
     * Returns and removes the next element of the worklist
     *
     * @precondition hasWork() is true
     * @postcondition return(next()) ++ after(next()) == before(next())
     * @postcondition after(size()) + 1 == before(size())
     * @throws NoSuchElementException
     *             if hasWork() is false
     * @return the next element in this worklist
     */
    @Override
    public E next() {
    	// if the worklist is empty, throw exception
        if(!hasWork()) {
        	throw new NoSuchElementException("The worklist is empty");
        }
        // get the min element
        E nextWork = data[0];
        // index will be the spot to replace
        int index = percolateDown(0, data[size-1]);
        // replace the last element in the correct hole position 
        data[index] = data[size-1];
        data[size-1] = null;
        size--;
        return nextWork;
    }
    
    private int percolateDown(int hole, E val) {
    	
    	int first, minChild;
    	
    	// while the number of nodes is <= size of heap
    	while(4*hole+1 < size) {
    		// left child gets 4 * hole + 1
    		first = 4*hole+1;
    		minChild = first;
    		// compare each sibling to find the minimum target
    		for(int i = 1; i < 4; i++) {
    			// if the minimum child is greater than the next sibling, 
    			if(first + i < size && comp.compare(data[minChild],data[first+i]) > 0) {
    				// update the minimum child to be the sibling
    				minChild = first + i;
    			}
    		}
    		// if val is greater than the minimum child, 
			if(comp.compare(val,data[minChild]) > 0) {
				// put the min child in the hole position
				data[hole] = data[minChild];
				// update hole position
				hole = minChild;
			} else {
				break;
			}
    	}
    	// if the leaf level is not full, check it
    	return hole;
    }
   
    @Override
    public int size() {
        return size;
    }
 
	@Override
    public void clear() {
        size = 0;
        cap = 10;
        data = (E[])new Object[cap];
    }
	
}
