package datastructures.dictionaries;

import java.util.Iterator;

import cse332.datastructures.containers.*;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list.
 */
@SuppressWarnings("rawtypes")
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private Node list;

    public MoveToFrontList() {
        list = new Node();
    }
    
    
    @Override
    //Me
    @SuppressWarnings("unchecked")
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException("enter a valid key");
        }
        if(size == 0) {//if list is empty, add new item in node
           list.item = new Item(key, value);
           size++;
           return null;
        } else if(find(key) == null) {//if key doesn't exist, create new node with value
                Node node = new Node();
                node.item = new Item(key, value);
                node.next = list;
                list = node;
                size++;
                return null;
        } else { //if key exist, replace with new value, found node should already be at front
            V oldValue = list.item.value;
            list.item.value = value;
            return oldValue;
        }
    }
    
    @Override
    //Me
    public V find(K key) {
        Node temp = list;
        // if key is null throw exception
        if(key == null) {
            throw new IllegalArgumentException("enter a valid key");
        }
            // list is empty
            if(size == 0) {
                return null;
            }
            // if list has size 1
            if(size == 1) {
                if(list.item.key.equals(key)) {//if first node contains key
                    return list.item.value;
                }
                return null;
            }
            // list must be greater than size 1   
            if(size == 2) {
                if(temp.item.key.equals(key)) {
                    return temp.item.value;
                }
                if(temp.next.item.key.equals(key)) {
                    Node tempHead = list;
                    list = temp.next;
                    list.next = tempHead;
                    temp.next = null;
                    return list.item.value;
                }
                return null;
            }
                if(temp.item.key.equals(key)) {// if the first item is the key
                    return temp.item.value;
                } else {//if first node doesnt contain key
                    while(temp.next != null) {      
                        // if the next node contains the key
                        if(temp.next.item.key.equals(key)) {
                          //moves found key to the front of list
                            Node previous = temp;
                            temp = temp.next;
                            if(temp.next != null) {
                                previous.next = temp.next;
                            } else {
                                previous.next = null;
                            }
                            temp.next = list;
                            list = temp;
                            return list.item.value;
                        } else {// next node doesnt contain key
                            temp = temp.next;
                        }
                    }
                }
           return null;

    }

    // shariya
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFIterator();
    }

    private class MTFIterator extends SimpleIterator<Item<K, V>> {
        private Node current;

       public MTFIterator() {
           // pointer to head of MTF list
           current = list;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item<K, V> next() {
            Item<K, V> value = new Item<K,V>(null,null);
            if(current != null) {
                value = current.item;
                this.current = this.current.next;
            }
            return value;
        }
    }

    public class Node {
        Item<K,V> item;
        Node next;
        
        public Node(Item<K,V> item) {
            this.item = item;
       }
        public Node() {
            item = null;
            next = null;
        }
    }
    
    

}
