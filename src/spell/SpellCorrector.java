package spell;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class SpellCorrector implements ISpellCorrector {
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictFile = new File(dictionaryFileName);
        Scanner scanner = new Scanner(dictFile);
        Trie dictionary = new Trie();

        while(scanner.hasNext()) {
            dictionary.add(scanner.next());
        }

        System.out.println(dictionary.toString());
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        // generate set of "edit distance 1" words
        // use wrong character (25n words)
        // omit character (n words)
        // insert extra character (26(n+1) words)
        // transpose two adjacent characters (n-1 words)

        // generate set of "edit distance 2" words
        // by running the same algorithm on the entire set of edit distance 1 words
        return null;
    }
}
