package LexicalAnalyzer;

import java.util.Arrays;
import java.util.HashSet;


/**
 * Store all reserved word. Able to check whether an id is
 * reserved word or not.
 */
public class KeyWord {

    private static KeyWord instance;
    private static HashSet<String> reservedWords;


    /**
     * Ctor
     */
    private KeyWord() {
        reservedWords = new HashSet<String>(
                Arrays.asList("if","then","else","for","class","integer","float","read","write","return","main"));
    }


    /**
     * Singleton structure initialize
     */
    static void init() { if (null == instance) instance = new KeyWord(); }


    /**
     * Check if the input id is reserved
     * @param word is the input id
     * @return true if input id is reserved, false otherwise
     */
    public static boolean reserved(String word) { return reservedWords.contains(word); }
}
