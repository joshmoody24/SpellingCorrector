package spell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictFile = new File(dictionaryFileName);
        Scanner scanner = new Scanner(dictFile);
        HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

        while(scanner.hasNext()) {
            String word = scanner.next();
            if(dictionary.containsKey(word)) {
                dictionary.put(word, dictionary.get(word)+1);
            }
            else{
                dictionary.put(word, 1);
            }
        }

        /* for debugging
        for(HashMap.Entry<String, Integer> entry : dictionary.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.format("%s: %d", key, value);
        }
        */
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
