package SemanticAnalyzer;

import java.util.ArrayList;

public class SymbolTable {
    private String tableName;
    private String inher;       // only for class symbol table usage
    private String scopeSpec;   // only for function symbol table usage
    private ArrayList<SymbolTableRecord> symbolTableRecords;

    public SymbolTable(String tableName) {
        this.tableName = tableName;
        symbolTableRecords = new ArrayList<>();
        inher = "";
        scopeSpec = "";
    }

    void insert(SymbolTableRecord newRecord) {
        boolean error = false;
        for (SymbolTableRecord  oldRecord : symbolTableRecords) {
            if (oldRecord.equal(newRecord)) {
                error = true;
                System.out.println("|");
                System.out.println("|  Multiply declared identifiers in scope " + tableName);
                System.out.println("|  " + oldRecord);
                System.out.println("|  " + newRecord);
            }
        }
        // TODO: if error, should the newRecord be ignored?
        if (!error) symbolTableRecords.add(newRecord);
    }

    void insertWithoutErrorCheck(SymbolTableRecord newRecord) { symbolTableRecords.add(newRecord); }

    // check if the funcDecl record link to a corresponding funcDef
    // only called when this class represent the global symbol tale
    void checkFuncDefExistence() {
        for (SymbolTableRecord globalRecord : symbolTableRecords) {
            if (globalRecord.getKind().equals("class")) {
                for (SymbolTableRecord classRecord : globalRecord.getLink().getSymbolTableRecords()) {
                    if (classRecord.getKind().equals("function") && null == classRecord.getLink()) {
                        System.out.println("|");
                        System.out.println("|  Missing member function definition for " + classRecord.getName() + " in scope " + globalRecord.getName());
                        System.out.println("|  " + classRecord);
                    }
                }
            }
        }
    }

    // only called when this class represent the global symbol tale
    void checkClassInherExistence() {
        for (SymbolTableRecord classRecord : symbolTableRecords) {
            if (classRecord.getKind().equals("class")) {
                String allInher = classRecord.getLink().getInher();
                if (allInher.isEmpty()) continue;
                allInher = allInher.substring(3);
                // TODO: deal with multi-inher
                if (!checkClassNameExistence(allInher)) {
                    System.out.println("|");
                    System.out.println("|  Undefined inheritance " + allInher + " in scope " + classRecord.getName());
                    System.out.println("|  " + classRecord);
                }
            } else {
                break;
            }
        }
    }

    // only called when this class represent the global symbol tale
    private boolean checkClassNameExistence(String className) {
        for (SymbolTableRecord classRecord : symbolTableRecords) {
            if (classRecord.getKind().equals("class")) {
                if (classRecord.getName().equals(className)) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    boolean linkMemberFunction(String className, SymbolTable globalSymbolTable) {
        boolean linkResult = false;
        for (SymbolTableRecord symbolTableRecord : globalSymbolTable.getSymbolTableRecords()) {
            if (symbolTableRecord.getName().equals(className) && symbolTableRecord.getKind().equals("class")) {
                ArrayList<SymbolTableRecord> memberRecords = symbolTableRecord.getLink().getSymbolTableRecords();
                for (SymbolTableRecord memberRecord : memberRecords) {
                    if (tableName.equals(className + "::" + memberRecord.getName())) {
                        memberRecord.setLink(this);
                        linkResult = true;
                    }
                }
            }
        }
        return linkResult;
    }

    void setInher(String inher) { this.inher = inher; }

    void setScopeSpec(String scopeSpec) {
        this.scopeSpec = scopeSpec;
        // TODO: separate scopeSpec to ArrayList for reducing further checking workload
        tableName = scopeSpec + "::" + tableName;
    }

    String getTableName() { return tableName; }

    String getInher() { return inher; }

    ArrayList<SymbolTableRecord> getSymbolTableRecords() { return symbolTableRecords; }

    SymbolTable copy() {
        SymbolTable temp = new SymbolTable("");
        return temp;
    }

    public String toString() {
        String name, kind, type, link;
        String startEndLine = "===========================================================================================\n";
        String separateLine = "|-------------------+-----------+-------------------------------------+-------------------|\n";
        String endLine = "|\n";

        StringBuilder temp = new StringBuilder("\n");
        temp.append(startEndLine);
        temp.append(String.format("%-90s" , "| " + tableName + inher)).append(endLine);
        temp.append(separateLine);
        temp.append(String.format("%-20s" , "| name")).append(String.format("%-12s" , "| kind")).append(String.format("%-38s" , "| type")).append(String.format("%-20s" , "| link")).append(endLine);
        for (SymbolTableRecord symbolTableRecord : symbolTableRecords) {
            name = symbolTableRecord.getName();
            kind = symbolTableRecord.getKind();
            type = symbolTableRecord.getType();
            link = symbolTableRecord.getLinkName();
            temp.append(separateLine);
            temp.append(String.format("%-20s" , "| " + name)).append(String.format("%-12s" , "| " + kind)).append(String.format("%-38s" , "| " + type)).append(String.format("%-20s" , "| " + link)).append(endLine);
        }
        temp.append(startEndLine);

        return temp.toString();
    }

    public String print() {
        StringBuilder temp = new StringBuilder(toString());
        for (SymbolTableRecord symbolTableRecord : symbolTableRecords) {
            SymbolTable tempLink = symbolTableRecord.getLink();
            if (null != tempLink) temp.append(tempLink.print());
        }
        return temp.toString();
    }
}
