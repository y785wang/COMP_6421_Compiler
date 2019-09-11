import LexicalAnalyzer.Token;
import LexicalAnalyzer.LexicalAnalyzer;
import SemanticAnalyzer.CEVisitor;
import SemanticAnalyzer.SymbolTable;
import SemanticAnalyzer.TCVisitor;
import SyntacticAnalyzer.SyntacticAnalyzer;
import SyntacticAnalyzer.AST;

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
//                "t_example.txt",
//                "t_phaseOne.txt",
                "t_phaseTwo.txt",
//                "t_simple.txt",

                "done"
        ));
        for (String filename : filenames) {
            if (filename.equals("done")) return;
            System.out.println("-------------------------------------");
            System.out.println("Parsing " + filename + " ...\n");
            SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer(filename);
            AST ast = syntacticAnalyzer.parse();
            if (syntacticAnalyzer.getSuccess()) {
                SymbolTable globalSymbolTable = new SymbolTable("Global");

                CEVisitor ceVisitor = new CEVisitor(globalSymbolTable);
                System.out.println("\nSemantic error during symbol table creation phase:");
                ast.getRoot().accept(ceVisitor);

                System.out.println(globalSymbolTable.print());

                TCVisitor tcVisitor = new TCVisitor(globalSymbolTable);
                System.out.println("\nSemantic error during semantic/type check phase:");
                ast.getRoot().accept(tcVisitor);

                BufferedWriter bw = null;
                try {
                    File symboltableFile = new File("test/symbolTables/" + filename);
                    bw = new BufferedWriter(new FileWriter(symboltableFile));
                    bw.write(globalSymbolTable.print());
                } catch (IOException e ) {
                    System.out.println("ERROR at Driver.runSyntacticAnalyzer(): " + e.getMessage());
                } finally {
                    if (null != bw) {
                        try {
                            bw.close();
                        } catch (IOException e) {
                            System.out.println("ERROR at Driver.run(), 2: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }
}
