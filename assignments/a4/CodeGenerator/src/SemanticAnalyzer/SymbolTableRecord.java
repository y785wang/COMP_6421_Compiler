package SemanticAnalyzer;

public class SymbolTableRecord {
    private String name;
    private String kind;
    private String type;
    private SymbolTable link;
    private int memorySize;
    private int offset;
    private int lineNumber;

    SymbolTableRecord(String name, String kind, String type, SymbolTable link) {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.link = link;
        this.memorySize = 0;
        this.offset = 0;
    }

    public String toString() { // for debugging usage only
        String temp = "  Name: " + name + ". Kind: " + kind + ". Type: " + type + ". Link: ";
        temp += null == link ? "X" : link.getTableName();
        return temp;
    }

    public String getName() { return name; }

    public String getKind() { return kind; }

    public String getType() { return type; }

    String getLinkName() {
        if (null == link) return "X";
        return link.getTableName();
    }

    public SymbolTable getLink() { return link; }

    void setLink(SymbolTable symbolTable) { this.link = symbolTable; }

    boolean equal(SymbolTableRecord record) {
        if (kind.equals("variable") || kind.equals("parameter") || kind.equals("class")) {
            return name.equals(record.getName()) && kind.equals(record.getKind());
        } else if (kind.equals("function")) {
            // TODO: remove dimList, then compare
            return name.equals(record.getName()) && getFuncParamType().equals(record.getFuncParamType());
        }
        return false;
    }

    private String getFuncParamType() {
        int indexOfColon = type.indexOf(":");
        if (-1 == indexOfColon) {
            return "";
        } else {
            return type.substring(indexOfColon+2);
        }
    }

    int calculateMemorySize(SymbolTable globalSymbolTable) {
        if (type.contains("integer")) {
            memorySize = 4 * getDimension(type.substring(7));
        } else if (type.contains("float")) {
            memorySize = 8 * getDimension(type.substring(5));
        } else {
            String classId;
            int dimListIndex = type.indexOf("[");
            if (-1 == dimListIndex) {
                classId = type;
            } else {
                classId = type.substring(0, dimListIndex);
            }
            memorySize = -1 * globalSymbolTable.findRecord(classId, "class").getOffset() * getDimension(type.substring(classId.length()));
        }
        return memorySize;
    }

    public int getMemorySize() { return memorySize; }

    void setOffset(int scopeOffset) { this.offset = scopeOffset; }

    public int getOffset() { return offset; }

    private int getDimension(String dimList) {
        int dim = 1;
        while (!dimList.isEmpty()) {
            dimList = dimList.substring(1);
            int endSquareBracketIndex = dimList.indexOf("]");
            dim *= Integer.parseInt(dimList.substring(0,endSquareBracketIndex));
            dimList = dimList.substring(endSquareBracketIndex+1);
        }
        return dim;
    }
}
