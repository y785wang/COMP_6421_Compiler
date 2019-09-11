import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class NonTerminalSet {

    private static NonTerminalSet instance;
    private ArrayList<String> nonTerminals;

    static NonTerminalSet getInstance() {
        if (null == instance) instance = new NonTerminalSet();
        return instance;
    }

    private NonTerminalSet() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/nonTerminals.txt")));
            nonTerminals = new ArrayList<>();
            String line;
            while (null != (line = br.readLine())) {
                String[] splitLine = line.split("\\s+");
                nonTerminals.addAll(Arrays.asList(splitLine));
            }
        } catch (IOException e) {
            System.out.println("ERROR at NonTerminalSet.ctor: " + e.getMessage());
        }
    }

    int getIndex(String symbol) { return nonTerminals.indexOf(symbol); }

}
