import java.io.*;


/**
 * Given a source program, analyze it, find all valid and invalid tokens
 */
class LexicalAnalyzer {

    private BufferedReader br;
    private int lineNumber;
    private int inLinePosition;
    private int lineLength;
    private String line;
    private Table table;


    /**
     * Ctor
     * @param filename source program file name
     */
    LexicalAnalyzer(String filename) {
        try {
            br = new BufferedReader(new FileReader(new File(filename)));
            line = br.readLine();
            if (null != line) {
                lineLength = line.length() + 1;
                lineNumber = 1;
                inLinePosition = 0;
                table = new Table();
            } else {
                System.out.println("Empty file.");
            }
        } catch (IOException e) {
            System.out.println("ERROR at LexicalAnalyzer.ctor: " + e.getMessage());
        }
    }


    /**
     * Get the next token
     * @return the next token
     */
    Token nextToken() {
        int state = 0;
        Token token = null;
        boolean error = false;
        StringBuilder valueBuilder = new StringBuilder();
        String value;
//        StringBuilder statePath = new StringBuilder("0");
        char nextChar;
        int lineOfCMT = -1;
        while(null == token) {
            nextChar = nextChar();
            if ('\3' == nextChar) {
                if (error) {
                    value = valueBuilder.toString();
                    value = value.substring(0, value.length() - 1);
                    token = table.createToken(16, value, lineNumber);
                    break;
                }
                if (21 == state || 22 == state) { // dealing with unclosed comment case
                    value = valueBuilder.toString();
                    value = value.substring(0, value.length() - 1);
                    token = new Token("UNCLOSED  CMT", value, lineOfCMT);
                    break;
                }
                return null; // finish
            }
            state = table.nextState(state, nextChar);
            if (21 == state && -1 == lineOfCMT) lineOfCMT  = lineNumber;
            if (0 != state) {
                if (16 == state) {
                    if (!error) { // first time encounter error char
                        backUpChar(16);
                    } else {
//                        statePath.append(", ").append(state);
                        valueBuilder.append(nextChar);
                    }
                    error = true;
                    state = 0;
                } else {
                    if (error) {
                        backUpChar(state);
                        value = valueBuilder.toString();
                        token = table.createToken(16, value, lineNumber);
                        break;
                    }
//                    statePath.append(", ").append(state);
                    valueBuilder.append(nextChar);
                }
            } else if (error) {
                value = valueBuilder.toString();
                int backTrackNum = backUpChar(state);
                if ('\n' == nextChar || ' ' == nextChar || '\t' == nextChar) {
                    backTrackNum = 0;
                }
                value = value.substring(0, value.length() - backTrackNum);
                token = table.createToken(16, value, lineNumber);
                break;
            }
            if (table.isFinalState(state)) {
                if (table.needBackTrack(state)) {
                    value = valueBuilder.toString();
                    int backTrackNum = backUpChar(state);
                    value = value.substring(0, value.length() - backTrackNum);
                } else {
                    value = valueBuilder.toString();
                }
                token = table.createToken(state, value, lineNumber);
            }
        }
//        System.out.println("\nState path: " + statePath.toString());
        return token;
    }


    /**
     * Get the next char
     * @return the next char
     */
    private char nextChar() {
        if (inLinePosition == lineLength - 1) {
            inLinePosition++;
            return '\n';
        } else if (inLinePosition == lineLength) {
            try {
                line = br.readLine();
                if (null == line) {
                    return '\3'; // End Of Text
                }
                lineLength = line.length() + 1;
                inLinePosition = 0;
                lineNumber++;
                if (1 == lineLength) return '\n'; // empty line
            } catch (IOException e) {
                System.out.println("ERROR at LexicalAnalyzer.nextToken: " + e.getMessage());
                return '\0';
            }
        }
        return line.charAt(inLinePosition++);
    }


    /**
     * Backtrack usage
     * @param state determines the different backtrack way, float may need more than one backtrack steps.
     * @return the backtrack number.
     */
    private int backUpChar(int state) {
        if (0 != inLinePosition) {
            inLinePosition--;
            if (10 == state) {
                int backTrackNum = 1;
                if (0 != inLinePosition) inLinePosition--;
                while(true) {
                    char previousChar = line.charAt(inLinePosition);
                    if ('1' <= previousChar && previousChar <= '9') {
                        inLinePosition++;
                        return backTrackNum;
                    } else if ('.' == previousChar) {
                        inLinePosition += 2;
                        return backTrackNum - 1;
                    } else {
                        backTrackNum++;
                        inLinePosition--;
                    }
                }
            }
            return 1;
        } else {
            return 0;
        }
    }
}