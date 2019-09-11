package SemanticAnalyzer;

public class SymbolTableRecord {
    private String name;
    private String kind;
    private String type;
    private SymbolTable link;

    SymbolTableRecord(String name, String kind, String type, SymbolTable link) {
        this.name = name;
        this.kind = kind;
        this.type = type;
        this.link = link;
    }

    public String toString() { // for debugging usage only
        String temp = "  Name: " + name + ". Kind: " + kind + ". Type: " + type + ". Link: ";
        temp += null == link ? "X" : link.getTableName();
        return temp;
    }

    String getName() { return name; }

    String getKind() { return kind; }

    String getType() { return type; }

    String getLinkName() {
        if (null == link) return "X";
        return link.getTableName();
    }

    SymbolTable getLink() { return link; }

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
}
