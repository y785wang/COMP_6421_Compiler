/**
 * Store type, value, and lineNumber info for a token
 */
class Token {
    private String type;
    private String value;
    private int lineNumber;


    /**
     * Ctor
     * @param type is the token type
     * @param value is the token value
     * @param lineNum is the token line number
     */
    Token(String type, String value, int lineNum) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNum;
        if (KeyWords.reserved(value)) {
            this.type = value.toUpperCase();
        }
    }


    /**
     * Help to print the token
     * @return a string that shows all the token info
     */
    public String toString() {return "[ " + type + ": " + value + ", line #: " + lineNumber + " ] "; }


    /**
     * Check whether a token is not a comment or not
     * @return false if the token represents a comment, true otherwise
     */
    boolean notCMT() { return !type.equals("CMT"); }


    /**
     * Check whether a token does not represent an error or not
     * @return false if the token represents an error, true otherwise
     */
    boolean notError() {
        return !type.equals("UNKNOWN  TYPE") && !type.equals("INVALID FLOAT") &&
                !type.equals("INVALID    ID") && !type.equals("UNCLOSED  CMT");
    }


    /**
     * Get the type of token, for atocc format usage
     * @return the type of the token
     */
    String getType() { return type; }
}
