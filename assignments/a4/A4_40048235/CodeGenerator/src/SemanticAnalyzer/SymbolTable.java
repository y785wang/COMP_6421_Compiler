package SemanticAnalyzer;

import java.util.ArrayList;
import Error.Error;

public class SymbolTable {
    private String tableName;
    private String inher;       // only for class symbol table usage
    private String scopeSpec;   // only for function symbol table usage
    private ArrayList<SymbolTableRecord> symbolTableRecords;
    private int scopeOffset;
    private String returnType;  // only for function symbol table usage

    public SymbolTable(String tableName) {
        this.tableName = tableName;
        symbolTableRecords = new ArrayList<>();
        inher = "";
        scopeSpec = "";
        scopeOffset = 0;
        returnType = "";
    }

    void insert(SymbolTableRecord newRecord) {
        boolean error = false;
        for (SymbolTableRecord  oldRecord : symbolTableRecords) {
            if (oldRecord.equal(newRecord)) {
                error = true;
                // 4.2.12
                System.out.println("|\n|  syt, 4.2.12, multiply declared identifiers in scope " + tableName);
                System.out.println("|  " + oldRecord);
                System.out.println("|  " + newRecord);
//                String errorMessage = "Multiply declared identifiers in scope " + tableName + "\n";
//                errorMessage       += "  " + oldRecord + "\n";
//                errorMessage       += "  " + newRecord + "\n";
//                Error.getInstance().insert(1000, errorMessage, 4);
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
                        System.out.println("|\n|  syt, 4.2.9, missing member function definition for " + classRecord.getName() + " in scope " + globalRecord.getName());
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
                    System.out.println("|\n|  Undefined inheritance " + allInher + " in scope " + classRecord.getName());
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

    public String getInher() { return inher; }

    public ArrayList<SymbolTableRecord> getSymbolTableRecords() { return symbolTableRecords; }

    public String toString() {
        String name, kind, type, link;
        String startEndLine = "===========================================================================================================\n";
        String separateLine = "|-------------------+-----------+-------------------------------------+-------------------+------+---------\n";
        String endLine = "|\n";

        StringBuilder temp = new StringBuilder("\n");
        temp.append(startEndLine);
        temp.append(String.format("%-50s" , "| " + tableName + inher + returnType)).append(String.format("%-56s" , "scope offset: " + scopeOffset)).append(endLine);
        temp.append(separateLine);
        temp.append(String.format("%-20s" , "| name")).append(String.format("%-12s" , "| kind")).append(String.format("%-38s" , "| type")).append(String.format("%-20s" , "| link")).append(String.format("%-7s" , "| size")).append(String.format("%-9s" , "| offset")).append(endLine);
        for (SymbolTableRecord symbolTableRecord : symbolTableRecords) {
            name = symbolTableRecord.getName();
            kind = symbolTableRecord.getKind();
            type = symbolTableRecord.getType();
            link = symbolTableRecord.getLinkName();
            temp.append(separateLine);
            temp.append(String.format("%-20s" , "| " + name)).append(String.format("%-12s" , "| " + kind)).append(String.format("%-38s" , "| " + type)).append(String.format("%-20s" , "| " + link)).append(String.format("%-7s" , "| " + symbolTableRecord.getMemorySize())).append(String.format("%-9s" , "| " + symbolTableRecord.getOffset())).append(endLine);
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

    // called when this table is global table
    public SymbolTableRecord findRecord(String recordName, String recordKind) {
        for (SymbolTableRecord record : symbolTableRecords) {
            if (record.getName().equals(recordName) && record.getKind().equals(recordKind)) {
                return record;
            }
        }
        return null;
    }

    public SymbolTableRecord findRecord(String recordName) {
        for (SymbolTableRecord record : symbolTableRecords) {
            if (record.getName().equals(recordName)) {
                return record;
            }
        }
        return null;
    }

    public void initScopeOffset(SymbolTable globalSymbolTable) {
        for (SymbolTableRecord globalRecord : getSymbolTableRecords()) {
            switch (globalRecord.getKind()) {
                case "class": {
                    int offset = updateClassTable(globalSymbolTable, globalRecord.getLink());
                    globalRecord.setOffset(offset);
                    break;
                }
                case "function": { // free function case
                    int offset = updateFunctionTable(globalSymbolTable, globalRecord.getLink(), globalRecord.getType());
                    globalRecord.setOffset(offset);
                    break;
                }
                case "main": {
                    int offset = 0; // accumulate offset shown in class table
                    for (SymbolTableRecord mainTableRecord : globalRecord.getLink().getSymbolTableRecords()) {
                        offset -= mainTableRecord.calculateMemorySize(globalSymbolTable);
                        mainTableRecord.setOffset(offset);
                    }
                    globalRecord.getLink().setScopeOffset(offset);
                    globalRecord.setOffset(offset);
                    break;
                }
            }
        }

    }

    private int updateClassTable(SymbolTable globalSymbolTable, SymbolTable classTable) {
        int offset = 0; // accumulate offset shown in class table

        String inhers = classTable.getInher();
        if (!inhers.isEmpty()) {
            inhers = classTable.getInher().substring(3);
            // TODO: deal with multi-inher
            offset += findRecord(inhers, "class").getOffset();
        }

        for (SymbolTableRecord classTableRecord : classTable.getSymbolTableRecords()) {
            if (classTableRecord.getKind().equals("variable")) {
                offset -= classTableRecord.calculateMemorySize(globalSymbolTable);
                classTableRecord.setOffset(offset);
            } else { // member function case
                updateFunctionTable(globalSymbolTable, classTableRecord.getLink(), classTableRecord.getType());
                classTableRecord.setOffset(0);
            }
        }
        classTable.setScopeOffset(offset);
        return offset;
    }

    private int updateFunctionTable(SymbolTable globalSymbolTable, SymbolTable funcTable, String funcType) {
        // accumulate offset shown in function table, -4 for func tag, i.e. func1     sw -4(r14),r15
        int offset = -4;

        // check return type, update offset
        String funcReturnType;
        int index = funcType.indexOf(":");
        if (-1 == index) {
            funcReturnType = funcType;
        } else {
            funcReturnType = funcType.substring(0, index-1);
        }
        switch (funcReturnType) {
            case "integer":
                offset -= 4;
                break;
            case "float":
                offset -= 8;
                break;
            default:
                offset += globalSymbolTable.findRecord(funcReturnType, "class").getOffset();
                break;
        }

        for (SymbolTableRecord funcTableRecord : funcTable.getSymbolTableRecords()) {
            offset -= funcTableRecord.calculateMemorySize(globalSymbolTable);
            funcTableRecord.setOffset(offset);
        }
        funcTable.setScopeOffset(offset);
        return offset;
    }

    private void setScopeOffset(int scopeOffset) { this.scopeOffset = scopeOffset; }

    void setReturnType(String returnType) { this.returnType = ", return " + returnType; }

    // for Circular class dependencies usage
    void removeRecord(SymbolTableRecord circularClassRecord) {
        symbolTableRecords.remove(circularClassRecord);
    }
}
