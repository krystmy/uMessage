package datastructures.worklists;


import java.util.Iterator;
import java.util.NoSuchElementException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
@SuppressWarnings("unchecked")
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {

    private int size;
    private E[] array;
    private int front;
    private int end;



	
	public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        size = 0;
    	array = (E[])new Comparable[capacity];
    	front = 0;
    	end = 0;

    }

    @Override
    public void add(E work) {
        if(isFull()) {
            throw new IllegalStateException("work is full");
            
        }
        array[end] = work;
        if(end + 1 == array.length) {
            end = 0;
        } else {
            end++;
        }
        size++;
    }

    @Override
    public E peek() {
    	if(!hasWork()) {//returns the next work, 1st item in array
        	throw new NoSuchElementException(); 
    	} 
    	return array[front];
    }
    
    @Override
    public E peek(int i) {
        if(hasWork()) {
            if(0 <= i && i < size) {
                if(front + i >= array.length) {
                    return array[front + i - array.length];
                }
                return array[front + i];
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NoSuchElementException("The worklist is empty");
    }


    @Override
    public E next() {
        // if there is no work, throw an exception
        if(!hasWork()) {
            throw new NoSuchElementException("The worklist is empty");
        }
        E work = array[front];
        if(front + 1 == array.length) {
            front = 0;
        } else {
            front++;
        }
        size--;
        return work;
    }


    @Override
    public void update(int i, E value) {
        // if the worklist is empty, throw exception
        if(!hasWork()) {
            throw new NoSuchElementException("The worklist is empty");
        }
        // if i is less than 0 or greater than size of worklist, throw exception
        if(i < 0 || i >= array.length) {
            throw new IndexOutOfBoundsException("This index does not exist");
        }
        // update value
        array[i] = value;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    //Me
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        Iterator<E> otherItr = other.iterator();
        Iterator<E> thisItr = this.iterator();
        int result = 0;
        while(thisItr.hasNext() && otherItr.hasNext()) {
            E otherE =  thisItr.next();
            E thisE = otherItr.next();
            if(otherE.compareTo(thisE) != 0) {
                result = otherE.compareTo(thisE);
                break;
            }
        }
        if(result == 0) {
            if(otherItr.hasNext()) {
                result = -1;
            } else if (thisItr.hasNext()){
                result = 1;
            }
        }

        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof CircularArrayFIFOQueue)) {
            return false;
        }

        // this and obj are the same class but not the same object
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            Iterator<E> otherItr = other.iterator();
            Iterator<E> thisItr = this.iterator();
            // while both objects have work
            while(thisItr.hasNext() && otherItr.hasNext()) {
                if(!thisItr.next().equals(otherItr.next())) {
                    return false;
                }
            }
            if(thisItr.hasNext() || otherItr.hasNext()) {
                return false;
            }
            return true;
        }
    }


    @Override
    public int hashCode() {
        int result = 0;
        int counter = 1;
        if(front < end) {            
            for(int i = front; i < end; i++) {
                result = (result + array[i].hashCode()) * 31 * counter;
                counter++;
            }
        }    
        else {
            for(int i = front; i < array.length; i++) {
                result = (result + array[i].hashCode()) * 31 * counter;
                counter++;
             }
            for(int i = 0; i < end; i++) {
                result = (result + array[i].hashCode()) * 31 * counter;
                counter++;
             }
        }
        return result;
    }
}
