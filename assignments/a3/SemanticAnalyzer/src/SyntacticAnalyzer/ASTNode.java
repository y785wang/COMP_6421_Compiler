package SyntacticAnalyzer;

import SemanticAnalyzer.SymbolTable;
import SemanticAnalyzer.SymbolTableRecord;
import SemanticAnalyzer.Visitor;

public class ASTNode {
    private ASTNode parent;
    private ASTNode sibling; // right sibling, like linked list format
    private ASTNode children; // linked list format
    private String type;
    private String value;
    private String returnType; // only exist in funcDef node

    private SymbolTable symbolTable;
    private SymbolTableRecord symbolTableRecord;

    private String semanticType;

    ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        parent = null;
        symbolTable = null;
        switch (type) {
            case "intNum":
                semanticType = "integer";
                break;
            case "floatNum":
                semanticType = "float";
                break;
            default:
                semanticType = "Unknown";
                break;
        }
    }


    void addSibling(ASTNode sibling) {
        if (null == this.sibling) {
            this.sibling = sibling;
        } else {
            this.sibling.addSibling(sibling);
        }
    }

    void adoptChildren(ASTNode children) {
        this.children = children;
        ASTNode child = children;
        while (null != child) {
            child.setParent(this);
            child = child.getSibling();
        }
    }

    private void setParent(ASTNode parent) { this.parent = parent; }

    void setValue(String value) { this.value = value; }

    public void setSemanticType(String semanticType) { this.semanticType = semanticType; }

    public void setReturnType(String returnType) { this.returnType = returnType; }

    public String getReturnType() { return returnType; }

    public String getSemanticType() { return semanticType; }

    public ASTNode getParent() { return parent; }

    public String getType() { return type; }

    public String getValue() { return value; }

    public ASTNode getChildren() { return children; }

    public ASTNode getChildren(int indexOfSibling) {
        ASTNode temp = getChildren();
        for (int i = 1; i < indexOfSibling; ++i) {
            if (null == temp) break;
            temp = temp.getSibling();
        }
        return temp;
    }

    public ASTNode getSibling() { return sibling; }

    public String toString() { return "[ " + type + ", " + value + " ] "; }

    String print() {
        if (null == children) return "";
        StringBuilder thisNode = new StringBuilder("parent: [ " + getValue() + " ]\n  children: ");
        StringBuilder childrenNodes = new StringBuilder();
        while (null != children) {
            thisNode.append("[ ").append(children.getValue()).append(" ] ");
            childrenNodes.append(children.print());
            children = children.getSibling();
        }
        return thisNode.toString() + "\n" + childrenNodes.toString();
    }

    public void accept(Visitor visitor) {
        ASTNode tempChild = children;
        while (null != tempChild) {
            tempChild.accept(visitor);
            tempChild = tempChild.getSibling();
        }
        visitor.visit(this);
    }

    public void setSymbolTable(SymbolTable symbolTable) { this.symbolTable = symbolTable; }

    public SymbolTable getSymbolTable() { return symbolTable; }

    public void setSymbolTableRecord(SymbolTableRecord symbolTableRecord) { this.symbolTableRecord = symbolTableRecord; }

    public SymbolTableRecord getSymbolTableRecord() { return symbolTableRecord; }

    public boolean equal(ASTNode node) {
        return node.getType().equals(type) &&
                node.getValue().equals(value) &&
                node.getSemanticType().equals(semanticType);
    }

}
