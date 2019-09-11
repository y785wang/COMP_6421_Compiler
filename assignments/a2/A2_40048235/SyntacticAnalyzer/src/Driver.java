import LexicalAnalyzer.Token;
import LexicalAnalyzer.LexicalAnalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Repeatedly calls the lexical analysis function,
 * prints each token till the end of source program
 * is reached
 */
class Driver {

    /**
     * run either all test file or single file
     */
    void runLexicalAnalyzer() {
        ArrayList<String> filenames = new ArrayList<>(Arrays.asList(
                "t_example",
                "done"
        ));
        Token token;
        for(String filename : filenames) {
            if (filename.equals("done")) return;
            System.out.println("\n---------- Testing file " + filename + " ... ----------\n");
            LexicalAnalyzer l = new LexicalAnalyzer("test/testCases/" + filename);
            StringBuilder resultData = new StringBuilder();
            StringBuilder errorData = new StringBuilder();
            BufferedWriter resultBW = null;
            BufferedWriter errorBW = null;
            while(null != (token = l.nextToken())) {
                if (token.notCMT()) {
                    if (token.notError()) {
                        System.out.println(token);
                        resultData.append(token.getType()).append("\n");
                    } else {
                        System.out.println(token);
                        errorData.append(token.toString()).append("\n");
                    }
                }
            }
            try {
                File resultFile = new File("test/testResults/" + filename);
                File errorFile = new File("test/testErrors/" + filename);
                resultBW = new BufferedWriter(new FileWriter(resultFile));
                resultBW.write(resultData.toString());
                errorBW = new BufferedWriter(new FileWriter(errorFile));
                errorBW.write(errorData.toString());
            } catch (IOException e ) {
                System.out.println("ERROR at Driver.run(), 1: " + e.getMessage());
            } finally {
                if (null != resultBW && null != errorBW) {
                    try {
                        resultBW.close();
                        errorBW.close();
                    } catch (IOException e) {
                        System.out.println("ERROR at Driver.run(), 2: " + e.getMessage());
                    }
                }
            }
        }
    }

    void runSyntacticAnalyzer() {
        ArrayList<String> filenames = new ArrayList<>(Arrays.asList(
                // valid case
//                "t_decl.txt",
//                "t_funcDef.txt",
//                "t_example.txt",
                "t_simple.txt",


                // invalid case
//                "t_pop.txt",
//                "t_scan.txt",

                // empty case
//                "t_emptyInput.txt",

                "done"
        ));
        for (String filename : filenames) {
            if (filename.equals("done")) return;
            System.out.println("-------------------------------------");
            System.out.println("Parsing " + filename + " ...\n");
            SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(filename);
            syntacticAnalyzer.parse();
        }
    }
}
