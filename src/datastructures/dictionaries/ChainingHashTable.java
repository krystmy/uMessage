package datastructures.dictionaries;
 
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
 
 
import cse332.datastructures.containers.*;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
 
 
/**
* TODO: Replace this comment with your own as appropriate.
* 1. You must implement a generic chaining hashtable. You may not
*    restrict the size of the input domain (i.e., it must accept
 *    any key) or the number of inputs (i.e., it must grow as necessary).
* 3. Your HashTable should rehash as appropriate (use load factor as
*    shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 *    than 200,000 elements. After more than 200,000 elements, it should
 *    continue to resize using some other mechanism.
* 6. We suggest you hard code some prime numbers. You can use this
*    list: http://primes.utm.edu/lists/small/100000.txt
 *    NOTE: Do NOT copy the whole list!
*/
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
 
    private final Supplier<Dictionary<K, V>> newChain;
    private final int[] primeNum;
    private Dictionary<K,V>[] array;
    private int counter;
 
    @SuppressWarnings("unchecked")
    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        counter = 0;
        array =  (Dictionary<K,V>[]) new Dictionary[31];
        primeNum = new int[]{61,127,251,503,1009,2003,4001,8009,16001,23003,64007,128021, 240883};
    }
 
    @SuppressWarnings("unchecked")
    @Override
    public V insert(K key, V value) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        double loadFactor = size * 1.0 / array.length;
        
        if( loadFactor > 1 ) {//if load factor is > 0.5, rehash
            Dictionary<K,V>[] newArray;
            if(counter < primeNum.length) {
                newArray =  (Dictionary<K,V>[]) new Dictionary[primeNum[counter]];
                counter++;
            } else {//when size is over 200,000
                newArray =  (Dictionary<K,V>[]) new Dictionary[array.length * 2 - 1];
            }
            for(int i = 0; i < array.length; i++) {//copy from old array to new
                if(array[i] != null) {
                    Iterator<Item<K, V>> itr = array[i].iterator();
                    while(itr.hasNext()) {
                        Item<K, V> temp = itr.next();
                        int index = ((temp.key.hashCode() % newArray.length) + newArray.length) % newArray.length;
                        if(newArray[index] == null) {
                            Dictionary<K,V> bucket = newChain.get();
                            bucket.insert(temp.key, temp.value);
                            newArray[index] = bucket;
                        } else {
                            newArray[index].insert(temp.key, temp.value);
                        }
                    }
                }
            }
            array = newArray;
        }
        int index = ((key.hashCode() % array.length) + array.length) % array.length;
       // if bucket has items
        if(array[index] != null) {
            V oldVal = array[index].find(key);
            array[index].insert(key, value);
            if(oldVal == null) {
                size++;
            }
            return oldVal;
            // bucket is empty
        } else {
            Dictionary<K,V> chain = newChain.get();
            chain.insert(key, value);
            array[index] = chain;
            size++;
 
            return null;
 
        }   
    }
 
    @Override
    public V find(K key) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        if(size == 0) {
            return null;
        }
        int index = ((key.hashCode() % array.length)+ array.length) % array.length;
        if(array[index] != null) {
            return array[index].find(key);
        } else {
            return null;
        }
    }
 
 
   
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new CHTIterator();
    }

    private class CHTIterator extends SimpleIterator<Item<K, V>> {
        private Iterator<Item<K,V>> current;
        private int index = 0;
        private Item<K,V> item = new Item<>(null, null);
//        private Item<K,V>[] list;
//        private int count;
 
    
        public CHTIterator() {
            if(array == null) {
                current = null;
            } else {
                for(int i = 0; i < array.length; i++) {
                    if(array[i] != null) {
                        current = array[i].iterator();
                        index = i + 1;
                        break;
                    }
                }
            }
        }
 
        public boolean hasNext() {
            return this.current.hasNext();
        }
 
        public Item<K, V> next() {
            if(!hasNext()) {
                throw new NoSuchElementException("There is no next element");
            }
            item = current.next();
            if(!current.hasNext()) {
                for(int j = index; j < array.length; j++) {
                    if(array[j] != null) {
                        current = array[j].iterator();
                        index = j + 1;
                        return item;
                    }
                }
            }
            return item;           
        }
    }
}