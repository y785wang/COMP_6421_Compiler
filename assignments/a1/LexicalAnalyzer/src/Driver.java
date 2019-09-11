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
    void run() {
        ArrayList<String> filenames = new ArrayList<String>(Arrays.asList(
//                "t_id",
                "t_integer",
//                "t_float",
//                "t_operator",
//                "t_punctuation",
//                "t_combined",
//                "t_creative",
//                "t_examples",
//                "t_empty",
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
                        resultData.append("[ ").append(token.getType()).append(" ] ");
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
}
