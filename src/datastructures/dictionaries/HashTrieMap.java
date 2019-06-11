package datastructures.dictionaries;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import datastructures.dictionaries.ChainingHashTable;
import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import datastructures.worklists.ArrayStack;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
@SuppressWarnings("unchecked")

public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {

	public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        @SuppressWarnings("rawtypes")
        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(() -> new MoveToFrontList());
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            return new HTMIterator();
        }
        
        private class HTMIterator extends SimpleIterator<Entry<A, HashTrieNode>> {
            private Iterator<Item<A, HashTrieNode>> current;
            
            public HTMIterator() {
                current = pointers.iterator();

            }
            
            @Override
            public Entry<A, HashTrieNode> next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item<A, HashTrieNode> item = current.next();
                Entry<A, HashTrieNode> entry = convert(item);
                return entry;
            }

            @Override
            public boolean hasNext() {
                if(root == null ) {
                    return false;
                } else {
                    return current.next() != null;
                }
            }
            
            private Entry<A, HashTrieNode> convert(Item<A, HashTrieNode> item) {
                return new AbstractMap.SimpleEntry<>(item.key, item.value);
            }
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
    	 
    	if(key == null || value == null ) {
    		throw new IllegalArgumentException("key or value is null");
    	}
    	Iterator<A> itr = key.iterator();// iterator for key
		HashTrieNode temp = (HashTrieNode) root;// temp variable for root
		A tempKey = null;
		V previous = null;
    	while(itr.hasNext()) {// while key has more letters    		
    		tempKey = itr.next();// get the next letter
    		// if temp pointers for the next value is null and there are more letters
	    	if((temp.pointers).find(tempKey) == null) {// add a new node here with no value
	    		temp.pointers.insert(tempKey, new HashTrieNode());
	   	 	} // temp gets the value associated
	   		temp = temp.pointers.find(tempKey);	
    	}
    	if(temp.value == null) {
    		temp.value = value;
   			size++;
		} else {
			previous = temp.value;
			temp.value = value;
		}		
		return previous;
    }


    @Override
    public V find(K key) {
    	if(key == null) {
    		throw new IllegalArgumentException("key does not exist");
    	}
    	
    	HashTrieNode temp = iterator(key);
		if(temp == null) {
			return null;
		} else {
			return temp.value;
		}
    }

    @Override
    public boolean findPrefix(K key) {
    	if(key == null) {
    		throw new IllegalArgumentException("key is null");
    	}
    	HashTrieNode temp = iterator(key);
    	
		return temp != null;
    }
    
    private HashTrieNode iterator(K key) {
    	Iterator<A> itr = key.iterator();
		HashTrieNode temp = (HashTrieNode) root;
		while(itr.hasNext() && temp != null){
			A tempKey = itr.next();
			temp = temp.pointers.find(tempKey);
		}
		return temp;
    }
    

    @Override
    public void delete(K key) {
    	// if the key is null or the key does not exist throw exception
        if(key == null) {
        	throw new IllegalArgumentException("Enter a valid key");
        }
        
        
        if(find(key) != null) {
	        // iterator for key
	        Iterator<A> it = key.iterator();
	        // stack
	        ArrayStack<HashTrieNode> stack = new ArrayStack<HashTrieNode>();
	        ArrayStack<A> stackKey = new ArrayStack<A>();
	        // temp root 
	        HashTrieNode temp = (HashTrieNode) root;
	        // tempKey for each letter in key               
	        A tempKey = null;
	        // while there are more letters
	        stack.add(temp);//adds root node
	        while(it.hasNext()) {       
	        	// get the next letter in the key
	        	tempKey = it.next();
	        	temp = temp.pointers.find(tempKey);
	        	stack.add(temp);      	  
	        	stackKey.add(tempKey);
	        	// move temp to the next node
	        }
	        // we are now at the node holding 
	        temp.value = null;
		    temp = stack.next();
		    while(temp.pointers.size() == 0 && stack.size() > 0 && stackKey.size() > 0 && temp.value == null) {
		    	temp = stack.next();
		    	temp.pointers.delete(stackKey.next());  
		    }
	        size--;
        }    
    }



    @Override
    public void clear() {
    	size = 0;
    	root = new HashTrieNode();
    }
}
