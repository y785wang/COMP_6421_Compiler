class ASTNode {
    private ASTNode parent;
    private ASTNode sibling; // right sibling, like linked list format
    private ASTNode children; // linked list format
    private String type;
    private String value;

    ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
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

    public String toString() { return "[ " + type + ", " + value + " ] "; }

    public String getType() { return type; }

    String getValue() { return value; }

    void setValue(String value) { this.value = value; }

    ASTNode getChildren() { return children; }

    ASTNode getSibling() { return sibling; }

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
}
