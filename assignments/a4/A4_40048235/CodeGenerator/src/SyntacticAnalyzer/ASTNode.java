package SyntacticAnalyzer;

import CodeGenerater.SBCGVisitor;
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
    private int dimension; // only exist in dimListNode
    private int tag; // memory address store the value that this node represents
    private int lineNumber;

    private SymbolTable symbolTable;
    private SymbolTableRecord symbolTableRecord;

    private String semanticType;


    ASTNode(String type, String value, int lineNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
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
        dimension = 1;
        tag = -1;
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

    private void setParent(ASTNode parent) {
        this.parent = parent;
    }

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
        ASTNode child = children;
        while (null != child) {
            thisNode.append("[ ").append(child.getValue()).append(" ] ");
            childrenNodes.append(child.print());
            child = child.getSibling();
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

    public void accept(SBCGVisitor visitor) { visitor.visit(this); }

    public void setSymbolTable(SymbolTable symbolTable) { this.symbolTable = symbolTable; }

    public SymbolTable getSymbolTable() { return symbolTable; }

    public void setSymbolTableRecord(SymbolTableRecord symbolTableRecord) { this.symbolTableRecord = symbolTableRecord; }

    public SymbolTableRecord getSymbolTableRecord() { return symbolTableRecord; }

    public boolean equal(ASTNode node) {
        return node.getType().equals(type) &&
                node.getValue().equals(value) &&
                node.getSemanticType().equals(semanticType);
    }

    public void setDimension(int dimension) { this.dimension = dimension; }

    public void setTagAndPassToParent(int tag) {
        this.tag = tag;
        if (-1 != tag) {
            ASTNode singleChild = parent.getChildren();
            while (null == singleChild.getSibling()) {
                singleChild.parent.setTag(tag);
                String nodeType = singleChild.getType();
                switch (nodeType) {
                    case "varDecl": case "funcDef":
                        return;
                }
                singleChild = singleChild.getParent().getParent().getChildren();
            }
        }
    }

    private void setTag(int tag) { this.tag = tag; }

    public int getTag() { return tag; }

    public String getVarId() {
        if (type.equals("var")) {
            return this.getChildren().getChildren().getValue();
        } else {
            System.out.println("Error.Error at ASTNode.getVarId(), not var node");
        }
        return "";
    }

    public int getIntNumNodeValue() {
        ASTNode child = children;
        while (null != child) {
            String childType = child.getType();
            if (childType.equals("addOp") || childType.equals("multOp") || childType.equals("relExpr")) {
                // dim is represent as comples arithExpr
                return -1;
            }
            if (child.getType().equals("intNum")) {
                return Integer.parseInt(child.getValue());
            } else {
                child = child.getChildren();
            }
        }
        System.out.println("Error.Error at ASTNode.getIntNumNodeValue(), intNum node not found");
        return -1;
    }

    private void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }

    public int getLineNumber() { return lineNumber; }

    private void passLineNumberToParent() {
        if (null == parent) return;
        if (-1 != lineNumber) {
            ASTNode singleChild = parent.getChildren();
            while (null == singleChild.getSibling()) {
                singleChild.parent.setLineNumber(lineNumber);
                String nodeType = singleChild.getType();
                switch (nodeType) {
                    case "varDecl": case "funcDef":
                        return;
                }
                singleChild = singleChild.getParent().getParent().getChildren();
            }
        }
    }

    public int findLineNumber() {
        ASTNode child = children;
        while (null != children) {
            if (-1 != child.getLineNumber()) {
                return child.getLineNumber();
            }
            child = child.getChildren();
        }
        System.out.println("Error at ASTNode.findLineNumber(), line number not found");
        return -1;
    }
}
