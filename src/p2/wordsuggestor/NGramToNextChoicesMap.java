package p2.wordsuggestor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.LargeValueFirstItemComparator;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import p2.sorts.QuickSort;
import p2.sorts.TopKSort;

public class NGramToNextChoicesMap {
    private final Dictionary<NGram, Dictionary<AlphabeticString, Integer>> map;
    private final Supplier<Dictionary<AlphabeticString, Integer>> newInner;

    public NGramToNextChoicesMap(
            Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter,
            Supplier<Dictionary<AlphabeticString, Integer>> newInner) {
        this.map = newOuter.get();
        this.newInner = newInner;
    }

    /**
     * Increments the count of word after the particular NGram ngram.
     */
    //Me
    public void seenWordAfterNGram(NGram ngram, String word) {
        // if the ngram does not exist, make new inner dictionary and add it
        AlphabeticString str = new AlphabeticString(word);
        if(map.find(ngram) == null) {
            Dictionary<AlphabeticString, Integer> newDict = newInner.get();
            newDict.insert(str, 1);
            map.insert(ngram, newDict);
        } else if(map.find(ngram).find(str) == null) {// if the word does not exist, add it
            map.find(ngram).insert(str, 1);
        } else {
            map.find(ngram).insert(str, map.find(ngram).find(str) + 1);// else, increment count
        }
    }

    // shariya
    /**
     * Returns an array of the DataCounts for this particular ngram. Order is
     * not specified.
     *
     * @param ngram
     *            the ngram we want the counts for
     * 
     * @return An array of all the Items for the requested ngram.
     */
    @SuppressWarnings("unchecked")
    public Item<String, Integer>[] getCountsAfter(NGram ngram) {
        // throw exception if ngram is null
        if(ngram == null || map == null) {
            throw new NoSuchElementException();
        }
        // array to return
        
        Item<String, Integer>[] arr;

        if(map.find(ngram) == null) {
            arr = (Item<String, Integer>[])new Item[0];
        } else {            
            int SIZE = map.find(ngram).size();
            arr = (Item<String, Integer>[])new Item[SIZE];
            Integer i = 0;
            // iterator for ngram inner dictionary
            Iterator<Item<AlphabeticString, Integer>> it = map.find(ngram).iterator();
            // for each string that is a match of some ngram
            while(it.hasNext()) {
                // get next ngram
                Item<AlphabeticString, Integer> nextItem = it.next();
                
                
                // add the string and count to arr
                String ngramWord = nextItem.key.toString();
                arr[i++] = new Item<String, Integer>(ngramWord, nextItem.value); 
            }
        }
        // return array
        return arr;
    }


    public String[] getWordsAfter(NGram ngram, int k) {
        System.err.println(ngram);
        Item<String, Integer>[] afterNGrams = getCountsAfter(ngram);

        Comparator<Item<String, Integer>> comp = new LargeValueFirstItemComparator<String, Integer>();
        if (k < 0) {
            QuickSort.sort(afterNGrams, comp);
        }
        else {
            
            TopKSort.sort(afterNGrams, k, comp.reversed());
        }

        String[] nextWords = new String[k < 0 ? afterNGrams.length : k];
        for (int l = 0; l < afterNGrams.length && l < nextWords.length
                && afterNGrams[l] != null; l++) {
            nextWords[l] = afterNGrams[l].key;
        }
        return nextWords;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
