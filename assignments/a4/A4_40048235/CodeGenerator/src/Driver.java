import CodeGenerater.SBCGVisitor;
import SemanticAnalyzer.CEVisitor;
import SemanticAnalyzer.SymbolTable;
import SemanticAnalyzer.TCVisitor;
import SyntacticAnalyzer.SyntacticAnalyzer;
import SyntacticAnalyzer.AST;
import Error.Error;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Repeatedly calls the lexical analysis function,
 * prints each token till the end of source program
 * is reached
 */
class Driver {

    void runCodeGenerator() {

        ArrayList<String> filenames = new ArrayList<>(Arrays.asList(

                "t_2.txt",
                "t_3.txt",
                "t_4.1.txt",
                "t_4.2.txt",
                "t_5.1.txt",
                "t_5.2.txt",
                "t_5.3.txt",
                "t_5.4.txt",
                "t_5.5.txt",

//                "t_single.txt",

                "done"
        ));

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter one of the following test case number: ");
//        System.out.println("2, 3, 4.1, 4.2, 5.1, 5.2, 5.3, 5.4, 5.5");
//        String testFileName = "t_" + scanner.nextLine() + ".txt";
//        filenames.clear();
//        filenames.add(testFileName);
//        filenames.add("done");

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

                TCVisitor tcVisitor = new TCVisitor(globalSymbolTable);
                System.out.println("\nSemantic error during semantic/type check phase:");
                ast.getRoot().accept(tcVisitor);

                System.out.println("\n-------------------------");

                // code generation
                SBCGVisitor sbcgVisitor = new SBCGVisitor(globalSymbolTable);
                if (!Error.getInstance().isSemanticErrorExist() || filename.contains("t_5.")) {
                    // calculate offset for allocate memory
                    globalSymbolTable.initScopeOffset(globalSymbolTable);

                    // print update symbol table
                    System.out.println(globalSymbolTable.print());

                    // generate assembly code
                    ast.getRoot().accept(sbcgVisitor);
                } else {
                    System.out.println(globalSymbolTable.print());
                }

                BufferedWriter bw1 = null;
                BufferedWriter bw2 = null;
                try {
                    File symbolTableFile = new File("test/4_symbol_tables/" + filename);
                    File assemblyCodeFile = new File("test/assemblyCodes/" + filename.replaceAll("txt", "m"));
                    bw1 = new BufferedWriter(new FileWriter(symbolTableFile));
                    bw2 = new BufferedWriter(new FileWriter(assemblyCodeFile));
                    bw1.write(globalSymbolTable.print());
                    bw2.write(sbcgVisitor.getAssemblyCode());
                } catch (IOException e ) {
                    System.out.println("ERROR at Driver.runSyntacticAnalyzer(): " + e.getMessage());
                } finally {
                    if (null != bw1 && null != bw2) {
                        try {
                            bw1.close();
                            bw2.close();
                        } catch (IOException e) {
                            System.out.println("ERROR at Driver.run(), 2: " + e.getMessage());
                        }
                    }
                }
            }
            Error.getInstance().print();
            Error.getInstance().clear();
        }
    }
}
