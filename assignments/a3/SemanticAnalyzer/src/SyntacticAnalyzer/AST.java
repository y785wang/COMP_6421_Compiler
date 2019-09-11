package SyntacticAnalyzer;

import LexicalAnalyzer.Token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class AST {

    private Stack<ASTNode> semanticStack;

    AST(Stack<ASTNode> semanticStack) { this.semanticStack = semanticStack; }

    private void makeNode(String type, String value) {
        semanticStack.push(new ASTNode(type, value));
        if (type.equals("scopeSpec")) {
            ASTNode scopeSpec = semanticStack.pop();
            ASTNode id = semanticStack.pop();
            semanticStack.push(scopeSpec);
            semanticStack.push(id);
        }
    }

    private void makeSiblings() {
        ASTNode temp = semanticStack.pop();
        semanticStack.peek().addSibling(temp);
    }

    private void makeFamily(String parentName, int childrenSize) {
        ASTNode parent = new ASTNode(parentName, parentName);
        if (-1 == childrenSize) {
            ASTNode temp = null;
            while (!semanticStack.isEmpty()) {
                temp = semanticStack.pop();
                if (!semanticStack.isEmpty() && temp.toString().equals(semanticStack.peek().toString())) {
                    semanticStack.peek().addSibling(temp);
                } else {
                    break;
                }
            }
            parent.adoptChildren(temp);
            semanticStack.push(parent);
        } else {
            if (parentName.equals("addOp") || parentName.equals("multOp")) {
                ASTNode rightSide = semanticStack.pop();            // pop  addOp/multOp right side variable/expression
                parent.setValue(semanticStack.pop().getValue());    // get  addOp/multOp operator, store it
                semanticStack.push(rightSide);                      // push addOp/multOp right side back
                makeSiblings();
            } else {
                for (int i = 0; i < childrenSize-1; ++i) makeSiblings();
            }
            parent.adoptChildren(semanticStack.pop());
            semanticStack.push(parent);
        }
    }

    private void makeFamily(String parentName, HashSet<String> childrenTypes) {
        ASTNode parent = new ASTNode(parentName, parentName);
        String topType = semanticStack.peek().getType();
        // first condition for avoiding statBlock consume the assignStat which belongs a forStat
        if (!checkForStatAssignStat() && childrenTypes.contains(topType)) {
            while (true) {
                ASTNode top = semanticStack.pop();
                String secondTopType = semanticStack.peek().getType();
                // second condition for avoiding statBlock consume the assignStat which belongs a forStat
                if (childrenTypes.contains(secondTopType) && !(secondTopType.equals("assignStat") && checkForStatAssignStat())) {
                    semanticStack.push(top);
                    makeSiblings();
                } else {
                    parent.adoptChildren(top);
                    break;
                }
            }
        }
        semanticStack.push(parent);
    }

    private boolean checkForStatAssignStat() {
        if (semanticStack.size() < 5) return false;
        boolean result = false;
        ASTNode[] top5 = new ASTNode[5];
        for (int i = 0; i < 5; ++i) top5[i] = semanticStack.pop();
        if (top5[0].getType().equals("assignStat") &&
                top5[1].getType().equals("relExpr") &&
                top5[2].getType().equals("expr") &&
                top5[3].getType().equals("id") &&
                (top5[4].getType().equals("integer") || top5[4].getType().equals("float") || top5[4].getType().equals("id"))) {
            result = true;
        }
        for (int i = 4; i >= 0; --i) semanticStack.push(top5[i]);
        return result;
    }

    void doSemanticAction(int semanticAction, Token token) {
        switch (semanticAction) {
            case 1:
                makeNode(token.getType(), token.getValue());
                break;
            case 2:
                makeNode("inherList", "inherList");
                break;
            case 3:
                makeFamily("membList", new HashSet<>(Arrays.asList("varDecl", "funcDecl")));
                break;
            case 4:
                makeFamily("classDecl", 3);
                break;
            case 5:
                makeFamily("classList", -1);
                break;
            case 6:
                makeFamily("inherList", 1);
                break;
            case 7:
                makeSiblings();
                break;
            case 8:
                makeFamily("dimList", new HashSet<>(Arrays.asList("intNum", "floatNum")));
                break;
            case 9:
                makeFamily("varDecl", 3);
                break;
            case 10:
                makeNode("fparmList", "fparmList");
                break;
            case 11:
                makeFamily("funcDecl", 3);
                break;
            case 12:
                makeFamily("fparam", 3);
                break;
            case 13:
                makeFamily("fparmList", 1);
                break;
            case 14:
                makeFamily("scopeSpec", 1);
                break;
            case 15:
                makeFamily("statBlock",
                        new HashSet<>(Arrays.asList("ifStat", "assignStat", "forStat", "readStat", "writeStat", "returnStat", "varDecl")));
                break;
            case 16:
                makeFamily("funcDef", 5);
                break;
            case 17:
                makeFamily("funcDefList", new HashSet<>(Arrays.asList("funcDef")));
                break;
            case 18:
                makeFamily("prog", 3);
                break;
            case 19:
                makeNode("scopeSpec", "scopeSpec");
                break;
            case 20:
                makeFamily("var", new HashSet<>(Arrays.asList("dataMember", "fCall")));
                break;
            case 21:
                makeFamily("indexList", new HashSet<>(Arrays.asList("arithExpr")));
                break;
            case 22:
                makeFamily("dataMember", 1);
                break;
            case 23:
                makeFamily("dataMember", 2);
                break;
            case 24:
                makeFamily("arithExpr", 1);
                break;
            case 25:
                makeFamily("aParams", new HashSet<>(Arrays.asList("expr")));
                break;
            case 26:
                makeFamily("fCall", 2);
                break;
            case 27:
                makeFamily("expr", 1);
                break;
            case 28:
                makeFamily("assignStat", 2);
                break;
            case 29:
                makeFamily("term", 1);
                break;
            case 30:
                makeFamily("factor", 1);
                break;
            case 31:
                makeNode("aParams", "aParams");
                break;
            case 32:
                makeFamily("addOp", 3);
                break;
            case 33:
                makeFamily("multOp", 3);
                break;
            case 34:
                makeFamily("not", 1);
                break;
            case 35:
                makeFamily("sign", 1);
                break;
            case 36:
                makeFamily("relExpr", 3);
                break;
            case 37:
                makeFamily("readStat", 1);
                break;
            case 38:
                makeFamily("writeStat", 1);
                break;
            case 39:
                makeFamily("returnStat", 1);
                break;
            case 40:
                makeFamily("ifStat", 3);
                break;
            case 41:
                makeFamily("forStat", 6);
                break;
        }
    }

    String print() {
        ASTNode root = semanticStack.peek();
        StringBuilder thisNode = new StringBuilder("parent: [ " + root.getValue() + " ]\n  children: ");
        StringBuilder childrenNodes = new StringBuilder();
        ASTNode children = root.getChildren();
        while (null != children) {
            thisNode.append("[ ").append(children.getValue()).append(" ] ");
            childrenNodes.append(children.print());
            children = children.getSibling();
        }
        return thisNode.toString() + "\n" + childrenNodes.toString();
    }

    public ASTNode getRoot() { return semanticStack.peek(); }
}
