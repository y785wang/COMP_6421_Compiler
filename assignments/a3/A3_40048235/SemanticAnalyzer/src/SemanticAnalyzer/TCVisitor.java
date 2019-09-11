package SemanticAnalyzer;

import SyntacticAnalyzer.ASTNode;

public class TCVisitor extends Visitor {

    private SymbolTable globalSymbolTable;

    public TCVisitor(SymbolTable globalSymbolTable) { this.globalSymbolTable = globalSymbolTable; }

    public void visit(ASTNode node) {
        String nodeType = node.getType();
        switch (nodeType) {
            case "var":
                ASTNode varElement = node.getChildren();
                String semanticType = "";
                String tableName = "";
                while (null != varElement) {
                    // TODO: check parents, find type from scope func, inher funcs, and global table
                    String id = varElement.getChildren().getValue();
                    if (varElement.getType().equals("funcDef")) {
                        tableName = tableName + "::" + tableName;
                    }
                    tableName = findIdType(id, node, tableName);
                    System.out.println("here_1 " + id);
                    varElement = varElement.getSibling();
                }

                node.setSemanticType(semanticType);
                break;
        }
    }

    private String findIdType(String id, ASTNode node, String tableName) {
        if (tableName.isEmpty()) {
            // TODO: local var in main
            // TODO: local var in funcDec or scopFunc or scopFuncInher
            return "A";
        } else {
            // TODO: find table, find id's type
            // TODO: if id is function, also check the fParamlist
            return "A::f";
//            return "integer";
        }
    }
}
