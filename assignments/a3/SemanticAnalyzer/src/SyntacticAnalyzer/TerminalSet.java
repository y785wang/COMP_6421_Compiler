package SyntacticAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class TerminalSet {
    private static TerminalSet instance;
    private ArrayList<String> terminals;

    static TerminalSet getInstance() {
        if (null == instance) instance = new TerminalSet();
        return instance;
    }

    private TerminalSet() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/terminals.txt")));
            terminals = new ArrayList<>();
            String line;
            while (null != (line = br.readLine())) {
                String[] splitLine = line.split("\\s+");
                terminals.addAll(Arrays.asList(splitLine));
            }
        } catch (IOException e) {
            System.out.println("ERROR at SyntacticAnalyzer.TerminalSet.ctor: " + e.getMessage());
        }
    }

    boolean isTerminal(String symbol) {
        return terminals.contains(symbol);
    }

    int getIndex(String symbol) { return terminals.indexOf(symbol); }
}
