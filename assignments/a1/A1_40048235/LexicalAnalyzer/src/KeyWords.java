import java.util.Arrays;
import java.util.HashSet;


/**
 * Store all reserved word. Able to check whether an id is
 * reserved word or not.
 */
class KeyWords {

    private static KeyWords instance;
    private static HashSet<String> reservedWords;


    /**
     * Ctor
     */
    private KeyWords () {
        reservedWords = new HashSet<String>(
                Arrays.asList("if","then","else","for","class","integer","float","read","write","return","main"));
    }


    /**
     * Singleton structure initialize
     */
    static void init() { if (null == instance) instance = new KeyWords(); }


    /**
     * Check if the input id is reserved
     * @param word is the input id
     * @return true if input id is reserved, false otherwise
     */
    static boolean reserved(String word) { return reservedWords.contains(word); }
}
