package datastructures.worklists;

import java.util.NoSuchElementException;


import cse332.interfaces.worklists.FIFOWorkList;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
	private Node front;
	private Node back;
    private int size;
	
    public ListFIFOQueue() {
    	front = new Node();
    	back = front;
    	size = 0;
    }

    @Override
    public void add(E work) {
    	if(size > 0) {
    		Node newWork = new Node();
   			newWork.data = work;
    		back.next = newWork;
    		back = newWork;
    	} else {
   			front.data = work;
   		}
   		size++;
    }

    @Override
    public E peek() {
    	if(hasWork()) {
        	return front.data;
    	}
    	throw new NoSuchElementException();
    }

    @Override
    public E next() {
    	if(!hasWork()) {
            throw new NoSuchElementException();
    	} 
    	if(size > 1) {
	    	Node curr = front;
			front = front.next;
			size--;
			curr.next = null;
			return curr.data;
    	} else {
    		E data = front.data;
    		front.data = null;
    		size--;
    		return data;
    	}
    }

    @Override
    public int size() {
    	return size;
    }

    @Override
    public void clear() {
    	front = new Node();
    	back = front;
    	size = 0;
    }
    
	public class Node {
    	E data;
    	Node next;
    }
}
