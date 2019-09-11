package SyntacticAnalyzer;

import java.util.ArrayList;

class ParseTreeNode {

    private ParseTreeNode parent;
    private ArrayList<ParseTreeNode> children;
    private String symbol;
    private int constructingProcess;
    private TerminalSet terminalSet;

    ParseTreeNode(String symbol, ParseTreeNode parent) {
        this.symbol = symbol;
        this.parent = parent;
        terminalSet = TerminalSet.getInstance();
        constructingProcess = 0;
        children = new ArrayList<>();
    }

    private String getSymbol() { return symbol; }

    ParseTreeNode addChildren(ArrayList<String> productionRule) {
        boolean findNextUnfinishedNode = false;
        ParseTreeNode nextUnfinishedNode = null;
        for(int i = 1; i < productionRule.size(); ++i) {
            ParseTreeNode parseTreeNode = new ParseTreeNode(productionRule.get(i), this);
            if (!findNextUnfinishedNode && !terminalSet.isTerminal(parseTreeNode.getSymbol())) {
                nextUnfinishedNode = parseTreeNode;
                constructingProcess = i;
                findNextUnfinishedNode = true;
            }
            children.add(parseTreeNode);
        }
        if (null == nextUnfinishedNode || nextUnfinishedNode.getSymbol().equals("EPSILON")) {
            return parent.findNextUnfinishedNode();
        } else {
            return nextUnfinishedNode;
        }
    }

    private ParseTreeNode findNextUnfinishedNode() {
        for (int i = constructingProcess; i < children.size(); ++i) {
            ParseTreeNode parseTreeNode = children.get(i);
            if (!terminalSet.isTerminal(parseTreeNode.getSymbol())) {
                constructingProcess = ++i;
                return parseTreeNode;
            }
        }
        if (null == parent) {
            return null;
        } else {
            return parent.findNextUnfinishedNode();
        }
    }

    public String toString() {
        StringBuilder node = new StringBuilder();
        if (0 == children.size()) {
            node.append("Leaf: ").append(symbol).append("\n");
        } else {
            node.append("Node: ").append(symbol).append("\n  children: ");
            for (ParseTreeNode child : children) {
                node.append(child.getSymbol()).append(" ");
            }
            node.append("\n");
            for (ParseTreeNode child : children) {
                node.append(child.toString());
            }
        }
        return node.toString();
    }
}
