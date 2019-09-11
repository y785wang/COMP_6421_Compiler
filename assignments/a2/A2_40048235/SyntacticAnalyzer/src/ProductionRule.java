import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class ProductionRule {

    private static ProductionRule instance;
    private HashMap<Integer, ArrayList<String>> productionRules;

    private ProductionRule() {
        productionRules = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/productionRule.txt")));
            String line;
            int index = 0;
            while (null != (line = br.readLine())) {
                line = line.trim();
                String[] symbols = line.split("\\s+");
                ArrayList<String> productionRule = new ArrayList<>();
                for (int i = 0; i < symbols.length; ++i) {
                    if (1 == i) continue; // ignore "->"
                    productionRule.add(symbols[i]);
                }
                productionRules.put(++index, productionRule);
            }
        } catch (IOException e) {
            System.out.println("ERROR at ProductionRule.ctor: " + e.getMessage());
        }
    }

    static ProductionRule getInstance() {
        if (null == instance) instance = new ProductionRule();
        return instance;
    }

    ArrayList<String> getProductionRule(int index) { return productionRules.get(index); }
}
