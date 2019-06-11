package datastructures.worklists;

import java.util.NoSuchElementException;


import cse332.interfaces.worklists.LIFOWorkList;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
@SuppressWarnings("unchecked")
public class ArrayStack<E> extends LIFOWorkList<E> {
	private int cap;//default size of 10
	private int size;
	private E[] array;
	
    public ArrayStack() {
    	cap = 10;
    	size = 0;
    	array = (E[])new Object[cap];
    }

    @Override
    public void add(E work) {
    	//if array is full, double size and copy everything over to the new array
    	if(size == cap) 	{
    		cap = cap * 2;
    		E[] newArray = (E[])new Object[cap];
    		for(int i = 0; i < size; i++) {
    			newArray[i] = array[i];
    		}
    		array = newArray;
    	}
    	array[size] = work;
    	size++;
    }

    @Override
    public E peek() {
    	
    	if(!hasWork()) {//returns the next work (last item in array)
    		throw new NoSuchElementException();
    	} 
    	return array[size - 1]; 	
    }

    @Override
    public E next() {
    	// if the worklist is empty, throw exception
    	if(!hasWork()) {
    		throw new NoSuchElementException("The worklist is empty");
    	// return the next element and remove it from the list
    	}
    	E nextWork = array[size-1];
    	array[size-1] = null;
    	size--;
    	return nextWork;
    }


    @Override
    public int size() {
    	return size;
    }

    //create a new array with default settings
    @Override
    public void clear() {
    	cap = 10;
    	size = 0;
    	array = (E[])new Object[cap];
    }
}
