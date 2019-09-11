package LexicalAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.lang.Character;


/**
 * Store all DFA table, match each coming char with current state.
 */
class Table {

    private HashSet<Integer> finalStates;
    private HashSet<Integer> backTrackStates;
    private ArrayList<ArrayList<Integer>> transTable;
    private String invalidType;


    /**
     * Ctor, initialize all table info
     */
    Table() {
        finalStates = new HashSet<Integer>();
        finalStates.addAll(
                Arrays.asList(2,4,6,10,14,15,18,20,23,25,26,28,29,30,32,33,35,36,37,39,41,42,43,44,45,46,47,48,49,50,51,52,53));
        backTrackStates = new HashSet<Integer>();
        backTrackStates.addAll(Arrays.asList(2,4,6,10,14,18,20,25,28,32,35));
        transTable = new ArrayList<ArrayList<Integer>>();
        transTable.add(0,  new ArrayList<Integer>(Arrays.asList( 0, 0, 1, 5, 3,    16,42, 1,46,47,    16,17,37,24,27,    31,34,38,40,43,    44,45,48,49,50,    51,52,53)));
        transTable.add(1,  new ArrayList<Integer>(Arrays.asList( 2, 2, 1, 1, 1,     1, 2, 1, 2, 2,     2, 2, 2, 2, 2,     2, 2, 2, 2, 2,     2, 2, 2, 2, 2,     2, 2, 2)));
        transTable.add(2,  new ArrayList<Integer>(0));
        transTable.add(3,  new ArrayList<Integer>(Arrays.asList( 4, 4, 4, 3, 3,     4, 7, 4, 4, 4,     4, 4, 4, 4, 4,     4, 4, 4, 4, 4,     4, 4, 4, 4, 4,     4, 4, 4)));
        transTable.add(4,  new ArrayList<Integer>(0));
        transTable.add(5,  new ArrayList<Integer>(Arrays.asList( 6, 6, 6, 6, 6,     6, 7, 6, 6, 6,     6, 6, 6, 6, 6,     6, 6, 6, 6, 6,     6, 6, 6, 6, 6,     6, 6, 6)));
        transTable.add(6,  new ArrayList<Integer>(0));
        transTable.add(7,  new ArrayList<Integer>(Arrays.asList(16,16,16, 8, 9,    16,16,16,16,16,    16,16,16,16,16,    16,16,16,16,16,    16,16,16,16,16,    16,16,16)));
        transTable.add(8,  new ArrayList<Integer>(Arrays.asList(10,10,10, 8, 9,    10,10,10,10,10,    10,10,10,10,10,    10,10,10,10,10,    10,10,10,10,10,    10,10,10)));
        transTable.add(9,  new ArrayList<Integer>(Arrays.asList(10,10,10, 8, 9,    10,10,11,10,10,    10,10,10,10,10,    10,10,10,10,10,    10,10,10,10,10,    10,10,10)));
        transTable.add(10, new ArrayList<Integer>(0));
        transTable.add(11, new ArrayList<Integer>(Arrays.asList(16,16,16,15,13,    16,16,16,12,12,    16,16,16,16,16,    16,16,16,16,16,    16,16,16,16,16,    16,16,16)));
        transTable.add(12, new ArrayList<Integer>(Arrays.asList(16,16,16,15,13,    16,16,16,16,16,    16,16,16,16,16,    16,16,16,16,16,    16,16,16,16,16,    16,16,16)));
        transTable.add(13, new ArrayList<Integer>(Arrays.asList(14,14,14,13,13,    14,14,14,14,14,    14,14,14,14,14,    14,14,14,14,14,    14,14,14,14,14,    14,14,14)));
        transTable.add(14, new ArrayList<Integer>(0));
        transTable.add(15, new ArrayList<Integer>(0));
        transTable.add(16, new ArrayList<Integer>(0));
        transTable.add(17, new ArrayList<Integer>(Arrays.asList(18,18,18,18,18,    18,18,18,18,18,    18,19,21,18,18,    18,18,18,18,18,    18,18,18,18,18,    18,18,18)));
        transTable.add(18, new ArrayList<Integer>(0));
        transTable.add(19, new ArrayList<Integer>(Arrays.asList(19,20,19,19,19,    19,19,19,19,19,    19,19,19,19,19,    19,19,19,19,19,    19,19,19,19,19,    19,19,19)));
        transTable.add(20, new ArrayList<Integer>(0));
        transTable.add(21, new ArrayList<Integer>(Arrays.asList(21,21,21,21,21,    21,21,21,21,21,    21,21,22,21,21,    21,21,21,21,21,    21,21,21,21,21,    21,21,21)));
        transTable.add(22, new ArrayList<Integer>(Arrays.asList(21,21,21,21,21,    21,21,21,21,21,    21,23,22,21,21,    21,21,21,21,21,    21,21,21,21,21,    21,21,21)));
        transTable.add(23, new ArrayList<Integer>(0));
        transTable.add(24, new ArrayList<Integer>(Arrays.asList(25,25,25,25,25,    25,25,25,25,25,    25,25,25,26,25,    25,25,25,25,25,    25,25,25,25,25,    25,25,25)));
        transTable.add(25, new ArrayList<Integer>(0));
        transTable.add(26, new ArrayList<Integer>(0));
        transTable.add(27, new ArrayList<Integer>(Arrays.asList(28,28,28,28,28,    28,28,28,28,28,    28,28,28,30,28,    29,28,28,28,28,    28,28,28,28,28,    28,28,28)));
        transTable.add(28, new ArrayList<Integer>(0));
        transTable.add(29, new ArrayList<Integer>(0));
        transTable.add(30, new ArrayList<Integer>(0));
        transTable.add(31, new ArrayList<Integer>(Arrays.asList(32,32,32,32,32,    32,32,32,32,32,    32,32,32,33,32,    32,32,32,32,32,    32,32,32,32,32,    32,32,32)));
        transTable.add(32, new ArrayList<Integer>(0));
        transTable.add(33, new ArrayList<Integer>(0));
        transTable.add(34, new ArrayList<Integer>(Arrays.asList(35,35,35,35,35,    35,35,35,35,35,    35,35,35,35,35,    35,36,35,35,35,    35,35,35,35,35,    35,35,35)));
        transTable.add(35, new ArrayList<Integer>(0));
        transTable.add(36, new ArrayList<Integer>(0));
        transTable.add(37, new ArrayList<Integer>(0));
        transTable.add(38, new ArrayList<Integer>(Arrays.asList(16,16,16,16,16,    16,16,16,16,16,    16,16,16,16,16,    16,16,39,16,16,    16,16,16,16,16,    16,16,16)));
        transTable.add(39, new ArrayList<Integer>(0));
        transTable.add(40, new ArrayList<Integer>(Arrays.asList(16,16,16,16,16,    16,16,16,16,16,    16,16,16,16,16,    16,16,16,41,16,    16,16,16,16,16,    16,16,16)));
        transTable.add(41, new ArrayList<Integer>(0));
        transTable.add(42, new ArrayList<Integer>(0));
        transTable.add(43, new ArrayList<Integer>(0));
        transTable.add(44, new ArrayList<Integer>(0));
        transTable.add(45, new ArrayList<Integer>(0));
        transTable.add(46, new ArrayList<Integer>(0));
        transTable.add(47, new ArrayList<Integer>(0));
        transTable.add(48, new ArrayList<Integer>(0));
        transTable.add(49, new ArrayList<Integer>(0));
        transTable.add(50, new ArrayList<Integer>(0));
        transTable.add(51, new ArrayList<Integer>(0));
        transTable.add(52, new ArrayList<Integer>(0));
        transTable.add(53, new ArrayList<Integer>(0));
        invalidType = "UNKNOWN  TYPE";
        KeyWord.init();
    }


    /**
     * Check whether the input state is final state or not
     * @param state is the input state
     * @return true if the input state is final state, false otherwise
     */
    boolean isFinalState(int state) { return finalStates.contains(state); }


    /**
     * Check whether the input final state needs backtrack or not
     * @param state is the input final state
     * @return true if the input final state need backtrack, false otherwise
     */
    boolean needBackTrack(int state) { return backTrackStates.contains(state); }


    /**
     * Get the next state based on the current state and the coming char
     * @param currentState is the current state
     * @param comingChar is the coming char
     * @return the next state number
     */
    int nextState(int currentState, char comingChar) {
        if (finalStates.contains(currentState)) return 0;
        int comingCharIndex = findComingCharIndex(comingChar);
        if (-1 == comingCharIndex) {
            invalidType = "UNKNOWN  TYPE";
            return 16; // error state
        } else {
            int nextState = transTable.get(currentState).get(comingCharIndex);
            if (7 == nextState || 11 == nextState || 12 == nextState) {
                invalidType = "INVALID FLOAT";
            }
            return nextState;
        }
    }


    /**
     * Match a defined char into table index
     * @param comingChar is the coming char
     * @return the table index
     */
    private int findComingCharIndex(char comingChar) {
        if ('\t' == comingChar || ' ' == comingChar) {
            return 0;
        } else if ('\n' == comingChar) {
            return 1;
        } else if ('e' == comingChar) {
            return 7;
        } else if (Character.isUpperCase(comingChar) || Character.isLowerCase(comingChar)) {
            return 2;
        } else if ('0' == comingChar) {
            return 3;
        } else if ('0' < comingChar && comingChar <= '9') {
            return 4;
        } else if ('_' == comingChar) {
            if (invalidType.equals("UNKNOWN  TYPE")) invalidType = "INVALID    ID";
            return 5;
        } else if ('.' == comingChar) {
            return 6;
        } else if ('+' == comingChar) {
            return 8;
        } else if ('-' == comingChar) {
            return 9;
        } else if ('/' == comingChar) {
            return 11;
        } else if ('*' == comingChar) {
            return 12;
        } else if ('=' == comingChar) {
            return 13;
        } else if ('<' == comingChar) {
            return 14;
        } else if ('>' == comingChar) {
            return 15;
        } else if (':' == comingChar) {
            return 16;
        } else if ('|' == comingChar) {
            return 17;
        } else if ('&' == comingChar) {
            return 18;
        } else if (',' == comingChar) {
            return 19;
        } else if (';' == comingChar) {
            return 20;
        } else if ('!' == comingChar) {
            return 21;
        } else if ('(' == comingChar) {
            return 22;
        } else if (')' == comingChar) {
            return 23;
        } else if ('[' == comingChar) {
            return 24;
        } else if (']' == comingChar) {
            return 25;
        } else if ('{' == comingChar) {
            return 26;
        } else if ('}' == comingChar) {
            return 27;
        } else {
            return 10; // unknown character
        }
    }


    /**
     * Create a token based on the state
     * @param state is the state
     * @param value is the token value
     * @param lineNumber is the token line number
     * @return the token
     */
    Token createToken(int state, String value, int lineNumber) {
        String type = "";
        switch (state) {
            case 2:
                type = "id";
                break;
            case 4: case 6:
                type = "intNum";
                break;
            case 10: case 14: case 15:
                type = "floatNum";
                break;
            case 16:
                type = invalidType;
                break;
            case 18:
                type = "/";
                break;
            case 20: case 23:
                type = "CMT";
                break;
            case 25:
                type = "=";
                break;
            case 26:
                type = "eq";
                break;
            case 28:
                type = "lt";
                break;
            case 29:
                type = "neq";
                break;
            case 30:
                type = "leq";
                break;
            case 32:
                type = "gt";
                break;
            case 33:
                type = "geq";
                break;
            case 35:
                type = ":";
                break;
            case 36:
                type = "sr";
                break;
            case 37:
                type = "*";
                break;
            case 39:
                type = "or";
                break;
            case 41:
                type = "and";
                break;
            case 42:
                type = ".";
                break;
            case 43:
                type = ",";
                break;
            case 44:
                type = ";";
                break;
            case 45:
                type = "not";
                break;
            case 46:
                type = "+";
                break;
            case 47:
                type = "-";
                break;
            case 48:
                type = "(";
                break;
            case 49:
                type = ")";
                break;
            case 50:
                type = "[";
                break;
            case 51:
                type = "]";
                break;
            case 52:
                type = "{";
                break;
            case 53:
                type = "}";
                break;
        }
        invalidType = "UNKNOWN  TYPE";
        return new Token(type, value, lineNumber);
    }
}
