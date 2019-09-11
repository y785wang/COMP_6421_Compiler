import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;

class SyntacticAnalyzer {

    private Stack<String> stack;
    private String filename;
    private LinkedList<String> derivation;
    private StringBuilder derivations;
    private StringBuilder syntacticErrors;
    private boolean showDerivations = false;
    private boolean showSyntacticErrors = false;
    private boolean error = false;

    SyntacticAnalyzer(String filename) {
        this.filename = filename;
        stack = new Stack<>();
        derivation = new LinkedList<>();
        derivations = new StringBuilder();
        syntacticErrors = new StringBuilder();
    }

    void parse() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("test/testCases/" + filename);
        TerminalSet terminalSet = TerminalSet.getInstance();
        ParsingTable parsingTable = ParsingTable.getInstance();
        ProductionRule productionRule = ProductionRule.getInstance();
        boolean success = true;
        int lineNumber;

        stack.push("$");
        stack.push("prog");
        derivation.add("prog");
        if (showDerivations) System.out.println(derivation);
        derivations.append(derivation.toString()).append("\n");
        Token token = lexicalAnalyzer.nextToken();
        if (null == token) { return; }
        String a = token.getType();
        lineNumber = token.getLineNumber();

        // TODO: if toke type is unknown, scan?
        while (!stack.isEmpty()) {

            // ignore a token if it represent a comment
            if (null != token && token.getType().equals("CMT")) {
                token = lexicalAnalyzer.nextToken();
                a = token.getType();
                lineNumber = token.getLineNumber();
                continue;
            }

            String x = stack.peek();
            if (terminalSet.isTerminal(x)) {
                // top stack is terminal symbol
                if (x.equals(a)) {
                    // top stack matches the input
                    error = false;
                    stack.pop();
                    if (!x.equals("$")) {
                        int drvIndex = derivation.indexOf(a);
                        derivation.remove(drvIndex);
                        derivation.add(drvIndex, token.getValue());
                        if (showDerivations) System.out.println(derivation);
                        derivations.append(derivation.toString()).append("\n");
                    }
                    token = lexicalAnalyzer.nextToken();
                    if (null != token) {
                        lineNumber = token.getLineNumber();
                        a = token.getType();
                    } else {
                        a = "$";
                    }
                } else {
                    // top stack does not match the input
                    String syntacticError = generateErrorMessage(a, lineNumber, a);
                    if (a.equals("$")) { // if nothing to scan, pop
                        stack.pop();
                        // TODO: also remove derivation symble?
                        syntacticError += "pop\n";
                    } else {
                        token = lexicalAnalyzer.nextToken();
                        if (null != token) {
                            lineNumber = token.getLineNumber();
                            a = token.getType();
                        } else {
                            a = "$";
                        }
                        syntacticError += "scan\n";
                    }
                    if (!error) {
                        error = true;
                        if (showSyntacticErrors) System.out.print(syntacticError);
                        syntacticErrors.append(syntacticError);
                    }
                    success = false;
                }

            } else {

                // top stack is non-terminal symbol, go through the table, find the rule
                int ruleNum = parsingTable.getRuleNum(x, a);
                if (ruleNum < 105) {
                    // find valid rule
                    error = false;
                    ArrayList<String> rule =  productionRule.getProductionRule(ruleNum);
                    int drvIndex = derivation.indexOf(rule.get(0));
                    stack.pop();
                    derivation.remove(rule.get(0));
                    if (2 != rule.size() || !rule.get(1).equals("EPSILON")) {
                        for (int i = rule.size()-1; i > 0; --i) {
                            stack.push(rule.get(i));
                            derivation.add(drvIndex, rule.get(i));
                        }
                    }
                    if (showDerivations) System.out.println(derivation);
                    derivations.append(derivation.toString()).append("\n");
                } else {
                    // no valid rule is found, either scan or pop
                    String tokenValue;
                    if (null == token) {
                        tokenValue = "$";
                    } else {
                        tokenValue = token.getValue();
                    }
                    String syntacticError = generateErrorMessage(a, lineNumber, tokenValue);
                    if (105 == ruleNum) { // scan or pop
                        token = lexicalAnalyzer.nextToken();
                        lineNumber = token.getLineNumber();
                        a = token.getType();
                        syntacticError += "scan\n";
                    } else if (106 == ruleNum) {
                        int drvIndex = derivation.indexOf(stack.peek());
                        if (-1 != drvIndex) {
                            derivation.remove(drvIndex);
                            if (showDerivations) System.out.println(derivation);
                            derivations.append(derivation.toString()).append("\n");
                        }
                        stack.pop();
                        syntacticError += "pop\n";
                    }
                    if (error) {
                        System.out.print("");
                    } else {
                        error = true;
                        if (showSyntacticErrors) System.out.print(syntacticError);
                        syntacticErrors.append(syntacticError);
                    }
                    success = false;
                }
            }
        }

        // determine parsing result
        if (!a.equals("$") || !success) {
            System.out.println("False");
        } else {
            System.out.println("True");
        }

        generateOutputFiles();
    }

    private void generateOutputFiles() {
        BufferedWriter derivationsBW = null;
        BufferedWriter syntacticErrorsBW = null;
        try {
            File derivationsFile = new File("test/derivations/" + filename);
            File syntacticErrorsFile = new File("test/syntacticErrors/" + filename);
            derivationsBW = new BufferedWriter(new FileWriter(derivationsFile));
            syntacticErrorsBW = new BufferedWriter(new FileWriter(syntacticErrorsFile));
            derivationsBW.write(derivations.toString());
            syntacticErrorsBW.write(syntacticErrors.toString());
        } catch (IOException e ) {
            System.out.println("ERROR 1 at SyntacticAnalyzer.writeDerivation(): " + e.getMessage());
        } finally {
            if (null != derivationsBW && null != syntacticErrorsBW) {
                try {
                    derivationsBW.close();
                    syntacticErrorsBW.close();
                } catch (IOException e) {
                    System.out.println("ERROR 2 at SyntacticAnalyzer.writeDerivation(): " + e.getMessage());
                }
            }
        }
    }

    private String generateErrorMessage(String terminal, int lineNumber, String terminalValue) {

        String syntacticError = "ERROR: ";
        String top = stack.peek();
        TerminalSet terminalSet = TerminalSet.getInstance();
        if (terminalSet.isTerminal(top)) {
            syntacticError += "missing \"" + top + "\" at line " + lineNumber + "\n";
        } else {
            if (terminalValue.equals("$")) {
                syntacticError += "reaching end of file at line " + lineNumber + "\n";
            } else {
                syntacticError += "invalid \"" + terminalValue + "\" at line " + lineNumber + "\n";
            }
        }
        syntacticError       += "       top stack: \"" + stack.peek() + "\"\n";
        syntacticError       += "       symbol   : \"" + terminal + "\"\n";
        syntacticError       += "       action   : ";
        return syntacticError;
    }
}
