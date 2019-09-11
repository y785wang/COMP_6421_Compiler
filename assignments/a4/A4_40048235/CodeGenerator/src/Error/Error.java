package Error;

import javafx.util.Pair;

import java.util.ArrayList;

public class Error {

    private static Error instance;
    private ArrayList<Pair<Integer, String>> errors;
    private boolean semanticError;

    private Error() {
        errors = new ArrayList<>();
        semanticError = false;
    }

    public static Error getInstance() {
        if (null == instance) instance = new Error();
        return instance;
    }

    public void insert(int lineNumber, String error, int errorType) {
        if (4 == errorType) {
            semanticError = true;
        }
        Pair<Integer, String> errorMessage = new Pair<>(lineNumber, error);
        errors.add(errorMessage);
    }

    public boolean isSemanticErrorExist() { return semanticError; }

    public void print() {
        errors.sort(new SortError());
        System.out.println("\nAll errors: ");
        for (Pair<Integer, String> error : errors) {
            System.out.println("line " + error.getKey() + ", " + error.getValue());
        }
        // TODO: write to a file
    }

    public void clear() {
        errors.clear();
        semanticError = false;
    }
}
