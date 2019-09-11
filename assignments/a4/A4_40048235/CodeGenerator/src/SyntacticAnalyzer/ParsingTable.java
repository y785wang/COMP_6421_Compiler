package SyntacticAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class ParsingTable {

    private static ParsingTable instance;
    private ArrayList<ArrayList<Integer>> parsingTable;

    private ParsingTable() {
        parsingTable = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/parsingTable.txt")));
            String line;
            int index = 0;
            while (null != (line = br.readLine())) {
                String[] splitLine = line.split("\\s+");
                ArrayList<Integer> row = new ArrayList<>();
                for (String ruleNum : splitLine) {
                    row.add(Integer.parseInt(ruleNum));
                }
                parsingTable.add(index++, row);
            }
        } catch (IOException e) {
            System.out.println("ERROR at SyntacticAnalyzer.ParsingTable.ctor: " + e.getMessage());
        }
    }

    static ParsingTable getInstance() {
        if (null == instance) instance = new ParsingTable();
        return instance;
    }

    int getRuleNum(String x, String a) {
        int row, col;

        NonTerminalSet nonTerminalSet = NonTerminalSet.getInstance();
        row = nonTerminalSet.getIndex(x);
        TerminalSet terminalSet = TerminalSet.getInstance();
        col = terminalSet.getIndex(a);

        return parsingTable.get(row).get(col);
    }
}
