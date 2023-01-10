package spell;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.HashSet;

public class SpellCorrector implements ISpellCorrector {

    private Trie dictionary;

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictFile = new File(dictionaryFileName);
        Scanner scanner = new Scanner(dictFile);
        Trie dictionary = new Trie();

        while(scanner.hasNext()) {
            dictionary.add(scanner.next());
        }

        this.dictionary = dictionary;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        // if it's already a good word, just suggest itself
        if(dictionary.find(inputWord) != null) return inputWord.toLowerCase();

        HashSet<String> dist1Permutations = permuteWord(inputWord);
        String bestMatch = null;
        int maxCount = 0;
        // find the best word that matches, if one exists (first by word count, then by alphabet
        for(String s : dist1Permutations){
            INode word = dictionary.find(s);
            if(word != null){
                if(word.getValue() > maxCount || (bestMatch != null && word.getValue() == maxCount && s.compareTo(bestMatch) < 0)){
                    bestMatch = s;
                    maxCount = word.getValue();
                }
            }
        }
        if(bestMatch == null){
            // generate set of "edit distance 2" words
            // by running the same algorithm on the entire set of edit distance 1 words
            HashSet<String> dist2Permutations = new HashSet<String>();
            for(String s : dist1Permutations){
                HashSet<String> newPermutations = permuteWord(s);
                dist2Permutations.addAll(newPermutations);
            }
            // find the best word that matches, if one exists (first by word count, then by alphabet
            for(String s : dist2Permutations){
                INode word = dictionary.find(s);
                if(word != null){
                    if(word.getValue() > maxCount || (bestMatch != null && word.getValue() == maxCount && s.compareTo(bestMatch) < 0)){
                        bestMatch = s;
                        maxCount = word.getValue();
                    }
                }
            }
        }

        if(bestMatch != null) return bestMatch.toLowerCase();
        return null;
    }

    // generate set of "edit distance 1" words
    public HashSet<String> permuteWord(String source){

        HashSet<String> permutations = new HashSet<String>();

        // use wrong character (25n words)
        for(int i = 0; i < source.length(); i++){
            for(char c : getCharacters()){
                StringBuilder b = new StringBuilder();
                // before wrong char
                b.append(source.substring(0, i));
                // wrong char
                b.append(c);
                // after wrong char
                b.append(source.substring(i+1));
                permutations.add(b.toString());
            }
        }

        // omit character (n words)
        for(int i = 0; i < source.length(); i++){
            permutations.add(source.substring(0, i) + source.substring(i+1));
        }

        // insert extra character (26(n+1) words)
        for(int i = 0; i < source.length()+1; i++){
            for(char c : getCharacters()){
                StringBuilder b = new StringBuilder();
                // before wrong char
                b.append(source.substring(0, i));
                // wrong char
                b.append(c);
                // after wrong char
                b.append(source.substring(i));
                permutations.add(b.toString());
            }
        }

        // transpose two adjacent characters (n-1 words)
        for(int i = 0; i < source.length()-1; i++){
            char current = source.toCharArray()[i];
            char next = source.toCharArray()[i+1];
            StringBuilder b = new StringBuilder();
            // before wrong char
            b.append(source.substring(0, i));
            // wrong char
            b.append(next);
            b.append(current);
            // after wrong char
            b.append(source.substring(i+2));
            permutations.add(b.toString());
        }

        return permutations;
    }

    public static char[] getCharacters(){
        return "abcdefghijklmnopqrstuvwxyz".toCharArray();
    }
}
