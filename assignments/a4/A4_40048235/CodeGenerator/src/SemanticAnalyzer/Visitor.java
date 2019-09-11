package SemanticAnalyzer;

import SyntacticAnalyzer.ASTNode;

import java.util.Arrays;
import java.util.HashSet;

public class Visitor {
    public void visit(ASTNode node) {}

    String getScopeMessage(ASTNode node) {
        StringBuilder scope = new StringBuilder();
        HashSet<String> scopes = new HashSet<>(Arrays.asList("ifStat", "assignStat", "forStat", "readStat", "writeStat", "returnStat", "varDecl", "prog", "funcDef", "classDecl"));
        ASTNode parent = node.getParent();
        while (null != parent) {
            String parentType = parent.getType();
            if (scopes.contains(parentType)) {
                scope.append(" -> ");
                switch (parentType) {
                    case "prog":
                        scope.append("main");
                        break;
                    case "classDecl":
                        scope.append(parent.getChildren().getValue());
                        parent = parent.getParent().getParent();
                        break;
                    case "funcDef":
                        ASTNode scopeSepcNode = parent.getChildren().getSibling();
                        String scopeSpecName = null == scopeSepcNode.getChildren() ? "" : scopeSepcNode.getChildren().getValue() + "::";
                        scope.append(scopeSpecName);

                        ASTNode idNode = scopeSepcNode.getSibling();
                        scope.append(idNode.getValue());

                        scope.append(getFuncParamListType(idNode.getSibling()));
                        parent = parent.getParent().getParent();
                        break;
                    default:
                        scope.append(parent.getType());
                        break;
                }
            }
            parent = parent.getParent();
        }
        return scope.toString();
    }

    String getFuncParamListType(ASTNode fparmListNode) {
        StringBuilder fparamListType = new StringBuilder();
        String separate = " : ";
        ASTNode fparamNode = fparmListNode.getChildren();
        while (null != fparamNode) {
            fparamListType.append(separate);
            separate = ", ";

            ASTNode fparamChild = fparamNode.getChildren(); // get fparam type
            String fparamType = fparamChild.getValue();
            fparamListType.append(fparamType);

            fparamChild = fparamChild.getSibling();         // get fparam id
            String name = fparamChild.getValue();

            fparamChild = fparamChild.getSibling();         // get fparam dimList
            String fparamDimListType = getDimListType(fparamChild);
            fparamListType.append(fparamDimListType);

            fparamNode.setSymbolTableRecord(new SymbolTableRecord(name, "parameter", fparamType+fparamDimListType, fparmListNode.getSymbolTable()));
            fparamNode = fparamNode.getSibling();
        }
        return fparamListType.toString();
    }

    String getDimListType(ASTNode dimListNode) {
        int dim = 1;
        StringBuilder dimListType = new StringBuilder();
        ASTNode numNode = dimListNode.getChildren();
        while (null != numNode) {
            dimListType.append("[").append(numNode.getValue()).append("]");
            dim *= (Integer.parseInt(numNode.getValue()));
            numNode = numNode.getSibling();
        }
        dimListNode.setDimension(dim);
        return dimListType.toString();
    }
}
