package Error;

import javafx.util.Pair;
import java.util.Comparator;

public class SortError implements Comparator<Pair<Integer, String>> {
    public int compare(Pair<Integer, String> error1, Pair<Integer, String> error2) {
        return error1.getKey() - error2.getKey();
    }
}
